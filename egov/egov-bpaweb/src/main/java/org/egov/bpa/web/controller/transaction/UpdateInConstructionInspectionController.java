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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.egov.bpa.transaction.entity.InConstructionInspection;
import org.egov.bpa.transaction.entity.InspectionApplication;
import org.egov.bpa.transaction.entity.PermitInspectionApplication;
import org.egov.bpa.transaction.entity.common.DocketDetailCommon;
import org.egov.bpa.transaction.entity.enums.ChecklistValues;
import org.egov.bpa.transaction.service.InConstructionInspectionService;
import org.egov.bpa.transaction.service.InspectionApplicationService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.infra.custom.CustomImplProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/inspection")
public class UpdateInConstructionInspectionController extends BpaGenericApplicationController {

	 private static final String SHOW_INSPECTION_DETAILS = "inconst-show-inspection";

	 private static final String INSPECTION_RESULT = "inconst-inspection-success";
	 @Autowired
	 private CustomImplProvider specificNoticeService;
	 @Autowired
	 protected ResourceBundleMessageSource messageSource;
	 @Autowired
	 private InspectionApplicationService inspectionAppService;
	 @Autowired
	 private InConstructionInspectionService inspectionService;
	 
	 public InConstructionInspection getInspection(@PathVariable final String applicationNumber) {
	        final PermitInspectionApplication permitInspection = inspectionAppService.findByInspectionApplicationNumber(applicationNumber);

	        InConstructionInspection inspection = null;
	        final List<InConstructionInspection> inspections = inspectionService
	                .findByInspectionApplicationOrderByIdAsc(permitInspection.getInspectionApplication());
	        if (!inspections.isEmpty())
	            inspection = inspections.get(0);
	        return inspection;
	 }

	@GetMapping("/updateinspection/{applicationNumber}")
	public String editInspectionAppointment(
			@PathVariable final String applicationNumber, final Model model) {
		loadApplication(model, getInspection(applicationNumber));
		model.addAttribute("mode", "editinsp");
		return "inconst-inspection-edit";
	}

	@PostMapping("/updateinspection/{applicationNumber}")
	public String updateInspection(@ModelAttribute("inConstructionInspection") final InConstructionInspection inConstructionInspection,
								   @PathVariable final String applicationNumber, final Model model, final BindingResult resultBinder) {
        final PermitInspectionApplication permitInspection = inspectionAppService.findByInspectionApplicationNumber(applicationNumber);
        inConstructionInspection.setInspectionApplication(permitInspection.getInspectionApplication());
		if (resultBinder.hasErrors()) {
			loadApplication(model, inConstructionInspection);
			return "inconst-inspection-edit";
		}
		/*final List<DocketDetailCommon> docketDetailTempList = buildDocketDetails(inConstructionInspection);
		inConstructionInspection.getInspection().getDocket().get(0).setDocketDetail(docketDetailTempList);*/
		final InConstructionInspectionService inConstInspectionService = (InConstructionInspectionService) specificNoticeService
                .find(InConstructionInspectionService.class, specificNoticeService.getCityDetails());
		InConstructionInspection inConstInspectionRes = inConstInspectionService.save(inConstructionInspection);
		model.addAttribute("message", messageSource.getMessage("msg.inspection.saved.success", null, null));
		return "redirect:/inspection/success/view-inspection-details/" + applicationNumber + "/" + inConstInspectionRes.getInspection().getInspectionNumber();
	}

	private List<DocketDetailCommon> buildDocketDetails(@ModelAttribute("inConstructionInspection") InConstructionInspection inConstructionInspection) {
		final List<DocketDetailCommon> docketDetailTempList = new ArrayList<>();
		final InConstructionInspectionService inConstInspectionService = (InConstructionInspectionService) specificNoticeService
                .find(InConstructionInspectionService.class, specificNoticeService.getCityDetails());
		final List<DocketDetailCommon> docketDetailList = inConstInspectionService.buildDocketDetail(inConstructionInspection.getInspection());
		for (final DocketDetailCommon docketDet : inConstructionInspection.getInspection().getDocket().get(0).getDocketDetail())
			for (final DocketDetailCommon tempLoc : docketDetailList)
				if (docketDet.getServiceChecklist().getId().equals(tempLoc.getServiceChecklist().getId())) {
					docketDet.setValue(tempLoc.getValue());
					docketDet.setRemarks(tempLoc.getRemarks());
					docketDetailTempList.add(docketDet);
				}
		return docketDetailTempList;
	}

