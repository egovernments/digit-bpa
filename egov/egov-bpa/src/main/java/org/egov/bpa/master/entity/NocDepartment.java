package org.egov.bpa.master.entity;

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

import org.egov.infra.persistence.entity.AbstractAuditable;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Table(name = "EGBPA_MSTR_NOCDEPARTMENT")
@SequenceGenerator(name = NocDepartment.SEQ, sequenceName = NocDepartment.SEQ, allocationSize = 1)
public class NocDepartment extends AbstractAuditable {

	public static final String SEQ = "SEQ_EGBPA_MSTR_NOCDEPARTMENT";
	@Id
	@GeneratedValue(generator = SEQ, strategy = GenerationType.SEQUENCE)
	private Long id;
	@SafeHtml
	@Length(min = 2, max = 20)
	
	
	private String code;
	@Length(min = 2, max = 124)
	
	
	@SafeHtml
	private String name;
	
	
	private Boolean isActive;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy="nocDepartment")
	private List<NocConfig> nocConfigs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public List<NocConfig> getNocConfigs() {
		return nocConfigs;
	}

	public void setNocConfigs(List<NocConfig> nocConfigs) {
		this.nocConfigs = nocConfigs;
	}

}
