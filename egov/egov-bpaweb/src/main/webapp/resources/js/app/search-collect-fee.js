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
                    $('.plotAreaHideShow').hide();
                    $('.builtUpAreaHideShow').hide();

					var viewurl = '/bpa/application/view/';
					var collecturl = '/bpa/application/bpageneratebill/';

					$('#btnSearch').click(function() {
                        var isValid = false;
                        $('#searchCollectFeeForm').find(':input',':select',':textarea').each(function() {
                            if($(this).val()) {
                                isValid =true;
                                return false;
                            } else
                                isValid =false;
                        });
                        if(isValid) {
                            callAjaxSearch();
                        } else {
                            bootbox.alert($('#atleastOneInputReq').val());
                            return false;
                        }
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
												url : "/bpa/application/bpacollectfee",
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
                                                        "statusId": $("#statusId").val(),
                                                        "applicantName": $("#applicantName").val(),
                                                        "applicationNumber": $("#applicationNumber").val(),
                                                        "fromDate": $("#fromDate").val(),
                                                        "toDate": $("#toDate").val(),
                                                        "serviceTypeId": $("#serviceTypeId").val(),
                                                        "zoneId": $("#zone").val(),
                                                        "wardId": $("#ward").val(),
                                                        "electionWardId": $("#electionBoundary").val(),
                                                        "fromPlotArea": $("#fromPlotArea").val(),
                                                        "toPlotArea": $("#toPlotArea").val()
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
														"data" : null,
														"sClass" : "text-left",
														"render" : function(data, type, row, meta) {
															if(row.onePermitApplication) {
																return "One day permit";
															} else {
																return "Regular";
															}
														}
													},
													{
														"data" : "applicantName",
														"sClass" : "text-left"
													},
													{
														"data" : "applicationNumber",
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
														"data" : null,
														"sClass" : "text-center",
														"render" : function(
																data, type,
																row, meta) {
															
															if ((row.status == 'Approved' || row.status == 'Registered' || row.status == 'Created') && row.isFeeCollected) {
																return '<button type="button" class="btn btn-xs btn-secondary dropchange pull-left" value='
																+ collecturl
																+ row.applicationNumber
																+ '><span class="glyphicon glyphicon-view"></span>&nbsp;Collect Fee</button>';
															} else {
																return '<button type="button" class="btn btn-xs btn-secondary dropchange pull-left" value='
																+ viewurl
																+ row.applicationNumber
																+ '><span class="glyphicon glyphicon-view"></span>&nbsp;View</button>';
															}
														}
													} ]
										});
					}
					
					
					$(document).on('click','.dropchange',function(){
					    var url = $(this).val();
					    if(url){
					    	openPopup(url);
					    }
					    
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
