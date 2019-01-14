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
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<form:form role="form" action="/bpa/holiday/create"
	modelAttribute="holiday" id="holidayListform"
	cssClass="form-horizontal form-groups-bordered"
	enctype="multipart/form-data">

	<div class="tab-content">
		<input type="hidden" id="holidayType" value="${holidayType}">
		<div id="applicant-info" class="tab-pane fade in active">
			<div class="panel panel-primary" data-collapsed="0">
				<div class="panel-heading">

				</div>
				<div class="col-sm-12">
					<table class="table table-bordered" id="holiday">
						<thead>
							<tr>
								<th class="col-sm-2 table-div-column">Holiday Date</th>
								<th class="col-sm-2 table-div-column">Holiday Type</th>
								<th class="col-sm-2 table-div-column">Description</th>
								<th class="col-sm-2 table-div-column">Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${not empty holiday.holidaysTemp}">
									<c:forEach items="${holiday.holidaysTemp}" var="holiday"
										varStatus="vs">
										<tr class="dynamicInput">
											<td><input type="text"
												name="holidaysTemp[${vs.index}].holidayDate"
												format="dd/MM/yyyy"
												class="form-control holidayDate datepicker" required="true" />
												<form:errors path="holidayDate"
													cssClass="add-margin error-msg" /></td>
											<td><form:select path="holidayType" id="holidayType"
													cssClass="form-control" cssErrorClass="form-control error"
													required="required">
													<form:option value="">
														<spring:message code="lbl.select" />
													</form:option>
													<form:options items="${holidayType}" />
												</form:select></td>
											<td><input type="text"
												name="holidaysTemp[${vs.index}].description"
												value="${holidaysTemp.description}"
												class="form-control patternvalidation " required="required" /></td>

											<td class="text-center"><span class="add-padding"><i
													class="fa fa-trash delete-row btn-sm btn-danger"
													id="deleteHolidayRow" aria-hidden="true"></i></span></td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td width="4%"><input name="holidaysTemp[0].holidayDate"
											id="date" format="dd/MM/yyyy" class="form-control datepicker"
											required="true" /></td>
										<td width="4%"><form:select
												path="holidaysTemp[0].holidayType" id="holidayType"
												cssClass="form-control" cssErrorClass="form-control error"
												required="required">
												<form:option value="">
													<spring:message code="lbl.select" />
												</form:option>
												<form:options items="${holidayType}" />
											</form:select></td>
										<td width="4%"><input type="text"
											name="holidaysTemp[0].description"
											class="form-control patternvalidation " required="required" /></td>

										<td class="text-center"><span class="add-padding"><i
												class="fa fa-trash delete-row btn-sm btn-danger"
												id="deleteHolidayRow" data-func="add" aria-hidden="true"></i></span></td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
					<div class="col-md-12 panel-title text-left">
						<button type="button" class="btn btn-secondary pull-right"
								id="addrow">
							<i class="fa fa-plus-circle" aria-hidden="true"></i> &nbsp;Add
							Row
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="text-center">
		<button type='submit' class='btn btn-primary' id="buttonSubmit">
			<spring:message code='lbl.create' />
		</button>
		<a href='javascript:void(0)' class='btn btn-default'
			onclick='self.close()'><spring:message code='lbl.close' /></a>
	</div>
</form:form>

<script
	src="<cdn:url value='/resources/global/js/bootstrap/bootstrap-datepicker.js' context='/egi'/>"></script>
<link rel="stylesheet"
	href="<cdn:url value='/resources/global/css/bootstrap/bootstrap-datepicker.css' context='/egi'/>">
<script
	src="<cdn:url value='/resources/js/app/holidaylist.js?rnd=${app_release_no}'/> "></script>
