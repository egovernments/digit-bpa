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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<div class="row">
	<div class="col-md-12">
		<form:form role="form" action="/bpa/inspection/captureinspection/${bpaApplication.applicationNumber}" method="post"
			modelAttribute="inConstructionInspection" id="inConstInspectionform"
			cssClass="form-horizontal form-groups-bordered"
			enctype="multipart/form-data">

			<input type="hidden" id="id" name="id" value="${inConstructionInspection.id}" />
			<input type="hidden" id="inConstructionInspection" name="inConstructionInspection"
				value="${inConstructionInspection.id}" />			
			<input type="hidden" name="planPermissionNumber" id="planPermissionNumber"
				value="${bpaApplication.planPermissionNumber}">
			<input type="hidden" name="inspectionDate" id="inspectionDate"
				value="${inspectionDate}">

			<ul class="nav nav-tabs" id="settingstab">
				<li class="active"><a data-toggle="tab" href="#inspn-details"
					data-tabidx=0><spring:message code='lbl.inspn.details' /></a></li>
				<c:if
					test="${not empty planScrutinyCheckList || not empty planScrutinyChecklistForDrawing}">
					<li><a data-toggle="tab" href="#ocplan-scrutiny-chklist"
						data-tabidx=1><spring:message code='lbl.plan.scrutiny' /></a></li>
				</c:if>
				<c:if
					test="${not empty inConstructionInspection.inspection.getInspectionSupportDocs()}">
					<li><a data-toggle="tab" href="#site-images" data-tabidx=2><spring:message
								code='title.site.image' /></a></li>
				</c:if>
			</ul>
			<div class="tab-content">
				<div id="inspn-details" class="tab-pane fade in active">
					
					<div class="panel panel-primary" data-collapsed="0">
						
						<div class="panel-body custom-form ">
							

						
							<jsp:include page="viewapplication-details.jsp"></jsp:include>	
											
							<jsp:include page="inconst-inspection-detail-form.jsp"></jsp:include>
							
							<div class="panel-heading toggle-header custom_form_panel_heading">
							
							<div class="panel-title">
		<spring:message code="lbl.inspn.details" />
	</div></div>
							<div class="form-group">
					
					
					<label class="col-sm-3 control-label text-right"><spring:message
							code="lbl.insp.bldngconststage" /></label>
					<div class="col-sm-3 add-margin">

						<form:select
							path="inspectionApplication.buildingConstructionStage"
							id="category" cssClass="form-control category" >
							<%-- required="required" --%>
							<form:option value="">
								<spring:message code="lbl.select" />
							</form:option>
							<form:options items="${constructions}" itemValue="id"
								itemLabel="name" />
						</form:select>
						<form:errors
							path="inspectionApplication.buildingConstructionStage"
							cssClass="add-margin error-msg" />
					</div></div>
												<div class="form-group">
					
					<label class="col-sm-3 control-label text-right"><spring:message
							code="lbl.applicant.remarks" /><span class="mandatory"></span></label>
					<div class="col-sm-5 add-margin">
						<form:textarea class="form-control patternvalidation resetValues"
							maxlength="20" id="remarks"
							path="inspectionApplication.remarks"
							value="${inConstructionInspection.inspectionApplication.remarks}"
							required="required" />
						
					</div></div>
						</div>
					</div>
				</div>
				<div id="ocplan-scrutiny-chklist" class="tab-pane fade">
					<c:if test="${not empty planScrutinyCheckList}">
						<div class="panel panel-primary" data-collapsed="0">
							<div class="panel-body custom-form ">
								<jsp:include page="inconst-plan-scrutiny-checklist-rule.jsp"></jsp:include>
							</div>
						</div>
					</c:if>
					<c:if test="${not empty planScrutinyChecklistForDrawing}">
						<div class="panel panel-primary" data-collapsed="0">
							<div class="panel-body custom-form ">
								<jsp:include page="inconst-plan-scrutiny-checklist-drawing.jsp"></jsp:include>
							</div>
						</div>
					</c:if>
				</div>
				<c:if
					test="${not empty inConstructionInspection.inspection.getInspectionSupportDocs()}">
					<div id="site-images" class="tab-pane fade">
						<div class="panel panel-primary" data-collapsed="0">
							<div class="panel-body custom-form ">
								<c:set var="inspectionSupportDocs"
									value="${inConstructionInspection.inspection.getInspectionSupportDocs()}"
									scope="request"></c:set>
								<jsp:include
									page="../application/upload-inspection-documents.jsp"></jsp:include>
							</div>
						</div>
					</div>
				</c:if>
			</div>
			<div align="center">
				<form:button type="submit" id="buttonSubmit" class="btn btn-primary"
					value="createinspectiondetails">
					<spring:message code="lbl.btn.submit" />
				</form:button>
				<input type="button" name="button2" id="button2" value="Close"
					class="btn btn-default" onclick="window.close();" />
			</div>
		</form:form>
	</div>
</div>

<script
		src="<cdn:url value='/resources/js/app/inconst-helper.js?rnd=${app_release_no}'/> "></script>
