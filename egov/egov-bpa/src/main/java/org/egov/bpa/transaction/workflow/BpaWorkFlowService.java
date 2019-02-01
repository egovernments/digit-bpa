/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *     accountability and the service delivery of the government  organizations.
 *
 *      Copyright (C) <2017>  eGovernments Foundation
 *
 *      The updated version of eGov suite of products as by eGovernments Foundation
 *      is available at http://www.egovernments.org
 *
 *      This program is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program. If not, see http://www.gnu.org/licenses/ or
 *      http://www.gnu.org/licenses/gpl.html .
 *
 *      In addition to the terms of the GPL license to be adhered to in using this
 *      program, the following additional terms are to be complied with:
 *
 *          1) All versions of this program, verbatim or modified must carry this
 *             Legal Notice.
 *
 *          2) Any misrepresentation of the origin of the material is prohibited. It
 *             is required that all modified versions of this material be marked in
 *             reasonable ways as different from the original version.
 *
 *          3) This license does not grant any rights to any user of the program
 *             with regards to rights under trademark law for use of the trade names
 *             or trademarks of eGovernments Foundation.
 *
 *    In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.bpa.transaction.workflow;

import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.SiteDetail;
import org.egov.bpa.transaction.entity.dto.BpaStateInfo;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.utils.BpaUtils;
import org.egov.common.entity.bpa.Occupancy;
import org.egov.eis.entity.Assignment;
import org.egov.eis.service.AssignmentService;
import org.egov.eis.service.PositionMasterService;
import org.egov.eis.web.contract.WorkflowContainer;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.utils.StringUtils;
import org.egov.infra.workflow.entity.State;
import org.egov.infra.workflow.entity.StateAware;
import org.egov.infra.workflow.entity.StateHistory;
import org.egov.infra.workflow.matrix.entity.WorkFlowMatrix;
import org.egov.infra.workflow.matrix.service.CustomizedWorkFlowService;
import org.egov.pims.commons.Position;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REGISTERED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_RESCHEDULED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_SCHEDULED;
import static org.egov.bpa.utils.BpaConstants.ST_CODE_02;
import static org.egov.bpa.utils.BpaConstants.ST_CODE_05;
import static org.egov.bpa.utils.BpaConstants.ST_CODE_08;
import static org.egov.bpa.utils.BpaConstants.ST_CODE_09;
import static org.egov.bpa.utils.BpaConstants.ST_CODE_14;
import static org.egov.bpa.utils.BpaConstants.ST_CODE_15;
import static org.egov.bpa.utils.BpaConstants.WF_REVERT_BUTTON;

@Service
@Transactional(readOnly = true)
public class BpaWorkFlowService {

    public static final String SCRUTINIZED_POS = "scrutinizedBy";
    public static final String SCRUTINIZED_USER = "scrutinizedUser";
    public static final String REVERTED_BY = "revertedBy";
    public static final String TS_INITIATOR_POS = "tsInitiatorPos";
    @Autowired
    protected AssignmentService assignmentService;
    @Autowired
    protected CustomizedWorkFlowService customizedWorkFlowService;
    @Autowired
    private BpaUtils bpaUtils;
    @Autowired
    private SecurityUtils securityUtils;
    @Autowired
    private PositionMasterService positionMasterService;

    public Assignment getWorkFlowInitiator(final State<Position> state, final User createdBy) {
        Assignment wfInitiator;
        List<Assignment> assignment;
            if (state != null && state.getInitiatorPosition() != null) {
                wfInitiator = getUserAssignmentByPassingPositionAndUser(createdBy, state.getInitiatorPosition());
                if (wfInitiator == null) {
                    assignment = assignmentService
                            .getAssignmentsForPosition(state.getInitiatorPosition().getId(),
                                    new Date());
                    wfInitiator = getActiveAssignment(assignment);
                }
            } else
                wfInitiator = assignmentService.getPrimaryAssignmentForUser(createdBy.getId());

        return wfInitiator;
    }


    private Assignment getActiveAssignment(final List<Assignment> assignment) {
        Assignment wfInitiator = null;
        for (final Assignment assign : assignment)
            if (assign.getEmployee().isActive()) {
                wfInitiator = assign;
                break;
            }
        return wfInitiator;
    }

    public boolean validateUserHasSamePositionAsInitiator(final Long userId, final Position position) {

        Boolean userHasSamePosition = false;

        if (userId != null && position != null) {
            final List<Assignment> assignmentList = assignmentService.findByEmployeeAndGivenDate(userId, new Date());
            for (final Assignment assignment : assignmentList)
                if (position.getId() == assignment.getPosition().getId())
                    userHasSamePosition = true;
        }
        return userHasSamePosition;
    }

