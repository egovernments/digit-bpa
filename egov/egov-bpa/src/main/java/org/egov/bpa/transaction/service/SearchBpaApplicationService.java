/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
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

import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_CREATED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REGISTERED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_RESCHEDULED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_SCHEDULED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_TYPE_ONEDAYPERMIT;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_TYPE_REGULAR;
import static org.egov.bpa.utils.BpaConstants.HIGHRISK;
import static org.egov.bpa.utils.BpaConstants.LOWRISK;
import static org.egov.bpa.utils.BpaConstants.MEDIUMRISK;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.egov.bpa.master.entity.ApplicationSubType;
import org.egov.bpa.master.service.ApplicationSubTypeService;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BuildingDetail;
import org.egov.bpa.transaction.entity.SlotApplication;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationForm;
import org.egov.bpa.transaction.entity.enums.ScheduleAppointmentType;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.repository.ApplicationBpaRepository;
import org.egov.bpa.transaction.repository.SlotApplicationRepository;
import org.egov.bpa.transaction.repository.specs.SearchBpaApplnFormSpec;
import org.egov.bpa.transaction.service.collection.BpaDemandService;
import org.egov.bpa.transaction.service.oc.OccupancyCertificateService;
import org.egov.infra.config.persistence.datasource.routing.annotation.ReadOnly;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

@Service
@Transactional(readOnly = true)
public class SearchBpaApplicationService {

    private static final String APPLICATION_TYPE = "applicationType";
    @Autowired
    private WorkflowHistoryService workflowHistoryService;
    @Autowired
    private BpaDemandService bpaDemandService;
    @Autowired
    private ApplicationBpaRepository applicationBpaRepository;
    @Autowired
    private SlotApplicationRepository slotApplicationRepository;
    @Autowired
    private ApplicationBpaService bpaCalculationService;
    @Autowired
    private ApplicationSubTypeService applicationTypeService;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private OccupancyCertificateService occupancyCertificateService;

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

    @SuppressWarnings("unchecked")
    @ReadOnly
    public List<SearchBpaApplicationForm> search(final SearchBpaApplicationForm bpaApplicationForm) {
        final Criteria criteria = buildSearchCriteria(bpaApplicationForm);
        List<SearchBpaApplicationForm> searchBpaApplicationFormList = new ArrayList<>();
        List<BpaApplication> bpaApplications = criteria.list();
        for (BpaApplication application : bpaApplications) {
            String pendingAction = application.getState() == null ? "N/A" : application.getState().getNextAction();
            Boolean hasCollectionPending = bpaDemandService.checkAnyTaxIsPendingToCollect(application);
            searchBpaApplicationFormList.add(
                    new SearchBpaApplicationForm(application, getProcessOwner(application), pendingAction, hasCollectionPending));
        }
        return searchBpaApplicationFormList;
    }

    @ReadOnly
    public Page<SearchBpaApplicationForm> pagedSearch(SearchBpaApplicationForm searchRequest) {

        final Pageable pageable = new PageRequest(searchRequest.pageNumber(),
                searchRequest.pageSize(), searchRequest.orderDir(), searchRequest.orderBy());

        Page<BpaApplication> bpaApplications = applicationBpaRepository
                .findAll(SearchBpaApplnFormSpec.searchSpecification(searchRequest), pageable);
        List<SearchBpaApplicationForm> searchResults = new ArrayList<>();
        for (BpaApplication application : bpaApplications) {
            String pendingAction = application.getState() == null ? "N/A" : application.getState().getNextAction();
            Boolean hasCollectionPending = bpaDemandService.checkAnyTaxIsPendingToCollect(application);
            searchResults.add(
                    new SearchBpaApplicationForm(application, getProcessOwner(application), pendingAction, hasCollectionPending));
        }
        return new PageImpl<>(searchResults, pageable, bpaApplications.getTotalElements());
    }

