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

import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.SiteDetail;
import org.egov.bpa.transaction.entity.Slot;
import org.egov.bpa.transaction.entity.SlotDetail;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationForm;
import org.egov.bpa.transaction.entity.oc.OCSlot;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.infra.admin.master.entity.Boundary;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.Date;

public class SearchOcSpec {

    public static Specification<OccupancyCertificate> search(final SearchBpaApplicationForm requestForm) {
        return (root, query, builder) -> {
            final Predicate predicate = builder.conjunction();
            Join<OccupancyCertificate, BpaApplication> bpaApplication = root.join("parent");
            Join<BpaApplication, SiteDetail> siteDetailJoin = bpaApplication.join("siteDetail");
            Join<SiteDetail, Boundary> adminBoundaryJoin = siteDetailJoin.join("adminBoundary");
            if (requestForm.getApplicantName() != null)
                predicate.getExpressions()
                        .add(builder.equal(bpaApplication.get("owner").get("name"),
                                requestForm.getApplicantName()));
            if (requestForm.getServiceTypeId() != null)
                predicate.getExpressions()
                        .add(builder.equal(bpaApplication.get("serviceType").get("id"), requestForm.getServiceTypeId()));
            if (requestForm.getServiceType() != null)
                predicate.getExpressions()
                        .add(builder.equal(bpaApplication.get("serviceType").get("description"),
                                requestForm.getServiceTypeId()));

            if (requestForm.getOccupancyId() != null)
                predicate.getExpressions()
                        .add(builder.equal(bpaApplication.get("occupancy").get("id"), requestForm.getOccupancyId()));
            if (requestForm.getElectionWardId() != null)
                predicate.getExpressions()
                        .add(builder.equal(siteDetailJoin.get("electionBoundary").get("id"), requestForm.getElectionWardId()));
            if (requestForm.getWardId() != null)
                predicate.getExpressions()
                        .add(builder.equal(adminBoundaryJoin.get("id"), requestForm.getWardId()));
            if (requestForm.getZoneId() != null) {
                predicate.getExpressions()
                        .add(builder.equal(adminBoundaryJoin.get("parent").get("id"), requestForm.getZoneId()));
            }
            if (requestForm.getZoneId() == null && requestForm.getZone() != null)
                predicate.getExpressions()
                        .add(builder.equal(adminBoundaryJoin.get("parent").get("name"), requestForm.getZone()));
            if (requestForm.getFromDate() != null)
                predicate.getExpressions()
                        .add(builder.greaterThanOrEqualTo(root.get("applicationDate"), requestForm.getFromDate()));
            if (requestForm.getToDate() != null)
                predicate.getExpressions()
                        .add(builder.lessThanOrEqualTo(root.get("applicationDate"), requestForm.getToDate()));
            if (requestForm.getFromBuiltUpArea() != null)
                predicate.getExpressions()
                        .add(builder.greaterThanOrEqualTo(bpaApplication.get("totalBuiltUpArea"), requestForm.getFromBuiltUpArea()));
            if (requestForm.getToBuiltUpArea() != null)
                predicate.getExpressions()
                        .add(builder.lessThanOrEqualTo(bpaApplication.get("totalBuiltUpArea"), requestForm.getToBuiltUpArea()));
            query.distinct(true);
            return predicate;
        };
    }

    public static Specification<OCSlot> hasDocumentScrutinyPendingSpecificationForOc(final SearchBpaApplicationForm requestForm) {
        return (root, query, builder) -> {
            final Predicate predicate = builder.conjunction();
            Join<OCSlot, OccupancyCertificate> certificateJoin = root.join("oc");
            Join<OccupancyCertificate, BpaApplication> applicationJoin = certificateJoin.join("parent");
            Join<BpaApplication, SiteDetail> siteDetailJoin = applicationJoin.join("siteDetail");
            requestForm.setToDate(new Date());
            if (requestForm.getToDate() != null) {
                Join<OCSlot, SlotDetail> slotDetailJoin = root.join("slotDetail");
                Join<SlotDetail, Slot> slotJoin = slotDetailJoin.join("slot");
                predicate.getExpressions()
                        .add(builder.equal(slotJoin.get("appointmentDate"), requestForm.getToDate()));
            }
            Join<SiteDetail, Boundary> adminBoundaryJoin = siteDetailJoin.join("adminBoundary");
            if (requestForm.getElectionWardId() != null)
                predicate.getExpressions()
                        .add(builder.equal(siteDetailJoin.get("electionBoundary").get("id"), requestForm.getElectionWardId()));
            if (requestForm.getWardId() != null)
                predicate.getExpressions()
                        .add(builder.equal(adminBoundaryJoin.get("id"), requestForm.getWardId()));
            if (requestForm.getZoneId() != null) {
                predicate.getExpressions()
                        .add(builder.equal(adminBoundaryJoin.get("parent").get("id"), requestForm.getZoneId()));
            }
            if (requestForm.getZoneId() == null && requestForm.getZone() != null)
                predicate.getExpressions()
                        .add(builder.equal(adminBoundaryJoin.get("parent").get("name"), requestForm.getZone()));
            predicate.getExpressions()
                    .add(certificateJoin.get("status").get("code").in("Scheduled For Document Scrutiny",
                            "Rescheduled For Document Scrutiny"));
            predicate.getExpressions()
                    .add(builder.equal(root.get("isActive"), true));
            query.distinct(true);
            return predicate;
        };
    }

}
