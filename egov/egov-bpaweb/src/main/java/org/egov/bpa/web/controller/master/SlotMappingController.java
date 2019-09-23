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
package org.egov.bpa.web.controller.master;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_TYPE_ONEDAYPERMIT;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_TYPE_REGULAR;
import static org.egov.bpa.utils.BpaConstants.OCCUPANCY_CERTIFICATE_NOTICE_TYPE;
import static org.egov.infra.utils.JsonUtils.toJSON;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.egov.bpa.master.entity.SlotMapping;
import org.egov.bpa.master.entity.enums.WorkingDays;
import org.egov.bpa.master.service.SlotMappingService;
import org.egov.bpa.web.controller.adaptor.SlotMappingJsonAdaptor;
import org.egov.bpa.web.controller.transaction.BpaGenericApplicationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/slotmapping")
public class SlotMappingController extends BpaGenericApplicationController {

    private static final String MESSAGE = "message";
    private static final String SLOT_MAPPING = "slotMapping";
    private static final String SLOT_MAPPING_FORM = "slotmapping-form";
    private static final String SLOTMAPPINGLIST_RESULT = "slotmapping-result";
    private static final String SLOTMAPPING_EDIT = "slotmapping-edit";
    private static final String DATA = "{ \"data\":";
    private static final String SLOTMAPPING_UPDATE = "slotmapping-update";
    private static final String SEARCH_SLOTMAPPING_VIEW = "search-slotmapping-view";
    private static final String SLOTMAPPING_VIEW = "slotmapping-view";

    @Autowired
    private SlotMappingService noOfApplicationsService;

    @GetMapping("/create")
    public String showSlotMapping(final Model model) {
        return loadForm(model, new SlotMapping());
    }

    private String loadForm(final Model model, final SlotMapping slotMapping) {
        prepareFormData(model);
        model.addAttribute("daysList", Arrays.asList(WorkingDays.values()));
        model.addAttribute(SLOT_MAPPING, slotMapping);
        return SLOT_MAPPING_FORM;
    }

    @PostMapping("/create")
    public String createSlotMapping(@Valid @ModelAttribute final SlotMapping slotMapping,
            final RedirectAttributes redirectAttributes, final Model model) {
        if (validateSlotMapping(slotMapping, model)) {
            return loadForm(model, slotMapping);
        }
        noOfApplicationsService.createSlotMapping(slotMapping);
        redirectAttributes.addFlashAttribute(MESSAGE,
                messageSource.getMessage("msg.create.slotmapping.success", null, null));
        return "redirect:/slotmapping/result";
    }

    private boolean validateSlotMapping(SlotMapping slotMapping, Model model) {
        if (slotMapping.getApplicationType().getName().equals(APPLICATION_TYPE_REGULAR)
                || slotMapping.getApplicationType().getName().equals(OCCUPANCY_CERTIFICATE_NOTICE_TYPE)) {
            if (!noOfApplicationsService.findByZoneAndAppType(slotMapping.getZone(), slotMapping.getApplicationType())
                    .isEmpty()) {
                model.addAttribute(MESSAGE,
                        messageSource.getMessage("msg.slotmapping.zone.already.exists", null, null));
                return true;
            } else {
                return false;
            }
        } else {
            if (!noOfApplicationsService.findByZoneElectionWardAndAppType(slotMapping.getZone(),
                    slotMapping.getElectionWard(), slotMapping.getApplicationType()).isEmpty()) {
                model.addAttribute(MESSAGE,
                        messageSource.getMessage("msg.slotmapping.zone.ward.already.exists", null, null));
                return true;
            } else {
                return false;
            }
        }
    }

    @GetMapping("/search/update")
    public String searchEditSlotMapping(final Model model) {
        loadForm(model, new SlotMapping());
        return SLOTMAPPING_EDIT;
    }

    @GetMapping("/search/view")
    public String searchViewSlotMapping(final Model model) {
        loadForm(model, new SlotMapping());
        return SEARCH_SLOTMAPPING_VIEW;
    }

