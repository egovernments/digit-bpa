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
package org.egov.bpa.web.controller.transaction;

import static org.egov.bpa.utils.BpaConstants.ENABLEONLINEPAYMENT;
import static org.egov.bpa.utils.BpaConstants.FILESTORE_MODULECODE;
import static org.egov.bpa.utils.BpaConstants.RENEWALSTATUS_MODULETYPE;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.egov.bpa.transaction.entity.PermitRenewal;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationForm;
import org.egov.bpa.transaction.notice.util.BpaNoticeUtil;
import org.egov.bpa.transaction.service.PermitRenewalService;
import org.egov.bpa.transaction.service.SearchPermitRenewalService;
import org.egov.bpa.web.controller.adaptor.SearchBpaApplicationAdaptor;
import org.egov.infra.web.support.ui.DataTable;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/application/permitrenewal")
public class SearchPermitRenewalController extends BpaGenericApplicationController {

    private static final String APPLICATION_HISTORY = "applicationHistory";
    private static final String SEARCH_BPA_APPLICATION_FORM = "searchBpaApplicationForm";

    @Autowired
    private SearchPermitRenewalService searchRenewalService;
    @Autowired
    private BpaNoticeUtil bpaNoticeUtil;
    @Autowired
    private PermitRenewalService renewalService;

    @GetMapping("/search")
    public String showSearchApprovedforFee(final Model model) {
        prepareFormData(model);
        model.addAttribute("applnStatusList", bpaStatusService.findAllByModuleType(RENEWALSTATUS_MODULETYPE));
        model.addAttribute(SEARCH_BPA_APPLICATION_FORM, new SearchBpaApplicationForm());
        return "search-permit-renewal";
    }

    @PostMapping(value = "/search", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String searchRegisterStatusMarriageRecords(@ModelAttribute final SearchBpaApplicationForm searchBpaApplicationForm) {
        return new DataTable<>(searchRenewalService.pagedSearch(searchBpaApplicationForm),
                searchBpaApplicationForm.draw())
                .toJson(SearchBpaApplicationAdaptor.class);
    }

    @GetMapping("/view/{applicationNumber}")
    public String viewApplicationForm(final Model model, @PathVariable final String applicationNumber,
            final HttpServletRequest request) {
    	PermitRenewal permitRenewal = renewalService.findByApplicationNumber(applicationNumber);
                model.addAttribute("permitExpiryDate", bpaNoticeUtil.calculateCertExpryDate(
                new DateTime(permitRenewal.getParent().getPlanPermissionDate()),
                permitRenewal.getParent().getServiceType().getValidity()));
        model.addAttribute("permitRenewal", permitRenewal);
        model.addAttribute("constStages", constructionStagesService.findByRequiredForPermitRenewal());
        model.addAttribute(APPLICATION_HISTORY,
                workflowHistoryService.getHistory(Collections.emptyList(), permitRenewal.getCurrentState(),
                        permitRenewal.getStateHistory()));
        model.addAttribute("feePending", bpaUtils.checkAnyTaxIsPendingToCollect(permitRenewal.getDemand()));
        String enableOrDisablePayOnline = bpaUtils.getAppconfigValueByKeyName(ENABLEONLINEPAYMENT);
        model.addAttribute("onlinePaymentEnable",
                (enableOrDisablePayOnline.equalsIgnoreCase("YES") ? Boolean.TRUE : Boolean.FALSE));
        if (permitRenewal.getDemand() != null)
            buildReceiptDetails(permitRenewal.getDemand().getEgDemandDetails(), permitRenewal.getReceipts());
        return "permit-renewal-citizen-view";
    }
    
    @GetMapping("/downloadfile/{fileStoreId}")
    public ResponseEntity<InputStreamResource> download(@PathVariable final String fileStoreId) {
        return fileStoreUtils.fileAsResponseEntity(fileStoreId, FILESTORE_MODULECODE, true);
    } 
}
