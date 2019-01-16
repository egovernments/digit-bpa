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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<input type="hidden" id="dcrDocsAutoPopulate" name="dcrDocsAutoPopulate" value="${dcrDocsAutoPopulate}"/>
<input type="hidden" id="dcrDocsManuallyUpload" name="dcrDocsManuallyUpload" value="${dcrDocsManuallyUpload}"/>
<input type="hidden" id="dcrDocsAutoPopulateAndManuallyUpload" name="dcrDocsAutoPopulateAndManuallyUpload"
       value="${dcrDocsAutoPopulateAndManuallyUpload}"/>
<div class="panel-heading custom_form_panel_heading">
    <div class="panel-title">
        <spring:message code="lbl.plan.scrutiny.doc"/>
    </div>
</div>
<div class="panel-body">
    <c:if test="${dcrDocsManuallyUpload eq true || dcrDocsAutoPopulateAndManuallyUpload eq true || (dcrDocsAutoPopulate eq false && dcrDocsManuallyUpload eq false && dcrDocsAutoPopulateAndManuallyUpload eq false)}">
        <div class = "panel panel-info">
            <div class = "panel-heading">
                <h3 class = "panel-title">Note :</h3>
            </div>
            <div class = "panel-body view-content">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;All drawings shall be prepared, incorporating the details specified as per KMBR 1999 and related amendments, including specifications on paper size and scale conventions. Keep a blank space of not less than 10 cm x 10 cm, on the bottom right corner of every drawing for validation stamp.
            </div>
        </div>
    </c:if>
    <div class="row view-content header-color hidden-xs">
        <label class="col-sm-3 ">
            <spring:message code="lbl.documentname"/>
        </label>
        <label class="col-sm-3 ">
            <spring:message code="lbl.remarks"/>
        </label>
        <label class="col-sm-6 ">
            <spring:message code="lbl.attachdocument"/>
            <c:if test="${dcrDocsManuallyUpload eq true || dcrDocsAutoPopulateAndManuallyUpload eq true}">
                <span class="error-msg"> (Only Pdf files allowed)</span>
                <br>
                <small class="error-msg"><spring:message
                        code="lbl.mesg.document1"/></small>
            </c:if>
        </label>
    </div>
    <c:forEach items="${bpaApplication.getDcrDocuments()}" var="dcrDocument" varStatus="dcrDocStatus">
        <div class="row">
            <div class="col-sm-3 add-margin">
                <c:out value="${dcrDocument.checklistDtl.description}"></c:out>
                <c:if test="${dcrDocument.checklistDtl.isMandatory}">
                    <span class="mandatory"></span>
                </c:if>
                <form:hidden path="dcrDocuments[${dcrDocStatus.index}].checklistDtl"
                             value="${dcrDocument.checklistDtl.id}"/>
            </div>

            <div class="col-sm-3 add-margin">
                <form:textarea class="form-control patternvalidation"
                               data-pattern="alphanumericspecialcharacters" maxlength="500"
                               path="dcrDocuments[${dcrDocStatus.index}].remarks"
                               value="${dcrDocument.remarks}"/>
                <form:errors path="dcrDocuments[${dcrDocStatus.index}].remarks"
                             cssClass="add-margin error-msg"/>
            </div>
            <c:set var="splittedString" value="${fn:split(dcrDocument.checklistDtl.description, ' ')}"/>
            <c:set var="checklistName" value="${fn:join(splittedString, '_')}"/>
            <c:if test="${dcrDocsAutoPopulate eq true && dcrDocsManuallyUpload ne true && dcrDocsAutoPopulateAndManuallyUpload ne true}">
                <div class="col-sm-6 add-margin autoPopulateDcrDocs">
                    <c:choose>
                        <c:when test="${fn:length(dcrDocument.getOrderedDcrAttachments()) gt 0}">
                            <div class="files-viewer ${checklistName}">
                                <c:forEach items="${dcrDocument.getOrderedDcrAttachments()}" var="file"
                                           varStatus="status1">
                                    <div class="file-viewer" data-toggle="tooltip"
                                         data-placement="top" title="${file.fileStoreMapper.fileName}">
                                        <a class="download" target="_blank"
                                           href="/bpa/application/downloadfile/${file.fileStoreMapper.fileStoreId}"></a>
                                        <i class="fa fa-file-pdf-o" aria-hidden="true"></i>
                                        <span class="doc-numbering">${status1.index+1}</span>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="files-viewer ${checklistName}">
                                <form:hidden path="dcrDocuments[${dcrDocStatus.index}].fileStoreIds" id="fileStoreIds"
                                             cssClass="${checklistName}_fileStoreIds"></form:hidden>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>

            <c:if test="${dcrDocsManuallyUpload eq true || dcrDocsAutoPopulateAndManuallyUpload eq true || (dcrDocsAutoPopulate eq false && dcrDocsManuallyUpload eq false && dcrDocsAutoPopulateAndManuallyUpload eq false)}">
                <div class="col-sm-6 add-margin">
                    <div class="files-upload-container <c:if test="${dcrDocument.checklistDtl.isMandatory eq true && fn:length(dcrDocument.getOrderedDcrAttachments()) eq 0}">mandatory-dcr-doc</c:if>"
                         data-file-max-size="20"
                         <c:if test="${dcrDocument.checklistDtl.isMandatory eq true && fn:length(dcrDocument.getOrderedDcrAttachments()) eq 0}">required</c:if>
                         data-allowed-extenstion="pdf">
                        <div class="files-viewer ${checklistName}">
                            <c:if test="${fn:length(dcrDocument.getOrderedDcrAttachments()) gt 0}">
                                <c:forEach items="${dcrDocument.getOrderedDcrAttachments()}" var="file"
                                           varStatus="status1">
                                    <div class="file-viewer" data-toggle="tooltip"
                                         data-placement="top" title="${file.fileStoreMapper.fileName}">
                                        <a class="download" target="_blank"
                                           href="/bpa/application/downloadfile/${file.fileStoreMapper.fileStoreId}"></a>
                                        <i class="fa fa-file-pdf-o" aria-hidden="true"></i>
                                        <span class="doc-numbering">${status1.index+1}</span>
                                    </div>
                                </c:forEach>
                            </c:if>
                            <form:hidden path="dcrDocuments[${dcrDocStatus.index}].fileStoreIds"
                                         cssClass="${checklistName}_fileStoreIds dcrFileStoreIds"></form:hidden>
                            <a href="javascript:void(0);" class="file-add"
                               data-unlimited-files="true"
                               data-file-input-name="dcrDocuments[${dcrDocStatus.index}].files">
                                <i class="fa fa-plus"></i>
                            </a>

                        </div>
                    </div>
                </div>
            </c:if>
        </div>
    </c:forEach>
</div>

<!-- The Modal -->
<div id="imgModel" class="image-modal">
    <span class="closebtn">&times;</span> <img class="modal-content"
                                               id="previewImg">
    <div id="caption"></div>
</div>