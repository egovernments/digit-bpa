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

import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.Inspection;
import org.egov.bpa.transaction.entity.enums.ChecklistValues;
import org.egov.bpa.transaction.service.InspectionService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.pims.commons.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping(value = "/application")
public class InspectionController extends BpaGenericApplicationController {
	private static final String CREATEINSPECTIONDETAIL_FORM = "createinspectiondetail-form";

	@Autowired
	private InspectionService inspectionService;

	@RequestMapping(value = "/createinspectiondetails/{applicationNumber}", method = RequestMethod.GET)
	public String inspectionDetailForm(final Model model, @PathVariable final String applicationNumber) {
		final BpaApplication application = applicationBpaService.findByApplicationNumber(applicationNumber);
		Position ownerPosition = application.getCurrentState().getOwnerPosition();
		if (validateLoginUserAndOwnerIsSame(model, securityUtils.getCurrentUser(), ownerPosition))
			return COMMON_ERROR;
		loadApplication(model, application);
		return CREATEINSPECTIONDETAIL_FORM;
	}

	@RequestMapping(value = "/createinspectiondetails/{applicationNumber}", method = RequestMethod.POST)
	public String createInspection(@Valid @ModelAttribute final Inspection inspection,
								   @PathVariable final String applicationNumber, final Model model, final BindingResult resultBinder) {
		BpaApplication application = applicationBpaService.findByApplicationNumber(applicationNumber);
		if (resultBinder.hasErrors()) {
			loadApplication(model, application);
			return CREATEINSPECTIONDETAIL_FORM;
		}
		Position ownerPosition = application.getCurrentState().getOwnerPosition();
		if (validateLoginUserAndOwnerIsSame(model, securityUtils.getCurrentUser(), ownerPosition))
			return COMMON_ERROR;
		inspection.setDocket(inspectionService.buildDocDetFromUI(inspection));
		final Inspection savedInspection = inspectionService.save(inspection, application);
		model.addAttribute("message", messageSource.getMessage("msg.inspection.saved.success", null, null));
		return "redirect:/application/view-inspection/" + savedInspection.getId();
	}

	private void loadApplication(final Model model, final BpaApplication application) {

		if (application != null && application.getState() != null
			&& application.getState().getValue().equalsIgnoreCase(BpaConstants.APPLICATION_STATUS_REGISTERED)) {
			prepareWorkflowDataForInspection(model, application);
			model.addAttribute("loginUser", securityUtils.getCurrentUser());
			model.addAttribute(BpaConstants.APPLICATION_HISTORY,
					workflowHistoryService.getHistory(application.getAppointmentSchedule(), application.getCurrentState(), application.getStateHistory()));
		}
		final Inspection inspection = new Inspection();
		inspection.setInspectionDate(new Date());
		inspectionService.buildDocketDetailList(inspection);
		model.addAttribute("inspection", inspection);
		model.addAttribute("docketDetailLocList", inspection.getDocketDetailLocList());
		model.addAttribute("docketDetailMeasumentList", inspection.getDocketDetailMeasumentList());
		model.addAttribute("docketDetailAccessList", inspection.getDocketDetailAccessList());
		model.addAttribute("docketDetlSurroundingPlotList", inspection.getDocketDetlSurroundingPlotList());
		model.addAttribute("docketDetailLandTypeList", inspection.getDocketDetailLandTypeList());
		model.addAttribute("docketDetailProposedWorkList", inspection.getDocketDetailProposedWorkList());
		model.addAttribute("docketDetailWorkAsPerPlanList", inspection.getDocketDetailWorkAsPerPlanList());
		model.addAttribute("docketDetailHgtAbuttRoadList", inspection.getDocketDetailHgtAbuttRoadList());
		model.addAttribute("docketDetailAreaLoc", inspection.getDocketDetailAreaLoc());
		model.addAttribute("docketDetailLengthOfCompWall", inspection.getDocketDetailLengthOfCompWall());
		model.addAttribute("docketDetailNumberOfWell", inspection.getDocketDetailNumberOfWell());
		model.addAttribute("docketDetailErectionTower", inspection.getDocketDetailErectionTower());
		model.addAttribute("docketDetailShutter", inspection.getDocketDetailShutter());
		model.addAttribute("docketDetailRoofConversion", inspection.getDocketDetailRoofConversion());
		model.addAttribute("planScrutinyCheckList", checkListDetailService.findActiveCheckListByChecklistTypeAndOrderById("PLANSCRUTINY"));
		model.addAttribute("planScrutinyChecklistForDrawing", checkListDetailService.findActiveCheckListByChecklistTypeAndOrderById("PLANSCRUTINYDRAWING"));
		model.addAttribute("planScrutinyValues", ChecklistValues.values());
		model.addAttribute(BpaConstants.BPA_APPLICATION, application);
	}

}
