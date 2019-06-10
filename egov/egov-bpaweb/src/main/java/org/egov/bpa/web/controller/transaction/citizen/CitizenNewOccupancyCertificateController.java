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

import static org.egov.bpa.utils.BpaConstants.AUTH_TO_SUBMIT_PLAN;
import static org.egov.bpa.utils.BpaConstants.CHECKLIST_TYPE_NOC;
import static org.egov.bpa.utils.BpaConstants.WF_LBE_SUBMIT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_NEW_STATE;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.egov.bpa.master.entity.ChecklistServiceTypeMapping;
import org.egov.bpa.master.entity.NocConfiguration;
import org.egov.bpa.master.service.NocConfigurationService;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.entity.common.NocDocument;
import org.egov.bpa.transaction.entity.enums.NocIntegrationInitiationEnum;
import org.egov.bpa.transaction.entity.enums.NocIntegrationTypeEnum;
import org.egov.bpa.transaction.entity.oc.OCNocDocuments;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.service.collection.GenericBillGeneratorService;
import org.egov.bpa.transaction.service.oc.OccupancyCertificateNocService;
import org.egov.bpa.transaction.service.oc.OccupancyCertificateService;
import org.egov.bpa.transaction.service.oc.OccupancyCertificateValidationService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.OccupancyCertificateUtils;
import org.egov.bpa.web.controller.transaction.BpaGenericApplicationController;
import org.egov.commons.entity.Source;
import org.egov.commons.service.SubOccupancyService;
import org.egov.eis.entity.Assignment;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.custom.CustomImplProvider;
import org.egov.infra.workflow.matrix.entity.WorkFlowMatrix;
import org.egov.pims.commons.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/application/citizen")
public class CitizenNewOccupancyCertificateController extends BpaGenericApplicationController {

    private static final String ONLINE_PAYMENT_ENABLE = "onlinePaymentEnable";

    private static final String WORK_FLOW_ACTION = "workFlowAction";

    private static final String TRUE = "TRUE";

    private static final String CITIZEN_OR_BUSINESS_USER = "citizenOrBusinessUser";

    private static final String IS_CITIZEN = "isCitizen";

    private static final String OFFICIAL_NOT_EXISTS = "No officials assigned to process this application.";

    private static final String MESSAGE = "message";

    public static final String CITIZEN_OCCUPANCY_CERTIFICATE_NEW = "citizen-occupancy-certificate-new";

    private static final String MSG_PORTAL_FORWARD_REGISTRATION = "msg.portal.forward.registration";

    @Autowired
    private GenericBillGeneratorService genericBillGeneratorService;
    @Autowired
    private OccupancyCertificateService occupancyCertificateService;
    @Autowired
    protected SubOccupancyService subOccupancyService;
    @Autowired
    private CustomImplProvider specificNoticeService;
    @Autowired
    private NocConfigurationService nocConfigurationService;
    @Autowired
    private OccupancyCertificateNocService ocNocService;
    @Autowired
    private OccupancyCertificateUtils occupancyCertificateUtils;
    
    @GetMapping("/occupancy-certificate/apply")
    public String newOCForm(final Model model, final HttpServletRequest request) {

        OccupancyCertificate occupancyCertificate = new OccupancyCertificate();
        occupancyCertificate.setApplicationDate(new Date());
        loadFormData(model, request, occupancyCertificate);
        return CITIZEN_OCCUPANCY_CERTIFICATE_NEW;
    }

