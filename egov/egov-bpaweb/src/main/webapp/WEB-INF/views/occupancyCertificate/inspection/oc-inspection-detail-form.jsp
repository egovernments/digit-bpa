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
  ~     GNU General Public License for more details.w
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

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>

<div class="panel-heading toggle-header custom_form_panel_heading">
	<div class="panel-title">
		<spring:message code="lbl.inspn.details" />
	</div>
</div>
<div class="panel-body">
	<c:choose>
		<c:when test="${!ocInspection.inspection.docket.isEmpty()}">
			<c:forEach var="doc" items="${ocInspection.inspection.docket}" varStatus="status1">
				<c:choose>
					<c:when test="${!doc.docketDetail.isEmpty()}">
						<div class="panel-heading custom_form_panel_heading">
							<div class="panel-title">
							<form:hidden 
							id="inspection.docket[${status1.index}].checklistType.id" 
							path="inspection.docket[${status1.index}].checklistType"
							value="${doc.checklistType.id}" />
								<c:out
									value="${doc.checklistType.description}" />
							</div>
						</div>
						<div class="form-group view-content header-color hidden-xs">
							<div class="col-sm-5 text-left">
								<spring:message code="lbl.files" />
							</div>
							<div class="col-sm-3 text-left">&nbsp;&nbsp;
								<spring:message code="lbl.is.applicable" />
							</div>
							<div class="col-sm-4 text-left">
								<spring:message code="lbl.remarks" />
							</div>
						</div>
						<c:forEach var="docs" items="${doc.docketDetail}"
							varStatus="status">
							<div class="form-group">
								<div class="col-sm-5 add-margin check-text text-left">
									<c:out value="${docs.serviceChecklist.checklist.description}" />
									<form:hidden
										id="inspection.docket[${status1.index}].docketDetail[${status.index}].serviceChecklist"
										path="inspection.docket[${status1.index}].docketDetail[${status.index}].serviceChecklist"
										value="${docs.serviceChecklist.id}" />
									<form:hidden
										id="inspection.docket[${status1.index}].docketDetail[${status.index}].serviceChecklist.checklist.description"
										path="inspection.docket[${status1.index}].docketDetail[${status.index}].serviceChecklist.checklist.description"
										value="${docs.serviceChecklist.checklist.description}" />
								</div>
			
								<div class="col-sm-3 add-margin text-left">
									<c:choose>
										<c:when test="${mode =='editinsp'}">
											<c:forEach items="${planScrutinyValues}" var="inspnVal">
												<!-- <div class="radio"> -->
													<label>&nbsp;&nbsp;
													<input type="radio" value="${inspnVal}"
														name="inspection.docket[${status1.index}].docketDetail[${status.index}].value"
														<c:if test="${inspnVal eq docs.value}"> checked="checked" </c:if> />&nbsp;${inspnVal.checkListVal}
													</label>
												<!-- </div> -->
											</c:forEach>
										</c:when>
										<c:otherwise>
											<c:forEach items="${planScrutinyValues}" var="inspnVal">
											<!-- 	<div class="radio"> -->
													<label>&nbsp;&nbsp;
													<input type="radio" value="${inspnVal}"
														name="inspection.docket[${status1.index}].docketDetail[${status.index}].value"
														<c:if test="${inspnVal eq 'NOT_APPLICABLE'}"> checked="checked" </c:if> />&nbsp;${inspnVal.checkListVal}
													</label>
												<!-- </div> -->
											</c:forEach>
										</c:otherwise>
									</c:choose>
									<form:errors
										path="inspection.docket[${status1.index}].docketDetail[${status.index}].value"
										cssClass="add-margin error-msg" />
								</div>
								<div class="col-sm-4 add-margin text-left">
									<form:textarea class="form-control patternvalidation"
										data-pattern="alphanumericspecialcharacters" maxlength="256"
										id="inspection.docket[${status1.index}].docketDetail[${status.index}].remarks"
										rows="2"
										path="inspection.docket[${status1.index}].docketDetail[${status.index}].remarks" />
			
									<form:errors
										path="inspection.docket[${status1.index}].docketDetail[${status.index}].remarks"
										cssClass="add-margin error-msg" />
								</div>
							</div>
						</c:forEach>
					</c:when>
				</c:choose>
			</c:forEach>
		</c:when>
	</c:choose>
	<label class="col-sm-3 control-label text-right"><spring:message
			code="lbl.ins.remarks" /></label>
	<div class="col-sm-6 add-margin text-left">
		<form:textarea class="form-control patternvalidation"
			data-pattern="alphanumericspecialcharacters" maxlength="1000"
			id="inspectionRemarks" rows="3" path="inspection.inspectionRemarks" />

		<form:errors path="inspection.inspectionRemarks"
			cssClass="add-margin error-msg" />
	</div>
</div>

<script>
	jQuery(document).ready(function($) {
		window.onunload = function() {
			window.opener.location.reload();
		};
	});
</script>