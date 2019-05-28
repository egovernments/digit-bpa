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
package org.egov.bpa.transaction.service;

import java.util.Date;
import java.util.List;

import org.egov.bpa.master.entity.Holiday;
import org.egov.bpa.master.entity.NocConfiguration;
import org.egov.bpa.master.service.HolidayListService;
import org.egov.bpa.master.service.NocConfigurationService;
import org.egov.bpa.transaction.entity.BpaNocApplication;
import org.egov.bpa.transaction.service.messaging.BPASmsAndEmailService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.infra.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class NocApprovalService {

	@Autowired
	public NocConfigurationService nocConfigurationService;
	@Autowired
	public BpaNocApplicationService bpaNocApplicationService;
	@Autowired
	public HolidayListService holidayListService;
	@Autowired
	private BpaStatusService statusService;
	@Autowired
    private BPASmsAndEmailService bpaSmsAndEmailService;
	
	@Transactional
    public void approveNocAsDeemed() {
		List<NocConfiguration> nocConfigIsDeemedList=nocConfigurationService.findIsDeemedApproval();
		for(NocConfiguration nocConfig:nocConfigIsDeemedList){
			Long sla=nocConfig.getSla();
			 List<BpaNocApplication> bpaNocApplication = bpaNocApplicationService.findInitiatedAppByType(nocConfig.getDepartment());
			for (BpaNocApplication nocApp : bpaNocApplication) {
				String applStatus = null;
				if (nocApp.getBpaApplication().getStatus() != null){
					applStatus = nocApp.getBpaApplication().getStatus().getCode();
				}
				if ((applStatus != null && !(applStatus.equalsIgnoreCase(BpaConstants.APPLICATION_STATUS_CANCELLED)
						|| applStatus.equalsIgnoreCase(BpaConstants.APPLICATION_STATUS_REJECTED)))) {
					
					if (nocApp.getSlaEndDate().compareTo(new Date())>=0) {
						nocApp.setStatus(statusService.findByModuleTypeAndCode(BpaConstants.CHECKLIST_TYPE_NOC,
								BpaConstants.NOC_DEEMED_APPROVED));
						nocApp.setDeemedApprovedDate(new Date());
						bpaNocApplicationService.save(nocApp);
						bpaSmsAndEmailService.sendSMSAndEmailForDeemedApprovalNoc(nocApp);
					}
				}
			}
		}
	}
}
