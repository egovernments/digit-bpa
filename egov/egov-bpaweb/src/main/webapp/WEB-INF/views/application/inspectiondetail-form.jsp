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

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>

<div class="panel-heading toggle-header custom_form_panel_heading">
    <div class="panel-title">
        <spring:message code="lbl.inpn.chklst.dtl" />
    </div>
</div>
<div class="panel-body">
<c:choose>
	<c:when test="${!docketDetailLocList.isEmpty()}">
		<div class="panel-heading custom_form_panel_heading">
			<div class="panel-title"><spring:message code="lbl.loc.of.plot"/></div>
		</div>
		<div class="form-group view-content header-color hidden-xs">
			<div class="col-sm-5 text-left"><spring:message code="lbl.files" /></div>
			<div class="col-sm-3 text-left"><spring:message code="lbl.provided" /></div>
			<div class="col-sm-4 text-left">
				<spring:message code="lbl.remarks" />
			</div>
		</div>
		<c:forEach var="docs" items="${docketDetailLocList}"
			varStatus="status">
			<div class="form-group">
				<div class="col-sm-5 add-margin check-text text-left">
					<c:out value="${docs.checkListDetail.description}" />
					<form:hidden
						id="docketDetailLocList${status.index}checkListDetail.id"
						path="docketDetailLocList[${status.index}].checkListDetail.id"
						value="${docs.checkListDetail.id}" />
					<form:hidden
						id="docketDetailLocList${status.index}checkListDetail.description"
						path="docketDetailLocList[${status.index}].checkListDetail.description"
						value="${docs.checkListDetail.description}" />
				</div>

				<div class="col-sm-3 add-margin text-left">
                    <c:choose>
                        <c:when test="${mode =='editinsp'}">
                            <c:forEach items="${planScrutinyValues}" var="inspnVal">
                                <div class="radio">
                                    <label><input type="radio" value="${inspnVal}"
                                                  name="docketDetailLocList[${status.index}].value"
                                            <c:if test="${inspnVal eq docs.value}"> checked="checked" </c:if> />${inspnVal.checkListVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${planScrutinyValues}" var="inspnVal">
                                <div class="radio">
                                    <label><input type="radio" value="${inspnVal}"
                                                  name="docketDetailLocList[${status.index}].value"
                                            <c:if test="${inspnVal eq 'NOT_APPLICABLE'}"> checked="checked" </c:if> />${inspnVal.checkListVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
					<form:errors path="docketDetailLocList[${status.index}].value"
								 cssClass="add-margin error-msg"/>
				</div>
				<div class="col-sm-4 add-margin text-left">
					<form:textarea class="form-control patternvalidation"
						data-pattern="alphanumericspecialcharacters" maxlength="255"
						id="docketDetailLocList${status.index}remarks" rows="3"
						path="docketDetailLocList[${status.index}].remarks" />

					<form:errors path="docketDetailLocList[${status.index}].remarks"
						cssClass="add-margin error-msg" />
				</div>
			</div>
		</c:forEach>
	</c:when>
</c:choose>

<c:choose>
	<c:when test="${!docketDetailMeasumentList.isEmpty()}">
		<div class="panel-heading custom_form_panel_heading">
			<div class="panel-title"><spring:message code="lbl.meas.plot"/></div>
		</div>
		<div class="form-group view-content header-color hidden-xs">
			<div class="col-sm-5 text-left"><spring:message code="lbl.files" /></div>
			<div class="col-sm-3 text-left"><spring:message code="lbl.provided" /></div>
			<div class="col-sm-4 text-left">
				<spring:message code="lbl.remarks" />
			</div>
		</div>
		<c:forEach var="docs" items="${docketDetailMeasumentList}"
			varStatus="status">
			<div class="form-group">
				<div class="col-sm-5 add-margin check-text text-left">
					<c:out value="${docs.checkListDetail.description}" />
					<form:hidden
						id="docketDetailMeasumentList${status.index}checkListDetail.id"
						path="docketDetailMeasumentList[${status.index}].checkListDetail.id"
						value="${docs.checkListDetail.id}" />
					<form:hidden
						id="docketDetailMeasumentList${status.index}checkListDetail.description"
						path="docketDetailMeasumentList[${status.index}].checkListDetail.description"
						value="${docs.checkListDetail.description}" />
				</div>
				<div class="col-sm-3 add-margin text-left">
                    <c:choose>
                        <c:when test="${mode =='editinsp'}">
                            <c:forEach items="${planScrutinyValues}" var="inspnVal">
                                <div class="radio">
                                    <label><input type="radio" value="${inspnVal}" class="radioBtnClass"
                                                  name="docketDetailMeasumentList[${status.index}].value"
                                            <c:if test="${inspnVal eq docs.value}"> checked="checked" </c:if> />${inspnVal.checkListVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${planScrutinyValues}" var="inspnVal">
                                <div class="radio">
                                    <label><input type="radio" value="${inspnVal}" class="radioBtnClass"
                                                  name="docketDetailMeasumentList[${status.index}].value"
                                            <c:if test="${inspnVal eq 'NOT_APPLICABLE'}"> checked="checked" </c:if> />${inspnVal.checkListVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>

					<form:errors path="docketDetailMeasumentList[${status.index}].value"
								 cssClass="add-margin error-msg"/>
				</div>
				<div class="col-sm-4 add-margin text-left">
					<form:textarea class="form-control patternvalidation"
						data-pattern="alphanumericspecialcharacters" maxlength="255"
						id="docketDetailMeasumentList${status.index}remarks" rows="3"
						path="docketDetailMeasumentList[${status.index}].remarks" />
					<form:errors
						path="docketDetailMeasumentList[${status.index}].remarks"
						cssClass="add-margin error-msg" />
				</div>
			</div>
		</c:forEach>
	</c:when>
</c:choose>

<c:choose>
	<c:when test="${!docketDetailAccessList.isEmpty()}">
		<div class="panel-heading custom_form_panel_heading">
			<div class="panel-title"><spring:message code="lbl.access.to.plot"/></div>
		</div>
		<div class="form-group view-content header-color hidden-xs">
			<div class="col-sm-5 text-left"><spring:message code="lbl.files" /></div>
			<div class="col-sm-3 text-left"><spring:message code="lbl.provided" /></div>
			<div class="col-sm-4 text-left">
				<spring:message code="lbl.remarks" />
			</div>
		</div>
		<c:forEach var="docs" items="${docketDetailAccessList}"
			varStatus="status">
			<div class="form-group">
				<div class="col-sm-5 add-margin check-text text-left">
					<c:out value="${docs.checkListDetail.description}" />
					<form:hidden
						id="docketDetailAccessList${status.index}checkListDetail.id"
						path="docketDetailAccessList[${status.index}].checkListDetail.id"
						value="${docs.checkListDetail.id}" />
					<form:hidden
						id="docketDetailAccessList${status.index}checkListDetail.description"
						path="docketDetailAccessList[${status.index}].checkListDetail.description"
						value="${docs.checkListDetail.description}" />
				</div>
				<div class="col-sm-3 add-margin text-left">
                    <c:choose>
                        <c:when test="${mode =='editinsp'}">
                            <c:forEach items="${planScrutinyValues}" var="inspnVal">
                                <div class="radio">
                                    <label><input type="radio" value="${inspnVal}" class="radioBtnClass"
                                                  name="docketDetailAccessList[${status.index}].value"
                                            <c:if test="${inspnVal eq docs.value}"> checked="checked" </c:if> />${inspnVal.checkListVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${planScrutinyValues}" var="inspnVal">
                                <div class="radio">
                                    <label><input type="radio" value="${inspnVal}" class="radioBtnClass"
                                                  name="docketDetailAccessList[${status.index}].value"
                                            <c:if test="${inspnVal eq 'NOT_APPLICABLE'}"> checked="checked" </c:if> />${inspnVal.checkListVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
					<form:errors path="docketDetailAccessList[${status.index}].value"
								 cssClass="add-margin error-msg"/>
				</div>

				<div class="col-sm-4 add-margin text-left">
					<form:textarea class="form-control patternvalidation"
						data-pattern="alphanumericspecialcharacters" maxlength="255"
						id="docketDetailAccessList${status.index}remarks" rows="3"
						path="docketDetailAccessList[${status.index}].remarks" />

					<form:errors path="docketDetailAccessList[${status.index}].remarks"
						cssClass="add-margin error-msg" />
				</div>
			</div>
		</c:forEach>
	</c:when>
</c:choose>


<c:choose>
	<c:when test="${!docketDetlSurroundingPlotList.isEmpty()}">
		<div class="panel-heading custom_form_panel_heading">
			<div class="panel-title"><spring:message code="lbl.surrnd.plot"/></div>
		</div>
		<div class="form-group view-content header-color hidden-xs">
			<div class="col-sm-5 text-left"><spring:message code="lbl.files" /></div>
			<div class="col-sm-3 text-left"><spring:message code="lbl.provided" /></div>
			<div class="col-sm-4 text-left">
				<spring:message code="lbl.remarks" />
			</div>
		</div>
		<c:forEach var="docs" items="${docketDetlSurroundingPlotList}"
			varStatus="status">
			<div class="form-group">
				<div class="col-sm-5 add-margin check-text text-left">
					<c:out value="${docs.checkListDetail.description}" />
					<form:hidden
						id="docketDetlSurroundingPlotList${status.index}checkListDetail.id"
						path="docketDetlSurroundingPlotList[${status.index}].checkListDetail.id"
						value="${docs.checkListDetail.id}" />
					<form:hidden
						id="docketDetlSurroundingPlotList${status.index}checkListDetail.description"
						path="docketDetlSurroundingPlotList[${status.index}].checkListDetail.description"
						value="${docs.checkListDetail.description}" />
				</div>
				<div class="col-sm-3 add-margin text-left">
                    <c:choose>
                        <c:when test="${mode =='editinsp'}">
                            <c:forEach items="${planScrutinyValues}" var="inspnVal">
                                <div class="radio">
                                    <label><input type="radio" value="${inspnVal}" class="radioBtnClass"
                                                  name="docketDetlSurroundingPlotList[${status.index}].value"
                                            <c:if test="${inspnVal eq docs.value}"> checked="checked" </c:if> />${inspnVal.checkListVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${planScrutinyValues}" var="inspnVal">
                            <div class="radio">
                                <label><input type="radio" value="${inspnVal}" class="radioBtnClass"
                                              name="docketDetlSurroundingPlotList[${status.index}].value"
                                        <c:if test="${inspnVal eq 'NOT_APPLICABLE'}"> checked="checked" </c:if> />${inspnVal.checkListVal}
                                </label>
                            </div>
                        </c:forEach>
                        </c:otherwise>
                    </c:choose>
					<form:errors path="docketDetlSurroundingPlotList[${status.index}].value"
								 cssClass="add-margin error-msg"/>
				</div>
				<div class="col-sm-4 add-margin text-left">
					<form:textarea class="form-control patternvalidation"
						data-pattern="alphanumericspecialcharacters" maxlength="255"
						id="docketDetlSurroundingPlotList${status.index}remarks" rows="3"
						path="docketDetlSurroundingPlotList[${status.index}].remarks" />
					<form:errors
						path="docketDetlSurroundingPlotList[${status.index}].remarks"
						cssClass="add-margin error-msg" />
				</div>
			</div>
		</c:forEach>
	</c:when>
</c:choose>


<c:choose>
	<c:when test="${!docketDetailLandTypeList.isEmpty()}">
		<div class="panel-heading custom_form_panel_heading">
			<div class="panel-title"><spring:message code="lbl.type.land"/></div>
		</div>
		<div class="form-group view-content header-color hidden-xs">
			<div class="col-sm-5 text-left"><spring:message code="lbl.files" /></div>
			<div class="col-sm-3 text-left"><spring:message code="lbl.provided" /></div>
			<div class="col-sm-4 text-left">
				<spring:message code="lbl.remarks" />
			</div>
		</div>
		<c:forEach var="docs" items="${docketDetailLandTypeList}"
			varStatus="status">
			<div class="form-group">
				<div class="col-sm-5 add-margin check-text text-left">
					<c:out value="${docs.checkListDetail.description}" />
					<form:hidden
						id="docketDetailLandTypeList${status.index}checkListDetail.id"
						path="docketDetailLandTypeList[${status.index}].checkListDetail.id"
						value="${docs.checkListDetail.id}" />
					<form:hidden
						id="docketDetailLandTypeList${status.index}checkListDetail.description"
						path="docketDetailLandTypeList[${status.index}].checkListDetail.description"
						value="${docs.checkListDetail.description}" />
				</div>
				<div class="col-sm-3 add-margin text-left">
                    <c:choose>
                        <c:when test="${mode =='editinsp'}">
                            <c:forEach items="${planScrutinyValues}" var="inspnVal">
                                <div class="radio">
                                    <label><input type="radio" value="${inspnVal}" class="radioBtnClass"
                                                  name="docketDetailLandTypeList[${status.index}].value"
                                            <c:if test="${inspnVal eq docs.value}"> checked="checked" </c:if> />${inspnVal.checkListVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${planScrutinyValues}" var="inspnVal">
                                <div class="radio">
                                    <label><input type="radio" value="${inspnVal}" class="radioBtnClass"
                                                  name="docketDetailLandTypeList[${status.index}].value"
                                            <c:if test="${inspnVal eq 'NOT_APPLICABLE'}"> checked="checked" </c:if> />${inspnVal.checkListVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
					<form:errors path="docketDetailLandTypeList[${status.index}].value"
								 cssClass="add-margin error-msg"/>
				</div>
				<div class="col-sm-4 add-margin text-left">
					<form:textarea class="form-control patternvalidation"
						data-pattern="alphanumericspecialcharacters" maxlength="255"
						id="docketDetailLandTypeList${status.index}remarks" rows="3"
						path="docketDetailLandTypeList[${status.index}].remarks" />
					<form:errors
						path="docketDetailLandTypeList[${status.index}].remarks"
						cssClass="add-margin error-msg" />
				</div>
			</div>
		</c:forEach>
	</c:when>
</c:choose>

<c:choose>
	<c:when test="${!docketDetailProposedWorkList.isEmpty()}">
		<div class="panel-heading custom_form_panel_heading">
			<div class="panel-title"><spring:message code="lbl.stage.propsd.work"/></div>
		</div>
		<div class="form-group view-content header-color hidden-xs">
			<div class="col-sm-5 text-left"><spring:message code="lbl.files" /></div>
			<div class="col-sm-3 text-left"><spring:message code="lbl.provided" /></div>
			<div class="col-sm-4 text-left">
				<spring:message code="lbl.remarks" />
			</div>
		</div>
		<c:forEach var="docs" items="${docketDetailProposedWorkList}"
			varStatus="status">
			<div class="form-group">
				<div class="col-sm-5 add-margin check-text text-left">
					<c:out value="${docs.checkListDetail.description}" />
					<form:hidden
						id="docketDetailProposedWorkList${status.index}checkListDetail.id"
						path="docketDetailProposedWorkList[${status.index}].checkListDetail.id"
						value="${docs.checkListDetail.id}" />
					<form:hidden
						id="docketDetailProposedWorkList${status.index}checkListDetail.description"
						path="docketDetailProposedWorkList[${status.index}].checkListDetail.description"
						value="${docs.checkListDetail.description}" />
				</div>
				<div class="col-sm-3 add-margin text-left">
                    <c:choose>
                        <c:when test="${mode =='editinsp'}">
                            <c:forEach items="${planScrutinyValues}" var="inspnVal">
                                <div class="radio">
                                    <label><input type="radio" value="${inspnVal}" class="radioBtnClass"
                                                  name="docketDetailProposedWorkList[${status.index}].value"
                                            <c:if test="${inspnVal eq docs.value}"> checked="checked" </c:if> />${inspnVal.checkListVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${planScrutinyValues}" var="inspnVal">
                                <div class="radio">
                                    <label><input type="radio" value="${inspnVal}" class="radioBtnClass"
                                                  name="docketDetailProposedWorkList[${status.index}].value"
                                            <c:if test="${inspnVal eq 'NOT_APPLICABLE'}"> checked="checked" </c:if> />${inspnVal.checkListVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
					<form:errors path="docketDetailProposedWorkList[${status.index}].value"
								 cssClass="add-margin error-msg"/>
				</div>
				<div class="col-sm-4 add-margin text-left">
					<form:textarea class="form-control patternvalidation"
						data-pattern="alphanumericspecialcharacters" maxlength="255"
						id="docketDetailProposedWorkList${status.index}remarks" rows="3"
						path="docketDetailProposedWorkList[${status.index}].remarks" />
					<form:errors
						path="docketDetailProposedWorkList[${status.index}].remarks"
						cssClass="add-margin error-msg" />
				</div>
			</div>
		</c:forEach>
	</c:when>
</c:choose>


<c:choose>
	<c:when test="${!docketDetailWorkAsPerPlanList.isEmpty()}">
		<div class="panel-heading custom_form_panel_heading">
			<div class="panel-title"><spring:message code="lbl.work.started"/></div>
		</div>
		<div class="form-group view-content header-color hidden-xs">
			<div class="col-sm-5 text-left"><spring:message code="lbl.files" /></div>
			<div class="col-sm-3 text-left"><spring:message code="lbl.provided" /></div>
			<div class="col-sm-4 text-left">
				<spring:message code="lbl.remarks" />
			</div>
		</div>
		<c:forEach var="docs" items="${docketDetailWorkAsPerPlanList}"
			varStatus="status">
			<div class="form-group">
				<div class="col-sm-5 add-margin check-text text-left">
					<c:out value="${docs.checkListDetail.description}" />
					<form:hidden
						id="docketDetailWorkAsPerPlanList${status.index}checkListDetail.id"
						path="docketDetailWorkAsPerPlanList[${status.index}].checkListDetail.id"
						value="${docs.checkListDetail.id}" />
					<form:hidden
						id="docketDetailWorkAsPerPlanList${status.index}checkListDetail.description"
						path="docketDetailWorkAsPerPlanList[${status.index}].checkListDetail.description"
						value="${docs.checkListDetail.description}" />
				</div>
				<div class="col-sm-3 add-margin text-left">
                    <c:choose>
                        <c:when test="${mode =='editinsp'}">
                            <c:forEach items="${planScrutinyValues}" var="inspnVal">
                                <div class="radio">
                                    <label><input type="radio" value="${inspnVal}" class="radioBtnClass"
                                                  name="docketDetailWorkAsPerPlanList[${status.index}].value"
                                            <c:if test="${inspnVal eq docs.value}"> checked="checked" </c:if> />${inspnVal.checkListVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${planScrutinyValues}" var="inspnVal">
                                <div class="radio">
                                    <label><input type="radio" value="${inspnVal}" class="radioBtnClass"
                                                  name="docketDetailWorkAsPerPlanList[${status.index}].value"
                                            <c:if test="${inspnVal eq 'NOT_APPLICABLE'}"> checked="checked" </c:if> />${inspnVal.checkListVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
					<form:errors path="docketDetailWorkAsPerPlanList[${status.index}].value"
								 cssClass="add-margin error-msg"/>
				</div>
				<div class="col-sm-4 add-margin text-left">
					<form:textarea class="form-control patternvalidation"
						data-pattern="alphanumericspecialcharacters" maxlength="255"
						id="docketDetailWorkAsPerPlanList${status.index}remarks" rows="3"
						path="docketDetailWorkAsPerPlanList[${status.index}].remarks" />
					<form:errors
						path="docketDetailWorkAsPerPlanList[${status.index}].remarks"
						cssClass="add-margin error-msg" />
				</div>
			</div>
		</c:forEach>
	</c:when>
</c:choose>

<c:choose>
	<c:when test="${!docketDetailHgtAbuttRoadList.isEmpty()}">
		<div class="panel-heading custom_form_panel_heading">
			<div class="panel-title"><spring:message code="lbl.gen.prov.regrd.site"/></div>
		</div>
		<div class="form-group view-content header-color hidden-xs">
			<div class="col-sm-5 text-left"><spring:message code="lbl.files" /></div>
			<div class="col-sm-3 text-left"><spring:message code="lbl.provided" /></div>
			<div class="col-sm-4 text-left">
				<spring:message code="lbl.remarks" />
			</div>
		</div>
		<c:forEach var="docs" items="${docketDetailHgtAbuttRoadList}"
			varStatus="status">
			<div class="form-group">
				<div class="col-sm-5 add-margin check-text text-left">
					<c:out value="${docs.checkListDetail.description}" />
					<form:hidden
						id="docketDetailHgtAbuttRoadList${status.index}checkListDetail.id"
						path="docketDetailHgtAbuttRoadList[${status.index}].checkListDetail.id"
						value="${docs.checkListDetail.id}" />
					<form:hidden
						id="docketDetailHgtAbuttRoadList${status.index}checkListDetail.description"
						path="docketDetailHgtAbuttRoadList[${status.index}].checkListDetail.description"
						value="${docs.checkListDetail.description}" />
				</div>
				<div class="col-sm-3 add-margin text-left">
                    <c:choose>
                        <c:when test="${mode =='editinsp'}">
                            <c:forEach items="${planScrutinyValues}" var="inspnVal">
                                <div class="radio">
                                    <label><input type="radio" value="${inspnVal}" class="radioBtnClass"
                                                  name="docketDetailHgtAbuttRoadList[${status.index}].value"
                                            <c:if test="${inspnVal eq docs.value}"> checked="checked" </c:if> />${inspnVal.checkListVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${planScrutinyValues}" var="inspnVal">
                                <div class="radio">
                                    <label><input type="radio" value="${inspnVal}" class="radioBtnClass"
                                                  name="docketDetailHgtAbuttRoadList[${status.index}].value"
                                            <c:if test="${inspnVal eq 'NOT_APPLICABLE'}"> checked="checked" </c:if> />${inspnVal.checkListVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
					<form:errors path="docketDetailHgtAbuttRoadList[${status.index}].value"
								 cssClass="add-margin error-msg"/>
				</div>
				<div class="col-sm-4 add-margin text-left">
					<form:textarea class="form-control patternvalidation"
						data-pattern="alphanumericspecialcharacters" maxlength="255"
						id="docketDetailHgtAbuttRoadList${status.index}remarks" rows="3"
						path="docketDetailHgtAbuttRoadList[${status.index}].remarks" />
					<form:errors
						path="docketDetailHgtAbuttRoadList[${status.index}].remarks"
						cssClass="add-margin error-msg" />
				</div>
			</div>
		</c:forEach>
	</c:when>
</c:choose>

<c:choose>
	<c:when test="${!docketDetailAreaLoc.isEmpty()}">
		<div class="panel-heading custom_form_panel_heading">
			<div class="panel-title"><spring:message code="lbl.extnt.plot"/></div>
		</div>
		<div class="form-group view-content header-color hidden-xs">
			<div class="col-sm-5 text-left"><spring:message code="lbl.files" /></div>
			<div class="col-sm-3 text-left"><spring:message code="lbl.provided" /></div>
			<div class="col-sm-4 text-left">
				<spring:message code="lbl.remarks" />
			</div>
		</div>
		<c:forEach var="docs" items="${docketDetailAreaLoc}"
			varStatus="status">
			<div class="form-group">
				<div class="col-sm-5 add-margin check-text text-left">
					<c:out value="${docs.checkListDetail.description}" />
					<form:hidden
						id="docketDetailAreaLoc${status.index}checkListDetail.id"
						path="docketDetailAreaLoc[${status.index}].checkListDetail.id"
						value="${docs.checkListDetail.id}" />
					<form:hidden
						id="docketDetailAreaLoc${status.index}checkListDetail.description"
						path="docketDetailAreaLoc[${status.index}].checkListDetail.description"
						value="${docs.checkListDetail.description}" />
				</div>
				<div class="col-sm-3 add-margin text-left">
                    <c:choose>
                        <c:when test="${mode =='editinsp'}">
                            <c:forEach items="${planScrutinyValues}" var="inspnVal">
                                <div class="radio">
                                    <label><input type="radio" value="${inspnVal}" class="radioBtnClass"
                                                  name="docketDetailAreaLoc[${status.index}].value"
                                            <c:if test="${inspnVal eq docs.value}"> checked="checked" </c:if> />${inspnVal.checkListVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${planScrutinyValues}" var="inspnVal">
                                <div class="radio">
                                    <label><input type="radio" value="${inspnVal}" class="radioBtnClass"
                                                  name="docketDetailAreaLoc[${status.index}].value"
                                            <c:if test="${inspnVal eq 'NOT_APPLICABLE'}"> checked="checked" </c:if> />${inspnVal.checkListVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
					<form:errors path="docketDetailAreaLoc[${status.index}].value"
								 cssClass="add-margin error-msg"/>
				</div>
				<div class="col-sm-4 add-margin text-left">
					<form:textarea class="form-control patternvalidation"
						data-pattern="alphanumericspecialcharacters" maxlength="255"
						id="docketDetailAreaLoc${status.index}remarks" rows="3"
						path="docketDetailAreaLoc[${status.index}].remarks" />

					<form:errors
						path="docketDetailAreaLoc[${status.index}].remarks"
						cssClass="add-margin error-msg" />
				</div>
			</div>
		</c:forEach>
	</c:when>
</c:choose>

<c:choose>
	<c:when test="${!docketDetailLengthOfCompWall.isEmpty()}">
		<div class="panel-heading custom_form_panel_heading">
			<div class="panel-title"><spring:message code="lbl.len.wall"/></div>
		</div>
		<div class="form-group view-content header-color hidden-xs">
			<div class="col-sm-5 text-left"><spring:message code="lbl.files" /></div>
			<div class="col-sm-3 text-left"><spring:message code="lbl.value" /></div>
			<div class="col-sm-4 text-left">
				<spring:message code="lbl.remarks" />
			</div>
		</div>
		<c:forEach var="docs" items="${docketDetailLengthOfCompWall}"
			varStatus="status">
			<div class="form-group">
				<div class="col-sm-5 add-margin check-text text-left">
					<c:out value="${docs.checkListDetail.description}" />
					<form:hidden
						id="docketDetailLengthOfCompWall${status.index}checkListDetail.id"
						path="docketDetailLengthOfCompWall[${status.index}].checkListDetail.id"
						value="${docs.checkListDetail.id}" />
					<form:hidden
						id="docketDetailLengthOfCompWall${status.index}checkListDetail.description"
						path="docketDetailLengthOfCompWall[${status.index}].checkListDetail.description"
						value="${docs.checkListDetail.description}" />
				</div>
				<div class="col-sm-3 add-margin text-left">
                    <c:choose>
                        <c:when test="${mode =='editinsp'}">
                            <c:forEach items="${planScrutinyValues}" var="inspnVal">
                                <div class="radio">
                                    <label><input type="radio" value="${inspnVal}" class="radioBtnClass"
                                                  name="docketDetailLengthOfCompWall[${status.index}].value"
                                            <c:if test="${inspnVal eq docs.value}"> checked="checked" </c:if> />${inspnVal.checkListVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${planScrutinyValues}" var="inspnVal">
                                <div class="radio">
                                    <label><input type="radio" value="${inspnVal}" class="radioBtnClass"
                                                  name="docketDetailLengthOfCompWall[${status.index}].value"
                                            <c:if test="${inspnVal eq 'NOT_APPLICABLE'}"> checked="checked" </c:if> />${inspnVal.checkListVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
					<form:errors path="docketDetailLengthOfCompWall[${status.index}].value"
								 cssClass="add-margin error-msg"/>
				</div>
				<div class="col-sm-4 add-margin text-left">
					<form:textarea class="form-control patternvalidation"
						data-pattern="alphanumericspecialcharacters" maxlength="255"
						id="docketDetailLengthOfCompWall${status.index}remarks" rows="3"
						path="docketDetailLengthOfCompWall[${status.index}].remarks" />
					<form:errors
						path="docketDetailLengthOfCompWall[${status.index}].remarks"
						cssClass="add-margin error-msg" />
				</div>
			</div>
		</c:forEach>
	</c:when>
</c:choose>

<c:choose>
	<c:when test="${!docketDetailNumberOfWell.isEmpty()}">
		<div class="panel-heading custom_form_panel_heading">
			<div class="panel-title"><spring:message code="lbl.no.wells"/></div>
		</div>
		<div class="form-group view-content header-color hidden-xs">
			<div class="col-sm-5 text-left"><spring:message code="lbl.files" /></div>
			<div class="col-sm-3 text-left"><spring:message code="lbl.value" /></div>
			<div class="col-sm-4 text-left">
				<spring:message code="lbl.remarks" />
			</div>
		</div>
		<c:forEach var="docs" items="${docketDetailNumberOfWell}"
			varStatus="status">
			<div class="form-group">
				<div class="col-sm-5 add-margin check-text text-left">
					<c:out value="${docs.checkListDetail.description}" />
					<form:hidden
						id="docketDetailNumberOfWell${status.index}checkListDetail.id"
						path="docketDetailNumberOfWell[${status.index}].checkListDetail.id"
						value="${docs.checkListDetail.id}" />
					<form:hidden
						id="docketDetailNumberOfWell${status.index}checkListDetail.description"
						path="docketDetailNumberOfWell[${status.index}].checkListDetail.description"
						value="${docs.checkListDetail.description}" />
				</div>
				<div class="col-sm-3 add-margin text-left">
                    <c:choose>
                        <c:when test="${mode =='editinsp'}">
                            <c:forEach items="${planScrutinyValues}" var="inspnVal">
                                <div class="radio">
                                    <label><input type="radio" value="${inspnVal}" class="radioBtnClass"
                                                  name="docketDetailNumberOfWell[${status.index}].value"
                                            <c:if test="${inspnVal eq docs.value}"> checked="checked" </c:if> />${inspnVal.checkListVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${planScrutinyValues}" var="inspnVal">
                                <div class="radio">
                                    <label><input type="radio" value="${inspnVal}" class="radioBtnClass"
                                                  name="docketDetailNumberOfWell[${status.index}].value"
                                            <c:if test="${inspnVal eq 'NOT_APPLICABLE'}"> checked="checked" </c:if> />${inspnVal.checkListVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
					<form:errors path="docketDetailNumberOfWell[${status.index}].value"
								 cssClass="add-margin error-msg"/>
				</div>
				<div class="col-sm-4 add-margin text-left">
					<form:textarea class="form-control patternvalidation"
						data-pattern="alphanumericspecialcharacters" maxlength="255"
						id="docketDetailNumberOfWell${status.index}remarks" rows="3"
						path="docketDetailNumberOfWell[${status.index}].remarks" />
					<form:errors
						path="docketDetailNumberOfWell[${status.index}].remarks"
						cssClass="add-margin error-msg" />
				</div>
			</div>
		</c:forEach>
	</c:when>
</c:choose>

<c:choose>
	<c:when test="${!docketDetailShutter.isEmpty()}">
		<div class="panel-heading custom_form_panel_heading">
			<div class="panel-title"><spring:message code="lbl.shuter"/></div>
		</div>
		<div class="form-group view-content header-color hidden-xs">
			<div class="col-sm-5 text-left"><spring:message code="lbl.files" /></div>
			<div class="col-sm-3 text-left"><spring:message code="lbl.value" /></div>
			<div class="col-sm-4 text-left">
				<spring:message code="lbl.remarks" />
			</div>
		</div>
		<c:forEach var="docs" items="${docketDetailShutter}"
			varStatus="status">
			<div class="form-group">
				<div class="col-sm-5 add-margin check-text text-left">
					<c:out value="${docs.checkListDetail.description}" />
					<form:hidden
						id="docketDetailShutter${status.index}checkListDetail.id"
						path="docketDetailShutter[${status.index}].checkListDetail.id"
						value="${docs.checkListDetail.id}" />
					<form:hidden
						id="docketDetailShutter${status.index}checkListDetail.description"
						path="docketDetailShutter[${status.index}].checkListDetail.description"
						value="${docs.checkListDetail.description}" />
				</div>
				<div class="col-sm-3 add-margin text-left">
                    <c:choose>
                        <c:when test="${mode =='editinsp'}">
                            <c:forEach items="${planScrutinyValues}" var="inspnVal">
                                <div class="radio">
                                    <label><input type="radio" value="${inspnVal}" class="radioBtnClass"
                                                  name="docketDetailShutter[${status.index}].value"
                                            <c:if test="${inspnVal eq docs.value}"> checked="checked" </c:if> />${inspnVal.checkListVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${planScrutinyValues}" var="inspnVal">
                                <div class="radio">
                                    <label><input type="radio" value="${inspnVal}" class="radioBtnClass"
                                                  name="docketDetailShutter[${status.index}].value"
                                            <c:if test="${inspnVal eq 'NOT_APPLICABLE'}"> checked="checked" </c:if> />${inspnVal.checkListVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
					<form:errors path="docketDetailShutter[${status.index}].value"
								 cssClass="add-margin error-msg"/>
				</div>
				<div class="col-sm-4 add-margin text-left">
					<form:textarea class="form-control patternvalidation"
						data-pattern="alphanumericspecialcharacters" maxlength="255"
						id="docketDetailShutter${status.index}remarks" rows="3"
						path="docketDetailShutter[${status.index}].remarks" />
					<form:errors
						path="docketDetailShutter[${status.index}].remarks"
						cssClass="add-margin error-msg" />
				</div>
			</div>
		</c:forEach>
	</c:when>
</c:choose>

<c:choose>
	<c:when test="${!docketDetailRoofConversion.isEmpty()}">
		<div class="panel-heading custom_form_panel_heading">
			<div class="panel-title"><spring:message code="lbl.roof.conversn"/></div>
		</div>
		<div class="form-group view-content header-color hidden-xs">
			<div class="col-sm-5 text-left"><spring:message code="lbl.files" /></div>
			<div class="col-sm-3 text-left"><spring:message code="lbl.value" /></div>
			<div class="col-sm-4 text-left">
				<spring:message code="lbl.remarks" />
			</div>
		</div>
		<c:forEach var="docs" items="${docketDetailRoofConversion}"
			varStatus="status">
			<div class="form-group">
				<div class="col-sm-5 add-margin check-text text-left">
					<c:out value="${docs.checkListDetail.description}" />
					<form:hidden
						id="docketDetailRoofConversion${status.index}checkListDetail.id"
						path="docketDetailRoofConversion[${status.index}].checkListDetail.id"
						value="${docs.checkListDetail.id}" />
					<form:hidden
						id="docketDetailRoofConversion${status.index}checkListDetail.description"
						path="docketDetailRoofConversion[${status.index}].checkListDetail.description"
						value="${docs.checkListDetail.description}" />
				</div>
				<div class="col-sm-3 add-margin text-left">
                    <c:choose>
                        <c:when test="${mode =='editinsp'}">
                            <c:forEach items="${planScrutinyValues}" var="inspnVal">
                                <div class="radio">
                                    <label><input type="radio" value="${inspnVal}" class="radioBtnClass"
                                                  name="docketDetailRoofConversion[${status.index}].value"
                                            <c:if test="${inspnVal eq docs.value}"> checked="checked" </c:if> />${inspnVal.checkListVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${planScrutinyValues}" var="inspnVal">
                                <div class="radio">
                                    <label><input type="radio" value="${inspnVal}" class="radioBtnClass"
                                                  name="docketDetailRoofConversion[${status.index}].value"
                                            <c:if test="${inspnVal eq 'NOT_APPLICABLE'}"> checked="checked" </c:if> />${inspnVal.checkListVal}
                                    </label>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
					<form:errors path="docketDetailRoofConversion[${status.index}].value"
								 cssClass="add-margin error-msg"/>
				</div>
				<div class="col-sm-4 add-margin text-left">
					<form:textarea class="form-control patternvalidation"
						data-pattern="alphabetspecialcharacters" maxlength="255"
						id="docketDetailRoofConversion${status.index}remarks" rows="3"
						path="docketDetailRoofConversion[${status.index}].remarks" />
					<form:errors
						path="docketDetailRoofConversion[${status.index}].remarks"
						cssClass="add-margin error-msg" />
				</div>
			</div>
		</c:forEach>
	</c:when>
</c:choose>
<div class="form-group">
	<%--<label class="col-sm-3 control-label text-right"><spring:message
			code="lbl.dimensionofplot" /></label>
	<div class="col-sm-3 add-margin">
		<form:radiobutton path="boundaryDrawingSubmitted" value="true" checked="checked"/>
		<spring:message code="lbl.yes" />
		<form:radiobutton path="boundaryDrawingSubmitted" value="false" />
		<spring:message code="lbl.no" />
		<form:errors path="boundaryDrawingSubmitted"
			cssClass="add-margin error-msg" />
	</div>--%>
	<label class="col-sm-3 control-label text-right"><spring:message
			code="lbl.righttomake.construction" /></label>
	<div class="col-sm-3 add-margin">
		<form:radiobutton path="rightToMakeConstruction" value="true"
			checked="checked" />
		<spring:message code="lbl.yes" />
		<form:radiobutton path="rightToMakeConstruction" value="false" />
		<spring:message code="lbl.no" />
		<form:errors path="rightToMakeConstruction"
			cssClass="add-margin error-msg" />
	</div>
</div>

<div class="form-group">
	<%-- <label class="col-sm-3 control-label text-right"><spring:message
			code="lbl.typeofland" /></label>
	<div class="col-sm-3 add-margin">
		<form:input class="form-control patternvalidation" maxlength="120"
			data-pattern="alphanumeric" id="typeofLand" path="typeofLand" />
		<form:errors path="typeofLand" cssClass="add-margin error-msg" />
	</div> --%>
	<label class="col-sm-3 control-label text-right"><spring:message
			code="lbl.ins.remarks" /></label>
	<div class="col-sm-6 add-margin text-left">
		<form:textarea class="form-control patternvalidation"
			data-pattern="alphanumericspecialcharacters" maxlength="1000"
			id="inspectionRemarks" rows="3" path="inspectionRemarks" />

		<form:errors path="inspectionRemarks" cssClass="add-margin error-msg" />
	</div>
</div>
</div>

<script>
    jQuery(document).ready(function ($) {
        window.onunload = function () {
            window.opener.location.reload();
        };
    });
</script>