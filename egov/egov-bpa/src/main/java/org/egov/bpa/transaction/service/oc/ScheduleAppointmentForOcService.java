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
package org.egov.bpa.transaction.service.oc;

import org.apache.log4j.Logger;
import org.egov.bpa.master.entity.enums.SlotMappingApplType;
import org.egov.bpa.master.service.SlotMappingService;
import org.egov.bpa.service.es.OccupancyCertificateIndexService;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.Slot;
import org.egov.bpa.transaction.entity.SlotDetail;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.entity.enums.ScheduleAppointmentType;
import org.egov.bpa.transaction.entity.oc.OCSlot;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.repository.BpaStatusRepository;
import org.egov.bpa.transaction.repository.OcSlotRepository;
import org.egov.bpa.transaction.repository.SlotDetailRepository;
import org.egov.bpa.transaction.repository.SlotRepository;
import org.egov.bpa.transaction.service.messaging.oc.OcSmsAndEmailService;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional(readOnly = true)
public class ScheduleAppointmentForOcService {
    private static final Logger LOGGER = Logger.getLogger(ScheduleAppointmentForOcService.class);


    private static final String MODULE_NAME = "BPA";
    private static final String GAP_FOR_SCHEDULING_CONFIG_KEY = "GAPFORSCHEDULINGOC";

    @Autowired
    private SlotMappingService slotMappingService;
    @Autowired
    private BpaStatusRepository bpaStatusRepository;
    @Autowired
    private SlotRepository slotRepository;
    @Autowired
    private SlotDetailRepository slotDetailRepository;
    @Autowired
    private OcSmsAndEmailService ocSmsAndEmailService;
    @Autowired
    private BoundaryRepository boundaryRepository;
    @Autowired
    private BpaUtils bpaUtils;
    @Autowired
    private OcSlotRepository ocSlotRepository;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private AppConfigValueService appConfigValuesService;
    @Autowired
    private OccupancyCertificateService occupancyCertificateService;
    @Autowired
    private OcSlotService ocSlotService;
    @Autowired
    private OccupancyCertificateIndexService occupancyCertificateIndexService;