    @ReadOnly
    public Page<SearchBpaApplicationForm> hasFeeCollectionPending(
            final SearchBpaApplicationForm searchRequest) {
        final Pageable pageable = new PageRequest(searchRequest.pageNumber(),
                searchRequest.pageSize(), searchRequest.orderDir(), searchRequest.orderBy());

        Page<BpaApplication> bpaApplications = applicationBpaRepository
                .findAll(SearchBpaApplnFormSpec.hasCollectionPendingSpecification(searchRequest), pageable);
        List<SearchBpaApplicationForm> searchResults = new ArrayList<>();
        for (BpaApplication application : bpaApplications) {
            String pendingAction = application.getState() == null ? "N/A" : application.getState().getNextAction();
            Boolean hasCollectionPending = bpaDemandService.checkAnyTaxIsPendingToCollect(application);
            searchResults.add(
                    new SearchBpaApplicationForm(application, getProcessOwner(application), pendingAction, hasCollectionPending));
        }
        return new PageImpl<>(searchResults, pageable, bpaApplications.getTotalElements());
    }

    @ReadOnly
    public Page<SearchBpaApplicationForm> searchForDocumentScrutinyPending(final SearchBpaApplicationForm searchRequest) {
        final Pageable pageable = new PageRequest(searchRequest.pageNumber(),
                searchRequest.pageSize(), searchRequest.orderDir(), searchRequest.orderBy());

        Page<SlotApplication> slotApplications = slotApplicationRepository
                .findAll(SearchBpaApplnFormSpec.hasDocumentScrutinyPendingSpecification(searchRequest), pageable);
        List<SearchBpaApplicationForm> searchResults = new ArrayList<>();
        for (SlotApplication slotApplication : slotApplications) {
            String processOwner = "N/A";
            String pendingAction = "N/A";
            if (slotApplication.getApplication().getState() != null
                    && slotApplication.getApplication().getState().getOwnerPosition() != null) {
                processOwner = getProcessOwner(slotApplication.getApplication());
                pendingAction = slotApplication.getApplication().getState().getNextAction();
            }
            Boolean hasCollectionPending = bpaDemandService.checkAnyTaxIsPendingToCollect(slotApplication.getApplication());
            searchResults.add(new SearchBpaApplicationForm(slotApplication.getApplication(), processOwner, pendingAction,
                    hasCollectionPending));
        }
        return new PageImpl<>(searchResults, pageable, slotApplications.getTotalElements());
    }

    private String getProcessOwner(BpaApplication application) {
        String processOwner;
        if (application.getState() != null && application.getState().getOwnerPosition() != null)
            processOwner = workflowHistoryService
                    .getUserPositionByPositionAndDate(application.getState().getOwnerPosition().getId(),
                            application.getState().getLastModifiedDate())
                    .getName();
        else
            processOwner = application.getLastModifiedBy().getName();
        return processOwner;
    }

    public Criteria searchApplicationsForPRReport(final SearchBpaApplicationForm searchBpaApplicationForm) {
        Criteria criteria = buildSearchCriteria(searchBpaApplicationForm);
        criteria.createAlias("bpaApplication.status", "status")
                .add(Restrictions.not(Restrictions.in("status.code", APPLICATION_STATUS_CREATED, APPLICATION_STATUS_REGISTERED,
                        APPLICATION_STATUS_SCHEDULED, APPLICATION_STATUS_RESCHEDULED)));
        return criteria;
    }

