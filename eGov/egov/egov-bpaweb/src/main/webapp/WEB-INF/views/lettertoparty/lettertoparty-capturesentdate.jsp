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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<form:form role="form" action="/bpa/lettertoparty/update" method="post"
	modelAttribute="lettertoParty" id="lettertoPartyform"
	cssClass="form-horizontal form-groups-bordered"
	enctype="multipart/form-data">
	<div class="row">
		<div class="col-md-12">
				<div class="panel panel-primary" data-collapsed="0">
					<jsp:include page="../application/viewapplication-details.jsp"></jsp:include>
					<div class="panel-heading custom_form_panel_heading">
						<div class="panel-title">
							<spring:message code="lbl.lp.details" />
						</div>
					</div>
					<div class="panel-body">
						<div class="row add-border">
							<div class="col-sm-3 add-margin">
								<spring:message code="lbl.lpNumber" />
							</div>
							<div class="col-sm-3 add-margin view-content">
								<c:out value="${lettertoParty.lpNumber}"></c:out>
							</div>
							<div class="col-sm-3 add-margin">
								<spring:message code="lbl.lp.date" />
							</div>
							<div class="col-sm-3 add-margin view-content">
								<fmt:formatDate value="${lettertoParty.letterDate}" pattern="dd/MM/yyyy" var="letterDate" />
								<c:out value="${letterDate}"></c:out>
							</div>
							
							<form:hidden path="application" id="applicationId"
								value="${application.id}" />

							<form:hidden path="letterDate" id="letterDate"
								value="${letterDate}" />
							<input type="hidden" id='lettertoParty' name="lettertoParty"
								value="${lettertoParty.id}">
						</div>
						<div class="row add-border">
							<div class="col-sm-3 add-margin">
								<spring:message code="lbl.lpreason" />
							</div>
							<div class="col-sm-3 add-margin view-content">
								<c:forEach items="${lettertoParty.lpReason}" var="lpReason"
									varStatus="status">
									<c:out value="${lpReason.description}" />
									<c:if test="${!status.last}">,</c:if>
								</c:forEach>
							</div>
							<div class="col-sm-3 add-margin">
								<spring:message	code="lbl.lpdescription" />
							</div>	
								<div class="col-sm-3 add-margin view-content">
									<c:out value="${lettertoParty.lpDesc}" />
								</div>
						</div>
						<div class="row add-border">
							<div class="col-sm-3 add-margin">
								<spring:message code="lbl.lastreplydate" />
							</div>
							<div class="col-sm-3 add-margin view-content">
								<fmt:formatDate value="${lettertoParty.lastReplyDate}" pattern="dd/MM/yyyy" var="lastReplyDate" />
								<c:out value="${lastReplyDate}"></c:out>
							</div>
						</div>
						<div class="row add-border">
							<div class="col-sm-3 add-margin">
								<spring:message code="lbl.lpsentdate" /><span class="mandatory"></span>
							</div>
							<div class="col-sm-3 add-margin">
								<form:input path="sentDate" class="form-control datepicker"
									data-date-end-date="0d" id="sentDate"
									data-inputmask="'mask': 'd/m/y'"  required="required" />
								<form:errors path="sentDate" cssClass="add-margin error-msg" /> 
							</div>
						</div>
					</div>
				</div>
			</div>
	</div>
	<div class="text-center">
	<button type='submit' class='btn btn-primary' id="buttonSubmit">
		<spring:message code='lbl.update' />
	</button>
	
	<a href='javascript:void(0)' class='btn btn-default'
		onclick='self.close()'><spring:message code='lbl.close' /></a>
</div>
</form:form>

<script
		src="<cdn:url value='/resources/global/js/egov/inbox.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script
	src="<cdn:url value='/resources/global/js/bootstrap/bootstrap-datepicker.js' context='/egi'/>"></script>
<link rel="stylesheet"
	href="<cdn:url value='/resources/global/css/bootstrap/bootstrap-datepicker.css' context='/egi'/>">
<script
	src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/moment.min.js' context='/egi'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/lettertoparty.js?rnd=${app_release_no}'/> "></script>
