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

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<div class="row">
	<div class="col-md-12">
	
	<div class="panel-heading">
	        <c:if test="${not empty errors}">
			<div class="alert alert-danger" role="alert">
				<strong>${errors}</strong>
			</div>
		    </c:if>
			<div class="panel-title text-center no-float">
				<c:if test="${not empty feeNotDefined}">
					<strong class="error-msg">${feeNotDefined}</strong>
				</c:if>
			</div>
		</div>
		
		<form:form role="form" action="/bpa/application/ownership/transfer/update-submit/${ownershipTransfer.applicationNumber}" method="post" modelAttribute="ownershipTransfer"
				   id="viewBpaApplicationForm"
				   cssClass="form-horizontal form-groups-bordered"
				   enctype="multipart/form-data">
			
			<input type="hidden" id="onlinePaymentEnable" value="${onlinePaymentEnable}">
			<input type="hidden" id="ownershipTransferId" name="ownershipTransfer"
				   value="${bpaApplication.id}">
			<input type="hidden" id="isFeeCollectionPending" value="${isFeeCollected}">
			<input type="hidden" id="status" value="${bpaApplication.status.code}">
			<input type="hidden" id="citizenOrBusinessUser" name="citizenOrBusinessUser"
				   value="${citizenOrBusinessUser}"/>
			<form:hidden path="" id="wfstate" value="${ownershipTransfer.state.id}" />
			<form:hidden path="" id="workFlowAction" name="workFlowAction" />
			<input type="hidden" id="serviceTypeCode" value="${ownershipTransfer.parent.serviceType.code}" />
			<form:hidden path="" id="wfstateDesc"
						 value="${ownershipTransfer.state.value}" />
			<form:hidden path="" id="collectFeeValidate" value="${collectFeeValidate}" />
			<form:hidden path="" id="mode" name="mode" value="${mode}" />
			<form:hidden  path="" id="amountRule" name="amountRule" value="${amountRule}"/>
			<form:hidden path="" id="scheduleType" name="scheduleType"
						 value="${scheduleType}" />
			<form:hidden path="" id="showPermitConditions" value="${showpermitconditions}" />
			<input type="hidden" id="approveComments" value="${ownershipTransfer.state.comments}" />
			<ul class="nav nav-tabs" id="settingstab">
				<li class="active"><a data-toggle="tab" href="#applicant-info"
									  data-tabidx=0><spring:message code='lbl.appln.details' /></a></li>
				<li><a data-toggle="tab" href="#document-info" data-tabidx=1><spring:message
						code='title.documentdetail' /></a></li>
			</ul>
			<div class="tab-content">
				<div id="applicant-info" class="tab-pane fade in active">
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="view-ownership-details.jsp"></jsp:include>
					</div>
                    <div class="panel panel-primary" data-collapsed="0">
                    	<c:set value="${ownershipTransfer.owner}" scope="request" var="owner"></c:set>
						<jsp:include page="../application/view-applicantdetails.jsp"></jsp:include>
						<c:if test="${not empty ownershipTransfer.coApplicants}">
							<c:set value="${ownershipTransfer.coApplicants}" scope="request" var="coApplicants"></c:set>						
							<jsp:include page="../application/view-co-applicant-details.jsp"></jsp:include>							
						</c:if>
					</div>
					
					
					<c:if test="${not empty  ownershipTransfer.receipts}">
						<c:set var="receipts" scope="request" value="${ownershipTransfer.receipts}"></c:set>
						<c:set var="applicationNumber" scope="request" value="${ownershipTransfer.applicationNumber}"></c:set>
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="../common/view-bpa-receipt-details.jsp"></jsp:include>
						</div>
					</c:if>
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="../application/applicationhistory-view.jsp"></jsp:include>
					</div>
					<c:if test="${showRejectionReasons}">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="../application/rejection-reasons.jsp"></jsp:include>
						</div>
					</c:if>
				</div>
				<div id="document-info" class="tab-pane fade">
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="view-ownership-documentdetails.jsp"></jsp:include>
					</div>
				</div>
				
				
			</div>
				<div align="center">
				<c:if
					test="${isFeeCollectionPending && onlinePaymentEnable && ownershipTransfer.status.code eq 'Approved'} ">
			<td><a
				href="/bpa/application/ownership/transfer/generate-bill/${ownershipTransfer.applicationNumber}"
				class="btn btn-primary"> <spring:message
						code='lbl.btn.pay.fee.online' />
			</a>&nbsp;</td>
		</c:if>
					<input type="button" name="button2" value="Close"	class="btn btn-default" onclick="window.close();" />
			</div>
			   
		</form:form>
	</div>