    private Assignment getUserAssignmentByPassingPositionAndUser(final User user, final Position position) {

        Assignment wfInitiatorAssignment = null;

        if (user != null && position != null) {
            final List<Assignment> assignmentList = assignmentService.findByEmployeeAndGivenDate(user.getId(), new Date());
            for (final Assignment assignment : assignmentList)
                if (position.getId() == assignment.getPosition().getId())
                    wfInitiatorAssignment = assignment;
        }

        return wfInitiatorAssignment;
    }

    /**
     * @param model
     * @param container
     * @return NextAction From Matrix With Parameters Type,CurrentState,CreatedDate
     */
    public String getNextAction(final StateAware<Position> model, final WorkflowContainer container) {

        WorkFlowMatrix wfMatrix = null;
        if (null != model && null != model.getId())
            if (null != model.getCurrentState())
                wfMatrix = customizedWorkFlowService.getWfMatrix(model.getStateType(),
                        container.getWorkFlowDepartment(), container.getAmountRule(), container.getAdditionalRule(),
                        model.getCurrentState().getValue(), container.getPendingActions(), model.getCreatedDate());
            else
                wfMatrix = customizedWorkFlowService.getWfMatrix(model.getStateType(),
                        container.getWorkFlowDepartment(), container.getAmountRule(), container.getAdditionalRule(),
                        State.DEFAULT_STATE_VALUE_CREATED, container.getPendingActions(), model.getCreatedDate());
        return wfMatrix == null ? "" : wfMatrix.getNextAction();
    }

    /**
     * @param model
     * @param container
     * @return List of WorkFlow Buttons From Matrix By Passing parametres Type,CurrentState,CreatedDate
     */
    public List<String> getValidActions(final StateAware<Position> model, final WorkflowContainer container) {
        List<String> validActions;
        if (null == model
                || null == model.getId() || (model.getCurrentState() == null)
                || ((model != null && model.getCurrentState() != null) && (model.getCurrentState().getValue()
																				.equals("Closed")
																		   || model.getCurrentState().getValue().equals("END"))))
            validActions = Arrays.asList("Forward");
        else if (null != model.getCurrentState())
            validActions = customizedWorkFlowService.getNextValidActions(model.getStateType(), container
                    .getWorkFlowDepartment(), container.getAmountRule(), container.getAdditionalRule(), model
                            .getCurrentState().getValue(),
                    container.getPendingActions(), model.getCreatedDate());
        else
            validActions = customizedWorkFlowService.getNextValidActions(model.getStateType(),
                    container.getWorkFlowDepartment(), container.getAmountRule(), container.getAdditionalRule(),
                    State.DEFAULT_STATE_VALUE_CREATED, container.getPendingActions(), model.getCreatedDate());
        return validActions;
    }

    public StateHistory<Position> getStateHistoryToGetLPInitiator(final List<StateHistory<Position>> stateHistories, final String stateForOwnerPosition) {
        return stateHistories.stream()
                .filter(history -> history.getValue().equalsIgnoreCase(stateForOwnerPosition))
                .findAny().orElse(null);
    }

    public Assignment getApproverAssignment(final Position position) {
        return assignmentService.getPrimaryAssignmentForPositon(position.getId());
    }

    public List<Assignment> getAllActiveAssignmentsForUser(Long userId) {
        return assignmentService.getAllActiveEmployeeAssignmentsByEmpId(userId);
    }

    public Assignment getApproverAssignmentByDate(final Position position , final Date date) {
        return assignmentService.getPrimaryAssignmentForPositionAndDate(position.getId(), date);
    }

    public List<Assignment> getAssignmentsByPositionAndDate(final Long positionId, final Date givenDate) {
        List<Assignment> assignments = assignmentService.getAssignmentsForPosition(positionId, givenDate);
        assignments.sort(Comparator.comparing(Assignment::getPrimary).reversed().thenComparing(Assignment::getFromDate));
        return assignments;
    }

    public List<Assignment> getAssignmentByPositionAndUserAsOnDate(final Long positionId, final Long userId, final Date givenDate) {
        return assignmentService.getAssignmentByPositionAndUserAsOnDate(positionId, userId, givenDate);
    }

    public List<Assignment> getAssignmentByUserAsOnDate(final Long userId, final Date givenDate) {
        List<Assignment> assignments = assignmentService.findByEmployeeAndGivenDate(userId, givenDate);
        assignments.sort(Comparator.comparing(Assignment::getPrimary).reversed().thenComparing(Assignment::getFromDate));
        return assignments;
    }

