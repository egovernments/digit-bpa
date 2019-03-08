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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>


<div class="panel-body">
	<div class="form-group view-content header-color hidden-xs">
		<div class="col-sm-5 text-left">
			<spring:message code="lbl.files" />
		</div>
		<div class="col-sm-3 text-left">
			<spring:message code="lbl.provided" />
		</div>
		<div class="col-sm-4 text-left">
			<spring:message code="lbl.remarks" />
		</div>
	</div>
	<c:forEach var="docs" items="${documentScrutinyChecklist}"
		varStatus="status">
		<div class="form-group">
			<div class="col-sm-5 add-margin check-text text-left">
				<c:out value="${docs.checklist.description}" />
				<form:hidden
					id="documentScrutinies[0].docScrutiny.documentScrutinyChecklists[${status.index}]serviceChecklist"
					path="documentScrutinies[0].docScrutiny.documentScrutinyChecklists[${status.index}].serviceChecklist"
					value="${docs.id}" />
			</div>

			<div class="col-sm-3 add-margin text-left">
				<c:forEach items="${planScrutinyValues}" var="scrutnyVal">
					<div class="radio">
						<label><input type="radio" value="${scrutnyVal}"
							name="documentScrutinies[0].docScrutiny.documentScrutinyChecklists[${status.index}].scrutinyValue"
							<c:if test="${scrutnyVal eq 'NOT_APPLICABLE'}"> checked="checked" </c:if> />${scrutnyVal.checkListVal}
						</label>
					</div>
				</c:forEach>
				<form:errors
					path="documentScrutinies[0].docScrutiny.documentScrutinyChecklists[${status.index}].scrutinyValue"
					cssClass="add-margin error-msg" />
			</div>
			<div class="col-sm-4 add-margin text-left">
				<form:textarea class="form-control patternvalidation"
					data-pattern="alphanumericspecialcharacters" maxlength="255"
					id="documentScrutinies[0].docScrutiny.documentScrutinyChecklists[${status.index}]remarks"
					rows="3"
					path="documentScrutinies[0].docScrutiny.documentScrutinyChecklists[${status.index}].remarks" />

				<form:errors
					path="documentScrutinies[0].docScrutiny.documentScrutinyChecklists[${status.index}].remarks"
					cssClass="add-margin error-msg" />
			</div>
		</div>
	</c:forEach>
</div>