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
		<form:form role="form" action="" method="post"
			modelAttribute="occupancyCertificate" id="ocDocumentscrutinyform"
			cssClass="form-horizontal form-groups-bordered"
			enctype="multipart/form-data">
			<form:hidden path="" value="${areaInSqMtr}" id="areaInSqMtr"
				name="areaInSqMtr" />
			<form:hidden path="" value="${currentDesignation}"
				id="currentDesignation" name="currentDesignation" />
			<form:hidden path="" value="${isApproveValid}" id="isApproveValid"
				name="isApproveValid" />
			<form:hidden path="" id="wfstateDesc"
				value="${occupancyCertificate.state.value}" />
			<input type="hidden" id="authorizedToSubmitPlan"
				value="${occupancyCertificate.authorizedToSubmitPlan}" />
			<ul class="nav nav-tabs" id="settingstab">
				<li class="active"><a data-toggle="tab"
					href="#application-info" data-tabidx=0><spring:message
							code='lbl.appln.details' /></a></li>
				<li><a data-toggle="tab" href="#document-info" data-tabidx=1><spring:message
							code='title.documentdetail' /></a></li>
				<li><a data-toggle="tab" href="#noc-document-info"
					data-tabidx=2><spring:message code='lbl.noc.details' /></a></li>
			</ul>
			<div class="tab-content">
				<div id="application-info" class="tab-pane fade in active">
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="view-oc-application-details.jsp"></jsp:include>
					</div>
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include
							page="../application/edcr-application-details-form.jsp"></jsp:include>
					</div>
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="view-bpa-basic-application-details.jsp"></jsp:include>
					</div>
					<c:if test="${not empty  occupancyCertificate.receipts}">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="view-oc-receipt-details.jsp"></jsp:include>
						</div>
					</c:if>
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="../application/applicationhistory-view.jsp"></jsp:include>
					</div>
				</div>
				<div id="document-info" class="tab-pane fade">
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="view-oc-dcr-documents.jsp"></jsp:include>
						<jsp:include page="view-oc-documents.jsp"></jsp:include>
					</div>
				</div>
				<div id="noc-document-info" class="tab-pane fade">
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="view-oc-noc-documents.jsp"></jsp:include>
					</div>
				</div>
			</div>
			<div align="center">
				<%--<c:if test="${ occupancyCertificate.status.code eq 'Scheduled For Document Scrutiny'
                                    || occupancyCertificate.status.code eq 'Pending For Rescheduling For Document Scrutiny'
                                    || occupancyCertificate.status.code eq 'Rescheduled For Document Scrutiny'}">
                        <a
                                href="/bpa/application/scrutiny/view/${occupancyCertificate.applicationNumber}"
                                class="btn btn-primary"> View Scheduled Appointment Details </a>
                    </c:if>
                    <c:if test="${collectFeeValidate eq ''}">
                        <c:if test="${ occupancyCertificate.status.code eq 'Registered'}">
                            <a
                                    href="/bpa/application/occupancy-certificate/scrutiny/schedule/${occupancyCertificate.applicationNumber}"
                                    class="btn btn-primary"> New Appointment </a>
                        </c:if>
                        <!-- Reschedule appointment not supported for one day permit application -->
                        <c:if test="${ mode eq 'showRescheduleToEmployee' && !occupancyCertificate.isOneDayPermitApplication}">
                            <a
                                    href="/bpa/application/occupancy-certificate/scrutiny/reschedule/${occupancyCertificate.applicationNumber}"
                                    class="btn btn-primary"> Reschedule Appointment </a>
                        </c:if>
                         <c:if test="${ occupancyCertificate.status.code eq 'Scheduled For Document Scrutiny' || occupancyCertificate.status.code eq 'Rescheduled For Document Scrutiny'}">
                            <a
                                    href="/bpa/application/occupancy-certificate/document-scrutiny/${occupancyCertificate.applicationNumber}"
                                    class="btn btn-primary"> Document Scrutiny </a>
                        </c:if>
                    </c:if>--%>
				<input type="button" name="button2" id="button2" value="Close"
					class="btn btn-default" onclick="window.close();" />
			</div>
		</form:form>
		<input type="hidden" id="requiredSignedDocuments" value="<spring:message code='msg.validate.license.requires.tosubmt.signed.plandocs.ondocscrutiny' />"/>
		<input type="hidden" id="confirmRejection" value="<spring:message code='msg.confirm.intiate.rejection.forappln' />"/>
		<input type="hidden" id="forwardApplication" value="<spring:message code='msg.confirm.forward.application' />"/>
		<input type="hidden" id="rejectionReason" value="<spring:message code='msg.validate.onerejection.reason.mandatory' />"/>
		<input type="hidden" id="rejectionComments" value="<spring:message code='msg.validate.enter.rejection.comments' />"/>
		<input type="hidden" id="feeAmount" value="<spring:message code='msg.validation.feeamount'/>"/>
		<input type="hidden" id="incrFeeamtTopOfsysCalcFee" value="<spring:message code='msg.validation.incrontopof.systemcalc.feeamount'/>"/>
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
		<input type="hidden" id="valuesCannotEmpty" value="<spring:message code='msg.validate.values.cannot.empty' />" />		
	</div>
</div>

<link rel="stylesheet"
	href="<c:url value='/resources/css/bpa-style.css?rnd=${app_release_no}'/>">
<link rel="stylesheet"
	href="<c:url value='/resources/global/css/bootstrap/bootstrap-tagsinput.css?rnd=${app_release_no}' context='/egi'/>">
<script
	src="<c:url value='/resources/global/js/bootstrap/bootstrap-tagsinput.min.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script
	src="<cdn:url value='/resources/global/js/egov/inbox.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/application-view.js?rnd=${app_release_no}'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/occupancy-certificate/oc-document-scrutiny.js?rnd=${app_release_no}'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/occupancy-certificate/oc-edcr-helper.js?rnd=${app_release_no}'/>"></script>

