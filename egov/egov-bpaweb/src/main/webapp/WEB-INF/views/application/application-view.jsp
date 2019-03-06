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
			<div class="panel-title text-center no-float">
				<c:if test="${not empty feeNotDefined}">
					<strong class="error-msg">${feeNotDefined}</strong>
				</c:if>
			</div>
		</div>
		
		<form:form role="form" action="/bpa/application/update-submit/${bpaApplication.applicationNumber}" method="post" modelAttribute="bpaApplication"
				   id="viewBpaApplicationForm"
				   cssClass="form-horizontal form-groups-bordered"
				   enctype="multipart/form-data">
			<c:if
					test="${isFeeCollected && bpaApplication.status.code eq 'Approved'}">
				<div data-collapsed="0">
					<div class="panel-heading">
						<div style="color: red; font-size: 16px;" align="center">
							<spring:message code="lbl.collect.bpaFee" />
						</div>
					</div>
				</div>
			</c:if>
			<input type="hidden" id="applicationId" name="bpaApplication"
				   value="${bpaApplication.id}">
			<input type="hidden" id="isFeeCollectionPending" value="${isFeeCollected}">
			<input type="hidden" id="status" value="${bpaApplication.status.code}">
			<input type="hidden" name="citizenOrBusinessUser"
				   value="${citizenOrBusinessUser}"/>
			<form:hidden path="" id="wfstate" value="${bpaApplication.state.id}" />
			<form:hidden path="" id="workFlowAction" name="workFlowAction" />
			<input type="hidden" id="serviceTypeCode" value="${bpaApplication.serviceType.code}" />
			<form:hidden path="" id="wfstateDesc"
						 value="${bpaApplication.state.value}" />
			<form:hidden path="" id="collectFeeValidate" value="${collectFeeValidate}" />
			<form:hidden path="" id="mode" name="mode" value="${mode}" />
			<form:hidden  path="" id="amountRule" name="amountRule" value="${amountRule}"/>
			<form:hidden path="" id="scheduleType" name="scheduleType"
						 value="${scheduleType}" />
			<form:hidden path="" id="showPermitConditions" value="${showpermitconditions}" />
			<form:hidden path="sentToPreviousOwner" id="sentToPreviousOwner" />
			<form:hidden path="LPRequestInitiated" id="lpRequestInitiated" value="${bpaApplication.LPRequestInitiated}" />
			<input type="hidden" id="isOneDayPermitApplication" value="${bpaApplication.isOneDayPermitApplication}" />
			<input type="hidden" id="approveComments" value="${bpaApplication.state.comments}" />
			<input type="hidden" id="captureTSRemarks" value="${captureTSRemarks}">
			<ul class="nav nav-tabs" id="settingstab">
				<li class="active"><a data-toggle="tab" href="#applicant-info"
									  data-tabidx=0><spring:message code='lbl.appln.details' /></a></li>
				<li><a data-toggle="tab" href="#document-info" data-tabidx=1><spring:message
						code='title.documentdetail' /></a></li>
				<c:if test="${not empty bpaApplication.documentScrutiny}">
					<li><a data-toggle="tab" href="#doc-scrnty" data-tabidx=2><spring:message
							code='lbl.document.scrutiny' /></a></li>
				</c:if>
				<c:if test="${not empty bpaApplication.inspections}">
					<li><a data-toggle="tab" href="#view-inspection" data-tabidx=3><spring:message
							code='lbl.inspection.appln' /></a></li>
				</c:if>
				<c:if test="${captureTSRemarks}">
					<li><a data-toggle="tab" href="#ts-remarks"
						   data-tabidx=4><spring:message code='lbl.town.surveyor.remarks' /></a></li>
				</c:if>
				<c:if test="${showUpdateNoc}">
					<li><a data-toggle="tab" href="#checklist-info" data-tabidx=5><spring:message
							code='lbl.noc.doc.details' /></a></li>
				</c:if>
				<c:if test="${!showUpdateNoc && not empty bpaApplication.applicationNOCDocument}">
					<li><a data-toggle="tab" href="#noc-info" data-tabidx=5><spring:message
							code='lbl.noc.details' /></a></li>
				</c:if>
				<c:if test="${not empty bpaApplication.permitFee}">
					<li><a data-toggle="tab" href="#view-fee" data-tabidx=6><spring:message
							code='lbl.fee.details' /></a></li>
				</c:if>
				<c:if test="${not empty lettertopartylist}">
					<li><a data-toggle="tab" href="#view-lp" data-tabidx=7><spring:message
							code='lbl.lp.details' /></a></li>
				</c:if>
				<c:if test="${showpermitconditions}">
					<li><a data-toggle="tab" href="#permit-conditions"
						   data-tabidx=8><spring:message code='lbl.permit.conditions' /></a></li>
				</c:if>
			</ul>
			<div class="tab-content">
				<div id="applicant-info" class="tab-pane fade in active">
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="viewapplication-details.jsp"></jsp:include>
					</div>
					<div class="panel panel-primary edcrApplnDetails" data-collapsed="0">
						<jsp:include page="view-edcr-application-details.jsp"></jsp:include>
					</div>
					<c:if test="${not empty  bpaApplication.buildingSubUsages}">
						<div class="panel panel-primary edcrApplnDetails" data-collapsed="0">
							<jsp:include page="view-building-subusages.jsp" />
						</div>
					</c:if>
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="view-applicantdetails.jsp"></jsp:include>
					</div>
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="view-sitedetail.jsp"></jsp:include>
					</div>
					<div class="panel panel-primary demolitionDetails" data-collapsed="0">
						<jsp:include page="view-demolition-details.jsp" />
					</div>
					<c:if test="${not empty  bpaApplication.existingBuildingDetails}">
						<div class="panel panel-primary buildingdetails" data-collapsed="0">
							<jsp:include page="view-existing-building-details.jsp" />
						</div>
					</c:if>
					<div class="panel panel-primary buildingdetails" data-collapsed="0">
						<jsp:include page="view-building-details.jsp" />
					</div>
					<c:if test="${not empty  bpaApplication.receipts}">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="view-bpa-receipt-details.jsp"></jsp:include>
						</div>
					</c:if>
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="applicationhistory-view.jsp"></jsp:include>
					</div>
					<c:if test="${showRejectionReasons}">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="rejection-reasons.jsp"></jsp:include>
						</div>
					</c:if>
				</div>
				<div id="document-info" class="tab-pane fade">
					<c:if test="${not empty  bpaApplication.getDcrDocuments()}">
						<div class="panel panel-primary dcrDocuments" data-collapsed="0">
							<jsp:include page="view-dcr-documentdetails.jsp"></jsp:include>
						</div>
					</c:if>
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="view-bpaDocumentdetails.jsp"></jsp:include>
					</div>
				</div>
				<c:if test="${not empty bpaApplication.documentScrutiny}">
					<div id="doc-scrnty" class="tab-pane fade">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="view-documentscrutiny.jsp"></jsp:include>
						</div>
					</div>
				</c:if>
				<c:if test="${not empty bpaApplication.inspections}">
					<div id="view-inspection" class="tab-pane fade">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="view-inspection-details.jsp"></jsp:include>
						</div>
						<c:if test="${null ne bpaApplication.townSurveyorRemarks}">
							<c:if test="${'Town Surveyor Inspected' eq bpaApplication.status.code}">
								<input type="hidden" id="viewTsRemarks" value="true">
							</c:if>
							<div class="panel panel-primary" data-collapsed="0">
								<jsp:include page="view-town-surveyor-remarks.jsp"></jsp:include>
							</div>
						</c:if>
					</div>
				</c:if>
				<c:if test="${captureTSRemarks}">
					<div id="ts-remarks" class="tab-pane fade">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="town-surveyor-remarks.jsp"></jsp:include>
						</div>
					</div>
				</c:if>
				<c:if test="${!showUpdateNoc && not empty bpaApplication.applicationNOCDocument}">
					<div id="noc-info" class="tab-pane fade">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="view-noc-document.jsp"></jsp:include>
						</div>
					</div>
				</c:if>
				<c:if test="${showUpdateNoc}">
					<input type="hidden" id="showUpdateNoc" value="${showUpdateNoc}">
					<div id="checklist-info" class="tab-pane fade">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="noc-document-updation.jsp"></jsp:include>
						</div>
					</div>
				</c:if>
				<c:if test="${not empty bpaApplication.permitFee}">
					<div id="view-fee" class="tab-pane fade">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="view-bpa-fee-details.jsp"></jsp:include>
						</div>
					</div>
				</c:if>
				<c:if test="${not empty lettertopartylist}">
					<div id="view-lp" class="tab-pane fade">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="../lettertoparty/lettertoparty-details.jsp"></jsp:include>
						</div>
					</div>
				</c:if>
				<c:if test="${showpermitconditions}">
					<input type="hidden" id="showPermitConditions" value="${showpermitconditions}">
					<div id="permit-conditions" class="tab-pane fade">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="permit-conditions.jsp"></jsp:include>
						</div>
					</div>
				</c:if>
			</div>

			<div class="text-center">

				<c:if test="${mode eq 'captureInspection'}">
					<a
							target="popup" class="btn btn-primary" onclick="window.open('/bpa/application/createinspectiondetails/${bpaApplication.applicationNumber}','popup','width=1100,height=700'); return false;"
							class="btn btn-primary"><spring:message code="lbl.btn.inspection.details"/> </a>
					<c:if test="${isInspnRescheduleEnabled eq true}">
						<a
								href="/bpa/application/postponeappointment/${scheduleType}/${bpaApplication.applicationNumber}"
								class="btn btn-primary"><spring:message code="lbl.btn.reschedule.appointment"/> </a>
					</c:if>
				</c:if>
				<c:if test="${mode eq 'modifyInspection'}">
					<a
							onclick="window.open('/bpa/application/modify-inspection/${bpaApplication.applicationNumber}','popup','width=1100,height=700'); return false;"
							target="popup" class="btn btn-primary"> <spring:message code="lbl.btn.add.edit.inspection.details"/> </a>
				</c:if>
				<c:if test="${mode eq 'newappointment'}">
					<a
							href="/bpa/application/scheduleappointment/${bpaApplication.applicationNumber}"
							class="btn btn-primary"> <spring:message code="lbl.btn.new.appointment"/> </a>
				</c:if>
				<c:if test="${mode eq 'initiatedForApproval'}">
					<a
							href="/bpa/application/calculateFee/${bpaApplication.applicationNumber}"
							class="btn btn-primary"> <spring:message code="lbl.btn.modify.fee"/> </a>

				</c:if>
				<c:if test="${bpaApplication.state.value ne 'Field Inspection completed' && bpaApplication.status.code eq 'Field Inspected'}">
					<input type="button" name="save" id="btnSave" value="Save" class="btn btn-primary"/>
				</c:if>
				<c:if test="${createlettertoparty}">
					<a
							href="/bpa/lettertoparty/create/${bpaApplication.applicationNumber}"
							target="_self" class="btn btn-primary"> <spring:message code="lbl.btn.letter.to.party"/> </a>
				</c:if>
			</div>
			<br>
			<c:if test="${isTSInspectionRequired eq true}">
				<div class="panel panel-primary" data-collapsed="0" id="townSurveyorInspectionDiv">
					<div class="panel-heading toggle-header custom_form_panel_heading">
						<div class="panel-title">
						</div>
					</div>
					<div class="panel-body">
						<label class="view-content">
							&nbsp;&nbsp;&nbsp;<form:checkbox path="townSurveyorInspectionRequire"
															 id="townSurveyorInspectionRequire"/>
							&nbsp;&nbsp;&nbsp;<spring:message code="lbl.ts.inspn.requr"/>
						</label>
					</div>
				</div>
			</c:if>
			<c:choose>
				<c:when
					test="${bpaApplication.status.code eq 'Approved' && isFeeCollected }">
				   <div  align="center">
					<a
						href="/bpa/application/demandnotice/${bpaApplication.applicationNumber}"
						target="popup" class="btn btn-primary"  
						onclick="window.open('/bpa/application/demandnotice/${bpaApplication.applicationNumber}','popup','width=1100,height=700'); return false;">
							<spring:message code='lbl.btn.print.demand.notice' />
					</a>
						<input type="button" name="button2" value="Close"
							class="btn btn-default" onclick="window.close();" />
					</div>
				</c:when>   

					<%--	<c:when
					test="${isFeeCollected && bpaApplication.status.code eq 'Approved'}">
					<div class="buttonbottom" align="center">
						<input type="button" name="button2" value="Close"
							class="btn btn-default" onclick="window.close();" />
					</div>
				</c:when>  --%>

				<c:otherwise>
					<c:choose>
						<c:when
								test="${ (citizenOrBusinessUser && bpaApplication.id !=null) || bpaApplication.state.value eq 'LP Created' || bpaApplication.state.value eq 'LP Reply Received'}">
							<div class="buttonbottom" align="center">
								<form:button type="submit" id="buttonSubmit"
											 class="btn btn-primary" value="Forward"><spring:message code="lbl.btn.forward"/></form:button>
								<input type="button" name="button2" value="Close"
									   class="btn btn-default" onclick="window.close();" />
							</div>
						</c:when>
						<c:otherwise>	
						   
						<%-- <c:if test="${bpaApplication.status.code ne 'Digitally signed' }"> --%>	
							<c:if test="${ bpaApplication.status.code ne 'Digitally signed' && bpaApplication.state.value ne 'Record Approved' }">
								<jsp:include page="../common/commonWorkflowMatrix.jsp" />
							</c:if>
							<div class="buttonbottom" align="center">
								<jsp:include page="../common/commonWorkflowMatrix-button.jsp" />
							</div>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</form:form>
	</div>
</div>

<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
	 aria-hidden="true" id="commentsModal">
	<div class="modal-dialog" role="document">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header text-center">
				<h4 class="modal-title w-100 font-weight-bold alert alert-info"><spring:message code="lbl.reason.for.revert"/></h4>
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
		<input type="hidden" id="intiateRejectionAppln" value="<spring:message code='msg.confirm.intiate.rejection.forappln' />" />
		<input type="hidden" id="valuesCannotEmpty" value="<spring:message code='msg.validate.values.cannot.empty' />" />
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