	private void loadApplication(final Model model, final InConstructionInspection inConstructionInspection) {
		final InspectionApplication inspectionApp = inConstructionInspection.getInspectionApplication();
		if (inspectionApp != null && inspectionApp.getState() != null
				&& inspectionApp.getState().getValue().equalsIgnoreCase(BpaConstants.INITIATEINSPECTION)) {
			prepareWorkflowDataForInspection(model, inspectionApp);
			model.addAttribute("loginUser", securityUtils.getCurrentUser());
			model.addAttribute(BpaConstants.APPLICATION_HISTORY, workflowHistoryService
					.getHistoryForInspection(inspectionApp.getAppointmentSchedules(), inspectionApp.getCurrentState(), inspectionApp.getStateHistory()));
		}
		if (inConstructionInspection != null)
			inConstructionInspection.getInspection().setInspectionDate(new Date());
		final InConstructionInspectionService inConstInspectionService  = (InConstructionInspectionService) specificNoticeService
                .find(InConstructionInspectionService.class, specificNoticeService.getCityDetails());
		inConstInspectionService.prepareImagesForView(inConstructionInspection);
		//inConstInspectionService.buildDocketDetailForModifyAndViewList(inConstructionInspection.getInspection(), model);
		model.addAttribute("inConstructionInspection", inConstructionInspection);
		//model.addAttribute(BpaConstants.BPA_APPLICATION, oc.getParent());
		model.addAttribute("inConstructionInspection", inConstructionInspection);
		model.addAttribute("planScrutinyValues", ChecklistValues.values());

		inConstructionInspection.getInspection().setPlanScrutinyChecklistForRuleTemp(inConstInspectionService.getPlanScrutinyForRuleValidation(inConstructionInspection));
		inConstructionInspection.getInspection().setPlanScrutinyChecklistForDrawingTemp(inConstInspectionService.getPlanScrutinyForDrawingDetails(inConstructionInspection));

	}

	 @GetMapping("/success/view-inspection-details/{applicationNumber}/{inspectionNumber}")
	    public String viewInspection(@PathVariable final String applicationNumber,
	            @PathVariable final String inspectionNumber, final Model model) {
			final InConstructionInspectionService inConstInspectionService  = (InConstructionInspectionService) specificNoticeService
	                .find(InConstructionInspectionService.class, specificNoticeService.getCityDetails());
			InConstructionInspection inConstructionInspection = inConstInspectionService.findByInspectionApplicationNoAndInspectionNo(applicationNumber, inspectionNumber);
	        model.addAttribute("docketDetail", inConstructionInspection.getInspection().getDocket().get(0).getDocketDetail());
	        model.addAttribute("message", messageSource.getMessage("msg.inspection.saved.success", null, null));
	        //inConstInspectionService.buildDocketDetailForModifyAndViewList(inConstructionInspection.getInspection(), model);
	        inConstInspectionService.prepareImagesForView(inConstructionInspection);
	        inConstInspectionService.buildPlanScrutinyChecklistDetails(inConstructionInspection);
	        model.addAttribute("inConstructionInspection", inConstructionInspection);
	        return INSPECTION_RESULT;
	    }

	    @GetMapping("/show-inspection-details/{applicationNumber}/{inspectionNumber}")
	    public String showInspectionDetails(@PathVariable final String applicationNumber,
	            @PathVariable final String inspectionNumber, final Model model) {
	    	final InConstructionInspectionService inConstInspectionService  = (InConstructionInspectionService) specificNoticeService
	                .find(InConstructionInspectionService.class, specificNoticeService.getCityDetails());
			InConstructionInspection inConstructionInspection = inConstInspectionService.findByInspectionApplicationNoAndInspectionNo(applicationNumber, inspectionNumber);
	        model.addAttribute("docketDetail", inConstructionInspection.getInspection().getDocket().get(0).getDocketDetail());
	        //inConstInspectionService.buildDocketDetailForModifyAndViewList(inConstructionInspection.getInspection(), model);
	        inConstInspectionService.prepareImagesForView(inConstructionInspection);
	        inConstInspectionService.buildPlanScrutinyChecklistDetails(inConstructionInspection);
	        model.addAttribute("inConstructionInspection", inConstructionInspection);
	        return INSPECTION_RESULT;
	    }    

}
