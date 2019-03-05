/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) <2018>  eGovernments Foundation
 *
 *  The updated version of eGov suite of products as by eGovernments Foundation
 *  is available at http://www.egovernments.org
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see http://www.gnu.org/licenses/ or
 *  http://www.gnu.org/licenses/gpl.html .
 *
 *  In addition to the terms of the GPL license to be adhered to in using this
 *  program, the following additional terms are to be complied with:
 *
 *      1) All versions of this program, verbatim or modified must carry this
 *         Legal Notice.
 *      Further, all user interfaces, including but not limited to citizen facing interfaces,
 *         Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *         derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *      For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *      For any further queries on attribution, including queries on brand guidelines,
 *         please contact contact@egovernments.org
 *
 *      2) Any misrepresentation of the origin of the material is prohibited. It
 *         is required that all modified versions of this material be marked in
 *         reasonable ways as different from the original version.
 *
 *      3) This license does not grant any rights to any user of the program
 *         with regards to rights under trademark law for use of the trade names
 *         or trademarks of eGovernments Foundation.
 *
 *  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.bpa.transaction.workflow.oc;

import org.apache.commons.lang3.StringUtils;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.SiteDetail;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.entity.dto.BpaStateInfo;
import org.egov.bpa.transaction.entity.oc.OCLetterToParty;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.service.BpaStatusService;
import org.egov.bpa.transaction.service.oc.OCLetterToPartyService;
import org.egov.bpa.transaction.workflow.BpaWorkFlowService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.bpa.utils.OcConstants;
import org.egov.bpa.utils.OccupancyCertificateUtils;
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

import java.util.Date;
import java.util.List;

import static org.egov.bpa.utils.BpaConstants.DESIGNATION_AE;
import static org.egov.bpa.utils.BpaConstants.DESIGNATION_OVERSEER;
import static org.egov.bpa.utils.BpaConstants.FWD_TO_AE_AFTER_TS_INSP;
import static org.egov.bpa.utils.BpaConstants.FWD_TO_CLERK_PENDING;
import static org.egov.bpa.utils.BpaConstants.FWD_TO_OVERSEER_AFTER_TS_INSPN;
import static org.egov.bpa.utils.BpaConstants.NATURE_OF_WORK_OC;

/**
 * The Class ApplicationCommonWorkflow.
 */
public abstract class OccupancyCertificateWorkflowCustomImpl implements OccupancyCertificateWorkflowCustom {
    private static final Logger LOG = LoggerFactory.getLogger(OccupancyCertificateWorkflowCustomImpl.class);

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
    private OCLetterToPartyService ocLetterToPartyService;
    @Autowired
    private WorkFlowMatrixService workFlowMatrixService;
    @Autowired
    private OccupancyCertificateUtils occupancyCertificateUtils;

    @Autowired
    public OccupancyCertificateWorkflowCustomImpl() {

    }

