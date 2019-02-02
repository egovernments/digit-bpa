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

import org.egov.bpa.master.service.CheckListDetailService;
import org.egov.bpa.transaction.entity.Inspection;
import org.egov.bpa.transaction.entity.enums.ChecklistValues;
import org.egov.bpa.transaction.entity.enums.ScrutinyChecklistType;
import org.egov.bpa.transaction.entity.oc.OCInspection;
import org.egov.bpa.transaction.service.oc.OCInspectionService;
import org.egov.bpa.transaction.service.oc.OCPlanScrutinyChecklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/application/occupancy-certificate")
public class ViewInspectionForOccupancyCertificateController {
	private static final String SHOW_INSPECTION_DETAILS = "oc-show-inspection";

	private static final String INSPECTION_RESULT = "oc-inspection-success";

	@Autowired
	private OCInspectionService ocInspectionService;

	@Autowired
	protected ResourceBundleMessageSource messageSource;

	@Autowired
	private OCPlanScrutinyChecklistService planScrutinyChecklistService;

	@GetMapping("/success/view-inspection-details/{applicationNumber}/{inspectionNumber}")
	public String viewInspection(@PathVariable final String applicationNumber,
			@PathVariable final String inspectionNumber, final Model model) {
		OCInspection ocInspection = ocInspectionService.findByOcApplicationNoAndInspectionNo(applicationNumber,
				inspectionNumber);
		model.addAttribute("docketDetail", ocInspection.getInspection().getDocket().get(0).getDocketDetail());
		model.addAttribute("message", messageSource.getMessage("msg.inspection.saved.success", null, null));
		ocInspectionService.buildDocketDetailForModifyAndViewList(ocInspection.getInspection(), model);
		ocInspection.getInspection().setEncodedImages(ocInspectionService.prepareImagesForView(ocInspection));
		buildPlanScrutinyChecklistDetails(ocInspection, model);
		model.addAttribute("ocInspection", ocInspection);
		return INSPECTION_RESULT;
	}

	@GetMapping("/show-inspection-details/{applicationNumber}/{inspectionNumber}")
	public String showInspectionDetails(@PathVariable final String applicationNumber,
			@PathVariable final String inspectionNumber, final Model model) {
		OCInspection ocInspection = ocInspectionService.findByOcApplicationNoAndInspectionNo(applicationNumber,
				inspectionNumber);
		model.addAttribute("docketDetail", ocInspection.getInspection().getDocket().get(0).getDocketDetail());
		model.addAttribute("inspection", ocInspection);
		ocInspectionService.buildDocketDetailForModifyAndViewList(ocInspection.getInspection(), model);
		ocInspection.getInspection().setEncodedImages(ocInspectionService.prepareImagesForView(ocInspection));
		model.addAttribute("ocInspection", ocInspection);
		buildPlanScrutinyChecklistDetails(ocInspection, model);
		model.addAttribute("planScrutinyValues", ChecklistValues.values());
		return SHOW_INSPECTION_DETAILS;
	}

	private void buildPlanScrutinyChecklistDetails(OCInspection inspectionObj, Model model) {
		inspectionObj.getPlanScrutinyChecklist().clear();
		inspectionObj.setPlanScrutinyChecklist(planScrutinyChecklistService
				.findByInspectionAndScrutinyChecklistType(inspectionObj, ScrutinyChecklistType.RULE_VALIDATION));
		model.addAttribute("modePSC", "separate");
		if (inspectionObj.getPlanScrutinyChecklist().isEmpty()) {
			model.addAttribute("modePSC", "combined");
			inspectionObj.setPlanScrutinyChecklist(planScrutinyChecklistService
					.findByInspectionAndScrutinyChecklistType(inspectionObj, ScrutinyChecklistType.COMBINED));
		}
		inspectionObj.getPlanScrutinyChecklistForDrawing().clear();
		inspectionObj.setPlanScrutinyChecklistForDrawing(planScrutinyChecklistService
				.findByInspectionAndScrutinyChecklistType(inspectionObj, ScrutinyChecklistType.DRAWING_DETAILS));
	}

}
