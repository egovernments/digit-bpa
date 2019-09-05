<div class=" row">
	<div class=" col-sm-12">
		<br>
		<table class="table table-bordered" id="result">
			<thead>
			<th><spring:message code="lbl.name" /></th>
			<th><spring:message code="lbl.description" /></th>
				<th><spring:message code="lbl.applicationtype" /></th>
				<th><spring:message code="lbl.integrationtype" /></th>
				<th><spring:message code="lbl.isdeemedapproved" /></th>
				<th><spring:message code="lbl.daysfordeemedapproval" /></th>
			</thead>
			<c:choose>
				<c:when test="${not empty nocConfigs.getNocConfigs()}">
					<tbody>
						<c:forEach items="${nocConfigs.nocConfigs}" var="nocConfigs"
							varStatus="vs">
							<tr id="resultrow${vs.index}">
								<form:select path="nocConfigs[${vs.index}].nocDepartment"
									id="nocConfigs[${vs.index}].nocDepartment"
									cssClass="form-control" required="required"
									cssErrorClass="form-control error">
									<form:option value="">
										<spring:message code="lbl.select" />
									</form:option>
									<form:options items="${nocDepartments}" itemValue="id"
										itemLabel="name" />
								</form:select>
								<form:errors path="nocConfigs[${vs.index}].nocDepartment"
									cssClass="error-msg" />
								</td>
								<td>
								<form:select path="nocConfigs[${vs.index}].applicationType"
									id="nocConfigs[${vs.index}].applicationType"
									cssClass="form-control" cssErrorClass="form-control error">
									<form:option value="">
										<spring:message code="lbl.select" />
									</form:option>
									<form:options items="${bpaApplicationTypes}" itemValue="id"
										itemLabel="name" />
								</form:select>
								<form:errors path="nocConfigs[${vs.index}].applicationType"
									cssClass="error-msg" />
								</td>
								<td>
								<form:select path="nocConfigs[${vs.index}].integrationType"
									id="nocConfigs[${vs.index}].integrationType"
									cssClass="form-control" cssErrorClass="form-control error">
									<form:option value="">
										<spring:message code="lbl.select" />
									</form:option>
									<form:options items="${nocIntegrationTypeEnums}" itemValue="id"
										itemLabel="name" />
								</form:select>
								<form:errors path="nocConfigs[${vs.index}].integrationType"
									cssClass="error-msg" />
								</td>
								<td>
								<form:checkbox path="nocConfigs[${vs.index}].isDeemedApproved" />
								<form:errors path="nocConfigs[${vs.index}].isDeemedApproved"
									cssClass="error-msg" />
								</td>

<td>								<form:input path="nocConfigs[${vs.index}].daysForDeemedApproval"
									class="form-control text-right patternvalidation"
									data-pattern="number" />
								<form:errors
									path="nocConfigs[${vs.index}].daysForDeemedApproval"
									cssClass="error-msg" />
								</td>
								<td><span class=" add-padding">
										<button type=" button" id=" del-row" class=" btn btn-primary"
											onclick=" deleteThisRow(this)" data-idx=" ${vs.index}">Delete
											Row</button> </i>
								</span></td>
							</tr>
						</c:forEach>
					</tbody>
				</c:when>
				<c:otherwise>
					<tbody>
						<tr id="resultrow0">
							<td>
							<form:input path="nocConfigs[0].name" id="nocConfigs[0].name"
								class="form-control text-left patternvalidation"
								data-pattern="alphanumeric" maxlength="124" />
							<form:errors path="name" cssClass="error-msg" />
							 </td>
							 <td>
							<form:input path="nocConfigs[0].description" id="nocConfigs[0].description"
								class="form-control text-left patternvalidation"
								data-pattern="alphanumeric" maxlength="124" />
							<form:errors path="name" cssClass="error-msg" />
							</td>
							 
							<td>
							<form:select path="nocConfigs[0].applicationType"
								id="nocConfigs[0].applicationType" cssClass="form-control"
								cssErrorClass="form-control error">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${bpaApplicationTypes}"  />
							</form:select>
							<form:errors path="nocConfigs[0].applicationType"
								cssClass="error-msg" />
							</td>
							<td>
							<form:select path="nocConfigs[0].integrationType"
								id="nocConfigs[0].integrationType" cssClass="form-control"
								cssErrorClass="form-control error">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${nocIntegrationTypeEnums}"  />
							</form:select>
							
							<form:errors path="nocConfigs[0].integrationType"
								cssClass="error-msg" />
							</td>
							<td>
							<form:checkbox path="nocConfigs[0].isDeemedApproved" />
							<form:errors path="nocConfigs[0].isDeemedApproved"
								cssClass="error-msg" />
							</td>
							<td>
							<form:input path="nocConfigs[0].daysForDeemedApproval"
								class="form-control text-right patternvalidation"
								data-pattern="number" />
							<form:errors path="nocConfigs[0].daysForDeemedApproval"
								cssClass="error-msg" />
							</td>
							<td><span class=" add-padding">
									<button type="button" id="del-row" class="btn btn-primary"
										onclick="deleteThisRow(this)" data-idx="0">Delete
										Row</button> </i>
							</span></td>
						</tr>
				 
					</tbody>
				</c:otherwise>
			</c:choose>
			<tbody>
		</table>
	</div>
	<div class=" col-sm-12 text-center">
		<button type="button" id="add-row" class="btn btn-primary">Add Row</button>
	</div>
</div>
