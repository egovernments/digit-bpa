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
		<form:form role="form" action="/bpa/citizen/application/permit/renewal/update/${permitRenewal.applicationNumber}" method="post"
			modelAttribute="permitRenewal" id="permitRenewalInitiateForm"
			cssClass="form-horizontal form-groups-bordered"
			enctype="multipart/form-data">
			<input type="hidden" id="workFlowAction" name="workFlowAction" />
			<input type="hidden" id="permitRenewal" name="permitRenewal" value="${permitRenewal.id}" />
			<input type="hidden" id="id" name="id" value="${permitRenewal.id}" />
			<div class="panel panel-primary" data-collapsed="0">

				<div class="panel-heading custom_form_panel_heading">
					<div class="panel-title">
						<spring:message code="lbl.appln.details" />
					</div>
				</div>
				<div class="panel-body">
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.edcr.number" /> <span class="mandatory"></span></label>
						<div class="col-sm-3 add-margin">
							<form:input class="form-control patternvalidation resetValues"
								maxlength="20" id="planPermissionNumber"
								placeholder="Enter plan permission number"
								path="parent.planPermissionNumber"
								value="${permitRenewal.parent.planPermissionNumber}"
								required="required" />
							<form:hidden path="parent" id="parent" />
						</div>
						<label class="col-sm-2 control-label text-right"><spring:message
								code="lbl.appln.date" /><span class="mandatory"></span> </label>
						<div class="col-sm-3 add-margin">
							<form:input path="applicationDate"
								class="form-control datepicker" data-date-end-date="0d"
								id="applicationDate" data-inputmask="'mask': 'd/m/y'"
								required="required" disabled="true" />
							<form:errors path="applicationDate"
								cssClass="add-margin error-msg" />
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.build.plan.permission.date" /> </label>
						<div class="col-sm-3 add-margin">
							<input type="text" name="planPermissionDate"
								id="planPermissionDate"
								class="form-control planPermissionDate resetValues"
								value="${permitRenewal.parent.planPermissionDate}"
								readonly="readonly">
						</div>
						<label class="col-sm-2 control-label text-right"><spring:message
								code="lbl.service.type" /> </label>
						<div class="col-sm-3 add-margin">
							<input type="text" name="serviceTypeDesc" id="serviceTypeDesc"
								class="form-control resetValues serviceType" readonly="readonly"
								value="${permitRenewal.parent.serviceType.description}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.applicant.name" /> </label>
						<div class="col-sm-3 add-margin">
							<input type="text" name="applicantName" id="applicantName"
								class="form-control applicantName resetValues"
								value="${permitRenewal.parent.owner.name}" readonly="readonly">
						</div>
						<label class="col-sm-2 control-label text-right"><spring:message
								code="lbl.occupancy.type" /> </label>
						<div class="col-sm-3 add-margin">
							<input type="text" name="" id="occupancy"
								class="form-control occupancy resetValues"
								value="${permitRenewal.parent.occupanciesName}"
								readonly="readonly">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.extentin.sqmts" /> </label>
						<div class="col-sm-3 add-margin">
							<input type="text" name="" id="extentInSqmts"
								class="form-control extentInSqmts resetValues decimalfixed"
								value="${permitRenewal.parent.siteDetail[0].extentinsqmts}"
								readonly="readonly">
						</div>
						<label class="col-sm-2 control-label text-right"><spring:message
								code="lbl.exist.permit.expiry" /> </label>
						<div class="col-sm-3 add-margin">
							<input type="text" name="" id="existingPermitExpiryDate"
								class="form-control existingPermitExpiryDate resetValues"
								value="${existingPermitExpiryDate}" readonly="readonly">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-3 control-label text-right constStages"><spring:message
								code="lbl.cons.stages" /><span class="mandatory"></span></label>
						<div class="col-sm-3 add-margin">
							<form:select path="constructionStage" data-first-option="false"
								id="constructionStage" cssClass="form-control"
								required="required">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${constStages}" itemValue="id"
									itemLabel="code" />
							</form:select>
							<form:errors path="constructionStage"
								cssClass="add-margin error-msg" />
						</div>
						<label class="col-sm-2 control-label text-right"><spring:message
								code="lbl.if.cons.not.cmplted" /><span class="mandatory"></span></label>
						<div class="col-sm-3 add-margin">
							<form:input class="form-control patternvalidation"
								data-pattern="alphanumericwithspace" maxlength="128"
								id="constructionStatus" path="constructionStatus"
								required="required" />
							<form:errors path="constructionStatus"
								cssClass="add-margin error-msg" />
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.permit.renewal.doc.upload" /><span class="mandatory"></span></label>
						<div class="col-sm-6 add-margin">
							<div class="files-upload-container"
								data-allowed-extenstion="doc,docx,xls,xlsx,rtf,pdf"
								<c:if test="${fn:length(permitRenewal.permitRenewalDocs) eq 0}">required</c:if>
								data-file-max-size="4">
								<div class="files-viewer"
									data-existing-files="${fn:length(permitRenewal.permitRenewalDocs)}">
									<c:if test="${fn:length(permitRenewal.permitRenewalDocs) gt 0}">
										<c:forEach items="${permitRenewal.permitRenewalDocs}"
											var="file" varStatus="status1">
											<div class="file-viewer" data-toggle="tooltip"
												data-placement="top" title="${file.fileName}">
												<a class="download" target="_blank"
													href="/bpa/application/downloadfile/${file.fileStoreId}"></a>
												<i class="fa fa-file-pdf-o" aria-hidden="true"></i> <span
													class="doc-numbering">${status1.index+1}</span>
											</div>
										</c:forEach>
									</c:if>
									<c:if test="${fn:length(permitRenewal.permitRenewalDocs) eq 0}">
										<a href="javascript:void(0);" class="file-add"> <i
											class="fa fa-plus" aria-hidden="true"></i>
										</a>
									</c:if>
								</div>
								<input type="file" name="files" class="filechange inline btn"
									style="display: none;" />
							</div>
						</div>
					</div>

				</div>
			</div>
			<div class="panel panel-primary" data-collapsed="0">
				<jsp:include page="permit-renewal-disclaimer.jsp" />
			</div>
			<div align="center">
				<form:button type="submit" id="prSave" class="btn btn-primary"
					value="Save">
					<spring:message code="lbl.save" />
				</form:button>
				<form:button type="submit" id="prSubmit" class="btn btn-primary"
					value="Submit">
					<spring:message code="lbl.submit" />
				</form:button>
				<input type="button" name="button2" id="button2" value="Close"
					class="btn btn-default" onclick="window.close();" />
			</div>
		</form:form>

		<!-- Start --- For javascript messages localization purpose following hidden input tags used -->
		<input type="hidden" id="saveAppln"
			value="<spring:message code='msg.confirm.save.appln'/>" /> <input
			type="hidden" id="submitAppln"
			value="<spring:message code='msg.confirm.submit.appln'/>" /> <input
			type="hidden" id="uploadMsg"
			value="<spring:message code='msg.upload' />" />
		<!-- End --- For javascript messages localization purpose following hidden input tags used -->
	</div>
</div>

<script
	src="<cdn:url value='/resources/global/js/egov/inbox.js?rnd=${app_release_no}' context='/egi'/>"></script>
<link rel="stylesheet"
	href="<c:url value='/resources/css/bpa-style.css?rnd=${app_release_no}'/>">
<script
	src="<cdn:url value='/resources/js/app/document-upload-helper.js?rnd=${app_release_no}'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/permit-renewal/citizen-permit-renewal-helper.js?rnd=${app_release_no}'/>"></script>

