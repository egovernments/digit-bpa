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
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<div class="panel-heading custom_form_panel_heading">
	<div class="panel-title">
		<spring:message code="lbl.revoke.details" />
	</div>
</div>
<div class="panel-body">
	<table class="table table-bordered  multiheadertbl"
		id=permitRevocationDetails>
		<thead>
			<tr>
				<th><spring:message code="lbl.nature.of.req" /></th>
				<th><spring:message code="lbl.req.date" /></th>
				<th><spring:message code="lbl.rply.date" /></th>
				<th><spring:message code="lbl.issued.by" /></th>
				<th><spring:message code="lbl.remarks" /></th>
				<th><spring:message code="lbl.attachdocument" /><br>(<spring:message
						code="lbl.mesg.document" />)</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty permitRevocation.revocationDetails }">
					<c:forEach items="${permitRevocation.revocationDetails}"
						var="revokeDetail" varStatus="status">
						<tr>
							<td>
								<div class="input-group">
									<form:textarea
										class="form-control patternvalidation textarea-content"
										data-pattern="alphanumericspecialcharacters" maxlength="999"
										id="revocationDetails${status.index}natureOfRequest"
										path="revocationDetails[${status.index}].natureOfRequest" />
									<form:errors
										path="revocationDetails[${status.index}].natureOfRequest"
										cssClass="add-margin error-msg" />
									<span class="input-group-addon showModal"
										data-assign-to="revocationDetails${status.index}natureOfRequest"
										data-header="Nature Of Request"><span
										class="glyphicon glyphicon-pencil" style="cursor: pointer"></span></span>
								</div>
							</td>
							<td><form:input class="form-control datepicker requestDate"
									maxlength="50" data-date-end-date="0d"
									data-inputmask="'mask': 'd/m/y'"
									path="revocationDetails[${status.index}].requestDate" /> <form:errors
									path="revocationDetails[${status.index}].requestDate"
									cssClass="add-margin error-msg" /></td>
							<td><form:input class="form-control datepicker replyDate"
									maxlength="50" data-date-end-date="0d"
									data-inputmask="'mask': 'd/m/y'"
									path="revocationDetails[${status.index}].replyDate" /> <form:errors
									path="revocationDetails[${status.index}].replyDate"
									cssClass="add-margin error-msg" /></td>
							<td><form:input class="form-control issuedBy"
									path="revocationDetails[${status.index}].issuedBy" /><form:errors path="revocationDetails[${status.index}].issuedBy"
										cssClass="add-margin error-msg" /></td>
							<td>
								<div class="input-group">
									<form:textarea
										class="form-control patternvalidation textarea-content remarks"
										data-pattern="alphanumericspecialcharacters"
										data-input-element="revocationDetails${status.index}remarks"
										maxlength="999" id="revocationDetails${status.index}remarks"
										path="revocationDetails[${status.index}].remarks" />
									<form:errors path="revocationDetails[${status.index}].remarks"
										cssClass="add-margin error-msg" />
									<span class="input-group-addon showModal"
										data-assign-to="revocationDetails${status.index}remarks"
										data-header="Remarks"><span
										class="glyphicon glyphicon-pencil" style="cursor: pointer"></span></span>
								</div>
							</td>
							<td>
								<div class="files-upload-container" data-file-max-size="5"
									data-allowed-extenstion="doc,docx,xls,xlsx,rtf,pdf,txt,zip,jpeg,jpg,png,gif,tiff">
									<div class="files-viewer">
										<c:forEach items="${revokeDetail.getRevokeSupportDocs()}"
											var="file" varStatus="status1">
											<div class="file-viewer" data-toggle="tooltip"
												data-placement="top" title="${file.fileName}">
												<a class="download" target="_blank"
													href="/bpa/application/downloadfile/${file.fileStoreId}"></a>
												<c:choose>
													<c:when test="${file.contentType eq 'application/pdf'}">
														<i class="fa fa-file-pdf-o" aria-hidden="true"></i>
													</c:when>
													<c:when test="${file.contentType eq 'application/txt'}">
														<i class="fa fa-file-text-o" aria-hidden="true"></i>
													</c:when>
													<c:when
														test="${file.contentType eq 'application/rtf' || file.contentType eq 'application/doc' || file.contentType eq 'application/docx' || file.contentType eq 'application/msword' || file.contentType eq 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'}">
														<i class="fa fa-file-word-o" aria-hidden="true"></i>
													</c:when>
													<c:when test="${file.contentType eq 'application/zip'}">
														<i class="fa fa-file-archive-o" aria-hidden="true"></i>
													</c:when>
													<c:when
														test="${file.contentType eq 'image/jpg' || file.contentType eq 'image/jpeg' || file.contentType eq 'image/png' || file.contentType eq 'image/gif' || file.contentType eq 'image/tiff'}">
														<i class="fa fa-picture-o" aria-hidden="true"></i>
													</c:when>
													<c:when
														test="${file.contentType eq 'application/xls' || file.contentType eq 'application/xlsx' || file.contentType eq 'application/vnd.ms-excel'}">
														<i class="fa fa-file-excel-o" aria-hidden="true"></i>
													</c:when>
													<c:otherwise>
														<i class="fa fa-file-o" aria-hidden="true"></i>
													</c:otherwise>
												</c:choose>
												<span class="doc-numbering">${status1.index+1}</span>
											</div>
										</c:forEach>

										<a href="javascript:void(0);" class="file-add"
											data-unlimited-files="true"
											data-file-input-name="revocationDetails[${status.index}].files">
											<i class="fa fa-plus"></i>
										</a>

									</div>
								</div>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td>
							<div class="input-group">
								<form:textarea
									class="form-control patternvalidation textarea-content"
									data-pattern="alphanumericspecialcharacters" maxlength="512"
									id="revocationDetails0natureOfRequest"
									path="revocationDetails[0].natureOfRequest" />
								<form:errors path="revocationDetails[0].natureOfRequest"
									cssClass="add-margin error-msg" />
								<span class="input-group-addon showModal"
									data-assign-to="revocationDetails0natureOfRequest"
									data-header="Nature Of Request"><span
									class="glyphicon glyphicon-pencil" style="cursor: pointer"></span></span>
							</div>
						</td>
						<td><form:input class="form-control datepicker requestDate"
								maxlength="50" data-date-end-date="0d"
								data-inputmask="'mask': 'd/m/y'"
								path="revocationDetails[0].requestDate" /> <form:errors
								path="revocationDetails[0].requestDate"
								cssClass="add-margin error-msg" /></td>
						<td><form:input class="form-control datepicker replyDate"
								maxlength="50" data-date-end-date="0d"
								data-inputmask="'mask': 'd/m/y'"
								path="revocationDetails[0].replyDate" /> <form:errors
								path="revocationDetails[0].replyDate"
								cssClass="add-margin error-msg" /></td>
						<td><form:input class="form-control issuedBy"
									path="revocationDetails[0].issuedBy" /><form:errors path="revocationDetails[0].issuedBy"
										cssClass="add-margin error-msg" /></td>
						<td>
							<div class="input-group">
								<form:textarea
									class="form-control patternvalidation textarea-content remarks"
									data-pattern="alphanumericspecialcharacters"
									data-input-element="revocationDetails0remarks" maxlength="512"
									id="revocationDetails0remarks"
									path="revocationDetails[0].remarks" />
								<form:errors path="revocationDetails[0].remarks"
									cssClass="add-margin error-msg" />
								<span class="input-group-addon showModal"
									data-assign-to="revocationDetails0remarks"
									data-header="Remarks"><span
									class="glyphicon glyphicon-pencil" style="cursor: pointer"></span></span>
							</div>
						</td>
						<td>
							<div class="files-upload-container" data-file-max-size="5"
								data-allowed-extenstion="doc,docx,xls,xlsx,rtf,pdf,txt,zip,jpeg,jpg,png,gif,tiff">
								<div class="files-viewer">
									<a href="javascript:void(0);" class="file-add"
										data-unlimited-files="true"
										data-file-input-name="revocationDetails[0].files"> <i
										class="fa fa-plus"></i>
									</a>
								</div>
							</div>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	<div class="text-right add-padding">
		<button type="button" class="btn btn-sm btn-primary"
			id="addRevokeDtlRow">ADD ROW</button>
	</div>
</div>

<!-- Modal -->
<div class="modal fade" id="textarea-modal" role="dialog">
	<div class="modal-dialog modal-lg">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title" id="textarea-header"></h4>
			</div>
			<div class="modal-body">
				<textarea class="form-control textarea-content-of-modal"
					id="textarea-updatedcontent" maxlength="512" rows="10"></textarea>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary"
					id="textarea-btnupdate" data-dismiss="modal">Update</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>

			</div>
		</div>

	</div>
</div>

<!-- The Modal -->
<div id="imgModel" class="image-modal">
	<span class="closebtn">&times;</span> <img class="modal-content"
		id="previewImg">
	<div id="caption"></div>
</div>

<link rel="stylesheet" href="<c:url value='/resources/css/bpa-style.css?rnd=${app_release_no}'/>">
<script
	src="<cdn:url value='/resources/js/app/document-upload-helper.js?rnd=${app_release_no}'/>"></script>