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

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="panel-heading custom_form_panel_heading">
	<div class="panel-title">
		<spring:message code="lbl.upload.image" />
	</div>
</div>
<div class="row">
	<label class="col-sm-2 control-label text-right"></label>
	<div class="col-sm-10">
		<div class="files-upload-container"
			data-allowed-extenstion="jpeg,jpg,png,gif" data-file-max-size="4">
			<div class="files-viewer"
				data-existing-files="${fn:length(inspectionSupportDocs)}">
				<c:forEach var="image" items="${inspection.encodedImages}"
					varStatus="status1">
					<div class="file-viewer"
						style="background:url('data:image/jpeg;base64,${image.value}');">
						<a href="javascript:void(0);" data-id="${image.key}"></a>
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
	src="<cdn:url value='/resources/js/app/document-upload-helper.js?rnd=${app_release_no}'/>"></script>
<link rel="stylesheet" href="<c:url value='/resources/css/bpa-style.css?rnd=${app_release_no}'/>">
