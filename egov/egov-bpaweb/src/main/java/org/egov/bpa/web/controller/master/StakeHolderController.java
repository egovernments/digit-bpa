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

package org.egov.bpa.web.controller.master;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.egov.infra.utils.JsonUtils.toJSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.egov.bpa.master.entity.StakeHolder;
import org.egov.bpa.master.entity.StakeHolderState;
import org.egov.bpa.master.entity.enums.StakeHolderStatus;
import org.egov.bpa.master.service.StakeHolderAuditService;
import org.egov.bpa.master.service.StakeHolderService;
import org.egov.bpa.master.service.StakeHolderStateService;
import org.egov.bpa.master.service.StakeholderTypeService;
import org.egov.bpa.service.es.StakeHolderIndexService;
import org.egov.bpa.transaction.entity.StakeHolderDocument;
import org.egov.bpa.transaction.entity.dto.SearchStakeHolderForm;
import org.egov.bpa.transaction.service.BPADocumentService;
import org.egov.bpa.transaction.service.collection.GenericBillGeneratorService;
import org.egov.bpa.transaction.service.messaging.BPASmsAndEmailService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.web.controller.adaptor.SearchStakeHolderJsonAdaptor;
import org.egov.bpa.web.controller.adaptor.StakeHolderJsonAdaptor;
import org.egov.commons.entity.Source;
import org.egov.eis.web.contract.WorkflowContainer;
import org.egov.eis.web.controller.workflow.GenericWorkFlowController;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.admin.master.service.UserService;
import org.egov.infra.persistence.entity.Address;
import org.egov.infra.persistence.entity.CorrespondenceAddress;
import org.egov.infra.persistence.entity.PermanentAddress;
import org.egov.infra.persistence.entity.enums.AddressType;
import org.egov.infra.persistence.entity.enums.Gender;
import org.egov.infra.persistence.entity.enums.UserType;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.security.utils.captcha.CaptchaUtils;
import org.egov.infra.workflow.entity.StateAware;
import org.egov.portal.service.CitizenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/stakeholder")
public class StakeHolderController extends GenericWorkFlowController {
	private static final String APPLICATION_HISTORY = "applicationHistory";
	private static final String RDRCT_STKHLDR_RSLT = "redirect:/stakeholder/result/";
	private static final String MESSAGE = "message";
	private static final String STAKEHOLDER_RESULT = "stakeholder-result";
	private static final String SEARCH_STAKEHOLDER_EDIT = "search-stakeholder-edit";
	private static final String STAKE_HOLDER = "stakeHolder";
	private static final String STAKEHOLDER_UPDATE = "stakeholder-update";
	private static final String STAKEHOLDER_VIEW = "stakeholder-view";
	private static final String SEARCH_STAKEHOLDER_VIEW = "search-stakeholder-view";
	private static final String DATA = "{ \"data\":";
	private static final String STAKEHOLDER_NEW = "stakeholder-new";
	private static final String STAKEHOLDER_NEW_BY_CITIZEN = "stakeholder-new-bycitizen";
	private static final String SRCH_STKHLDR_FR_APPRVL = "search-stakeholder-forapproval";
	private static final String STAKE_HOLDER_FORM = "searchStakeHolderForm";
	private static final String STKHLDR_APPROVE = "stakeholder-approve";
	public static final String STAKE_HOLDER_TYPES = "stakeHolderTypes";
	public static final String STAKE_HOLDER_STATUS_LIST = "stakeHolderStatusList";
	public static final String COMMON_ERROR = "common-error";
	private static final String STAKEHOLDERSTATE_VIEW = "stakeholderstate-view";
	@Autowired
	private StakeHolderService stakeHolderService;
	@Autowired
	private StakeholderTypeService stakeholderTypeService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private BPADocumentService bpaDocumentService;
	@Autowired
	private CitizenService citizenService;
	@Autowired
	private BPASmsAndEmailService bpaSmsAndEmailService;
	@Autowired
	private UserService userService;
	@Autowired
	private SecurityUtils securityUtils;
	@Autowired
	private CaptchaUtils captchaUtils;
	@Autowired
	private StakeHolderAuditService stakeHolderAuditService;
	@Autowired
	private StakeHolderIndexService stakeHolderIndexService;
	@Autowired
	private StakeHolderStateService stakeHolderStateService;
	@Autowired
	private AppConfigValueService appConfigValueService;
	@Autowired
	private GenericBillGeneratorService genericBillGeneratorService;

