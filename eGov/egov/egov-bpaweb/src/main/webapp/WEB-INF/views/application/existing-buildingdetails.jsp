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

<%@page import="org.python.modules.jarray"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="panel-heading toggle-header custom_form_panel_heading">
	<div class="panel-title">
		<spring:message code="lbl.exist.build.details" />
	</div>
	<div class="history-icon toggle-icon">
		<i class="fa fa-angle-up fa-2x"></i>
	</div>
</div>
<div class="panel-body display-hide">
	<div class="panel-title header-color">
		<spring:message code="lbl.plint.carpet.details" />
	</div>
	<input type="hidden" id="buildingFloorListForExistingBuild"
		value="${buildingFloorList}"> <input type="hidden"
		id="occupancyListForExistingBuild" value="${occupancyList}"> <input
		type="hidden" id="sumOfFloorAreaOfExist" value="">
	<form:hidden id="existDeletedFloorIds"
		path="existingBuildingDetails[0].deletedFloorIds" value="" />
	<table class="table table-striped table-bordered"
		id="existingBuildingAreaDetails">
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
				<th class="text-center"><spring:message code="lbl.action" /></th>
			</tr>
		</thead>
		<tbody
			data-existing-len="${fn:length(bpaApplication.existingBuildingDetails[0].existingBuildingFloorDetails)}">
			<c:choose>
				<c:when
					test="${!bpaApplication.existingBuildingDetails[0].existingBuildingFloorDetails.isEmpty()}">
					<c:forEach
						items="${bpaApplication.existingBuildingDetails[0].existingBuildingFloorDetails}"
						var="existingBuildFloorDetail" varStatus="counter">
						<tr class="data-fetched">
							<td class="text-center"><span class="serialNoForExistBuild text-center"
								id="slNoInsp">${counter.index+1}</span>
							<form:hidden path="existingBuildingDetails[0].existingBuildingFloorDetails[${counter.index}]" value="${existingBuildFloorDetail.id}" />
								<form:hidden class="orderNoForExistBuild"
									path="existingBuildingDetails[0].existingBuildingFloorDetails[${counter.index}].orderOfFloor" /></td>
							<td><form:select
									path="existingBuildingDetails[0].existingBuildingFloorDetails[${counter.index}].floorDescription"
									data-first-option="false"
									id="existingBuildingFloorDetails${counter.index}floorDescription"
									class="form-control exist-floor-details-mandatory existFloorDescription"
									maxlength="128">
									<form:option value="">
										<spring:message code="lbl.select" />
									</form:option>
									<form:options items="${buildingFloorList}" />
								</form:select></td>
							<td><form:input type="text"
									class="form-control table-input patternvalidation exist-floor-details-mandatory existFloorNumber text-center"
									data-pattern="number"
									path="existingBuildingDetails[0].existingBuildingFloorDetails[${counter.index}].floorNumber"
									id="existingBuildingFloorDetails${counter.index}floorNumber"
									maxlength="15" value="${existingBuildFloorDetail.floorNumber}" /></td>
							<td><form:select
									path="existingBuildingDetails[0].existingBuildingFloorDetails[${counter.index}].occupancy"
									data-first-option="false"
									id="existingBuildingFloorDetails${counter.index}occupancy"
									class="form-control exist-floor-details-mandatory existOccupancy"
									maxlength="128">
									<form:option value="">
										<spring:message code="lbl.select" />
									</form:option>
									<form:options items="${occupancyList}" itemValue="id"
										itemLabel="description" />
								</form:select></td>
							<td><form:input type="text"
									class="form-control table-input patternvalidation exist-floor-details-mandatory decimalfixed nonzero existPlinthArea text-right"
									data-pattern="decimalvalue"
									path="existingBuildingDetails[0].existingBuildingFloorDetails[${counter.index}].plinthArea"
									id="existingBuildingFloorDetails${counter.index}plinthArea"
									maxlength="15" required="required"
									value="${existingBuildFloorDetail.plinthArea}" /></td>
							<td><form:input type="text"
									class="form-control table-input text-right patternvalidation exist-floor-details-mandatory decimalfixed nonzero existFloorArea"
									data-pattern="decimalvalue"
									path="existingBuildingDetails[0].existingBuildingFloorDetails[${counter.index}].floorArea"
									id="existingBuildingFloorDetails${counter.index}floorArea"
									maxlength="15" required="required" value="${existingBuildFloorDetail.floorArea}" /></td>
							<td><form:input type="text"
									class="form-control table-input text-right patternvalidation exist-floor-details-mandatory decimalfixed existCarpetArea"
									data-pattern="decimalvalue"
									path="existingBuildingDetails[0].existingBuildingFloorDetails[${counter.index}].carpetArea"
									id="existingBuildingFloorDetails${counter.index}carpetArea"
									maxlength="15" required="required"
									value="${existingBuildFloorDetail.carpetArea}" /></td>
							<c:if test="${counter.index!=0}">
								<td class="text-center"><a href="javascript:void(0);"
									class="btn-sm btn-danger" id="deleteExistBuildFloorRow"
									data-record-id="${existingBuildFloorDetail.id}"><i
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
						<td class="text-center"><span class="serialNoForExistBuild" id="slNoInsp">1</span>
						<form:hidden class="orderNoForExistBuild"
								path="existingBuildingDetails[0].existingBuildingFloorDetails[0].orderOfFloor"
								id="orderOfFloorForExistBuild" value="1" /></td>
						<td><form:select
								path="existingBuildingDetails[0].existingBuildingFloorDetails[0].floorDescription"
								data-first-option="false"
								id="existingBuildingFloorDetails[0]floorDescription"
								class="form-control exist-floor-details-mandatory existFloorDescription"
								maxlength="128">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${buildingFloorList}" />
							</form:select></td>
						<td><form:input type="text"
								class="form-control table-input patternvalidation existFloorNumber exist-floor-details-mandatory text-center"
								data-pattern="number"
								path="existingBuildingDetails[0].existingBuildingFloorDetails[0].floorNumber"
								id="existingBuildingFloorDetails0floorNumber" maxlength="3"
								value="${existingBuildFloorDetail.floorNumber}" /></td>
						<td><form:select
								path="existingBuildingDetails[0].existingBuildingFloorDetails[0].occupancy"
								data-first-option="false"
								id="existingBuildingFloorDetails[0]occupancy"
								class="form-control exist-floor-details-mandatory existOccupancy"
								maxlength="128">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${occupancyList}" itemValue="id"
									itemLabel="description" />
							</form:select></td>
						<td><form:input type="text"
								class="form-control table-input text-right patternvalidation decimalfixed nonzero existPlinthArea exist-floor-details-mandatory"
								data-pattern="decimalvalue"
								path="existingBuildingDetails[0].existingBuildingFloorDetails[0].plinthArea"
								id="existingBuildingFloorDetails0plinthArea" maxlength="10" value="${existingBuildFloorDetail.plinthArea}" /></td>
						<td><form:input type="text"
								class="form-control table-input text-right patternvalidation decimalfixed nonzero existFloorArea exist-floor-details-mandatory"
								data-pattern="decimalvalue"
								path="existingBuildingDetails[0].existingBuildingFloorDetails[0].floorArea"
								id="existingBuildingFloorDetails0floorArea" maxlength="10" value="${existingBuildFloorDetail.floorArea}" /></td>
						<td><form:input type="text"
								class="form-control table-input text-right patternvalidation decimalfixed existCarpetArea exist-floor-details-mandatory"
								data-pattern="decimalvalue"
								path="existingBuildingDetails[0].existingBuildingFloorDetails[0].carpetArea"
								id="existingBuildingFloorDetails0carpetArea" maxlength="10" value="${existingBuildFloorDetail.carpetArea}" /></td>
						<td></td>
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
			id="addExistBuildFloorRow">ADD ROW</button>
	</div>
	<form:hidden id="existTotalPlintArea" path="existingBuildingDetails[0].totalPlintArea" />
</div>

<script
	src="<cdn:url value='/resources/js/app/existing-buildingarea-details.js?rnd=${app_release_no}'/>"></script>