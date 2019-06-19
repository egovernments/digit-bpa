/* eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2017>  eGovernments Foundation
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
package org.egov.bpa.transaction.service;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_SUBMITTED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_TS_INS_INITIATED;
import static org.egov.bpa.utils.BpaConstants.WF_APPROVE_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_LBE_SUBMIT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_SAVE_BUTTON;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.egov.bpa.autonumber.InspectionApplicationNumberGenerator;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.PermitInspectionApplication;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.repository.BpaStatusRepository;
import org.egov.bpa.transaction.repository.InspectionApplicationRepository;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.infra.utils.autonumber.AutonumberServiceBeanResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class InspectionApplicationService {
    
    @Autowired
    private InspectionApplicationRepository inspectionReposiory;
    
    @Autowired
    private AutonumberServiceBeanResolver beanResolver;
    
    @Autowired
	private InspectionApplicationService inspectionService; 
    
    @Autowired
    private BpaStatusRepository statusRepository;
    
    
    @Autowired
    private BpaUtils bpaUtils;
    
    @PersistenceContext
	private EntityManager entityManager;
    
    @Transactional
	public PermitInspectionApplication save(final PermitInspectionApplication inspection) {
		return inspectionReposiory.save(inspection);
	}
    
    @Transactional
	public PermitInspectionApplication findByInspectionApplicationNumber(final String appNo) {
		return inspectionReposiory.findByInspectionApplicationApplicationNumber(appNo);
	}
    
    public String generateInspectionNumber() {
        final InspectionApplicationNumberGenerator inspectionNUmber = beanResolver
                .getAutoNumberServiceFor(InspectionApplicationNumberGenerator.class);
        return inspectionNUmber.generateInspectionNumber("INSP");
    }
    
    @Transactional
    public PermitInspectionApplication saveOrUpdate(final PermitInspectionApplication permitInspection, final WorkflowBean wfBean) {
        if (permitInspection.getInspectionApplication().getApplicationDate() == null)
        	permitInspection.getInspectionApplication().setApplicationDate(new Date());
        if (permitInspection.getInspectionApplication().getApplicationNumber() == null)
        	permitInspection.getInspectionApplication().setApplicationNumber(inspectionService.generateInspectionNumber());
        if (wfBean.getWorkFlowAction() != null && wfBean.getWorkFlowAction().equals(WF_LBE_SUBMIT_BUTTON)) {
            final BpaStatus bpaStatus = statusRepository.findByCodeAndModuleType(APPLICATION_STATUS_SUBMITTED, BpaConstants.INSPECTION_MODULE_TYPE);
            permitInspection.getInspectionApplication().setStatus(bpaStatus);
        }        
        return inspectionReposiory.save(permitInspection);  
    } 
    
    
    @Transactional
    public PermitInspectionApplication update(final PermitInspectionApplication permitInspectionApplication, final WorkflowBean wfBean) {
        if (WF_APPROVE_BUTTON.equals(wfBean.getWorkFlowAction())) {
        	permitInspectionApplication.getInspectionApplication().setApprovalDate(new Date());          
        }
        permitInspectionApplication.getInspectionApplication().setSentToPreviousOwner(false);
        
        if (APPLICATION_STATUS_TS_INS_INITIATED.equals(permitInspectionApplication.getInspectionApplication().getStatus().getCode())) {
        	permitInspectionApplication.getInspectionApplication().setTownSurveyorInspectionRequire(false);
        }
        if (!WF_SAVE_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction()))
            bpaUtils.redirectInspectionWorkFlow(permitInspectionApplication, wfBean);
        
        inspectionReposiory.saveAndFlush(permitInspectionApplication);
        return permitInspectionApplication;
    }
}