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

package org.egov.bpa.master.entity;

import static org.egov.infra.validation.constants.ValidationErrorCode.INVALID_ADDRESS;
import static org.egov.infra.validation.constants.ValidationErrorCode.INVALID_PERSON_NAME;
import static org.egov.infra.validation.constants.ValidationErrorCode.INVALID_PHONE_NUMBER;
import static org.egov.infra.validation.constants.ValidationRegex.ADDRESS;
import static org.egov.infra.validation.constants.ValidationRegex.PERSON_NAME;
import static org.egov.infra.validation.constants.ValidationRegex.PHONE_NUMBER;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.apache.commons.lang3.StringUtils;
import org.egov.bpa.master.entity.enums.StakeHolderStatus;
import org.egov.bpa.transaction.entity.StakeHolderDocument;
import org.egov.commons.entity.Source;
import org.egov.demand.model.EgDemand;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.persistence.entity.CorrespondenceAddress;
import org.egov.infra.persistence.entity.PermanentAddress;
import org.egov.infra.persistence.entity.enums.UserType;
import org.egov.infra.persistence.validator.annotation.Unique;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Table(name = "EGBPA_MSTR_STAKEHOLDER", schema = "state")
@Unique(fields = { "code", "coaEnrolmentNumber", "tinNumber" }, enableDfltMsg = true)
public class StakeHolder extends User {

    private static final long serialVersionUID = 3078684328383202788L;

    @Valid
    @OneToMany(mappedBy = "stakeHolder", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StakeHolderDocument> stakeHolderDocument = new ArrayList<>(0);

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stakeholdertype")
    private StakeHolderType stakeHolderType;

    @SafeHtml
    @NotNull
    @Length(min = 1, max = 128)
    @Column(name = "code", unique = true)
    @Audited
    private String code;

    @SafeHtml
    @Length(min = 1, max = 64)
    @Audited
    private String licenceNumber;

    @Temporal(value = TemporalType.DATE)
    private Date buildingLicenceIssueDate;

    @Enumerated(EnumType.ORDINAL)
    private Source source;

    @Temporal(value = TemporalType.DATE)
    private Date buildingLicenceExpiryDate;

    @SafeHtml
    @Length(min = 1, max = 64)
    private String coaEnrolmentNumber;

    @Temporal(value = TemporalType.DATE)
    private Date coaEnrolmentDueDate;

    private Boolean isEnrolWithLocalBody;

    @SafeHtml
    @Length(min = 1, max = 128)
    @Pattern(regexp = PERSON_NAME, message = INVALID_PERSON_NAME)
    private String organizationName;

    @SafeHtml
    @Length(min = 1, max = 128)
    @Pattern(regexp = ADDRESS, message = INVALID_ADDRESS)
    private String organizationAddress;

    @SafeHtml
    @Length(min = 1, max = 64)
    private String organizationUrl;

    @SafeHtml
    @Length(min = 1, max = 15)
    @Pattern(regexp = PHONE_NUMBER, message = INVALID_PHONE_NUMBER)
    private String organizationMobNo;

    private Boolean isOnbehalfOfOrganization;

    @SafeHtml
    @Length(max = 11)
    private String tinNumber;

    @SafeHtml
    @Length(max = 256)
    @Pattern(regexp = PERSON_NAME, message = INVALID_PERSON_NAME)
    private String contactPerson;

    @SafeHtml
    @Length(max = 50)
    private String designation;

    @SafeHtml
    @Length(max = 64)
    private String cinNumber;

    @SafeHtml
    @Audited
    private String comments;

    @Enumerated(EnumType.STRING)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private StakeHolderStatus status;

    private Boolean isAddressSame;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdUser")
    private User createdUser;

    private Date createDate;

    @Audited
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lastUpdatedUser")
    private User lastUpdatedUser;

    @Audited
    private Date lastUpdatedDate;

    @PositiveOrZero
    private Integer noOfTimesRejected;

    @PositiveOrZero
    private Integer noOfTimesBlocked;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "demand")
    private EgDemand demand;

    @Valid
    private transient CorrespondenceAddress correspondenceAddress = new CorrespondenceAddress();

    @Valid
    private transient PermanentAddress permanentAddress = new PermanentAddress();

    private transient List<CheckListDetail> checkListDocuments = new ArrayList<>(0);

    private transient String activationCode;

    private transient Long approvalDepartment;

    private transient Long approvalDesignation;

    @SafeHtml
    private transient String workFlowAction;

    private transient Long nextPosition;

    @SafeHtml
    private transient String approvalComent;

    public String getApprovalComent() {
        return approvalComent;
    }

    public void setApprovalComent(String approvalComent) {
        this.approvalComent = approvalComent;
    }

    public String getWorkFlowAction() {
        return workFlowAction;
    }

    public void setWorkFlowAction(String wfAction) {
        this.workFlowAction = wfAction;
    }

    public Long getNextPosition() {
        return nextPosition;
    }

    public void setNextPosition(Long nextPosition) {
        this.nextPosition = nextPosition;
    }

    public Boolean getIsAddressSame() {
        return isAddressSame;
    }

    public void setIsAddressSame(Boolean isAddressSame) {
        this.isAddressSame = isAddressSame;
    }

