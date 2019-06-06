/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.bpa.transaction.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.egov.bpa.transaction.entity.common.NocDocument;
import org.egov.infra.persistence.entity.AbstractAuditable;
import org.egov.infra.persistence.entity.CorrespondenceAddress;

@Entity
@Table(name = "egbpa_permit_noc_document")
@SequenceGenerator(name = PermitNocDocument.SEQ_EGBPA_NOC_DOCUMENT, sequenceName = PermitNocDocument.SEQ_EGBPA_NOC_DOCUMENT, allocationSize = 1)
public class PermitNocDocument extends AbstractAuditable {

    public static final String SEQ_EGBPA_NOC_DOCUMENT = "seq_egbpa_permit_noc_document";
    private static final long serialVersionUID = -5625450672698620264L;

    @Id
    @GeneratedValue(generator = SEQ_EGBPA_NOC_DOCUMENT, strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull
    @JoinColumn(name = "application", nullable = false)
    private BpaApplication application;
    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull
    @JoinColumn(name = "nocDocument", nullable = false)
    private NocDocument nocDocument;
    
    private transient PermitNocApplication permitNoc = new PermitNocApplication();


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

    public NocDocument getNocDocument() {
        return nocDocument;
    }

    public void setNocDocument(NocDocument nocDocument) {
        this.nocDocument = nocDocument;
    }

	public PermitNocApplication getPermitNoc() {
		return permitNoc;
	}

	public void setPermitNoc(PermitNocApplication permitNoc) {
		this.permitNoc = permitNoc;
	}

}