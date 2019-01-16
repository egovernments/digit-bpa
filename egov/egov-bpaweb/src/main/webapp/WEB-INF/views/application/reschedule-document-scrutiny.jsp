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

<form:form role="form" action="" modelAttribute="scheduleScrutiny"
		   id="scrutinyReScheduleForm"
		   cssClass="form-horizontal form-groups-bordered"
		   enctype="multipart/form-data">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-primary" data-collapsed="0">
				<jsp:include page="view-scheduled-scrutiny-details.jsp"></jsp:include>
			</div>
			<c:forEach items="${bpaApplication.slotApplications}" var="slotAppln"
					   varStatus="counter">
				<c:if test="${counter.last}">
					<input type="hidden" id="previousAppointmentDate"
						   value="${slotAppln.slotDetail.slot.appointmentDate}" />
					<input type="hidden" id="previousAppointmentTime"
						   value="${slotAppln.slotDetail.appointmentTime}" />
				</c:if>
			</c:forEach>

			<div class="panel panel-primary" data-collapsed="0">
				<div class="panel-heading custom_form_panel_heading">
					<div class="panel-title">
					</div>
				</div>
				<div class="panel-body">
					<input type="hidden" name="reScheduledBy" value="${scheduleScrutiny.reScheduledBy}">
					<div class="form-group" id="scheduleElements">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.appmnt.date"/> <span class="mandatory"></span> </label>
							<div class="col-sm-3 add-margin">
                                <input type="hidden" name="reScheduleAction" id="reScheduleAction" value="">
								<input type="hidden" name="applicationNumber" id="applicationNumber" value="${bpaApplication.applicationNumber}">
								<input type="hidden" name="applicationId" id="applicationId" value="${bpaApplication.id}">
								<input type="hidden" name="zoneId" id="zoneId" value="${bpaApplication.siteDetail[0].adminBoundary.parent.id}">
								<input type="hidden" id="slotsNotAvailableMsg" value="${slotsNotAvailableMsg}">
								<form:select path="appointmentDate" id="appointmentDate" data-first-option="false" cssClass="form-control" required="required">
									<form:option value="">Select</form:option>
									<form:options items="${appointmentDates}"></form:options>
								</form:select>
								<form:errors path="appointmentDate" cssClass="add-margin error-msg"/>
							</div>
						<label class="col-sm-2 control-label text-right"><spring:message
								code="lbl.appmnt.time"/><span class="mandatory"></span></label>
						<div class="col-sm-3 add-margin">
								<select name="appointmentTime" id="appointmentTime" class="form-control" required="required">
									<option>Select</option>
								</select>
							<form:errors path="appointmentTime" cssClass="add-margin error-msg"/>
						</div>
					</div>
					<div class="form-group text-center" id="reScheduleButtons">
						<button class='btn btn-primary reScheduleAction' id="autoRescheduleSubmit" value="Auto Re-Schedule">
							Auto ReSchedule
						</button>
                        <c:if test="${scheduleScrutiny.reScheduledBy eq 'CITIZENPORTAL'}">
                            <button class='btn btn-primary reScheduleAction' id="cancelSubmit" value="Cancel Application">
								Cancel Application
                            </button>
                        </c:if>
						<a href='javascript:void(0)' class='btn btn-default'
						   onclick='self.close()'><spring:message code='lbl.close' /></a>
					</div>
				</div>
			</div>
			<div class="text-center" id="buttons">
				<button type="submit" class='btn btn-primary reScheduleAction' id="rescheduleSubmit" value="Re-Schedule">
					<spring:message code='lbl.reschedule' />
				</button>
				<a href='javascript:void(0)' class='btn btn-default'
				   onclick='self.close()'><spring:message code='lbl.close' /></a>
			</div>
			<br>
			<div class="panel panel-primary" data-collapsed="0">
				<jsp:include page="reschedule-scrutiny-notes.jsp"></jsp:include>
			</div>
		</div>
	</div>
</form:form>

<script
		src="<cdn:url value='/resources/global/js/egov/inbox.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script
		src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/moment.min.js' context='/egi'/>"></script>
<script
		src="<cdn:url value='/resources/js/app/reschedule-doc-scrutiny.js?rnd=${app_release_no}'/>"></script>