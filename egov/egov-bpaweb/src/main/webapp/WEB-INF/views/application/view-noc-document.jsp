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


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>

<div class="panel-heading">
	<div class="panel-title"><spring:message code="lbl.tittle.noc.status" /> </div>
</div>

<div class="panel-body custom">
	<table class="table table-bordered  multiheadertbl"  id="bpaupdatenocdetails">
		<thead>
			<tr>
				<th><spring:message code="lbl.srl.no" /></th>
				<th><spring:message code="lbl.department" /></th>
				<th><spring:message code="lbl.nature.noc.req" /></th>
				<th><spring:message code="lbl.noc.initiated.date" /></th>
				<th><spring:message code="lbl.noc.approved.date" /></th>
				<th><spring:message code="lbl.noc.status" /></th>
				<th><spring:message code="lbl.remarks" /></th>
				<th><spring:message code="lbl.files" /></th>
				<c:if test="${not empty nocConfigMap}">
				<th class="thbtn" style="display: none"><spring:message code="lbl.action.noc" /></th>
				</c:if>
				<c:if test="${not empty nocApplication}">								
					<th class="thstatus" style="display: none"><spring:message code="lbl.noc.dept.status" /></th>		
					<th class="thsla" style="display: none"><spring:message code="lbl.sla.enddate" /></th>		
					<th class="thda" style="display: none"><spring:message code="lbl.deemed.approved.date" /></th>									
				</c:if>
			</tr>
		</thead>
		<tbody>

			<c:forEach var="nocdoc"
				items="${bpaApplication.permitNocDocuments}" varStatus="status">
				<tr>
					<td class="view-content text-center" style="font-size: 97%;"><c:out value="${status.index+1}"></c:out></td>
					<td width="15%" class="view-content" style="font-size: 97%;"><c:out value="${nocdoc.nocDocument.serviceChecklist.checklist.description}" default="N/A"></c:out></td>
					<td class="view-content text-justify" style="font-size: 97%;"><c:out value="${nocdoc.nocDocument.natureOfRequest}" default="N/A"></c:out></td>
					<c:choose>
						<c:when test="${not empty nocApplication}">
							<td class="view-content" style="font-size: 97%;"><c:if
									test="${nocdoc.permitNoc.bpaNocApplication.createdDate eq null}">
									<c:out value="N/A"></c:out>
								</c:if> <fmt:formatDate value="${nocdoc.permitNoc.bpaNocApplication.createdDate}"
									pattern="dd/MM/yyyy"></fmt:formatDate>
									</td>
						</c:when>
						<c:otherwise>
							<td class="view-content" style="font-size: 97%;"><c:if
									test="${nocdoc.nocDocument.letterSentOn eq null}">
									<c:out value="N/A"></c:out>
								</c:if> <fmt:formatDate value="${nocdoc.nocDocument.letterSentOn}"
									pattern="dd/MM/yyyy"></fmt:formatDate></td>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${not empty nocApplication}">
							<td class="view-content" style="font-size: 97%;"><c:if
									test="${nocdoc.permitNoc.bpaNocApplication.deemedApprovedDate eq null}">
									<c:out value="N/A"></c:out>
								</c:if> <fmt:formatDate value="${nocdoc.permitNoc.bpaNocApplication.deemedApprovedDate}"
									pattern="dd/MM/yyyy"></fmt:formatDate>
									</td>
						</c:when>
						<c:otherwise>
							<td class="view-content" style="font-size: 97%;"><c:if
									test="${nocdoc.nocDocument.replyReceivedOn eq null}">
									<c:out value="N/A"></c:out>
								</c:if> <fmt:formatDate value="${nocdoc.nocDocument.replyReceivedOn}"
									pattern="dd/MM/yyyy"></fmt:formatDate></td>
						</c:otherwise>
					</c:choose>
					<td class="view-content" style="font-size: 97%;"><c:out value="${nocdoc.nocDocument.nocStatus.nocStatusVal}" default="N/A"></c:out></td>
					<td class="view-content text-justify" style="font-size: 97%;"><c:out value="${nocdoc.nocDocument.remarks}" default="N/A"></c:out></td>
					<td class="view-content" style="font-size: 97%;"><c:set value="false" var="isDocFound"></c:set> <c:forEach
							var="bpanoc" items="${nocdoc.nocDocument.nocSupportDocs}" varStatus="loop">
						<c:if test="${bpanoc.fileStoreId ne null}">
							<c:set value="true" var="isDocFound"></c:set>
							<a target="_blank" href="/bpa/application/downloadfile/${bpanoc.fileStoreId}"
							   data-gallery>${loop.index +1} - ${bpanoc.fileName} </a>
							<c:if test="${!loop.last}">,</c:if>&nbsp;
						</c:if>
					</c:forEach> <c:if test="${!isDocFound}">
						N/A
					</c:if></td>
					<%-- <c:if test="${not empty nocConfigMap}">
					<td>
						<div class="text-right add-padding nocbutton">
							<c:set var="noccode"
								value="${nocdoc.nocDocument.serviceChecklist.checklist.code}" />
							<c:set var="nocbtn" value="${nocConfigMap[noccode]}" />
							<c:if test="${nocbtn eq 'initiate'}">
								<button type="button" value="/bpa/nocapplication/create/${noccode}" class="btn btn-secondary"
									id="btninitiatenoc">
									<spring:message code="lbl.initiate.noc" />
								</button>
							</c:if>
						</div>
					</td>
					</c:if> --%>
					<c:forTokens var="splittedString"
							items="${nocdoc.nocDocument.serviceChecklist.checklist.description}"
							delims="\ ()" varStatus="stat">
							<c:set var="checklistName"
								value="${stat.first ? '' : checklistName}_${splittedString}" />
						</c:forTokens>
						<c:if test="${not empty nocConfigMap}">
						<td class="tdbtn" style="display:none" >
							<div class="text-right">
								<c:set var="noccode"
									value="${nocdoc.nocDocument.serviceChecklist.checklist.code}" />
								<c:set var="nocbtn" value="${nocConfigMap[noccode]}" />
								<c:set var="nocapp" value="${nocTypeApplMap[noccode]}" />
								<input type="hidden" id="nocchkcode" value="${noccode}"/>
								<c:if test="${nocbtn eq 'initiate' && nocapp ne 'initiated'}">
								<c:out value="${nocapp}"/>
								<button type="button" id="btninitiatenoc" value="/bpa/nocapplication/create/${noccode}"  class="btn btn-secondary btn${checklistName}">
										<spring:message code="lbl.initiate.noc" />
								</button>
								</c:if>
							</div>
						</td>
					</c:if>	
					<c:if test="${not empty nocApplication}">								  			
						<td class="view-content tdstatus" style="font-size: 97%;">												
							  <fmt:formatDate value="${nocdoc.permitNoc.bpaNocApplication.lastModifiedDate}"
								pattern="dd/MM/yyyy" var="applicationDate" />
								<a
		                            style="cursor: pointer; font-size: 12px;"
		                             onclick="window.open('/bpa/nocapplication/view/${nocdoc.permitNoc.bpaNocApplication.nocApplicationNumber}','view','width=600, height=400,scrollbars=yes')">
		                             ${nocdoc.permitNoc.bpaNocApplication.nocApplicationNumber}
	                            </a><br/>						  
									${nocdoc.permitNoc.bpaNocApplication.status.code} <br/>
									${applicationDate} <br />
									<c:if test="${not empty nocdoc.permitNoc.bpaNocApplication.remarks}">																	
									  Remarks : ${nocdoc.permitNoc.bpaNocApplication.remarks}
								    </c:if>									
						</td>
						<td class="view-content tdsla" style="font-size: 97%;">	
							  <fmt:formatDate value="${nocdoc.permitNoc.bpaNocApplication.slaEndDate}"
								pattern="dd/MM/yyyy" var="slaDate" />
								<span style="font-weight:bold">${slaDate}<br />	</span>		
						</td>
                        <td class="view-content tdda" style="font-size: 97%;">					
							  <fmt:formatDate value="${nocdoc.permitNoc.bpaNocApplication.deemedApprovedDate}"
								pattern="dd/MM/yyyy" var="dadate" />
								<span style="font-weight:bold">${dadate}<br /> </span>		
						</td></c:if>				
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<input type="hidden" id="applicationNo" value="${bpaApplication.applicationNumber}"/>
<input type="hidden" id="isPermitApplFeeReq" value="${isPermitApplFeeReq}"/>
<input type="hidden" id="permitApplFeeCollected" value="${permitApplFeeCollected}"/>
<input type="hidden" id="nocAppl" value="${nocApplication}"/>
<input type="hidden" id="nocUserExists" value="${nocUserExists}"/>
<input type="hidden" id="citizenOrBusinessUser" value="${citizenOrBusinessUser}"/>
<input type="hidden" id="nocStatusUpdated" value="${nocStatusUpdated}"/>
<script
	src="<cdn:url value='/resources/js/app/noc-helper.js?rnd=${app_release_no}'/>"></script>