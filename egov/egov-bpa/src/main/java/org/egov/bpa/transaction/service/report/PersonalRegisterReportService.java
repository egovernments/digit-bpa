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
package org.egov.bpa.transaction.service.report;

import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BuildingDetail;
import org.egov.bpa.transaction.entity.SiteDetail;
import org.egov.bpa.transaction.entity.dto.PersonalRegisterHelper;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationForm;
import org.egov.bpa.transaction.service.ApplicationBpaFeeCalculationService;
import org.egov.bpa.transaction.service.SearchBpaApplicationService;
import org.egov.bpa.transaction.workflow.BpaWorkFlowService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.eis.entity.Assignment;
import org.egov.eis.service.AssignmentService;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.utils.StringUtils;
import org.egov.infra.workflow.entity.State;
import org.egov.infra.workflow.entity.StateHistory;
import org.egov.pims.commons.Position;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Collections;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_DOC_VERIFIED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_RESCHEDULED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_SCHEDULED;
import static org.egov.bpa.utils.BpaConstants.LPCREATED;
import static org.egov.bpa.utils.BpaConstants.LPREPLIED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_TYPE_ONEDAYPERMIT;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_TYPE_REGULAR;

@Service
@Transactional(readOnly = true)
public class PersonalRegisterReportService {

	private static final String APPROVAL_INITIATED = "Final Approval Process initiated";
	private static final String PERMIT_FEE_COLLECTED = "Permit Fee Collected";
	public static final String CLOSED = "Closed";

	@Autowired
	private BpaWorkFlowService bpaWorkFlowService;
	@Autowired
	private ApplicationBpaFeeCalculationService bpaCalculationService;
	@Autowired
	private BpaReportsService bpaReportService;
	@Autowired
	private AssignmentService assignmentService;
	@Autowired
	private SearchBpaApplicationService searchBpaApplicationService;
	@PersistenceContext
	private EntityManager entityManager;

	public Session getCurrentSession() {
		return entityManager.unwrap(Session.class);
	}

	public List<PersonalRegisterHelper> searchPersonalRegisterDetail(
			final SearchBpaApplicationForm bpaApplicationForm, final List<Long> userIds, User loggedInUser) {
		final Criteria criteria = searchBpaApplicationService.searchApplicationsForPRReport(bpaApplicationForm);
		return buildPersonalRegisterResponse((List<BpaApplication>) criteria.list(), userIds, loggedInUser);
	}

	private List<PersonalRegisterHelper> buildPersonalRegisterResponse(List<BpaApplication> bpaApplications, final List<Long> userIds, final User loggedInUser) {
		List<PersonalRegisterHelper> personalRegisterHelperList = new ArrayList<>();
		Boolean isClerk;
		String[] designations = {"SECTION CLERK"};
		Set<User> usersByDesignations = assignmentService.getUsersByDesignations(designations);
		List<User> userList = new ArrayList<>(usersByDesignations);
		isClerk = userList.stream()
						  .anyMatch(user -> user.getId().equals(loggedInUser.getId()));
		boolean isSystem = loggedInUser.getRoles().stream().anyMatch(role -> role.getName().equalsIgnoreCase("System"));
		buildPrReport(bpaApplications, userIds, personalRegisterHelperList, isClerk, isSystem);
		Collections.sort(personalRegisterHelperList, Comparator.comparing(PersonalRegisterHelper::getApplicationNumber)
				.thenComparing((b1, b2) -> b2.getPreviousDateAndTime().compareTo(b1.getPreviousDateAndTime())));
		return personalRegisterHelperList;
	}

