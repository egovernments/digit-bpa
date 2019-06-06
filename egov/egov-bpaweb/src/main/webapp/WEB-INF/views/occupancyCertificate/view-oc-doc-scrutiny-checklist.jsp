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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="row add-border">
	<div class="col-sm-5 add-margin view-content bg-info">
		<div class="text-info">
			<spring:message code="lbl.description" />
		</div>
	</div>
	<div class="col-sm-2 add-margin view-content bg-info">
		<div class="text-info">
			<spring:message code="lbl.is.applicable" />
		</div>
	</div>
	<div class="col-sm-5 add-margin view-content bg-info">
		<div class="text-info">
			<spring:message code="lbl.remarks" />
		</div>
	</div>
</div>
<c:forEach
	items="${occupancyCertificate.documentScrutinies[0].docScrutiny.documentScrutinyChecklists}"
	var="doc" varStatus="counter">
	<div class="row add-border">
		<div class="col-sm-5 add-margin view-content">
			<c:out value="${doc.serviceChecklist.checklist.description}"
				default="N/A"></c:out>
		</div>
		<div class="col-sm-2 add-margin view-content">
			<c:out value="${doc.scrutinyValue.checkListVal}" default="N/A"></c:out>
		</div>
		<div class="col-sm-5 add-margin view-content text-justify">
			<c:out value="${doc.remarks}" default="N/A"></c:out>
		</div>
	</div>
</c:forEach>
