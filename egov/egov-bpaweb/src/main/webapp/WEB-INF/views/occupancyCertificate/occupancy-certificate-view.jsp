<%--
  ~ eGov suite of products aim to improve the internal efficiency,transparency,
  ~      accountability and the service delivery of the government  organizations.
  ~
  ~       Copyright (C) <2017>  eGovernments Foundation
  ~
  ~       The updated version of eGov suite of products as by eGovernments Foundation
  ~       is available at http://www.egovernments.org
  ~
  ~       This program is free software: you can redistribute it and/or modify
  ~       it under the terms of the GNU General Public License as published by
  ~       the Free Software Foundation, either version 3 of the License, or
  ~       any later version.
  ~
  ~       This program is distributed in the hope that it will be useful,
  ~       but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~       GNU General Public License for more details.
  ~
  ~       You should have received a copy of the GNU General Public License
  ~       along with this program. If not, see http://www.gnu.org/licenses/ or
  ~       http://www.gnu.org/licenses/gpl.html .
  ~
  ~       In addition to the terms of the GPL license to be adhered to in using this
  ~       program, the following additional terms are to be complied with:
  ~
  ~           1) All versions of this program, verbatim or modified must carry this
  ~              Legal Notice.
  ~
  ~           2) Any misrepresentation of the origin of the material is prohibited. It
  ~              is required that all modified versions of this material be marked in
  ~              reasonable ways as different from the original version.
  ~
  ~           3) This license does not grant any rights to any user of the program
  ~              with regards to rights under trademark law for use of the trade names
  ~              or trademarks of eGovernments Foundation.
  ~
  ~     In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
  --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>
