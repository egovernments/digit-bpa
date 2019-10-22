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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<div class="row">
	<div class="col-md-12">
		<div class="panel-heading">
			<div class="panel-title text-center no-float">
			</div>
		</div>

		<form:form role="form" action="create" method="post"
			modelAttribute="ownershipTransfer" id="ownershipTransferForm"
			cssClass="form-horizontal form-groups-bordered"
			enctype="multipart/form-data">
			<input type="hidden" name="parent" id="bpaApplicationId" />
			<form:hidden path="" id="onlinePaymentEnable" name="onlinePaymentEnable" value="${onlinePaymentEnable}" />
			<input type="hidden" name="serviceType" id="serviceType"
				class="serviceType" />
			<input type="hidden" name="serviceTypeCode" id="serviceTypeCode" />
			<input type="hidden" name="occupancyCertificate"
				id="occupancyCertificate" value="${occupancyCertificate.id}" />
			<form:hidden path="" id="workFlowAction" name="workFlowAction" />
			<input type="hidden" id="mode" name="mode" value="${mode}" />
			<input type="hidden" id="isEDCRIntegrationRequire"
				value="${isEDCRIntegrationRequire}" />
			<input type="hidden" id="loadingFloorDetailsFromEdcrRequire"
				value="${loadingFloorDetailsFromEdcrRequire}" />
			<ul class="nav nav-tabs" id="settingstab">
				<li class="active"><a data-toggle="tab"
					href="#application-info" data-tabidx=1><spring:message
							code='lbl.ownership.change.details' /></a></li>
				<li><a data-toggle="tab" href="#document-info"
					data-tabidx=2><spring:message code='title.documentdetail' /></a></li>
			</ul>
			
			<div class="tab-content">
				<div id="application-info" class="tab-pane fade in active">
				
				<div class="panel panel-primary" data-collapsed="0">	
				<div class="panel-heading custom_form_panel_heading">
				<div class="panel-title">
					<spring:message code="lbl.basic.info" />
				</div>
			</div>			
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.plan.permission.no" /> <span class="mandatory"></span></label>
						<div class="col-sm-3 add-margin">
							<form:input class="form-control patternvalidation resetValues"
								maxlength="20" id="planPermissionNumber"
								placeholder="Enter plan permission number"
								path="parent.planPermissionNumber"
								value="${ownershipTransfer.parent.planPermissionNumber}"
								required="required" />
							<form:hidden path="parent" id="parent" />
						</div>
						<label class="col-sm-2 control-label text-right"><spring:message
								code="lbl.appln.date" /><span class="mandatory"></span> </label>
						<div class="col-sm-3 add-margin">
							<form:input path="applicationDate"
								class="form-control datepicker" data-date-end-date="0d"
								id="applicationDate" data-inputmask="'mask': 'd/m/y'"
								required="required" disabled="true" />
							<form:errors path="applicationDate"
								cssClass="add-margin error-msg" />
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.build.plan.permission.date" /> </label>
						<div class="col-sm-3 add-margin">
							<input type="text" name="planPermissionDate"
								id="planPermissionDate"
								class="form-control planPermissionDate resetValues"
								value="${ownershipTransfer.parent.planPermissionDate}"
								readonly="readonly">
						</div>
						<label class="col-sm-2 control-label text-right"><spring:message
								code="lbl.service.type" /> </label>
						<div class="col-sm-3 add-margin">
							<input type="text" name="serviceTypeDesc" id="serviceTypeDesc"
								class="form-control resetValues serviceType" readonly="readonly"
								value="${ownershipTransfer.parent.serviceType.description}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.applicant.name" /> </label>
						<div class="col-sm-3 add-margin">
							<input type="text" name="applicantName" id="applicantName"
								class="form-control applicantName resetValues"
								value="${ownershipTransfer.parent.owner.name}" readonly="readonly">
						</div>
						<label class="col-sm-2 control-label text-right"><spring:message
								code="lbl.owner.address" /> </label>
						<div class="col-sm-3 add-margin">
							<input type="text" name="" id="address"
								class="form-control occupancy resetValues"
								value="${ownershipTransfer.parent.owner.address}"
								readonly="readonly">
						</div>
						
					</div>
					<div class="form-group">
					
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.extentin.sqmts" /> </label>
						<div class="col-sm-3 add-margin">
							<input type="text" name="" id="extentInSqmts"
								class="form-control extentInSqmts resetValues decimalfixed"
								value="${ownershipTransfer.parent.siteDetail[0].extentinsqmts}"
								readonly="readonly">
						</div>	
						<label class="col-sm-2 control-label text-right"><spring:message
								code="lbl.occupancy.type" /> </label>
						<div class="col-sm-3 add-margin">
							<input type="text" name="" id="occupancy"
								class="form-control occupancy resetValues"
								value="${ownershipTransfer.parent.occupanciesName}"
								readonly="readonly">
						</div>					
					</div>
					
					<div class="form-group">					
						<label class="col-sm-3 control-label text-left">
				            <spring:message code="lbl.edcr.appln.no"/>
				        </label>
				        <div class="col-sm-2 add-margin view-content">
				            <div class="text-center" id="edcrApplicationNumber"></div>
				        </div>				
					</div>
				</div>
				
				<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="../application/applicantDetailForm.jsp"></jsp:include>
						<div class="panel-heading custom_form_panel_heading">
				<div class="panel-title">
					<spring:message code="lbl.remarks" />
				</div>
			</div>	
								<div class="form-group" >		
		                      <label class="col-sm-2 control-label text-right"><%-- <spring:message code="lbl.remarks" /> --%></label>
							<div class="col-sm-5 add-margin">
								<form:textarea path="remarks" id="remarks"	class="form-control patternvalidation"
			                            data-pattern="alphanumericspecialcharacters" maxlength="256" cols="25" rows="4" />
								<form:errors path="remarks" cssClass="add-margin error-msg" />
							</div>		
						</div>	
					</div></div>
				<div id="document-info" class="tab-pane fade">
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="ownership-transfer-documents.jsp"></jsp:include>
					</div>
				</div>
			</div>

			<div align="center">
				<form:button type="submit" id="buttonSave" class="btn btn-primary"
					value="Save">
					<spring:message code="lbl.save" />
				</form:button>
				<form:button type="submit" id="buttonSubmit" class="btn btn-primary"
					value="Submit">
					<spring:message code="lbl.submit" />
				</form:button>
				<input type="button" name="button2" id="button2" value="Close"
					class="btn btn-default" onclick="window.close();" />
			</div>
		</form:form>
		<!-- Start --- For javascript messages localization purpose following hidden input tags used -->
		<input type="hidden" id="saveAppln" value="<spring:message code='msg.confirm.save.appln'/>"/>
		<input type="hidden" id="submitAppln" value="<spring:message code='msg.confirm.submit.appln'/>"/>
	    <input type="hidden" id="uploadMsg" value="<spring:message code='msg.upload' />" />
        <input type="hidden" id="docNameLength" value="<spring:message code='msg.validate.docname.length' />" />
	    <input type="hidden" id="fileSizeLimit" value="<spring:message code='msg.validate.filesize.limit' />" />
	    <input type="hidden" id="validDocFormat" value="<spring:message code='msg.validate.docformat' />" />	
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
	   	<input type="hidden" id="zone"/>
	   	<input type="hidden" id="revenueWard" />
       <!-- End --- For javascript messages localization purpose following hidden input tags used -->
	</div>
</div>

<link rel="stylesheet"
	href="<c:url value='/resources/global/css/bootstrap/bootstrap-tagsinput.css?rnd=${app_release_no}' context='/egi'/>">
<script
	src="<c:url value='/resources/global/js/bootstrap/bootstrap-tagsinput.min.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script
	src="<c:url value='/resources/global/js/handlebars/handlebars.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script
	src="<cdn:url value='/resources/global/js/egov/inbox.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/bpa-ajax-helper.js?rnd=${app_release_no}'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/documentsuploadvalidation.js?rnd=${app_release_no}'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/ownershipTransfer/citizen-ownership-transfer-helper.js?rnd=${app_release_no}'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/applicant-helper.js?rnd=${app_release_no}'/>"></script>
	
