package org.egov.bpa.utils;

import static org.egov.bpa.utils.BpaConstants.BOUNDARY_TYPE_CITY;
import static org.egov.bpa.utils.BpaConstants.BOUNDARY_TYPE_ZONE;
import static org.egov.bpa.utils.BpaConstants.LETTERTOPARTYINITIATE;
import static org.egov.bpa.utils.BpaConstants.LPCREATED;
import static org.egov.bpa.utils.BpaConstants.LPREPLIED;
import static org.egov.bpa.utils.BpaConstants.LPREPLYRECEIVED;

import java.util.ArrayList;
import java.util.List;

import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.OwnershipTransfer;
import org.egov.bpa.transaction.entity.PermitRenewal;
import org.egov.bpa.transaction.entity.SiteDetail;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.workflow.ownershiptransfer.OwnershipTransferWorkflowCustomDefaultImpl;
import org.egov.bpa.transaction.workflow.permitrenewal.PermitRenewalWorkflowCustomDefaultImpl;
import org.egov.eis.entity.Assignment;
import org.egov.eis.service.AssignmentService;
import org.egov.eis.service.DesignationService;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.admin.master.service.BoundaryService;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.workflow.matrix.entity.WorkFlowMatrix;
import org.egov.infra.workflow.service.SimpleWorkflowService;
import org.egov.pims.commons.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author vinoth
 *
 */
@Service
@Transactional(readOnly = true)
public class BpaWorkflowRedirectUtility {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private BoundaryService boundaryService;

    @Autowired
    @Qualifier("workflowService")
    private SimpleWorkflowService<BpaApplication> bpaApplicationWorkflowService;

    @Autowired
    private DesignationService designationService;

    @Autowired
    private AppConfigValueService appConfigValuesService;

    public User getCurrentUser() {
        return securityUtils.getCurrentUser();
    }

    @Transactional(readOnly = true)
    public Long getUserPositionIdByZone(final String designation, final Long boundary) {
        List<Assignment> assignment = getAssignmentsByDesigAndBndryId(designation, boundary);
        return assignment.isEmpty() ? 0 : assignment.get(0).getPosition().getId();
    }

    @Transactional(readOnly = true)
    public Position getUserPositionByZone(final String designation, final Long boundary) {
        List<Assignment> assignment = getAssignmentsByDesigAndBndryId(designation, boundary);
        return assignment.isEmpty() ? null : assignment.get(0).getPosition();
    }

    public List<Assignment> getAssignmentsByDesigAndBndryId(String designation, Long boundary) {
        final Boundary boundaryObj = getBoundaryById(boundary);
        final String[] designationarr = designation.split(",");
        List<Assignment> assignment = new ArrayList<>();
        for (final String desg : designationarr) {
            assignment = assignmentService.findAssignmentByDepartmentDesignationAndBoundary(null,
                    designationService.getDesignationByName(desg).getId(), boundaryObj.getId());
            if (assignment.isEmpty()) {
                // Ward->Zone
                if (boundaryObj.getParent() != null && boundaryObj.getParent().getBoundaryType() != null && boundaryObj
                        .getParent().getBoundaryType().getName().equals(BOUNDARY_TYPE_ZONE)) {
                    assignment = assignmentService.findByDeptDesgnAndParentAndActiveChildBoundaries(null,
                            designationService.getDesignationByName(desg).getId(), boundaryObj.getParent().getId());
                    if (assignment.isEmpty() && boundaryObj.getParent() != null
                            && boundaryObj.getParent().getParent() != null && boundaryObj.getParent().getParent()
                                    .getBoundaryType().getName().equals(BOUNDARY_TYPE_CITY))
                        assignment = assignmentService.findByDeptDesgnAndParentAndActiveChildBoundaries(null,
                                designationService.getDesignationByName(desg).getId(),
                                boundaryObj.getParent().getParent().getId());
                }
                // ward->City mapp
                if (assignment.isEmpty() && boundaryObj.getParent() != null
                        && boundaryObj.getParent().getBoundaryType().getName().equals(BOUNDARY_TYPE_CITY))
                    assignment = assignmentService.findByDeptDesgnAndParentAndActiveChildBoundaries(null,
                            designationService.getDesignationByName(desg).getId(), boundaryObj.getParent().getId());
            }
            if (!assignment.isEmpty())
                break;
        }
        return assignment;
    }

    public Boundary getBoundaryById(final Long boundary) {
        return boundaryService.getBoundaryById(boundary);
    }

    private PermitRenewalWorkflowCustomDefaultImpl getInitialisedWorkFlowBeanOfPermitRenewal() {
        PermitRenewalWorkflowCustomDefaultImpl applicationWorkflowCustomDefaultImpl = null;
        if (null != context)
            applicationWorkflowCustomDefaultImpl = (PermitRenewalWorkflowCustomDefaultImpl) context
                    .getBean("permitRenewalWorkflowCustomDefaultImpl");
        return applicationWorkflowCustomDefaultImpl;
    }
    
    private OwnershipTransferWorkflowCustomDefaultImpl getInitialisedWorkFlowBeanOfOwnershipTransfer() {
        OwnershipTransferWorkflowCustomDefaultImpl applicationWorkflowCustomDefaultImpl = null;
        if (null != context)
            applicationWorkflowCustomDefaultImpl = (OwnershipTransferWorkflowCustomDefaultImpl) context
                    .getBean("ownershipTransferWorkflowCustomDefaultImpl");
        return applicationWorkflowCustomDefaultImpl;
    }

