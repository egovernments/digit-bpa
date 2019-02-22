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
<div class="tab-content">

	<div class="row">


		<div class="col-md-12">

			<input type="hidden" id="applicationTypes"
				value="${applicationTypes}"> <input type="hidden"
				id="feeSubTypes" value="${feeSubTypes}"> <input
				type="hidden" id="calculationTypes" value="${calculationTypes}">
			<input type="hidden" id="serviceTypeList" value="${serviceTypeList}">

			<div class="panel panel-primary" data-collapsed="0">
				<div class="panel-heading">
					<div class="panel-title">
						<spring:message code="title.create.fee" />
					</div>
				</div>
				<div class="panel-body">
					<form:form role="form" action="create"
						modelAttribute="bpaFeeMapping" id="bpafeeform"
						cssClass="form-horizontal form-groups-bordered">
						<spring:hasBindErrors name="feeMatrix">
							<form:errors path="" cssClass="error-msg add-margin" />
							<br />
						</spring:hasBindErrors>

						<div class="form-group">
							<label class="col-sm-3 control-label"> <spring:message
									code="lbl.fee.code" /><span class="mandatory"></span>
							</label>
							<div class="col-sm-3 add-margin">
								<form:input type="text" id="code" path="bpaFeeCommon.code"
									autocomplete="off" cssClass="form-control name" />
								<form:errors path="bpaFeeCommon.code"
									cssClass="add-margin error-msg" />
							</div>

							<label class="col-sm-3 control-label"> <spring:message
									code="lbl.fees.name" /><span class="mandatory"></span>
							</label>
							<div class="col-sm-3 add-margin">
								<form:input type="text" id="name" path="bpaFeeCommon.name"
									autocomplete="off" cssClass="form-control name" />
								<form:errors path="bpaFeeCommon.name"
									cssClass="add-margin error-msg" />
							</div>

						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label"> <spring:message
									code="lbl.description" /><span class="mandatory"></span>
							</label>
							<div class="col-sm-3 add-margin">
								<form:input type="text" id="description" autocomplete="off"
									path="bpaFeeCommon.description"
									cssClass="form-control description" />
								<form:errors path="bpaFeeCommon.description"
									cssClass="add-margin error-msg" />
							</div>
							<label class="col-sm-3 control-label"> <spring:message
									code="lbl.fees.accounthead" /><span class="mandatory"></span>
							</label>
							<div class="col-sm-3 add-margin">
								<input type="text" id="accountDetailGlcode"
									class="form-control table-input accountDetailGlcode"
									data-errormsg="Account Code is mandatory!" data-idx="0"
									data-optional="0"
									placeholder="Type first 3 letters of Account code" />
								<form:hidden path="bpaFeeCommon.glcode.glcode"
									name="accountglcode" id="accountglcode"
									class="form-control table-input hidden-input accountglcode" />
								<form:hidden path="bpaFeeCommon.glcode" name="accountglcodeid"
									id="accountglcodeid"
									class="form-control table-input hidden-input accountglcodeid" />

								<form:errors path="bpaFeeCommon.glcode"
									cssClass="add-margin glcodeerror error-msg" />
							</div>
						</div>
						<div class="form-group">

							<label class="col-sm-3 control-label"> <spring:message
									code="lbl.fees.category" /><span class="mandatory"></span>
							</label>
							<div class="col-sm-3 add-margin">
								<form:input type="text" id="category" readonly="true"
									value="${category}" path="bpaFeeCommon.category"
									cssClass="form-control category" />
								<form:errors path="bpaFeeCommon.category"
									cssClass="add-margin error-msg" />
								<%-- 						   <format:input type="hidden"	value="${category}" id="category" path="bpaFeeCommon.category" />
 --%>
							</div>

						</div>




						<div class="panel-heading">
							<div class="col-md-12 panel-title text-left">
								<spring:message code="lbl.fees.details" />
							</div>
						</div>
						<div class="col-sm-12">
							<div align="center">
								<form:errors path="applicationType" cssClass="error-msg" />
							</div>
							<table class="table table-bordered" id="bpaFeeMapping">
								<thead>
									<tr>
										<th class="col-sm-2 table-div-column"><spring:message
												code='lbl.service.type' /></th>
										<th class="col-sm-2 table-div-column"><spring:message
												code='lbl.applctn.type' /></th>
										<th class="col-sm-2 table-div-column"><spring:message
												code='lbl.fee.subtype' /></th>
										<th class="col-sm-2 table-div-column"><spring:message
												code='lbl.fees.calculationtype' /></th>
										<th class="col-sm-2 table-div-column"><spring:message
												code='lbl.amount' /></th>
										<th class="col-sm-2 table-div-column"><spring:message
												code='lbl.actions' /></th>
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
															path="bpaFeeMapTemp[${item.index}].serviceType"
															id="bpaFeeMapTemp[${item.index}].serviceType"
															value="${bpaFeeDetail.serviceType}"
															cssClass="form-control serviceType" required="required">
															<form:option value="">
																<spring:message code="lbl.select" />
															</form:option>
															<form:options items="${serviceTypeList}" itemValue="id"
																itemLabel="description" />
														</form:select> <form:errors
															path="bpaFeeMapTemp[${item.index}].serviceType"
															cssClass="add-margin error-msg" /></td>
													<td><form:select
															path="bpaFeeMapTemp[${item.index}].applicationType"
															id="bpaFeeMapTemp[${item.index}].applicationType"
															value="${bpaFeeDetail.applicationType}"
															cssClass="form-control applicationType"
															required="required">
															<form:option value="">
																<spring:message code="lbl.select" />
															</form:option>
															<form:options items="${applicationTypes}" />
														</form:select> <form:errors
															path="bpaFeeMapTemp[${item.index}].applicationType"
															cssClass="add-margin error-msg" /></td>
													<td><form:select
															path="bpaFeeMapTemp[${item.index}].feeSubType"
															id="bpaFeeMapTemp[${item.index}].feeSubType"
															value="${bpaFeeDetail.feeSubType}"
															cssClass="form-control feeSubType"
															required="required">
															<form:option value="">
																<spring:message code="lbl.select" />
															</form:option>
															<form:options items="${feeSubTypes}" />
														</form:select> <form:errors
															path="bpaFeeMapTemp[${item.index}].feeSubType"
															cssClass="add-margin error-msg" /></td>
													<td><form:select
															path="bpaFeeMapTemp[${item.index}].calculationType"
															id="bpaFeeMapTemp[${item.index}].calculationType"
															value="${bpaFeeDetail.calculationType}"
															cssClass="form-control calculationType"
															required="required">
															<form:option value="">
																<spring:message code="lbl.select" />
															</form:option>
															<form:options items="${calculationTypes}" />
														</form:select> <form:errors
															path="bpaFeeMapTemp[${item.index}].calculationType"
															cssClass="add-margin error-msg" /></td>
													<td><form:input type="text"
															id="bpaFeeMapTemp[${item.index}].amount"
															path="bpaFeeMapTemp[${item.index}].amount"
															value="${bpaFeeDetail.amount}"
															class="form-control patternvalidation text-right amount"
															data-pattern="number" maxlength="8" required="required" /></td>
													<td align="center"><span class="add-padding"><i
															class="fa fa-trash" aria-hidden="true" id="deleterow"></i></span>
													</td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr>

												<td><form:select path="bpaFeeMapTemp[0].serviceType"
														id="bpaFeeMapTemp[0].serviceType"
														cssClass="form-control serviceType" required="required">
														<form:option value="">
															<spring:message code="lbl.select" />
														</form:option>
														<form:options items="${serviceTypeList}" itemValue="id"
															itemLabel="description" />
													</form:select> <form:errors path="bpaFeeMapTemp[0].serviceType"
														cssClass="add-margin error-msg" /></td>
												<td><form:select
														path="bpaFeeMapTemp[0].applicationType"
														id="bpaFeeMapTemp[0].applicationType"
														cssClass="form-control applicationType"
														required="required">
														<form:option value="">
															<spring:message code="lbl.select" />
														</form:option>
														<form:options items="${applicationTypes}" />
													</form:select> <form:errors path="bpaFeeMapTemp[0].applicationType"
														cssClass="add-margin error-msg" /></td>
												<td><form:select path="bpaFeeMapTemp[0].feeSubType"
														id="bpaFeeMapTemp[0].feeSubType"
														cssClass="form-control feeSubType" required="required">
														<form:option value="">
															<spring:message code="lbl.select" />
														</form:option>
														<form:options items="${feeSubTypes}" />
													</form:select> <form:errors path="bpaFeeMapTemp[0].feeSubType"
														cssClass="add-margin error-msg" /></td>
												<td><form:select
														path="bpaFeeMapTemp[0].calculationType"
														id="bpaFeeMapTemp[0].calculationType"
														cssClass="form-control calculationType"
														required="required">
														<form:option value="">
															<spring:message code="lbl.select" />
														</form:option>
														<form:options items="${calculationTypes}" />
													</form:select> <form:errors path="bpaFeeMapTemp[0].calculationType"
														cssClass="add-margin error-msg" /></td>

												<td><form:input type="text"
														name="bpaFeeMapTemp[0].amount"
														path="bpaFeeMapTemp[0].amount"
														class="form-control patternvalidation text-right"
														data-pattern="number" maxlength="8" required="required" />
												</td>
												<td class="text-center"><span class="add-padding"><i
														class="fa fa-trash delete-row btn-sm btn-danger"
														id="deleteFeeMapRow" data-func="add" aria-hidden="true"></i></span></td>
											</tr>
										</c:otherwise>
									</c:choose>

								</tbody>
							</table>
							<div class="col-md-12 panel-title text-left">
								<button type="button" class="btn btn-secondary pull-right"
									id="addrow">
									<i class="fa fa-plus-circle" aria-hidden="true"></i> &nbsp;
									<spring:message code='lbl.btn.add.row' />
								</button>
							</div>
						</div>
						<div class="form-group text-center">
							<button type="submit" class="btn btn-primary" id="search">
								<spring:message code="lbl.save" />
							</button>
							<button type="reset" class="btn btn-default">
								<spring:message code="lbl.reset" />
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal" onclick="window.close();">
								<spring:message code="lbl.close" />
							</button>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
<script
	src="<cdn:url value='/resources/js/app/bpafee-helper.js?rnd=${app_release_no}'/>"></script>