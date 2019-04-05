/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.bpa.transaction.workflow;

import org.apache.commons.lang3.StringUtils;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.PermitLetterToParty;
import org.egov.bpa.transaction.entity.SiteDetail;
import org.egov.bpa.transaction.entity.dto.BpaStateInfo;
import org.egov.bpa.transaction.service.BpaStatusService;
import org.egov.bpa.transaction.service.LettertoPartyService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.eis.entity.Assignment;
import org.egov.eis.service.PositionMasterService;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.workflow.entity.StateHistory;
import org.egov.infra.workflow.matrix.entity.WorkFlowMatrix;
import org.egov.infra.workflow.matrix.service.WorkFlowMatrixService;
import org.egov.infra.workflow.service.SimpleWorkflowService;
import org.egov.pims.commons.Position;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REGISTERED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_RESCHEDULED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_SCHEDULED;
import static org.egov.bpa.utils.BpaConstants.DESIGNATION_AE;
import static org.egov.bpa.utils.BpaConstants.DESIGNATION_OVERSEER;
import static org.egov.bpa.utils.BpaConstants.FWD_TO_AE_AFTER_TS_INSP;
import static org.egov.bpa.utils.BpaConstants.FWD_TO_CLERK_PENDING;
import static org.egov.bpa.utils.BpaConstants.FWD_TO_OVERSEER_AFTER_TS_INSPN;

/**
 * The Class ApplicationCommonWorkflow.
 */
public abstract class BpaApplicationWorkflowCustomImpl implements BpaApplicationWorkflowCustom {
    private static final Logger LOG = LoggerFactory.getLogger(BpaApplicationWorkflowCustomImpl.class);

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private PositionMasterService positionMasterService;

    @Autowired
    @Qualifier("workflowService")
    private SimpleWorkflowService<BpaApplication> bpaApplicationWorkflowService;

    @Autowired
    private BpaStatusService bpaStatusService;

    @Autowired
    private BpaWorkFlowService bpaWorkFlowService;

    @Autowired
    private BpaUtils bpaUtils;
    @Autowired
    private LettertoPartyService lettertoPartyService;
    @Autowired
    private WorkFlowMatrixService workFlowMatrixService;

    @Autowired
    public BpaApplicationWorkflowCustomImpl() {

    }

