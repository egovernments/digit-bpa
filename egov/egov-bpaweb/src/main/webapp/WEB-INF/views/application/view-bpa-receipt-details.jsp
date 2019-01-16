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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<%-- <div class="panel-heading custom_form_panel_heading">
	<div class="panel-title">
		<spring:message code="lbl.recpt.details" />
	</div>
</div> --%>


<div class="panel-heading toggle-header">
    <div class="panel-title">
        <spring:message code="lbl.recpt.details"/>
    </div>
    <div class="history-icon toggle-icon">
        <i class="fa fa-angle-up fa-2x"></i>
    </div>
</div>
<div class="panel-body display-hide">
    <table class="table table-striped table-bordered" id="bpaReceiptDetail" style="width:50%;margin:0 auto;">
        <thead>
        <tr>
            <th class="text-center"><spring:message code="lbl.slno"/></th>
            <th><spring:message code="lbl.rcpt.no"/></th>
            <th><spring:message code="lbl.rcpt.date"/></th>
            <th class="text-right"><spring:message code="lbl.rcpt.amt"/></th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${not empty  bpaApplication.receipts}">
                <c:set var="totalAmount" value="${0}"/>
                <c:forEach var="receipt" items="${bpaApplication.receipts}"
                           varStatus="counter">
                    <tr>
                        <td class="text-center view-content"><c:out value="${counter.index+1}"/></td>
                        <td class="view-content"><a class="open-popup"
                                                    href="/collection/citizen/onlineReceipt-viewReceipt.action?receiptNumber=${receipt.receiptNumber}&consumerCode=${bpaApplication.applicationNumber}&serviceCode=BPA">${receipt.receiptNumber}</a>
                        </td>
                        <td class="view-content">
                            <fmt:formatDate value="${receipt.receiptDate}" pattern="dd/MM/yyyy" var="receiptDate"/>
                            <c:out value="${receiptDate}" default="N/A"></c:out></td>
                        <td class="text-right view-content"><c:set var="totalAmount"
                                                                   value="${totalAmount + receipt.receiptAmt}"/><fmt:formatNumber
                                type="number" maxFractionDigits="2" value="${receipt.receiptAmt}"/></td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="col-md-12 col-xs-6  panel-title">No Receipts
                    found
                </div>
            </c:otherwise>
        </c:choose>
        </tbody>
        <tfoot>
        <tr>
            <td></td>
            <td></td>
            <td class="text-right view-content">Total Amount</td>
            <td class="text-right view-content"><fmt:formatNumber type="number" maxFractionDigits="2"
                                                                  value="${totalAmount}"/></td>
        </tr>
        </tfoot>
    </table>
</div>
