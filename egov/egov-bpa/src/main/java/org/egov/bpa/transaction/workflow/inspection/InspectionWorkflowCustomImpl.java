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
package org.egov.bpa.transaction.workflow.inspection;

import static org.egov.bpa.utils.BpaConstants.FWD_TO_CLERK_PENDING;

import java.util.Date;
import java.util.List;

import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.InspectionApplication;
import org.egov.bpa.transaction.entity.PermitInspectionApplication;
import org.egov.bpa.transaction.entity.SiteDetail;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.service.BpaStatusService;
import org.egov.bpa.transaction.workflow.BpaWorkFlowService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.eis.entity.Assignment;
import org.egov.eis.service.PositionMasterService;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.workflow.matrix.entity.WorkFlowMatrix;
import org.egov.infra.workflow.service.SimpleWorkflowService;
import org.egov.pims.commons.Position;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class InspectionWorkflow.
 */
public abstract class InspectionWorkflowCustomImpl implements InspectionWorkflowCustom {
    private static final Logger LOG = LoggerFactory.getLogger(InspectionWorkflowCustomImpl.class);

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private PositionMasterService positionMasterService;

    @Autowired
    @Qualifier("workflowService")
    private SimpleWorkflowService<InspectionApplication> inspectionWorkflowService;

    @Autowired
    private BpaStatusService bpaStatusService;

    @Autowired
    private BpaWorkFlowService bpaWorkFlowService;

    @Autowired
    private BpaUtils bpaUtils;

    @Autowired
    public InspectionWorkflowCustomImpl() {

    }

