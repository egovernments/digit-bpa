package org.egov.bpa.web.controller.master;

import java.util.List;

import javax.validation.Valid;

import org.egov.bpa.master.entity.BpaApplicationType;
import org.egov.bpa.master.entity.NocConfig;
import org.egov.bpa.master.service.NocConfigService;
import org.egov.bpa.master.service.NocDepartmentService;
import org.egov.bpa.transaction.entity.enums.NocIntegrationType;
import org.egov.bpa.web.adaptor.NocConfigJsonAdaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/nocconfig")
public class NocConfigController {
	private final static String NOCCONFIG_NEW = "nocconfig-new";
	private final static String NOCCONFIG_RESULT = "nocconfig-result";
	private final static String NOCCONFIG_EDIT = "nocconfig-edit";
	private final static String NOCCONFIG_VIEW = "nocconfig-view";
	private final static String NOCCONFIG_SEARCH = "nocconfig-search";
	@Autowired
	private NocConfigService nocConfigService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private NocDepartmentService nocDepartmentService;
	

	private void prepareNewForm(Model model) {
		model.addAttribute("nocDepartments", nocDepartmentService.findAll());
		model.addAttribute("bpaApplicationTypes", BpaApplicationType.values());
		model.addAttribute("nocIntegrationTypeEnums", NocIntegrationType.values());
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newForm(final Model model) {
		prepareNewForm(model);
		model.addAttribute("nocConfig", new NocConfig());
		return NOCCONFIG_NEW;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute final NocConfig nocConfig, final BindingResult errors,
			final Model model, final RedirectAttributes redirectAttrs) {
		if (errors.hasErrors()) {
			prepareNewForm(model);
			return NOCCONFIG_NEW;
		}
		nocConfigService.create(nocConfig);
		redirectAttrs.addFlashAttribute("message", messageSource.getMessage("msg.nocconfig.success", null, null));
		return "redirect:/nocconfig/result/" + nocConfig.getId();
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") final Long id, Model model) {
		NocConfig nocConfig = nocConfigService.findOne(id);
		prepareNewForm(model);
		model.addAttribute("nocConfig", nocConfig);
		return NOCCONFIG_EDIT;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute final NocConfig nocConfig, final BindingResult errors,
			final Model model, final RedirectAttributes redirectAttrs) {
		if (errors.hasErrors()) {
			prepareNewForm(model);
			return NOCCONFIG_EDIT;
		}
		nocConfigService.update(nocConfig);
		redirectAttrs.addFlashAttribute("message", messageSource.getMessage("msg.nocconfig.success", null, null));
		return "redirect:/nocconfig/result/" + nocConfig.getId();
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable("id") final Long id, Model model) {
		NocConfig nocConfig = nocConfigService.findOne(id);
		prepareNewForm(model);
		model.addAttribute("nocConfig", nocConfig);
		return NOCCONFIG_VIEW;
	}

	@RequestMapping(value = "/result/{id}", method = RequestMethod.GET)
	public String result(@PathVariable("id") final Long id, Model model) {
		NocConfig nocConfig = nocConfigService.findOne(id);
		model.addAttribute("nocConfig", nocConfig);
		return NOCCONFIG_RESULT;
	}

	@RequestMapping(value = "/search/{mode}", method = RequestMethod.GET)
	public String search(@PathVariable("mode") final String mode, Model model) {
		NocConfig nocConfig = new NocConfig();
		prepareNewForm(model);
		model.addAttribute("nocConfig", nocConfig);
		return NOCCONFIG_SEARCH;

	}

	@RequestMapping(value = "/ajaxsearch/{mode}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody String ajaxsearch(@PathVariable("mode") final String mode, Model model,
			@ModelAttribute final NocConfig nocConfig) {
		List<NocConfig> searchResultList = nocConfigService.search(nocConfig);
		String result = new StringBuilder("{ \"data\":").append(toSearchResultJson(searchResultList)).append("}")
				.toString();
		return result;
	}

	public Object toSearchResultJson(final Object object) {
		final GsonBuilder gsonBuilder = new GsonBuilder();
		final Gson gson = gsonBuilder.registerTypeAdapter(NocConfig.class, new NocConfigJsonAdaptor()).create();
		final String json = gson.toJson(object);
		return json;
	}
}