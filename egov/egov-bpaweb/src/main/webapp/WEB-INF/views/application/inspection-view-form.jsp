<%--
  ~ eGov suite of products aim to improve the internal efficiency,transparency,
  ~    accountability and the service delivery of the government  organizations.
  ~
  ~     Copyright (C) <2017>  eGovernments Foundation
  ~
  ~     The updated version of eGov suite of products as by eGovernments Foundation
  ~     is available at http://www.egovernments.org
  ~
  ~     This program is free software: you can redistribute it and/or modify
  ~     it under the terms of the GNU General Public License as published by
  ~     the Free Software Foundation, either version 3 of the License, or
  ~     any later version.
  ~
  ~     This program is distributed in the hope that it will be useful,
  ~     but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~     GNU General Public License for more details.
  ~
  ~     You should have received a copy of the GNU General Public License
  ~     along with this program. If not, see http://www.gnu.org/licenses/ or
  ~     http://www.gnu.org/licenses/gpl.html .
  ~
  ~     In addition to the terms of the GPL license to be adhered to in using this
  ~     program, the following additional terms are to be complied with:
  ~
  ~         1) All versions of this program, verbatim or modified must carry this
  ~            Legal Notice.
  ~
  ~         2) Any misrepresentation of the origin of the material is prohibited. It
  ~            is required that all modified versions of this material be marked in
  ~            reasonable ways as different from the original version.
  ~
  ~         3) This license does not grant any rights to any user of the program
  ~            with regards to rights under trademark law for use of the trade names
  ~            or trademarks of eGovernments Foundation.
  ~
  ~   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
  --%>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="row">
	<div class="col-md-12">
		<ul class="nav nav-tabs" id="settingstab">
			<li class="active"><a data-toggle="tab"
				href="#inspection-details" data-tabidx=0><spring:message
						code='lbl.inspn.details' /></a></li>
			<c:if
				test="${not empty permitInspection.inspection.planScrutinyChecklistForRule || not empty permitInspection.inspection.planScrutinyChecklistForDrawing}">
				<li><a data-toggle="tab" href="#plan-scrutiny" data-tabidx=1><spring:message
							code='lbl.plan.scrutiny' /></a></li>
			</c:if>
			<c:if
				test="${not empty permitInspection.inspection.getInspectionSupportDocs()}">
				<li><a data-toggle="tab" href="#site-images" data-tabidx=2><spring:message
							code='title.site.image' /></a></li>
			</c:if>
		</ul>
		<div class="tab-content">
			<div id="inspection-details" class="tab-pane fade in active">
				<div class="panel panel-primary" data-collapsed="0">
					<div class="panel-heading custom_form_panel_heading">
						<div class="panel-title">
							<spring:message code="lbl.basic.info" />
						</div>
					</div>
					<div class="panel-body">
						<%--<div class="row add-border">
                            <div class="col-sm-3 add-margin">Location of the Plot</div>
                            <div class="col-sm-3 add-margin view-content">
                                <c:out value="${docket[0].locationOfPlot}" default="N/A"></c:out>
                            </div>
                            <div class="col-sm-3 add-margin">Land Area</div>
                            <div class="col-sm-3 add-margin view-content">
                                <c:out value="${permitInspection.inspection.lndRegularizationArea}" default="N/A"></c:out>
                            </div>
                        </div>

                        <div class="row add-border">
                            <div class="col-sm-3 add-margin">Building Area</div>
                            <div class="col-sm-3 add-margin view-content">
                                <c:out value="${permitInspection.inspection.bldngBuildUpArea}" default="N/A"></c:out>
                            </div>
                            <div class="col-sm-3 add-margin">Coumpound Wall</div>
                            <div class="col-sm-3 add-margin view-content">
                                <c:out value="${permitInspection.inspection.bldngCompoundWall}" default="N/A"></c:out>
                            </div>
                        </div>
                        <div class="row add-border">
                            <div class="col-sm-3 add-margin">No of Wells</div>
                            <div class="col-sm-3 add-margin view-content">
                                <c:out value="${permitInspection.inspection.bldngWellOhtSumpTankArea}"
                                    default="N/A"></c:out>
                            </div>
                            <div class="col-sm-3 add-margin">Occupancy</div>
                            <div class="col-sm-3 add-margin view-content">N/A</div>
                        </div>
                        <div class="row add-border">
                            <div class="col-sm-3 add-margin">Extent in Sqmtr</div>
                            <div class="col-sm-3 add-margin view-content">
                                <c:out value="${permitInspection.inspection.lndMinPlotExtent}" default="N/A"></c:out>
                            </div>
                        </div>--%>
						<%-- <div class="row add-border">
                             <div class="col-sm-3 add-margin">
                                 <spring:message code="lbl.dimensionofplot"/>
                             </div>
                             <div class="col-sm-3 add-margin view-content">
                                 <c:out value="${permitInspection.inspection.boundaryDrawingSubmitted ? 'Yes' : 'No'}"
                                        default="N/A"></c:out>
                             </div>
                             <div class="col-sm-3 add-margin">
                                 <spring:message code="lbl.typeofland" />
                             </div>
                             <div class="col-sm-3 add-margin view-content">
                                 <c:out value="${permitInspection.inspection.typeofLand}" default="N/A"></c:out>
                             </div>
                         </div>--%>
						<div class="row add-border">
							<div class="col-sm-3 add-margin">
								<spring:message code="lbl.inspn.no" />
							</div>
							<div class="col-sm-2 add-margin view-content">
								<c:out value="${permitInspection.inspection.inspectionNumber}"
									default="N/A"></c:out>
							</div>
							<div class="col-sm-2 add-margin">
								<spring:message code="lbl.inspn.date" />
							</div>
							<div class="col-sm-5 add-margin view-content">
								<fmt:formatDate
									value="${permitInspection.inspection.inspectionDate}"
									pattern="dd/MM/yyyy" var="inspectionDate" />
								<c:out value="${inspectionDate}" default="N/A" />
								</td>
							</div>
						</div>
						<div class="row add-border">
							<div class="col-sm-3 add-margin">
								<spring:message code="lbl.righttomake.construction" />
							</div>
							<div class="col-sm-2 add-margin view-content">
								<c:out
									value="${permitInspection.inspection.rightToMakeConstruction ? 'Yes' : 'No'}"
									default="N/A"></c:out>
							</div>
							<div class="col-sm-2 add-margin">
								<spring:message code="lbl.ins.remarks" />
							</div>
							<div class="col-sm-5 add-margin view-content text-justify">
								<c:out value="${permitInspection.inspection.inspectionRemarks}"
									default="N/A"></c:out>
							</div>
						</div>
					</div>
				</div>
				<div class="panel panel-primary" data-collapsed="0">
					<div class="panel-body">
						<div class="panel-heading custom_form_panel_heading">
							<div class="panel-title">
								<spring:message code="lbl.inpn.chklst.dtl" />
							</div>
						</div>
						<div class="row add-border">
							<div class="col-sm-5 add-margin view-content bg-info">
								<div class="panel-title text-info">
									<spring:message code="lbl.description" />
								</div>
							</div>
							<div class="col-sm-2 add-margin view-content bg-info">
								<div class="panel-title text-info">
									<spring:message code="lbl.is.applicable" />
								</div>
							</div>
							<div class="col-sm-5 add-margin view-content bg-info">
								<div class="panel-title">
									<spring:message code="lbl.remarks" />
								</div>
							</div>
						</div>
						<c:if test="${not empty permitInspection.inspection.docket}">
							<c:forEach var="doc" items="${permitInspection.inspection.docket}" varStatus="status">
								<div class="panel-heading custom_form_panel_heading">
									<div class="panel-title">
										<c:out value="${doc.checklistType.description}" />
									</div>
								</div>
								<div class="panel-body">
									<c:forEach items="${doc.docketDetail}" var="docs"
										varStatus="counter">
										<div class="row add-border">
											<div class="col-sm-5 add-margin view-content">
												<c:out value="${docs.serviceChecklist.checklist.description}"
													default="N/A"></c:out>
											</div>
											<div class="col-sm-2 add-margin view-content">
												<c:out value="${docs.value.checkListVal}" default="N/A"></c:out>
											</div>
											<div class="col-sm-5 add-margin view-content text-justify">
												<c:out value="${docs.remarks}" default="N/A"></c:out>
											</div>
										</div>
									</c:forEach>
								</div>
							</c:forEach>
						</c:if>
						<c:if test="${not empty docketDetailMeasurementList}">
							<div class="panel-heading custom_form_panel_heading">
								<div class="panel-title">
									<c:out value="${docketDetailMeasurementList[0].serviceChecklist.checklist.checklistType.description}" />
								</div>
							</div>
							<div class="panel-body">
								<c:forEach items="${docketDetailMeasurementList}" var="doc"
									varStatus="counter">
									<div class="row add-border">
										<div class="col-sm-5 add-margin view-content">
											<c:out value="${doc.serviceChecklist.checklist.description}"
												default="N/A"></c:out>
										</div>
										<div class="col-sm-2 add-margin view-content">
											<c:out value="${doc.value.checkListVal}" default="N/A"></c:out>
										</div>
										<div class="col-sm-5 add-margin view-content text-justify">
											<c:out value="${doc.remarks}" default="N/A"></c:out>
										</div>
									</div>
								</c:forEach>
							</div>
						</c:if>
						<c:if test="${not empty docketDetailAccessList}">
							<div class="panel-heading custom_form_panel_heading">
								<div class="panel-title">
									<spring:message code="lbl.access.to.plot" />
								</div>
							</div>
							<div class="panel-body">
								<c:forEach items="${docketDetailAccessList}" var="doc"
									varStatus="counter">
									<div class="row add-border">
										<div class="col-sm-5 add-margin view-content">
											<c:out value="${doc.serviceChecklist.checklist.description}"
												default="N/A"></c:out>
										</div>
										<div class="col-sm-2 add-margin view-content">
											<c:out value="${doc.value.checkListVal}" default="N/A"></c:out>
										</div>
										<div class="col-sm-5 add-margin view-content text-justify">
											<c:out value="${doc.remarks}" default="N/A"></c:out>
										</div>
									</div>
								</c:forEach>
							</div>
						</c:if>
						<c:if test="${not empty docketDetailSurroundingPlotList}">
							<div class="panel-heading custom_form_panel_heading">
								<div class="panel-title">
									<c:out value="${docketDetailSurroundingPlotList[0].serviceChecklist.checklist.checklistType.description}" />
								</div>
							</div>
							<div class="panel-body">
								<c:forEach items="${docketDetailSurroundingPlotList}" var="doc"
									varStatus="counter">
									<div class="row add-border">
										<div class="col-sm-5 add-margin view-content">
											<c:out value="${doc.serviceChecklist.checklist.description}"
												default="N/A"></c:out>
										</div>
										<div class="col-sm-2 add-margin view-content">
											<c:out value="${doc.value.checkListVal}" default="N/A"></c:out>
										</div>
										<div class="col-sm-5 add-margin view-content text-justify">
											<c:out value="${doc.remarks}" default="N/A"></c:out>
										</div>
									</div>
								</c:forEach>
							</div>
						</c:if>
						<c:if test="${not empty docketDetailLandTypeList}">
							<div class="panel-heading custom_form_panel_heading">
								<div class="panel-title">
									<c:out value="${docketDetailLandTypeList[0].serviceChecklist.checklist.checklistType.description}" />
								</div>
							</div>
							<div class="panel-body">
								<c:forEach items="${docketDetailLandTypeList}" var="doc"
									varStatus="counter">
									<div class="row add-border">
										<div class="col-sm-5 add-margin view-content">
											<c:out value="${doc.serviceChecklist.checklist.description}"
												default="N/A"></c:out>
										</div>
										<div class="col-sm-2 add-margin view-content">
											<c:out value="${doc.value.checkListVal}" default="N/A"></c:out>
										</div>
										<div class="col-sm-5 add-margin view-content text-justify">
											<c:out value="${doc.remarks}" default="N/A"></c:out>
										</div>
									</div>
								</c:forEach>
							</div>
						</c:if>
						<c:if test="${not empty docketDetailProposedWorkList}">
							<div class="panel-heading custom_form_panel_heading">
								<div class="panel-title">
									<c:out value="${docketDetailProposedWorkList[0].serviceChecklist.checklist.checklistType.description}" />
								</div>
							</div>
							<div class="panel-body">
								<c:forEach items="${docketDetailProposedWorkList}" var="doc"
									varStatus="counter">
									<div class="row add-border">
										<div class="col-sm-5 add-margin view-content">
											<c:out value="${doc.serviceChecklist.checklist.description}"
												default="N/A"></c:out>
										</div>
										<div class="col-sm-2 add-margin view-content">
											<c:out value="${doc.value.checkListVal}" default="N/A"></c:out>
										</div>
										<div class="col-sm-5 add-margin view-content text-justify">
											<c:out value="${doc.remarks}" default="N/A"></c:out>
										</div>
									</div>
								</c:forEach>
							</div>
						</c:if>
						<c:if test="${not empty docketDetailWorkAsPerPlanList}">
							<div class="panel-heading custom_form_panel_heading">
								<div class="panel-title">
									<c:out value="${docketDetailWorkAsPerPlanList[0].serviceChecklist.checklist.checklistType.description}" />
								</div>
							</div>
							<div class="panel-body">
								<c:forEach items="${docketDetailWorkAsPerPlanList}" var="doc"
									varStatus="counter">
									<div class="row add-border">
										<div class="col-sm-5 add-margin view-content">
											<c:out value="${doc.serviceChecklist.checklist.description}"
												default="N/A"></c:out>
										</div>
										<div class="col-sm-2 add-margin view-content">
											<c:out value="${doc.value.checkListVal}" default="N/A"></c:out>
										</div>
										<div class="col-sm-5 add-margin view-content text-justify">
											<c:out value="${doc.remarks}" default="N/A"></c:out>
										</div>
									</div>
								</c:forEach>
							</div>
						</c:if>
						<c:if test="${not empty docketDetailHgtAbuttRoadList}">
							<div class="panel-heading custom_form_panel_heading">
								<div class="panel-title">
									<c:out value="${docketDetailHgtAbuttRoadList[0].serviceChecklist.checklist.checklistType.description}" />
								</div>
							</div>
							<div class="panel-body">
								<c:forEach items="${docketDetailHgtAbuttRoadList}" var="doc"
									varStatus="counter">
									<div class="row add-border">
										<div class="col-sm-5 add-margin view-content">
											<c:out value="${doc.serviceChecklist.checklist.description}"
												default="N/A"></c:out>
										</div>
										<div class="col-sm-2 add-margin view-content">
											<c:out value="${doc.value.checkListVal}" default="N/A"></c:out>
										</div>
										<div class="col-sm-5 add-margin view-content text-justify">
											<c:out value="${doc.remarks}" default="N/A"></c:out>
										</div>
									</div>
								</c:forEach>
							</div>
						</c:if>
						<c:if test="${not empty docketDetailAreaLoc}">
							<div class="panel-heading custom_form_panel_heading">
								<div class="panel-title">
									<c:out value="${docketDetailAreaLoc[0].serviceChecklist.checklist.checklistType.description}" />
								</div>
							</div>
							<div class="panel-body">
								<c:forEach items="${docketDetailAreaLoc}" var="doc"
									varStatus="counter">
									<div class="row add-border">
										<div class="col-sm-5 add-margin view-content">
											<c:out value="${doc.serviceChecklist.checklist.description}"
												default="N/A"></c:out>
										</div>
										<div class="col-sm-2 add-margin view-content">
											<c:out value="${doc.value.checkListVal}" default="N/A"></c:out>
										</div>
										<div class="col-sm-5 add-margin view-content text-justify">
											<c:out value="${doc.remarks}" default="N/A"></c:out>
										</div>
									</div>
								</c:forEach>
							</div>
						</c:if>
						<c:if test="${not empty docketDetailLengthOfCompWall}">
							<div class="panel-heading custom_form_panel_heading">
								<div class="panel-title">
									<c:out value="${docketDetailLengthOfCompWall[0].serviceChecklist.checklist.checklistType.description}" />
								</div>
							</div>
							<div class="panel-body">
								<c:forEach items="${docketDetailLengthOfCompWall}" var="doc"
									varStatus="counter">
									<div class="row add-border">
										<div class="col-sm-5 add-margin view-content">
											<c:out value="${doc.serviceChecklist.checklist.description}"
												default="N/A"></c:out>
										</div>
										<div class="col-sm-2 add-margin view-content">
											<c:out value="${doc.value.checkListVal}" default="N/A"></c:out>
										</div>
										<div class="col-sm-5 add-margin view-content text-justify">
											<c:out value="${doc.remarks}" default="N/A"></c:out>
										</div>
									</div>
								</c:forEach>
							</div>
						</c:if>
						<c:if test="${not empty docketDetailNumberOfWell}">
							<div class="panel-heading custom_form_panel_heading">
								<div class="panel-title">
									<c:out value="${docketDetailNumberOfWell[0].serviceChecklist.checklist.checklistType.description}" />
								</div>
							</div>
							<div class="panel-body">
								<c:forEach items="${docketDetailNumberOfWell}" var="doc"
									varStatus="counter">
									<div class="row add-border">
										<div class="col-sm-5 add-margin view-content">
											<c:out value="${doc.serviceChecklist.checklist.description}"
												default="N/A"></c:out>
										</div>
										<div class="col-sm-2 add-margin view-content">
											<c:out value="${doc.value.checkListVal}" default="N/A"></c:out>
										</div>
										<div class="col-sm-5 add-margin view-content text-justify">
											<c:out value="${doc.remarks}" default="N/A"></c:out>
										</div>
									</div>
								</c:forEach>
							</div>
						</c:if>
						<c:if test="${not empty docketDetailErectionTower}">
							<div class="panel-heading custom_form_panel_heading">
								<div class="panel-title">
									<c:out value="${docketDetailErectionTower[0].serviceChecklist.checklist.checklistType.description}" />
								</div>
							</div>
							<div class="panel-body">
								<c:forEach items="${docketDetailErectionTower}" var="doc"
									varStatus="counter">
									<div class="row add-border">
										<div class="col-sm-5 add-margin view-content">
											<c:out value="${doc.serviceChecklist.checklist.description}"
												default="N/A"></c:out>
										</div>
										<div class="col-sm-2 add-margin view-content">
											<c:out value="${doc.value.checkListVal}" default="N/A"></c:out>
										</div>
										<div class="col-sm-5 add-margin view-content text-justify">
											<c:out value="${doc.remarks}" default="N/A"></c:out>
										</div>
									</div>
								</c:forEach>
							</div>
						</c:if>
						<c:if test="${not empty docketDetailShutter}">
							<div class="panel-heading custom_form_panel_heading">
								<div class="panel-title">
									<c:out value="${docketDetailShutter[0].serviceChecklist.checklist.checklistType.description}" />
								</div>
							</div>
							<div class="panel-body">
								<c:forEach items="${docketDetailShutter}" var="doc"
									varStatus="counter">
									<div class="row add-border">
										<div class="col-sm-5 add-margin view-content">
											<c:out value="${doc.serviceChecklist.checklist.description}"
												default="N/A"></c:out>
										</div>
										<div class="col-sm-2 add-margin view-content">
											<c:out value="${doc.value.checkListVal}" default="N/A"></c:out>
										</div>
										<div class="col-sm-5 add-margin view-content text-justify">
											<c:out value="${doc.remarks}" default="N/A"></c:out>
										</div>
									</div>
								</c:forEach>
							</div>
						</c:if>
						<c:if test="${not empty docketDetailRoofConversion}">
							<div class="panel-heading custom_form_panel_heading">
								<div class="panel-title">
									<c:out value="${docketDetailRoofConversion[0].serviceChecklist.checklist.checklistType.description}" />
								</div>
							</div>
							<div class="panel-body">
								<c:forEach items="${docketDetailRoofConversion}" var="doc"
									varStatus="counter">

									<div class="row add-border">
										<div class="col-sm-5 add-margin view-content">
											<c:out value="${doc.serviceChecklist.checklist.description}"
												default="N/A"></c:out>
										</div>
										<div class="col-sm-2 add-margin view-content">
											<c:out value="${doc.value.checkListVal}" default="N/A"></c:out>
										</div>

										<div class="col-sm-5 add-margin view-content text-justify">
											<c:out value="${doc.remarks}" default="N/A"></c:out>
										</div>
									</div>
								</c:forEach>
							</div>
						</c:if>
					</div>
				</div>
			</div>
			<div id="plan-scrutiny" class="tab-pane fade">
				<c:if
				test="${not empty permitInspection.inspection.planScrutinyChecklistForRule}">
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="view-plan-scrutiny-checklist-rule.jsp"></jsp:include>
					</div>
				</c:if>
				<c:if
				test="${not empty permitInspection.inspection.planScrutinyChecklistForDrawing}">
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="view-plan-scrutiny-checklist-drawing.jsp"></jsp:include>
					</div>
				</c:if>
			</div>
			<c:if
				test="${not empty permitInspection.inspection.getInspectionSupportDocs()}">
				<div id="site-images" class="tab-pane fade">
					<div class="panel panel-primary" data-collapsed="0">
						<div class="panel-body custom-form ">
						<c:set var="inspectionSupportDocs" value="${permitInspection.inspection.getInspectionSupportDocs()}" scope="request"></c:set>
							<jsp:include page="view-inspection-documents.jsp"></jsp:include>
						</div>
					</div>
				</div>
			</c:if>
		</div>
	</div>
</div>
<div align="center">
	<input type="button" name="button2" id="button2" value="Close"
		class="btn btn-default" onclick="window.close();" />
</div>
