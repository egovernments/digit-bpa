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
package org.egov.bpa.transaction.service;


import static org.egov.bpa.utils.BpaConstants.APPLICATION_TYPE_ONEDAYPERMIT;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_TYPE_REGULAR;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.egov.bpa.master.entity.ApplicationSubType;
import org.egov.bpa.master.entity.SlotMapping;
import org.egov.bpa.master.service.ApplicationSubTypeService;
import org.egov.bpa.master.service.SlotMappingService;
import org.egov.bpa.service.es.BpaIndexService;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.Slot;
import org.egov.bpa.transaction.entity.SlotApplication;
import org.egov.bpa.transaction.entity.SlotDetail;
import org.egov.bpa.transaction.entity.enums.ScheduleAppointmentType;
import org.egov.bpa.transaction.repository.BpaStatusRepository;
import org.egov.bpa.transaction.repository.SlotApplicationRepository;
import org.egov.bpa.transaction.repository.SlotDetailRepository;
import org.egov.bpa.transaction.repository.SlotRepository;
import org.egov.bpa.transaction.service.messaging.BPASmsAndEmailService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.admin.master.repository.BoundaryRepository;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Service
@Transactional(readOnly = true)
public class ScheduleAppointmentForDocumentScrutinyService {
    private static final String MODULE_NAME = "BPA";
    private static final String GAP_FOR_SCHEDULING_CONFIG_KEY = "GAPFORSCHEDULING";

    private static final Logger LOGGER = Logger.getLogger(ScheduleAppointmentForDocumentScrutinyService.class);
    private static final String GAP_FOR_SCHEDULING_CONFIG_KEY_ONE_DAY_PERMIT = "GAPFORSCHEDULINGONEDAYPERMITAPPLICATIONS";

    @Autowired
    private SlotMappingService slotMappingService;
    @Autowired
    private BpaStatusRepository bpaStatusRepository;
    @Autowired
    private ApplicationBpaService applicationBpaService;
    @Autowired
    private SlotRepository slotRepository;
    @Autowired
    private SlotApplicationService slotApplicationService;
    @Autowired
    private SlotDetailRepository slotDetailRepository;
    @Autowired
    private BPASmsAndEmailService bpaSmsAndEmailService;
    @Autowired
    private BoundaryRepository boundaryRepository;
    @Autowired
    private BpaUtils bpaUtils;
    @Autowired
    private SlotApplicationRepository slotApplicationRepository;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private AppConfigValueService appConfigValuesService;
    @Autowired
    private BpaIndexService bpaIndexService;
    @Autowired
    private ApplicationSubTypeService applicationTypeService;

    public void scheduleScrutiny() {
        Calendar calendar = Calendar.getInstance();
        String noOfDays = getGapForSchedulingForNormalApplications();
        calendar.add(Calendar.DAY_OF_YEAR, Integer.valueOf(noOfDays));
    	ApplicationSubType appType = applicationTypeService.findByName(APPLICATION_TYPE_REGULAR.toUpperCase());
        List<Boundary> zonesList = slotMappingService.slotfindZoneByApplType(appType);
        for (Boundary bndry : zonesList) {
            List<Slot> slotList = new ArrayList<>();//slotRepository.findByZoneAndApplicationDate(bndry.getId(), calendar.getTime());
            if (!slotList.isEmpty()) {
                for (Slot slot : slotList) {
                    List<SlotDetail> slotDetailList = slotDetailRepository.findBySlot(slot);
                    slot.setSlotDetail(slotDetailList);
                    if (!slot.getSlotDetail().isEmpty()) {
                        List<BpaApplication> bpaApplications = getNormalBpaApplicationsBySlotsStatusAndBoundary(slot);
                        if (!bpaApplications.isEmpty()) {
                            Map<Long, SlotApplication> processedApplication = new HashMap<>();
                            Map<Long,String> errorMsg = new ConcurrentHashMap<>();
                            for (BpaApplication bpaApp : bpaApplications) {
                                if (LOGGER.isInfoEnabled()) {
                                    LOGGER.info(
                                            "******************Application Number ------>>>>>>" + bpaApp.getApplicationNumber());
                                }
                                try {
                                    TransactionTemplate template = new TransactionTemplate(
                                            transactionTemplate.getTransactionManager());
                                    template.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                                    template.execute(result -> {
                                        try {
                                            if (LOGGER.isInfoEnabled())
                                                LOGGER.info(
                                                        "****************** Schedule appointment  Transaction start *****************");
                                            processSchedulingForNormalApplications(slot, processedApplication, bpaApp);
                                            if (LOGGER.isInfoEnabled())
                                                LOGGER.info(
                                                        "****************** Schedule appointment Transaction End *****************");
                                        } catch (Exception e) {
                                            errorMsg.put(bpaApp.getId(), getErrorMessage(e));
                                            throw e;
                                        }
                                        return true;
                                    });
                                } catch (Exception e) {
                                    LOGGER.error(e.getMessage(), e);
                                }
                            }
                            if (processedApplication.size() > 0) {
                                sendSmsAndEmailForSuccessfulApplications(processedApplication, errorMsg);
                            }
                            if (!errorMsg.isEmpty()) {
                                setFailureInSchedulerTrueForFailedApplications(errorMsg);
                            }
                        }
                    }
                }
            }
        }
    }

