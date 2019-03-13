package org.egov.edcr.web.controller;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.egov.infra.persistence.entity.enums.UserType.BUSINESS;
import static org.egov.infra.utils.JsonUtils.toJSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.egov.bpa.master.entity.StakeHolder;
import org.egov.bpa.master.entity.enums.StakeHolderStatus;
import org.egov.bpa.master.service.ServiceTypeService;
import org.egov.bpa.master.service.StakeHolderService;
import org.egov.bpa.master.service.StakeholderTypeService;
import org.egov.commons.service.OccupancyService;
import org.egov.edcr.entity.ApplicationType;
import org.egov.edcr.entity.EdcrApplication;
import org.egov.edcr.entity.EdcrApplicationDetail;
import org.egov.edcr.entity.EdcrPdfDetail;
import org.egov.edcr.service.EdcrApplicationService;
import org.egov.edcr.service.EdcrBpaRestService;
import org.egov.edcr.service.EdcrPdfDetailService;
import org.egov.edcr.web.adaptor.EdcrApplicationJsonAdaptor;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.persistence.entity.Address;
import org.egov.infra.persistence.entity.enums.AddressType;
import org.egov.infra.persistence.entity.enums.UserType;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.JsonObject;

@Controller
public class EdcrApplicationController {
    private static final String EDCR_APPLICATION = "edcrApplication";
    private static final String MSG_EDCRAPPLICATION_SUCCESS = "msg.edcrapplication.success";
    private static final String REDIRECT_APPLICATION_RESULT = "redirect:/edcrapplication/result/";
    private static final String EDCRAPPLICATION_NEW = "edcrapplication-new";
    private static final String EDCRAPPLICATION_RESULT = "edcrapplication-result";
    private static final String EDCRAPPLICATION_EDIT = "edcrapplication-edit";
    private static final String EDCRAPPLICATION_VIEW = "edcrapplication-view";
    private static final String EDCRAPPLICATION_SEARCH = "edcrapplication-search";
    private static final String EDCRAPPLICATION_RE_UPLOAD = "edcr-reupload-form";
    private static final String EDCRAPPLICATION_CONVERTED_PDF = "view-edcr-pdf";
    private static final String DCR_ACKNOWLEDGEMENT = "dcr-acknowledgement";
    private static final String OC_PLAN_SCRUTINY_NEW = "oc-plan-scrutiny-new";
    private static final String OC_PLAN_SCRUTINY_RESUBMIT = "oc-resubmit-plan-scrutiny-form";
    private static final String OC_PLAN_SCRUTINY_RESULT = "oc-plan-scrutiny-result";
    private static final String REDIRECT_OC_APPLICATION_RESULT = "redirect:/occupancy-certificate/plan/result/";
    private static final String MESSAGE = "message";

    @Autowired
    private EdcrApplicationService edcrApplicationService;

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private OccupancyService occupancyService;
    @Autowired
    private ServiceTypeService serviceTypeService;
    @Autowired
    private SecurityUtils securityUtils;
    @Autowired
    private StakeHolderService stakeHolderService;
    @Autowired
    private EdcrBpaRestService edcrBpaRestService;
    @Autowired
    private EdcrPdfDetailService edcrPdfDetailService;
    @Autowired
    private StakeholderTypeService stakeholderTypeService;

    private void prepareNewForm(Model model, HttpServletRequest request) {
        model.addAttribute("serviceTypeList", serviceTypeService.getEDcrRequiredServiceTypes());
        model.addAttribute("amenityTypeList", serviceTypeService.getAllActiveAmenities());
        model.addAttribute("occupancyList", occupancyService.findAllOrderByOrderNumber());
    }

