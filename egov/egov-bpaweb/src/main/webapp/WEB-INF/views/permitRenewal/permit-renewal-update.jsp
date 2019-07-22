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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>

<div class="row">
	<div class="col-md-12">
		<c:if test="${feePending eq true}">
			<div class="panel-heading custom_form_panel_heading">
				<div class="panel-title text-center no-float">
					<strong class="error-msg"><spring:message code="msg.collect.applnfees.toprocess.appln"/></strong>
				</div>
			</div>
		</c:if>
		<form:form role="form"
			action="/bpa/application/permit/renewal/update-submit/${permitRenewal.applicationNumber}"
			method="post" modelAttribute="permitRenewal"
			id="permitRenewalUpdateForm"
			cssClass="form-horizontal form-groups-bordered"
			enctype="multipart/form-data">
			<input type="hidden" id="workFlowAction" name="workFlowAction" />
			<input type="hidden" id="wfstateDesc"
				value="${permitRenewal.state.value}" />
			<input type="hidden" name="citizenOrBusinessUser"
				value="${citizenOrBusinessUser}">
			<input type="hidden" id="applicationStatus"
				value="${permitRenewal.status.code}">
			<input type="hidden" name="permitRenewal" id="permitRenewal"
				value="${permitRenewal.id}">
			<input type="hidden" name="id" id="permitRenewalId"
				value="${permitRenewal.id}">
			<div class="panel panel-primary" data-collapsed="0">
				<jsp:include page="view-renewal-application-details.jsp"></jsp:include>
			</div>
			<div class="panel panel-primary" data-collapsed="0">
				<jsp:include page="view-permit-application-details.jsp"></jsp:include>
			</div>
			<div class="panel panel-primary" data-collapsed="0">
				<jsp:include page="../application/applicationhistory-view.jsp"></jsp:include>
			</div>
			<c:if test="${showRejectionReasons}">
				<div class="panel panel-primary" data-collapsed="0">
					<jsp:include page="renewal-rejection-reasons.jsp"></jsp:include>
				</div>
			</c:if>
			<jsp:include page="../common/commonWorkflowMatrix.jsp" />
			<div class="buttonbottom" align="center">
				<jsp:include page="../common/commonWorkflowMatrix-button.jsp" />
			</div>
		</form:form>
	</div>
</div>

<input type="hidden" id="approveAppln"
	value="<spring:message code='msg.confirm.approve.appln' />" />
<input type="hidden" id="forwardAppln"
	value="<spring:message code='msg.confirm.forward.application' />" />
<input type="hidden" id="generatePermitOrder"
	value="<spring:message code='msg.confirm.renew.generate.permitorder' />" />
<input type="hidden" id="intiateRejectionAppln"
	value="<spring:message code='msg.confirm.intiate.rejection.forappln' />" />
<input type="hidden" id="rejectAppln"
	value="<spring:message code='msg.confirm.reject.appln' />" />
<input type="hidden" id="generateRejectNotice"
	value="<spring:message code='msg.confirm.generate.rejection.notice' />" />
<input type="hidden" id="rejectionReasonMandatory"
	value="<spring:message code='msg.validate.onerejection.reason.mandatory' />" />


<script
	src="<cdn:url value='/resources/global/js/egov/inbox.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/permit-renewal/permit-renewal-update.js?rnd=${app_release_no}'/>"></script>

