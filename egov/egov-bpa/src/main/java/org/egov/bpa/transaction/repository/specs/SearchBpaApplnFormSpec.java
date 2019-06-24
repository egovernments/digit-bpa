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

import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_APPROVED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_CANCELLED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_INIT_REVOKE;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_ORDER_ISSUED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REGISTERED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_RESCHEDULED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REVOKE_CANCELED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_SCHEDULED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_SUBMITTED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_TYPE_ONEDAYPERMIT;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_TYPE_REGULAR;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.SiteDetail;
import org.egov.bpa.transaction.entity.Slot;
import org.egov.bpa.transaction.entity.SlotApplication;
import org.egov.bpa.transaction.entity.SlotDetail;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationForm;
import org.egov.demand.model.EgDemand;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.workflow.entity.State;
import org.egov.infra.workflow.entity.StateHistory;
import org.egov.pims.commons.Position;
import org.springframework.data.jpa.domain.Specification;

public final class SearchBpaApplnFormSpec {

    private static final String ID = "id";
    private static final String PLAN_PERMISSION_NUMBER = "planPermissionNumber";
    private static final String APPLICATION_DATE = "applicationDate";
    private static final String STATUS = "status";
    private static final String IS_ONE_DAY_PERMIT_APPLICATION = "isOneDayPermitApplication";

    private SearchBpaApplnFormSpec() {
        // static methods only
    }

    public static Specification<BpaApplication> searchSpecification(final SearchBpaApplicationForm requestForm) {
        return (root, query, builder) -> {
            final Predicate predicate = builder.conjunction();
            commonSpec(requestForm, root, builder, predicate);
            siteDetailSpec(requestForm, root, builder, predicate);
            query.distinct(true);
            return predicate;
        };
    }

    public static Specification<BpaApplication> hasCollectionPendingSpecification(final SearchBpaApplicationForm requestForm) {
        return (root, query, builder) -> {
            final Predicate predicate = builder.conjunction();
            predicate.getExpressions()
                    .add(root.get(STATUS).get("code").in(APPLICATION_STATUS_SUBMITTED, APPLICATION_STATUS_REGISTERED,
                            APPLICATION_STATUS_APPROVED));
            Join<BpaApplication, EgDemand> demandJoin = root.join("demand");
            predicate.getExpressions().add(builder.lessThan(demandJoin.get("amtCollected"), demandJoin.get("baseDemand")));
            commonSpec(requestForm, root, builder, predicate);
            siteDetailSpec(requestForm, root, builder, predicate);
            query.distinct(true);
            return predicate;
        };
    }

    public static Specification<SlotApplication> hasDocumentScrutinyPendingSpecification(
            final SearchBpaApplicationForm requestForm) {
        return (root, query, builder) -> {
            final Predicate predicate = builder.conjunction();
            Join<SlotApplication, BpaApplication> applicationJoin = root.join("application");
            Join<BpaApplication, SiteDetail> siteDetailJoin = applicationJoin.join("siteDetail");
            if (requestForm.getServiceTypeEnum() != null
                    && requestForm.getServiceTypeEnum().equalsIgnoreCase(APPLICATION_TYPE_REGULAR)) {
                predicate.getExpressions()
                        .add(builder.equal(applicationJoin.get(IS_ONE_DAY_PERMIT_APPLICATION), false));
            } else if (requestForm.getServiceTypeEnum() != null
                    && requestForm.getServiceTypeEnum().equalsIgnoreCase(APPLICATION_TYPE_ONEDAYPERMIT))
                predicate.getExpressions()
                        .add(builder.equal(applicationJoin.get(IS_ONE_DAY_PERMIT_APPLICATION), true));
            if (requestForm.getToDate() != null) {
                Join<SlotApplication, SlotDetail> slotDetailJoin = root.join("slotDetail");
                Join<SlotDetail, Slot> slotJoin = slotDetailJoin.join("slot");
                predicate.getExpressions()
                        .add(builder.equal(slotJoin.get("appointmentDate"), requestForm.getToDate()));
            }
            Join<SiteDetail, Boundary> adminBoundaryJoin = siteDetailJoin.join("adminBoundary");
            if (requestForm.getElectionWardId() != null)
                predicate.getExpressions()
                        .add(builder.equal(siteDetailJoin.get("electionBoundary").get(ID), requestForm.getElectionWardId()));
            if (requestForm.getWardId() != null)
                predicate.getExpressions()
                        .add(builder.equal(adminBoundaryJoin.get(ID), requestForm.getWardId()));
            if (requestForm.getZoneId() != null) {
                predicate.getExpressions()
                        .add(builder.equal(adminBoundaryJoin.get("parent").get(ID), requestForm.getZoneId()));
            }
            if (requestForm.getZoneId() == null && requestForm.getZone() != null)
                predicate.getExpressions()
                        .add(builder.equal(adminBoundaryJoin.get("parent").get("name"), requestForm.getZone()));
            predicate.getExpressions()
                    .add(applicationJoin.get(STATUS).get("code").in(APPLICATION_STATUS_SCHEDULED,
                            APPLICATION_STATUS_RESCHEDULED));
            predicate.getExpressions()
                    .add(builder.equal(root.get("isActive"), true));
            query.distinct(true);
            return predicate;
        };
    }

