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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>
<form:form role="form" action="/bpa/stakeholder/createbycitizen" modelAttribute="stakeHolder"
           id="stakeHolderByCitizenform" autocomplete="off" cssClass="form-horizontal form-groups-bordered"
           enctype="multipart/form-data">
    <input type="hidden" name="isBusinessUser" id="isBusinessUser" value="${isBusinessUser}">
    <input type="hidden" name="source" id="source" value="${stakeHolder.source}">
    <input type="hidden" name="isActive" value="${stakeHolder.isActive}">
    <input type="hidden" id="showNotification" value="${showNotification}">
    <ul class="nav nav-tabs" id="settingstab">
        <li class="active"><a data-toggle="tab" href="#applicant-info"
                              data-tabidx=0><spring:message code='lbl.applicant.info'/></a></li>
        <li><a data-toggle="tab" href="#checklist-info" data-tabidx=1><spring:message code='title.documentdetail'/></a>
        </li>
    </ul>
    <div class="tab-content">
        <div id="applicant-info" class="tab-pane fade in active">
            <div class="panel panel-primary" data-collapsed="0">
                <jsp:include page="stakeholder-form.jsp"></jsp:include>
            </div>
        </div>
        <div id="checklist-info" class="tab-pane fade">
            <div class="panel panel-primary" data-collapsed="0">
                <jsp:include page="checklist.jsp"></jsp:include>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-offset-3 col-md-6 text-center "  id="captcha-section" style="padding-bottom: 10px;">
                <spring:eval expression="@environment.getProperty('captcha.strength')" var="strength"/>
                <c:import url="/WEB-INF/views/common/captcha-${strength}.jsp" context="/egi"/>
                <form:errors cssClass="add-margin error-msg"/>
            </div>
        </div>
    </div>

    <div class="form-group col-sm-12" id="otp-section" style="display: block;" >
        <div class="col-sm-4"></div>
        <div class="col-sm-1" style=" text-align: right;padding-right: 0px;">
            <i class="fa fa-eye show  style-color otp-view" data-view="show" aria-hidden="true"></i>
        </div>
        <div class="col-sm-3" style=" padding-left: 5px;">
            <form:password path="activationCode" id="activationcode" cssClass="form-control"
                           placeholder="Enter OTP" minlength="5" maxlength="5" autocomplete="off"/>
            <label id="activationcode-error" class="error align-right pull-right display-hide"
                   for="activationcode">Required</label>
            <span class="mandatory set-mandatory"></span>
        </div>
        <div class="col-sm-4" >

        </div>
    </div>


    <div class="text-center" id="otpbtn-section">
        <form:errors path="activationCode" cssClass="add-margin error-msg"/>
        <button type="button" class="btn btn-primary" id="otpbtn">
            <i class="fa fa-key" aria-hidden="true"></i>
            <spring:message code="lbl.generate.otp"/>
        </button>
        <a href='javascript:void(0)' class='btn btn-default'
           onclick='self.close()'><spring:message code='lbl.close'/></a>
    </div>
    <div class="text-center" id="create-section">
        <button type="submit" class='btn btn-primary' id="buttonSubmit">
            <spring:message code="lbl.submit"/>
        </button>
        <a href='javascript:void(0)' class='btn btn-default'
           onclick='self.close()'><spring:message code='lbl.close'/></a>
    </div>
</form:form>

<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true"  id="infoModal">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <div class="alert alert-warning">
                    <strong>Please note, following details are required for new building licensee account</strong>
                </div>
            </div>
            <div class="modal-body">
                <ul>
                    <li>You need to have <span class="view-content">Mobile, Email, Aadhaar, License </span> details.</li>
                    <li>Along with above details, You will have to submit few documents, Please check <span class="view-content">DOCUMENT DETAILS</span> tab for more details on the documents to be submitted.</li>
                    <li>Before proceeding, Please make sure you have all the required documents.</li>
                    <li>All mandatory details and documents are marked with <span class="view-content">Asterisk</span> <span class="error-msg">(*)</span> symbol.</li>
                </ul>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">OK</button>
            </div>
        </div>
    </div>
</div>

<script src='https://www.google.com/recaptcha/api.js' async defer></script>
<script
        src="<cdn:url value='/resources/global/js/bootstrap/bootstrap-datepicker.js' context='/egi'/>"></script>
<link rel="stylesheet"
      href="<cdn:url value='/resources/global/css/bootstrap/bootstrap-datepicker.css' context='/egi'/>">
<script
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/moment.min.js' context='/egi'/>"></script>
<script src="<cdn:url value='/resources/js/app/stakeholder-by-citizen.js?rnd=${app_release_no}'/> "></script>
<script src="<cdn:url value='/resources/js/app/stakeholder-common.js?rnd=${app_release_no}'/> "></script>
<script src="<cdn:url value='/resources/js/app/documentsuploadvalidation.js?rnd=${app_release_no}'/> "></script>
