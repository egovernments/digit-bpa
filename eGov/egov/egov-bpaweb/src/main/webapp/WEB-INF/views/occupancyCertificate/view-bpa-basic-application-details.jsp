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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>

<div class="panel-heading custom_form_panel_heading">
	<div class="panel-title"><spring:message code="lbl.bpa.appln.dtls"/></div>
</div>

<div class="panel-body">
	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.plan.permission.no"/>
		</div>
		<div class="col-sm-3 add-margin view-content">
			<a target="popup" onclick="window.open('/bpa/application/details-view/by-permit-number/${occupancyCertificate.parent.planPermissionNumber}','popup','width=1100,height=700'); return false;"
			>${occupancyCertificate.parent.planPermissionNumber}</a>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.edcr.number"/>
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${occupancyCertificate.parent.eDcrNumber}" default="N/A"></c:out>
		</div>
	</div>
	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.applicant.name"/>
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${occupancyCertificate.parent.owner.name}" default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.owner.address"/>
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${occupancyCertificate.parent.owner.address}"></c:out>
		</div>
	</div>
	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.stakeholder.type"/>
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${occupancyCertificate.parent.stakeHolder[0].stakeHolder.stakeHolderType}" default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.stakeholder.name"/>
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${occupancyCertificate.parent.stakeHolder[0].stakeHolder.name}"></c:out>
		</div>
	</div>
	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.build.plan.permission.date"/>
		</div>
		<div class="col-sm-3 add-margin view-content">
            <fmt:formatDate value="${occupancyCertificate.parent.planPermissionDate}" pattern="dd/MM/yyyy" var="planPermissionDate" />
			<c:out value="${planPermissionDate}" default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.is.one.permit"/>
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${occupancyCertificate.parent.isOneDayPermitApplication ?  'YES' : 'NO'}"></c:out>
		</div>
	</div>
	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.occupancy"/>
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${occupancyCertificate.parent.occupancy.description}" default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.service.type"/>
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${occupancyCertificate.parent.serviceType.description}" default="N/A"></c:out>
		</div>
	</div>

	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.zonal.office"/>
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${occupancyCertificate.parent.siteDetail[0].adminBoundary.parent.name}" default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.rev.ward"/>
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${occupancyCertificate.parent.siteDetail[0].adminBoundary.name}" default="N/A"></c:out>
		</div>
	</div>

	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.election.ward"/>
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${occupancyCertificate.parent.siteDetail[0].electionBoundary.name}" default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.re.survey.no"/>
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${occupancyCertificate.parent.siteDetail[0].reSurveyNumber}" default="N/A"></c:out>
		</div>
	</div>

	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.amenity.type"/>
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${occupancyCertificate.parent.amenityName ne '' ?  occupancyCertificate.parent.amenityName : 'N/A'}"></c:out>
		</div>
		<div class="col-sm-6 add-margin view-content">
			<a
					target="popup" class="btn btn-primary" onclick="window.open('/bpa/application/details-view/by-permit-number/${occupancyCertificate.parent.planPermissionNumber}','popup','width=1100,height=700'); return false;"
					class="btn btn-primary"><spring:message code="lbl.bpa.appln.dtls"/></a>
		</div>
	</div>
</div>