    @GetMapping("/edcrapplication/new")
    public String newForm(final Model model, HttpServletRequest request) {
        prepareNewForm(model, request);
        StakeHolder stakeHolder = stakeHolderService.findById(securityUtils.getCurrentUser().getId());
        if (validateStakeholder(model, stakeHolder, request))
            return DCR_ACKNOWLEDGEMENT;
        Address permanentAddress = stakeHolder.getAddress().stream()
                .filter(permtAddress -> permtAddress.getType().equals(AddressType.PERMANENT)).findAny().orElse(null);
        StringBuilder architectInfo = new StringBuilder(256).append(stakeHolder.getName()).append(",")
                .append(stakeHolder.getStakeHolderType().getName()).append(",").append(stakeHolder.getMobileNumber())
                .append(",").append(permanentAddress.getStreetRoadLine()).append(".");

        EdcrApplication edcrApplication = new EdcrApplication();
        if (stakeHolder != null && !isBlank(stakeHolder.getName())) {
            edcrApplication.setArchitectInformation(stakeHolder.getName());
        }

        model.addAttribute(EDCR_APPLICATION, edcrApplication);

        return EDCRAPPLICATION_NEW;
    }

    private boolean validateStakeholder(Model model, StakeHolder stakeHolder, HttpServletRequest request) {
        if (stakeHolder != null && StakeHolderStatus.BLOCKED.equals(stakeHolder.getStatus())) {
            model.addAttribute(MESSAGE, messageSource.getMessage("msg.stakeholder.license.blocked",
                    new String[] { ApplicationThreadLocals.getMunicipalityName() }, null));
            return true;
        }

        if (stakeHolder != null && stakeHolder.getBuildingLicenceExpiryDate().before(new Date())) {
            model.addAttribute(MESSAGE, messageSource.getMessage("msg.stakeholder.expiry.reached",
                    new String[] { securityUtils.getCurrentUser().getName() }, null));
            return true;
        }

        User user = securityUtils.getCurrentUser();

        if (user.getType().equals(BUSINESS) && stakeHolder != null && stakeHolder.getDemand() != null
                && edcrBpaRestService.checkAnyTaxIsPendingToCollectForStakeHolder(user.getId(), request)) {
            model.addAttribute("userId", user.getId());
            return true;
        }

        return false;
    }

    @PostMapping("/edcrapplication/create")
    public String create(@ModelAttribute final EdcrApplication edcrApplication, final BindingResult errors,
            final Model model, final RedirectAttributes redirectAttrs, HttpServletRequest request) {
        if (errors.hasErrors()) {
            prepareNewForm(model, request);
            return EDCRAPPLICATION_NEW;
        }

        /*
         * Plan plan = new Plan(); PlanInformation planInformation = new PlanInformation();
         * planInformation.setApplicantName(edcrApplication.getApplicantName());
         * planInformation.setOccupancy(edcrApplication.getOccupancy());
         * planInformation.setServiceType(edcrApplication.getServiceType());
         * planInformation.setAmenities(edcrApplication.getAmenities()); planInformation.setArchitectInformation(edcrApplication.
         * getArchitectInformation());
         */
        EdcrApplicationDetail edcrApplicationDetail = new EdcrApplicationDetail();
        // plan.setPlanInformation(planInformation);
        // edcrApplicationDetail.setPlanDetail(plan);
        // edcrApplicationDetail.getPlan().setPlanInformation(planInformation);
        List<EdcrApplicationDetail> edcrApplicationDetails = new ArrayList<>();
        edcrApplicationDetails.add(edcrApplicationDetail);
        edcrApplication.setEdcrApplicationDetails(edcrApplicationDetails);
        edcrApplicationService.create(edcrApplication);

        redirectAttrs.addFlashAttribute(MESSAGE, messageSource.getMessage(MSG_EDCRAPPLICATION_SUCCESS, null, null));
        return REDIRECT_APPLICATION_RESULT + edcrApplication.getApplicationNumber();
    }

    @GetMapping("/edcrapplication/edit/{applicationNumber}")
    public String edit(@PathVariable("id") final String applicationNumber, Model model, HttpServletRequest request) {
        EdcrApplication edcrApplication = edcrApplicationService.findByApplicationNo(applicationNumber);
        prepareNewForm(model, request);
        model.addAttribute(EDCR_APPLICATION, edcrApplication);
        return EDCRAPPLICATION_EDIT;
    }

