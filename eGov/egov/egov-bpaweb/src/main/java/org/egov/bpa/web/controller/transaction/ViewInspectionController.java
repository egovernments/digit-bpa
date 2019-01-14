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
package org.egov.bpa.web.controller.transaction;

import org.egov.bpa.transaction.entity.DocketDetail;
import org.egov.bpa.transaction.entity.Inspection;
import org.egov.bpa.transaction.entity.enums.ScrutinyChecklistType;
import org.egov.bpa.transaction.service.InspectionService;
import org.egov.bpa.transaction.service.PlanScrutinyChecklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/application")
public class ViewInspectionController {
    private static final String SHOW_INSPECTION_DETAILS = "show-inspection-details";

    private static final String INSPECTION_RESULT = "inspection-details-result";

    @Autowired
    private InspectionService inspectionService;
    @Autowired
    private PlanScrutinyChecklistService planScrutinyChecklistService;


    @RequestMapping(value = "/view-inspection/{id}", method = RequestMethod.GET)
    public String viewInspection(@PathVariable final Long id, final Model model) {
        final List<Inspection> inspection = inspectionService.findByIdOrderByIdAsc(id);
        List<DocketDetail> dockeDetList = new ArrayList<>();
        if (!inspection.isEmpty())
            dockeDetList = inspection.get(0).getDocket().get(0).getDocketDetail();
        model.addAttribute("docketDetail", dockeDetList);
        Inspection inspectionObj=inspection.get(0);
        model.addAttribute("inspection", inspection.get(0));
        model.addAttribute("message", "Inspection Saved Successfully");
        inspectionService.buildDocketDetailForModifyAndViewList(inspectionObj,model);
        inspectionObj.setEncodedImages(inspectionService.prepareImagesForView(inspectionObj));
        model.addAttribute("inspection", inspectionObj);
        buildPlanScrutinyChecklistDetails(inspectionObj, model);
        return INSPECTION_RESULT;
    }
    
    @RequestMapping(value = "/showinspectiondetails/{id}", method = RequestMethod.GET)
    public String showInspectionDetails(@PathVariable final Long id, final Model model) {
        final List<Inspection> inspection = inspectionService.findByIdOrderByIdAsc(id);
        List<DocketDetail> dockeDetList = new ArrayList<>();
        if (!inspection.isEmpty())
            dockeDetList = inspection.get(0).getDocket().get(0).getDocketDetail();
        model.addAttribute("docketDetail", dockeDetList);
        Inspection inspectionObj=inspection.get(0);
        model.addAttribute("inspection", inspection.get(0));
        inspectionService.buildDocketDetailForModifyAndViewList(inspectionObj,model);
        inspectionObj.setEncodedImages(inspectionService.prepareImagesForView(inspectionObj));
        model.addAttribute("inspection", inspectionObj);
        buildPlanScrutinyChecklistDetails(inspectionObj, model);
        return SHOW_INSPECTION_DETAILS;
    }

    private void buildPlanScrutinyChecklistDetails(Inspection inspectionObj, Model model) {
        inspectionObj.getPlanScrutinyChecklist().clear();
        inspectionObj.setPlanScrutinyChecklist(planScrutinyChecklistService.findByInspectionAndScrutinyChecklistType(inspectionObj, ScrutinyChecklistType.RULE_VALIDATION));
        model.addAttribute("modePSC", "separate");
        if(inspectionObj.getPlanScrutinyChecklist().isEmpty()) {
            model.addAttribute("modePSC", "combined");
            inspectionObj.setPlanScrutinyChecklist(planScrutinyChecklistService.findByInspectionAndScrutinyChecklistType(inspectionObj, ScrutinyChecklistType.COMBINED));
        }
        inspectionObj.getPlanScrutinyChecklistForDrawing().clear();
        inspectionObj.setPlanScrutinyChecklistForDrawing(planScrutinyChecklistService.findByInspectionAndScrutinyChecklistType(inspectionObj, ScrutinyChecklistType.DRAWING_DETAILS));
    }


}
