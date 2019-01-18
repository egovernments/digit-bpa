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
<div class="panel-heading custom_form_panel_heading">
	<div class="panel-title">
		<spring:message code="lbl.applicant.details" />
	</div>
</div>
<div class="panel-body">
	<div class="form-group">
		<label class="col-sm-3 control-label text-right"><spring:message
				code="lbl.applicant.name" /> <span class="mandatory"></span> </label>
		<div class="col-sm-3 add-margin">
			<form:hidden path="owner.user" id="userId" />
			<form:input class="form-control patternvalidation"
						data-pattern="alphabetspecialcharacters" data-role="tagsinput"
						maxlength="100" id="name" path="owner.name" cols="5" rows="3"
						required="required" />
			<small class=""> <spring:message code="lbl.names.separated.comma" /></small>
			<form:errors path="owner.name" cssClass="add-margin error-msg" />
		</div>
		<label class="col-sm-2 control-label text-right"><spring:message
				code="lbl.owner.address" /><span class="mandatory"></span></label>
		<div class="col-sm-3 add-margin">
			<form:textarea path="owner.address"
						   id="address" class="form-control patternvalidation"
						   data-pattern="alphanumericspecialcharacters" required="required"
						   maxlength="249" cols="5" rows="4" />
			<form:errors path="owner.address" cssClass="add-margin error-msg" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label text-right"><spring:message
				code="lbl.mobileNo" /> <span class="mandatory"></span> </label>
		<div class="col-sm-3 add-margin">
			<form:input class="form-control patternvalidation"
						data-pattern="number" maxlength="10" id="mobileNumber"
						path="owner.user.mobileNumber" required="required" />
			<spring:message code="lbl.sms.send" /> <span class=""></span>
			<form:errors path="owner.user.mobileNumber" cssClass="add-margin error-msg" />
		</div>

		<label class="col-sm-2 control-label text-right"><spring:message
				code="lbl.emailid" /></label>
		<div class="col-sm-3 add-margin">
			<form:input class="form-control " maxlength="128" onblur=""
						id="emailId" name="emailId" path="owner.emailId" />
			<spring:message code="lbl.email.send" /> <span class=""></span>
			<form:errors path="owner.emailId" cssClass="add-margin error-msg" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label text-right"><spring:message
				code="lbl.gender" /> <span class="mandatory"></span></label>
		<div class="col-sm-3 add-margin">
			<form:select path="owner.gender" id="gender"
						 required="required" cssClass="form-control"
						 cssErrorClass="form-control error">
				<form:option value="">
					<spring:message code="lbl.select" />
				</form:option>
				<form:options items="${genderList}" />
			</form:select>
			<form:errors path="owner.gender" cssClass="error-msg" />
		</div>

		<label class="col-sm-2 control-label text-right"><spring:message
				code="lbl.aadhar" /> </label>
		<div class="col-sm-3 add-margin">
			<form:input class="form-control patternvalidation"
						data-pattern="number" minlength="12" maxlength="12" id="aadhaarNumber"
						path="owner.aadhaarNumber" />
			<form:errors path="owner.aadhaarNumber" data-server-error="owner.aadhaarNumber"
						 cssClass="add-margin error-msg" />
		</div>
	</div>
</div>