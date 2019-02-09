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
package org.egov.bpa.web.controller.transaction.occupancy;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.egov.bpa.master.entity.BpaFee;
import org.egov.bpa.master.service.BpaFeeService;
import org.egov.bpa.transaction.entity.ApplicationFee;
import org.egov.bpa.transaction.entity.ApplicationFeeDetail;
import org.egov.bpa.transaction.entity.oc.OccupancyFee;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.repository.PermitFeeRepository;
import org.egov.bpa.transaction.repository.oc.OccupancyFeeRepository;
import org.egov.bpa.transaction.service.ApplicationBpaFeeCalculationService;
import org.egov.bpa.transaction.service.ApplicationFeeService;
import org.egov.bpa.transaction.service.BpaStatusService;
import org.egov.bpa.transaction.service.OccupancyCertificateFeeCalculation;
import org.egov.bpa.transaction.service.PermitFeeService;
import org.egov.bpa.transaction.service.collection.BpaDemandService;
import org.egov.bpa.transaction.service.oc.OccupancyFeeService;
import org.egov.bpa.transaction.service.oc.OccupancyCertificateService;
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
@RequestMapping(value = "/occupancy-certificate/fee")
public class UpdateOccupancyCertificateFeeController {
    private static final String OC_FEE = "occupancyFee";
    private static final String OC = "oc";
    private static final String CREATEOCFEE_FORM = "createocfee-form";
    private static final String MODIFYOCFEE_FORM = "modifyocfee-form"; 
    private static final String CREATEOCFEE_VIEW = "createocfee-view"; 
    private static final String MODIFYOCFEE_VIEW = "modifyocfee-view"; 
    private static final String ADDITIONALRULE = "additionalRule";
    private static final String MESSAGE = "message";

    @Autowired
    protected OccupancyCertificateService ocService;
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
    protected PermitFeeService permitFeeService;
    @Autowired
    protected PermitFeeRepository permitFeeRepository;
    @Autowired
    protected ApplicationBpaFeeCalculationService applicationBpaFeeCalculationService;
    @Autowired
    protected OccupancyFeeService ocFeeService;
    @Autowired
    protected OccupancyCertificateFeeCalculation occupancyCertificateFeeCalculation;
    @Autowired
    protected OccupancyFeeRepository ocFeeRepository;
    @Autowired
    protected BpaUtils bpaUtils;
    @ModelAttribute
    public OccupancyFee getOCApplication(@PathVariable final String applicationNumber) {
    	OccupancyCertificate oc = ocService.findByApplicationNumber(applicationNumber);
        OccupancyFee ocFee = new OccupancyFee();
        if (oc != null) {
            List<OccupancyFee> ocFeeList = ocFeeService
                    .getOCFeeListByApplicationId(oc.getId());
            if (ocFeeList.isEmpty()) {
            	ocFee.setOc(oc);
                return ocFee;
            } else {
                return ocFeeList.get(0);
            }
        }
        return ocFee;
    }
   

