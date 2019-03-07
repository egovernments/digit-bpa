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

import org.egov.bpa.master.entity.LpReason;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.persistence.entity.AbstractAuditable;
import org.hibernate.validator.constraints.Length;

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
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "EGBPA_LETTERTOPARTY_COMMON")
@SequenceGenerator(name = LetterToPartyCommon.SEQ_OC_LETTERTOPARTY, sequenceName = LetterToPartyCommon.SEQ_OC_LETTERTOPARTY, allocationSize = 1)
public class LetterToPartyCommon extends AbstractAuditable {

	public static final String SEQ_OC_LETTERTOPARTY = "SEQ_EGBPA_LETTERTOPARTY_COMMON";
	private static final long serialVersionUID = -2504766895161087311L;

	@Id
	@GeneratedValue(generator = SEQ_OC_LETTERTOPARTY, strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "inspection")
	private InspectionCommon inspection;

	@Length(min = 1, max = 32)
	private String acknowledgementNumber;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "egbpa_lp_reason_common", joinColumns = @JoinColumn(name = "lettertoparty"), inverseJoinColumns = @JoinColumn(name = "lpreason"))
	private List<LpReason> lpReason = new ArrayList<>();

	@Length(min = 1, max = 128)
	private String lpNumber;

	@Temporal(TemporalType.DATE)
	private Date letterDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "scheduledby")
	private User scheduledBy;

	@Length(min = 1, max = 128)
	private String scheduledPlace;

	@Temporal(TemporalType.DATE)
	private Date scheduledTime;

	@Temporal(TemporalType.DATE)
	private Date sentDate;

	@Temporal(TemporalType.DATE)
	private Date replyDate;

	@Length(min = 1, max = 1024)
	private String lpRemarks;

	@Length(min = 1, max = 1024)
	private String lpReplyRemarks;

	@Length(min = 1, max = 1024)
	private String lpDesc;

	@Length(min = 1, max = 1024)
	private String lpReplyDesc;

	private Boolean isHistory;

	@Length(min = 1, max = 512)
	private String documentId;

	@Temporal(TemporalType.DATE)
	private Date lastReplyDate;

	private String currentStateValueOfLP;

	private String stateForOwnerPosition;

	private String pendingAction;

	@OrderBy("id ASC")
	@OneToMany(mappedBy = "letterToParty", cascade = CascadeType.ALL)
	private List<LetterToPartyDocumentCommon> letterToPartyDocuments = new ArrayList<>(0);

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "currentapplnstatus")
	private BpaStatus currentApplnStatus;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public InspectionCommon getInspection() {
		return inspection;
	}

	public void setInspection(InspectionCommon inspection) {
		this.inspection = inspection;
	}

	public String getAcknowledgementNumber() {
		return acknowledgementNumber;
	}

	public void setAcknowledgementNumber(String acknowledgementNumber) {
		this.acknowledgementNumber = acknowledgementNumber;
	}

	public List<LpReason> getLpReason() {
		return lpReason;
	}

	public void setLpReason(List<LpReason> lpReason) {
		this.lpReason = lpReason;
	}

	public String getLpNumber() {
		return lpNumber;
	}

	public void setLpNumber(String lpNumber) {
		this.lpNumber = lpNumber;
	}

	public Date getLetterDate() {
		return letterDate;
	}

	public void setLetterDate(Date letterDate) {
		this.letterDate = letterDate;
	}

	public User getScheduledBy() {
		return scheduledBy;
	}

	public void setScheduledBy(User scheduledBy) {
		this.scheduledBy = scheduledBy;
	}

	public String getScheduledPlace() {
		return scheduledPlace;
	}

	public void setScheduledPlace(String scheduledPlace) {
		this.scheduledPlace = scheduledPlace;
	}

	public Date getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(Date scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public Date getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}

	public String getLpRemarks() {
		return lpRemarks;
	}

	public void setLpRemarks(String lpRemarks) {
		this.lpRemarks = lpRemarks;
	}

	public String getLpReplyRemarks() {
		return lpReplyRemarks;
	}

	public void setLpReplyRemarks(String lpReplyRemarks) {
		this.lpReplyRemarks = lpReplyRemarks;
	}

	public String getLpDesc() {
		return lpDesc;
	}

	public void setLpDesc(String lpDesc) {
		this.lpDesc = lpDesc;
	}

	public String getLpReplyDesc() {
		return lpReplyDesc;
	}

	public void setLpReplyDesc(String lpReplyDesc) {
		this.lpReplyDesc = lpReplyDesc;
	}

	public Boolean getHistory() {
		return isHistory;
	}

	public void setHistory(Boolean history) {
		isHistory = history;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public Date getLastReplyDate() {
		return lastReplyDate;
	}

	public void setLastReplyDate(Date lastReplyDate) {
		this.lastReplyDate = lastReplyDate;
	}

	public String getCurrentStateValueOfLP() {
		return currentStateValueOfLP;
	}

	public void setCurrentStateValueOfLP(String currentStateValueOfLP) {
		this.currentStateValueOfLP = currentStateValueOfLP;
	}

	public String getStateForOwnerPosition() {
		return stateForOwnerPosition;
	}

	public void setStateForOwnerPosition(String stateForOwnerPosition) {
		this.stateForOwnerPosition = stateForOwnerPosition;
	}

	public String getPendingAction() {
		return pendingAction;
	}

	public void setPendingAction(String pendingAction) {
		this.pendingAction = pendingAction;
	}

	public List<LetterToPartyDocumentCommon> getLetterToPartyDocuments() {
		return letterToPartyDocuments;
	}

	public void setLetterToPartyDocuments(List<LetterToPartyDocumentCommon> letterToPartyDocuments) {
		this.letterToPartyDocuments = letterToPartyDocuments;
	}

	public BpaStatus getCurrentApplnStatus() {
		return currentApplnStatus;
	}

	public void setCurrentApplnStatus(BpaStatus currentApplnStatus) {
		this.currentApplnStatus = currentApplnStatus;
	}
}