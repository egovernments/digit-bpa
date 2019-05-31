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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>

<div class="row">
	<div class="col-md-12">
		<div class="panel panel-primary" data-collapsed="0">
			<div class="panel-heading">
			</div>
			<div class="panel-body">

                <div class="form-group">
                    <label class="col-sm-3 control-label text-right"><spring:message
                            code="lbl.noc.dept.type" /></label>
                    <div class="col-sm-3 add-margin">
                        <form:select path="nocDepartmentName" data-first-option="false"
                                     id="nocDepartmentName" cssClass="form-control nocDepartmentName">
                            <form:option value="">
                                <spring:message code="lbl.select" />
                            </form:option>
                            <form:options items="${nocTypes}" itemLabel="department" itemValue="department" />
                        </form:select>
                        <form:errors path="nocDepartmentName" cssClass="add-margin error-msg" />
                    </div>
            		<label class="col-sm-2 control-label text-right"><spring:message
							code="lbl.noc.status" /></label>
					<div class="col-sm-3 add-margin">
						<form:select path="nocStatusId" data-first-option="false"
									 id="nocStatusId" cssClass="form-control">
							<form:option value="">
								<spring:message code="lbl.select" />
							</form:option>
							<form:options items="${nocStatus}" itemValue="id"
										  itemLabel="code" />
						</form:select>
					</div>
                </div>

				<div class="form-group">
					<label class="col-sm-3 control-label text-right"><spring:message
                            code="lbl.noc.app.no"/></label>
                    <div class="col-sm-3 add-margin">
                        <form:input class="form-control patternvalidation" maxlength="50"
                                    id="nocApplicationNumber" path="nocApplicationNumber"/>
                        <form:errors path="nocApplicationNumber"
                                     cssClass="add-margin error-msg"/>
                    </div>
					<label class="col-sm-2 control-label text-right"><spring:message
							code="lbl.noc.app.date" /></label>
					<div class="col-sm-3 add-margin">
						<form:input path="nocApplicationDate" class="form-control datepicker"
									data-date-end-date="0d" id="nocApplicationDate"
									data-inputmask="'mask': 'd/m/y'" />
						<form:errors path="nocApplicationDate" cssClass="add-margin error-msg" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label text-right"><spring:message
                            code="lbl.permit.app.no"/></label>
                    <div class="col-sm-3 add-margin">
                        <form:input class="form-control patternvalidation" maxlength="50"
                                    id="permitApplicationNo" path="permitApplicationNo"/>
                        <form:errors path="permitApplicationNo"
                                     cssClass="add-margin error-msg"/>
                    </div>
					<label class="col-sm-2 control-label text-right"><spring:message
                            code="lbl.noc.thirdparty.no"/></label>
                    <div class="col-sm-3 add-margin">
                        <form:input class="form-control patternvalidation" maxlength="50"
                                    id="thirdPartyApplicationNo" path="thirdPartyApplicationNo"/>
                        <form:errors path="thirdPartyApplicationNo"
                                     cssClass="add-margin error-msg"/>
                    </div>
				</div>
			</div>
		</div>
	</div>
</div>
