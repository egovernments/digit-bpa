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
<div class="panel-heading custom_form_panel_heading">
	<div class="panel-title text-center no-float">
		<c:if test="${not empty message}">
			<strong>${message}</strong>
		</c:if>
	</div>
<div class="row">
	<div class="col-md-12">
		<form:form role="form" action="" method="post"
			modelAttribute="bpaApplication" id="viewApplicationStatus"
			cssClass="form-horizontal form-groups-bordered"
			enctype="multipart/form-data">
			<div class="col-md-12">
				<br>
				<table class="table table-striped datatable">
					<thead>
						<tr>
							<th><spring:message code="lbl.applicant.name" /></th>
							<th><spring:message code="lbl.applicartionno" /></th>
							<th><spring:message code="lbl.applicationdate" /></th>
							<th><spring:message code="lbl.servicename" /></th>
							<th><spring:message code="lbl.status" /></th>
							<th><spring:message code="lbl.pendingaction" /></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>${bpaApplication.owner.name}</td>
							<td>${bpaApplication.applicationNumber}</td>
							<td>${bpaApplication.applicationDate}</td>
							<td>${bpaApplication.serviceType.description}</td>
							<td>${bpaApplication.getCurrentState().getValue()}</td>
							<td>${bpaApplication.getCurrentState().getNextAction()}</td>

						</tr>

					</tbody>
				</table>
			</div>
			 <div class="buttonbottom" align="center">
                <table>
                    <tr>
                        <td><input type="button" name="button2" id="button2" value="Close"
                                   class="btn btn-default" onclick="window.close();"/>
                        </td>
                    </tr>
                </table>
            </div>
		</form:form>
	</div>
</div>

