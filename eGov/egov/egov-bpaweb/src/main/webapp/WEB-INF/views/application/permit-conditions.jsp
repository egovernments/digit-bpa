<%--
  ~ eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
  ~ accountability and the service delivery of the government  organizations.
  ~
  ~  Copyright (C) <2017>  eGovernments Foundation
  ~
  ~  The updated version of eGov suite of products as by eGovernments Foundation
  ~  is available at http://www.egovernments.org
  ~
  ~  This program is free software: you can redistribute it and/or modify
  ~  it under the terms of the GNU General Public License as published by
  ~  the Free Software Foundation, either version 3 of the License, or
  ~  any later version.
  ~
  ~  This program is distributed in the hope that it will be useful,
  ~  but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~  GNU General Public License for more details.
  ~
  ~  You should have received a copy of the GNU General Public License
  ~  along with this program. If not, see http://www.gnu.org/licenses/ or
  ~  http://www.gnu.org/licenses/gpl.html .
  ~
  ~  In addition to the terms of the GPL license to be adhered to in using this
  ~  program, the following additional terms are to be complied with:
  ~
  ~      1) All versions of this program, verbatim or modified must carry this
  ~         Legal Notice.
  ~ 	Further, all user interfaces, including but not limited to citizen facing interfaces,
  ~         Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
  ~         derived works should carry eGovernments Foundation logo on the top right corner.
  ~
  ~ 	For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
  ~ 	For any further queries on attribution, including queries on brand guidelines,
  ~         please contact contact@egovernments.org
  ~
  ~      2) Any misrepresentation of the origin of the material is prohibited. It
  ~         is required that all modified versions of this material be marked in
  ~         reasonable ways as different from the original version.
  ~
  ~      3) This license does not grant any rights to any user of the program
  ~         with regards to rights under trademark law for use of the trade names
  ~         or trademarks of eGovernments Foundation.
  ~
  ~  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
  --%>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="panel-heading toggle-header custom_form_panel_heading">
	<div class="panel-title">
		<spring:message code="lbl.permit.conditions" />
	</div>
