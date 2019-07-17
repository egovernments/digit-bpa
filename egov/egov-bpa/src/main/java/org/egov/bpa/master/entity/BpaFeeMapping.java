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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.egov.bpa.master.entity.enums.CalculationType;
import org.egov.bpa.master.entity.enums.FeeApplicationType;
import org.egov.bpa.master.entity.enums.FeeSubType;
import org.egov.infra.persistence.entity.AbstractAuditable;

@Entity
@Table(name = "EGBPA_MSTR_BPAFEEMAPPING")
@SequenceGenerator(name = BpaFeeMapping.SEQ_BPAFEEMAPPING, sequenceName = BpaFeeMapping.SEQ_BPAFEEMAPPING, allocationSize = 1)
public class BpaFeeMapping extends AbstractAuditable {

    private static final long serialVersionUID = 3078684328383202788L;
    public static final String SEQ_BPAFEEMAPPING = "SEQ_EGBPA_MSTR_BPAFEEMAPPING";
    @Id
    @GeneratedValue(generator = SEQ_BPAFEEMAPPING, strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "bpafeecommon")
    private BpaFeeCommon bpaFeeCommon;

    @Enumerated(EnumType.STRING)
    private CalculationType calculationType;

    @Enumerated(EnumType.STRING)
    private FeeApplicationType applicationType;

    @Enumerated(EnumType.STRING)
    private FeeSubType feeSubType;

    @ManyToOne
    @JoinColumn(name = "serviceType")
    private ServiceType serviceType;

    @ManyToOne
    @JoinColumn(name = "applicationSubType")
    private ApplicationSubType applicationSubType;

    @JoinColumn(name = "amount")
    private Double amount;

    private transient List<BpaFeeMapping> bpaFeeMapTemp = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CalculationType getCalculationType() {
        return calculationType;
    }

    public void setCalculationType(CalculationType calculationType) {
        this.calculationType = calculationType;
    }

    public FeeApplicationType getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(FeeApplicationType applicationType) {
        this.applicationType = applicationType;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public List<BpaFeeMapping> getBpaFeeMapTemp() {
        return bpaFeeMapTemp;
    }

    public void setBpaFeeMapTemp(List<BpaFeeMapping> bpaFeeMapTemp) {
        this.bpaFeeMapTemp = bpaFeeMapTemp;
    }

    public BpaFeeCommon getBpaFeeCommon() {
        return bpaFeeCommon;
    }

    public void setBpaFeeCommon(BpaFeeCommon bpaFeeCommon) {
        this.bpaFeeCommon = bpaFeeCommon;
    }

    public FeeSubType getFeeSubType() {
        return feeSubType;
    }

    public void setFeeSubType(FeeSubType feeSubType) {
        this.feeSubType = feeSubType;
    }

    public ApplicationSubType getApplicationSubType() {
        return applicationSubType;
    }

    public void setApplicationSubType(ApplicationSubType applicationSubType) {
        this.applicationSubType = applicationSubType;
    }
}