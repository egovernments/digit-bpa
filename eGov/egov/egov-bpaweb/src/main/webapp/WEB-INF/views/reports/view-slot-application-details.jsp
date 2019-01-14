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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>

<form:form role="form" action=""
	modelAttribute="searchBpaApplicationForm" id="searchBpaApplicationForm"
	cssClass="form-horizontal form-groups-bordered"
	enctype="multipart/form-data">
<input type="hidden" name="appointmentDate" id="appointmentDate"
	value="${appointmentDate}" />
<input type="hidden" name="appointmentTime" id="appointmentTime"
	value="${appointmentTime}" />
<input type="hidden" name="wardId" id="wardId"
	value="${revenueWardId}" />
<input type="hidden" name="electionWardId" id="electionWardId"
	value="${electionWardId}" />
<input type="hidden" name="zoneId" id="zoneId" value="${zoneId}" />
<input type="hidden" name="zone" id="zone" value="${zone}" />
<input type="hidden" name="applicationType" id="applicationType" value="${applicationType}" />
<input type="hidden" name="scheduleType" id="scheduleType" value="${scheduleType}" />

</form:form>
<div class="row display-hide report-section" id="table_container">
	<div class="col-md-12 table-header text-left"></div>
	<div class="col-md-12 form-group report-table-container">
		<table class="table table-bordered table-hover multiheadertbl"
			id="viewSlotApplicationDetails">
			<thead>
				<tr>
					<th><spring:message code="lbl.slno" /></th>
					<th><spring:message code="lbl.applicant.name" /></th>
					<th><spring:message code="lbl.application.no" /></th>
					<th><spring:message code="lbl.appln.date" /></th>
					<th><spring:message code="lbl.owner.address" /></th>
					<th><spring:message code="lbl.service.type" /></th>
					<th><spring:message code="lbl.occupancy" /></th>
					<th><spring:message code="lbl.zone" /></th>
					<th><spring:message code="lbl.rev.ward" /></th>
					<th><spring:message code="lbl.election.ward" /></th>
					<th><spring:message code="lbl.locality" /></th>
					<th><spring:message code="lbl.re.survey.no" /></th>
				</tr>
			</thead>
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

<script src="<cdn:url value='/resources/js/app/view-slot-applications-report.js?rnd=${app_release_no}'/> "></script>
