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

import java.util.Date;
import java.util.HashSet;
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

import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.persistence.entity.AbstractAuditable;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "EGBPA_PERMIT_REVOCATION_DETAIL")
@SequenceGenerator(name = PermitRevocationDetail.SEQ_REVOCATION_DETAIL, sequenceName = PermitRevocationDetail.SEQ_REVOCATION_DETAIL, allocationSize = 1)
public class PermitRevocationDetail extends AbstractAuditable {

    /**
    * 
    */
    private static final long serialVersionUID = -3497037801478484778L;
    public static final String SEQ_REVOCATION_DETAIL = "SEQ_EGBPA_PERMIT_REVOCATION_DETAIL";

    @Id
    @GeneratedValue(generator = SEQ_REVOCATION_DETAIL, strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "revocation", nullable = false)
    private PermitRevocation revocation;
    @Length(min = 1, max = 256)
    private String natureOfRequest;
    @Temporal(value = TemporalType.DATE)
    private Date requestDate;
    @Temporal(value = TemporalType.DATE)
    private Date replyDate;
    @Length(min = 1, max = 128)
    private String issuedBy;
    @Length(min = 1, max = 512)
    private String remarks;
    private Integer orderNumber;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "egbpa_permit_revocation_document", joinColumns = @JoinColumn(name = "revocationDetail"), inverseJoinColumns = @JoinColumn(name = "fileStore"))
    private Set<FileStoreMapper> revokeSupportDocs = new HashSet<>();
    private transient MultipartFile[] files;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public PermitRevocation getRevocation() {
        return revocation;
    }

    public void setRevocation(PermitRevocation revocation) {
        this.revocation = revocation;
    }

    public String getNatureOfRequest() {
        return natureOfRequest;
    }

    public void setNatureOfRequest(String natureOfRequest) {
        this.natureOfRequest = natureOfRequest;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Set<FileStoreMapper> getRevokeSupportDocs() {
        return revokeSupportDocs;
    }

    public void setRevokeSupportDocs(Set<FileStoreMapper> revokeSupportDocs) {
        this.revokeSupportDocs = revokeSupportDocs;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

}