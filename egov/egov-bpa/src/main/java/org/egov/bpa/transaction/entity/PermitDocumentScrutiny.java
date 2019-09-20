package org.egov.bpa.transaction.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;

import org.egov.bpa.transaction.entity.common.DocumentScrutiny;
import org.egov.infra.persistence.entity.AbstractAuditable;

@Entity
@Table(name = "EGBPA_PERMIT_DOCUMENT_SCRUTINY")
@SequenceGenerator(name = PermitDocumentScrutiny.SEQ_EGBPA_DOCUMENTSCRUTINY, sequenceName = PermitDocumentScrutiny.SEQ_EGBPA_DOCUMENTSCRUTINY, allocationSize = 1)
public class PermitDocumentScrutiny extends AbstractAuditable {
	private static final long serialVersionUID = 1L;
	public static final String SEQ_EGBPA_DOCUMENTSCRUTINY = "SEQ_EGBPA_PERMIT_DOCUMENT_SCRUTINY";
	@Id
	@GeneratedValue(generator = SEQ_EGBPA_DOCUMENTSCRUTINY, strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "application", nullable = false)
	private BpaApplication application;
	
	@Valid
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "documentScrutiny", nullable = false)
	private DocumentScrutiny docScrutiny;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(final Long id) {
		this.id = id;
	}

	public BpaApplication getApplication() {
		return application;
	}

	public void setApplication(BpaApplication application) {
		this.application = application;
	}

	public DocumentScrutiny getDocScrutiny() {
		return docScrutiny;
	}

	public void setDocScrutiny(DocumentScrutiny docScrutiny) {
		this.docScrutiny = docScrutiny;
	}

}
