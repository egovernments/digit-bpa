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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>


<div class="panel-heading custom_form_panel_heading">
	<div class="panel-title">
		<spring:message code="lbl.basic.info" />
	</div>
</div>
<div class="panel-body">
	<div id="appDet">
				<div class="form-group">
					<label class="col-sm-3 control-label text-right edcrApplnDetails"><spring:message
							code="lbl.edcr.number" /> <span class="mandatory"></span> </label>
					<div class="col-sm-3 add-margin">
						<form:input
							class="form-control patternvalidation edcrApplnDetails"
							maxlength="20" id="eDcrNumber" path="bpaApplication.eDcrNumber"
							required="required" disabled="true"/>
						<form:errors path="bpaApplication.eDcrNumber" cssClass="add-margin error-msg" />
					</div>
					 <label class="col-sm-2 control-label text-right"><spring:message
							code="lbl.occupancy" /><span class="mandatory"></span></label>
					<div class="col-sm-3 add-margin">
						<select name="permitOccupanciesTemp" multiple
							id="occupancyapplnlevel"
							class="form-control occupancies tick-indicator" readonly="true">
							<c:forEach items="${occupancyList}" var="ocpncy">
								<option value="${ocpncy.id}" title="${ocpncy.name}"
									<c:if test="${fn:contains(noc.bpaApplication.permitOccupancies, ocpncy)}"> Selected </c:if>>${ocpncy.name}</option>
							</c:forEach>
						</select>
						<form:errors path="bpaApplication.permitOccupanciesTemp"
							cssClass="add-margin error-msg" />
					</div> 
				</div>
		
		<div class="form-group" id="applicationTypeSec">
				<label class="col-sm-3 control-label text-right"><spring:message
						code="lbl.applctn.type" /><span class="mandatory"></span></label>
				<div class="col-sm-3 add-margin">
					<form:input
							class="form-control patternvalidation edcrApplnDetails"
							maxlength="20" id="applicationType" path="bpaApplication.applicationType.name"
							required="required" disabled="true"/>
					<form:errors path="bpaApplication.applicationType"	cssClass="add-margin error-msg" />
				</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label text-right"><spring:message
					code="lbl.service.type" /> <span class="mandatory"></span> </label>
			<div class="col-sm-3 add-margin">
				<form:input
							class="form-control patternvalidation edcrApplnDetails"
							maxlength="20" id="servieType" path="bpaApplication.serviceType.description"
							required="required" disabled="true"/>
				<form:errors path="bpaApplication.serviceType" cssClass="add-margin error-msg" />
			</div>
		</div>

		<c:if test="${mode != 'new'}">
			<div class="form-group">
				<label class="col-sm-3 control-label text-right"><spring:message
						code="lbl.application.number" /> <span class="mandatory"></span>
				</label>
				<div class="col-sm-3 add-margin">
					<form:input class="form-control patternvalidation" maxlength="50"
						id="applicationNumber" path="bpaApplication.applicationNumber" disabled="true" />
					<form:errors path="bpaApplication.applicationNumber"
						cssClass="add-margin error-msg" />
				</div>
			</div>
		</c:if>

		<div class="form-group">

			<label class="col-sm-3 control-label text-right"> <spring:message
					code="lbl.appln.date" /> <span class="mandatory"></span>
			</label>
			<div class="col-sm-3 add-margin">
				<form:input path="bpaApplication.applicationDate" class="form-control datepicker"
					data-date-end-date="0d" id="applicationDate"
					data-inputmask="'mask': 'd/m/y'" required="required" />
				<form:errors path="bpaApplication.applicationDate" cssClass="add-margin error-msg" />
			</div>

			
		</div>
	</div>
</div>
