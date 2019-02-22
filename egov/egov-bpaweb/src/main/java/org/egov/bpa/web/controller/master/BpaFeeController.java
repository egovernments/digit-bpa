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
package org.egov.bpa.web.controller.master;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.egov.bpa.master.entity.BpaFeeCommon;
import org.egov.bpa.master.entity.BpaFeeMapping;
import org.egov.bpa.master.entity.enums.CalculationType;
import org.egov.bpa.master.entity.enums.FeeSubType;
import org.egov.bpa.master.entity.enums.FeeApplicationType;
import org.egov.bpa.master.service.BpaFeeCommonService;
import org.egov.bpa.master.service.BpaFeeMappingService;
import org.egov.bpa.master.service.ServiceTypeService;
import org.egov.bpa.transaction.service.collection.BpaDemandService;
import org.egov.demand.dao.DemandGenericDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/bpafee")
public class BpaFeeController {
	public static final String CATEGORY_FEE = "Fee";

	private static final String FEES_NEW = "fees-new";
	private static final String FEES_RESULT = "fees-result";
	private static final String BPAFEEMAPPING = "bpaFeeMapping";

	@Autowired
	private BpaFeeCommonService bpaFeeCommonService;

	@Autowired
	private BpaFeeMappingService bpaFeeMappingService;

	@Autowired
	private ServiceTypeService serviceTypeService;

	@Autowired
	private BpaDemandService bpaDemandService;

	@Autowired
	private DemandGenericDao demadGenericDao;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createFees(final Model model) {
		model.addAttribute("bpaFeeMapping", new BpaFeeMapping());
		loadForm(model);
		return FEES_NEW;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createFee(@Valid @ModelAttribute(BPAFEEMAPPING) final BpaFeeMapping bpaFeeMap, final Model model,
			final HttpServletRequest request, final BindingResult errors, final RedirectAttributes redirectAttributes) {
		bpaFeeCommonService.validateFeeList(bpaFeeMap, errors);
		BpaFeeMapping bpaFeeMapping = new BpaFeeMapping();
		if (errors.hasErrors()) {
			loadForm(model);
			return FEES_NEW;
		}

		BpaFeeCommon bpaFee = bpaFeeCommonService.update(bpaFeeMap.getBpaFeeCommon());
		for (BpaFeeMapping bpafee : bpaFeeMap.getBpaFeeMapTemp()) {
			bpafee.setBpaFeeCommon(bpaFee);
		}
		bpaDemandService.createEgDemandReasonMaster(bpaFee);
		List<BpaFeeMapping> bpaFeeTempList = bpaFeeMappingService.save(bpaFeeMap.getBpaFeeMapTemp());
		bpaFeeMapping.setBpaFeeMapTemp(bpaFeeTempList);
		bpaFeeMapping.setBpaFeeCommon(bpaFee);
		redirectAttributes.addFlashAttribute("bpaFeeMapping", bpaFeeMapping);
		model.addAttribute("message", messageSource.getMessage("msg.create.fees.success", null, null));

		return FEES_RESULT;
	}

	@GetMapping(value = "/fee-by-code", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BpaFeeCommon feeByCode(@RequestParam String code) {
		return bpaFeeCommonService.findByCode(code).get(0);
	}

	private void prepareSearchForm(Model model) {
		model.addAttribute("bpaFeeList", bpaFeeCommonService.findAll());
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String showUpdateFeeSearchForm(Model model) {
		model.addAttribute("bpaFeeMapping", new BpaFeeMapping());
		prepareSearchForm(model);
		return "fees-search-update";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String showUpdateSubCategoryForm(@ModelAttribute BpaFeeMapping bpaFeeMapping) {
		return "redirect:/bpafee/update/" + bpaFeeMapping.getBpaFeeCommon().getCode();
	}

	@RequestMapping(value = "/update/{code}", method = RequestMethod.GET)
	public String showSubCategoryUpdateForm(Model model, @PathVariable(required = false) String code) {
		List<BpaFeeMapping> bpaFeeMapList = bpaFeeMappingService.findByFeeCode(code);
		BpaFeeMapping bpaFeeMapping = new BpaFeeMapping();
		bpaFeeMapping.setBpaFeeCommon(bpaFeeMapList.get(0).getBpaFeeCommon());
		bpaFeeMapping.setBpaFeeMapTemp(bpaFeeMapList);
		model.addAttribute("bpaFeeMapping", bpaFeeMapping);
		return "update-fees";
	}

	@RequestMapping(value = "/update/{code}", method = RequestMethod.POST)
	public String updateFees(@ModelAttribute @Valid BpaFeeMapping bpaFeeMap, BindingResult bindingResult,
			RedirectAttributes responseAttrbs, Model model) {
		bpaFeeMappingService.update(bpaFeeMap.getBpaFeeMapTemp());
		responseAttrbs.addFlashAttribute("message", "msg.update.fees.success");
		return "redirect:/bpafee/update/" + bpaFeeMap.getBpaFeeCommon().getCode();
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String showViewFeeSearchForm(Model model) {
		model.addAttribute("bpaFeeMapping", new BpaFeeMapping());
		prepareSearchForm(model);
		return "fees-search-view";
	}

	@RequestMapping(value = "/view", method = RequestMethod.POST)
	public String showViewFees(@ModelAttribute BpaFeeMapping bpaFeeMap) {
		return "redirect:/bpafee/view/" + bpaFeeMap.getBpaFeeCommon().getCode();
	}

	@RequestMapping(value = "/view/{code}", method = RequestMethod.GET)
	public String viewFees(Model model, @PathVariable(required = false) String code) {
		List<BpaFeeMapping> bpaFeeMapList = bpaFeeMappingService.findByFeeCode(code);
		BpaFeeMapping bpaFeeMapping = new BpaFeeMapping();
		bpaFeeMapping.setBpaFeeCommon(bpaFeeMapList.get(0).getBpaFeeCommon());
		bpaFeeMapping.setBpaFeeMapTemp(bpaFeeMapList);
		model.addAttribute("bpaFeeMapping", bpaFeeMapping);
		return "fees-view";
	}

	public void loadForm(final Model model) {
		model.addAttribute("category", demadGenericDao.getReasonCategoryByCode(CATEGORY_FEE).getCode());
		model.addAttribute("serviceTypeList", serviceTypeService.getAllActiveServiceTypes());
		model.addAttribute("applicationTypes", Arrays.asList(FeeApplicationType.values()));
		model.addAttribute("calculationTypes", Arrays.asList(CalculationType.values()));
		model.addAttribute("feeSubTypes", Arrays.asList(FeeSubType.values()));
	}

}