    @GetMapping("/edcrapplication/resubmit")
    public String uploadAgain(Model model, HttpServletRequest request) {
        StakeHolder stakeHolder = stakeHolderService.findById(securityUtils.getCurrentUser().getId());
        if (validateStakeholder(model, stakeHolder, request))
            return DCR_ACKNOWLEDGEMENT;
        prepareNewForm(model, request);
        EdcrApplication edcrApplication = new EdcrApplication();
        edcrApplication.setApplicationType(ApplicationType.PERMIT);
        model.addAttribute(EDCR_APPLICATION, edcrApplication);
        return EDCRAPPLICATION_RE_UPLOAD;
    }

    @PostMapping("/edcrapplication/update")
    public String update(@ModelAttribute final EdcrApplication edcrApplication, final BindingResult errors,
            final Model model, final RedirectAttributes redirectAttrs, HttpServletRequest request) {
        if (errors.hasErrors()) {
            prepareNewForm(model, request);
            return EDCRAPPLICATION_EDIT;
        }

        /*
         * PlanDetail planDetail = new PlanDetail(); PlanInformation planInformation = new PlanInformation();
         * planInformation.setApplicantName(edcrApplication.getApplicantName());
         * planInformation.setOccupancy(edcrApplication.getOccupancy());
         * planInformation.setServiceType(edcrApplication.getServiceType());
         * planInformation.setAmenities(edcrApplication.getAmenities()); planInformation.setArchitectInformation(edcrApplication.
         * getArchitectInformation());
         */

        EdcrApplicationDetail edcrApplicationDetail = new EdcrApplicationDetail();
        // planDetail.setPlanInformation(planInformation);
        // edcrApplicationDetail.setPlanDetail(planDetail);

        List<EdcrApplicationDetail> edcrApplicationDetails = new ArrayList<>();
        edcrApplicationDetails.add(edcrApplicationDetail);
        edcrApplication.setEdcrApplicationDetails(edcrApplicationDetails);

        edcrApplicationService.update(edcrApplication);
        redirectAttrs.addFlashAttribute(MESSAGE, messageSource.getMessage("msg.edcrapplication.success", null, null));
        return REDIRECT_APPLICATION_RESULT + edcrApplication.getApplicationNumber();
    }

    @GetMapping("/edcrapplication/view/{applicationNumber}")
    public String view(@PathVariable final String applicationNumber, Model model, HttpServletRequest request) {
        EdcrApplication edcrApplication = edcrApplicationService.findByApplicationNo(applicationNumber);
        prepareNewForm(model, request);
        setFailedLayersCount(edcrApplication);
        model.addAttribute(EDCR_APPLICATION, edcrApplication);
        return EDCRAPPLICATION_VIEW;
    }

    @GetMapping("/edcrapplication/result/{applicationNumber}")
    public String result(@PathVariable final String applicationNumber, Model model) {
        EdcrApplication edcrApplication = edcrApplicationService.findByApplicationNo(applicationNumber);
        setFailedLayersCount(edcrApplication);
        model.addAttribute(EDCR_APPLICATION, edcrApplication);
        return EDCRAPPLICATION_RESULT;
    }

    @GetMapping("/occupancy-certificate/plan/submit")
    public String ocNewPlanScrutinyForm(final Model model, final HttpServletRequest request) {
        prepareNewForm(model, request);
        StakeHolder stakeHolder = stakeHolderService.findById(securityUtils.getCurrentUser().getId());
        if (validateStakeholder(model, stakeHolder, request))
            return DCR_ACKNOWLEDGEMENT;
        EdcrApplication edcrApplication = new EdcrApplication();
        edcrApplication.setApplicationType(ApplicationType.OCCUPANCY_CERTIFICATE);
        if (stakeHolder != null && StringUtils.isNotBlank(stakeHolder.getName()))
            edcrApplication.setArchitectInformation(stakeHolder.getName());
        model.addAttribute("stakeHolderTypeList",stakeholderTypeService.findAllIsActive());
        model.addAttribute("isCitizen", securityUtils.getCurrentUser().getType().equals(UserType.CITIZEN));
        model.addAttribute(EDCR_APPLICATION, edcrApplication);
        return OC_PLAN_SCRUTINY_NEW;
    }

