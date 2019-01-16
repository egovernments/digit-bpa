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


$(document).ready(function () {

    if ($('#showNotification').val()) {
        $('#infoModal').modal('show');
        $('#mobileNumber1').val('');
        $('#emailId1').val('');
    }

    $('#otp-section,#create-section').hide();

    //validate form while toggle between multiple tab
    $('#stakeHolderByCitizenform').validate({
        ignore: ".ignore",
        invalidHandler: function (e, validator) {
            if (validator.errorList.length)
                $('#settingstab a[href="#' + jQuery(validator.errorList[0].element).closest(".tab-pane").attr('id') + '"]').tab('show');
        }
    });

    var validator = $('#stakeHolderByCitizenform').validate({
        highlight: function (element, errorClass) {
            $(element).fadeOut(function () {
                $(element).fadeIn();
            });
        }
    });

    $('#buttonSubmit').click(function (e) {
        if (validateForm(validator)) {
            bootbox
                .confirm({
                    message: 'Do you really to want submit the application, once application is submitted then information cannot modify. Please cross verify once before submit.',
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
                            $('#stakeHolderByCitizenform').trigger('submit');
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

    function validateForm(validator) {
        if ($('form').valid() && validateUploadFilesMandatory()) {
            return true;
        } else {
            $.each(validator.invalidElements(), function (index, elem) {
                if (!$(elem).is(":visible") && !$(elem).closest('div.panel-body').is(":visible")) {
                    $(elem).closest('div.panel-body').show();
                }
            });
            validator.focusInvalid();
            return false;
        }
    }

    $('#otpbtn').click(function (e) {
        if (validateForm(validator)) {
            $.ajax({
                url: "/portal/citizen/signup/otp/"+$('#mobileNumber1').val()+"/"+$('#emailId1').val(),
                type: "get",
                dataType: "json",
                success: function (data) {
                    if (data) {
                        console.log('OTP sent');
                        $('#activationcode').val('');
                        $('#activationcode').attr('required', true);
                        $('#create-section,#otp-section').show();
                        $('#otpbtn-section').hide();
                    }
                },
                error: function () {
                    console.error('Error while sending otp');
                    $(this).show();
                }
            });
        } else {
            e.preventDefault();
        }
    });

    $(document).on('click', '.otp-view', function () {
        if ($(this).hasClass('otp-view')) {
            if ('show' == $(this).data('view')) {
                $(this).closest('.form-group').find('input').attr({type: 'text', autocomplete: 'off'});
                $(this).parent().empty().html('<i class="fa fa-eye-slash otp-view" data-view="hide" aria-hidden="true"></i>');
            } else {
                $(this).closest('.form-group').find('input').attr({type: 'password', autocomplete: 'new-password'});
                $(this).parent().empty().html('<i class="fa fa-eye otp-view" data-view="show" aria-hidden="true"></i>');
            }
        }
    });
});
