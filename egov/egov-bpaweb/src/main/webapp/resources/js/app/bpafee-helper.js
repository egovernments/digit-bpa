/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2017>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

jQuery(document)
		.ready(
				function($) {
					//$('#category').val(2);
					
					$('#category').prop('selectedIndex',2);
					accountDetailGlcode_initialize();
					
					$('#submit').click(function(e) {
						if ($('form').valid()) {
							console.log('submitted')
						} else {
							e.preventDefault();
						}
					});

					var deletedId = [];
					$(document)
							.on(
									'click',
									"#deleteFeeMapRow",
									function() {
										var id = $(tbody).find('tr').length;
										var idx = $(this).closest('tr').index();
										var obj = $(this);
										if (id == 1) {
											bootbox
													.alert('Cannot delete first row!');
										} else {

											var rowIndex = $(this)
													.closest('td').parent()[0].sectionRowIndex;
											if ($(this).data('record-id'))
												deletedId.push($(this).data(
														'record-id'));

											$('#feeMapIds').val(deletedId);
											$(this).closest('tr').remove();

											$("#bpaFeeMapping tbody tr")
													.each(
															function() {
																$(this)
																		.find(
																				"input, select, hidden,textarea")
																		.each(
																				function() {
																					var index = $(
																							this)
																							.closest(
																									'td')
																							.parent()[0].sectionRowIndex;
																					if (index >= rowIndex) {
																						var increment = index++;
																						$(
																								this)
																								.attr(
																										{
																											'name' : function(
																													_,
																													name) {
																												var idxWithNameStr = name
																														.match(/\d+(\D*)$/g)[0]
																														.replace(
																																/\d+\]/g,
																																increment
																																		+ "]");
																												return name
																														.replace(
																																/\d+(\D*)$/g,
																																idxWithNameStr);
																											},
																											'id' : function(
																													_,
																													id) {
																												if (id == undefined) {
																													return "";
																												}
																												return id
																														.replace(
																																/\d+(\D*)$/g,
																																+increment);
																											}
																										});
																					}
																				});
															});
										}
									});

					var tbody = $('#bpaFeeMapping').children('tbody');
					var table = tbody.length ? tbody : $('#bpaFeeMapping');
					var row = '<tr>'
							+ '<td ><select name="bpaFeeMapTemp[{{idx}}].serviceType" data-first-option="false" id="bpaFeeMap[{{idx}}]serviceType" class="form-control serviceType " required="required" > <option value="">Select</option><options items="${serviceTypeList}" itemValue="id" itemLabel="description" /></select></td>'
							+ '<td ><select name="bpaFeeMapTemp[{{idx}}].applicationType" data-first-option="false" id="bpaFeeMap[{{idx}}]applicationType" class="form-control applicationType " required="required" > <option value="">Select</option><options items="${applicationTypes}" itemLabel="feeApplicationTypeVal"/></select></td>'
							+ '<td ><select name="bpaFeeMapTemp[{{idx}}].applicationSubType" data-first-option="false" id="bpaFeeMap[{{idx}}]applicationSubType" class="form-control applicationSubType " required="required" > <option value="">Select</option><options items="${appSubTypes}" itemValue="id" itemLabel="description" /></select></td>'
                            + '<td ><select name="bpaFeeMapTemp[{{idx}}].feeSubType" data-first-option="false" id="bpaFeeMap[{{idx}}]feeSubType" class="form-control feeSubType " required="required" > <option value="">Select</option><options items="${feeSubTypes}" itemLabel="feeSubTypeVal" /></select></td>'
							+ '<td ><select name="bpaFeeMapTemp[{{idx}}].calculationType" data-first-option="false" id="bpaFeeMap[{{idx}}]calculationType" class="form-control calculationType " required="required" > <option value="">Select</option><options items="${calculationTypes}" /></select></td>'
							+ '<td ><input type="text" class="form-control patternvalidation text-right" data-pattern="number" name="bpaFeeMapTemp[{{idx}}].amount" id="bpaFeeMapTemp[{{idx}}]amount" required="required" maxlength="8" /><form:errors path="bpaFeeMapTemp[{{idx}}]amount" cssClass="error-msg" /></td>'
							+ '<td class="text-center"><a href="javascript:void(0);" class="btn-sm btn-danger" id="deleteFeeMapRow" ><i class="fa fa-trash"></i></a></td>'
							+ '</tr>';

					$('#addrow')
							.click(
									function() {
										var idx = $(tbody).find('tr').length;
										var sno = idx + 1;
										// Add row
										var row = {
											'sno' : sno,
											'idx' : idx
										};
										var rowCount = $('#bpaFeeMapping tbody tr').length;
										var valid = true;
										// validate all rows before adding new
										// row
										$('#bpaFeeMapping tbody tr')
												.each(
														function(idx, value) {
															$(
																	'#bpaFeeMapping tbody tr:eq('
																			+ idx
																			+ ') td input[type="text"]')
																	.each(
																			function(
																					i,
																					v) {
																				if (!$
																						.trim($(
																								v)
																								.val())) {
																					valid = false;
																					bootbox
																							.alert(
																									"Enter all values for existing rows!",
																									function() {
																										$(
																												v)
																												.focus();
																									});
																					return false;
																				}
																			});
														});
										if (valid) {
											addRowFromObject(row);
											patternvalidation();
											loadApplicationTypes("bpaFeeMapTemp["
													+ idx + "].applicationType");
											loadAppSubTypes("bpaFeeMapTemp["
													+ idx + "].applicationSubType");
											loadCalculationTypes("bpaFeeMapTemp["
													+ idx + "].calculationType");
											loadFeeSubTypes("bpaFeeMapTemp["
													+ idx + "].feeSubType");
											loadServiceTypes("bpaFeeMapTemp["
													+ idx + "].serviceType");

										}

									});

					function addRowFromObject(rowJsonObj) {
						table.append(row.compose(rowJsonObj));
					}

					String.prototype.compose = (function() {
						var re = /\{{(.+?)\}}/g;
						return function(o) {
							return this.replace(re, function(_, k) {
								return typeof o[k] != 'undefined' ? o[k] : '';
							});
						}
					}());

					function accountDetailGlcode_initialize() {
						var custom = new Bloodhound({
							datumTokenizer : function(d) {
								return d.tokens;
							},
							queryTokenizer : Bloodhound.tokenizers.whitespace,
							remote : {
								url : '/EGF/common/getallaccountcodes?glcode=',
								replace : function(url, query) {
									return url + query;
								},
								dataType : "json",
								filter : function(data) {
									var responseObj = JSON.parse(data);
									return $.map(responseObj, function(ct) {
										return {
											id : ct.id,
											name : ct.name,
											glcode : ct.glcode,
											glcodesearch : ct.glcode + ' ~ '
													+ ct.name
										};
									});
								}
							}
						});

						custom.initialize();
						$('.accountDetailGlcode').typeahead({
							hint : true,
							highlight : true,
							minLength : 3

						}, {
							displayKey : 'glcodesearch',
							source : custom.ttAdapter()
						}).on('typeahead:selected', function(event, data) {
							$("#accountglcode").val(data.glcode);
							$("#accountglcodeid").val(data.id);
						});

						$("#accountDetailGlcode").focus(function() {
							$('.glcodeerror').hide();
							custom.initialize();
						});

					}

					var validator = $("#updateBpaFeeDetailForm").validate({
						highlight : function(element, errorClass) {
							$(element).fadeOut(function() {
								$(element).fadeIn();
							});
						}
					});

					if ($("#updateBpaFeeDetailForm").valid()) {
						return true;
					} else {
						$.each(validator.invalidElements(), function(index,
								elem) {
							if (!$(elem).is(":visible")
									&& !$(elem).closest('div.panel-body').is(
											":visible")) {
								$(elem).closest('div.panel-body').show();
								console.log("elem", elem);
							}
						});
						validator.focusInvalid();
						return false;
					}

				});