    public Criteria buildSearchCriteria(final SearchBpaApplicationForm searchBpaApplicationForm) {
        final Criteria criteria = getCurrentSession().createCriteria(BpaApplication.class, "bpaApplication");

        if (searchBpaApplicationForm.getApplicantName() != null) {
            criteria.createAlias("bpaApplication.owner", "owner");
            criteria.add(
                    Restrictions.ilike("owner.name", searchBpaApplicationForm.getApplicantName(), MatchMode.ANYWHERE));
        }

        if (searchBpaApplicationForm.getApplicationNumber() != null) {
            criteria.add(Restrictions.eq("bpaApplication.applicationNumber",
                    searchBpaApplicationForm.getApplicationNumber()));
        }
        if (searchBpaApplicationForm.getServiceTypeId() != null) {
            criteria.add(Restrictions.eq("bpaApplication.serviceType.id", searchBpaApplicationForm.getServiceTypeId()));
        }
        if (searchBpaApplicationForm.getServiceType() != null) {
            criteria.createAlias("bpaApplication.serviceType", "serviceType")
                    .add(Restrictions.eq("serviceType.description", searchBpaApplicationForm.getServiceType()));
        }
        if (searchBpaApplicationForm.getStatusId() != null) {
            criteria.add(Restrictions.eq("bpaApplication.status.id", searchBpaApplicationForm.getStatusId()));
        }
        if (searchBpaApplicationForm.getStatus() != null) {
            criteria.createAlias("bpaApplication.status", "status")
                    .add(Restrictions.eq("status.code", searchBpaApplicationForm.getStatus()));
        }
        if (searchBpaApplicationForm.getOccupancyId() != null) {
            criteria.createAlias("bpaApplication.occupancy", "occupancy")
                    .add(Restrictions.eq("occupancy.id", searchBpaApplicationForm.getOccupancyId()));
        }
        if (searchBpaApplicationForm.getFromDate() != null)
            criteria.add(Restrictions.ge("bpaApplication.applicationDate",
                    resetFromDateTimeStamp(searchBpaApplicationForm.getFromDate())));
        if (searchBpaApplicationForm.getToDate() != null)
            criteria.add(Restrictions.le("bpaApplication.applicationDate",
                    resetToDateTimeStamp(searchBpaApplicationForm.getToDate())));
        buildCommonSearchCriterias(searchBpaApplicationForm, criteria);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria;
    }

    public void buildCommonSearchCriterias(SearchBpaApplicationForm searchBpaApplicationForm, Criteria criteria) {
        if (searchBpaApplicationForm.getServiceTypeEnum() != null
                && !searchBpaApplicationForm.getServiceTypeEnum().isEmpty()) {
            if (searchBpaApplicationForm.getServiceTypeEnum()
                    .equalsIgnoreCase(APPLICATION_TYPE_REGULAR)) {
                criteria.add(Restrictions.eq("bpaApplication.isOneDayPermitApplication", false));
                searchBpaApplicationForm.setToDate(new Date());
            } else if (searchBpaApplicationForm.getServiceTypeEnum()
                    .equalsIgnoreCase(APPLICATION_TYPE_ONEDAYPERMIT))
                criteria.add(Restrictions.eq("bpaApplication.isOneDayPermitApplication", true));
        }
        if (searchBpaApplicationForm.getApplicationTypeId() != null) {
            ApplicationSubType appType = applicationTypeService.findById(searchBpaApplicationForm.getApplicationTypeId());
            if (!appType.getName().equals(APPLICATION_TYPE_ONEDAYPERMIT))
                searchBpaApplicationForm.setToDate(new Date());
            criteria.add(Restrictions.eq("bpaApplication.applicationType.id", searchBpaApplicationForm.getApplicationTypeId()));
        }

        if (searchBpaApplicationForm.getApplicationTypeId() == null && searchBpaApplicationForm.getApplicationType() != null) {
            if (!searchBpaApplicationForm.getApplicationType().equals(APPLICATION_TYPE_ONEDAYPERMIT))
                searchBpaApplicationForm.setToDate(new Date());
            criteria.createAlias("bpaApplication.applicationType", APPLICATION_TYPE)
                    .add(Restrictions.eq("applicationType.name", searchBpaApplicationForm.getApplicationType()));
        }
        if (searchBpaApplicationForm.getElectionWardId() != null || searchBpaApplicationForm.getWardId() != null
                || searchBpaApplicationForm.getZoneId() != null || searchBpaApplicationForm.getZone() != null ||
                searchBpaApplicationForm.getFromPlotArea() != null || searchBpaApplicationForm.getToPlotArea() != null)
            criteria.createAlias("bpaApplication.siteDetail", "siteDetail");
        if (searchBpaApplicationForm.getFromPlotArea() != null)
            criteria.add(Restrictions.ge("siteDetail.extentinsqmts", searchBpaApplicationForm.getFromPlotArea()));
        if (searchBpaApplicationForm.getToPlotArea() != null)
            criteria.add(Restrictions.le("siteDetail.extentinsqmts", searchBpaApplicationForm.getToPlotArea()));
        if (searchBpaApplicationForm.getWardId() != null || searchBpaApplicationForm.getZoneId() != null
                || searchBpaApplicationForm.getZone() != null)
            criteria.createAlias("siteDetail.adminBoundary", "adminBoundary");
        if (searchBpaApplicationForm.getElectionWardId() != null)
            criteria.createAlias("siteDetail.electionBoundary", "electionBoundary")
                    .add(Restrictions.eq("electionBoundary.id", searchBpaApplicationForm.getElectionWardId()));
        if (searchBpaApplicationForm.getWardId() != null)
            criteria.add(Restrictions.eq("adminBoundary.id", searchBpaApplicationForm.getWardId()));
        if (searchBpaApplicationForm.getZoneId() != null)
            criteria.add(Restrictions.eq("adminBoundary.parent.id", searchBpaApplicationForm.getZoneId()));
        if (searchBpaApplicationForm.getZoneId() == null && searchBpaApplicationForm.getZone() != null)
            criteria.createAlias("adminBoundary.parent", "parent")
                    .add(Restrictions.eq("parent.name", searchBpaApplicationForm.getZone()));
    }

