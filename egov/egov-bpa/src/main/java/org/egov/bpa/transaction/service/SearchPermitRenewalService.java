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
package org.egov.bpa.transaction.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.egov.bpa.transaction.entity.PermitRenewal;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationForm;
import org.egov.bpa.transaction.repository.PermitRenewalRepository;
import org.egov.bpa.transaction.repository.specs.SearchPermitRenewalFormSpec;
import org.egov.bpa.utils.BpaUtils;
import org.egov.infra.config.persistence.datasource.routing.annotation.ReadOnly;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SearchPermitRenewalService {

    @Autowired
    private WorkflowHistoryService workflowHistoryService;
    @Autowired
    private PermitRenewalRepository renewalRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private BpaUtils bpaUtils;

    public Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }

    public Date resetFromDateTimeStamp(final Date date) {
        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        return cal1.getTime();
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

    @ReadOnly
    public Page<SearchBpaApplicationForm> pagedSearch(SearchBpaApplicationForm searchRequest) {

        final Pageable pageable = new PageRequest(searchRequest.pageNumber(),
                searchRequest.pageSize(), searchRequest.orderDir(), searchRequest.orderBy());

        Page<PermitRenewal> renewalApplications = renewalRepository
                .findAll(SearchPermitRenewalFormSpec.searchSpecification(searchRequest), pageable);
        List<SearchBpaApplicationForm> searchResults = new ArrayList<>();
        for (PermitRenewal renewal : renewalApplications) {
            String pendingAction = renewal.getState() == null ? "N/A" : renewal.getState().getNextAction();
            Boolean hasCollectionPending = bpaUtils.checkAnyTaxIsPendingToCollect(renewal.getDemand());
            searchResults.add(
                    new SearchBpaApplicationForm(renewal, getProcessOwner(renewal), pendingAction, bpaUtils.feeCollector(), hasCollectionPending));
        }
        return new PageImpl<>(searchResults, pageable, renewalApplications.getTotalElements());
    }
    
    private String getProcessOwner(PermitRenewal renewal) {
        String processOwner;
        if (renewal.getState() != null && renewal.getState().getOwnerPosition() != null)
            processOwner = workflowHistoryService
                    .getUserPositionByPositionAndDate(renewal.getState().getOwnerPosition().getId(),
                    		renewal.getState().getLastModifiedDate())
                    .getName();
        else
            processOwner = renewal.getLastModifiedBy().getName();
        return processOwner;
    }
}   
    
    