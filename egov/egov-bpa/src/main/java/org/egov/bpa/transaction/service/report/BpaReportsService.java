/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2017>  eGovernments Foundation
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
package org.egov.bpa.transaction.service.report;

import static org.egov.bpa.utils.BpaConstants.ADDING_OF_EXTENSION;
import static org.egov.bpa.utils.BpaConstants.ALTERATION;
import static org.egov.bpa.utils.BpaConstants.AMENITIES;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_CANCELLED;
import static org.egov.bpa.utils.BpaConstants.BPA_ADDITIONAL_FEE;
import static org.egov.bpa.utils.BpaConstants.BPA_OTHER_FEE;
import static org.egov.bpa.utils.BpaConstants.CHANGE_IN_OCCUPANCY;
import static org.egov.bpa.utils.BpaConstants.DEMOLITION;
import static org.egov.bpa.utils.BpaConstants.DIVISION_OF_PLOT;
import static org.egov.bpa.utils.BpaConstants.NEW_CONSTRUCTION;
import static org.egov.bpa.utils.BpaConstants.PERM_FOR_HUT_OR_SHED;
import static org.egov.bpa.utils.BpaConstants.POLE_STRUCTURES;
import static org.egov.bpa.utils.BpaConstants.RECONSTRUCTION;
import static org.egov.bpa.utils.BpaConstants.TOWER_CONSTRUCTION;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.egov.bpa.master.entity.enums.ApplicationType;
import org.egov.bpa.transaction.entity.ApplicationFeeDetail;
import org.egov.bpa.transaction.entity.ApplicationNocDocument;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BpaAppointmentSchedule;
import org.egov.bpa.transaction.entity.SiteDetail;
import org.egov.bpa.transaction.entity.SlotDetail;
import org.egov.bpa.transaction.entity.dto.BpaRegisterReportHelper;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationForm;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationReport;
import org.egov.bpa.transaction.entity.dto.SlotDetailsHelper;
import org.egov.bpa.transaction.entity.enums.AppointmentSchedulePurpose;
import org.egov.bpa.transaction.notice.util.BpaNoticeUtil;
import org.egov.bpa.transaction.repository.ApplicationBpaRepository;
import org.egov.bpa.transaction.repository.SlotDetailRepository;
import org.egov.bpa.transaction.repository.specs.BpaReportsSearchSpec;
import org.egov.bpa.transaction.repository.specs.SearchBpaApplnFormSpec;
import org.egov.bpa.transaction.service.ApplicationBpaFeeCalculationService;
import org.egov.bpa.transaction.service.BpaAppointmentScheduleService;
import org.egov.bpa.transaction.service.SearchBpaApplicationService;
import org.egov.bpa.transaction.workflow.BpaWorkFlowService;
import org.egov.eis.entity.Assignment;
import org.egov.eis.service.PositionMasterService;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.workflow.entity.State;
import org.egov.infra.workflow.entity.StateHistory;
import org.egov.pims.commons.Position;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BpaReportsService {

    public static final String SECTION_CLERK = "SECTION CLERK";
    @Autowired
    private SearchBpaApplicationService searchBpaApplicationService;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private BpaNoticeUtil bpaNoticeUtil;
    @Autowired
    private PositionMasterService positionMasterService;
    @Autowired
    private BpaWorkFlowService bpaWorkFlowService;
    @Autowired
    private BpaAppointmentScheduleService bpaAppointmentScheduleService;
    @Autowired
    private ApplicationBpaFeeCalculationService bpaCalculationService;
    @Autowired
    private ApplicationBpaRepository applicationBpaRepository;
    @Autowired
    private SlotDetailRepository slotDetailRepository;

    public Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }

    public List<SearchBpaApplicationReport> getResultsByServicetypeAndStatus(
            final SearchBpaApplicationForm searchBpaApplicationForm) {
        List<SearchBpaApplicationReport> searchBpaApplicationReportList = new ArrayList<>();
        List<SearchBpaApplicationForm> searchBpaApplnResultList = searchBpaApplicationService
                .search(searchBpaApplicationForm);
        Map<String, Map<String, Long>> resultMap = searchBpaApplnResultList.stream()
                .collect(Collectors.groupingBy(SearchBpaApplicationForm::getStatus,
                        Collectors.groupingBy(SearchBpaApplicationForm::getServiceType, Collectors.counting())));
        for (final Entry<String, Map<String, Long>> statusCountResMap : resultMap.entrySet()) {
            Long newConstruction = 0l;
            Long demolition = 0l;
            Long reConstruction = 0l;
            Long alteration = 0l;
            Long divisionOfPlot = 0l;
            Long addingExtension = 0l;
            Long changeInOccupancy = 0l;
            Long amenities = 0l;
            Long hut = 0l;
            Long towerConstruction = 0l;
            Long poleStructure = 0l;
            SearchBpaApplicationReport bpaApplicationReport = new SearchBpaApplicationReport();
            bpaApplicationReport.setStatus(statusCountResMap.getKey());
            for (final Entry<String, Long> statusCountMap : statusCountResMap.getValue().entrySet()) {
                if (NEW_CONSTRUCTION.equalsIgnoreCase(statusCountMap.getKey())) {
                    newConstruction = newConstruction + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType01(newConstruction);
                } else if (DEMOLITION.equalsIgnoreCase(statusCountMap.getKey())) {
                    demolition = demolition + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType02(demolition);
                } else if (RECONSTRUCTION.equalsIgnoreCase(statusCountMap.getKey())) {
                    reConstruction = reConstruction + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType03(reConstruction);
                } else if (ALTERATION.equalsIgnoreCase(statusCountMap.getKey())) {
                    alteration = alteration + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType04(alteration);
                } else if (DIVISION_OF_PLOT.equalsIgnoreCase(statusCountMap.getKey())) {
                    divisionOfPlot = divisionOfPlot + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType05(divisionOfPlot);
                } else if (ADDING_OF_EXTENSION.equalsIgnoreCase(statusCountMap.getKey())) {
                    addingExtension = addingExtension + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType06(addingExtension);
                } else if (CHANGE_IN_OCCUPANCY.equalsIgnoreCase(statusCountMap.getKey())) {
                    changeInOccupancy = changeInOccupancy + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType07(changeInOccupancy);
                } else if (AMENITIES.equalsIgnoreCase(statusCountMap.getKey())) {
                    amenities = amenities + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType08(amenities);
                } else if (PERM_FOR_HUT_OR_SHED.equalsIgnoreCase(statusCountMap.getKey())) {
                    hut = hut + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType09(hut);
                } else if (TOWER_CONSTRUCTION.equalsIgnoreCase(statusCountMap.getKey())) {
                    towerConstruction = towerConstruction + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType14(towerConstruction);
                } else if (POLE_STRUCTURES.equalsIgnoreCase(statusCountMap.getKey())) {
                    poleStructure = poleStructure + statusCountMap.getValue();
                    bpaApplicationReport.setServiceType15(poleStructure);
                }
            }
            searchBpaApplicationReportList.add(bpaApplicationReport);
        }
        return searchBpaApplicationReportList;
    }

    public List<SearchBpaApplicationReport> getResultsForEachServicetypeByZone(
            final SearchBpaApplicationForm searchBpaApplicationForm) {
        List<SearchBpaApplicationReport> searchBpaApplicationReportList = new ArrayList<>();
        List<SearchBpaApplicationForm> searchBpaApplnResultList = searchBpaApplicationService
                .search(searchBpaApplicationForm);
        Map<String, Map<String, Long>> resultMap = searchBpaApplnResultList.stream()
                .collect(Collectors.groupingBy(SearchBpaApplicationForm::getServiceType,
                        Collectors.groupingBy(SearchBpaApplicationForm::getZone, Collectors.counting())));
        for (final Entry<String, Map<String, Long>> statusCountResMap : resultMap.entrySet()) {
            Long zone1 = 0l;
            Long zone2 = 0l;
            Long zone3 = 0l;
            Long zone4 = 0l;
            SearchBpaApplicationReport bpaApplicationReport = new SearchBpaApplicationReport();
            bpaApplicationReport.setServiceType(statusCountResMap.getKey());
            for (final Entry<String, Long> statusCountMap : statusCountResMap.getValue().entrySet()) {

                if ("ZONE-1 (MAIN OFFICE)".equalsIgnoreCase(statusCountMap.getKey())) {
                    zone1 = zone1 + statusCountMap.getValue();
                    bpaApplicationReport.setZone1N(zone1);
                } else if ("ZONE-2 (ELATHUR ZONAL OFFICE)".equalsIgnoreCase(statusCountMap.getKey())) {
                    zone2 = zone2 + statusCountMap.getValue();
                    bpaApplicationReport.setZone2(zone2);
                } else if ("ZONE-3 (BEYPORE ZONAL OFFICE)".equalsIgnoreCase(statusCountMap.getKey())) {
                    zone3 = zone3 + statusCountMap.getValue();
                    bpaApplicationReport.setZone3(zone3);
                } else if ("ZONE-4 (CHERUVANNURE NALLALAM ZONAL OFFICE)".equalsIgnoreCase(statusCountMap.getKey())) {
                    zone4 = zone4 + statusCountMap.getValue();
                    bpaApplicationReport.setZone4(statusCountMap.getValue());
                }
            }
            searchBpaApplicationReportList.add(bpaApplicationReport);
        }
        return searchBpaApplicationReportList;
    }

    public Page<SlotDetailsHelper> searchSlotDetails(final SlotDetailsHelper slotDetailsHelper, final String applicationType) {
        slotDetailsHelper.setApplicationType(applicationType);
        final Pageable pageable = new PageRequest(slotDetailsHelper.pageNumber(),
                slotDetailsHelper.pageSize(), slotDetailsHelper.orderDir(), slotDetailsHelper.orderBy());
        Page<SlotDetail> slotDetails = slotDetailRepository
                .findAll(BpaReportsSearchSpec.searchReportsSpecification(slotDetailsHelper), pageable);
        return new PageImpl<>(buildSlotDetailsResponse(slotDetails), pageable, slotDetails.getTotalElements());
    }

    private List<SlotDetailsHelper> buildSlotDetailsResponse(final Page<SlotDetail> slotDetails) {
        List<SlotDetailsHelper> slotDetailsHelperList = new ArrayList<>();
        for (SlotDetail slotDetail : slotDetails) {
            SlotDetailsHelper slotDetailsHelper = new SlotDetailsHelper();
            slotDetailsHelper.setSlotId(slotDetail.getSlot().getId());
            slotDetailsHelper.setAppointmentDate(slotDetail.getSlot().getAppointmentDate());
            slotDetailsHelper.setZone(slotDetail.getSlot().getZone().getName());
            slotDetailsHelper.setZoneId(slotDetail.getSlot().getZone().getId());
            if (slotDetail.getSlot().getElectionWard() != null) {
                slotDetailsHelper.setElectionWard(slotDetail.getSlot().getElectionWard().getName());
                slotDetailsHelper.setElectionWardId(slotDetail.getSlot().getElectionWard().getId());
            }
            slotDetailsHelper.setSlotDetailsId(slotDetail.getId());
            slotDetailsHelper.setAppointmentTime(slotDetail.getAppointmentTime());
            slotDetailsHelper.setMaxScheduledSlots(slotDetail.getMaxScheduledSlots());
            slotDetailsHelper.setMaxRescheduledSlots(slotDetail.getMaxRescheduledSlots());
            slotDetailsHelper.setUtilizedScheduledSlots(slotDetail.getUtilizedScheduledSlots());
            slotDetailsHelper.setUtilizedRescheduledSlots(slotDetail.getUtilizedRescheduledSlots());
            slotDetailsHelperList.add(slotDetailsHelper);
        }
        return slotDetailsHelperList;
    }

    public Page<BpaRegisterReportHelper> getBpaRegisterReportDetails(final SearchBpaApplicationForm searchRequest,
            final List<Long> userIds) {
        final Pageable pageable = new PageRequest(searchRequest.pageNumber(),
                searchRequest.pageSize(), searchRequest.orderDir(), searchRequest.orderBy());
        List<Long> positionIds = new ArrayList<>();
        for (Long userId : userIds) {
            Position position = getUserPositionByUserId(userId);
            if (position != null)
                positionIds.add(getUserPositionByUserId(userId).getId());
        }

        Page<BpaApplication> bpaApplications = applicationBpaRepository
                .findAll(SearchBpaApplnFormSpec.searchBpaRegisterSpecification(searchRequest, positionIds), pageable);
        return new PageImpl<>(new ArrayList<>(buildBpaRegisterReportResponse(bpaApplications, userIds)), pageable,
                bpaApplications.getTotalElements());
    }

    private Set<BpaRegisterReportHelper> buildBpaRegisterReportResponse(final Page<BpaApplication> bpaApplications,
            final List<Long> userIds) {
        Set<BpaRegisterReportHelper> bpaRegisterReportHelperList = new HashSet<>();
        for (BpaApplication application : bpaApplications) {
            /*
             * for(Long userId : userIds) { if (checkUserInWorkFlow(application, userId)) {
             */
            BpaRegisterReportHelper bpaRegister = new BpaRegisterReportHelper();
            bpaRegister.setId(application.getId());
            bpaRegister.setApplicationNumber(application.getApplicationNumber());
            bpaRegister.setApplicationType(application.getServiceType().getDescription());
            bpaRegister.setDateOfAdmission(application.getApplicationDate());
            bpaRegister.setPermitType(
                    application.getIsOneDayPermitApplication() ? ApplicationType.ONE_DAY_PERMIT.getApplicationType()
                            : ApplicationType.ALL_OTHER_SERVICES.getApplicationType());
            bpaRegister.setDateOfAdmission(application.getApplicationDate());
            bpaRegister.setApplicantName(application.getOwner().getName());
            bpaRegister.setAddress(application.getOwner().getAddress());
            for (SiteDetail siteDetail : application.getSiteDetail()) {
                bpaRegister.setSurveyNumber(siteDetail.getReSurveyNumber());
                bpaRegister.setVillage(siteDetail.getLocationBoundary().getName());
                bpaRegister.setRevenueWard(siteDetail.getAdminBoundary().getName());
                bpaRegister.setElectionWard(siteDetail.getElectionBoundary().getName());
            }
            bpaRegister.setNatureOfOccupancy(application.getOccupancy().getDescription());
            if (!application.getBuildingDetail().isEmpty()
                    && application.getBuildingDetail().get(0).getTotalPlintArea() != null) {
                bpaRegister.setNumberOfFloors(application.getBuildingDetail().get(0).getFloorCount());
                BigDecimal totalFloorArea = bpaCalculationService.getTotalFloorArea(application);
                BigDecimal existBldgFloorArea = bpaCalculationService.getExistBldgTotalFloorArea(application);
                totalFloorArea = totalFloorArea.add(existBldgFloorArea);
                bpaRegister.setTotalFloorArea(totalFloorArea.setScale(3, RoundingMode.HALF_UP));
            }

            bpaRegister.setFar(searchBpaApplicationService.getFar(application));
            bpaRegister.setApplicationFee(application.getAdmissionfeeAmount());
            if (!application.getSlotApplications().isEmpty())
                bpaRegister.setDocVerificationDate(
                        application.getSlotApplications().get(application.getSlotApplications().size() - 1)
                                .getSlotDetail().getSlot().getAppointmentDate());
            List<BpaAppointmentSchedule> appointmentScheduledList = bpaAppointmentScheduleService
                    .findByApplication(application, AppointmentSchedulePurpose.INSPECTION);
            if (!appointmentScheduledList.isEmpty())
                bpaRegister.setFieldVerificationDate(appointmentScheduledList.get(0).getAppointmentDate());
            bpaRegister.setNocDetails(getNocDetails(application));
            bpaRegister.setBuildingPermitNo(application.getPlanPermissionNumber());
            if (!application.getApplicationFee().isEmpty()) {
                BigDecimal permitFee = BigDecimal.ZERO;
                BigDecimal additionalFee = BigDecimal.ZERO;
                BigDecimal otherFee = BigDecimal.ZERO;
                for (ApplicationFeeDetail appFeeDtl : application.getApplicationFee().get(0)
                        .getApplicationFeeDetail()) {
                    if (appFeeDtl.getBpaFee().getDescription().equalsIgnoreCase(BPA_ADDITIONAL_FEE))
                        additionalFee = appFeeDtl.getAmount();
                    else if (appFeeDtl.getBpaFee().getDescription().equalsIgnoreCase(BPA_OTHER_FEE))
                        otherFee = appFeeDtl.getAmount();
                    else {
                        permitFee = permitFee.add(appFeeDtl.getAmount());
                    }
                }
                bpaRegister.setPermitFee(permitFee);
                bpaRegister.setAdditionalFee(additionalFee);
                bpaRegister.setOtherFee(otherFee);
            }
            if (application.getStatus().getCode().equalsIgnoreCase(APPLICATION_STATUS_CANCELLED)) {
                bpaRegister.setRejectionReason(bpaNoticeUtil.buildRejectionReasons(application));
                Assignment rejectedOwner;
                Assignment assignment = bpaWorkFlowService.getApproverAssignmentByDate(
                        application.getCurrentState().getPreviousOwner(), application.getState().getLastModifiedDate());
                if (application.getCurrentState().getOwnerUser() != null) {
                    List<Assignment> assignments = bpaWorkFlowService.getAssignmentByPositionAndUserAsOnDate(
                            application.getCurrentState().getOwnerPosition().getId(),
                            application.getCurrentState().getOwnerUser().getId(),
                            application.getCurrentState().getLastModifiedDate());
                    if (!assignments.isEmpty())
                        assignment = assignments.get(0);
                }

                if (assignment == null)
                    assignment = bpaWorkFlowService
                            .getAssignmentsByPositionAndDate(application.getCurrentState().getPreviousOwner().getId(),
                                    application.getState().getLastModifiedDate())
                            .get(0);

                User lastModifiedBy = application.getCurrentState().getLastModifiedBy();
                if (lastModifiedBy.getName().equalsIgnoreCase("System")) {
                    bpaRegister.setApprvd_rejected_by(lastModifiedBy.getName().concat(" :: ").concat(lastModifiedBy.getName()));
                } else {
                    if (assignment != null && assignment.getDesignation().getName().equalsIgnoreCase(SECTION_CLERK)) {
                        rejectedOwner = bpaWorkFlowService.getApproverAssignmentByDate(
                                application.getCurrentState().getOwnerPosition(), application.getState().getLastModifiedDate());
                        if (application.getCurrentState().getOwnerUser() != null) {
                            List<Assignment> assignments = bpaWorkFlowService.getAssignmentByPositionAndUserAsOnDate(
                                    application.getCurrentState().getOwnerPosition().getId(),
                                    application.getCurrentState().getOwnerUser().getId(),
                                    application.getCurrentState().getLastModifiedDate());
                            if (!assignments.isEmpty())
                                rejectedOwner = assignments.get(0);
                        }
                        if (rejectedOwner == null)
                            rejectedOwner = bpaWorkFlowService
                                    .getAssignmentsByPositionAndDate(application.getCurrentState().getOwnerPosition().getId(),
                                            application.getState().getLastModifiedDate())
                                    .get(0);
                    } else {
                        rejectedOwner = assignment;
                    }
                    bpaRegister.setApprvd_rejected_by(rejectedOwner == null ? "N/A"
                            : rejectedOwner.getEmployee().getName().concat(" :: ")
                                    .concat(rejectedOwner.getDesignation().getName()));
                }
            } else {
                bpaRegister.setFinalApprovalDate(application.getPlanPermissionDate());
                bpaRegister.setApprvd_rejected_by(bpaNoticeUtil.getApproverName(application).concat(" :: ").concat(bpaNoticeUtil
                        .getApproverDesignation(bpaWorkFlowService.getAmountRuleByServiceType(application).intValue())));
            }
            bpaRegisterReportHelperList.add(bpaRegister);
            /*
             * } }
             */
        }
        return bpaRegisterReportHelperList;
    }

    private String getNocDetails(final BpaApplication application) {
        StringBuilder nocDetails = new StringBuilder();
        int additionalOrder = 1;
        for (ApplicationNocDocument nocDoc : application.getApplicationNOCDocument()) {
            nocDetails.append(String.valueOf(additionalOrder) + ") " + nocDoc.getChecklist().getDescription() + " : "
                    + (nocDoc.getNocStatus() != null ? nocDoc.getNocStatus().getNocStatusVal() : "N/A") + "\n");
            additionalOrder++;
        }
        return nocDetails.toString();
    }

    public Boolean checkUserInWorkFlow(final BpaApplication application, final Long userId) {
        final State<Position> workflowState = application.getState();
        if (null != workflowState) {
            if (null != application.getStateHistory() && !application.getStateHistory().isEmpty())
                Collections.reverse(application.getStateHistory());
            Position userPosition = getUserPositionByUserId(userId);
            for (final StateHistory<Position> stateHistory : application.getStateHistory()) {
                Position owner = stateHistory.getOwnerPosition();
                if (userPosition != null && userPosition.getId().equals(owner.getId()))
                    return true;
            }
        }
        return false;
    }

    public Boolean checkApplicationPresentInInbox(final BpaApplication application, final Long userId) {
        final State<Position> workflowState = application.getState();
        if (null != workflowState) {
            Position userPosition = getUserPositionByUserId(userId);
            Position owner = workflowState.getOwnerPosition();
            if (userPosition != null && userPosition.getId().equals(owner.getId()))
                return true;
        }
        return false;
    }

    public Position getUserPositionByUserId(Long userId) {
        return positionMasterService.getPositionByUserId(userId);
    }
}