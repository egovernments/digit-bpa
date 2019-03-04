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
import org.egov.bpa.master.entity.BpaFeeMapping;
import org.egov.bpa.master.service.BpaFeeMappingService;
import org.egov.bpa.master.service.BpaFeeService;
import org.egov.bpa.transaction.entity.ApplicationFee;
import org.egov.bpa.transaction.entity.ApplicationFeeDetail;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.PermitFee;
import org.egov.bpa.transaction.repository.PermitFeeRepository;
import org.egov.bpa.transaction.service.ApplicationBpaFeeCalculationService;
import org.egov.bpa.transaction.service.ApplicationBpaService;
import org.egov.bpa.transaction.service.ApplicationFeeService;
import org.egov.bpa.transaction.service.BpaStatusService;
import org.egov.bpa.transaction.service.PermitFeeService;
import org.egov.bpa.transaction.service.collection.BpaDemandService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.demand.model.EgDemand;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.utils.StringUtils;
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
    private static final String PERMIT_FEE = "permitFee";
    private static final String BPA_APPLICATION = "bpaApplication";
    private static final String CREATEBPAFEE_FORM = "createbpafee-form";
    private static final String MODIFYPAFEE_FORM = "modifybpafee-form";
    private static final String CREATEBPAFEE_VIEW = "createbpafee-view";
    private static final String MODIFYBPAFEE_VIEW = "modifybpafee-view";
    private static final String ADDITIONALRULE = "additionalRule";
    private static final String MESSAGE = "message";

    @Autowired
    protected ApplicationBpaService applicationBpaService;
    @Autowired
    private AppConfigValueService appConfigValuesService;
    @Autowired
    protected BpaFeeService bpaFeeService;
    @Autowired
    private BpaFeeMappingService bpaFeeMappingService;
    @Autowired
    private BpaStatusService bpaStatusService;
    @Autowired
    protected ApplicationFeeService applicationFeeService;
    @Autowired
    protected ResourceBundleMessageSource messageSource;
    @Autowired
    protected BpaDemandService bpaDemandService;
    @Autowired
    protected PermitFeeService permitFeeService;
    @Autowired
    protected PermitFeeRepository permitFeeRepository;
    @Autowired
    private ApplicationBpaFeeCalculationService applicationBpaFeeCalculationService;
    @Autowired
    private BpaUtils bpaUtils;

    @ModelAttribute
    public PermitFee getBpaApplication(@PathVariable final String applicationNumber) {
        BpaApplication bpaApplication = applicationBpaService.findByApplicationNumber(applicationNumber);
        PermitFee permitFee = new PermitFee();
        if (bpaApplication != null) {
            List<PermitFee> permitFeeList = permitFeeService
                    .getPermitFeeListByApplicationId(bpaApplication.getId());
            if (permitFeeList.isEmpty()) {
            	permitFee.setApplication(bpaApplication);
                return permitFee;
            } else {
                return permitFeeList.get(0);
            }
        }
        return permitFee;
    }
   

    @RequestMapping(value = "/calculateFee/{applicationNumber}", method = RequestMethod.GET)
    public String calculateFeeform(final Model model, @PathVariable final String applicationNumber,
            final HttpServletRequest request) {
        PermitFee permitFee = getBpaApplication(applicationNumber);
        if (permitFee != null && permitFee.getApplication() != null) {
            loadViewdata(model, permitFee);

            // check fee calculate first time or update ? Check inspection is captured for existing application ?

            // Get all sanction fee by service type
            List<BpaFeeMapping> bpaSanctionFees = bpaFeeMappingService
                    .getPermitFeeForListOfServices(permitFee.getApplication().getServiceType().getId(),BpaConstants.BPA_PERMIT_FEE);

            String feeCalculationMode = bpaUtils.getAppConfigValueForFeeCalculation(BpaConstants.EGMODULE_NAME, BpaConstants.BPAFEECALULATION);
            model.addAttribute("sanctionFees", bpaSanctionFees);
            model.addAttribute("feeCalculationMode", feeCalculationMode);
            
            if (feeCalculationMode.equalsIgnoreCase(BpaConstants.AUTOFEECAL) ||
            		feeCalculationMode.equalsIgnoreCase(BpaConstants.AUTOFEECALEDIT)) {
                // calculate fee by passing sanction list, inspection latest object.
                // based on fee code, define calculation logic for each servicewise.
            	permitFee = applicationBpaFeeCalculationService.calculateBpaSanctionFees(permitFee.getApplication());
                model.addAttribute(PERMIT_FEE, permitFee);
               return MODIFYPAFEE_FORM;
            }  else {
                if (permitFee.getApplicationFee() == null) {
                	permitFee.setApplicationFee(new ApplicationFee());
                	permitFee.getApplicationFee().setStatus(bpaStatusService
                            .findByModuleTypeAndCode(BpaConstants.BPASTATUS_MODULETYPE_REGISTRATIONFEE,
                                    BpaConstants.BPASTATUS_REGISTRATIONFEE_APPROVED));
                	permitFee.getApplicationFee().setFeeDate(new Date());

                    for (BpaFeeMapping bpaFee : bpaSanctionFees) {
                        ApplicationFeeDetail applicationDtl = new ApplicationFeeDetail();
                        applicationDtl.setApplicationFee(permitFee.getApplicationFee());
                        applicationDtl.setBpaFeeMapping(bpaFee);
                        applicationDtl.setAmount(BigDecimal.ZERO);
                        permitFee.getApplicationFee().addApplicationFeeDetail(applicationDtl);
                    }
                    
                    model.addAttribute(PERMIT_FEE, permitFee);
                    BigDecimal amount = permitFee.getApplicationFee().getApplicationFeeDetail().stream().map(ApplicationFeeDetail::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    return  amount.compareTo(BigDecimal.ZERO) > 0 ? MODIFYPAFEE_FORM : CREATEBPAFEE_FORM;
                }
                // If manual process, then return list of sanction fee.
            }
        }

        return MODIFYPAFEE_FORM;
    }

    public Boolean getAppConfigValueByPassingModuleAndType(String moduleName, String autocalculateFee) {
        final List<AppConfigValues> appConfigValue = appConfigValuesService.getConfigValuesByModuleAndKey(moduleName,
                autocalculateFee);
        return "YES".equalsIgnoreCase(
                appConfigValue != null && !appConfigValue.isEmpty() ? appConfigValue.get(0).getValue() : "NO");
    }

    private void loadViewdata(final Model model, final PermitFee permitFee) {
        model.addAttribute("stateType", permitFee.getApplication().getClass().getSimpleName());
        model.addAttribute(ADDITIONALRULE, BpaConstants.CREATE_ADDITIONAL_RULE_CREATE);
        model.addAttribute("currentState", permitFee.getApplication().getCurrentState().getValue());
        model.addAttribute(PERMIT_FEE, permitFee);
        model.addAttribute(BPA_APPLICATION, permitFee.getApplication());

    }

    @RequestMapping(value = "/calculateFee/{applicationNumber}", method = RequestMethod.POST)
    public String calculateFeeform(@Valid @ModelAttribute(PERMIT_FEE) PermitFee permitFee,
            @PathVariable final String applicationNumber,
            final BindingResult resultBinder, final RedirectAttributes redirectAttributes,
            final HttpServletRequest request, final Model model, @RequestParam("files") final MultipartFile[] files) {

        // save sanction fee in application fee
        // generate demand based on sanction list, application
        ApplicationFee applicationFee = applicationFeeService.saveApplicationFee(permitFee.getApplicationFee());
        permitFee.setApplicationFee(applicationFee);
        EgDemand demand = bpaDemandService.generateDemandUsingSanctionFeeList(permitFee.getApplicationFee(), permitFee.getApplication().getDemand());
        if (permitFee.getApplication().getDemand() == null) { 
        	permitFee.getApplication().setDemand(demand);	
		}
        permitFeeRepository.save(permitFee);
        
        String message = messageSource.getMessage("msg.create.calculateFee", new String[] {
                permitFee.getApplication().getApplicationNumber() }, LocaleContextHolder.getLocale());
        model.addAttribute(MESSAGE, message);

        return StringUtils.isBlank(applicationFee.getModifyFeeReason()) ? CREATEBPAFEE_VIEW : MODIFYBPAFEE_VIEW;
        // redirect to success page.

    }
}
