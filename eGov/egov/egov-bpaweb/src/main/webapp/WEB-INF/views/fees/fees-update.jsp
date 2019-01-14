<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<div class="page-container">
	<div class="main-content">
		<form:form role="form" method="post" modelAttribute="bpaFee"
			id="updateBpaFeeDetailForm"
			cssClass="form-horizontal form-groups-bordered"
			enctype="multipart/form-data">
			<form:hidden path="" id="mode" name="mode" value="${mode}" />
			<div class="row">
				<div class="col-md-12">
					<div class="panel panel-primary" data-collapsed="0">
						<c:if test="${mode == 'view'}">
							<div class="panel-title">Fees Amount Updated Successfully.
							</div>
						</c:if>
						<div class="panel-heading">
							<div class="new-page-header">
								<spring:message code="lbl.bpaFee" />
							</div>
						</div>
						<div class="panel-body">
							<div class="text-left error-msg col-sm-6"
								style="font-size: 14px;">
								<spring:message code="lbl.service.type" />
								: ${bpaFee.serviceType.description}
							</div>

							<div class="text-right error-msg col-sm-6"
								style="font-size: 14px;">Fees Code :${bpaFee.code}</div>
							<table class="table table-striped table-bordered"
								id="feesDetails">
								<thead>
									<tr>
										<th class="text-center"><spring:message code="lbl.srl.no" /></th>
										<th class="text-center"><spring:message
												code="lbl.fees.type" /></th>
										<th class="text-center"><spring:message
												code="lbl.feestype.description" /></th>
										<th class="text-center"><spring:message
												code="lbl.feestype.additionaltype" /></th>
										<th class="text-center"><spring:message code="lbl.amount" /></th>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${!bpaFee.feeDetail.isEmpty()}">
											<c:forEach items="${bpaFee.feeDetail}" var="rec"
												varStatus="counter">
												<c:choose>
													<c:when test="${mode != 'view'}">
														<form:hidden id="feeDetailId${counter.index}"
															name="feeDetailId${counter.index}"
															path="feeDetail[${counter.index}].id" />
														<form:hidden id="feeId${counter.index}"
															name="feeId${counter.index}"
															path="feeDetail[${counter.index}].bpafee.id" />
														<tr class="data-fetched">
															<td class="text-center"><span class="serialNo"
																id="slNoFees">${counter.index+1}</span></td>
															<td><c:out value="${rec.bpafee.feeType}"></c:out></td>
															<td><c:out value="${rec.bpafee.description} "></c:out></td>
															<td><c:out value="${rec.additionalType} "></c:out></td>
															<td><form:input type="text"
																	class="form-control table-input text-right patternvalidation for-calculation clear-values"
																	data-pattern="number"
																	path="feeDetail[${counter.index}].amount"
																	id="feeDetailAmount${counter.index}"
																	required="true" maxlength="10"
																	value="${rec.amount}" /></td>
														</tr>
													</c:when>
													<c:otherwise>
														<tr class="data-fetched">
															<td class="text-center"><span class="serialNo"
																id="slNoFees">${counter.index+1}</span></td>
															<td><c:out value="${rec.bpafee.feeType}"></c:out></td>
															<td><c:out value="${rec.bpafee.description} "></c:out></td>
															<td><c:out value="${rec.additionalType} "></c:out></td>
															<td><c:out value="${rec.amount} "></c:out></td>
														</tr>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<div class="text-center error-msg col-sm-6"
												style="font-size: 14px;">No Fees Detail.</div>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>

					</div>
				</div>
			</div>

			<div class="text-center">
				<c:if test="${mode != 'view'}">
					<button id="buttonSubmit" class="btn btn-primary" type="submit">
						Update</button>
				</c:if>
				<a class="btn btn-default" onclick="self.close()"
					href="javascript:void(0)">Close</a>
			</div>

		</form:form>
	</div>
</div>
<script
	src="<c:url value='/resources/global/js/handlebars/handlebars.js?rnd=${app_release_no}' context='/egi'/>"></script>
	<script
	src="<cdn:url value='/resources/js/app/bpafee.js?rnd=${app_release_no}'/>"></script>