    public Assignment getAssignmentsForPosition(final Long posId) {
        try {
            return assignmentService.getAssignmentsForPosition(posId).get(0);
        } catch (final IndexOutOfBoundsException e) {
            throw new ApplicationRuntimeException("Assignment Details Not Found For Given Position : "+posId);
        } catch (final Exception e) {
            throw new ApplicationRuntimeException(e.getMessage());
        }
    }

    public Optional<StateHistory<Position>> getLastStateHstryObj(final List<StateHistory<Position>> stateHistories) {
        return stateHistories.stream().reduce((sh1, sh2) -> sh2);
    }

    public BpaStateInfo getBpaStateInfo(final State<Position> state, final List<StateHistory<Position>> stateHistories
                                        , final boolean isTownSurveyorInspectionRequire, final BpaStateInfo bpaStateInfo, final WorkFlowMatrix wfmatrix, final String workFlowAction) {
        bpaStateInfo.setWfMatrixRef(wfmatrix.getId());
        List<Assignment> assignments = getAssignmentByUserAsOnDate(securityUtils.getCurrentUser().getId(), new Date());
        if ((state != null
            && state.getValue().equalsIgnoreCase(APPLICATION_STATUS_REGISTERED)||
             state.getValue().equalsIgnoreCase(APPLICATION_STATUS_SCHEDULED)
            || state.getValue().equalsIgnoreCase(APPLICATION_STATUS_RESCHEDULED))
            && !assignments.isEmpty()) {
            bpaStateInfo.setScrutinizedBy(assignments.get(0).getPosition().getId());
            bpaStateInfo.setScrutinizedUser(assignments.get(0).getEmployee().getId());
        }
        if (isTownSurveyorInspectionRequire && getTownSurveyorInspnInitiator(stateHistories, state) != 0)
            bpaStateInfo.setTsInitiatorPos(getTownSurveyorInspnInitiator(stateHistories, state));
        else if (isTownSurveyorInspectionRequire)
            bpaStateInfo.setTsInitiatorPos(state.getOwnerPosition().getId());

        if (!isBlank(workFlowAction)&& WF_REVERT_BUTTON.equalsIgnoreCase(workFlowAction)
                && !assignments.isEmpty() && assignments.get(0).getDesignation() != null) {
            bpaStateInfo.setRevertedBy("Reverted By " + securityUtils.getCurrentUser().getName() + " - " + assignments.get(0).getDesignation().getName());
        }

        return bpaStateInfo;
    }

