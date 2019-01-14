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
			modelAttribute="bpaApplication" id="documentscrutinyform"
			cssClass="form-horizontal form-groups-bordered"
			enctype="multipart/form-data">
			<form:hidden path="" value="${areaInSqMtr}" id="areaInSqMtr"
				name="areaInSqMtr" />
			<form:hidden path="" value="${currentDesignation}"
				id="currentDesignation" name="currentDesignation" />
			<form:hidden path="" value="${isApproveValid}" id="isApproveValid"
				name="isApproveValid" />
			<form:hidden path="" id="wfstateDesc"
						 value="${bpaApplication.state.value}" />
			<input type="hidden" id="authorizedToSubmitPlan" value="${bpaApplication.authorizedToSubmitPlan}"/>
				<ul class="nav nav-tabs" id="settingstab">
				<li class="active"><a data-toggle="tab" href="#doc-scrutiny-info"
					data-tabidx=0><spring:message code='lbl.document.scrutiny' /></a></li>
				<li><a data-toggle="tab" href="#applicant-info"
						   data-tabidx=1><spring:message code='lbl.appln.details' /></a></li>
				<li><a data-toggle="tab" href="#document-info" data-tabidx=2><spring:message
							code='title.documentdetail' /></a></li>
				<li><a data-toggle="tab" href="#checklist-info" data-tabidx=3><spring:message
						code='lbl.noc.doc.details' /></a></li>
				</ul>
			<div class="tab-content">
				<div id="doc-scrutiny-info" class="tab-pane fade in active">
					<div class="panel panel-primary" data-collapsed="0">
						<div class="panel-body custom-form ">
							<jsp:include page="viewapplication-details.jsp"></jsp:include>
						</div>
						<div class="panel-heading custom_form_panel_heading">
							<div class="panel-title">
								<spring:message code="lbl.document.scrutiny" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label text-right"><spring:message
									code="lbl.re.survey.no" /></label>
							<div class="col-sm-3 add-margin">
								<form:hidden path="documentScrutiny[0].application"
									id="scrutinyapplicationid" value="${bpaApplication.id}" />
								<form:hidden path="documentScrutiny[0].verifiedBy"
									id="verifiedById" value="${loginUser.id}" />
								<form:hidden path="documentScrutiny[0].reSurveynumber"
											 id="verifiedById" value="${bpaApplication.siteDetail[0].reSurveyNumber}" />
								<form:input class="form-control patternvalidation"
									maxlength="120" data-pattern="alphanumeric"
									data-role="tagsinput" id="reSurveynumber"
									path="" disabled="true"
									value="${bpaApplication.siteDetail[0].reSurveyNumber}" />
								<form:errors path="documentScrutiny[0].reSurveynumber"
									cssClass="add-margin error-msg" />
							</div>

							<%-- <label class="col-sm-2 control-label text-right"><spring:message
									code="lbl.subdivision.number" /></label>
							<div class="col-sm-3 add-margin">
								<form:input class="form-control patternvalidation"
									maxlength="120" data-pattern="alphanumeric"
									id="subdivisionNumber"
									path="documentScrutiny[0].subdivisionNumber"
									data-role="tagsinput"
									value="${bpaApplication.siteDetail[0].subdivisionNumber}" />
								<form:errors path="documentScrutiny[0].subdivisionNumber"
									cssClass="add-margin error-msg" />
							</div> --%>
						</div>

						<div class="form-group">

							<label class="col-sm-3 control-label text-right extentOfLand"><spring:message
									code="lbl.extent.of.land" /></label> <label
								class="col-sm-3 control-label text-right areaOfBase"><spring:message
									code="lbl.area.base" /> </label>
							<div class="col-sm-3 add-margin">
								<form:input class="form-control patternvalidation decimalfixed"
									maxlength="10" data-pattern="number" id="extentinsqmts"
									path="documentScrutiny[0].extentinsqmts" readonly="true" required="required"
									value="${bpaApplication.siteDetail[0].extentinsqmts}" />
								<form:errors path="documentScrutiny[0].extentinsqmts"
									cssClass="add-margin error-msg" />
							</div>
							<label class="col-sm-2 control-label text-right"><spring:message
									code="lbl.deednumber" /></label>
							<div class="col-sm-3 add-margin">
								<form:input class="form-control patternvalidation"
									maxlength="60" data-pattern="alphanumeric" id="deedNumber"
									path="documentScrutiny[0].deedNumber" />
								<form:errors path="documentScrutiny[0].deedNumber"
									cssClass="add-margin error-msg" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label text-right"><spring:message
									code="lbl.registraroffice" /></label>
							<div class="col-sm-3 add-margin">
								<form:input class="form-control patternvalidation"
									maxlength="120" data-pattern="alphanumeric" required="required"
									id="registrarOffice" readonly="true" path="documentScrutiny[0].registrarOffice"
									value="${bpaApplication.siteDetail[0].registrarOffice.registrarOffice.name}" />
								<form:errors path="documentScrutiny[0].registrarOffice"
									cssClass="add-margin error-msg" />
							</div>
							<label class="col-sm-2 control-label text-right"><spring:message
									code="lbl.nature.of.ownership" /></label>
							<div class="col-sm-3 add-margin">
								<form:input class="form-control patternvalidation"
									maxlength="120" data-pattern="alphanumeric"
									id="natureofOwnership" readonly="true"
									path="documentScrutiny[0].natureofOwnership"
									value="${bpaApplication.siteDetail[0].natureofOwnership}" />
								<form:errors path="documentScrutiny[0].natureofOwnership"
									cssClass="add-margin error-msg" />
							</div>
						</div>

						<div class="form-group"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label text-right"><spring:message
									code="lbl.taluk" /></label>
							<div class="col-sm-3 add-margin">
								<form:input class="form-control patternvalidation"
									maxlength="120" data-pattern="alphanumeric" id="taluk"
									path="documentScrutiny[0].taluk" readonly="true"
									value="${bpaApplication.siteDetail[0].postalAddress.taluk}" />
								<form:errors path="documentScrutiny[0].taluk"
									cssClass="add-margin error-msg" />
							</div>

							<label class="col-sm-2 control-label text-right"><spring:message
									code="lbl.district" /></label>
							<div class="col-sm-3 add-margin">
								<form:input class="form-control patternvalidation"
									maxlength="120" data-pattern="alphanumeric" id="district"
									path="documentScrutiny[0].district" readonly="true"
									value="${bpaApplication.siteDetail[0].postalAddress.district}" />
								<form:errors path="documentScrutiny[0].district"
									cssClass="add-margin error-msg" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label text-right"><spring:message
									code="lbl.detailof.neigbour" /></label>
							<div class="col-sm-3 add-margin">
								<c:forEach items="${documentScrutinyValues}" var="scrutinyVal">
									<div class="radio">
										<label><input type="radio" value="${scrutinyVal}" class="scrutinyValue"
													  name="documentScrutiny[0].neighoutOwnerDtlSubmitted"
												<c:if test="${scrutinyVal eq 'NOT_APPLICABLE'}"> checked="checked" </c:if> />${scrutinyVal.checkListVal}
										</label>
									</div>
								</c:forEach>
								<form:errors
									path="documentScrutiny[0].neighoutOwnerDtlSubmitted"
									cssClass="add-margin error-msg" />
							</div>
							<label class="col-sm-2 control-label text-right"><spring:message
									code="lbl.various.doc.matching" /></label>
							<div class="col-sm-3 add-margin">
								<c:forEach items="${documentScrutinyValues}" var="scrutinyVal">
									<div class="radio">
										<label><input type="radio" value="${scrutinyVal}" class="scrutinyValue"
													  name="documentScrutiny[0].whetherDocumentMatch"
												<c:if test="${scrutinyVal eq 'NOT_APPLICABLE'}"> checked="checked" </c:if> />${scrutinyVal.checkListVal}
										</label>
									</div>
								</c:forEach>
								<form:errors path="documentScrutiny[0].whetherDocumentMatch"
									cssClass="add-margin error-msg" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label text-right"><spring:message
									code="lbl.alldocument.attached" /></label>
							<div class="col-sm-3 add-margin">
								<c:forEach items="${documentScrutinyValues}" var="scrutinyVal">
									<div class="radio">
										<label><input type="radio" value="${scrutinyVal}" class="scrutinyValue"
													  name="documentScrutiny[0].whetherAllDocAttached"
												<c:if test="${scrutinyVal eq 'NOT_APPLICABLE'}"> checked="checked" </c:if> />${scrutinyVal.checkListVal}
										</label>
									</div>
								</c:forEach>
								<form:errors path="documentScrutiny[0].whetherAllDocAttached"
									cssClass="add-margin error-msg" />
							</div>
							<label class="col-sm-2 control-label text-right"><spring:message
									code="lbl.allpage.attached" /></label>
							<div class="col-sm-3 add-margin">
								<c:forEach items="${documentScrutinyValues}" var="scrutinyVal">
									<div class="radio">
										<label><input type="radio" value="${scrutinyVal}" class="scrutinyValue"
													  name="documentScrutiny[0].whetherAllPageOfDocAttached"
												<c:if test="${scrutinyVal eq 'NOT_APPLICABLE'}"> checked="checked" </c:if> />${scrutinyVal.checkListVal}
										</label>
									</div>
								</c:forEach>
								<form:errors
									path="documentScrutiny[0].whetherAllPageOfDocAttached"
									cssClass="add-margin error-msg" />
							</div>
						</div>
					</div>
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="rejection-reasons.jsp"></jsp:include>
					</div>
				</div>
				<div id="applicant-info" class="tab-pane fade">
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
					<div class="panel panel-primary" data-collapsed="0">
						<c:if test="${showDcrDocuments}">
							<div class="dcrDocuments">
								<jsp:include page="bpa-dcr-documents.jsp"></jsp:include>
							</div>
						</c:if>
						<jsp:include page="bpaDocumentDetails.jsp"></jsp:include>
					</div>
				</div>
				<div id="checklist-info" class="tab-pane fade">
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="noc-document-updation.jsp"></jsp:include>
					</div>
				</div>
			</div>

			<jsp:include page="../common/commonWorkflowMatrix.jsp" />
			<div class="buttonbottom" align="center">
				<jsp:include page="../common/commonWorkflowMatrix-button.jsp" />
			</div>
		</form:form>
	</div>
</div>

<link rel="stylesheet" href="<c:url value='/resources/css/bpa-style.css?rnd=${app_release_no}'/>">
<link rel="stylesheet"
	href="<c:url value='/resources/global/css/bootstrap/bootstrap-tagsinput.css?rnd=${app_release_no}' context='/egi'/>">
<script
	src="<c:url value='/resources/global/js/bootstrap/bootstrap-tagsinput.min.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script
	src="<cdn:url value='/resources/global/js/egov/inbox.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/application-view.js?rnd=${app_release_no}'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/document-scrutiny.js?rnd=${app_release_no}'/>"></script>
<script
		src="<cdn:url value='/resources/js/app/edcr-helper.js?rnd=${app_release_no}'/>"></script>
	
