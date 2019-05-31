/* eGov suite of products aim to improve the internal efficiency,transparency,
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
package org.egov.bpa.transaction.service.report;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_CREATED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REGISTERED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_RESCHEDULED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_SCHEDULED;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.egov.bpa.transaction.entity.BpaNocApplication;
import org.egov.bpa.transaction.entity.dto.NocDetailsHelper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BpaNocApplicationReportService {

	@PersistenceContext
	private EntityManager entityManager;

	public Session getCurrentSession() {
		return entityManager.unwrap(Session.class);
	}

	@SuppressWarnings("unchecked")
	public List<NocDetailsHelper> searchNocDetails(final NocDetailsHelper nocDetailsHelper) {
		final Criteria criteria = buildSearchCriteria(nocDetailsHelper);
		return buildNocDetailsResponse((List<BpaNocApplication>) criteria.list());
	}

	private List<NocDetailsHelper> buildNocDetailsResponse(List<BpaNocApplication> nocApplication) {
		List<NocDetailsHelper> nocDetails = new ArrayList<>();
		for(BpaNocApplication nocApp : nocApplication) {
			NocDetailsHelper nocDetail = new NocDetailsHelper();
		    nocDetail.setNocDepartmentName(nocApp.getNocType());
		    nocDetail.setNocStatusName(nocApp.getStatus().getCode());
		    nocDetail.setNocApplicationNumber(nocApp.getNocApplicationNumber());
		    nocDetail.setPermitApplicationNo(nocApp.getBpaApplication().getApplicationNumber());
		    nocDetail.setNocApplicationDate(nocApp.getCreatedDate());
		    nocDetail.setStatusUpdatedDate(nocApp.getLastModifiedDate());
		    nocDetails.add(nocDetail);
		}
		return nocDetails;
	}
	
	public Criteria buildSearchCriteria(final NocDetailsHelper nocDetailsHelper) {
		final Criteria criteria = getCurrentSession().createCriteria(BpaNocApplication.class, "nocApplication");

		if (nocDetailsHelper.getNocDepartmentName() != null) {
			criteria.add(Restrictions.eq("nocApplication.nocType",nocDetailsHelper.getNocDepartmentName()));
		}
		if (nocDetailsHelper.getNocStatusId() != null) {
			criteria.add(Restrictions.eq("nocApplication.status.id",nocDetailsHelper.getNocStatusId()));
		}
		if (nocDetailsHelper.getNocApplicationNumber() != null) {
			criteria.add(Restrictions.eq("nocApplication.nocApplicationNumber", nocDetailsHelper.getNocApplicationNumber()));
		}
		if (nocDetailsHelper.getNocApplicationDate() != null) {
			criteria.add(Restrictions.eq("nocApplication.createdDate",
					resetToDateTimeStamp(nocDetailsHelper.getNocApplicationDate())));
		}
		if (nocDetailsHelper.getPermitApplicationNo() != null) {
			criteria.createAlias("nocApplication.bpaApplication", "application")
					.add(Restrictions.eq("application.applicationNumber", nocDetailsHelper.getPermitApplicationNo()));
		}
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}
	
	
	
	public Date resetToDateTimeStamp(final Date date) {
		final Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date);
		cal1.set(Calendar.HOUR_OF_DAY, 23);
		cal1.set(Calendar.MINUTE, 59);
		cal1.set(Calendar.SECOND, 59);
		cal1.set(Calendar.MILLISECOND, 999);
		return cal1.getTime();
	}
}