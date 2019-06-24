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
package org.egov.bpa.web.controller.transaction.citizen;

import static org.egov.bpa.utils.BpaConstants.WF_LBE_SUBMIT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_NEW_STATE;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.egov.bpa.master.service.BuildingConstructionStageService;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.PermitInspectionApplication;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.service.ApplicationBpaService;
import org.egov.bpa.transaction.service.InspectionApplicationService;
import org.egov.bpa.transaction.workflow.BpaWorkFlowService;
import org.egov.bpa.utils.BpaUtils;
import org.egov.eis.entity.Assignment;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.workflow.matrix.entity.WorkFlowMatrix;
import org.egov.pims.commons.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/inspection")
public class CitizenInspectionApplicationController {
	
    private static final String WORK_FLOW_ACTION = "workFlowAction";
	 
	@Autowired
	private ApplicationBpaService applicationService; 
	@Autowired
	private InspectionApplicationService inspectionService; 
	@Autowired
	protected ResourceBundleMessageSource messageSource;
	@Autowired
	protected SecurityUtils securityUtils;
	@Autowired
	private BpaUtils bpaUtils;
	@Autowired
	protected BpaWorkFlowService bpaWorkFlowService;
	@Autowired
	protected BuildingConstructionStageService constructionService;
	
	
    @GetMapping("/citizen/create")
    public String inspectionForm(final Model model) {
    	model.addAttribute("permitInspectionApplication",new PermitInspectionApplication());
    	model.addAttribute("constructions",constructionService.getAllActiveConstructionTypes());
        return "citizen-inspection-form";
    }
    
    @PostMapping("/citizen/create/{bpaApplicationNumber}")
    public String createInspection(@ModelAttribute final PermitInspectionApplication permitInspectionApplication,
    		@PathVariable final String bpaApplicationNumber, final HttpServletRequest request, final Model model, final BindingResult errors,
            final RedirectAttributes redirectAttributes) {
        String workFlowAction = request.getParameter(WORK_FLOW_ACTION);
        Boolean citizenOrBusinessUser = bpaUtils.logedInuseCitizenOrBusinessUser();
        WorkflowBean wfBean = new WorkflowBean();

        BpaApplication application = applicationService.findByApplicationNumber(bpaApplicationNumber);
        permitInspectionApplication.setApplication(application);
        
        wfBean.setWorkFlowAction(request.getParameter(WORK_FLOW_ACTION));

        Long approvalPosition = null;

        final WorkFlowMatrix wfMatrix = bpaUtils.getWfMatrixByCurrentState(
        		permitInspectionApplication.getInspectionApplication().getStateType(), WF_NEW_STATE, null);
        if (wfMatrix != null)
            approvalPosition = bpaUtils.getUserPositionIdByZone(wfMatrix.getNextDesignation(),
                    bpaUtils.getBoundaryForWorkflow(permitInspectionApplication.getApplication().getSiteDetail().get(0)).getId());
        
        if (citizenOrBusinessUser && workFlowAction != null && workFlowAction.equals(WF_LBE_SUBMIT_BUTTON)
                && (approvalPosition == 0 || approvalPosition == null)) {
            model.addAttribute("noJAORSAMessage", messageSource.getMessage("msg.official.not.exist",
                    new String[] { ApplicationThreadLocals.getMunicipalityName() }, LocaleContextHolder.getLocale()));
            return "citizen-inspection-form";
        }  
        PermitInspectionApplication permitInspectionResponse = inspectionService.saveOrUpdate(permitInspectionApplication, wfBean);

    
        if (citizenOrBusinessUser){
          
        	if( workFlowAction.equals(WF_LBE_SUBMIT_BUTTON))            
        		bpaUtils.createPortalUserinbox(permitInspectionResponse,Arrays.asList(permitInspectionResponse.getApplication().getOwner().getUser(), 
        				securityUtils.getCurrentUser()), workFlowAction);            	
            
        }
        if (workFlowAction != null && workFlowAction.equals(WF_LBE_SUBMIT_BUTTON)) {
            wfBean.setCurrentState(WF_NEW_STATE);

            bpaUtils.redirectInspectionWorkFlow(permitInspectionResponse, wfBean);
            
            List<Assignment> assignments;
            if (null == approvalPosition)
                assignments = bpaWorkFlowService.getAssignmentsByPositionAndDate(
                        permitInspectionResponse.getInspectionApplication().getCurrentState().getOwnerPosition().getId(), new Date());
            else
                assignments = bpaWorkFlowService.getAssignmentsByPositionAndDate(approvalPosition, new Date());
            Position pos = assignments.get(0).getPosition();
            User wfUser = assignments.get(0).getEmployee();
            String message = messageSource.getMessage("msg.portal.forward.inspection",
                    new String[] {
                            wfUser == null ? ""
                                    : wfUser.getUsername().concat("~").concat(getDesinationNameByPosition(pos)),
                                    permitInspectionResponse.getInspectionApplication().getApplicationNumber() },
                    LocaleContextHolder.getLocale());
            
            redirectAttributes.addFlashAttribute("message", message);
        }
        bpaUtils.sendSmsEmailForInspection(permitInspectionResponse.getInspectionApplication(), permitInspectionResponse.getApplication());
        
        return "redirect:/inspection/citizen/success/" + permitInspectionResponse.getInspectionApplication().getApplicationNumber();
    }
    
    @GetMapping("/citizen/success/{applicationNumber}")
    public String success(@PathVariable final String applicationNumber, final Model model) {
        return "citizen-inspection-success";
    }
    
    protected String getDesinationNameByPosition(Position pos) {
        return pos.getDeptDesig() != null && pos.getDeptDesig().getDesignation() == null
                ? ""
                : pos.getDeptDesig().getDesignation().getName();
    }
}