	@ModelAttribute("stakeHolderDocumentList")
	public List<StakeHolderDocument> getStakeHolderDocuments() {
		return bpaDocumentService.getStakeHolderDocuments();
	}

	@GetMapping("/create")
	public String showStakeHolder(final Model model) {
		StakeHolder stakeHolder = new StakeHolder();
		prepareModel(model, stakeHolder);
		model.addAttribute("mode", "new");
		return STAKEHOLDER_NEW;
	}

	private void prepareModel(final Model model, final StakeHolder stakeHolder) {

		StakeHolderState stakeHolderState = new StakeHolderState();
		stakeHolder.setActive(true);
		stakeHolderState.setStakeHolder(stakeHolder);
		model.addAttribute(STAKE_HOLDER, stakeHolderState.getStakeHolder());
		// check why is this required
		model.addAttribute("isEmployee",
				securityUtils.getCurrentUser().getType().equals(UserType.EMPLOYEE)
						|| "egovernments".equals(securityUtils.getCurrentUser().getUsername()) ? Boolean.TRUE
								: Boolean.FALSE);
		// is this correct?
		model.addAttribute("isBusinessUser",
				securityUtils.getCurrentUser().getType().equals(UserType.SYSTEM) ? Boolean.TRUE : Boolean.FALSE);
		model.addAttribute("genderList", Arrays.asList(Gender.values()));
		model.addAttribute(STAKE_HOLDER_TYPES, stakeholderTypeService.findAllIsActive());
		model.addAttribute("isOnbehalfOfOrganization", false);
	}

	@PostMapping("/create")
	public String createStakeholder(@ModelAttribute(STAKE_HOLDER) final StakeHolder stakeHolder, final Model model,
			final HttpServletRequest request, final BindingResult errors, final RedirectAttributes redirectAttributes) {
		if (stakeHolder.getStakeHolderType() == null)
			stakeHolder.setStakeHolderType(
					stakeholderTypeService.findOne(Long.valueOf(request.getParameter("stakeHolderType"))));
		StakeHolderState stakeHolderState = new StakeHolderState();
		stakeHolderState.setStakeHolder(stakeHolder);
		validateStakeholder(stakeHolder, errors);
		if (errors.hasErrors() || checkIsUserExists(stakeHolder, model)) {
			prepareModel(model, stakeHolder);
			return STAKEHOLDER_NEW;
		}
		stakeHolder.setSource(Source.SYSTEM);
		StakeHolder stakeHolderRes = stakeHolderService.save(stakeHolder);
		stakeHolderIndexService.updateIndexes(stakeHolderRes);
		bpaSmsAndEmailService.sendSMSForStakeHolder(stakeHolderRes, false);
		bpaSmsAndEmailService.sendEmailForStakeHolder(stakeHolderRes, false);
		redirectAttributes.addFlashAttribute(MESSAGE, messageSource.getMessage("msg.approve.stakeholder.success",
				new String[] { stakeHolder.getStakeHolderType().getName() }, null));
		return RDRCT_STKHLDR_RSLT + stakeHolderRes.getId();
	}

	@GetMapping("/createbycitizen")
	public String showOnlineStakeHolder(final Model model) {
		StakeHolder stakeHolder = new StakeHolder();
		stakeHolder.setSource(Source.ONLINE);
		stakeHolder.setActive(false);
		model.addAttribute("showNotification", true);
		prepareModel(model, stakeHolder);
		model.addAttribute("mode", "new");
		return STAKEHOLDER_NEW_BY_CITIZEN;
	}

