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

<div class="row">
	<div class="col-md-12">
		<div class="panel panel-primary" data-collapsed="0">
			<c:choose>
				<c:when test="${message eq null || message eq ''}">
					<div class="panel-heading custom_form_panel_heading">
						<div class="panel-title">
							<spring:message code="lbl.appln.details" />
						</div>
					</div>
					<div class="panel-body">
						<div class="row add-border">
							<div class="col-sm-3 add-margin">
								<spring:message code="lbl.application.type" />
							</div>
							<div class="col-sm-3 add-margin view-content text-justify">
								<c:out value="${applicationType}" default="N/A"></c:out>
							</div>
							<div class="col-sm-3 add-margin">
								<spring:message code="lbl.applctn.subtype" />
							</div>
							<div class="col-sm-3 add-margin view-content text-justify">
								<c:out value="${applicationSubType}" default="N/A"></c:out>
							</div>
						</div>

						<div class="row add-border">
							<div class="col-sm-3 add-margin">
								<spring:message code="lbl.application.no" />
							</div>
							<div class="col-sm-3 add-margin view-content">
								<c:out value="${applicationNumber}" default="N/A"></c:out>
							</div>
							<c:if test="${applicationType eq 'Permit'}">
								<div class="col-sm-3 add-margin">
									<spring:message code="lbl.plan.permission.no" />
								</div>
							</c:if>
							<c:if test="${applicationType eq 'Occupancy Certificate'}">
								<div class="col-sm-3 add-margin">
									<spring:message code="lbl.oc.no" />
								</div>
							</c:if>
							<div class="col-sm-3 add-margin view-content">
								<c:out value="${approvalNumber}" default="N/A"></c:out>
							</div>
						</div>

						<div class="row add-border">
							<div class="col-sm-3 add-margin">
								<spring:message code="lbl.approval.date" />
							</div>
							<div class="col-sm-3 add-margin view-content">
								<fmt:formatDate value="${approvalDate}" pattern="dd/MM/yyyy"
									var="approvalDate" />
								<c:out value="${approvalDate}" default="N/A"></c:out>
							</div>
						</div>

						<div class="row add-border">
							<div class="col-sm-3 add-margin">
								<spring:message code="lbl.occupancy" />
							</div>
							<div class="col-sm-3 add-margin view-content">
								<c:out value="${occupancyType}" default="N/A"></c:out>
							</div>
							<div class="col-sm-2 add-margin">
								<spring:message code="lbl.service.type" />
							</div>
							<div class="col-sm-3 add-margin view-content">
								<c:out value="${serviceType}" default="N/A"></c:out>
							</div>
						</div>

						<div class="row add-border">
							<div class="col-sm-3 add-margin">
								<spring:message code="lbl.stakeholder.type" />
							</div>
							<div class="col-sm-3 add-margin view-content">
								<c:out value="${stakeholderType}" default="N/A"></c:out>
							</div>
							<div class="col-sm-3 add-margin">
								<spring:message code="lbl.stakeholder.name" />
							</div>
							<div class="col-sm-3 add-margin view-content">
								<c:out value="${stakeholderName}" default="N/A"></c:out>
							</div>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="panel-heading">
						<div class="panel-title text-center no-float">
							<c:if test="${not empty message}">
								<strong>${message}</strong>
							</c:if>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="row text-center">
			<div class="row">
				<div class="text-center">
					<button type="button" class="btn btn-default" data-dismiss="modal"
						onclick="window.close();">
						<spring:message code="lbl.close" />
					</button>
				</div>
			</div>
		</div>
	</div>
</div>

