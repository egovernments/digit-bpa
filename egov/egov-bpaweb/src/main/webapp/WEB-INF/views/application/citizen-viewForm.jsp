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
		<form:form role="form"
			action="/bpa/application/citizen/update-submit/${bpaApplication.applicationNumber}"
			method="post" modelAttribute="bpaApplication"
			id="citizenViewApplicationForm"
			cssClass="form-horizontal form-groups-bordered"
			enctype="multipart/form-data">
			<form:hidden path="" id="workFlowAction" name="workFlowAction" />
			<form:hidden path="" id="wfstateDesc"
				value="${bpaApplication.state.value}" />
			<input type="hidden" id="appointmentDateRes"
				value="${appointmentDateRes}" />
			<input type="hidden" id="appointmentTimeRes"
				value="${appointmentTimeRes}" />
			<input type="hidden" id="appointmentTitle"
				value="${appointmentTitle}" />
			<input type="hidden" id="appmntInspnRemarks"
				value="${appmntInspnRemarks}" />
			<input type="hidden" id="collectFeeValidate"
				value="${collectFeeValidate}" />
			<input type="hidden" name="citizenOrBusinessUser"
				value="${citizenOrBusinessUser}">
			<input type="hidden" id="applicationStatus"
				value="${bpaApplication.status.code}">
			<ul class="nav nav-tabs" id="settingstab">
				<li class="active"><a data-toggle="tab"
					href="#appliccation-info" data-tabidx=0><spring:message
							code='lbl.appln.details' /></a></li>
				<li><a data-toggle="tab" href="#document-info" data-tabidx=1><spring:message
							code='title.documentdetail' /></a></li>
				<c:if test="${not empty bpaApplication.documentScrutiny}">
					<li><a data-toggle="tab" href="#doc-scrnty" data-tabidx=2><spring:message
								code='lbl.document.scrutiny' /></a></li>
				</c:if>

				<c:if test="${not empty bpaApplication.permitInspections}">
					<li><a data-toggle="tab" href="#view-inspection" data-tabidx=3><spring:message
								code='lbl.inspection.appln' /></a></li>
				</c:if>
				<c:if test="${not empty bpaApplication.permitNocDocuments}">
					<li><a data-toggle="tab" href="#noc-info" data-tabidx=4><spring:message
								code='lbl.noc.details' /></a></li>
				</c:if>
				<c:if
					test="${not empty bpaApplication.permitFee || bpaApplication.admissionfeeAmount > 0}">
					<li><a data-toggle="tab" href="#view-fee" data-tabidx=5><spring:message
								code='lbl.fees.details' /></a></li>
				</c:if>
				<c:if
					test="${not empty lettertopartylist && mode eq 'showLPDetails'}">
					<li><a data-toggle="tab" href="#view-lp" data-tabidx=6><spring:message
								code='lbl.lp.details' /></a></li>
				</c:if>
			</ul>
			<div class="tab-content">
				<div id="document-info" class="tab-pane fade">
					<c:if test="${not empty  bpaApplication.permitNocDocuments}">
						<div class="panel panel-primary dcrDocuments" data-collapsed="0">
							<jsp:include page="view-dcr-documentdetails.jsp"></jsp:include>
						</div>
					</c:if>
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="view-bpaDocumentdetails.jsp"></jsp:include>
					</div>
				</div>
				<div id="appliccation-info" class="tab-pane fade in active">
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="viewapplication-details.jsp"></jsp:include>
					</div>
					<div class="panel panel-primary edcrApplnDetails"
						data-collapsed="0">
						<jsp:include page="view-edcr-application-details.jsp"></jsp:include>
					</div>
					<c:if test="${not empty  bpaApplication.buildingSubUsages}">
						<div class="panel panel-primary edcrApplnDetails"
							data-collapsed="0">
							<jsp:include page="view-building-subusages.jsp" />
						</div>
					</c:if>
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="view-applicantdetails.jsp"></jsp:include>
					</div>
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="../common/generic-boundary-view.jsp">
							<jsp:param name="boundaryData"
								value="${bpaApplication.adminBoundary}:${bpaApplication.revenueBoundary}:${bpaApplication.locationBoundary}" />
						</jsp:include>
					</div>
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="view-sitedetail.jsp"></jsp:include>
					</div>
					<div class="panel panel-primary demolitionDetails"
						data-collapsed="0">
						<jsp:include page="view-demolition-details.jsp" />
					</div>
					<c:if test="${not empty  bpaApplication.existingBuildingDetails}">
						<div class="panel panel-primary buildingdetails"
							data-collapsed="0">
							<jsp:include page="view-existing-building-details.jsp" />
						</div>
					</c:if>
					<div class="panel panel-primary buildingdetails" data-collapsed="0">
						<jsp:include page="view-building-details.jsp" />
					</div>
					<c:if
						test="${(isCitizen && validateCitizenAcceptance && (bpaApplication.status.code ne 'Cancelled') && !citizenDisclaimerAccepted && bpaApplication.sentToCitizen)}">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="disclaimer.jsp" />
						</div>
					</c:if>
					<c:if test="${not empty  bpaApplication.receipts}">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="view-bpa-receipt-details.jsp"></jsp:include>
						</div>
					</c:if>
					<c:if test="${not empty applicationHistory}">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="applicationhistory-view.jsp"></jsp:include>
						</div>
					</c:if>
				</div>

				<c:if test="${not empty bpaApplication.documentScrutiny}">
					<div id="doc-scrnty" class="tab-pane fade">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="view-documentscrutiny.jsp"></jsp:include>
						</div>
					</div>
				</c:if>
				<c:if test="${not empty bpaApplication.permitInspections}">
					<div id="view-inspection" class="tab-pane fade">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="view-inspection-details.jsp"></jsp:include>
						</div>
						<c:if test="${ null ne bpaApplication.townSurveyorRemarks}">
							<div class="panel panel-primary" data-collapsed="0">
								<jsp:include page="view-town-surveyor-remarks.jsp"></jsp:include>
							</div>
						</c:if>
					</div>
				</c:if>
				<c:if test="${not empty bpaApplication.permitNocDocuments}">
					<div id="noc-info" class="tab-pane fade">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="view-noc-document.jsp"></jsp:include>
						</div>
					</div>
				</c:if>
				<c:if
					test="${not empty bpaApplication.permitFee || bpaApplication.admissionfeeAmount > 0}">
					<div id="view-fee" class="tab-pane fade">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="view-bpa-fee-details.jsp"></jsp:include>
						</div>
					</div>
				</c:if>
				<c:if
					test="${not empty lettertopartylist && mode eq 'showLPDetails'}">
					<div id="view-lp" class="tab-pane fade">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include
								page="../lettertoparty/lettertoparty-details-citizen.jsp"></jsp:include>
						</div>
					</div>
				</c:if>
			</div>
			<div class="buttonbottom" align="center">
				<table>
					<tr>
						<td><c:if
								test="${isCitizen && validateCitizenAcceptance && (bpaApplication.status.code ne 'Cancelled') && !citizenDisclaimerAccepted && bpaApplication.sentToCitizen}">
								<form:button type="button" id="buttonAccept"
									class="btn btn-primary" value="Save">
									<spring:message code='lbl.btn.accpt' />
								</form:button>&nbsp;
							</c:if></td>
						<c:if test="${ mode eq 'showRescheduleToCitizen'}">
							<td><a
								href="/bpa/application/scrutiny/reschedule/${bpaApplication.applicationNumber}"
								class="btn btn-primary"> <spring:message
										code='lbl.btn.reschedule.appointment' />
							</a>&nbsp;</td>
						</c:if>
						<c:if
							test="${ bpaApplication.status.code eq 'Scheduled For Document Scrutiny'
								|| bpaApplication.status.code eq 'Pending For Rescheduling For Document Scrutiny'
								|| bpaApplication.status.code eq 'Rescheduled For Document Scrutiny'}">
							<td><a
								href="/bpa/application/scrutiny/view/${bpaApplication.applicationNumber}"
								target="popup" class="btn btn-primary"
								onclick="window.open('/bpa/application/scrutiny/view/${bpaApplication.applicationNumber}','popup','width=1100,height=700'); return false;">
									<spring:message code='title.view.schedule.appmnt' />
							</a>&nbsp;</td>
						</c:if>

						<c:if
							test="${bpaApplication.status.code eq  'Letter To Party Created' && mode eq 'showLPDetails' }">
							<td><a
								href="/bpa/lettertoparty/lettertopartyreply/${lettertopartylist.get(0).id}"
								class="btn btn-primary"> <spring:message
										code='lbl.btn.reply.letter.to.party' />
							</a>&nbsp;</td>
						</c:if>
						<input type="hidden" id="onlinePaymentEnable"
							value="${onlinePaymentEnable}">
						<c:if
							test="${onlinePaymentEnable && isFeeCollected && (bpaApplication.status.code eq 'Submitted' || bpaApplication.status.code eq 'Approved') }">
							<td><a
								href="/bpa/application/bpageneratebill/${bpaApplication.applicationNumber}"
								class="btn btn-primary"> <spring:message
										code='lbl.btn.pay.fee.online' />
							</a>&nbsp;</td>
						</c:if>
						<c:if
							test="${bpaApplication.status.code eq 'Approved' && isFeeCollected }">
							<td><a
								href="/bpa/application/demandnotice/${bpaApplication.applicationNumber}"
								target="popup" class="btn btn-primary"
								onclick="window.open('/bpa/application/demandnotice/${bpaApplication.applicationNumber}','popup','width=1100,height=700'); return false;">
									<spring:message code='lbl.btn.print.demand.notice' />
							</a>&nbsp;</td>
						</c:if>
						<td><c:if
								test="${citizenOrBusinessUser && bpaApplication.id != null && bpaApplication.state == null && bpaApplication.status.code ne 'Cancelled'
								    && (validateCitizenAcceptance && (bpaApplication.status.code ne 'Cancelled') && !citizenDisclaimerAccepted &&  bpaApplication.sentToCitizen)}">
								<form:button type="button" id="buttonCancel"
									class="btn btn-danger" value="Cancel Application">
									<spring:message code='lbl.btn.cancel.application' />
								</form:button>&nbsp;
							</c:if></td>
						<c:if
							test="${(bpaApplication.status.code eq 'Order Issued to Applicant' || bpaApplication.applicationType.name eq 'Low Risk') && (bpaApplication.status.code ne 'Revocated') && not empty bpaApplication.bpaNotice}">
							<td><a
								href="/bpa/application/generatepermitorder/${bpaApplication.applicationNumber}"
								target="popup" class="btn btn-primary"
								onclick="window.open('/bpa/application/generatepermitorder/${bpaApplication.applicationNumber}','popup','width=1100,height=700'); return false;">
									<spring:message code='lbl.btn.print.permit.order' />
							</a>&nbsp;</td>
						</c:if>

						<c:if
							test="${bpaApplication.status.code eq 'Revocated' && bpaApplication.state ne null}">
							<td><a
								href="/bpa/application/revocation/generateorder/${bpaApplication.permitRevocation[0].revocationNumber}"
								target="popup" class="btn btn-primary"
								onclick="window.open('/bpa/application/revocation/generateorder/${bpaApplication.permitRevocation[0].revocationNumber}','popup','width=1100,height=700'); return false;">
									<spring:message code='lbl.btn.print.revocation.notice' />
							</a>&nbsp;</td>
						</c:if>


						<c:if
							test="${bpaApplication.status.code eq 'Cancelled' && bpaApplication.state ne null}">
							<td><a
								href="/bpa/application/rejectionnotice/${bpaApplication.applicationNumber}"
								target="popup" class="btn btn-primary"
								onclick="window.open('/bpa/application/rejectionnotice/${bpaApplication.applicationNumber}','popup','width=1100,height=700'); return false;">
									<spring:message code='lbl.btn.print.rejection.notice' />
							</a>&nbsp;</td>
						</c:if>

						<td>&nbsp;<input type="button" name="button2" id="button2"
							value="Close" class="btn btn-default" onclick="window.close();" />
						</td>
					</tr>
				</table>
			</div>
		</form:form>
	</div>