	@GetMapping("/payregfee/{acknum}")
	public String payRegFee(final Model model, @PathVariable final String acknum, HttpServletRequest request) {

		StakeHolder stakeHolder = stakeHolderService.findByCode(acknum);
		if (stakeHolder == null) {
			model.addAttribute(MESSAGE,
					messageSource.getMessage("msg.invalid.acknowledgement.num", new String[] {}, null));
			return COMMON_ERROR;
		}
		List<AppConfigValues> appConfigValueList = appConfigValueService.getConfigValuesByModuleAndKey(
				BpaConstants.APPLICATION_MODULE_TYPE, "BUILDING_LICENSEE_REG_FEE_REQUIRED");
		if ((appConfigValueList.isEmpty() ? "" : appConfigValueList.get(0).getValue()).equalsIgnoreCase("YES")) {
			if (stakeHolder.getStatus() != null
					&& BpaConstants.APPLICATION_STATUS_PENDNING.equalsIgnoreCase(stakeHolder.getStatus().toString())) {
				return genericBillGeneratorService.generateBillAndRedirectToCollection(stakeHolder, model);
			} else if (stakeHolder.getStatus() != null
					&& BpaConstants.APPLICATION_STATUS_SUBMITTED.equalsIgnoreCase(stakeHolder.getStatus().toString())
					|| "RE_SUBMITTED".equalsIgnoreCase(stakeHolder.getStatus().toString())) {
				model.addAttribute(MESSAGE, messageSource.getMessage("msg.stakeholder.not.approved",
						new String[] { acknum, stakeHolder.getStakeHolderType().getName() }, null));
				return COMMON_ERROR;
			} else if (stakeHolder.getStatus() != null
					&& BpaConstants.APPLICATION_STATUS_SUBMITTED.equalsIgnoreCase(stakeHolder.getStatus().toString())) {
				model.addAttribute(MESSAGE,
						messageSource.getMessage("msg.stakeholder.payment.done", new String[] {}, null));
				return COMMON_ERROR;
			} else {
				model.addAttribute(MESSAGE, messageSource.getMessage("msg.stakeholder.pay.fee.paid",
						new String[] { acknum, stakeHolder.getLicenceNumber() }, null));
				return COMMON_ERROR;
			}
		}
		model.addAttribute(MESSAGE,
				messageSource.getMessage("msg.stakeholder.pay.fee.generic.issue", new String[] {}, null));
		return COMMON_ERROR;
	}

	@PostMapping("/createbycitizen")
	public String createOnlineStakeholder(@ModelAttribute(STAKE_HOLDER) final StakeHolder stakeHolder,
			final Model model, final BindingResult errors, final RedirectAttributes redirectAttributes,
			HttpServletRequest request) {

		if (!captchaUtils.captchaIsValid(request))
			errors.reject("captcha.not.valid");

		StakeHolder existingStakeholder = stakeHolderService.validateStakeHolderIsRejected(
				stakeHolder.getMobileNumber(), stakeHolder.getEmailId(), stakeHolder.getAadhaarNumber(),
				stakeHolder.getPan(), stakeHolder.getLicenceNumber());
		if (existingStakeholder == null)
			validateStakeholder(stakeHolder, errors);
		if (securityUtils.getCurrentUser().getType().equals(UserType.SYSTEM)
				&& !citizenService.isValidOTP(stakeHolder.getActivationCode(), stakeHolder.getMobileNumber()))
			errors.rejectValue("activationCode", "error.otp.verification.failed");

		if (errors.hasErrors()) {
			prepareModel(model, stakeHolder);
			return STAKEHOLDER_NEW_BY_CITIZEN;
		}
		try {
			stakeHolder.setStatus(StakeHolderStatus.SUBMITTED);
			stakeHolder.setSource(Source.ONLINE);
			stakeHolder.setActive(false);
			StakeHolder stakeHolderRes = null;
			if (existingStakeholder == null)

				stakeHolderRes = stakeHolderService.save(stakeHolder);

			else
				stakeHolderRes = stakeHolderService.updateOnResubmit(stakeHolder, existingStakeholder);

			stakeHolderIndexService.updateIndexes(stakeHolderRes);
			bpaSmsAndEmailService.sendSMSForStakeHolder(stakeHolderRes, true);
			bpaSmsAndEmailService.sendEmailForStakeHolder(stakeHolderRes, true);

			if (stakeHolder.getStatus().getStakeHolderStatusVal().equalsIgnoreCase("Approved")) {
				redirectAttributes.addFlashAttribute(MESSAGE,
						messageSource.getMessage("msg.update.stakeholder.success", null, null));
			} else {
				redirectAttributes.addFlashAttribute(MESSAGE,
						messageSource.getMessage(
								"msg.create.stakeholder.citizen.success", new String[] {
										stakeHolderRes.getStakeHolderType().getName(), stakeHolderRes.getCode() },
								null));
			}
			return RDRCT_STKHLDR_RSLT + stakeHolderRes.getId();
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", e.getMessage());
			prepareModel(model, stakeHolder);
			return STAKEHOLDER_NEW_BY_CITIZEN;

		}
	}

