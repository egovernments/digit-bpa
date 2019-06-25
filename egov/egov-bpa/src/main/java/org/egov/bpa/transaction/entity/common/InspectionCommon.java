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
import java.util.Date;
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
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.egov.infra.admin.master.entity.User;
import org.egov.infra.persistence.entity.AbstractAuditable;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "EGBPA_INSPECTION_COMMON")
@SequenceGenerator(name = InspectionCommon.SEQ_INSPECTION, sequenceName = InspectionCommon.SEQ_INSPECTION, allocationSize = 1)
public class InspectionCommon extends AbstractAuditable {

    public static final String SEQ_INSPECTION = "SEQ_EGBPA_INSPECTION_COMMON";
    private static final long serialVersionUID = -6537197288191260269L;
    public static final String ORDER_BY_ID_ASC = "id ASC";
    
    @Id
    @GeneratedValue(generator = SEQ_INSPECTION, strategy = GenerationType.SEQUENCE)
    private Long id;

    @Length(min = 1, max = 64)
    private String inspectionNumber;

    @Temporal(value = TemporalType.DATE)
    private Date inspectionDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent")
    private InspectionCommon parent;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inspectedby")
    private User inspectedBy;

    private Boolean isInspected;

    @Length(min = 1, max = 1000)
    private String inspectionRemarks;

    private boolean boundaryDrawingSubmitted;

    private boolean rightToMakeConstruction;

