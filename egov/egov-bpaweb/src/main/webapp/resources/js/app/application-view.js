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
				function() {
					
					//Don't allow double quotes in approval comments, replace double quotes(") with single quote(')
					$('#approvalComent').keyup(function () {
			            $(this).val($(this).val().replace(/["]/g, "'"));
			        });
					
                    $('.demolitionDetails').hide();
					if($('#eDcrNumber').val()) {
                        $('#eDcrNumber').show();
                        $('.edcrApplnDetails').show();
                        $('.dcrDocuments').show();
					} else {
                        $('#eDcrNumber').hide();
                        $('.edcrApplnDetails').hide();
                        $('.dcrDocuments').hide();
					}
					//To show notification to business user/citizen on application open
					if($('#appointmentDateRes').val() && $('#appointmentTimeRes').val() && $('#appointmentTitle').val()) {
						$('#appointmentDateModal').html($('#appointmentDateRes').val());
						$('#appointmentTimeModal').html($('#appointmentTimeRes').val());
						$('#appointmentTitleModal').html($('#appointmentTitle').val());
						if($('#appmntInspnRemarks').val())
                        	$('#appmntInspnRemarksModal').html($('#appmntInspnRemarks').val());
						$('#myModal').modal('show');
					}

                    // start -- update checked true for saved permit conditions
					var savedPermitCondn = $("#persistedPermitConditions")
							.val();
					if (savedPermitCondn && savedPermitCondn.length > 0) {
						var persistedPermitConditionsArr = [];
						var persistedPermitConditions = savedPermitCondn
								.substring(1, savedPermitCondn.length - 1)
								.split(',');
						if(persistedPermitConditions[0] && persistedPermitConditions.length > 0) {
							$.each(persistedPermitConditions, function(index,
									permitCondnId) {
								persistedPermitConditionsArr.push(permitCondnId
										.split(':')[1].trim());
							});
						}

						$(".permitConditions")
								.each(
										function() {
											if ($
													.inArray($(this).val(),
															persistedPermitConditionsArr) >= 0) {
												$(this).attr('checked',
														'checked');
											}
										});
					}
					// end
					
					$("#viewBpaApplicationForm").validate({
						highlight : function(element, errorClass) {
							$(element).fadeOut(function() {
								$(element).fadeIn();
							});
						}
					});

					if($('#currentPermitFee').val()) {
						var currentFee = $('#currentPermitFee').val();
						var modifiedFee = $('.AdditionalFees').val();
						if(modifiedFee < currentFee) {
							bootbox.alert($('#feeAmount').val()+currentFee+ $('#incrFeeamtTopOfsysCalcFee').val());
						}
					}

					// By default to point update noc details tab
					var mode = $('#mode').val();

					if (mode == 'view') {
						removeWorkFlowMandatoryAndHideDepartmentDetails();
					} else if (mode == 'captureInspection') {
						removeWorkFlowMandatoryAndHideDepartmentDetails();
						$("#Forward").hide();
						$("#Reject").hide();
					}
					if ($('#wfstateDesc').val() != 'Registered'
							&& $('#wfstateDesc').val() != 'NEW'
							&& mode == 'newappointment') {
						$(".show-row").hide();
						$("#Forward").hide();
                        $("#approverDetailBody").hide();
					}
					if ($('#wfstateDesc').val() == 'NEW'
							&& mode == 'newappointment') {
						removeWorkFlowMandatoryAndHideDepartmentDetails();
					}
					if ($('#wfstateDesc').val() == 'LP Created') {
						removeWorkFlowMandatoryAndHideDepartmentDetails();
						$("#buttonSubmit").hide();
					} else if ($('#wfstateDesc').val() == 'LP Reply Received') {
						removeWorkFlowMandatoryAndHideDepartmentDetails();
						$("#buttonSubmit").show();
					} else if ($('#wfstateDesc').val() == 'Rejected'
						|| $('#wfstateDesc').val() == 'Town Surveyor Inspection Initiated'
						|| $('#wfstateDesc').val() == 'Rejection initiated by clerk') {
						$("#approverDetailBody").hide();
					} else if($('#isOneDayPermitApplication').val() === 'true' && $('#wfstateDesc').val() === 'Record Approved') {
                        removeWorkFlowMandatoryAndHideDepartmentDetails();
                        $("#approverDetailBody").hide();
					}


					// Prevent continuous send back to previous owners, one step backward only allowed
                    if ($('#sentToPreviousOwner').val() == 'true' || $('#lpRequestInitiated').val() == 'true') {
                        $("#Revert").hide();
                    } else {
                        $("#Revert").show();
					}

					function removeWorkFlowMandatoryAndHideDepartmentDetails() {
						$('#approvalDepartment').removeAttr('required');
						$('#approvalDesignation').removeAttr('required');
						$('#approvalPosition').removeAttr('required');
                        $("#approverDetailBody").hide();
					}

					// When SUP updating noc details, Noc status is mandatory
                    if ($('#showUpdateNoc').val()) {
                        $('.nocStatus').attr('required', true);
                        $('.nocStatusHeader').addClass( "mandatory" );
                    }

					var tabfocus;
					if($('#wfstateDesc').val() == 'LP Created'
						|| $('#wfstateDesc').val() == 'LP Reply Received') {
                        tabfocus = '#view-lp';
					} else if ($('#showUpdateNoc').val()) {
						tabfocus = '#checklist-info';
					} else if ($('#showPermitConditions').val()) {
                        tabfocus = '#permit-conditions';
                    } else if ($('#captureTSRemarks').val() === 'true') {
                        tabfocus = '#ts-remarks';
                    } else if ($('#captureTSRemarks').val() === 'false') {
                        tabfocus = '#view-inspection';
                    } else if($('#wfstateDesc').val() == 'Scheduled For Document Scrutiny'
                        || $('#wfstateDesc').val() == 'Rescheduled For Document Scrutiny') {
                        tabfocus = '#doc-scrutiny-info';
                    } else {
						tabfocus = '#applicant-info';
					}

					var prefix = "tab_";
					if (tabfocus) {
						$(
								'.nav-tabs a[href="'
										+ tabfocus.replace(prefix, "") + '"]')
								.tab('show');
					}

					// toggle between multiple tab
					jQuery('form')
							.validate(
									{
										ignore : ".ignore",
										invalidHandler : function(e, validator) {
											if (validator.errorList.length)
												$(
														'#settingstab a[href="#'
																+ jQuery(
																		validator.errorList[0].element)
																		.closest(
																				".tab-pane")
																		.attr(
																				'id')
																+ '"]').tab(
														'show');
										}
									});

					// government or quasi validation
					if ($("#governmentType").val() == 'NOT_APPLICABLE') {
						$('#isEconomicallyWeakerSec').hide();
					} else {
						$('#isEconomicallyWeakerSec').show();
					}

					$(".serviceType").trigger("change");
					$(".applicationAmenity").trigger("change");

					var seviceTypeName = $("#serviceType").val();
					$('.show-hide').hide();
					$('.areaOfBase').hide();
					$('.extentOfLand').show();

                    /*if($('#isOneDayPermitApplication').val()) {
                        $('.amenityHideShow').hide();
                        if('Amenities'.localeCompare(seviceTypeName) == 0)
                            $('.amenityHideShow').show();
					}*/
					if ('New Construction'.localeCompare(seviceTypeName) == 0) {
						$('.doorNo').hide();
						$('.totalPlintArea').show();
                        $('.demolitionDetails').show();
					} else if ('Sub-Division of plot/Land Development'
							.localeCompare(seviceTypeName) == 0
							|| 'Amenities'.localeCompare(seviceTypeName) == 0) {
						$('.buildingdetails').hide();
					} else if ('Tower Construction'
							.localeCompare(seviceTypeName) == 0
							|| 'Pole Structures'.localeCompare(seviceTypeName) == 0) {
						$('.buildingdetails').hide();
						$('.extentOfLand').hide();
						$('.areaOfBase').show();
					} else if ('Demolition' == seviceTypeName) {
                        $('.buildingdetails').hide();
						$('.demolitionDetails').show();
                        $('.amenityHideShow').hide();
					} else if ('Change in occupancy' == seviceTypeName) {
						$('.changeInOccupancyArea').show();
                        $('.demolitionDetails').show();
					} else if ('Alteration' == seviceTypeName) {
						$('.alterationInArea').show();
                        $('.demolitionDetails').show();
					} else if ('Addition or Extension' == seviceTypeName) {
						$('.additionInArea').show();
                        $('.demolitionDetails').show();
					} else if ('Huts and Sheds' == seviceTypeName) {
						$('.buildingdetails').hide();
						$('.noofhutorshed').show();
					} else {
						$('.totalPlintArea').show();
					}

					loadAmenities();

					function loadAmenities() {
						var amenities = [];
						if (seviceTypeName == 'Huts and Sheds') {
							amenities.push('Huts and Sheds');
						} else if (seviceTypeName == 'Tower Construction') {
							amenities.push('Tower Construction');
						} else if (seviceTypeName == 'Pole Structures') {
							amenities.push('Pole Structures');
						}
						if ($("#applicationAmenity").val()) {
							var amenityDesc = $("#applicationAmenity").val()
									.split(',');
							$.each(amenityDesc, function(index, amenityDesc) {
								amenities.push(amenityDesc);
							});
						}

						var result = "";
						$
								.each(
										amenities,
										function(idx, value) {
											// console.log('is even?',
											// $(this).text(), idx, );
											var isEven = (parseInt(idx) % 2 === 0);
											if (isEven) {
												result = result
														+ (result ? "</div><div class='row add-border'>"
																: "<div class='row add-border'>");
											}
											result = result
													+ getTemplateComplie(value,
															isEven);
										});
						result = result + "</div>";
						$('#amenitiesOuptuts').html(result);
					}

					function getTemplateComplie(value, isFirstPosition) {
						var templateStr = "";

						switch (value) {
						case "Well":
							templateStr = $('#well-view-template').html();
							break;
						case "Compound Wall":
							templateStr = $('#compound-view-template').html();
							break;
						case "Shutter or Door Conversion/Erection under rule 100 or 101":
							templateStr = $('#shutter-view-template').html();
							break;
						case "Roof Conversion under rule 100 or 101":
							templateStr = $('#roof-view-template').html();
							break;
						case "Tower Construction":
							templateStr = $('#tower-view-template').html();
							break;
						case "Pole Structures":
							templateStr = $('#poles-view-template').html();
							break;
						case "Huts and Sheds":
							templateStr = $('#sheds-view-template').html();
							break;
						}

						return templateStr;
					}

					//To show Validation message if collection fee is pending ?
					if ($('#collectFeeValidate').val() != ''
							&& $('#collectFeeValidate').val() != null) {
						bootbox.alert($('#collectFeeValidate').val());
						removeWorkFlowMandatoryAndHideDepartmentDetails();
						$("#Forward").hide();
						$(".workAction").hide();
						return false;
					}

				});