</div>

<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
	 aria-hidden="true" id="commentsModal">
	<div class="modal-dialog" role="document">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header text-center">
			</div>
			<div class="modal-body mx-3">
				<div id="showCommentsModal" class="md-form mb-5"></div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-danger" data-dismiss="modal"><spring:message code="lbl.btn.close"/></button>
			</div>
		</div>
		
		<input type="hidden" id="feeAmount" value="<spring:message code='msg.validation.feeamount'/>"/>
		<input type="hidden" id="incrFeeamtTopOfsysCalcFee" value="<spring:message code='msg.validation.incrontopof.systemcalc.feeamount'/>"/>
	    <input type="hidden" id="uploadMsg" value="<spring:message code='msg.upload' />" />
        <input type="hidden" id="docNameLength" value="<spring:message code='msg.validate.docname.length' />" />
	    <input type="hidden" id="fileSizeLimit" value="<spring:message code='msg.validate.filesize.limit' />" />
	    <input type="hidden" id="validDocFormat" value="<spring:message code='msg.validate.docformat' />" />
	    <input type="hidden" id="floorareaValidate" value="<spring:message code='msg.validate.floorarea' />"/>
		<input type="hidden" id="carpetareaValidate" value="<spring:message code='msg.validate.carpetarea' />"/>
		<input type="hidden" id="typeOfMsg" value="<spring:message code='msg.vlaidate.typeof' />"/>
		<input type="hidden" id="permissibleAreaForFloor1" value="<spring:message code='msg.vlaidate.permissibleAreaForFloor1' />"/>
		<input type="hidden" id="permissibleAreaForFloor2" value="<spring:message code='msg.vlaidate.permissibleAreaForFloor2' />"/>
		<input type="hidden" id="builtupAndCarpetDetails" value="<spring:message code='msg.tittle.builtup.carpet.details' />"/>
		<input type="hidden" id="blockMsg" value="<spring:message code='msg.tittle.blockmsg' />"/>
		<input type="hidden" id="buildScrutinyNumber" value="<spring:message code='msg.validate.building.scrutiny.number' />"/>
		<input type="hidden" id="buildingPlanApplnForServiceType" value="<spring:message code='msg.validate.buildingplan.applnfor.servicetype' />"/>
		<input type="hidden" id="buildServiceType" value="<spring:message code='msg.validate.building.servicetype' />"/>
		<input type="hidden" id="forBuildScrutinyNumber" value="<spring:message code='msg.validate.forbuilding.scrutiny.number' />"/>
		<input type="hidden" id="floorDetailsNotExtracted" value="<spring:message code='msg.validate.floordetsil.not.extracted' />"/>
		<input type="hidden" id="existingBuildDetailsNotPresent" value="<spring:message code='msg.validate.existing.building.details.notpresent' />"/>
		<input type="hidden" id="rejectAppln" value="<spring:message code='msg.confirm.reject.appln' />" />
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
		<input type="hidden" id="rejectionCommentsRequired" value="<spring:message code='msg.validate.enter.rejection.comments' />" />
		<input type="hidden" id="applnSendbackCommentsRequired" value="<spring:message code='msg.validate.comments.required.toappln.sentback' />" />
		<input type="hidden" id="revokePermitCommentsRequired" value="<spring:message code='msg.validate.comments.required.revoke.permit' />" />
		<input type="hidden" id="intiateRejectionAppln" value="<spring:message code='msg.confirm.intiate.rejection.forappln' />" />
		<input type="hidden" id="valuesCannotEmpty" value="<spring:message code='msg.validate.values.cannot.empty' />" />
	    <input type="hidden" id="revocationReasonMandatory" value="<spring:message code='msg.validate.onerevocation.reason.mandatory' />" />
	    <input type="hidden" id="nocInProgress" value="<spring:message code='msg.nocinitiation.progress' />" />
		
		
	</div>
</div>

<script
		src="<cdn:url value='/resources/global/js/egov/inbox.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script
		src="<cdn:url value='/resources/js/app/application-edit.js?rnd=${app_release_no}'/>"></script>
<script
		src="<cdn:url value='/resources/js/app/documentsuploadvalidation.js?rnd=${app_release_no}'/>"></script>
<script
		src="<cdn:url value='/resources/js/app/application-view.js?rnd=${app_release_no}'/>"></script>
<script
		src="<cdn:url value='/resources/js/app/edcr-helper.js?rnd=${app_release_no}'/>"></script>