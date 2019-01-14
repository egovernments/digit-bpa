<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<div class="page-container">
	<div class="main-content">
		<form:form role="form" modelAttribute="bpaFee"
			id="viewBpaFeeDetailForm"
			cssClass="form-horizontal form-groups-bordered"
			enctype="multipart/form-data">
			<div class="row">
				<div class="col-md-12">
					<div class="panel panel-primary" data-collapsed="0">
						<div class="panel-heading">
							<div class="panel-title">
								<spring:message code="lbl.bpaFee" />
							</div>
						</div>
						<div class="panel-body">
							<table class="table table-striped table-bordered"
								id="feesDetails">
								<thead>
									<tr>
										<th class="text-center"><spring:message code="lbl.srl.no" /></th>
										<th class="text-center"><spring:message
												code="lbl.service.type" /></th>
										<th class="text-center"><spring:message
												code="lbl.fees.type" /></th>
										<th class="text-center"><spring:message
												code="lbl.fees.code" /></th>
										<th class="text-center"><spring:message code="lbl.action" /></th>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${!activeBpaFees.isEmpty()}">
											<c:forEach items="${activeBpaFees}" var="rec"
												varStatus="counter">
												<form:hidden id="feeId${counter.index+1}"
													name="feeId${counter.index+1}" value="${rec.id}" path="" />
												<tr class="data-fetched">
													<td class="text-center"><span class="serialNo"
														id="slNoFees">${counter.index+1}</span></td>
													<td><c:out value="${rec.serviceType.description}"></c:out></td>
													<td><c:out
															value="${rec.feeType} - ${rec.description} "></c:out></td>
													<td><c:out value="${rec.code} "></c:out></td>
													<td class=" text-center"><a href='javascript:void(0)'
														class='btn btn-xs btn-secondary edit'
														onclick='redirectToUpdateFeeDetail(${rec.id})'><span
															class="glyphicon glyphicon-edit"></span> Edit</a></td>
												</tr>
											</c:forEach>
										</c:when>
									</c:choose>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>
<<script type="text/javascript">
function redirectToUpdateFeeDetail(feeId){
	var url = "/bpa/fees/update/"+feeId;
	window.open(url,'_self','scrollbars=yes,resizable=yes,height=700,width=800,status=yes');
}
</script>
<script
	src="<c:url value='/resources/global/js/handlebars/handlebars.js?rnd=${app_release_no}' context='/egi'/>"></script>