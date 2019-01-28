<%--
  ~ eGov suite of products aim to improve the internal efficiency,transparency,
  ~      accountability and the service delivery of the government  organizations.
  ~
  ~       Copyright (C) <2017>  eGovernments Foundation
  ~
  ~       The updated version of eGov suite of products as by eGovernments Foundation
  ~       is available at http://www.egovernments.org
  ~
  ~       This program is free software: you can redistribute it and/or modify
  ~       it under the terms of the GNU General Public License as published by
  ~       the Free Software Foundation, either version 3 of the License, or
  ~       any later version.
  ~
  ~       This program is distributed in the hope that it will be useful,
  ~       but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~       GNU General Public License for more details.
  ~
  ~       You should have received a copy of the GNU General Public License
  ~       along with this program. If not, see http://www.gnu.org/licenses/ or
  ~       http://www.gnu.org/licenses/gpl.html .
  ~
  ~       In addition to the terms of the GPL license to be adhered to in using this
  ~       program, the following additional terms are to be complied with:
  ~
  ~           1) All versions of this program, verbatim or modified must carry this
  ~              Legal Notice.
  ~
  ~           2) Any misrepresentation of the origin of the material is prohibited. It
  ~              is required that all modified versions of this material be marked in
  ~              reasonable ways as different from the original version.
  ~
  ~           3) This license does not grant any rights to any user of the program
  ~              with regards to rights under trademark law for use of the trade names
  ~              or trademarks of eGovernments Foundation.
  ~
  ~     In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
  --%>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>

<div class="panel-heading custom_form_panel_heading">
	<div class="panel-title text-center no-float">
		<c:if test="${not empty message}">
			<strong>${message}</strong>
		</c:if>
	</div>
</div>

<div class="row">
	<div class="col-md-12">
		<input type="hidden" id="workFlowAction" name="workFlowAction" /> <input
			type="hidden" id="wfstateDesc"
			value="${occupancyCertificate.state.value}" /> <input type="hidden"
			id="status" value="${occupancyCertificate.status.code}"> <input
			type="hidden" id="wfstate" value="${occupancyCertificate.state.id}" />
		<input type="hidden" id="serviceTypeCode"
			value="${occupancyCertificate.parent.serviceType.code}" />

		<ul class="nav nav-tabs" id="settingstab">
			<li class="active"><a data-toggle="tab" href="#application-info"
				data-tabidx=0><spring:message code='lbl.appln.details' /></a></li>
			<li><a data-toggle="tab" href="#document-info" data-tabidx=1><spring:message
						code='title.documentdetail' /></a></li>
			<li><a data-toggle="tab" href="#noc-document-info" data-tabidx=2><spring:message
						code='lbl.noc.doc.details' /></a></li>
		</ul>
		<div class="tab-content">
			<div id="application-info" class="tab-pane fade in active">
				<div class="panel panel-primary" data-collapsed="0">
					<jsp:include page="view-oc-application-details.jsp"></jsp:include>
				</div>
				<div class="panel panel-primary" data-collapsed="0">
						<jsp:include
							page="../application/edcr-application-details-form.jsp"></jsp:include>
					</div>
				<div class="panel panel-primary" data-collapsed="0">
					<jsp:include page="view-bpa-basic-application-details.jsp"></jsp:include>
				</div>
				<c:if test="${not empty occupancyCertificate.existingBuildings}">
						<div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="view-oc-existing-building-details.jsp"></jsp:include>
						</div>
					</c:if>
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="view-oc-building-details.jsp"></jsp:include>
					</div>
				<c:if test="${not empty  occupancyCertificate.receipts}">
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="../application/view-bpa-receipt-details.jsp"></jsp:include>
					</div>
				</c:if>
				<div class="panel panel-primary" data-collapsed="0">
					<jsp:include page="../application/applicationhistory-view.jsp"></jsp:include>
				</div>
			</div>
			<div id="document-info" class="tab-pane fade">
				<div class="panel panel-primary" data-collapsed="0">
					<jsp:include page="view-oc-dcr-documents.jsp"></jsp:include>
					<jsp:include page="view-oc-documents.jsp"></jsp:include>
				</div>
			</div>
			<div id="noc-document-info" class="tab-pane fade">
				<div class="panel panel-primary" data-collapsed="0">
					<jsp:include page="view-oc-noc-documents.jsp"></jsp:include>
				</div>
			</div>
		</div>

		<div class="buttonbottom" align="center">
			<input type="button" name="button2" value="Close"
				class="btn btn-default" onclick="window.close();" />
		</div>
		<input type="hidden" id="feeAmount" value="<spring:message code='msg.validation.feeamount'/>"/>
		<input type="hidden" id="incrFeeamtTopOfsysCalcFee" value="<spring:message code='msg.validation.incrontopof.systemcalc.feeamount'/>"/>
	    <input type="hidden" id="floorareaValidate" value="<spring:message code='msg.validate.floorarea' />"/>
		<input type="hidden" id="carpetareaValidate" value="<spring:message code='msg.validate.carpetarea' />"/>
		<input type="hidden" id="typeOfMsg" value="<spring:message code='msg.vlaidate.typeof' />"/>
		<input type="hidden" id="permissibleAreaForFloor1" value="<spring:message code='msg.vlaidate.permissibleAreaForFloor1' />"/>
		<input type="hidden" id="permissibleAreaForFloor2" value="<spring:message code='msg.vlaidate.permissibleAreaForFloor2' />"/>
		<input type="hidden" id="builtupAndCarpetDetails" value="<spring:message code='msg.tittle.builtup.carpet.details' />"/>
		<input type="hidden" id="blockMsg" value="<spring:message code='msg.tittle.blockmsg' />"/>
		<input type="hidden" id="buildScrutinyNumber" value="<spring:message code='msg.validate.building.scrutiny.number' />"/>
		<input type="hidden" id="buildingPlanApplnForServiceType" value="<spring:message code='msg.validate.buildingplan.applnfor.servicetype' />"/>
		<input type="hidden" id="buildServiceType" value="<spring:message code='msg.validate.building.servicetype' />"/>
		<input type="hidden" id="forBuildScrutinyNumber" value="<spring:message code='msg.validate.forbuilding.scrutiny.number' />"/>
		<input type="hidden" id="floorDetailsNotExtracted" value="<spring:message code='msg.validate.floordetsil.not.extracted' />"/>
		<input type="hidden" id="existingBuildDetailsNotPresent" value="<spring:message code='msg.validate.existing.building.details.notpresent' />"/>		
	</div>
</div>


<script
	src="<cdn:url value='/resources/global/js/egov/inbox.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/application-view.js?rnd=${app_release_no}'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/occupancy-certificate/oc-edcr-helper.js?rnd=${app_release_no}'/>"></script>