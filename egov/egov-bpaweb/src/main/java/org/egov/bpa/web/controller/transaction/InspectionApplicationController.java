/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) <2017>  eGovernments Foundation
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
package org.egov.bpa.web.controller.transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.egov.bpa.master.entity.ChecklistServiceTypeMapping;
import org.egov.bpa.master.service.BuildingConstructionStageService;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.InConstructionInspection;
import org.egov.bpa.transaction.entity.PermitInspectionApplication;
import org.egov.bpa.transaction.entity.common.InspectionCommon;
import org.egov.bpa.transaction.entity.common.InspectionFilesCommon;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationForm;
import org.egov.bpa.transaction.entity.enums.ChecklistValues;
import org.egov.bpa.transaction.service.ApplicationBpaService;
import org.egov.bpa.transaction.service.InConstructionInspectionService;
import org.egov.bpa.transaction.service.InspectionApplicationService;
import org.egov.bpa.transaction.service.SearchBpaApplicationService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.web.controller.adaptor.InspectionCaptureAdaptor;
import org.egov.infra.custom.CustomImplProvider;
import org.egov.infra.web.support.ui.DataTable;
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

@Controller
@RequestMapping(value = "/inspection")
public class InspectionApplicationController extends BpaGenericApplicationController {

    private static final String SEARCH_BPA_APPLICATION_FORM = "searchBpaApplicationForm";
    private static final String CAPTURE_INSPECTION = "inconst-capture-inspection";

    @Autowired
    private SearchBpaApplicationService searchBpaApplicationService;
    @Autowired
    private CustomImplProvider specificNoticeService;
    @Autowired
    private InspectionApplicationService inspectionAppService;
    @Autowired
    private ApplicationBpaService applicationService;
    @Autowired
    protected BuildingConstructionStageService constructionService;

    @GetMapping("/search")
    public String showRevocationSearchForm(final Model model) {
        model.addAttribute(SEARCH_BPA_APPLICATION_FORM, new SearchBpaApplicationForm());
        return "search-inspection-application";
    }

