package org.egov.bpa.transaction.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "EGBPA_NOCLIST")
@SequenceGenerator(name = NocList.SEQ, sequenceName = NocList.SEQ, allocationSize = 1)
public class NocList {
	public static final String SEQ = "SEQ_EGBPA_NOCLIST";
	@Id
	@GeneratedValue(generator = SEQ, strategy = GenerationType.SEQUENCE)
	private Long id;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "nocList", targetEntity = NoObjectionCertificate.class)
	private List<NoObjectionCertificate> nocList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<NoObjectionCertificate> getNocList() {
		return nocList;
	}

	public void setNocList(List<NoObjectionCertificate> nocList) {
		this.nocList = nocList;
	}

	public static String getSeq() {
		return SEQ;
	}

}