	private boolean checkIsUserExists(@ModelAttribute(STAKE_HOLDER) StakeHolder stakeHolder, Model model) {
		List<User> users = userService.getUserByMobileNumberAndType(stakeHolder.getMobileNumber(), UserType.BUSINESS);
		if (!users.isEmpty()) {
			String message = messageSource
					.getMessage(
							"msg.name.mobile.exists", new String[] { users.get(0).getName(),
									users.get(0).getMobileNumber(), users.get(0).getGender().name() },
							LocaleContextHolder.getLocale());
			model.addAttribute("invalidBuildingLicensee", message);
			prepareModel(model, stakeHolder);
			return true;
		}
		return false;
	}

	private void validateStakeholder(final StakeHolder stakeHolder, final BindingResult errors) {
		// this suppose to be license number
		if (stakeHolderService.checkIsStakeholderCodeAlreadyExists(stakeHolder)) {
			errors.rejectValue("code", "msg.code.exists");
		}
		if (stakeHolderService.checkIsEmailAlreadyExists(stakeHolder)) {
			errors.rejectValue("emailId", "msg.email.exists");
		}
	}

	@GetMapping("/update/{id}")
	public String editStakeholder(@PathVariable final Long id, final Model model) {
		final StakeHolder stakeHolder;
		StakeHolderState stakeHolderstate = stakeHolderStateService.findByStakeHolderId(id);
		if (stakeHolderstate != null) {
			stakeHolder = stakeHolderstate.getStakeHolder();
			model.addAttribute("stateType", stakeHolderstate.getStateType());
		} else {
			stakeHolder = stakeHolderService.findById(id);
		}
		preapreUpdateModel(stakeHolder, model);
		model.addAttribute("mode", "edit");
		model.addAttribute(STAKE_HOLDER_TYPES, stakeholderTypeService.findAllIsActive());
		model.addAttribute("stakeHolderDocumentList", stakeHolder.getStakeHolderDocument());
		model.addAttribute(APPLICATION_HISTORY,
				stakeHolderAuditService.getStakeholderUpdateHistory(stakeHolder.getId()));
		if (stakeHolderstate != null && stakeHolderstate.transitionInprogress()) {
			final WorkflowContainer workflowContainer = new WorkflowContainer();
			workflowContainer.setPendingActions(stakeHolderstate.getCurrentState().getNextAction());
			prepareWorkflow(model, stakeHolderstate, workflowContainer);
			return STAKEHOLDERSTATE_VIEW;
		}
		return STAKEHOLDER_UPDATE;
	}

	private void preapreUpdateModel(final StakeHolder stakeHolder, final Model model) {
		for (final Address address : stakeHolder.getAddress())
			if (AddressType.CORRESPONDENCE.equals(address.getType()))
				stakeHolder.setCorrespondenceAddress((CorrespondenceAddress) address);
			else
				stakeHolder.setPermanentAddress((PermanentAddress) address);
		model.addAttribute("isEmployee",
				securityUtils.getCurrentUser().getType().equals(UserType.EMPLOYEE)
						|| "egovernments".equals(securityUtils.getCurrentUser().getUsername()) ? Boolean.TRUE
								: Boolean.FALSE);
		model.addAttribute(STAKE_HOLDER, stakeHolder);
	}

	@PostMapping("/update")
	public String updateStakeholder(@ModelAttribute(STAKE_HOLDER) final StakeHolder stakeHolder, final Model model,
			final HttpServletRequest request, final BindingResult errors, final RedirectAttributes redirectAttributes) {

		if (errors.hasErrors()) {
			preapreUpdateModel(stakeHolder, model);
			return STAKEHOLDER_UPDATE;
		}
		String workFlowAction = request.getParameter("workFlowAction");
		StakeHolder stakeHolderRes = stakeHolderService.update(stakeHolder, workFlowAction);
		stakeHolderIndexService.updateIndexes(stakeHolderRes);

		String message;
		if ("Block".equals(workFlowAction)) {
			message = messageSource.getMessage("msg.block.stakeholder", null, null);
			bpaSmsAndEmailService.sendSmsAndEmailOnBlockAndUnblock(stakeHolderRes);
		} else if ("Unblock".equals(workFlowAction)) {
			message = messageSource.getMessage("msg.unblock.stakeholder", null, null);
			bpaSmsAndEmailService.sendSmsAndEmailOnBlockAndUnblock(stakeHolderRes);
		} else {
			message = messageSource.getMessage("msg.update.stakeholder.success", null, null);
		}

		redirectAttributes.addFlashAttribute(MESSAGE, message);
		return RDRCT_STKHLDR_RSLT + stakeHolderRes.getId();
	}