    public StakeHolder() {
        setType(UserType.BUSINESS);
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public StakeHolderType getStakeHolderType() {
        return stakeHolderType;
    }

    public void setStakeHolderType(final StakeHolderType stakeHolderType) {
        this.stakeHolderType = stakeHolderType;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public Date getBuildingLicenceIssueDate() {
        return buildingLicenceIssueDate;
    }

    public void setBuildingLicenceIssueDate(Date buildingLicenceIssueDate) {
        this.buildingLicenceIssueDate = buildingLicenceIssueDate;
    }

    public Date getBuildingLicenceExpiryDate() {
        return buildingLicenceExpiryDate;
    }

    public void setBuildingLicenceExpiryDate(Date buildingLicenceExpiryDate) {
        this.buildingLicenceExpiryDate = buildingLicenceExpiryDate;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCoaEnrolmentNumber() {
        return coaEnrolmentNumber;
    }

    public void setCoaEnrolmentNumber(final String coaEnrolmentNumber) {
        this.coaEnrolmentNumber = coaEnrolmentNumber;
    }

    public Boolean getIsEnrolWithLocalBody() {
        return isEnrolWithLocalBody;
    }

    public void setIsEnrolWithLocalBody(final Boolean isEnrolWithLocalBody) {
        this.isEnrolWithLocalBody = isEnrolWithLocalBody;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(final String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationAddress() {
        return organizationAddress;
    }

    public void setOrganizationAddress(final String organizationAddress) {
        this.organizationAddress = organizationAddress;
    }

    public String getOrganizationUrl() {
        return organizationUrl;
    }

    public void setOrganizationUrl(final String organizationUrl) {
        this.organizationUrl = organizationUrl;
    }

    public String getOrganizationMobNo() {
        return organizationMobNo;
    }

    public void setOrganizationMobNo(final String organizationMobNo) {
        this.organizationMobNo = organizationMobNo;
    }

    public Boolean getIsOnbehalfOfOrganization() {
        return isOnbehalfOfOrganization;
    }

    public void setIsOnbehalfOfOrganization(final Boolean isOnbehalfOfOrganization) {
        this.isOnbehalfOfOrganization = isOnbehalfOfOrganization;
    }

    public String getTinNumber() {
        return tinNumber;
    }

    public void setTinNumber(final String tinNumber) {
        this.tinNumber = tinNumber;
    }

    public List<StakeHolderDocument> getStakeHolderDocument() {
        return stakeHolderDocument;
    }

    public void setStakeHolderDocument(List<StakeHolderDocument> stakeHolderDocument) {
        this.stakeHolderDocument = stakeHolderDocument;
    }

    public Date getCoaEnrolmentDueDate() {
        return coaEnrolmentDueDate;
    }

    public void setCoaEnrolmentDueDate(final Date coaEnrolmentDueDate) {
        this.coaEnrolmentDueDate = coaEnrolmentDueDate;
    }

    public CorrespondenceAddress getCorrespondenceAddress() {
        return correspondenceAddress;
    }

    public void setCorrespondenceAddress(final CorrespondenceAddress correspondenceAddress) {
        this.correspondenceAddress = correspondenceAddress;
    }

    public PermanentAddress getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(final PermanentAddress permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public List<CheckListDetail> getCheckListDocuments() {
        return checkListDocuments;
    }

    public void setCheckListDocuments(final List<CheckListDetail> checkListDocuments) {
        this.checkListDocuments = checkListDocuments;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public StakeHolderStatus getStatus() {
        return status;
    }

    public void setStatus(StakeHolderStatus status) {
        this.status = status;
    }

    public User getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(User createdUser) {
        this.createdUser = createdUser;
    }

    public User getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(User lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Integer getNoOfTimesRejected() {
        return noOfTimesRejected;
    }

    public void setNoOfTimesRejected(Integer noOfTimesRejected) {
        this.noOfTimesRejected = noOfTimesRejected;
    }

    public Integer getNoOfTimesBlocked() {
        return noOfTimesBlocked;
    }

    public void setNoOfTimesBlocked(Integer noOfTimesBlocked) {
        this.noOfTimesBlocked = noOfTimesBlocked;
    }

    public EgDemand getDemand() {
        return demand;
    }

    public void setDemand(final EgDemand demand) {
        this.demand = demand;
    }

    public String showAadhaarNumber() {
        return StringUtils.isBlank(getAadhaarNumber()) ? getAadhaarNumber()
                : getAadhaarNumber().replaceAll("\\d(?=(?:\\D*\\d){4})", "*");
    }

    public String showMobileNumber() {
        return StringUtils.isBlank(getMobileNumber()) ? getMobileNumber()
                : getMobileNumber().replaceAll("\\d(?=(?:\\D*\\d){4})", "*");
    }

    public String showPanNumber() {
        return StringUtils.isBlank(getPan()) ? getPan() : getPan().substring(0, getPan().length() - 6) + "*******";
    }

    public Long getApprovalDepartment() {
        return approvalDepartment;
    }

    public void setApprovalDepartment(final Long approvalDepartment) {
        this.approvalDepartment = approvalDepartment;
    }

    public Long getApprovalDesignation() {
        return approvalDesignation;
    }

    public void setApprovalDesignation(final Long approvalDesignation) {
        this.approvalDesignation = approvalDesignation;
    }

    public String getCinNumber() {
        return cinNumber;
    }

    public void setCinNumber(String cinNumber) {
        this.cinNumber = cinNumber;
    }

}