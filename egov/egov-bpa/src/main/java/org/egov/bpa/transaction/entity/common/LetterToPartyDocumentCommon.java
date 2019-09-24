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

package org.egov.bpa.transaction.entity.common;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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

import org.egov.bpa.master.entity.ChecklistServiceTypeMapping;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.persistence.entity.AbstractAuditable;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "egbpa_lp_document_common")
@SequenceGenerator(name = LetterToPartyDocumentCommon.SEQ_LETTERTOPARTYDOCUMENT, sequenceName = LetterToPartyDocumentCommon.SEQ_LETTERTOPARTYDOCUMENT, allocationSize = 1)
public class LetterToPartyDocumentCommon extends AbstractAuditable {

    public static final String SEQ_LETTERTOPARTYDOCUMENT = "seq_egbpa_lp_document_common";
    private static final long serialVersionUID = 3665580365090675067L;

    @Id
    @GeneratedValue(generator = SEQ_LETTERTOPARTYDOCUMENT, strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "egbpa_lp_files_common", joinColumns = @JoinColumn(name = "lpDocument"), inverseJoinColumns = @JoinColumn(name = "filestore"))
    private Set<FileStoreMapper> supportDocs = Collections.emptySet();

    private transient MultipartFile[] files;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serviceChecklist", nullable = false)
    private ChecklistServiceTypeMapping serviceChecklist;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "lettertoParty", nullable = false)
    private LetterToPartyCommon letterToParty;

    @Temporal(value = TemporalType.DATE)
    private Date submissionDate;

    private Boolean isSubmitted;

    private Boolean isRequested;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createduser")
    private User createdUser;

    @SafeHtml
    @Length(min = 1, max = 256)
    private String remarks;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Set<FileStoreMapper> getSupportDocs() {
        return supportDocs;
    }

    public void setSupportDocs(Set<FileStoreMapper> supportDocs) {
        this.supportDocs = supportDocs;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

    public ChecklistServiceTypeMapping getServiceChecklist() {
        return serviceChecklist;
    }

    public void setServiceChecklist(ChecklistServiceTypeMapping serviceChecklist) {
        this.serviceChecklist = serviceChecklist;
    }

    public LetterToPartyCommon getLetterToParty() {
        return letterToParty;
    }

    public void setLetterToParty(LetterToPartyCommon letterToParty) {
        this.letterToParty = letterToParty;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Boolean getIsSubmitted() {
        return isSubmitted;
    }

    public void setIsSubmitted(Boolean isSubmitted) {
        this.isSubmitted = isSubmitted;
    }

    public Boolean getIsRequested() {
        return isRequested;
    }

    public void setIsRequested(Boolean isRequested) {
        this.isRequested = isRequested;
    }

    public User getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(User createdUser) {
        this.createdUser = createdUser;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
