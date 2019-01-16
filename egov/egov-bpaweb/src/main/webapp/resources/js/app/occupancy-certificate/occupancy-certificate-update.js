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


jQuery(document).ready(function ($) {

    var inputs = $('#emailId');
    inputs.each(function () {
        this.style.textTransform = 'lowercase';
    }).keyup(function () {
        this.value = this.value.toLowerCase();
    });

    var seviceTypeName = $("#serviceType option:selected").text();

    //toggle between multiple tab
    jQuery('form').validate({
        ignore: ".ignore",
        invalidHandler: function (e, validator) {
            if (validator.errorList.length)
                $('#settingstab a[href="#' + jQuery(validator.errorList[0].element).closest(".tab-pane").attr('id') + '"]').tab('show');
        }
    });


    var validator = $("#occupancyCertificateUpdateForm").validate({
        highlight: function (element, errorClass) {
            $(element).fadeOut(function () {
                $(element).fadeIn();
            });
        }
    });

    if ($('#noJAORSAMessage') && $('#noJAORSAMessage').val())
        bootbox.alert($('#noJAORSAMessage').val());

    if ($('#invalidStakeholder').val())
        bootbox.alert($('#invalidStakeholder').val());

    function validateFormOnSave(button, validator) {
        // On Save documents mandatory check not require and on Submit only need to do documents mandatory check
        if ($('#occupancyCertificateUpdateForm').valid() && validateAdditionalConditions()) {
            document.getElementById("workFlowAction").value = button;
            validateIsAuthorizedToSubmitPlanOnCreate($("#extentinsqmts").val(), seviceTypeName);
            return true;
        } else {
            return validateMandatoryAndFocus(validator);
        }
    }

    function validateFormOnSubmit(button, validator) {

        if ($('#occupancyCertificateUpdateForm').valid() && validateUploadFilesMandatory() && validateAdditionalConditions()) {
            document.getElementById("workFlowAction").value = button;
            validateIsAuthorizedToSubmitPlanOnCreate($("#extentinsqmts").val(), seviceTypeName);
            return true;
        } else {
            return validateMandatoryAndFocus(validator);
        }
    }

    function validateMandatoryAndFocus(validator) {
        $errorInput = undefined;

        $.each(validator.invalidElements(), function (index, elem) {

            if (!$(elem).is(":visible") && !$(elem).val() && index == 0
                && $(elem).closest('div').find('.bootstrap-tagsinput').length > 0) {
                $errorInput = $(elem);
            }

            if (!$(elem).is(":visible") && !$(elem).closest('div.panel-body').is(":visible")) {
                $(elem).closest('div.panel-body').show();
                console.log("elem", $(elem));
            }
        });

        if ($errorInput)
            $errorInput.tagsinput('focus');

        validator.focusInvalid();

        return false;
    }

    function removeDisabledAttribute() {
        $('#serviceType').prop("disabled", false);
        $('#occupancyCertificateUpdateForm').find(':input', ':select', ':textarea').each(function () {
            $(this).removeAttr("disabled");
        });
    }

    function validateAdditionalConditions() {
        /*if ($('#citizenOrBusinessUser').val()) {
            if ($('#isCitizen').val() == 'true') {
                if ($('#validateCitizenAcceptance').val() == 'true' && !$('#citizenAccepted').prop('checked')) {
                    bootbox.alert("Please accept disclaimer to continue...");
                    return false;
                }
            } else {
                if (!$('#architectAccepted').prop('checked')) {
                    bootbox.alert("Please accept disclaimer to continue...");
                    return false;
                }
            }
        }*/

        return true;
    }

    function validateIsAuthorizedToSubmitPlanOnCreate(extentInSqmts, seviceTypeName) {
        var stakeHolderType = $('#stakeHolderType').val();
        if (stakeHolderType !== 'Town Planner - A' && (stakeHolderType === 'Engineer - B' || stakeHolderType === 'Building Designer - B') && 'Sub-Division of plot/Land Development' === seviceTypeName && extentInSqmts > 5000)
            $('#authorizedToSubmitPlan').val(true);
        else if (stakeHolderType !== 'Town Planner - A' && stakeHolderType !== 'Engineer - B' && stakeHolderType !== 'Building Designer - B' && 'Sub-Division of plot/Land Development' === seviceTypeName && extentInSqmts > 10000)
            $('#authorizedToSubmitPlan').val(true);
        else
            $('#authorizedToSubmitPlan').val(false);
    }

    function getValidationMessageOnCreate() {
        var extentInSqmts = $("#extentinsqmts").val();
        var stakeHolderType = $('#stakeHolderType').val();
        var msg;
        if ('Sub-Division of plot/Land Development' === seviceTypeName && stakeHolderType !== 'Town Planner - A' && (((stakeHolderType === 'Engineer - B' || stakeHolderType === 'Building Designer - B') && extentInSqmts > 5000) || ((stakeHolderType !== 'Engineer - B' && stakeHolderType !== 'Building Designer - B') && extentInSqmts > 10000)))
            msg = 'You are trying to submit the application more than the permissible plot area. Please present all the signed required documents mandatorily during document scrutiny. Click on YES to proceed with application or NO to re-enter the plot area.';
        else
            msg = '';
        return msg;
    }

    $('#ocSave').click(function (e) {
        var msg = getValidationMessageOnCreate();
        var button = $('#ocSave').val();
        if (validateFormOnSave(button, validator)) {
            bootbox
                .confirm({
                    message: 'Do you really want to save the application, once the application is saved you are not allowed to modify applicant details especially mobile number. Please make sure entered applicant details and mobile no. are valid before save.' + msg,
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
                            removeDisabledAttribute();
                            $('#occupancyCertificateUpdateForm').trigger('submit');
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


    $('#ocCreate').click(function (e) {
        var msg = getValidationMessageOnCreate();
        var button = $('#ocCreate').val();
        if (validateFormOnSubmit(button, validator)) {
            bootbox
                .confirm({
                    message: 'Do you really want to submit the application, once application is submitted you are not allowed to modify application details and please make sure entered details are valid before submit. ' + msg,
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
                            removeDisabledAttribute();
                            $('#occupancyCertificateUpdateForm').trigger('submit');
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

});
	