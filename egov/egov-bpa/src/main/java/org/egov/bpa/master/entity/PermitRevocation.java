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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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

import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.infra.persistence.entity.AbstractAuditable;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "EGBPA_PERMIT_REVOCATION")
@SequenceGenerator(name = PermitRevocation.SEQ_REVOCATION, sequenceName = PermitRevocation.SEQ_REVOCATION, allocationSize = 1)
public class PermitRevocation extends AbstractAuditable {

    /**
    * 
    */
    private static final long serialVersionUID = -4954480849979881787L;

    public static final String SEQ_REVOCATION = "SEQ_EGBPA_PERMIT_REVOCATION";

    @Id
    @GeneratedValue(generator = SEQ_REVOCATION, strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "application", nullable = false)
    private BpaApplication application;

    @Length(min = 1, max = 64)
    private String applicationNumber;

    @Temporal(value = TemporalType.DATE)
    private Date applicationDate;

    @Length(min = 1, max = 64)
    private String revocationNumber;

    @Temporal(value = TemporalType.DATE)
    private Date revocationDate;

    @Length(min = 1, max = 1024)
    private String initiateRemarks;

    @Length(min = 1, max = 1024)
    private String approveCancelRemarks;

    @OneToMany(mappedBy = "revocation", cascade = CascadeType.ALL)
    @OrderBy("id asc")
    private List<PermitRevocationDetail> revocationDetails = new ArrayList<>();

    private transient String workflowAction;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public BpaApplication getApplication() {
        return application;
    }

    public void setApplication(BpaApplication application) {
        this.application = application;
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

    public String getRevocationNumber() {
        return revocationNumber;
    }

    public void setRevocationNumber(String revocationNumber) {
        this.revocationNumber = revocationNumber;
    }

    public Date getRevocationDate() {
        return revocationDate;
    }

    public void setRevocationDate(Date revocationDate) {
        this.revocationDate = revocationDate;
    }

    public String getInitiateRemarks() {
        return initiateRemarks;
    }

    public void setInitiateRemarks(String initiateRemarks) {
        this.initiateRemarks = initiateRemarks;
    }

    public String getApproveCancelRemarks() {
        return approveCancelRemarks;
    }

    public void setApproveCancelRemarks(String approveCancelRemarks) {
        this.approveCancelRemarks = approveCancelRemarks;
    }

    public List<PermitRevocationDetail> getRevocationDetails() {
        return revocationDetails;
    }

    public void setRevocationDetails(List<PermitRevocationDetail> revocationDetails) {
        this.revocationDetails = revocationDetails;
    }

    public String getWorkflowAction() {
        return workflowAction;
    }

    public void setWorkflowAction(String workflowAction) {
        this.workflowAction = workflowAction;
    }

}