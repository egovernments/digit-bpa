/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) <2017>  eGovernments Foundation
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

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
import javax.validation.constraints.NotNull;

import org.egov.infra.admin.master.entity.User;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.persistence.entity.AbstractAuditable;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "EGBPA_NOCAPPLICATION")
@SequenceGenerator(name = BpaNocApplication.SEQ_NOCAPPLICATION, sequenceName = BpaNocApplication.SEQ_NOCAPPLICATION, allocationSize = 1)
public class BpaNocApplication extends AbstractAuditable {

    public static final String SEQ_NOCAPPLICATION = "SEQ_EGBPA_NOCAPPLICATION";
    public static final String ORDER_BY_ID_ASC = "id ASC";
    private static final long serialVersionUID = -361205348191992869L;
    public static final String ORDER_BY_NUMBER_ASC = "orderNumber ASC";
    @Id
    @GeneratedValue(generator = SEQ_NOCAPPLICATION, strategy = GenerationType.SEQUENCE)
    private Long id;
    @Length(min = 1, max = 128)
    private String nocApplicationNumber;
    @Length(min = 1, max = 256)
    private String nocType;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status")
    private BpaStatus status;
	@Length(min = 1, max = 128)
    private String remarks;
    private Date slaEndDate;
    private Date deemedApprovedDate;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "egbpa_noc_certificate", joinColumns = @JoinColumn(name = "nocapplication"), inverseJoinColumns = @JoinColumn(name = "fileStore"))
    private Set<FileStoreMapper> nocSupportDocs = new HashSet<>();
    private transient MultipartFile[] files;
    private transient Map<Long, String> encodedImages = new HashMap<>();
    private transient User ownerUser;

	
	
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNocType() {
		return nocType;
	}
	public void setNocType(String nocType) {
		this.nocType = nocType;
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

	public Date getSlaEndDate() {
		return slaEndDate;
	}
	public void setSlaEndDate(Date slaEndDate) {
		this.slaEndDate = slaEndDate;
	}
	public Date getDeemedApprovedDate() {
		return deemedApprovedDate;
	}
	public void setDeemedApprovedDate(Date deemedApprovedDate) {
		this.deemedApprovedDate = deemedApprovedDate;
	}
	public MultipartFile[] getFiles() {
		return files;
	}
	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}
	public Set<FileStoreMapper> getNocSupportDocs() {
		return nocSupportDocs;
	}
	public void setNocSupportDocs(Set<FileStoreMapper> nocSupportDocs) {
		this.nocSupportDocs = nocSupportDocs;
	}
	public Map<Long, String> getEncodedImages() {
		return encodedImages;
	}
	public void setEncodedImages(Map<Long, String> encodedImages) {
		this.encodedImages = encodedImages;
	}
	public String getNocApplicationNumber() {
		return nocApplicationNumber;
	}
	public void setNocApplicationNumber(String nocApplicationNumber) {
		this.nocApplicationNumber = nocApplicationNumber;
	}
	public User getOwnerUser() {
		return ownerUser;
	}
	public void setOwnerUser(User ownerUser) {
		this.ownerUser = ownerUser;
	}
}