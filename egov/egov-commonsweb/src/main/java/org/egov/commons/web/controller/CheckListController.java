package org.egov.commons.web.controller;

import static org.egov.infra.utils.JsonUtils.toJSON;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.egov.common.entity.bpa.Checklist;
import org.egov.common.entity.bpa.ChecklistType;
import org.egov.common.entity.bpa.SearchChecklist;
import org.egov.commons.service.BpaCheckListService;
import org.egov.commons.service.CheckListTypeService;
import org.egov.commons.web.adaptor.ChecklistJsonAdaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
@RequestMapping("/checklist")
public class CheckListController {

    @Autowired
    private CheckListTypeService checkListTypeService;

    @Autowired
    private BpaCheckListService bpaCheckListService;

    @Autowired
    private MessageSource messageSource;

    private static final String DATA = "{ \"data\":";

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getSearchFormToCreate(final Model model) {
        model.addAttribute("checklist", new Checklist());
        model.addAttribute("checklistTypes", checkListTypeService.findAll());
        return "checklist-search";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String getSearchFormToUpdate(final Model model) {
        model.addAttribute("checklist", new Checklist());
        model.addAttribute("checklistTypes", checkListTypeService.findAll());
        return "checklist-search-update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateCheckListForm(@ModelAttribute("checklist") final Checklist checkList, final Model model,
            final HttpServletRequest request, final BindingResult errors, final RedirectAttributes redirectAttributes) {
        List<Checklist> list = bpaCheckListService.findByChecklistType(checkList.getChecklistType());
        if (list.isEmpty()) {
            model.addAttribute("message", "No Checklists found to update.");
            model.addAttribute("checklistTypes", checkListTypeService.findAll());
            return "checklist-search-update";
        }

        model.addAttribute("checklistTypes", checkListTypeService.findAll());
        model.addAttribute("selectedChecklist", checkList.getChecklistType());

        Checklist checklist = new Checklist();
        checklist.setChecklistTemp(list);
        model.addAttribute("checklist", checklist);
        return "checklist-update";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String showCheckListForm(@ModelAttribute("checklist") final Checklist checkList, final Model model,
            final HttpServletRequest request, final BindingResult errors, final RedirectAttributes redirectAttributes) {
        model.addAttribute("checklist", new Checklist());
        model.addAttribute("checklistTypes", checkListTypeService.findAll());
        model.addAttribute("selectedChecklist", checkList.getChecklistType());
        return "checklist-new";
    }

    @RequestMapping(value = "/createChecklist", method = RequestMethod.POST)
    public String createCheckList(@ModelAttribute("checklist") final Checklist checkList, final Model model,
            final HttpServletRequest request, final BindingResult errors, final RedirectAttributes redirectAttributes) {
        ChecklistType checklistType = checkList.getChecklistTemp().get(0).getChecklistType();
        for (Checklist checklist : checkList.getChecklistTemp()) {
            checklist.setChecklistType(checklistType);
        }
        bpaCheckListService.validateCreateChecklists(checkList, errors);
        if (errors.hasErrors()) {
            model.addAttribute("selectedChecklist", checklistType);
            model.addAttribute("checklistTypes", checkListTypeService.findAll());
            return "checklist-new";
        }
        bpaCheckListService.save(checkList.getChecklistTemp());
        redirectAttributes.addFlashAttribute("message",
                messageSource.getMessage("msg.create.checklist.success", null, null));
        return "redirect:/checklist/result";
    }

    @RequestMapping(value = "/updateChecklist", method = RequestMethod.POST)
    public String updateCheckList(@ModelAttribute("checklist") final Checklist checkList, final Model model,
            final HttpServletRequest request, final BindingResult errors, final RedirectAttributes redirectAttributes) {
        for (Checklist checklist : checkList.getChecklistTemp())
            if (checklist.getChecklistType() == null)
                checklist.setChecklistType(checkList.getChecklistTemp().get(0).getChecklistType());

        bpaCheckListService.save(checkList.getChecklistTemp());
        redirectAttributes.addFlashAttribute("message",
                messageSource.getMessage("msg.update.checklist.success", null, null));
        return "redirect:/checklist/result";
    }

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public String ChecklistTypeResult(final Model model) {
        return "checklist-result";
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String searchViewCheckLists(final Model model) {
        model.addAttribute("checklistTypes", checkListTypeService.findAll());
        model.addAttribute("searchChecklist", new SearchChecklist());
        return "checklist-view";
    }

    @RequestMapping(value = "/view", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String getChecklistListForView(@ModelAttribute final SearchChecklist checklist, final Model model) {
        final List<SearchChecklist> searchResultList = bpaCheckListService.search(checklist);
        return new StringBuilder(DATA)
                .append(toJSON(searchResultList, SearchChecklist.class, ChecklistJsonAdaptor.class)).append("}")
                .toString();
    }

}
