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

<%@page import="org.python.modules.jarray"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>


<input type="hidden" id="serviceType"
	value="${bpaApplication.serviceType.description}" />
<input type="hidden" id="applicationAmenity"
	value="${bpaApplication.amenityName}" />

<div class="row add-border">
	<div class="col-sm-3 add-margin extentOfLand">
		<spring:message code="lbl.extent.of.land" />
	</div>
	<div class="col-sm-3 add-margin areaOfBase">
		<spring:message code="lbl.area.base" />
	</div>
	<div class="col-sm-3 add-margin view-content">
		<fmt:formatNumber type="number" maxFractionDigits="2"
				value="${bpaApplication.siteDetail[0].extentOfLand}" />
		<c:out value="${bpaApplication.siteDetail[0].unitOfMeasurement}"></c:out>
	</div>
	<div class="col-sm-3 add-margin">
		<spring:message code="lbl.extentin.sqmts" />
	</div>
	<div class="col-sm-3 add-margin view-content">
		<fmt:formatNumber type="number" maxFractionDigits="2"
			value="${bpaApplication.siteDetail[0].extentinsqmts}" />
	</div>
</div>
<div id="amenitiesOuptuts"></div>

<script id="roof-view-template" type="text/egov-template">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.roof.conv" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<fmt:formatNumber type="number" maxFractionDigits="2" value="${bpaApplication.siteDetail[0].roofConversion}" />
		</div>
</script>

<script id="compound-view-template" type="text/egov-template">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.len.com.wall" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<fmt:formatNumber type="number" maxFractionDigits="2" value="${bpaApplication.siteDetail[0].lengthOfCompoundWall}" />
		</div>
</script>

<script id="well-view-template" type="text/egov-template">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.number.well" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].dwellingunitnt}"
				default="N/A"></c:out>
		</div>
</script>

<script id="shutter-view-template" type="text/egov-template">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.shutter" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].shutter}" default="N/A"></c:out>
		</div>
</script>

<script id="tower-view-template" type="text/egov-template">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.erection.tower" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].erectionoftower}"
				default="N/A"></c:out>
		</div>
</script>

<script id="poles-view-template" type="text/egov-template">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.no.of.poles" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].noOfPoles}"
				default="N/A"></c:out>
		</div>
</script>

<script id="sheds-view-template" type="text/egov-template">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.no.of.shuts.huts" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${bpaApplication.siteDetail[0].noOfHutOrSheds}"
				default="N/A"></c:out>
		</div>
</script>