    @PostMapping("/occupancy-certificate/plan/submit")
    public String submitPlanForOccupancyCertificate(@ModelAttribute final EdcrApplication edcrApplication,
            final BindingResult errors, final Model model, final RedirectAttributes redirectAttrs, HttpServletRequest request) {
        if (errors.hasErrors()) {
            prepareNewForm(model, request);
            return OC_PLAN_SCRUTINY_NEW;
        }
        /*
         * PlanDetail planDetail = new PlanDetail(); PlanInformation planInformation = new PlanInformation();
         * planInformation.setApplicantName(edcrApplication.getApplicantName());
         * planInformation.setOccupancy(edcrApplication.getOccupancy());
         * planInformation.setServiceType(edcrApplication.getServiceType());
         * planInformation.setAmenities(edcrApplication.getAmenities()); planInformation.setArchitectInformation(edcrApplication.
         * getArchitectInformation());
         */

        EdcrApplicationDetail edcrApplicationDetail = new EdcrApplicationDetail();
        // planDetail.setPlanInformation(planInformation);
        // edcrApplicationDetail.setPlanDetail(planDetail);

        List<EdcrApplicationDetail> edcrApplicationDetails = new ArrayList<>();
        edcrApplicationDetails.add(edcrApplicationDetail);
        edcrApplication.setEdcrApplicationDetails(edcrApplicationDetails);
        edcrApplication
                .setPermitApplicationDate(DateUtils.toDateUsingDefaultPattern(edcrApplication.getPermitDateTemp()));
        edcrApplicationService.create(edcrApplication);
        redirectAttrs.addFlashAttribute(MESSAGE, messageSource.getMessage(MSG_EDCRAPPLICATION_SUCCESS, null, null));
        return REDIRECT_OC_APPLICATION_RESULT + edcrApplication.getApplicationNumber();
    }

    @GetMapping("/occupancy-certificate/plan/resubmit")
    public String resubmitPlanForOccupancyCertificate(Model model, HttpServletRequest request) {
        StakeHolder stakeHolder = stakeHolderService.findById(securityUtils.getCurrentUser().getId());
        if (validateStakeholder(model, stakeHolder, request))
            return DCR_ACKNOWLEDGEMENT;
        prepareNewForm(model, request);
        EdcrApplication edcrApplication = new EdcrApplication();
        edcrApplication.setApplicationType(ApplicationType.OCCUPANCY_CERTIFICATE);
        model.addAttribute(EDCR_APPLICATION, edcrApplication);
        return OC_PLAN_SCRUTINY_RESUBMIT;
    }

    @PostMapping("/occupancy-certificate/plan/resubmit")
    public String resubmitPlanForOccupancyCertificate(@ModelAttribute final EdcrApplication edcrApplication,
            final BindingResult errors, final Model model, final RedirectAttributes redirectAttrs, HttpServletRequest request) {
        if (errors.hasErrors()) {
            prepareNewForm(model, request);
            return OC_PLAN_SCRUTINY_RESUBMIT;
        }
        /*
         * PlanDetail planDetail = new PlanDetail(); PlanInformation planInformation = new PlanInformation();
         * planInformation.setApplicantName(edcrApplication.getApplicantName());
         * planInformation.setOccupancy(edcrApplication.getOccupancy());
         * planInformation.setServiceType(edcrApplication.getServiceType());
         * planInformation.setAmenities(edcrApplication.getAmenities()); planInformation.setArchitectInformation(edcrApplication.
         * getArchitectInformation());
         */

        EdcrApplicationDetail edcrApplicationDetail = new EdcrApplicationDetail();
        // planDetail.setPlanInformation(planInformation);
        // edcrApplicationDetail.setPlanDetail(planDetail);

        List<EdcrApplicationDetail> edcrApplicationDetails = new ArrayList<>();
        edcrApplicationDetails.add(edcrApplicationDetail);
        edcrApplication.setEdcrApplicationDetails(edcrApplicationDetails);
        edcrApplicationService.update(edcrApplication);
        redirectAttrs.addFlashAttribute(MESSAGE, messageSource.getMessage(MSG_EDCRAPPLICATION_SUCCESS, null, null));
        return REDIRECT_OC_APPLICATION_RESULT + edcrApplication.getApplicationNumber();
    }

