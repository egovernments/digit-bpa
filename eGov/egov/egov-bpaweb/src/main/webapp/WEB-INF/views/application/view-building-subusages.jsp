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

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>

<div class="panel-heading custom_form_panel_heading">
    <div class="panel-title">
        <spring:message code="title.block.occupancy.usage"/>
    </div>
</div>
<div class="panel-body">
    <c:if test="${not empty bpaApplication.buildingSubUsages}">
        <table class="table table-bordered multiheadertbl"
               id="planScrutinyDetails">
            <thead>
            <tr>
                <th class="text-center" width="9%"><spring:message code="lbl.block.no"/></th>
                <th><spring:message code="lbl.occupancy"/></th>
                <th><spring:message code="lbl.sub.usage"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${bpaApplication.buildingSubUsages}" var="subUsage" varStatus="counter">
                <c:forEach items="${subUsage.subUsageDetails}" var="subUsageDtl" varStatus="counter">
                    <tr>
                        <td class="view-content text-center" style="font-size: 97%;"><c:out
                                value="${subUsage.blockNumber}" default="N/A"/></td>
                        <td class="view-content" style="font-size: 97%;"><c:out
                                value="${subUsageDtl.mainUsage.additionalDescription}"
                                default="N/A"/></td>
                        <td class="view-content" style="font-size: 97%;"><c:out value="${subUsageDtl.subUsageNames}"
                                                                                default="N/A"/></td>
                    </tr>
                </c:forEach>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>