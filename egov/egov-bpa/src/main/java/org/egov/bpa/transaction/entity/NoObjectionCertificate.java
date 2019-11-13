package org.egov.bpa.transaction.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.egov.bpa.master.entity.NocConfig;
import org.egov.bpa.transaction.entity.enums.NocStatus;
import org.hibernate.validator.constraints.Length;
@Entity
@Table(name = "EGBPA_NOC")
@SequenceGenerator(name = NoObjectionCertificate.SEQ, sequenceName = NoObjectionCertificate.SEQ, allocationSize = 1)
public class NoObjectionCertificate {
	public static final String SEQ = "SEQ_EGBPA_NOC";
	@Id
	@GeneratedValue(generator = SEQ, strategy = GenerationType.SEQUENCE)
	private Long id;

	private Date initiationDate;

	private Date endDate;

	@OneToOne
	@JoinColumn(name="nocConfigId")
	private NocConfig nocConfig;
 
	@Enumerated(EnumType.STRING)
	private NocStatus status;

	@OneToOne
	@JoinColumn(name="supportDocsId")
	private DocumentAttachment supportDocs;
	
	
	@ManyToOne
	@JoinColumn(name="noclistId")
	private NocList nocList;

	@OneToOne
	@JoinColumn(name="nocId")
	private DocumentAttachment noc;
	
	@Length(min=2,max=256)
	private String referenceNumber;

	private Boolean isDeemedApproved;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public NocConfig getNocConfig() {
		return nocConfig;
	}

	public void setNocConfig(NocConfig nocConfig) {
		this.nocConfig = nocConfig;
	}

	public NocStatus getStatus() {
		return status;
	}

	public void setStatus(NocStatus status) {
		this.status = status;
	}

	public DocumentAttachment getNoc() {
		return noc;
	}

	public void setNoc(DocumentAttachment noc) {
		this.noc = noc;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public Date getInitiationDate() {
		return initiationDate;
	}

	public void setInitiationDate(Date initiationDate) {
		this.initiationDate = initiationDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Boolean getIsDeemedApproved() {
		return isDeemedApproved;
	}

	public void setIsDeemedApproved(Boolean isDeemedApproved) {
		this.isDeemedApproved = isDeemedApproved;
	}

	public DocumentAttachment getSupportDocs() {
		return supportDocs;
	}

	public void setSupportDocs(DocumentAttachment supportDocs) {
		this.supportDocs = supportDocs;
	}

	 
}
