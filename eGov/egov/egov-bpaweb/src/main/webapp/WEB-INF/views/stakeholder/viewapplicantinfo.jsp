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

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<div class="panel-body">

	<div class="row add-border">
		<div class="col-sm-3 control-div add-margin">
			<spring:message code="lbl.behalf.org" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${stakeHolder.isOnbehalfOfOrganization  ? 'YES' : 'NO'}" default="N/A"></c:out>
		</div>
	</div>
	<c:if test="${stakeHolder.isOnbehalfOfOrganization}">
		<div class="row add-border">
			<div class="col-sm-3 control-div add-margin">
				<spring:message code="lbl.nameof.org" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out value="${stakeHolder.organizationName}" default="N/A"></c:out>
			</div>
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.contactNo" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out value="${stakeHolder.organizationMobNo}" default="N/A"></c:out>
			</div>
		</div>

		<div class="row add-border">
			<div class="col-sm-3 control-div add-margin">
				<spring:message code="lbl.addressof.org" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out value="${stakeHolder.organizationAddress}" default="N/A"></c:out>
			</div>
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.contact.person" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out value="${stakeHolder.contactPerson}" default="N/A"></c:out>
			</div>
		</div>
		<div class="row add-border">
			<div class="col-sm-3 control-div add-margin">
				<spring:message code="lbl.designation" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out value="${stakeHolder.designation}" default="N/A"></c:out>
			</div>
		</div>
	</c:if>
	
	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.applicant.name" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${stakeHolder.name}"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.stakeholder.ackno" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${stakeHolder.code}" default="N/A"></c:out>
		</div>
	</div>
	
	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.stakeholder.type" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${stakeHolder.stakeHolderType}"></c:out>
		</div>
		<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.isActive" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${stakeHolder.isActive ? 'YES' : 'NO'}" default="N/A"></c:out>
		</div>
	</div>
	</div>
	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.application.date" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<fmt:formatDate value="${stakeHolder.createdDate}" pattern="dd/MM/yyyy" var="createdDate" />
			<c:out value="${createdDate}" default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.status" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${stakeHolder.status.stakeHolderStatusVal}" default="N/A"></c:out>
		</div>
	</div>

	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.dob" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<fmt:formatDate value="${stakeHolder.dob}" pattern="dd/MM/yyyy" var="dob" />
			<c:out value="${dob}" default="N/A"></c:out>
		</div>

		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.gender" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${stakeHolder.gender}" default="N/A"></c:out>
		</div>
	</div>
	
	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.mobileNo" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${stakeHolder.showMobileNumber()}" default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.emailid" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${stakeHolder.emailId}" default="N/A"></c:out>
		</div>
	</div>
	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.lic.no" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${stakeHolder.licenceNumber}" default="N/A"></c:out>
		</div>
	</div>
	
	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.buil.lic.iss.date" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<fmt:formatDate value="${stakeHolder.buildingLicenceIssueDate}" pattern="dd/MM/yyyy" var="buildingLicenceIssueDate" />
			<c:out value="${buildingLicenceIssueDate}" default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.build.lic.exp.date" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<fmt:formatDate value="${stakeHolder.buildingLicenceExpiryDate}" pattern="dd/MM/yyyy" var="buildingLicenceExpiryDate" />
			<c:out value="${buildingLicenceExpiryDate}" default="N/A"></c:out>
		</div>
	</div>

	<%-- <div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.coa.enrol.no" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${stakeHolder.coaEnrolmentNumber}"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.coa.renew.date" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${stakeHolder.coaEnrolmentDueDate}"></c:out>
		</div>
	</div> 
	<div class="row add-border">
		<div class="col-sm-3 control-div add-margin">
			<spring:message code="lbl.enrol.with.local.body" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${stakeHolder.isEnrolWithLocalBody}" default="NA"></c:out>
		</div>
		<div class="col-sm-3 control-div add-margin">
			<spring:message code="lbl.tin.no" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${stakeHolder.tinNumber}" default="NA"></c:out>
		</div>
	</div> --%>

	<div class="row add-border">
		<div class="col-sm-3 control-div add-margin">
			<spring:message code="lbl.aadhar" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${stakeHolder.showAadhaarNumber()}" default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.pan" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${stakeHolder.showPanNumber()}" default="N/A"></c:out>
		</div>
	</div>
	<div class="row add-border">
		<div class="col-sm-3 control-div add-margin">
			<spring:message code="lbl.if.addresssame" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${stakeHolder.isAddressSame ? 'YES' : 'NO'}"
				default="N/A"></c:out>
		</div>
	</div>
</div>


<c:forEach items="${stakeHolder.address}" var="address">
	<div class="panel-heading custom_form_panel_heading">
		<div class="panel-title">
			<c:if test="${address.type eq 'CORRESPONDENCE'}">
				<spring:message code="lbl.comm.address" />
			</c:if>
			<c:if test="${address.type eq 'PERMANENT'}">
				<spring:message code="lbl.permt.address" />
			</c:if>
		</div>
	</div>

	<div class="panel-body">
		<div class="row add-border">
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.addr.dno" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out value="${address.houseNoBldgApt}" default="N/A"></c:out>
			</div>

			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.addr.Steet.name" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out value="${address.streetRoadLine}" default="N/A"></c:out>
			</div>
		</div>
		<div class="row add-border">
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.locality" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out value="${address.areaLocalitySector}" default="N/A"></c:out>
			</div>

			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.city" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out value="${address.cityTownVillage}" default="N/A"></c:out>
			</div>
		</div>
		<div class="row add-border">
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.district" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out value="${address.district}" default="N/A"></c:out>
			</div>

			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.state" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out value="${address.state}" default="N/A"></c:out>
			</div>
		</div>
		<div class="row add-border">
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.post.office" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out value="${address.postOffice}" default="N/A"></c:out>
			</div>
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.pincode" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out value="${address.pinCode}" default="N/A"></c:out>
			</div>
		</div>
	</div>
	
	</c:forEach>
<div class="panel-body">
	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.comments" />
		</div>
		<div class="col-sm-9 add-margin view-content">
			<c:out value="${stakeHolder.comments eq '' ? 'N/A' : stakeHolder.comments}" default="N/A"></c:out>
		</div>
	</div>
</div>

	
	

