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

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="panel-heading toggle-header custom_form_panel_heading">
	<div class="panel-title">
		<spring:message code="lbl.build.details" />
	</div>
	<div class="history-icon toggle-icon">
		<i class="fa fa-angle-up fa-2x"></i>
	</div>
</div>
<div class="panel-body display-hide">
	<h3 class="error-msg showViolationMessage" id="showViolationMessage"></h3>
	<div class="panel-title header-color">
		<spring:message code="lbl.plint.carpet.details" />
	</div>
	<input type="hidden" id="buildingFloorList"
		value="${buildingFloorList}"> <input type="hidden"
		id="occupancyList" value="${occupancyList}"> <input
		type="hidden" id="sumOfFloorArea" value=""> 
	<input type="hidden" id="violationMessage" value="${violationMessage}">
	<table class="table table-striped table-bordered"
		id="buildingAreaDetails">
		<thead>
			<tr>
				<th class="text-center"><spring:message code="lbl.srl.no" /></th>
				<th class="text-center floor-toggle-mandatory"><span></span>&nbsp;<spring:message
						code="lbl.floor.name" /></th>
				<th class="text-center floor-toggle-mandatory"><span></span>&nbsp;<spring:message
						code="lbl.floor.level" /></th>
				<th class="text-center floor-toggle-mandatory"><span></span>&nbsp;<spring:message
						code="lbl.occupancy" /></th>
				<th class="text-center floor-toggle-mandatory"><span></span>&nbsp;<spring:message
						code="lbl.plinth.area" /></th>
				<th class="text-center floor-toggle-mandatory"><span></span>&nbsp;<spring:message
						code="lbl.floor.area" /></th>
				<th class="text-center floor-toggle-mandatory"><span></span>&nbsp;<spring:message
						code="lbl.carpet.area" /></th>
				<th class="text-center"><spring:message code="lbl.action" /><input type="hidden" id="debug" value="${occupancyCertificate.buildings[0].floorDetails}"></th>
			</tr>
		</thead>
		<tbody
			data-existing-len="${fn:length(occupancyCertificate.buildings[0].floorDetails)}">
			<c:choose>
				<c:when
					test="${!occupancyCertificate.buildings[0].floorDetails.isEmpty()}">
					<c:forEach
						items="${occupancyCertificate.buildings[0].floorDetails}"
						var="proposedBuildFloorDetail" varStatus="counter">
						<tr class="data-fetched">
							<td class="text-center"><span class="serialNo text-center"
								id="slNoInsp">${counter.index+1}</span>
								<form:hidden path="buildings[0].floorDetails[${counter.index}]" value="${proposedBuildFloorDetail.id}" />
								<form:hidden class="orderNo"
									path="buildings[0].floorDetails[${counter.index}].orderOfFloor" /></td>
							<td><form:select
									path="buildings[0].floorDetails[${counter.index}].floorDescription"
									data-first-option="false"
									id="floorDetails${counter.index}floorDescription"
									class="form-control floor-details-mandatory duplicate-clear floorDescription"
									maxlength="128">
									<form:option value="">
										<spring:message code="lbl.select" />
									</form:option>
									<form:options items="${buildingFloorList}" />
								</form:select></td>
							<td><form:input type="text"
									class="form-control table-input patternvalidation duplicate-clear floor-details-mandatory floorNumber text-center"
									data-pattern="number"
									path="buildings[0].floorDetails[${counter.index}].floorNumber"
									id="floorDetails${counter.index}floorNumber"
									maxlength="15" value="${proposedBuildFloorDetail.floorNumber}" /></td>
							<td><form:select
									path="buildings[0].floorDetails[${counter.index}].occupancy"
									data-first-option="false"
									id="floorDetails${counter.index}occupancy"
									class="form-control floor-details-mandatory occupancy"
									maxlength="128">
									<form:option value="">
										<spring:message code="lbl.select" />
									</form:option>
									<form:options items="${occupancyList}" itemValue="id"
										itemLabel="description" />
								</form:select></td>
							<td><form:input type="text"
									class="form-control table-input patternvalidation decimalfixed nonzero plinthArea text-right"
									data-pattern="decimalvalue"
									path="buildings[0].floorDetails[${counter.index}].plinthArea"
									id="floorDetails${counter.index}plinthArea"
									maxlength="15" required="required"
									value="${proposedBuildFloorDetail.plinthArea}"
									onblur="validateFloorDetails(this)" /></td>
							<td><form:input type="text"
									class="form-control table-input text-right patternvalidation decimalfixed nonzero floorArea"
									data-pattern="decimalvalue"
									path="buildings[0].floorDetails[${counter.index}].floorArea"
									id="floorDetails${counter.index}floorArea"
									maxlength="15" required="required" value="${proposedBuildFloorDetail.floorArea}" /></td>
							<td><form:input type="text"
									class="form-control table-input text-right patternvalidation decimalfixed carpetArea"
									data-pattern="decimalvalue"
									path="buildings[0].floorDetails[${counter.index}].carpetArea"
									id="floorDetails${counter.index}carpetArea"
									maxlength="15" required="required"
									value="${proposedBuildFloorDetail.carpetArea}" /></td>
							<c:if test="${counter.index!=0}">
								<td class="text-center"><a href="javascript:void(0);"
									class="btn-sm btn-danger" id="deleteBuildAreaRow"
									data-record-id="${proposedBuildFloorDetail.id}"><i
										class="fa fa-trash"></i></a></td>
							</c:if>

							<c:if test="${counter.index eq 0}">
								<td></td>
							</c:if>

						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="data-fetched">
						<td class="text-center"><span class="serialNo" id="slNoInsp">1</span>
						<form:hidden class="orderNo"
								path="buildings[0].floorDetails[0].orderOfFloor"
								id="orderOfFloor" value="1" /></td>
						<td><form:select
								path="buildings[0].floorDetails[0].floorDescription"
								data-first-option="false"
								id="floorDetails[0]floorDescription"
								class="form-control floor-details-mandatory floorDescription"
								maxlength="128">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${buildingFloorList}" />
							</form:select></td>
						<td><form:input type="text"
								class="form-control table-input patternvalidation floorNumber floor-details-mandatory text-center"
								data-pattern="number"
								path="buildings[0].floorDetails[0].floorNumber"
								id="floorDetails0floorNumber" maxlength="3"
								value="${proposedBuildFloorDetail.floorNumber}" /></td>
						<td><form:select
								path="buildings[0].floorDetails[0].occupancy"
								data-first-option="false"
								id="floorDetails[0]occupancy"
								class="form-control floor-details-mandatory occupancy"
								maxlength="128">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${occupancyList}" itemValue="id"
									itemLabel="description" />
							</form:select></td>
						<td><form:input type="text"
								class="form-control table-input text-right patternvalidation decimalfixed nonzero plinthArea floor-details-mandatory"
								data-pattern="decimalvalue"
								path="buildings[0].floorDetails[0].plinthArea"
								onblur="validateFloorDetails(this)"
								id="floorDetails0plinthArea" maxlength="10" value="${proposedBuildFloorDetail.plinthArea}" /></td>
						<td><form:input type="text"
								class="form-control table-input text-right patternvalidation decimalfixed nonzero floorArea floor-details-mandatory"
								data-pattern="decimalvalue"
								path="buildings[0].floorDetails[0].floorArea"
								id="floorDetails0floorArea" maxlength="10" value="${proposedBuildFloorDetail.floorArea}" /></td>
						<td><form:input type="text"
								class="form-control table-input text-right patternvalidation decimalfixed carpetArea floor-details-mandatory"
								data-pattern="decimalvalue"
								path="buildings[0].floorDetails[0].carpetArea"
								id="floorDetails0carpetArea" maxlength="10" value="${proposedBuildFloorDetail.carpetArea}" /></td>
						<td></td>
						<%-- <td class=" text-center"><a href="javascript:void(0);"
							class="btn-sm btn-danger" id="deleteBuildAreaRow"
							data-record-id="${proposedBuildFloorDetail.id}"><i
								class="fa fa-trash"></i></a></td> --%>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
		<tfoot>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td class="text-right">Total</td>
				<td class="text-right"></td>
				<td class="text-right"></td>
				<td class="text-right"></td>
				<td></td>
			</tr>
		</tfoot>
	</table>
	<div class="text-right add-padding">
		<button type="button" class="btn btn-sm btn-primary"
			id="addBuildAreaRow">ADD ROW</button>
	</div>
	<div class="form-group">
		<label
			class="col-sm-3 control-label text-right handle-mandatory show-hide totalPlintArea"><spring:message
				code="lbl.build.total.plinth" /><span class="mandatory"></span> </label> <label
			class="col-sm-3 control-label text-right handle-mandatory show-hide demolition"><spring:message
				code="lbl.demolition.area" /> <span class="mandatory"></span> </label><label
			class="col-sm-3 control-label text-right handle-mandatory show-hide noofhutorshed"><spring:message
				code="lbl.area.hut.shed" /> <span class="mandatory"></span> </label> <label
			class="col-sm-3 control-label text-right handle-mandatory show-hide alterationInArea"><spring:message
				code="lbl.alteration.area" /> <span class="mandatory"></span> </label> <label
			class="col-sm-3 control-label text-right handle-mandatory show-hide additionInArea"><spring:message
				code="lbl.extension.area" /> <span class="mandatory"></span> </label> <label
			class="col-sm-3 control-label text-right handle-mandatory show-hide changeInOccupancyArea"><spring:message
				code="lbl.change.occupancy.area" /> <span class="mandatory"></span>
		</label>
		<div class="col-sm-3 add-margin">
			<form:input
				class="form-control patternvalidation handle-mandatory nonzero clear-values"
				maxlength="10" data-pattern="decimalvalue" id="totalPlintArea"
				path="buildings[0].totalPlinthArea" required="required" />
			<form:errors path="buildings[0].totalPlinthArea"
				cssClass="add-margin error-msg" />
		</div>
		<label
			class="col-sm-2 control-label text-right handle-mandatory floorCount"><spring:message
				code="lbl.floor.count" /><span class="mandatory"></span></label>
		<div class="col-sm-3 add-margin">
			<form:input
				class="form-control patternvalidation clear-values handle-mandatory nonzero floorCount"
				data-pattern="number" maxlength="3" id="floorCount" readonly="true"
				path="buildings[0].floorCount" required="required" />
			<form:errors path="buildings[0].floorCount"
				cssClass="add-margin error-msg" />
		</div>
	</div>

	<div class="form-group">
		<label
				class="col-sm-3 control-label text-right heightFromGroundWithOutStairRoom"><spring:message
				code="lbl.grnd.wo.stair" /><span class="mandatory"></span></label>
		<div class="col-sm-3 add-margin">
			<form:input
					class="form-control patternvalidation clear-values handle-mandatory decimalfixed nonzero heightFromGroundWithOutStairRoom"
					maxlength="6" data-pattern="decimalvalue"
					id="heightFromGroundWithOutStairRoom" required="required"
					path="buildings[0].heightFromGroundWithOutStairRoom"/>
			<form:errors
					path="buildings[0].heightFromGroundWithOutStairRoom"
					cssClass="add-margin error-msg" />
		</div>
		<label
			class="col-sm-2 control-label text-right handle-mandatory heightFromGroundWithStairRoom"><spring:message
				code="lbl.grnd.with.stair" /></label>
		<div class="col-sm-3 add-margin">
			<form:input
				class="form-control patternvalidation clear-values decimalfixed heightFromGroundWithStairRoom"
				maxlength="6" data-pattern="decimalvalue"
				id="heightFromGroundWithStairRoom"
				path="buildings[0].heightFromGroundWithStairRoom"/>
			<form:errors path="buildings[0].heightFromGroundWithStairRoom"
				cssClass="add-margin error-msg" />
		</div>
	</div>
	<div class="form-group">
		<label
			class="col-sm-3 control-label text-right fromStreetLevelWithOutStairRoom"><spring:message
				code="lbl.street.wo.stair" /></label>
		<div class="col-sm-3 add-margin">
			<form:input
				class="form-control patternvalidation clear-values decimalfixed fromStreetLevelWithOutStairRoom"
				maxlength="6" data-pattern="decimalvalue"
				id="fromStreetLevelWithOutStairRoom"
				path="buildings[0].fromStreetLevelWithOutStairRoom" />
			<form:errors path="buildings[0].fromStreetLevelWithOutStairRoom"
				cssClass="add-margin error-msg" />
		</div>
		<label
				class="col-sm-2 control-label text-right fromStreetLevelWithStairRoom"><spring:message
				code="lbl.street.with.stair" /></label>
		<div class="col-sm-3 add-margin">
			<form:input
					class="form-control patternvalidation clear-values decimalfixed fromStreetLevelWithStairRoom"
					maxlength="6" data-pattern="decimalvalue"
					id="fromStreetLevelWithStairRoom"
					path="buildings[0].fromStreetLevelWithStairRoom"/>
			<form:errors path="buildings[0].fromStreetLevelWithStairRoom"
						 cssClass="add-margin error-msg" />
		</div>
	</div>
</div>

<script
		src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/moment.min.js' context='/egi'/>"></script>