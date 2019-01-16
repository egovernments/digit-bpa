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

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<div class="row">
	<div class="col-md-12">
		<form:form role="form" action="" method="post"
			modelAttribute="applicationFee" id="applicationFee"
			cssClass="form-horizontal form-groups-bordered"
			enctype="multipart/form-data">

			<div class="panel panel-primary" data-collapsed="0">
				<div class="panel-body custom-form ">
					<div class="panel-heading custom_form_panel_heading">
						<div class="panel-title">
							<spring:message code="lbl.basic.info" />
						</div>
					</div>
					<div class="panel-body">
						<div class="row add-border">
							<div class="col-sm-3 add-margin">
								<spring:message code="lbl.application.no" />
							</div>
							<div class="col-sm-3 add-margin view-content">
								<c:out value="${applicationFee.application.applicationNumber}"
									default="N/A"></c:out>
							</div>
							<div class="col-sm-3 add-margin">
								<spring:message code="lbl.application.date" />
							</div>
							<div class="col-sm-3 add-margin view-content">
								<fmt:formatDate value="${applicationFee.application.applicationDate}" pattern="dd/MM/yyyy" var="applicationDate" />
								<c:out value="${applicationDate}" default="N/A"></c:out>
							</div>
						</div>
						<div class="row add-border">
							<div class="col-sm-3 add-margin">
								<spring:message code="lbl.service.type" />
							</div>
							<div class="col-sm-3 add-margin view-content">
								<c:out
									value="${applicationFee.application.serviceType.description}"
									default="N/A"></c:out>
							</div>
							<div class="col-sm-3 add-margin">Amenity Type</div>
							<c:choose>
								<c:when test="${empty applicationFee.application.amenityName}">
									<div class="col-sm-3 add-margin view-content">
										<c:out value="N/A"/>
									</div>
								</c:when>
								<c:otherwise>
									<div class="col-sm-3 add-margin view-content">
									<c:out value="${applicationFee.application.amenityName}"/>
									</div>
								</c:otherwise>
							</c:choose>
						</div>
						<div class="row add-border">
							<div class="col-sm-3 add-margin">
								<spring:message code="lbl.admission.fees" />
							</div>
							<div class="col-sm-3 add-margin view-content">
								<c:out value="${applicationFee.application.admissionfeeAmount}"
									default="N/A"></c:out>
							</div>
						</div>
					</div>
					<div class="panel-heading custom_form_panel_heading">
						<div class="panel-title">
							<spring:message code="lbl.fee.details" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-3 add-margin">
							<form:hidden path="application" id="feeapplicationid"
								value="${applicationFee.application.id}" />
							<form:hidden path="status" id="feeapplicationstatusid"
								value="${applicationFee.status.id}" />

							<form:hidden path="id" id="id" value="${applicationFee.id}" />

						</div>
					</div>

					<c:choose>
						<c:when test="${!applicationFeeDetail.isEmpty()}">
							<div class="form-group view-content header-color hidden-xs">
								<div class="col-sm-5 text-right">
									<spring:message code="lbl.applicationFee.feeType" />
								</div>
								<div class="col-sm-2 text-right">
									<spring:message code="lbl.applicationFee.amount" />
								</div>
							</div>
							<c:forEach var="docs"
								items="${applicationFee.applicationFeeDetail}"
								varStatus="status">
								<div class="form-group">
									<div class="col-sm-5 add-margin check-text text-right">
										<c:out value="${docs.bpaFee.description}" />
										<form:hidden id="applicationFeeDetail${status.index}id"
											path="applicationFeeDetail[${status.index}].id"
											value="${docs.id}" />
										<form:hidden id="applicationFeeDetail${status.index}bpaFee"
											path="applicationFeeDetail[${status.index}].bpaFee"
											value="${docs.bpaFee.id}" />
										<form:hidden
											id="applicationFeeDetail${status.index}applicationFee"
											path="applicationFeeDetail[${status.index}].applicationFee"
											value="${docs.applicationFee.id}" />

									</div>
									<div class="col-sm-2 add-margin text-right">
										<c:choose>
											<c:when
												test="${docs.bpaFee.description eq 'Other Fees'}">
												<input type="hidden" id="currentPermitFee" value="${docs.amount}">
												<form:input class="form-control patternvalidation text-right otherFees"
													data-pattern="number" maxlength="10"
													id="applicationFeeDetail${status.index}amount"
													value="${docs.amount}"
													path="applicationFeeDetail[${status.index}].amount" />
											</c:when>
											<c:otherwise>
												<form:input class="form-control patternvalidation text-right"
													data-pattern="decimalvalue" maxlength="10"
													id="applicationFeeDetail${status.index}amount"
													value="${docs.amount}" disabled="true"
													path="applicationFeeDetail[${status.index}].amount" />
											</c:otherwise>
										</c:choose>
										<form:errors
											path="applicationFeeDetail[${status.index}].amount"
											cssClass="add-margin error-msg" />
									</div>
								</div>
							</c:forEach>
						</c:when>
					</c:choose>

					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.modify.fee.reason" /><span class="mandatory"></span></label>
						<div class="col-sm-7 add-margin text-center">
							<form:textarea class="form-control patternvalidation"
								data-pattern="alphanumericspecialcharacters" rows="3" maxlength="510"
								id="modifyFeeReason" path="modifyFeeReason" required="required" />
							<form:errors path="modifyFeeReason"
								cssClass="add-margin error-msg" />
						</div>
					</div>

				</div>
			</div>
			<div class="text-center">
				<button type="submit" class='btn btn-primary' id="createFeeSubmit">
					<spring:message code='lbl.applicationFee.create' />
				</button>
				<a href='javascript:void(0)' class='btn btn-default'
					onclick='self.close()'><spring:message code='lbl.close' /></a>
			</div>

		</form:form>
	</div>
</div>

<script
	src="<cdn:url value='/resources/global/js/egov/inbox.js?rnd=${app_release_no}' context='/egi'/>"></script>
