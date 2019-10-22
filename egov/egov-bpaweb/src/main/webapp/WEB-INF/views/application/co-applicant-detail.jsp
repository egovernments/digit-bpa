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
<div class="panel-heading custom_form_panel_heading">
	<div class="panel-title">
		<spring:message code="lbl.coapplicant.details" />
	</div>
</div>
<div class="panel-body">
	<input type="hidden" id="genderList" value="${genderList}">
	<table id="coApplicantDetails"
		class="table table-striped table-bordered">
		<thead>
			<tr>
				<th><spring:message code="lbl.srl.no" /></th>
				<th><spring:message code="lbl.applicant.name" /></th>
				<th><spring:message code="lbl.mobileNo" /></th>
				<th><spring:message code="lbl.emailid" /></th>
				<th><spring:message code="lbl.gender" /></th>
				<th><spring:message code="lbl.action" /></th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty coApplicants}">
					<c:forEach items="${coApplicants}" var="coap" varStatus="counter">
						<tr>
							<td class="text-center"><span class="applcntSerialNo">${counter.index+1}</span></td>
							<td><form:input class="form-control patternvalidation name"
									data-pattern="alphabetspecialcharacters" maxlength="99"
									id="name"
									path="coApplicants[${counter.index}].coApplicant.name"
									disabled="true" /></td>
							<td><form:input
									class="form-control patternvalidation mobileNumber"
									data-pattern="number" maxlength="10"
									id="coApplicants[0].mobileNumber"
									path="coApplicants[${counter.index}].coApplicant.mobileNumber"
									disabled="true" /><form:errors path="coApplicants[0].mobileNumber"
								cssClass="error-msg" /></td>
							<td><form:input class="form-control emailId" maxlength="120"
									id="coApplicants[${counter.index}].coApplicant.emailId"
									path="coApplicants[${counter.index}].coApplicant.emailId"
									disabled="true" />
									<form:errors path="coApplicants[${counter.index}].coApplicant.emailId"
								cssClass="error-msg" /></td>
							<td><form:select
									path="coApplicants[${counter.index}].coApplicant.gender"
									id="coApplicants[${counter.index}].coApplicant.gender"
									cssClass="form-control gender"
									cssErrorClass="form-control error" disabled="true">
									<form:option value="">
										<spring:message code="lbl.select" />
									</form:option>
									<form:options items="${genderList}" />
								</form:select></td>
							<td></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td class="text-center"><span class="applcntSerialNo">1</span></td>
						<td><form:input class="form-control patternvalidation name"
								data-pattern="alphabetspecialcharacters" maxlength="99"
								id="name" path="coApplicants[0].coApplicant.name" /> <form:errors
								path="coApplicants[0].coApplicant.name" cssClass="error-msg" /></td>
						<td><form:input
								class="form-control patternvalidation mobileNumber"
								data-pattern="number" maxlength="10"
								id="coApplicants[0].coApplicant.mobileNumber"
								path="coApplicants[0].coApplicant.mobileNumber" />
							<form:errors path="coApplicants[0].coApplicant.mobileNumber"
								cssClass="error-msg" /></td>
						<td><form:input class="form-control emailId" maxlength="120"
								id="coApplicants[0].coApplicant.emailId"
								path="coApplicants[0].coApplicant.emailId" /> <span></span> <form:errors
								path="coApplicants[0].coApplicant.emailId" cssClass="error-msg" /></td>
						<td><form:select path="coApplicants[0].coApplicant.gender"
								id="coApplicants[0].coApplicant.gender"
								cssClass="form-control gender"
								cssErrorClass="form-control error">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${genderList}" />
							</form:select> <form:errors path="coApplicants[0].coApplicant.gender"
								cssClass="error-msg" /></td>
						<td></td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	<div class="text-right add-padding">
		<button type="button" class="btn btn-sm btn-primary addApplicantRow"
			id="addApplicantRow">
			<spring:message code="lbl.add.applcnt.btn" />
		</button>
	</div>
</div>
