<div class="main-content">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-primary" data-collapsed="0">
				<div class="panel-heading">
					<div class="panel-title">NocConfig</div>
				</div>
				<div class="panel-body">
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.nocdepartment" /> <span class="mandatory"></span> </label>
						<div class="col-sm-3 add-margin">
							<form:select path="nocDepartment" id="nocDepartment"
								cssClass="form-control" required="required"
								cssErrorClass="form-control error">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${nocDepartments}" itemValue="id"
									itemLabel="name" />
							</form:select>
							<form:input path="nocDepartment"
								class="form-control text-right patternvalidation"
								data-pattern="number" required="required" />
							<form:errors path="nocDepartment" cssClass="error-msg" />
						</div>
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.applicationtype" /> </label>
						<div class="col-sm-3 add-margin">
							<form:select path="applicationType" id="applicationType"
								cssClass="form-control" cssErrorClass="form-control error">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${bpaApplicationTypes}" itemValue="id"
									itemLabel="name" />
							</form:select>
							<form:input path="applicationType"
								class="form-control text-right patternvalidation"
								data-pattern="number" />
							<form:errors path="applicationType" cssClass="error-msg" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.integrationtype" /> </label>
						<div class="col-sm-3 add-margin">
							<form:select path="integrationType" id="integrationType"
								cssClass="form-control" cssErrorClass="form-control error">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${nocIntegrationTypeEnums}" itemValue="id"
									itemLabel="name" />
							</form:select>
							<form:input path="integrationType"
								class="form-control text-right patternvalidation"
								data-pattern="number" />
							<form:errors path="integrationType" cssClass="error-msg" />
						</div>
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.isdeemedapproved" /> </label>
						<div class="col-sm-3 add-margin">
							<form:checkbox path="isDeemedApproved" />
							<form:errors path="isDeemedApproved" cssClass="error-msg" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.daysfordeemedapproval" /> </label>
						<div class="col-sm-3 add-margin">
							<form:input path="daysForDeemedApproval"
								class="form-control text-right patternvalidation"
								data-pattern="number" />
							<form:errors path="daysForDeemedApproval" cssClass="error-msg" />
						</div>
						<input type="hidden" name="nocConfig" value="${nocConfig.id}" />