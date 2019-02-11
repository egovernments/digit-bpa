<%--
  ~ eGov suite of products aim to improve the internal efficiency,transparency,
  ~      accountability and the service delivery of the government  organizations.
  ~
  ~       Copyright (C) <2017>  eGovernments Foundation
  ~
  ~       The updated version of eGov suite of products as by eGovernments Foundation
  ~       is available at http://www.egovernments.org
  ~
  ~       This program is free software: you can redistribute it and/or modify
  ~       it under the terms of the GNU General Public License as published by
  ~       the Free Software Foundation, either version 3 of the License, or
  ~       any later version.
  ~
  ~       This program is distributed in the hope that it will be useful,
  ~       but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~       GNU General Public License for more details.
  ~
  ~       You should have received a copy of the GNU General Public License
  ~       along with this program. If not, see http://www.gnu.org/licenses/ or
  ~       http://www.gnu.org/licenses/gpl.html .
  ~
  ~       In addition to the terms of the GPL license to be adhered to in using this
  ~       program, the following additional terms are to be complied with:
  ~
  ~           1) All versions of this program, verbatim or modified must carry this
  ~              Legal Notice.
  ~
  ~           2) Any misrepresentation of the origin of the material is prohibited. It
  ~              is required that all modified versions of this material be marked in
  ~              reasonable ways as different from the original version.
  ~
  ~           3) This license does not grant any rights to any user of the program
  ~              with regards to rights under trademark law for use of the trade names
  ~              or trademarks of eGovernments Foundation.
  ~
  ~     In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
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
			modelAttribute="occupancyCertificate"
			id="citizenOccupancyCertificateViewForm"
			cssClass="form-horizontal form-groups-bordered"
			enctype="multipart/form-data">

			<form:hidden path="" id="workFlowAction" name="workFlowAction" />
			<form:hidden path="" id="wfstateDesc"
				value="${occupancyCertificate.state.value}" />
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
				value="${occupancyCertificate.status.code}">
			<input type="hidden" id="serviceTypeCode"
				value="${occupancyCertificate.parent.serviceType.code}" />

			<ul class="nav nav-tabs" id="settingstab">
				<li class="active"><a data-toggle="tab"
					href="#application-info" data-tabidx=0><spring:message
							code='lbl.appln.details' /></a></li>
				<li><a data-toggle="tab" href="#document-info" data-tabidx=1><spring:message
							code='title.documentdetail' /></a></li>
				<li><a data-toggle="tab" href="#noc-document-info"
					data-tabidx=2><spring:message code='lbl.noc.doc.details' /></a></li>
				<c:if test="${not empty occupancyCertificate.inspections}">
					<li><a data-toggle="tab" href="#view-inspection" data-tabidx=3><spring:message
								code='lbl.inspection.appln' /></a></li>
				</c:if>
				<c:if
					test="${not empty letterToPartyList && mode eq 'showLPDetails'}">
					<li><a data-toggle="tab" href="#view-lp" data-tabidx=4><spring:message
								code='lbl.lp.details' /></a></li>
				</c:if>
			</ul>
			<div class="tab-content">
				<div id="application-info" class="tab-pane fade in active">
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="../view-oc-application-details.jsp"></jsp:include>
					</div>
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include
							page="../../application/edcr-application-details-form.jsp"></jsp:include>
					</div>
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="../view-bpa-basic-application-details.jsp"></jsp:include>
					</div>
					<c:if test="${not empty occupancyCertificate.existingBuildings}">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="../view-oc-existing-building-details.jsp"></jsp:include>
						</div>
					</c:if>
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="../view-oc-building-details.jsp"></jsp:include>
					</div>
					<c:if test="${not empty  occupancyCertificate.receipts}">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include
								page="../../application/view-bpa-receipt-details.jsp"></jsp:include>
						</div>
					</c:if>
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="../../application/applicationhistory-view.jsp"></jsp:include>
					</div>
				</div>
				<div id="document-info" class="tab-pane fade">
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="../view-oc-dcr-documents.jsp"></jsp:include>
						<jsp:include page="../view-oc-documents.jsp"></jsp:include>
					</div>
				</div>
				<div id="noc-document-info" class="tab-pane fade">
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="../view-oc-noc-documents.jsp"></jsp:include>
					</div>
				</div>
				<c:if test="${not empty occupancyCertificate.inspections}">
					<div id="view-inspection" class="tab-pane fade">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="../inspection/oc-view-inspection-details.jsp"></jsp:include>
						</div>
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include
								page="../inspection/oc-view-town-surveyor-remarks.jsp"></jsp:include>
						</div>
					</div>
				</c:if>
				<c:if
					test="${not empty letterToPartyList && mode eq 'showLPDetails'}">
					<div id="view-lp" class="tab-pane fade">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="../letterToParty/oc-ltp-details-citizen.jsp"></jsp:include>
						</div>
					</div>
				</c:if>
			</div>
			<div class="buttonbottom" align="center">
				<table>
					<tr>
						<c:if test="${ mode eq 'showRescheduleToCitizen'}">
							<td><a
								href="/bpa/application/occupancy-certificate/scrutiny/reschedule/${occupancyCertificate.applicationNumber}"
								class="btn btn-primary"> <spring:message
										code='lbl.btn.reschedule.appointment' />
							</a></td>
						</c:if>
						<c:if
							test="${ occupancyCertificate.status.code eq 'Scheduled For Document Scrutiny'
								|| occupancyCertificate.status.code eq 'Pending For Rescheduling For Document Scrutiny'
								|| occupancyCertificate.status.code eq 'Rescheduled For Document Scrutiny'}">
							<td><a
								href="/bpa/application/occupancy-certificate/scrutiny/view/${occupancyCertificate.applicationNumber}"
								target="popup" class="btn btn-primary"
								onclick="window.open('/bpa/application/occupancy-certificate/scrutiny/view/${occupancyCertificate.applicationNumber}','popup','width=1100,height=700'); return false;">
									<spring:message code='title.view.schedule.appmnt' />
							</a></td>
						</c:if>
						<c:if
							test="${occupancyCertificate.status.code eq  'Letter To Party Created' && mode eq 'showLPDetails' }">
							<td><a
								href="/bpa/occupancy-certificate/letter-to-party/reply/${occupancyCertificate.applicationNumber}/${letterToPartyList.get(0).letterToParty.lpNumber}"
								class="btn btn-primary"> <spring:message
										code='lbl.btn.reply.letter.to.party' />
							</a></td>
						</c:if>
						<c:if
							test="${occupancyCertificate.status.code eq 'Order Issued to Applicant' }">
							<td><a
								href="/bpa/application/occupancy-certificate/generate-occupancy-certificate/${occupancyCertificate.applicationNumber}"
								target="popup" class="btn btn-primary"
								onclick="window.open('/bpa/application/occupancy-certificate/generate-occupancy-certificate/${occupancyCertificate.applicationNumber}','popup','width=1100,height=700'); return false;">
									<spring:message code='lbl.print.occupancy.certificate' />
							</a></td>
						</c:if>
						<td>&nbsp;<input type="button" name="button2" class="btn btn-default" id="button2"
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
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
			<input type="hidden" id="feeAmount"
				value="<spring:message code='msg.validation.feeamount'/>" /> <input
				type="hidden" id="incrFeeamtTopOfsysCalcFee"
				value="<spring:message code='msg.validation.incrontopof.systemcalc.feeamount'/>" />
		</div>
	</div>
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
	src="<cdn:url value='/resources/js/app/bpa-ajax-helper.js?rnd=${app_release_no}'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/occupancy-certificate/oc-edcr-helper.js?rnd=${app_release_no}'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/occupancy-certificate/occupancy-certificate-view.js?rnd=${app_release_no}'/>"></script>
