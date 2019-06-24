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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<form:form role="form"  id="createInspectionForm"
 action="/bpa/inspection/citizen/create/${applicationNumber}"
 method="post" modelAttribute="permitInspectionApplication"
			cssClass="form-horizontal form-groups-bordered"
			enctype="multipart/form-data">
			
<div class="row">
	<div class="col-md-12">	
		<form:hidden path="" id="workFlowAction" name="workFlowAction" />
		<input type="hidden" id="noJAORSAMessage" name="noJAORSAMessage" value="${noJAORSAMessage}" />	
			
      <div class="panel panel-primary" data-collapsed="0">   
      </br>
		   <div class="form-group" >			
		    	       <label class="col-sm-3 control-label text-right"><spring:message code="lbl.plan.permission.no" /><span class="mandatory"></span></label>
		<div class="col-sm-3 add-margin">
			<form:input class="form-control patternvalidation resetValues"
				maxlength="20" id="planPermissionNumber"
				placeholder="Enter plan permission number" path="application.planPermissionNumber"
				value="${permitInspectionApplication.application.planPermissionNumber}" required="required" />
		</div>
				    	        <label class="col-sm-2 control-label text-right"><spring:message code="lbl.insp.bldngconststage" /></label>
				<div class="col-sm-2 add-margin">
		
		<form:select path="inspectionApplication.buildingConstructionStage"
														id="category"
														cssClass="form-control category" required="required">
														<form:option value="">
															<spring:message code="lbl.select" />
														</form:option>
														<form:options items="${constructions}" itemValue="id"
															itemLabel="name" />
													</form:select> <form:errors path="inspectionApplication.buildingConstructionStage"
														cssClass="add-margin error-msg" />	</div>	
		 
		</div>	</div> 
		
		
		<div id="application-info" class="tab-pane fade in active">
		         <div class="panel panel-primary edcrApplnDetails" data-collapsed="0">
						<jsp:include page="edcr-application-details-form.jsp"></jsp:include>
				 </div>
				 	  <div class="panel panel-primary" data-collapsed="0">
							<jsp:include page="view-application-details.jsp"></jsp:include>
						</div> 
		</div>
			
    	     <div class="panel panel-primary docdetails" data-collapsed="0">  
    	     <div class="panel-heading custom_form_panel_heading">				
	                	<div class="panel-title"><spring:message code="lbl.applicant.remarks" /></div>
					</div>	  	    
 				   		 <div class="form-group" >		
		                      <label class="col-sm-2 control-label text-right"><%-- <spring:message code="lbl.remarks" /> --%></label>
							<div class="col-sm-5 add-margin">
								<form:textarea path="inspectionApplication.remarks" id="remarks"	class="form-control patternvalidation"
			                            data-pattern="alphanumericspecialcharacters" maxlength="256" cols="25" rows="4" />
								<form:errors path="inspectionApplication.remarks" cssClass="add-margin error-msg" />
							</div>		
						</div>	
			</div>		
			<div align="center">	
					<form:button type="submit" id="buttonSubmit" class="btn btn-primary" value="submit">
						<spring:message code='lbl.btn.submit' />
					</form:button>
				<input type="button" name="button2" id="button2" value="Close"	class="btn btn-default" onclick="window.close();" />
			</div>		
				<input	type="hidden" id="submitApplication" value="<spring:message code='msg.portal.submit.appln'/>" /> 
			    <input	type="hidden" id="bpaApplicationNumber" value="" /> 
			    
			</div>
</div>
</form:form>

	
<script
	src="<c:url value='/resources/global/js/bootstrap/bootstrap-tagsinput.min.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script
	src="<c:url value='/resources/global/js/handlebars/handlebars.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script
	src="<cdn:url value='/resources/global/js/egov/inbox.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script	
	src="<cdn:url value='/resources/js/app/inspection-edcr-helper.js?rnd=${app_release_no}'/>"></script>
<script	
	src="<cdn:url value='/resources/js/app/citizen-inspection-helper.js?rnd=${app_release_no}'/>"></script>
<link rel="stylesheet"
		href="<c:url value='/resources/css/bpa-style.css?rnd=${app_release_no}'/>">		
	