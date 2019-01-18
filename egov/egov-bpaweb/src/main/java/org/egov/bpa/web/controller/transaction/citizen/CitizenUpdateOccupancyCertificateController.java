/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) <2018>  eGovernments Foundation
 *
 *  The updated version of eGov suite of products as by eGovernments Foundation
 *  is available at http://www.egovernments.org
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see http://www.gnu.org/licenses/ or
 *  http://www.gnu.org/licenses/gpl.html .
 *
 *  In addition to the terms of the GPL license to be adhered to in using this
 *  program, the following additional terms are to be complied with:
 *
 *      1) All versions of this program, verbatim or modified must carry this
 *         Legal Notice.
 *      Further, all user interfaces, including but not limited to citizen facing interfaces,
 *         Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *         derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *      For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *      For any further queries on attribution, including queries on brand guidelines,
 *         please contact contact@egovernments.org
 *
 *      2) Any misrepresentation of the origin of the material is prohibited. It
 *         is required that all modified versions of this material be marked in
 *         reasonable ways as different from the original version.
 *
 *      3) This license does not grant any rights to any user of the program
 *         with regards to rights under trademark law for use of the trade names
 *         or trademarks of eGovernments Foundation.
 *
 *  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.bpa.web.controller.transaction.citizen;

import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.entity.enums.AppointmentSchedulePurpose;
import org.egov.bpa.transaction.entity.oc.OCAppointmentSchedule;
import org.egov.bpa.transaction.entity.oc.OCLetterToParty;
import org.egov.bpa.transaction.entity.oc.OCSlot;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.service.collection.GenericBillGeneratorService;
import org.egov.bpa.transaction.service.oc.OCAppointmentScheduleService;
import org.egov.bpa.transaction.service.oc.OCInspectionService;
import org.egov.bpa.transaction.service.oc.OCLetterToPartyService;
import org.egov.bpa.transaction.service.oc.OccupancyCertificateService;
import org.egov.bpa.web.controller.transaction.BpaGenericApplicationController;
import org.egov.eis.service.PositionMasterService;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.utils.DateUtils;
import org.egov.infra.workflow.matrix.entity.WorkFlowMatrix;
import org.egov.pims.commons.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_HISTORY;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_CREATED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_DOC_VERIFIED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_RESCHEDULED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_SCHEDULED;
import static org.egov.bpa.utils.BpaConstants.CREATE_ADDITIONAL_RULE_CREATE_OC;
import static org.egov.bpa.utils.BpaConstants.DISCLIMER_MESSAGE_ONSAVE;
import static org.egov.bpa.utils.BpaConstants.WF_CANCELAPPLICATION_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_LBE_SUBMIT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_NEW_STATE;

@Controller
@RequestMapping(value = "/application/citizen")
public class CitizenUpdateOccupancyCertificateController extends BpaGenericApplicationController {

	public static final String OCCUPANCY_CERTIFICATE_UPDATE = "citizen-occupancy-certificate-update";
	public static final String CITIZEN_OCCUPANCY_CERTIFICATE_VIEW = "citizen-occupancy-certificate-view";
	private static final String ONLINE_PAYMENT_ENABLE = "onlinePaymentEnable";
	private static final String WORK_FLOW_ACTION = "workFlowAction";
	private static final String TRUE = "TRUE";
	private static final String CITIZEN_OR_BUSINESS_USER = "citizenOrBusinessUser";
	private static final String IS_CITIZEN = "isCitizen";
	private static final String OFFICIAL_NOT_EXISTS = "No officials assigned to process this application.";
	private static final String MSG_PORTAL_FORWARD_REGISTRATION = "msg.portal.forward.registration";
	private static final String MESSAGE = "message";
	private static final String BPAAPPLICATION_CITIZEN = "citizen_suceess";
	private static final String COMMON_ERROR = "common-error";
	private static final String ADDITIONALRULE = "additionalRule";
	@Autowired
	private GenericBillGeneratorService genericBillGeneratorService;
	@Autowired
	private PositionMasterService positionMasterService;
	@Autowired
	private OccupancyCertificateService occupancyCertificateService;
	@Autowired
	private OCAppointmentScheduleService ocAppointmentScheduleService;
	@Autowired
	private OCLetterToPartyService ocLetterToPartyService;
	@Autowired
	private OCInspectionService ocInspectionService;


