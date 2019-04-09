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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-primary" data-collapsed="0">
            <div class="panel-heading">
            </div>
            <div class="panel-body">

                <div class="form-group">
                    <label class="col-sm-3 control-label text-right"><spring:message
                            code="lbl.applctn.type"/></label>
                    <div class="col-sm-3 add-margin">
                        <form:select path="applicationTypeId" data-first-option="false"
                                     id="applicationTypeId" cssClass="form-control applicationType">
                            <form:option value="">
                                <spring:message code="lbl.select"/>
                            </form:option>
                            <form:options items="${appTypes}" itemLabel="name" itemValue="id"/>
                        </form:select>
                        <form:errors path="statusId" cssClass="add-margin error-msg"/>
                    </div>
                    <label class="col-sm-2 control-label text-right"><spring:message
                            code="lbl.status"/></label>
                    <div class="col-sm-3 add-margin">
                        <form:select path="statusId" data-first-option="false"
                                     id="statusId" cssClass="form-control">
                            <form:option value="">
                                <spring:message code="lbl.select"/>
                            </form:option>
                            <form:options items="${applnStatusList}" itemValue="id"
                                          itemLabel="description"/>
                        </form:select>
                        <form:errors path="statusId" cssClass="add-margin error-msg"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label text-right"><spring:message
                            code="lbl.service.type"/></label>
                    <div class="col-sm-3 add-margin">
                        <form:select path="serviceTypeId" data-first-option="false"
                                     id="serviceTypeId" cssClass="form-control serviceType">
                            <form:option value="">
                                <spring:message code="lbl.select"/>
                            </form:option>
                            <form:options items="${serviceTypeList}" itemValue="id"
                                          itemLabel="description"/>
                        </form:select>
                    </div>
                    <label class="col-sm-2 control-label text-right"><spring:message
                            code="lbl.application.no"/></label>
                    <div class="col-sm-3 add-margin">
                        <form:input class="form-control patternvalidation" maxlength="50"
                                    id="applicationNumber" path="applicationNumber"/>
                        <form:errors path="applicationNumber"
                                     cssClass="add-margin error-msg"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label text-right"><spring:message
                            code="lbl.fromDate"/></label>
                    <div class="col-sm-3 add-margin">
                        <form:input path="fromDate" class="form-control datepicker"
                                    data-date-end-date="0d" id="fromDate"
                                    data-inputmask="'mask': 'd/m/y'"/>
                        <form:errors path="fromDate" cssClass="add-margin error-msg"/>
                    </div>
                    <label class="col-sm-2 control-label text-right"><spring:message
                            code="lbl.toDate"/></label>
                    <div class="col-sm-3 add-margin">
                        <form:input path="toDate" class="form-control datepicker"
                                    data-date-end-date="0d" id="toDate"
                                    data-inputmask="'mask': 'd/m/y'"/>
                        <form:errors path="toDate" cssClass="add-margin error-msg"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label text-right"><spring:message
                            code="lbl.applicant.name"/></label>
                    <div class="col-sm-3 add-margin">
                        <form:input class="form-control patternvalidation"
                                    data-pattern="alphabetspecialcharacters" maxlength="128"
                                    id="applicantName" path="applicantName"/>
                        <form:errors path="applicantName" cssClass="add-margin error-msg"/>
                    </div>
                </div>
                <div class="form-group plotAreaHideShow">
                    <label class="col-sm-3 control-label text-right"><spring:message
                            code="lbl.fromplotarea"/></label>
                    <div class="col-sm-3 add-margin">
                        <form:input class="form-control patternvalidation"
                                    data-pattern="number" id="fromPlotArea" path="fromPlotArea"/>
                        <form:errors path="fromPlotArea"
                                     cssClass="add-margin error-msg"/>
                    </div>
                    <label class="col-sm-2 control-label text-right"><spring:message
                            code="lbl.toplotarea"/></label>
                    <div class="col-sm-3 add-margin">
                        <form:input class="form-control patternvalidation"
                                    data-pattern="number" id="toPlotArea" path="toPlotArea"/>
                        <form:errors path="toPlotArea"
                                     cssClass="add-margin error-msg"/>
                    </div>
                </div>
                <div class="form-group builtUpAreaHideShow">
                    <label class="col-sm-3 control-label text-right"><spring:message
                            code="lbl.from.builtup.area"/></label>
                    <div class="col-sm-3 add-margin">
                        <form:input class="form-control patternvalidation"
                                    data-pattern="number" id="fromBuiltUpArea" path="fromBuiltUpArea"/>
                        <form:errors path="fromBuiltUpArea"
                                     cssClass="add-margin error-msg"/>
                    </div>
                    <label class="col-sm-2 control-label text-right"><spring:message
                            code="lbl.to.builtup.area"/></label>
                    <div class="col-sm-3 add-margin">
                        <form:input class="form-control patternvalidation"
                                    data-pattern="number" id="toBuiltUpArea" path="toBuiltUpArea"/>
                        <form:errors path="toBuiltUpArea"
                                     cssClass="add-margin error-msg"/>
                    </div>
                </div>
                 <div class="form-group" id="boundarysection">
                 </div>
            </div>
        </div>
    </div>
</div>