    private List<BpaApplication> getNormalBpaApplicationsBySlotsStatusAndBoundary(Slot slot) {
        Integer totalAvailableSlots = getTotalAvailableSlotsForNormalApplications(slot);
        BpaStatus bpaStatusPendingForRescheduling = bpaStatusRepository
                .findByCode(BpaConstants.APPLICATION_STATUS_PENDING_FOR_RESCHEDULING);
        BpaStatus bpaStatusRegistered = bpaStatusRepository
                .findByCode(BpaConstants.APPLICATION_STATUS_REGISTERED);
        List<BpaStatus> bpaStatusList = new ArrayList<>();
        bpaStatusList.add(bpaStatusRegistered);
        bpaStatusList.add(bpaStatusPendingForRescheduling);
        List<Boundary> boundaryList = boundaryRepository
                .findActiveImmediateChildrenWithOutParent(slot.getZone().getId());
        return applicationBpaService
                .getBpaApplicationsForScheduleAndReSchedule(bpaStatusList, boundaryList, totalAvailableSlots);

    }

    private void processSchedulingForNormalApplications(Slot slot, Map<Long, SlotApplication> processedApplication,
            BpaApplication bpaApp) {
        for (SlotDetail slotDetail : slot.getSlotDetail()) {
            slotDetail = slotDetailRepository.findOne(slotDetail.getId());
            if (bpaApp.getStatus().getCode()
                    .equals(BpaConstants.APPLICATION_STATUS_PENDING_FOR_RESCHEDULING)) {
                List<SlotApplication> slotApplications = slotApplicationRepository
                        .findByApplicationOrderByIdDesc(bpaApp);
                slotApplications.get(0).setActive(false);
                slotApplicationRepository.save(slotApplications.get(0));
                Date appointmentDate = slotApplications.get(0).getSlotDetail().getSlot()
                        .getAppointmentDate();
                if (slotDetail.getSlot().getAppointmentDate()
                        .compareTo(appointmentDate) > 0) {
                    if (slotDetail.getMaxRescheduledSlots()
                            - slotDetail.getUtilizedRescheduledSlots() > 0) {
                        slotDetail.setUtilizedRescheduledSlots(
                                slotDetail.getUtilizedRescheduledSlots() + 1);
                        SlotApplication slotApplication = buildSlotApplicationObject(bpaApp,
                                slotDetail);
                        createSlotApplicationAndUpdateStatus(bpaApp, slotApplication);
                        processedApplication.put(bpaApp.getId(), slotApplication);
                        break;
                    } else if (slotDetail.getMaxScheduledSlots()
                            - slotDetail.getUtilizedScheduledSlots() > 0) {
                        processSchedulingIfScheduledSlotsAreAvailable(processedApplication, bpaApp, slotDetail);
                        break;
                    }
                }
            } else {
                if (LOGGER.isInfoEnabled())
                    LOGGER.info(
                            "**Inside Transaction --- Regular Application schedule start ------>>>>>>");
                if (slotDetail.getMaxScheduledSlots()
                        - slotDetail.getUtilizedScheduledSlots() > 0) {
                    processSchedulingIfScheduledSlotsAreAvailable(processedApplication, bpaApp, slotDetail);
                    if (LOGGER.isInfoEnabled())
                        LOGGER.info(
                                "**Inside Transaction --- Regular Application schedule end ------>>>>>>");
                    break;
                }
            }
        }
    }

