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
package org.egov.bpa.transaction.entity;

import org.egov.bpa.master.entity.CheckListDetail;
import org.egov.bpa.transaction.entity.enums.NocStatus;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.persistence.entity.AbstractAuditable;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "egbpa_NOC_Document")
@SequenceGenerator(name = ApplicationNocDocument.SEQ_EGBPA_NOC_DOCUMENT, sequenceName = ApplicationNocDocument.SEQ_EGBPA_NOC_DOCUMENT, allocationSize = 1)
public class ApplicationNocDocument extends AbstractAuditable {

    public static final String SEQ_EGBPA_NOC_DOCUMENT = "seq_egbpa_NOC_Document";
    private static final long serialVersionUID = -5625450672698620264L;

    @Id
    @GeneratedValue(generator = SEQ_EGBPA_NOC_DOCUMENT, strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "egbpa_noc_document_store", joinColumns = @JoinColumn(name = "nocdocumentid"), inverseJoinColumns = @JoinColumn(name = "filestoreid"))
    private Set<FileStoreMapper> nocSupportDocs = Collections.emptySet();

    private transient MultipartFile[] files;
    @ManyToOne
    @NotNull
    @JoinColumn(name = "checklist", nullable = false)
    private CheckListDetail checklist;
    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull
    @JoinColumn(name = "application", nullable = false)
    private BpaApplication application;
    @Temporal(value = TemporalType.DATE)
    private Date submissionDate;
    private Boolean issubmitted = false;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "createduser")
    private User createduser;
    @Length(min = 1, max = 1000)
    private String remarks;
    @Length(min = 1, max = 1000)
    private String natureOfRequest;
    @Temporal(value = TemporalType.DATE)
    private Date letterSentOn;
    @Temporal(value = TemporalType.DATE)
    private Date replyReceivedOn;
    private Boolean rejection = false;
    private Boolean notApplicable = false;
    @Enumerated(EnumType.STRING)
    private NocStatus nocStatus;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(final MultipartFile[] files) {
        this.files = files;
    }

    public CheckListDetail getChecklist() {
        return checklist;
    }

    public void setChecklist(final CheckListDetail checklist) {
        this.checklist = checklist;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(final Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Boolean getIssubmitted() {
        return issubmitted;
    }

    public void setIssubmitted(final Boolean issubmitted) {
        this.issubmitted = issubmitted;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(final String remarks) {
        this.remarks = remarks;
    }

    public BpaApplication getApplication() {
        return application;
    }

    public void setApplication(final BpaApplication application) {
        this.application = application;
    }

    public User getCreateduser() {
        return createduser;
    }

    public void setCreateduser(final User createduser) {
        this.createduser = createduser;
    }

    public Set<FileStoreMapper> getNocSupportDocs() {
        return this.nocSupportDocs
                .stream()
                .sorted(Comparator.comparing(FileStoreMapper::getId))
                .collect(Collectors.toSet());
    }

    public void setNocSupportDocs(Set<FileStoreMapper> nocSupportDocs) {
        this.nocSupportDocs = nocSupportDocs;
    }

    public String getNatureOfRequest() {
        return natureOfRequest;
    }

    public void setNatureOfRequest(String natureOfRequest) {
        this.natureOfRequest = natureOfRequest;
    }

    public Date getLetterSentOn() {
        return letterSentOn;
    }

    public void setLetterSentOn(Date letterSentOn) {
        this.letterSentOn = letterSentOn;
    }

    public Date getReplyReceivedOn() {
        return replyReceivedOn;
    }

    public void setReplyReceivedOn(Date replyReceivedOn) {
        this.replyReceivedOn = replyReceivedOn;
    }

    public Boolean getRejection() {
        return rejection;
    }

    public void setRejection(Boolean rejection) {
        this.rejection = rejection;
    }

    public Boolean getNotApplicable() {
        return notApplicable;
    }

    public void setNotApplicable(Boolean notApplicable) {
        this.notApplicable = notApplicable;
    }

    public NocStatus getNocStatus() {
        return nocStatus;
    }

    public void setNocStatus(NocStatus nocStatus) {
        this.nocStatus = nocStatus;
    }

}