	private void buildPrReport(List<BpaApplication> bpaApplications, List<Long> userIds, List<PersonalRegisterHelper> personalRegisterHelperList, Boolean isClerk, boolean isSystem) {
		for (BpaApplication application : bpaApplications) {
			State<Position> currentState = application.getState();
			if (currentState != null &&
				!((currentState.getValue().equalsIgnoreCase(BpaConstants.APPLICATION_STATUS_REGISTERED)
				   || currentState.getValue().equalsIgnoreCase(APPLICATION_STATUS_SCHEDULED)
				   || currentState.getValue().equalsIgnoreCase(BpaConstants.APPLICATION_STATUS_RESCHEDULED)
				   || currentState.getValue().equalsIgnoreCase(BpaConstants.APPLICATION_STATUS_PENDING_FOR_RESCHEDULING)))) {
				for (Long userId : userIds) {
					Position userPosition = bpaReportService.getUserPositionByUserId(userId);
					List<Assignment> assignments;
					Long documentScrutinizedBy = getDocumentScrutinizedBy(currentState.getExtraInfo());
					if (currentState.getValue().equalsIgnoreCase(APPLICATION_STATUS_DOC_VERIFIED)
						&& userPosition != null && documentScrutinizedBy != 0L && documentScrutinizedBy == userPosition.getId()) {
						PersonalRegisterHelper personalRegister = new PersonalRegisterHelper();
						buildPRObject(application, personalRegister);
						Optional<StateHistory<Position>> stateHistory = bpaWorkFlowService.getLastStateHstryObj(application.getStateHistory());
						if (stateHistory.isPresent()) {
							personalRegister.setFromWhom(application.getCreatedBy().getUsername().concat(" :: ").concat(application.getCreatedBy().getName()));
							personalRegister.setPreviousStatus(setRevertedByStatus(stateHistory.get().getExtraInfo(), stateHistory.get().getValue()));
							personalRegister.setPreviousDateAndTime(stateHistory.get().getDateInfo());
						}
						buildDispatchDetails4(currentState, personalRegister);
						personalRegisterHelperList.add(personalRegister);
					} else if (currentState.getValue().equalsIgnoreCase(APPLICATION_STATUS_DOC_VERIFIED)
							   && userPosition != null && documentScrutinizedBy != 0L && currentState.getOwnerPosition().getId() == userPosition.getId()) {
						PersonalRegisterHelper personalRegister = new PersonalRegisterHelper();
						buildStateLevelPrObject(personalRegister, application, currentState);
						personalRegisterHelperList.add(personalRegister);
					} else {
						//state
						if (currentState.getValue().equalsIgnoreCase(LPREPLIED)
							&& userPosition != null && currentState.getOwnerPosition().getId() == userPosition.getId()) {
							PersonalRegisterHelper personalRegister = new PersonalRegisterHelper();
							buildPRObject(application, personalRegister);
							Optional<StateHistory<Position>> stateHistory = bpaWorkFlowService.getLastStateHstryObj(application.getStateHistory());
							application.getStateHistory().sort(Comparator.comparing(StateHistory::getId));
							StateHistory<Position> previousStatehistory = application.getStateHistory().get(application.getStateHistory().size() - 2);
							if (stateHistory.isPresent()) {
								documentScrutinizedBy = getDocumentScrutinizedBy(currentState.getExtraInfo());
								Long positionId = documentScrutinizedBy != null && documentScrutinizedBy != 0L ? documentScrutinizedBy : previousStatehistory.getOwnerPosition().getId();
								buildDispatchDetails1(stateHistory.get(), previousStatehistory, personalRegister, positionId);
							}
							if (!currentState.getValue().equalsIgnoreCase(CLOSED))
								setDefaultDispatchValues(personalRegister);
							else
								buildDispatchDetails4(currentState, personalRegister);
							personalRegisterHelperList.add(personalRegister);
						} else if ((currentState.getValue().equalsIgnoreCase(PERMIT_FEE_COLLECTED))
								   && userPosition != null && currentState.getOwnerPosition().getId() == userPosition.getId()) {
							PersonalRegisterHelper personalRegister = new PersonalRegisterHelper();
							buildPRObject(application, personalRegister);
							Optional<StateHistory<Position>> stateHistory = bpaWorkFlowService.getLastStateHstryObj(application.getStateHistory());
							if (stateHistory.isPresent()) {
								documentScrutinizedBy = getDocumentScrutinizedBy(currentState.getExtraInfo());
								Long positionId = documentScrutinizedBy != null && documentScrutinizedBy != 0L ? documentScrutinizedBy : stateHistory.get().getOwnerPosition().getId();
								assignments = assignmentService.getAssignmentsForPosition(positionId, stateHistory.get().getOwnerPosition().getLastModifiedDate());
								personalRegister.setFromWhom(!assignments.isEmpty() ? assignments.get(0).getEmployee().getName().concat(" :: ").concat(assignments.get(0).getDesignation().getName()) : "N/A");
								personalRegister.setPreviousStatus(setRevertedByStatus(stateHistory.get().getExtraInfo(), stateHistory.get().getValue()));
								personalRegister.setPreviousDateAndTime(stateHistory.get().getDateInfo());
							}
							if (!currentState.getValue().equalsIgnoreCase(CLOSED))
								setDefaultDispatchValues(personalRegister);
							else
								buildDispatchDetails4(currentState, personalRegister);
							personalRegisterHelperList.add(personalRegister);
						} else if (userPosition != null && currentState.getOwnerPosition().getId() == userPosition.getId()
								   && !currentState.getValue().equalsIgnoreCase(CLOSED)
								   && !currentState.getValue().equalsIgnoreCase(LPREPLIED)
								   && !currentState.getValue().equalsIgnoreCase(PERMIT_FEE_COLLECTED)) {
							PersonalRegisterHelper personalRegister = new PersonalRegisterHelper();
							buildStateLevelPrObject(personalRegister, application, currentState);
							personalRegisterHelperList.add(personalRegister);
						}

						//state history
						if (!application.getStateHistory().isEmpty()) {
							for (int i = 0; i < application.getStateHistory().size(); i++) {
								StateHistory stateHistory = application.getStateHistory().get(i);
								userPosition = bpaReportService.getUserPositionByUserId(userId);
								StateHistory<Position> previousStateHistory;
								StateHistory<Position> nextStateHistory;
								Long previousHistoryScrutizedBy = 0L;
								Long secondPreviousStateScrutizedBy = 0L;
								try {
									previousStateHistory = application.getStateHistory().get(i - 1);
								} catch (Exception e) {
									previousStateHistory = null;
								}

								try {
									nextStateHistory = application.getStateHistory().get(i + 1);
								} catch (Exception e) {
									nextStateHistory = null;
								}
								String historyValue = stateHistory.getValue();
								if (userPosition != null && userPosition.getId() == stateHistory.getOwnerPosition().getId()) {

									if (previousStateHistory != null && userPosition != null
										&& userPosition.getId() == stateHistory.getOwnerPosition().getId()
										&& !historyValue.equalsIgnoreCase(APPLICATION_STATUS_SCHEDULED)
										&& !historyValue.equalsIgnoreCase(APPLICATION_STATUS_RESCHEDULED)
										&& !historyValue.equalsIgnoreCase(LPREPLIED)
										&& !historyValue.equalsIgnoreCase(LPCREATED)
										&& !historyValue.equalsIgnoreCase(APPROVAL_INITIATED)
										&& !historyValue.equalsIgnoreCase(PERMIT_FEE_COLLECTED)) {

										PersonalRegisterHelper personalRegister = new PersonalRegisterHelper();
										buildPRObject(application, personalRegister);
										if (stateHistory.getOwnerPosition().getId() == previousStateHistory.getOwnerPosition().getId()) {
											if (nextStateHistory != null)
												buildDispatchDetails2(nextStateHistory, personalRegister);
											else
												currentState = buildDispatchDetails3(application, personalRegister, currentState);
										}
									}

									if (previousStateHistory != null
										&& stateHistory.getOwnerPosition().getId() == previousStateHistory.getOwnerPosition().getId()
										&& !historyValue.equalsIgnoreCase(APPLICATION_STATUS_SCHEDULED)
										&& !historyValue.equalsIgnoreCase(APPLICATION_STATUS_RESCHEDULED)
										&& !historyValue.equalsIgnoreCase(LPREPLIED)
										&& !historyValue.equalsIgnoreCase(LPCREATED)
										&& !historyValue.equalsIgnoreCase(APPROVAL_INITIATED)
										&& !historyValue.equalsIgnoreCase(PERMIT_FEE_COLLECTED)) {
										currentState = buildHistoryWhenTransitionHappenTwiceWithSameOwner(personalRegisterHelperList, application, currentState, i, stateHistory, nextStateHistory, secondPreviousStateScrutizedBy);
									}

									if (previousStateHistory != null &&
										stateHistory.getOwnerPosition().getId() != previousStateHistory.getOwnerPosition().getId()
										&& !historyValue.equalsIgnoreCase(APPLICATION_STATUS_SCHEDULED)
										&& !historyValue.equalsIgnoreCase(LPREPLIED)
										&& !historyValue.equalsIgnoreCase(LPCREATED)
										&& !historyValue.equalsIgnoreCase(APPROVAL_INITIATED)
										&& !historyValue.equalsIgnoreCase(PERMIT_FEE_COLLECTED)) {
										PersonalRegisterHelper personalRegister = new PersonalRegisterHelper();
										buildPRObject(application, personalRegister);
										documentScrutinizedBy = getDocumentScrutinizedBy(stateHistory.getExtraInfo());
										documentScrutinizedBy = documentScrutinizedBy != null && documentScrutinizedBy != 0L ? documentScrutinizedBy : previousHistoryScrutizedBy;
										Long positionId = documentScrutinizedBy != null && documentScrutinizedBy != 0L ? documentScrutinizedBy : previousStateHistory.getOwnerPosition().getId();

										buildDispatchDetails1(stateHistory, previousStateHistory, personalRegister, positionId);
										if (nextStateHistory != null) {
											buildDispatchDetails2(nextStateHistory, personalRegister);
										} else {
											currentState = buildDispatchDetails3(application, personalRegister, currentState);
										}
										personalRegisterHelperList.add(personalRegister);
									}

									if (historyValue.equalsIgnoreCase(LPREPLIED)) {
										StateHistory<Position> secondPreviousStateHistory;
										PersonalRegisterHelper personalRegister = new PersonalRegisterHelper();
										buildPRObject(application, personalRegister);
										try {
											secondPreviousStateHistory = application.getStateHistory().get(i - 2);
										} catch (Exception e) {
											secondPreviousStateHistory = null;
										}

										if (secondPreviousStateHistory != null) {
											Long positionId = secondPreviousStateScrutizedBy != null && secondPreviousStateScrutizedBy != 0L ? secondPreviousStateScrutizedBy : secondPreviousStateHistory.getOwnerPosition().getId();
											buildDispatchDetails1(previousStateHistory, secondPreviousStateHistory, personalRegister, positionId);
										} else
											buildDispatchedDetails(stateHistory, personalRegister);

										if (nextStateHistory != null)
											buildDispatchDetails2(nextStateHistory, personalRegister);
										else
											currentState = buildDispatchDetails3(application, personalRegister, currentState);
										personalRegisterHelperList.add(personalRegister);
									}

									if (historyValue.equalsIgnoreCase(APPROVAL_INITIATED) && !currentState.getValue().equalsIgnoreCase(PERMIT_FEE_COLLECTED)) {
										StateHistory<Position> secondNextStateHistory;
										PersonalRegisterHelper personalRegister = new PersonalRegisterHelper();
										buildPRObject(application, personalRegister);
										try {
											secondNextStateHistory = application.getStateHistory().get(i + 2);
										} catch (Exception e) {
											secondNextStateHistory = null;
										}

										if (previousStateHistory != null) {
											Long positionId = previousHistoryScrutizedBy != null && previousHistoryScrutizedBy != 0L ? secondPreviousStateScrutizedBy : previousStateHistory.getOwnerPosition().getId();
											buildDispatchDetails1(stateHistory, previousStateHistory, personalRegister, positionId);
										} else
											buildDispatchedDetails(stateHistory, personalRegister);

										if (secondNextStateHistory != null)
											buildDispatchDetails2(secondNextStateHistory, personalRegister);
										else
											currentState = buildDispatchDetails3(application, personalRegister, currentState);
										personalRegisterHelperList.add(personalRegister);
									}
								}

								if (historyValue.equalsIgnoreCase(APPLICATION_STATUS_SCHEDULED)) {
									if (nextStateHistory != null) {
										documentScrutinizedBy = getDocumentScrutinizedBy(nextStateHistory.getExtraInfo());
										if (documentScrutinizedBy == 0L)
											documentScrutinizedBy = nextStateHistory.getOwnerPosition().getId();
									} else {
										documentScrutinizedBy = getDocumentScrutinizedBy(currentState.getExtraInfo());
										if (documentScrutinizedBy == 0L)
											documentScrutinizedBy = currentState.getOwnerPosition().getId();
									}
									if (userPosition != null && userPosition.getId() == documentScrutinizedBy) {
										PersonalRegisterHelper personalRegister = new PersonalRegisterHelper();
										buildPRObject(application, personalRegister);
										StateHistory<Position> secondPreviousStateHistory;
										StateHistory<Position> history;
										try {
											secondPreviousStateHistory = application.getStateHistory().get(i - 2);
										} catch (Exception e) {
											secondPreviousStateHistory = null;
										}
										if (secondPreviousStateHistory != null) {
											Long positionId = secondPreviousStateScrutizedBy != null && secondPreviousStateScrutizedBy != 0L ? secondPreviousStateScrutizedBy : secondPreviousStateHistory.getOwnerPosition().getId();
											assignments = assignmentService.getAssignmentsForPosition(positionId, secondPreviousStateHistory.getLastModifiedDate());
											personalRegister.setFromWhom(!assignments.isEmpty() ? assignments.get(0).getEmployee().getName().concat(" :: ").concat(assignments.get(0).getDesignation().getName()) : "N/A");
											personalRegister.setPreviousStatus(setRevertedByStatus(secondPreviousStateHistory.getExtraInfo(), secondPreviousStateHistory.getValue()));
											personalRegister.setPreviousDateAndTime(secondPreviousStateHistory.getDateInfo());
										} else {
											documentScrutinizedBy = getDocumentScrutinizedBy(stateHistory.getExtraInfo());
											Long positionId = documentScrutinizedBy != null && documentScrutinizedBy != 0L ? documentScrutinizedBy : stateHistory.getOwnerPosition().getId();
											assignments = assignmentService.getAssignmentsForPosition(positionId, stateHistory.getLastModifiedDate());
											if (historyValue.equalsIgnoreCase(APPLICATION_STATUS_SCHEDULED)) {
												personalRegister.setFromWhom(application.getCreatedBy().getUsername().concat(" :: ").concat(application.getCreatedBy().getName()));
											} else
												personalRegister.setFromWhom(!assignments.isEmpty() ? assignments.get(0).getEmployee().getName().concat(" :: ").concat(assignments.get(0).getDesignation().getName()) : "N/A");
											if (nextStateHistory != null) {
												if (!nextStateHistory.getValue().equalsIgnoreCase(APPLICATION_STATUS_RESCHEDULED)) {
													personalRegister.setPreviousStatus(historyValue);
													personalRegister.setPreviousDateAndTime(stateHistory.getDateInfo());
												} else {
													personalRegister.setPreviousStatus(setRevertedByStatus(nextStateHistory.getExtraInfo(), nextStateHistory.getValue()));
													personalRegister.setPreviousDateAndTime(nextStateHistory.getDateInfo());
												}
											} else {
												personalRegister.setPreviousStatus(setRevertedByStatus(stateHistory.getExtraInfo(), historyValue));
												personalRegister.setPreviousDateAndTime(stateHistory.getDateInfo());
											}
										}

										if (nextStateHistory != null) {
											if (!nextStateHistory.getValue().equalsIgnoreCase(APPLICATION_STATUS_RESCHEDULED)) {
												history = nextStateHistory;
											} else {
												try {
													history = application.getStateHistory().get(i + 2);
												} catch (Exception e) {
													history = null;
												}
											}
										} else
											history = null;

										if (history != null)
											buildDispatchDetails2(history, personalRegister);
										else
											currentState = buildDispatchDetails3(application, personalRegister, currentState);
										personalRegisterHelperList.add(personalRegister);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private State<Position> buildHistoryWhenTransitionHappenTwiceWithSameOwner(List<PersonalRegisterHelper> personalRegisterHelperList, BpaApplication application, State<Position> currentState, int i, StateHistory stateHistory, StateHistory<Position> nextStateHistory, Long secondPreviousStateScrutizedBy) {
		StateHistory<Position> secondPreviousStateHistory;
		PersonalRegisterHelper personalRegister = new PersonalRegisterHelper();
		buildPRObject(application, personalRegister);
		try {
			secondPreviousStateHistory = application.getStateHistory().get(i - 2);
		} catch (Exception e) {
			secondPreviousStateHistory = null;
		}

		if (secondPreviousStateHistory != null) {
			Long positionId = secondPreviousStateScrutizedBy != null && secondPreviousStateScrutizedBy != 0L ? secondPreviousStateScrutizedBy : secondPreviousStateHistory.getOwnerPosition().getId();
			buildDispatchDetails1(stateHistory, secondPreviousStateHistory, personalRegister, positionId);
		} else
			buildDispatchedDetails(stateHistory, personalRegister);

		if (nextStateHistory != null)
			buildDispatchDetails2(nextStateHistory, personalRegister);
		else
			currentState = buildDispatchDetails3(application, personalRegister, currentState);
		personalRegisterHelperList.add(personalRegister);
		return currentState;
	}

	private void setDefaultDispatchValues(PersonalRegisterHelper personalRegister) {
		personalRegister.setCurrentStatus("N/A");
		personalRegister.setToWhom("N/A");
		personalRegister.setNextDateAndTime(null);
	}

	private void buildDispatchDetails4(State<Position> state, PersonalRegisterHelper personalRegister) {
		List<Assignment> assignments;
		assignments = assignmentService.getAssignmentsForPosition(state.getOwnerPosition().getId(), state.getLastModifiedDate());
		personalRegister.setCurrentStatus(setRevertedByStatus(state.getExtraInfo(), state.getValue() == null ? EMPTY : state.getValue()));
		personalRegister.setToWhom(!assignments.isEmpty() ? assignments.get(0).getEmployee().getName().concat(" :: ").concat(assignments.get(0).getDesignation().getName()) : "N/A");
		personalRegister.setNextDateAndTime(state.getDateInfo());
	}

	private State<Position> buildDispatchDetails3(BpaApplication application, PersonalRegisterHelper personalRegister, State<Position> currentState) {
		List<Assignment> assignments;
		assignments = assignmentService.getAssignmentsForPosition(currentState.getOwnerPosition().getId(), application.getState().getOwnerPosition().getLastModifiedDate());
		personalRegister.setToWhom(!assignments.isEmpty() ? assignments.get(0).getEmployee().getName().concat(" :: ").concat(assignments.get(0).getDesignation().getName()) : "N/A");
		personalRegister.setCurrentStatus(setRevertedByStatus(currentState.getExtraInfo(), currentState.getValue()));
		personalRegister.setNextDateAndTime(currentState.getDateInfo());
		return currentState;
	}

	private void buildDispatchDetails2(StateHistory<Position> secondNextStateHistory, PersonalRegisterHelper personalRegister) {
		List<Assignment> assignments;
		assignments = assignmentService.getAssignmentsForPosition(secondNextStateHistory.getOwnerPosition().getId(), secondNextStateHistory.getLastModifiedDate());
		personalRegister.setToWhom(!assignments.isEmpty() ? assignments.get(0).getEmployee().getName().concat(" :: ").concat(assignments.get(0).getDesignation().getName()) : "N/A");
		personalRegister.setCurrentStatus(setRevertedByStatus(secondNextStateHistory.getExtraInfo(), secondNextStateHistory.getValue()));
		personalRegister.setNextDateAndTime(secondNextStateHistory.getDateInfo());
	}

	private void buildDispatchDetails1(StateHistory<Position> previousStateHistory, StateHistory<Position> secondPreviousStateHistory, PersonalRegisterHelper personalRegister, Long positionId) {
		List<Assignment> assignments;
		assignments = assignmentService.getAssignmentsForPosition(positionId, secondPreviousStateHistory.getLastModifiedDate());
		personalRegister.setFromWhom(!assignments.isEmpty() ? assignments.get(0).getEmployee().getName().concat(" :: ").concat(assignments.get(0).getDesignation().getName()) : "N/A");
		personalRegister.setPreviousStatus(setRevertedByStatus(previousStateHistory.getExtraInfo(), previousStateHistory.getValue()));
		personalRegister.setPreviousDateAndTime(previousStateHistory.getDateInfo());
	}

	private void buildDispatchedDetails(StateHistory stateHistory, PersonalRegisterHelper personalRegister) {
		Long documentScrutinizedBy;
		List<Assignment> assignments;
		documentScrutinizedBy = getDocumentScrutinizedBy(stateHistory.getExtraInfo());
		Long positionId = documentScrutinizedBy != null && documentScrutinizedBy != 0L ? documentScrutinizedBy : stateHistory.getOwnerPosition().getId();
		assignments = assignmentService.getAssignmentsForPosition(positionId, stateHistory.getLastModifiedDate());
		personalRegister.setFromWhom(!assignments.isEmpty() ? assignments.get(0).getEmployee().getName().concat(" :: ").concat(assignments.get(0).getDesignation().getName()) : "N/A");
		personalRegister.setPreviousStatus(setRevertedByStatus(stateHistory.getExtraInfo(), stateHistory.getValue()));
		personalRegister.setPreviousDateAndTime(stateHistory.getDateInfo());
	}


	private void buildStateLevelPrObject(PersonalRegisterHelper personalRegister, BpaApplication application, State<Position> state) {
		List<Assignment> assignments;
		buildPRObject(application, personalRegister);
		Optional<StateHistory<Position>> stateHistory = bpaWorkFlowService.getLastStateHstryObj(application.getStateHistory());
		if (stateHistory.isPresent()) {
			Long documentScrutinizedBy = getDocumentScrutinizedBy(state.getExtraInfo());
			Long positionId = documentScrutinizedBy != null && documentScrutinizedBy != 0L ? documentScrutinizedBy : stateHistory.get().getOwnerPosition().getId();
			assignments = assignmentService.getAssignmentsForPosition(positionId, stateHistory.get().getOwnerPosition().getLastModifiedDate());
			personalRegister.setFromWhom(!assignments.isEmpty() ? assignments.get(0).getEmployee().getName().concat(" :: ").concat(assignments.get(0).getDesignation().getName()) : "N/A");
			personalRegister.setPreviousStatus(setRevertedByStatus(state.getExtraInfo(), state.getValue()));
			personalRegister.setPreviousDateAndTime(state.getDateInfo());
		}
		if (!state.getValue().equalsIgnoreCase(CLOSED))
			setDefaultDispatchValues(personalRegister);
		else {
			assignments = assignmentService.getAssignmentsForPosition(state.getOwnerPosition().getId(), state.getLastModifiedDate());
			personalRegister.setCurrentStatus(setRevertedByStatus(state.getExtraInfo(), state.getValue() == null ? EMPTY : state.getValue()));
			personalRegister.setToWhom(!assignments.isEmpty() ? assignments.get(0).getEmployee().getName().concat(" :: ").concat(assignments.get(0).getDesignation().getName()) : "N/A");
			personalRegister.setNextDateAndTime(state.getDateInfo());
		}
	}

	private void buildPRObject(BpaApplication application, PersonalRegisterHelper personalRegister) {
		personalRegister.setApplicationNumber(application.getApplicationNumber());
		personalRegister.setApplicantName(application.getOwner().getName());
		personalRegister.setAddress(application.getOwner().getAddress());
		personalRegister.setPermitType(application.getIsOneDayPermitApplication() ? APPLICATION_TYPE_ONEDAYPERMIT  : APPLICATION_TYPE_REGULAR);
		personalRegister.setDateOfAdmission(application.getApplicationDate());
		personalRegister.setNatureOfOccupancy(application.getOccupanciesName());
		personalRegister.setFar(searchBpaApplicationService.getFar(application));
		personalRegister.setApplicationType(application.getServiceType().getDescription());
		for (SiteDetail siteDetail : application.getSiteDetail()) {
			personalRegister.setSurveyNo(siteDetail.getReSurveyNumber());
			personalRegister.setVillage(siteDetail.getLocationBoundary() == null ? "" : siteDetail.getLocationBoundary().getName());
			personalRegister.setElectionWard(siteDetail.getElectionBoundary().getName());
			personalRegister.setRevenueWard(siteDetail.getAdminBoundary().getName());
		}
		for (BuildingDetail buildingDetail : application.getBuildingDetail()) {
			personalRegister.setNoOfFloors(buildingDetail.getFloorCount());
			BigDecimal totalFloorArea = bpaCalculationService.getTotalFloorArea(application);
			BigDecimal existBldgFloorArea = bpaCalculationService.getExistBldgTotalFloorArea(application);
			totalFloorArea = totalFloorArea.add(existBldgFloorArea);
			personalRegister.setTotalFloarArea(totalFloorArea.setScale(3, RoundingMode.HALF_UP));
		}
	}

	private Long getDocumentScrutinizedBy(final String extraInfo) {

		JSONParser parser = new JSONParser();
		JSONObject json = null;
		try {
			if (StringUtils.isNotEmpty(extraInfo))
				json = (JSONObject) parser.parse(extraInfo);
			if (json == null || json.get("scrutinizedBy") == null)
				if (extraInfo != null && extraInfo.contains("scrutinizedBy"))
					json = (JSONObject) parser.parse(extraInfo);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return json == null || json.get("scrutinizedBy") == null ? 0 : Long.valueOf(json.get("scrutinizedBy").toString());
	}

	private String setRevertedByStatus(String extraInfo, String value) {
		String revertedBy = bpaWorkFlowService.getRevertedBy(extraInfo);
		return !isBlank(revertedBy) ? revertedBy : value;
	}

}