</div>
<div class="panel-body" id="permitConditions">
	<c:if
			test="${bpaApplication.serviceType.code ne '14' && bpaApplication.serviceType.code ne '15'}">
		<div class="panel-heading">
			<div class="panel-title">Permit Conditions Type - I</div>
		</div>
		<table class="table table-bordered  multiheadertbl"
			   id="bpaDynamicPermitConditions">
			<thead>
			<tr>
				<th><spring:message code="lbl.srl.no" /></th>
				<th><spring:message code="lbl.isrequired" /></th>
				<th><spring:message code="lbl.condition" /></th>
				<th>Order No./Vide No.</th>
				<th>Date</th>
			</tr>
			</thead>
			<tbody>
			<c:choose>
				<c:when
						test="${not empty bpaApplication.dynamicPermitConditionsTemp}">
					<c:forEach var="modifiablePermitCondition"
							   items="${bpaApplication.dynamicPermitConditionsTemp}"
							   varStatus="modifyPCStatus">
						<tr>
							<td><form:hidden
									path="dynamicPermitConditionsTemp[${modifyPCStatus.index}].application"
									value="${bpaApplication.id}" /> <form:hidden
									path="dynamicPermitConditionsTemp[${modifyPCStatus.index}].permitCondition"
									value="${modifiablePermitCondition.permitCondition.id}" /> <form:hidden
									path="dynamicPermitConditionsTemp[${modifyPCStatus.index}].permitConditionType"
									value="DYNAMIC_PERMITCONDITION" /> <form:hidden
									path="dynamicPermitConditionsTemp[${modifyPCStatus.index}].orderNumber"
									value="${modifyPCStatus.index+1}" /> <c:out
									value="${modifyPCStatus.index+1}"></c:out></td>
							<c:choose>
								<c:when test="${modifiablePermitCondition.required}">
									<td><input type="checkbox"
											   data-change-to="dynamicPermitConditionsTemp[${modifyPCStatus.index}].required"
											   name="dynamicPermitConditionsTemp[${modifyPCStatus.index}].required"
											   class="modifiablePermitConditions" checked="checked"
											   value="${modifiablePermitCondition.required}" /></td>
								</c:when>
								<c:otherwise>
									<td><input type="checkbox"
											   data-change-to="dynamicPermitConditionsTemp[${modifyPCStatus.index}].required"
											   name="dynamicPermitConditionsTemp[${modifyPCStatus.index}].required"
											   class="modifiablePermitConditions"
											   value="${modifiablePermitCondition.required}" /></td>
								</c:otherwise>
							</c:choose>
							<td class="view-content" style="font-size: 97%;"><c:out
									value="${modifiablePermitCondition.permitCondition.description}"></c:out></td>
							<td><form:input
									class="form-control permitConditionNumber addremovemandatory" maxlength="30"
									path="dynamicPermitConditionsTemp[${modifyPCStatus.index}].permitConditionNumber" /><span
									class="error-msg display-hide">Required</span></td>
							<td><form:input
									path="dynamicPermitConditionsTemp[${modifyPCStatus.index}].permitConditiondDate"
									class="form-control datepicker permitConditiondDate addremovemandatory"
									data-date-end-date="0d" data-inputmask="'mask': 'd/m/y'" /><span
									class="error-msg display-hide">Required</span></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<c:forEach var="modifiablePermitCondition"
							   items="${modifiablePermitConditions}" varStatus="modifyPCStatus">
						<tr>
							<td><form:hidden
									path="dynamicPermitConditionsTemp[${modifyPCStatus.index}].application"
									value="${bpaApplication.id}" /> <form:hidden
									path="dynamicPermitConditionsTemp[${modifyPCStatus.index}].permitCondition"
									value="${modifiablePermitCondition.id}" /> <form:hidden
									path="dynamicPermitConditionsTemp[${modifyPCStatus.index}].permitConditionType"
									value="DYNAMIC_PERMITCONDITION" /> <form:hidden
									path="dynamicPermitConditionsTemp[${modifyPCStatus.index}].orderNumber"
									value="${modifyPCStatus.index+1}" /> <c:out
									value="${modifyPCStatus.index+1}"></c:out></td>
							<td class="view-content" style="font-size: 97%;"><form:checkbox
									path="dynamicPermitConditionsTemp[${modifyPCStatus.index}].required"
									id="modifiablePermitConditions"
									class="modifiablePermitConditions" checked="checked"></form:checkbox></td>
							<td class="view-content" style="font-size: 97%;"><c:out value="${modifiablePermitCondition.description}"></c:out>
							</td>
							<td><form:input
									class="form-control permitConditionNumber addremovemandatory"
									path="dynamicPermitConditionsTemp[${modifyPCStatus.index}].permitConditionNumber"
									required="true" maxlength="30" /><span class="error-msg display-hide">Required</span>
							</td>
							<td><form:input
									path="dynamicPermitConditionsTemp[${modifyPCStatus.index}].permitConditiondDate"
									class="form-control datepicker permitConditiondDate addremovemandatory"
									data-date-end-date="0d" data-inputmask="'mask': 'd/m/y'"
									required="true" /><span class="error-msg display-hide">Required</span></td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			</tbody>
		</table>
		<div class="panel-heading">
			<div class="panel-title">Permit Conditions Type - II</div>
		</div>
		<table class="table table-bordered  multiheadertbl"
			   id="bpaStaticPermitConditions">
			<thead>
			<tr>
				<th><spring:message code="lbl.srl.no" /></th>
				<th><spring:message code="lbl.isrequired" /></th>
				<th><spring:message code="lbl.condition" /></th>
			</tr>
			</thead>
			<tbody>
			<c:choose>
				<c:when
						test="${not empty bpaApplication.staticPermitConditionsTemp}">
					<c:forEach var="staticPermitCondition"
							   items="${bpaApplication.staticPermitConditionsTemp}"
							   varStatus="staticPCStatus">
						<tr>
							<td><form:hidden
									path="staticPermitConditionsTemp[${staticPCStatus.index}].application"
									value="${bpaApplication.id}" /> <form:hidden
									path="staticPermitConditionsTemp[${staticPCStatus.index}].permitCondition"
									value="${staticPermitCondition.permitCondition.id}" /> <form:hidden
									path="staticPermitConditionsTemp[${staticPCStatus.index}].permitConditionType"
									value="STATIC_PERMITCONDITION" /> <form:hidden
									path="staticPermitConditionsTemp[${staticPCStatus.index}].orderNumber"
									value="${staticPCStatus.index+1}" /> <c:out
									value="${staticPCStatus.index+1}"></c:out></td>
							<c:choose>
								<c:when test="${staticPermitCondition.required}">
									<td><input type="checkbox"
											   data-change-to="staticPermitConditionsTemp[${staticPCStatus.index}].required"
											   name="staticPermitConditionsTemp[${staticPCStatus.index}].required"
											   class="staticPermitConditions" checked="checked"
											   value="${staticPermitCondition.required}" /></td>
								</c:when>
								<c:otherwise>
									<td><input type="checkbox"
											   data-change-to="staticPermitConditionsTemp[${staticPCStatus.index}].required"
											   name="staticPermitConditionsTemp[${staticPCStatus.index}].required"
											   class="staticPermitConditions"
											   value="${staticPermitCondition.required}" /></td>
								</c:otherwise>
							</c:choose>
							<td class="view-content" style="font-size: 97%;"><c:out
									value="${staticPermitCondition.permitCondition.description}"></c:out></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<c:forEach var="staticPermitCondition" items="${permitConditions}"
							   varStatus="staticPCStatus">
						<tr>
							<td><form:hidden
									path="staticPermitConditionsTemp[${staticPCStatus.index}].application"
									value="${bpaApplication.id}" /> <form:hidden
									path="staticPermitConditionsTemp[${staticPCStatus.index}].permitCondition"
									value="${staticPermitCondition.id}" /> <form:hidden
									path="staticPermitConditionsTemp[${staticPCStatus.index}].permitConditionType"
									value="STATIC_PERMITCONDITION" /> <form:hidden
									path="staticPermitConditionsTemp[${staticPCStatus.index}].orderNumber"
									value="${staticPCStatus.index+1}" /> <c:out
									value="${staticPCStatus.index+1}"></c:out></td>
							<td><form:checkbox
									path="staticPermitConditionsTemp[${staticPCStatus.index}].required"
									class="staticPermitConditions" checked="checked" /></td>
							<td class="view-content" style="font-size: 97%;"><c:out value="${staticPermitCondition.description}"></c:out>
							</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			</tbody>
		</table>
	</c:if>
	<div class="panel-heading">
		<div class="panel-title">
			<spring:message code="lbl.addnl.permit.condition" />
		</div>
	</div>
	<table class="table table-bordered  multiheadertbl"
		   id="bpaAdditionalPermitConditions">
		<thead>
		<tr>
			<th><spring:message code="lbl.srl.no" /></th>
			<th><spring:message code="lbl.condition" /></th>
		</tr>
		</thead>
		<tbody>
		<c:choose>
			<c:when
					test="${not empty bpaApplication.additionalPermitConditionsTemp}">
				<c:forEach var="addnlPermitCondition"
						   items="${bpaApplication.additionalPermitConditionsTemp}"
						   varStatus="addnlPCStatus">
					<tr>
						<td class="text-center"><form:hidden
								path="additionalPermitConditionsTemp[${addnlPCStatus.index}].application"
								value="${bpaApplication.id}" />
							<form:hidden id="additionalPermitCondition"
										 path="additionalPermitConditionsTemp[${addnlPCStatus.index}].permitCondition" />
							<form:hidden
									path="additionalPermitConditionsTemp[${addnlPCStatus.index}].permitConditionType"
									value="ADDITIONAL_PERMITCONDITION" /> <form:hidden
									class="orderNo"
									path="additionalPermitConditionsTemp[${addnlPCStatus.index}].orderNumber"
									value="${addnlPermitCondition.orderNumber}" />
							<c:out value="${addnlPCStatus.index+1}"></c:out></td>
						<td><form:textarea
								path="additionalPermitConditionsTemp[${addnlPCStatus.index}].additionalPermitCondition"
								rows="2" maxlength="500" data-pattern="alphanumericspecialcharacters"
								class="form-control patternvalidation additionalPermitCondition" value=""></form:textarea></td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td class="text-center"><form:hidden
							path="additionalPermitConditionsTemp[0].application"
							value="${bpaApplication.id}" /> <form:hidden
							id="additionalPermitCondition"
							path="additionalPermitConditionsTemp[0].permitCondition"
							value="${additionalPermitCondition.id}" /> <form:hidden
							path="additionalPermitConditionsTemp[0].permitConditionType"
							value="ADDITIONAL_PERMITCONDITION" /> <form:hidden
							class="orderNo"
							path="additionalPermitConditionsTemp[0].orderNumber" value="1" />
						1</td>
					<td><form:textarea
							path="additionalPermitConditionsTemp[0].additionalPermitCondition"
							rows="2" maxlength="500" data-pattern="alphanumericspecialcharacters"
							class="form-control patternvalidation additionalPermitCondition" value=""></form:textarea></td>
				</tr>
			</c:otherwise>
		</c:choose>
		</tbody>
	</table>
	<div class="text-right add-padding">
		<button type="button" class="btn btn-sm btn-primary"
				id="addAddnlPermitRow">Add Additional Conditions</button>
	</div>
</div>