    private void processSchedulingIfScheduledSlotsAreAvailable(Map<Long, SlotApplication> processedApplication,
            BpaApplication bpaApp, SlotDetail slotDetail) {
        slotDetail.setUtilizedScheduledSlots(
                slotDetail.getUtilizedScheduledSlots() + 1);
        SlotApplication slotApplication = buildSlotApplicationObject(bpaApp,
                slotDetail);
        createSlotApplicationAndUpdateStatus(bpaApp,
                slotApplication);
        processedApplication.put(bpaApp.getId(), slotApplication);
    }

    private Integer getTotalAvailableSlotsForNormalApplications(Slot slot) {
        Integer totalAvailableSlots = 0;
        for (SlotDetail slotDetail : slot.getSlotDetail()) {
            Integer diffScheduledSlots = 0;
            Integer diffRescheduledSlots = 0;
            if (slotDetail.getMaxScheduledSlots() > slotDetail.getUtilizedScheduledSlots())
                diffScheduledSlots = slotDetail.getMaxScheduledSlots()
                        - slotDetail.getUtilizedScheduledSlots();
            if (slotDetail.getMaxRescheduledSlots() > slotDetail.getUtilizedRescheduledSlots())
                diffRescheduledSlots = slotDetail.getMaxRescheduledSlots()
                        - slotDetail.getUtilizedRescheduledSlots();
            totalAvailableSlots = totalAvailableSlots + diffScheduledSlots + diffRescheduledSlots;
        }
        return totalAvailableSlots;
    }

    private String getGapForSchedulingForNormalApplications() {
        List<AppConfigValues> appConfigValue = appConfigValuesService.getConfigValuesByModuleAndKey(MODULE_NAME,
                GAP_FOR_SCHEDULING_CONFIG_KEY);
        return appConfigValue.get(0).getValue();
    }

    private SlotApplication buildSlotApplicationObject(BpaApplication bpaApp, SlotDetail slotDetail) {
        SlotApplication slotApplication = new SlotApplication();
        slotApplication.setActive(true);
        BpaApplication bpaApplication = applicationBpaService.findByApplicationNumber(bpaApp.getApplicationNumber());
        if (bpaApplication != null)
            slotApplication.setApplication(bpaApplication);
        else
            slotApplication.setApplication(bpaApp);
        slotApplication.setSlotDetail(slotDetail);
        if (bpaApp.getStatus().getCode().equals(BpaConstants.APPLICATION_STATUS_PENDING_FOR_RESCHEDULING)) {
            slotApplication.setScheduleAppointmentType(ScheduleAppointmentType.RESCHEDULE);
            bpaApp.setStatus(bpaStatusRepository.findByCode(BpaConstants.APPLICATION_STATUS_RESCHEDULED));
            return slotApplication;
        } else {
            slotApplication.setScheduleAppointmentType(ScheduleAppointmentType.SCHEDULE);
            bpaApp.setStatus(bpaStatusRepository.findByCode(BpaConstants.APPLICATION_STATUS_SCHEDULED));
            return slotApplication;
        }

    }

    private void createSlotApplicationAndUpdateStatus(BpaApplication bpaApp,
            SlotApplication slotApplication) {
        if (LOGGER.isInfoEnabled())
            LOGGER.info("******************Inside Transaction --- Before slotApplication Save ******************************"
                    + slotApplication);
        slotApplicationService.saveApplicationForScheduler(slotApplication);
        if (LOGGER.isInfoEnabled())
            LOGGER.info("******************Inside Transaction --- After slotApplication Save ******************************"
                    + slotApplication);
        if (LOGGER.isInfoEnabled())
            LOGGER.info("******************Inside Transaction --- Before Bpa Application Save ******************************"
                    + bpaApp);
        applicationBpaService.saveApplicationForScheduler(bpaApp);
        if (LOGGER.isInfoEnabled())
            LOGGER.info("******************Inside Transaction --- After Bpa Application Save ******************************"
                    + bpaApp);
        if (LOGGER.isInfoEnabled())
            LOGGER.info("****************** Schedule Appointment Type ******************************"
                    + slotApplication.getScheduleAppointmentType().name());
        if (slotApplication.getScheduleAppointmentType().toString()
                .equals(ScheduleAppointmentType.RESCHEDULE.toString())) {
            bpaUtils.redirectToBpaWorkFlowForScheduler(
                    slotApplication.getApplication().getCurrentState().getOwnerPosition().getId(),
                    slotApplication.getApplication(), null,
                    "document scrutiny re-scheduled", BpaConstants.WF_RESCHDLE_APPMNT_BUTTON, null);
            bpaIndexService.updateIndexes(bpaApp);
        } else if (slotApplication.getScheduleAppointmentType().toString()
                .equals(ScheduleAppointmentType.SCHEDULE.toString())) {
            if (LOGGER.isInfoEnabled())
                LOGGER.info("******************Start workflow - Schedule Appointment******************************");
            bpaUtils.redirectToBpaWorkFlowForScheduler(
                    slotApplication.getApplication().getCurrentState().getOwnerPosition().getId(),
                    slotApplication.getApplication(), null, BpaConstants.APPLICATION_STATUS_SCHEDULED, "Forward", null);
            bpaIndexService.updateIndexes(bpaApp);
            if (LOGGER.isInfoEnabled())
                LOGGER.info("******************End workflow - Schedule Appointment******************************");
        }

    }

