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
package org.egov.bpa.transaction.service;

import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationForm;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class FailureInSchedulerService {

    private static final String BPA_APPLICATION_APPLICATION_DATE = "bpaApplication.applicationDate";

    @Autowired
    private SearchBpaApplicationService searchBpaApplicationService;

    @PersistenceContext
    private EntityManager entityManager;

    public Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }

    public List<SearchBpaApplicationForm> getAllFailedApplications(SearchBpaApplicationForm searchBpaApplicationForm) {
        return buildResponseAsPerForm(buildSearchCriteria(searchBpaApplicationForm));
    }

    private List<SearchBpaApplicationForm> buildResponseAsPerForm(List<BpaApplication> bpaAppsList) {
        List<SearchBpaApplicationForm> searchBpaApplicationFormList = new ArrayList<>();
        for (BpaApplication bpaApplication : bpaAppsList) {
            SearchBpaApplicationForm searchBpaApplicationForm = new SearchBpaApplicationForm();
            searchBpaApplicationForm.setId(bpaApplication.getId());
            searchBpaApplicationForm.setApplicationNumber(bpaApplication.getApplicationNumber());
            searchBpaApplicationForm.setApplicationDate(bpaApplication.getApplicationDate());
            searchBpaApplicationForm.setApplicantName(
                    bpaApplication.getOwner() == null ? "" : bpaApplication.getOwner().getUser().getName());
            searchBpaApplicationForm.setServiceTypeId(bpaApplication.getServiceType().getId());
            searchBpaApplicationForm.setServiceType(
                    bpaApplication.getServiceType() == null ? "" : bpaApplication.getServiceType().getCode());
            searchBpaApplicationForm.setOccupancy(
                    bpaApplication.getOccupancy() == null ? "" : bpaApplication.getOccupancy().getDescription());
            searchBpaApplicationForm.setStatus(bpaApplication.getStatus().getDescription());
            buildStateAndSiteDetails(bpaApplication, searchBpaApplicationForm);
            searchBpaApplicationForm.setFailureRemarks(
                    bpaApplication.getSchedulerFailedRemarks() == null ? "" : bpaApplication.getSchedulerFailedRemarks());
            searchBpaApplicationFormList.add(searchBpaApplicationForm);
        }
        return searchBpaApplicationFormList;

    }

    private void buildStateAndSiteDetails(BpaApplication bpaApplication, SearchBpaApplicationForm searchBpaApplicationForm) {
        if (!bpaApplication.getSiteDetail().isEmpty() && bpaApplication.getSiteDetail().get(0) != null) {
            searchBpaApplicationForm
                    .setElectionWard(bpaApplication.getSiteDetail().get(0).getElectionBoundary() == null
                                     ? "" : bpaApplication.getSiteDetail().get(0).getElectionBoundary().getName());
            searchBpaApplicationForm.setWard(bpaApplication.getSiteDetail().get(0).getAdminBoundary() == null
                                             ? "" : bpaApplication.getSiteDetail().get(0).getAdminBoundary().getName());
            searchBpaApplicationForm
                    .setZone(bpaApplication.getSiteDetail().get(0).getAdminBoundary().getParent() == null
                             ? "" : bpaApplication.getSiteDetail().get(0).getAdminBoundary().getParent().getName());
        }
    }

    @SuppressWarnings("unchecked")
    private List<BpaApplication> buildSearchCriteria(SearchBpaApplicationForm searchBpaApplicationForm) {
        Criteria criteria = getCurrentSession().createCriteria(BpaApplication.class, "bpaApplication");
        criteria.add(Restrictions.eq("bpaApplication.failureInScheduler", true));
        searchBpaApplicationService.buildCommonSearchCriterias(searchBpaApplicationForm, criteria);
        criteria.addOrder(Order.desc(BPA_APPLICATION_APPLICATION_DATE));
        criteria.addOrder(Order.desc("bpaApplication.createdDate"));
        return criteria.list();
    }

}