<%--
  ~    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
  ~    accountability and the service delivery of the government  organizations.
  ~
  ~     Copyright (C) 2018  eGovernments Foundation
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

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>

<div class="row" id="page-content">
	<div class="col-md-12">
		<form:form role="form" method="GET" modelAttribute="bpaFeeMapping"
			class="form-horizontal form-groups-bordered">
			<div class="panel panel-primary" data-collapsed="0">
				<div class="panel-heading">
					<div class="panel-title">
						<strong><spring:message code="title.fees.view" /></strong>
					</div>
				</div>
				<div class="panel-body custom-form">
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="lbl.fees.name" /></label>
						<div class="col-sm-3 add-margin">
							<form:label cssClass="form-control" path="bpaFeeCommon.name">${bpaFeeMapping.bpaFeeCommon.name}</form:label>
						</div>
						<label class="col-sm-2 control-label"><spring:message
								code="lbl.description" /></label>
						<div class="col-sm-3 add-margin">
							<form:label cssClass="form-control"
								path="bpaFeeCommon.description">${bpaFeeMapping.bpaFeeCommon.description}</form:label>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label text-right"><spring:message
								code="lbl.fees.accounthead" /></label>
						<div class="col-sm-3 add-margin">
							<form:label cssClass="form-control" path="bpaFeeCommon.glcode">${bpaFeeMapping.bpaFeeCommon.glcode.glcode}</form:label>
						</div>
						<label class="col-sm-2 control-label text-right"><spring:message
								code="lbl.fees.code" /></label>
						<div class="col-sm-3 add-margin">
							<form:label cssClass="form-control" path="bpaFeeCommon.code">${bpaFeeMapping.bpaFeeCommon.code}</form:label>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="lbl.fees.category" /></label>
						<div class="col-sm-3 add-margin">
							<form:label cssClass="form-control" path="bpaFeeCommon.category">${bpaFeeMapping.bpaFeeCommon.category.name}</form:label>
						</div>
					</div>
					<div class="panel-heading">
						<div class="col-md-12 panel-title text-left">
							<strong> <spring:message code="lbl.fees.details" />
							</strong>
						</div>
					</div>
					<div class="col-md-12">
						<table class="table table-bordered " id="subcat">
							<thead>
								<tr>
									<th class="text-center"><spring:message
											code='lbl.service.type' /></th>

									<th class="text-center"><spring:message
											code='lbl.applctn.type' /></th>
									<th class="text-center"><spring:message
											code='lbl.fee.subtype' /></th>
									<th class="text-center"><spring:message
											code='lbl.fees.calculationtype' /></th>
									<th class="text-center"><spring:message code='lbl.amount' /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${bpaFeeMapping.bpaFeeMapTemp}"
									var="bpaFeeDetail" varStatus="item">
									<tr>

										<td><form:label cssClass="form-control"
												path="bpaFeeMapTemp[${item.index}].serviceType">
                                        ${bpaFeeDetail.serviceType.description}
                                    </form:label></td>
										<td><form:label cssClass="form-control"
												path="bpaFeeMapTemp[${item.index}].applicationType">
                                        ${bpaFeeDetail.applicationType.feeApplicationTypeVal}
                                    </form:label></td>
										<td><form:label cssClass="form-control"
												path="bpaFeeMapTemp[${item.index}].feeSubType">
                                        ${bpaFeeDetail.feeSubType.feeSubTypeVal}
                                    </form:label></td>
										<td><form:label cssClass="form-control"
												path="bpaFeeMapTemp[${item.index}].calculationType">
                                        ${bpaFeeDetail.calculationType.toString()}
                                    </form:label></td>
										<td><form:label cssClass="form-control"
												path="bpaFeeMapTemp[${item.index}].amount">
                                        ${bpaFeeDetail.amount}
                                    </form:label></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
	</div>
	<div class="form-group">
		<div class="text-center">
			<a href='javascript:void(0)' class='btn btn-default'
				onclick='window.location="."'><spring:message code='lbl.back' /></a>
			<button type="button" class="btn btn-default" data-dismiss="modal"
				onclick="self.close()">
				<spring:message code="lbl.close" />
			</button>
		</div>
	</div>
	</form:form>
</div>
<script
	src="<cdn:url  value='/resources/js/app/bpafee.js?rnd=${app_release_no}'/>"></script>