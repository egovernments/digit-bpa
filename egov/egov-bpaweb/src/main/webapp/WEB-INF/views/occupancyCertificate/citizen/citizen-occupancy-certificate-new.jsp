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
<div class="row">
	<div class="col-md-12">
		<div class="text-left error-msg col-sm-12" style="font-size: 14px;">
			<span class="applicantname"> <spring:message
					code="lbl.applicant.name" /> : <span class="applicantName"></span>
			</span>
		</div>

		<form:form role="form" action="submit" method="post"
				   modelAttribute="occupancyCertificate" id="occupancyCertificateForm"
				   cssClass="form-horizontal form-groups-bordered"
				   enctype="multipart/form-data">
			<input type="hidden"  id="citizenOrBusinessUser" name="citizenOrBusinessUser" value="${citizenOrBusinessUser}"/>
			<input type="hidden"  id="validateCitizenAcceptance" name="validateCitizenAcceptance" value="${validateCitizenAcceptance}"/>
			<input type="hidden"  id="citizenDisclaimerAccepted" name="citizenDisclaimerAccepted" value="${citizenDisclaimerAccepted}"/>
			<input type="hidden"  id="isCitizen" name="isCitizen" value="${isCitizen}"/>
			<input type="hidden"  id="cityName" name="cityName" value="${cityName}"/>
			<input type="hidden" name="parent" id="bpaApplicationId" />
			<input type="hidden" name="occupancyCertificate" id="occupancyCertificate" value="${occupancyCertificate.id}" />
			<form:hidden path="" id="workFlowAction" name="workFlowAction" />
			<ul class="nav nav-tabs" id="settingstab">
				<li class="active"><a data-toggle="tab"
									  href="#application-info" data-tabidx=0><spring:message
						code='lbl.appln.details' /></a></li>
				<li><a data-toggle="tab" href="#document-info" data-tabidx=1><spring:message
						code='title.documentdetail' /></a></li>
				<li><a data-toggle="tab" href="#noc-document-info" data-tabidx=2><spring:message
						code='lbl.noc.doc.details' /></a></li>
			</ul>
			<div class="tab-content">
				<div id="application-info" class="tab-pane fade in active">
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="../oc-application-details.jsp"></jsp:include>
					</div>
				</div>
				<div id="document-info" class="tab-pane fade">
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="../oc-general-documents.jsp"></jsp:include>
					</div>
				</div>
				<div id="noc-document-info" class="tab-pane fade">
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="../oc-noc-documents.jsp"></jsp:include>
					</div>
				</div>
			</div>

			<div align="center">
				<form:button type="submit" id="ocSave" class="btn btn-primary"
							 value="Save"><spring:message code="lbl.save"/> </form:button>
				<form:button type="submit" id="ocSubmit" class="btn btn-primary"
							 value="Submit"><spring:message code="lbl.submit"/></form:button>
				<input type="button" name="button2" id="button2" value="Close"
					   class="btn btn-default" onclick="window.close();" />
			</div>
		</form:form>
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
		src="<cdn:url value='/resources/js/app/edcr-helper.js?rnd=${app_release_no}'/>"></script>
<script
		src="<cdn:url value='/resources/js/app/occupancy-certificate/occupancy-certificate-new.js?rnd=${app_release_no}'/>"></script>
