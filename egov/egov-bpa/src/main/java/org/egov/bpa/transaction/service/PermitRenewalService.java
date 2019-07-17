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
import static org.egov.bpa.utils.BpaConstants.RENEWALSTATUS_MODULETYPE;
import static org.egov.bpa.utils.BpaConstants.WF_SAVE_BUTTON;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.egov.bpa.master.entity.PermitRenewal;
import org.egov.bpa.service.es.PermitRenewalIndexService;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.repository.PermitRenewalRepository;
import org.egov.bpa.utils.BpaWorkflowRedirectUtility;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.utils.ApplicationNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author vinoth
 *
 */
@Service
@Transactional(readOnly = true)
public class PermitRenewalService {

    @Autowired
    private PermitRenewalRepository permitRenewalRepository;
    @Autowired
    private ApplicationNumberGenerator applicationNumberGenerator;
    @Autowired
    private ApplicationBpaService applicationBpaService;
    @Autowired
    private PermitRenewalIndexService permitRenewalIndexService;
    @Autowired
    private BpaWorkflowRedirectUtility bpaWorkflowRedirectUtility;
    @Autowired
    private BpaStatusService bpaStatusService;

    @Transactional
    public PermitRenewal save(final PermitRenewal permitRenewal, final WorkflowBean wfBean) {
        if (permitRenewal.getApplicationNumber() == null) {
            permitRenewal.setApplicationNumber(applicationNumberGenerator.generate());
            permitRenewal.setApplicationDate(new Date());
        }
        buildDocuments(permitRenewal);
        permitRenewal.setStatus(bpaStatusService.findByModuleTypeAndCode(RENEWALSTATUS_MODULETYPE, APPLICATION_STATUS_CREATED));
        if (!WF_SAVE_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction()))
            bpaWorkflowRedirectUtility.redirectToBpaWorkFlow(permitRenewal, wfBean);
        PermitRenewal renewalResponse = permitRenewalRepository.saveAndFlush(permitRenewal);
        permitRenewalIndexService.createPermitRenewalIndex(renewalResponse);
        return renewalResponse;
    }

    private void buildDocuments(final PermitRenewal permitRenewal) {
        if (permitRenewal.getFiles() != null) {
            Set<FileStoreMapper> docs = new HashSet<>();
            for (MultipartFile file : permitRenewal.getFiles())
                docs.add(applicationBpaService.addToFileStore(file));
            permitRenewal.setPermitRenewalDocs(docs);
        }
    }

    @Transactional
    public PermitRenewal update(PermitRenewal permitRenewal, final WorkflowBean wfBean) {
        PermitRenewal permitRenewalRes = permitRenewalRepository.save(permitRenewal);
        bpaWorkflowRedirectUtility.redirectToBpaWorkFlow(permitRenewal, wfBean);
        permitRenewalIndexService.updateIndexes(permitRenewalRes);
        return permitRenewalRes;
    }

    public PermitRenewal findByPlanPermissionNumberAndRevocationApplnDate(final String permitNumber) {
        List<PermitRenewal> permitRenewals = permitRenewalRepository
                .findByParentPlanPermissionNumberOrderByIdDesc(permitNumber);
        return permitRenewals.isEmpty() ? null : permitRenewals.get(0);
    }

    public PermitRenewal findByApplicationNumber(final String applicationNumber) {
        return permitRenewalRepository.findByApplicationNumber(applicationNumber);
    }

    public PermitRenewal findByRevocationNumber(final String renewalNumber) {
        return permitRenewalRepository.findByRenewalNumber(renewalNumber);
    }

}
