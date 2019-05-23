/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
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

import java.util.ArrayList;
import java.util.List;

import org.egov.bpa.master.entity.NocConfiguration;
import org.egov.bpa.master.service.NocConfigurationService;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BpaNocApplication;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.PermitNocDocument;
import org.egov.bpa.transaction.entity.enums.NocIntegrationInitiationEnum;
import org.egov.bpa.transaction.entity.enums.NocIntegrationTypeEnum;
import org.egov.bpa.transaction.repository.BpaNocApplicationRepository;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class BpaNocApplicationService {
	
	@Autowired
	private BpaNocApplicationRepository nocRepository;
	@Autowired
	private BpaStatusService statusService;
	@Autowired
	private BpaUtils bpaUtils;
	@Autowired
    private NocConfigurationService nocConfigurationService;
	@Autowired
    private UserService userService;
	
	@Transactional
	public BpaNocApplication save(final BpaNocApplication nocApplication) {
		return nocRepository.save(nocApplication);
	}
	
	@Transactional
	public BpaNocApplication update(final BpaNocApplication nocApplication) {
		return nocRepository.save(nocApplication);
	}
	
	public List<BpaNocApplication> findByApplicationNumber(String appNo) {
		return nocRepository.findByApplicationNumber(appNo);		
	}
	
	public BpaNocApplication findByApplicationNumberAndType(String appNo, final String nocType) {
		return nocRepository.findByApplicationNumberAndType(appNo, nocType);		
	}
	
	public List<BpaNocApplication> findInitiatedAppByType(final String nocType) {
		return nocRepository.findInitiatedAppByType(nocType);		
	}
	
	public BpaNocApplication createNocApplication(BpaApplication application, NocConfiguration nocConfig) {
		BpaNocApplication nocApplication = new BpaNocApplication() ;	
		BpaStatus status = statusService.findByModuleTypeAndCode(BpaConstants.CHECKLIST_TYPE_NOC, BpaConstants.NOC_INITIATED);
		nocApplication.setBpaApplication(application);
		nocApplication.setNocType(nocConfig.getDepartment());
		nocApplication.setStatus(status);	
		return save(nocApplication);	
	}
	
	public void initiateNoc(BpaApplication application) {
    for (PermitNocDocument nocDocument : application.getPermitNocDocuments()) {
		NocConfiguration nocConfig = nocConfigurationService
				.findByDepartment(nocDocument.getNocDocument().getServiceChecklist().getChecklist().getCode());
		if (nocConfig.getIntegrationType().equalsIgnoreCase(NocIntegrationTypeEnum.SEMI_AUTO.toString())
				&& nocConfig.getIntegrationInitiation().equalsIgnoreCase(NocIntegrationInitiationEnum.AUTO.toString())) {
			ArrayList<User> nocUsers = new ArrayList<User>(userService.getUsersByRoleName(BpaConstants.getNocRole().get(nocConfig))); 
		    BpaNocApplication nocApplication = createNocApplication(application, nocConfig);			 
	        bpaUtils.createNocPortalUserinbox(nocApplication, nocUsers, nocApplication.getStatus().getCode());
		}
      }
	}
	
	public void createNoc(BpaApplication application, String nocType) {
		NocConfiguration nocConfig = nocConfigurationService
				.findByDepartment(nocType);
		if (nocConfig.getIntegrationType().equalsIgnoreCase(NocIntegrationTypeEnum.SEMI_AUTO.toString())
				&& nocConfig.getIntegrationInitiation().equalsIgnoreCase(NocIntegrationInitiationEnum.MANUAL.toString())) {
			ArrayList<User> nocUsers = new ArrayList<User>(userService.getUsersByRoleName(BpaConstants.getNocRole().get(nocConfig.getDepartment()))); 
		    BpaNocApplication nocApplication = createNocApplication(application, nocConfig);			 
	        bpaUtils.createNocPortalUserinbox(nocApplication, nocUsers, nocApplication.getStatus().getCode());
		}
	}
	
    
}