    private void loadFormData(final Model model, final HttpServletRequest request, OccupancyCertificate occupancyCertificate) {
        occupancyCertificate.setSource(Source.CITIZENPORTAL);
        occupancyCertificate.setApplicationType("Occupancy Certificate");
        model.addAttribute("mode", "new");
        model.addAttribute("loadingFloorDetailsFromEdcrRequire", true);
        model.addAttribute("subOccupancyList", subOccupancyService.findAllOrderByOrderNumber());
        setCityName(model, request);
        getDcrDocumentsUploadMode(model);
        prepareCommonModelAttribute(model, occupancyCertificate.isCitizenAccepted());
        model.addAttribute("occupancyCertificate", occupancyCertificate);
       // occupancyCertificate.getParent().setServiceType(serviceTypeService.getServiceTypeByCode(serviceCode));

		/*
		 * model.addAttribute("isOcApplFeeReq","NO");
		 * model.addAttribute("ocApplFeeCollected","NO");
		 * if(occupancyCertificateUtils.isApplicationFeeCollectionRequired() ){
		 * model.addAttribute("isOcApplFeeReq","YES"); }
		 * if(occupancyCertificate.getDemand() != null &&
		 * occupancyCertificate.getDemand().getAmtCollected().compareTo(
		 * occupancyCertificate.getAdmissionfeeAmount())>=0){
		 * model.addAttribute("ocApplFeeCollected","YES"); } Map nocTypeApplMap = new
		 * HashMap<String, String>(); if
		 * (occupancyCertificate.getNocDocuments().isEmpty()) { Map nocConfigMap = new
		 * HashMap<String,String>(); List<ChecklistServiceTypeMapping>
		 * checklistServicetypeList = checklistServiceTypeService
		 * .findByActiveChecklistAndServiceType(occupancyCertificate.getParent().
		 * getServiceType().getDescription(), CHECKLIST_TYPE_NOC); for
		 * (ChecklistServiceTypeMapping serviceChklist : checklistServicetypeList) {
		 * OCNocDocuments ocNocDocument = new OCNocDocuments(); NocDocument nocDocument
		 * = new NocDocument(); nocDocument.setServiceChecklist(serviceChklist);
		 * ocNocDocument.setOc(occupancyCertificate);
		 * ocNocDocument.setNocDocument(nocDocument);
		 * occupancyCertificate.getNocDocuments().add(ocNocDocument); String
		 * code=serviceChklist.getChecklist().getCode(); NocConfiguration
		 * nocConfig=nocConfigurationService.findByDepartmentAndType(code,
		 * BpaConstants.OC); if(occupancyCertificate.getApplicationNumber()!=null &&
		 * ocNocService.findByApplicationNumberAndType(occupancyCertificate.
		 * getApplicationNumber(),code)!=null) nocTypeApplMap.put(code, "initiated");
		 * if(nocConfig != null &&
		 * nocConfig.getApplicationType().trim().equalsIgnoreCase(BpaConstants.OC) &&
		 * nocConfig.getIntegrationType().equalsIgnoreCase(NocIntegrationTypeEnum.
		 * SEMI_AUTO.toString()) &&
		 * nocConfig.getIntegrationInitiation().equalsIgnoreCase(
		 * NocIntegrationInitiationEnum.MANUAL.toString()))
		 * nocConfigMap.put(nocConfig.getDepartment(),"initiate"); }
		 * model.addAttribute("nocConfigMap",nocConfigMap); }
		 * model.addAttribute("nocTypeApplMap",nocTypeApplMap);
		 */
    }

    private void setCityName(final Model model, final HttpServletRequest request) {
        if (request.getSession().getAttribute("cityname") != null)
            model.addAttribute("cityName", request.getSession().getAttribute("cityname"));
    }

