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

import org.egov.bpa.transaction.entity.InspectionApplication;
import org.egov.bpa.transaction.entity.InspectionLetterToParty;
import org.egov.bpa.transaction.entity.PermitInspectionApplication;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.repository.InspectionLetterToPartyRepository;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class InspectionLetterToPartyService {
	
    static final String LPCHK = "lp";
    static final String LPREPLYCHK = "lpreply";

    @Autowired
    private InspectionLetterToPartyRepository lettertoPartyRepository;
    @Autowired
    private LettertoPartyService lettertoPartyService;
    @Autowired
    BpaUtils bpaUtils;
    @Autowired
    private BpaStatusService bpaStatusService;

    @Transactional
    public InspectionLetterToParty save(final InspectionLetterToParty inspectionLTP, final PermitInspectionApplication permitInspection, final Long approverPosition) {
    	WorkflowBean wfBean = new WorkflowBean();

    	if (inspectionLTP.getLetterToParty().getLpNumber() == null || "".equals(inspectionLTP.getLetterToParty().getLpNumber())) {
        	inspectionLTP.getLetterToParty().setLetterDate(new Date());
        	inspectionLTP.getLetterToParty().setLpNumber(lettertoPartyService.generateLettertpPartyNumber());
        	wfBean.setApproverPositionId(approverPosition);
        	wfBean.setCurrentState(BpaConstants.LETTERTOPARTYINITIATE);
        	wfBean.setApproverComments(BpaConstants.LETTERTOPARTYINITIATE);
        	wfBean.setWorkFlowAction(BpaConstants.LETTERTOPARTYINITIATE);

            bpaUtils.redirectInspectionWorkFlow(permitInspection, wfBean);
          //  bpaSmsAndEmailService.sendSMSAndEmailToApplicantForLettertoparty(permitLTP.getApplication());
        }

        if (inspectionLTP.getLetterToParty().getReplyDate() != null) {
        	wfBean.setApproverPositionId(inspectionLTP.getInspectionApplication().getState().getOwnerPosition().getId());
        	wfBean.setCurrentState(BpaConstants.LPCREATED);
        	wfBean.setApproverComments(BpaConstants.LPREPLYRECEIVED);
        	wfBean.setWorkFlowAction(BpaConstants.LPCREATED);

        	inspectionLTP.getLetterToParty().setAcknowledgementNumber(lettertoPartyService.generateLettertpPartyReplyAck());
            bpaUtils.redirectInspectionWorkFlow(permitInspection, wfBean);

            inspectionLTP.getInspectionApplication().setStatus(bpaStatusService
                    .findByModuleTypeAndCode(BpaConstants.INSPECTION_MODULE_TYPE, BpaConstants.LETTERTOPARTY_REPLY_RECEIVED));
        }
        return lettertoPartyRepository.save(inspectionLTP);
    }


    public InspectionLetterToParty findById(final Long id) {
        return lettertoPartyRepository.findOne(id);
    }   

    public List<InspectionLetterToParty> findByInspectionApplicationOrderByIdDesc(final InspectionApplication application) {
        return lettertoPartyRepository.findByInspectionApplicationOrderByIdDesc(application);
    }

 
}