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

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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
						<strong><spring:message code="title.fee.view" /></strong>
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
							<div class="col-md-12 panel-title text-left">
								<spring:message code="lbl.fees.details" />
							</div>
						</div>
						<div class="col-sm-12">
							<table class="table table-bordered" id="bpaFeeMapping">
								<thead>
									<tr>
										<th class="col-sm-2 table-div-column"><spring:message
												code='lbl.applctn.type' /></th>
										<th class="col-sm-2 table-div-column"><spring:message
												code='lbl.service.type' /></th>
										<th class="col-sm-2 table-div-column"><spring:message
												code='lbl.fee.subtype' /></th>
										<th class="col-sm-2 table-div-column"><spring:message
												code='lbl.fees.calculationtype' /></th>
										<th class="col-sm-2 table-div-column"><spring:message
												code='lbl.amount' /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="feeMap" items="${bpaFeeMapping.bpaFeeMapTemp}"
										varStatus="status">
										<tr>
											<td><form:input type="hidden"
													id="bpaFeeMapTemp[${status.index}].applicationType"
													path="bpaFeeMapTemp[${status.index}].applicationType" />
												<input type="text" id="table_applicationType${status.index}"
												class="form-control" readonly="readonly"
												style="text-align: center" value="${feeMap.applicationType.feeApplicationTypeVal}" />

											</td>
											<td><form:input type="hidden"
													id="bpaFeeMapTemp[${status.index}].serviceType"
													path="bpaFeeMapTemp[${status.index}].serviceType" />
												<input type="text" id="table_serviceType${status.index}"
												class="form-control" readonly="readonly"
												style="text-align: center"
												value="${feeMap.serviceType.description}" /></td>
											<td><form:input type="hidden"
													id="bpaFeeMapTemp[${status.index}].feeSubType"
													path="bpaFeeMapTemp[${status.index}].feeSubType" />
												<input type="text" id="table_feeSubType${status.index}"
												class="form-control" readonly="readonly"
												style="text-align: center" value="${feeMap.feeSubType.feeSubTypeVal}" />
											</td>
											<td><form:input type="hidden"
													id="bpaFeeMapTemp[${status.index}].calculationType"
													path="bpaFeeMapTemp[${status.index}].calculationType" />
												<input type="text" id="table_calculationType${status.index}"
												class="form-control" readonly="readonly"
												style="text-align: center" value="${feeMap.calculationType}" />
											</td>
											<td><form:input type="hidden"
													id="bpaFeeMapTemp[${status.index}].amount"
													path="bpaFeeMapTemp[${status.index}].amount" />
												<input type="text" id="table_amount${status.index}"
												class="form-control" readonly="readonly"
												style="text-align: center" value="${feeMap.amount}" /></td>

										</tr>
									</c:forEach>
								</tbody>
							</table>

						</div>
						</div>
						</div>
						</div>
						<div class="form-group text-center">

							<button type="button" class="btn btn-default"
								data-dismiss="modal" onclick="window.close();">
								<spring:message code="lbl.close" />
							</button>
						</div>
					</form:form>
	</div>
</div>
<script
	src="<cdn:url value='/resources/js/app/bpafee.js?rnd=${app_release_no}'/>"></script>