</div>

<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel" aria-hidden="true" id="myModal">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h4 class="modal-title">
					<div id="appointmentTitleModal" />
				</h4>
			</div>
			<div class="modal-body">
				<div class="row add-border">
					<div class="col-sm-5 add-margin">
						<spring:message code="lbl.appmnt.date"></spring:message>
					</div>
					<div class="col-sm-7 add-margin view-content"
						id="appointmentDateModal"></div>
				</div>
				<div class="row add-border">
					<div class="col-sm-5 add-margin">
						<spring:message code="lbl.appmnt.time"></spring:message>
					</div>
					<div class="col-sm-7 add-margin view-content"
						id="appointmentTimeModal"></div>
				</div>
				<c:if
					test="${appmntInspnRemarks ne null && appmntInspnRemarks ne ''}">
					<div class="row add-border">
						<div class="col-sm-5 add-margin">
							<spring:message code="lbl.remarks"></spring:message>
						</div>
						<div class="col-sm-7 add-margin view-content"
							id="appmntInspnRemarksModal"></div>
					</div>
				</c:if>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<spring:message code="lbl.btn.close" />
				</button>
			</div>
			<input type="hidden" id="feeAmount"
				value="<spring:message code='msg.validation.feeamount'/>" /> <input
				type="hidden" id="incrFeeamtTopOfsysCalcFee"
				value="<spring:message code='msg.validation.incrontopof.systemcalc.feeamount'/>" />
			<input type="hidden" id="acceptAppln"
				value="<spring:message code='msg.confirm.accept.appln'/>" /> <input
				type="hidden" id="cancelAppln"
				value="<spring:message code='msg.confirm.cancel.appln'/>" /> <input
				type="hidden" id="floorareaValidate"
				value="<spring:message code='msg.validate.floorarea' />" /> <input
				type="hidden" id="carpetareaValidate"
				value="<spring:message code='msg.validate.carpetarea' />" /> <input
				type="hidden" id="typeOfMsg"
				value="<spring:message code='msg.vlaidate.typeof' />" /> <input
				type="hidden" id="permissibleAreaForFloor1"
				value="<spring:message code='msg.vlaidate.permissibleAreaForFloor1' />" />
			<input type="hidden" id="permissibleAreaForFloor2"
				value="<spring:message code='msg.vlaidate.permissibleAreaForFloor2' />" />
			<input type="hidden" id="builtupAndCarpetDetails"
				value="<spring:message code='msg.tittle.builtup.carpet.details' />" />
			<input type="hidden" id="blockMsg"
				value="<spring:message code='msg.tittle.blockmsg' />" /> <input
				type="hidden" id="buildScrutinyNumber"
				value="<spring:message code='msg.validate.building.scrutiny.number' />" />
			<input type="hidden" id="buildingPlanApplnForServiceType"
				value="<spring:message code='msg.validate.buildingplan.applnfor.servicetype' />" />
			<input type="hidden" id="buildServiceType"
				value="<spring:message code='msg.validate.building.servicetype' />" />
			<input type="hidden" id="forBuildScrutinyNumber"
				value="<spring:message code='msg.validate.forbuilding.scrutiny.number' />" />
			<input type="hidden" id="floorDetailsNotExtracted"
				value="<spring:message code='msg.validate.floordetsil.not.extracted' />" />
			<input type="hidden" id="existingBuildDetailsNotPresent"
				value="<spring:message code='msg.validate.existing.building.details.notpresent' />" />
			<input type="hidden" id="acceptDisclaimer"
				value="<spring:message code='msg.validate.accept.disclaimer'/>" /> <input
				type="hidden" id="validateCitizenAcceptance"
				name="validateCitizenAcceptance"
				value="${validateCitizenAcceptance}" /> <input type="hidden"
				id="citizenDisclaimerAccepted" name="citizenDisclaimerAccepted"
				value="${citizenDisclaimerAccepted}" /> <input type="hidden"
				id="isCitizen" name="isCitizen" value="${isCitizen}" />
		</div>
	</div>
</div>

<script
	src="<cdn:url value='/resources/global/js/egov/inbox.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/application-view.js?rnd=${app_release_no}'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/edcr-helper.js?rnd=${app_release_no}'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/citizen-view-helper.js?rnd=${app_release_no}'/>"></script>