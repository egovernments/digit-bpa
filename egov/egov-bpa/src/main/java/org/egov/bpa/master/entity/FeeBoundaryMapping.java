/**
 * eGov suite of products aim to improve the internal efficiency,transparency,
   accountability and the service delivery of the government  organizations.
    Copyright (C) <2015>  eGovernments Foundation
    The updated version of eGov suite of products as by eGovernments Foundation
    is available at http://www.egovernments.org
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program. If not, see http://www.gnu.org/licenses/ or
    http://www.gnu.org/licenses/gpl.html .
    In addition to the terms of the GPL license to be adhered to in using this
    program, the following additional terms are to be complied with:
        1) All versions of this program, verbatim or modified must carry this
           Legal Notice.
        2) Any misrepresentation of the origin of the material is prohibited. It
           is required that all modified versions of this material be marked in
           reasonable ways as different from the original version.
        3) This license does not grant any rights to any user of the program
           with regards to rights under trademark law for use of the trade names
           or trademarks of eGovernments Foundation.
  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.bpa.master.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.persistence.entity.AbstractAuditable;

@Entity
@Table(name = "EGBPA_FEEBOUNDARY_MAPPING")
@SequenceGenerator(name = FeeBoundaryMapping.SEQ_BPAFEEBOUNDARYMAPPING, sequenceName = FeeBoundaryMapping.SEQ_BPAFEEBOUNDARYMAPPING, allocationSize = 1)
public class FeeBoundaryMapping extends AbstractAuditable {

    private static final long serialVersionUID = 3078684328383202798L;
    public static final String SEQ_BPAFEEBOUNDARYMAPPING = "SEQ_EGBPA_FEEBOUNDARY_MAPPING";
    @Id
    @GeneratedValue(generator = SEQ_BPAFEEBOUNDARYMAPPING, strategy = GenerationType.SEQUENCE)
    private Long id;
   
    
    @NotNull
    @JoinColumn(name = "boundary")
    private Long boundaryId;
    @Transient
    private Boundary boundary; 
    

    public Long getBoundaryId() {
		return boundaryId;
	}

	public void setBoundaryId(Long boundaryId) {
		this.boundaryId = boundaryId;
	}

	@ManyToOne
    @JoinColumn(name = "bpafeemapping")
    private BpaFeeMapping bpaFeeMapping;
    
    @JoinColumn(name = "amount")
    private Double amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boundary getBoundary() {
		return boundary;
	}

	public void setBoundary(Boundary boundary) {
		this.boundary = boundary;
	}

	public BpaFeeMapping getBpaFeeMapping() {
		return bpaFeeMapping;
	}

	public void setBpaFeeMapping(BpaFeeMapping bpaFeeMapping) {
		this.bpaFeeMapping = bpaFeeMapping;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}      
	
}