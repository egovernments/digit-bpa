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

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>

<div class="panel-heading custom_form_panel_heading">
    <div class="panel-title">
        <spring:message code="title.block.occupancy.usage"/>
    </div>
</div>
<div class="panel-body">
    <c:choose>
        <c:when test="${not empty bpaApplication.buildingSubUsages}">
            <div>
                <c:forEach items="${bpaApplication.buildingSubUsages}" var="subUsage" varStatus="counter">
                    <div style="display: block;float:left;width:100%;height:auto;padding-bottom:10px;">
                        <h5 class="view-content">Block - ${subUsage.blockNumber}</h5>
                        <div class=form-group" id="block${subUsage.blockNumber}">
                            <c:forEach items="${subUsage.subUsageDetails}" var="subUsageDtl" varStatus="counter1">
                                <div class="col-sm-3 col-md-3">
                                    <h5 class="view-content">${subUsageDtl.mainUsage.additionalDescription}<span class="mandatory"></span></h5>
                                    <select name="buildingSubUsages[${counter.index}].subUsageDetails[${counter1.index}].subUsagesTemp"
                                            multiple
                                            class="form-control occupancySubUsages${subUsage.blockNumber} tick-indicator"
                                            required="required">
                                        <c:forEach items="${subUsageDtl.subUsagesTemp}" var="usage">
                                            <option value="${usage.id}" title="${usage.description}" <c:if
                                                    test="${fn:contains(subUsageDtl.subUsages, usage)}"> Selected </c:if>>${usage.description}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <div id="blockAndOccupancyInputs">
                <div id="block1-desc" class="view-content"></div>
                <div id="block1" class="form-group">

                </div>
                <div id="block2-desc" class="view-content"></div>
                <div id="block2" class="form-group">

                </div>
                <div id="block3-desc" class="view-content"></div>
                <div id="block3" class="form-group">

                </div>
                <div id="block4-desc" class="view-content"></div>
                <div id="block4" class="form-group">

                </div>
                <div id="block5-desc" class="view-content"></div>
                <div id="block5" class="form-group">

                </div>

                <div id="block6-desc" class="view-content"></div>
                <div id="block6" class="form-group">

                </div>
                <div id="block7-desc" class="view-content"></div>
                <div id="block7" class="form-group">

                </div>
                <div id="block8-desc" class="view-content"></div>
                <div id="block8" class="form-group">

                </div>
                <div id="block9-desc" class="view-content"></div>
                <div id="block9" class="form-group">

                </div>
                <div id="block10-desc view-content"></div>
                <div id="block10" class="form-group">

                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>