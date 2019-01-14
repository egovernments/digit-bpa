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
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>

<div class="panel-heading custom_form_panel_heading">
	<div class="panel-title">
		<spring:message code="lbl.encloseddocuments" />
	</div>
</div>
<div class="panel-body">
	<table class="table table-bordered  multiheadertbl">
		<thead>
			<tr>
				<th><spring:message code="lbl.srl.no" /></th>
				<th><spring:message code="lbl.documentname" /></th>
				<%--<th><spring:message code="lbl.issubmitted" /></th>--%>
				<th><spring:message code="lbl.remarks" /></th>
				<th><spring:message code="lbl.files" /></th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty  bpaApplication.applicationDocument}">
					<c:forEach items="${bpaApplication.applicationDocument}" var="docs"
						varStatus="status">
						<tr>
							<td class="view-content text-center" style="font-size: 97%;"><c:out value="${status.index+1}" /></td>
							<td class="view-content text-justify" style="font-size: 97%;"><c:out value="${docs.checklistDetail.description}"
									default="N/A" /></td>
							<%--<td><c:out value="${docs.issubmitted ? 'Yes' : 'No'}"
									default="N/A"></c:out></td>--%>
							<td class="view-content text-justify" style="font-size: 97%;"><c:out value="${docs.remarks}" default="N/A" /></td>
							<td class="view-content" style="font-size: 97%;"><c:set value="false" var="isDocFound"></c:set> <c:forEach
									var="bpadoc" items="${docs.getOrderedSupportDocs()}" varStatus="loop">
									<c:if test="${bpadoc.fileStoreId ne null}">
										<c:set value="true" var="isDocFound"></c:set>
										<a target="_blank" href="/bpa/application/downloadfile/${bpadoc.fileStoreId}"
											data-gallery>${loop.index +1} - ${bpadoc.fileName} </a>
										<c:if test="${!loop.last}">,</c:if>&nbsp;
									</c:if>
								</c:forEach> <c:if test="${!isDocFound}">
								N/A
							</c:if></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<div class="col-md-12 col-xs-6  panel-title">No documents
						found</div>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
</div>
