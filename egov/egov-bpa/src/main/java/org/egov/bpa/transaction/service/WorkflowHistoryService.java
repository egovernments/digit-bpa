/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2016>  eGovernments Foundation
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

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REGISTERED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_RESCHEDULED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_SCHEDULED;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.egov.bpa.transaction.entity.BpaAppointmentSchedule;
import org.egov.bpa.transaction.entity.InspectionAppointmentSchedule;
import org.egov.bpa.transaction.entity.common.AppointmentScheduleCommon;
import org.egov.bpa.transaction.entity.enums.AppointmentSchedulePurpose;
import org.egov.bpa.transaction.entity.oc.OCAppointmentSchedule;
import org.egov.bpa.transaction.workflow.BpaWorkFlowService;
import org.egov.eis.entity.Assignment;
import org.egov.eis.service.PositionMasterService;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.UserService;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.egov.infra.workflow.entity.State;
import org.egov.infra.workflow.entity.StateHistory;
import org.egov.pims.commons.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class WorkflowHistoryService {
    private static final String APPOINTMENT_FOR_INSPECTION_RESCHEDULED = "Appointment for inspection rescheduled";
    private static final String APPOINTMENT_FOR_INSPECTION_SCHEDULED = "Appointment for inspection scheduled";
    private static final String USER = "user";
    private static final String DATE = "date";
    private static final String STATUS = "status";
    private static final String UPDATED_BY = "updatedBy";
    private static final String COMMENTS = "comments";
    private static final String INSPECTION = "INSPECTION";
    private static final String DEPARTMENT = "department";
    @Autowired
    private PositionMasterService positionMasterService;
    @Autowired
    @Qualifier("parentMessageSource")
    private MessageSource bpaMessageSource;
    @Autowired
    private BpaWorkFlowService bpaWorkFlowService;
    @Autowired
    private UserService userService;

    public List<HashMap<String, Object>> getHistory(List<BpaAppointmentSchedule> appointmentSchedules, State<Position> state,
            List<StateHistory<Position>> stateHistories) {
        final List<HashMap<String, Object>> historyTable = new ArrayList<>();
        final State<Position> workflowState = state;
        final HashMap<String, Object> workFlowHistory = new HashMap<>(0);
        if (null != workflowState) {
            if (!stateHistories.isEmpty())
                Collections.reverse(stateHistories);

            buildStateHistory(stateHistories, historyTable, workflowState);
            buildApplicationHistoryForSchedulingAppointments(appointmentSchedules, historyTable);
            workFlowHistory.put(DATE, workflowState.getDateInfo());
            workFlowHistory.put(COMMENTS, workflowState.getComments() == null ? "" : workflowState.getComments());
            workFlowHistory.put(UPDATED_BY,
                    workflowState.getLastModifiedBy().getUsername() + "::" + workflowState.getLastModifiedBy().getName());

            String revertedBy = bpaWorkFlowService.getRevertedBy(workflowState.getExtraInfo());
            workFlowHistory.put(STATUS, !isBlank(revertedBy) ? revertedBy : workflowState.getValue());

            buildEmployeeInformation(state, stateHistories, workFlowHistory, state != null, state.getValue(),
                    workflowState.getOwnerPosition(), workflowState.getLastModifiedDate(), workflowState.getOwnerUser());

            historyTable.add(workFlowHistory);
        }
        historyTable.sort(Comparator.comparing(history -> String.valueOf(history.get(DATE))));
        return historyTable;
    }

    private void buildEmployeeInformation(State<Position> state, List<StateHistory<Position>> stateHistories,
            HashMap<String, Object> workFlowHistory, boolean b, String value, Position ownerPosition2, Date lastModifiedDate,
            User ownerUser) {
        User userObject;
        if (b
                && value.equalsIgnoreCase(APPLICATION_STATUS_REGISTERED) ||
                value.equalsIgnoreCase(APPLICATION_STATUS_SCHEDULED)
                || value.equalsIgnoreCase(APPLICATION_STATUS_RESCHEDULED)) {
            Position scrutinizedByPos = positionMasterService
                    .getPositionById(bpaWorkFlowService.getDocumentScrutinizedUserPosition(state, stateHistories));
            bpaWorkFlowService.getDocumentScrutinizedUser(state, stateHistories);
            Long userId = bpaWorkFlowService.getDocumentScrutinizedUser(state, stateHistories);
            if (userId == 0)
                userObject = ownerUser;
            else
                userObject = userService.getUserById(userId);
            setEmployeeDetailsByDate(userObject, workFlowHistory, scrutinizedByPos == null ? ownerPosition2 : scrutinizedByPos,
                    lastModifiedDate);
        } else {
            final Position ownerPosition = ownerPosition2;
            userObject = ownerUser;
            setEmployeeDetailsByDate(userObject, workFlowHistory, ownerPosition, lastModifiedDate);
        }
    }

    private void buildStateHistory(List<StateHistory<Position>> stateHistories, List<HashMap<String, Object>> historyTable,
            State<Position> workflowState) {
        for (final StateHistory<Position> stateHistory : stateHistories) {
            final HashMap<String, Object> historyMap = new HashMap<>(0);
            historyMap.put(DATE, stateHistory.getDateInfo());
            historyMap.put(COMMENTS, stateHistory.getComments() == null ? "" : stateHistory.getComments());
            historyMap.put(UPDATED_BY, stateHistory.getLastModifiedBy().getUsername() + "::"
                    + stateHistory.getLastModifiedBy().getName());

            String revertedBy = bpaWorkFlowService.getRevertedBy(stateHistory.getExtraInfo());
            historyMap.put(STATUS, !isBlank(revertedBy) ? revertedBy : stateHistory.getValue());

            buildEmployeeInformation(workflowState, stateHistories, historyMap, stateHistory != null, stateHistory.getValue(),
                    stateHistory.getOwnerPosition(), stateHistory.getLastModifiedDate(), stateHistory.getOwnerUser());
            historyTable.add(historyMap);
        }
    }

    public List<HashMap<String, Object>> getHistoryForOC(List<OCAppointmentSchedule> appointmentSchedules, State<Position> state,
            List<StateHistory<Position>> stateHistories) {
        final List<HashMap<String, Object>> historyTable = new ArrayList<>();
        final State<Position> workflowState = state;
        final HashMap<String, Object> workFlowHistory = new HashMap<>(0);
        if (null != workflowState) {
            if (!stateHistories.isEmpty())
                Collections.reverse(stateHistories);

            buildStateHistory(stateHistories, historyTable, state);
            buildOCApplnHistoryForSchedulingAppointments(appointmentSchedules, historyTable);
            workFlowHistory.put(DATE, workflowState.getDateInfo());
            workFlowHistory.put(COMMENTS, workflowState.getComments() == null ? "" : workflowState.getComments());
            workFlowHistory.put(UPDATED_BY,
                    workflowState.getLastModifiedBy().getUsername() + "::" + workflowState.getLastModifiedBy().getName());

            String revertedBy = bpaWorkFlowService.getRevertedBy(workflowState.getExtraInfo());
            workFlowHistory.put(STATUS, !isBlank(revertedBy) ? revertedBy : workflowState.getValue());

            buildEmployeeInformation(state, stateHistories, workFlowHistory, state != null, state.getValue(),
                    workflowState.getOwnerPosition(), workflowState.getLastModifiedDate(), workflowState.getOwnerUser());

            historyTable.add(workFlowHistory);
        }
        historyTable.sort(Comparator.comparing(history -> String.valueOf(history.get(DATE))));
        return historyTable;
    }

    private void setEmployeeDetailsByDate(User userObject, HashMap<String, Object> historyMap, Position owner, Date date) {
        Assignment assignment = null;
        if (null != userObject && owner == null) {
            historyMap.put(USER, userObject.getUsername() + "::" + userObject.getName());
            List<Assignment> assignments = bpaWorkFlowService.getAssignmentByUserAsOnDate(userObject.getId(), date);
            if (!assignments.isEmpty())
                assignment = assignments.get(0);
            if (assignment == null)
                assignment = bpaWorkFlowService.getAssignmentsForPosition(owner.getId());
            historyMap.put(DEPARTMENT, assignment == null ? "" : assignment.getDepartment().getName());
        } else if (null != owner && userObject != null) {
            List<Assignment> assignments = bpaWorkFlowService.getAssignmentByPositionAndUserAsOnDate(owner.getId(),
                    userObject.getId(), date);
            if (assignments.isEmpty())
                assignments = bpaWorkFlowService.getAssignmentsByPositionAndDate(owner.getId(), date);
            else if (assignments.isEmpty())
                assignment = bpaWorkFlowService.getAssignmentsForPosition(owner.getId());
            if (!assignments.isEmpty())
                assignment = assignments.get(0);
            historyMap
                    .put(USER, userObject.getUsername() == null ? ""
                            : userObject.getUsername() + "::" + userObject.getName());
            historyMap.put(DEPARTMENT, assignment == null ? "" : assignment.getDepartment().getName());
        } else if (null != owner) {
            User user = getUserPositionByPositionAndDate(owner.getId(), date);
            assignment = bpaWorkFlowService.getApproverAssignmentByDate(owner, date);
            List<Assignment> assignments = bpaWorkFlowService.getAssignmentsByPositionAndDate(owner.getId(), date);
            if (assignment == null && !assignments.isEmpty())
                assignment = assignments.get(0);
            if (assignment == null)
                assignment = bpaWorkFlowService.getAssignmentsForPosition(owner.getId());
            historyMap
                    .put(USER, user.getUsername() == null ? ""
                            : user.getUsername() + "::" + user.getName());
            historyMap.put(DEPARTMENT, assignments.isEmpty() ? "" : assignment.getDepartment().getName());
        }
    }

    private void buildApplicationHistoryForSchedulingAppointments(final List<BpaAppointmentSchedule> appointmentSchedules,
            final List<HashMap<String, Object>> historyTable) {
        if (!appointmentSchedules.isEmpty()) {
            for (BpaAppointmentSchedule appointmentSchedule : appointmentSchedules) {
                buildSchedulingDetails(historyTable, appointmentSchedule.getPurpose(), appointmentSchedule.getCreatedDate(),
                        appointmentSchedule.isPostponed(), appointmentSchedule.getPostponementReason(),
                        appointmentSchedule.getLastModifiedBy(), appointmentSchedule.getCreatedBy(),
                        appointmentSchedule.getLastModifiedDate());
            }
        }
    }

    private void buildSchedulingDetails(List<HashMap<String, Object>> historyTable, AppointmentSchedulePurpose purpose,
            Date createdDate, boolean postponed, String postponementReason, User lastModifiedBy, User createdBy,
            Date lastModifiedDate) {
        if (INSPECTION.equals(purpose.name())) {
            final HashMap<String, Object> appointmentHistory = new HashMap<>();
            appointmentHistory.put(DATE, createdDate);
            String comments = StringUtils.EMPTY;
            String status = StringUtils.EMPTY;
            if (INSPECTION.equals(purpose.name()) && postponed) {
                status = APPOINTMENT_FOR_INSPECTION_RESCHEDULED;
                comments = APPOINTMENT_FOR_INSPECTION_RESCHEDULED + " for " + postponementReason;
            } else if (INSPECTION.equals(purpose.name()) && !postponed) {
                status = APPOINTMENT_FOR_INSPECTION_SCHEDULED;
                comments = APPOINTMENT_FOR_INSPECTION_SCHEDULED;
            }
            appointmentHistory.put(COMMENTS, comments);
            appointmentHistory.put(UPDATED_BY, lastModifiedBy.getUsername() + "::"
                    + lastModifiedBy.getName());
            appointmentHistory.put(STATUS, status);

            List<Assignment> assignments = bpaWorkFlowService.getAssignmentByUserAsOnDate(createdBy.getId(), lastModifiedDate);
            Position ownerPos;
            if (assignments.isEmpty())
                ownerPos = positionMasterService.getCurrentPositionForUser(createdBy.getId());
            else
                ownerPos = assignments.get(0).getPosition();

            setEmployeeDetailsByDate(createdBy, appointmentHistory, ownerPos, lastModifiedDate);
            historyTable.add(appointmentHistory);
        }
    }

    private void buildOCApplnHistoryForSchedulingAppointments(final List<OCAppointmentSchedule> appointmentSchedules,
            final List<HashMap<String, Object>> historyTable) {
        if (!appointmentSchedules.isEmpty()) {
            for (OCAppointmentSchedule ocAppointmentSchedule : appointmentSchedules) {
                AppointmentScheduleCommon appmntScheduleCommon = ocAppointmentSchedule.getAppointmentScheduleCommon();
                buildSchedulingDetails(historyTable, appmntScheduleCommon.getPurpose(), ocAppointmentSchedule.getCreatedDate(),
                        appmntScheduleCommon.isPostponed(), appmntScheduleCommon.getPostponementReason(),
                        ocAppointmentSchedule.getLastModifiedBy(), ocAppointmentSchedule.getCreatedBy(),
                        ocAppointmentSchedule.getLastModifiedDate());
            }
        }
    }

    public User getUserPositionByPassingPosition(Long ownerPosition) {
        return getUserPositionByPositionAndDate(ownerPosition, new Date());
    }

    public User getUserPositionByPositionAndDate(Long ownerPosition, Date date) {
        try {
            return bpaWorkFlowService.getAssignmentsByPositionAndDate(ownerPosition, date).get(0).getEmployee();
        } catch (final IndexOutOfBoundsException e) {
            throw new ApplicationRuntimeException("Assignment Details Not Found For Given Position : " + ownerPosition);
        } catch (final Exception e) {
            throw new ApplicationRuntimeException(e.getMessage());
        }
    }
    
    public List<HashMap<String, Object>> getHistoryForInspection(List<InspectionAppointmentSchedule> appointmentSchedules, State<Position> state,
            List<StateHistory<Position>> stateHistories) {
        final List<HashMap<String, Object>> historyTable = new ArrayList<>();
        final State<Position> workflowState = state;
        final HashMap<String, Object> workFlowHistory = new HashMap<>(0);
        if (null != workflowState) {
            if (!stateHistories.isEmpty())
                Collections.reverse(stateHistories);

            buildStateHistory(stateHistories, historyTable, state);
            buildInspectionApplnHistoryForSchedulingAppointments(appointmentSchedules, historyTable);
            workFlowHistory.put(DATE, workflowState.getDateInfo());
            workFlowHistory.put(COMMENTS, workflowState.getComments() == null ? "" : workflowState.getComments());
            workFlowHistory.put(UPDATED_BY,
                    workflowState.getLastModifiedBy().getUsername() + "::" + workflowState.getLastModifiedBy().getName());

            String revertedBy = bpaWorkFlowService.getRevertedBy(workflowState.getExtraInfo());
            workFlowHistory.put(STATUS, !isBlank(revertedBy) ? revertedBy : workflowState.getValue());

            buildEmployeeInformation(state, stateHistories, workFlowHistory, state != null, state.getValue(),
                    workflowState.getOwnerPosition(), workflowState.getLastModifiedDate(), workflowState.getOwnerUser());

            historyTable.add(workFlowHistory);
        }
        historyTable.sort(Comparator.comparing(history -> String.valueOf(history.get(DATE))));
        return historyTable;
    }
    
    private void buildInspectionApplnHistoryForSchedulingAppointments(final List<InspectionAppointmentSchedule> appointmentSchedules,
            final List<HashMap<String, Object>> historyTable) {
        if (!appointmentSchedules.isEmpty()) {
            for (InspectionAppointmentSchedule inspectionchedule : appointmentSchedules) {
                AppointmentScheduleCommon appmntScheduleCommon = inspectionchedule.getAppointmentScheduleCommon();
                buildSchedulingDetails(historyTable, appmntScheduleCommon.getPurpose(), inspectionchedule.getCreatedDate(),
                        appmntScheduleCommon.isPostponed(), appmntScheduleCommon.getPostponementReason(),
                        inspectionchedule.getLastModifiedBy(), inspectionchedule.getCreatedBy(),
                        inspectionchedule.getLastModifiedDate());
            }
        }
    }


}