<div class="row">
    <div class="col-md-12">
        <form:form role="form" action="../update-submit" method="post"
                   modelAttribute="occupancyCertificate" id="occupancyCertificateUpdateForm"
                   cssClass="form-horizontal form-groups-bordered"
                   enctype="multipart/form-data">

            <c:if
                    test="${isFeeCollected && occupancyCertificate.status.code eq 'Approved'}">
                <div data-collapsed="0">
                    <div class="panel-heading">
                        <div style="color: red; font-size: 16px;" align="center">
                            <spring:message code="lbl.collect.bpaFee"/>
                        </div>
                    </div>
                </div>
            </c:if>

            <input type="hidden" id="workFlowAction" name="workFlowAction"/>
            <input type="hidden" id="wfstateDesc" value="${occupancyCertificate.state.value}"/>
            <input type="hidden" id="occupancyCertificateId" name="occupancyCertificate" value="${occupancyCertificate.id}">
            <form:hidden path="id" value="${occupancyCertificate.id}"/>
            <input type="hidden" id="isFeeCollectionPending" value="${isFeeCollected}">
            <input type="hidden" id="status" value="${occupancyCertificate.status.code}">
            <input type="hidden" id="wfstate" value="${occupancyCertificate.state.id}"/>
            <input type="hidden" id="workFlowAction" name="workFlowAction"/>
            <input type="hidden" id="serviceTypeCode" value="${occupancyCertificate.parent.serviceType.code}"/>
            <input type="hidden" id="wfstateDesc" value="${occupancyCertificate.state.value}"/>
            <input type="hidden" id="collectFeeValidate" value="${collectFeeValidate}"/>
            <input type="hidden" id="mode" name="mode" value="${mode}"/>
            <input type="hidden" id="amountRule" name="amountRule" value="${amountRule}"/>
            <input type="hidden" id="scheduleType" name="scheduleType" value="${scheduleType}"/>
            <input type="hidden" id="showPermitConditions" value="${showPermitConditions}"/>
            <form:hidden path="sentToPreviousOwner" id="sentToPreviousOwner"/>
            <form:hidden path="LPRequestInitiated" id="lpRequestInitiated"
                         value="${occupancyCertificate.LPRequestInitiated}"/>
            <input type="hidden" id="approveComments" value="${occupancyCertificate.state.comments}"/>
            <input type="hidden" id="captureTSRemarks" value="${captureTSRemarks}">
            <ul class="nav nav-tabs" id="settingstab">
                <li class="active"><a data-toggle="tab"
                                      href="#application-info" data-tabidx=0><spring:message
                        code='lbl.appln.details'/></a></li>
                <li><a data-toggle="tab" href="#document-info" data-tabidx=1><spring:message
                        code='title.documentdetail'/></a></li>
                <c:if test="${showUpdateNoc}">
                    <li><a data-toggle="tab" href="#noc-document-info" data-tabidx=2><spring:message
                            code='lbl.noc.doc.details'/></a></li>
                </c:if>
                <c:if test="${!showUpdateNoc && not empty occupancyCertificate.nocDocuments}">
                    <li><a data-toggle="tab" href="#noc-info" data-tabidx=3><spring:message
                            code='lbl.noc.details'/></a></li>
                </c:if>
                <c:if test="${not empty occupancyCertificate.documentScrutinies}">
                    <li><a data-toggle="tab" href="#doc-scrnty" data-tabidx=4><spring:message
                            code='lbl.document.scrutiny'/></a></li>
                </c:if>
                <c:if test="${not empty occupancyCertificate.inspections}">
                    <li><a data-toggle="tab" href="#view-inspection" data-tabidx=5><spring:message
                            code='lbl.inspection.appln'/></a></li>
                </c:if>
                <c:if test="${captureTSRemarks}">
                    <li><a data-toggle="tab" href="#ts-remarks"
                           data-tabidx=6><spring:message code='lbl.town.surveyor.remarks' /></a></li>
                </c:if>
                <c:if test="${not empty letterToPartyList}">
                    <li><a data-toggle="tab" href="#view-lp" data-tabidx=7><spring:message
                            code='lbl.lp.details'/></a></li>
                </c:if>
            </ul>
            <div class="tab-content">
                <div id="application-info" class="tab-pane fade in active">
                    <div class="panel panel-primary" data-collapsed="0">
                        <jsp:include page="view-oc-application-details.jsp"></jsp:include>
                    </div>
                    <div class="panel panel-primary" data-collapsed="0">
                        <jsp:include page="view-bpa-basic-application-details.jsp"></jsp:include>
                    </div>
                    <c:if test="${not empty  occupancyCertificate.receipts}">
                        <div class="panel panel-primary" data-collapsed="0">
                            <jsp:include page="../application/view-bpa-receipt-details.jsp"></jsp:include>
                        </div>
                    </c:if>
                    <div class="panel panel-primary" data-collapsed="0">
                        <jsp:include page="../application/applicationhistory-view.jsp"></jsp:include>
                    </div>
                </div>
                <div id="document-info" class="tab-pane fade">
                    <div class="panel panel-primary" data-collapsed="0">
                        <jsp:include page="view-oc-documents.jsp"></jsp:include>
                    </div>
                </div>
                <c:if test="${showUpdateNoc}">
                    <input type="hidden" id="showUpdateNoc" value="${showUpdateNoc}">
                    <div id="noc-document-info" class="tab-pane fade">
                        <div class="panel panel-primary" data-collapsed="0">
                            <jsp:include page="oc-noc-documents.jsp"></jsp:include>
                        </div>
                    </div>
                </c:if>
                <c:if test="${!showUpdateNoc && not empty occupancyCertificate.nocDocuments}">
                    <div id="noc-info" class="tab-pane fade">
                        <div class="panel panel-primary" data-collapsed="0">
                            <jsp:include page="view-oc-noc-documents.jsp"></jsp:include>
                        </div>
                    </div>
                </c:if>
                <c:if test="${not empty occupancyCertificate.documentScrutinies}">
                    <div id="doc-scrnty" class="tab-pane fade">
                        <div class="panel panel-primary" data-collapsed="0">
                            <jsp:include page="view-oc-document-scrutiny.jsp"></jsp:include>
                        </div>
                    </div>
                </c:if>
                <c:if test="${not empty occupancyCertificate.inspections}">
                    <div id="view-inspection" class="tab-pane fade">
                        <div class="panel panel-primary" data-collapsed="0">
                            <jsp:include
                                    page="../occupancyCertificate/inspection/oc-view-inspection-details.jsp"></jsp:include>
                        </div>
                        <c:if test="${null ne occupancyCertificate.townSurveyorRemarks}">
                            <c:if test="${'Town Surveyor Inspected' eq occupancyCertificate.status.code}">
                                <input type="hidden" id="viewTsRemarks" value="true">
                            </c:if>
                            <div class="panel panel-primary" data-collapsed="0">
                                <jsp:include page="../occupancyCertificate/inspection/oc-view-town-surveyor-remarks.jsp"></jsp:include>
                            </div>
                        </c:if>
                    </div>
                </c:if>
                <c:if test="${captureTSRemarks}">
                    <div id="ts-remarks" class="tab-pane fade">
                        <div class="panel panel-primary" data-collapsed="0">
                            <jsp:include page="../occupancyCertificate/inspection/oc-town-surveyor-remarks.jsp"></jsp:include>
                        </div>
                    </div>
                </c:if>
                <c:if test="${not empty letterToPartyList}">
                    <div id="view-lp" class="tab-pane fade">
                        <div class="panel panel-primary" data-collapsed="0">
                            <jsp:include page="../occupancyCertificate/letterToParty/oc-ltp-details.jsp"></jsp:include>
                        </div>
                    </div>
                </c:if>
            </div>

            <div class="text-center">

                <c:if test="${mode eq 'newappointment'}">
                    <a
                            href="/bpa/application/occupancy-certificate/schedule-appointment/${occupancyCertificate.applicationNumber}"
                            class="btn btn-primary"> New Appointment </a>
                </c:if>

                <c:if test="${mode eq 'captureInspection'}">
                    <a
                            target="popup" class="btn btn-primary"
                            onclick="window.open('/bpa/application/occupancy-certificate/create-inspection/${occupancyCertificate.applicationNumber}','popup','width=1100,height=700'); return false;"
                            class="btn btn-primary">Capture Inspection Details </a>
                    <c:if test="${isInspnRescheduleEnabled eq true}">
                        <a
                                href="/bpa/application/occupancy-certificate/reschedule-appointment/${scheduleType}/${occupancyCertificate.applicationNumber}"
                                class="btn btn-primary"> Reschedule Appointment </a>
                    </c:if>
                </c:if>

                <c:if test="${mode eq 'captureAdditionalInspection'}">
                    <a
                            target="popup" class="btn btn-primary"
                            onclick="window.open('/bpa/application/occupancy-certificate/create-inspection/${occupancyCertificate.applicationNumber}','popup','width=1100,height=700'); return false;"
                            class="btn btn-primary">Capture Inspection Details </a>
                </c:if>

                <c:if test="${occupancyCertificate.state.value ne 'Field Inspection completed' && occupancyCertificate.status.code eq 'Field Inspected'}">
                    <input type="button" name="save" id="btnSave" value="Save"
                           class="btn btn-primary"/>
                </c:if>

                <c:if test="${createlettertoparty}">
                    <a
                            href="/bpa/occupancy-certificate/letter-to-party/create/${occupancyCertificate.applicationNumber}"
                            target="_self" class="btn btn-primary"> Create Letter to Party </a>
                </c:if>
            </div>
            <br>
            <c:if test="${isTSInspectionRequired eq true}">

                <div class="panel panel-primary" data-collapsed="0" id="townSurveyorInspectionDiv">
                    <div class="panel-heading toggle-header custom_form_panel_heading">
                        <div class="panel-title">
                        </div>
                    </div>
                    <div class="panel-body">
                        <label class="view-content">
                            &nbsp;&nbsp;&nbsp;<form:checkbox path="townSurveyorInspectionRequire"
                                                             id="townSurveyorInspectionRequire"/>
                            &nbsp;&nbsp;&nbsp;<spring:message
                                code="lbl.ts.inspn.requr"/>
                        </label>
                    </div>
                </div>
            </c:if>

            <c:choose>
                <c:when
                        test="${isFeeCollected && occupancyCertificate.status.code eq 'Approved'}">
                    <div class="buttonbottom" align="center">
                        <input type="button" name="button2" value="Close"
                               class="btn btn-default" onclick="window.close();"/>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when
                                test="${ (citizenOrBusinessUser && occupancyCertificate.id !=null) || occupancyCertificate.state.value eq 'LP Created' || occupancyCertificate.state.value eq 'LP Reply Received'}">
                            <div class="buttonbottom" align="center">
                                <form:button type="submit" id="buttonSubmit"
                                             class="btn btn-primary" value="Forward">Forward</form:button>
                                <input type="button" name="button2" value="Close"
                                       class="btn btn-default" onclick="window.close();"/>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${occupancyCertificate.status.code ne 'Digitally signed'}">
                                <jsp:include page="../common/commonWorkflowMatrix.jsp"/>
                            </c:if>
                            <div class="buttonbottom" align="center">
                                <jsp:include page="../common/commonWorkflowMatrix-button.jsp"/>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
        </form:form>
    </div>
</div>

<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
     aria-hidden="true" id="commentsModal">
    <div class="modal-dialog" role="document">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header text-center">
                <h4 class="modal-title w-100 font-weight-bold alert alert-info">Reason For Revert</h4>
            </div>
            <div class="modal-body mx-3">
                <div id="showCommentsModal" class="md-form mb-5"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<script
        src="<cdn:url value='/resources/global/js/egov/inbox.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script
        src="<cdn:url value='/resources/js/app/occupancy-certificate/occupancy-certificate-edit.js?rnd=${app_release_no}'/>"></script>
<script
        src="<cdn:url value='/resources/js/app/edcr-helper.js?rnd=${app_release_no}'/>"></script>
<script
        src="<cdn:url value='/resources/js/app/occupancy-certificate/occupancy-certificate-view.js?rnd=${app_release_no}'/>"></script>