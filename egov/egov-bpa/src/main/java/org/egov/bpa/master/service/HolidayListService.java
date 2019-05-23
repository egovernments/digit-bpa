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
package org.egov.bpa.master.service;

import org.egov.bpa.master.entity.Holiday;
import org.egov.bpa.master.repository.HolidayListRepository;
import org.egov.bpa.transaction.entity.dto.SearchHolidayList;
import org.egov.bpa.transaction.entity.enums.HolidayType;
import org.egov.infra.utils.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class HolidayListService {

    private static final String HOLIDAY = "holiday";

    private static final String HOLIDAY_DATE = "holidayDate";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private HolidayListRepository holidayListRepository;

    public Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }

    public List<Holiday> findAll() {
        return holidayListRepository.findAll();
    }
    
    public List<Holiday> findByFromAndToDate(Date fromdate,Date toDate) {
        return holidayListRepository.findByFromAndToDate(fromdate, toDate);
    }

    @Transactional
    public List<Holiday> save(final List<Holiday> holidayList) {
        return holidayListRepository.save(holidayList);
    }

    @Transactional
    public Holiday update(final Holiday holidayList) {
        return holidayListRepository.save(holidayList);
    }

    @Transactional
    public void delete(Long id) {
        holidayListRepository.delete(id);
    }

    public Holiday findById(final Long id) {
        return holidayListRepository.findOne(id);
    }

    public List<SearchHolidayList> search(final SearchHolidayList holidayList) {
        final Criteria criteria = buildSearchCriteria(holidayList);
        return buildHolidayListResponse(criteria);

    }

    // search holiday by holiday type and date
    @SuppressWarnings("unchecked")
    public List<Holiday> getHolidayTypeListByType(final HolidayType holidayType, final Date holidayDate) {
        final Criteria criteria = getCurrentSession().createCriteria(Holiday.class, HOLIDAY);
        if (holidayType != null) {
            criteria.add(Restrictions.eq("holiday.holidayType", holidayType));
        }
        if (holidayDate != null) {
            criteria.add(Restrictions.eq("holiday.holidayDate", holidayDate));
        }
        return criteria.list();
    }

    // search all pre-loaded saturday and sunday
    public boolean getPreLoadedGeneralHolidays(final String year) {
        final Criteria criteria = getCurrentSession().createCriteria(Holiday.class, HOLIDAY);
        criteria.add(Restrictions.eq("holiday.year", year));
        criteria.add(Restrictions.eq("holiday.holidayType", HolidayType.GENERAL));
        return !criteria.list().isEmpty() ? true : false;
    }

    public Criteria buildSearchCriteria(final SearchHolidayList holidayList) {
        final Criteria criteria = getCurrentSession().createCriteria(Holiday.class, HOLIDAY);

        if (holidayList.getId() != null) {
            criteria.add(Restrictions.eq("holiday.id", holidayList.getId().toString()));
        }
        if (holidayList.getHolidayType() != null) {
            criteria.add(Restrictions.eq("holiday.holidayType", holidayList.getHolidayType()));
        }
        if (holidayList.getHolidayDate() != null) {
            criteria.add(Restrictions.eq("holiday.holidayDate", holidayList.getHolidayDate()));
        }

        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria;
    }

    // validating holiday present or not in the system and overriding sunday and
    // saturday
    public void validateCreateHolidayList(final Holiday holiday, final BindingResult errors) {

        for (Holiday hlyday : holiday.getHolidaysTemp()) {
            if (checkIsHolidayAlreadyEnter(hlyday.getHolidayDate())) {
                errors.rejectValue(HOLIDAY_DATE, "msg.hol.exists");
            }

            if (!isDateAfterFourdays(hlyday.getHolidayDate())) {
                errors.rejectValue(HOLIDAY_DATE, "msg.fourdays.gteq");
            }
        }
    }

    public void validateUpdateHolidayList(final Holiday holiday, final BindingResult errors) {
        Holiday holidayDate = holidayListRepository.findByHolidayDate(holiday.getHolidayDate());

        if (holidayDate != null && holidayDate.getId() != holiday.getId()
                && checkIsHolidayAlreadyEnter(holiday.getHolidayDate())) {
            errors.rejectValue(HOLIDAY_DATE, "msg.hol.exists");
        }
        if (!isDateAfterFourdays(holiday.getHolidayDate())) {
            errors.rejectValue(HOLIDAY_DATE, "msg.fourdays.gteq");

        }
    }

    public boolean checkIsHolidayAlreadyEnter(final Date date) {
        return holidayListRepository.findByHolidayDate(date) != null;

    }

    // collecting list of all second saturday r
    public List<Holiday> listOfSecondSaturday(final Date date) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        c1.set(Calendar.MONTH, 0);
        c1.set(Calendar.DAY_OF_MONTH, 1);

        Date fromDate = c1.getTime();

        Calendar c2 = Calendar.getInstance();
        c2.setTime(date);
        c2.set(Calendar.MONTH, 11);
        c2.set(Calendar.DAY_OF_MONTH, 31);

        Date toDate = c2.getTime();
        return secondSaturday(fromDate, toDate);
    }

    public List<Holiday> secondSaturday(Date fromDate, Date toDate) {
        List<Holiday> secondSaturdayList = new ArrayList<>();
        Calendar c1 = Calendar.getInstance();
        Holiday h = new Holiday();
        c1.setTime(fromDate);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(toDate);

        while (c2.after(c1)) {
            h = new Holiday();
            if ((c1.get(Calendar.WEEK_OF_MONTH) == 2) && (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) {
                h.setHolidayDate(c1.getTime());
                h.setDescription("second saturday");
                h.setHolidayType(HolidayType.GENERAL);
                h.setYear(String.valueOf(c1.YEAR));
                secondSaturdayList.add(h);
            }
            c1.add(Calendar.DATE, 1);

        }
        if (c1.equals(c2) && (c1.get(Calendar.WEEK_OF_MONTH) == 2)
                && (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) {
            h.setHolidayDate(c1.getTime());
            h.setHolidayType(HolidayType.GENERAL);
            h.setDescription("second saturday");
            h.setYear(String.valueOf(c1.YEAR));
            secondSaturdayList.add(h);
        }

        return secondSaturdayList;
    }

    // collecting list of all sunday
    public List<Holiday> listOfSunday(final Date date) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        c1.set(Calendar.MONTH, 0);
        c1.set(Calendar.DAY_OF_MONTH, 1);

        Date fromDate = c1.getTime();

        Calendar c2 = Calendar.getInstance();
        c2.setTime(date);
        c2.set(Calendar.MONTH, 11);
        c2.set(Calendar.DAY_OF_MONTH, 31);

        Date toDate = c2.getTime();
        return sundays(fromDate, toDate);
    }

    public List<Holiday> sundays(Date fromDate, Date toDate) {
        List<Holiday> sundays = new ArrayList<>();
        Holiday h = new Holiday();
        Calendar c1 = Calendar.getInstance();
        c1.setTime(fromDate);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(toDate);

        while (c2.after(c1)) {
            h = new Holiday();
            if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                h.setHolidayDate(c1.getTime());
                h.setDescription("sunday");
                h.setHolidayType(HolidayType.GENERAL);
                h.setYear(String.valueOf(c1.YEAR));
                sundays.add(h);

            }
            c1.add(Calendar.DATE, 1);
        }

        if (c1.equals(c2) && c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            h.setHolidayDate(c1.getTime());
            h.setDescription("sunday");
            h.setHolidayType(HolidayType.GENERAL);
            h.setYear(String.valueOf(c1.YEAR));
            sundays.add(h);
        }

        return sundays;
    }

    @SuppressWarnings("unchecked")
    private List<SearchHolidayList> buildHolidayListResponse(final Criteria criteria) {
        List<SearchHolidayList> searchHolidayList = new ArrayList<>();
        for (Holiday holiday : (List<Holiday>) criteria.list()) {
            SearchHolidayList searchHolidayListForm = new SearchHolidayList();
            searchHolidayListForm.setId(holiday.getId());
            searchHolidayListForm.setHolidayDate(holiday.getHolidayDate());
            searchHolidayListForm.setHolidayType(holiday.getHolidayType());
            searchHolidayListForm.setDescription(holiday.getDescription());
            searchHolidayList.add(searchHolidayListForm);
        }
        return searchHolidayList;
    }

    public boolean isDateAfterFourdays(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +4);
        Date nextFourDay = cal.getTime();
        return date.equals(nextFourDay) || date.after(nextFourDay);
    }

    public Boolean isHoliday(Date date) {
        Holiday holiday = holidayListRepository.findByHolidayDate(date);
        return holiday == null ? false : true;
    }

    public Calendar getNextWorkingDay(Calendar calender) {
        int flag = 0;
        while (flag != 1) {

            if (isHoliday(calender.getTime()) || !getListOfDaysWhichAreSecordSaturdaysOrSundays(calender.getTime()).isEmpty()) {
                calender.add(Calendar.DAY_OF_YEAR, 1);
            } else {
                flag = 1;
            }
        }
        return calender;
    }

    public List<Holiday> getListOfDaysWhichAreSecordSaturdaysOrSundays(Date date) {
        List<Holiday> holidayList = new ArrayList<>();
        List<Holiday> listOfAllSatAndSunHolidays = new ArrayList<>();
        listOfAllSatAndSunHolidays.addAll(listOfSecondSaturday(date));
        listOfAllSatAndSunHolidays.addAll(listOfSunday(date));
        for (Holiday holiday : listOfAllSatAndSunHolidays) {
            if (DateUtils.toDefaultDateFormat(convertToLocalDate(holiday.getHolidayDate()))
                    .equals(DateUtils.toDefaultDateFormat(convertToLocalDate(date)))) {
                holidayList.add(holiday);
                break;
            }

        }
        return holidayList;
    }

    LocalDate convertToLocalDate(Date date) {
        if (date == null)
            return null;
        return new LocalDate(date);
    }

    public Calendar getNextWeekWorkingDay(Calendar calender) {
        int flag = 0;
        while (flag != 1) {

            if (isHoliday(calender.getTime())) {
                calender.add(Calendar.WEEK_OF_YEAR, 1);
            } else {
                flag = 1;
            }
        }
        return calender;
    }

}
