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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>


<div class="panel-heading custom_form_panel_heading">
	<div class="panel-title">
		<spring:message code="lbl.basic.info" />
	</div>
</div>
<div class="panel-body">
	<div id="appDet">
		<c:choose>
			<c:when test="${isEDCRIntegrationRequire eq true}">
				<div class="form-group">
					<label class="col-sm-3 control-label text-right edcrApplnDetails"><spring:message
							code="lbl.edcr.number" /> <span class="mandatory"></span> </label>
					<div class="col-sm-3 add-margin">
						<form:input
							class="form-control patternvalidation edcrApplnDetails"
							maxlength="20" id="eDcrNumber" path="eDcrNumber"
							required="required" />
						<form:errors path="eDcrNumber" cssClass="add-margin error-msg" />
					</div>
					<label class="col-sm-2 control-label text-right"><spring:message
							code="lbl.occupancy" /><span class="mandatory"></span></label>
					<div class="col-sm-3 add-margin">
						<select name="permitOccupanciesTemp" multiple
							id="occupancyapplnlevel"
							class="form-control occupancies tick-indicator">
							<c:forEach items="${occupancyList}" var="ocpncy">
								<option value="${ocpncy.id}" title="${ocpncy.name}"
									<c:if test="${fn:contains(bpaApplication.permitOccupancies, ocpncy)}"> Selected </c:if>>${ocpncy.name}</option>
							</c:forEach>
						</select>
						<form:errors path="permitOccupanciesTemp"
							cssClass="add-margin error-msg" />
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="form-group">
					<label class="col-sm-3 control-label text-right"><spring:message
							code="lbl.occupancy" /><span class="mandatory"></span></label>
					<div class="col-sm-3 add-margin">
						<select name="permitOccupanciesTemp" multiple
							id="occupancyapplnlevel"
							class="form-control occupancies tick-indicator">
							<c:forEach items="${occupancyList}" var="ocpncy">
								<option value="${ocpncy.id}" title="${ocpncy.name}"
									<c:if test="${fn:contains(bpaApplication.permitOccupancies, ocpncy)}"> Selected </c:if>>${ocpncy.name}</option>
							</c:forEach>
						</select>
						<form:errors path="permitOccupanciesTemp"
							cssClass="add-margin error-msg" />
					</div>
				</div>
			</c:otherwise>
		</c:choose>

		<c:if test="${isOneDayPermitApplicationIntegrationRequired eq true}">
			<div class="form-group" id="oneDayPermitSec">
				<label class="col-sm-3 control-label text-right"><spring:message
						code="lbl.is.one.permit" /></label>
				<div class="col-sm-3 add-margin">
					<form:checkbox path="isOneDayPermitApplication"
						id="isOneDayPermitApplication" name="isOneDayPermitApplication"
						value="false" />
				</div>
				<div id="oneDayPermitTypeOfLandSec">
					<label class="col-sm-2 control-label text-right"><spring:message
							code="lbl.type.land" /> <span class="mandatory"></span></label>
					<div class="col-sm-3 add-margin">
						<form:select path="typeOfLand" id="typeOfLand"
							cssClass="form-control" cssErrorClass="form-control error">
							<form:option value="">
								<spring:message code="lbl.select" />
							</form:option>
							<form:options items="${oneDayPermitLandTypeList}" />
						</form:select>
						<form:errors path="typeOfLand" cssClass="add-margin error-msg" />
					</div>
				</div>
			</div>
		</c:if>
		
		
		<div class="form-group" id="applicationTypeSec">
				<label class="col-sm-3 control-label text-right"><spring:message
						code="lbl.applctn.type" /><span class="mandatory"></span></label>
				<div class="col-sm-3 add-margin">
					<form:select path="applicationType" id="applicationType"
								cssClass="form-control" cssErrorClass="form-control error"
								required="required">
						<form:option value=""><spring:message code="lbl.select" /></form:option>
						<form:options items="${appTypes}" itemValue="id"
						itemLabel="name" />
					</form:select> 
					<form:errors path="applicationType"	cssClass="add-margin error-msg" />
				</div>
		</div>
			
			
		
		<div class="form-group appForRegularization">
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
						data-first-option="false" id="constStages"
						cssClass="form-control resetRegularization">
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
					<label
						class="col-sm-2 control-label text-right stateOfConstruction"><spring:message
							code="lbl.if.cons.not.cmplted" /><span></span></label>
					<div class="col-sm-3 add-margin">
						<form:input
							class="form-control patternvalidation resetRegularization"
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
							class="form-control datepicker resetRegularization"
							data-date-end-date="0d" id="workCommencementDate"
							data-inputmask="'mask': 'd/m/y'" />
						<form:errors path="siteDetail[0].workCommencementDate"
							cssClass="add-margin error-msg" />
					</div>
				</div>
				<div class="workCompletionDate1">
					<label class="col-sm-2 control-label text-right workCompletionDate"><spring:message
							code="lbl.work.completion.date" /><span></span></label>
					<div class="col-sm-3 add-margin">
						<form:input path="siteDetail[0].workCompletionDate"
							class="form-control datepicker resetRegularization"
							data-date-end-date="0d" id="workCompletionDate"
							data-inputmask="'mask': 'd/m/y'" />
						<form:errors path="siteDetail[0].workCompletionDate"
							cssClass="add-margin error-msg" />
					</div>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label text-right"><spring:message
					code="lbl.service.type" /> <span class="mandatory"></span> </label>
			<div class="col-sm-3 add-margin">
				<form:select path="serviceType" data-first-option="false"
					id="serviceType" cssClass="form-control serviceType"
					required="required">
					<form:option value="">
						<spring:message code="lbl.select" />
					</form:option>
					<form:options items="${serviceTypeList}" itemValue="id"
						itemLabel="description" />
				</form:select>
				<form:errors path="serviceType" cssClass="add-margin error-msg" />
			</div>
			<%-- <label class="col-sm-2 control-label amenityHideShow text-right">
				<spring:message code="lbl.amenity.type" />
			</label>
			<div class="col-sm-3 add-margin">
				<select name="applicationAmenityTemp" multiple
					id="applicationAmenity"
					class="form-control applicationAmenity tick-indicator amenityHideShow">
					<c:forEach items="${amenityTypeList}" var="amenity">
						<option value="${amenity.id}" title="${amenity.description}"
							<c:if test="${fn:contains(bpaApplication.applicationAmenity, amenity)}"> Selected </c:if>>${amenity.description}</option>
					</c:forEach>
				</select>
				<form:errors path="applicationAmenityTemp"
					cssClass="add-margin error-msg" />
			</div> --%>

		</div>

		<c:if test="${mode != 'new'}">
			<div class="form-group">
				<label class="col-sm-3 control-label text-right"><spring:message
						code="lbl.application.number" /> <span class="mandatory"></span>
				</label>
				<div class="col-sm-3 add-margin">
					<form:input class="form-control patternvalidation" maxlength="50"
						id="applicationNumber" path="applicationNumber" disabled="true" />
					<form:errors path="applicationNumber"
						cssClass="add-margin error-msg" />
				</div>
			</div>
		</c:if>

		<div class="form-group">

			<label class="col-sm-3 control-label text-right"> <spring:message
					code="lbl.appln.date" /> <span class="mandatory"></span>
			</label>
			<div class="col-sm-3 add-margin">
				<form:input path="applicationDate" class="form-control datepicker"
					data-date-end-date="0d" id="applicationDate"
					data-inputmask="'mask': 'd/m/y'" required="required" />
				<form:errors path="applicationDate" cssClass="add-margin error-msg" />
			</div>

			<label class="col-sm-2 control-label text-right"><spring:message
					code="lbl.admission.fees" /> </label>
			<div class="col-sm-3 add-margin">
				<form:hidden class="form-control patternvalidation" maxlength="50"
					name="admissionfeeAmount" id="admissionfeeAmount"
					path="admissionfeeAmount" />
				<input type="text" class="form-control patternvalidation"
					id="admissionfee" value="${admissionFee}" disabled="disabled" />
				<form:errors path="admissionfeeAmount"
					cssClass="add-margin error-msg" />
			</div>
		</div>
	</div>
	<!-- Provide Stakeholder dropdown for citizen login and officials -->
	<c:if
		test="${(!citizenOrBusinessUser && !workFlowByNonEmp) || isCitizen}">
		<div class="form-group">
			<label class="col-sm-3 control-label text-right"><spring:message
					code="lbl.stakeholder.type" /> <span class="mandatory"></span></label>
			<div class="col-sm-3 add-margin">
				<form:select path="stakeHolder[0].stakeHolder.stakeHolderType"
					id="stakeHolderType"
					value="${bpaApplication.stakeHolder[0].stakeHolder.stakeHolderType}"
					required="required" cssClass="form-control">
					<form:option value="">
						<spring:message code="lbl.select" />
					</form:option>
					<form:options items="${stakeHolderTypeList}" />
				</form:select>
				<form:errors path="stakeHolder[0].stakeHolder.stakeHolderType"
					cssClass="add-margin error-msg" />
			</div>
			<label class="col-sm-2 control-label text-right"><spring:message
					code="lbl.stakeholder.name" /><span class="mandatory"></span> </label>
			<div class="col-sm-3 add-margin">
				<form:hidden path="stakeHolder[0].application" />
				<input type="text" id="stakeHolderTypeHead"
					placeholder="Search Building Licensee.."
					class="form-control typeahead" autocomplete="off"
					required="required"
					value="${bpaApplication.stakeHolder[0].stakeHolder.name}" />
				<form:hidden path="stakeHolder[0].stakeHolder" id="stakeHolderName"
					value="${bpaApplication.stakeHolder[0].stakeHolder.id}" />
				<form:errors path="stakeHolder[0].stakeHolder.name"
					cssClass="add-margin error-msg" />
			</div>
		</div>
	</c:if>
	<%-- <div class="form-group">
	<label class="col-sm-3 control-label text-right">Old
		Application Number 
	</label>
	<div class="col-sm-3 add-margin">
		<form:input class="form-control" maxlength="50"
			id="oldApplicationNumber" path="oldApplicationNumber" />
		<form:errors path="oldApplicationNumber"
			cssClass="add-margin error-msg" />
	
	<label class="col-sm-2 control-label text-right">Approval Date </label>
		<c:choose>
		<c:when test="%{approvalDate!=null}">
			<div class="col-sm-3 add-margin">
			<form:input path="approvalDate" class="form-control datepicker"
				data-date-end-date="0d" id="approveDate"
				data-inputmask="'mask': 'd/m/y'" required="required" />
			<form:errors path="approvalDate" cssClass="add-margin error-msg" /></div>
		</c:when>
		<c:otherwise>
			<div class="col-sm-3 add-margin">
			<form:input path="approvalDate" class="form-control datepicker"
				data-date-end-date="0d" id="approveDate"
				data-inputmask="'mask': 'd/m/y'" disabled="true" />
			<form:errors path="approvalDate" cssClass="add-margin error-msg" /></div>
		</c:otherwise>
	</c:choose>