    @Override
    @Transactional
    public void createCommonWorkflowTransition(final PermitInspectionApplication permitInspection, final WorkflowBean wfBean) {

        if (LOG.isDebugEnabled())
            LOG.debug(" Create WorkFlow Transition Started  ...");
        final User user = securityUtils.getCurrentUser();
        final DateTime currentDate = new DateTime();
        Position pos = null;
        Assignment wfInitiator = null;
        if (permitInspection.getCreatedBy() != null)
            wfInitiator = bpaWorkFlowService.getWorkFlowInitiator(permitInspection.getInspectionApplication().getState(), permitInspection.getInspectionApplication().getCreatedBy());
        User ownerUser = null;
        if (wfBean.getApproverPositionId() != null && wfBean.getApproverPositionId() > 0) 
            pos = positionMasterService.getPositionById(wfBean.getApproverPositionId());
		if(pos != null)
			ownerUser = bpaWorkFlowService.getAssignmentsByPositionAndDate(pos.getId(), new Date()).get(0).getEmployee();
        WorkFlowMatrix wfmatrix;
        if (null == permitInspection.getInspectionApplication().getState()) {
            String pendingAction =  FWD_TO_CLERK_PENDING;
            
                wfmatrix = inspectionWorkflowService.getWfMatrix(permitInspection.getInspectionApplication().getStateType(), null,
                        null, wfBean.getAdditionalRule(), BpaConstants.WF_NEW_STATE, pendingAction);
            

            if (wfmatrix != null) {
              if (pos == null) {
                    SiteDetail siteDetail = permitInspection.getApplication().getSiteDetail().get(0);
					pos = bpaUtils.getUserPositionByZone(wfmatrix.getNextDesignation(),
							bpaUtils.getBoundaryForWorkflow(siteDetail).getId());
                    List<Assignment> assignments = bpaWorkFlowService.getAssignmentsByPositionAndDate(pos.getId(), new Date());
                    if(!assignments.isEmpty())
                        ownerUser = assignments.get(0).getEmployee();
                }
               // BpaStateInfo bpaStateInfo = bpaWorkFlowService.getBpaStateInfo(application.getCurrentState(), application.getStateHistory(), application.getTownSurveyorInspectionRequire(), new BpaStateInfo(), wfmatrix, workFlowAction);

            	permitInspection.getInspectionApplication().setStatus(getStatusByCurrentMatrxiStatus(wfmatrix));
            	permitInspection.getInspectionApplication().transition().start()
                           .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                           .withComments(wfBean.getApproverComments()).withInitiator(wfInitiator != null ? wfInitiator.getPosition() : null)
                           .withStateValue(wfmatrix.getNextState()).withDateInfo(new Date()).withOwner(pos).withOwner(ownerUser)
                           .withNextAction(wfmatrix.getNextAction()).withNatureOfTask(BpaConstants.NATURE_OF_WORK_INSPECTION);
            }

        }  else if (BpaConstants.WF_APPROVE_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())
                   && (BpaConstants.APPLICATION_STATUS_APPROVED.equalsIgnoreCase(permitInspection.getInspectionApplication().getStatus().getCode()))) {
                wfmatrix = inspectionWorkflowService.getWfMatrix(permitInspection.getInspectionApplication().getStateType(), null, null,
                		wfBean.getAdditionalRule(), "Application Approval Pending", "Forwarded to Assistant Engineer For Approval");
            	
            BpaStatus status = bpaStatusService
                    .findByModuleTypeAndCode(BpaConstants.INSPECTION_MODULE_TYPE, BpaConstants.APPLICATION_STATUS_APPROVED);
            if (status != null)
            	permitInspection.getInspectionApplication().setStatus(status);

        	
            permitInspection.getInspectionApplication().transition().end()
                .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                .withComments(wfBean.getApproverComments())
                .withStateValue(wfmatrix.getNextState()).withDateInfo(currentDate.toDate())
                .withOwner(pos).withOwner(ownerUser)
                .withNextAction(wfmatrix.getNextAction()).withNatureOfTask(BpaConstants.NATURE_OF_WORK_INSPECTION);
        	
        }  else {
            Assignment approverAssignment = bpaWorkFlowService.getApproverAssignment(pos);
            if(approverAssignment == null)
                approverAssignment = bpaWorkFlowService.getAssignmentsByPositionAndDate(pos.getId(), new Date()).get(0);
             if (BpaConstants.APPLICATION_STATUS_APPROVED.equalsIgnoreCase(permitInspection.getInspectionApplication().getStatus().getCode())) {
                wfmatrix = inspectionWorkflowService .getWfMatrix(permitInspection.getInspectionApplication().getStateType(), null,
                		wfBean.getAmountRule(), wfBean.getAdditionalRule(),
                        permitInspection.getInspectionApplication().getCurrentState().getValue(), null);
            } else if (permitInspection.getInspectionApplication().getStatus().getCode().equals(BpaConstants.INITIATEINSPECTION)) {
            	
                wfmatrix = inspectionWorkflowService.getWfMatrix(permitInspection.getInspectionApplication().getStateType(), null,
                        null, wfBean.getAdditionalRule(), permitInspection.getInspectionApplication().getCurrentState().getValue(), permitInspection.getInspectionApplication().getState().getNextAction());
            }   else {
                wfmatrix = inspectionWorkflowService.getWfMatrix(permitInspection.getInspectionApplication().getStateType(), null,
                        null, wfBean.getAdditionalRule(), permitInspection.getInspectionApplication().getCurrentState().getValue(), permitInspection.getInspectionApplication().getState().getNextAction());
                if(wfmatrix == null)
                    wfmatrix = inspectionWorkflowService.getWfMatrix(permitInspection.getInspectionApplication().getStateType(), null,
                        null, wfBean.getAdditionalRule(), permitInspection.getInspectionApplication().getCurrentState().getValue(), null);
            }
            if (wfmatrix != null) {
               // BpaStateInfo bpaStateInfo = bpaWorkFlowService.getBpaStateInfo(permitInspection.getInspectionApplication().getCurrentState(), permitInspection.getInspectionApplication().getStateHistory(), application.getTownSurveyorInspectionRequire(), new BpaStateInfo(), wfmatrix, workFlowAction);
                BpaStatus status = getStatusByCurrentMatrxiStatus(wfmatrix);
                ownerUser = bpaWorkFlowService.getAssignmentsByPositionAndDate(pos.getId(), new Date()).get(0).getEmployee();
                if (status != null)
                	permitInspection.getInspectionApplication().setStatus(getStatusByCurrentMatrxiStatus(wfmatrix));
                
                  
				if (wfBean.getWorkFlowAction().equalsIgnoreCase(BpaConstants.WF_REVOCATE_BUTTON)) {
					permitInspection.getInspectionApplication().setStatus(getStatusByPassingCode("Revocated"));
					permitInspection.getInspectionApplication().transition().end()
	                .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
	                .withComments(wfBean.getApproverComments())
	                .withStateValue(wfmatrix.getNextState()).withDateInfo(currentDate.toDate())
	                .withOwner(pos).withOwner(ownerUser)
	                .withNextAction(wfmatrix.getNextAction()).withNatureOfTask(BpaConstants.NATURE_OF_WORK_INSPECTION);
				}
				
				if (wfmatrix.getNextAction().contains("END"))
					permitInspection.getInspectionApplication().transition().end()
                               .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                               .withComments(wfBean.getApproverComments()).withDateInfo(currentDate.toDate())
                               .withNextAction(wfmatrix.getNextAction()).withNatureOfTask(BpaConstants.NATURE_OF_WORK_INSPECTION);
                else
                	permitInspection.getInspectionApplication().transition().progressWithStateCopy()
                               .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                               .withComments(wfBean.getApproverComments())
                               .withStateValue(wfmatrix.getNextState()).withDateInfo(currentDate.toDate()).withOwner(pos).withOwner(ownerUser)
                               .withNextAction(wfmatrix.getNextAction()).withNatureOfTask(BpaConstants.NATURE_OF_WORK_INSPECTION);             
                
                
            }
        }
        if (LOG.isDebugEnabled())
            LOG.debug(" WorkFlow Transition Completed ");
        bpaUtils.updatePortalUserinbox(permitInspection, null);  
    }

    private BpaStatus getStatusByCurrentMatrxiStatus(final WorkFlowMatrix wfmatrix) {
        if (wfmatrix != null && wfmatrix.getNextStatus() != null && !"".equals(wfmatrix.getNextStatus()))
            return bpaStatusService
                    .findByModuleTypeAndCode(BpaConstants.INSPECTION_MODULE_TYPE, wfmatrix.getNextStatus());
        return null;
    }

    private BpaStatus getStatusByPassingCode(final String code) {
        if (code != null && !"".equals(code))
            return bpaStatusService
                    .findByModuleTypeAndCode(BpaConstants.INSPECTION_MODULE_TYPE, code);
        return null;
    }

}