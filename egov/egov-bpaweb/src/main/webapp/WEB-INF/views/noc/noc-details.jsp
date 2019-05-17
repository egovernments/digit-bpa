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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<form:form role="form" action="/bpa/nocapplication/updateNoc/${noc.nocType}~${noc.bpaApplication.applicationNumber}" method="POST" modelAttribute="noc"
			id="editNocApplicationForm"
			cssClass="form-horizontal form-groups-bordered"
			enctype="multipart/form-data">
<div class="row">
	<div class="col-md-12">
	
		
			<form:hidden path="" id="workFlowAction" name="workFlowAction" />

			<div id="appliccation-info" class="tab-pane fade in active">
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="permit-application-details.jsp"></jsp:include>
					</div>
					<div class="panel panel-primary edcrApplnDetails" data-collapsed="0">
						<jsp:include page="../application/edcr-application-details-form.jsp"></jsp:include>
					</div>
				
				    <div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="noc-sitedetails.jsp"></jsp:include>
					</div>
					<div class="panel panel-primary buildingdetails" data-collapsed="0">
						<jsp:include page="noc-building-details.jsp"></jsp:include>
					</div>
					
					
    	    </div>
    	    					<div class="panel panel-primary docdetails" data-collapsed="0">
    	    
    	    <div class="panel-heading custom_form_panel_heading">
					
	<div class="panel-title">
		Document Upload
	</div>
</div>
					
					
                      <label class="col-sm-3 control-label text-right">Upload file</span></label>
		      <div class="form-group" id="documentupload">
		      
			<div class="fileSection col-md77-4">
				<input type="file" required="required" name="files" id="myfile"
					style="display: none;">
				<p class="hide">
					<i class="fa fa-file-text"></i>&nbsp;&nbsp;<span id="fileName"></span>
				</p>
				<button type="button" id="fileTrigger"
					class="btn btn-primary fullWidth">
					<span class="glyphicon glyphicon glyphicon-cloud-upload"></span>
					&nbsp;
					Choose a file:
				</button>
				<div class="row hide fileActions">
					<div class="col-md-6">
						<button type="button" id="fileDelete"
							class="btn btn-danger btn-sm">
							<i class="fa fa-trash-o"></i> &nbsp;
                         Delete						</button>
					</div>
				</div>
			</div>
			<%-- <small class="text-info view-content"><spring:message
					code="lbl.dcr.upload.help" /></small> --%>
		</div>
							
	</div>		
	<div align="center">
		<c:if test="${noc.status.code eq 'NOC_INITIATED'}">
	
			<form:button type="submit" id="buttonApprove" class="btn btn-primary" value="submit">
				<spring:message code='lbl.approve' />
			</form:button>
			<form:button type="submit" id="buttonReject" class="btn btn-primary" value="submit">
				<spring:message code='lbl.reject' />
			</form:button>
		</c:if>
		<input type="button" name="button2" id="button2" value="Close"
			class="btn btn-default" onclick="window.close();" />
	</div>		
	<input
			type="hidden" id="submitApplication"
			value="<spring:message code='msg.confirm.submit.appln'/>" /> 
    
 </div>
</div>
    </form:form>

	
<link rel="stylesheet"
	href="<c:url value='/resources/global/css/bootstrap/bootstrap-tagsinput.css?rnd=${app_release_no}' context='/egi'/>">
<script
	src="<c:url value='/resources/global/js/bootstrap/bootstrap-tagsinput.min.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script
	src="<c:url value='/resources/global/js/handlebars/handlebars.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script
	src="<cdn:url value='/resources/global/js/egov/inbox.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/application-edit.js?rnd=${app_release_no}'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/bpa-ajax-helper.js?rnd=${app_release_no}'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/buildingarea-details.js?rnd=${app_release_no}'/>"></script>
<script	
	src="<cdn:url value='/resources/js/app/noc/noc-edcr-helper.js?rnd=${app_release_no}'/>"></script>
	<Script	src="<cdn:url value='/resources/js/app/documentsuploadvalidation.js?rnd=${app_release_no}'/>"></script>
	
	