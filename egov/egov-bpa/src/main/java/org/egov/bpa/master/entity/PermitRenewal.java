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

package org.egov.bpa.master.entity;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.commons.entity.Source;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.utils.DateUtils;
import org.egov.infra.workflow.entity.StateAware;
import org.egov.pims.commons.Position;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author vinoth
 *
 */
@Entity
@Table(name = "EGBPA_PERMIT_RENEWAL")
@SequenceGenerator(name = PermitRenewal.SEQ_PERMIT_RENEWAL, sequenceName = PermitRenewal.SEQ_PERMIT_RENEWAL, allocationSize = 1)
public class PermitRenewal extends StateAware<Position> {

    /**
    * 
    */
    private static final long serialVersionUID = -4954480849979881787L;

    public static final String SEQ_PERMIT_RENEWAL = "SEQ_EGBPA_PERMIT_RENEWAL";

    @Id
    @GeneratedValue(generator = SEQ_PERMIT_RENEWAL, strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Source source;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent", nullable = false)
    private BpaApplication parent;

    @Length(min = 1, max = 64)
    private String applicationNumber;

    @Temporal(value = TemporalType.DATE)
    private Date applicationDate;

    @Length(min = 1, max = 64)
    private String renewalNumber;

    @Temporal(value = TemporalType.DATE)
    private Date renewalApprovalDate;

    @Temporal(value = TemporalType.DATE)
    private Date permitRenewalExpiryDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "status")
    private BpaStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "constructionStage")
    private ConstructionStages constructionStage;

    @Length(min = 1, max = 256)
    private String constructionStatus;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "egbpa_permit_renewal_documents", joinColumns = @JoinColumn(name = "permitrenewal"), inverseJoinColumns = @JoinColumn(name = "filestore"))
    private Set<FileStoreMapper> permitRenewalDocs = Collections.emptySet();

    private transient MultipartFile[] files;
    private transient String workflowAction;
    private transient Long approvalDepartment;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String myLinkId() {
        return applicationNumber == null ? renewalNumber : applicationNumber;
    }

    @Override
    public String getStateDetails() {
        return String.format("Application Type: %s Applicant Name: %s Application Number %s Dated %s For the service type - %s.",
                parent.getApplicationType().getName(),
                parent.getOwner().getName(),
                applicationNumber == null ? renewalNumber : applicationNumber,
                applicationDate == null ? DateUtils.toDefaultDateFormat(new Date())
                        : DateUtils.toDefaultDateFormat(applicationDate),
                parent.getServiceType().getDescription());
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public BpaApplication getParent() {
        return parent;
    }

    public void setParent(BpaApplication parent) {
        this.parent = parent;
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

    public String getRenewalNumber() {
        return renewalNumber;
    }

    public void setRenewalNumber(String renewalNumber) {
        this.renewalNumber = renewalNumber;
    }

    public Date getRenewalApprovalDate() {
        return renewalApprovalDate;
    }

    public void setRenewalApprovalDate(Date renewalApprovalDate) {
        this.renewalApprovalDate = renewalApprovalDate;
    }

    public Date getPermitRenewalExpiryDate() {
        return permitRenewalExpiryDate;
    }

    public void setPermitRenewalExpiryDate(Date permitRenewalExpiryDate) {
        this.permitRenewalExpiryDate = permitRenewalExpiryDate;
    }

    public BpaStatus getStatus() {
        return status;
    }

    public void setStatus(BpaStatus status) {
        this.status = status;
    }

    public ConstructionStages getConstructionStage() {
        return constructionStage;
    }

    public void setConstructionStage(ConstructionStages constructionStage) {
        this.constructionStage = constructionStage;
    }

    public String getConstructionStatus() {
        return constructionStatus;
    }

    public void setConstructionStatus(String constructionStatus) {
        this.constructionStatus = constructionStatus;
    }

    public Set<FileStoreMapper> getPermitRenewalDocs() {
        return permitRenewalDocs;
    }

    public void setPermitRenewalDocs(Set<FileStoreMapper> permitRenewalDocs) {
        this.permitRenewalDocs = permitRenewalDocs;
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

}