    @GetMapping("/occupancy-certificate/plan/result/{applicationNumber}")
    public String planScrutinyResultForOccupancyCertificate(@PathVariable final String applicationNumber, Model model) {
        EdcrApplication edcrApplication = edcrApplicationService.findByApplicationNo(applicationNumber);
        setFailedLayersCount(edcrApplication);
        model.addAttribute(EDCR_APPLICATION, edcrApplication);
        return OC_PLAN_SCRUTINY_RESULT;
    }

    @GetMapping("/edcrapplication/search/{mode}")
    public String search(@PathVariable("mode") final String mode, Model model, HttpServletRequest request) {
        EdcrApplication edcrApplication = new EdcrApplication();
        prepareNewForm(model, request);
        model.addAttribute(EDCR_APPLICATION, edcrApplication);
        return EDCRAPPLICATION_SEARCH;

    }

    @GetMapping(value = "/edcrapplication/get-information/{applicationNumber}/{applicationType}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EdcrApplication getEdcrApplicationDetailsByApplnNumber(@PathVariable final String applicationNumber,
            @PathVariable final ApplicationType applicationType, Model model) {
        return edcrApplicationService.findByApplicationNoAndType(applicationNumber, applicationType);
    }

    @PostMapping(value = "/edcrapplication/ajaxsearch/{mode}", produces = MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody String ajaxsearch(@PathVariable("mode") final String mode, Model model,
            @ModelAttribute final EdcrApplication edcrApplication) {
        List<EdcrApplication> searchResultList = edcrApplicationService.search(edcrApplication);
        return new StringBuilder("{ \"data\":")
                .append(toJSON(searchResultList, EdcrApplication.class, EdcrApplicationJsonAdaptor.class)).append("}")
                .toString();
    }

    @GetMapping("/edcrapplication/get-convertedpdf/{applicationDetailId}")
    public String getConvertedPdfByApplicationDetailId(@PathVariable final String applicationDetailId, Model model) {
        List<EdcrPdfDetail> pdfDetails = edcrPdfDetailService.findByDcrApplicationId(Long.valueOf(applicationDetailId));
        if (pdfDetails != null && !pdfDetails.isEmpty()) {

            for (EdcrPdfDetail edcrPdfDetail : pdfDetails) {
                if (StringUtils.isNotBlank(edcrPdfDetail.getStandardViolations())) {
                    String[] split = edcrPdfDetail.getStandardViolations().split("\\|");
                    List<String> violations = Arrays.asList(split);
                    edcrPdfDetail.setViolations(violations);
                }
            }
        }
        model.addAttribute("pdfDetails", pdfDetails);
        return EDCRAPPLICATION_CONVERTED_PDF;
    }

    @GetMapping(value = "/scrutinized-plan/findby-permitnumber/{permitNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void getEdcrApplicationDetailsByPermitNumber(@PathVariable final String permitNumber,
            HttpServletResponse response) throws IOException {
        EdcrApplication application = edcrApplicationService.findByPlanPermitNumber(permitNumber);
        final JsonObject jsonObj = new JsonObject();
        if (application != null)
            jsonObj.addProperty("applicationNumber", application.getApplicationNumber());
        IOUtils.write(jsonObj.toString(), response.getWriter());
    }

    private void setFailedLayersCount(EdcrApplication edcrApplication) {
        /*
         * for (EdcrApplicationDetail edcrApplicationDetail : edcrApplication.getEdcrApplicationDetails()){ List<EdcrPdfDetail>
         * edcrPdfDetails = edcrApplicationDetail.getEdcrPdfDetails(); if (edcrPdfDetails != null && edcrPdfDetails.size()> 0){
         * long count = edcrPdfDetails.stream().filter(edcrPdfDetail ->
         * StringUtils.isNotBlank(edcrPdfDetail.getFailureReasons())).count(); edcrApplicationDetail.setNoOfErrors(count); } }
         */
    }
}