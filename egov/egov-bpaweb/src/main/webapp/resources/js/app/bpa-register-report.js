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

					$('#btnSearch').click(function() {
                        var isValid = false;
                        $('#bpaRegisterReport').find(':input',':select',':textarea').each(function() {
                            if($(this).val()) {
                                isValid =true;
                                return false;
                            } else
                                isValid =false;
                        });
                        if(isValid) {
                            callAjaxSearch();
                        } else {
                            bootbox.alert($("#atleastOneInputReq").val());
                            return false;
                        }
					});

					function callAjaxSearch() {
						$('.report-section').removeClass('display-hide');
						$("#searchBpaRegister")
								.dataTable(
										{
                                            processing: true,
                                            serverSide: true,
                                            sort: true,
                                            filter: true,
                                            "searching": false,
                                            responsive: true,
                                            "order": [[1, 'asc']],
                                            paging: true,
                                            fixedColumns: {
                                                leftColumns: 3
                                            },
											ajax : {
												url : "/bpa/reports/bparegister",
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
                                                        "occupancyId": $("#occupancy").val(),
                                                        "fromDate": $("#fromDate").val(),
                                                        "toDate": $("#toDate").val(),
                                                        "serviceTypeId": $("#serviceTypeId").val(),
                                                        "applicationTypeId": $("#applicationTypeId").val(),
                                                        "revenueBoundary" : $('select[name="revenueBoundary"]').val(),
                                                       	"adminBoundary" : $('select[name="adminBoundary"]').val(),
                                                       	"locationBoundary" : $('select[name="locationBoundary"]').val(),
                                                        "userId": $("#userId").val()
                                                    };
                                                },
												complete : function() {
													$('.loader-class').modal(
															'hide');
												}
											},
											"bDestroy" : true,
											"autoWidth" : true,
                                            scrollX: true,
                                            scrollCollapse: true,
                                            "aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
                                            dom: "<'row'<'col-xs-4 pull-right'f>r>t<'row add-margin'<'col-md-3 col-xs-6'i><'col-md-2 col-xs-6'l>" +
                                            "<'col-md-2 col-xs-6 text-left'B><'col-md-5 col-xs-6 text-right'p>>",
                                            buttons: [{
                                                extend: 'pdf',
                                                title: 'Building Plan Application Register Report',
                                                filename: 'bpa_register_report',
                                                orientation: 'landscape',
                                                pageSize: 'A1',
                                                exportOptions: {
                                                    columns: ':visible'
                                                }
                                            }, {
                                                extend: 'excel',
                                                title: 'Building Plan Application Register Report',
                                                filename: 'bpa_register_report',
                                                exportOptions: {
                                                    columns: ':visible'
                                                }
                                            }, {
                                                extend: 'print',
                                                title: 'Building Plan Application Register Report',
                                                filename: 'bpa_register_report',
                                                orientation: 'landscape',
                                                pageSize: 'A1',
                                                exportOptions: {
                                                    columns: ':visible'
                                                }
                                            }],
											aaSorting : [],
											columns : [
													{
														"data" : null,
														render : function(data,
																type, row, meta) {
															return meta.row
																	+ meta.settings._iDisplayStart
																	+ 1;
														},
														"sClass" : "text-center"
													},
													{
														"data" : "applicationNumber",
														"sClass" : "text-left"
													},
													{
														"data" : "applicationType",
														"sClass" : "text-left"
													},
													{
														"data" : "serviceType",
														"sClass" : "text-left"
													},
													{
														"data" : "dateOfAdmission",
														"sClass" : "text-left"
													},
													{
														"data" : "applicantName",
														"sClass" : "text-left"
													},
													{
														"data" : "address",
														"sClass" : "text-left"
													},
													{
														"data" : "revenueWard",
														"sClass" : "text-left"
													},
													{
														"data" : "natureOfOccupancy",
														"sClass" : "text-left"
													},
													{
														"data" : "totalFloorArea",
														"sClass" : "text-left"
													},
													{
														"data" : "numberOfFloors",
														"sClass" : "text-left"
													},
													{
														"data" : "far",
														"sClass" : "text-left"
													},
													{
														"data" : "applicationFee",
														"sClass" : "text-left"
													},
													{
														"data" : "docVerificationDate",
														"sClass" : "text-left"
													},
													{
														"data" : "fieldVerificationDate",
														"sClass" : "text-left"
													},
													{
														"data" : "nocDetails",
														"sWidth": "40%",
														"sClass" : "text-left"
													},
													{
														"data" : "buildingPermitNo",
														"sClass" : "text-left"
													},
													{
														"data" : "permitFee",
														"sClass" : "text-left"
													},
													{
														"data" : "shelterFund",
														"sClass" : "text-left"
													},
													{
														"data" : "labourcess",
														"sClass" : "text-left"
													},
													{
														"data" : "developmentPermitFees",
														"sClass" : "text-left"
													},
													{
														"data" : "rejectionReason",
														"sClass" : "text-left"
													}, 
													{
														"data" : "finalApprovalDate",
														"sClass" : "text-left"
													},
													{
														"data" : "apprvd_rejected_by", 
														"sClass" : "text-left"
													} ],columnDefs:[
									     	              {
									     	                   "render": function ( data, type, row ) {
									     	                       return type === 'display' && '<div><span>'+(data.length > 100 ? data.substr( 0, 100 )+'</span> <button class="details" data-text="'+escape(data)+'" class="btn-xs" style="font-size:10px;">More <i class="fa fa-angle-double-right" aria-hidden="true"></i></button></div>' : data+"</p>");
									     	                   },
									     	                   "targets": [18,23]
										     	           }
						]  
										});
					}

                    var zoneLen = $("#zone option").length;
                    if(zoneLen === 2)
                        $('#zone').find('option').get(0).remove();
                    var userLen = $("#userId option").length;
                    if(userLen === 2)
                        $('#userId').find('option').get(0).remove();
				});

$("#searchBpaRegister").on('click','tbody tr td button.details',function(e) {
	if($(this).parent().find('span').text().length==100){
		$(this).parent().find('span').text(unescape($(this).data('text')));	
		$(this).html('<i class="fa fa-angle-double-left" aria-hidden="true"></i> Less');
	}
	else
	{
		$(this).parent().find('span').text(unescape($(this).data('text')).substr(0,100));	
		$(this).html('More <i class="fa fa-angle-double-right" aria-hidden="true"></i>');
	}
	e.stopPropagation();
	e.preventDefault();
});

function getFormData($form) {
	var unindexed_array = $form.serializeArray();
	var indexed_array = {};

	$.map(unindexed_array, function(n, i) {
		indexed_array[n['name']] = n['value'];
	});

	return indexed_array;
}