    @Override
    @Transactional
    public void createCommonWorkflowTransition(final OccupancyCertificate oc, final WorkflowBean wfBean) {

        if (LOG.isDebugEnabled())
            LOG.debug(" Create WorkFlow Transition Started  ...");
        final User user = securityUtils.getCurrentUser();
        final DateTime currentDate = new DateTime();
        Position pos = null;
        Assignment wfInitiator = null;
        User ownerUser = null;
        if (oc.getCreatedBy() != null)
            wfInitiator = bpaWorkFlowService.getWorkFlowInitiator(oc.getState(), oc.getCreatedBy());

        if (wfBean.getApproverPositionId() != null && wfBean.getApproverPositionId() > 0) {
            pos = positionMasterService.getPositionById(wfBean.getApproverPositionId());
            ownerUser = bpaWorkFlowService.getAssignmentsByPositionAndDate(pos.getId(), new Date()).get(0).getEmployee();
        }
        WorkFlowMatrix wfMatrix;
        if (oc.getCurrentState() == null) {
            String pendingAction = null;
            if (!occupancyCertificateUtils.isDocScrutinyIntegrationRequiredForOc())
                pendingAction = FWD_TO_CLERK_PENDING;
            if (bpaUtils.applicationInitiatedByNonEmployee(oc.getCreatedBy()))
                wfMatrix = bpaApplicationWorkflowService.getWfMatrix(oc.getStateType(), null,
                        null, wfBean.getAdditionalRule(), BpaConstants.WF_NEW_STATE, pendingAction);
            else
                wfMatrix = bpaApplicationWorkflowService.getWfMatrix(oc.getStateType(), null,
                        null, wfBean.getAdditionalRule(), BpaConstants.WF_CREATED_STATE, pendingAction);

            if (wfMatrix != null) {
                if (pos == null) {
                    SiteDetail siteDetail = oc.getParent().getSiteDetail().get(0);
					pos = bpaUtils.getUserPositionByZone(wfMatrix.getNextDesignation(),
							bpaUtils.getBoundaryForWorkflow(siteDetail).getId());
                    List<Assignment> assignments = bpaWorkFlowService.getAssignmentsByPositionAndDate(pos.getId(), new Date());
                    if (!assignments.isEmpty())
                        ownerUser = assignments.get(0).getEmployee();
                }
                oc.setStatus(getStatusByCurrentMatrixStatus(wfMatrix));
                oc.transition().start()
                        .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                        .withOwner(ownerUser)
                        .withComments(wfBean.getApproverComments())
                        .withInitiator(wfInitiator != null ? wfInitiator.getPosition() : null)
                        .withStateValue(wfMatrix.getNextState()).withDateInfo(new Date()).withOwner(pos)
                        .withNextAction(wfMatrix.getNextAction()).withNatureOfTask(NATURE_OF_WORK_OC);
            }
        } else if (BpaConstants.WF_APPROVE_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())
                || OcConstants.WF_FEE_COLL_PENDING.equals(wfBean.getCurrentState())) {
            if (bpaUtils.checkAnyTaxIsPendingToCollect(oc.getDemand()))
                wfMatrix = bpaApplicationWorkflowService.getWfMatrix(oc.getStateType(), null, wfBean.getAmountRule(),
                        wfBean.getAdditionalRule(), "Final Approval Process initiated", OcConstants.WF_APPROVED_AND_FEE_PENDING);
            else
                wfMatrix = bpaApplicationWorkflowService.getWfMatrix(oc.getStateType(), null, wfBean.getAmountRule(),
                        wfBean.getAdditionalRule(), oc.getCurrentState().getValue(), oc.getState().getNextAction());
            if (wfMatrix != null) {
                oc.setStatus(getStatusByCurrentMatrixStatus(wfMatrix));
                oc.transition().progressWithStateCopy()
                        .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                        .withComments(wfBean.getApproverComments())
                        .withStateValue(wfMatrix.getNextState()).withDateInfo(currentDate.toDate())
                        .withOwner(pos).withOwner(ownerUser)
                        .withNextAction(wfMatrix.getNextAction()).withNatureOfTask(NATURE_OF_WORK_OC);
            }
        } else if (BpaConstants.WF_INITIATE_REJECTION_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            pos = bpaWorkFlowService.getApproverPositionOfElectionWardByCurrentStateForOC(oc,
                    BpaConstants.REJECT_BY_CLERK);
            ownerUser = bpaWorkFlowService.getAssignmentsByPositionAndDate(pos.getId(), new Date()).get(0).getEmployee();
            wfMatrix = bpaApplicationWorkflowService.getWfMatrix(oc.getStateType(), null,
                    null, wfBean.getAdditionalRule(), BpaConstants.REJECT_BY_CLERK, null);
            BpaStateInfo bpaStateInfo = bpaWorkFlowService.getBpaStateInfo(oc.getCurrentState(), oc.getStateHistory(),
                    oc.getTownSurveyorInspectionRequire(), new BpaStateInfo(), wfMatrix, wfBean.getWorkFlowAction());
            oc.setStatus(getStatusByPassingCode(BpaConstants.WF_REJECT_STATE));
            oc.transition().progressWithStateCopy()
                    .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                    .withComments(wfBean.getApproverComments())
                    .withStateValue(BpaConstants.REJECT_BY_CLERK).withDateInfo(currentDate.toDate())
                    .withOwner(pos).withOwner(ownerUser)
                    .withNextAction(wfMatrix.getNextAction())
                    .withNatureOfTask(NATURE_OF_WORK_OC).withExtraInfo(bpaStateInfo);
        } else if (BpaConstants.WF_REJECT_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            wfMatrix = bpaApplicationWorkflowService.getWfMatrix(oc.getStateType(), null,
                    null, wfBean.getAdditionalRule(), BpaConstants.WF_REJECT_STATE, null);
            oc.setStatus(getStatusByPassingCode(BpaConstants.WF_REJECT_STATE));
            oc.transition().progressWithStateCopy()
                    .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                    .withComments(wfBean.getApproverComments())
                    .withStateValue(BpaConstants.WF_REJECT_STATE).withDateInfo(currentDate.toDate())
                    .withOwner(pos).withOwner(ownerUser)
                    .withNextAction(wfMatrix.getNextAction())
                    .withNatureOfTask(NATURE_OF_WORK_OC);

        } else if (BpaConstants.LETTERTOPARTYINITIATE.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            wfMatrix = bpaApplicationWorkflowService.getWfMatrix(oc.getStateType(), null,
                    null, wfBean.getAdditionalRule(), wfBean.getWorkFlowAction(), null);
            if (wfMatrix != null) {
                oc.setStatus(getStatusByCurrentMatrixStatus(wfMatrix));
                oc.transition().progressWithStateCopy()
                        .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                        .withComments(wfBean.getApproverComments())
                        .withStateValue(wfMatrix.getNextState()).withDateInfo(currentDate.toDate())
                        .withOwner(pos).withOwner(ownerUser)
                        .withNextAction(wfMatrix.getNextAction()).withNatureOfTask(NATURE_OF_WORK_OC);
            }
        } else if (BpaConstants.LPCREATED.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            wfMatrix = bpaApplicationWorkflowService.getWfMatrix(oc.getStateType(), null, null, wfBean.getAdditionalRule(),
                    BpaConstants.LPCREATED, null);
            ownerUser = bpaWorkFlowService.getAssignmentsByPositionAndDate(oc.getState().getOwnerPosition().getId(), new Date()).get(0).getEmployee();
            oc.setStatus(getStatusByCurrentMatrixStatus(wfMatrix));
            oc.transition().progressWithStateCopy()
                    .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                    .withComments(wfBean.getApproverComments()).withStateValue(wfMatrix.getNextState())
                    .withDateInfo(currentDate.toDate()).withOwner(oc.getState().getOwnerPosition()).withOwner(ownerUser)
                    .withNextAction(wfMatrix.getNextAction()).withNatureOfTask(NATURE_OF_WORK_OC);
        } else if (BpaConstants.GENERATEREJECTNOTICE.equalsIgnoreCase(wfBean.getWorkFlowAction())
                || BpaConstants.WF_CANCELAPPLICATION_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            oc.setStatus(getStatusByPassingCode(BpaConstants.APPLICATION_STATUS_CANCELLED));
            oc.transition().end()
                    .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                    .withComments(wfBean.getApproverComments()).withDateInfo(currentDate.toDate())
                    .withNextAction(BpaConstants.WF_END_STATE).withNatureOfTask(BpaConstants.NATURE_OF_WORK_OC);
        } else if (BpaConstants.LPREPLYRECEIVED.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            List<OCLetterToParty> letterToParties = ocLetterToPartyService.findAllByOC(oc);
            StateHistory<Position> stateHistory = bpaWorkFlowService.getStateHistoryToGetLPInitiator(oc.getStateHistory(),
                    letterToParties.get(0).getLetterToParty().getStateForOwnerPosition());
            wfMatrix = workFlowMatrixService
                    .getWorkFlowObjectbyId(bpaWorkFlowService.getPreviousWfMatrixId(oc.getStateHistory()));
            if (null == wfMatrix)
                wfMatrix = bpaApplicationWorkflowService.getWfMatrix(oc.getStateType(), null, null, wfBean.getAdditionalRule(),
                        letterToParties.get(0).getLetterToParty().getCurrentStateValueOfLP(), null);
            oc.setStatus(letterToParties.get(0).getLetterToParty().getCurrentApplnStatus());
            oc.transition().progressWithStateCopy()
                    .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                    .withComments(wfBean.getApproverComments()).withStateValue(wfMatrix.getNextState())
                    .withDateInfo(currentDate.toDate()).withOwner(stateHistory.getOwnerPosition()).withOwner(stateHistory.getOwnerUser())
                    .withNextAction(wfMatrix.getNextAction()).withNatureOfTask(NATURE_OF_WORK_OC);
        } else {
            Assignment approverAssignment = bpaWorkFlowService.getApproverAssignment(pos);
            if (approverAssignment == null)
                approverAssignment = bpaWorkFlowService.getAssignmentsByPositionAndDate(pos.getId(), new Date()).get(0);
            if (BpaConstants.WF_REVERT_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
                oc.setSentToPreviousOwner(true);
                pos = oc.getCurrentState().getPreviousOwner();
                wfMatrix = workFlowMatrixService
                        .getWorkFlowObjectbyId(bpaWorkFlowService.getPreviousWfMatrixId(oc.getStateHistory()));
            } else if (BpaConstants.WF_AUTO_RESCHDLE_APPMNT_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
                wfMatrix = bpaApplicationWorkflowService.getWfMatrix(oc.getStateType(), null,
                        wfBean.getAmountRule(), wfBean.getAdditionalRule(),
                        oc.getCurrentState().getValue(), BpaConstants.WF_AUTO_RESCHEDULE_PENDING);
            } else if (BpaConstants.APPLICATION_STATUS_NOCUPDATED.equalsIgnoreCase(oc.getStatus().getCode())) {
                wfMatrix = bpaApplicationWorkflowService.getWfMatrix(oc.getStateType(), null,
                        wfBean.getAmountRule(), wfBean.getAdditionalRule(),
                        oc.getCurrentState().getValue(), oc.getState().getNextAction());
            } else if (BpaConstants.WF_TS_INSPECTION_INITIATED.equalsIgnoreCase(oc.getStatus().getCode())) {
                pos = positionMasterService.getPositionById(
                        bpaWorkFlowService.getTownSurveyorInspnInitiator(oc.getStateHistory(), oc.getCurrentState()));
                wfMatrix = bpaApplicationWorkflowService.getWfMatrix(oc.getStateType(), null,
                        wfBean.getAmountRule(), wfBean.getAdditionalRule(),
                        oc.getCurrentState().getValue(), OcConstants.WF_TS_ADDNL_INSPN);
            } else if (BpaConstants.APPLICATION_STATUS_TS_INS.equalsIgnoreCase(oc.getStatus().getCode())) {
                Assignment currentUserAssgmnt = bpaWorkFlowService.getApproverAssignment(oc.getCurrentState().getOwnerPosition());
                if (currentUserAssgmnt == null)
                    currentUserAssgmnt = bpaWorkFlowService
                            .getAssignmentsByPositionAndDate(oc.getCurrentState().getOwnerPosition().getId(), new Date()).get(0);
                String pendingAction = StringUtils.EMPTY;
                if (DESIGNATION_AE.equals(currentUserAssgmnt.getDesignation().getName()))
                    pendingAction = FWD_TO_AE_AFTER_TS_INSP;
                else if (DESIGNATION_OVERSEER.equals(currentUserAssgmnt.getDesignation().getName()))
                    pendingAction = FWD_TO_OVERSEER_AFTER_TS_INSPN;
                wfMatrix = bpaApplicationWorkflowService.getWfMatrix(oc.getStateType(), null,
                        wfBean.getAmountRule(), wfBean.getAdditionalRule(),
                        oc.getCurrentState().getValue(), pendingAction);
            } else if (BpaConstants.APPLICATION_STATUS_APPROVED.equalsIgnoreCase(oc.getStatus().getCode())
                    && !BpaConstants.APPLICATION_STATUS_RECORD_APPROVED.equalsIgnoreCase(oc.getState().getValue())) {
                wfMatrix = bpaApplicationWorkflowService.getWfMatrix(oc.getStateType(), null,
                        wfBean.getAmountRule(), wfBean.getAdditionalRule(),
                        oc.getCurrentState().getValue(), oc.getCurrentState().getNextAction());
            } else if (wfBean.getApproverComments() != null && wfBean.getApproverComments().equals(BpaConstants.BPAFEECOLLECT)
                    && bpaUtils.applicationInitiatedByNonEmployee(oc.getCreatedBy())
                    && oc.getStatus().getCode().equals(BpaConstants.APPLICATION_STATUS_REGISTERED)) {
                wfMatrix = bpaApplicationWorkflowService.getWfMatrix(oc.getStateType(), null,
                        null, wfBean.getAdditionalRule(), BpaConstants.WF_NEW_STATE, null);
            } else if (approverAssignment != null
                    && BpaConstants.DESIGNATION_TOWN_SURVEYOR.equalsIgnoreCase(approverAssignment.getDesignation().getName())) {
                wfMatrix = bpaApplicationWorkflowService.getWfMatrix(oc.getStateType(), null,
                        null, wfBean.getAdditionalRule(), BpaConstants.WF_TS_INSPECTION_INITIATED,
                        OcConstants.WF_FWD_AE_APPROVAL_PENDING);
            } else if (BpaConstants.WF_RESCHDLE_APPMNT_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
                wfMatrix = bpaApplicationWorkflowService.getWfMatrix(oc.getStateType(), null,
                        null, wfBean.getAdditionalRule(), oc.getCurrentState().getValue(),
                        BpaConstants.WF_DOC_SCRTNY_RE_SCHDLE_PENDING);
            } else {
                wfMatrix = bpaApplicationWorkflowService.getWfMatrix(oc.getStateType(), null,
                        null, wfBean.getAdditionalRule(), oc.getCurrentState().getValue(), oc.getState().getNextAction());
                if (wfMatrix == null)
                    wfMatrix = bpaApplicationWorkflowService.getWfMatrix(oc.getStateType(), null,
                            null, wfBean.getAdditionalRule(), oc.getCurrentState().getValue(), null);
            }
            if (wfMatrix != null) {
                BpaStateInfo bpaStateInfo = bpaWorkFlowService.getBpaStateInfo(oc.getCurrentState(), oc.getStateHistory(),
                        oc.getTownSurveyorInspectionRequire(), new BpaStateInfo(), wfMatrix, wfBean.getWorkFlowAction());
                BpaStatus status = getStatusByCurrentMatrixStatus(wfMatrix);
                ownerUser = bpaWorkFlowService.getAssignmentsByPositionAndDate(pos.getId(), new Date()).get(0).getEmployee();
                if (status != null)
                    oc.setStatus(getStatusByCurrentMatrixStatus(wfMatrix));

                if (BpaConstants.GENERATE_OCCUPANCY_CERTIFICATE.equalsIgnoreCase(wfBean.getWorkFlowAction()))
                    oc.transition().end()
                            .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                            .withComments(wfBean.getApproverComments()).withDateInfo(currentDate.toDate())
                            .withNextAction(wfMatrix.getNextAction()).withNatureOfTask(NATURE_OF_WORK_OC);
                else
                    oc.transition().progressWithStateCopy()
                            .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                            .withComments(wfBean.getApproverComments()).withOwner(ownerUser)
                            .withStateValue(wfMatrix.getNextState()).withDateInfo(currentDate.toDate()).withOwner(pos)
                            .withNextAction(wfMatrix.getNextAction()).withNatureOfTask(NATURE_OF_WORK_OC)
                            .withExtraInfo(bpaStateInfo);
            }
        }
        if (LOG.isDebugEnabled())
            LOG.debug(" WorkFlow Transition Completed ");
        bpaUtils.updatePortalUserinbox(oc, null);
    }

    private BpaStatus getStatusByCurrentMatrixStatus(final WorkFlowMatrix wfMatrix) {
        if (wfMatrix != null && wfMatrix.getNextStatus() != null && !"".equals(wfMatrix.getNextStatus()))
            return bpaStatusService
                    .findByModuleTypeAndCode(BpaConstants.BPASTATUS_MODULETYPE, wfMatrix.getNextStatus());
        return null;
    }

    private BpaStatus getStatusByPassingCode(final String code) {
        if (code != null && !"".equals(code))
            return bpaStatusService
                    .findByModuleTypeAndCode(BpaConstants.BPASTATUS_MODULETYPE, code);
        return null;
    }

}