    public static Specification<BpaApplication> searchBpaRegisterSpecification(final SearchBpaApplicationForm requestForm,
            final List<Long> positionIds) {
        return (root, query, builder) -> {
            final Predicate predicate = builder.conjunction();
            Join<BpaApplication, State<Position>> stateJoin = root.join("state");
            Join<State<Position>, StateHistory<Position>> historyJoin = stateJoin.join("history");
            if (!positionIds.isEmpty())
                predicate.getExpressions()
                        .add(historyJoin.get("ownerPosition").get(ID).in(positionIds));
            commonSpec(requestForm, root, builder, predicate);
            siteDetailSpec(requestForm, root, builder, predicate);
            predicate.getExpressions()
                    .add(root.get(STATUS).get("code").in(APPLICATION_STATUS_ORDER_ISSUED, APPLICATION_STATUS_CANCELLED));
            query.distinct(true);
            return predicate;
        };
    }

    private static void commonSpec(SearchBpaApplicationForm requestForm, Root<BpaApplication> root, CriteriaBuilder builder,
            Predicate predicate) {
        if (requestForm.getApplicantName() != null)
            predicate.getExpressions()
                    .add(builder.equal(root.get("owner").get("name"), requestForm.getApplicantName()));
        if (requestForm.getApplicationNumber() != null)
            predicate.getExpressions()
                    .add(builder.equal(root.get("applicationNumber"), requestForm.getApplicationNumber()));
        if (requestForm.getServiceTypeId() != null)
            predicate.getExpressions()
                    .add(builder.equal(root.get("serviceType").get(ID), requestForm.getServiceTypeId()));
        if (requestForm.getApplicationTypeId() != null)
            predicate.getExpressions()
                    .add(builder.equal(root.get("applicationType").get(ID), requestForm.getApplicationTypeId()));
        if (requestForm.getServiceType() != null)
            predicate.getExpressions()
                    .add(builder.equal(root.get("serviceType").get("description"), requestForm.getServiceTypeId()));
        if (requestForm.getStatusId() != null)
            predicate.getExpressions()
                    .add(builder.equal(root.get(STATUS).get(ID), requestForm.getStatusId()));
        if (requestForm.getStatus() != null)
            predicate.getExpressions()
                    .add(builder.equal(root.get(STATUS).get("code"), requestForm.getStatus()));
        if (requestForm.getOccupancyId() != null)
            predicate.getExpressions()
                    .add(builder.equal(root.get("occupancy").get(ID), requestForm.getOccupancyId()));
        if (requestForm.getFromDate() != null)
            predicate.getExpressions()
                    .add(builder.greaterThanOrEqualTo(root.get(APPLICATION_DATE), requestForm.getFromDate()));
        if (requestForm.getToDate() != null)
            predicate.getExpressions()
                    .add(builder.lessThanOrEqualTo(root.get(APPLICATION_DATE), requestForm.getToDate()));
        if (requestForm.getServiceTypeEnum() != null
                && requestForm.getServiceTypeEnum().equalsIgnoreCase(APPLICATION_TYPE_REGULAR))
            predicate.getExpressions()
                    .add(builder.equal(root.get(IS_ONE_DAY_PERMIT_APPLICATION), false));
        else if (requestForm.getServiceTypeEnum() != null
                && requestForm.getServiceTypeEnum().equalsIgnoreCase(APPLICATION_TYPE_ONEDAYPERMIT))
            predicate.getExpressions()
                    .add(builder.equal(root.get(IS_ONE_DAY_PERMIT_APPLICATION), true));
        if (requestForm.getFromBuiltUpArea() != null)
            predicate.getExpressions()
                    .add(builder.greaterThanOrEqualTo(root.get("totalBuiltUpArea"), requestForm.getFromBuiltUpArea()));
        if (requestForm.getToBuiltUpArea() != null)
            predicate.getExpressions()
                    .add(builder.lessThanOrEqualTo(root.get("totalBuiltUpArea"), requestForm.getToBuiltUpArea()));
        if (requestForm.getRevocationNumber() != null)
            predicate.getExpressions()
                    .add(builder.equal(root.get("revocationNumber"), requestForm.getRevocationNumber()));
    }