    @Length(min = 1, max = 128)
    private String typeofLand;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "inspection")
    private List<DocketCommon> docket = new ArrayList<>();

    @OneToMany(mappedBy = "inspection", cascade = CascadeType.ALL)
    @OrderBy(ORDER_BY_ID_ASC)
    private List<InspectionFilesCommon> inspectionSupportDocs = new ArrayList<>();

    @OneToMany(mappedBy = "inspection", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy(ORDER_BY_ID_ASC)
    private List<PlanScrutinyChecklistCommon> planScrutinyChecklistForRule = new ArrayList<>(0);

    @OneToMany(mappedBy = "inspection", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy(ORDER_BY_ID_ASC)
    private List<PlanScrutinyChecklistCommon> planScrutinyChecklistForDrawing = new ArrayList<>(0);

    private transient List<DocketDetailCommon> docketDetailLocList = new ArrayList<>();
    private transient List<DocketDetailCommon> docketDetailAccessList = new ArrayList<>();
    private transient List<DocketDetailCommon> docketDetailSurroundingPlotList = new ArrayList<>();
    private transient List<DocketDetailCommon> docketDetailLandTypeList = new ArrayList<>();
    private transient List<DocketDetailCommon> docketDetailProposedWorkList = new ArrayList<>();
    private transient List<DocketDetailCommon> docketDetailWorkAsPerPlanList = new ArrayList<>();
    private transient List<DocketDetailCommon> docketDetailHgtAbuttRoadList = new ArrayList<>();
    private transient List<DocketDetailCommon> docketDetailMeasurementList = new ArrayList<>();
    private transient List<DocketDetailCommon> docketDetailAreaLoc = new ArrayList<>();
    private transient List<DocketDetailCommon> docketDetailLengthOfCompWall = new ArrayList<>();
    private transient List<DocketDetailCommon> docketDetailNumberOfWell = new ArrayList<>();
    private transient List<DocketDetailCommon> docketDetailErectionTower = new ArrayList<>();
    private transient List<DocketDetailCommon> docketDetailShutter = new ArrayList<>();
    private transient List<DocketDetailCommon> docketDetailRoofConversion = new ArrayList<>();
    
    private transient List<PlanScrutinyChecklistCommon> planScrutinyChecklistForRuleTemp = new ArrayList<>(0);
    private transient List<PlanScrutinyChecklistCommon> planScrutinyChecklistForDrawingTemp = new ArrayList<>(0);

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getInspectionNumber() {
        return inspectionNumber;
    }

    public void setInspectionNumber(String inspectionNumber) {
        this.inspectionNumber = inspectionNumber;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public InspectionCommon getParent() {
        return parent;
    }

    public void setParent(InspectionCommon parent) {
        this.parent = parent;
    }

    public User getInspectedBy() {
        return inspectedBy;
    }

    public void setInspectedBy(User inspectedBy) {
        this.inspectedBy = inspectedBy;
    }

    public Boolean getInspected() {
        return isInspected;
    }

    public void setInspected(Boolean inspected) {
        isInspected = inspected;
    }

    public String getInspectionRemarks() {
        return inspectionRemarks;
    }

    public void setInspectionRemarks(String inspectionRemarks) {
        this.inspectionRemarks = inspectionRemarks;
    }

    public List<DocketCommon> getDocket() {
        return docket;
    }

    public void setDocket(List<DocketCommon> docket) {
        this.docket = docket;
    }

    public List<InspectionFilesCommon> getInspectionSupportDocs() {
        return inspectionSupportDocs;
    }

    public void setInspectionSupportDocs(List<InspectionFilesCommon> inspectionSupportDocs) {
        this.inspectionSupportDocs = inspectionSupportDocs;
    }

    public boolean isBoundaryDrawingSubmitted() {
        return boundaryDrawingSubmitted;
    }

    public void setBoundaryDrawingSubmitted(boolean boundaryDrawingSubmitted) {
        this.boundaryDrawingSubmitted = boundaryDrawingSubmitted;
    }

    public boolean isRightToMakeConstruction() {
        return rightToMakeConstruction;
    }

    public void setRightToMakeConstruction(boolean rightToMakeConstruction) {
        this.rightToMakeConstruction = rightToMakeConstruction;
    }

    public String getTypeofLand() {
        return typeofLand;
    }

    public void setTypeofLand(String typeofLand) {
        this.typeofLand = typeofLand;
    }

    public List<DocketDetailCommon> getDocketDetailLocList() {
        return docketDetailLocList;
    }

    public void setDocketDetailLocList(List<DocketDetailCommon> docketDetailLocList) {
        this.docketDetailLocList = docketDetailLocList;
    }

    public List<DocketDetailCommon> getDocketDetailAccessList() {
        return docketDetailAccessList;
    }

    public void setDocketDetailAccessList(List<DocketDetailCommon> docketDetailAccessList) {
        this.docketDetailAccessList = docketDetailAccessList;
    }

    public List<DocketDetailCommon> getDocketDetailSurroundingPlotList() {
        return docketDetailSurroundingPlotList;
    }

    public void setDocketDetailSurroundingPlotList(List<DocketDetailCommon> docketDetailSurroundingPlotList) {
        this.docketDetailSurroundingPlotList = docketDetailSurroundingPlotList;
    }

    public List<DocketDetailCommon> getDocketDetailLandTypeList() {
        return docketDetailLandTypeList;
    }

    public void setDocketDetailLandTypeList(List<DocketDetailCommon> docketDetailLandTypeList) {
        this.docketDetailLandTypeList = docketDetailLandTypeList;
    }

    public List<DocketDetailCommon> getDocketDetailProposedWorkList() {
        return docketDetailProposedWorkList;
    }

    public void setDocketDetailProposedWorkList(List<DocketDetailCommon> docketDetailProposedWorkList) {
        this.docketDetailProposedWorkList = docketDetailProposedWorkList;
    }

    public List<DocketDetailCommon> getDocketDetailWorkAsPerPlanList() {
        return docketDetailWorkAsPerPlanList;
    }

    public void setDocketDetailWorkAsPerPlanList(List<DocketDetailCommon> docketDetailWorkAsPerPlanList) {
        this.docketDetailWorkAsPerPlanList = docketDetailWorkAsPerPlanList;
    }

    public List<DocketDetailCommon> getDocketDetailHgtAbuttRoadList() {
        return docketDetailHgtAbuttRoadList;
    }

    public void setDocketDetailHgtAbuttRoadList(List<DocketDetailCommon> docketDetailHgtAbuttRoadList) {
        this.docketDetailHgtAbuttRoadList = docketDetailHgtAbuttRoadList;
    }

    public List<DocketDetailCommon> getDocketDetailMeasurementList() {
        return docketDetailMeasurementList;
    }

    public void setDocketDetailMeasurementList(List<DocketDetailCommon> docketDetailMeasurementList) {
        this.docketDetailMeasurementList = docketDetailMeasurementList;
    }

    public List<DocketDetailCommon> getDocketDetailAreaLoc() {
        return docketDetailAreaLoc;
    }

    public void setDocketDetailAreaLoc(List<DocketDetailCommon> docketDetailAreaLoc) {
        this.docketDetailAreaLoc = docketDetailAreaLoc;
    }

    public List<DocketDetailCommon> getDocketDetailLengthOfCompWall() {
        return docketDetailLengthOfCompWall;
    }

    public void setDocketDetailLengthOfCompWall(List<DocketDetailCommon> docketDetailLengthOfCompWall) {
        this.docketDetailLengthOfCompWall = docketDetailLengthOfCompWall;
    }

    public List<DocketDetailCommon> getDocketDetailNumberOfWell() {
        return docketDetailNumberOfWell;
    }

    public void setDocketDetailNumberOfWell(List<DocketDetailCommon> docketDetailNumberOfWell) {
        this.docketDetailNumberOfWell = docketDetailNumberOfWell;
    }

    public List<DocketDetailCommon> getDocketDetailErectionTower() {
        return docketDetailErectionTower;
    }

    public void setDocketDetailErectionTower(List<DocketDetailCommon> docketDetailErectionTower) {
        this.docketDetailErectionTower = docketDetailErectionTower;
    }

    public List<DocketDetailCommon> getDocketDetailShutter() {
        return docketDetailShutter;
    }

    public void setDocketDetailShutter(List<DocketDetailCommon> docketDetailShutter) {
        this.docketDetailShutter = docketDetailShutter;
    }

    public List<DocketDetailCommon> getDocketDetailRoofConversion() {
        return docketDetailRoofConversion;
    }

    public void setDocketDetailRoofConversion(List<DocketDetailCommon> docketDetailRoofConversion) {
        this.docketDetailRoofConversion = docketDetailRoofConversion;
    }

    public List<PlanScrutinyChecklistCommon> getPlanScrutinyChecklistForRule() {
        return planScrutinyChecklistForRule;
    }

    public void setPlanScrutinyChecklistForRule(List<PlanScrutinyChecklistCommon> planScrutinyChecklistForRule) {
        this.planScrutinyChecklistForRule = planScrutinyChecklistForRule;
    }

    public List<PlanScrutinyChecklistCommon> getPlanScrutinyChecklistForDrawing() {
        return planScrutinyChecklistForDrawing;
    }

    public void setPlanScrutinyChecklistForDrawing(List<PlanScrutinyChecklistCommon> planScrutinyChecklistForDrawing) {
        this.planScrutinyChecklistForDrawing = planScrutinyChecklistForDrawing;
    }

    public List<PlanScrutinyChecklistCommon> getPlanScrutinyChecklistForRuleTemp() {
        return planScrutinyChecklistForRuleTemp;
    }

    public void setPlanScrutinyChecklistForRuleTemp(List<PlanScrutinyChecklistCommon> planScrutinyChecklistForRuleTemp) {
        this.planScrutinyChecklistForRuleTemp = planScrutinyChecklistForRuleTemp;
    }

    public List<PlanScrutinyChecklistCommon> getPlanScrutinyChecklistForDrawingTemp() {
        return planScrutinyChecklistForDrawingTemp;
    }

    public void setPlanScrutinyChecklistForDrawingTemp(List<PlanScrutinyChecklistCommon> planScrutinyChecklistForDrawingTemp) {
        this.planScrutinyChecklistForDrawingTemp = planScrutinyChecklistForDrawingTemp;
    }

}