	@GetMapping("/occupancy-certificate/update/{applicationNumber}")
	public String showNewApplicationForm(@PathVariable final String applicationNumber, final Model model, final HttpServletRequest request) {
		OccupancyCertificate oc = occupancyCertificateService.findByApplicationNumber(applicationNumber);
		setCityName(model, request);
		prepareFormData(oc, model);
		prepareCommonModelAttribute(model, oc.isCitizenAccepted());
		loadData(oc, model);
		model.addAttribute("occupancyCertificate", oc);
		if (APPLICATION_STATUS_CREATED.equals(oc.getStatus().getCode()))
			return OCCUPANCY_CERTIFICATE_UPDATE;
		else {
			model.addAttribute("bpaApplication", oc.getParent());
			return CITIZEN_OCCUPANCY_CERTIFICATE_VIEW;
		}
	}

	private void loadData(OccupancyCertificate oc, Model model) {
		List<OCLetterToParty> ocLetterToParties = ocLetterToPartyService.findAllByOC(oc);
		if (!ocLetterToParties.isEmpty() && ocLetterToParties.get(0).getLetterToParty().getSentDate() != null)
			model.addAttribute("mode", "showLPDetails");
		model.addAttribute("letterToPartyList", ocLetterToParties);
		if (APPLICATION_STATUS_SCHEDULED.equals(oc.getStatus().getCode())
			|| APPLICATION_STATUS_RESCHEDULED.equals(oc.getStatus().getCode())
			   && !oc.getRescheduledByCitizen()) {
			model.addAttribute("mode", "showRescheduleToCitizen");
		}
		model.addAttribute("inspectionList", ocInspectionService.findByOcOrderByIdAsc(oc));
		buildAppointmentDetailsOfScrutinyAndInspection(model, oc);
		buildReceiptDetails(oc.getDemand().getEgDemandDetails(), new HashSet<>());
		model.addAttribute(APPLICATION_HISTORY,
				workflowHistoryService.getHistoryForOC(oc.getAppointmentSchedules(), oc.getCurrentState(), oc.getStateHistory()));
	}

	private void prepareFormData(final OccupancyCertificate oc, final Model model) {
		model.addAttribute("stateType", oc.getClass().getSimpleName());
		model.addAttribute(ADDITIONALRULE, CREATE_ADDITIONAL_RULE_CREATE_OC);
		model.addAttribute("currentState", oc.getCurrentState() == null ? "" : oc.getCurrentState().getValue());
	}

	private void setCityName(final Model model, final HttpServletRequest request) {
		if (request.getSession().getAttribute("cityname") != null)
			model.addAttribute("cityName", request.getSession().getAttribute("cityname"));
	}

