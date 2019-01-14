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

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
    <div class="col-md-12">
        <ul class="nav nav-tabs" id="settingstab">
            <li class="active"><a data-toggle="tab" href="#inspection-details"
                                  data-tabidx=0><spring:message code='lbl.inspn.details'/></a></li>
            <li><a data-toggle="tab" href="#plan-scrutiny" data-tabidx=1><spring:message
                    code='lbl.plan.scrutiny'/></a></li>
        </ul>
        <div class="tab-content">
            <div id="inspection-details" class="tab-pane fade in active">
                <div class="panel panel-primary" data-collapsed="0">
                    <div class="panel-heading custom_form_panel_heading">
                        <div class="panel-title"><spring:message code="lbl.basic.info"/></div>
                    </div>
                    <div class="panel-body">
                        <%--<div class="row add-border">
                            <div class="col-sm-3 add-margin">Location of the Plot</div>
                            <div class="col-sm-3 add-margin view-content">
                                <c:out value="${docket[0].locationOfPlot}" default="N/A"></c:out>
                            </div>
                            <div class="col-sm-3 add-margin">Land Area</div>
                            <div class="col-sm-3 add-margin view-content">
                                <c:out value="${inspection.lndRegularizationArea}" default="N/A"></c:out>
                            </div>
                        </div>

                        <div class="row add-border">
                            <div class="col-sm-3 add-margin">Building Area</div>
                            <div class="col-sm-3 add-margin view-content">
                                <c:out value="${inspection.bldngBuildUpArea}" default="N/A"></c:out>
                            </div>
                            <div class="col-sm-3 add-margin">Coumpound Wall</div>
                            <div class="col-sm-3 add-margin view-content">
                                <c:out value="${inspection.bldngCompoundWall}" default="N/A"></c:out>
                            </div>
                        </div>
                        <div class="row add-border">
                            <div class="col-sm-3 add-margin">No of Wells</div>
                            <div class="col-sm-3 add-margin view-content">
                                <c:out value="${inspection.bldngWellOhtSumpTankArea}"
                                    default="N/A"></c:out>
                            </div>
                            <div class="col-sm-3 add-margin">Occupancy</div>
                            <div class="col-sm-3 add-margin view-content">N/A</div>
                        </div>
                        <div class="row add-border">
                            <div class="col-sm-3 add-margin">Extent in Sqmtr</div>
                            <div class="col-sm-3 add-margin view-content">
                                <c:out value="${inspection.lndMinPlotExtent}" default="N/A"></c:out>
                            </div>
                        </div>--%>
                        <%-- <div class="row add-border">
                             <div class="col-sm-3 add-margin">
                                 <spring:message code="lbl.dimensionofplot"/>
                             </div>
                             <div class="col-sm-3 add-margin view-content">
                                 <c:out value="${inspection.boundaryDrawingSubmitted ? 'Yes' : 'No'}"
                                        default="N/A"></c:out>
                             </div>
                             <div class="col-sm-3 add-margin">
                                 <spring:message code="lbl.typeofland" />
                             </div>
                             <div class="col-sm-3 add-margin view-content">
                                 <c:out value="${inspection.typeofLand}" default="N/A"></c:out>
                             </div>
                         </div>--%>
                        <div class="row add-border">
                            <div class="col-sm-3 add-margin">
                                <spring:message code="lbl.inspn.no"/>
                            </div>
                            <div class="col-sm-2 add-margin view-content">
                                <c:out value="${inspection.inspectionNumber}"
                                       default="N/A"></c:out>
                            </div>
                            <div class="col-sm-2 add-margin">
                                <spring:message code="lbl.inspn.date"/>
                            </div>
                            <div class="col-sm-5 add-margin view-content">
                                <fmt:formatDate value="${inspection.inspectionDate}" pattern="dd/MM/yyyy"
                                                var="inspectionDate"/>
                                <c:out value="${inspectionDate}" default="N/A"/></td>
                            </div>
                        </div>
                        <div class="row add-border">
                            <div class="col-sm-3 add-margin">
                                <spring:message code="lbl.righttomake.construction"/>
                            </div>
                            <div class="col-sm-2 add-margin view-content">
                                <c:out value="${inspection.rightToMakeConstruction ? 'Yes' : 'No'}"
                                       default="N/A"></c:out>
                            </div>
                            <div class="col-sm-2 add-margin">
                                <spring:message code="lbl.ins.remarks"/>
                            </div>
                            <div class="col-sm-5 add-margin view-content text-justify">
                                <c:out value="${inspection.inspectionRemarks}" default="N/A"></c:out>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel panel-primary" data-collapsed="0">
                    <div class="panel-body">
                        <div class="panel-heading custom_form_panel_heading">
                            <div class="panel-title"><spring:message code="lbl.inpn.chklst.dtl"/></div>
                        </div>
                        <div class="row add-border">
                            <div class="col-sm-5 add-margin view-content bg-info">
                                <div class="panel-title text-info"><spring:message code="lbl.description"/></div>
                            </div>
                            <div class="col-sm-2 add-margin view-content bg-info">
                                <div class="panel-title text-info"><spring:message code="lbl.doc.provided"/></div>
                            </div>
                            <div class="col-sm-5 add-margin view-content bg-info">
                                <div class="panel-title"><spring:message code="lbl.remarks"/></div>
                            </div>
                        </div>
                        <c:choose>
                            <c:when test="${!docketDetailLocList.isEmpty()}">
                                <div class="panel-heading custom_form_panel_heading">
                                    <div class="panel-title"><spring:message code="lbl.loc.of.plot"/></div>
                                </div>
                                <div class="panel-body">
                                    <c:forEach items="${docketDetailLocList}" var="doc"
                                               varStatus="counter">
                                        <div class="row add-border">
                                            <div class="col-sm-5 add-margin view-content">
                                                <c:out value="${doc.checkListDetail.description}" default="N/A"></c:out>
                                            </div>
                                            <div class="col-sm-2 add-margin view-content">
                                                <c:out value="${doc.value.checkListVal}"
                                                       default="N/A"></c:out>
                                            </div>
                                            <div class="col-sm-5 add-margin view-content text-justify">
                                                <c:out value="${doc.remarks}" default="N/A"></c:out>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </c:when>
                        </c:choose>
                        <div class="panel-heading custom_form_panel_heading">
                            <div class="panel-title"><spring:message code="lbl.meas.plot"/></div>
                        </div>
                        <div class="panel-body">
                            <c:forEach items="${docketDetailMeasumentList}" var="doc"
                                       varStatus="counter">
                                <div class="row add-border">
                                    <div class="col-sm-5 add-margin view-content">
                                        <c:out value="${doc.checkListDetail.description}" default="N/A"></c:out>
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

                        <div class="panel-heading custom_form_panel_heading">
                            <div class="panel-title"><spring:message code="lbl.access.to.plot"/></div>
                        </div>
                        <div class="panel-body">
                            <c:forEach items="${docketDetailAccessList}" var="doc"
                                       varStatus="counter">
                                <div class="row add-border">
                                    <div class="col-sm-5 add-margin view-content">
                                        <c:out value="${doc.checkListDetail.description}" default="N/A"></c:out>
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
                        <div class="panel-heading custom_form_panel_heading">
                            <div class="panel-title"><spring:message code="lbl.surrnd.plot"/></div>
                        </div>
                        <div class="panel-body">
                            <c:forEach items="${docketDetlSurroundingPlotList}" var="doc"
                                       varStatus="counter">
                                <div class="row add-border">
                                    <div class="col-sm-5 add-margin view-content">
                                        <c:out value="${doc.checkListDetail.description}" default="N/A"></c:out>
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

                        <div class="panel-heading custom_form_panel_heading">
                            <div class="panel-title"><spring:message code="lbl.type.land"/></div>
                        </div>
                        <div class="panel-body">
                            <c:forEach items="${docketDetailLandTypeList}" var="doc"
                                       varStatus="counter">
                                <div class="row add-border">
                                    <div class="col-sm-5 add-margin view-content">
                                        <c:out value="${doc.checkListDetail.description}" default="N/A"></c:out>
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

                        <div class="panel-heading custom_form_panel_heading">
                            <div class="panel-title"><spring:message code="lbl.stage.propsd.work"/></div>
                        </div>
                        <div class="panel-body">
                            <c:forEach items="${docketDetailProposedWorkList}" var="doc"
                                       varStatus="counter">
                                <div class="row add-border">
                                    <div class="col-sm-5 add-margin view-content">
                                        <c:out value="${doc.checkListDetail.description}" default="N/A"></c:out>
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

                        <div class="panel-heading custom_form_panel_heading">
                            <div class="panel-title"><spring:message code="lbl.work.started"/></div>
                        </div>
                        <div class="panel-body">
                            <c:forEach items="${docketDetailWorkAsPerPlanList}" var="doc"
                                       varStatus="counter">
                                <div class="row add-border">
                                    <div class="col-sm-5 add-margin view-content">
                                        <c:out value="${doc.checkListDetail.description}" default="N/A"></c:out>
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

                        <div class="panel-heading custom_form_panel_heading">
                            <div class="panel-title"><spring:message code="lbl.gen.prov.regrd.site"/></div>
                        </div>
                        <div class="panel-body">
                            <c:forEach items="${docketDetailHgtAbuttRoadList}" var="doc"
                                       varStatus="counter">
                                <div class="row add-border">
                                    <div class="col-sm-5 add-margin view-content">
                                        <c:out value="${doc.checkListDetail.description}" default="N/A"></c:out>
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

                        <div class="panel-heading custom_form_panel_heading">
                            <div class="panel-title"><spring:message code="lbl.extnt.plot"/></div>
                        </div>
                        <div class="panel-body">
                            <c:forEach items="${docketDetailAreaLoc}" var="doc"
                                       varStatus="counter">
                                <div class="row add-border">
                                    <div class="col-sm-5 add-margin view-content">
                                        <c:out value="${doc.checkListDetail.description}" default="N/A"></c:out>
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

                        <div class="panel-heading custom_form_panel_heading">
                            <div class="panel-title"><spring:message code="lbl.len.wall"/></div>
                        </div>
                        <div class="panel-body">
                            <c:forEach items="${docketDetailLengthOfCompWall}" var="doc"
                                       varStatus="counter">
                                <div class="row add-border">
                                    <div class="col-sm-5 add-margin view-content">
                                        <c:out value="${doc.checkListDetail.description}" default="N/A"></c:out>
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
                        <div class="panel-heading custom_form_panel_heading">
                            <div class="panel-title"><spring:message code="lbl.no.wells"/></div>
                        </div>
                        <div class="panel-body">
                            <c:forEach items="${docketDetailNumberOfWell}" var="doc"
                                       varStatus="counter">
                                <div class="row add-border">
                                    <div class="col-sm-5 add-margin view-content">
                                        <c:out value="${doc.checkListDetail.description}" default="N/A"></c:out>
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
                        <c:if test="${not empty docketDetailErectionTower}">
                        <div class="panel-heading custom_form_panel_heading">
                            <div class="panel-title"><spring:message code="lbl.erctn.tower"/></div>
                        </div>
                        <div class="panel-body">
                            <c:forEach items="${docketDetailErectionTower}" var="doc"
                                       varStatus="counter">
                                <div class="row add-border">
                                    <div class="col-sm-5 add-margin view-content">
                                        <c:out value="${doc.checkListDetail.description}" default="N/A"></c:out>
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

                        <div class="panel-heading custom_form_panel_heading">
                            <div class="panel-title"><spring:message code="lbl.shuter"/></div>
                        </div>
                        <div class="panel-body">
                            <c:forEach items="${docketDetailShutter}" var="doc"
                                       varStatus="counter">
                                <div class="row add-border">
                                    <div class="col-sm-5 add-margin view-content">
                                        <c:out value="${doc.checkListDetail.description}" default="N/A"></c:out>
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

                        <div class="panel-heading custom_form_panel_heading">
                            <div class="panel-title"><spring:message code="lbl.roof.conversn"/></div>
                        </div>
                        <div class="panel-body">
                            <c:forEach items="${docketDetailRoofConversion}" var="doc"
                                       varStatus="counter">

                                <div class="row add-border">
                                    <div class="col-sm-5 add-margin view-content">
                                        <c:out value="${doc.checkListDetail.description}" default="N/A"></c:out>
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
                    </div>
                </div>
                <div class="panel panel-primary" data-collapsed="0">
                    <div class="panel-body custom-form ">
                        <jsp:include page="view-inspection-documents.jsp"></jsp:include>
                    </div>
                </div>
            </div>
            <div id="plan-scrutiny" class="tab-pane fade">
                <div class="panel panel-primary" data-collapsed="0">
                    <jsp:include page="view-plan-scrutiny-checklist-rule.jsp"></jsp:include>
                </div>
                <c:if test="${not empty inspection.planScrutinyChecklistForDrawing}">
                    <div class="panel panel-primary" data-collapsed="0">
                        <jsp:include page="view-plan-scrutiny-checklist-drawing.jsp"></jsp:include>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>
<div align="center">
    <input type="button" name="button2" id="button2" value="Close"
           class="btn btn-default" onclick="window.close();"/>
</div>
