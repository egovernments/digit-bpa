<div class="main-content">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-primary" data-collapsed="0">
				<div class="panel-heading">
					<div class="panel-title">NocDepartment</div>
				</div>
				<div class="panel-body">
					<div class="form-group">
						
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.name" /> </label>
						<div class="col-sm-3 add-margin">
							<form:input path="name"
								class="form-control text-left patternvalidation"
								data-pattern="alphanumeric" maxlength="124" />
							<form:errors path="name" cssClass="error-msg" />
						</div>
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.code" /> </label>
						<div class="col-sm-3 add-margin">
							<form:input path="code"
								class="form-control text-left patternvalidation"
								data-pattern="alphanumeric" maxlength="20" />
							<form:errors path="code" cssClass="error-msg" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.isactive" /> </label>
						<div class="col-sm-3 add-margin">
							<form:checkbox path="isActive" />
							<form:errors path="isActive" cssClass="error-msg" />
						</div>
						<input type="hidden" name="nocDepartment"
							value="${nocDepartment.id}" /><%@ include
							file="../nocconfig/nocconfig-formtable.jsp"%>