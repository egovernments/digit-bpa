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

<div class="panel-heading toggle-header custom_form_panel_heading">
	<div class="panel-title">
		<spring:message code="lbl.site.details" />
	</div>
	<div class="history-icon toggle-icon">
		<i class="fa fa-angle-up fa-2x"></i>
	</div>
</div>
<div class="panel-body display-hide">
	
	<jsp:include page="amenityDetails.jsp"></jsp:include>
	
	<div class="form-group">
		<label class="col-sm-3 control-label text-right"><spring:message
				code="lbl.re.survey.no" /><span class="mandatory"></span></label>
		<div class="col-sm-3 add-margin">
			<form:input class="form-control patternvalidation" maxlength="72"
				data-pattern="alphanumericspecialcharacters" data-role="tagsinput" id="reSurveyNumber"
				path="siteDetail[0].reSurveyNumber" required="required" />
				<small class="text-info view-content"><spring:message code="lbl.values.separated.comma"/></small>
			<form:errors path="siteDetail[0].reSurveyNumber"
				cssClass="add-margin error-msg" />
		</div>
		<label class="col-sm-2 control-label text-right"><spring:message
				code="lbl.nature.of.ownership" /><span class="mandatory"></span> </label>
		<div class="col-sm-3 add-margin">
			<form:input class="form-control patternvalidation" maxlength="128"
				data-pattern="alphanumericspecialcharacters" data-role="tagsinput" id="natureofOwnership"
				path="siteDetail[0].natureofOwnership" required="required" />
			<small class="text-info view-content"><spring:message code="lbl.values.separated.comma"/></small>
			<form:errors path="siteDetail[0].natureofOwnership"
				cssClass="add-margin error-msg" />
		</div>
	</div>

	<div class="form-group">
		<%-- <label class="col-sm-3 control-label text-right"><spring:message
				code="lbl.subdiv.no" /><span class="mandatory"></span> </label>
		<div class="col-sm-3 add-margin">
			<form:input class="form-control patternvalidation" maxlength="120"
				data-pattern="alphanumericwithspace" data-role="tagsinput" id="subdivisionNumber"
				path="siteDetail[0].subdivisionNumber" required="required" />
				<small class="error"> (Enter multiple values by comma seperated) </small>
			<form:errors path="siteDetail[0].subdivisionNumber"
				cssClass="add-margin error-msg" />
		</div> --%>
		<%-- <label class="col-sm-3 control-label text-right"><spring:message
				code="lbl.registrar.office" /> </label>
		<div class="col-sm-3 add-margin">
			<form:hidden path="siteDetail[0].registrarVillageId" id="registrarVillageIdHdn"/>
			<form:hidden path="siteDetail[0].registrarOffice"
				id="registrarOfficeObjId"
				value="${bpaApplication.siteDetail[0].registrarOffice.id}" />
			<select class="form-control patternvalidation registrarOffice"
				name="registrarOffice" id="registrarOffice"
				class="form-control ">
				<option value="">Selected</option>
			</select>
			<form:errors path="siteDetail[0].registrarOffice"
				cssClass="add-margin error-msg" />
		</div> --%>
	</div>

	<div class="form-group">
		<label class="col-sm-3 control-label text-right"><spring:message
				code="lbl.nearest.build.no" /><span class="mandatory"></span> </label>
		<div class="col-sm-3 add-margin">
			<form:input class="form-control patternvalidation" maxlength="12"
				required="required" data-pattern="alphanumericspecialcharacters"
				id="nearestbuildingnumber"
				path="siteDetail[0].nearestbuildingnumber" />
			<form:errors path="siteDetail[0].nearestbuildingnumber"
				cssClass="add-margin error-msg" />
		</div>
		<div class="doorNo">
			<label class="col-sm-2 control-label text-right"><spring:message
					code="lbl.addr.dno" /> </label>
			<div class="col-sm-3 add-margin">
				<form:input class="form-control patternvalidation" maxlength="12"
					data-pattern="alphanumericspecialcharacters" id="plotdoornumber"
					path="siteDetail[0].plotdoornumber" />
				<form:errors path="siteDetail[0].plotdoornumber"
					cssClass="add-margin error-msg" />
			</div>
		</div>
	</div>

	<div class="form-group">
		<label class="col-sm-3 control-label text-right"><spring:message
				code="lbl.street.address1" /> </label>
		<div class="col-sm-3 add-margin">
			<form:input class="form-control patternvalidation" maxlength="120"
				data-pattern="alphanumericspecialcharacters" id="streetaddress1"
				path="siteDetail[0].streetaddress1" />
			<form:errors path="siteDetail[0].streetaddress1"
				cssClass="add-margin error-msg" />
		</div>
		<label class="col-sm-2 control-label text-right"><spring:message
				code="lbl.street.address2" /> </label>
		<div class="col-sm-3 add-margin">
			<form:input class="form-control patternvalidation" maxlength="120"
				data-pattern="alphanumericspecialcharacters" id="streetaddress2"
				path="siteDetail[0].streetaddress2" />
			<form:errors path="siteDetail[0].streetaddress2"
				cssClass="add-margin error-msg" />
		</div>
	</div>

	<div class="form-group">
		<label class="col-sm-3 control-label text-right"><spring:message
				code="lbl.city" /><span class="mandatory"></span></label>
		<div class="col-sm-3 add-margin">
			<form:input class="form-control patternvalidation" maxlength="50"
				data-pattern="alphanumericwithspace" id="citytown"
				path="siteDetail[0].citytown" required="required" />
			<form:errors path="siteDetail[0].citytown"
				cssClass="add-margin error-msg" />
		</div>
		<%-- <label class="col-sm-2 control-label text-right"><spring:message
				code="lbl.site.pincode" /><span class="mandatory"></span></label>
		<div class="col-sm-3 add-margin">
			<input type="text" id="postalAddressTypeHead" required="required"
					class="form-control searchpincode patternvalidation typeahead" placeholder="Search Pincode" autocomplete="off" data-pattern="number"
					value="${bpaApplication.siteDetail[0].postalAddress.pincode}" />
			<form:hidden path="siteDetail[0].postalAddress" value="${bpaApplication.siteDetail[0].postalAddress.id}" />
			<form:hidden path="siteDetail[0].postalId" id="postalAddress" value="" />
		</div> --%>
	</div>

	<%-- <div class="form-group">
		<label class="col-sm-3 control-label text-right"><spring:message
				code="lbl.taluk" /> <span class="mandatory"></span> </label>
		<div class="col-sm-3 add-margin">
			<input class="form-control patternvalidation" id="taluk" value="${bpaApplication.siteDetail[0].postalAddress.taluk}" disabled="disabled" />
		</div>
		<label class="col-sm-2 control-label text-right"><spring:message
				code="lbl.post.office" /><span class="mandatory"></span></label>
		<div class="col-sm-3 add-margin">
			<input class="form-control patternvalidation" maxlength="128"
				data-pattern="alphanumericwithspace" id="postOffices"
				value="${bpaApplication.siteDetail[0].postalAddress.postOffice}" />
		</div>
	</div>
	
	<div class="form-group">
		<label class="col-sm-3 control-label text-right"><spring:message
				code="lbl.district" /> <span class="mandatory"></span> </label>
		<div class="col-sm-3 add-margin">
			<input class="form-control patternvalidation" maxlength="128"
				data-pattern="alphanumericwithspace" id="district"
				value="${bpaApplication.siteDetail[0].postalAddress.district}"/>
		</div>
		<label class="col-sm-2 control-label text-right"><spring:message
				code="lbl.state" /> <span class="mandatory"></span> </label>
		<div class="col-sm-3 add-margin">
			<input class="form-control patternvalidation" maxlength="128"
				data-pattern="alphanumericwithspace" id="state"
				value="${bpaApplication.siteDetail[0].postalAddress.state}"/>
			<form:input class="form-control patternvalidation" maxlength="32"
				data-pattern="alphanumericwithspace" id="state"
				path="siteDetail[0].state" required="required" />
			<form:errors path="siteDetail[0].state"
				cssClass="add-margin error-msg" />
		</div>
	</div> --%>

	<%-- <div class="form-group">
		<label class="col-sm-3 control-label text-right"><spring:message
				code="lbl.approved.layout.details" /></label>
		<div class="col-sm-3 add-margin">
			<form:input class="form-control patternvalidation" maxlength="64"
				data-pattern="alphanumericwithspace" id="approvedLayoutDetail"
				path="siteDetail[0].approvedLayoutDetail" />
			<form:errors path="siteDetail[0].approvedLayoutDetail"
				cssClass="add-margin error-msg" />
		</div>
		<label class="col-sm-2 control-label text-right"><spring:message
				code="lbl.crz.zone" /></label>
		<div class="col-sm-3 add-margin">
			<form:input class="form-control patternvalidation " maxlength="10"
				data-pattern="alphanumericwithspace" id="crzZone"
				path="buildingDetail[0].crzZone" />
			<form:errors path="buildingDetail[0].crzZone"
				cssClass="add-margin error-msg" />
		</div>
	</div> --%>