function getData() {
	var url = '/bpa/fees/feemap/create/' + $('#code').val();
	$('#bpafeeform').attr('method', 'get');
	$('#bpafeeform').attr('action', url);
	window.location = url;
}

function loadApplicationTypes(selectBoxName) {
	var applicationType = $('#applicationTypes').val();
	console.log(selectBoxName);
	var applicationTypeStringArry = applicationType.substring(1,
			applicationType.length - 1);
	var applicationTypeDesc = applicationTypeStringArry.split(',');
	$.each(applicationTypeDesc, function(index, applicationTypeDesc) {
		$('select[name="' + selectBoxName + '"]').append(
				$('<option>').val(applicationTypeDesc)
						.text(applicationTypeDesc));
	});
}

function loadCalculationTypes(selectBoxName) {
	var calculationType = $('#calculationTypes').val();
	console.log(selectBoxName);
	var calculationTypeStringArry = calculationType.substring(1,
			calculationType.length - 1);
	var calculationTypeDesc = calculationTypeStringArry.split(',');
	$.each(calculationTypeDesc, function(index, calculationTypeDesc) {
		$('select[name="' + selectBoxName + '"]').append(
				$('<option>').val(calculationTypeDesc)
						.text(calculationTypeDesc));
	});
}

function loadFeeSubTypes(selectBoxName) {
	var feeSubType = $('#feeSubTypes').val();
	console.log(selectBoxName);
	var feeSubTypeStringArry = feeSubType.substring(1,
			feeSubType.length - 1);
	var feeSubTypeDesc = feeSubTypeStringArry.split(',');
	$.each(feeSubTypeDesc,
			function(index, feeSubTypeDesc) {
				$('select[name="' + selectBoxName + '"]').append(
						$('<option>').val(feeSubTypeDesc).text(
								feeSubTypeDesc));
			});
}

function loadServiceTypes(selectBoxName) {

	$.ajax({
		url : "/bpa/application/getServiceTypeDetails",
		type : "GET",
		async : false,
		dataType : "json",
		success : function(response) {
			$('select[name="' + selectBoxName + '"]').empty();
			$('select[name="' + selectBoxName + '"]').append(
					$("<option value=''>Select </option>"));
			$.each(response, function(index, serviceType) {
				$('select[name="' + selectBoxName + '"]').append(
						$('<option>').val(serviceType.id).text(
								serviceType.description));
			});
		},
		error : function(response) {
		}
	});
}
	
	function loadAppSubTypes(selectBoxName) {

		$.ajax({
			url : "/bpa/application/getapplicationsubtypes",
			type : "GET",
			async : false,
			dataType : "json",
			success : function(response) {
				$('select[name="' + selectBoxName + '"]').empty();
				$('select[name="' + selectBoxName + '"]').append(
						$("<option value=''>Select </option>"));
				$.each(response, function(index, applicationSubType) {
					$('select[name="' + selectBoxName + '"]').append(
							$('<option>').val(applicationSubType.id).text(
									applicationSubType.description));
				});
			},
			error : function(response) {
			}
		});
}

