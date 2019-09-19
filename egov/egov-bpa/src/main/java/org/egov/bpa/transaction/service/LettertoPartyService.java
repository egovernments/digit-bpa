/**
 * eGov suite of products aim to improve the internal efficiency,transparency,
   accountability and the service delivery of the government  organizations.
    Copyright (C) <2015>  eGovernments Foundation
    The updated version of eGov suite of products as by eGovernments Foundation
    is available at http://www.egovernments.org
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program. If not, see http://www.gnu.org/licenses/ or
    http://www.gnu.org/licenses/gpl.html .
    In addition to the terms of the GPL license to be adhered to in using this
    program, the following additional terms are to be complied with:
        1) All versions of this program, verbatim or modified must carry this
           Legal Notice.
        2) Any misrepresentation of the origin of the material is prohibited. It
           is required that all modified versions of this material be marked in
           reasonable ways as different from the original version.
        3) This license does not grant any rights to any user of the program
           with regards to rights under trademark law for use of the trade names
           or trademarks of eGovernments Foundation.
  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.bpa.transaction.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.egov.bpa.autonumber.LettertoPartyNumberGenerator;
import org.egov.bpa.autonumber.LettertoPartyReplyAckNumberGenerator;
import org.egov.bpa.config.properties.BpaApplicationSettings;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.PermitLetterToParty;
import org.egov.bpa.transaction.entity.common.LetterToPartyDocumentCommon;
import org.egov.bpa.transaction.repository.LettertoPartyRepository;
import org.egov.bpa.transaction.service.messaging.BPASmsAndEmailService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.commons.service.FinancialYearService;
import org.egov.infra.utils.autonumber.AutonumberServiceBeanResolver;
import org.egov.infra.workflow.entity.StateHistory;
import org.egov.pims.commons.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

@Service
@Transactional(readOnly = true)
public class LettertoPartyService {
    private static final Logger LOG = LoggerFactory.getLogger(LettertoPartyService.class);

    private static final String LETTER_TO_PARTY_INITIATE = "Letter to party initiate";
    static final String LPCHK = "lp";
    static final String LPREPLYCHK = "lpreply";

    private final LettertoPartyRepository lettertoPartyRepository;
    @Autowired
    private FinancialYearService financialYearService;
    @Autowired
    private AutonumberServiceBeanResolver beanResolver;
    @Autowired
    BpaUtils bpaUtils;
    @Autowired
    private BpaStatusService bpaStatusService;
    @Autowired
    private BPASmsAndEmailService bpaSmsAndEmailService;
    @Autowired
    private BpaApplicationSettings bpaApplicationSettings;

    @Autowired
    public LettertoPartyService(final LettertoPartyRepository lettertoPartyRepository) {
        this.lettertoPartyRepository = lettertoPartyRepository;
    }

    @Transactional
    public PermitLetterToParty save(final PermitLetterToParty permitLTP, final Long approverPosition) {
        if (permitLTP.getLetterToParty().getLpNumber() == null || "".equals(permitLTP.getLetterToParty().getLpNumber())) {
            permitLTP.getLetterToParty().setLetterDate(new Date());
            permitLTP.getLetterToParty().setLpNumber(generateLettertpPartyNumber());
            bpaUtils.redirectToBpaWorkFlow(approverPosition, permitLTP.getApplication(), BpaConstants.LETTERTOPARTYINITIATE,
                    LETTER_TO_PARTY_INITIATE, BpaConstants.LETTERTOPARTYINITIATE, null);
            bpaSmsAndEmailService.sendSMSAndEmailToApplicantForLettertoparty(permitLTP.getApplication());
        }

        if (permitLTP.getLetterToParty().getReplyDate() != null) {
            permitLTP.getLetterToParty().setAcknowledgementNumber(generateLettertpPartyReplyAck());
            bpaUtils.redirectToBpaWorkFlow(permitLTP.getApplication().getState().getOwnerPosition().getId(),
                    permitLTP.getApplication(), BpaConstants.LPCREATED,
                    BpaConstants.LPREPLYRECEIVED, BpaConstants.LPCREATED, null);
            permitLTP.getApplication().setStatus(bpaStatusService
                    .findByModuleTypeAndCode(BpaConstants.BPASTATUS_MODULETYPE, BpaConstants.LETTERTOPARTY_REPLY_RECEIVED));
        }
        return lettertoPartyRepository.save(permitLTP);
    }

    public String generateLettertpPartyNumber() {
        final String financialYearRange = financialYearService
                .getCurrentFinancialYear().getFinYearRange();
        final LettertoPartyNumberGenerator lettertoPartyNumberGenerator = beanResolver
                .getAutoNumberServiceFor(LettertoPartyNumberGenerator.class);
        return lettertoPartyNumberGenerator.generateLettertoPartyNumber(financialYearRange);
    }

    public PermitLetterToParty findById(final Long id) {
        return lettertoPartyRepository.findOne(id);
    }

    public Long getDocScutinyUser(BpaApplication bpaApplication) {
        Long docSrcuityUserPos = null;
        if (!bpaApplication.getStateHistory().isEmpty()) {
            for (final StateHistory<Position> stateHistory : bpaApplication.getStateHistory()) {
                if (stateHistory.getValue().equals(BpaConstants.APPLICATION_STATUS_REGISTERED)) {
                    docSrcuityUserPos = stateHistory.getOwnerPosition().getId();
                    break;
                }
            }
        }
        return docSrcuityUserPos;
    }

    public List<PermitLetterToParty> findByBpaApplicationOrderByIdDesc(final BpaApplication application) {
        return lettertoPartyRepository.findByApplicationOrderByIdDesc(application);
    }

    public String generateLettertpPartyReplyAck() {
        final String financialYearRange = financialYearService
                .getCurrentFinancialYear().getFinYearRange();
        final LettertoPartyReplyAckNumberGenerator lettertoPartyReplyAckNumberGenerator = beanResolver
                .getAutoNumberServiceFor(LettertoPartyReplyAckNumberGenerator.class);
        return lettertoPartyReplyAckNumberGenerator.generateLettertoPartyReplyAckNumber(financialYearRange);
    }

    public void validateDocs(final PermitLetterToParty lettertoparty, final BindingResult errors) {
        List<String> lpDocAllowedExtenstions = new ArrayList<>(
                Arrays.asList(bpaApplicationSettings.getValue("bpa.lpreply.docs.allowed.extenstions").split(",")));

        List<String> lpDocMimeTypes = new ArrayList<>(
                Arrays.asList(bpaApplicationSettings.getValue("bpa.lpreply.docs.allowed.mime.types").split(",")));

        Integer i = 0;
        for (LetterToPartyDocumentCommon document : lettertoparty.getLetterToParty().getLetterToPartyDocuments()) {
            bpaUtils.validateFiles(errors, lpDocAllowedExtenstions, lpDocMimeTypes, document.getFiles(),
                    "lettertoPartyDocument[" + i + "].files",
                    bpaApplicationSettings.getValue("bpa.lpreply.docs.max.size"));
            i++;
        }

    }
}