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

            $('#btnSearch').click(function (e) {
                var isValid = false;
                $('#personalRegister').find(':input',':select',':textarea').each(function() {
                    if($(this).val()) {
                        isValid =true;
                        return false;
                    } else
                        isValid =false;
                });
                if(isValid) {
                    callAjaxSearch();
                } else {
                    bootbox.alert("Please enter at least one input value to search");
                    return false;
                }
            });

            function callAjaxSearch() {
                $('.report-section').removeClass('display-hide');
                $("#personalRegisterReportTable")
                    .dataTable(
                        {
                            ajax : {
                                url : "/bpa/reports/personalregister",
                                type : "POST",
                                beforeSend : function() {
                                    $('.loader-class')
                                        .modal(
                                            'show',
                                            {
                                                backdrop : 'static'
                                            });
                                },
                                "data" : getFormData($('form')),
                                complete : function() {
                                    $('.loader-class').modal(
                                        'hide');
                                }
                            },
                            "searching": false,
                            "bDestroy" : true,
                            "scrollX": true,
                            "responsive": true,
                            "autoWidth": true,
                            fixedColumns: {
                                leftColumns: 3
                            },
                            "aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
                            dom: "<'row'<'col-xs-4 pull-right'f>r>t<'row add-margin'<'col-md-3 col-xs-6'i><'col-md-2 col-xs-6'l>" +
                            "<'col-md-2 col-xs-6 text-left'B><'col-md-5 col-xs-6 text-right'p>>",
                            buttons: [{
                                extend: 'pdf',
                                title: 'Personal Register Report',
                                filename: 'pr_register_report',
                                orientation: 'landscape',
                                pageSize: 'A1',
                                exportOptions: {
                                    columns: ':visible'
                                }
                            }, {
                                extend: 'excel',
                                title: 'Personal Register Report',
                                filename: 'pr_register_report',
                                exportOptions: {
                                    columns: ':visible'
                                }
                            }, {
                                extend: 'print',
                                title: 'Personal Register Report',
                                filename: 'pr_register_report',
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
                                    render : function(data, type, row, meta) {
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
                                    "data" : "permitType",
                                    "sClass" : "text-left"
                                },
                                {
                                    "data" : "dateOfAdmission",
                                    "sClass" : "text-left",
                                    render: function (data) {
                                        return data.split("-").reverse().join("/");
                                    }
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
                                    "data" : "surveyNo",
                                    "sClass" : "text-left"
                                },
                                {
                                    "data" : "village",
                                    "sClass" : "text-left"
                                },
                                {
                                    "data" : "revenueWard",
                                    "sClass" : "text-left"
                                },
                                {
                                    "data" : "electionWard",
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
                                    "data" : "noOfFloars",
                                    "sClass" : "text-left"
                                },
                                {
                                    "data" : "far",
                                    "sClass" : "text-left"
                                },
                                {
                                    "data" : "fromWhom",
                                    "sClass" : "text-left"
                                },
                                {
                                    "data" : "previousStatus",
                                    "sClass" : "text-left"
                                },
                                {
                                    "data" : "previousDateAndTime",
                                    "sClass" : "text-left",
                                    render: function (data) {
                                        return data.split("-").reverse().join("/");
                                    }
                                },
                                {
                                    "data" : "toWhom",
                                    "sClass" : "text-left"
                                },
                                {
                                    "data" : "currentStatus",
                                    "sClass" : "text-left"
                                },
                                {
                                    "data" : "nextDateAndTime",
                                    "sClass" : "text-left",
                                    render: function (data) {
                                        return data.split("-").reverse().join("/");
                                    }
                                }]
                        });
            }

            var zoneLen = $("#zone option").length;
            if(zoneLen === 2)
                $('#zone').find('option').get(0).remove();
            var userLen = $("#userId option").length;
            if(userLen === 2)
                $('#userId').find('option').get(0).remove();
        });



function getFormData($form) {
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i) {
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}