    private String getErrorMessage(final Exception exception) {
        StringBuilder msg = new StringBuilder();
        msg.append(exception.toString()).append(' ');
        int i = 0;
        for (StackTraceElement element : exception.getStackTrace()) {
            i++;
            if (i > 1) {
                break;
            }
            msg.append(element.toString());
        }
        return msg.toString();
    }

    public void scheduleOneDayPermitApplicationsForDocumentScrutiny(BpaApplication bpaApplication, SlotDetail slotDetail) {
        SlotApplication slotApplication = buildSlotApplicationObject(bpaApplication, slotDetail);
        slotDetail.setUtilizedScheduledSlots(
                slotDetail.getUtilizedScheduledSlots() + 1);
        slotApplicationService.save(slotApplication);
        applicationBpaService.saveBpaApplication(bpaApplication);
        bpaSmsAndEmailService.sendSMSAndEmailForDocumentScrutiny(slotApplication);
    }

    public void scheduleOneDayPermit() {
        Calendar calendar = Calendar.getInstance();
        String noOfDays = getGapForSchedulingForOneDayPermitApplications();
        calendar.add(Calendar.DAY_OF_YEAR, Integer.valueOf(noOfDays));
    	ApplicationSubType appType = applicationTypeService.findByName(APPLICATION_TYPE_ONEDAYPERMIT.toUpperCase());
        List<SlotMapping> slotMappings = slotMappingService.slotMappingForOneDayPermit(appType);
        for (SlotMapping sltMapping : slotMappings) {
            List<Slot> slotList = new ArrayList<>();//slotRepository.findByZoneWardAndApplicationDateForOneDayPermit(sltMapping.getZone(),sltMapping.getElectionWard(), calendar.getTime());
            if (!slotList.isEmpty()) {
                for (Slot slot : slotList) {
                    List<SlotDetail> slotDetailList = slotDetailRepository.findBySlotForOneDayPermit(slot);
                    slot.setSlotDetail(slotDetailList);
                    if (!slot.getSlotDetail().isEmpty()) {
                        Integer totalAvailableSlots = getTotalAvailableSlotsForParticularSlot(slot);
                        List<BpaApplication> bpaApplications = getBpaApplicationsByStatusSlotsAndBoundary(sltMapping.getZone(),sltMapping.getElectionWard(),
                                totalAvailableSlots);
                        if (!bpaApplications.isEmpty()) {
                            Map<Long, SlotApplication> processedApplication = new HashMap<>();
                            Map<Long,String> errorMsg = new ConcurrentHashMap<>();
                            for (BpaApplication bpaApp : bpaApplications) {
                                try {
                                    TransactionTemplate template = new TransactionTemplate(
                                            transactionTemplate.getTransactionManager());
                                    template.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                                    template.execute(result -> {
                                        try {
                                            if (LOGGER.isInfoEnabled())
                                                LOGGER.info(
                                                        "****************** Schedule appointment  Transaction start for one day permit applications *****************");
                                            processSchedulingForOneDayPermitApplications(slot, processedApplication, bpaApp);
                                            if (LOGGER.isInfoEnabled())
                                                LOGGER.info(
                                                        "****************** Schedule appointment Transaction End For One Day Permit Applications*****************");
                                        } catch (Exception e) {
                                            errorMsg.put(bpaApp.getId(), getErrorMessage(e));
                                            throw e;
                                        }
                                        return true;
                                    });
                                    if (LOGGER.isInfoEnabled())
                                        LOGGER.info("****************** Outside Transaction Template *****************");
                                } catch (Exception e) {
                                    LOGGER.error(e.getMessage(), e);
                                }
                            }
                            if (processedApplication.size() > 0) {
                                sendSmsAndEmailForSuccessfulApplications(processedApplication, errorMsg);
                            }
                            if (!errorMsg.isEmpty()) {
                                setFailureInSchedulerTrueForFailedApplications(errorMsg);
                            }
                        }
                    }
                }
            }
        }
    }

