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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>

<div class="panel-heading custom_form_panel_heading">
	<div class="panel-title">

	</div>
</div>
<div class="panel-body">
	<div class="form-group">
		<label class="col-sm-3 control-label text-right"><spring:message
				code="lbl.remarks"/><span class="mandatory"></span></label>
		<div class="col-sm-7 add-margin text-center">
			<form:textarea class="form-control patternvalidation"
						   data-pattern="alphanumericspecialcharacters" rows="5" maxlength="5000"
						   id="townSurveyorRemarks" path="townSurveyorRemarks" required="required"/>
			<form:errors path="townSurveyorRemarks"
						 cssClass="add-margin error-msg"/>
		</div>
	</div>
</div>

<div class="panel-heading custom_form_panel_heading">
	<div class="panel-title">
		<spring:message code="lbl.appln.upload.files" />
	</div>
</div>
<div class="row">
	<label class="col-sm-2 control-label text-right"></label>
	<div class="col-sm-10">
		<div class="files-upload-container"
			 data-allowed-extenstion="doc,docx,xls,xlsx,rtf,pdf,txt,zip,jpeg,jpg,png,gif,tiff" data-file-max-size="4">
			<div class="files-viewer"
				 data-existing-files="${fn:length(bpaApplication.tsInspnSupportDocs)}">

				<c:forEach items="${bpaApplication.tsInspnSupportDocs}" var="file" varStatus="status1">
					<div class="file-viewer" data-toggle="tooltip"
						 data-placement="top" title="${file.fileName}">
						<a class="download" target="_blank"
						   href="/bpa/application/downloadfile/${file.fileStoreId}" data-id="${file.id}"></a>
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

				<a href="javascript:void(0);" class="file-add"> <i
						class="fa fa-plus" aria-hidden="true"></i>
				</a>

			</div>

			<input type="file" name="files" class="filechange inline btn"
				   style="display: none;" /> <input type="file" name="files"
													class="filechange inline btn" style="display: none;" /> <input
				type="file" name="files" class="filechange inline btn"
				style="display: none;" /> <input type="file" name="files"
												 class="filechange inline btn" style="display: none;" /> <input
				type="file" name="files" class="filechange inline btn"
				style="display: none;" /><input
				type="file" name="files" class="filechange inline btn"
				style="display: none;" /><input
				type="file" name="files" class="filechange inline btn"
				style="display: none;" /><input
				type="file" name="files" class="filechange inline btn"
				style="display: none;" /><input
				type="file" name="files" class="filechange inline btn"
				style="display: none;" /><input
				type="file" name="files" class="filechange inline btn"
				style="display: none;" />
		</div>
	</div>
</div>

<!-- The Modal -->
<div id="imgModel" class="image-modal">
	<span class="closebtn">&times;</span> <img class="modal-content"
											   id="previewImg">
	<div id="caption"></div>
</div>

<script
		src="<cdn:url value='/resources/global/js/egov/inbox.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script
		src="<cdn:url value='/resources/js/app/document-upload-helper.js?rnd=${app_release_no}'/>"></script>
<link rel="stylesheet" href="<c:url value='/resources/css/bpa-style.css?rnd=${app_release_no}'/>">