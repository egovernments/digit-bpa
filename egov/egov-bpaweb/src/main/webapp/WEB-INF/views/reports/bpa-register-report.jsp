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

<form:form role="form" action=""
           modelAttribute="searchBpaApplicationForm" id="bpaRegisterReport"
           cssClass="form-horizontal form-groups-bordered"
           enctype="multipart/form-data">
    <jsp:include page="search-bpa-register-form.jsp"></jsp:include>
    <div class="text-center">
        <button type='button' class='btn btn-primary' id="btnSearch">
            <spring:message code='lbl.search'/>
        </button>
        <button type="reset" class="btn btn-danger">
            <spring:message code="lbl.reset"/>
        </button>
        <a href='javascript:void(0)' class='btn btn-default'
           onclick='self.close()'><spring:message code='lbl.close'/></a>
    </div>
</form:form>
<br>
<div class="row display-hide report-section" id="table_container">
    <div class="col-md-12">
        <div class="panel panel-primary">
            <div class="panel-body">
                <table class="table table-bordered table-hover multiheadertbl nowrap display"
                       id="searchBpaRegister" style="overflow-x: auto;max-width: 100%;min-width: 100%;">
                    <thead>
                    <tr>
                        <th colspan="1"></th>
                        <th colspan="1"></th>
                        <th colspan="1"></th>
                        <th colspan="1"></th>
                        <th colspan="1"></th>
                        <th colspan="1"></th>
                        <th colspan="1"></th>
                        <th colspan="1"></th>
                        <th colspan="1"></th>
                        <th colspan="1"></th>
                        <th colspan="1"></th>
                        <th colspan="1"></th>
                        <th colspan="1"></th>
                        <th colspan="1"></th>
                        <th colspan="1"></th>
                        <th colspan="1"></th>
                        <th colspan="1"></th>
                        <th colspan="1"></th>
                        <th colspan="3"><spring:message code="lbl.fee.details"/></th>
                        <th colspan="1"></th>
                        <th colspan="1"></th>
                        <th colspan="1"></th>
                    </tr>
                    <tr>
                        <th><spring:message code="lbl.slno"/></th>
                        <th><spring:message code="lbl.app.num"/></th>
                        <th><spring:message code="lbl.applctn.type"/></th>
                        <th><spring:message code="lbl.service.type"/></th>
                        <th><spring:message code="lbl.date.admison"/></th>
                        <th><spring:message code="lbl.applicant.name"/></th>
                        <th><spring:message code="lbl.owner.address"/></th>
                        <th><spring:message code="lbl.survey.no"/></th>
                        <th><spring:message code="lbl.reven.ward"/></th>
                        <th><spring:message code="lbl.natur.occupan"/></th>
                        <th><spring:message code="lbl.totl.floorarea"/></th>
                        <th><spring:message code="lbl.no.floors"/></th>
                        <th><spring:message code="lbl.far"/></th>
                        <th><spring:message code="lbl.admission.fees"/></th>
                        <th><spring:message code="lbl.document.verification.date"/></th>
                        <th><spring:message code="lbl.field.verification.date"/></th>
                        <th><spring:message code="lbl.noc.details"/></th>
                        <th><spring:message code="lbl.building.permit.number"/></th>
                        <th><spring:message code="lbl.permit.fee"/></th>
                        <th><spring:message code="lbl.shelterfund.type"/></th>
                        <th><spring:message code="lbl.labourcess.type"/></th>
                        <th><spring:message code="lbl.devpermitfee.type"/></th>
                        <th><spring:message code="lbl.rejection.reasons"/></th>
                        <th><spring:message code="lbl.final.approval.date"/></th>
                        <th><spring:message code="lbl.approved.rejected.by"/></th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
    <input type="hidden" id="atleastOneInputReq" value="<spring:message code='msg.validate.atleast.oneinput.required'/>" />
</div>

<link rel="stylesheet"
      href="<cdn:url value='/resources/global/css/jquery/plugins/datatables/jquery.dataTables.min.css' context='/egi'/>"/>
<link rel="stylesheet"
      href="<cdn:url value='/resources/global/css/jquery/plugins/datatables/dataTables.bootstrap.min.css' context='/egi'/>">
<script type="text/javascript"
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/jquery.dataTables.min.js' context='/egi'/>"></script>
<script type="text/javascript"
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/dataTables.bootstrap.js' context='/egi'/>"></script>
<script
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/dataTables.buttons.min.js' context='/egi'/>"></script>
<script
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/buttons.bootstrap.min.js' context='/egi'/>"></script>
<script
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/buttons.flash.min.js' context='/egi'/>"></script>
<script
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/jszip.min.js' context='/egi'/>"></script>
<script
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/pdfmake.min.js' context='/egi'/>"></script>
<script
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/vfs_fonts.js' context='/egi'/>"></script>
<script
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/buttons.html5.min.js' context='/egi'/>"></script>
<script
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/buttons.print.min.js' context='/egi'/>"></script>
<script type="text/javascript"
        src="<cdn:url  value='/resources/global/js/jquery/plugins/datatables/extensions/fixed columns/dataTables.fixedColumns.min.js' context='/egi'/>"></script>
<script
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/dataTables.buttons.min.js' context='/egi'/>"></script>
<script type="text/javascript"
        src="<cdn:url  value='/resources/global/js/jquery/plugins/jquery.validate.min.js' context='/egi'/>"></script>
<script
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/datetime-moment.js' context='/egi'/>"></script>
<script
        src="<cdn:url value='/resources/global/js/bootstrap/bootstrap-datepicker.js' context='/egi'/>"
        type="text/javascript"></script>
<script
        src="<c:url value='/resources/global/js/handlebars/handlebars.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script
        src="<cdn:url value='/resources/js/app/bpa-ajax-helper.js?rnd=${app_release_no}'/> "></script>
<script
        src="<cdn:url value='/resources/js/app/bpa-register-report.js?rnd=${app_release_no}'/> "></script>
        
<script src="<cdn:url value='/resources/js/app/genericBoundarySearchForm.js?rnd=${app_release_no}'/>"></script>        

