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
						callAjaxSearch();
					});

					function callAjaxSearch() {
						var applicationType = $('#applicationType').val();
						$('.report-section').removeClass('display-hide');
						$("#oneDayPermitApplnsSlotDetailsCount")
								.dataTable(
										{
                                            processing: true,
                                            serverSide: true,
                                            sort: true,
                                            filter: true,
                                            destroy: true,
                                            "searching": false,
                                            responsive: true,
                                            "order": [[2, 'desc'],[1, 'asc']],
											ajax : {
												url : "/bpa/reports/slotdetails/"+applicationType,
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
                                                        "appointmentDate": $("#fromDate").val(),
                                                        "zoneId": $("#zone").val(),
                                                        "revenueWardId": $("#ward").val(),
                                                        "electionWardId": $("#electionBoundary").val()
                                                    };
                                                },
												complete : function() {
													$('.loader-class').modal(
															'hide');
												}
											},
											"bDestroy" : true,
                                            "aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
                                            dom: "<'row'<'col-xs-4 pull-right'f>r>t<'row add-margin'<'col-md-3 col-xs-6'i><'col-md-2 col-xs-6'l>" +
                                            "<'col-md-2 col-xs-6 text-left'B><'col-md-5 col-xs-6 text-right'p>>",
                                            buttons: [{
                                                extend: 'pdf',
                                                title: 'One day permit application type slot details report',
                                                filename: 'One_day_permit_slot_dtls',
                                                orientation: 'landscape',
                                                pageSize: 'A4',
                                                exportOptions: {
                                                    columns: ':visible'
                                                }
                                            }, {
                                                extend: 'excel',
                                                title: 'One day permit application type slot details report',
                                                filename: 'One_day_permit_slot_dtls',
                                                exportOptions: {
                                                    columns: ':visible'
                                                }
                                            }, {
                                                extend: 'print',
                                                title: 'One day permit application type slot details report',
                                                filename: 'One_day_permit_slot_dtls',
                                                orientation: 'landscape',
                                                pageSize: 'A4',
                                                exportOptions: {
                                                    columns: ':visible'
                                                }
                                            }],
											aaSorting : [],
											columns : [
												{
													"data" : null,
													render : function(data, type, row, meta) {
														return meta.row
																+ meta.settings._iDisplayStart
																+ 1;
													},
													"sClass" : "text-center"
												},
												{
													"data" : "zone",
													"sClass" : "text-left"
												},{
                                                    "data" : "electionWard",
                                                    "sClass" : "text-left"
                                                },
                                                {
                                                    "data" : "appointmentDate",
                                                    "sClass" : "text-center",
                                                    render: function (data) {
                                                        return data.split("-").reverse().join("/");
                                                    }
                                                },
                                                {
                                                    "data" : "appointmentTime",
                                                    "sClass" : "text-center"
                                                },
                                                {
                                                    "data" : "maxScheduledSlots",
                                                    "sClass" : "text-center"
                                                },
                                                {
                                                    "data" : "utilizedScheduledSlots",
                                                    render : function(data, type, row, meta) {
                                                        return parseInt(row.utilizedScheduledSlots) !== 0 ? '<a onclick="openPopup(\'/bpa/reports/slotdetails/viewapplications?'
                                                            + 'applicationType='+applicationType
                                                            + '&'
                                                            + 'scheduleType=SCHEDULE'
                                                            + '&'
                                                            + 'appointmentDate='+row.appointmentDate
                                                            + '&'
                                                            + 'appointmentTime='+row.appointmentTime
                                                            + '&'
                                                            + 'zoneId='+row.zoneId
                                                            + '&'
                                                            + 'electionWardId='+row.electionWardId
                                                            + '\')" href="javascript:void(0);">'
                                                            + row.utilizedScheduledSlots + '</a>':row.utilizedScheduledSlots;
                                                    },
                                                    "sClass" : "text-center"
                                                }],
													
													"footerCallback" : function(row, data, start, end, display) {
														var api = this.api(), data;
														if (data.length == 0) {
															jQuery('#report-footer').hide();
														} else {
															jQuery('#report-footer').show(); 
														}
														if (data.length > 0) {
															updateTotalFooter(5, api);
															updateTotalFooter(6, api);
															}
													},
													"aoColumnDefs" : [ {
														"aTargets" : [5,6],
														"mRender" : function(data, type, full) {
															return formatNumberInr(data);    
														}
													} ]	
										});
					}
				});

function openPopup(url) {
	window.open(url, 'window',
			'scrollbars=1,resizable=yes,height=800,width=1100,status=yes');

}

function getFormData($form) {
	var unindexed_array = $form.serializeArray();
	var indexed_array = {};

	$.map(unindexed_array, function(n, i) {
		indexed_array[n['name']] = n['value'];
	});

	return indexed_array;
}

function updateTotalFooter(colidx, api) {
	// Remove the formatting to get integer data for summation
	var intVal = function(i) {
		return typeof i === 'string' ? i.replace(/[\$,]/g, '') * 1
				: typeof i === 'number' ? i : 0;
	};

	// Total over all pages
	
	if (api.column(colidx).data().length){
        var total = api
        .column(colidx )
        .data()
        .reduce( function (a, b) {
        return intVal(a) + intVal(b);
        } ) }
    else {
        total = 0
    }
    // Total over this page
	
	if (api.column(colidx).data().length){
        var pageTotal = api
            .column( colidx, { page: 'current'} )
            .data()
            .reduce( function (a, b) {
                return intVal(a) + intVal(b);
            } ) }
    else {
        pageTotal = 0
    }
    // Update footer
	jQuery(api.column(colidx).footer()).html(
			formatNumberInr(pageTotal) + ' (' + formatNumberInr(total)
					+ ')');
}


//inr formatting number
function formatNumberInr(x) {
	if (x) {
		x = x.toString();
		var afterPoint = '';
		if (x.indexOf('.') > 0)
			afterPoint = x.substring(x.indexOf('.'), x.length);
		x = Math.floor(x);
		x = x.toString();
		var lastThree = x.substring(x.length - 3);
		var otherNumbers = x.substring(0, x.length - 3);
		if (otherNumbers != '')
			lastThree = ',' + lastThree;
		var res = otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",")
				+ lastThree + afterPoint;
		return res;
	}
	return x;
}
