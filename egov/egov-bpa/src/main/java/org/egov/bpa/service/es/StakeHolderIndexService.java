package org.egov.bpa.service.es;

import org.apache.commons.lang3.StringUtils;
import org.egov.bpa.entity.es.StakeHolderIndex;
import org.egov.bpa.master.entity.StakeHolder;
import org.egov.bpa.master.entity.enums.StakeHolderStatus;
import org.egov.bpa.repository.es.StakeHolderIndexRepository;
import org.egov.bpa.utils.BpaConstants;
import org.egov.commons.entity.Source;
import org.egov.infra.admin.master.entity.City;
import org.egov.infra.admin.master.service.CityService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.elasticsearch.entity.ApplicationIndex;
import org.egov.infra.elasticsearch.entity.enums.ApprovalStatus;
import org.egov.infra.elasticsearch.entity.enums.ClosureStatus;
import org.egov.infra.elasticsearch.service.ApplicationIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
@Transactional(readOnly = true)
public class StakeHolderIndexService {

    @Autowired
    private CityService cityService;

    @Autowired
    private StakeHolderIndexRepository stakeHolderRepository;

    @Autowired
    private ApplicationIndexService applicationIndexService;

    public StakeHolderIndex createStakeHolderIndex(final StakeHolder stakeHolder) {
        final City cityWebsite = cityService.getCityByURL(ApplicationThreadLocals.getDomainName());
        StakeHolderIndex stakeHolderIndex = new StakeHolderIndex();
        buildUlbDetails(cityWebsite, stakeHolderIndex);
        stakeHolderIndex.setId(String.valueOf(stakeHolder.getId()));
        stakeHolderIndex.setCode(stakeHolder.getCode() == null ? EMPTY : stakeHolder.getCode());
        stakeHolderIndex.setBuildingLicenceIssueDate(stakeHolder.getBuildingLicenceIssueDate());
        stakeHolderIndex.setLicenceNumber(stakeHolder.getLicenceNumber() == null ? EMPTY : stakeHolder.getLicenceNumber());
        stakeHolderIndex.setStakeHolderName(stakeHolder.getName() == null ? EMPTY : stakeHolder.getName());
        stakeHolderIndex.setStakeHolderType(stakeHolder.getType() == null ? EMPTY : stakeHolder.getType().name());
        stakeHolderIndex.setStatus(stakeHolder.getStatus() == null ? EMPTY : stakeHolder.getStatus().name());
        stakeHolderRepository.save(stakeHolderIndex);
        return stakeHolderIndex;
    }

    private void buildUlbDetails(final City cityWebsite, StakeHolderIndex stakeHolderIndex) {
        stakeHolderIndex.setUlbName(cityWebsite.getName());
        stakeHolderIndex.setDistrictName(cityWebsite.getDistrictName());
        stakeHolderIndex.setRegionName(cityWebsite.getRegionName());
        stakeHolderIndex.setUlbGrade(cityWebsite.getGrade());
        stakeHolderIndex.setUlbCode(cityWebsite.getCode());
    }

    public void updateIndexes(final StakeHolder stakeHolder) {
        ApplicationIndex applicationIndex = applicationIndexService.findByApplicationNumber(stakeHolder
                .getCode());
        if (applicationIndex != null && stakeHolder.getId() != null)
            buildApplicationIndexForUpdate(stakeHolder, applicationIndex);
        else {
            String viewUrl = "/stakeholder/view/%s";
            applicationIndex = ApplicationIndex.builder().withModuleName(BpaConstants.APPL_INDEX_MODULE_NAME)
                    .withApplicationNumber(stakeHolder.getCode())
                    .withApplicationDate(stakeHolder.getBuildingLicenceIssueDate())
                    .withApplicationType(stakeHolder.getType().name())
                    .withOwnername(stakeHolder.getName())
                    .withApplicantName(stakeHolder.getName())
                    .withApplicantAddress(stakeHolder.getOrganizationAddress())
                    .withStatus(stakeHolder.getStatus().name())
                    .withChannel(stakeHolder.getSource() == null ? Source.SYSTEM.toString()
                            : stakeHolder.getSource().name())
                    .withConsumerCode(stakeHolder.getCode())
                    .withMobileNumber(stakeHolder.getMobileNumber())
                    .withAadharNumber(StringUtils.isBlank(stakeHolder.getAadhaarNumber())
                            ? EMPTY : stakeHolder.getAadhaarNumber())
                    .withUrl(String.format(viewUrl, stakeHolder.getId()))
                    .withClosed(ClosureStatus.NO)
                    .withApproved(ApprovalStatus.INPROGRESS)
                    .build();
            applicationIndexService.createApplicationIndex(applicationIndex);
            createStakeHolderIndex(stakeHolder);
        }
    }

    private void buildApplicationIndexForUpdate(final StakeHolder stakeHolder,
                                                ApplicationIndex applicationIndex) {
        applicationIndex.setStatus(stakeHolder.getStatus().name());
        if (stakeHolder.getStatus().name().equals(StakeHolderStatus.REJECTED)) {
            applicationIndex.setApproved(ApprovalStatus.REJECTED);
            applicationIndex.setClosed(ClosureStatus.YES);
        } else if (stakeHolder.getStatus().name().equals(StakeHolderStatus.APPROVED)) {
            applicationIndex.setApproved(ApprovalStatus.APPROVED);
            applicationIndex.setClosed(ClosureStatus.YES);
        }
        applicationIndexService.updateApplicationIndex(applicationIndex);
        createStakeHolderIndex(stakeHolder);
    }
}