	@PostMapping("/occupancy-certificate/update-submit")
	public String createNewConnection(@Valid @ModelAttribute("occupancyCertificate") final OccupancyCertificate occupancyCertificate,
									  final HttpServletRequest request, final Model model,
									  final BindingResult errors) {
		if (errors.hasErrors())
			return OCCUPANCY_CERTIFICATE_UPDATE;

		WorkflowBean wfBean = new WorkflowBean();
		Long userPosition = null;
		String workFlowAction = request.getParameter(WORK_FLOW_ACTION);
		Boolean isCitizen = request.getParameter(IS_CITIZEN) != null
							&& request.getParameter(IS_CITIZEN)
									  .equalsIgnoreCase(TRUE) ? Boolean.TRUE : Boolean.FALSE;
		Boolean citizenOrBusinessUser = request.getParameter(CITIZEN_OR_BUSINESS_USER) != null
										&& request.getParameter(CITIZEN_OR_BUSINESS_USER)
												  .equalsIgnoreCase(TRUE) ? Boolean.TRUE : Boolean.FALSE;
		Boolean onlinePaymentEnable = request.getParameter(ONLINE_PAYMENT_ENABLE) != null
									  && request.getParameter(ONLINE_PAYMENT_ENABLE)
												.equalsIgnoreCase(TRUE) ? Boolean.TRUE : Boolean.FALSE;
		final WorkFlowMatrix wfMatrix = bpaUtils.getWfMatrixByCurrentState(occupancyCertificate.getStateType(), WF_NEW_STATE);
		if (wfMatrix != null)
			userPosition = bpaUtils.getUserPositionIdByZone(wfMatrix.getNextDesignation(),
					occupancyCertificate.getParent().getSiteDetail().get(0) != null
					&& occupancyCertificate.getParent().getSiteDetail().get(0).getElectionBoundary() != null
					? occupancyCertificate.getParent().getSiteDetail().get(0).getElectionBoundary().getId() : null);
		if (citizenOrBusinessUser && workFlowAction != null
			&& workFlowAction.equals(WF_LBE_SUBMIT_BUTTON)
			&& (userPosition == 0 || userPosition == null)) {
			model.addAttribute("noJAORSAMessage", OFFICIAL_NOT_EXISTS);
			return OCCUPANCY_CERTIFICATE_UPDATE;
		}

		wfBean.setWorkFlowAction(request.getParameter(WORK_FLOW_ACTION));
		OccupancyCertificate ocResponse = occupancyCertificateService.saveOrUpdate(occupancyCertificate, wfBean);
		bpaUtils.updatePortalUserinbox(ocResponse, null);
		if (workFlowAction != null
			&& workFlowAction
					.equals(WF_LBE_SUBMIT_BUTTON)
			&& !bpaUtils.logedInuserIsCitizen()) {
			Position pos = positionMasterService.getPositionById(ocResponse.getCurrentState().getOwnerPosition().getId());
			User wfUser = workflowHistoryService.getUserPositionByPassingPosition(pos.getId());
			String message = messageSource.getMessage(MSG_PORTAL_FORWARD_REGISTRATION, new String[]{
					wfUser == null ? "" : wfUser.getUsername().concat("~")
												.concat(getDesinationNameByPosition(pos)),
					ocResponse.getApplicationNumber()}, LocaleContextHolder.getLocale());

			message = message.concat(DISCLIMER_MESSAGE_ONSAVE);
			model.addAttribute(MESSAGE, message);
		} else if (workFlowAction != null && workFlowAction.equals(WF_CANCELAPPLICATION_BUTTON))
			model.addAttribute(MESSAGE, messageSource.getMessage("msg.occupancy.certificate.cancel.applnby.applicantitself.success", new String[]{ocResponse.getApplicationNumber()}, null));
		else
			model.addAttribute(MESSAGE,
					messageSource.getMessage("msg.occupancy.certificate.appln.saved.succes", new String[]{ocResponse.getApplicationNumber()}, null));
		if (workFlowAction != null
			&& workFlowAction
					.equals(WF_LBE_SUBMIT_BUTTON)
			&& onlinePaymentEnable && bpaUtils.checkAnyTaxIsPendingToCollect(occupancyCertificate.getDemand())) {
			return genericBillGeneratorService
					.generateBillAndRedirectToCollection(occupancyCertificate, model);
		}
		return BPAAPPLICATION_CITIZEN;
	}

	private void buildAppointmentDetailsOfScrutinyAndInspection(Model model, OccupancyCertificate oc) {
		if (APPLICATION_STATUS_SCHEDULED.equals(oc.getStatus().getCode())
			|| APPLICATION_STATUS_RESCHEDULED.equals(oc.getStatus().getCode())) {
			Optional<OCSlot> activeSlotApplication = oc.getOcSlots().stream().reduce((slotAppln1, slotAppln2) -> slotAppln2);
			if (activeSlotApplication.isPresent()) {
				model.addAttribute("appointmentDateRes", DateUtils.toDefaultDateFormat(activeSlotApplication.get().getSlotDetail().getSlot().getAppointmentDate()));
				model.addAttribute("appointmentTimeRes", activeSlotApplication.get().getSlotDetail().getAppointmentTime());
				model.addAttribute("appointmentTitle", messageSource.getMessage("msg.appointment.details.for.docscrutiny", null, null));
			}
		} else if (APPLICATION_STATUS_DOC_VERIFIED.equals(oc.getStatus().getCode()) && oc.getInspections().isEmpty()) {
			List<OCAppointmentSchedule> appointmentScheduledList = ocAppointmentScheduleService.findByApplication(oc,
					AppointmentSchedulePurpose.INSPECTION);
			if (!appointmentScheduledList.isEmpty()) {
				model.addAttribute("appointmentDateRes", DateUtils.toDefaultDateFormat(appointmentScheduledList.get(0).getAppointmentScheduleCommon().getAppointmentDate()));
				model.addAttribute("appointmentTimeRes", appointmentScheduledList.get(0).getAppointmentScheduleCommon().getAppointmentTime());
				model.addAttribute("appmntInspnRemarks", appointmentScheduledList.get(0).getAppointmentScheduleCommon().isPostponed() ? appointmentScheduledList.get(0).getAppointmentScheduleCommon().getPostponementReason() : appointmentScheduledList.get(0).getAppointmentScheduleCommon().getRemarks());
				model.addAttribute("appointmentTitle", messageSource.getMessage("msg.appointment.details.for.fieldinspec", null, null));
			}
		}
	}
}