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

import org.egov.bpa.master.entity.CheckListDetail;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.persistence.entity.AbstractAuditable;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
@Table(name = "egbpa_documents_common")
@SequenceGenerator(name = DocumentsCommon.SEQ_COMMON_DOCUMENT, sequenceName = DocumentsCommon.SEQ_COMMON_DOCUMENT, allocationSize = 1)
public class DocumentsCommon extends AbstractAuditable {

	public static final String SEQ_COMMON_DOCUMENT = "seq_egbpa_documents_common";
	private static final long serialVersionUID = 8833590155845005135L;

	@Id
	@GeneratedValue(generator = SEQ_COMMON_DOCUMENT, strategy = GenerationType.SEQUENCE)
	private Long id;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "egbpa_documents_common_files", joinColumns = @JoinColumn(name = "documentid"), inverseJoinColumns = @JoinColumn(name = "filestoreid"))
	private Set<FileStoreMapper> supportDocs = Collections.emptySet();

	@ManyToOne(cascade = CascadeType.ALL)
	@NotNull
	@JoinColumn(name = "checklistDetail", nullable = false)
	private CheckListDetail checklistDetail;

	@Temporal(value = TemporalType.DATE)
	private Date submissionDate;

	private Boolean isSubmitted;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "createduser")
	private User createdUser;

	@Length(min = 1, max = 256)
	private String remarks;

	private transient MultipartFile[] files;

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

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(final Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public Boolean getSubmitted() {
		return isSubmitted;
	}

	public void setSubmitted(Boolean submitted) {
		isSubmitted = submitted;
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

	public void setRemarks(final String remarks) {
		this.remarks = remarks;
	}

	public CheckListDetail getChecklistDetail() {
		return checklistDetail;
	}

	public void setChecklistDetail(final CheckListDetail checklistDetail) {
		this.checklistDetail = checklistDetail;
	}

	public Set<FileStoreMapper> getSupportDocs() {
		return supportDocs;
	}

	public void setSupportDocs(final Set<FileStoreMapper> supportDocs) {
		this.supportDocs = supportDocs;
	}

	public Set<FileStoreMapper> getOrderedSupportDocs() {
		return this.supportDocs
				.stream()
				.sorted(Comparator.comparing(FileStoreMapper::getId))
				.collect(Collectors.toSet());
	}

}