    @Transactional
    public void scheduleScrutiny() {
        Calendar calendar = Calendar.getInstance();
        String noOfDays = getGapForSchedulingForOCApplications();
        calendar.add(Calendar.DAY_OF_YEAR, Integer.valueOf(noOfDays));
        List<Boundary> zonesList = slotMappingService.slotfindZoneByApplType(SlotMappingApplType.OCCUPANCY_CERTIFICATE);
        for (Boundary bndry : zonesList) {
            List<Slot> slotList = slotRepository.findByZoneApplicationDateForOc(bndry, calendar.getTime());
            if (!slotList.isEmpty()) {
                for (Slot slot : slotList) {
                    List<SlotDetail> slotDetailList = slotDetailRepository.findBySlot(slot);
                    slot.setSlotDetail(slotDetailList);
                    if (!slot.getSlotDetail().isEmpty()) {
                        List<OccupancyCertificate> occupancyCertificates = getOCApplicationsBySlotsStatusAndBoundary(slot);
                        if (!occupancyCertificates.isEmpty()) {
                            Map<Long, OCSlot> processedApplication = new HashMap<>();
                            Map<Long, String> errorMsg = new ConcurrentHashMap<>();
                            for (OccupancyCertificate occupancyCertificate : occupancyCertificates) {
                                if (LOGGER.isInfoEnabled()) {
                                    LOGGER.info(
                                            "******************Application Number ------>>>>>>" + occupancyCertificate.getApplicationNumber());
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
                                            processSchedulingForOcApplications(slot, processedApplication, occupancyCertificate);
                                            if (LOGGER.isInfoEnabled())
                                                LOGGER.info(
                                                        "****************** Schedule appointment Transaction End *****************");
                                        } catch (Exception e) {
                                            errorMsg.put(occupancyCertificate.getId(), getErrorMessage(e));
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

    private List<OccupancyCertificate> getOCApplicationsBySlotsStatusAndBoundary(Slot slot) {
        Integer totalAvailableSlots = getTotalAvailableSlotsForOCApplications(slot);
        BpaStatus bpaStatusPendingForRescheduling = bpaStatusRepository
                .findByCode(BpaConstants.APPLICATION_STATUS_PENDING_FOR_RESCHEDULING);
        BpaStatus bpaStatusRegistered = bpaStatusRepository
                .findByCode(BpaConstants.APPLICATION_STATUS_REGISTERED);
        List<BpaStatus> bpaStatusList = new ArrayList<>();
        bpaStatusList.add(bpaStatusRegistered);
        bpaStatusList.add(bpaStatusPendingForRescheduling);
        List<Boundary> boundaryList = boundaryRepository
                .findActiveImmediateChildrenWithOutParent(slot.getZone().getId());
        return occupancyCertificateService
                .getOcApplicationsForScheduleAndReSchedule(bpaStatusList, boundaryList, totalAvailableSlots);

    }

    private void processSchedulingForOcApplications(Slot slot, Map<Long, OCSlot> processedApplication,
                                                    OccupancyCertificate occupancyCertificate) {
        for (SlotDetail slotDetail : slot.getSlotDetail()) {
            slotDetail = slotDetailRepository.findOne(slotDetail.getId());
            if (occupancyCertificate.getStatus().getCode()
                    .equals(BpaConstants.APPLICATION_STATUS_PENDING_FOR_RESCHEDULING)) {
                List<OCSlot> ocSlots = ocSlotRepository
                        .findByOcOrderByIdDesc(occupancyCertificate);
                ocSlots.get(0).setActive(false);
                ocSlotRepository.save(ocSlots.get(0));
                Date appointmentDate = ocSlots.get(0).getSlotDetail().getSlot()
                        .getAppointmentDate();
                if (slotDetail.getSlot().getAppointmentDate()
                        .compareTo(appointmentDate) > 0) {
                    if (slotDetail.getMaxRescheduledSlots()
                            - slotDetail.getUtilizedRescheduledSlots() > 0) {
                        slotDetail.setUtilizedRescheduledSlots(
                                slotDetail.getUtilizedRescheduledSlots() + 1);
                        OCSlot ocSlot = buildOcSlotObject(occupancyCertificate,
                                slotDetail);
                        createOcSlotAndUpdateStatus(occupancyCertificate, ocSlot);
                        processedApplication.put(occupancyCertificate.getId(), ocSlot);
                        break;
                    } else if (slotDetail.getMaxScheduledSlots()
                            - slotDetail.getUtilizedScheduledSlots() > 0) {
                        processSchedulingIfScheduledSlotsAreAvailable(processedApplication, occupancyCertificate, slotDetail);
                        break;
                    }
                }
            } else {
                if (LOGGER.isInfoEnabled())
                    LOGGER.info(
                            "**Inside Transaction --- oc Application schedule start ------>>>>>>");
                if (slotDetail.getMaxScheduledSlots()
                        - slotDetail.getUtilizedScheduledSlots() > 0) {
                    processSchedulingIfScheduledSlotsAreAvailable(processedApplication, occupancyCertificate, slotDetail);
                    if (LOGGER.isInfoEnabled())
                        LOGGER.info(
                                "**Inside Transaction --- oc Application schedule end ------>>>>>>");
                    break;
                }
            }
        }
    }

    private void processSchedulingIfScheduledSlotsAreAvailable(Map<Long, OCSlot> processedApplication,
                                                               OccupancyCertificate occupancyCertificate, SlotDetail slotDetail) {
        slotDetail.setUtilizedScheduledSlots(
                slotDetail.getUtilizedScheduledSlots() + 1);
        OCSlot ocSlot = buildOcSlotObject(occupancyCertificate,
                slotDetail);
        createOcSlotAndUpdateStatus(occupancyCertificate,
                ocSlot);
        processedApplication.put(occupancyCertificate.getId(), ocSlot);
    }

    private Integer getTotalAvailableSlotsForOCApplications(Slot slot) {
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

    private String getGapForSchedulingForOCApplications() {
        List<AppConfigValues> appConfigValue = appConfigValuesService.getConfigValuesByModuleAndKey(MODULE_NAME,
                GAP_FOR_SCHEDULING_CONFIG_KEY);
        return appConfigValue.get(0).getValue();
    }

    private OCSlot buildOcSlotObject(OccupancyCertificate occupancyCertificate, SlotDetail slotDetail) {
        OCSlot ocSlot = new OCSlot();
        ocSlot.setActive(true);
        OccupancyCertificate occupancyCrtfct = occupancyCertificateService.findByApplicationNumber(occupancyCertificate.getApplicationNumber());
        if (occupancyCrtfct != null)
            ocSlot.setOc(occupancyCrtfct);
        else
            ocSlot.setOc(occupancyCertificate);
        ocSlot.setSlotDetail(slotDetail);
        if (occupancyCertificate.getStatus().getCode().equals(BpaConstants.APPLICATION_STATUS_PENDING_FOR_RESCHEDULING)) {
            ocSlot.setScheduleAppointmentType(ScheduleAppointmentType.RESCHEDULE);
            occupancyCertificate.setStatus(bpaStatusRepository.findByCode(BpaConstants.APPLICATION_STATUS_RESCHEDULED));
            return ocSlot;
        } else {
            ocSlot.setScheduleAppointmentType(ScheduleAppointmentType.SCHEDULE);
            occupancyCertificate.setStatus(bpaStatusRepository.findByCode(BpaConstants.APPLICATION_STATUS_SCHEDULED));
            return ocSlot;
        }

    }

    private void createOcSlotAndUpdateStatus(OccupancyCertificate occupancyCertificate,
                                             OCSlot ocSlot) {
        if (LOGGER.isInfoEnabled())
            LOGGER.info("******************Inside Transaction --- Before ocslot Save ******************************"
                    + ocSlot);
        ocSlotService.saveOcSlotForScheduler(ocSlot);
        if (LOGGER.isInfoEnabled())
            LOGGER.info("******************Inside Transaction --- After ocslot Save ******************************"
                    + ocSlot);
        if (LOGGER.isInfoEnabled())
            LOGGER.info("******************Inside Transaction --- Before Oc Application Save ******************************"
                    + occupancyCertificate);
        occupancyCertificateService.saveOccupancyCertificate(occupancyCertificate);
        if (LOGGER.isInfoEnabled())
            LOGGER.info("******************Inside Transaction --- After Oc Application Save ******************************"
                    + occupancyCertificate);
        if (LOGGER.isInfoEnabled())
            LOGGER.info("****************** Schedule Appointment Type ******************************"
                    + ocSlot.getScheduleAppointmentType().name());
        if (ocSlot.getScheduleAppointmentType().toString()
                .equals(ScheduleAppointmentType.RESCHEDULE.toString())) {
            WorkflowBean workflowBean = new WorkflowBean();
            workflowBean.setApproverPositionId(ocSlot.getOc().getCurrentState().getOwnerPosition().getId());
            workflowBean.setCurrentState(null);
            workflowBean.setApproverComments("document scrutiny re-scheduled");
            workflowBean.setWorkFlowAction(BpaConstants.WF_RESCHDLE_APPMNT_BUTTON);
            workflowBean.setAmountRule(null);
            bpaUtils.redirectToBpaWorkFlowForOC(ocSlot.getOc(), workflowBean);
            occupancyCertificateIndexService.updateOccupancyIndex(occupancyCertificate);
        } else if (ocSlot.getScheduleAppointmentType().toString()
                .equals(ScheduleAppointmentType.SCHEDULE.toString())) {
            if (LOGGER.isInfoEnabled())
                LOGGER.info("******************Start workflow - Schedule Appointment******************************");
            WorkflowBean workflowBean = new WorkflowBean();
            workflowBean.setApproverPositionId(ocSlot.getOc().getCurrentState().getOwnerPosition().getId());
            workflowBean.setCurrentState(null);
            workflowBean.setApproverComments(BpaConstants.APPLICATION_STATUS_SCHEDULED);
            workflowBean.setWorkFlowAction("Forward");
            workflowBean.setAmountRule(null);
            bpaUtils.redirectToBpaWorkFlowForOC(ocSlot.getOc(), workflowBean);
            occupancyCertificateIndexService.updateOccupancyIndex(occupancyCertificate);
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

    private void sendSmsAndEmailForSuccessfulApplications(Map<Long, OCSlot> processedApplication,
                                                          Map<Long, String> errorMsg) {
        for (Map.Entry<Long, OCSlot> application : processedApplication.entrySet()) {
            if (errorMsg.containsKey(application.getKey()))
                continue;
            else {
                transactionTemplate.execute(result1 -> {
                    OccupancyCertificate occupancyCertificate = occupancyCertificateService.findById(application.getKey());
                    if (application.getValue() != null) {
                        OCSlot ocSlot = ocSlotService
                                .findById(application.getValue().getId());
                        if (ocSlot != null && occupancyCertificate != null)
                            ocSmsAndEmailService.sendSMSAndEmailForOcDocumentScrutiny(ocSlot);
                    }
                    return Boolean.TRUE;
                });
            }
        }
    }

    private void setFailureInSchedulerTrueForFailedApplications(Map<Long, String> errorMsg) {
        for (Map.Entry<Long, String> errMsg : errorMsg.entrySet()) {
            transactionTemplate.execute(result1 -> {
                OccupancyCertificate occupancyCertificate = occupancyCertificateService
                        .findById(errMsg.getKey());
                occupancyCertificate.setFailureInScheduler(Boolean.TRUE);
                occupancyCertificate.setSchedulerFailedRemarks(errMsg.getValue());
                occupancyCertificateService.saveOccupancyCertificate(occupancyCertificate);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.error("Exception in document schedule Generation " + occupancyCertificate.getId());
                }
                return Boolean.FALSE;
            });
        }
    }
}