    @PostMapping(value = "/search", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String searchApplications(@ModelAttribute final SearchBpaApplicationForm searchBpaApplicationForm) {
        List<Long> userIds = new ArrayList<>();
        userIds.add(securityUtils.getCurrentUser().getId());
        return new DataTable<>(searchBpaApplicationService.searchInspection(searchBpaApplicationForm, userIds),
                searchBpaApplicationForm.draw())
                        .toJson(InspectionCaptureAdaptor.class);
    }

    @GetMapping("/captureinspection/{applicationNumber}")
    public String inspectionForm(final Model model, @PathVariable final String applicationNumber) {
        loadApplication(model, applicationNumber);
        return CAPTURE_INSPECTION;
    }

    @PostMapping("/captureinspection/{applicationNumber}")
    public String captureInspection(@PathVariable final String applicationNumber,
            @Valid @ModelAttribute final InConstructionInspection inConstructionInspection, final BindingResult resultBinder,
            final Model model) {

        if (resultBinder.hasErrors()) {
            loadApplication(model, applicationNumber);
            return CAPTURE_INSPECTION;
        }
        final PermitInspectionApplication permitInspection = new PermitInspectionApplication();
        final BpaApplication application = applicationService.findByApplicationNumber(applicationNumber);
        permitInspection.setApplication(application);
        permitInspection.setInspectionApplication(inConstructionInspection.getInspectionApplication());
        PermitInspectionApplication permitInspectionRes = inspectionAppService.save(permitInspection);
        inConstructionInspection.setInspectionApplication(permitInspectionRes.getInspectionApplication());
        final InConstructionInspectionService inConstructionInspectionService = (InConstructionInspectionService) specificNoticeService
                .find(InConstructionInspectionService.class, specificNoticeService.getCityDetails());
        final InConstructionInspection savedInspection = inConstructionInspectionService.save(inConstructionInspection);
        model.addAttribute("message", messageSource.getMessage("msg.inspection.saved.success", null, null));
        return "redirect:/inspection/result/view-inspection-details/"
                + permitInspectionRes.getInspectionApplication().getApplicationNumber() + "/"
                + savedInspection.getInspection().getInspectionNumber();
    }

    @GetMapping("/result/view-inspection-details/{applicationNumber}/{inspectionNumber}")
    public String viewInspection(@PathVariable final String applicationNumber,
            @PathVariable final String inspectionNumber, final Model model) {
        final InConstructionInspectionService inConstInspectionService = (InConstructionInspectionService) specificNoticeService
                .find(InConstructionInspectionService.class, specificNoticeService.getCityDetails());
        final PermitInspectionApplication permitInspection = inspectionAppService
                .findByInspectionApplicationNumber(applicationNumber);
        InConstructionInspection inConstructionInspection = inConstInspectionService
                .findByInspectionApplicationNoAndInspectionNo(applicationNumber, inspectionNumber);
        model.addAttribute("docketDetail", inConstructionInspection.getInspection().getDocket().get(0).getDocketDetail());
        model.addAttribute("message", messageSource.getMessage("msg.inspection.saved.success", null, null));
        inConstInspectionService.prepareImagesForView(inConstructionInspection);
        inConstInspectionService.buildPlanScrutinyChecklistDetails(inConstructionInspection);
        model.addAttribute("inConstructionInspection", inConstructionInspection);
        model.addAttribute(BpaConstants.BPA_APPLICATION, permitInspection.getApplication());
        return "inconst-inspection-result";
    }

    private void loadApplication(final Model model, final String applicationNumber) {
        final BpaApplication application = applicationService.findByApplicationNumber(applicationNumber);
        model.addAttribute("loginUser", securityUtils.getCurrentUser());
        model.addAttribute("constructions", constructionService.getAllActiveConstructionTypes());
        final InConstructionInspection inConstructionInspection = new InConstructionInspection();
        InspectionCommon inspectionCommon = new InspectionCommon();
        inspectionCommon.setInspectionDate(new Date());
        final InConstructionInspectionService inConstInspectionService = (InConstructionInspectionService) specificNoticeService
                .find(InConstructionInspectionService.class, specificNoticeService.getCityDetails());
        inConstInspectionService.buildDocketDetailList(inspectionCommon, application.getServiceType().getId());
        inConstructionInspection.setInspection(inspectionCommon);
        // inConstructionInspection.setInspectionApplication(permitInspection.getInspectionApplication());
        model.addAttribute("inConstructionInspection", inConstructionInspection);
        model.addAttribute("docketDetailLocList", inspectionCommon.getDocketDetailLocList());
        model.addAttribute("docketDetailMeasurementList", inspectionCommon.getDocketDetailMeasurementList());
        model.addAttribute("planScrutinyCheckList",
                inConstInspectionService.buildPlanScrutiny(application.getServiceType().getId()));
        model.addAttribute("planScrutinyValues", ChecklistValues.values());
        model.addAttribute("planScrutinyChecklistForDrawing",
                inConstInspectionService.buildPlanScrutinyDrawing(application.getServiceType().getId()));
        List<ChecklistServiceTypeMapping> imagesChecklist = checklistServiceTypeService
                .findByActiveByServiceTypeAndChecklist(application.getServiceType().getId(), "INCNSTRNINSPNIMAGES");
        for (ChecklistServiceTypeMapping serviceChklst : imagesChecklist) {
            InspectionFilesCommon inspectionFile = new InspectionFilesCommon();
            inspectionFile.setServiceChecklist(serviceChklst);
            inspectionFile.setInspection(inConstructionInspection.getInspection());
            inConstructionInspection.getInspection().getInspectionSupportDocs().add(inspectionFile);
        }
        model.addAttribute(BpaConstants.BPA_APPLICATION, application);
        // model.addAttribute("inspectionApplication", permitInspection.getInspectionApplication());
    }
}