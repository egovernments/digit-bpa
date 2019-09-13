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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="uri" value="${req.requestURI}" />
<c:set var="domainURL"
	value="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}" />
<div class="container-fluid">
	<header class="citizen-header">
		<nav class="navbar center-align-flex bpa-navbar-new">
			<div class="col-md-6 col-sm-7 col-xs-12 left-section">
				<span> <img
					src="<c:url value='/downloadfile/logo' context="/egi"/>"
					height="60" class="homepage_logo">
				</span> <span class="corporation-name"><spring:message
						code="lbl.portalservices" /></span>
			</div>
			<%--<div class="col-md-6 col-sm-7 col-xs-12 left-section">
					<span>
						<a href="http://www.egovernments.org" target="_blank">
								<img src="<c:url value='/resources/global/images/logo@2x.png' context='/egi'/>" title="Powered by eGovernments" height="20px">
							</a>
					</span>
				</div>--%>

			<div style="justify-content: flex-end;"
				class="center-align-flex col-md-6 col-sm-5 col-xs-12 right-section">

				<span
					class="pull-right profile-name"> <span
					class="text hidden-sm">${userName }</span> <span><i
						class="fa fa-caret-down" aria-hidden="true"></i></span>
					<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">

						<li><a href="/egi/home/profile/edit"
							onClick="MyWindow=window.open('/egi/home/profile/edit','MyWindow',width=980,height=650); return false;">Edit
								Profile</a></li>
						<li><a href="javascript:void(0)"
							onclick="jQuery('.change-password').modal('show', {backdrop: 'static'});">Change
								Password</a></li>
						<li><a href="/egi/logout"><i class="fa fa-sign-out" aria-hidden="true"></i> Sign Out</a></li>
					</ul>
				</span> <span class="pull-right profile-dd"> <a
					href="http://www.egovernments.org" target="_blank"> <img
						src="<cdn:url value='/resources/global/images/digit-logo-black.png' context='/egi'/>"
						title="Powered by eGovernments" height="35px" style="opacity: 0.8;">
				</a>
				</span>
			</div>
		</nav>
	</header>
	<div class="bpa-citizen-main-wrapper clearfix">
		<div class="left-menu col-md-2">
			<ul class="modules-ul">
				<li class="modules-li inbox active" data-module="home"><a
					class="home-module-menu-link" href="javascript:void(0)">
						<div>
							<i class="fa fa-home img-view"></i>
						</div>
						<div class="module-text">Home</div>
				</a></li>
				<c:forEach items="${moduleNames}" var="moduleName" varStatus="item">
					<li class="modules-li" data-module="${moduleName }"><a
						class="home-module-menu-link" href="javascript:void(0)">
							<div>
								<i class="fa fa-building img-view" aria-hidden="true"></i>
							</div>
							<div class="module-text">${moduleName }</div>
					</a></li>
				</c:forEach>
			</ul>
		</div>
		<div class="right-content col-md-10">


			<div class="main-content">
				<div class="action-bar hide">
					<div class="action-item">
						<input type="text" id="search" placeholder="Search">
					</div>
					<div class="action-item">
						<i class="material-icons">search</i>
					</div>
					<div class="module-heading">
					</div>				
				</div>
				<div class="inbox-modules clearfix">
					<div id="showServiceGroup" class = "col-md-12 clearfix">
						<div class = "col-md-6 padding0">
						<input type="hidden" id="userId" value="${currentUser.id}">
							<div class="col-md-12 welcome-message"
								style="line-height: 2.3; padding: 0;">
								Welcome ${userName},
							</div>
						</div>
						<div class = "col-md-6">
							<div class="col-md-6 text-right"
								style="line-height: 2.3;">
								<spring:message code="lbl.servicegroup" />
								:
							</div>
							<div class="col-md-6">
								<select class="form-control" id="serviceGroup">
									<option value="all">ALL</option>
									<c:forEach items="${distinctModuleNames}" var="module"
										varStatus="item">
										<option value="${module.contextRoot }">${module.displayName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<div class="stats-item col-md-12" style="overflow: auto;padding: 0">
						<div class="col-md-4 col-sm-4 services">
							<div name = "totalServicesApplied" class = "bpa-service-card" id="totalServicesAppliedDiv" style="cursor: pointer">
								<div class="bpa-home-card content x">
									<div class="count" id="totalServicesAppliedSize">
										${totalServicesAppliedSize }</div>
									<div class="text">
										<spring:message code="lbl.totalservicesapplied" />
									</div>
								</div>
							</div>
						</div>
						<div class="col-md-4 col-sm-4 services">
							<div name = "servicesUnderScrutiny" class = "bpa-service-card" id="servicesUnderScrutinyDiv" style="cursor: pointer">
								<div class="bpa-home-card content x clicked-card">
									<div class="count" id="totalServicesPendingSize">
										${totalServicesPendingSize}</div>
									<div class="text color-generic-new">
										<spring:message code="lbl.servicesunderscrutiny" />
									</div>
								</div>
							</div>
						</div>
						<div class="col-md-4 col-sm-4 services">
							<div name = "servicesCompleted" class = "bpa-service-card" id="servicesCmpletedDiv" style="cursor: pointer">
								<div class="bpa-home-card content x">
									<div class="count" id="totalServicesCompletedSize">
										${totalServicesCompletedSize }</div>
									<div class="text">
										<spring:message code="lbl.servicescompleted" />
									</div>
								</div>
							</div>
						</div>
					</div>
					<br>
					
					<div class="bpa-table-container col-md-12">
						<table class = "hover row-border" id = "bpa-home-table" >
							<thead>
								<tr>
									<th><spring:message code="lbl.slno" /></th>
									<th><spring:message code="lbl.applicant.name" /></th>
									<th><spring:message code="lbl.applicartionno" /></th>
									<th><spring:message code="lbl.applicationdate" /></th>
									<th><spring:message code="lbl.servicegroup" /></th>
									<th><spring:message code="lbl.servicename" /></th>
									<th><spring:message code="lbl.status" /></th>
									<th><spring:message code="lbl.pendingaction" /></th>
									<%-- <th><spring:message code="lbl.expectedservicedelivery" /></th>
									<th><spring:message code="lbl.description" /></th> --%>
								</tr>
							</thead>
						</table>
						
						<%-- <table class="table table-striped datatable" id="tabelPortal">
							<thead>
								<tr>
									<th><spring:message code="lbl.slno" /></th>
									<th><spring:message code="lbl.applicant.name" /></th>
									<th><spring:message code="lbl.applicartionno" /></th>
									<th><spring:message code="lbl.applicationdate" /></th>
									<th><spring:message code="lbl.servicegroup" /></th>
									<th><spring:message code="lbl.servicename" /></th>
									<th><spring:message code="lbl.status" /></th>
									<th><spring:message code="lbl.pendingaction" /></th>
									<th><spring:message code="lbl.expectedservicedelivery" /></th>
									<th><spring:message code="lbl.description" /></th>
								</tr>
							</thead>
							<tbody class="servicesUnderScrutinyHide">
								<c:forEach items="${totalServicesPending}" var="inboxItem"
									varStatus="item">
									<c:set var="newDomainURL"
										value="${fn:replace(domainURL, clientId, inboxItem.portalInbox.tenantId)}" />
									<tr
										onclick="openPopUp('${newDomainURL}${inboxItem.portalInbox.link}');"
										class="${inboxItem.portalInbox.module.contextRoot } showAll">
										<td><span class="spansno">${item.index + 1}</span></td>
										<td>${inboxItem.portalInbox.applicantName == null ? inboxItem.portalInbox.portalInboxUsers[0].user.name : inboxItem.portalInbox.applicantName}</td>
										<td>${inboxItem.portalInbox.applicationNumber}</td>
										<td><fmt:formatDate
												value="${inboxItem.portalInbox.applicationDate}"
												pattern="dd/MM/yyyy" /></td>
										<td>${inboxItem.portalInbox.module.displayName}</td>
										<td>${inboxItem.portalInbox.serviceType}</td>
										<td>${inboxItem.portalInbox.status}</td>
										<td><c:choose>
												<c:when
													test="${inboxItem.portalInbox.state != null && inboxItem.portalInbox.state.nextAction != ''}">
		 								${inboxItem.portalInbox.state.nextAction}
	 								</c:when>
												<c:otherwise>
													<div class="text-center">
														<c:out value="-"></c:out>
													</div>
												</c:otherwise>
											</c:choose></td>
										<td>
		 						<div>
									<fmt:formatDate
										value="${inboxItem.portalInbox.slaEndDate}"
										pattern="dd/MM/yyyy" />
							</div>
		 					</td>
										<td>
 								${inboxItem.portalInbox.detailedMessage}
		 					</td>
									</tr>
								</c:forEach>
							</tbody>
							<tbody class="totalServicesAppliedHide">
								<c:forEach items="${totalServicesApplied}" var="inboxItem"
									varStatus="item">
									<c:set var="newDomainURL"
										value="${fn:replace(domainURL, clientId, inboxItem.portalInbox.tenantId)}" />
									<tr
										onclick="openPopUp('${newDomainURL}${inboxItem.portalInbox.link}');"
										class="${inboxItem.portalInbox.module.contextRoot } showAll">
										<td><span class="spansno">${item.index + 1}</span></td>
										<td>${inboxItem.portalInbox.applicantName == null ? inboxItem.portalInbox.portalInboxUsers[0].user.name : inboxItem.portalInbox.applicantName}</td>
										<td>${inboxItem.portalInbox.applicationNumber}</td>
										<td><fmt:formatDate
												value="${inboxItem.portalInbox.applicationDate}"
												pattern="dd/MM/yyyy" /></td>

										<td>${inboxItem.portalInbox.module.displayName}</td>
										<td>${inboxItem.portalInbox.serviceType}</td>
										<td>${inboxItem.portalInbox.status}</td>
										<td><c:choose>
												<c:when
													test="${inboxItem.portalInbox.state != null && inboxItem.portalInbox.state.nextAction != ''}">
									${inboxItem.portalInbox.state.nextAction}
								</c:when>
												<c:otherwise>
													<div class="text-center">
														<c:out value="-"></c:out>
													</div>
												</c:otherwise>
											</c:choose></td>
										<td>
							<div>
								<fmt:formatDate
										value="${inboxItem.portalInbox.slaEndDate}"
										pattern="dd/MM/yyyy"/>
							</div>
						</td>
										<td>
                                 ${inboxItem.portalInbox.detailedMessage}
                             </td>
									</tr>
								</c:forEach>
							</tbody>
							<tbody class="totalServicesCompletedHide">
								<c:forEach items="${totalServicesCompleted}" var="inboxItem"
									varStatus="item">
									<c:set var="newDomainURL"
										value="${fn:replace(domainURL, clientId, inboxItem.portalInbox.tenantId)}" />
									<tr
										onclick="openPopUp('${newDomainURL}${inboxItem.portalInbox.link}');"
										class="${inboxItem.portalInbox.module.contextRoot } showAll">
										<td><span class="spansno">${item.index + 1}</span></td>
										<td>${inboxItem.portalInbox.applicantName == null ? inboxItem.portalInbox.portalInboxUsers[0].user.name : inboxItem.portalInbox.applicantName}</td>
										<td>${inboxItem.portalInbox.applicationNumber}</td>
										<td><fmt:formatDate
												value="${inboxItem.portalInbox.applicationDate}"
												pattern="dd/MM/yyyy" /></td>
										<td>${inboxItem.portalInbox.module.displayName}</td>
										<td>${inboxItem.portalInbox.serviceType}</td>
										<td>${inboxItem.portalInbox.status}</td>
										<td><c:choose>
												<c:when
													test="${inboxItem.portalInbox.state != null && inboxItem.portalInbox.state.nextAction != ''}">
									${inboxItem.portalInbox.state.nextAction}
								</c:when>
												<c:otherwise>
													<div class="text-center">
														<c:out value="-"></c:out>
													</div>
												</c:otherwise>
											</c:choose></td>
										<td>
							<div>
								<fmt:formatDate
										value="${inboxItem.portalInbox.slaEndDate}"
										pattern="dd/MM/yyyy"/>
							</div>
						</td>
										<td>
                                 ${inboxItem.portalInbox.detailedMessage}
                             </td>
									</tr>
								</c:forEach>
							</tbody>
						</table> --%>
					</div>
				</div>
				<br> <input type="hidden"
					value="<spring:message code="error.pwd.invalid.case" />"
					id="errorPwdInvalid" /> <input type="hidden" value="${clientId}"
					id="clientId" name="clientId" />

				<c:forEach items="${services}" var="service" varStatus="item">
					<div class="is-flex services-item">
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 services"
							data-services="${service.module.displayName }">
							<a href="javascript:openPopUp('${service.url}')">
								<div class="content a">${service.name}</div>
							</a>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>
	
<link rel="stylesheet" href="<cdn:url value='/resources/global/css/jquery/plugins/datatables/jquery.dataTables.min.css' context='/egi'/>"/>
<link rel="stylesheet" href="<cdn:url value='/resources/global/css/jquery/plugins/datatables/dataTables.bootstrap.min.css' context='/egi'/>">	
<script	src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/jquery.dataTables.min.js' context='/egi'/>"></script>
<script	src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/responsive/js/datatables.responsive.js' context='/egi'/>"></script>
<script	src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/dataTables.bootstrap.js' context='/egi'/>"></script>