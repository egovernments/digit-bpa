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
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="panel-heading toggle-header custom_form_panel_heading">
	<div class="panel-title">
		<spring:message code="lbl.site.details" />
	</div>
	<div class="history-icon toggle-icon">
		<i class="fa fa-angle-up fa-2x"></i>
	</div>
</div>
<div class="panel-body display-hide">
	
	<jsp:include page="view-amenities-details.jsp"></jsp:include>
	
	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.zonal.office" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out
				value="${bpaApplication.siteDetail[0].adminBoundary.parent.name}"
				default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.rev.ward" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].adminBoundary.name}"
				default="N/A"></c:out>
		</div>
	</div>

	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.locality" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].locationBoundary.name}"
				default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.election.ward" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].electionBoundary.name}"
				default="N/A"></c:out>
		</div>
	</div>

	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.re.survey.no" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].reSurveyNumber}"
				default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.nature.of.ownership" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].natureofOwnership}"
				default="N/A"></c:out>
		</div>
	</div>

	<div class="row add-border">
		<%-- <div class="col-sm-3 add-margin">
			<spring:message code="lbl.subdiv.no" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].subdivisionNumber}"
				default="N/A"></c:out>
		</div> --%>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.registrar.office" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].registrarOffice.registrarOffice.name}"
				default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.nearest.build.no" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].nearestbuildingnumber}"
				default="N/A"></c:out>
		</div>
	</div>
	<div class="doorNo">
		<div class="row add-border">
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.addr.dno" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out value="${bpaApplication.siteDetail[0].plotdoornumber}"
					default="N/A"></c:out>
			</div>
		</div>
	</div>

	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.street.address1" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].streetaddress1}"
				default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.street.address2" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].streetaddress2}"
				default="N/A"></c:out>
		</div>
	</div>

	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.city" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].citytown}" default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.site.pincode" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].postalAddress.pincode}"
				default="N/A"></c:out>
		</div>
	</div>
	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.taluk" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].postalAddress.taluk}" default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.post.office" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out
				value="${bpaApplication.siteDetail[0].postalAddress.postOffice}"
				default="N/A"></c:out>
		</div>
	</div>

	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.district" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].postalAddress.district}"
				default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.state" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].postalAddress.state}" default="N/A"></c:out>
		</div>
	</div>

	<%-- <div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.approved.layout.details" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].approvedLayoutDetail}"
				default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.crz.zone" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.buildingDetail[0].crzZone}"
				default="N/A"></c:out>
		</div>
	</div> --%>
	
	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.town.plan.zone" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].scheme.description}"
				default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.proposed.land.use" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].landUsage.description}"
				default="N/A"></c:out>
		</div>
	</div>
	
	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.government.type" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<input type="hidden" id="governmentType" value="${bpaApplication.governmentType}">
			<c:out value="${bpaApplication.governmentType.governmentTypeVal}" default="N/A"></c:out>
		</div>
		<div id="isEconomicallyWeakerSec">
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.is.econ.weaker.sec" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out value="${bpaApplication.isEconomicallyWeakerSection ? 'YES' : 'NO'}" default="N/A"></c:out>
			</div>
		</div>
	</div>

	<%--<c:if test="${bpaApplication.siteDetail[0].isappForRegularization}">
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
				<c:when test="${bpaApplication.siteDetail[0].constStages.description eq 'In Progress'}">
					<div class="col-sm-3 add-margin">
						<spring:message code="lbl.work.commence.date" />
					</div>
					<div class="col-sm-3 add-margin view-content">
						<fmt:formatDate value="${bpaApplication.siteDetail[0].workCommencementDate}" pattern="dd/MM/yyyy" var="workCommencementDate" />
						<c:out value="${workCommencementDate}" default="N/A"></c:out>
					</div>
				</c:when>
				<c:otherwise>
					<div class="col-sm-3 add-margin">
						<spring:message code="lbl.work.commence.date" />
					</div>
					<div class="col-sm-3 add-margin view-content">
						<fmt:formatDate value="${bpaApplication.siteDetail[0].workCommencementDate}" pattern="dd/MM/yyyy" var="workCommencementDate1" />
						<c:out value="${workCommencementDate1}" default="N/A"></c:out>
					</div>
					<div class="col-sm-3 add-margin">
						<spring:message code="lbl.work.completion.date" />
					</div>
					<div class="col-sm-3 add-margin view-content">
						<fmt:formatDate value="${bpaApplication.siteDetail[0].workCompletionDate}" pattern="dd/MM/yyyy" var="workCompletionDate" />
						<c:out value="${workCompletionDate}" default="N/A"></c:out>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</c:if>--%>

	<%-- <div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.set.back.front" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].setBackFront}"
				default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.set.back.rear" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].setBackRear}"
				default="N/A"></c:out>
		</div>
	</div>

	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.set.back.side1" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].setBackSide1}"
				default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.set.back.side2" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].setBackSide2}"
				default="N/A"></c:out>
		</div>
	</div> --%>

	<%-- <div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.encroch.issue.present" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out
				value="${bpaApplication.siteDetail[0].encroachmentIssuesPresent ? 'Yes' : 'No'}"
				default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.encroach.remarks" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].encroachmentRemarks}"
				default="N/A"></c:out>
		</div>
	</div> --%>
</div>