<div class="form-group">
		<label class="col-sm-3 control-label text-right"><spring:message
				code="lbl.town.plan.zone" /></label>
		<div class="col-sm-3 add-margin">
			<form:select name="schemes" id="schemes"
				path="siteDetail[0].scheme" cssClass="form-control"
				cssErrorClass="form-control error">
				<form:option value=""><spring:message code="lbl.select" /></form:option>
				<form:options items="${schemesList}" itemValue="id" itemLabel="description" />
			</form:select>
		</div>
		<label class="col-sm-2 control-label text-right landUsage"><spring:message
				code="lbl.proposed.land.use" /><span></span> </label>
		<div class="col-sm-3 add-margin">
			<select name="landUsage"  id="landUsage" class="form-control ">
						<option value=""> Selected </option>
			</select>
			<form:hidden path="siteDetail[0].landUsage" id="landUsageObjectId" value="${bpaApplication.siteDetail[0].landUsage.id}" />
			<form:hidden path="siteDetail[0].landUsageId" id="landUsageId" value="" /> 
		</div>
	</div>
    <div class="form-group">
			<label class="col-sm-3 control-label text-right"><spring:message
				code="lbl.construction.cost" /><span class="mandatory"></span></label>
			<div class="col-sm-3 add-margin">
				<form:input class="form-control patternvalidation" maxlength="12"
					data-pattern="number" id="constructionCost" required="required" 
					path="constructionCost" />
				<form:errors path="constructionCost"
					cssClass="add-margin error-msg" />
			</div>
	
		<label class="col-sm-2 control-label text-right"><spring:message
				code="lbl.infrastructure.cost" /></label>
			<div class="col-sm-3 add-margin">
				<form:input class="form-control patternvalidation" maxlength="12"
					data-pattern="number" id="infrastructureCost"
					path="infrastructureCost" />
				<form:errors path="infrastructureCost"
					cssClass="add-margin error-msg" />
			</div>
	</div>
	<div class="form-group">
		
		<label class="col-sm-3 control-label text-right"><spring:message
				code="lbl.government.type" /></label>
		<div class="col-sm-3 add-margin">
			<%-- <form:radiobuttons items="${governmentTypeList}" path="governmentType"/> --%>
			<c:forEach items="${governmentTypeList}" var="govType">
			<div class="radio">
				<c:choose>
					<c:when test="${govType eq 'NOT_APPLICABLE'}">
						<label> <input type="radio" value="${govType}" checked="checked" class="governmentType" name="governmentType" /> ${govType.governmentTypeVal}</label>
					</c:when>
					<c:otherwise>
						<label><input type="radio" value="${govType}" class="governmentType" name="governmentType" <c:if test="${govType eq bpaApplication.governmentType}"> checked="checked" </c:if> />${govType.governmentTypeVal}</label>
					</c:otherwise>
				</c:choose>
			</div>
			</c:forEach>
			<form:errors path="governmentType" cssClass="add-margin error-msg" />
		</div>
		<div id="isEconomicallyWeakerSec">
			<label class="col-sm-2 control-label text-right"><spring:message
					code="lbl.is.econ.weaker.sec" /></label>
			<div class="col-sm-3 add-margin">
				<form:checkbox path="isEconomicallyWeakerSection"
					id="isEconomicallyWeakerSection" value="false" />
			</div>
		</div>
	</div>

	<%--<div class="form-group">
		<label class="col-sm-3 control-label text-right"><spring:message
				code="lbl.if.regularized" /> </label>
		<div class="col-sm-3 add-margin">
			<form:checkbox id="isappForRegularization"
						   path="siteDetail[0].isappForRegularization"
						   value="siteDetail[0].isappForRegularization" />
			<form:errors path="siteDetail[0].isappForRegularization" />
		</div>
	</div>
	<div class="form-group" id="constDiv">
		<div class="form-group">
			<label class="col-sm-3 control-label text-right constStages"><spring:message
					code="lbl.cons.stages" /><span></span></label>
			<div class="col-sm-3 add-margin">
				<form:select path="siteDetail[0].constStages"
							 data-first-option="false" id="constStages" cssClass="form-control">
					<form:option value="">
						<spring:message code="lbl.select" />
					</form:option>
					<form:options items="${constStages}" itemValue="id"
								  itemLabel="code" />
				</form:select>
				<form:errors path="siteDetail[0].constStages"
							 cssClass="add-margin error-msg" />
			</div>
			<div id="inprogress">
				<label class="col-sm-2 control-label text-right stateOfConstruction"><spring:message
						code="lbl.if.cons.not.cmplted" /><span></span></label>
				<div class="col-sm-3 add-margin">
					<form:input class="form-control patternvalidation"
								data-pattern="alphanumericwithspace" maxlength="128"
								id="stateOfConstruction" path="siteDetail[0].stateOfConstruction" />
					<form:errors path="siteDetail[0].stateOfConstruction"
								 cssClass="add-margin error-msg" />
				</div>
			</div>
		</div>
		<div class="form-group">
			<div class="workCommencementDate1">
				<label
						class="col-sm-3 control-label text-right workCommencementDate"><spring:message
						code="lbl.work.commence.date" /><span></span></label>
				<div class="col-sm-3 add-margin">
					<form:input path="siteDetail[0].workCommencementDate"
								class="form-control datepicker" data-date-end-date="0d"
								id="workCommencementDate" data-inputmask="'mask': 'd/m/y'" />
					<form:errors path="siteDetail[0].workCommencementDate"
								 cssClass="add-margin error-msg" />
				</div>
			</div>
			<div class="workCompletionDate1">
				<label class="col-sm-2 control-label text-right workCompletionDate"><spring:message
						code="lbl.work.completion.date" /><span></span></label>
				<div class="col-sm-3 add-margin">
					<form:input path="siteDetail[0].workCompletionDate"
								class="form-control datepicker" data-date-end-date="0d"
								id="workCompletionDate" data-inputmask="'mask': 'd/m/y'" />
					<form:errors path="siteDetail[0].workCompletionDate"
								 cssClass="add-margin error-msg" />
				</div>
			</div>
		</div>
	</div>--%>

	<%-- <div class="form-group">
	<label class="col-sm-3 control-label text-right"><spring:message code="lbl.set.back.front" /></label>
	<div class="col-sm-3 add-margin">
		<form:input class="form-control patternvalidation"
			data-pattern="decimalvalue" id="setBackFront" maxlength="10"
			path="siteDetail[0].setBackFront" />
		<form:errors path="siteDetail[0].setBackFront"
			cssClass="add-margin error-msg" />
	</div>
	<label class="col-sm-2 control-label text-right"><spring:message code="lbl.set.back.rear" /></label>
	<div class="col-sm-3 add-margin">
		<form:input class="form-control patternvalidation"
			data-pattern="decimalvalue" id="setBackRear" maxlength="10"
			path="siteDetail[0].setBackRear" />
		<form:errors path="siteDetail[0].setBackRear"
			cssClass="add-margin error-msg" />
	</div>