    @SuppressWarnings("unchecked")
    public List<SearchBpaApplicationForm> buildSlotApplicationDetails(final SearchBpaApplicationForm searchBpaApplicationForm) {
        final Criteria criteria = getCurrentSession().createCriteria(SlotApplication.class, "slotApplication");
        criteria.createAlias("slotApplication.slotDetail", "slotDetail");
        if (searchBpaApplicationForm.getAppointmentTime() != null)
            criteria.add(Restrictions.eq("slotDetail.appointmentTime",
                    searchBpaApplicationForm.getAppointmentTime()));
        if (searchBpaApplicationForm.getZoneId() != null || searchBpaApplicationForm.getAppointmentDate() != null
                || searchBpaApplicationForm.getServiceType() != null)
            criteria.createAlias("slotDetail.slot", "slot");
        if (searchBpaApplicationForm.getZoneId() != null) {
            criteria.createAlias("slot.zone", "zone");
            criteria.add(Restrictions.eq("zone.id", searchBpaApplicationForm.getZoneId()));
        }
        if (searchBpaApplicationForm.getAppointmentDate() != null) {
            criteria.add(Restrictions.eq("slot.appointmentDate",
                    resetToDateTimeStamp(searchBpaApplicationForm.getAppointmentDate())));
        }
        if ("SCHEDULE".equals(searchBpaApplicationForm.getScheduleType())) {
            criteria.add(Restrictions.eq("slotApplication.scheduleAppointmentType", ScheduleAppointmentType.SCHEDULE));
        } else if ("RESCHEDULE".equals(searchBpaApplicationForm.getScheduleType())) {
            criteria.add(Restrictions.eq("slotApplication.scheduleAppointmentType", ScheduleAppointmentType.RESCHEDULE));
        }

        if ("onedaypermit".equals(searchBpaApplicationForm.getApplicationType())) {
            criteria.add(Restrictions.isNotNull("slot.electionWard"));
        } else
            criteria.add(Restrictions.isNull("slot.electionWard"));

        List<SearchBpaApplicationForm> searchBpaApplicationFormList = new ArrayList<>();
        for (SlotApplication slotApplication : (List<SlotApplication>) criteria.list()) {
            String pendingAction = slotApplication.getApplication().getState() == null ? "N/A"
                    : slotApplication.getApplication().getState().getNextAction();
            Boolean hasCollectionPending = bpaDemandService.checkAnyTaxIsPendingToCollect(slotApplication.getApplication());
            searchBpaApplicationFormList.add(new SearchBpaApplicationForm(slotApplication.getApplication(),
                    getProcessOwner(slotApplication.getApplication()), pendingAction, hasCollectionPending));
        }
        return searchBpaApplicationFormList;
    }

