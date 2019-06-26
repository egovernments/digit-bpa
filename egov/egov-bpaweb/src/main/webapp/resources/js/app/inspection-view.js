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
					

					//To show notification to business user/citizen on application open
					if($('#appointmentDateRes').val() && $('#appointmentTimeRes').val() && $('#appointmentTitle').val()) {
						$('#appointmentDateModal').html($('#appointmentDateRes').val());
						$('#appointmentTimeModal').html($('#appointmentTimeRes').val());
						$('#appointmentTitleModal').html($('#appointmentTitle').val());
						if($('#appmntInspnRemarks').val())
                        	$('#appmntInspnRemarksModal').html($('#appmntInspnRemarks').val());
						$('#myModal').modal('show');
					}

                    
					
					$("#updateInspectionForm").validate({
						highlight : function(element, errorClass) {
							$(element).fadeOut(function() {
								$(element).fadeIn();
							});
						}
					});

					
					// By default to point update noc details tab
					var mode = $('#mode').val();

					if (mode == 'view') {
						removeWorkFlowMandatoryAndHideDepartmentDetails();
					} else if (mode == 'captureInspection') {
						removeWorkFlowMandatoryAndHideDepartmentDetails();
						$("#Forward").hide();
						$("#Reject").hide();
					}
					if ($('#wfstateDesc').val() != 'Initiated Inspection'
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
					 if ( $('#wfstateDesc').val() == 'Town Surveyor Inspection Initiated') {
						$("#approverDetailBody").hide();
					} else if($('#isOneDayPermitApplication').val() === 'true' && $('#wfstateDesc').val() === 'Record Approved') {
                        removeWorkFlowMandatoryAndHideDepartmentDetails();
                        $("#approverDetailBody").hide();
					}
					 if ($('#wfstateDesc').val() == 'LP Created') {
							removeWorkFlowMandatoryAndHideDepartmentDetails();
							$("#buttonSubmit").hide();
						} else if ($('#wfstateDesc').val() == 'LP Reply Received') {
							removeWorkFlowMandatoryAndHideDepartmentDetails();
							$("#buttonSubmit").show();
						}

						
					function removeWorkFlowMandatoryAndHideDepartmentDetails() {
						$('#approvalDepartment').removeAttr('required');
						$('#approvalDesignation').removeAttr('required');
						$('#approvalPosition').removeAttr('required');
                        $("#approverDetailBody").hide();
						$('#amountRule').removeAttr('required');

                        
					}

					

					var tabfocus;
					if($('#wfstateDesc').val() == 'LP Created'
						|| $('#wfstateDesc').val() == 'LP Reply Received') {
                        tabfocus = '#view-lp';
					}  else {
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

					
					
				});