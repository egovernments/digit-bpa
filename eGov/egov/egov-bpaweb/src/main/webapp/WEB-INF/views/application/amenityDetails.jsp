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


	<div class="form-group">
		<label class="col-sm-3 control-label text-right extentOfLand"><spring:message
				code="lbl.extent.of.land" /> <span class="mandatory"></span> </label>
		<label class="col-sm-3 control-label text-right areaOfBase"><spring:message
				code="lbl.area.base" /> <span class="mandatory"></span> </label>
		<div class="col-sm-3 add-margin">
			<form:input
				class="form-control patternvalidation for-calculation dcr-calculation decimalfixed nonzero clear-values"
				maxlength="10" data-pattern="decimalvalue" id="extentOfLand"
				required="required" path="siteDetail[0].extentOfLand" />
			<form:errors path="siteDetail[0].extentOfLand"
				cssClass="add-margin error-msg" />

			<form:select path="siteDetail[0].unitOfMeasurement"
				data-first-option="false" id="unitOfMeasurement"
				cssClass="form-control for-calculation dcr-calculation" required="required">
				<form:options items="${uomList}" />
			</form:select>
			<form:errors path="siteDetail[0].unitOfMeasurement"
				cssClass="add-margin error-msg" />
		</div>
		<label class="col-sm-2 control-label text-right"><spring:message
				code="lbl.extentin.sqmts" /></label>
		<div class="col-sm-3 add-margin">
			<form:hidden id="extentinsqmtshdn" path="siteDetail[0].extentinsqmts" />
			<form:input class="form-control patternvalidation decimalfixed extentinsqmts"
				data-pattern="decimalvalue" id="extentinsqmts" disabled="true"
				path="siteDetail[0].extentinsqmts" />
			<form:errors path="siteDetail[0].extentinsqmts"
				cssClass="add-margin error-msg" />
		</div>
	</div>


	<div id="amenitiesInputs"></div>

	<script id="poles-template" type="text/egov-template">
	<label class="{className} control-label text-right toggle-madatory Pole"><spring:message
			code="lbl.no.of.poles" /><span class="mandatory"></span></label>
	<div class="col-sm-3 add-margin">
		<form:input class="form-control patternvalidation dyn-mandatory nonzero Pole"
			data-pattern="number" id="noOfPoles" maxlength="2"
			path="siteDetail[0].noOfPoles" required="required" />
		<form:errors path="siteDetail[0].noOfPoles"
			cssClass="add-margin error-msg" />
	</div>
</script>

	<script id="sheds-template" type="text/egov-template">
	<label class="{className} control-label text-right noofhutorshed"><spring:message
			code="lbl.no.of.shuts.huts" /><span class="mandatory"></span></label>
	<div class="col-sm-3 add-margin">
		<form:input class="form-control patternvalidation nonzero noofhutorshed"
			data-pattern="number" id="noOfHutOrSheds" maxlength="2"
			path="siteDetail[0].noOfHutOrSheds" required="required" />
		<form:errors path="siteDetail[0].noOfHutOrSheds"
			cssClass="add-margin error-msg" />
	</div>
</script>

	<script id="shutter-template" type="text/egov-template">
	<label class="{className} control-label text-right toggle-madatory Shut"><spring:message
			code="lbl.shutter" /><span class="mandatory"></span></label>
	<div class="col-sm-3 add-margin">
		<form:input class="form-control patternvalidation dyn-mandatory nonzero Shut" maxlength="2"
			id="shutter" data-pattern="number" path="siteDetail[0].shutter" required="required"/>
		<form:errors path="siteDetail[0].shutter"
			cssClass="add-margin error-msg" />
	</div>
</script>

	<script id="tower-template" type="text/egov-template">
	<label class="{className} control-label text-right toggle-madatory Towe"><spring:message
			code="lbl.erection.tower" /> <span class="mandatory"></span></label>
	<div class="col-sm-3 add-margin">
		<form:input class="form-control patternvalidation dyn-mandatory nonzero Towe"
			data-pattern="number" id="erectionoftower" maxlength="2"
			path="siteDetail[0].erectionoftower" required="required" />
		<form:errors path="siteDetail[0].erectionoftower"
			cssClass="add-margin error-msg" />
	</div>
</script>

	<script id="well-template" type="text/egov-template">
	<label class="{className} control-label text-right toggle-madatory Well"><spring:message
			code="lbl.number.well" /><span class="mandatory"></span></label>
	<div class="col-sm-3 add-margin">
		<form:input class="form-control patternvalidation dyn-mandatory nonzero Well"
			data-pattern="number" id="dwellingunitnt" maxlength="3"
			path="siteDetail[0].dwellingunitnt" required="required" />
		<form:errors path="siteDetail[0].dwellingunitnt"
			cssClass="add-margin error-msg" />
	</div>
</script>

	<script id="compound-template" type="text/egov-template">
	<label class="{className} control-label text-right toggle-madatory Comp"><spring:message
			code="lbl.len.com.wall" /><span class="mandatory"></span></label>
	<div class="col-sm-3 add-margin">
		<form:input class="form-control patternvalidation dyn-mandatory decimalfixed nonzero Comp"
			data-pattern="decimalvalue" id="siteDetail[0].lengthOfCompoundWall" maxlength="8"
			path="siteDetail[0].lengthOfCompoundWall" required="required" />
		<form:errors path="siteDetail[0].lengthOfCompoundWall"
			cssClass="add-margin error-msg" />
	</div>
</script>

	<script id="roof-template" type="text/egov-template">
	<label class="{className} control-label text-right toggle-madatory Roof"><spring:message
			code="lbl.roof.conv" /><span class="mandatory"></span></label>
	<div class="col-sm-3 add-margin">
		<form:input class="form-control patternvalidation dyn-mandatory decimalfixed nonzero Roof"
			data-pattern="decimalvalue" id="roofConversion" maxlength="10"
			path="siteDetail[0].roofConversion" required="required" />
		<form:errors path="siteDetail[0].roofConversion"
			cssClass="add-margin error-msg" />
	</div>
</script>

