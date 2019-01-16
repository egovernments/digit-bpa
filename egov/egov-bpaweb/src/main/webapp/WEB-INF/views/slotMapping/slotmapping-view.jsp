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
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<div class="row">
	<div class="col-md-12">
		<div class="panel panel-primary" data-collapsed="0">
			<div class="panel-heading custom_form_panel_heading">
				<div class="panel-title"></div>
			</div>
			<div class="panel-body" data-collapsed="0">
				<div class="row add-border">
					<div class="col-sm-3 add-margin">
						<spring:message code="lbl.zonal.office" />
					</div>
					<div class="col-sm-3 add-margin view-content">
						<c:out value="${slotMapping.zone.name}" default="N/A"></c:out>
					</div>
					<div class="col-sm-3 add-margin">
						<spring:message code="lbl.slotmapping.applicationtype" />
					</div>
					<div class="col-sm-3 add-margin view-content">
						<c:out value="${slotMapping.applType.applicationTypeVal}" default="N/A"></c:out>
					</div>
				</div>

				<div class="row add-border">
					<div class="col-sm-3 add-margin">
						<spring:message code="lbl.slotmapping.numberofapplications" />
					</div>
					<div class="col-sm-3 add-margin view-content">
						<c:out value="${slotMapping.maxSlotsAllowed}" default="N/A"></c:out>
					</div>
					<c:if test="${slotMapping.applType eq 'ALL_OTHER_SERVICES' || slotMapping.applType eq 'OCCUPANCY_CERTIFICATE'}">
						<div class="col-sm-3 add-margin">
							<spring:message code="lbl.slotmapping.numberofresapplications" />
						</div>
						<div class="col-sm-3 add-margin view-content">
							<c:out value="${slotMapping.maxRescheduledSlotsAllowed}"
								default="N/A"></c:out>
						</div>
					</c:if>
				</div>
				<c:if test="${slotMapping.applType eq 'ONE_DAY_PERMIT'}">
					<div class="row add-border">
						<div class="col-sm-3 add-margin">
							<spring:message code="lbl.rev.ward" />
						</div>
						<div class="col-sm-3 add-margin view-content">
							<c:out value="${slotMapping.revenueWard.name}" default="N/A"></c:out>
						</div>
						<div class="col-sm-3 add-margin">
							<spring:message code="lbl.election.ward" />
						</div>
						<div class="col-sm-3 add-margin view-content">
							<c:out value="${slotMapping.electionWard.name}" default="N/A"></c:out>
						</div>

					</div>
				</c:if>
				<c:if test="${slotMapping.applType eq 'ONE_DAY_PERMIT'}">
					<div class="row add-border">
						<div class="col-sm-3 add-margin">
							<spring:message code="lbl.slotmapping.days" />
						</div>
						<div class="col-sm-3 add-margin view-content">
							<c:out value="${slotMapping.days}" default="N/A"></c:out>
						</div>
					</div>
				</c:if>
			</div>
		</div>
		<div class="buttonbottom" align="center">
			<input type="button" name="button2" value="Close"
				class="btn btn-default" onclick="window.close();" />
		</div>
	</div>
</div>

