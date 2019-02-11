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
package org.egov.bpa.transaction.service.oc;

import java.util.ArrayList;
import java.util.List;

import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationForm;
import org.egov.bpa.transaction.entity.oc.OCSlot;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.repository.OcSlotRepository;
import org.egov.bpa.transaction.repository.oc.OccupancyCertificateRepository;
import org.egov.bpa.transaction.service.WorkflowHistoryService;
import org.egov.bpa.utils.BpaUtils;
import org.egov.infra.config.persistence.datasource.routing.annotation.ReadOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SearchOCService {

    @Autowired
    private OcSlotRepository ocSlotRepository;

    @Autowired
    private WorkflowHistoryService workflowHistoryService;

    @Autowired
    private BpaUtils bpaUtils;

    @Autowired
    private BpaUtils bpaDemandService;

    @Autowired
    private OccupancyCertificateRepository occupancyCertificateRepository;

    @ReadOnly
    public Page<SearchBpaApplicationForm> searchForDocumentScrutinyPending(final SearchBpaApplicationForm searchRequest) {
        final Pageable pageable = new PageRequest(searchRequest.pageNumber(),
                searchRequest.pageSize(), searchRequest.orderDir(), searchRequest.orderBy());
        Page<OCSlot> ocSlots = ocSlotRepository.findAll(SearchOcSpec.hasDocumentScrutinyPendingSpecificationForOc(searchRequest),
                pageable);
        List<SearchBpaApplicationForm> searchResults = new ArrayList<>();
        for (OCSlot ocSlot : ocSlots) {
            String processOwner = "N/A";
            String pendingAction = "N/A";
            if (ocSlot.getOc().getState() != null && ocSlot.getOc().getState().getOwnerPosition() != null) {
                processOwner = getProcessOwner(ocSlot.getOc());
                pendingAction = ocSlot.getOc().getState().getNextAction();
            }
            Boolean hasCollectionPending = bpaUtils.checkAnyTaxIsPendingToCollect(ocSlot.getOc().getDemand());
            searchResults.add(new SearchBpaApplicationForm(ocSlot.getOc(), processOwner, pendingAction, hasCollectionPending));
        }
        return new PageImpl<>(searchResults, pageable, ocSlots.getTotalElements());
    }

    private String getProcessOwner(OccupancyCertificate occupancyCertificate) {
        String processOwner;
        if (occupancyCertificate.getState() != null && occupancyCertificate.getState().getOwnerPosition() != null)
            processOwner = workflowHistoryService
                    .getUserPositionByPositionAndDate(occupancyCertificate.getState().getOwnerPosition().getId(),
                            occupancyCertificate.getState().getLastModifiedDate())
                    .getName();
        else
            processOwner = occupancyCertificate.getLastModifiedBy().getName();
        return processOwner;
    }

    @ReadOnly
    public Page<SearchBpaApplicationForm> pagedSearch(SearchBpaApplicationForm searchRequest) {
        final Pageable pageable = new PageRequest(searchRequest.pageNumber(),
                searchRequest.pageSize(), searchRequest.orderDir(), searchRequest.orderBy());
        Page<OccupancyCertificate> bpaApplications = occupancyCertificateRepository
                .findAll(SearchOcSpec.search(searchRequest), pageable);
        List<SearchBpaApplicationForm> searchResults = new ArrayList<>();
        for (OccupancyCertificate application : bpaApplications) {
            String pendingAction = application.getState() == null ? "N/A" : application.getState().getNextAction();
            Boolean hasCollectionPending = bpaDemandService.checkAnyTaxIsPendingToCollect(application.getDemand());
            searchResults.add(
                    new SearchBpaApplicationForm(application, getProcessOwner(application), pendingAction, hasCollectionPending));
        }
        return new PageImpl<>(searchResults, pageable, bpaApplications.getTotalElements());
    }

}
