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

import org.egov.bpa.master.entity.Holiday;
import org.egov.bpa.master.service.HolidayListService;
import org.egov.bpa.transaction.entity.dto.SearchHolidayList;
import org.egov.bpa.transaction.entity.enums.HolidayType;
import org.egov.bpa.web.controller.adaptor.HolidayListJsonAdaptor;
import org.egov.infra.utils.DateUtils;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.egov.infra.utils.JsonUtils.toJSON;

@Controller
@RequestMapping(value = "/holiday")
public class HolidayListController {
	private static final String HOLIDAYLIST_RESULT = "holidaylist-result";
	private static final String SEARCH_HOLIDAYLIST_EDIT = "search-holidayList-edit";
	private static final String HOLIDAY = "holiday";
	private static final String HOLIDAYLIST_UPDATE = "holidaylist-update";
	private static final String HOLIDAYLIST_VIEW = "holidaylist-view";
	private static final String SEARCH_HOLIDAYLIST_VIEW = "search-holidaylist-view";
	private static final String HOLIDAYLIST_NEW = "holidaylist-new";
	private static final String DATA = "{ \"data\":";

	@Autowired
	private HolidayListService holidayListService;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String showHolidaYList(final Model model) {
		model.addAttribute(HOLIDAY, new Holiday());
		model.addAttribute("holidayType", Arrays.asList(HolidayType.values()));
		return HOLIDAYLIST_NEW;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createHolidayList(@ModelAttribute(HOLIDAY) final Holiday holiday, final Model model,
			final HttpServletRequest request, final BindingResult errors, final RedirectAttributes redirectAttributes) {
		holidayListService.validateCreateHolidayList(holiday, errors);
		
		if (errors.hasErrors()) {
			return HOLIDAYLIST_NEW;
		}
		Date d = holiday.getHolidaysTemp().get(0).getHolidayDate();
		if(d == null)
			d = new Date();
		if (!holidayListService.getPreLoadedGeneralHolidays(DateUtils.toYearFormat(d))) {
			List<Holiday> saturdayList = holidayListService.listOfSecondSaturday(d);
			List<Holiday> sundayList = holidayListService.listOfSunday(d);
			holiday.getHolidaysTemp().addAll(saturdayList);
			holiday.getHolidaysTemp().addAll(sundayList);
		}
		for (Holiday hlday : holiday.getHolidaysTemp())
			hlday.setYear(DateUtils.toYearFormat(hlday.getHolidayDate()));


		holidayListService.save(holiday.getHolidaysTemp());
		redirectAttributes.addFlashAttribute("message",
				messageSource.getMessage("msg.create.holiday.success", null, null));
		return "redirect:/holiday/result";
	}

	@RequestMapping(value = "/search/update", method = RequestMethod.GET)
	public String searchEditHolidayList(final Model model) {
		model.addAttribute("holidayType", Arrays.asList(HolidayType.values()));
		model.addAttribute(HOLIDAY, new SearchHolidayList());
		return SEARCH_HOLIDAYLIST_EDIT;
	}

	@RequestMapping(value = "/search/update", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String getHolidayListResultForEdit(final Model model,
			@ModelAttribute final SearchHolidayList searchHolidayList) {
		final List<SearchHolidayList> searchResultList = holidayListService.search(searchHolidayList);

		return new StringBuilder(DATA)
				.append(toJSON(searchResultList, SearchHolidayList.class, HolidayListJsonAdaptor.class)).append("}")
				.toString();
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String editHolidayList(@PathVariable final Long id, final Model model) {
		final Holiday holidayList = holidayListService.findById(id);
		preapreUpdateModel(holidayList, model);
		return HOLIDAYLIST_UPDATE;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateHolidayList(@ModelAttribute(HOLIDAY) final Holiday holidayList, final Model model,
			final HttpServletRequest request, final BindingResult errors, final RedirectAttributes redirectAttributes) {
		holidayListService.validateUpdateHolidayList(holidayList, errors);
		if (errors.hasErrors()) {
			preapreUpdateModel(holidayList, model);
			return HOLIDAYLIST_UPDATE;
		}

		holidayListService.update(holidayList);
		redirectAttributes.addFlashAttribute("message",
				messageSource.getMessage("msg.update.holiday.success", null, null));
		return "redirect:/holiday/result";
	}

	private void preapreUpdateModel(final Holiday holidayList, final Model model) {
		model.addAttribute(HOLIDAY, holidayList);
	}

	@RequestMapping(value = "/search/view", method = RequestMethod.GET)
	public String searchViewHolidayList(final Model model) {
		model.addAttribute("holidayType", Arrays.asList(HolidayType.values()));
		model.addAttribute(HOLIDAY, new Holiday());
		return SEARCH_HOLIDAYLIST_VIEW;
	}

	@RequestMapping(value = "/search/view", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String getHolidayListForView(@ModelAttribute final SearchHolidayList holidayList, final Model model) {
		final List<SearchHolidayList> searchResultList = holidayListService.search(holidayList);
		return new StringBuilder(DATA)
				.append(toJSON(searchResultList, SearchHolidayList.class, HolidayListJsonAdaptor.class)).append("}")
				.toString();
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String viewHolidayList(@PathVariable final Long id, final Model model) {
		model.addAttribute("holidayType", HolidayType.values());

		model.addAttribute(HOLIDAY, holidayListService.findById(id));
		return HOLIDAYLIST_VIEW;
	}

	@RequestMapping(value = "/result", method = RequestMethod.GET)
	public String resultHolidayList(final Model model) {
		return HOLIDAYLIST_RESULT;
	}

	/*
	 * @RequestMapping(value = "/markasworkingday/{id}", method =
	 * RequestMethod.GET) public String deleteHolidayList(@PathVariable final
	 * Long id, final Model model) { final Holiday holidayList =
	 * holidayListService.findById(id); preapreUpdateModel(holidayList, model);
	 * return HOLIDAYLIST_UPDATE; }
	 * 
	 * @RequestMapping(value = "/markasworkingday/{id}", method =
	 * RequestMethod.GET) public String deleteHolidayList(@PathVariable final
	 * Long id, final Model model, final HttpServletRequest request, final
	 * BindingResult errors, final RedirectAttributes redirectAttributes) { if
	 * (errors.hasErrors()) { return HOLIDAYLIST_UPDATE; }
	 * holidayListService.delete(id);
	 * redirectAttributes.addFlashAttribute("message",
	 * messageSource.getMessage("msg.mark.work.holiday.success", null, null));
	 * return "redirect:/holiday/result"; }
	 */

}