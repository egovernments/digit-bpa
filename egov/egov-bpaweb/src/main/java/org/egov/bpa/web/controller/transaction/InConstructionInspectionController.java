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

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.egov.bpa.master.entity.ChecklistServiceTypeMapping;
import org.egov.bpa.transaction.entity.InConstructionInspection;
import org.egov.bpa.transaction.entity.PermitInspectionApplication;
import org.egov.bpa.transaction.entity.common.InspectionCommon;
import org.egov.bpa.transaction.entity.common.InspectionFilesCommon;
import org.egov.bpa.transaction.entity.enums.ChecklistValues;
import org.egov.bpa.transaction.service.InConstructionInspectionService;
import org.egov.bpa.transaction.service.InspectionApplicationService;
import org.egov.bpa.utils.BpaConstants;
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
@RequestMapping(value = "/inspection")
public class InConstructionInspectionController extends BpaGenericApplicationController {
	private static final String CREATE_INSPECTION = "inconst-create-inspection";

	@Autowired
    private CustomImplProvider specificNoticeService;
	@Autowired
	private InspectionApplicationService inspectionAppService;

	@GetMapping("/createinspection/{applicationNumber}")
	public String inspectionDetailForm(final Model model, @PathVariable final String applicationNumber) {
		loadApplication(model, applicationNumber);
		return CREATE_INSPECTION;
	}

	@PostMapping("/createinspection/{applicationNumber}")
	public String createInspection(@Valid @ModelAttribute final InConstructionInspection inConstructionInspection,
								   @PathVariable final String applicationNumber, final Model model, final BindingResult resultBinder) {
        final PermitInspectionApplication permitInspection = inspectionAppService.findByInspectionApplicationNumber(applicationNumber);
        inConstructionInspection.setInspectionApplication(permitInspection.getInspectionApplication());
		final InConstructionInspectionService inConstructionInspectionService = (InConstructionInspectionService) specificNoticeService
                .find(InConstructionInspectionService.class, specificNoticeService.getCityDetails());
		//inConstructionInspection.getInspection().setDocket(inConstructionInspectionService.buildDocDetFromUI(inConstructionInspection));
		if (resultBinder.hasErrors()) {
			loadApplication(model, applicationNumber);
			return CREATE_INSPECTION;
		}
		final InConstructionInspection savedInspection = inConstructionInspectionService.save(inConstructionInspection);
		model.addAttribute("message", messageSource.getMessage("msg.inspection.saved.success", null, null));
		return "redirect:/inspection/success/view-inspection-details/" + applicationNumber + "/" + savedInspection.getInspection().getInspectionNumber();
	}

	private void loadApplication(final Model model, final String applicationNumber) {
        final PermitInspectionApplication permitInspection = inspectionAppService.findByInspectionApplicationNumber(applicationNumber);
		if (permitInspection.getInspectionApplication() != null && permitInspection.getInspectionApplication().getState() != null
			&& permitInspection.getInspectionApplication().getState().getValue().equalsIgnoreCase(BpaConstants.INITIATEINSPECTION)) {
			prepareWorkflowDataForInspection(model, permitInspection.getInspectionApplication());
			model.addAttribute("loginUser", securityUtils.getCurrentUser());
			model.addAttribute(BpaConstants.APPLICATION_HISTORY,
					workflowHistoryService.getHistoryForInspection(permitInspection.getInspectionApplication().getAppointmentSchedules(), permitInspection.getInspectionApplication().getCurrentState(), permitInspection.getInspectionApplication().getStateHistory()));
		}
		final InConstructionInspection inConstructionInspection = new InConstructionInspection();
		InspectionCommon inspectionCommon = new InspectionCommon();
		inspectionCommon.setInspectionDate(new Date());
		final InConstructionInspectionService inConstInspectionService = (InConstructionInspectionService) specificNoticeService
                .find(InConstructionInspectionService.class, specificNoticeService.getCityDetails());
		inConstInspectionService.buildDocketDetailList(inspectionCommon, permitInspection.getApplication().getServiceType().getId());
		inConstructionInspection.setInspection(inspectionCommon);
		inConstructionInspection.setInspectionApplication(permitInspection.getInspectionApplication());
		model.addAttribute("inConstructionInspection", inConstructionInspection);
		model.addAttribute("docketDetailLocList", inspectionCommon.getDocketDetailLocList());
		model.addAttribute("docketDetailMeasurementList", inspectionCommon.getDocketDetailMeasurementList());
		model.addAttribute("planScrutinyCheckList",inConstInspectionService.buildPlanScrutiny(permitInspection.getApplication().getServiceType().getId()));
		model.addAttribute("planScrutinyValues", ChecklistValues.values());
		model.addAttribute("planScrutinyChecklistForDrawing",inConstInspectionService.buildPlanScrutinyDrawing(permitInspection.getApplication().getServiceType().getId()));
		List<ChecklistServiceTypeMapping> imagesChecklist = checklistServiceTypeService
                        .findByActiveByServiceTypeAndChecklist(permitInspection.getApplication().getServiceType().getId(), "INCNSTRNINSPNIMAGES");
                for (ChecklistServiceTypeMapping serviceChklst : imagesChecklist) {
                    InspectionFilesCommon inspectionFile = new InspectionFilesCommon();
                    inspectionFile.setServiceChecklist(serviceChklst);
                    inspectionFile.setInspection(inConstructionInspection.getInspection());
                    inConstructionInspection.getInspection().getInspectionSupportDocs().add(inspectionFile);
                }
		model.addAttribute(BpaConstants.BPA_APPLICATION, permitInspection.getApplication());
		model.addAttribute("inspectionApplication", permitInspection.getInspectionApplication());
	}

}
