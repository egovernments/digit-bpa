package org.egov.bpa.utils;

import static java.io.File.separator;
import static org.apache.commons.io.FileUtils.getUserDirectoryPath;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_MODULE_TYPE;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_CANCELLED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REJECTED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REJECT_CLERK;
import static org.egov.bpa.utils.BpaConstants.BOUNDARY_TYPE_CITY;
import static org.egov.bpa.utils.BpaConstants.BOUNDARY_TYPE_ZONE;
import static org.egov.bpa.utils.BpaConstants.BPA_CITIZENACCEPTANCE_CHECK;
import static org.egov.bpa.utils.BpaConstants.CREATE_ADDITIONAL_RULE_CREATE;
import static org.egov.bpa.utils.BpaConstants.CREATE_ADDITIONAL_RULE_CREATE_OC;
import static org.egov.bpa.utils.BpaConstants.CREATE_ADDITIONAL_RULE_CREATE_ONEDAYPERMIT;
import static org.egov.bpa.utils.BpaConstants.DOC_SCRUTINY_INTEGRATION_REQUIRED;
import static org.egov.bpa.utils.BpaConstants.EGMODULE_NAME;
import static org.egov.bpa.utils.BpaConstants.LETTERTOPARTYINITIATE;
import static org.egov.bpa.utils.BpaConstants.LPCREATED;
import static org.egov.bpa.utils.BpaConstants.LPREPLIED;
import static org.egov.bpa.utils.BpaConstants.LPREPLYRECEIVED;
import static org.egov.bpa.utils.BpaConstants.ONE_DAY_PERMIT_APPLN_INTEGRATION_REQUIRED;
import static org.egov.bpa.utils.BpaConstants.ONE_DAY_PERMIT_INSPECTION_SCHEDULE_INTEGRATION_REQUIRED;
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
import org.egov.bpa.transaction.entity.ApplicationFloorDetail;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BuildingDetail;
import org.egov.bpa.transaction.entity.ExistingBuildingDetail;
import org.egov.bpa.transaction.entity.ExistingBuildingFloorDetail;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.entity.oc.OCBuilding;
import org.egov.bpa.transaction.entity.oc.OCFloor;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.service.PdfQrCodeAppendService;
import org.egov.bpa.transaction.service.messaging.BPASmsAndEmailService;
import org.egov.bpa.transaction.workflow.BpaApplicationWorkflowCustomDefaultImpl;
import org.egov.bpa.transaction.workflow.oc.OccupancyCertificateWorkflowCustomDefaultImpl;
import org.egov.collection.integration.models.BillReceiptInfo;
import org.egov.collection.integration.services.CollectionIntegrationService;
import org.egov.common.entity.bpa.Occupancy;
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
		List<BillReceiptInfo> receipts = collectionIntegrationService.getOnlinePendingReceipts(APPLICATION_MODULE_TYPE, applicationNumber);
		if(!receipts.isEmpty())
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

	public WorkFlowMatrix getWfMatrixByCurrentState(final Boolean isOneDayPermit, final String stateType, final String currentState) {
		if (isOneDayPermit) {
			return bpaApplicationWorkflowService.getWfMatrix(stateType, null, null,
					CREATE_ADDITIONAL_RULE_CREATE_ONEDAYPERMIT, currentState, null);
		} else
			return bpaApplicationWorkflowService.getWfMatrix(stateType, null, null,
					CREATE_ADDITIONAL_RULE_CREATE, currentState, null);
	}

	public WorkFlowMatrix getWfMatrixByCurrentState(final String stateType, final String currentState) {
			return bpaApplicationWorkflowService.getWfMatrix(stateType, null, null,
					CREATE_ADDITIONAL_RULE_CREATE_OC, currentState, null);
	}

	@Transactional
	public void updatePortalUserinbox(final BpaApplication application, final User additionalPortalInboxUser) {
		Module module = moduleService.getModuleByName(EGMODULE_NAME);
		boolean isResolved = false;
		if ((application.getState() != null && (CLOSED.equals(application.getState().getValue())
												|| WF_END_ACTION.equals(application.getState().getValue())))
			|| (application.getStatus() != null
				&& application.getStatus().getCode().equals(APPLICATION_STATUS_CANCELLED)))
			isResolved = true;
		String url = "/bpa/application/citizen/update/" + application.getApplicationNumber();
		if (application.getStatus() != null)
			portalInboxService.updateInboxMessage(application.getApplicationNumber(), module.getId(),
					application.getStatus().getDescription(), isResolved, new Date(), application.getState(),
					additionalPortalInboxUser, application.getPlanPermissionNumber(), url);
	}

	@Transactional
	public void createPortalUserinbox(final BpaApplication application, final List<User> portalInboxUser,
									  final String workFlowAction) {
		String status = StringUtils.EMPTY;
		if ("Save".equalsIgnoreCase(workFlowAction)) {
			status = "To be submitted";
		} else if (null != application.getStatus().getDescription()
				   && WF_LBE_SUBMIT_BUTTON.equalsIgnoreCase(workFlowAction)) {
			status = application.getStatus().getDescription();
		}
		Module module = moduleService.getModuleByName(EGMODULE_NAME);
		boolean isResolved = false;
		String url = "/bpa/application/citizen/update/" + application.getApplicationNumber();
		final PortalInboxBuilder portalInboxBuilder = new PortalInboxBuilder(module, application.getOwner().getName(),
				application.getServiceType().getDescription(), application.getApplicationNumber(),
				application.getPlanPermissionNumber(), application.getId(), "Success", "Success", url, isResolved,
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
			status = oc.getStatus().getDescription();
		}
		Module module = moduleService.getModuleByName(EGMODULE_NAME);
		boolean isResolved = false;
		String url = "/bpa/application/citizen/occupancy-certificate/update/" + oc.getApplicationNumber();
		final PortalInboxBuilder portalInboxBuilder = new PortalInboxBuilder(module, oc.getParent().getOwner().getName(),
				oc.getApplicationType(), oc.getApplicationNumber(),
				oc.getOccupancyCertificateNumber(), oc.getId(), "Success", "Success", url, isResolved,
				status, new Date(), oc.getState(), portalInboxUser);

		final PortalInbox portalInbox = portalInboxBuilder.build();
		portalInboxService.pushInboxMessage(portalInbox);
	}

	@Transactional
	public void updatePortalUserinbox(final OccupancyCertificate oc, final User additionalPortalInboxUser) {
		Module module = moduleService.getModuleByName(EGMODULE_NAME);
		boolean isResolved = false;
		if ((oc.getState() != null && (CLOSED.equals(oc.getState().getValue())
									   || WF_END_ACTION.equals(oc.getState().getValue())))
			|| (oc.getStatus() != null
				&& oc.getStatus().getCode().equals(APPLICATION_STATUS_CANCELLED)))
			isResolved = true;
		String url = "/bpa/application/citizen/occupancy-certificate/update/" + oc.getApplicationNumber();
		if (oc.getStatus() != null)
			portalInboxService.updateInboxMessage(oc.getApplicationNumber(), module.getId(),
					oc.getStatus().getDescription(), isResolved, new Date(), oc.getState(),
					additionalPortalInboxUser, oc.getOccupancyCertificateNumber(), url);
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

	private List<Assignment> getAssignmentsByDesigAndBndryId(String designation, Long boundary) {
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
		final WorkFlowMatrix wfMatrix = getWfMatrixByCurrentState(application.getIsOneDayPermitApplication(), application.getStateType(), currentState);
		final BpaApplicationWorkflowCustomDefaultImpl applicationWorkflowCustomDefaultImpl = getInitialisedWorkFlowBean();
		Long approvalPositionId = approvalPosition;
		if (approvalPosition == null) {
			approvalPositionId = getUserPositionIdByZone(wfMatrix.getNextDesignation(),
					application.getSiteDetail().get(0).getElectionBoundary().getId());
		}
		if(applicationWorkflowCustomDefaultImpl != null)
			if (LETTERTOPARTYINITIATE.equals(currentState))
				applicationWorkflowCustomDefaultImpl.createCommonWorkflowTransition(application, approvalPositionId, remarks,
						CREATE_ADDITIONAL_RULE_CREATE, LETTERTOPARTYINITIATE, amountRule);
			else if (LPCREATED.equals(currentState))
				applicationWorkflowCustomDefaultImpl.createCommonWorkflowTransition(application, approvalPositionId, remarks,
						CREATE_ADDITIONAL_RULE_CREATE, LPCREATED, amountRule);
			else if (LPREPLIED.equals(currentState))
				applicationWorkflowCustomDefaultImpl.createCommonWorkflowTransition(application, approvalPositionId, remarks,
						CREATE_ADDITIONAL_RULE_CREATE, LPREPLYRECEIVED, amountRule);
			else if (WF_PERMIT_FEE_COLL_PENDING.equals(currentState)) {
				if (application.getIsOneDayPermitApplication()) {
					applicationWorkflowCustomDefaultImpl.createCommonWorkflowTransition(application, approvalPositionId, remarks,
							CREATE_ADDITIONAL_RULE_CREATE_ONEDAYPERMIT, WF_PERMIT_FEE_COLL_PENDING, amountRule);
				} else
					applicationWorkflowCustomDefaultImpl.createCommonWorkflowTransition(application, approvalPositionId, remarks,
							CREATE_ADDITIONAL_RULE_CREATE, WF_PERMIT_FEE_COLL_PENDING, amountRule);
			} else {
				if (application.getIsOneDayPermitApplication()) {
					applicationWorkflowCustomDefaultImpl.createCommonWorkflowTransition(application, approvalPositionId, remarks,
							CREATE_ADDITIONAL_RULE_CREATE_ONEDAYPERMIT, workFlowAction, amountRule);
				} else
					applicationWorkflowCustomDefaultImpl.createCommonWorkflowTransition(application, approvalPositionId, remarks,
							CREATE_ADDITIONAL_RULE_CREATE, workFlowAction, amountRule);
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
					oc.getParent().getSiteDetail().get(0).getElectionBoundary().getId());
		wfBean.setAdditionalRule(CREATE_ADDITIONAL_RULE_CREATE_OC);
		wfBean.setApproverPositionId(approvalPositionId);
		if(ocWorkflowCustomDefaultImpl != null)
			if (LETTERTOPARTYINITIATE.equals(wfBean.getCurrentState())) {
				wfBean.setWorkFlowAction(LETTERTOPARTYINITIATE);
				ocWorkflowCustomDefaultImpl.createCommonWorkflowTransition(oc, wfBean);
			} else if (LPCREATED.equals(wfBean.getCurrentState())) {
				wfBean.setWorkFlowAction(LPCREATED);
				ocWorkflowCustomDefaultImpl.createCommonWorkflowTransition(oc, wfBean);
			} else if (LPREPLIED.equals(wfBean.getCurrentState())) {
				wfBean.setWorkFlowAction(LPREPLYRECEIVED);
				ocWorkflowCustomDefaultImpl.createCommonWorkflowTransition(oc, wfBean);
			} else if (WF_PERMIT_FEE_COLL_PENDING.equals(wfBean.getCurrentState())) {
				wfBean.setWorkFlowAction(WF_PERMIT_FEE_COLL_PENDING);
				ocWorkflowCustomDefaultImpl.createCommonWorkflowTransition(oc, wfBean);
			} else {
				ocWorkflowCustomDefaultImpl.createCommonWorkflowTransition(oc, wfBean);
			}
	}

	public void sendSmsEmailOnCitizenSubmit(BpaApplication bpaApplication) {
		bpaSmsAndEmailService.sendSMSAndEmail(bpaApplication,null,null);
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

	public StateHistory<Position> getRejectionComments(BpaApplication bpaApplication) {
		StateHistory<Position> stateHistory = bpaApplication.getStateHistory().stream()
															.filter(history -> history.getValue().equalsIgnoreCase(APPLICATION_STATUS_REJECTED))
															.findAny().orElse(null);
		if (stateHistory == null)
			stateHistory = bpaApplication.getStateHistory().stream()
										 .filter(history -> history.getValue().equalsIgnoreCase(APPLICATION_STATUS_REJECT_CLERK))
										 .findAny().orElse(null);
		return stateHistory;
	}


	public void addQrCodeToPdfDocuments(FileStoreMapper fileMapper, BpaApplication application){
	pdfQrCodeAppend.addStamp(fileMapper,application);
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

	public Map<Occupancy, BigDecimal> getBlockWiseOccupancyAndBuiltupArea(List<BuildingDetail> buildingDetails) {
		Map<Occupancy, BigDecimal> occupancyWiseBuiltupArea = new ConcurrentHashMap<>();
		for (BuildingDetail building : buildingDetails) {
			for (ApplicationFloorDetail floor : building.getApplicationFloorDetails()) {
				if (occupancyWiseBuiltupArea.containsKey(floor.getOccupancy()))
						occupancyWiseBuiltupArea.put(floor.getOccupancy(), occupancyWiseBuiltupArea.get(floor.getOccupancy()).add(floor.getPlinthArea()));
				else
					occupancyWiseBuiltupArea.put(floor.getOccupancy(), floor.getPlinthArea());
			}
		}
		return occupancyWiseBuiltupArea;
	}
	
	public Map<Occupancy, BigDecimal> getOccupancyWiseFloorArea(List<BuildingDetail> buildingDetails) {
		Map<Occupancy, BigDecimal> occupancyWiseBuiltupArea = new ConcurrentHashMap<>();
		for (BuildingDetail building : buildingDetails) {
			for (ApplicationFloorDetail floor : building.getApplicationFloorDetails()) {
				if (occupancyWiseBuiltupArea.containsKey(floor.getOccupancy()))
						occupancyWiseBuiltupArea.put(floor.getOccupancy(), occupancyWiseBuiltupArea.get(floor.getOccupancy()).add(floor.getFloorArea()));
				else
					occupancyWiseBuiltupArea.put(floor.getOccupancy(), floor.getFloorArea());
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
					occupancyAndFloorNumber.put(floor.getFloorNumber(), floor.getOccupancy().getCode());
			}
		}
		return occupancyAndFloorNumber;
	}
	
	public Map<Occupancy, BigDecimal> getExistingBldgBlockWiseOccupancyAndBuiltupArea(List<ExistingBuildingDetail> existBldgDtls) {
		Map<Occupancy, BigDecimal> occupancyWiseBuiltupArea = new ConcurrentHashMap<>();
		for (ExistingBuildingDetail building : existBldgDtls) {
			for (ExistingBuildingFloorDetail floor : building.getExistingBuildingFloorDetails()) {
				if (occupancyWiseBuiltupArea.containsKey(floor.getOccupancy()))
					occupancyWiseBuiltupArea.put(floor.getOccupancy(), occupancyWiseBuiltupArea.get(floor.getOccupancy()).add(floor.getPlinthArea()));
				else
					occupancyWiseBuiltupArea.put(floor.getOccupancy(), floor.getPlinthArea());
			}
		}
		return occupancyWiseBuiltupArea;
	}

	public BuildingDetail getBuildingHasHighestHeight(List<BuildingDetail> buildingDetails) {
		return buildingDetails.stream().max(Comparator.comparing(BuildingDetail::getHeightFromGroundWithOutStairRoom)).get();
	}
	
	public static Map<String,BigDecimal> getTotalProposedArea(List<BuildingDetail> buildingDetails) {
		BigDecimal totalBltUpArea = BigDecimal.ZERO;
		BigDecimal totalFloorArea = BigDecimal.ZERO;
		BigDecimal totalCarpetArea = BigDecimal.ZERO;
		Map<String,BigDecimal> proposedArea = new HashMap<>();
		for (BuildingDetail building : buildingDetails) {
			for (ApplicationFloorDetail floor : building.getApplicationFloorDetails()) {
				totalBltUpArea = totalBltUpArea.add(floor.getPlinthArea());
				totalFloorArea = totalFloorArea.add(floor.getFloorArea());
				totalCarpetArea = totalCarpetArea.add(floor.getCarpetArea());
			}
		}
		proposedArea.put("totalBltUpArea",totalBltUpArea);
		proposedArea.put("totalFloorArea",totalFloorArea);
		proposedArea.put("totalCarpetArea",totalCarpetArea);
		return proposedArea;
	}

	public static Map<String,BigDecimal> getTotalExstArea(List<ExistingBuildingDetail> exstBuildingDetails) {
		BigDecimal totalBltUpArea = BigDecimal.ZERO;
		BigDecimal totalFloorArea = BigDecimal.ZERO;
		BigDecimal totalCarpetArea = BigDecimal.ZERO;
		Map<String,BigDecimal> exstArea = new HashMap<>();
		for (ExistingBuildingDetail building : exstBuildingDetails) {
			for (ExistingBuildingFloorDetail floor : building.getExistingBuildingFloorDetails()) {
				totalBltUpArea = totalBltUpArea.add(floor.getPlinthArea());
				totalFloorArea = totalFloorArea.add(floor.getFloorArea());
				totalCarpetArea = totalCarpetArea.add(floor.getCarpetArea());
			}
		}
		exstArea.put("exstTotalBltUpArea",totalBltUpArea);
		exstArea.put("exstTotalFloorArea",totalFloorArea);
		exstArea.put("exstTotalCarpetArea",totalCarpetArea);
		return exstArea;
	}

	public static Map<String,BigDecimal> getOCTotalProposedArea(List<OCBuilding> buildingDetails) {
		BigDecimal totalBltUpArea = BigDecimal.ZERO;
		BigDecimal totalFloorArea = BigDecimal.ZERO;
		BigDecimal totalCarpetArea = BigDecimal.ZERO;
		Map<String,BigDecimal> proposedArea = new HashMap<>();
		for (OCBuilding building : buildingDetails) {
			for (OCFloor floor : building.getFloorDetails()) {
				totalBltUpArea = totalBltUpArea.add(floor.getPlinthArea());
				totalFloorArea = totalFloorArea.add(floor.getFloorArea());
				totalCarpetArea = totalCarpetArea.add(floor.getCarpetArea());
			}
		}
		proposedArea.put("totalBltUpArea",totalBltUpArea);
		proposedArea.put("totalFloorArea",totalFloorArea);
		proposedArea.put("totalCarpetArea",totalCarpetArea);
		return proposedArea;
	}
	

	public String getAppConfigValueForFeeCalculation(String moduleName, String mode) {
		final List<AppConfigValues> appConfigValue = appConfigValueService.getConfigValuesByModuleAndKey(moduleName,
				mode);
		String feeCalculationMode = BpaConstants.MANUAL;
		
		if(appConfigValue != null && !appConfigValue.isEmpty()) {
			String configValue = appConfigValue.get(0).getValue();
			if(configValue.equalsIgnoreCase(BpaConstants.AUTOFEECAL))
			return configValue;
			else if(configValue.equalsIgnoreCase(BpaConstants.AUTOFEECALEDIT))
				return configValue;
			else
				return feeCalculationMode;
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
		 for(BuildingDetail bpaBuilding : bpaBuildings){
		    	HashMap<Integer, BigDecimal> map = new HashMap<Integer,BigDecimal>();
		    	for(ApplicationFloorDetail bpaFloor : bpaBuilding.getApplicationFloorDetails())
		    		map.put(bpaFloor.getFloorNumber(), bpaFloor.getFloorArea());
		    	bpaMap.put(bpaBuilding.getNumber(), map);
		    }
		return bpaMap;
	
	}
	
	public String getAppConfigForOcAllowDeviation() {
		List<AppConfigValues> appConfigValueList = appConfigValueService
				.getConfigValuesByModuleAndKey(BpaConstants.EGMODULE_NAME, "OC_ALLOW_DEVIATION");
		return appConfigValueList.get(0).getValue();
	}
}