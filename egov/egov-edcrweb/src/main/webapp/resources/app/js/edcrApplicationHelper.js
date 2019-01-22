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

            /*$('#occupancy option').filter(function() {
                return ($(this).text() == 'Residential');
            }).prop('selected', true);

            $('#serviceType option').filter(function() {
                return ($(this).text() == 'New Construction');
            }).prop('selected', true);*/
            /*$(document).on('change',"#occupancy",function (){
                $('#planInfoOccupancy').val( $( "#occupancy option:selected" ).text());
            });
            $(document).on('change',"#serviceType",function (){
                $('#planInfoServiceType').val($( "#serviceType option:selected" ).text());
            });*/

            function validateNewPlanScrutiny() {
                if (!$('#myfile').val()) {
                    bootbox.alert('Please upload plan file');
                    return false;
                } else if ($('#edcrApplicationform').valid()) {
                    return true;
                } else {
                    return false;
                }
            }

            function validateResubmitPlanScrutiny() {
                if (!$('#myfile').val()) {
                    bootbox.alert('Please upload plan file');
                    return false;
                } else if ($('#edcrReuploadform').valid()) {
                    return true;
                } else {
                    return false;
                }
            }

            function getValidationMessageOnSubmit() {
                var serviceType = $('#serviceType').val();
                if (serviceType === 'Addition or Extension')
                    return 'Please confirm, The submitted DXF file for service type addition or extension for building plan scrutiny shall be as per KMBR rules defined as and above date 30th March 2000. Please click Yes to continue or No to cancel the scrutiny.';
                else
                    return 'Please confirm, do you really want to submit plan ?';
            }

            // New Upload EDCR Form Submit
            $('#buttonSubmit').click(function (e) {
                if (validateNewPlanScrutiny()) {
                    bootbox
                        .confirm({
                            message: getValidationMessageOnSubmit(),
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
                                    $('#edcrApplicationform').trigger('submit');
                                } else {
                                    e.stopPropagation();
                                    e.preventDefault();
                                }
                            }
                        });
                } else {
                    e.preventDefault();
                }
                return false;
            });

            // Re-Upload EDCR Form Submit
            $('#reUploadSubmit').click(function (e) {
                if (validateResubmitPlanScrutiny()) {
                    bootbox
                        .confirm({
                            message: getValidationMessageOnSubmit(),
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
                                    $('#edcrReuploadform').trigger('submit');
                                } else {
                                    e.stopPropagation();
                                    e.preventDefault();
                                }
                            }
                        });
                } else {
                    e.preventDefault();
                }
                return false;
            });

            $(document).on('change',"#applicationAmenity",function (){
                var amenities = [];
                $.each($("#applicationAmenity option:selected"), function(idx){
                    amenities.push($(this).text());
                });
                $('#amenities').val(amenities);
            });

            $(document).on('change',"#occupancies",function (){
                var occupancies = [];
                $.each($("#occupancies option:selected"), function(idx){
                    occupancies.push($(this).text());
                });
                $('#occupancy').val(occupancies);
            });

            $('#applicationNumber').blur(function() {
                $.ajax({
                    url : '/edcr/edcrapplication/get-information/'+$('#applicationNumber').val(),
                    type: "GET",
                    cache: false,
                    dataType: "json",
                    success: function (response) {
                        if(response) {
                            if(response.status == 'Accepted') {
                                bootbox.alert("One of E-DCR plan is approved for the application with application number "+$('#applicationNumber').val()+", so using this application number you are not allowed resubmit plan. Please use new application to submit new plan.");
                                $('#applicationNumber').val('');
                            } else {
                                $('#edcrApplnId').val(response.id);
                                $('#edcrApplication').val(response.id);
                                $('#applicationNumber').val(response.applicationNumber);
                                $('#occupancy').val(response.occupancy);
                                $('#applicantName').val(response.applicantName);
                                $('#serviceType').val(response.serviceType);
                                $('#amenities').val(response.amenities);
                            }
                        } else {
                            $('.resetValues').val('');
                            bootbox.alert("Please check application number is correct, with entered application number data not found.");
                        }
                    },
                    error: function (response) {
                        $('.resetValues').val('');
                        bootbox.alert("Please check application number is correct, with entered application number data not found.");
                    }
                });
            });

     });

    // multi-select without pressing ctrl key
    $("select.tick-indicator").mousedown(function(e){
        e.preventDefault();

        var select = this;
        var scroll = select.scrollTop;

        e.target.selected = !e.target.selected;

        $(this).trigger('change');

        setTimeout(function(){select.scrollTop = scroll;}, 0);

        $(select).focus();

    }).mousemove(function(e){e.preventDefault()});

