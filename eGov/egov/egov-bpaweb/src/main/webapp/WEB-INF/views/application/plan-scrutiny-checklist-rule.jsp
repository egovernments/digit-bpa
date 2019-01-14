<%--
  ~ eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
  ~ accountability and the service delivery of the government  organizations.
  ~
  ~  Copyright (C) <2017>  eGovernments Foundation
  ~
  ~  The updated version of eGov suite of products as by eGovernments Foundation
  ~  is available at http://www.egovernments.org
  ~
  ~  This program is free software: you can redistribute it and/or modify
  ~  it under the terms of the GNU General Public License as published by
  ~  the Free Software Foundation, either version 3 of the License, or
  ~  any later version.
  ~
  ~  This program is distributed in the hope that it will be useful,
  ~  but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~  GNU General Public License for more details.
  ~
  ~  You should have received a copy of the GNU General Public License
  ~  along with this program. If not, see http://www.gnu.org/licenses/ or
  ~  http://www.gnu.org/licenses/gpl.html .
  ~
  ~  In addition to the terms of the GPL license to be adhered to in using this
  ~  program, the following additional terms are to be complied with:
  ~
  ~      1) All versions of this program, verbatim or modified must carry this
  ~         Legal Notice.
  ~ 	Further, all user interfaces, including but not limited to citizen facing interfaces,
  ~         Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
  ~         derived works should carry eGovernments Foundation logo on the top right corner.
  ~
  ~ 	For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
  ~ 	For any further queries on attribution, including queries on brand guidelines,
  ~         please contact contact@egovernments.org
  ~
  ~      2) Any misrepresentation of the origin of the material is prohibited. It
  ~         is required that all modified versions of this material be marked in
  ~         reasonable ways as different from the original version.
  ~
  ~      3) This license does not grant any rights to any user of the program
  ~         with regards to rights under trademark law for use of the trade names
  ~         or trademarks of eGovernments Foundation.
  ~
  ~  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
  --%>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="panel-heading custom_form_panel_heading">
    <div class="panel-title">
        <spring:message code="lbl.pln.scrty.rule"/>
    </div>
</div>
<div class="panel-body">
    <table class="table table-bordered  multiheadertbl"
           id="planScrutinyDetails">
        <thead>
        <tr>
            <th width="2%"><spring:message code="lbl.srl.no"/></th>
            <th width="50%"><spring:message code="lbl.description"/></th>
            <th width="13%"><spring:message code="lbl.isrequired"/></th>
            <th><spring:message code="lbl.remarks"/></th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when
                    test="${not empty inspection.planScrutinyChecklistTemp}">
                <c:forEach var="planScrutiny"
                           items="${inspection.planScrutinyChecklistTemp}"
                           varStatus="planScrutinyStatus">
                    <tr>
                        <td><form:hidden
                                path="planScrutinyChecklistTemp[${planScrutinyStatus.index}].inspection"
                                value="${inspection.id}"/> <form:hidden
                                path="planScrutinyChecklistTemp[${planScrutinyStatus.index}].orderNumber"
                                value="${planScrutinyStatus.index+1}"/> <form:hidden
                                path="planScrutinyChecklistTemp[${planScrutinyStatus.index}].checklistDetail"
                                value="${planScrutiny.checklistDetail.id}"/><form:hidden
                                path="planScrutinyChecklistTemp[${planScrutinyStatus.index}].scrutinyChecklistType"
                                value="RULE_VALIDATION"/> <c:out
                                value="${planScrutinyStatus.index+1}"></c:out></td>
                        <td class="view-content" style="font-size: 100%;"><c:out
                                value="${planScrutiny.checklistDetail.description}"></c:out></td>
                        <td class="view-content">
                            <c:forEach items="${planScrutinyValues}" var="scrutinyVal">
                                <div class="radio">
                                    <label><input type="radio" value="${scrutinyVal}" class="scrutinyValue"
                                                  name="planScrutinyChecklistTemp[${planScrutinyStatus.index}].scrutinyValue"
                                            <c:if test="${scrutinyVal eq planScrutiny.scrutinyValue}"> checked="checked" </c:if> />${scrutinyVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </td>
                        <td><form:textarea
                                path="planScrutinyChecklistTemp[${planScrutinyStatus.index}].remarks"
                                class="form-control remarks" rows="3" maxlength="1024"/></td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <c:forEach var="planScrutiny"
                           items="${planScrutinyCheckList}" varStatus="planScrutinyStatus">
                    <tr>
                        <td><form:hidden
                                path="planScrutinyChecklistTemp[${planScrutinyStatus.index}].inspection"/>
                            <form:hidden
                                    path="planScrutinyChecklistTemp[${planScrutinyStatus.index}].orderNumber"
                                    value="${planScrutinyStatus.index+1}"/><form:hidden
                                    path="planScrutinyChecklistTemp[${planScrutinyStatus.index}].checklistDetail"
                                    value="${planScrutiny.id}"/><form:hidden
                                    path="planScrutinyChecklistTemp[${planScrutinyStatus.index}].scrutinyChecklistType"
                                    value="RULE_VALIDATION"/> <c:out
                                    value="${planScrutinyStatus.index+1}"></c:out></td>
                        <td class="view-content" style="font-size: 100%;"><c:out
                                value="${planScrutiny.description}"></c:out>
                        <td class="view-content">
                            <c:forEach items="${planScrutinyValues}" var="scrutinyVal">
                                <div class="radio">
                                    <label><input type="radio" value="${scrutinyVal}" class="scrutinyValue"
                                                  name="planScrutinyChecklistTemp[${planScrutinyStatus.index}].scrutinyValue"
                                            <c:if test="${scrutinyVal eq 'NOT_APPLICABLE'}"> checked="checked" </c:if> />${scrutinyVal.checkListVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </td>
                        <td><form:textarea
                                path="planScrutinyChecklistTemp[${planScrutinyStatus.index}].remarks"
                                class="form-control remarks" rows="3" maxlength="1024"/></td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</div>