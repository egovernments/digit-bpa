package org.egov.bpa.utils;

import static java.io.File.separator;
import static org.apache.commons.io.FileUtils.getUserDirectoryPath;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_MODULE_TYPE;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_CANCELLED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REJECTED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REJECT_CLERK;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_SUBMITTED;
import static org.egov.bpa.utils.BpaConstants.BOUNDARY_TYPE_CITY;
import static org.egov.bpa.utils.BpaConstants.BOUNDARY_TYPE_ZONE;
import static org.egov.bpa.utils.BpaConstants.BPA_CITIZENACCEPTANCE_CHECK;
import static org.egov.bpa.utils.BpaConstants.CREATE_ADDITIONAL_RULE_CREATE_OC;
import static org.egov.bpa.utils.BpaConstants.DOC_SCRUTINY_INTEGRATION_REQUIRED;
import static org.egov.bpa.utils.BpaConstants.EGMODULE_NAME;
import static org.egov.bpa.utils.BpaConstants.LETTERTOPARTYINITIATE;
import static org.egov.bpa.utils.BpaConstants.LPCREATED;
import static org.egov.bpa.utils.BpaConstants.LPREPLIED;
import static org.egov.bpa.utils.BpaConstants.LPREPLYRECEIVED;
import static org.egov.bpa.utils.BpaConstants.ONE_DAY_PERMIT_APPLN_INTEGRATION_REQUIRED;
import static org.egov.bpa.utils.BpaConstants.ONE_DAY_PERMIT_INSPECTION_SCHEDULE_INTEGRATION_REQUIRED;
import static org.egov.bpa.utils.BpaConstants.PERMIT_APPLN_FEE_COLLECTION_REQUIRED;
import static org.egov.bpa.utils.BpaConstants.REGULAR_PERMIT_INSPECTION_SCHEDULE_INTEGRATION_REQUIRED;
import static org.egov.bpa.utils.BpaConstants.WF_LBE_SUBMIT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_PERMIT_FEE_COLL_PENDING;
import static org.egov.bpa.utils.BpaConstants.YES;
import static org.egov.infra.config.core.ApplicationThreadLocals.getCityCode;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.egov.bpa.master.entity.ApplicationSubType;
import org.egov.bpa.master.service.ApplicationSubTypeService;
import org.egov.bpa.transaction.entity.ApplicationFloorDetail;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BuildingDetail;
import org.egov.bpa.transaction.entity.ExistingBuildingDetail;
import org.egov.bpa.transaction.entity.ExistingBuildingFloorDetail;
import org.egov.bpa.transaction.entity.PermitNocApplication;
import org.egov.bpa.transaction.entity.SiteDetail;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.entity.oc.OCBuilding;
import org.egov.bpa.transaction.entity.oc.OCExistingBuilding;
import org.egov.bpa.transaction.entity.oc.OCExistingBuildingFloor;
import org.egov.bpa.transaction.entity.oc.OCFloor;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.entity.oc.OccupancyNocApplication;
import org.egov.bpa.transaction.service.PdfQrCodeAppendService;
import org.egov.bpa.transaction.service.messaging.BPASmsAndEmailService;
import org.egov.bpa.transaction.workflow.BpaApplicationWorkflowCustomDefaultImpl;
import org.egov.bpa.transaction.workflow.oc.OccupancyCertificateWorkflowCustomDefaultImpl;
import org.egov.collection.integration.models.BillReceiptInfo;
import org.egov.collection.integration.services.CollectionIntegrationService;
import org.egov.common.entity.bpa.SubOccupancy;
import org.egov.demand.model.EgDemand;
import org.egov.demand.model.EgDemandDetails;
import org.egov.eis.entity.Assignment;
import org.egov.eis.service.AssignmentService;
import org.egov.eis.service.DesignationService;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.admin.master.entity.Module;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.admin.master.service.BoundaryService;
import org.egov.infra.admin.master.service.ModuleService;
import org.egov.infra.admin.master.service.UserService;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.persistence.entity.enums.UserType;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.workflow.entity.StateHistory;
import org.egov.infra.workflow.matrix.entity.WorkFlowMatrix;
import org.egov.infra.workflow.service.SimpleWorkflowService;
import org.egov.pims.commons.Position;
import org.egov.portal.entity.PortalInbox;
import org.egov.portal.entity.PortalInboxBuilder;
import org.egov.portal.service.PortalInboxService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BpaUtils {
    private static final String APPLICATION_FEE_PAYMENT_PENDING = "Application fee payment pending";

    private static final String SUCCESS = "Success";

    private static final Logger LOG = getLogger(BpaUtils.class);

    private static final String CLOSED = "Closed";
    private static final String WF_END_ACTION = "END";
    @Autowired
    private ApplicationContext context;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private PortalInboxService portalInboxService;
    @Autowired
    private BPASmsAndEmailService bpaSmsAndEmailService;

    @Autowired
    private BoundaryService boundaryService;

    @Autowired
    @Qualifier("workflowService")
    private SimpleWorkflowService<BpaApplication> bpaApplicationWorkflowService;

    @Autowired
    private DesignationService designationService;

    @Autowired
    private AppConfigValueService appConfigValueService;

    @Autowired
    private UserService userService;

    @Autowired
    private PdfQrCodeAppendService pdfQrCodeAppend;

    @Autowired
    private CollectionIntegrationService collectionIntegrationService;

    private String fileStoreDir;
    @Autowired
    private AppConfigValueService appConfigValuesService;
    @Autowired
    private ApplicationSubTypeService applicationTypeService;

    @Autowired
    public BpaUtils(@Value("${filestore.base.dir}") String fileStoreDir) {
        if (fileStoreDir.isEmpty())
            this.fileStoreDir = getUserDirectoryPath() + separator + "egovfilestore";
        else
            this.fileStoreDir = fileStoreDir;
    }

    public String getAppconfigValueByKeyName(String code) {
        List<AppConfigValues> appConfigValueList = appConfigValueService
                .getConfigValuesByModuleAndKey(APPLICATION_MODULE_TYPE, code);
        return appConfigValueList.isEmpty() ? "" : appConfigValueList.get(0).getValue();
    }

    public Boolean checkAnyTaxIsPendingToCollect(final EgDemand demand) {
        Boolean pendingTaxCollection = false;

        if (demand != null)
            for (final EgDemandDetails demandDtl : demand.getEgDemandDetails())
                if (demandDtl.getAmount().subtract(demandDtl.getAmtCollected()).compareTo(BigDecimal.ZERO) > 0) {
                    pendingTaxCollection = true;
                    break;
                }
        return pendingTaxCollection;
    }

    public Boolean checkIsReconciliationInProgress(final String applicationNumber) {
        List<BillReceiptInfo> receipts = collectionIntegrationService.getOnlinePendingReceipts(APPLICATION_MODULE_TYPE,
                applicationNumber);
        if (!receipts.isEmpty())
            return true;
        return false;
    }

    public Boolean applicationInitiatedByNonEmployee(User createdBy) {
        Boolean initiatedByNonEmployee = false;
        User applicationInitiator;
        if (createdBy != null)
            applicationInitiator = userService.getUserById(createdBy.getId());
        else
            applicationInitiator = getCurrentUser();
        if (applicationInitiator != null && !applicationInitiator.getType().equals(UserType.EMPLOYEE)) {
            initiatedByNonEmployee = Boolean.TRUE;
        }

        return initiatedByNonEmployee;
    }

    public User getCurrentUser() {
        return securityUtils.getCurrentUser();
    }

    private BpaApplicationWorkflowCustomDefaultImpl getInitialisedWorkFlowBean() {
        BpaApplicationWorkflowCustomDefaultImpl applicationWorkflowCustomDefaultImpl = null;
        if (null != context)
            applicationWorkflowCustomDefaultImpl = (BpaApplicationWorkflowCustomDefaultImpl) context
                    .getBean("bpaApplicationWorkflowCustomDefaultImpl");
        return applicationWorkflowCustomDefaultImpl;
    }

    private OccupancyCertificateWorkflowCustomDefaultImpl getInitialisedWorkFlowBeanForOC() {
        OccupancyCertificateWorkflowCustomDefaultImpl applicationWorkflowCustomDefaultImpl = null;
        if (null != context)
            applicationWorkflowCustomDefaultImpl = (OccupancyCertificateWorkflowCustomDefaultImpl) context
                    .getBean("occupancyCertificateWorkflowCustomDefaultImpl");
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

    public WorkFlowMatrix getWfMatrixByCurrentState(final String stateType, final String currentState) {
        return bpaApplicationWorkflowService.getWfMatrix(stateType, null, null,
                CREATE_ADDITIONAL_RULE_CREATE_OC, currentState, null);
    }

    @Transactional
    public void updatePortalUserinbox(final BpaApplication application, final User additionalPortalInboxUser) {
        Module module = moduleService.getModuleByName(EGMODULE_NAME);
        boolean isResolved = false;
        String status;
        if (APPLICATION_STATUS_SUBMITTED.equals(application.getStatus().getCode())
                && checkAnyTaxIsPendingToCollect(application.getDemand()))
            status = APPLICATION_FEE_PAYMENT_PENDING;
        else
            status = application.getStatus().getDescription();
        if ((application.getState() != null && (CLOSED.equals(application.getState().getValue())
                || WF_END_ACTION.equals(application.getState().getValue())))
                || (application.getStatus() != null
                        && application.getStatus().getCode().equals(APPLICATION_STATUS_CANCELLED)))
            isResolved = true;
        String url = "/bpa/application/citizen/update/" + application.getApplicationNumber();
        if (application.getStatus() != null)
            portalInboxService.updateInboxMessage(application.getApplicationNumber(), module.getId(),
                    status, isResolved, new Date(), application.getState(),
                    additionalPortalInboxUser, application.getPlanPermissionNumber(), url);
    }

    @Transactional
    public void createPortalUserinbox(final BpaApplication application, final List<User> portalInboxUser,
            final String workFlowAction) {
        String status = StringUtils.EMPTY;
        if ("Save".equalsIgnoreCase(workFlowAction) || "Send".equalsIgnoreCase(workFlowAction)) {
            status = "To be submitted";
        } else if (null != application.getStatus().getDescription()
                && WF_LBE_SUBMIT_BUTTON.equalsIgnoreCase(workFlowAction)) {
            if (checkAnyTaxIsPendingToCollect(application.getDemand()))
                status = APPLICATION_FEE_PAYMENT_PENDING;
            else
                status = application.getStatus().getDescription();
        }
        Module module = moduleService.getModuleByName(EGMODULE_NAME);
        boolean isResolved = false;
        String url = "/bpa/application/citizen/update/" + application.getApplicationNumber();
        final PortalInboxBuilder portalInboxBuilder = new PortalInboxBuilder(module, application.getOwner().getName(),
                application.getServiceType().getDescription(), application.getApplicationNumber(),
                application.getPlanPermissionNumber(), application.getId(), SUCCESS, SUCCESS, url, isResolved,
                status, new Date(), application.getState(), portalInboxUser);

        final PortalInbox portalInbox = portalInboxBuilder.build();
        portalInboxService.pushInboxMessage(portalInbox);
    }

    @Transactional
    public void createPortalUserinbox(final OccupancyCertificate oc, final List<User> portalInboxUser,
            final String workFlowAction) {
        String status = StringUtils.EMPTY;
        if ("Save".equalsIgnoreCase(workFlowAction)) {
            status = "To be submitted";
        } else if (null != oc.getStatus().getDescription()
                && WF_LBE_SUBMIT_BUTTON.equalsIgnoreCase(workFlowAction)) {
            if (checkAnyTaxIsPendingToCollect(oc.getDemand()))
                status = APPLICATION_FEE_PAYMENT_PENDING;
            else
                status = oc.getStatus().getDescription();
        }
        Module module = moduleService.getModuleByName(EGMODULE_NAME);
        boolean isResolved = false;
        String url = "/bpa/application/citizen/occupancy-certificate/update/" + oc.getApplicationNumber();
        final PortalInboxBuilder portalInboxBuilder = new PortalInboxBuilder(module, oc.getParent().getOwner().getName(),
                oc.getApplicationType(), oc.getApplicationNumber(),
                oc.getOccupancyCertificateNumber(), oc.getId(), SUCCESS, SUCCESS, url, isResolved,
                status, new Date(), oc.getState(), portalInboxUser);

        final PortalInbox portalInbox = portalInboxBuilder.build();
        portalInboxService.pushInboxMessage(portalInbox);
    }

    @Transactional
    public void updatePortalUserinbox(final OccupancyCertificate oc, final User additionalPortalInboxUser) {
        Module module = moduleService.getModuleByName(EGMODULE_NAME);
        boolean isResolved = false;
        String status;
        if (APPLICATION_STATUS_SUBMITTED.equals(oc.getStatus().getCode()) && checkAnyTaxIsPendingToCollect(oc.getDemand()))
            status = APPLICATION_FEE_PAYMENT_PENDING;
        else
            status = oc.getStatus().getDescription();
        if ((oc.getState() != null && (CLOSED.equals(oc.getState().getValue())
                || WF_END_ACTION.equals(oc.getState().getValue())))
                || (oc.getStatus() != null
                        && oc.getStatus().getCode().equals(APPLICATION_STATUS_CANCELLED)))
            isResolved = true;
        String url = "/bpa/application/citizen/occupancy-certificate/update/" + oc.getApplicationNumber();
        if (oc.getStatus() != null)
            portalInboxService.updateInboxMessage(oc.getApplicationNumber(), module.getId(),
                    status, isResolved, new Date(), oc.getState(),
                    additionalPortalInboxUser, oc.getOccupancyCertificateNumber(), url);
    }
    
    @Transactional
    public void createNocPortalUserinbox(final PermitNocApplication permitNoc, final List<User> portalInboxUser,
            final String workFlowAction) {
        String status = BpaConstants.NOC_INITIATED;

        Module module = moduleService.getModuleByName(BpaConstants.NOCMODULE);
        
        boolean isResolved = false;
        String url = "/bpa/nocapplication/update/" + permitNoc.getBpaNocApplication().getNocApplicationNumber();
        final PortalInboxBuilder portalInboxBuilder = new PortalInboxBuilder(module, permitNoc.getBpaApplication().getOwner().getName(),
        		permitNoc.getBpaApplication().getServiceType().getDescription(), permitNoc.getBpaNocApplication().getNocApplicationNumber(),
        		permitNoc.getBpaApplication().getPlanPermissionNumber(), permitNoc.getId(), SUCCESS, SUCCESS, url, isResolved,
                status, new Date(), null, portalInboxUser);

        final PortalInbox portalInbox = portalInboxBuilder.build();
        portalInboxService.pushInboxMessage(portalInbox);
    }
    
    @Transactional
    public void updateNocPortalUserinbox(final PermitNocApplication permitNoc, final User additionalPortalInboxUser) {
        Module module = moduleService.getModuleByName(BpaConstants.NOCMODULE);
        String status = permitNoc.getBpaNocApplication().getStatus().getCode();        
        String url = "/bpa/nocapplication/update/" +permitNoc.getBpaNocApplication().getNocApplicationNumber();
        portalInboxService.updateInboxMessage(permitNoc.getBpaNocApplication().getNocApplicationNumber(), module.getId(),
                    status, true, new Date(), null,
                    additionalPortalInboxUser, permitNoc.getBpaApplication().getPlanPermissionNumber(), url);
    }
    
    @Transactional
    public void createOCNocPortalUserinbox(final OccupancyNocApplication ocNoc, final List<User> portalInboxUser,
            final String workFlowAction) {
        String status = BpaConstants.NOC_INITIATED;

        Module module = moduleService.getModuleByName(BpaConstants.NOCMODULE);
        
        boolean isResolved = false;
        String url = "/bpa/ocnocapplication/update/" + ocNoc.getBpaNocApplication().getNocApplicationNumber();
        final PortalInboxBuilder portalInboxBuilder = new PortalInboxBuilder(module, ocNoc.getOc().getParent().getOwner().getName(),
        		ocNoc.getOc().getParent().getServiceType().getDescription(), ocNoc.getBpaNocApplication().getNocApplicationNumber(),
        		ocNoc.getOc().getOccupancyCertificateNumber(), ocNoc.getId(), SUCCESS, SUCCESS, url, isResolved,
                status, new Date(), null, portalInboxUser);

        final PortalInbox portalInbox = portalInboxBuilder.build();
        portalInboxService.pushInboxMessage(portalInbox);
    }
    
    @Transactional
    public void updateOCNocPortalUserinbox(final OccupancyNocApplication ocNoc, final User additionalPortalInboxUser) {
        Module module = moduleService.getModuleByName(BpaConstants.NOCMODULE);
        String status = ocNoc.getBpaNocApplication().getStatus().getCode();        
        String url = "/bpa/ocnocapplication/update/" +ocNoc.getBpaNocApplication().getNocApplicationNumber();
        portalInboxService.updateInboxMessage(ocNoc.getBpaNocApplication().getNocApplicationNumber(), module.getId(),
                    status, true, new Date(), null,
                    additionalPortalInboxUser, ocNoc.getOc().getOccupancyCertificateNumber(), url);
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

    public Boolean logedInuseCitizenOrBusinessUser() {
        Boolean citizenOrbusiness = Boolean.FALSE;
        User applicationInitiator = getCurrentUser();
        if (applicationInitiator != null && (applicationInitiator.getType().equals(UserType.CITIZEN)
                || applicationInitiator.getType().equals(UserType.BUSINESS))) {
            citizenOrbusiness = Boolean.TRUE;
        }
        return citizenOrbusiness;
    }

    public Boolean logedInuserIsCitizen() {
        return getCurrentUser() != null && getCurrentUser().getType().equals(UserType.CITIZEN) ? Boolean.TRUE : Boolean.FALSE;
    }

    @Transactional
    public void redirectToBpaWorkFlow(Long approvalPosition, final BpaApplication application,
            final String currentState, final String remarks, final String workFlowAction, final BigDecimal amountRule) {

        buildWorkFlow(approvalPosition, application, currentState, remarks, workFlowAction, amountRule);
    }

    @Transactional
    public void redirectToOccupancyCertificateWorkFlow(Long approvalPosition, final BpaApplication application,
            final String currentState, final String remarks, final String workFlowAction, final BigDecimal amountRule) {

        buildWorkFlow(approvalPosition, application, currentState, remarks, workFlowAction, amountRule);
    }

    public void redirectToBpaWorkFlowForScheduler(Long approvalPosition, final BpaApplication application,
            final String currentState, final String remarks, final String workFlowAction, final BigDecimal amountRule) {
        buildWorkFlow(approvalPosition, application, currentState, remarks, workFlowAction, amountRule);
    }

    private void buildWorkFlow(Long approvalPosition, final BpaApplication application, final String currentState,
            final String remarks, final String workFlowAction, final BigDecimal amountRule) {
        final WorkFlowMatrix wfMatrix = getWfMatrixByCurrentState(application.getIsOneDayPermitApplication(),
                application.getStateType(), currentState, application.getApplicationType().getName());
        final BpaApplicationWorkflowCustomDefaultImpl applicationWorkflowCustomDefaultImpl = getInitialisedWorkFlowBean();
        Long approvalPositionId = approvalPosition;
        if (approvalPosition == null) {
            approvalPositionId = getUserPositionIdByZone(wfMatrix.getNextDesignation(),
                    getBoundaryForWorkflow(application.getSiteDetail().get(0)).getId());
        }
        if (applicationWorkflowCustomDefaultImpl != null)
            if (LETTERTOPARTYINITIATE.equals(currentState))
                applicationWorkflowCustomDefaultImpl.createCommonWorkflowTransition(application, approvalPositionId, remarks,
                        application.getApplicationType().getName(), LETTERTOPARTYINITIATE, amountRule);
            else if (LPCREATED.equals(currentState))
                applicationWorkflowCustomDefaultImpl.createCommonWorkflowTransition(application, approvalPositionId, remarks,
                        application.getApplicationType().getName(), LPCREATED, amountRule);
            else if (LPREPLIED.equals(currentState))
                applicationWorkflowCustomDefaultImpl.createCommonWorkflowTransition(application, approvalPositionId, remarks,
                        application.getApplicationType().getName(), LPREPLYRECEIVED, amountRule);
            else if (WF_PERMIT_FEE_COLL_PENDING.equals(currentState)) {
                if (application.getIsOneDayPermitApplication()) {
                    applicationWorkflowCustomDefaultImpl.createCommonWorkflowTransition(application, approvalPositionId, remarks,
                            BpaConstants.APPLICATION_TYPE_ONEDAYPERMIT, WF_PERMIT_FEE_COLL_PENDING, amountRule);
                } else
                    applicationWorkflowCustomDefaultImpl.createCommonWorkflowTransition(application, approvalPositionId, remarks,
                            application.getApplicationType().getName(), WF_PERMIT_FEE_COLL_PENDING, amountRule);
            } else {
                if (application.getIsOneDayPermitApplication()) {
                    applicationWorkflowCustomDefaultImpl.createCommonWorkflowTransition(application, approvalPositionId, remarks,
                            BpaConstants.APPLICATION_TYPE_ONEDAYPERMIT, workFlowAction, amountRule);
                } else
                    applicationWorkflowCustomDefaultImpl.createCommonWorkflowTransition(application, approvalPositionId, remarks,
                            application.getApplicationType().getName(), workFlowAction, amountRule);
            }
    }

    public void redirectToBpaWorkFlowForOC(final OccupancyCertificate oc, final WorkflowBean wfBean) {
        buildWorkFlowForOccupancyCertificate(oc, wfBean);
    }

    private void buildWorkFlowForOccupancyCertificate(final OccupancyCertificate oc, final WorkflowBean wfBean) {
        final WorkFlowMatrix wfMatrix = getWfMatrixByCurrentState(oc.getStateType(), wfBean.getCurrentState());
        final OccupancyCertificateWorkflowCustomDefaultImpl ocWorkflowCustomDefaultImpl = getInitialisedWorkFlowBeanForOC();
        Long approvalPositionId = wfBean.getApproverPositionId();
        if (wfBean.getApproverPositionId() == null)
            approvalPositionId = getUserPositionIdByZone(wfMatrix.getNextDesignation(),
                    getBoundaryForWorkflow(oc.getParent().getSiteDetail().get(0)).getId());
        wfBean.setAdditionalRule(CREATE_ADDITIONAL_RULE_CREATE_OC);
        wfBean.setApproverPositionId(approvalPositionId);
        if (ocWorkflowCustomDefaultImpl != null)
            if (LETTERTOPARTYINITIATE.equals(wfBean.getCurrentState())) {
                wfBean.setWorkFlowAction(LETTERTOPARTYINITIATE);
                ocWorkflowCustomDefaultImpl.createCommonWorkflowTransition(oc, wfBean);
            } else if (LPCREATED.equals(wfBean.getCurrentState())) {
                wfBean.setWorkFlowAction(LPCREATED);
                ocWorkflowCustomDefaultImpl.createCommonWorkflowTransition(oc, wfBean);
            } else if (LPREPLIED.equals(wfBean.getCurrentState())) {
                wfBean.setWorkFlowAction(LPREPLYRECEIVED);
                ocWorkflowCustomDefaultImpl.createCommonWorkflowTransition(oc, wfBean);
            } else {
                ocWorkflowCustomDefaultImpl.createCommonWorkflowTransition(oc, wfBean);
            }
    }

    public void sendSmsEmailOnCitizenSubmit(BpaApplication bpaApplication) {
        bpaSmsAndEmailService.sendSMSAndEmail(bpaApplication, null, null);
    }

    public String generateUserName(final String name) {
        final StringBuilder userNameBuilder = new StringBuilder();
        String userName;
        if (name.length() < 6)
            userName = String.format("%-6s", name).replace(' ', '0').replace(',', '0');
        else
            userName = name.substring(0, 6).replace(' ', '0').replace(',', '0');
        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', '9').build();
        userNameBuilder.append(userName).append(generator.generate(4));
        return userNameBuilder.toString();
    }

    public String getAppconfigValueByKeyNameForDefaultDept() {
        List<AppConfigValues> appConfigValueList = appConfigValueService
                .getConfigValuesByModuleAndKey(APPLICATION_MODULE_TYPE, "BPAPRIMARYDEPARTMENT");
        return !appConfigValueList.isEmpty() ? appConfigValueList.get(0).getValue() : "";
    }

    public StateHistory<Position> getRejectionComments(List<StateHistory<Position>> stateHistories) {
        StateHistory<Position> stateHistory = stateHistories.stream()
                .filter(history -> history.getValue().equalsIgnoreCase(APPLICATION_STATUS_REJECTED))
                .findAny().orElse(null);
        if (stateHistory == null)
            stateHistory = stateHistories.stream()
                    .filter(history -> history.getValue().equalsIgnoreCase(APPLICATION_STATUS_REJECT_CLERK))
                    .findAny().orElse(null);
        return stateHistory;
    }

    public void addQrCodeToPdfDocuments(FileStoreMapper fileMapper, BpaApplication application) {
        pdfQrCodeAppend.addStamp(fileMapper, application);
    }

    public void addQrCodeToOcPdfDocuments(FileStoreMapper fileMapper, OccupancyCertificate oc) {
        pdfQrCodeAppend.addStampForOc(fileMapper, oc);
    }

    public Path getExistingFilePath(FileStoreMapper fileMapper, String moduleName) throws IOException {
        Path fileDirPath = this.getFileDirectoryPath(moduleName);
        if (!fileDirPath.toFile().exists()) {
            LOG.info("File Store Directory {}/{}/{} not found, creating one", this.fileStoreDir, getCityCode(),
                    moduleName);
            Files.createDirectories(fileDirPath);
            LOG.info("Created File Store Directory {}/{}/{}", this.fileStoreDir, getCityCode(), moduleName);
        }
        return this.getFilePath(fileDirPath, fileMapper.getFileStoreId());
    }

    private Path getFileDirectoryPath(String moduleName) {
        return Paths.get(new StringBuilder().append(this.fileStoreDir).append(separator).append(getCityCode())
                .append(separator).append(moduleName).toString());
    }

    private Path getFilePath(Path fileDirPath, String fileStoreId) {
        return Paths.get(fileDirPath + separator + fileStoreId);
    }

    public Map<SubOccupancy, BigDecimal> getBlockWiseOccupancyAndBuiltupArea(List<BuildingDetail> buildingDetails) {
        Map<SubOccupancy, BigDecimal> occupancyWiseBuiltupArea = new ConcurrentHashMap<>();
        for (BuildingDetail building : buildingDetails) {
            for (ApplicationFloorDetail floor : building.getApplicationFloorDetails()) {
                if (floor.getSubOccupancy() != null && occupancyWiseBuiltupArea.containsKey(floor.getSubOccupancy()))
                    occupancyWiseBuiltupArea.put(floor.getSubOccupancy(),
                            occupancyWiseBuiltupArea.get(floor.getSubOccupancy()).add(floor.getPlinthArea()));
                else
                    occupancyWiseBuiltupArea.put(floor.getSubOccupancy(), floor.getPlinthArea());
            }
        }
        return occupancyWiseBuiltupArea;
    }

    public Map<SubOccupancy, BigDecimal> getOccupancyWiseFloorArea(List<BuildingDetail> buildingDetails) {
        Map<SubOccupancy, BigDecimal> occupancyWiseBuiltupArea = new ConcurrentHashMap<>();
        for (BuildingDetail building : buildingDetails) {
            for (ApplicationFloorDetail floor : building.getApplicationFloorDetails()) {
                if (occupancyWiseBuiltupArea.containsKey(floor.getSubOccupancy()))
                    occupancyWiseBuiltupArea.put(floor.getSubOccupancy(),
                            occupancyWiseBuiltupArea.get(floor.getSubOccupancy()).add(floor.getFloorArea()));
                else
                    occupancyWiseBuiltupArea.put(floor.getSubOccupancy(), floor.getFloorArea());
            }
        }
        return occupancyWiseBuiltupArea;
    }

    public Map<Integer, String> getOccupancyAndFloorNumber(List<BuildingDetail> buildingDetails) {
        Map<Integer, String> occupancyAndFloorNumber = new ConcurrentHashMap<>();
        for (BuildingDetail building : buildingDetails) {
            for (ApplicationFloorDetail floor : building.getApplicationFloorDetails()) {
                if (occupancyAndFloorNumber.containsKey(floor.getFloorNumber()))
                    occupancyAndFloorNumber.put(floor.getFloorNumber(), occupancyAndFloorNumber.get(floor.getFloorNumber()));
                else
                    occupancyAndFloorNumber.put(floor.getFloorNumber(), floor.getSubOccupancy().getCode());
            }
        }
        return occupancyAndFloorNumber;
    }

    public Map<SubOccupancy, BigDecimal> getExistingBldgBlockWiseOccupancyAndBuiltupArea(
            List<ExistingBuildingDetail> existBldgDtls) {
        Map<SubOccupancy, BigDecimal> occupancyWiseBuiltupArea = new ConcurrentHashMap<>();
        for (ExistingBuildingDetail building : existBldgDtls) {
            for (ExistingBuildingFloorDetail floor : building.getExistingBuildingFloorDetails()) {
                if (occupancyWiseBuiltupArea.containsKey(floor.getSubOccupancy()))
                    occupancyWiseBuiltupArea.put(floor.getSubOccupancy(),
                            occupancyWiseBuiltupArea.get(floor.getSubOccupancy()).add(floor.getPlinthArea()));
                else
                    occupancyWiseBuiltupArea.put(floor.getSubOccupancy(), floor.getPlinthArea());
            }
        }
        return occupancyWiseBuiltupArea;
    }

    public BuildingDetail getBuildingHasHighestHeight(List<BuildingDetail> buildingDetails) {
        return buildingDetails.stream().max(Comparator.comparing(BuildingDetail::getHeightFromGroundWithOutStairRoom)).get();
    }

    public static Map<String, BigDecimal> getTotalProposedArea(List<BuildingDetail> buildingDetails) {
        BigDecimal totalBltUpArea = BigDecimal.ZERO;
        BigDecimal totalFloorArea = BigDecimal.ZERO;
        BigDecimal totalCarpetArea = BigDecimal.ZERO;
        Map<String, BigDecimal> proposedArea = new HashMap<>();
        for (BuildingDetail building : buildingDetails) {
            for (ApplicationFloorDetail floor : building.getApplicationFloorDetails()) {
                totalBltUpArea = totalBltUpArea.add(floor.getPlinthArea());
                totalFloorArea = totalFloorArea.add(floor.getFloorArea());
                totalCarpetArea = totalCarpetArea.add(floor.getCarpetArea());
            }
        }
        proposedArea.put("totalBltUpArea", totalBltUpArea);
        proposedArea.put("totalFloorArea", totalFloorArea);
        proposedArea.put("totalCarpetArea", totalCarpetArea);
        return proposedArea;
    }

    public static Map<String, BigDecimal> getTotalExstArea(List<ExistingBuildingDetail> exstBuildingDetails) {
        BigDecimal totalBltUpArea = BigDecimal.ZERO;
        BigDecimal totalFloorArea = BigDecimal.ZERO;
        BigDecimal totalCarpetArea = BigDecimal.ZERO;
        Map<String, BigDecimal> exstArea = new HashMap<>();
        for (ExistingBuildingDetail building : exstBuildingDetails) {
            for (ExistingBuildingFloorDetail floor : building.getExistingBuildingFloorDetails()) {
                totalBltUpArea = totalBltUpArea.add(floor.getPlinthArea());
                totalFloorArea = totalFloorArea.add(floor.getFloorArea());
                totalCarpetArea = totalCarpetArea.add(floor.getCarpetArea());
            }
        }
        exstArea.put("exstTotalBltUpArea", totalBltUpArea);
        exstArea.put("exstTotalFloorArea", totalFloorArea);
        exstArea.put("exstTotalCarpetArea", totalCarpetArea);
        return exstArea;
    }

    public static Map<String, BigDecimal> getProposedBuildingAreasOfOC(List<OCBuilding> ocBuildings) {
        BigDecimal totalBltUpArea = BigDecimal.ZERO;
        BigDecimal totalFloorArea = BigDecimal.ZERO;
        BigDecimal totalCarpetArea = BigDecimal.ZERO;
        Map<String, BigDecimal> proposedArea = new HashMap<>();
        for (OCBuilding building : ocBuildings) {
            for (OCFloor floor : building.getFloorDetails()) {
                totalBltUpArea = totalBltUpArea.add(floor.getPlinthArea());
                totalFloorArea = totalFloorArea.add(floor.getFloorArea());
                totalCarpetArea = totalCarpetArea.add(floor.getCarpetArea());
            }
        }
        proposedArea.put("totalBltUpArea", totalBltUpArea);
        proposedArea.put("totalFloorArea", totalFloorArea);
        proposedArea.put("totalCarpetArea", totalCarpetArea);
        return proposedArea;
    }

    public static Map<String, BigDecimal> getExistingBuildingAreasOfOC(List<OCExistingBuilding> ocExistingBuildings) {
        BigDecimal totalBltUpArea = BigDecimal.ZERO;
        BigDecimal totalFloorArea = BigDecimal.ZERO;
        BigDecimal totalCarpetArea = BigDecimal.ZERO;
        Map<String, BigDecimal> proposedArea = new HashMap<>();
        for (OCExistingBuilding existingBuilding : ocExistingBuildings) {
            for (OCExistingBuildingFloor floor : existingBuilding.getExistingBuildingFloorDetails()) {
                totalBltUpArea = totalBltUpArea.add(floor.getPlinthArea());
                totalFloorArea = totalFloorArea.add(floor.getFloorArea());
                totalCarpetArea = totalCarpetArea.add(floor.getCarpetArea());
            }
        }
        proposedArea.put("totalBltUpArea", totalBltUpArea);
        proposedArea.put("totalFloorArea", totalFloorArea);
        proposedArea.put("totalCarpetArea", totalCarpetArea);
        return proposedArea;
    }

    public String getOCFeeCalculationMode() {
        return getAppConfigValueForFeeCalculation(BpaConstants.OCFEECALULATION);
    }

    public String getBPAFeeCalculationMode() {
        return getAppConfigValueForFeeCalculation(BpaConstants.BPAFEECALULATION);
    }

    private String getAppConfigValueForFeeCalculation(String mode) {
        List<AppConfigValues> appConfigValue = appConfigValueService.getConfigValuesByModuleAndKey(EGMODULE_NAME, mode);
        String feeCalculationMode = BpaConstants.MANUAL;
        if (appConfigValue != null && !appConfigValue.isEmpty()) {
            String configValue = appConfigValue.get(0).getValue();
            return configValue.equalsIgnoreCase(BpaConstants.AUTOFEECAL)
                    || configValue.equalsIgnoreCase(BpaConstants.AUTOFEECALEDIT) ? configValue : feeCalculationMode;
        }
        return feeCalculationMode;
    }

    public boolean isDocScrutinyIntegrationRequired() {
        return getAppconfigValueByKeyName(DOC_SCRUTINY_INTEGRATION_REQUIRED).equalsIgnoreCase(YES);
    }

    public boolean isOneDayPermitInspectionSchedulingIntegrationRequired() {
        return getAppconfigValueByKeyName(ONE_DAY_PERMIT_INSPECTION_SCHEDULE_INTEGRATION_REQUIRED).equalsIgnoreCase(YES);
    }

    public boolean isRegularPermitInspectionSchedulingIntegrationRequired() {
        return getAppconfigValueByKeyName(REGULAR_PERMIT_INSPECTION_SCHEDULE_INTEGRATION_REQUIRED).equalsIgnoreCase(YES);
    }

    public boolean isOneDayPermitApplicationIntegrationRequired() {
        return getAppconfigValueByKeyName(ONE_DAY_PERMIT_APPLN_INTEGRATION_REQUIRED).equalsIgnoreCase(YES);
    }

    public boolean isCitizenAcceptanceRequired() {
        return getAppconfigValueByKeyName(BPA_CITIZENACCEPTANCE_CHECK).equalsIgnoreCase(YES);
    }

    public Map<Integer, HashMap<Integer, BigDecimal>> getBlockWiseAndFloorWiseFloorArea(
            List<BuildingDetail> bpaBuildings) {
        Map<Integer, HashMap<Integer, BigDecimal>> bpaMap = new HashMap<>();
        for (BuildingDetail bpaBuilding : bpaBuildings) {
            HashMap<Integer, BigDecimal> map = new HashMap<>();
            for (ApplicationFloorDetail bpaFloor : bpaBuilding.getApplicationFloorDetails())
                map.put(bpaFloor.getFloorNumber(), bpaFloor.getFloorArea());
            bpaMap.put(bpaBuilding.getNumber(), map);
        }
        return bpaMap;

    }

    public String getAppConfigForOcAllowDeviation() {
        List<AppConfigValues> appConfigValueList = appConfigValueService
                .getConfigValuesByModuleAndKey(EGMODULE_NAME, "OC_ALLOW_DEVIATION");
        return appConfigValueList.get(0).getValue();
    }

    public Boolean isApplicationFeeCollectionRequired() {
        return getAppconfigValueByKeyName(PERMIT_APPLN_FEE_COLLECTION_REQUIRED).equalsIgnoreCase(YES);
    }

    public void saveOrUpdateBoundary(BpaApplication bpaApplication) {
        SiteDetail siteDetail = bpaApplication.getSiteDetail().get(0);
        List<SiteDetail> siteDetails = new ArrayList<>();
        if (bpaApplication.getAdminBoundary() != null && !bpaApplication.getAdminBoundary().isEmpty()) {
            siteDetail.setElectionBoundary(
                    boundaryService.getBoundaryById(Long.valueOf(bpaApplication.getAdminBoundary())));
        }
        if (bpaApplication.getRevenueBoundary() != null && !bpaApplication.getRevenueBoundary().isEmpty()) {
            siteDetail.setAdminBoundary(
                    boundaryService.getBoundaryById(Long.valueOf(bpaApplication.getRevenueBoundary())));
            bpaApplication.setWardId(Long.valueOf(bpaApplication.getRevenueBoundary()));
        }
        if (bpaApplication.getLocationBoundary() != null && !bpaApplication.getLocationBoundary().isEmpty()) {
            siteDetail.setLocationBoundary(
                    boundaryService.getBoundaryById(Long.valueOf(bpaApplication.getLocationBoundary())));
        }
        siteDetails.add(siteDetail);
        bpaApplication.setSiteDetail(siteDetails);
    }

    public void loadBoundary(BpaApplication bpaApplication) {
        SiteDetail siteDetail = bpaApplication.getSiteDetail().get(0);
        bpaApplication.setAdminBoundary(
                siteDetail.getElectionBoundary() == null ? "" : String.valueOf(siteDetail.getElectionBoundary().getId()));
        bpaApplication.setLocationBoundary(
                siteDetail.getLocationBoundary() == null ? "" : String.valueOf(siteDetail.getLocationBoundary().getId()));
        bpaApplication.setRevenueBoundary(
                siteDetail.getAdminBoundary() == null ? "" : String.valueOf(siteDetail.getAdminBoundary().getId()));
    }

    public Boundary getBoundaryForWorkflow(SiteDetail siteDetail) {
        Boundary workFlowBoundary = null;
        String workflowBoundary = appConfigValuesService.getConfigValuesByModuleAndKey(BpaConstants.BPA_MODULE_NAME,
                BpaConstants.WORKFLOW_EMPLOYEE_BOUNDARY_HIERARCHY).get(0).getValue();
        if (workflowBoundary != null && !workflowBoundary.isEmpty()) {
            if (workflowBoundary.equals("ADMINISTRATION")) {
                workFlowBoundary = siteDetail.getElectionBoundary();
            } else if (workflowBoundary.equals("REVENUE")) {
                workFlowBoundary = siteDetail.getAdminBoundary();
            } else {
                workFlowBoundary = siteDetail.getLocationBoundary();
            }
        }
        return workFlowBoundary;
    }

    public String getStateLogoPath() {
        List<AppConfigValues> appConfigValueList = appConfigValueService
                .getConfigValuesByModuleAndKey(EGMODULE_NAME, BpaConstants.STATELOGO);
        return appConfigValueList.isEmpty() ? "" : appConfigValueList.get(0).getValue();
    }

    public ApplicationSubType getBuildingType(BigDecimal heightOfTheBuilding, BigDecimal plotArea, String occupancy) {
		if (BpaConstants.BPA_RESIDENTIAL.equalsIgnoreCase(occupancy)) {
			return getResidentialApplicationType(plotArea, heightOfTheBuilding);
		} else if (BpaConstants.BPA_INDUSTRIAL.equalsIgnoreCase(occupancy)) {
			return getIndustrialApplicationType(plotArea, heightOfTheBuilding);
		} else {
			return getMostRestrOccAppType(plotArea, heightOfTheBuilding);
		}
	}
	
	public ApplicationSubType getResidentialApplicationType(BigDecimal plotArea, BigDecimal heightOfTheBuilding) {
		if(plotArea.compareTo(BigDecimal.valueOf(500)) > 0 || 
				heightOfTheBuilding.compareTo(BigDecimal.valueOf(15)) > 0)
			return applicationTypeService.findByName(BpaConstants.APPLICATION_TYPE_HIGHRISK);
		else if (plotArea.compareTo(BigDecimal.valueOf(300)) >= 0
				&& plotArea.compareTo(BigDecimal.valueOf(500)) <= 0
				|| heightOfTheBuilding.compareTo(BigDecimal.TEN) >= 0
						&& heightOfTheBuilding.compareTo(BigDecimal.valueOf(15)) <= 0) {
			return applicationTypeService.findByName(BpaConstants.APPLICATION_TYPE_MEDIUMRISK);
		} else
			return applicationTypeService.findByName(BpaConstants.APPLICATION_TYPE_LOWRISK);
	}
	

	public ApplicationSubType getIndustrialApplicationType(BigDecimal plotArea, BigDecimal heightOfTheBuilding) {
		if(plotArea.compareTo(BigDecimal.valueOf(1000)) > 0 
				|| heightOfTheBuilding.compareTo(BigDecimal.valueOf(11)) > 0)
			return applicationTypeService.findByName(BpaConstants.APPLICATION_TYPE_HIGHRISK);
		else
			return applicationTypeService.findByName(BpaConstants.APPLICATION_TYPE_MEDIUMRISK);
	}
	
	public ApplicationSubType getMostRestrOccAppType(BigDecimal plotArea, BigDecimal heightOfTheBuilding) {
		if (plotArea.compareTo(BigDecimal.valueOf(500)) > 0 || 
				heightOfTheBuilding.compareTo(BigDecimal.valueOf(15)) > 0)
			return applicationTypeService.findByName(BpaConstants.APPLICATION_TYPE_HIGHRISK);
	    else
			return applicationTypeService.findByName(BpaConstants.APPLICATION_TYPE_MEDIUMRISK);
	}

}