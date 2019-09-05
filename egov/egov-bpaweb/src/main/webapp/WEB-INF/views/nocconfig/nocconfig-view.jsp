<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/taglibs.jsp"%>
<div class="main-content"><div class="row"><div class="col-md-12"><div class="panel panel-primary" data-collapsed="0"><div class="panel-heading"><div class="panel-title">NocConfig</div></div><div class="panel-body custom"><div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.nocdepartment" />
</div><div class="col-sm-3 add-margin view-content">
${nocConfig.nocDepartment.name}
</div><div class="col-xs-3 add-margin"><spring:message code="lbl.applicationtype" />
</div><div class="col-sm-3 add-margin view-content">
${nocConfig.applicationType.name}
</div></div>
<div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.integrationtype" />
</div><div class="col-sm-3 add-margin view-content">
${nocConfig.integrationType.name}
</div><div class="col-xs-3 add-margin"><spring:message code="lbl.isdeemedapproved" />
</div><div class="col-sm-3 add-margin view-content">
${nocConfig.isDeemedApproved}
</div></div>
<div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.daysfordeemedapproval" />
</div><div class="col-sm-3 add-margin view-content">
${nocConfig.daysForDeemedApproval}
</div></div></div></div></div><div class="row text-center"><div class="add-margin"><a href="javascript:void(0)" class="btn btn-default" onclick="self.close()">Close</a></div></div>