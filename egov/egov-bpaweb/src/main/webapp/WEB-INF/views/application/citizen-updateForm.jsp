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
			<form:form role="form" modelAttribute="bpaApplication" action="/bpa/application/citizen/update-submit/${bpaApplication.applicationNumber}" method="post"
			id="editCitizenApplicationform"
			cssClass="form-horizontal form-groups-bordered"
			enctype="multipart/form-data">
			<form:hidden path="" id="workFlowAction" name="workFlowAction"/>
			<form:hidden path="" id="onlinePaymentEnable" name="onlinePaymentEnable" value="${onlinePaymentEnable}" />
			<form:hidden path="" id="wfstateDesc" value="${bpaApplication.state.value}" />
			<input type="hidden" name="bpaApplication" value="${bpaApplication.id}">
			<input type="hidden" name="citizenOrBusinessUser" id="citizenOrBusinessUser" value="${citizenOrBusinessUser}">
			<input type="hidden"  id="validateCitizenAcceptance" name="validateCitizenAcceptance" value="${validateCitizenAcceptance}"/>
			<input type="hidden"  id="citizenDisclaimerAccepted" name="citizenDisclaimerAccepted" value="${citizenDisclaimerAccepted}"/>
			<input type="hidden"  id="isCitizen" name="isCitizen" value="${isCitizen}"/>
			<input type="hidden" id="invalidStakeholder" name="invalidStakeholder" value="${invalidStakeholder}" />
			<input type="hidden" id="noJAORSAMessage" name="noJAORSAMessage" value="${noJAORSAMessage}" />
			<input type="hidden" id="serviceTypeCode" value="${bpaApplication.serviceType.code}" />
			<input type="hidden"  id="isEDCRIntegrationRequire" value="${isEDCRIntegrationRequire}"/>
			<input type="hidden"  id="loadingFloorDetailsFromEdcrRequire" value="${loadingFloorDetailsFromEdcrRequire}"/>
			<input type="hidden"  id="isReconciliationInProgress" value="${isReconciliationInProgress}"/>
			<form:hidden path="authorizedToSubmitPlan" id="authorizedToSubmitPlan"/>
			<input type="hidden"  id="stakeHolderType" value="${bpaApplication.stakeHolder[0].getStakeHolder().getStakeHolderType().getStakeHolderTypeVal()}"/>
		<ul class="nav nav-tabs" id="settingstab">
				<li class="active"><a data-toggle="tab"
					href="#appliccation-info" data-tabidx=0><spring:message
							code='lbl.appln.details' /></a></li>
				<li><a data-toggle="tab" href="#document-info" data-tabidx=1><spring:message
							code='title.documentdetail' /></a></li>
			<li><a data-toggle="tab" href="#noc-document-info" data-tabidx=2><spring:message
					code='lbl.noc.doc.details' /></a></li>
			</ul>
		<div class="tab-content">
			<div id="document-info" class="tab-pane fade">
				<div class="panel panel-primary" data-collapsed="0">
					<div class="dcrDocuments">
						<jsp:include page="bpa-dcr-documents.jsp"></jsp:include>
					</div>
					<jsp:include page="bpaDocumentDetails.jsp"></jsp:include>
				</div>
			</div>
				<div id="appliccation-info" class="tab-pane fade in active">
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="applicationDetails.jsp"></jsp:include>
					</div>
					<div class="panel panel-primary edcrApplnDetails" data-collapsed="0">
						<jsp:include page="edcr-application-details-form.jsp"></jsp:include>
					</div>
					<c:if test="${not empty  bpaApplication.buildingSubUsages}">
						<div class="panel panel-primary edcrApplnDetails" data-collapsed="0">
							<jsp:include page="building-subusages.jsp" />
						</div>
					</c:if>
					<div class="panel panel-primary" data-collapsed="0" id="applicantDiv">
						<jsp:include page="applicantDetailForm.jsp"></jsp:include>
					</div>
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="siteDetail.jsp"></jsp:include>
					</div>
					<div class="panel panel-primary demolitionDetails" data-collapsed="0">
						<jsp:include page="demolition-details.jsp" />
					</div>
					<c:choose>
						<c:when test="${isEDCRIntegrationRequire && loadingFloorDetailsFromEdcrRequire}">
							<div class="panel panel-primary existingbuildingdetails" data-collapsed="0">
								<jsp:include page="edcr-existing-bldg-details.jsp" />
							</div>
							<div class="panel panel-primary edcrbuildingdetails" data-collapsed="0">
								<jsp:include page="edcr-buildingdetails.jsp" />
							</div>
						</c:when>
						<c:otherwise>
							<div class="panel panel-primary existingbuildingdetails"
								 data-collapsed="0">
								<jsp:include page="existing-buildingdetails.jsp" />
							</div>
							<div class="panel panel-primary buildingdetails" data-collapsed="0">
								<jsp:include page="buildingDetails.jsp" />
							</div>
						</c:otherwise>
					</c:choose>
					<c:if test="${(isCitizen && validateCitizenAcceptance) || (!isCitizen)}">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="disclaimer.jsp" />
						</div>
					</c:if>
					<c:if test="${not empty  bpaApplication.receipts}">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="view-bpa-receipt-details.jsp"></jsp:include>
						</div>
					</c:if>
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="applicationhistory-view.jsp"></jsp:include>
					</div>
				</div>
				<div id="noc-document-info" class="tab-pane fade">
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="noc-document-updation.jsp"></jsp:include>
					</div>
				</div>
			</div>
			<div class="buttonbottom" align="center" id="buttonDiv">
				<table>
					<tr>
						<td>
							<c:if test="${isReconciliationInProgress ne true}">
								<form:button type="submit" id="buttonSave" class="btn btn-primary"
											 value="Save"><spring:message code='lbl.btn.save' /></form:button>
								<c:if test="${citizenOrBusinessUser && bpaApplication.id !=null && bpaApplication.state==null && !isCitizen && bpaApplication.status.code ne 'Cancelled'}">
									<form:button type="submit" id="buttonSubmit" class="btn btn-primary"
												 value="Submit"><spring:message code='lbl.btn.submit' /></form:button>
									<form:button type="button" id="buttonCancel" class="btn btn-danger"
												 value="Cancel Application"> <spring:message code='lbl.btn.cancel.application' /> </form:button>
								</c:if>
							</c:if>
							<input type="button" name="button2" id="button2" value="Close"
								   class="btn btn-default" onclick="window.close();"/>
						</td>
					</tr>
				</table>
			</div>
		</form:form>
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
	src="<cdn:url value='/resources/js/app/documentsuploadvalidation.js?rnd=${app_release_no}'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/buildingarea-details.js?rnd=${app_release_no}'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/bpa-application-validations.js?rnd=${app_release_no}'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/citizen-update-helper.js?rnd=${app_release_no}'/>"></script>
<script
		src="<cdn:url value='/resources/js/app/edcr-helper.js?rnd=${app_release_no}'/>"></script>