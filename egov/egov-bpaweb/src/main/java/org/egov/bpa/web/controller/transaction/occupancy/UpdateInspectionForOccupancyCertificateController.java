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
package org.egov.bpa.web.controller.transaction.occupancy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.egov.bpa.config.properties.BpaApplicationSettings;
import org.egov.bpa.transaction.entity.common.DocketDetailCommon;
import org.egov.bpa.transaction.entity.enums.ChecklistValues;
import org.egov.bpa.transaction.entity.oc.OCInspection;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.service.oc.OcInspectionService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.OcConstants;
import org.egov.bpa.web.controller.transaction.BpaGenericApplicationController;
import org.egov.infra.custom.CustomImplProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/application/occupancy-certificate")
public class UpdateInspectionForOccupancyCertificateController extends BpaGenericApplicationController {

    @Autowired
    private CustomImplProvider specificNoticeService;
    @Autowired
    private OcInspectionService ocInspectionService;
    @Autowired
    private BpaApplicationSettings bpaApplicationSettings;

    @GetMapping("/update-inspection/{applicationNumber}/{inspectionNumber}")
    public String editInspectionAppointment(
            @PathVariable final String applicationNumber, @PathVariable final String inspectionNumber, final Model model) {
        final OcInspectionService ocInspectionService = (OcInspectionService) specificNoticeService
                .find(OcInspectionService.class, specificNoticeService.getCityDetails());
        OCInspection ocInspection = ocInspectionService.findByOcApplicationNoAndInspectionNo(applicationNumber, inspectionNumber);
        loadApplication(model, ocInspection);
        model.addAttribute("mode", "editinsp");
        return "oc-inspection-edit";
    }

    @PostMapping("/update-inspection/{applicationNumber}/{inspectionNumber}")
    public String updateInspection(@PathVariable final String applicationNumber,
            @PathVariable final String inspectionNumber,
            @Valid @ModelAttribute("ocInspection") final OCInspection ocInspection,
            final BindingResult resultBinder,
            final Model model) {
        ocInspectionService.validateinspectionDocs(ocInspection, resultBinder);
        if (resultBinder.hasErrors()) {
            loadApplication(model, ocInspection);
            return "oc-inspection-edit";
        }
        /*
         * final List<DocketDetailCommon> docketDetailTempList = buildDocketDetails(ocInspection);
         * ocInspection.getInspection().getDocket().get(0).setDocketDetail(docketDetailTempList);
         */
        final OcInspectionService ocInspectionService = (OcInspectionService) specificNoticeService
                .find(OcInspectionService.class, specificNoticeService.getCityDetails());
        OCInspection ocInspectionRes = ocInspectionService.save(ocInspection);
        model.addAttribute("message", messageSource.getMessage("msg.inspection.saved.success", null, null));
        return "redirect:/application/occupancy-certificate/success/view-inspection-details/" + applicationNumber + "/"
                + ocInspectionRes.getInspection().getInspectionNumber();
    }

    private List<DocketDetailCommon> buildDocketDetails(final OCInspection ocInspection) {
        final List<DocketDetailCommon> docketDetailTempList = new ArrayList<>();
        final OcInspectionService ocInspectionService = (OcInspectionService) specificNoticeService
                .find(OcInspectionService.class, specificNoticeService.getCityDetails());
        final List<DocketDetailCommon> docketDetailList = ocInspectionService.buildDocketDetail(ocInspection.getInspection());
        for (final DocketDetailCommon docketDet : ocInspection.getInspection().getDocket().get(0).getDocketDetail())
            for (final DocketDetailCommon tempLoc : docketDetailList)
                if (docketDet.getServiceChecklist().getId().equals(tempLoc.getServiceChecklist().getId())) {
                    docketDet.setValue(tempLoc.getValue());
                    docketDet.setRemarks(tempLoc.getRemarks());
                    docketDetailTempList.add(docketDet);
                }
        return docketDetailTempList;
    }

    private void loadApplication(final Model model, final OCInspection ocInspection) {
        final OccupancyCertificate oc = ocInspection.getOc();
        if (oc != null && oc.getState() != null
                && oc.getState().getValue().equalsIgnoreCase(BpaConstants.APPLICATION_STATUS_REGISTERED)) {
            prepareWorkflowDataForInspection(model, oc);
            model.addAttribute("loginUser", securityUtils.getCurrentUser());
            model.addAttribute(BpaConstants.APPLICATION_HISTORY, workflowHistoryService
                    .getHistoryForOC(oc.getAppointmentSchedules(), oc.getCurrentState(), oc.getStateHistory()));
        }
        if (ocInspection != null)
            ocInspection.getInspection().setInspectionDate(new Date());
        final OcInspectionService ocInspectionService = (OcInspectionService) specificNoticeService
                .find(OcInspectionService.class, specificNoticeService.getCityDetails());
        ocInspectionService.prepareImagesForView(ocInspection);
        // ocInspectionService.buildDocketDetailForModifyAndViewList(ocInspection.getInspection(), model);
        model.addAttribute("ocInspection", ocInspection);
        model.addAttribute(BpaConstants.BPA_APPLICATION, oc.getParent());
        model.addAttribute(OcConstants.OCCUPANCY_CERTIFICATE, oc);
        model.addAttribute("planScrutinyValues", ChecklistValues.values());

        ocInspection.getInspection()
                .setPlanScrutinyChecklistForRuleTemp(ocInspectionService.getPlanScrutinyForRuleValidation(ocInspection));
        ocInspection.getInspection()
                .setPlanScrutinyChecklistForDrawingTemp(ocInspectionService.getPlanScrutinyForDrawingDetails(ocInspection));
        model.addAttribute("inspectionDocAllowedExtenstions",
                bpaApplicationSettings.getValue("bpa.oc.inspection.docs.allowed.extenstions"));
        model.addAttribute("inspectionDocMaxSize", bpaApplicationSettings.getValue("bpa.oc.inspection.docs.max.size"));
    }

}
