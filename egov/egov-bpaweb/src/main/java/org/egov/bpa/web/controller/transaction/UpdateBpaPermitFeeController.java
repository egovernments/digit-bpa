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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.egov.bpa.master.entity.BpaFee;
import org.egov.bpa.master.service.BpaFeeService;
import org.egov.bpa.transaction.entity.ApplicationFee;
import org.egov.bpa.transaction.entity.ApplicationFeeDetail;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.service.ApplicationBpaFeeCalculationService;
import org.egov.bpa.transaction.service.ApplicationBpaService;
import org.egov.bpa.transaction.service.ApplicationFeeService;
import org.egov.bpa.transaction.service.BpaStatusService;
import org.egov.bpa.transaction.service.collection.BpaDemandService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/application")
public class UpdateBpaPermitFeeController {
    private static final String BPA_APPLICATIONFEE = "applicationFee";
    private static final String BPA_APPLICATION = "bpaApplication";
    private static final String CREATEBPAFEE_FORM = "createbpafee-form";
    private static final String CREATEBPAFEE_VIEW = "createbpafee-view";
    private static final String ADDITIONALRULE = "additionalRule";
    private static final String MESSAGE = "message";

    @Autowired
    protected ApplicationBpaService applicationBpaService;
    @Autowired
    private AppConfigValueService appConfigValuesService;
    @Autowired
    protected BpaFeeService bpaFeeService;
    @Autowired
    private BpaStatusService bpaStatusService;
    @Autowired
    protected ApplicationFeeService applicationFeeService;
    @Autowired
    protected ResourceBundleMessageSource messageSource;
    @Autowired
    protected BpaDemandService bpaDemandService;
    @Autowired
    private ApplicationBpaFeeCalculationService applicationBpaFeeCalculationService;
    @ModelAttribute
    public ApplicationFee getBpaApplication(@PathVariable final String applicationNumber) {
        BpaApplication bpaApplication = applicationBpaService.findByApplicationNumber(applicationNumber);
        ApplicationFee applicationFee = new ApplicationFee();
        if (bpaApplication != null) {
            List<ApplicationFee> applicationFeeList = applicationFeeService
                    .getApplicationFeeListByApplicationId(bpaApplication.getId());
            if (applicationFeeList.isEmpty()) {
                applicationFee.setApplication(bpaApplication);
                return applicationFee;
            } else {
                return applicationFeeList.get(0);
            }
        }
        return applicationFee;
    }

    @RequestMapping(value = "/calculateFee/{applicationNumber}", method = RequestMethod.GET)
    public String calculateFeeform(final Model model, @PathVariable final String applicationNumber,
            final HttpServletRequest request) {
        ApplicationFee applicationFee = getBpaApplication(applicationNumber);
        if (applicationFee != null && applicationFee.getApplication() != null
                            ) {
            loadViewdata(model, applicationFee);

            // check fee calculate first time or update ? Check inspection is captured for existing application ?

            // Get all sanction fee by service type
            List<BpaFee> bpaSanctionFees = bpaFeeService
                    .getAllActiveSanctionFeesByServiceId(applicationFee.getApplication().getServiceType().getId(),BpaConstants.FEETYPE_SANCTIONFEE);

            if (getAppConfigValueByPassingModuleAndType(BpaConstants.EGMODULE_NAME, BpaConstants.AUTOCALCULATEFEEBYINSPECTION)) {
                // calculate fee by passing sanction list, inspection latest object.
                // based on fee code, define calculation logic for each servicewise.
                applicationFee = applicationBpaFeeCalculationService.calculateBpaSanctionFees(applicationFee.getApplication());
            } else {

                if (applicationFee.getId() == null) {
                    applicationFee.setStatus(bpaStatusService
                            .findByModuleTypeAndCode(BpaConstants.BPASTATUS_MODULETYPE_REGISTRATIONFEE,
                                    BpaConstants.BPASTATUS_REGISTRATIONFEE_APPROVED));
                    applicationFee.setFeeDate(new Date());

                    for (BpaFee bpaFee : bpaSanctionFees) {
                        ApplicationFeeDetail applicationDtl = new ApplicationFeeDetail();
                        applicationDtl.setApplicationFee(applicationFee);
                        applicationDtl.setBpaFee(bpaFee);
                        applicationDtl.setAmount(BigDecimal.ZERO);
                        applicationFee.addApplicationFeeDetail(applicationDtl);
                    }
                }
                // If manual process, then return list of sanction fee.
            }
            model.addAttribute(BPA_APPLICATIONFEE, applicationFee);
            model.addAttribute("sanctionFees", bpaSanctionFees);
        }

        return CREATEBPAFEE_FORM;
    }

    public Boolean getAppConfigValueByPassingModuleAndType(String moduleName, String autocalculateFee) {
        final List<AppConfigValues> appConfigValue = appConfigValuesService.getConfigValuesByModuleAndKey(moduleName,
                autocalculateFee);
        return "YES".equalsIgnoreCase(
                appConfigValue != null && !appConfigValue.isEmpty() ? appConfigValue.get(0).getValue() : "NO");
    }

    private void loadViewdata(final Model model, final ApplicationFee applicationFee) {
        model.addAttribute("stateType", applicationFee.getApplication().getClass().getSimpleName());
        model.addAttribute(ADDITIONALRULE, BpaConstants.CREATE_ADDITIONAL_RULE_CREATE);
        model.addAttribute("currentState", applicationFee.getApplication().getCurrentState().getValue());
        model.addAttribute(BPA_APPLICATIONFEE, applicationFee);
        model.addAttribute(BPA_APPLICATION, applicationFee.getApplication());

    }

    @RequestMapping(value = "/calculateFee/{applicationNumber}", method = RequestMethod.POST)
    public String calculateFeeform(@Valid @ModelAttribute(BPA_APPLICATIONFEE) ApplicationFee applicationFee,
            @PathVariable final String applicationNumber,
            final BindingResult resultBinder, final RedirectAttributes redirectAttributes,
            final HttpServletRequest request, final Model model, @RequestParam("files") final MultipartFile[] files) {

        // save sanction fee in application fee
        // generate demand based on sanction list, application
        applicationFeeService.saveApplicationFee(applicationFee);
        bpaDemandService.generateDemandUsingSanctionFeeList(applicationFee);

        String message = messageSource.getMessage("msg.create.calculateFee", new String[] {
                applicationFee.getApplication().getApplicationNumber() }, LocaleContextHolder.getLocale());
        model.addAttribute(MESSAGE, message);

        return CREATEBPAFEE_VIEW;
        // redirect to success page.

    }
}
