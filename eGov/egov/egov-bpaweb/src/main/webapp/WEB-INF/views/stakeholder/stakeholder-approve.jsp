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
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>

<form:form role="form" action="/bpa/stakeholder/citizen/update/"
           modelAttribute="stakeHolder" id="stakeHolder"
           cssClass="form-horizontal form-groups-bordered"
           enctype="multipart/form-data">

    <input type="hidden" name="stakeHolder" id="stakeHolderId"
           value="${stakeHolder.id}">
    <input type="hidden" id="workFlowAction" name="workFlowAction" value="">
    <input type="hidden" id="approvalNotRequire" value="${approvalNotRequire}">
    <ul class="nav nav-tabs" id="settingstab">
        <li class="active"><a data-toggle="tab" href="#applicant-info"
                              data-tabidx=0><spring:message code='lbl.applicant.info'/></a></li>
        <li><a data-toggle="tab" href="#checklist-info" data-tabidx=1><spring:message
                code='title.documentdetail'/></a></li>
    </ul>
    <div class="tab-content">
        <div id="applicant-info" class="tab-pane fade in active">
            <div class="panel panel-primary" data-collapsed="0">
                <jsp:include page="viewapplicantinfo.jsp"></jsp:include>
            </div>
            <div class="panel panel-primary" data-collapsed="0">
                <jsp:include page="stakeholder-addnlinfo.jsp"></jsp:include>
            </div>
            <div class="panel panel-primary" data-collapsed="0">
                <jsp:include page="../application/applicationhistory-view.jsp"></jsp:include>
            </div>
        </div>
        <div id="checklist-info" class="tab-pane fade">
            <div class="panel panel-primary" data-collapsed="0">
                <jsp:include page="view-checklist.jsp"></jsp:include>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary" data-collapsed="0">
                <div class="panel-heading">
                    <div class="panel-title">
                        Approver Comments
                    </div>
                </div>
                <div class="panel-body">
                    <div class="form-group">
                        <label class="col-sm-3 control-label text-right"><spring:message
                                code="lbl.comments"/> </label>
                        <div class="col-sm-9 add-margin">
                            <form:textarea class="form-control" rows="3" path="comments" id="comments"
                                           maxlength="1024"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="text-center">
        <c:if test="${approvalNotRequire ne true}">
            <button type="submit" class='btn btn-primary' id="btnApprove"
                    value="Approve">
                <spring:message code='lbl.approve'/>
            </button>
        </c:if>
        <button type="submit" class='btn btn-danger' id="btnReject"
                value="Reject">
            <spring:message code='lbl.reject'/>
        </button>
        <c:if test="${stakeHolder.noOfTimesRejected != null && stakeHolder.noOfTimesRejected ge 1 }">
            <button type="submit" class='btn btn-danger' id="btnBlock"
                    value="Block">
                <spring:message code='lbl.block'/>
            </button>
        </c:if>
        <a href='javascript:void(0)' class='btn btn-default'
           onclick='self.close()'><spring:message code='lbl.close'/></a>
    </div>
</form:form>
<link rel="stylesheet"
      href="<cdn:url value='/resources/global/css/jquery/plugins/datatables/jquery.dataTables.min.css' context='/egi'/>"/>
<link rel="stylesheet"
      href="<cdn:url value='/resources/global/css/jquery/plugins/datatables/dataTables.bootstrap.min.css' context='/egi'/>">
<script
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/jquery.dataTables.min.js' context='/egi'/>"></script>
<script
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/responsive/js/datatables.responsive.js' context='/egi'/>"></script>
<script
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/dataTables.bootstrap.js' context='/egi'/>"></script>
<script
        src="<cdn:url value='/resources/js/app/stakeholder-approval-rejection.js?rnd=${app_release_no}'/> "></script>

