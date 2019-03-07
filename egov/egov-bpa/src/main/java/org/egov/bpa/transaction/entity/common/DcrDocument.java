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
package org.egov.bpa.transaction.entity.common;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.egov.bpa.master.entity.ChecklistServiceTypeMapping;
import org.egov.infra.persistence.entity.AbstractAuditable;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "egbpa_dcr_document")
@SequenceGenerator(name = DcrDocument.SEQ_APPLN_DCR_DOCUMENT, sequenceName = DcrDocument.SEQ_APPLN_DCR_DOCUMENT, allocationSize = 1)
public class DcrDocument extends AbstractAuditable {

    private static final long serialVersionUID = -4967429196646450211L;
    public static final String SEQ_APPLN_DCR_DOCUMENT = "seq_egbpa_dcr_document";
    @Id
    @GeneratedValue(generator = SEQ_APPLN_DCR_DOCUMENT, strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    @NotNull
    @JoinColumn(name = "servicechecklist", nullable = false)
    private ChecklistServiceTypeMapping serviceChecklist;
    @Temporal(value = TemporalType.DATE)
    private Date submissionDate;
    private Boolean isSubmitted;
    @Length(min = 1, max = 256)
    private String remarks;
    @OrderBy("id ASC")
    @OneToMany(mappedBy = "dcrDocument", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<StoreDcrFiles> dcrAttachments = Collections.emptySet();
    private transient MultipartFile[] files;
    private transient String[] fileStoreIds;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public ChecklistServiceTypeMapping getServiceChecklist() {
        return serviceChecklist;
    }

    public void setServiceChecklist(ChecklistServiceTypeMapping serviceChecklist) {
        this.serviceChecklist = serviceChecklist;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Boolean getSubmitted() {
        return isSubmitted;
    }

    public void setSubmitted(Boolean submitted) {
        isSubmitted = submitted;
    }

    public Set<StoreDcrFiles> getDcrAttachments() {
        return dcrAttachments;
    }

    public Set<StoreDcrFiles> getOrderedDcrAttachments() {
        return this.dcrAttachments.stream()
                .sorted(Comparator.comparing(StoreDcrFiles::getId))
                .collect(Collectors.toSet());
    }

    public void setDcrAttachments(Set<StoreDcrFiles> dcrAttachments) {
        this.dcrAttachments = dcrAttachments;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

    public String[] getFileStoreIds() {
        return fileStoreIds;
    }

    public void setFileStoreIds(String[] fileStoreIds) {
        this.fileStoreIds = fileStoreIds;
    }
}