    @PostMapping("/occupancy-certificate/submit")
    public String submitOCDetails(@Valid @ModelAttribute final OccupancyCertificate occupancyCertificate,
            final HttpServletRequest request, final Model model,
            final BindingResult errors, final RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            return CITIZEN_OCCUPANCY_CERTIFICATE_NEW;
        }
        occupancyCertificateService.validateProposedAndExistingBuildings(occupancyCertificate);
        OccupancyCertificateValidationService ocService =  (OccupancyCertificateValidationService) specificNoticeService
                .find(OccupancyCertificateValidationService.class, specificNoticeService.getCityDetails());
        Boolean result = ocService.validateOcApplnWithPermittedBpaAppln(model, occupancyCertificate);
        if (occupancyCertificate.getParent() != null && occupancyCertificate.getParent().geteDcrNumber() != null && !result) {
            occupancyCertificate.getBuildingDetailFromEdcr().clear();
            occupancyCertificate.getBuildings().clear();
            loadFormData(model, request, occupancyCertificate);
            return CITIZEN_OCCUPANCY_CERTIFICATE_NEW;
        }
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
                    bpaUtils.getBoundaryForWorkflow(occupancyCertificate.getParent().getSiteDetail().get(0)).getId());
        if (citizenOrBusinessUser && workFlowAction != null && workFlowAction.equals(WF_LBE_SUBMIT_BUTTON)
                && (userPosition == 0 || userPosition == null)) {
            model.addAttribute("noJAORSAMessage", OFFICIAL_NOT_EXISTS);
            loadFormData(model, request, occupancyCertificate);
            return CITIZEN_OCCUPANCY_CERTIFICATE_NEW;
        }

        wfBean.setWorkFlowAction(request.getParameter(WORK_FLOW_ACTION));
        OccupancyCertificate ocResponse = occupancyCertificateService.saveOrUpdate(occupancyCertificate, wfBean);
        if (citizenOrBusinessUser) {
            if (isCitizen)
                bpaUtils.createPortalUserinbox(ocResponse, Arrays.asList(ocResponse.getParent().getOwner().getUser(),
                        ocResponse.getParent().getStakeHolder().get(0).getStakeHolder()), workFlowAction);
            else
                bpaUtils.createPortalUserinbox(ocResponse,
                        Arrays.asList(ocResponse.getParent().getOwner().getUser(), securityUtils.getCurrentUser()),
                        workFlowAction);
        }
        String message;
        if (workFlowAction != null
                && workFlowAction
                        .equals(WF_LBE_SUBMIT_BUTTON)
                && onlinePaymentEnable && bpaUtils.checkAnyTaxIsPendingToCollect(occupancyCertificate.getDemand())) {
            return genericBillGeneratorService
                    .generateBillAndRedirectToCollection(occupancyCertificate, model);
        }
        // When fee collection not require then directly will forward to official
        else if (workFlowAction != null && workFlowAction.equals(WF_LBE_SUBMIT_BUTTON)
                && !bpaUtils.checkAnyTaxIsPendingToCollect(occupancyCertificate.getDemand())) {
            if (occupancyCertificate.getAuthorizedToSubmitPlan())
                wfBean.setApproverComments(AUTH_TO_SUBMIT_PLAN);
            wfBean.setCurrentState(WF_NEW_STATE);
            bpaUtils.redirectToBpaWorkFlowForOC(occupancyCertificate, wfBean);
            ocSmsAndEmailService.sendSMSAndEmail(occupancyCertificate, null, null);
            List<Assignment> assignments;
            if (null == userPosition)
                assignments = bpaWorkFlowService
                        .getAssignmentsByPositionAndDate(occupancyCertificate.getCurrentState().getOwnerPosition().getId(),
                                new Date());
            else
                assignments = bpaWorkFlowService.getAssignmentsByPositionAndDate(userPosition, new Date());
            Position pos = assignments.get(0).getPosition();
            User wfUser = assignments.get(0).getEmployee();
            message = messageSource.getMessage(MSG_PORTAL_FORWARD_REGISTRATION, new String[] {
                    wfUser == null ? ""
                            : wfUser.getUsername().concat("~")
                                    .concat(getDesinationNameByPosition(pos)),
                    occupancyCertificate.getApplicationNumber() }, LocaleContextHolder.getLocale());

            redirectAttributes.addFlashAttribute(MESSAGE, message);
        } else {
            message = "Successfully saved with ApplicationNumber " + ocResponse.getApplicationNumber() + ".";
            if (bpaUtils.isCitizenAcceptanceRequired())// && !occupancyCertificate.isCitizenAccepted())
                ocSmsAndEmailService.sendSMSAndEmail(occupancyCertificate, null, null);
        }
        redirectAttributes.addFlashAttribute(MESSAGE, message);
        return "redirect:/application/citizen/success/" + occupancyCertificate.getApplicationNumber();
    }
}