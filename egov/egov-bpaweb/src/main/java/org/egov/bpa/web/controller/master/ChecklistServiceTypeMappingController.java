package org.egov.bpa.web.controller.master;

import static org.egov.infra.utils.JsonUtils.toJSON;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.egov.bpa.master.entity.ChecklistServiceTypeMapping;
import org.egov.bpa.master.service.ChecklistServicetypeMappingService;
import org.egov.bpa.master.service.ServiceTypeService;
import org.egov.bpa.web.controller.adaptor.ChecklistServicetypeMappingAdaptor;
import org.egov.commons.service.BpaCheckListService;
import org.egov.commons.service.CheckListTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/checklistservicetypemapping")
public class ChecklistServiceTypeMappingController {

    @Autowired
    BpaCheckListService bpaChecklistService;

    @Autowired
    CheckListTypeService checklistTypeService;

    @Autowired
    ServiceTypeService serviceTypeService;

    @Autowired
    ChecklistServicetypeMappingService checklisterviceTypeService;

    @Autowired
    protected ResourceBundleMessageSource messageSource;

    private static final String DATA = "{ \"data\":";

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String showServiceTypeChecklistMapping(final Model model) {
        model.addAttribute("servicetypes", serviceTypeService.getAllActiveMainServiceTypes());
        model.addAttribute("checklistTypes", checklistTypeService.findAll());
        model.addAttribute("serviceTypeChecklist", new ChecklistServiceTypeMapping());
        return "servicetype-checklist-mapping";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateServiceTypeChecklistMapping(final Model model) {
        model.addAttribute("servicetypes", serviceTypeService.getAllActiveMainServiceTypes());
        model.addAttribute("checklistTypes", checklistTypeService.findAll());
        model.addAttribute("serviceTypeChecklist", new ChecklistServiceTypeMapping());
        return "update-servicetype-checklist-mapping";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String getServiceTypeChecklistMappingForm(
            @ModelAttribute("serviceTypeChecklist") final ChecklistServiceTypeMapping checklistServiceType,
            final Model model, final HttpServletRequest request, final BindingResult errors) {

        if (checklisterviceTypeService.validateChecklistServiceTypeAlreadyExist(
                checklistServiceType.getServiceType().getDescription(),
                checklistServiceType.getChecklistType().getDescription())) {
            model.addAttribute("message",
                    "Checklist Type And ServiceType Mapping Already Exists.Please use update screen to update mapping.");
            model.addAttribute("serviceType", checklistServiceType.getServiceType());
            model.addAttribute("checklistType", checklistServiceType.getChecklistType());
            model.addAttribute("servicetypes", serviceTypeService.getAllActiveMainServiceTypes());
            model.addAttribute("checklistTypes", checklistTypeService.findAll());
            return "servicetype-checklist-mapping";
        }
        model.addAttribute("serviceType", checklistServiceType.getServiceType());
        model.addAttribute("checklistType", checklistServiceType.getChecklistType());
        model.addAttribute("checklistServicetype", new ChecklistServiceTypeMapping());
        model.addAttribute("checklists", bpaChecklistService.findByChecklistType(checklistServiceType.getChecklistType()));
        model.addAttribute("servicetypes", serviceTypeService.getAllActiveMainServiceTypes());
        return "servicetype-checklist-mapping-create";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateServiceTypeChecklistMappingForm(
            @ModelAttribute("serviceTypeChecklist") final ChecklistServiceTypeMapping checklistServiceType,
            final Model model, final HttpServletRequest request, final BindingResult errors) {
        model.addAttribute("serviceType", checklistServiceType.getServiceType());
        model.addAttribute("checklistType", checklistServiceType.getChecklistType());
        List<ChecklistServiceTypeMapping> mappintList = checklisterviceTypeService.findByServiceTypeAndChecklistType(
                checklistServiceType.getServiceType().getDescription(), checklistServiceType.getChecklistType().getDescription());
        ChecklistServiceTypeMapping mapping = new ChecklistServiceTypeMapping();
        mapping.setMappingList(mappintList);
        model.addAttribute("checklistServicetype", mapping);
        /* model.addAttribute("checklists", bpaChecklistService.findByChecklistType(checklistServiceType.getChecklistType())); */
        model.addAttribute("servicetypes", serviceTypeService.getAllActiveMainServiceTypes());
        return "servicetype-checklist-mapping-update";
    }

    @RequestMapping(value = "/updatemapping", method = RequestMethod.POST)
    public String updateServiceTypeChecklistMapping(
            @ModelAttribute("checklistServicetype") final ChecklistServiceTypeMapping checklistServiceType,
            final Model model, final HttpServletRequest request, final RedirectAttributes redirectAttributes,
            final BindingResult errors) {
        checklisterviceTypeService.save(checklistServiceType.getMappingList());
        redirectAttributes.addFlashAttribute("message",
                messageSource.getMessage("msg.update.checklist.servicetype.mapping.success", null, null));
        return "redirect:/checklistservicetypemapping/update/result";
    }

    @RequestMapping(value = "/createmapping", method = RequestMethod.POST)
    public String createServiceTypeChecklistMapping(
            @ModelAttribute("checklistServicetype") final ChecklistServiceTypeMapping checklistServiceType,
            final Model model, final HttpServletRequest request, final RedirectAttributes redirectAttributes,
            final BindingResult errors) {
        checklisterviceTypeService.save(checklistServiceType.getMappingList());
        redirectAttributes.addFlashAttribute("message",
                messageSource.getMessage("msg.create.checklist.servicetype.mapping.success", null, null));
        return "redirect:/checklistservicetypemapping/result";
    }

    @RequestMapping(value = "/search/view", method = RequestMethod.GET)
    public String searchViewChecklistServiceTypeMapping(final Model model) {
        model.addAttribute("servicetypes", serviceTypeService.getAllActiveMainServiceTypes());
        model.addAttribute("checklistTypes", checklistTypeService.findAll());
        return "checklist-servicetype-mapping-search";
    }

    @RequestMapping(value = "/search/view", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String viewslotMapping(@ModelAttribute final ChecklistServiceTypeMapping checklistServiceTypeMapping) {
        List<ChecklistServiceTypeMapping> slotMappingList = checklisterviceTypeService
                .searchChecklistServicetypeMapping(checklistServiceTypeMapping);
        return new StringBuilder(DATA)
                .append(toJSON(slotMappingList, ChecklistServiceTypeMapping.class, ChecklistServicetypeMappingAdaptor.class))
                .append("}").toString();
    }

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public String resultChecklistServicetypeMappingList(final Model model) {
        return "checklist-servicetype-mapping-result";
    }

    @RequestMapping(value = "/update/result", method = RequestMethod.GET)
    public String updateResultChecklistServicetypeMapping(final Model model) {
        return "checklist-servicetype-mapping-update-result";
    }
}
