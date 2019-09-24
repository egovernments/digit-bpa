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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;

import org.egov.common.entity.bpa.ChecklistType;
import org.egov.infra.persistence.entity.AbstractAuditable;

@Entity
@Table(name = "EGBPA_DOCKET_COMMON")
@SequenceGenerator(name = DocketCommon.SEQ_DOCKET, sequenceName = DocketCommon.SEQ_DOCKET, allocationSize = 1)
public class DocketCommon extends AbstractAuditable {

    public static final String SEQ_DOCKET = "SEQ_EGBPA_DOCKET_COMMON";
    private static final long serialVersionUID = 680731317252397771L;

    @Id
    @GeneratedValue(generator = SEQ_DOCKET, strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "inspection")
    private InspectionCommon inspection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklisttype")
    private ChecklistType checklistType;

    @Valid
    @OneToMany(mappedBy = "docket", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DocketConstructionStageCommon> docketConstructionStage = new ArrayList<>(0);

    @Valid
    @OneToMany(mappedBy = "docket", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DocketDetailCommon> docketDetail = new ArrayList<>(0);

    public DocketCommon() {
        // invariant
    }

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

    public List<DocketConstructionStageCommon> getDocketConstructionStage() {
        return docketConstructionStage;
    }

    public void setDocketConstructionStage(List<DocketConstructionStageCommon> docketConstructionStage) {
        this.docketConstructionStage = docketConstructionStage;
    }

    public List<DocketDetailCommon> getDocketDetail() {
        return docketDetail;
    }

    public void setDocketDetail(List<DocketDetailCommon> docketDetail) {
        this.docketDetail = docketDetail;
    }

    public ChecklistType getChecklistType() {
        return checklistType;
    }

    public void setChecklistType(ChecklistType checklistType) {
        this.checklistType = checklistType;
    }
}