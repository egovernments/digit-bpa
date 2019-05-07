package org.egov.portal.web.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.egov.infra.admin.master.entity.City;
import org.egov.infra.admin.master.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/common")
public class CitySelectionController {

    @Value("${client.id}")
    private String clientId;
    
    @Autowired
    private TenantService tenantService;

    @GetMapping("/city/selection-form")
    public String showNewApplicationForm(@RequestParam(name = "url") final String url, final Model model,
            final HttpServletRequest request) {
    	
        model.addAttribute("tenants", tenantService.findAll());
        model.addAttribute("city", new City());
        model.addAttribute("clientId", clientId);
        model.addAttribute("url", url);

        return "city-selection";
    }
}