	@GetMapping("/result/{id}")
	public String resultStakeHolder(@PathVariable final Long id, final Model model) {
		model.addAttribute(STAKE_HOLDER, stakeHolderService.findById(id));
		model.addAttribute(APPLICATION_HISTORY, stakeHolderAuditService.getStakeholderUpdateHistory(id));
		return STAKEHOLDER_RESULT;
	}

	@GetMapping("/view/{id}")
	public String viewStakeHolder(@PathVariable final Long id, final Model model) {
		model.addAttribute(STAKE_HOLDER, stakeHolderService.findById(id));
		model.addAttribute(APPLICATION_HISTORY, stakeHolderAuditService.getStakeholderUpdateHistory(id));
		return STAKEHOLDER_VIEW;
	}

	@GetMapping("/search/update")
	public String searchEditStakeHolder(final Model model) {
		model.addAttribute(STAKE_HOLDER, new StakeHolder());
		model.addAttribute(STAKE_HOLDER_TYPES, stakeholderTypeService.findAllIsActive());
		model.addAttribute(STAKE_HOLDER_STATUS_LIST, Arrays.asList(StakeHolderStatus.values()));
		model.addAttribute("mode", "edit");
		return SEARCH_STAKEHOLDER_EDIT;
	}

	@PostMapping(value = "/search/update", produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String getStakeHolderResultForEdit(@ModelAttribute final StakeHolder stakeHolder, final Model model) {
		final List<StakeHolder> searchResultList = stakeHolderService.searchForUpdate(stakeHolder);
		return new StringBuilder(DATA).append(toJSON(searchResultList, StakeHolder.class, StakeHolderJsonAdaptor.class))
				.append("}").toString();
	}

	@GetMapping("/search/view")
	public String searchViewStakeHolder(final Model model) {
		model.addAttribute(STAKE_HOLDER, new StakeHolder());
		model.addAttribute(STAKE_HOLDER_TYPES, stakeholderTypeService.findAllIsActive());
		model.addAttribute(STAKE_HOLDER_STATUS_LIST, Arrays.asList(StakeHolderStatus.values()));
		model.addAttribute("mode", "view");
		return SEARCH_STAKEHOLDER_VIEW;
	}

	@PostMapping(value = "/search/view", produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String getStakeHolderForView(@ModelAttribute final StakeHolder stakeHolder, final Model model) {
		final List<StakeHolder> searchResultList = stakeHolderService.search(stakeHolder);
		return new StringBuilder(DATA).append(toJSON(searchResultList, StakeHolder.class, StakeHolderJsonAdaptor.class))
				.append("}").toString();
	}

	@GetMapping("/search")
	public String searchStakeHolderForApprovalView(Model model) {
		model.addAttribute(STAKE_HOLDER_FORM, new SearchStakeHolderForm());
		model.addAttribute(STAKE_HOLDER_TYPES, stakeholderTypeService.findAllIsActive());
		return SRCH_STKHLDR_FR_APPRVL;
	}

	@PostMapping(value = "/search", produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String searchStakeHolderForApproval(@ModelAttribute final SearchStakeHolderForm srchStkeHldrFrm,
			final Model model) {
		List<SearchStakeHolderForm> srchStkeHldrFrmLst = stakeHolderService.searchForApproval(srchStkeHldrFrm);
		return new StringBuilder(DATA)
				.append(toJSON(srchStkeHldrFrmLst, SearchStakeHolderForm.class, SearchStakeHolderJsonAdaptor.class))
				.append("}").toString();
	}

	@GetMapping("/search/citizen/{id}")
	public String searchStakeHolderForApproval(@PathVariable final Long id, Model model) {
		StakeHolder stakeHolder = stakeHolderService.findById(id);
		model.addAttribute("approvalNotRequire",
				stakeHolder != null && stakeHolder.getBuildingLicenceExpiryDate().before(new Date()));
		if (stakeHolder != null && (StakeHolderStatus.APPROVED.equals(stakeHolder.getStatus())
				|| StakeHolderStatus.REJECTED.equals(stakeHolder.getStatus())
				|| StakeHolderStatus.BLOCKED.equals(stakeHolder.getStatus()))) {
			model.addAttribute(MESSAGE, messageSource.getMessage("msg.stakeholder.processed", new String[] {}, null));
			return COMMON_ERROR;
		}
		model.addAttribute(STAKE_HOLDER, stakeHolder);
		preapreUpdateModel(stakeHolder, model);
		model.addAttribute("stakeHolderDocumentList", stakeHolder.getStakeHolderDocument());
		model.addAttribute("isApproval", true);
		model.addAttribute(APPLICATION_HISTORY, stakeHolderAuditService.getStakeholderUpdateHistory(id));
		return STKHLDR_APPROVE;
	}

	@GetMapping("/stake/update")
	public String approveStakeHolder() {
		return STAKEHOLDER_UPDATE;
	}

	@PostMapping("/citizen/update")
	public String approveStakeHolder(@ModelAttribute final StakeHolder stakeHolder, final Model model,
			final HttpServletRequest request, final BindingResult errors, final RedirectAttributes redirectAttributes) {
		if (errors.hasErrors()) {
			return STAKEHOLDER_UPDATE;
		}
		String nextPosition = request.getParameter("approvalPosition");
		if (nextPosition != null)
			stakeHolder.setNextPosition(Long.valueOf(nextPosition));

		StakeHolderState stakeHolderState = stakeHolderStateService.findByStakeHolderId(stakeHolder.getId());
		stakeHolderState.setStakeHolder(stakeHolder);

		StakeHolder stakeHolderResponse = stakeHolderService.updateForApproval(stakeHolderState,
				stakeHolderState.getStakeHolder().getWorkFlowAction());
		stakeHolderIndexService.updateIndexes(stakeHolderResponse);

		if ("Forward".equals(stakeHolder.getWorkFlowAction())) {
			redirectAttributes.addFlashAttribute(MESSAGE,
					messageSource.getMessage("msg.forward.stakeholder",
							new String[] { (isNotBlank(stakeHolderState.getCurrentState().getComments())
									? stakeHolderResponse.getComments()
									: stakeHolderState.getCurrentState().getComments()) },
							null));
		} else if ("Approve".equals(stakeHolder.getWorkFlowAction())) {
			redirectAttributes.addFlashAttribute(MESSAGE, messageSource.getMessage("msg.approve.stakeholder.success",
					new String[] { stakeHolder.getStakeHolderType().getName() }, null));
		} else if ("Reject".equals(stakeHolder.getWorkFlowAction())) {
			bpaSmsAndEmailService.sendSMSToStkHldrForRejection(stakeHolderResponse);
			bpaSmsAndEmailService.sendEmailToStkHldrForRejection(stakeHolderResponse);
			redirectAttributes
					.addFlashAttribute(MESSAGE,
							messageSource
									.getMessage("msg.reject.stakeholder",
											new String[] { (isNotBlank(stakeHolderState.getCurrentState().getComments())
													? stakeHolderState.getCurrentState().getComments() : "Rejected") },
											null));
		} else if ("Block".equals(stakeHolder.getWorkFlowAction())) {
			bpaSmsAndEmailService.sendSmsAndEmailOnBlockAndUnblock(stakeHolderResponse);
			redirectAttributes
					.addFlashAttribute(MESSAGE,
							messageSource
									.getMessage("msg.block.stakeholder",
											new String[] { (isNotBlank(stakeHolderState.getCurrentState().getComments())
													? stakeHolderState.getCurrentState().getComments() : "Blocked") },
											null));
		}
		return RDRCT_STKHLDR_RSLT + stakeHolder.getId();
	}

	// this api is present in generic workflow class
	@Override
	protected void prepareWorkflow(final Model prepareModel, final StateAware model,
			final WorkflowContainer container) {
		if ("END".equals(model.getCurrentState().getValue())) {
			prepareModel.addAttribute("nextAction", "END");
			prepareModel.addAttribute("validActionList", new ArrayList<>());
		} else {
			prepareModel.addAttribute("approverDepartmentList", addAllDepartments());
			prepareModel.addAttribute("validActionList", getValidActions(model, container));
			prepareModel.addAttribute("nextAction", getNextAction(model, container));
			prepareModel.addAttribute("pendingActions", container.getPendingActions());
		}

	}

}