    @Override
    @Transactional
    public void createCommonWorkflowTransition(final BpaApplication application,
                                               Long approvalPosition, final String approvalComent, final String additionalRule,
                                               final String workFlowAction, final BigDecimal amountRule) {

        if (LOG.isDebugEnabled())
            LOG.debug(" Create WorkFlow Transition Started  ...");
        final User user = securityUtils.getCurrentUser();
        final DateTime currentDate = new DateTime();
        Position pos = null;
        Assignment wfInitiator = null;
        if (application.getCreatedBy() != null)
            wfInitiator = bpaWorkFlowService.getWorkFlowInitiator(application.getState(), application.getCreatedBy());
        User ownerUser = null;
        if (approvalPosition != null && approvalPosition > 0)
            pos = positionMasterService.getPositionById(approvalPosition);
		if(pos != null)
			ownerUser = bpaWorkFlowService.getAssignmentsByPositionAndDate(pos.getId(), new Date()).get(0).getEmployee();
        WorkFlowMatrix wfmatrix;
        if (null == application.getState()) {
            String pendingAction = null;
            if(!bpaUtils.isDocScrutinyIntegrationRequired())
                pendingAction = FWD_TO_CLERK_PENDING;
            if (bpaUtils.applicationInitiatedByNonEmployee(application.getCreatedBy()) || (application.getAdmissionfeeAmount() != null
                                                                                           && application.getAdmissionfeeAmount().compareTo(BigDecimal.ZERO) == 0))
                wfmatrix = bpaApplicationWorkflowService.getWfMatrix(application.getStateType(), null,
                        null, additionalRule, BpaConstants.WF_NEW_STATE, pendingAction);
            else
                wfmatrix = bpaApplicationWorkflowService.getWfMatrix(application.getStateType(), null,
                        null, additionalRule, BpaConstants.WF_CREATED_STATE, pendingAction);

            if (wfmatrix != null) {
                if (pos == null) {
                    SiteDetail siteDetail = application.getSiteDetail().get(0);
					pos = bpaUtils.getUserPositionByZone(wfmatrix.getNextDesignation(),
							bpaUtils.getBoundaryForWorkflow(siteDetail).getId());
                    List<Assignment> assignments = bpaWorkFlowService.getAssignmentsByPositionAndDate(pos.getId(), new Date());
                    if(!assignments.isEmpty())
                        ownerUser = assignments.get(0).getEmployee();
                }
                application.setStatus(getStatusByCurrentMatrxiStatus(wfmatrix));
                application.transition().start()
                           .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                           .withComments(approvalComent).withInitiator(wfInitiator != null ? wfInitiator.getPosition() : null)
                           .withStateValue(wfmatrix.getNextState()).withDateInfo(new Date()).withOwner(pos).withOwner(ownerUser)
                           .withNextAction(wfmatrix.getNextAction()).withNatureOfTask(BpaConstants.NATURE_OF_WORK);
            }

        } else if (BpaConstants.WF_PERMIT_FEE_COLL_PENDING.equalsIgnoreCase(workFlowAction)) {
            if (LOG.isInfoEnabled())
                LOG.info("**********Permit Fee Collection Pending******* Application Number---------->>>>>"+application.getApplicationNumber());
            wfmatrix = bpaApplicationWorkflowService.getWfMatrix(application.getStateType(), null, amountRule,
                    additionalRule, application.getCurrentState().getValue(), application.getState().getNextAction());
            if (LOG.isInfoEnabled())
                LOG.info(" *************workflow matrix**************"+wfmatrix);
            if (LOG.isInfoEnabled())
                LOG.info(" *************Date INFO*************"+currentDate);
            if (LOG.isInfoEnabled())
                LOG.info(" *************User INFO*************"+user);
            application.transition().progressWithStateCopy()
                       .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                       .withComments(approvalComent)
                       .withStateValue(wfmatrix.getNextState()).withDateInfo(currentDate.toDate())
                       .withOwner(pos).withOwner(ownerUser)
                       .withNextAction(wfmatrix.getNextAction()).withNatureOfTask(BpaConstants.NATURE_OF_WORK);
        } else if (BpaConstants.WF_APPROVE_BUTTON.equalsIgnoreCase(workFlowAction)
                   && (BpaConstants.APPLICATION_STATUS_APPROVED.equalsIgnoreCase(application.getStatus().getCode())
                       || BpaConstants.APPLICATION_STATUS_NOCUPDATED.equalsIgnoreCase(application.getStatus().getCode()))) {
            if(!bpaUtils.checkAnyTaxIsPendingToCollect(application.getDemand()))
            	if(application.getApplicationType().getName().equals(BpaConstants.LOWRISK))
                wfmatrix = bpaApplicationWorkflowService.getWfMatrix(application.getStateType(), null, null,
                            additionalRule, "Application Approval Pending", "Forwarded to Assistant Engineer For Approval");
            	else
                wfmatrix = bpaApplicationWorkflowService.getWfMatrix(application.getStateType(), null, null,
                        additionalRule, "Final Approval Process initiated", "Permit Fee Collection Pending");
            else
                wfmatrix = bpaApplicationWorkflowService.getWfMatrix(application.getStateType(), null, amountRule,
                    additionalRule, application.getCurrentState().getValue(), application.getState().getNextAction());

            BpaStatus status = bpaStatusService
                    .findByModuleTypeAndCode(BpaConstants.BPASTATUS_MODULETYPE, BpaConstants.APPLICATION_STATUS_APPROVED);
            if (status != null)
                application.setStatus(status);

            application.transition().progressWithStateCopy()
                       .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                       .withComments(approvalComent)
                       .withStateValue(wfmatrix.getNextState()).withDateInfo(currentDate.toDate())
                       .withOwner(pos).withOwner(ownerUser)
                       .withNextAction(wfmatrix.getNextAction()).withNatureOfTask(BpaConstants.NATURE_OF_WORK);
        } else if (BpaConstants.WF_REJECT_BUTTON.equalsIgnoreCase(workFlowAction)) {
            wfmatrix = bpaApplicationWorkflowService.getWfMatrix(application.getStateType(), null,
                    null, additionalRule, BpaConstants.WF_REJECT_STATE, null);
            application.setStatus(getStatusByPassingCode(BpaConstants.WF_REJECT_STATE));
            application.transition().progressWithStateCopy()
                       .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                       .withComments(approvalComent)
                       .withStateValue(BpaConstants.WF_REJECT_STATE).withDateInfo(currentDate.toDate())
                       .withOwner(pos).withOwner(ownerUser)
                       .withNextAction(wfmatrix.getNextAction())
                       .withNatureOfTask(BpaConstants.NATURE_OF_WORK);

        } else if (BpaConstants.WF_INITIATE_REJECTION_BUTTON.equalsIgnoreCase(workFlowAction)) {
        	//For one day permit, position need not be set 
        	if(!application.getIsOneDayPermitApplication())  {
        		pos = bpaWorkFlowService.getApproverPositionOfElectionWardByCurrentState(application, BpaConstants.REJECT_BY_CLERK);
        		ownerUser = bpaWorkFlowService.getAssignmentsByPositionAndDate(pos.getId(), new Date()).get(0).getEmployee();
        	}
            wfmatrix = bpaApplicationWorkflowService.getWfMatrix(application.getStateType(), null,
                    null, additionalRule, BpaConstants.REJECT_BY_CLERK, null);
            BpaStateInfo bpaStateInfo = bpaWorkFlowService.getBpaStateInfo(application.getCurrentState(), application.getStateHistory(), application.getTownSurveyorInspectionRequire(), new BpaStateInfo(), wfmatrix, workFlowAction);
            application.setStatus(getStatusByPassingCode(BpaConstants.WF_REJECT_STATE));
            application.transition().progressWithStateCopy()
                       .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                       .withComments(approvalComent)
                       .withStateValue(BpaConstants.REJECT_BY_CLERK).withDateInfo(currentDate.toDate())
                       .withOwner(pos).withOwner(ownerUser)
                       .withNextAction(wfmatrix.getNextAction())
                       .withNatureOfTask(BpaConstants.NATURE_OF_WORK).withExtraInfo(bpaStateInfo);

        } else if (BpaConstants.GENERATEREJECTNOTICE.equalsIgnoreCase(workFlowAction) || BpaConstants.WF_CANCELAPPLICATION_BUTTON.equalsIgnoreCase(workFlowAction)) {
            application.setStatus(getStatusByPassingCode(BpaConstants.APPLICATION_STATUS_CANCELLED));
            application.transition().end()
                       .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                       .withComments(approvalComent).withDateInfo(currentDate.toDate())
                       .withNextAction(BpaConstants.WF_END_STATE).withNatureOfTask(BpaConstants.NATURE_OF_WORK);

            if (additionalRule != null && additionalRule.equalsIgnoreCase(BpaConstants.CREATE_ADDITIONAL_RULE_CREATE))
                application.setStatus(getStatusByPassingCode(BpaConstants.APPLICATION_STATUS_CANCELLED));

        } else if (BpaConstants.LETTERTOPARTYINITIATE.equalsIgnoreCase(workFlowAction)) {
            wfmatrix = bpaApplicationWorkflowService.getWfMatrix(application.getStateType(), null,
                    null, additionalRule, workFlowAction, null);
            if (wfmatrix != null) {
                application.setStatus(getStatusByCurrentMatrxiStatus(wfmatrix));
                application.transition().progressWithStateCopy()
                           .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                           .withComments(approvalComent)
                           .withStateValue(wfmatrix.getNextState()).withDateInfo(currentDate.toDate())
                           .withOwner(pos).withOwner(ownerUser)
                           .withNextAction(wfmatrix.getNextAction()).withNatureOfTask(BpaConstants.NATURE_OF_WORK);
            }
        } else if (BpaConstants.LPCREATED.equalsIgnoreCase(workFlowAction)) {
            System.out.println(application.getStateType());
            wfmatrix = bpaApplicationWorkflowService.getWfMatrix(application.getStateType(), null, null, additionalRule,
                    BpaConstants.LPCREATED, null);
            application.setStatus(getStatusByCurrentMatrxiStatus(wfmatrix));
            application.transition().progressWithStateCopy()
                       .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                       .withComments(approvalComent).withStateValue(wfmatrix.getNextState())
                       .withDateInfo(currentDate.toDate()).withOwner(application.getState().getOwnerPosition()).withOwner(application.getState().getOwnerUser())
                       .withNextAction(wfmatrix.getNextAction()).withNatureOfTask(BpaConstants.NATURE_OF_WORK);
        } else if (BpaConstants.LPREPLYRECEIVED.equalsIgnoreCase(workFlowAction)) {
            List<PermitLetterToParty> letterToParties = lettertoPartyService.findByBpaApplicationOrderByIdDesc(application);
            StateHistory<Position> stateHistory = bpaWorkFlowService.getStateHistoryToGetLPInitiator(application.getStateHistory(), letterToParties.get(0).getLetterToParty().getStateForOwnerPosition());
            wfmatrix = workFlowMatrixService.getWorkFlowObjectbyId(bpaWorkFlowService.getPreviousWfMatrixId(application.getStateHistory()));
            if(null == wfmatrix)
                wfmatrix = bpaApplicationWorkflowService.getWfMatrix(application.getStateType(), null, null, additionalRule,
                        letterToParties.get(0).getLetterToParty().getCurrentStateValueOfLP(), null);
            application.setStatus(letterToParties.get(0).getLetterToParty().getCurrentApplnStatus());
            application.transition().progressWithStateCopy()
                       .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                       .withComments(approvalComent).withStateValue(wfmatrix.getNextState())
                       .withDateInfo(currentDate.toDate()).withOwner(stateHistory.getOwnerPosition()).withOwner(stateHistory.getOwnerUser())
                       .withNextAction(wfmatrix.getNextAction()).withNatureOfTask(BpaConstants.NATURE_OF_WORK);
        } else {
            Assignment approverAssignment = bpaWorkFlowService.getApproverAssignment(pos);
            if(approverAssignment == null)
                approverAssignment = bpaWorkFlowService.getAssignmentsByPositionAndDate(pos.getId(), new Date()).get(0);
            if (BpaConstants.WF_REVERT_BUTTON.equalsIgnoreCase(workFlowAction)) {
                application.setSentToPreviousOwner(true);
                pos = application.getCurrentState().getPreviousOwner();
                wfmatrix = workFlowMatrixService.getWorkFlowObjectbyId(bpaWorkFlowService.getPreviousWfMatrixId(application.getStateHistory()));
            } else if (BpaConstants.WF_AUTO_RESCHDLE_APPMNT_BUTTON.equalsIgnoreCase(workFlowAction)) {
                wfmatrix = bpaApplicationWorkflowService.getWfMatrix(application.getStateType(), null,
                        amountRule, additionalRule,
                        application.getCurrentState().getValue(), BpaConstants.WF_AUTO_RESCHEDULE_PENDING);
            } else if (BpaConstants.APPLICATION_STATUS_NOCUPDATED.equalsIgnoreCase(application.getStatus().getCode())) {
                wfmatrix = bpaApplicationWorkflowService.getWfMatrix(application.getStateType(), null,
                        amountRule, additionalRule,
                        application.getCurrentState().getValue(), application.getState().getNextAction());
            } else if (BpaConstants.WF_TS_INSPECTION_INITIATED.equalsIgnoreCase(application.getStatus().getCode())) {
                pos = positionMasterService.getPositionById(bpaWorkFlowService.getTownSurveyorInspnInitiator(application.getStateHistory(), application.getCurrentState()));
                wfmatrix = bpaApplicationWorkflowService.getWfMatrix(application.getStateType(), null,
                        amountRule, additionalRule,
                        application.getCurrentState().getValue(), BpaConstants.WF_TS_APPROVAL_PENDING);
            } else if (BpaConstants.APPLICATION_STATUS_TS_INS.equalsIgnoreCase(application.getStatus().getCode())) {
                Assignment currentUserAssgmnt = bpaWorkFlowService.getApproverAssignment(application.getCurrentState().getOwnerPosition());
                if(currentUserAssgmnt == null)
                    currentUserAssgmnt = bpaWorkFlowService.getAssignmentsByPositionAndDate(application.getCurrentState().getOwnerPosition().getId(), new Date()).get(0);
                String pendingAction = StringUtils.EMPTY;
                if (DESIGNATION_AE.equals(currentUserAssgmnt.getDesignation().getName()))
                    pendingAction = FWD_TO_AE_AFTER_TS_INSP;
                else if (DESIGNATION_OVERSEER.equals(currentUserAssgmnt.getDesignation().getName()))
                    pendingAction = FWD_TO_OVERSEER_AFTER_TS_INSPN;
                wfmatrix = bpaApplicationWorkflowService.getWfMatrix(application.getStateType(), null,
                        amountRule, additionalRule,
                        application.getCurrentState().getValue(), pendingAction);
            } else if (BpaConstants.APPLICATION_STATUS_APPROVED.equalsIgnoreCase(application.getStatus().getCode())
                       && !BpaConstants.APPLICATION_STATUS_RECORD_APPROVED.equalsIgnoreCase(application.getState().getValue())) {
                wfmatrix = bpaApplicationWorkflowService.getWfMatrix(application.getStateType(), null,
                        amountRule, additionalRule,
                        application.getCurrentState().getValue(), null);
            } else if (approvalComent != null && approvalComent.equals(BpaConstants.BPAFEECOLLECT)
                       && bpaUtils.applicationInitiatedByNonEmployee(application.getCreatedBy())
                       && application.getStatus().getCode().equals(BpaConstants.APPLICATION_STATUS_REGISTERED)) {
                wfmatrix = bpaApplicationWorkflowService.getWfMatrix(application.getStateType(), null,
                        null, additionalRule, BpaConstants.WF_NEW_STATE, null);
            } else if(approverAssignment != null && BpaConstants.DESIGNATION_TOWN_SURVEYOR.equalsIgnoreCase(approverAssignment.getDesignation().getName())) {
                wfmatrix = bpaApplicationWorkflowService.getWfMatrix(application.getStateType(), null,
                        null, additionalRule, BpaConstants.WF_TS_INSPECTION_INITIATED, BpaConstants.WF_AE_APPROVAL_PENDING);
            } else if(BpaConstants.WF_RESCHDLE_APPMNT_BUTTON.equalsIgnoreCase(workFlowAction)) {
                wfmatrix = bpaApplicationWorkflowService.getWfMatrix(application.getStateType(), null,
                        null, additionalRule, application.getCurrentState().getValue(), BpaConstants.WF_DOC_SCRTNY_RE_SCHDLE_PENDING);
            } else if (application.getState() != null
                       && application.getState().getValue().equalsIgnoreCase(APPLICATION_STATUS_REGISTERED) ||
                       application.getState().getValue().equalsIgnoreCase(APPLICATION_STATUS_SCHEDULED)
                       || application.getState().getValue().equalsIgnoreCase(APPLICATION_STATUS_RESCHEDULED)) {
                wfmatrix = bpaApplicationWorkflowService.getWfMatrix(application.getStateType(), null,
                        null, additionalRule, application.getCurrentState().getValue(), application.getState().getNextAction());
            }  else {
                wfmatrix = bpaApplicationWorkflowService.getWfMatrix(application.getStateType(), null,
                        null, additionalRule, application.getCurrentState().getValue(), application.getState().getNextAction());
                if(wfmatrix == null)
                    wfmatrix = bpaApplicationWorkflowService.getWfMatrix(application.getStateType(), null,
                        null, additionalRule, application.getCurrentState().getValue(), null);
            }
            if (wfmatrix != null) {
                BpaStateInfo bpaStateInfo = bpaWorkFlowService.getBpaStateInfo(application.getCurrentState(), application.getStateHistory(), application.getTownSurveyorInspectionRequire(), new BpaStateInfo(), wfmatrix, workFlowAction);
                BpaStatus status = getStatusByCurrentMatrxiStatus(wfmatrix);
                ownerUser = bpaWorkFlowService.getAssignmentsByPositionAndDate(pos.getId(), new Date()).get(0).getEmployee();
                if (status != null)
                    application.setStatus(getStatusByCurrentMatrxiStatus(wfmatrix));
                
				if (workFlowAction.equalsIgnoreCase(BpaConstants.GENERATEREVOCATIONNOTICE))
					application.setStatus(getStatusByPassingCode("Revocated"));
                
                if (BpaConstants.GENERATEPERMITORDER.equalsIgnoreCase(workFlowAction) || wfmatrix.getNextAction().contains("END"))
                    application.transition().end()
                               .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                               .withComments(approvalComent).withDateInfo(currentDate.toDate())
                               .withNextAction(wfmatrix.getNextAction()).withNatureOfTask(BpaConstants.NATURE_OF_WORK);
                else
                    application.transition().progressWithStateCopy()
                               .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                               .withComments(approvalComent)
                               .withStateValue(wfmatrix.getNextState()).withDateInfo(currentDate.toDate()).withOwner(pos).withOwner(ownerUser)
                               .withNextAction(wfmatrix.getNextAction()).withNatureOfTask(BpaConstants.NATURE_OF_WORK).withExtraInfo(bpaStateInfo);
            }
        }
        if (LOG.isDebugEnabled())
            LOG.debug(" WorkFlow Transition Completed ");
        bpaUtils.updatePortalUserinbox(application, null);

    }

    private BpaStatus getStatusByCurrentMatrxiStatus(final WorkFlowMatrix wfmatrix) {
        if (wfmatrix != null && wfmatrix.getNextStatus() != null && !"".equals(wfmatrix.getNextStatus()))
            return bpaStatusService
                    .findByModuleTypeAndCode(BpaConstants.BPASTATUS_MODULETYPE, wfmatrix.getNextStatus());
        return null;
    }

    private BpaStatus getStatusByPassingCode(final String code) {
        if (code != null && !"".equals(code))
            return bpaStatusService
                    .findByModuleTypeAndCode(BpaConstants.BPASTATUS_MODULETYPE, code);
        return null;
    }

}