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
<div class="panel-heading custom_form_panel_heading">
	<div class="panel-title text-center no-float">
		<c:if test="${not empty message}">
			<strong>${message}</strong>
		</c:if>
	</div>
</div>
<div class="row">
	<div class="col-md-12">
		<form:form role="form" action="" method="post"
			modelAttribute="occupancyFee" id="occupancyFee"
			cssClass="form-horizontal form-groups-bordered"
			enctype="multipart/form-data">

			<div class="panel panel-primary" data-collapsed="0">
				<div class="panel-body custom-form ">
					<div class="panel-heading custom_form_panel_heading">
						<div class="panel-title">
							<spring:message code="lbl.appln.details" />
						</div>
					</div>
					<div class="panel-body">
						<div class="row add-border">
							<div class="col-sm-3 add-margin">
								<spring:message code="lbl.application.no" />
							</div>
							<div class="col-sm-3 add-margin view-content">
								<c:out value="${occupancyFee.oc.applicationNumber}"
									default="N/A"></c:out>
							</div>
							<div class="col-sm-3 add-margin">
								<spring:message code="lbl.application.date" />
							</div>
							<div class="col-sm-3 add-margin view-content">
								<c:out value="${occupancyFee.oc.applicationDate}"
									default="N/A"></c:out>
							</div>
						</div>
						<div class="row add-border">
							<div class="col-sm-3 add-margin">
								<spring:message code="lbl.service.type" />
							</div>
							<div class="col-sm-3 add-margin view-content">
								<c:out
									value="${occupancyFee.oc.parent.serviceType.description}"
									default="N/A"></c:out>
							</div>
							<div class="col-sm-3 add-margin">Amenity Type</div>
							<c:choose>
								<c:when test="${empty occupancyFee.oc.parent.amenityName}">
									<div class="col-sm-3 add-margin view-content">
										<c:out value="N/A"/>
									</div>
								</c:when>
								<c:otherwise>
									<div class="col-sm-3 add-margin view-content">
										<c:out value="${occupancyFee.oc.parent.amenityName}"/>
									</div>
								</c:otherwise>
							</c:choose>
						</div>
						<div class="row add-border">
							<div class="col-sm-3 add-margin">
								<spring:message code="lbl.admission.fees" />
							</div>
							<div class="col-sm-3 add-margin view-content">
								<c:out value="${occupancyFee.oc.parent.admissionfeeAmount}"
									default="N/A"></c:out>
							</div>
						</div>
					</div>
					<div class="panel-heading custom_form_panel_heading">
						<div class="panel-title">
							<spring:message code="lbl.ocfee.details" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-3 add-margin">
							<form:hidden path="oc" id="feeocid"
								value="${occupancyFee.oc.id}" />
							<form:hidden path="applicationFee.status" id="feeapplicationstatusid"
								value="${occupancyFee.applicationFee.status.id}" />
									<form:hidden path="applicationFee" id="feeapplicationid"
								value="${occupancyFee.applicationFee.id}" />
							<form:hidden path="id" id="id" value="${occupancyFee.id}" />

						</div>
					</div>

					<c:choose>
						<c:when test="${!applicationFeeDetail.isEmpty()}">
                            <table class="table table-striped table-bordered" id="bpafeedetail" style="width:50%;margin:0 auto;">
                                <thead>
                                <tr>
                                    <th class="text-center"><spring:message code="lbl.applicationFee.feeType"/></th>
                                    <th class="text-center"><spring:message code="lbl.applicationFee.amount"/></th>
                                </tr>
                                </thead>
                                <tbody id="tblBody">
                                <c:set var="totalAmount" value="${0}"/>
                                <c:forEach var="docs"
                                           items="${occupancyFee.applicationFee.applicationFeeDetail}"
                                           varStatus="status">
                                    <tr>
                                        <td class="text-left view-content"><c:out value="${docs.bpaFeeMapping.bpaFeeCommon.description}"/></td>
                                        <td class="text-right view-content"><c:set var="totalAmount"
                                                                                   value="${totalAmount + docs.amount}"/>
                                            <fmt:formatNumber type="number" maxFractionDigits="2" value="${docs.amount}"/>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                                <tfoot>
                                <tr>
                                    <td class="text-left view-content">Total Amount</td>
                                    <td class="text-right view-content"><fmt:formatNumber type="number" maxFractionDigits="2"
                                                                                          value="${totalAmount}"/></td>
                                </tr>
                                </tfoot>
                            </table>
						</c:when>
					</c:choose>
					
					<div class="form-group">
						<label class="col-sm-3 control-label text-center"><spring:message
								code="lbl.modify.ocfee.reason" /><span class="mandatory"></span></label>
						<div class="col-sm-7 add-margin text-left">
							<c:out value="${oc.applicationFee.modifyFeeReason}"
								   default="N/A"></c:out>
						</div>
					</div>
				</div>
			</div>
			<div class="text-center">
				<a href='javascript:void(0)' class='btn btn-default'
					onclick='self.close()'><spring:message code='lbl.close' /></a>
			</div>

		</form:form>
	</div>
</div>

<script
	src="<cdn:url value='/resources/global/js/egov/inbox.js?rnd=${app_release_no}' context='/egi'/>"></script>