    private void processSchedulingForOneDayPermitApplications(Slot slot, Map<Long, SlotApplication> processedApplication,
            BpaApplication bpaApp) {
        for (SlotDetail slotDetail : slot.getSlotDetail()) {
            slotDetail = slotDetailRepository.findOne(slotDetail.getId());
            if (LOGGER.isInfoEnabled())
                LOGGER.info(
                        "******************Inside Transaction --- Slot Details List Size ------>>>>>>"
                                + slot.getSlotDetail().size());
            if (bpaApp.getStatus().getCode()
                    .equals(BpaConstants.APPLICATION_STATUS_REGISTERED)) {
                if (LOGGER.isInfoEnabled())
                    LOGGER.info(
                            "**Inside Transaction --- One Day Permit Application schedule start ------>>>>>>");
                if (slotDetail.getMaxScheduledSlots()
                        - slotDetail.getUtilizedScheduledSlots() > 0) {
                    processSchedulingIfScheduledSlotsAreAvailable(processedApplication, bpaApp, slotDetail);
                    if (LOGGER.isInfoEnabled())
                        LOGGER.info(
                                "**Inside Transaction --- One Day Permit Application schedule end ------>>>>>>");
                    break;
                }
            }
        }
    }

    private Integer getTotalAvailableSlotsForParticularSlot(Slot slot) {
        Integer totalAvailableSlots = 0;
        for (SlotDetail slotDetail : slot.getSlotDetail()) {
            Integer diffScheduledSlots = 0;
            if (slotDetail.getMaxScheduledSlots() > slotDetail.getUtilizedScheduledSlots())
                diffScheduledSlots = slotDetail.getMaxScheduledSlots()
                        - slotDetail.getUtilizedScheduledSlots();
            totalAvailableSlots = totalAvailableSlots + diffScheduledSlots;
        }
        return totalAvailableSlots;
    }

    private List<BpaApplication> getBpaApplicationsByStatusSlotsAndBoundary(Boundary bndry,Boundary ward, Integer totalAvailableSlots) {
        BpaStatus bpaStatusRegistered = bpaStatusRepository
                .findByCode(BpaConstants.APPLICATION_STATUS_REGISTERED);
        List<Boundary> boundaryList = boundaryRepository
                .findActiveImmediateChildrenWithOutParent(bndry.getId());
        return applicationBpaService
                .getOneDayPermitAppForAppointment(bpaStatusRegistered,ward, boundaryList, totalAvailableSlots);
    }

    private String getGapForSchedulingForOneDayPermitApplications() {
        List<AppConfigValues> appConfigValue = appConfigValuesService.getConfigValuesByModuleAndKey(MODULE_NAME,
                GAP_FOR_SCHEDULING_CONFIG_KEY_ONE_DAY_PERMIT);
        return appConfigValue.get(0).getValue();
    }

    private void sendSmsAndEmailForSuccessfulApplications(Map<Long, SlotApplication> processedApplication,
            Map<Long,String> errorMsg) {
        for (Entry<Long, SlotApplication> application : processedApplication.entrySet()) {
            if (errorMsg.containsKey(application.getKey()))
                continue;
            else {
                transactionTemplate.execute(result1 -> {
                    BpaApplication bpaApplication = applicationBpaService.findById(application.getKey());
                    if (application.getValue() != null) {
                        SlotApplication slotApplication = slotApplicationService
                                .findById(application.getValue().getId());
                        if (slotApplication != null && bpaApplication != null)
                            bpaSmsAndEmailService.sendSMSAndEmailForDocumentScrutiny(slotApplication);
                    }
                    return Boolean.TRUE;
                });
            }
        }
    }

    private void setFailureInSchedulerTrueForFailedApplications(Map<Long,String> errorMsg) {
        for (Entry<Long, String> errMsg : errorMsg.entrySet()) {
            transactionTemplate.execute(result1 -> {
                BpaApplication bpaApplication = applicationBpaService
                        .findById(errMsg.getKey());
                bpaApplication.setFailureInScheduler(Boolean.TRUE);
                bpaApplication.setSchedulerFailedRemarks(errMsg.getValue());
                applicationBpaService.saveApplicationForScheduler(bpaApplication);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.error("Exception in document schedule Generation " + bpaApplication.getId());
                }
                return Boolean.FALSE;
            });
        }
    }
}