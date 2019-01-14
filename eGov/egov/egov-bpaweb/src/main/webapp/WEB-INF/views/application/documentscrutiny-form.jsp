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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>
<div class="row">
	<div class="col-md-12">
		<form:form role="form" method="post" modelAttribute="bpaApplication"
				   id="documentscrutinyform"
				   cssClass="form-horizontal form-groups-bordered"
				   enctype="multipart/form-data">

			<form:hidden path="" id="collectFeeValidate" value="${collectFeeValidate}" />
			<ul class="nav nav-tabs" id="settingstab">
				<li class="active"><a data-toggle="tab" href="#applicant-info"
									  data-tabidx=0><spring:message code='lbl.appln.details' /></a></li>
				<li><a data-toggle="tab" href="#document-info" data-tabidx=1><spring:message
						code='title.documentdetail' /></a></li>
				<c:if test="${not empty bpaApplication.applicationNOCDocument}">
					<li><a data-toggle="tab" href="#noc-info" data-tabidx=2><spring:message
							code='lbl.noc.details' /></a></li>
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
				<c:if test="${ not empty bpaApplication.applicationNOCDocument}">
					<div id="noc-info" class="tab-pane fade">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="view-noc-document.jsp"></jsp:include>
						</div>
					</div>
				</c:if>
			</div>

			<div align="center">
					<%--<c:if test="${ bpaApplication.status.code eq 'Scheduled For Document Scrutiny'
                                    || bpaApplication.status.code eq 'Pending For Rescheduling For Document Scrutiny'
                                    || bpaApplication.status.code eq 'Rescheduled For Document Scrutiny'}">
                        <a
                                href="/bpa/application/scrutiny/view/${bpaApplication.applicationNumber}"
                                class="btn btn-primary"> View Scheduled Appointment Details </a>
                    </c:if>
                    <c:if test="${collectFeeValidate eq ''}">
                        <c:if test="${ bpaApplication.status.code eq 'Registered'}">
                            <a
                                    href="/bpa/application/scrutiny/schedule/${bpaApplication.applicationNumber}"
                                    class="btn btn-primary"> New Appointment </a>
                        </c:if>
                        <!-- Reschedule appointment not supported for one day permit application -->
                        <c:if test="${ mode eq 'showRescheduleToEmployee' && !bpaApplication.isOneDayPermitApplication}">
                            <a
                                    href="/bpa/application/scrutiny/reschedule/${bpaApplication.applicationNumber}"
                                    class="btn btn-primary"> Reschedule Appointment </a>
                        </c:if>
                         <c:if test="${ bpaApplication.status.code eq 'Scheduled For Document Scrutiny' || bpaApplication.status.code eq 'Rescheduled For Document Scrutiny'}">
                            <a
                                    href="/bpa/application/documentscrutiny/${bpaApplication.applicationNumber}"
                                    class="btn btn-primary"> Document Scrutiny </a>
                        </c:if>
                    </c:if>--%>
				<input
						type="button" name="button2" id="button2" value="Close"
						class="btn btn-default" onclick="window.close();"/>
			</div>
		</form:form>
	</div>
</div>

<script
		src="<cdn:url value='/resources/global/js/egov/inbox.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script
		src="<cdn:url value='/resources/js/app/application-view.js?rnd=${app_release_no}'/>"></script>
<script
		src="<cdn:url value='/resources/js/app/edcr-helper.js?rnd=${app_release_no}'/>"></script>