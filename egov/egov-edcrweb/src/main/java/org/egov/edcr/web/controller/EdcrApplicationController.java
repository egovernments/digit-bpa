package org.egov.edcr.web.controller;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.egov.infra.utils.JsonUtils.toJSON;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.egov.bpa.master.entity.ServiceType;
import org.egov.bpa.master.entity.StakeHolder;
import org.egov.bpa.master.entity.enums.StakeHolderStatus;
import org.egov.bpa.master.service.ServiceTypeService;
import org.egov.bpa.master.service.StakeHolderService;
import org.egov.commons.service.OccupancyService;
import org.egov.edcr.entity.EdcrApplication;
import org.egov.edcr.entity.EdcrApplicationDetail;
import org.egov.edcr.service.EdcrApplicationService;
import org.egov.edcr.web.adaptor.EdcrApplicationJsonAdaptor;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.persistence.entity.Address;
import org.egov.infra.persistence.entity.enums.AddressType;
import org.egov.infra.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/edcrapplication")
public class EdcrApplicationController {
    private final static String EDCRAPPLICATION_NEW = "edcrapplication-new";
    private final static String EDCRAPPLICATION_RESULT = "edcrapplication-result";
    private final static String EDCRAPPLICATION_EDIT = "edcrapplication-edit";
    private final static String EDCRAPPLICATION_VIEW = "edcrapplication-view";
    private final static String EDCRAPPLICATION_SEARCH = "edcrapplication-search";
    private final static String EDCRAPPLICATION_RE_UPLOAD = "edcr-reupload-form";
    private final static String EDCRAPPLICATION_CONVERTED_PDF = "view-edcr-pdf";
    private final static String DCR_ACKNOWLEDGEMENT = "dcr-acknowledgement";
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
  /*  @Autowired
    private EdcrPdfDetailService edcrPdfDetailService;
*/
    /*  @Autowired
    //  private PlanInformationService planInformationService;
  */
    private void prepareNewForm(Model model) {
        // model.addAttribute("planInformations", planInformationService.findAll());
        List<ServiceType> dcrRequireServices = new ArrayList<>();
        for (ServiceType serviceType : serviceTypeService.getAllActiveMainServiceTypes()) {
            if (serviceType.getCode().equals("01") || serviceType.getCode().equals("03") || serviceType.getCode().equals("04")
                    || serviceType.getCode().equals("06") || serviceType.getCode().equals("07"))
                dcrRequireServices.add(serviceType);
        }
        model.addAttribute("serviceTypeList", dcrRequireServices);
        model.addAttribute("amenityTypeList", serviceTypeService.getAllActiveAmenities());
        model.addAttribute("occupancyList", occupancyService.findAllOrderByOrderNumber());
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newForm(final Model model) {
        prepareNewForm(model);
        StakeHolder stakeHolder = stakeHolderService.findById(securityUtils.getCurrentUser().getId());
        if (validateStakeholder(model, stakeHolder)) return DCR_ACKNOWLEDGEMENT;
        Address permanentAddress = stakeHolder.getAddress().stream().filter(permtAddress -> permtAddress.getType().equals(AddressType.PERMANENT)).findAny().orElse(null);
        StringBuilder architectInfo = new StringBuilder(256).append(stakeHolder.getName()).append(",")
                .append(stakeHolder.getStakeHolderType().name()).append(",").
                        append(stakeHolder.getMobileNumber()).append(",").
                        append(permanentAddress.getStreetRoadLine()).append(".");

        EdcrApplication edcrApplication = new EdcrApplication();
        if (stakeHolder != null && !isBlank(stakeHolder.getName())) {
            edcrApplication.setArchitectInformation(stakeHolder.getName());
        }

        model.addAttribute("edcrApplication", edcrApplication);

        return EDCRAPPLICATION_NEW;
    }

    private boolean validateStakeholder(Model model, StakeHolder stakeHolder) {
        if (stakeHolder != null && StakeHolderStatus.BLOCKED.equals(stakeHolder.getStatus())) {
            model.addAttribute(MESSAGE,
                    messageSource.getMessage("msg.stakeholder.license.blocked", new String[]{ApplicationThreadLocals.getMunicipalityName()}, null));
            return true;
        }

        if (stakeHolder != null && stakeHolder.getBuildingLicenceExpiryDate().before(new Date())) {
            model.addAttribute(MESSAGE,
                    messageSource.getMessage("msg.stakeholder.expiry.reached", new String[]{securityUtils.getCurrentUser().getName()}, null));
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@ModelAttribute final EdcrApplication edcrApplication, final BindingResult errors,
                         final Model model, final RedirectAttributes redirectAttrs) {
        if (errors.hasErrors()) {
            prepareNewForm(model);
            return EDCRAPPLICATION_NEW;
        }


     /*   Plan plan = new Plan();

        PlanInformation planInformation = new PlanInformation();
        planInformation.setApplicantName(edcrApplication.getApplicantName());
        planInformation.setOccupancy(edcrApplication.getOccupancy());
        planInformation.setServiceType(edcrApplication.getServiceType());
        planInformation.setAmenities(edcrApplication.getAmenities());
        planInformation.setArchitectInformation(edcrApplication.getArchitectInformation());
*/
        EdcrApplicationDetail edcrApplicationDetail = new EdcrApplicationDetail();
      //  plan.setPlanInformation(planInformation); 
       // edcrApplicationDetail.setPlanDetail(plan);
      //  edcrApplicationDetail.getPlan().setPlanInformation(planInformation);
        List<EdcrApplicationDetail> edcrApplicationDetails = new ArrayList<>();
        edcrApplicationDetails.add(edcrApplicationDetail);
        edcrApplication.setEdcrApplicationDetails(edcrApplicationDetails);
        edcrApplicationService.create(edcrApplication);

        redirectAttrs.addFlashAttribute("message", messageSource.getMessage("msg.edcrapplication.success", null, null));
        return "redirect:/edcrapplication/result/" + edcrApplication.getApplicationNumber();
    }

    @RequestMapping(value = "/edit/{applicationNumber}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") final String applicationNumber, Model model) {
        EdcrApplication edcrApplication = edcrApplicationService.findByApplicationNo(applicationNumber);
        prepareNewForm(model);
        model.addAttribute("edcrApplication", edcrApplication);
        return EDCRAPPLICATION_EDIT;
    }

    @RequestMapping(value = "/resubmit", method = RequestMethod.GET)
    public String uploadAgain(Model model) {
        StakeHolder stakeHolder = stakeHolderService.findById(securityUtils.getCurrentUser().getId());
        if (validateStakeholder(model, stakeHolder)) return DCR_ACKNOWLEDGEMENT;
        prepareNewForm(model);
        model.addAttribute("edcrApplication", new EdcrApplication());
        return EDCRAPPLICATION_RE_UPLOAD;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@ModelAttribute final EdcrApplication edcrApplication, final BindingResult errors,
                         final Model model, final RedirectAttributes redirectAttrs) {
        if (errors.hasErrors()) {
            prepareNewForm(model);
            return EDCRAPPLICATION_EDIT;
        }

    /*    PlanDetail planDetail = new PlanDetail();

        PlanInformation planInformation = new PlanInformation();
        planInformation.setApplicantName(edcrApplication.getApplicantName());
        planInformation.setOccupancy(edcrApplication.getOccupancy());
        planInformation.setServiceType(edcrApplication.getServiceType());
        planInformation.setAmenities(edcrApplication.getAmenities());
        planInformation.setArchitectInformation(edcrApplication.getArchitectInformation());*/

        EdcrApplicationDetail edcrApplicationDetail = new EdcrApplicationDetail();
     //   planDetail.setPlanInformation(planInformation);
      //  edcrApplicationDetail.setPlanDetail(planDetail);

        List<EdcrApplicationDetail> edcrApplicationDetails = new ArrayList<>();
        edcrApplicationDetails.add(edcrApplicationDetail);
        edcrApplication.setEdcrApplicationDetails(edcrApplicationDetails);

        edcrApplicationService.update(edcrApplication);
        redirectAttrs.addFlashAttribute("message", messageSource.getMessage("msg.edcrapplication.success", null, null));
        return "redirect:/edcrapplication/result/" + edcrApplication.getApplicationNumber();
    }

    @RequestMapping(value = "/view/{applicationNumber}", method = RequestMethod.GET)
    public String view(@PathVariable final String applicationNumber, Model model) {
        EdcrApplication edcrApplication = edcrApplicationService.findByApplicationNo(applicationNumber);
        prepareNewForm(model);
        setFailedLayersCount(edcrApplication);
        model.addAttribute("edcrApplication", edcrApplication);
        return EDCRAPPLICATION_VIEW;
    }

    @RequestMapping(value = "/result/{applicationNumber}", method = RequestMethod.GET)
    public String result(@PathVariable final String applicationNumber, Model model) {
        EdcrApplication edcrApplication = edcrApplicationService.findByApplicationNo(applicationNumber);
        setFailedLayersCount(edcrApplication);
        model.addAttribute("edcrApplication", edcrApplication);
        return EDCRAPPLICATION_RESULT;
    }

    @RequestMapping(value = "/search/{mode}", method = RequestMethod.GET)
    public String search(@PathVariable("mode") final String mode, Model model) {
        EdcrApplication edcrApplication = new EdcrApplication();
        prepareNewForm(model);
        model.addAttribute("edcrApplication", edcrApplication);
        return EDCRAPPLICATION_SEARCH;

    }

    @RequestMapping(value = "/get-information/{applicationNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EdcrApplication getEdcrApplicationDetailsByApplnNumber(@PathVariable final String applicationNumber, Model model) {
        return edcrApplicationService.findByApplicationNo(applicationNumber);
    }

    @RequestMapping(value = "/ajaxsearch/{mode}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody
    String ajaxsearch(@PathVariable("mode") final String mode, Model model,
                      @ModelAttribute final EdcrApplication edcrApplication) {
        List<EdcrApplication> searchResultList = edcrApplicationService.search(edcrApplication);
        return new StringBuilder("{ \"data\":")
                .append(toJSON(searchResultList, EdcrApplication.class, EdcrApplicationJsonAdaptor.class))
                .append("}")
                .toString();
    }

    @RequestMapping(value = "/get-convertedpdf/{applicationDetailId}", method = RequestMethod.GET)
    public String getConvertedPdfByApplicationDetailId(@PathVariable final String applicationDetailId, Model model) {
      /*  List<EdcrPdfDetail> pdfDetails = edcrPdfDetailService.findByDcrApplicationId(Long.valueOf(applicationDetailId));
        if (pdfDetails != null && pdfDetails.size() > 0) {

            for (EdcrPdfDetail edcrPdfDetail : pdfDetails) {
                if (StringUtils.isNotBlank(edcrPdfDetail.getStandardViolations())){
                    String[] split = edcrPdfDetail.getStandardViolations().split("\\|");
                    List<String> violations = Arrays.asList(split);
                    edcrPdfDetail.setViolations(violations);
                }
            }
        }*/
       // model.addAttribute("pdfDetails", pdfDetails);
        return EDCRAPPLICATION_CONVERTED_PDF;
    }

    private void setFailedLayersCount(EdcrApplication edcrApplication) {
       /* for (EdcrApplicationDetail edcrApplicationDetail : edcrApplication.getEdcrApplicationDetails()){
            List<EdcrPdfDetail> edcrPdfDetails = edcrApplicationDetail.getEdcrPdfDetails();
            if (edcrPdfDetails != null && edcrPdfDetails.size()> 0){
                long count = edcrPdfDetails.stream().filter(edcrPdfDetail -> StringUtils.isNotBlank(edcrPdfDetail.getFailureReasons())).count();
                edcrApplicationDetail.setNoOfErrors(count);
            }
        }*/
    }
}