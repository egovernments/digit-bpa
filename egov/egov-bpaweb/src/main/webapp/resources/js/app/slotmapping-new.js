/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2017  eGovernments Foundation
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
 *            Further, all user interfaces, including but not limited to citizen facing interfaces,
 *            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *            derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *            For any further queries on attribution, including queries on brand guidelines,
 *            please contact contact@egovernments.org
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
 *
 */
$(document)
		.ready(
				function() {
					if ($('#message').val()) {
						bootbox.alert($('#message').val());
					}
					$('.allservices').hide();
					$('.maxRescheduledSlotsAllowed').hide();
					$('.daysforonedaypermit').hide();
					$('#applType')
							.change(
									function() {
										var applicationTypeName = $(
												"#applType option:selected")
												.val();
										if ('ONE_DAY_PERMIT'
												.localeCompare(applicationTypeName) == 0) {
											$('.allservices').show();
											$('.daysforonedaypermit').show();
											$('.maxRescheduledSlotsAllowed')
													.hide();
											$('#zone').attr('required', true);
											$('#applType').attr('required',
													true);
											$('#ward').attr('required', true);
											$('#days').attr('required', true);
											$('#maxSlotsAllowed').attr(
													'required', true);
											$('.maxRescheduledSlotsAllowed')
													.removeAttr('required');
										} else if ('ALL_OTHER_SERVICES'
												.localeCompare(applicationTypeName) == 0 || 'OCCUPANCY_CERTIFICATE'.localeCompare(applicationTypeName) == 0) {
											$('.allservices').hide();
											$('.daysforonedaypermit').hide();
											$('.maxRescheduledSlotsAllowed')
													.show();
											$('#zone').attr('required', true);
											$('#applType').attr('required',
													true);
											$('#maxSlotsAllowed').attr(
													'required', true);
											$('.maxRescheduledSlotsAllowed')
													.attr('required', true);
											$('#ward').removeAttr('required');
											$('#days').removeAttr('required');

										}
									});

					$('#zone')
							.change(
									function() {
										$
												.ajax({
													url : "/bpa/boundary/ajaxBoundary-blockByWard",
													type : "GET",
													data : {
														wardId : $('#zone')
																.val()
													},
													cache : false,
													dataType : "json",
													success : function(response) {
														$('#revenueWard').html("");
														$('#revenueWard')
																.append(
																		"<option value=''>Select</option>");
														$
																.each(
																		response,
																		function(
																				index,
																				value) {
																			$(
																					'#revenueWard')
																					.append(
																							$(
																									'<option>')
																									.text(
																											value.blockName)
																									.attr(
																											'value',
																											value.blockId));
																		});
													},
													error : function(response) {
														$('#revenueWard').html("");
														$('#revenueWard')
																.append(
																		"<option value=''>Select</option>");
													}
												});
									});
					
					$('#revenueWard').change(function(){
						$.ajax({
							url: "/bpa/boundary/ajaxBoundary-electionwardbyrevenueward",
							type: "GET",
							data: {
								wardId : $('#revenueWard').val()
							},
							cache: false,
							dataType: "json",
							success: function (response) {
								$('#electionWard').html("");
								$('#electionWard').append("<option value=''>Select</option>");
								$.each(response, function(index, value) {
									$('#electionWard').append($('<option>').text(value.electionwardName).attr('value', value.electionwardId));
								});
							}, 
							error: function (response) {
								$('#electionWard').html("");
								$('#electionWard').append("<option value=''>Select</option>");
							}
						});
						
					});

					$('#applType').trigger("change");
					$('#zone').trigger("change");

					$('#buttonSubmit').click(function() {
						if ($('#slotMappingform').valid()) {
							return true;
						} else {
							return false;
						}
					});

				});