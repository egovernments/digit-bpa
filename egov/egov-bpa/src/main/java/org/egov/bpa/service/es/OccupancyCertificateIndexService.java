package org.egov.bpa.service.es;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.egov.bpa.entity.es.OccupancyCertificateIndex;
import org.egov.bpa.repository.es.OccupancyCertificateIndexRepository;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.utils.BpaConstants;
import org.egov.commons.entity.Source;
import org.egov.eis.entity.Assignment;
import org.egov.eis.service.AssignmentService;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.entity.City;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.admin.master.service.CityService;
import org.egov.infra.admin.master.service.UserService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.elasticsearch.entity.ApplicationIndex;
import org.egov.infra.elasticsearch.entity.enums.ApprovalStatus;
import org.egov.infra.elasticsearch.entity.enums.ClosureStatus;
import org.egov.infra.elasticsearch.service.ApplicationIndexService;
import org.egov.infra.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.EMPTY;


@Service
@Transactional(readOnly = true)
public class OccupancyCertificateIndexService {

    private static final String SLA_BPA_APPLICATION = "SLA_BPA_APPLICATION";

    @Autowired
    private CityService cityService;

    @Autowired
    private OccupancyCertificateIndexRepository occupancyCertificateIndexRepository;

    @Autowired
    private ApplicationIndexService applicationIndexService;

    @Autowired
    private AppConfigValueService appConfigValuesService;

    @Autowired
    private UserService userService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private SecurityUtils securityUtils;


    public OccupancyCertificateIndex createOccupancyIndex(OccupancyCertificate occupancyCertificate) {
        final City cityWebsite = cityService.getCityByURL(ApplicationThreadLocals.getDomainName());
        OccupancyCertificateIndex occupancyIndex = new OccupancyCertificateIndex();
        occupancyIndex.setUlbName(cityWebsite.getName());
        occupancyIndex.setDistrictName(cityWebsite.getDistrictName());
        occupancyIndex.setRegionName(cityWebsite.getRegionName());
        occupancyIndex.setUlbGrade(cityWebsite.getGrade());
        occupancyIndex.setUlbCode(cityWebsite.getCode());
        occupancyIndex.setId(cityWebsite.getCode() + "-" + occupancyCertificate.getApplicationNumber());
        occupancyIndex.setApplicationNumber(occupancyCertificate.getApplicationNumber());
        occupancyIndex.setApplicationDate(occupancyCertificate.getApplicationDate());
        occupancyIndex.setWorkCommencementDate(occupancyCertificate.getCommencedDate());
        occupancyIndex.setWorkCompletionDate(occupancyCertificate.getCompletionDate());
        occupancyIndex.setWorkCompletionDueDate(occupancyCertificate.getWorkCompletionDueDate());
        occupancyIndex.setOccupancyCertificateNumber(occupancyCertificate.getOccupancyCertificateNumber() == null ? EMPTY : occupancyCertificate.getOccupancyCertificateNumber());
        occupancyIndex.setApplicationType(occupancyCertificate.getApplicationType() == null ? EMPTY : occupancyCertificate.getApplicationType());
        occupancyIndex.setStatus(occupancyCertificate.getStatus().getCode() == null ? EMPTY : occupancyCertificate.getStatus().getCode());
        occupancyIndex.setSource(occupancyCertificate.getSource() == null ? Source.SYSTEM.toString() : occupancyCertificate.getSource().name());
        occupancyIndex.setParentPermissionNumber(occupancyCertificate.getParent().getPlanPermissionNumber() == null ? EMPTY : occupancyCertificate.getParent().getPlanPermissionNumber());
        occupancyIndex.setTotalDemandAmount(occupancyCertificate.getDemand().getBaseDemand() == null ? BigDecimal.ZERO : occupancyCertificate.getDemand().getBaseDemand());
        occupancyIndex.setTotalCollectedAmount(occupancyCertificate.getDemand().getAmtCollected() == null ? BigDecimal.ZERO : occupancyCertificate.getDemand().getAmtCollected());
        occupancyCertificateIndexRepository.save(occupancyIndex);
        return occupancyIndex;
    }

