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

<form:form role="form" action="/bpa/checklistservicetypemapping/createmapping"
	modelAttribute="checklistServicetype" cssClass="form-horizontal form-groups-bordered"
	enctype="multipart/form-data">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-primary" data-collapsed="0">
				<div class="panel-heading">
					<div class="panel-title">
						<spring:message code="lbl.checklist.servicetype.main.title" />
					</div>
				</div>
				<div class="panel-body">
					<div class="form-group">
					Service Type : ${serviceType.description} </br> </br> Checklist Type : ${checklists[0].checklistType.description}
					</br> </br>
                  <c:if test="${not empty checklists}">
                    <table class="table table-bordered table-hover multiheadertbl">
							<thead>
								<tr>
									<th><spring:message code="lbl.checklist" /></th>
									<th><spring:message code="lbl.isrequired" /></th>
									<th><spring:message code="lbl.ismandatory" /></th>
								</tr>
							</thead>
								<c:forEach items="${checklists}" var="checklist" varStatus="vs">
									<tr>
										<td class="form-control">${checklist.description}</td>
										<td><input type="checkbox" name="mappingList[${vs.index}].required"/></td>
										<td><input type="checkbox" name="mappingList[${vs.index}].mandatory"/></td>
										<div class="hidden">
										<form:select path="mappingList[${vs.index}].checklist">
											<c:forEach items="${checklists}" var="check">
												<option value="${check.id}" title="${check.description}"
													<c:if test="${check.id == checklist.id}"> Selected </c:if>>${check.description}</option>
											</c:forEach>
										</form:select>
										<form:select path="mappingList[${vs.index}].serviceType">
											<c:forEach items="${servicetypes}" var="service">
												<option value="${service.id}" title="${service.description}"
													<c:if test="${service.id == serviceType.id}"> Selected </c:if>>${service.description}</option>
											</c:forEach>
										</form:select>
										</div>
									</tr>
								</c:forEach>
						</table>
						</c:if>
						<c:if test="${empty checklists}">
							<tr>No Checklists found for selected checklist type</tr>
						</c:if>
						<div class="text-center">
						<c:if test="${not empty checklists}">
							<button type='submit' class='btn btn-primary' id="buttonSubmit">
								<spring:message code='lbl.create' />
							</button>
						</c:if>
							<a href='javascript:void(0)' class='btn btn-default'
								onclick='self.close()'><spring:message code='lbl.close' /></a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form:form>


