<%--
  ~    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
  ~    accountability and the service delivery of the government  organizations.
  ~
  ~     Copyright (C) 2017  eGovernments Foundation
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
  ~            Further, all user interfaces, including but not limited to citizen facing interfaces,
  ~            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
  ~            derived works should carry eGovernments Foundation logo on the top right corner.
  ~
  ~            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
  ~            For any further queries on attribution, including queries on brand guidelines,
  ~            please contact contact@egovernments.org
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
  ~
  --%>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="panel-heading toggle-header custom_form_panel_heading">
    <div class="panel-title">
        <spring:message code="lbl.cmplted.bldg.dtl"/>
    </div>
    <div class="history-icon toggle-icon">
        <i class="fa fa-angle-up fa-2x"></i>
    </div>
</div>
<div class="panel-body display-hide edcrBuildDetails" data-bldg-len="${fn:length(occupancyCertificate.buildings)}">
    <h3 class="error-msg showViolationMessage" id="showViolationMessageDcr"></h3>
    <input type="hidden" id="violationMessage" value="${violationMessage}">
    <c:if test="${mode ne 'new' && not empty occupancyCertificate.buildings}">
        <c:forEach
                items="${occupancyCertificate.buildings}"
                var="bldg" varStatus="bldgCounter">
            <div class="panel-heading custom_form_panel_heading toggle-bldg-header toggle-head${bldgCounter.index}"
                 data-bldg-idx="${bldgCounter.index}">
                <div class="panel-title">
                    <c:if test="${fn:length(occupancyCertificate.buildings) gt 1}">Block - ${bldg.name}</c:if>
                    <spring:message code="lbl.plint.carpet.details"/>
                </div>
                <c:if test="${fn:length(occupancyCertificate.buildings) gt 1}">
                    <div class="history-icon toggle-icon${bldgCounter.index}">
                        <i class="fa fa-angle-up fa-2x"></i>
                    </div>
                </c:if>
            </div>
            <div <c:if
                         test="${fn:length(occupancyCertificate.buildings) gt 1}">class="buildingDetailsToggle${bldgCounter.index} display-hide" </c:if>>
                <table class="table table-striped table-bordered edcrBuildingAreaDetails${bldgCounter.index}">
                    <thead>
                    <tr>
                        <th class="text-center"><spring:message code="lbl.srl.no"/></th>
                        <th class="text-center dcr-floor-toggle-mandatory"><span></span>&nbsp;<spring:message
                                code="lbl.floor.name"/></th>
                        <th class="text-center dcr-floor-toggle-mandatory"><span></span>&nbsp;<spring:message
                                code="lbl.floor.level"/></th>
                        <th class="text-center dcr-floor-toggle-mandatory"><span></span>&nbsp;<spring:message
                                code="lbl.occupancy"/></th>
                        <th class="text-center dcr-floor-toggle-mandatory"><span></span>&nbsp;<spring:message
                                code="lbl.plinth.area"/></th>
                        <th class="text-center dcr-floor-toggle-mandatory"><span></span>&nbsp;<spring:message
                                code="lbl.floor.area"/></th>
                        <th class="text-center dcr-floor-toggle-mandatory"><span></span>&nbsp;<spring:message
                                code="lbl.carpet.area"/></th>
                    </tr>
                    </thead>
                    <tbody
                            data-existing-len="${fn:length(bldg.floorDetails)}">
                    <c:choose>
                        <c:when
                                test="${!bldg.floorDetails.isEmpty() && bldg.floorDetails[0].id ne null}">
                            <c:forEach
                                    items="${bldg.floorDetails}"
                                    var="floor" varStatus="counter">
                                <tr class="data-fetched">
                                    <td class="text-center"><span class="serialNo text-center"
                                                                  id="slNoInsp">${counter.index+1}</span>
                                        <%-- <form:hidden
                                                path="buildings[${bldgCounter.index}].floorDetails[${counter.index}]"
                                                value="${floor.id}"/> --%>
                                        <form:hidden class="orderNo"
                                                     path="buildings[${bldgCounter.index}].floorDetails[${counter.index}].orderOfFloor"/>
                                    </td>
                                    <td><form:select
                                            path="buildings[${bldgCounter.index}].floorDetails[${counter.index}].floorDescription"
                                            data-first-option="false"
                                            id="floorDetails${counter.index}floorDescription"
                                            class="form-control dcr-floor-details-mandatory floorDescription${bldgCounter.index}"
                                            maxlength="128" disabled="true">
                                        <form:option value="">
                                            <spring:message code="lbl.select"/>
                                        </form:option>
                                        <form:options items="${buildingFloorList}"/>
                                    </form:select></td>
                                    <td><form:input type="text"
                                                    class="form-control table-input patternvalidation dcr-floor-details-mandatory floorNumber${bldgCounter.index} text-center"
                                                    data-pattern="number"
                                                    path="buildings[${bldgCounter.index}].floorDetails[${counter.index}].floorNumber"
                                                    id="buildingFloorDetailsByEdcr${counter.index}floorNumber"
                                                    maxlength="15" value="${floor.floorNumber}"
                                                    disabled="true"/></td>
                                    <td><form:select
                                            path="buildings[${bldgCounter.index}].floorDetails[${counter.index}].subOccupancy"
                                            data-first-option="false"
                                            id="floorDetails${counter.index}occupancy"
                                            class="form-control dcr-floor-details-mandatory occupancy${bldgCounter.index}"
                                            maxlength="128" disabled="true">
                                        <form:option value="">
                                            <spring:message code="lbl.select"/>
                                        </form:option>
                                        <form:options items="${subOccupancyList}" itemValue="id"
                                                      itemLabel="description"/>
                                    </form:select></td>
                                    <td><form:input type="text"
                                                    class="form-control table-input dcr-floor-details-mandatory patternvalidation decimalfixed nonzero dcrPlinthArea${bldgCounter.index} text-right"
                                                    data-pattern="decimalvalue"
                                                    path="buildings[${bldgCounter.index}].floorDetails[${counter.index}].plinthArea"
                                                    id="floorDetails${counter.index}plinthArea"
                                                    maxlength="15" required="required" disabled="true"
                                                    value="${floor.plinthArea}"
                                                    onblur="validateDCRFloorDetails(this)"/></td>
                                    <td><form:input type="text"
                                                    class="form-control table-input dcr-floor-details-mandatory text-right patternvalidation decimalfixed dcrFloorArea${bldgCounter.index}"
                                                    data-pattern="decimalvalue"
                                                    path="buildings[${bldgCounter.index}].floorDetails[${counter.index}].floorArea"
                                                    id="buildingFloorDetailsByEdcr${counter.index}floorArea"
                                                    maxlength="15" required="required" disabled="true"
                                                    value="${floor.floorArea}"
                                    /></td>
                                    <td><form:input type="text"
                                                    class="form-control table-input dcr-floor-details-mandatory text-right patternvalidation decimalfixed dcrCarpetArea${bldgCounter.index}"
                                                    data-pattern="decimalvalue"
                                                    path="buildings[${bldgCounter.index}].floorDetails[${counter.index}].carpetArea"
                                                    id="floorDetails${counter.index}carpetArea"
                                                    maxlength="15" required="required" disabled="true"
                                                    value="${floor.carpetArea}"/></td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>

                        </c:otherwise>
                    </c:choose>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td class="text-right">Total</td>
                        <td class="text-right dcr-reset-values"></td>
                        <td class="text-right dcr-reset-values"></td>
                        <td class="text-right dcr-reset-values"></td>
                    </tr>
                    </tfoot>
                </table>
                <div class="form-group">
                    <label
                            class="col-sm-3 control-label text-right show-hide totalPlintArea"><spring:message
                            code="lbl.build.total.plinth"/><span class="mandatory"></span> </label> <label
                        class="col-sm-3 control-label text-right show-hide demolition"><spring:message
                        code="lbl.demolition.area"/> <span class="mandatory"></span> </label><label
                        class="col-sm-3 control-label text-right show-hide noofhutorshed"><spring:message
                        code="lbl.area.hut.shed"/> <span class="mandatory"></span> </label> <label
                        class="col-sm-3 control-label text-right show-hide alterationInArea"><spring:message
                        code="lbl.alteration.area"/> <span class="mandatory"></span> </label> <label
                        class="col-sm-3 control-label text-right show-hide additionInArea"><spring:message
                        code="lbl.extension.area"/> <span class="mandatory"></span> </label> <label
                        class="col-sm-3 control-label text-right show-hide changeInOccupancyArea"><spring:message
                        code="lbl.change.occupancy.area"/> <span class="mandatory"></span>
                </label>
                    <div class="col-sm-3 add-margin">
                        <form:input
                                class="form-control patternvalidation decimalfixed dcr-reset-values totalPlintAreaFromEdcr"
                                maxlength="10" data-pattern="decimalvalue" id="totalPlintAreaFromEdcr${bldgCounter.index}"
                                path="buildingDetailFromEdcr[${bldgCounter.index}].totalPlinthArea"
                                value="${bldg.totalPlinthArea}" disabled="true"/>
                        <form:errors path="buildingDetailFromEdcr[${bldgCounter.index}].totalPlinthArea"
                                     cssClass="add-margin error-msg"/>
                    </div>
                    <label
                            class="col-sm-2 control-label text-right floorCount"><spring:message
                            code="lbl.floor.count"/><span class="mandatory"></span></label>
                    <div class="col-sm-3 add-margin">
                        <input type="hidden" id="setFloorCount" name="buildings[${bldgCounter.index}].floorCount"
                               value="${bldg.floorCount}">
                        <form:input
                                class="form-control patternvalidation dcr-reset-values floorCount"
                                data-pattern="number" maxlength="3" id="floorCountFromEdcr" readonly="true"
                                path="buildingDetailFromEdcr[${bldgCounter.index}].floorCount"
                                value="${bldg.floorCount}"/>
                        <form:errors path="buildingDetailFromEdcr[${bldgCounter.index}].floorCount"
                                     cssClass="add-margin error-msg"/>
                    </div>
                </div>

                <div class="form-group">
                    <label
                            class="col-sm-3 control-label text-right heightFromGroundWithOutStairRoom"><spring:message
                            code="lbl.grnd.wo.stair"/><span class="mandatory"></span></label>
                    <div class="col-sm-3 add-margin">
                        <input type="hidden" id="setHeightFromGroundWithOutStairRoom"
                               name="buildings[${bldgCounter.index}].heightFromGroundWithOutStairRoom"
                               value="${bldg.heightFromGroundWithOutStairRoom}">
                        <form:input
                                class="form-control patternvalidation dcr-reset-values nonzero decimalfixed heightFromGroundWithOutStairRoom"
                                maxlength="6" data-pattern="decimalvalue"  disabled="true"
                                id="heightFromGroundWithOutStairRoomFromEdcr" required="required"
                                path="buildingDetailFromEdcr[0].heightFromGroundWithOutStairRoom"
                                value="${bldg.heightFromGroundWithOutStairRoom}"/>
                        <form:errors
                                path="buildingDetailFromEdcr[${bldgCounter.index}].heightFromGroundWithOutStairRoom"
                                cssClass="add-margin error-msg"/>
                    </div>
                  <%--   <label
                            class="col-sm-2 control-label text-right heightFromGroundWithStairRoom"><spring:message
                            code="lbl.grnd.with.stair"/></label>
                    <div class="col-sm-3 add-margin">
                        <input type="hidden" id="setHeightFromGroundWithStairRoom"
                               name="buildings[${bldgCounter.index}].heightFromGroundWithStairRoom"
                               value="${bldg.heightFromGroundWithStairRoom}">
                        <form:input
                                class="form-control patternvalidation dcr-reset-values decimalfixed heightFromGroundWithStairRoom"
                                maxlength="6" data-pattern="decimalvalue"
                                id="heightFromGroundWithStairRoomFromEdcr"
                                path="buildingDetailFromEdcr[${bldgCounter.index}].heightFromGroundWithStairRoom"
                                value="${bldg.heightFromGroundWithStairRoom}"/>
                        <form:errors path="buildingDetailFromEdcr[${bldgCounter.index}].heightFromGroundWithStairRoom"
                                     cssClass="add-margin error-msg"/>
                    </div>
                </div>
                <div class="form-group">
                    <label
                            class="col-sm-3 control-label text-right fromStreetLevelWithOutStairRoom"><spring:message
                            code="lbl.street.wo.stair"/></label>
                    <div class="col-sm-3 add-margin">
                        <input type="hidden" id="setFromStreetLevelWithOutStairRoom"
                               name="buildings[${bldgCounter.index}].fromStreetLevelWithOutStairRoom"
                               value="${bldg.fromStreetLevelWithOutStairRoom}">
                        <form:input
                                class="form-control patternvalidation dcr-reset-values decimalfixed fromStreetLevelWithOutStairRoom"
                                maxlength="6" data-pattern="decimalvalue"
                                id="fromStreetLevelWithOutStairRoomFromEdcr"
                                path="buildingDetailFromEdcr[${bldgCounter.index}].fromStreetLevelWithOutStairRoom"
                                value="${bldg.fromStreetLevelWithOutStairRoom}"/>
                        <form:errors path="buildingDetailFromEdcr[${bldgCounter.index}].fromStreetLevelWithOutStairRoom"
                                     cssClass="add-margin error-msg"/>
                    </div>
                    <label
                            class="col-sm-2 control-label text-right fromStreetLevelWithStairRoom"><spring:message
                            code="lbl.street.with.stair"/></label>
                    <div class="col-sm-3 add-margin">
                        <input type="hidden" id="setFromStreetLevelWithStairRoom"
                               name="buildings[${bldgCounter.index}].fromStreetLevelWithStairRoom"
                               value="${bldg.fromStreetLevelWithStairRoom}">
                        <form:input
                                class="form-control patternvalidation dcr-reset-values decimalfixed fromStreetLevelWithStairRoom"
                                maxlength="6" data-pattern="decimalvalue"
                                id="fromStreetLevelWithStairRoomFromEdcr"
                                path="buildingDetailFromEdcr[${bldgCounter.index}].fromStreetLevelWithStairRoom"
                                value="${bldg.fromStreetLevelWithStairRoom}"/>
                        <form:errors path="buildingDetailFromEdcr[${bldgCounter.index}].fromStreetLevelWithStairRoom"
                                     cssClass="add-margin error-msg"/>
                    </div>
                </div> --%>
            </div>
        </c:forEach>

    </c:if>
    <div id="blocksContainer">

    </div>
</div>

<script
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/moment.min.js' context='/egi'/>"></script>