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

$(document)
		.ready(
				function() {

                    $('#wardId').change(function(){
                        $.ajax({
                            url: "/bpa/boundary/ajaxBoundary-electionwardbyrevenueward",
                            type: "GET",
                            data: {
                                wardId : $('#wardId').val()
                            },
                            cache: false,
                            dataType: "json",
                            success: function (response) {
                                $('#electionBoundary').html("");
                                $('#electionBoundary').append("<option value=''>Select</option>");
                                $.each(response, function(index, value) {
                                    $('#electionBoundary').append($('<option>').text(value.electionwardName).attr('value', value.electionwardId));
                                });
                            },
                            error: function (response) {
                                $('#electionBoundary').html("");
                                $('#electionBoundary').append("<option value=''>Select</option>");
                            }
                        });
                    });
                    $("#toDate").datepicker().datepicker("setDate", new Date());
					$('#serviceTypeEnum').on('change', function() {
						if($("#serviceTypeEnum option:selected" ).val() == 'ALL_OTHER_SERVICES') {
							$("#toDate").prop('disabled', true);
						} else {
							$("#toDate").prop('disabled', false);
						}
					});

					$('#resetbutton').on('click', function(event) {
                        $(".reset-value").each(function(){
                            $(this)[0].selectedIndex = 0;
                        });
                        $('#serviceTypeEnum').trigger('change');
					});

                    $('#serviceTypeEnum').trigger('change');
					var documentScrutinyUrl = '/bpa/application/documentscrutiny/';
					var rescheduleUrl = '/bpa/application/scrutiny/reschedule/';
					$('#btnSearch').click(function() {
                        $('#searchScrutinyApplicationForm').find(':input',':select').each(function(){
                            $(this).removeAttr("disabled")
                        });
						callAjaxSearch();
                        $('#serviceTypeEnum').trigger('change');
					});

					function callAjaxSearch() {
						$('.report-section').removeClass('display-hide');
						$("#search_bpa_application_table")
								.dataTable(
										{
                                            processing: true,
                                            serverSide: true,
                                            sort: true,
                                            filter: true,
                                            "searching": false,
                                            responsive: true,
                                            "order": [[1, 'asc']],
											ajax : {
												url : "/bpa/application/bpadocumentscrutiny",
												type : "POST",
												beforeSend : function() {
													$('.loader-class')
															.modal(
																	'show',
																	{
																		backdrop : 'static'
																	});
												},
                                                data: function (args) {
                                                    return {
                                                        "args": JSON.stringify(args),
                                                        "serviceTypeEnum": $("#serviceTypeEnum").val(),
                                                        "toDate": $("#toDate").val(),
                                                        "zoneId": $("#zone").val(),
                                                        "wardId": $("#wardId").val(),
                                                        "electionWardId": $("#electionBoundary").val()
                                                    };
                                                },
												complete : function() {
													$('.loader-class').modal(
															'hide');
												}
											},
											"bDestroy" : true,
											"sDom" : "<'row'<'col-xs-12 hidden col-right'f>r>t<'row'<'col-xs-3'i><'col-xs-3 col-right'l><'col-xs-3 col-right'<'export-data'T>><'col-xs-3 text-right'p>>",
											"oTableTools" : {
												"sSwfPath" : "../../../../../../egi/resources/global/swf/copy_csv_xls_pdf.swf",
												"aButtons" : [ "xls", "pdf",
																"print" ]
											},
											aaSorting : [],
											columns : [
													{
														"data" : "applicantName",
														"sClass" : "text-left"
													},
													{
														"data" : "applicationNumber",
														"sClass" : "text-left"
													},
													{
														"data" : "appointmentDate",
														"sClass" : "text-left"
													},
													{
														"data" : "appointmentTime",
														"sClass" : "text-left"
													},
													{
														"data" : "applicationDate",
														"sClass" : "text-left",
                                                        render: function (data) {
                                                            return data.split("-").reverse().join("/");
                                                        }
													},
													{
														"data" : "serviceType",
														"sClass" : "text-left"
													},
													{
														"data" : "occupancy",
														"sClass" : "text-left"
													},
													{
														"data" : "electionWard",
														"sClass" : "text-left"
													},
													{
														"data" : "status",
														"sClass" : "text-left"
													},
													{
														"data" : "currentOwner",
														"sClass" : "text-left"
													},
													{
														"data" : "pendingAction",
														"sClass" : "text-left"
													},
													{
														"data" : "isFeeCollected",
														"sClass" : "text-left",
														"visible": false
													}, 
													{
														"data" : null,
														"sClass" : "text-center",
														"render" : function(
																data, type,
																row, meta) {
																if(!row.isFeeCollected && !row.rescheduledByEmployee && !row.onePermitApplication){
                                                                    return '<button type="button" class="btn btn-xs btn-secondary documentScrutiny pull-left"  value='
                                                                        + documentScrutinyUrl
                                                                        + row.applicationNumber
                                                                        + '><span class="glyphicon glyphicon-view"></span>&nbsp;Document Scrutiny</button><br /><br /><button type="button" class="btn btn-xs btn-secondary ReSchedule pull-left" value='+rescheduleUrl+row.applicationNumber+'><span class="glyphicon glyphicon-view"></span>&nbsp;Re-Schedule</button>';
																} else if(row.isFeeCollected){
                                                                    return '<button type="button" class="btn btn-xs btn-secondary documentScrutiny" value="feePending"><span class="glyphicon glyphicon-view"></span>&nbsp;Document Scrutiny</button>';
                                                                } else {
																	return '<button type="button" class="btn btn-xs btn-secondary documentScrutiny" value='
																	+ documentScrutinyUrl
																	+ row.applicationNumber
																	+ '><span class="glyphicon glyphicon-view"></span>&nbsp;Document Scrutiny</button>';
																} 
														}
													} ]
										});
					}
					
					
					$(document).on('click','.documentScrutiny',function(){
					    var url = $(this).val();
					    if(url=='feePending') {
					    	bootbox.alert("Please Collect Application Fees to Process Application.");
					    } else {
					    	openPopup(url);
					    }
					});
                    $(document).on('click','.ReSchedule',function(){
                    	 var url = $(this).val();
                            openPopup(url);
                    });

					function openPopup(url)
					{
                        window.open(url,'window','scrollbars=yes,resizable=yes,height=700,width=800,status=yes');
                    }

				});

function getFormData($form) {
	var unindexed_array = $form.serializeArray();
	var indexed_array = {};

	$.map(unindexed_array, function(n, i) {
		indexed_array[n['name']] = n['value'];
	});

	return indexed_array;
}
