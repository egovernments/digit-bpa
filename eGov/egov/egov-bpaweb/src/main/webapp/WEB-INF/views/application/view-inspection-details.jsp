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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<br>
<div align="center" class="overflow-x-scroll">
	<input type="hidden" name="applicationNumber" id="applicationNumber"
		value="${applicationNumber}">
	<table class="table table-bordered  multiheadertbl" id="inspedetails">
		<thead>
			<tr>
				<th><spring:message code="lbl.slno" /></th>
				<th><spring:message code="lbl.inspn.no"/></th>
				<th><spring:message code="lbl.inspn.date"/></th>
				<th><spring:message code="lbl.action"/></th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty inspectionList}">
					<c:forEach items="${inspectionList}" var="inspn" varStatus="status">
						<tr id="lprow">
							<td align="center" class="view-content" style="font-size: 90%;">${status.index+1}</td>
							<td align="center" class="view-content" style="font-size: 90%;"> <c:out value="${inspn.inspectionNumber}" /></td>
							<td align="center" class="view-content" style="font-size: 90%;">
								<fmt:formatDate value="${inspn.inspectionDate}" pattern="dd/MM/yyyy" var="inspectionDate"/>
								<c:out value="${inspectionDate}" /></td>
							<td align="center"  style="font-size: 90%;"><a class="view-content"
									style="cursor: pointer; font-size: 15px;" onclick="window.open('/bpa/application/showinspectiondetails/${inspn.id}','view','width=600, height=400,scrollbars=yes')">
								<i class="fa fa-eye" aria-hidden="true"> <spring:message
										code="lbl.view"/></i>
							</a></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<div class="col-md-12 col-xs-6  panel-title">No Inspection Details
						Found</div>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
</div>
<script>
	$(document).ready(function() {
		$("#inspedetails tbody tr:gt(1)").each(function(index) {
			$(this).find('a').hide();
		});
	});
</script>