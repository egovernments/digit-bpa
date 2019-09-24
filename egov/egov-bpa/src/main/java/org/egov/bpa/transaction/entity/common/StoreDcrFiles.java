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
package org.egov.bpa.transaction.entity.common;

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

import org.egov.infra.filestore.entity.FileStoreMapper;

@Entity
@Table(name = "egbpa_dcr_document_files")
@SequenceGenerator(name = StoreDcrFiles.SEQ_CMN_DCR_FILES, sequenceName = StoreDcrFiles.SEQ_CMN_DCR_FILES, allocationSize = 1)
public class StoreDcrFiles {

    public static final String SEQ_CMN_DCR_FILES = "seq_egbpa_dcr_document_files";
    @Id
    @GeneratedValue(generator = SEQ_CMN_DCR_FILES, strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "dcrdocument", nullable = false)
    private DcrDocument dcrDocument;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "filestore", nullable = false)
    private FileStoreMapper fileStoreMapper;
    private boolean isAutoPopulated = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DcrDocument getDcrDocument() {
        return dcrDocument;
    }

    public void setDcrDocument(DcrDocument dcrDocument) {
        this.dcrDocument = dcrDocument;
    }

    public FileStoreMapper getFileStoreMapper() {
        return fileStoreMapper;
    }

    public void setFileStoreMapper(FileStoreMapper fileStoreMapper) {
        this.fileStoreMapper = fileStoreMapper;
    }

    public boolean isAutoPopulated() {
        return isAutoPopulated;
    }

    public void setAutoPopulated(boolean autoPopulated) {
        isAutoPopulated = autoPopulated;
    }
}
