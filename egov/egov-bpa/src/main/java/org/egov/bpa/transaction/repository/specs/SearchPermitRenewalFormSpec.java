/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2017  eGovernments Foundation
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
 *            Further, all user interfaces, including but not limited to citizen facing interfaces,
 *            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *            derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *            For any further queries on attribution, including queries on brand guidelines,
 *            please contact contact@egovernments.org
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
 *
 */

package org.egov.bpa.transaction.repository.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.PermitRenewal;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationForm;
import org.springframework.data.jpa.domain.Specification;

public final class SearchPermitRenewalFormSpec {

    private static final String ID = "id";
    private static final String APPLICATION_DATE = "applicationDate";
    private static final String STATUS = "status";

    private SearchPermitRenewalFormSpec() {
    }

    public static Specification<PermitRenewal> searchSpecification(final SearchBpaApplicationForm requestForm) {
        return (root, query, builder) -> {
            final Predicate predicate = builder.conjunction();
            commonSpec(requestForm, root, builder, predicate);
            query.distinct(true);
            return predicate;
        };
    }

    private static void commonSpec(SearchBpaApplicationForm requestForm, Root<PermitRenewal> root, CriteriaBuilder builder,
            Predicate predicate) {
        Join<PermitRenewal, BpaApplication> bpaApplication = root.join("parent");

        if (requestForm.getApplicantName() != null)
            predicate.getExpressions()
                    .add(builder.equal(bpaApplication.get("owner").get("name"), requestForm.getApplicantName()));
        if (requestForm.getApplicationNumber() != null)
            predicate.getExpressions()
                    .add(builder.equal(root.get("applicationNumber"), requestForm.getApplicationNumber()));
        if (requestForm.getServiceTypeId() != null)
            predicate.getExpressions()
                    .add(builder.equal(bpaApplication.get("serviceType").get(ID), requestForm.getServiceTypeId()));
        if (requestForm.getApplicationTypeId() != null)
            predicate.getExpressions()
                    .add(builder.equal(bpaApplication.get("applicationType").get(ID), requestForm.getApplicationTypeId()));
        if (requestForm.getServiceType() != null)
            predicate.getExpressions()
                    .add(builder.equal(bpaApplication.get("serviceType").get("description"), requestForm.getServiceTypeId()));
        if (requestForm.getStatusId() != null)
            predicate.getExpressions()
                    .add(builder.equal(root.get(STATUS).get(ID), requestForm.getStatusId()));
        if (requestForm.getStatus() != null)
            predicate.getExpressions()
                    .add(builder.equal(root.get(STATUS).get("code"), requestForm.getStatus()));
        if (requestForm.getOccupancyId() != null)
            predicate.getExpressions()
                    .add(builder.equal(bpaApplication.get("occupancy").get(ID), requestForm.getOccupancyId()));
        if (requestForm.getFromDate() != null)
            predicate.getExpressions()
                    .add(builder.greaterThanOrEqualTo(root.get(APPLICATION_DATE), requestForm.getFromDate()));
        if (requestForm.getToDate() != null)
            predicate.getExpressions()
                    .add(builder.lessThanOrEqualTo(root.get(APPLICATION_DATE), requestForm.getToDate()));
    }
}
