/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) <2018>  eGovernments Foundation
 *
 *  The updated version of eGov suite of products as by eGovernments Foundation
 *  is available at http://www.egovernments.org
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see http://www.gnu.org/licenses/ or
 *  http://www.gnu.org/licenses/gpl.html .
 *
 *  In addition to the terms of the GPL license to be adhered to in using this
 *  program, the following additional terms are to be complied with:
 *
 *      1) All versions of this program, verbatim or modified must carry this
 *         Legal Notice.
 *      Further, all user interfaces, including but not limited to citizen facing interfaces,
 *         Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *         derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *      For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *      For any further queries on attribution, including queries on brand guidelines,
 *         please contact contact@egovernments.org
 *
 *      2) Any misrepresentation of the origin of the material is prohibited. It
 *         is required that all modified versions of this material be marked in
 *         reasonable ways as different from the original version.
 *
 *      3) This license does not grant any rights to any user of the program
 *         with regards to rights under trademark law for use of the trade names
 *         or trademarks of eGovernments Foundation.
 *
 *  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.bpa.transaction.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.egov.commons.entity.Source;
import org.egov.dcb.bean.Receipt;
import org.egov.demand.model.EgDemand;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.utils.DateUtils;
import org.egov.infra.workflow.entity.StateAware;
import org.egov.pims.commons.Position;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "EGBPA_OWNERSHIP_TRANSFER")
@SequenceGenerator(name = OwnershipTransfer.SEQ_OWNERSHIP_TRANSFER, sequenceName = OwnershipTransfer.SEQ_OWNERSHIP_TRANSFER, allocationSize = 1)
public class OwnershipTransfer extends StateAware<Position> {

    private static final long serialVersionUID = -4954480849979881787L;

    public static final String SEQ_OWNERSHIP_TRANSFER = "SEQ_EGBPA_OWNERSHIP_TRANSFER";
    public static final String ORDER_BY_ID_ASC = "id ASC";