    public void updateOccupancyIndex(OccupancyCertificate occupancyCertificate) {
        User user = getCurrentUser(occupancyCertificate);
        ApplicationIndex applicationIndex = applicationIndexService.findByApplicationNumber(occupancyCertificate
                .getApplicationNumber());
        if (applicationIndex != null && occupancyCertificate.getId() != null)
            buildApplicationIndexForUpdate(occupancyCertificate, user, applicationIndex);
        else {
            String viewUrl = "/bpa/application/occupancy-certificate/view/%s";
            List<AppConfigValues> appConfigValue = appConfigValuesService.getConfigValuesByModuleAndKey(BpaConstants.APPLICATION_MODULE_TYPE,
                    SLA_BPA_APPLICATION);
            Date disposalDate = calculateDisposalDate(appConfigValue);
            applicationIndex = ApplicationIndex.builder().withModuleName(BpaConstants.APPL_INDEX_MODULE_NAME)
                    .withApplicationNumber(occupancyCertificate.getApplicationNumber())
                    .withApplicationDate(occupancyCertificate.getApplicationDate())
                    .withApplicationType(occupancyCertificate.getApplicationType())
                    .withOwnername(user == null ? EMPTY : user.getUsername() + "::" + user.getName())
                    .withApplicantName(occupancyCertificate.getParent().getOwner().getName())
                    .withApplicantAddress(occupancyCertificate.getParent().getOwner().getAddress())
                    .withStatus(occupancyCertificate.getStatus().getCode())
                    .withChannel(occupancyCertificate.getSource() == null ? Source.SYSTEM.toString()
                            : occupancyCertificate.getSource().name())
                    .withConsumerCode(occupancyCertificate.getApplicationNumber())
                    .withMobileNumber(occupancyCertificate.getParent().getOwner().getUser().getMobileNumber())
                    .withAadharNumber(StringUtils.isBlank(occupancyCertificate.getParent().getOwner().getAadhaarNumber())
                            ? EMPTY : occupancyCertificate.getParent().getOwner().getAadhaarNumber())
                    .withUrl(String.format(viewUrl, occupancyCertificate.getApplicationNumber()))
                    .withClosed(ClosureStatus.NO)
                    .withSla(appConfigValue.get(0).getValue() == null ? 0 : Integer.valueOf(appConfigValue.get(0).getValue()))
                    .withDisposalDate(disposalDate)
                    .withApproved(ApprovalStatus.INPROGRESS)
                    .build();
            applicationIndexService.createApplicationIndex(applicationIndex);
            createOccupancyIndex(occupancyCertificate);


            createOccupancyIndex(occupancyCertificate);
        }
    }


    private void buildApplicationIndexForUpdate(OccupancyCertificate occupancyCertificate, User user,
                                                ApplicationIndex applicationIndex) {
        applicationIndex.setStatus(occupancyCertificate.getStatus().getCode());
        applicationIndex.setOwnerName(user == null ? "" : user.getUsername() + "::" + user.getName());
        if (occupancyCertificate.getStatus().getCode().equals(BpaConstants.APPLICATION_STATUS_CANCELLED)) {
            applicationIndex.setApproved(ApprovalStatus.REJECTED);
            applicationIndex.setClosed(ClosureStatus.YES);
        } else if (occupancyCertificate.getStatus().getCode().equals(BpaConstants.APPLICATION_STATUS_ORDER_ISSUED)) {
            applicationIndex.setApproved(ApprovalStatus.APPROVED);
            applicationIndex.setClosed(ClosureStatus.YES);
        }

        if (occupancyCertificate.getParent().getPlanPermissionNumber() != null)
            applicationIndex.setConsumerCode(occupancyCertificate.getParent().getPlanPermissionNumber());
        applicationIndexService.updateApplicationIndex(applicationIndex);
        createOccupancyIndex(occupancyCertificate);
    }

    private User getCurrentUser(final OccupancyCertificate occupancyCertificate) {
        User user = null;
        if (occupancyCertificate.getState() == null || occupancyCertificate.getState().getOwnerPosition() == null) {
            user = securityUtils.getCurrentUser();
        } else {
            Assignment assignment = assignmentService.getPrimaryAssignmentForPositionAndDate(occupancyCertificate.getState()
                    .getOwnerPosition().getId(), new Date());
            List<Assignment> asignList;
            if (assignment != null) {
                asignList = new ArrayList<>();
                asignList.add(assignment);
            } else
                asignList = assignmentService.getAssignmentsForPosition(occupancyCertificate.getState()
                        .getOwnerPosition().getId(), new Date());
            if (!asignList.isEmpty())
                user = userService.getUserById(asignList.get(0).getEmployee().getId());
        }

        return user;
    }

    private Date calculateDisposalDate(List<AppConfigValues> appConfigValue) {
        return DateUtils.addDays(new Date(),
                appConfigValue.get(0) == null || appConfigValue.get(0).getValue() == null
                        ? 0 : Integer.valueOf(appConfigValue.get(0).getValue()));
    }

}