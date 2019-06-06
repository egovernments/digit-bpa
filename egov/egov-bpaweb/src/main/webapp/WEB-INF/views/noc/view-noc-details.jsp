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


<form:form role="form" modelAttribute="permitNocApplication" id="editNocApplicationForm"
 action="/bpa/nocapplication/updateNoc/${permitNocApplication.bpaNocApplication.nocType}~${permitNocApplication.bpaApplication.applicationNumber}"
 method="post" 
			cssClass="form-horizontal form-groups-bordered"
			enctype="multipart/form-data">
			<input type="hidden" name="permitNoc" value="${permitNocApplication.id}">
			<input type="hidden" name="id" value="${permitNocApplication.id}">
<div class="row">
	<div class="col-md-12">		
		<form:hidden path="" id="workFlowAction" name="workFlowAction" />
			<ul class="nav nav-tabs" id="settingstab">
				<li class="active"><a data-toggle="tab" href="#application-info"
									  data-tabidx=0><spring:message code='lbl.appln.details' /></a></li>
				<li><a data-toggle="tab" href="#document-info" data-tabidx=1><spring:message
						code='title.documentdetail' /></a></li>
			</ul>
			
			<div class="tab-content">
			
			<div id="document-info" class="tab-pane fade">
					<div class="panel panel-primary dcrDocuments" data-collapsed="0">
							<jsp:include page="noc-dcr-documentdetails.jsp"></jsp:include>
					</div>
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="noc-bpaDocumentdetails.jsp"></jsp:include>
					</div>
			</div>
			<div id="application-info" class="tab-pane fade in active">
					<div class="panel panel-primary docdetails" data-collapsed="0">  
		    	       <div class="panel-heading custom_form_panel_heading">				
			                <div class="panel-title"><spring:message code="lbl.noc.details"/></div>
							</div>	  	
						<div class="form-group">
							    
							<div class="col-sm-3 control-label text-right">
								<spring:message code="lbl.application.number" />
							</div>
							<div class="col-sm-3 add-margin view-content text-justify">
								<c:out value="${permitNocApplication.bpaNocApplication.nocApplicationNumber}"
									default="N/A"></c:out>
							</div>
							<div class="col-sm-3 control-label text-right">
								<spring:message code="lbl.noc.app.date" />
							</div>
							<fmt:formatDate value="${permitNocApplication.bpaNocApplication.createdDate}"
								  pattern="dd/MM/yyyy" var="applicationDate" />	
							<div class="col-sm-3 add-margin view-content text-justify">
								<c:out value="${applicationDate}"
									default="N/A"></c:out>
							</div>
						</div>
						<div class="form-group">
						
							<div class="col-sm-3 control-label text-right">
								<spring:message code="lbl.noc.status" />
							</div>
							<div class="col-sm-3 add-margin view-content text-justify">
								<c:out value="${permitNocApplication.bpaNocApplication.status.code}"
									default="N/A"></c:out>
							</div>							
						</div>	
					</div>
					
					
					<div id="nocdoc-info" class="tab-pane fade in active">
			 		<div class="panel panel-primary" data-collapsed="0">
			 
			 			<div class="panel-heading custom_form_panel_heading">				
	                		<div class="panel-title"><spring:message code="lbl.noc.existing.doc" /></div>
						</div>		
							<c:forEach
								var="bpanoc" items="${nocDocs.nocDocument.nocSupportDocs}" varStatus="loop">
								<c:if test="${bpanoc.fileStoreId ne null}">&nbsp;&nbsp;&nbsp;&nbsp;
									<a target="_blank" href="/bpa/application/downloadfile/${bpanoc.fileStoreId}"
							  	 	data-gallery>${loop.index +1} - ${bpanoc.fileName} </a>
								<c:if test="${!loop.last}">,</c:if>&nbsp;
								</c:if>
							</c:forEach>	
					</div>			
    	         </div>
    	         
    	         <div class="panel panel-primary docdetails" data-collapsed="0">    	    
    	            <div class="panel-heading custom_form_panel_heading">				
	                	<div class="panel-title">${permitNocApplication.bpaNocApplication.nocType} <spring:message code="lbl.noc.dept.doc" /></div>
					</div>									
               	
							<c:forEach
								var="bpanoc" items="${permitNocApplication.bpaNocApplication.nocSupportDocs}" varStatus="loop">
								<c:if test="${bpanoc.fileStoreId ne null}">&nbsp;&nbsp;&nbsp;&nbsp;
									<a target="_blank" href="/bpa/application/downloadfile/${bpanoc.fileStoreId}"
							  	 	data-gallery>${loop.index +1} - ${bpanoc.fileName} </a>
								<c:if test="${!loop.last}">,</c:if>&nbsp;
								</c:if>
							</c:forEach>	
					</div>
			 
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
	                	<div class="panel-title">Comments</div>
					</div>&nbsp;&nbsp;&nbsp;&nbsp;	  	    
		                      <c:out value="${permitNocApplication.bpaNocApplication.remarks}"
									default="N/A"></c:out>		
			</div>		
			<div align="center">
				
				<input type="button" name="button2" id="button2" value="Close"	class="btn btn-default" onclick="window.close();" />
			               
			</div>
</div></div>
</form:form>

	
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
	<script
		src="<cdn:url value='/resources/js/app/document-upload-helper.js?rnd=${app_release_no}'/>"></script>
	<link rel="stylesheet"
		href="<c:url value='/resources/css/bpa-style.css?rnd=${app_release_no}'/>">
	