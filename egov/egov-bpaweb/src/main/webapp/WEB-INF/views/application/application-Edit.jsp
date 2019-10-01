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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<div class="row">
	<div class="col-md-12">
		<form:form role="form" action="/bpa/application/update-submit/${bpaApplication.applicationNumber}" method="post" modelAttribute="bpaApplication"
			id="editBpaApplicationForm"
			cssClass="form-horizontal form-groups-bordered"
			enctype="multipart/form-data">

			<form:hidden path="" id="workFlowAction" name="workFlowAction" />
			<input type="hidden" name="citizenOrBusinessUser"
				value="${citizenOrBusinessUser}">
			<form:hidden path="" id="wfstate" value="${bpaApplication.state.id}" />
			<form:hidden path="" id="collectFeeValidate" value="${collectFeeValidate}" />
			<input type="hidden"  id="workFlowByNonEmp" value="${workFlowByNonEmp}" />
			<form:hidden path="" value="${areaInSqMtr}" id="areaInSqMtr" name="areaInSqMtr"/>
			<form:hidden path="" value="${currentDesignation}" id="currentDesignation" name="currentDesignation"/>
			<form:hidden path="" value="${isApproveValid}" id="isApproveValid" name="isApproveValid"/>
			<form:hidden path="" id="applicationId" value="${bpaApplication.id}" />
			<form:hidden path="" id="wfstateDesc"
				value="${bpaApplication.state.value}" />
			<form:hidden path="" id="mode" name="mode" value="${mode}" />
			<ul class="nav nav-tabs" id="settingstab">
				<li class="active"><a data-toggle="tab"
					href="#appliccation-info" data-tabidx=0><spring:message
							code='lbl.appln.details' /></a></li>
				<li><a data-toggle="tab" href="#document-info" data-tabidx=1><spring:message
							code='title.documentdetail' /></a></li>
			</ul>
			<div class="tab-content">
				<div id="document-info" class="tab-pane fade">
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="permit-general-documents.jsp"></jsp:include>
					</div>
				</div>
				<div id="appliccation-info" class="tab-pane fade in active">
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="applicationDetails.jsp"></jsp:include>
					</div>
					<div class="panel panel-primary edcrApplnDetails" data-collapsed="0">
						<jsp:include page="edcr-application-details-form.jsp"></jsp:include>
					</div>
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="applicantDetailForm.jsp"></jsp:include>
					</div>
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="siteDetail.jsp"></jsp:include>
					</div>
					<div class="panel panel-primary existingbuildingdetails" data-collapsed="0">
						<jsp:include page="existing-buildingdetails.jsp" />
					</div>
					<div class="panel panel-primary buildingdetails" data-collapsed="0">
						<jsp:include page="buildingDetails.jsp" />
					</div>
					<c:if test="${not empty  bpaApplication.receipts}">
						<c:set var="receipts" scope="request" value="${bpaApplication.receipts}"></c:set>
						<c:set var="applicationNumber" scope="request" value="${bpaApplication.applicationNumber}"></c:set>
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="../common/view-bpa-receipt-details.jsp"></jsp:include>
						</div>
					</c:if>
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="applicationhistory-view.jsp"></jsp:include>
					</div>
				</div>
			</div>
			<c:if test="${wfstateDesc !='NEW'}">
			<jsp:include page="../common/commonWorkflowMatrix.jsp" />
			</c:if>
			<div class="buttonbottom" align="center">
				<jsp:include page="../common/commonWorkflowMatrix-button.jsp" />
			</div>
		</form:form>
	</div>
	<input type="hidden" id="rejectAppln" value="<spring:message code='msg.confirm.reject.appln' />" />
	<input type="hidden" id="revokePermitAppln" value="<spring:message code='msg.confirm.revoke.appln' />" />
	<input type="hidden" id="intiateRejectionAppln" value="<spring:message code='msg.confirm.intiate.rejection.forappln' />" />
	<input type="hidden" id="sendBackApplnPreOfficial" value="<spring:message code='msg.confirm.sendback.previous.approved.official' />" />
	<input type="hidden" id="approveAppln" value="<spring:message code='msg.confirm.approve.appln' />" />
	<input type="hidden" id="forwardAppln" value="<spring:message code='msg.confirm.forward.application' />" />
	<input type="hidden" id="generatePermitOrder" value="<spring:message code='msg.confirm.generate.permitorder' />" />
	<input type="hidden" id="permitRequired" value="<spring:message code='msg.validate.permit.mandatory' />" />
	<input type="hidden" id="generateRejectNotice" value="<spring:message code='msg.confirm.generate.rejection.notice' />" />
	<input type="hidden" id="townsurvFieldInspeRequest" value="<spring:message code='msg.validate.townsurveyor.filedinspec.request' />" />
	<input type="hidden" id="townsurvFieldInspeRequired" value="<spring:message code='msg.validate.townsurveyor.fieldinspec.required' />" />
	<input type="hidden" id="townsurvCommentsRequired" value="<spring:message code='msg.validate.comments.reqfor.townsurveyor' />" />
	<input type="hidden" id="rejectionReasonMandatory" value="<spring:message code='msg.validate.onerejection.reason.mandatory' />" />
	<input type="hidden" id="revocationReasonMandatory" value="<spring:message code='msg.validate.onerevocation.reason.mandatory' />" />
	<input type="hidden" id="rejectionCommentsRequired" value="<spring:message code='msg.validate.enter.rejection.comments' />" />
	<input type="hidden" id="applnSendbackCommentsRequired" value="<spring:message code='msg.validate.comments.required.toappln.sentback' />" />
	<input type="hidden" id="revokePermitCommentsRequired" value="<spring:message code='msg.validate.comments.required.revoke.permit' />" />
	<input type="hidden" id="violationMessage1" value="<spring:message code='msg.validate.violationMessage' />"/>
	<input type="hidden" id="startingDateReq" value="<spring:message code='msg.validate.startingdate.req' />"/>
	<input type="hidden" id="completionDateReq" value="<spring:message code='msg.validate.workcompletiondate' />"/>
	<input type="hidden" id="extendAreaLimit" value="<spring:message code='msg.validate.extendarea.limit' />"/>
	<input type="hidden" id="resetFloorDetails" value="<spring:message code='msg.confirm.reset.floordetails' />"/>
	<input type="hidden" id="slotmappingValidate" value="<spring:message code='msg.validate.slotmapping' />"/>
	<input type="hidden" id="landAreaReq" value="<spring:message code='msg.validate.landarea.req' />"/>
	<input type="hidden" id="floorareaValidate" value="<spring:message code='msg.validate.floorarea' />"/>
	<input type="hidden" id="carpetareaValidate" value="<spring:message code='msg.validate.carpetarea' />"/>
	<input type="hidden" id="occupancyTypeMsg" value="<spring:message code='msg.validate.occupancytype' />"/>
	<input type="hidden" id="areaPermissibleWOAddnlFee1" value="<spring:message code='msg.validate.areaPermissibleWOAddnlFee1' />"/>
	<input type="hidden" id="areaPermissibleWOAddnlFee2" value="<spring:message code='msg.validate.areaPermissibleWOAddnlFee2' />"/>
	<input type="hidden" id="areaPermissibleWithAddnlFee1" value="<spring:message code='msg.validate.areaPermissibleWithAddnlFee1' />"/>
	<input type="hidden" id="areaPermissibleWithAddnlFee2" value="<spring:message code='msg.validate.areaPermissibleWithAddnlFee2' />"/>
	<input type="hidden" id="areaPermissibleWithAddnlFee3" value="<spring:message code='msg.validate.areaPermissibleWithAddnlFee3' />"/>
	<input type="hidden" id="areaPermissibleWithAddnlFee4" value="<spring:message code='msg.validate.areaPermissibleWithAddnlFee4' />"/>
	<input type="hidden" id="confirmAreaPermiWOAddnlFee" value="<spring:message code='msg.confirm.areaPermissibleWOAddnlFee' />"/>
	<input type="hidden" id="confirmAreaPermiWOAddnlFee1" value="<spring:message code='msg.confirm.areaPermissibleWOAddnlFee1' />"/>
	<input type="hidden" id="typeOfMsg" value="<spring:message code='msg.vlaidate.typeof' />"/>
	<input type="hidden" id="permissibleAreaForFloor1" value="<spring:message code='msg.vlaidate.permissibleAreaForFloor1' />"/>
	<input type="hidden" id="permissibleAreaForFloor2" value="<spring:message code='msg.vlaidate.permissibleAreaForFloor2' />"/>
	<input type="hidden" id="floorCombination" value="<spring:message code='msg.validate.floorcombination' />" />
	<input type="hidden" id="levelValidate" value="<spring:message code='msg.validate.level' />" />
	<input type="hidden" id="occuptypemsg" value="<spring:message code='msg.validate.occuptypemsg' />" />
	<input type="hidden" id="floorAlreadyExist" value="<spring:message code='msg.floordetails.already.exist' />" />
	<input type="hidden" id="valuesCannotEmpty" value="<spring:message code='msg.validate.values.cannot.empty' />" />
	<input type="hidden" id="nocInProgress" value="<spring:message code='msg.nocinitiation.progress' />" />

</div>

<link rel="stylesheet"
	href="<c:url value='/resources/global/css/bootstrap/bootstrap-tagsinput.css?rnd=${app_release_no}' context='/egi'/>">
<script
	src="<c:url value='/resources/global/js/bootstrap/bootstrap-tagsinput.min.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script
	src="<c:url value='/resources/global/js/handlebars/handlebars.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script
	src="<cdn:url value='/resources/global/js/egov/inbox.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/application-edit.js?rnd=${app_release_no}'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/bpa-ajax-helper.js?rnd=${app_release_no}'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/buildingarea-details.js?rnd=${app_release_no}'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/bpa-application-validations.js?rnd=${app_release_no}'/>"></script>