    @Id
    @GeneratedValue(generator = SEQ_OWNERSHIP_TRANSFER, strategy = GenerationType.SEQUENCE)
    private Long id;

    
    @Enumerated(EnumType.STRING)
    private Source source;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "application", nullable = false)
    private BpaApplication application;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent")
    private OwnershipTransfer parent;

    @Valid
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", nullable = false)
    private Applicant owner;

    @SafeHtml
    @Length(min = 1, max = 64)
    @Column(unique = true)
    private String applicationNumber;

    @Temporal(value = TemporalType.DATE)
    private Date applicationDate;

    @SafeHtml
    @Length(min = 1, max = 64)
    @Column(unique = true)
    private String ownershipNumber;

    @Temporal(value = TemporalType.DATE)
    private Date ownershipApprovalDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "demand")
    private EgDemand demand;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "status")
    private BpaStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "approverPosition")
    private Position approverPosition;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "approverUser")
    private User approverUser;

    @SafeHtml
    @Length(min = 1, max = 128)
    private String remarks;

    private Boolean isActive;

    @PositiveOrZero
    private BigDecimal admissionfeeAmount;

    private Boolean mailPwdRequired = false;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "egbpa_ownership_transfer_documents", joinColumns = @JoinColumn(name = "ownershipTransfer"), inverseJoinColumns = @JoinColumn(name = "filestore"))
    private Set<FileStoreMapper> ownershipTransferDocs = Collections.emptySet();

    @OneToMany(mappedBy = "ownershipTransfer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OwnershipFee> ownershipFee = new ArrayList<>();

    @OneToMany(mappedBy = "ownershipTransfer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OwnershipTransferNotice> ownershipNotices = new ArrayList<>(0);

    @OneToMany(mappedBy = "ownershipTransfer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OwnershipTransferConditions> rejectionReasons = new ArrayList<>(0);

    @OrderBy(ORDER_BY_ID_ASC)
    @OneToMany(mappedBy = "ownershipTransfer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OwnershipTransferConditions> additionalOwnershipConditions = new ArrayList<>(0);

    @OneToMany(mappedBy = "ownershipTransfer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OwnershipTransferCoApplicant> coApplicants = new ArrayList<>();

    @OrderBy(ORDER_BY_ID_ASC)
    @OneToMany(mappedBy = "ownershipTransfer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OwnershipTransferDocument> ownershipTransferDocuments = new ArrayList<>(0);

    private transient MultipartFile[] files;

    @SafeHtml
    private transient String workflowAction;

    private transient Long approvalDepartment;

    @SafeHtml
    private transient String approvalComent;

    @Valid
    private transient List<OwnershipTransferConditions> dynamicOwenrshipConditionsTemp = new ArrayList<>(0);

    @Valid
    private transient List<OwnershipTransferConditions> staticOwenrshipConditionsTemp = new ArrayList<>(0);

    @Valid
    private transient List<OwnershipTransferConditions> additionalOwenrshipConditionsTemp = new ArrayList<>(0);

    @Valid
    private transient List<OwnershipTransferConditions> rejectionReasonsTemp = new ArrayList<>(0);

    @Valid
    private transient List<OwnershipTransferConditions> additionalRejectReasonsTemp = new ArrayList<>(0);

    private transient Set<Receipt> receipts = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    @Override
    public String myLinkId() {
        return applicationNumber == null ? ownershipNumber : applicationNumber;
    }

    @Override
    public String getStateDetails() {
        return String.format("Application Type: %s Applicant Name: %s Application Number %s Dated %s For the service type - %s.",
                application.getApplicationType().getName(),
                owner.getName(),
                applicationNumber == null ? ownershipNumber : applicationNumber,
                applicationDate == null ? DateUtils.toDefaultDateFormat(new Date())
                        : DateUtils.toDefaultDateFormat(applicationDate),
                application.getServiceType().getDescription());
    }

    public String getApplicantName() {
        StringBuilder nameSB = new StringBuilder();
        nameSB.append(owner == null ? "" : owner.getName());
        if (!coApplicants.isEmpty()) {
            List<CoApplicant> coApps = coApplicants.stream().map(coapp -> coapp.getCoApplicant()).collect(Collectors.toList());
            nameSB.append(",").append(
                    coApps.stream().map(CoApplicant::getName).collect(Collectors.joining(",")));
        }
        return nameSB.toString();
    }

    public OwnershipTransfer getParent() {
        return parent;
    }

    public void setParent(OwnershipTransfer parent) {
        this.parent = parent;
    }

    public BpaApplication getApplication() {
        return application;
    }

    public void setApplication(BpaApplication application) {
        this.application = application;
    }

    public Applicant getOwner() {
        return owner;
    }

    public void setOwner(Applicant owner) {
        this.owner = owner;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public EgDemand getDemand() {
        return demand;
    }

    public void setDemand(EgDemand demand) {
        this.demand = demand;
    }

    public BpaStatus getStatus() {
        return status;
    }

    public void setStatus(BpaStatus status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getMailPwdRequired() {
        return mailPwdRequired;
    }

    public void setMailPwdRequired(Boolean mailPwdRequired) {
        this.mailPwdRequired = mailPwdRequired;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile... files) {
        this.files = files;
    }

    public String getWorkflowAction() {
        return workflowAction;
    }

    public void setWorkflowAction(String workflowAction) {
        this.workflowAction = workflowAction;
    }

    public Long getApprovalDepartment() {
        return approvalDepartment;
    }

    public void setApprovalDepartment(Long approvalDepartment) {
        this.approvalDepartment = approvalDepartment;
    }

    public String getApprovalComent() {
        return approvalComent;
    }

    public void setApprovalComent(String approvalComent) {
        this.approvalComent = approvalComent;
    }

    public Set<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(Set<Receipt> receipts) {
        this.receipts = receipts;
    }

    public String getOwnershipNumber() {
        return ownershipNumber;
    }

    public void setOwnershipNumber(String ownershipNumber) {
        this.ownershipNumber = ownershipNumber;
    }

    public Date getOwnershipApprovalDate() {
        return ownershipApprovalDate;
    }

    public void setOwnershipApprovalDate(Date ownershipApprovalDate) {
        this.ownershipApprovalDate = ownershipApprovalDate;
    }

    public Set<FileStoreMapper> getOwnershipTransferDocs() {
        return ownershipTransferDocs;
    }

    public void setOwnershipTransferDocs(Set<FileStoreMapper> ownershipTransferDocs) {
        this.ownershipTransferDocs = ownershipTransferDocs;
    }

    public List<OwnershipTransferNotice> getOwnershipNotices() {
        return ownershipNotices;
    }

    public void setOwnershipNotices(List<OwnershipTransferNotice> ownershipNotices) {
        this.ownershipNotices = ownershipNotices;
    }

    public List<OwnershipTransferConditions> getRejectionReasons() {
        return rejectionReasons;
    }

    public void setRejectionReasons(List<OwnershipTransferConditions> rejectionReasons) {
        this.rejectionReasons = rejectionReasons;
    }

    public List<OwnershipFee> getOwnershipFee() {
        return ownershipFee;
    }

    public void setOwnershipFee(List<OwnershipFee> ownershipFee) {
        this.ownershipFee = ownershipFee;
    }

    public void addOwnershipNotice(OwnershipTransferNotice ownershipNotice) {
        this.ownershipNotices.add(ownershipNotice);
    }

    public List<OwnershipTransferConditions> getAdditionalOwnershipConditions() {
        return additionalOwnershipConditions;
    }

    public void setAdditionalOwnershipConditions(List<OwnershipTransferConditions> additionalOwnershipConditions) {
        this.additionalOwnershipConditions = additionalOwnershipConditions;
    }

    public List<OwnershipTransferConditions> getDynamicOwenrshipConditionsTemp() {
        return dynamicOwenrshipConditionsTemp;
    }

    public void setDynamicOwenrshipConditionsTemp(List<OwnershipTransferConditions> dynamicOwenrshipConditionsTemp) {
        this.dynamicOwenrshipConditionsTemp = dynamicOwenrshipConditionsTemp;
    }

    public List<OwnershipTransferConditions> getStaticOwenrshipConditionsTemp() {
        return staticOwenrshipConditionsTemp;
    }

    public void setStaticOwenrshipConditionsTemp(List<OwnershipTransferConditions> staticOwenrshipConditionsTemp) {
        this.staticOwenrshipConditionsTemp = staticOwenrshipConditionsTemp;
    }

    public List<OwnershipTransferConditions> getAdditionalOwenrshipConditionsTemp() {
        return additionalOwenrshipConditionsTemp;
    }

    public void setAdditionalOwenrshipConditionsTemp(
            List<OwnershipTransferConditions> additionalOwenrshipConditionsTemp) {
        this.additionalOwenrshipConditionsTemp = additionalOwenrshipConditionsTemp;
    }

    public List<OwnershipTransferConditions> getRejectionReasonsTemp() {
        return rejectionReasonsTemp;
    }

    public void setRejectionReasonsTemp(List<OwnershipTransferConditions> rejectionReasonsTemp) {
        this.rejectionReasonsTemp = rejectionReasonsTemp;
    }

    public List<OwnershipTransferConditions> getAdditionalRejectReasonsTemp() {
        return additionalRejectReasonsTemp;
    }

    public void setAdditionalRejectReasonsTemp(List<OwnershipTransferConditions> additionalRejectReasonsTemp) {
        this.additionalRejectReasonsTemp = additionalRejectReasonsTemp;
    }

    public List<OwnershipTransferCoApplicant> getCoApplicants() {
        return coApplicants;
    }

    public void setCoApplicants(List<OwnershipTransferCoApplicant> coApplicants) {
        this.coApplicants = coApplicants;
    }

    public List<OwnershipTransferDocument> getOwnershipTransferDocuments() {
        return ownershipTransferDocuments;
    }

    public void setOwnershipTransferDocuments(List<OwnershipTransferDocument> ownershipTransferDocuments) {
        this.ownershipTransferDocuments = ownershipTransferDocuments;
    }

    public BigDecimal getAdmissionfeeAmount() {
        return admissionfeeAmount;
    }

    public void setAdmissionfeeAmount(BigDecimal admissionfeeAmount) {
        this.admissionfeeAmount = admissionfeeAmount;
    }

    public Position getApproverPosition() {
        return approverPosition;
    }

    public void setApproverPosition(Position approverPosition) {
        this.approverPosition = approverPosition;
    }

    public User getApproverUser() {
        return approverUser;
    }

    public void setApproverUser(User approverUser) {
        this.approverUser = approverUser;
    }
}