    public WorkFlowMatrix getWfMatrixByCurrentState(final Boolean isOneDayPermit, final String stateType,
            final String currentState, String applicationType) {
        if (isOneDayPermit) {
            return bpaApplicationWorkflowService.getWfMatrix(stateType, null, null,
                    BpaConstants.APPLICATION_TYPE_ONEDAYPERMIT, currentState, null);
        } else
            return bpaApplicationWorkflowService.getWfMatrix(stateType, null, null,
                    applicationType, currentState, null);
    }

    public WorkFlowMatrix getWfMatrixByCurrentState(final String stateType, final String currentState,
            final String additionalRule) {
        return bpaApplicationWorkflowService.getWfMatrix(stateType, null, null,
                additionalRule, currentState, null);
    }

    @Transactional
    public void redirectToBpaWorkFlow(final PermitRenewal permitRenewal, final WorkflowBean wfBean) {
        final WorkFlowMatrix wfMatrix = getWfMatrixByCurrentState(permitRenewal.getStateType(), wfBean.getCurrentState(),
                permitRenewal.getParent().getApplicationType().getName());
        final PermitRenewalWorkflowCustomDefaultImpl permitRenewalWorkflowImpl = getInitialisedWorkFlowBeanOfPermitRenewal();
        Long approvalPositionId = wfBean.getApproverPositionId();
        if (wfBean.getApproverPositionId() == null)
            approvalPositionId = getUserPositionIdByZone(wfMatrix.getNextDesignation(),
                    getBoundaryForWorkflow(permitRenewal.getParent().getSiteDetail().get(0)).getId());
        wfBean.setAdditionalRule(permitRenewal.getParent().getApplicationType().getName());
        wfBean.setApproverPositionId(approvalPositionId);
        if (permitRenewalWorkflowImpl != null)
            if (LETTERTOPARTYINITIATE.equals(wfBean.getCurrentState())) {
                wfBean.setWorkFlowAction(LETTERTOPARTYINITIATE);
                permitRenewalWorkflowImpl.createCommonWorkflowTransition(permitRenewal, wfBean);
            } else if (LPCREATED.equals(wfBean.getCurrentState())) {
                wfBean.setWorkFlowAction(LPCREATED);
                permitRenewalWorkflowImpl.createCommonWorkflowTransition(permitRenewal, wfBean);
            } else if (LPREPLIED.equals(wfBean.getCurrentState())) {
                wfBean.setWorkFlowAction(LPREPLYRECEIVED);
                permitRenewalWorkflowImpl.createCommonWorkflowTransition(permitRenewal, wfBean);
            } else {
                permitRenewalWorkflowImpl.createCommonWorkflowTransition(permitRenewal, wfBean);
            }
    }

    public Boundary getBoundaryForWorkflow(SiteDetail siteDetail) {
        Boundary workFlowBoundary = null;
        String workflowBoundary = appConfigValuesService.getConfigValuesByModuleAndKey(BpaConstants.BPA_MODULE_NAME,
                BpaConstants.WORKFLOW_EMPLOYEE_BOUNDARY_HIERARCHY).get(0).getValue();
        if (workflowBoundary != null && !workflowBoundary.isEmpty()) {
            if ("ADMINISTRATION".equals(workflowBoundary)) {
                workFlowBoundary = siteDetail.getElectionBoundary();
            } else if ("REVENUE".equals(workflowBoundary)) {
                workFlowBoundary = siteDetail.getAdminBoundary();
            } else {
                workFlowBoundary = siteDetail.getLocationBoundary();
            }
        }
        return workFlowBoundary;
    }
    
    @Transactional
    public void redirectToBpaWorkFlow(final OwnershipTransfer ownershipTransfer, final WorkflowBean wfBean) {
        final WorkFlowMatrix wfMatrix = getWfMatrixByCurrentState(ownershipTransfer.getStateType(), wfBean.getCurrentState(),
        		ownershipTransfer.getApplication().getApplicationType().getName());
        final OwnershipTransferWorkflowCustomDefaultImpl ownershipTransferWorkflowImpl = getInitialisedWorkFlowBeanOfOwnershipTransfer();
        Long approvalPositionId = wfBean.getApproverPositionId();
        if (wfBean.getApproverPositionId() == null)
            approvalPositionId = getUserPositionIdByZone(wfMatrix.getNextDesignation(),
                    getBoundaryForWorkflow(ownershipTransfer.getApplication().getSiteDetail().get(0)).getId());
        wfBean.setAdditionalRule(ownershipTransfer.getApplication().getApplicationType().getName());
        wfBean.setApproverPositionId(approvalPositionId);
        if (ownershipTransferWorkflowImpl != null)
            if (BpaConstants.WF_OWNERSHIP_FEE_PENDING.equals(wfBean.getCurrentState())) {
            	ownershipTransferWorkflowImpl.createCommonWorkflowTransition(ownershipTransfer, wfBean);
            } else {
            	ownershipTransferWorkflowImpl.createCommonWorkflowTransition(ownershipTransfer, wfBean);
            }
    }

}