package org.egov.bpa.service.es;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.egov.bpa.utils.BpaConstants.BPA_ADDITIONAL_FEE;
import static org.egov.bpa.utils.BpaConstants.BPA_COMPOUND_FEE;
import static org.egov.bpa.utils.BpaConstants.BPA_OTHER_FEE;
import static org.egov.bpa.utils.BpaConstants.BPA_PERMIT_FEE;
import static org.egov.bpa.utils.BpaConstants.BPA_WELL_FEE;
import static org.egov.bpa.utils.BpaConstants.ROOF_CNVRSN_FEE;
import static org.egov.bpa.utils.BpaConstants.SHTR_DOOR_FEE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.egov.bpa.entity.es.BpaIndex;
import org.egov.bpa.master.entity.BpaFee;
import org.egov.bpa.master.entity.BpaFeeMapping;
import org.egov.bpa.repository.es.BpaIndexRepository;
import org.egov.bpa.transaction.entity.ApplicationFeeDetail;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.service.ApplicationBpaService;
import org.egov.bpa.transaction.workflow.BpaWorkFlowService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.commons.entity.Source;
import org.egov.eis.entity.Assignment;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.entity.City;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.admin.master.service.CityService;
import org.egov.infra.admin.master.service.UserService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.elasticsearch.entity.ApplicationIndex;
import org.egov.infra.elasticsearch.entity.enums.ApprovalStatus;
import org.egov.infra.elasticsearch.entity.enums.ClosureStatus;
import org.egov.infra.elasticsearch.service.ApplicationIndexService;
import org.egov.infra.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BpaIndexService {

	private static final String APP_CONFIG_KEY = "SLAFORBPAAPPLICATION";
	private static final String GOVERNMENT_TYPE_GOV = "Government";
	private static final String GOVERNMENT_TYPE_QUASI_GOV = "Quasi Government";
	private static final String CONSTRUCTION_STAGES_INPROGRESS = "In Progress";
	private static final String CONSTRUCTION_STAGES_COMPLETED = "Completed";

	@Autowired
	private CityService cityService;

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityUtils securityUtils;

	@Autowired
	private BpaIndexRepository bpaIndexRepository;

	@Autowired
	private BpaWorkFlowService bpaWorkFlowService;

	@Autowired
	private AppConfigValueService appConfigValuesService;

	@Autowired
	private ApplicationIndexService applicationIndexService;

	@Autowired
	private ApplicationBpaService applicationBpaService;

	public BpaIndex createBpaIndex(final BpaApplication bpaApplication) {
		final City cityWebsite = cityService.getCityByURL(ApplicationThreadLocals.getDomainName());
		BpaIndex bpaIndex = new BpaIndex();
		buildUlbDetails(cityWebsite, bpaIndex);
		bpaIndex.setId(cityWebsite.getCode() + "-" + bpaApplication.getApplicationNumber());
		buildApplicantDetails(bpaApplication, bpaIndex);
		bpaIndex.setServiceType(bpaApplication.getServiceType().getDescription());
		bpaIndex.setApplicationNumber(bpaApplication.getApplicationNumber());
		Date applicationDate = bpaApplication.getApplicationDate();
		bpaIndex.setApplicationDate(applicationDate);
		bpaIndex.seteDcrNumber(bpaApplication.geteDcrNumber() == null ? EMPTY : bpaApplication.geteDcrNumber());
		if (bpaApplication.getIsOneDayPermitApplication())
			bpaIndex.setTypeOfLand(bpaApplication.getTypeOfLand().name());
		if (!bpaApplication.getStakeHolder().isEmpty()) {
			bpaIndex.setStakeHolderName(bpaApplication.getStakeHolder().get(0).getStakeHolder() == null ? EMPTY
					: bpaApplication.getStakeHolder().get(0).getStakeHolder().getName());
			bpaIndex.setStakeHolderType(bpaApplication.getStakeHolder().get(0).getStakeHolder() == null ? EMPTY
					: bpaApplication.getStakeHolder().get(0).getStakeHolder().getStakeHolderType().getName());
		}
		bpaIndex.setRemarks(bpaApplication.getRemarks() == null ? EMPTY : bpaApplication.getRemarks());
		bpaIndex.setPlanPermissionNumber(
				bpaApplication.getPlanPermissionNumber() == null ? EMPTY : bpaApplication.getPlanPermissionNumber());
		Date planPermissionDate = bpaApplication.getPlanPermissionDate();
		if (planPermissionDate != null) {
			bpaIndex.setPlanPermissionDate(planPermissionDate);
			int daysBetween = org.egov.infra.utils.DateUtils.daysBetween(applicationDate, planPermissionDate);
			bpaIndex.setNoOfDaysToProcess(daysBetween);
		}
		bpaIndex.setStatus(bpaApplication.getStatus() == null ? EMPTY : bpaApplication.getStatus().getCode());
		bpaIndex.setIsOneDayPermitApplication(bpaApplication.getIsOneDayPermitApplication());
		buildBpaSiteDetails(bpaApplication, bpaIndex);
		bpaIndex.setOccupancy(
				bpaApplication.getPermitOccupancies().isEmpty() ? EMPTY : bpaApplication.getOccupanciesName());
		buildGovernmentData(bpaApplication, bpaIndex);
		buildBpaBuildingDetails(bpaApplication, bpaIndex);
		buildBpaFeeData(bpaApplication, bpaIndex);
		bpaIndexRepository.save(bpaIndex);
		return bpaIndex;
	}

	private void buildBpaFeeData(BpaApplication bpaApplication, BpaIndex bpaIndex) {
		bpaIndex.setAdmissionFeeAmount(bpaApplication.getAdmissionfeeAmount() == null ? BigDecimal.ZERO
				: bpaApplication.getAdmissionfeeAmount());
		if (bpaApplication.getApplicationAmenity().isEmpty()) {
			bpaIndex.setApplicationFee(bpaApplication.getAdmissionfeeAmount() == null ? BigDecimal.ZERO
					: bpaApplication.getAdmissionfeeAmount());
		} else {
			bpaIndex.setApplicationFee(applicationBpaService.setAdmissionFeeAmountForRegistrationWithAmenities(
					bpaApplication.getServiceType().getId(), bpaApplication.getApplicationAmenity()));
		}
		bpaIndex.setTotalDemandAmount(bpaApplication.getDemand().getBaseDemand() == null ? BigDecimal.ZERO
				: bpaApplication.getDemand().getBaseDemand());
		bpaIndex.setTotalCollectedAmount(bpaApplication.getDemand().getAmtCollected() == null ? BigDecimal.ZERO
				: bpaApplication.getDemand().getAmtCollected());
		if (!bpaApplication.getPermitFee().isEmpty()
				&& bpaApplication.getPermitFee().get(0).getApplicationFee() != null && 
				!bpaApplication.getPermitFee().get(0).getApplicationFee().getApplicationFeeDetail().isEmpty()) {
			for (ApplicationFeeDetail appFeeDetail : bpaApplication.getPermitFee().get(0).getApplicationFee()
					.getApplicationFeeDetail()) {
				BpaFeeMapping bpaFee = appFeeDetail.getBpaFeeMapping();
				if (bpaFee.getBpaFeeCommon().getDescription().equals(BPA_PERMIT_FEE)) {
					bpaIndex.setPermitFee(appFeeDetail.getAmount());
				} else if (bpaFee.getBpaFeeCommon().getDescription().equals(BPA_ADDITIONAL_FEE)) {
					bpaIndex.setAdditionalFee(appFeeDetail.getAmount());
				} else if (bpaFee.getBpaFeeCommon().getDescription().equals(BPA_WELL_FEE)) {
					bpaIndex.setWellCharges(appFeeDetail.getAmount());
				} else if (bpaFee.getBpaFeeCommon().getDescription().equals(BPA_COMPOUND_FEE)) {
					bpaIndex.setCompoundWellCharges(appFeeDetail.getAmount());
				} else if (bpaFee.getBpaFeeCommon().getDescription().equals(SHTR_DOOR_FEE)) {
					bpaIndex.setShutterOrDoorConversionCharges(appFeeDetail.getAmount());
				} else if (bpaFee.getBpaFeeCommon().getDescription().equals(ROOF_CNVRSN_FEE)) {
					bpaIndex.setRoofConversionCharges(appFeeDetail.getAmount());
				} else if (bpaFee.getBpaFeeCommon().getDescription().equals(BPA_OTHER_FEE)) {
					bpaIndex.setOtherFee(appFeeDetail.getAmount());
				}
			}
		}
	}

	private void buildGovernmentData(final BpaApplication bpaApplication, BpaIndex bpaIndex) {
		bpaIndex.setGovernmentType(
				bpaApplication.getGovernmentType() == null ? EMPTY : bpaApplication.getGovernmentType().name());
		if (bpaApplication.getGovernmentType() != null
				&& bpaApplication.getGovernmentType().getGovernmentTypeVal().equals(GOVERNMENT_TYPE_GOV)
				|| bpaApplication.getGovernmentType().getGovernmentTypeVal().equals(GOVERNMENT_TYPE_QUASI_GOV))
			bpaIndex.setIsEconomicallyWeakerSection(bpaApplication.getIsEconomicallyWeakerSection());
	}

	private void buildUlbDetails(final City cityWebsite, BpaIndex bpaIndex) {
		bpaIndex.setUlbName(cityWebsite.getName());
		bpaIndex.setDistrictName(cityWebsite.getDistrictName());
		bpaIndex.setRegionName(cityWebsite.getRegionName());
		bpaIndex.setUlbGrade(cityWebsite.getGrade());
		bpaIndex.setUlbCode(cityWebsite.getCode());
	}

	private void buildApplicantDetails(final BpaApplication bpaApplication, BpaIndex bpaIndex) {
		bpaIndex.setApplicantName(bpaApplication.getOwner().getName());
		bpaIndex.setApplicantMobileNumber(bpaApplication.getOwner().getUser().getMobileNumber());
		bpaIndex.setApplicantEmailId(bpaApplication.getOwner().getEmailId());
		bpaIndex.setApplicantAddress(bpaApplication.getOwner().getAddress());
		bpaIndex.setApplicantGender(bpaApplication.getOwner().getGender().name());
	}

	private void buildBpaBuildingDetails(BpaApplication bpaApplication, BpaIndex bpaIndex) {
		if (!bpaApplication.getBuildingDetail().isEmpty()) {
			bpaIndex.setTotalPlintArea(
					bpaApplication.getBuildingDetail().get(0).getTotalPlintArea() == null ? BigDecimal.ZERO
							: bpaApplication.getBuildingDetail().get(0).getTotalPlintArea());
			bpaIndex.setFloorCount(bpaApplication.getBuildingDetail().get(0).getFloorCount() == null ? 0
					: bpaApplication.getBuildingDetail().get(0).getFloorCount());
			bpaIndex.setHeightFromGroundWithStairRoom(
					bpaApplication.getBuildingDetail().get(0).getHeightFromGroundWithStairRoom() == null
							? BigDecimal.ZERO
							: bpaApplication.getBuildingDetail().get(0).getHeightFromGroundWithStairRoom());
			bpaIndex.setHeightFromGroundWithOutStairRoom(
					bpaApplication.getBuildingDetail().get(0).getHeightFromGroundWithOutStairRoom() == null
							? BigDecimal.ZERO
							: bpaApplication.getBuildingDetail().get(0).getHeightFromGroundWithOutStairRoom());
			bpaIndex.setHeightFromStreetLevelWithStairRoom(
					bpaApplication.getBuildingDetail().get(0).getFromStreetLevelWithStairRoom() == null
							? BigDecimal.ZERO
							: bpaApplication.getBuildingDetail().get(0).getFromStreetLevelWithStairRoom());
			bpaIndex.setHeightFromStreetLevelWithOutStairRoom(
					bpaApplication.getBuildingDetail().get(0).getFromStreetLevelWithOutStairRoom() == null
							? BigDecimal.ZERO
							: bpaApplication.getBuildingDetail().get(0).getFromStreetLevelWithOutStairRoom());
		}
	}

	private void buildBpaSiteDetails(BpaApplication bpaApplication, BpaIndex bpaIndex) {
		if (!bpaApplication.getSiteDetail().isEmpty()) {
			buildRegularizationDetails(bpaApplication, bpaIndex);
			bpaIndex.setExtentOfLand(bpaApplication.getSiteDetail().get(0).getExtentOfLand() == null ? BigDecimal.ZERO
					: bpaApplication.getSiteDetail().get(0).getExtentOfLand());
			bpaIndex.setExtentinsqmts(bpaApplication.getSiteDetail().get(0).getExtentinsqmts() == null ? BigDecimal.ZERO
					: bpaApplication.getSiteDetail().get(0).getExtentinsqmts());
			buildSiteBoundaryDetails(bpaApplication, bpaIndex);
			bpaIndex.setReSurveyNumber(bpaApplication.getSiteDetail().get(0).getReSurveyNumber() == null ? EMPTY
					: bpaApplication.getSiteDetail().get(0).getReSurveyNumber());
			bpaIndex.setNatureofOwnership(bpaApplication.getSiteDetail().get(0).getNatureofOwnership() == null ? EMPTY
					: bpaApplication.getSiteDetail().get(0).getNatureofOwnership());
			buildSiteAddressDetails(bpaApplication, bpaIndex);
			bpaIndex.setScheme(bpaApplication.getSiteDetail().get(0).getScheme() == null ? EMPTY
					: bpaApplication.getSiteDetail().get(0).getScheme().getDescription());
			bpaIndex.setLandUsage(bpaApplication.getSiteDetail().get(0).getLandUsage() == null ? EMPTY
					: bpaApplication.getSiteDetail().get(0).getLandUsage().getDescription());
			bpaIndex.setNoOfTowers(bpaApplication.getSiteDetail().get(0).getErectionoftower() == null ? BigDecimal.ZERO
					: bpaApplication.getSiteDetail().get(0).getErectionoftower());
			bpaIndex.setNoOfPoles(bpaApplication.getSiteDetail().get(0).getNoOfPoles() == null ? BigDecimal.ZERO
					: bpaApplication.getSiteDetail().get(0).getNoOfPoles());
			bpaIndex.setNoOfHutOrSheds(
					bpaApplication.getSiteDetail().get(0).getNoOfHutOrSheds() == null ? BigDecimal.ZERO
							: bpaApplication.getSiteDetail().get(0).getNoOfHutOrSheds());
			bpaIndex.setRoofConversion(
					bpaApplication.getSiteDetail().get(0).getRoofConversion() == null ? BigDecimal.ZERO
							: bpaApplication.getSiteDetail().get(0).getRoofConversion());
			bpaIndex.setNoOfShuttersOrDoors(bpaApplication.getSiteDetail().get(0).getShutter() == null ? BigDecimal.ZERO
					: bpaApplication.getSiteDetail().get(0).getShutter());
			bpaIndex.setNoOfWells(bpaApplication.getSiteDetail().get(0).getDwellingunitnt() == null ? BigDecimal.ZERO
					: bpaApplication.getSiteDetail().get(0).getDwellingunitnt());
			bpaIndex.setLengthOfCompoundWall(
					bpaApplication.getSiteDetail().get(0).getLengthOfCompoundWall() == null ? BigDecimal.ZERO
							: bpaApplication.getSiteDetail().get(0).getLengthOfCompoundWall());
		}

	}

	private void buildSiteBoundaryDetails(BpaApplication bpaApplication, BpaIndex bpaIndex) {
		bpaIndex.setZone(bpaApplication.getSiteDetail().get(0).getAdminBoundary() == null ? EMPTY
				: bpaApplication.getSiteDetail().get(0).getAdminBoundary().getParent().getName());
		bpaIndex.setRevenueWard(bpaApplication.getSiteDetail().get(0).getAdminBoundary() == null ? EMPTY
				: bpaApplication.getSiteDetail().get(0).getAdminBoundary().getName());
		bpaIndex.setVillage(bpaApplication.getSiteDetail().get(0).getLocationBoundary() == null ? EMPTY
				: bpaApplication.getSiteDetail().get(0).getLocationBoundary().getName());
		bpaIndex.setElectionWard(bpaApplication.getSiteDetail().get(0).getElectionBoundary() == null ? EMPTY
				: bpaApplication.getSiteDetail().get(0).getElectionBoundary().getName());
		bpaIndex.setRegistrarOffice(bpaApplication.getSiteDetail().get(0).getRegistrarOffice() == null ? EMPTY
				: bpaApplication.getSiteDetail().get(0).getRegistrarOffice().getRegistrarOffice().getName());
	}

	private void buildSiteAddressDetails(BpaApplication bpaApplication, BpaIndex bpaIndex) {
		bpaIndex.setNearestbuildingnumber(
				bpaApplication.getSiteDetail().get(0).getNearestbuildingnumber() == null ? EMPTY
						: bpaApplication.getSiteDetail().get(0).getNearestbuildingnumber());
		bpaIndex.setDoorNumber(bpaApplication.getSiteDetail().get(0).getPlotdoornumber() == null ? EMPTY
				: bpaApplication.getSiteDetail().get(0).getPlotdoornumber());
		bpaIndex.setStreetAddress(bpaApplication.getSiteDetail().get(0).getStreetaddress1() == null ? EMPTY
				: bpaApplication.getSiteDetail().get(0).getStreetaddress1());
		bpaIndex.setLocality(bpaApplication.getSiteDetail().get(0).getStreetaddress2() == null ? EMPTY
				: bpaApplication.getSiteDetail().get(0).getStreetaddress2());
		bpaIndex.setCityTown(bpaApplication.getSiteDetail().get(0).getCitytown() == null ? EMPTY
				: bpaApplication.getSiteDetail().get(0).getCitytown());
        /*
         * bpaIndex.setPinCode(bpaApplication.getSiteDetail().get(0).getPostalAddress().getPincode() == null ? EMPTY :
         * bpaApplication.getSiteDetail().get(0).getPostalAddress().getPincode());
         * bpaIndex.setPlotTaluk(bpaApplication.getSiteDetail().get(0).getPostalAddress().getTaluk() == null ? EMPTY :
         * bpaApplication.getSiteDetail().get(0).getPostalAddress().getTaluk());
         * bpaIndex.setPostOffice(bpaApplication.getSiteDetail().get(0).getPostalAddress().getPostOffice() == null ? EMPTY :
         * bpaApplication.getSiteDetail().get(0).getPostalAddress().getPostOffice());
         * bpaIndex.setDistrict(bpaApplication.getSiteDetail().get(0).getPostalAddress().getDistrict() == null ? EMPTY :
         * bpaApplication.getSiteDetail().get(0).getPostalAddress().getDistrict());
         * bpaIndex.setState(bpaApplication.getSiteDetail().get(0).getPostalAddress().getState() == null ? EMPTY :
         * bpaApplication.getSiteDetail().get(0).getPostalAddress().getState());
         */
	}

	private void buildRegularizationDetails(BpaApplication bpaApplication, BpaIndex bpaIndex) {
		bpaIndex.setIsappForRegularization(bpaApplication.getSiteDetail().get(0).getIsappForRegularization());
		if (bpaApplication.getSiteDetail().get(0).getIsappForRegularization())
			bpaIndex.setConstStages(bpaApplication.getSiteDetail().get(0).getConstStages() == null ? EMPTY
					: bpaApplication.getSiteDetail().get(0).getConstStages().getCode());
		if (bpaApplication.getSiteDetail().get(0).getConstStages() != null)
			if (bpaApplication.getSiteDetail().get(0).getConstStages().getCode()
					.equals(CONSTRUCTION_STAGES_INPROGRESS)) {
				bpaIndex.setStateOfConstruction(
						bpaApplication.getSiteDetail().get(0).getStateOfConstruction() == null ? EMPTY
								: bpaApplication.getSiteDetail().get(0).getStateOfConstruction());
				bpaIndex.setWorkCommencementDate(bpaApplication.getSiteDetail().get(0).getWorkCommencementDate());

			} else if (bpaApplication.getSiteDetail().get(0).getConstStages().getCode()
					.equals(CONSTRUCTION_STAGES_COMPLETED)) {
				bpaIndex.setWorkCommencementDate(bpaApplication.getSiteDetail().get(0).getWorkCommencementDate());
				bpaIndex.setWorkCompletionDate(bpaApplication.getSiteDetail().get(0).getWorkCompletionDate());
			}
	}

	public void updateIndexes(final BpaApplication bpaApplication) {
		User user = getCurrentUser(bpaApplication);
		ApplicationIndex applicationIndex = applicationIndexService
				.findByApplicationNumber(bpaApplication.getApplicationNumber());
		if (applicationIndex != null && bpaApplication.getId() != null)
			buildApplicationIndexForUpdate(bpaApplication, user, applicationIndex);
		else {
			String viewUrl = "/bpa/application/view/%s";
			List<AppConfigValues> appConfigValue = appConfigValuesService
					.getConfigValuesByModuleAndKey(BpaConstants.APPLICATION_MODULE_TYPE, APP_CONFIG_KEY);
			Date disposalDate = calculateDisposalDate(appConfigValue);
			applicationIndex = ApplicationIndex.builder().withModuleName(BpaConstants.APPL_INDEX_MODULE_NAME)
					.withApplicationNumber(bpaApplication.getApplicationNumber())
					.withApplicationDate(bpaApplication.getApplicationDate())
					.withApplicationType(bpaApplication.getIsOneDayPermitApplication().equals(true)
							? BpaConstants.APPLICATION_TYPE_ONEDAYPERMIT
							: BpaConstants.APPLICATION_TYPE_REGULAR)
					.withOwnername(user == null ? EMPTY : user.getUsername() + "::" + user.getName())
					.withApplicantName(bpaApplication.getOwner().getName())
					.withApplicantAddress(bpaApplication.getOwner().getAddress())
					.withStatus(bpaApplication.getStatus().getCode())
					.withChannel(bpaApplication.getSource() == null ? Source.SYSTEM.toString()
							: bpaApplication.getSource().name())
					.withConsumerCode(bpaApplication.getApplicationNumber())
					.withMobileNumber(bpaApplication.getOwner().getUser().getMobileNumber())
					.withAadharNumber(StringUtils.isBlank(bpaApplication.getOwner().getAadhaarNumber()) ? EMPTY
							: bpaApplication.getOwner().getAadhaarNumber())
					.withUrl(String.format(viewUrl, bpaApplication.getApplicationNumber())).withClosed(ClosureStatus.NO)
					.withSla(appConfigValue.get(0).getValue() == null ? 0
							: Integer.valueOf(appConfigValue.get(0).getValue()))
					.withDisposalDate(disposalDate).withApproved(ApprovalStatus.INPROGRESS).build();
			applicationIndexService.createApplicationIndex(applicationIndex);
			createBpaIndex(bpaApplication);
		}
	}

	private void buildApplicationIndexForUpdate(final BpaApplication bpaApplication, User user,
			ApplicationIndex applicationIndex) {
		applicationIndex.setStatus(bpaApplication.getStatus().getCode());
		applicationIndex.setOwnerName(user == null ? "" : user.getUsername() + "::" + user.getName());
		if (bpaApplication.getStatus().getCode().equals(BpaConstants.APPLICATION_STATUS_CANCELLED)) {
			applicationIndex.setApproved(ApprovalStatus.REJECTED);
			applicationIndex.setClosed(ClosureStatus.YES);
		} else if (bpaApplication.getStatus().getCode().equals(BpaConstants.APPLICATION_STATUS_ORDER_ISSUED)) {
			applicationIndex.setApproved(ApprovalStatus.APPROVED);
			applicationIndex.setClosed(ClosureStatus.YES);
		}

		if (bpaApplication.getPlanPermissionNumber() != null)
			applicationIndex.setConsumerCode(bpaApplication.getPlanPermissionNumber());
		applicationIndexService.updateApplicationIndex(applicationIndex);
		createBpaIndex(bpaApplication);
	}

	private User getCurrentUser(final BpaApplication bpaApplication) {
		User user = null;
		if (bpaApplication.getState() == null || bpaApplication.getState().getOwnerPosition() == null) {
			user = securityUtils.getCurrentUser();
		} else {
			Assignment assignment = bpaWorkFlowService
					.getApproverAssignmentByDate(bpaApplication.getState().getOwnerPosition(), new Date());
			List<Assignment> assignments;
			if (assignment != null) {
				assignments = new ArrayList<>();
				assignments.add(assignment);
			} else
				assignments = bpaWorkFlowService.getAssignmentsByPositionAndDate(
						bpaApplication.getState().getOwnerPosition().getId(), new Date());
			if (!assignments.isEmpty())
				user = userService.getUserById(assignments.get(0).getEmployee().getId());
		}

		return user;
	}

	private Date calculateDisposalDate(List<AppConfigValues> appConfigValue) {
		return DateUtils.addDays(new Date(),
				appConfigValue.get(0) == null || appConfigValue.get(0).getValue() == null ? 0
						: Integer.valueOf(appConfigValue.get(0).getValue()));
	}
}
