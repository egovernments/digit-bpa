/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2018>  eGovernments Foundation
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

import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_CREATED;
import static org.egov.bpa.utils.BpaConstants.AUTOGENERATE_OWNERSHIP_NUMBER;
import static org.egov.bpa.utils.BpaConstants.EGMODULE_NAME;
import static org.egov.bpa.utils.BpaConstants.RENEWALSTATUS_MODULETYPE;
import static org.egov.bpa.utils.BpaConstants.WF_APPROVE_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_SAVE_BUTTON;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.egov.bpa.autonumber.PlanPermissionNumberGenerator;
import org.egov.bpa.transaction.entity.OwnershipTransfer;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.repository.OwnershipTransferRepository;
import org.egov.bpa.utils.BpaWorkflowRedirectUtility;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.utils.ApplicationNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;



@Service
@Transactional(readOnly = true)
public class OwnershipTransferService {

    @Autowired
    private OwnershipTransferRepository ownershipTransferRepository;
    @Autowired
    private ApplicationNumberGenerator applicationNumberGenerator;
    @Autowired
    private ApplicationBpaService applicationBpaService;
    @Autowired
    private BpaStatusService bpaStatusService;
    @Autowired
    private AppConfigValueService appConfigValueService;
    @Autowired
    private PlanPermissionNumberGenerator planPermissionNumberGenerator;
    @Autowired
    private BpaWorkflowRedirectUtility bpaWorkflowRedirectUtility;

    @Transactional
    public OwnershipTransfer save(final OwnershipTransfer ownershipTransfer, final WorkflowBean wfBean) {
        if (ownershipTransfer.getApplicationNumber() == null) {
        	ownershipTransfer.setApplicationNumber(applicationNumberGenerator.generate());
        	ownershipTransfer.setApplicationDate(new Date());
        }
        buildDocuments(ownershipTransfer);
        ownershipTransfer.setStatus(bpaStatusService.findByModuleTypeAndCode(RENEWALSTATUS_MODULETYPE, APPLICATION_STATUS_CREATED));
        if (!WF_SAVE_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction()))
            bpaWorkflowRedirectUtility.redirectToBpaWorkFlow(ownershipTransfer, wfBean);
        OwnershipTransfer ownershipResponse = ownershipTransferRepository.saveAndFlush(ownershipTransfer);
        return ownershipResponse;
    }

    @Transactional
    public void saveOwnership(OwnershipTransfer ownershipTransfer) {
    	ownershipTransferRepository.saveAndFlush(ownershipTransfer);
    }

    private void buildDocuments(final OwnershipTransfer ownershipTransfer) {
        if (ownershipTransfer.getFiles() != null && ownershipTransfer.getFiles().length > 0) {
            Set<FileStoreMapper> docs = new HashSet<>();
            for (MultipartFile file : ownershipTransfer.getFiles())
                docs.add(applicationBpaService.addToFileStore(file));
            ownershipTransfer.setOwnershipTransferDocs(docs);
        }
    }

    @Transactional
    public OwnershipTransfer update(OwnershipTransfer ownershipTransfer, final WorkflowBean wfBean) {
        if (WF_APPROVE_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
        	ownershipTransfer.setOwnershipApprovalDate(new Date());
        	if (Boolean.valueOf(appConfigValueService.getConfigValuesByModuleAndKey(EGMODULE_NAME,
        			AUTOGENERATE_OWNERSHIP_NUMBER).get(0).getValue())) {
               ownershipTransfer.setOwnershipNumber(ownershipTransfer.getParent().getPlanPermissionNumber());
            }
        	else
        	   ownershipTransfer.setOwnershipNumber(planPermissionNumberGenerator.generatePlanPermissionNumber(ownershipTransfer.getParent()));
        }
        
        OwnershipTransfer permitRenewalRes = ownershipTransferRepository.save(ownershipTransfer);
        bpaWorkflowRedirectUtility.redirectToBpaWorkFlow(ownershipTransfer, wfBean);
        return permitRenewalRes;
    }

}