    @PostMapping("/update")
    public String updateSlotMapping(@Valid @ModelAttribute final SlotMapping slotMapping, final BindingResult result,
            final RedirectAttributes redirectAttributes, final Model model) {
        if (result.hasErrors()) {
            loadForm(model, slotMapping);
            return SLOTMAPPING_EDIT;
        }
        validateUpdateSlotMapping(slotMapping, model);
        noOfApplicationsService.updateSlotMapping(slotMapping);
        redirectAttributes.addFlashAttribute(MESSAGE,
                messageSource.getMessage("msg.update.slotmapping.success", null, null));
        return "redirect:/slotmapping/result";
    }

    private boolean validateUpdateSlotMapping(SlotMapping slotMapping, Model model) {
        SlotMapping sltMapping = noOfApplicationsService.findById(slotMapping.getId());
        if (!slotMapping.getZone().getId().equals(sltMapping.getZone().getId())) {
            model.addAttribute(MESSAGE, messageSource.getMessage("msg.slotmapping.zone.not.updatable", null, null));
            return true;
        } else if (slotMapping.getRevenueWard() != null) {
            if (!slotMapping.getRevenueWard().getId().equals(sltMapping.getRevenueWard().getId()))

                model.addAttribute(MESSAGE,
                        messageSource.getMessage("msg.slotmapping.revenue.ward.not.updatable", null, null));
            return true;

        } else if (slotMapping.getElectionWard() != null) {
            if (!slotMapping.getElectionWard().getId().equals(sltMapping.getElectionWard().getId()))

                model.addAttribute(MESSAGE,
                        messageSource.getMessage("msg.slotmapping.election.ward.not.updatable", null, null));
            return true;

        } else if (!slotMapping.getApplicationType().getName().equals(sltMapping.getApplicationType().getName())) {
            model.addAttribute(MESSAGE,
                    messageSource.getMessage("msg.slotmapping.applicationtype.not.updatable", null, null));
            return true;
        } else {
            return false;
        }

    }

    @GetMapping("/update/{id}")
    public String editHolidayList(@PathVariable final Long id, final Model model) {
        final SlotMapping slotmapping = noOfApplicationsService.findById(id);
        if (slotmapping.getApplicationType().getName().equals(APPLICATION_TYPE_ONEDAYPERMIT))
            slotmapping.setDays(WorkingDays.valueOf(WorkingDays.getEnumNameForValue(slotmapping.getDay())));
        preapreUpdateModel(slotmapping, model);
        return SLOTMAPPING_UPDATE;
    }

    private void preapreUpdateModel(SlotMapping slotmapping, Model model) {
        loadForm(model, slotmapping);
        model.addAttribute(SLOT_MAPPING, slotmapping);
    }

    @GetMapping(value = "/search/update", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String searchslotMapping(@ModelAttribute final SlotMapping slotMapping) {
        List<SlotMapping> slotMappingList = noOfApplicationsService.searchSlotMapping(slotMapping);
        return new StringBuilder(DATA).append(toJSON(slotMappingList, SlotMapping.class, SlotMappingJsonAdaptor.class))
                .append("}").toString();
    }

    @GetMapping("/view/{id}")
    public String viewHolidayList(@PathVariable final Long id, final Model model) {
        final SlotMapping slotmapping = noOfApplicationsService.findById(id);
        if (slotmapping.getApplicationType().getName().equals(APPLICATION_TYPE_ONEDAYPERMIT))
            slotmapping.setDays(WorkingDays.valueOf(WorkingDays.getEnumNameForValue(slotmapping.getDay())));
        preapreUpdateModel(slotmapping, model);
        return SLOTMAPPING_VIEW;
    }

    @PostMapping(value = "/search/view", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String viewslotMapping(@Valid @ModelAttribute final SlotMapping slotMapping, final BindingResult result,
            final Model model) {
        if (result.hasErrors()) {
            preapreUpdateModel(slotMapping, model);
            return SLOTMAPPING_VIEW;
        }
        List<SlotMapping> slotMappingList = noOfApplicationsService.searchSlotMapping(slotMapping);
        return new StringBuilder(DATA).append(toJSON(slotMappingList, SlotMapping.class, SlotMappingJsonAdaptor.class))
                .append("}").toString();
    }

    @GetMapping("/result")
    public String resultSlotMappingList(final Model model) {
        return SLOTMAPPINGLIST_RESULT;
    }

}
