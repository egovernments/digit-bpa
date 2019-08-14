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

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="panel-heading custom_form_panel_heading">
	<div class="panel-title">
		<spring:message code="lbl.basic.info" />
	</div>
</div>
<div class="panel-body">

        <div class="row add-border">
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.application.no" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out value="${ownershipTransfer.applicationNumber}" default="N/A"></c:out>
			</div>
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.application.date" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<fmt:formatDate value="${ownershipTransfer.applicationDate}"
					pattern="dd/MM/yyyy" var="applicationDate" />
				<c:out value="${applicationDate}" default="N/A"></c:out>
			</div>
	   </div>
	   
	   	<div class="row add-border">
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.service.type" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<input type="hidden" id="serviceType"
					value="${ownershipTransfer.parent.serviceType.description}">
				<c:out value="${ownershipTransfer.parent.serviceType.description}"
					default="N/A"></c:out>
			</div>
			<div class="col-sm-3 add-margin">
			<spring:message code="lbl.occupancy" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out
				value="${ownershipTransfer.parent.occupanciesName ne '' ?  ownershipTransfer.parent.occupanciesName : 'N/A'}"></c:out>
		</div>
			
		</div>
	
	  <div class="row add-border">
		<div class="col-sm-3 add-margin">
				<spring:message code="lbl.applicant.name" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out value="${applicants}"
					default="N/A"></c:out>
			</div>
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.owner.address" />
			</div>
			<div class="col-sm-3 add-margin view-content">				
				<c:out value="${applicantAddress}"
					default="N/A"></c:out>
			</div>
		
	   </div>
	   <c:if
		test="${ownershipTransfer.ownershipNumber ne null && ownershipTransfer.ownershipNumber ne ''}">

	 <div class="row add-border">
	 
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.application.no" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out value="${ownershipTransfer.ownershipNumber}" default="N/A"></c:out>
			</div>
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.application.date" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<fmt:formatDate value="${ownershipTransfer.ownershipApprovalDate}"
					pattern="dd/MM/yyyy" var="applicationDate" />
				<c:out value="${applicationDate}" default="N/A"></c:out>
			</div>
	  </div></c:if>
	
	<div class="row add-border">
	<div class="col-sm-3 add-margin">
				<spring:message code="lbl.admission.fees" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out value="${ownershipTransfer.admissionfeeAmount}" default="N/A"></c:out>
			</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.remarks" />
		</div>
		<div class="col-sm-3 add-margin view-content text-justify">
			<c:out value="${ownershipTransfer.remarks}" default="N/A"></c:out>
		</div>
	</div>
	<div class="row add-border">
			<c:choose>
			<c:when test="${ownershipNumber ne null && ownershipNumber ne '' }">
            <div class="col-sm-3 add-margin">
				<spring:message code="lbl.ownership.number" />
			</div>
			<div class="col-sm-3 add-margin">
				<a target="popup" onclick="window.open('/bpa/application/ownership/transfer/view/${ownershipNumber}','popup','width=1100,height=700'); return false;"
				>${ownershipNumber}</a>	</div>			
			</div>
			</c:when>
			<c:otherwise>
            <div class="col-sm-3 add-margin">
				<spring:message code="lbl.plan.permission.no" />
			</div>
			<div class="col-sm-3 add-margin">
				<a target="popup" onclick="window.open('/bpa/application/details-view/by-permit-number/${ownershipTransfer.parent.planPermissionNumber}','popup','width=1100,height=700'); return false;"
				>${ownershipTransfer.parent.planPermissionNumber}</a>	
			</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>