    @RequestMapping(value = "/calculateFee/{applicationNumber}", method = RequestMethod.GET)
    public String calculateFeeform(final Model model, @PathVariable final String applicationNumber,
            final HttpServletRequest request) {
        OccupancyFee ocFee = getOCApplication(applicationNumber);
        if (ocFee != null && ocFee.getOc() != null
                            ) {
            loadViewdata(model, ocFee);

            // check fee calculate first time or update ? Check inspection is captured for existing application ?

            // Get all sanction fee by service type
            List<BpaFee> bpaSanctionFees = bpaFeeService
                    .getAllActiveSanctionFeesByServiceId(ocFee.getOc()
                    		.getParent().getServiceType().getId(),BpaConstants.OCFEETYPE_SANCTIONFEE);
            String feeCalculationMode = bpaUtils.getAppConfigValueForFeeCalculation(BpaConstants.EGMODULE_NAME, BpaConstants.OCFEECALULATION);
            model.addAttribute("sanctionFees", bpaSanctionFees);
            model.addAttribute("feeCalculationMode", feeCalculationMode);
            if (feeCalculationMode.equalsIgnoreCase(BpaConstants.AUTOFEECAL) ||
            		feeCalculationMode.equalsIgnoreCase(BpaConstants.AUTOFEECALEDIT)){
                // calculate fee by passing sanction list, inspection latest object.
                // based on fee code, define calculation logic for each servicewise.
            	try {
					ocFee = occupancyCertificateFeeCalculation.calculateOCSanctionFees(ocFee.getOc());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                model.addAttribute(OC_FEE, ocFee);

                return MODIFYOCFEE_FORM;
            } else {

                if (ocFee.getApplicationFee() == null) {
                	ocFee.setApplicationFee(new ApplicationFee());
                	ocFee.getApplicationFee().setStatus(bpaStatusService
                            .findByModuleTypeAndCode(BpaConstants.BPASTATUS_MODULETYPE_REGISTRATIONFEE,
                                    BpaConstants.APPROVED));
                	ocFee.getApplicationFee().setFeeDate(new Date());

                    for (BpaFee bpaFee : bpaSanctionFees) {
                        ApplicationFeeDetail applicationDtl = new ApplicationFeeDetail();
                        applicationDtl.setApplicationFee(ocFee.getApplicationFee());
                        applicationDtl.setBpaFee(bpaFee);
                        applicationDtl.setAmount(BigDecimal.ZERO);
                        ocFee.getApplicationFee().addApplicationFeeDetail(applicationDtl);
                    }
                }
                model.addAttribute(OC_FEE, ocFee);

                BigDecimal amount = ocFee.getApplicationFee().getApplicationFeeDetail().stream().map(ApplicationFeeDetail::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return  amount.compareTo(BigDecimal.ZERO) > 0 ? MODIFYOCFEE_FORM : CREATEOCFEE_FORM;
            }
        }

        return MODIFYOCFEE_FORM;
    }

    public Boolean getAppConfigValueByPassingModuleAndType(String moduleName, String autocalculateFee) {
        final List<AppConfigValues> appConfigValue = appConfigValuesService.getConfigValuesByModuleAndKey(moduleName,
                autocalculateFee);
        return "YES".equalsIgnoreCase(
                appConfigValue != null && !appConfigValue.isEmpty() ? appConfigValue.get(0).getValue() : "NO");
    }

    private void loadViewdata(final Model model, final OccupancyFee ocFee) {
        model.addAttribute("stateType", ocFee.getOc().getClass().getSimpleName());
        model.addAttribute(ADDITIONALRULE, BpaConstants.CREATE_ADDITIONAL_RULE_CREATE);
        model.addAttribute("currentState", ocFee.getOc().getCurrentState().getValue());
        model.addAttribute(OC_FEE, ocFee);
        model.addAttribute(OC, ocFee.getOc());

    }

    @RequestMapping(value = "/calculateFee/{applicationNumber}", method = RequestMethod.POST)
    public String calculateFeeform(@Valid @ModelAttribute(OC_FEE) OccupancyFee ocFee,
            @PathVariable final String applicationNumber,
            final BindingResult resultBinder, final RedirectAttributes redirectAttributes,
            final HttpServletRequest request, final Model model, @RequestParam("files") final MultipartFile[] files) {

        // save sanction fee in application fee
        // generate demand based on sanction list, application
        ApplicationFee applicationFee = applicationFeeService.saveApplicationFee(ocFee.getApplicationFee());
        ocFee.setApplicationFee(applicationFee);
        EgDemand demand = bpaDemandService.generateDemandUsingSanctionFeeList(ocFee.getApplicationFee(), ocFee.getOc().getDemand());
        if (ocFee.getOc().getDemand() == null) { 
        	ocFee.getOc().setDemand(demand);	
		}
        ocFeeRepository.save(ocFee);

        String message = messageSource.getMessage("msg.create.calculateFee", new String[] {
                ocFee.getOc().getApplicationNumber() }, LocaleContextHolder.getLocale());
        model.addAttribute(MESSAGE, message);

        return StringUtils.isBlank(applicationFee.getModifyFeeReason()) ? CREATEOCFEE_VIEW : MODIFYOCFEE_VIEW;
    }
}