    public Long getPreviousWfMatrixId(final List<StateHistory<Position>> stateHistories) {
        Optional<StateHistory<Position>> stateHistory = getLastStateHstryObj(stateHistories);
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        try {
            if (stateHistory.isPresent() && StringUtils.isNotEmpty(stateHistory.get().getExtraInfo()))
                json = (JSONObject) parser.parse(stateHistory.get().getExtraInfo());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Long.valueOf(json.get("wfMatrixRef").toString());
    }

    public Long getTownSurveyorInspnInitiator(final List<StateHistory<Position>> stateHistories, final State<Position> currentState) {
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        try {
            if (StringUtils.isNotEmpty(currentState.getExtraInfo()))
                json = (JSONObject) parser.parse(currentState.getExtraInfo());
            if (json == null || json.get(TS_INITIATOR_POS) == null) {
                Optional<StateHistory<Position>> stateHistory = getLastStateHstryObj(stateHistories);
                if (stateHistory.isPresent() && StringUtils.isNotEmpty(stateHistory.get().getExtraInfo()))
                    json = (JSONObject) parser.parse(stateHistory.get().getExtraInfo());
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return json == null || json.get(TS_INITIATOR_POS) == null ? 0 : Long.valueOf(json.get(TS_INITIATOR_POS).toString());
    }

    public Long getDocumentScrutinizedUserPosition(State<Position> currentState, List<StateHistory<Position>> stateHistories) {
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        try {
            if (StringUtils.isNotEmpty(currentState.getExtraInfo()))
                json = (JSONObject) parser.parse(currentState.getExtraInfo());
            if (json == null || json.get(SCRUTINIZED_POS) == null) {
                for(StateHistory<Position> stateHistory : stateHistories) {
                    if(stateHistory.getExtraInfo() != null && stateHistory.getExtraInfo().contains(SCRUTINIZED_POS)) {
                        json = (JSONObject) parser.parse(stateHistory.getExtraInfo());
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return json == null || json.get(SCRUTINIZED_POS) == null ? 0 : Long.valueOf(json.get(SCRUTINIZED_POS).toString());
    }

    public Long getDocumentScrutinizedUser(State<Position> currentState, List<StateHistory<Position>> stateHistories) {
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        try {
            if (StringUtils.isNotEmpty(currentState.getExtraInfo()))
                json = (JSONObject) parser.parse(currentState.getExtraInfo());
            if (json == null || json.get(SCRUTINIZED_USER) == null) {
                for(StateHistory<Position> stateHistory : stateHistories) {
                    if(stateHistory.getExtraInfo() != null && stateHistory.getExtraInfo().contains(SCRUTINIZED_USER)) {
                        json = (JSONObject) parser.parse(stateHistory.getExtraInfo());
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return json == null || json.get(SCRUTINIZED_USER) == null ? 0 : Long.valueOf(json.get(SCRUTINIZED_USER).toString());
    }


    public String getRevertedBy(final String extraInfo) {

        JSONParser parser = new JSONParser();
        JSONObject json = null;
        try {
            if (StringUtils.isNotEmpty(extraInfo))
                json = (JSONObject) parser.parse(extraInfo);
            if (json == null || json.get(REVERTED_BY) == null) {
                if (extraInfo != null && extraInfo.contains(REVERTED_BY)) {
                    json = (JSONObject) parser.parse(extraInfo);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return json == null || json.get(REVERTED_BY) == null ? EMPTY : json.get(REVERTED_BY).toString();
    }

    public Position getApproverPositionOfElectionWardByCurrentState(final BpaApplication application, final String currentState) {
        WorkFlowMatrix wfMatrix = bpaUtils.getWfMatrixByCurrentState(application.getIsOneDayPermitApplication(), application.getStateType(), currentState);
        return bpaUtils.getUserPositionByZone(wfMatrix.getNextDesignation(),
                application.getSiteDetail().get(0) != null
                && application.getSiteDetail().get(0).getElectionBoundary() != null
                ? application.getSiteDetail().get(0).getElectionBoundary().getId() : null);
    }

    public Position getApproverPositionOfElectionWardByCurrentStateForOC(final OccupancyCertificate oc, final String currentState) {
        WorkFlowMatrix wfMatrix = bpaUtils.getWfMatrixByCurrentState(oc.getStateType(), currentState);
        SiteDetail siteDetail = oc.getParent().getSiteDetail().get(0);
        return bpaUtils.getUserPositionByZone(wfMatrix.getNextDesignation(),
                siteDetail != null && siteDetail.getElectionBoundary() != null
                ? siteDetail.getElectionBoundary().getId() : null);
    }

    public BigDecimal getAmountRuleByServiceType(final BpaApplication application) {
        BigDecimal amountRule = BigDecimal.ONE;
        if (ST_CODE_14.equalsIgnoreCase(application.getServiceType().getCode())
            || ST_CODE_15.equalsIgnoreCase(application.getServiceType().getCode())) {
            amountRule = new BigDecimal(2501);
        } else if (ST_CODE_02.equalsIgnoreCase(application.getServiceType().getCode()) && application.getSiteDetail().get(0).getDemolitionArea() != null) {
            amountRule = application.getSiteDetail().get(0).getDemolitionArea().doubleValue() == 0
                         ? BigDecimal.ONE : application.getSiteDetail().get(0).getDemolitionArea();
        } else if (ST_CODE_05.equalsIgnoreCase(application.getServiceType().getCode())) {
            amountRule = application.getSiteDetail().get(0).getExtentinsqmts();
        } else if (ST_CODE_08.equalsIgnoreCase(application.getServiceType().getCode())
                   || ST_CODE_09.equalsIgnoreCase(application.getServiceType().getCode())) {
            amountRule = BigDecimal.ONE;
        } else if (!application.getBuildingDetail().isEmpty()
                   && application.getBuildingDetail().get(0).getTotalPlintArea() != null) {
            if (!application.getExistingBuildingDetails().isEmpty())
                for (Map.Entry<Occupancy, BigDecimal> existBuiltupArea : bpaUtils.getExistingBldgBlockWiseOccupancyAndBuiltupArea(application.getExistingBuildingDetails()).entrySet())
                    amountRule = amountRule.add(existBuiltupArea.getValue());
            else {
                for (Map.Entry<Occupancy, BigDecimal> builtupArea : bpaUtils.getBlockWiseOccupancyAndBuiltupArea(application.getBuildingDetail()).entrySet())
                    amountRule = amountRule.add(builtupArea.getValue());
            }
        }
        return amountRule.setScale(0, BigDecimal.ROUND_UP);
    }

}
