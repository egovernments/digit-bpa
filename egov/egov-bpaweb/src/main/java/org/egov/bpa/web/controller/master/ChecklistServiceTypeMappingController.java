package org.egov.bpa.web.controller.master;

import static org.egov.infra.utils.JsonUtils.toJSON;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/checklistservicetypemapping")
public class ChecklistServiceTypeMappingController {

    private static final String CHECKLIST_SERVICETYPE = "checklistServicetype";

    private static final String SERVICETYPE_CHECKLIST_MAPPING = "servicetype-checklist-mapping";

    private static final String CHECKLIST_TYPE = "checklistType";

    private static final String SERVICE_TYPE = "serviceType";

    private static final String MESSAGE = "message";

    private static final String SERVICE_TYPE_CHECKLIST = "serviceTypeChecklist";

    private static final String CHECKLIST_TYPES = "checklistTypes";

    private static final String SERVICETYPES = "servicetypes";

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

    private void formData(final Model model, final ChecklistServiceTypeMapping checklistServiceTypeMapping) {
        model.addAttribute(SERVICETYPES, serviceTypeService.getAllActiveMainServiceTypes());
        model.addAttribute(CHECKLIST_TYPES, checklistTypeService.findAll());
        model.addAttribute(SERVICE_TYPE_CHECKLIST, checklistServiceTypeMapping);
    }

    @GetMapping("/create")
    public String showServiceTypeChecklistMapping(final Model model) {
        formData(model, new ChecklistServiceTypeMapping());
        return SERVICETYPE_CHECKLIST_MAPPING;
    }

    @PostMapping("/create")
    public String getServiceTypeChecklistMappingForm(
            @Valid @ModelAttribute(SERVICE_TYPE_CHECKLIST) final ChecklistServiceTypeMapping checklistServiceType,
            final BindingResult errors, final Model model, final HttpServletRequest request) {

        if (checklisterviceTypeService.validateChecklistServiceTypeAlreadyExist(
                checklistServiceType.getServiceType().getDescription(),
                checklistServiceType.getChecklistType().getDescription())) {
            model.addAttribute(MESSAGE,
                    "Checklist Type And ServiceType Mapping Already Exists.Please use update screen to update mapping.");
            model.addAttribute(SERVICE_TYPE, checklistServiceType.getServiceType());
            model.addAttribute(CHECKLIST_TYPE, checklistServiceType.getChecklistType());
            formData(model, checklistServiceType);
            return SERVICETYPE_CHECKLIST_MAPPING;
        }
        model.addAttribute(SERVICE_TYPE, checklistServiceType.getServiceType());
        model.addAttribute(CHECKLIST_TYPE, checklistServiceType.getChecklistType());
        model.addAttribute(CHECKLIST_SERVICETYPE, new ChecklistServiceTypeMapping());
        model.addAttribute("checklists",
                bpaChecklistService.findByChecklistType(checklistServiceType.getChecklistType()));
        model.addAttribute(SERVICETYPES, serviceTypeService.getAllActiveMainServiceTypes());
        return "servicetype-checklist-mapping-create";
    }

    @PostMapping("/createmapping")
    public String createServiceTypeChecklistMapping(
            @ModelAttribute(CHECKLIST_SERVICETYPE) final ChecklistServiceTypeMapping checklistServiceType,
            final Model model, final HttpServletRequest request, final RedirectAttributes redirectAttributes,
            final BindingResult errors) {

        if (errors.hasErrors()) {
            model.addAttribute(SERVICE_TYPE, checklistServiceType.getServiceType());
            model.addAttribute(CHECKLIST_TYPE, checklistServiceType.getChecklistType());
            model.addAttribute(CHECKLIST_SERVICETYPE, checklistServiceType);
            model.addAttribute("checklists",
                    bpaChecklistService.findByChecklistType(checklistServiceType.getChecklistType()));
            model.addAttribute(SERVICETYPES, serviceTypeService.getAllActiveMainServiceTypes());
            return "servicetype-checklist-mapping-create";
        }

        checklisterviceTypeService.save(checklistServiceType.getMappingList());
        redirectAttributes.addFlashAttribute(MESSAGE,
                messageSource.getMessage("msg.create.checklist.servicetype.mapping.success", null, null));
        return "redirect:/checklistservicetypemapping/result";
    }

