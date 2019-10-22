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

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.egov.bpa.config.properties.BpaApplicationSettings;
import org.egov.bpa.master.entity.ChecklistServiceTypeMapping;
import org.egov.bpa.transaction.entity.common.InspectionCommon;
import org.egov.bpa.transaction.entity.common.InspectionFilesCommon;
import org.egov.bpa.transaction.entity.enums.ChecklistValues;
import org.egov.bpa.transaction.entity.oc.OCInspection;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.service.oc.OcInspectionService;
import org.egov.bpa.transaction.service.oc.OccupancyCertificateService;
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
public class CreateInspectionForOccupancyCertificateController extends BpaGenericApplicationController {
    private static final String CREATE_INSPECTION = "oc-create-inspection";

    @Autowired
    private OccupancyCertificateService occupancyCertificateService;
    @Autowired
    private CustomImplProvider specificNoticeService;
    @Autowired
    private BpaApplicationSettings bpaApplicationSettings;

    @GetMapping("/create-inspection/{applicationNumber}")
    public String inspectionDetailForm(final Model model, @PathVariable final String applicationNumber) {
        loadApplication(model, applicationNumber, new OCInspection(),new InspectionCommon());
        return CREATE_INSPECTION;
    }

    @PostMapping("/create-inspection/{applicationNumber}")
    public String createInspection(@PathVariable final String applicationNumber,
            @Valid @ModelAttribute("ocInspection") final OCInspection ocInspection,
            final BindingResult resultBinder,
            final Model model) {
        if (resultBinder.hasErrors()) {
            loadApplication(model, applicationNumber, ocInspection,ocInspection.getInspection());
            return CREATE_INSPECTION;
        }
        final OcInspectionService ocInspectionService = (OcInspectionService) specificNoticeService
                .find(OcInspectionService.class, specificNoticeService.getCityDetails());
        // ocInspection.getInspection().setDocket(ocInspectionService.buildDocDetFromUI(ocInspection));
        ocInspectionService.validateinspectionDocs(ocInspection, resultBinder);
        if (resultBinder.hasErrors()) {
            loadApplication(model, applicationNumber, ocInspection, ocInspection.getInspection());
            return CREATE_INSPECTION;
        }
        final OCInspection savedInspection = ocInspectionService.save(ocInspection);
        model.addAttribute("message", messageSource.getMessage("msg.inspection.saved.success", null, null));
        return "redirect:/application/occupancy-certificate/success/view-inspection-details/" + applicationNumber + "/"
                + savedInspection.getInspection().getInspectionNumber();
    }

    private void loadApplication(final Model model, final String applicationNumber , final OCInspection ocInspection, final InspectionCommon inspectionCommon ) {
        final OccupancyCertificate oc = occupancyCertificateService.findByApplicationNumber(applicationNumber);
        if (oc != null && oc.getState() != null
                && oc.getState().getValue().equalsIgnoreCase(BpaConstants.APPLICATION_STATUS_REGISTERED)) {
            prepareWorkflowDataForInspection(model, oc);
            model.addAttribute("loginUser", securityUtils.getCurrentUser());
            model.addAttribute(BpaConstants.APPLICATION_HISTORY,
                    workflowHistoryService.getHistoryForOC(oc.getAppointmentSchedules(), oc.getCurrentState(),
                            oc.getStateHistory()));
        }
        inspectionCommon.setInspectionDate(new Date());
        final OcInspectionService ocInspectionService = (OcInspectionService) specificNoticeService
                .find(OcInspectionService.class, specificNoticeService.getCityDetails());
        ocInspectionService.buildDocketDetailList(inspectionCommon, oc.getParent().getServiceType().getId());
        ocInspection.setInspection(inspectionCommon);
        ocInspection.setOc(oc);
        model.addAttribute("ocInspection", ocInspection);
        model.addAttribute("docketDetailLocList", inspectionCommon.getDocketDetailLocList());
        model.addAttribute("docketDetailMeasurementList", inspectionCommon.getDocketDetailMeasurementList());
        model.addAttribute("planScrutinyCheckList",
                ocInspectionService.buildPlanScrutiny(oc.getParent().getServiceType().getId()));
        model.addAttribute("planScrutinyValues", ChecklistValues.values());
        model.addAttribute("planScrutinyChecklistForDrawing",
                ocInspectionService.buildPlanScrutinyDrawing(oc.getParent().getServiceType().getId()));
        List<ChecklistServiceTypeMapping> imagesChecklist = checklistServiceTypeService
                .findByActiveByServiceTypeAndChecklist(oc.getParent().getServiceType().getId(), "OCINSPNIMAGES");
        for (ChecklistServiceTypeMapping serviceChklst : imagesChecklist) {
            InspectionFilesCommon inspectionFile = new InspectionFilesCommon();
            inspectionFile.setServiceChecklist(serviceChklst);
            inspectionFile.setInspection(ocInspection.getInspection());
            ocInspection.getInspection().getInspectionSupportDocs().add(inspectionFile);
        }
        model.addAttribute(BpaConstants.BPA_APPLICATION, oc.getParent());
        model.addAttribute(OcConstants.OCCUPANCY_CERTIFICATE, oc);
        model.addAttribute("inspectionDocAllowedExtenstions",
                bpaApplicationSettings.getValue("bpa.oc.inspection.docs.allowed.extenstions"));
        model.addAttribute("inspectionDocMaxSize", bpaApplicationSettings.getValue("bpa.oc.inspection.docs.max.size"));
    }

}