    private static void siteDetailSpec(SearchBpaApplicationForm requestForm, Root<BpaApplication> root, CriteriaBuilder builder,
            Predicate predicate) {

        Join<BpaApplication, SiteDetail> siteDetailJoin = root.join("siteDetail");
        if (requestForm.getFromPlotArea() != null)
            predicate.getExpressions()
                    .add(builder.greaterThanOrEqualTo(siteDetailJoin.get("extentinsqmts"), requestForm.getFromPlotArea()));
        if (requestForm.getToPlotArea() != null)
            predicate.getExpressions()
                    .add(builder.lessThanOrEqualTo(siteDetailJoin.get("extentinsqmts"), requestForm.getToPlotArea()));
        if (requestForm.getRevenueBoundary() != null)
            predicate.getExpressions()
                    .add(builder.equal(siteDetailJoin.get("adminBoundary").get(ID), requestForm.getRevenueBoundary()));

        if (requestForm.getLocationBoundary() != null)
            predicate.getExpressions()
                    .add(builder.equal(siteDetailJoin.get("locationBoundary").get(ID), requestForm.getLocationBoundary()));

        if (requestForm.getAdminBoundary() != null)
            predicate.getExpressions()
                    .add(builder.equal(siteDetailJoin.get("electionBoundary").get(ID), requestForm.getAdminBoundary()));
    }

    public static Specification<BpaApplication> searchPermitRevocationSpecification(final SearchBpaApplicationForm requestForm) {
        return (root, query, builder) -> {
            final Predicate predicate = builder.conjunction();
            if (requestForm.getPlanPermissionNumber() != null)
                predicate.getExpressions()
                        .add(builder.equal(root.get(PLAN_PERMISSION_NUMBER), requestForm.getPlanPermissionNumber()));
            if (requestForm.getFromDate() != null)
                predicate.getExpressions()
                        .add(builder.greaterThanOrEqualTo(root.get(APPLICATION_DATE), requestForm.getFromDate()));
            if (requestForm.getToDate() != null)
                predicate.getExpressions()
                        .add(builder.lessThanOrEqualTo(root.get(APPLICATION_DATE), requestForm.getToDate()));
            predicate.getExpressions()
                    .add(root.get(STATUS).get("code").in(APPLICATION_STATUS_ORDER_ISSUED, APPLICATION_STATUS_INIT_REVOKE,
                            APPLICATION_STATUS_REVOKE_CANCELED));
            query.distinct(true);
            return predicate;
        };
    }
}