    public BigDecimal getFar(BpaApplication application) {
        BigDecimal plotArea = application.getSiteDetail().get(0).getExtentinsqmts();
        BigDecimal far = BigDecimal.ZERO;
        if (!application.getBuildingDetail().isEmpty()) {
            BigDecimal totalFloorArea = bpaCalculationService.getTotalFloorArea(application);
            BigDecimal existBldgFloorArea = bpaCalculationService.getExistBldgTotalFloorArea(application);
            totalFloorArea = totalFloorArea.add(existBldgFloorArea);
            if (totalFloorArea != null)
                far = totalFloorArea.divide(plotArea, 3, RoundingMode.HALF_UP);
        }
        return far.setScale(3, RoundingMode.HALF_UP);
    }

    public void validateApplicationTypeAndHeight(final BpaApplication application, final BindingResult errors) {
        if (application.getApplicationType() != null) {
            String appType = application.getApplicationType().getName();
            for (BuildingDetail buildingDetail : application.getBuildingDetail()) {
                BigDecimal buildingHeight = buildingDetail.getHeightFromGroundWithOutStairRoom();
                if (buildingHeight != null) {
                    if (appType.equals(LOWRISK) && buildingHeight.compareTo(BigDecimal.valueOf(10)) > 0)
                        errors.rejectValue(APPLICATION_TYPE, "msg.validation.lowrisk");
                    else if (appType.equals(MEDIUMRISK) && (buildingHeight.compareTo(BigDecimal.valueOf(10)) < 0 ||
                            buildingHeight.compareTo(BigDecimal.valueOf(15)) > 0))
                        errors.rejectValue(APPLICATION_TYPE, "msg.validation.mediumrisk");
                    else if (appType.equals(HIGHRISK) && buildingHeight.compareTo(BigDecimal.valueOf(15)) < 0)
                        errors.rejectValue(APPLICATION_TYPE, "msg.validation.highrisk");
                }
            }
        } else if (!application.getIsOneDayPermitApplication())
            errors.rejectValue(APPLICATION_TYPE, "msg.validation.applicationtype");

    }

    @ReadOnly
    public Page<SearchBpaApplicationForm> searchForRevocation(final SearchBpaApplicationForm searchRequest,
            final List<Long> userIds) {
        final Pageable pageable = new PageRequest(searchRequest.pageNumber(),
                searchRequest.pageSize(), searchRequest.orderDir(), searchRequest.orderBy());
        Page<BpaApplication> bpaApplications = applicationBpaRepository
                .findAll(SearchBpaApplnFormSpec.searchPermitRevocationSpecification(searchRequest), pageable);
        List<SearchBpaApplicationForm> searchResults = new ArrayList<>();
        for (BpaApplication application : bpaApplications) {
            searchResults.add(
                    new SearchBpaApplicationForm(application, "", "", false));
        }
        return new PageImpl<>(searchResults, pageable, bpaApplications.getTotalElements());
    }
    
    
    @ReadOnly
    public Page<SearchBpaApplicationForm> searchInspection(final SearchBpaApplicationForm searchRequest,
            final List<Long> userIds) {
        final Pageable pageable = new PageRequest(searchRequest.pageNumber(),
                searchRequest.pageSize(), searchRequest.orderDir(), searchRequest.orderBy());
        
        Page<BpaApplication> bpaApplications = applicationBpaRepository
                .findAll(SearchBpaApplnFormSpec.searchInspectionSpecification(searchRequest), pageable);
        
        List<SearchBpaApplicationForm> searchResults = new ArrayList<>();
        for (BpaApplication application : bpaApplications) {
        	List<OccupancyCertificate> occupancyCertificate = occupancyCertificateService.findByPermitNumber(application.getPlanPermissionNumber());
            if(occupancyCertificate.isEmpty())
        	 searchResults.add(
                    new SearchBpaApplicationForm(application, null));
            else
            	searchResults.add(
                        new SearchBpaApplicationForm(application, occupancyCertificate.get(0)));
        }
        return new PageImpl<>(searchResults, pageable, bpaApplications.getTotalElements());
    }
}