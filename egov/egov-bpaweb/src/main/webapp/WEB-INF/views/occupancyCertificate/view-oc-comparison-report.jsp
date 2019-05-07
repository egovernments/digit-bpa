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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>

<div class="row">
	<div class="col-md-12">
		<div class="panel panel-primary" data-collapsed="0">
			<div class="panel-heading custom_form_panel_heading">
				<div class="panel-title">
					<spring:message code="lbl.oc.bpa.comparison.report" />
				</div>
			</div>
			<div class="panel-body">
				<c:if test="${not empty bpaMap && not empty ocMap}">

					<c:forEach items="${bpaMap}" var="map">
						<div class="panel-title">Block - ${map.key}</div>
						<div>
							<table class="table table-striped table-bordered multiheadertbl">
								<thead>
									<tr>
										<th class="text-center view-content" colspan="2"><spring:message
												code="lbl.floor.number" /></th>
										<th class="text-center view-content" colspan="2"><spring:message
												code="lbl.occupancy.type" /></th>
										<th class="text-center view-content" colspan="3"><spring:message
												code="lbl.plinth.area" /></th>
										<th class="text-center view-content" colspan="3"><spring:message
												code="lbl.floor.area" /></th>
										<th class="text-center view-content" colspan="3"><spring:message
												code="lbl.carpet.area" /></th>
									</tr>
									<tr>
										<th class="text-center"><spring:message code="lbl.oc" /></th>
										<th class="text-center"><spring:message code="lbl.permit" /></th>
										<th class="text-center"><spring:message code="lbl.oc" /></th>
										<th class="text-center"><spring:message code="lbl.permit" /></th>
										<th class="text-center"><spring:message code="lbl.oc" /></th>
										<th class="text-center"><spring:message code="lbl.permit" /></th>
										<th class="text-center"><spring:message
												code="lbl.oc.permit.deviation" /></th>
										<th class="text-center"><spring:message code="lbl.oc" /></th>
										<th class="text-center"><spring:message code="lbl.permit" /></th>
										<th class="text-center"><spring:message
												code="lbl.oc.permit.deviation" /></th>
										<th class="text-center"><spring:message code="lbl.oc" /></th>
										<th class="text-center"><spring:message code="lbl.permit" /></th>
										<th class="text-center"><spring:message
												code="lbl.oc.permit.deviation" /></th>
									</tr>
								</thead>
								<tbody>
									<c:set var="bpaFloorMap" value="${bpaMap[map.key]}"></c:set>
									<c:set var="OcFloorMap" value="${ocMap[map.key]}"></c:set>
									<c:set var="ocFloorAreaTotal" value="${0}" />
									<c:set var="ocPlinthAreaTotal" value="${0}" />
									<c:set var="ocCarpetAreaTotal" value="${0}" />
									<c:set var="bpaFloorAreaTotal" value="${0}" />
									<c:set var="bpaPlinthAreaTotal" value="${0}" />
									<c:set var="bpaCarpetAreaTotal" value="${0}" />
									<c:forEach items="${bpaFloorMap}" var="bpaFloor">
										<c:set var="ocFloor" value="${OcFloorMap[bpaFloor.key]}"></c:set>
										<c:set var="bpaFloor" value="${bpaFloorMap[bpaFloor.key]}"></c:set>
										<c:set var="ocFloorAreaTotal"
											value="${ocFloorAreaTotal + ocFloor.floorArea}" />
										<c:set var="ocPlinthAreaTotal"
											value="${ocPlinthAreaTotal + ocFloor.plinthArea}" />
										<c:set var="ocCarpetAreaTotal"
											value="${ocCarpetAreaTotal + ocFloor.carpetArea}" />
										<c:set var="bpaFloorAreaTotal"
											value="${bpaFloorAreaTotal + bpaFloor.floorArea}" />
										<c:set var="bpaPlinthAreaTotal"
											value="${bpaPlinthAreaTotal + bpaFloor.plinthArea}" />
										<c:set var="bpaCarpetAreaTotal"
											value="${bpaCarpetAreaTotal + bpaFloor.carpetArea}" />
										<tr>
											<td class="text-center">${ocFloor.floorNumber}</td>
											<td class="text-center">${bpaFloor.floorNumber}</td>
											<td>${ocFloor.subOccupancy.name}</td>
											<td>${bpaFloor.subOccupancy.name}</td>
											<td class="text-right"><fmt:formatNumber type="number"
													minFractionDigits="2" maxFractionDigits="2"
													value="${ocFloor.plinthArea}" /></td>
											<td class="text-right"><fmt:formatNumber type="number"
													minFractionDigits="2" maxFractionDigits="2"
													value="${bpaFloor.plinthArea}" /></td>
											<td class="text-right"><c:set var="pinthAreaPercent"
													value="${((ocFloor.plinthArea -bpaFloor.plinthArea) * 100 )/ bpaFloor.plinthArea}"></c:set>
												<fmt:formatNumber type="number" maxFractionDigits="2"
													value="${pinthAreaPercent}" /></td>
											<td class="text-right"><fmt:formatNumber type="number"
													minFractionDigits="2" maxFractionDigits="2"
													value="${ocFloor.floorArea}" /></td>
											<td class="text-right"><fmt:formatNumber type="number"
													minFractionDigits="2" maxFractionDigits="2"
													value="${bpaFloor.floorArea}" /></td>
											<td class="text-right"><c:set var="floorAreaPercent"
													value="${((ocFloor.floorArea - bpaFloor.floorArea) * 100 ) / bpaFloor.floorArea}"></c:set>
												<fmt:formatNumber type="number" maxFractionDigits="2"
													value="${floorAreaPercent}" /></td>
											<td class="text-right"><fmt:formatNumber type="number"
													minFractionDigits="2" maxFractionDigits="2"
													value="${ocFloor.carpetArea}" /></td>
											<td class="text-right"><fmt:formatNumber type="number"
													minFractionDigits="2" maxFractionDigits="2"
													value="${bpaFloor.carpetArea}" /></td>
											<td class="text-right"><c:set var="carpetAreaPercent"
													value="${((ocFloor.carpetArea - bpaFloor.carpetArea) *  100 ) / bpaFloor.carpetArea}"></c:set>
												<fmt:formatNumber type="number" maxFractionDigits="2"
													value="${carpetAreaPercent}" /></td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="2"></td>
										<td colspan="2" class="text-right view-content">Total</td>
										<td class="text-right view-content"><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${ocPlinthAreaTotal}" /></td>
										<td class="text-right view-content"><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${bpaPlinthAreaTotal}" /></td>
										<td class="text-right view-content"><c:set
												var="ocPlinthAreaTotalPercent"
												value="${(ocPlinthAreaTotal - bpaPlinthAreaTotal)  * 100 / bpaPlinthAreaTotal}" />
											<fmt:formatNumber type="number" maxFractionDigits="2"
												value="${ocPlinthAreaTotalPercent}" /></td>
										<td class="text-right view-content"><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${ocFloorAreaTotal}" /></td>
										<td class="text-right view-content"><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${bpaFloorAreaTotal}" /></td>
										<td class="text-right view-content"><c:set
												var="ocFloorAreaTotalPercent"
												value="${(ocFloorAreaTotal - bpaFloorAreaTotal) * 100 / bpaFloorAreaTotal}" />
											<fmt:formatNumber type="number" maxFractionDigits="2"
												value="${ocFloorAreaTotalPercent}" /></td>
										<td class="text-right view-content"><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${ocCarpetAreaTotal}" /></td>
										<td class="text-right view-content"><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${bpaCarpetAreaTotal}" /></td>
										<td class="text-right view-content"><c:set
												var="ocCarpetAreaTotalPercent"
												value="${(ocCarpetAreaTotal - bpaCarpetAreaTotal) * 100 / bpaCarpetAreaTotal}" />
											<fmt:formatNumber type="number" maxFractionDigits="2"
												value="${ocCarpetAreaTotalPercent}" /></td>
									</tr>
								</tfoot>
							</table>
							<div class="row add-border">
								<div class="col-sm-3 add-margin">
									<spring:message code="lbl.floor.count" />
									(
									<spring:message code="lbl.oc" />
									)
								</div>
								<div class="col-sm-3 add-margin view-content">
									<c:out value="${ocMap[map.key].size()}" default="N/A"></c:out>
								</div>
								<div class="col-sm-3 add-margin">
									<spring:message code="lbl.floor.count" />
									(
									<spring:message code="lbl.permit" />
									)
								</div>
								<div class="col-sm-3 add-margin view-content">
									<c:out value="${bpaFloorMap.size()}" default="N/A"></c:out>
								</div>
							</div>

							<div class="row add-border">
								<div class="col-sm-3 add-margin">
									<spring:message code="lbl.grnd.with.stair" />
									(
									<spring:message code="lbl.oc" />
									)
								</div>
								<div class="col-sm-3 add-margin view-content">
									<fmt:formatNumber type="number" maxFractionDigits="2"
										value="${ocBuildingHeightMap[map.key]}" />
								</div>
								<div class="col-sm-3 add-margin">
									<spring:message code="lbl.grnd.with.stair" />
									(
									<spring:message code="lbl.permit" />
									)
								</div>
								<div class="col-sm-3 add-margin view-content">
									<fmt:formatNumber type="number" maxFractionDigits="2"
										value="${bpaBuildingHeightMap[map.key]}" />
								</div>
							</div>

						</div>
					</c:forEach>
				</c:if>
			</div>
		</div>
	</div>
</div>
