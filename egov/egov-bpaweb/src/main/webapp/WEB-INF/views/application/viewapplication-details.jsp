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
	<c:if
		test="${bpaApplication.planPermissionNumber ne null && bpaApplication.planPermissionNumber ne ''}">
		<div class="row add-border">
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.build.plan.permission.date" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<fmt:formatDate value="${bpaApplication.planPermissionDate}"
					pattern="dd/MM/yyyy" var="planPermissionDate" />
				<c:out value="${planPermissionDate}" default="N/A"></c:out>
			</div>
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.plan.permission.no" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out value="${bpaApplication.planPermissionNumber}" default="N/A"></c:out>
			</div>
		</div>
	</c:if>
	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.occupancy" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out
				value="${bpaApplication.occupanciesName ne '' ?  bpaApplication.occupanciesName : 'N/A'}"></c:out>
		</div>
		<c:if
			test="${bpaApplication.eDcrNumber ne null && bpaApplication.eDcrNumber ne ''}">
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.edcr.number" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<input type="hidden" id="eDcrNumber"
					value="${bpaApplication.eDcrNumber}">
				<c:out value="${bpaApplication.eDcrNumber}" default="N/A"></c:out>
			</div>
		</c:if>
	</div>

	<c:if test="${bpaApplication.isOneDayPermitApplication}">
		<div class="row add-border">
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.applctn.type" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<input type="hidden" id="isOneDayPermitApplication"
					value="${bpaApplication.applicationType.name}">
				<c:out
					value="${bpaApplication.applicationType.name}"
					default="N/A"></c:out>
			</div>

			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.type.land" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out value="${bpaApplication.typeOfLand}" default="N/A"></c:out>
			</div>
		</div>
	</c:if>
	<c:if test="${bpaApplication.siteDetail[0].isappForRegularization}">
		<div class="row add-border">
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.if.regularized" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out
					value="${bpaApplication.siteDetail[0].isappForRegularization ? 'YES' : 'NO'}"></c:out>
			</div>
		</div>
		<div class="row add-border">
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.cons.stages" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out
					value="${bpaApplication.siteDetail[0].constStages.description}"
					default="N/A"></c:out>
			</div>
			<c:if
				test="${bpaApplication.siteDetail[0].constStages.description eq 'In Progress'}">
				<div class="col-sm-3 add-margin">
					<spring:message code="lbl.if.cons.not.cmplted" />
				</div>
				<div class="col-sm-3 add-margin view-content">
					<c:out value="${bpaApplication.siteDetail[0].stateOfConstruction}"
						default="N/A"></c:out>
				</div>
			</c:if>
		</div>
		<div class="row add-border">
			<c:choose>
				<c:when
					test="${bpaApplication.siteDetail[0].constStages.description eq 'In Progress'}">
					<div class="col-sm-3 add-margin">
						<spring:message code="lbl.work.commence.date" />
					</div>
					<div class="col-sm-3 add-margin view-content">
						<fmt:formatDate
							value="${bpaApplication.siteDetail[0].workCommencementDate}"
							pattern="dd/MM/yyyy" var="workCommencementDate" />
						<c:out value="${workCommencementDate}" default="N/A"></c:out>
					</div>
				</c:when>
				<c:otherwise>
					<div class="col-sm-3 add-margin">
						<spring:message code="lbl.work.commence.date" />
					</div>
					<div class="col-sm-3 add-margin view-content">
						<fmt:formatDate
							value="${bpaApplication.siteDetail[0].workCommencementDate}"
							pattern="dd/MM/yyyy" var="workCommencementDate1" />
						<c:out value="${workCommencementDate1}" default="N/A"></c:out>
					</div>
					<div class="col-sm-3 add-margin">
						<spring:message code="lbl.work.completion.date" />
					</div>
					<div class="col-sm-3 add-margin view-content">
						<fmt:formatDate
							value="${bpaApplication.siteDetail[0].workCompletionDate}"
							pattern="dd/MM/yyyy" var="workCompletionDate" />
						<c:out value="${workCompletionDate}" default="N/A"></c:out>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</c:if>
	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.service.type" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<input type="hidden" id="serviceType"
				value="${bpaApplication.serviceType.description}">
			<c:out value="${bpaApplication.serviceType.description}"
				default="N/A"></c:out>
		</div>
		<%-- <div class="amenityHideShow">
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.amenity.type" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out
					value="${bpaApplication.amenityName ne '' ?  bpaApplication.amenityName : 'N/A'}"></c:out>
			</div>
		</div> --%>
	</div>
	<c:if
		test="${ empty  bpaApplication.receipts && (bpaApplication.status.code eq 'Created' || bpaApplication.status.code eq 'Registered')}">
		<div class="row add-border">
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.admission.fees" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out value="${bpaApplication.admissionfeeAmount}" default="N/A"></c:out>
			</div>
		</div>
	</c:if>
	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.stakeholder.type" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out
				value="${bpaApplication.stakeHolder[0].stakeHolder.stakeHolderType.name}"
				default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.stakeholder.name" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.stakeHolder[0].stakeHolder.name}"
				default="N/A"></c:out>
		</div>
	</div>

	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.application.no" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.applicationNumber}" default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.application.date" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<fmt:formatDate value="${bpaApplication.applicationDate}"
				pattern="dd/MM/yyyy" var="applicationDate" />
			<c:out value="${applicationDate}" default="N/A"></c:out>
		</div>
	</div>

	
		
		<%--<div class="col-sm-3 add-margin">
			<spring:message code="lbl.project.type" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.projectName}" default="N/A"></c:out>
		</div>--%>
		<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.remarks" />
		</div>
		<div class="col-sm-3 add-margin view-content text-justify">
			<c:out value="${bpaApplication.remarks}" default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.application.type" />
		</div>
		<div class="col-sm-3 add-margin view-content text-justify">
			<c:out value="${bpaApplication.applicationType.name}" default="N/A"></c:out>
		</div>
	</div>
</div>