</div> --%>

	<%-- <div class="form-group">
	<label class="col-sm-3 control-label text-right"><spring:message code="lbl.set.back.side1" /></label>
		<div class="col-sm-3 add-margin">
			<form:input class="form-control patternvalidation" maxlength="10"
				data-pattern="decimalvalue" id="setBackSide1"
				path="siteDetail[0].setBackSide1" />
			<form:errors path="siteDetail[0].setBackSide1"
				cssClass="add-margin error-msg" />
	</div>
	<label class="col-sm-2 control-label text-right"><spring:message code="lbl.set.back.side2" /></label>
	<div class="col-sm-3 add-margin">
		<form:input class="form-control patternvalidation"
			data-pattern="decimalvalue" id="setBackSide2" maxlength="10"
			path="siteDetail[0].setBackSide2" />
		<form:errors path="siteDetail[0].setBackSide2"
			cssClass="add-margin error-msg" />
	</div>
</div>

<div class="form-group" id="statusdiv">
		<label class="col-sm-3 control-label text-right"><spring:message code="lbl.encroch.issue.present" /></label>
		<div class="col-sm-3 add-margin">
			<form:checkbox id="encroachmentIssuesPresent"
				path="siteDetail[0].encroachmentIssuesPresent"
				value="encroachmentIssuesPresent" />
			<form:errors path="siteDetail[0].encroachmentIssuesPresent" />
		</div>
		<label class="col-sm-2 control-label text-right"><spring:message code="lbl.encroach.remarks" /></label>
	<div class="col-sm-3 add-margin">
		<form:textarea class="form-control patternvalidation"
			data-pattern="alphanumericwithspace" maxlength="128"
			id="encroachmentRemarks" path="siteDetail[0].encroachmentRemarks" />
		<form:errors path="siteDetail[0].encroachmentRemarks"
			cssClass="add-margin error-msg" />
	</div>
</div> --%>

</div>