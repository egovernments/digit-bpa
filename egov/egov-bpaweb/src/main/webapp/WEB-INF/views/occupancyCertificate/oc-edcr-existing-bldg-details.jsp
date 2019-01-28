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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="panel-heading toggle-header custom_form_panel_heading">
	<div class="panel-title">
		<spring:message code="lbl.exist.build.details" />
	</div>
	<div class="history-icon toggle-icon">
		<i class="fa fa-angle-up fa-2x"></i>
	</div>
</div>
<div class="panel-body display-hide existBuilding" data-bldg-len="${fn:length(occupancyCertificate.existingBuildings)}">
<c:if test="${mode ne 'new' && not empty occupancyCertificate.existingBuildings}">
	<c:forEach
			items="${occupancyCertificate.existingBuildings}"
			var="existBldg" varStatus="existBldgCounter">
		<div class="panel-heading custom_form_panel_heading toggle-bldg-header toggle-head${existBldgCounter.index}"
			 data-bldg-idx="${existBldgCounter.index}">
			<div class="panel-title">
				<c:if test="${fn:length(occupancyCertificate.existingBuildings) gt 1}">Block - ${existBldg.name}</c:if>
				<spring:message code="lbl.plint.carpet.details" />
			</div>
			<c:if test="${fn:length(occupancyCertificate.existingBuildings) gt 1}">
				<div class="history-icon toggle-icon${existBldgCounter.index}">
					<i class="fa fa-angle-up fa-2x"></i>
				</div>
			</c:if>
		</div>
    <div <c:if
                 test="${fn:length(occupancyCertificate.existingBuildings) gt 1}">class="buildingDetailsToggle${existBldgCounter.index} display-hide" </c:if>>
	<table class="table table-striped table-bordered existingBuildingAreaDetails${existBldgCounter.index}">
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
			</tr>
		</thead>
		<tbody
			data-existing-len="${fn:length(existBldg.existingBuildingFloorDetails)}">
					<c:forEach items="${existBldg.existingBuildingFloorDetails}"
						var="existingBuildFloorDetail" varStatus="counter">
						<tr class="data-fetched">
							<td class="text-center"><span class="serialNoForExistBuild text-center"
								id="slNoInsp">${counter.index+1}</span>
								<form:hidden class="orderNoForExistBuild"
									path="existingBuildings[${existBldgCounter.index}].existingBuildingFloorDetails[${counter.index}].orderOfFloor" /></td>
							<td><form:select
									path="existingBuildings[${existBldgCounter.index}].existingBuildingFloorDetails[${counter.index}].floorDescription"
									data-first-option="false"
									id="existingBuildingFloorDetails${counter.index}floorDescription"
									class="form-control exist-floor-details-mandatory existFloorDescription"
									maxlength="128" disabled="true">
									<form:option value="">
										<spring:message code="lbl.select" />
									</form:option>
									<form:options items="${buildingFloorList}" />
								</form:select></td>
							<td><form:input type="text"
									class="form-control table-input patternvalidation exist-floor-details-mandatory existFloorNumber text-center"
									data-pattern="number"
									path="existingBuildings[${existBldgCounter.index}].existingBuildingFloorDetails[${counter.index}].floorNumber"
									id="existingBuildingFloorDetails${counter.index}floorNumber"
									maxlength="15" value="${existingBuildFloorDetail.floorNumber}" disabled="true"/></td>
							<td><form:select
									path="existingBuildings[${existBldgCounter.index}].existingBuildingFloorDetails[${counter.index}].occupancy"
									data-first-option="false"
									id="existingBuildingFloorDetails${counter.index}occupancy"
									class="form-control exist-floor-details-mandatory existOccupancy"
									maxlength="128" disabled="true">
									<form:option value="">
										<spring:message code="lbl.select" />
									</form:option>
									<form:options items="${occupancyList}" itemValue="id"
										itemLabel="description" />
								</form:select></td>
							<td><form:input type="text"
									class="form-control table-input patternvalidation exist-floor-details-mandatory decimalfixed nonzero dcrPlinthArea${existBldgCounter.index} text-right"
									data-pattern="decimalvalue"
									path="existingBuildings[${existBldgCounter.index}].existingBuildingFloorDetails[${counter.index}].plinthArea"
									id="existingBuildingFloorDetails${counter.index}plinthArea"
									maxlength="15" required="required"
									value="${existingBuildFloorDetail.plinthArea}" disabled="true" /></td>
							<td><form:input type="text"
									class="form-control table-input text-right patternvalidation exist-floor-details-mandatory decimalfixed nonzero dcrFloorArea${existBldgCounter.index}"
									data-pattern="decimalvalue"
									path="existingBuildings[${existBldgCounter.index}].existingBuildingFloorDetails[${counter.index}].floorArea"
									id="existingBuildingFloorDetails${counter.index}floorArea"
									maxlength="15" required="required" value="${existingBuildFloorDetail.floorArea}" disabled="true" /></td>
							<td><form:input type="text"
									class="form-control table-input text-right patternvalidation exist-floor-details-mandatory decimalfixed dcrCarpetArea${existBldgCounter.index}"
									data-pattern="decimalvalue"
									path="existingBuildings[${existBldgCounter.index}].existingBuildingFloorDetails[${counter.index}].carpetArea"
									id="existingBuildingFloorDetails${counter.index}carpetArea"
									maxlength="15" required="required"
									value="${existingBuildFloorDetail.carpetArea}" disabled="true" /></td>

						</tr>
					</c:forEach>
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
			</tr>
		</tfoot>
	</table>
    </div>
		<form:hidden class="existTotalPlintArea" id="existTotalPlintArea${existBldgCounter.index}" path="existingBuildings[${existBldgCounter.index}].totalPlinthArea" />
	</c:forEach>
</c:if>
	<div id="existBldgBlocksContainer">

	</div>

</div>

<script
	src="<cdn:url value='/resources/js/app/existing-buildingarea-details.js?rnd=${app_release_no}'/>"></script>