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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<form:form role="form" action="/bpa/lettertoparty/lettertopartyreply"
	method="post" modelAttribute="lettertoParty" id="lettertoPartyReplyform"
	cssClass="form-horizontal form-groups-bordered"
	enctype="multipart/form-data">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-primary" data-collapsed="0">
				<jsp:include page="../application/viewapplication-details.jsp"></jsp:include>
				<div class="panel-heading custom_form_panel_heading">
					<div class="panel-title">
						<spring:message code="lbl.lp.details" />
					</div>
				</div>
				<div class="panel-body">
					<div class="row add-border">
						<div class="col-sm-3 add-margin">
							<spring:message code="lbl.lpNumber" />
						</div>
						<div class="col-sm-3 add-margin view-content">
							<c:out value="${lettertoParty.lpNumber}"></c:out>
						</div>
						<div class="col-sm-2 add-margin">
							<spring:message code="lbl.letterdate" />
						</div>
						<div class="col-sm-3 add-margin view-content">
							<fmt:formatDate value="${lettertoParty.letterDate}" pattern="dd/MM/yyyy" var="letterDate" />
							<c:out value="${letterDate}"></c:out>
						</div>
						<form:hidden path="application" id="applicationId"
							value="${application.id}" />

						<form:hidden path="letterDate" id="letterDate"
							value="${letterDate}" />
						<input type="hidden" id='lettertoParty' name="lettertoParty"
							value="${lettertoParty.id}">
					</div>
					<div class="row add-border">
						<div class="col-sm-3 add-margin">
							<spring:message code="lbl.lpreason" />
						</div>
						<div class="col-sm-3 add-margin  view-content">
							<c:forEach items="${lettertoParty.lpReason}" var="lpReason"
								varStatus="status">
								<c:out value="${lpReason.description}" />
								<c:if test="${!status.last}">,</c:if>
							</c:forEach>
						</div>
						<div class="col-sm-2 add-margin">
							<spring:message code="lbl.lpsentdate" />
						</div>
						<div class="col-sm-3 add-margin  view-content">
							<fmt:formatDate value="${lettertoParty.sentDate}" pattern="dd/MM/yyyy" var="sentDate" />
							<c:out value="${sentDate}"></c:out>
						</div>
						<form:hidden path="sentDate" id="sentDate" value="${sentDate}" />
					</div>
					<div class="row add-border">
						<div class="col-sm-3 add-margin">
							<spring:message code="lbl.lpdescription" />
						</div>
						<div class="col-sm-9 add-margin  view-content">
							<c:out value="${lettertoParty.lpDesc}" />
						</div>
					</div>

					<div class="row add-border">
						<div class="col-sm-3 add-margin">
							<spring:message code="lbl.lpreplydate" /><span class="mandatory"></span>
						</div>
						<div class="col-sm-3 add-margin">
							<form:input path="replyDate" class="form-control datepicker"
								data-date-end-date="0d" id="replyDate"
								data-inputmask="'mask': 'd/m/y'" required="required" />
							<form:errors path="replyDate" cssClass="add-margin error-msg" />
						</div>
						<label class="col-sm-2 control-label text-right"><spring:message
										code="lbl.lpReplyRemarks" /></label>
								<div class="col-sm-3 add-margin">
									<form:textarea path="lpReplyRemarks"
										class="form-control patternvalidation" data-pattern="alphanumericspecialcharacters"
										rows="5" id="lpDesc" maxlength="1016" />
									<form:errors path="lpReplyRemarks" cssClass="error-msg" />
								</div>
					</div>
				</div>
			</div>
			<div class="panel panel-primary" data-collapsed="0">
				<div class="panel-heading custom_form_panel_heading">
					<div class="panel-title">
						<spring:message code="lbl.encloseddocuments" />
						-
						<spring:message code="lbl.checklist" />
					</div>
				</div>

				<div class="panel-body">
						<div class="form-group">
							<div class="col-sm-2 view-content">
								<spring:message code="lbl.documentname" />
							</div>
							<div class="col-sm-2 view-content">
								<spring:message code="lbl.isrequested" />
							</div>
							<%--<div class="col-sm-2 text-center view-content">
								<spring:message code="lbl.issubmitted" />
							</div>--%>
							<div class="col-sm-4 view-content">
								<spring:message code="lbl.remarks" />
							</div>
							<div class="col-sm-4 view-content">
								<spring:message code="lbl.attachdocument" />
								<div class="add-margin error-msg">
									<font size="2"> <spring:message code="lbl.mesg.document" />
									</font>
								</div>
							</div>
						</div>
						<c:choose>
							<c:when test="${!lettertoPartyDocument.isEmpty()}">
						<c:forEach var="lpdoc" items="${lettertopartydocList}"
							varStatus="status">
							<form:hidden
								path="lettertoPartyDocument[${status.index}].lettertoParty"
								id="lettertopartyId" value="${lettertoParty.id}" />
							<form:hidden
								path="lettertoPartyDocument[${status.index}].checklistDetail"
								id="checklist" value="${doc.id}" />
									<div class="form-group">
										<div class="col-sm-2 add-margin check-text">
											<c:out value="${lpdoc.checklistDetail.description}" />
											<form:hidden
												id="lettertoPartyDocument${status.index}checklistDetail"
												path="lettertoPartyDocument[${status.index}].checklistDetail"
												value="${lpdoc.checklistDetail.id}" />
											<form:hidden
												id="lettertoPartyDocument${status.index}checklistDetail"
												path="lettertoPartyDocument[${status.index}].checklistDetail.isMandatory"
												value="${lpdoc.checklistDetail.isMandatory}" />
										</div>
										<div class="col-sm-2 add-margin">
											<form:checkbox
												id="lettertoPartyDocument${status.index}isrequested"
												path="lettertoPartyDocument[${status.index}].isrequested"
												value="lettertoPartyDocument${status.index}isrequested"
												disabled="true" />
										</div>
										<%--<div class="col-sm-2 add-margin text-center">
											<form:checkbox
												id="lettertoPartyDocument${status.index}issubmitted"
												path="lettertoPartyDocument[${status.index}].issubmitted"
												value="lettertoPartyDocument${status.index}issubmitted" />
										</div>--%>

										<div class="col-sm-4 add-margin">
											<form:textarea class="form-control patternvalidation"
												data-pattern="alphanumericspecialcharacters" maxlength="248"
												id="lettertoPartyDocument${status.index}remarks" rows="3"
												path="lettertoPartyDocument[${status.index}].remarks" />
											<form:errors
												path="lettertoPartyDocument[${status.index}].remarks"
												cssClass="add-margin error-msg" />
										</div>

										<div class="col-sm-4 add-margin">
												<div class="files-upload-container"
													 data-file-max-size="5"
													 <c:if test="${lpdoc.isrequested eq true && fn:length(lpdoc.getSupportDocs()) eq 0}">required</c:if>
													 data-allowed-extenstion="doc,docx,xls,xlsx,rtf,pdf,txt,zip,jpeg,jpg,png,gif,tiff">
													<div class="files-viewer">

														<c:forEach items="${lpdoc.getSupportDocs()}" var="file" varStatus="status1">
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
														   data-file-input-name="lettertoPartyDocument[${status.index}].files">
															<i class="fa fa-plus"></i>
														</a>

													</div>
												</div>
										</div>

										</div>
						</c:forEach>
					</c:when>
				</c:choose>
				</div>
			</div>
		</div>
		</div>
	<div class="text-center">
		<button type='submit' class='btn btn-primary' id="btnLPReply">
			<spring:message code='lbl.reply' />
		</button>

		<button type="submit" class="btn btn-primary"
			onclick="return getUrlToPring()">
			<spring:message code="lbl.print.lettertoparty" />
		</button>
		<a href='javascript:void(0)' class='btn btn-default'
			onclick='self.close()'><spring:message code='lbl.close' /></a>
	</div>
</form:form>

<!-- The Modal -->
<div id="imgModel" class="image-modal">
	<span class="closebtn">&times;</span> <img class="modal-content"
											   id="previewImg">
	<div id="caption"></div>
</div>

<script
		src="<cdn:url value='/resources/global/js/egov/inbox.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script
	src="<cdn:url value='/resources/global/js/bootstrap/bootstrap-datepicker.js' context='/egi'/>"></script>
<link rel="stylesheet"
	href="<cdn:url value='/resources/global/css/bootstrap/bootstrap-datepicker.css' context='/egi'/>">
<script
	src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/moment.min.js' context='/egi'/>"></script>
<link rel="stylesheet" href="<c:url value='/resources/css/bpa-style.css?rnd=${app_release_no}'/>">
<script
	src="<cdn:url value='/resources/js/app/lettertoparty.js?rnd=${app_release_no}'/> "></script>
<script
		src="<cdn:url value='/resources/js/app/document-upload-helper.js?rnd=${app_release_no}'/>"></script>
