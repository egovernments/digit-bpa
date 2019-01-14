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

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<div class="row">
	<div class="col-md-12">
		<div class="panel-heading custom_form_panel_heading">
			<div class="panel-title">
				<spring:message code="lbl.document.scrutiny" />
			</div>
		</div>
		<div class="panel-body">
			<div class="row add-border">
				<div class="col-sm-3 add-margin">
					<spring:message code="lbl.re.survey.no" />
				</div>
				<div class="col-sm-3 add-margin view-content">
					<c:out value="${occupancyCertificate.documentScrutinies[0].docScrutiny.reSurveyNumber}"
						default="N/A"></c:out>
				</div>
				<div class="col-sm-3 add-margin">
					<spring:message code="lbl.nature.of.ownership" />
				</div>
				<div class="col-sm-3 add-margin view-content">
					<c:out value="${occupancyCertificate.documentScrutinies[0].docScrutiny.natureOfOwnership}"
						default="N/A"></c:out>
				</div>

			</div>
			<div class="row add-border">
				<div class="col-sm-3 add-margin extentOfLand">
					<spring:message code="lbl.extent.of.land" />
				</div>
				<div class="col-sm-3 add-margin areaOfBase">
					<spring:message code="lbl.area.base" />
				</div>
				<div class="col-sm-3 add-margin view-content">
					<fmt:formatNumber type="number" maxFractionDigits="2"
						value="${occupancyCertificate.documentScrutinies[0].docScrutiny.extentInSqmts}" />
				</div>
				<div class="col-sm-3 add-margin">
					<spring:message code="lbl.registraroffice" />
				</div>
				<div class="col-sm-3 add-margin view-content">
					<c:out
						value="${occupancyCertificate.documentScrutinies[0].docScrutiny.registrarOffice}"
						default="N/A"></c:out>
				</div>
			</div>
			<div class="row add-border">
				<div class="col-sm-3 add-margin">
					<spring:message code="lbl.deednumber" />
				</div>
				<div class="col-sm-3 add-margin view-content">
					<c:out value="${occupancyCertificate.documentScrutinies[0].docScrutiny.deedNumber}" default="N/A"></c:out>
				</div>
				<div class="col-sm-3 add-margin">
					<spring:message code="lbl.taluk" />
				</div>
				<div class="col-sm-3 add-margin view-content">
					<c:out value="${occupancyCertificate.documentScrutinies[0].docScrutiny.taluk}" default="N/A"></c:out>
				</div>
			</div>
			<div class="row add-border">
				<div class="col-sm-3 add-margin">
					<spring:message code="lbl.district" />
				</div>
				<div class="col-sm-3 add-margin view-content">
					<c:out value="${occupancyCertificate.documentScrutinies[0].docScrutiny.district}" default="N/A"></c:out>
				</div>
			</div>
			<div class="row add-border">
				<div class="col-sm-3 add-margin">
					<spring:message code="lbl.detailof.neigbour" />
				</div>
				<div class="col-sm-3 add-margin view-content">
					<c:out
							value="${occupancyCertificate.documentScrutinies[0].docScrutiny.neighbourOwnerDtlSubmitted.checkListVal}"
							default="N/A"></c:out>
				</div>
				<div class="col-sm-3 add-margin">
					<spring:message code="lbl.various.doc.matching" />
				</div>
				<div class="col-sm-3 add-margin view-content">
					<c:out
							value="${occupancyCertificate.documentScrutinies[0].docScrutiny.whetherDocumentMatch.checkListVal}"
							default="N/A"></c:out>
				</div>
			</div>
			<div class="row add-border">
				<div class="col-sm-3 add-margin">
					<spring:message code="lbl.alldocument.attached" />
				</div>
				<div class="col-sm-3 add-margin view-content">
					<c:out
						value="${occupancyCertificate.documentScrutinies[0].docScrutiny.whetherAllDocAttached.checkListVal}"
						default="N/A"></c:out>
				</div>
				<div class="col-sm-3 add-margin">
					<spring:message code="lbl.allpage.attached" />
				</div>
				<div class="col-sm-3 add-margin view-content">
					<c:out
						value="${occupancyCertificate.documentScrutinies[0].docScrutiny.whetherAllPageOfDocAttached.checkListVal}"
						default="N/A"></c:out>
				</div>
			</div>
		</div>
	</div>
</div>
