<%--
  ~    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
  ~    accountability and the service delivery of the government  organizations.
  ~
  ~     Copyright (C) 2018  eGovernments Foundation
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

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>

<div class="row" id="page-content">
	<div class="col-md-12">
		<c:if test="${not empty message}">
			<div class="alert alert-success" role="alert">
				<strong>${message}</strong>
			</div>
		</c:if>
		
		<form:form role="id" method="post" modelAttribute="bpaFeeMapping"
			class="form-horizontal form-groups-bordered">
			
			<div class="panel panel-primary" data-collapsed="0">
				<div class="panel-heading">
					<div class="panel-title">
						<strong><spring:message code="title.fee.edit" /></strong>
					</div>
				</div>
				<div class="panel-body custom-form">
					<div class="form-group">
						<label class="col-sm-2 control-label text-right"><spring:message
								code="lbl.name" /> <span id="mandatory" class="mandatory"></span></label>
						<div class="col-sm-3 add-margin">
							<form:input path="bpaFeeCommon.name" id="name"
								cssClass="form-control" cssErrorClass="form-control error"
								required="required" maxLength="150" />
						</div>
						<label class="col-sm-2 control-label text-right"><spring:message
								code="lbl.fees.code" /> <span id="mandatory" class="mandatory"></span></label>
						<div class="col-sm-3 add-margin">
							<form:input path="bpaFeeCommon.code" id="code"
								cssClass="form-control patternvalidation"
								data-pattern="alphabets" cssErrorClass="form-control error"
								readonly="true" required="required" maxLength="5" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right"><spring:message
								code="lbl.description" /> <span id="mandatory" class="mandatory"></span></label>
						<div class="col-sm-3 add-margin">
							<form:input path="bpaFeeCommon.description" id="description"
								cssClass="form-control" cssErrorClass="form-control error"
								required="required" maxLength="150" />
						</div>
						<label class="col-sm-2 control-label text-right"><spring:message
								code="lbl.fees.accounthead" /> <span id="mandatory"
							class="mandatory"></span></label>
						<div class="col-sm-3 add-margin">
							<form:input path="bpaFeeCommon.glcode.glcode" id="glcode"
								cssClass="form-control patternvalidation"
								data-pattern="alphabets" cssErrorClass="form-control error"
								readonly="true" required="required" maxLength="5" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="lbl.fees.category" /></label>
						<div class="col-sm-3 add-margin">
							<form:label cssClass="form-control" path="bpaFeeCommon.category">${bpaFeeMapping.bpaFeeCommon.category.name}</form:label>
						</div>
						<form:hidden path="bpaFeeCommon"
									name="bpaFeeCommon" id="bpaFeeCommon"
									class="form-control table-input hidden-input bpaFeeCommon" />
					</div>
					<div class="panel-heading">
						<div class="panel-title text-left">
							<strong><spring:message code="lbl.fees.details" /></strong>
						</div>
					</div>
					<div class="col-md-12">
						<table class="table table-bordered" id="subcat">
							<thead>
								<tr>
									<th class="text-center"><spring:message
											code='lbl.applctn.type' /></th>
									<th class="text-center"><spring:message
											code='lbl.service.type' /></th>
									<th class="text-center"><spring:message
											code='lbl.fee.subtype' /></th>
									<th class="text-center"><spring:message
											code='lbl.fees.calculationtype' /></th>
									<th class="text-center"><spring:message code='lbl.amount' /></th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${not empty bpaFeeMapping.bpaFeeMapTemp}">
										<c:forEach items="${bpaFeeMapping.bpaFeeMapTemp}"
											var="bpaFeeDetail" varStatus="item">
											<c:set var="display" value="table-row" />

											<tr style="display:${display}">
												<td><form:select
														path="bpaFeeMapTemp[${item.index}].applicationType"
														id="bpaFeeDetail[${item.index}].applicationType"
														value="${bpaFeeDetail.applicationType}"
														cssClass="form-control applicationType" disabled="true"
														required="required">
														<form:option value="">
															<spring:message code="lbl.select" />
														</form:option>
														<form:options items="${applicationTypes}" itemLabel="feeApplicationTypeVal"/>
													</form:select> <form:errors
														path="bpaFeeMapTemp[${item.index}].applicationType"
														cssClass="add-margin error-msg" /></td>
												<td><form:select
														path="bpaFeeMapTemp[${item.index}].serviceType"
														id="bpaFeeDetail[${item.index}].serviceType"
														value="${bpaFeeDetail.serviceType.description}"
														cssClass="form-control serviceType" disabled="true"
														required="required">
														<form:option value="">
															<spring:message code="lbl.select" />
														</form:option>
														<form:options items="${serviceTypeList}" itemValue="id"
															itemLabel="description" />
													</form:select> <form:errors
														path="bpaFeeMapTemp[${item.index}].serviceType"
														cssClass="add-margin error-msg" /></td>
												<td><form:select
														path="bpaFeeMapTemp[${item.index}].feeSubType"
														id="bpaFeeDetail[${item.index}].feeSubType"
														value="${bpaFeeDetail.feeSubType}"
														cssClass="form-control feeSubType" disabled="true"
														required="required">
														<form:option value="">
															<spring:message code="lbl.select" />
														</form:option>
														<form:options items="${feeSubTypes}" itemLabel="feeSubTypeVal" />
													</form:select> <form:errors
														path="bpaFeeMapTemp[${item.index}].feeSubType"
														cssClass="add-margin error-msg" /></td>
												<td><form:select
														path="bpaFeeMapTemp[${item.index}].calculationType"
														id="bpaFeeDetail[${item.index}].calculationType"
														value="${bpaFeeDetail.calculationType}"
														cssClass="form-control calculationType" disabled="true"
														required="required">
														<form:option value="">
															<spring:message code="lbl.select" />
														</form:option>
														<form:options items="${calculationTypes}" />
													</form:select> <form:errors
														path="bpaFeeMapTemp[${item.index}].calculationType"
														cssClass="add-margin error-msg" /></td>
												<td><form:input type="text"
														id="bpaFeeDetail[${item.index}].amount"
														value="${bpaFeeDetail.amount}"
														path="bpaFeeMapTemp[${item.index}].amount"
														data-pattern="number" maxlength="8"
														cssClass="form-control text-right amount" /></td>
														<form:hidden path="bpaFeeMapTemp[${item.index}]"
									name="bpaFeeMapTemp" id="bpaFeeMapTemp"
									class="form-control table-input hidden-input bpaFeeMapTemp" />

											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td><form:select path="bpaFeeMapTemp[0].applicationType"
													id="bpaFeeDetail[0].applicationType"
													cssClass="form-control applicationType" required="required">
													<form:option value="">
														<spring:message code="lbl.select" />
													</form:option>
													<form:options items="${applicationTypes}" />
												</form:select> <form:errors path="bpaFeeMapTemp[0].applicationType"
													cssClass="add-margin error-msg" /></td>
											<td><form:select path="bpaFeeMapTemp[0].serviceType"
													id="bpaFeeDetail[0].serviceType"
													cssClass="form-control serviceType" required="required">
													<form:option value="">
														<spring:message code="lbl.select" />
													</form:option>
													<form:options items="${serviceTypeList}" itemValue="id"
														itemLabel="description" />
												</form:select> <form:errors path="bpaFeeMapTemp[0].serviceType"
													cssClass="add-margin error-msg" /></td>
											<td><form:select path="bpaFeeMapTemp[0].feeSubType"
													id="bpaFeeDetail[0].feeSubType"
													cssClass="form-control feeSubType" required="required">
													<form:option value="">
														<spring:message code="lbl.select" />
													</form:option>
													<form:options items="${feeSubTypes}" />
												</form:select> <form:errors path="bpaFeeMapTemp[0].feeSubType"
													cssClass="add-margin error-msg" /></td>
											<td><form:select path="bpaFeeMapTemp[0].calculationType"
													id="bpaFeeDetail[0].calculationType"
													cssClass="form-control calculationType" required="required">
													<form:option value="">
														<spring:message code="lbl.select" />
													</form:option>
													<form:options items="${calculationTypes}" />
												</form:select> <form:errors path="bpaFeeMapTemp[0].calculationType"
													cssClass="add-margin error-msg" /></td>

											<td><form:input type="text" id="amount"
													path="bpaFeeMapTemp[0].amount" data-pattern="number"
													maxlength="8" cssClass="form-control text-right amount" />
											</td>
											<td align="center"><span class="add-padding"><i
													class="fa fa-trash" aria-hidden="true" id="deleterow"></i></span>
											</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
							<tfoot>
							</tfoot>
						</table>
					</div>
				</div>
			</div>
	</div>
	<div class="form-group">
		<div class="text-center">
			<button type="submit" class="btn btn-primary">
				<spring:message code="lbl.update" />
			</button>
			<a href='javascript:void(0)' class='btn btn-default'
				onclick='window.location="."'><spring:message code='lbl.back' /></a>
			<button type="button" class="btn btn-default" data-dismiss="modal"
				onclick="self.close()">
				<spring:message code="lbl.close" />
			</button>
		</div>
	</div>
	</form:form>
</div>
<script
	src="<cdn:url  value='/resources/js/app/fee-search.js?rnd=${app_release_no}'/>"></script>