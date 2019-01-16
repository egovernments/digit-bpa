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

<form:form role="form" action="/bpa/slotmapping/update"
	modelAttribute="slotMapping" id="slotMappingform"
	cssClass="form-horizontal form-groups-bordered"
	enctype="multipart/form-data">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-primary" data-collapsed="0">
				<div class="panel-heading">
					<div class="panel-title">
						<spring:message code="lbl.sm.main.title" />
					</div>
				</div>
				<div class="panel-body">
					<div class="form-group">
						<label class="col-sm-3 control-label"><spring:message
								code="lbl.zonal.office" /> <span class="mandatory"></span></label>
						<div class="col-sm-3 add-margin">
							<input type="hidden" name="slotMapping" value="${slotMapping.id}">
							<input type="hidden" id="message" value="${message}">
							<form:select path="zone" id="zone" cssClass="form-control"
								disabled="true" value="${slotMapping.zone.name}">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${zones}" itemValue="id" itemLabel="name" />
							</form:select>
							<form:errors path="zone" cssClass="add-margin error-msg" />
						</div>
						<label class="col-sm-2 control-label text-right"><spring:message
								code="lbl.slotmapping.applicationtype" /> <span
							class="mandatory"></span></label>
						<div class="col-sm-3 add-margin">
							<form:select path="applType" id="applType"
								cssClass="form-control" disabled="true"
								value="${slotMapping.applType}">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<c:forEach items="${slotMappingApplTypes}" var="applicationType">
									<option value="${applicationType}" <c:if test="${applicationType eq slotMapping.applType}"> Selected </c:if> >${applicationType.applicationTypeVal}</option>
								</c:forEach>
							</form:select>
							<form:errors path="applType" cssClass="add-margin error-msg" />
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.slotmapping.numberofapplications" /> <span
							class="mandatory"></span></label>
						<div class="col-sm-3 add-margin">
							<form:input type="text" cssClass="form-control inline-elem patternvalidation required"
								path="maxSlotsAllowed" id="maxSlotsAllowed" data-pattern="number"
								value="${slotMapping.maxSlotsAllowed}" />
							<form:errors path="maxSlotsAllowed" maxlength="10"
								cssClass="error-msg" />
						</div>
						<label
							class="col-sm-2 control-label text-right maxRescheduledSlotsAllowed"><spring:message
								code="lbl.slotmapping.numberofresapplications" /> <span
							class="mandatory"></span></label>
						<div class="col-sm-3 add-margin ">
							<form:input type="text"
								cssClass="form-control inline-elem patternvalidation maxRescheduledSlotsAllowed required"
								path="maxRescheduledSlotsAllowed"
								id="maxRescheduledSlotsAllowed" data-pattern="number"
								value="${slotMapping.maxRescheduledSlotsAllowed}" />
							<form:errors path="maxRescheduledSlotsAllowed" maxlength="10"
								cssClass="error-msg" />
						</div>
					</div>
					<div class="form-group allservices">
						<label class="col-sm-3 control-label"><spring:message
								code="lbl.rev.ward" /> <span class="mandatory"></span>
						</label>
						<div class="col-sm-3 add-margin">
							<form:input type="text" cssClass="form-control required"
								path="revenueWard" id="revenueWard" disabled="true"
								value="${slotMapping.revenueWard.name}" />
							<form:errors path="revenueWard" maxlength="10"
								cssClass="error-msg" />
						</div>
						<label class="col-sm-2 control-label"><spring:message
								code="lbl.election.ward" /> <span class="mandatory"></span>
						</label>
						<div class="col-sm-3 add-margin">
							<form:input type="text" cssClass="form-control required"
								path="electionWard" id="electionWard" disabled="true"
								value="${slotMapping.electionWard.name}" />
							<form:errors path="electionWard" maxlength="10"
								cssClass="error-msg" />
						</div>
					</div>

				</div>
				<div class="form-group daysforonedaypermit">
					<label class="col-sm-3 control-label"><spring:message
							code="lbl.slotmapping.days" /> <span class="mandatory"></span> </label>
					<div class="col-sm-3 add-margin">
						<form:select path="days" id="days" cssClass="form-control">
							<form:option value="">
								<spring:message code="lbl.select" />
							</form:option>
							<form:options items="${daysList}" />
						</form:select>
						<form:errors path="days" cssClass="add-margin error-msg" />
					</div>
				</div>
			</div>
		</div>
		</div>
	</div>
	<div class="text-center">
		<button type='submit' class='btn btn-primary' id="buttonSubmit">
			<spring:message code='lbl.update' />
		</button>
		<a href='javascript:void(0)' class='btn btn-default'
			onclick='self.close()'><spring:message code='lbl.close' /></a>
	</div>
</form:form>

<script
	src="<cdn:url value='/resources/js/app/slotmapping-new.js?rnd=${app_release_no}'/>">
	
</script>
