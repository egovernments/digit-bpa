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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="panel-body">
    <input type="hidden" name="username" value="${stakeHolder.username}">
    <input type="hidden" name="password" value="${stakeHolder.password}">
    <input type="hidden" id="invalidBuildingLicensee" value="${invalidBuildingLicensee}">

    <div class="form-group">
        <label class="col-sm-3 control-label text-right"> <spring:message code="lbl.behalf.org"/><span
                class="mandatory"></span></label>
        <div class="col-sm-3 add-margin">
            <form:radiobutton path="isOnbehalfOfOrganization" class="isOnbehalfOfOrganization" value="true"/>
            <spring:message code="lbl.yes"/>
            <form:radiobutton path="isOnbehalfOfOrganization" class="isOnbehalfOfOrganization" value="false"
                              checked="checked"/> <spring:message code="lbl.no"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;If Yes
            please specify
        </div>
    </div>
    <div id="showhide" class="hide">
        <div class="form-group">
            <label class="col-sm-3 control-label text-right toggle-madatory"><spring:message
                    code="lbl.nameof.org"/><span class="mandatory"></span></label>
            <div class="col-sm-3 add-margin">
                <form:input type="text" cssClass="form-control addremoverequired"
                            path="organizationName" maxlength="128" id="organizationName"/>
                <form:errors path="organizationName" cssClass="error-msg"/>
            </div>
            <label class="col-sm-2 control-label text-right toggle-madatory"><spring:message
                    code="lbl.contactNo"/><span class="mandatory"></span></label>
            <div class="col-sm-3 add-margin">
                <form:input type="text" cssClass="form-control patternvalidation addremoverequired"
                            path="organizationMobNo" data-pattern="number" maxlength="11"
                            id="organizationMobNo"/>
                <form:errors path="organizationMobNo" cssClass="error-msg"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label text-right toggle-madatory"><spring:message
                    code="lbl.addressof.org"/><span class="mandatory"></span></label>
            <div class="col-sm-3 add-margin">
                <form:textarea path="organizationAddress" id="organizationAddress"
                               type="text" class="form-control low-width patternvalidation addremoverequired"
                               data-pattern="regexp_alphabetspecialcharacters" maxlength="128"
                               placeholder="" />
                <form:errors path="organizationAddress" cssClass="error-msg"/>
            </div>
            <label class="col-sm-2 control-label text-right toggle-madatory"><spring:message
                    code="lbl.contact.person"/><span class="mandatory"></span></label>
            <div class="col-sm-3 add-margin">
                <form:input type="text" cssClass="form-control addremoverequired"
                            path="contactPerson" maxlength="50" id="contactPerson"/>
                <form:errors path="contactPerson" cssClass="error-msg"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label text-right toggle-madatory"><spring:message
                    code="lbl.designation"/><span class="mandatory"></span></label>
            <div class="col-sm-3 add-margin">
                <form:input path="designation" id="designation"
                            type="text" class="form-control low-width patternvalidation addremoverequired"
                            data-pattern="regexp_alphabetspecialcharacters" maxlength="50"
                            placeholder="" />
                <form:errors path="designation" cssClass="error-msg"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label text-right"><spring:message
                code="lbl.applicant.name"/><span class="mandatory"></span></label>
        <div class="col-sm-3 add-margin">
            <form:input path="name"
                        class="form-control text-left patternvalidation"
                        data-pattern="alphanumeric" maxlength="100" required="required"/>
            <form:errors path="name" cssClass="error-msg"/>
        </div>
        <label class="col-sm-2 control-label text-right"><spring:message
                code="lbl.stakeholder.type"/> <span class="mandatory"></span></label>
        <div class="col-sm-3 add-margin">
            <form:select path="stakeHolderType" id="stakeHolderType" required="required"
                         cssClass="form-control stakeHolderType" cssErrorClass="form-control error">
                <form:option value="">
                    <spring:message code="lbl.select"/>
                </form:option>
                <form:options items="${stakeHolderTypes}" itemLabel="stakeHolderTypeVal"/>
            </form:select>
            <form:errors path="stakeHolderType" cssClass="error-msg"/>
        </div>
    </div>

    <c:if test="${isEmployee}">
        <div class="form-group">
            <label class="col-sm-3 control-label text-right"><spring:message
                    code="lbl.stakeholder.ackno"/></label>
            <div class="col-sm-3 add-margin">
                <form:input type="text" cssClass="form-control" path="code" id="code"/>
                <form:errors path="code" cssClass="error-msg"/>
            </div>
            <label class="col-sm-2 control-label text-right"><spring:message
                    code="lbl.status"/></label>
            <div class="col-sm-3 add-margin">
                <form:radiobutton path="isActive" value="true"/> <spring:message code="lbl.active"/>
                <form:radiobutton path="isActive" value="false"/> <spring:message code="lbl.in.active"/>
                <form:errors path="isActive" cssClass="error-msg"/>
            </div>
        </div>
    </c:if>

    <div class="form-group">
        <label class="col-sm-3 control-label text-right"><spring:message
                code="lbl.gender"/> <span class="mandatory"></span></label>
        <div class="col-sm-3 add-margin">
            <form:select path="gender" id="gender" required="required"
                         cssClass="form-control" cssErrorClass="form-control error">
                <form:option value="">
                    <spring:message code="lbl.select"/>
                </form:option>
                <form:options items="${genderList}"/>
            </form:select>
            <form:errors path="gender" cssClass="error-msg"/>
        </div>
        <label class="col-sm-2 control-label text-right"><spring:message
                code="lbl.dob"/> <span class="mandatory"></span></label>
        <div class="col-sm-3 add-margin">
            <form:input type="text" cssClass="form-control datepicker" path="dob"
                        id="birthDate" data-date-end-date="-18y" required="required"/>
            <form:errors path="dob" cssClass="error-msg"/>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label text-right"><spring:message
                code="lbl.mobileNo"/><span class="mandatory"></span></label>
        <div class="col-sm-3 add-margin">
            <form:input type="text" cssClass="form-control patternvalidation mobileno-field"
                        data-pattern="number"
                        placeholder="Mobile Number" path="mobileNumber" maxlength="10"
                        id="mobileNumber1" required="required"/><span class=""></span>
            <form:errors path="mobileNumber" cssClass="error-msg"/>
        </div>
        <label class="col-sm-2 control-label text-right"><spring:message
                code="lbl.emailid"/><span class="mandatory"></span></label>
        <div class="col-sm-3 add-margin">
            <form:input type="text" cssClass="form-control patternvalidation"
                        data-pattern="regexp_alphabetspecialcharacters" placeholder="Email ID" path="emailId"
                        id="emailId1" maxlength="128" required="required"/><span class=""></span>
            <form:errors path="emailId" cssClass="error-msg"/>
        </div>
    </div>

    <%-- <div class="form-group">
        <label class="col-sm-3 control-label text-right"><spring:message
                code="lbl.user.id" /><span class="mandatory"></span></label>
        <div class="col-sm-3 add-margin">
            <form:input type="text" cssClass="form-control" path="username"
                id="userId" required="required" />
            <form:errors path="username" cssClass="error-msg" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label text-right"><spring:message
                code="lbl.pwd" /><span class="mandatory"></span></label>
        <div class="col-sm-3 add-margin">
            <form:input type="text" cssClass="form-control" path="password"
                id="password" required="required" />
            <form:errors path="password" cssClass="error-msg" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label text-right"><spring:message
                code="lbl.verify.pwd" /><span class="mandatory"></span></label>
        <div class="col-sm-3 add-margin">
            <form:input type="text" cssClass="form-control" path="" id="verifyPwd"
                required="required" />
            <form:errors path="" cssClass="error-msg" />
        </div>
    </div> --%>
    <div class="form-group">
        <label class="col-sm-3 control-label text-right"><spring:message
                code="lbl.lic.no"/><span class="mandatory"></span></label>
        <div class="col-sm-3 add-margin">
            <form:input type="text" cssClass="form-control"
                        path="licenceNumber" maxlength="64" id="licenceNumber" required="required"/><span class=""></span>
            <form:errors path="licenceNumber" cssClass="error-msg"/>
        </div>
    </div>

    <%-- <div class="form-group">
        <label class="col-sm-3 control-label text-right"><spring:message
                code="lbl.coa.enrol.no" /><span class="mandatory"></span></label>
        <div class="col-sm-3 add-margin">
            <form:input type="text" cssClass="form-control"
                path="coaEnrolmentNumber" maxlength="64" id="coaEnrolmentNo" required="required" />
            <form:errors path="coaEnrolmentNumber" cssClass="error-msg" />
        </div>
        <label class="col-sm-2 control-label text-right"><spring:message
                code="lbl.coa.renew.date" /><span class="mandatory"></span></label>
        <div class="col-sm-3 add-margin">
            <form:input type="text" cssClass="form-control datepicker"
                path="coaEnrolmentDueDate" id="coaRenewalDueDate" required="required" />
            <form:errors path="coaEnrolmentDueDate" cssClass="error-msg" />
        </div>
    </div> --%>

    <%-- <div class="form-group">
        <label class="col-sm-3 control-label text-right"> <spring:message code="lbl.enrol.with.local.body" /><span class="mandatory"></span></label>
        <div class="col-sm-3 add-margin">
            <form:radiobutton path="isEnrolWithLocalBody" value="true" checked="checked" /> <spring:message code="lbl.yes" />
            <form:radiobutton path="isEnrolWithLocalBody" value="false" /> <spring:message code="lbl.no" />
        </div>
        <label class="col-sm-2 control-label text-right"><spring:message
                code="lbl.tin.no" /></label>
        <div class="col-sm-3 add-margin">
            <form:input type="text" cssClass="form-control" maxlength="11" path="tinNumber"
                id="tinNumber" />
            <form:errors path="tinNumber" cssClass="error-msg" />
        </div>
    </div> --%>

    <div class="form-group">
        <label class="col-sm-3 control-label text-right"><spring:message
                code="lbl.buil.lic.iss.date"/><span class="mandatory"></span></label>
        <div class="col-sm-3 add-margin">
            <form:input type="text" cssClass="form-control datepicker"
                        path="buildingLicenceIssueDate" id="buildingLicenceIssueDate" required="required"/>
            <form:errors path="buildingLicenceIssueDate" cssClass="error-msg"/>
        </div>
        <label class="col-sm-2 control-label text-right"><spring:message
                code="lbl.build.lic.exp.date"/><span class="mandatory"></span></label>
        <div class="col-sm-3 add-margin">
            <form:input type="text" cssClass="form-control datepicker"
                        path="buildingLicenceExpiryDate" id="buildingLicenceExpiryDate" required="required"/>
            <form:errors path="buildingLicenceExpiryDate" cssClass="error-msg"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label text-right"><spring:message
                code="lbl.aadhar"/></label>
        <div class="col-sm-3 add-margin">
            <form:input type="text" cssClass="form-control patternvalidation"
                        path="aadhaarNumber" data-pattern="number" maxlength="12" id="aadhaarNumber"/>
            <form:errors path="aadhaarNumber" cssClass="error-msg"/>
        </div>
        <label class="col-sm-2 control-label text-right"><spring:message
                code="lbl.pan"/></label>
        <div class="col-sm-3 add-margin">
            <form:input type="text" cssClass="form-control patternvalidation" path="pan"
                        data-pattern="alphanumeric" id="panNumber" maxlength="10"/><span class=""></span>
            <form:errors path="pan" cssClass="error-msg"/>
        </div>
    </div>
    <c:forEach var="address" items="${stakeHolder.address}" varStatus="status1">
        <form:hidden path="address[${status1.index}].id" value="${address.id}"/>
    </c:forEach>
    <c:set value="correspondenceAddress" var="address" scope="request"></c:set>

    <form:hidden path="correspondenceAddress.user" value="${stakeHolder.id}"/>
    <%-- <form:hidden path="address[0].type" value="PRESENTADDRESS" /> --%>
    <jsp:include page="address-info.jsp">
        <jsp:param value="lbl.comm.address" name="subhead"/>
    </jsp:include>
	<div class="form-group">
		<label class="col-sm-3 control-label text-right"><spring:message
				code="lbl.if.addresssame" /> </label>
		<div class="col-sm-3 add-margin">
			<form:checkbox id="isAddressSame" path="isAddressSame"
				value="isAddressSame" />
			<form:errors path="isAddressSame" />
		</div>
	</div>
	<c:set value="permanentAddress" var="address" scope="request"></c:set>
    <form:hidden path="permanentAddress.user" value="${stakeHolder.id}"/>
    <%-- <form:hidden path="address[1].type" value="PERMANENT" /> --%>
    <jsp:include page="address-info.jsp">
        <jsp:param value="lbl.permt.address" name="subhead"/>
    </jsp:include>

</div>
