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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.egov.bpa.master.entity.ChecklistServiceTypeMapping;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.persistence.entity.AbstractAuditable;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "egbpa_inspection_common_files")
@SequenceGenerator(name = InspectionFilesCommon.SEQ_COMMON_DOCUMENT, sequenceName = InspectionFilesCommon.SEQ_COMMON_DOCUMENT, allocationSize = 1)
public class InspectionFilesCommon extends AbstractAuditable {

    public static final String SEQ_COMMON_DOCUMENT = "seq_egbpa_inspection_common_files";
    private static final long serialVersionUID = -7913915173561192911L;

    @Id
    @GeneratedValue(generator = SEQ_COMMON_DOCUMENT, strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull
    @JoinColumn(name = "inspectionid", nullable = false)
    private InspectionCommon inspection;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "servicechecklist", nullable = false)
    private ChecklistServiceTypeMapping serviceChecklist;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "egbpa_site_inspection_images", joinColumns = @JoinColumn(name = "inspectiondocument"), inverseJoinColumns = @JoinColumn(name = "filestoreid"))
    private Set<FileStoreMapper> images = Collections.emptySet();

    private transient MultipartFile[] files;
    private transient Map<Long, List<String>> encodedImages = new HashMap<>();

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

    public ChecklistServiceTypeMapping getServiceChecklist() {
        return serviceChecklist;
    }

    public void setServiceChecklist(ChecklistServiceTypeMapping serviceChecklist) {
        this.serviceChecklist = serviceChecklist;
    }

    public Set<FileStoreMapper> getImages() {
        return images;
    }

    public void setImages(Set<FileStoreMapper> images) {
        this.images = images;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

    public Map<Long, List<String>> getEncodedImages() {
        return encodedImages;
    }

    public void setEncodedImages(Map<Long, List<String>> encodedImages) {
        this.encodedImages = encodedImages;
    }

}
