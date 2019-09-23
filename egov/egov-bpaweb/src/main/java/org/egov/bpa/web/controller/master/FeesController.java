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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.egov.bpa.master.entity.BpaFee;
import org.egov.bpa.master.entity.BpaFeeDetail;
import org.egov.bpa.master.service.BpaFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/fees")
public class FeesController {

    private static final String FEES_DETAIL_UPDATE = "feesDetail-update";
    private static final String BPA_FEE = "bpaFee";

    @Autowired
    private BpaFeeService bpaFeeService;

    @GetMapping("/view")
    public String showFees(final Model model, final HttpServletRequest request) {
        model.addAttribute(BPA_FEE, new BpaFee());
        final List<BpaFee> activeBpaFees = bpaFeeService.getAllActiveBpaFees();
        model.addAttribute("activeBpaFees", activeBpaFees);
        return "view-fees";
    }

    @GetMapping("/update/{id}")
    public String showFeesDetail(@ModelAttribute BpaFee bpaFee, @PathVariable final Long id, final Model model) {
        if (id != null) {
            model.addAttribute(BPA_FEE, bpaFeeService.findById(id));
            model.addAttribute("mode", "edit");
        }
        return FEES_DETAIL_UPDATE;
    }

    @PostMapping("/update/{id}")
    public String updateFeesDetail(@PathVariable final Long id, @Valid @ModelAttribute final BpaFee bpaFee,
            final BindingResult result, final Model model, final HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute(BPA_FEE, bpaFee);
            model.addAttribute("mode", "edit");
            return FEES_DETAIL_UPDATE;
        }
        BpaFee dbBpaFee = bpaFeeService.findById(id);
        for (BpaFeeDetail bfd : bpaFee.getFeeDetail())
            for (BpaFeeDetail bfd1 : dbBpaFee.getFeeDetail())
                if (bfd.getId().equals(bfd1.getId()))
                    bfd1.setAmount(bfd.getAmount());
        bpaFeeService.update(dbBpaFee);
        model.addAttribute(BPA_FEE, dbBpaFee);
        model.addAttribute("mode", "view");
        return FEES_DETAIL_UPDATE;
    }

}
