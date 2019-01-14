<%--
  ~ eGov suite of products aim to improve the internal efficiency,transparency,
  ~    accountability and the service delivery of the government  organizations.
  ~
  ~     Copyright (C) <2017>  eGovernments Foundation
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
  --%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>

<form:form role="form" action=""
		   modelAttribute="slotDetailsHelper" id="oneDayPermitApplnsSlotDetailsReport"
		   cssClass="form-horizontal form-groups-bordered"
		   enctype="multipart/form-data">
	<input type="hidden" id="applicationType" value="${applicationType}">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-primary" data-collapsed="0">
				<div class="panel-heading">
				</div>
				<div class="panel-body">
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"> <spring:message
								code="lbl.zonal.office"/>
						</label>
						<div class="col-sm-3 add-margin">
							<form:select path="zoneId" data-first-option="false" id="zone"
										 cssClass="form-control">
								<form:option value="">
									<spring:message code="lbl.select"/>
								</form:option>
								<form:options items="${zones}" itemValue="id" itemLabel="name"/>
							</form:select>
							<form:errors path="zoneId" cssClass="add-margin error-msg"/>
						</div>
						<label class="col-sm-2 control-label text-right"><spring:message
								code="lbl.appmnt.date"/></label>
						<div class="col-sm-3 add-margin">
							<form:input path="appointmentDate" class="form-control datepicker"
										data-date-end-date="0d" id="fromDate"
										data-inputmask="'mask': 'd/m/y'"/>
							<form:errors path="appointmentDate" cssClass="add-margin error-msg"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.rev.ward" /> </label>
						<div class="col-sm-3 add-margin">
							<form:select path="revenueWardId" data-first-option="false" id="ward"
										 cssClass="form-control">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${wards}" itemValue="id" itemLabel="name" />
							</form:select>
							<form:errors path="revenueWardId" cssClass="add-margin error-msg" />
						</div>
						<label class="col-sm-2 control-label text-right"><spring:message
								code="lbl.election.ward" /></label>
						<div class="col-sm-3 add-margin">
							<form:select path="electionWardId" data-first-option="false"
										 id="electionBoundary" cssClass="form-control">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${electionwards}" itemValue="id"
											  itemLabel="name" />
							</form:select>
							<form:errors path="electionWardId" cssClass="add-margin error-msg" />
						</div>
					</div>
					<%--<div class="form-group">
						<label class="col-sm-3 control-label text-right"> By Days
						</label>
						<div class="col-sm-3 add-margin">
							<form:select path="byNoOfDays" data-first-option="false" id="byNoOfDays"
										 cssClass="form-control">
								<form:options items="${searchByNoOfDays}" />
							</form:select>
							<form:errors path="byNoOfDays" cssClass="add-margin error-msg"/>
						</div>
					</div>--%>
				</div>
			</div>
		</div>
	</div>
	<div class="text-center">
		<button type='button' class='btn btn-primary' id="btnSearch">
			<spring:message code='lbl.search'/>
		</button>
		<button type="reset" class="btn btn-danger">
			<spring:message code="lbl.reset"/>
		</button>
		<a href='javascript:void(0)' class='btn btn-default'
		   onclick='self.close()'><spring:message code='lbl.close'/></a>
	</div>
</form:form>
<div class="row display-hide report-section" id="table_container">
	<div class="col-md-12 table-header text-left">The one day permit application slot details search result
		is
	</div>
	<div class="col-md-12 form-group report-table-container">
		<table class="table table-bordered table-hover multiheadertbl"
			   id="oneDayPermitApplnsSlotDetailsCount">
			<thead>
				<tr>
					<th><spring:message code="lbl.slno"/> </th>
					<th><spring:message code="lbl.zonal.office"/></th>
					<th><spring:message code="lbl.election.ward"/></th>
					<th><spring:message code="lbl.appmnt.date"/></th>
					<th><spring:message code="lbl.appmnt.time"/></th>
					<th><spring:message code="lbl.max.allowed.schdle"/></th>
					<th><spring:message code="lbl.utilized.schdle"/></th>
				</tr>
			</thead>
			<tfoot id="report-footer">
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td>Total</td>
					<td></td>
					<td></td>
				</tr>
			</tfoot>
		</table>
	</div>
</div>
<link rel="stylesheet"
	  href="<cdn:url value='/resources/global/css/jquery/plugins/datatables/jquery.dataTables.min.css' context='/egi'/>"/>
<link rel="stylesheet"
	  href="<cdn:url value='/resources/global/css/jquery/plugins/datatables/dataTables.bootstrap.min.css' context='/egi'/>">
<script type="text/javascript"
		src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/jquery.dataTables.min.js' context='/egi'/>"></script>
<script type="text/javascript"
		src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/dataTables.bootstrap.js' context='/egi'/>"></script>
<script
		src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/dataTables.buttons.min.js' context='/egi'/>"></script>
<script
		src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/buttons.bootstrap.min.js' context='/egi'/>"></script>
<script
		src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/buttons.flash.min.js' context='/egi'/>"></script>
<script
		src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/jszip.min.js' context='/egi'/>"></script>
<script
		src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/pdfmake.min.js' context='/egi'/>"></script>
<script
		src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/vfs_fonts.js' context='/egi'/>"></script>
<script
		src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/buttons.html5.min.js' context='/egi'/>"></script>
<script
		src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/buttons.print.min.js' context='/egi'/>"></script>
<script type="text/javascript"
		src="<cdn:url value='/resources/global/js/jquery/plugins/jquery.validate.min.js' context='/egi'/>"></script>
<script src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/datetime-moment.js' context='/egi'/>"></script>
<script src="<cdn:url value='/resources/global/js/bootstrap/bootstrap-datepicker.js' context='/egi'/>"
		type="text/javascript"></script>
<script
		src="<c:url value='/resources/global/js/handlebars/handlebars.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script src="<cdn:url value='/resources/js/app/bpa-ajax-helper.js?rnd=${app_release_no}'/> "></script>
<script src="<cdn:url value='/resources/js/app/onedaypermit-slotdetails-report.js?rnd=${app_release_no}'/> "></script>