    @GetMapping("/update")
    public String updateServiceTypeChecklistMapping(final Model model) {
        formData(model, new ChecklistServiceTypeMapping());
        return "update-servicetype-checklist-mapping";
    }

    @PostMapping("/update")
    public String updateServiceTypeChecklistMappingForm(
            @Valid @ModelAttribute(SERVICE_TYPE_CHECKLIST) final ChecklistServiceTypeMapping checklistServiceType,
            final BindingResult errors, final Model model, final HttpServletRequest request) {
        model.addAttribute(SERVICE_TYPE, checklistServiceType.getServiceType());
        model.addAttribute(CHECKLIST_TYPE, checklistServiceType.getChecklistType());
        List<ChecklistServiceTypeMapping> mappintList = checklisterviceTypeService.findByServiceTypeAndChecklistType(
                checklistServiceType.getServiceType().getDescription(),
                checklistServiceType.getChecklistType().getDescription());
        ChecklistServiceTypeMapping mapping = new ChecklistServiceTypeMapping();
        mapping.setMappingList(mappintList);
        model.addAttribute(CHECKLIST_SERVICETYPE, mapping);
        model.addAttribute(SERVICETYPES, serviceTypeService.getAllActiveMainServiceTypes());
        return "servicetype-checklist-mapping-update";
    }

    @PostMapping("/updatemapping")
    public String updateServiceTypeChecklistMapping(
            @Valid @ModelAttribute(CHECKLIST_SERVICETYPE) final ChecklistServiceTypeMapping checklistServiceType,
            final BindingResult errors, final Model model, final HttpServletRequest request,
            final RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute(SERVICE_TYPE, checklistServiceType.getServiceType());
            model.addAttribute(CHECKLIST_TYPE, checklistServiceType.getChecklistType());
            List<ChecklistServiceTypeMapping> mappintList = checklisterviceTypeService
                    .findByServiceTypeAndChecklistType(checklistServiceType.getServiceType().getDescription(),
                            checklistServiceType.getChecklistType().getDescription());
            ChecklistServiceTypeMapping mapping = new ChecklistServiceTypeMapping();
            mapping.setMappingList(mappintList);
            model.addAttribute(CHECKLIST_SERVICETYPE, mapping);
            model.addAttribute(SERVICETYPES, serviceTypeService.getAllActiveMainServiceTypes());
            return "servicetype-checklist-mapping-update";
        }
        checklisterviceTypeService.save(checklistServiceType.getMappingList());
        redirectAttributes.addFlashAttribute(MESSAGE,
                messageSource.getMessage("msg.update.checklist.servicetype.mapping.success", null, null));
        return "redirect:/checklistservicetypemapping/update/result";
    }

    @GetMapping("/search/view")
    public String searchViewChecklistServiceTypeMapping(final Model model) {
        model.addAttribute(SERVICETYPES, serviceTypeService.getAllActiveMainServiceTypes());
        model.addAttribute(CHECKLIST_TYPES, checklistTypeService.findAll());
        return "checklist-servicetype-mapping-search";
    }

    @PostMapping(value = "/search/view", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String viewslotMapping(@ModelAttribute final ChecklistServiceTypeMapping checklistServiceTypeMapping) {
        List<ChecklistServiceTypeMapping> slotMappingList = checklisterviceTypeService
                .searchChecklistServicetypeMapping(checklistServiceTypeMapping);
        return new StringBuilder(DATA).append(
                toJSON(slotMappingList, ChecklistServiceTypeMapping.class, ChecklistServicetypeMappingAdaptor.class))
                .append("}").toString();
    }

    @GetMapping("/result")
    public String resultChecklistServicetypeMappingList(final Model model) {
        return "checklist-servicetype-mapping-result";
    }

    @GetMapping("/update/result")
    public String updateResultChecklistServicetypeMapping(final Model model) {
        return "checklist-servicetype-mapping-update-result";
    }
}
