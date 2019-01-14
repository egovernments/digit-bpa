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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<div class="col-md-12">
		<c:if test="${not empty message}">
			<div class="alert alert-success" role="alert">
				<spring:message code="${message}" />
			</div>
		</c:if>
		<div class="panel panel-primary" data-collapsed="0">
			<div class="panel-heading">
				<div class="panel-title">
					<spring:message code="title.holiday.view" />
				</div>
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
										<td><input type="date"
											name="holidaysTemp[${vs.index}].holidayDate" id="date"
											format="dd/MM/yyyy" class="form-control datepicker"
											data-date-end-date="0d" required="true" /></td>
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

										<td><span class="add-padding"><i
												class="fa fa-trash delete-row" aria-hidden="true"></i></span></td>
									</tr>
								</c:forEach>
							</c:when>
						</c:choose>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>





