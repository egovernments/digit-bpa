package org.egov.bpa.web.controller.master;

import java.util.List;

import javax.validation.Valid;

import org.egov.bpa.master.entity.BpaApplicationType;
import org.egov.bpa.master.entity.NocDepartment;
import org.egov.bpa.master.service.NocDepartmentService;
import org.egov.bpa.transaction.entity.enums.NocIntegrationType;
import org.egov.bpa.web.adaptor.NocDepartmentJsonAdaptor;
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
@RequestMapping("/nocdepartment")
public class NocDepartmentController {
	private final static String NOCDEPARTMENT_NEW = "nocdepartment-new";
	private final static String NOCDEPARTMENT_RESULT = "nocdepartment-result";
	private final static String NOCDEPARTMENT_EDIT = "nocdepartment-edit";
	private final static String NOCDEPARTMENT_VIEW = "nocdepartment-view";
	private final static String NOCDEPARTMENT_SEARCH = "nocdepartment-search";
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
		model.addAttribute("nocDepartment", new NocDepartment());
		return NOCDEPARTMENT_NEW;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute final NocDepartment nocDepartment, final BindingResult errors,
			final Model model, final RedirectAttributes redirectAttrs) {
		if (errors.hasErrors()) {
			prepareNewForm(model);
			return NOCDEPARTMENT_NEW;
		}
		nocDepartmentService.create(nocDepartment);
		redirectAttrs.addFlashAttribute("message", messageSource.getMessage("msg.nocdepartment.success", null, null));
		return "redirect:/nocdepartment/result/" + nocDepartment.getId();
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") final Long id, Model model) {
		NocDepartment nocDepartment = nocDepartmentService.findOne(id);
		prepareNewForm(model);
		model.addAttribute("nocDepartment", nocDepartment);
		return NOCDEPARTMENT_EDIT;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute final NocDepartment nocDepartment, final BindingResult errors,
			final Model model, final RedirectAttributes redirectAttrs) {
		if (errors.hasErrors()) {
			prepareNewForm(model);
			return NOCDEPARTMENT_EDIT;
		}
		nocDepartmentService.update(nocDepartment);
		redirectAttrs.addFlashAttribute("message", messageSource.getMessage("msg.nocdepartment.success", null, null));
		return "redirect:/nocdepartment/result/" + nocDepartment.getId();
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable("id") final Long id, Model model) {
		NocDepartment nocDepartment = nocDepartmentService.findOne(id);
		prepareNewForm(model);
		model.addAttribute("nocDepartment", nocDepartment);
		return NOCDEPARTMENT_VIEW;
	}

	@RequestMapping(value = "/result/{id}", method = RequestMethod.GET)
	public String result(@PathVariable("id") final Long id, Model model) {
		NocDepartment nocDepartment = nocDepartmentService.findOne(id);
		model.addAttribute("nocDepartment", nocDepartment);
		return NOCDEPARTMENT_RESULT;
	}

	@RequestMapping(value = "/search/{mode}", method = RequestMethod.GET)
	public String search(@PathVariable("mode") final String mode, Model model) {
		NocDepartment nocDepartment = new NocDepartment();
		prepareNewForm(model);
		model.addAttribute("nocDepartment", nocDepartment);
		return NOCDEPARTMENT_SEARCH;

	}

	@RequestMapping(value = "/ajaxsearch/{mode}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody String ajaxsearch(@PathVariable("mode") final String mode, Model model,
			@ModelAttribute final NocDepartment nocDepartment) {
		List<NocDepartment> searchResultList = nocDepartmentService.search(nocDepartment);
		String result = new StringBuilder("{ \"data\":").append(toSearchResultJson(searchResultList)).append("}")
				.toString();
		return result;
	}

	public Object toSearchResultJson(final Object object) {
		final GsonBuilder gsonBuilder = new GsonBuilder();
		final Gson gson = gsonBuilder.registerTypeAdapter(NocDepartment.class, new NocDepartmentJsonAdaptor()).create();
		final String json = gson.toJson(object);
		return json;
	}
}