</div> --%>

	<div class="form-group">
		<label class="col-sm-3 control-label text-right"><spring:message
				code="lbl.remarks" /></label>
		<div class="col-sm-3 add-margin">
			<form:hidden path="applicantMode" />
			<form:hidden path="source" />
			<form:textarea class="form-control patternvalidation"
				data-pattern="alphanumericspecialcharacters" maxlength="128"
				id="remarks" path="remarks" />
			<form:errors path="remarks" cssClass="add-margin error-msg" />
		</div>
	</div>

	<%-- <div class="form-group">
		<div class="col-sm-3 add-margin">
			<c:choose>
				<c:when test="%{buildingPlanApprovalDate!=null}">
					<label class="col-sm-3 control-label text-right">Building
						PlanApproval Date <span class="mandatory"></span>
					</label>
					<form:input path="buildingPlanApprovalDate"
						class="form-control datepicker" data-date-end-date="0d"
						id="buildingPlanApprovalDate" data-inputmask="'mask': 'd/m/y'"
						required="required" />
					<form:errors path="buildingPlanApprovalDate"
						cssClass="add-margin error-msg" />
				</c:when>
				<c:otherwise>
					<td class="bluebox">&nbsp;</td>
					<td class="bluebox">&nbsp;</td>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="col-sm-3 add-margin">
			<c:choose>
				<c:when test="%{buildingplanapprovalnumber!=null}">
					<label class="col-sm-3 control-label text-right">Building
						PlanApproval Number <span class="mandatory"></span>
					</label>
					<form:input class="form-control patternvalidation" maxlength="50"
						id="buildingplanapprovalnumber" data-pattern="number"
						path="buildingplanapprovalnumber" />
					<form:errors path="buildingplanapprovalnumber"
						cssClass="add-margin error-msg" />
				</c:when>
				<c:otherwise>
					<td class="bluebox">&nbsp;</td>
					<td class="bluebox">&nbsp;</td>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<div class="col-sm-3 add-margin">
		<c:choose>
			<c:when test="%{planPermissionDate!=null}">
				<label class="col-sm-3 control-label text-right">Plan
					Submission Date <span class="mandatory"></span>
				</label>
				<form:input path="planPermissionDate"
					class="form-control datepicker" data-date-end-date="0d"
					id="planPermissionDate" data-inputmask="'mask': 'd/m/y'"
					required="required" />
				<form:errors path="planPermissionDate"
					cssClass="add-margin error-msg" />
			</c:when>
			<c:otherwise>
				<td class="bluebox">&nbsp;</td>
				<td class="bluebox">&nbsp;</td>
			</c:otherwise>
		</c:choose>
	</div>
	<div class="col-sm-3 add-margin">
		<c:choose>
			<c:when test="%{planPermissionNumber!=null}">
				<label class="col-sm-3 control-label text-right">PlanSubmission
					Number <span class="mandatory"></span>
				</label>
				<form:input class="form-control patternvalidation" maxlength="50"
					id="planPermissionNumber" path="planPermissionNumber" />
				<form:errors path="planPermissionNumber"
					cssClass="add-margin error-msg" />
			</c:when>
			<c:otherwise>
				<td class="bluebox">&nbsp;</td>
				<td class="bluebox">&nbsp;</td>
			</c:otherwise>
		</c:choose>
	</div> --%>
</div>
