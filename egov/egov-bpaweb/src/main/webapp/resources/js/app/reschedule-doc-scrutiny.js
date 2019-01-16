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

$(document).ready(function() {

    if($('#slotsNotAvailableMsg').val()) {
        $('#scheduleElements').hide();
        $('#buttons').hide();
        $('#reScheduleButtons').show();
        $('#scrutinyNote2').show();
        bootbox.alert($('#slotsNotAvailableMsg').val())
    } else {
        $('#scheduleElements').show();
        $('#buttons').show();
        $('#reScheduleButtons').hide();
        $('#scrutinyNote2').hide();
    }


    $('.reScheduleAction').click(function(e) {
        var action = $(this).val();
        $('#reScheduleAction').val(action);
        if (action == 'Auto Re-Schedule') {
            bootbox
                .confirm({
                    message : 'Please confirm, system will automatically re-schedule to next immediate available slot once slots open and you will notified once rescheduled through sms and email or you can check through online portal services.',
                    buttons : {
                        'cancel' : {
                            label : 'No',
                            className : 'btn-danger'
                        },
                        'confirm' : {
                            label : 'Yes',
                            className : 'btn-primary'
                        }
                    },
                    callback : function(result) {
                        if (result) {
                            $('.loader-class').modal('show', {
                                backdrop : 'static'
                            });
                            document.forms[0].submit();
                        } else {
                            e.stopPropagation();
                            e.preventDefault();
                        }
                    }
                });
            return false;
        } else if (action == 'Cancel Application') {
            bootbox
                .confirm({
                    message : 'Please confirm, do you really going to cancel application ?',
                    buttons : {
                        'cancel' : {
                            label : 'No',
                            className : 'btn-danger'
                        },
                        'confirm' : {
                            label : 'Yes',
                            className : 'btn-primary'
                        }
                    },
                    callback : function(result) {
                        if (result) {
                            $('.loader-class').modal('show', {
                                backdrop : 'static'
                            });
                            document.forms[0].submit();
                        } else {
                            e.stopPropagation();
                            e.preventDefault();
                        }
                    }
                });
            return false;
        } else if (action == 'Re-Schedule') {
            if ($('#scrutinyReScheduleForm').valid()) {
                bootbox
                    .confirm({
                        message: 'Please confirm, do you really going to Re-Schedule appointment ?',
                        buttons: {
                            'cancel': {
                                label: 'No',
                                className: 'btn-danger'
                            },
                            'confirm': {
                                label: 'Yes',
                                className: 'btn-primary'
                            }
                        },
                        callback: function (result) {
                            if (result) {
                                    $('.loader-class').modal('show', {
                                        backdrop: 'static'
                                    });
                                    document.forms[0].submit();
                            } else {
                                e.stopPropagation();
                                e.preventDefault();
                            }
                        }
                    });
            }else {
                e.stopPropagation();
                e.preventDefault();
            }

            return false;
        }
    });

    $('#appointmentDate').change(function (event) {
        $.ajax({
            url : "/bpa/application/scrutiny/slotdetails",
            type : "GET",
            data : {
                appointmentDate : $('#appointmentDate').val(),
                zoneId : $('#zoneId').val()
            },
            async: false,
            cache : false,
            dataType : "json",
            success : function(response) {
                if(response.length == 0) {
                    $('#appointmentTime').html("");
                    $('#appointmentTime').append("<option value=''>Select</option>");
                    bootbox.alert("No slots available on selected date, please select different date.");
                    $('#appointmentDate').val('');
                } else {
                    $('#appointmentTime').html("");
                    $('#appointmentTime').append("<option value=''>Select</option>");
                    $.each(response, function(index, value) {
                        $('#appointmentTime').append(
                            $('<option>').text(value.appointmentTime).attr(
                                'value', value.appointmentTime));
                    });
                }
            },
            error : function(response) {
                $('#appointmentTime').html("");
                $('#appointmentTime').append("<option value=''>Select</option>");
            }
        });
    });

    $('#appointmentTime').change(function (event) {
        if($('#appointmentDate') && $('#appointmentTime')) {
            var previousAppointmentDate = moment($('#previousAppointmentDate').val(),[ "YYYYY-MM-DD" ]);
            var selectedAppointmentDate = moment($('#appointmentDate').val(),[ "DD/MM/YYYY" ]);
            var previousAppointmentTime = getAppointmentTime($('#previousAppointmentTime').val());
            var selectedAppointmentTime = getAppointmentTime($(this).val());
            if(previousAppointmentDate >= selectedAppointmentDate && previousAppointmentTime >= selectedAppointmentTime) {
                $(this).val('');
                bootbox.alert('Re-Schedule Date and Time should be greater than the earlier scheduled Date and Time');
            }
        }
    });

    function getAppointmentTime(appointmentTime) {
        var session;
        if(appointmentTime == 'Morning')
            session = 0;
        else if(appointmentTime == 'Evening')
            session = 1;
        return session;
    }
});