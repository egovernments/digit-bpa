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


$(document).ready(function ($) {

    if($('#isReconciliationInProgress').val() === 'true')
        bootbox.alert("For this application payment reconciliation is in progress, please wait for sometime!!!!!!")

    if ($('#isEDCRIntegrationRequire').val() === 'true') {
        $("#occupancyapplnlevel").attr("disabled", "true");
        $("#eDcrNumber").attr("disabled", "true");
        $('.edcrApplnDetails').show();
        $('#demolitionArea').attr('readOnly',true);
    } else {
        $("#occupancyapplnlevel").removeAttr("disabled");
        $("#eDcrNumber").removeAttr("disabled");
        $('.edcrApplnDetails').hide();
    }

    /*if('Addition or Extension' === $( "#serviceType option:selected" ).text() && 'Residential' === $("#occupancyapplnlevel option:selected").text())
        $('#isOneDayPermitApplication').attr('disabled',true);*/

    if ($('#invalidStakeholder').val())
        bootbox.alert($('#invalidStakeholder').val());

    if ($('#noJAORSAMessage') && $('#noJAORSAMessage').val())
        bootbox.alert($('#noJAORSAMessage').val());

    //toggle between multiple tab
    jQuery('form').validate({
        ignore: ".ignore",
        invalidHandler: function (e, validator) {
            if (validator.errorList.length)
                $('#settingstab a[href="#' + jQuery(validator.errorList[0].element).closest(".tab-pane").attr('id') + '"]').tab('show');
        }
    });

    $('#applicantDiv :input').attr('disabled', true);
    $('#gender').attr("style", "pointer-events: none;");

    if ($('#isCitizen').val() == 'true') {
        $(':input').attr('readOnly', true);
        $('#buttonSubmit,#button2').prop("readOnly", false);
        $('option:not(:selected)').attr('disabled', true);
        if ($('#validateCitizenAcceptance').val() == 'true') {
            $('#citizenAccepted').prop("readOnly", false);
            $("input[type=checkbox]:not('#citizenAccepted')").on("click", function (e) {
                e.preventDefault();
            });
        } else {
            $("input[type=checkbox]").on("click", function (e) {
                e.preventDefault();
            });
        }
    } else {
        $('#applicantDiv :input').attr('disabled', true);
        $("#serviceType").prop("disabled", true);
        $("#admissionfeeAmount").prop("disabled", true);
    }


    var validator = $("#editCitizenApplicationform").validate({
        highlight: function (element, errorClass) {
            $(element).fadeOut(function () {
                $(element).fadeIn();
            });
        }
    });


    $('#buttonSave').click(function (e) {
        var button = $('#buttonSave').val();
        if (validateEditFormOnSave(button, validator)) {
            var msg = getValidationMessageOnUpdate();
            bootbox
                .confirm({
                    message: 'Do you want to save the application ?. ' + msg,
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
                            $('#editCitizenApplicationform').trigger('submit');
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

    $('#buttonSubmit').click(function (e) {
        var msg = getValidationMessageOnUpdate();
        var button = $('#buttonSubmit').val();
        if (validateEditFormOnSubmit(button, validator)) {
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
                            $('#editCitizenApplicationform').trigger('submit');
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

    $('#buttonCancel').click(function (e) {

        if ($('#editCitizenApplicationform').valid()) {
            bootbox
                .confirm({
                    message: 'Do you really want to Cancel the application ?',
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
                            var button = $('#buttonCancel').val();
                            document.getElementById("workFlowAction").value = button;
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
        } else {
            e.stopPropagation();
            e.preventDefault();
        }
    });

});

function validateEditFormOnSave(button, validator) {

    // On Save documents mandatory check not require and on Submit only need to do documents mandatory check
    if ($('#editCitizenApplicationform').valid() && validateAdditionalConditionsOnUpdate(button)) {

        document.getElementById("workFlowAction").value = button;
        setBuildingModifiedDetails();
        validateIsAuthorizedToSubmitPlanOnUpdate($("#extentinsqmts").val());
        return true;

    } else {
        validateMandatoryAndFocus(validator);
    }
}

function validateEditFormOnSubmit(button, validator) {

    if ($('#editCitizenApplicationform').valid() && validateUploadFilesMandatory() && validateAdditionalConditionsOnUpdate(button)) {

        document.getElementById("workFlowAction").value = button;
        setBuildingModifiedDetails();
        validateIsAuthorizedToSubmitPlanOnUpdate($("#extentinsqmts").val());
        return true;

    } else {
        validateMandatoryAndFocus(validator);
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
    $("#applicantdet").prop("disabled", false);
    $("#appDet").prop("disabled", false);
    $("#serviceType").prop("disabled", false);
    $("#admissionfeeAmount").prop("disabled", false);
    $('#editCitizenApplicationform').find(':input', ':select', ':textarea').each(function () {
        $(this).removeAttr("disabled");
    });
}

function validateAdditionalConditionsOnUpdate(button) {
    if ($('#citizenOrBusinessUser').val()) {
        // for citizen login
        if ($('#validateCitizenAcceptance').val() == 'true' && $('#isCitizen').val() == 'true') {
            if (!$('#citizenAccepted').prop('checked')) {
                bootbox.alert("Please accept disclaimer to continue...");
                return false;
            }
        } else if ($('#isCitizen').val() != 'true') {  //for business user login
            if (typeof focusToTabElement === 'function') {
                focusToTabElement($('#architectAccepted'));
            }
            $('#architectAccepted').closest('div.panel-body').show();
            if (!$('#architectAccepted').prop('checked')) {
                bootbox.alert("Please accept disclaimer to continue...");
                return false;
            }
            if (button == 'Submit' && $('#validateCitizenAcceptance').val() == 'true' && $('#citizenDisclaimerAccepted').val() != 'true') {
                bootbox.alert("Citizen Disclaimer Acceptance Pending. Cannot Submit Application.");
                return false;
            }

        }
    }
    var seviceTypeName = $("#serviceType option:selected").text();
    if ('Amenities' == seviceTypeName && !$("#applicationAmenity option:selected").val()) {
        bootbox.alert("Please select at least one amenity.");
        return false;
    }
    if ($('#isOneDayPermitApplication').is(':checked')) {
        var inputArea;
        if ('Amenities' == seviceTypeName) {
            inputArea = $('#roofConversion').val();
        } else if ('Addition or Extension' === seviceTypeName || 'Alteration' === seviceTypeName) {
            if($('#totalPlintArea').val()) {
                inputArea = $('#totalPlintArea').val();
            } else {
                $('.totalPlintAreaFromEdcr').each(function () {
                    inputArea = parseFloat(inputArea) + parseFloat($(this).val());
                });
            }
            $('.existTotalPlintArea').each(function () {
                inputArea = parseFloat(inputArea) + parseFloat($(this).val());
            });
        } else {
            if($('#totalPlintArea').val()) {
                inputArea = $('#totalPlintArea').val();
            } else {
                $('.totalPlintAreaFromEdcr').each(function () {
                    inputArea = parseFloat(inputArea) + parseFloat($(this).val());
                });
            }
        }
        if (parseFloat(inputArea) > 300) {
            bootbox.alert("For one day permit application maximum permissible area allowed is less than or equal to 300 Sq.Mtrs. Beyond of maximum permissible area not allowed to submit application.");
            return false;
        }
    }
    return true;
}

function setBuildingModifiedDetails() {
    $('#setTotalPlintArea').val($('#totalPlintAreaFromEdcr').val());
    $('#setFloorCount').val($('#floorCountFromEdcr').val());
    $('#setHeightFromGroundWithStairRoom').val($('#heightFromGroundWithStairRoomFromEdcr').val());
    $('#setHeightFromGroundWithOutStairRoom').val($('#heightFromGroundWithOutStairRoomFromEdcr').val());
    $('#setFromStreetLevelWithStairRoom').val($('#fromStreetLevelWithStairRoomFromEdcr').val());
    $('#setFromStreetLevelWithOutStairRoom').val($('#fromStreetLevelWithOutStairRoomFromEdcr').val());
}

function validateIsAuthorizedToSubmitPlanOnUpdate(extentInSqmts) {
    var serviceType = $("#serviceType option:selected").text();
    var stakeHolderType = $('#stakeHolderType').val();
    if (stakeHolderType !== 'Town Planner - A' && (stakeHolderType === 'Engineer - B' || stakeHolderType === 'Building Designer - B') && 'Sub-Division of plot/Land Development' === serviceType && extentInSqmts > 5000)
        $('#authorizedToSubmitPlan').val(true);
    else if (stakeHolderType !== 'Town Planner - A' && stakeHolderType !== 'Engineer - B' && stakeHolderType !== 'Building Designer - B' && 'Sub-Division of plot/Land Development' === serviceType && extentInSqmts > 10000)
        $('#authorizedToSubmitPlan').val(true);
    else
        $('#authorizedToSubmitPlan').val(false);
}

function getValidationMessageOnUpdate() {
    var extentInSqmts = $("#extentinsqmts").val();
    var stakeHolderType = $('#stakeHolderType').val();
    var seviceTypeName = $("#serviceType option:selected").text();
    var msg;
    if ('Sub-Division of plot/Land Development' === seviceTypeName && stakeHolderType !== 'Town Planner - A' && (((stakeHolderType === 'Engineer - B' || stakeHolderType === 'Building Designer - B') && extentInSqmts > 5000) || ((stakeHolderType !== 'Engineer - B' && stakeHolderType !== 'Building Designer - B') && extentInSqmts > 10000)))
        msg = 'You are trying to submit the application more than the permissible plot area. Please present all the signed required documents mandatorily during document scrutiny. Click on YES to proceed with application or NO to re-enter the plot area.';
    else
        msg = '';
    return msg;
}