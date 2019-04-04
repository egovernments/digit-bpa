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

					var initiateRevoke = '/bpa/application/initiate/revocation/new/';
					var approveRevoke = '/bpa/application/revocation/approval/edit/';
					$('#btnSearch').click(function() {
                        $('#searchScrutinyApplicationForm').find(':input',':select').each(function(){
                            $(this).removeAttr("disabled")
                        });   
						callAjaxSearch();
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
												url : "/bpa/application/search/initiate-revocation",
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
                                                        "planPermissionNumber": $("#planPermissionNumber").val(),
                                                        "fromDate": $("#fromDate").val(),
                                                        "toDate": $("#toDate").val()
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
														"data" : "planPermissionNumber",
														"sClass" : "text-left"
													},
													{
														"data" : "planPermissionDate",
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
														"data" : "zone",
														"sClass" : "text-left"
													},
													{
														"data" : "ward",
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
														"data" : null,
														"sClass" : "text-center",
														"render" : function(
																data, type,
																row, meta) {
																if(row.status === 'Order Issued to Applicant' || row.status === 'Revocation cancelled'){
                                                                    return '<button type="button" class="btn btn-xs btn-secondary initiateRevoke pull-left"  value='
                                                                        + initiateRevoke
                                                                        + row.planPermissionNumber
                                                                        + '><span class="glyphicon glyphicon-view"></span>&nbsp;Initiate Revocation</button>';
																} else if(row.status === 'Revocation initiated'){
                                                                    return '<button type="button" class="btn btn-xs btn-secondary initiateRevoke pull-left"  value='
                                                                    + approveRevoke
                                                                    + row.planPermissionNumber
                                                                    + '><span class="glyphicon glyphicon-view"></span>&nbsp;Update/Approve Revocation</button>';
															}
														}
													} ]
										});
					}
					
					
					
                    $(document).on('click','.initiateRevoke',function(){
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
