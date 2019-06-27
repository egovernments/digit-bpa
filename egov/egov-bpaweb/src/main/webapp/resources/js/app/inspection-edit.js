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
jQuery(document)
    .ready(
        function () {

            if ($('#sentToPreviousOwner').val() === 'true' &&
                $('#approveComments').val() &&
                $('#wfstateDesc').val() !== 'LP Created' &&
                $('#wfstateDesc').val() !== 'LP Reply Received') {
                $('#showCommentsModal').html($('#approveComments').val());
                $('#commentsModal').modal('show');
            }
            
           
            
            

                      if ($('#townSurveyorInspectionRequire:checked').length == 0) {
                $('#townSurveyorInspectionRequire').val(false);
            }
            $("#townSurveyorInspectionRequire").click(function () {
                if ($('#townSurveyorInspectionRequire').is(':checked')) {
                    $('#townSurveyorInspectionRequire').attr('checked', 'true');
                    $('#townSurveyorInspectionRequire').val(true);
                } else {
                    $('#townSurveyorInspectionRequire').attr('checked', 'false');
                    $('#townSurveyorInspectionRequire').val(false);
                }
            });

            function setCheckBoxValue(currentVal) {
                var $hiddenName = currentVal.data('change-to');
                if (currentVal.is(':checked')) {
                    $('input[name="' + $hiddenName + '"]').val(true);
                } else {
                    $('input[name="' + $hiddenName + '"]').val(false);
                }
            }

            // show mandatory fields on select of dynamic permit conditions
            $(".addremovemandatory").keyup(function () {
                if ($(this).val()) {
                    $(this).closest('td').find("span").addClass('display-hide');
                } else {
                    $(this).closest('td').find("span").removeClass('display-hide');
                }
            });

          

            // toggle between multiple tab
            jQuery('form')
                .validate(
                    {
                        ignore: ".ignore",
                        invalidHandler: function (e, validator) {
                            if (validator.errorList.length)
                                $(
                                    '#settingstab a[href="#'
                                    + jQuery(
                                    validator.errorList[0].element)
                                        .closest(
                                            ".tab-pane")
                                        .attr(
                                            'id')
                                    + '"]').tab(
                                    'show');
                        }
                    });

            var validator = $("#updateInspectionForm").validate({
                highlight: function (element, errorClass) {
                    $(element).fadeOut(function () {
                        $(element).fadeIn();
                    });
                }
            });

     
            $(".workAction")
                .click(
                    function (e) {
                        var action = document
                            .getElementById("workFlowAction").value;
                        if (action == 'Approve') {
                            if (validateOnApproveAndForward(validator, action)) {
                                bootbox
                                    .dialog({
                                        message: $('#approveAppln').val(),
                                        buttons: {
                                            'confirm': {
                                                label: 'Yes',
                                                className: 'btn-primary',
                                                callback: function (result) {
                                                    $('#updateInspectionForm').trigger('submit');
                                                }
                                            },
                                            'cancel': {
                                                label: 'No',
                                                className: 'btn-danger',
                                                callback: function (result) {
                                                    e.stopPropagation();
                                                    e.preventDefault();
                                                }
                                            }
                                        }
                                    });
                            } else {
                                e.preventDefault();
                            }
                            return false;
                        } else if (action == 'Forward') {
                            if (validateOnApproveAndForward(validator, action) && validateAdditionalConditionsOnFwd()) {
                                bootbox
                                    .dialog({
                                        message: $('#updateInspectionForm').val(),
                                        buttons: {
                                            'confirm': {
                                                label: 'Yes',
                                                className: 'btn-primary',
                                                callback: function (result) {
                                                    $('#viewBpaApplicationForm').trigger('submit');
                                                }
                                            },
                                            'cancel': {
                                                label: 'No',
                                                className: 'btn-danger',
                                                callback: function (result) {
                                                    e.stopPropagation();
                                                    e.preventDefault();
                                                }
                                            }
                                        },
                                    });
                            } else {
                                e.preventDefault();
                            }
                            return false;
                        } else if (action == 'Revoke') {
                            if (validateOnRevokePermit()) {
                                bootbox
                                    .dialog({
                                        message: $('#updateInspectionForm').val(),
                                        buttons: {
                                            'confirm': {
                                                label: 'Yes',
                                                className: 'btn-primary',
                                                callback: function (result) {
                                                    $('#updateInspectionForm').trigger('submit');
                                                }
                                            },
                                            'cancel': {
                                                label: 'No',
                                                className: 'btn-danger',
                                                callback: function (result) {
                                                    e.stopPropagation();
                                                    e.preventDefault();
                                                }
                                            }
                                        },
                                    });
                            } else {
                                e.preventDefault();
                            }
                            return false;
                        }else {
                            validateOnApproveAndForward(validator, action);
                        }
                    });

            $("#btnSave")
                .click(
                    function (e) {
                        document
                            .getElementById("workFlowAction").value = 'Save';
                        if(validateForm(validator))
                            $('#updateInspectionForm').trigger('submit');
                    });


        });

function validateAdditionalConditionsOnFwd() {
    var approvalComent = $('#approvalComent').val();
    if (($("#approvalDesignation option:selected").text() == 'Superintendent' && $('#townSurveyorInspectionRequire').val() == 'true')) {
        bootbox.alert($('#townsurvFieldInspeRequest').val());
        return false;
    } else if (($("#approvalDesignation option:selected").text() == 'Town Surveyor' && $('#townSurveyorInspectionRequire').val() == 'false')) {
        bootbox.alert($('#townsurvFieldInspeRequired').val());
        return false;
    } else if (($("#approvalDesignation option:selected").text() == 'Town Surveyor' || $('#townSurveyorInspectionRequire').val() == 'true') && approvalComent == "") {
        $('#approvalComent').focus();
        bootbox.alert($('#townsurvCommentsRequired').val());
        return false;
    }
    return true;
}

function validateOnReject(isCommentsRequire) {
    makePermitConditionsNotMandatory();
    var approvalComent = $('#approvalComent').val();
    var rejectionReasonsLength = $('.rejectionReasons:checked').length;
    if (rejectionReasonsLength <= 0) {
        $('.rejectionReason').show();
        bootbox.alert($('#rejectionReasonMandatory').val());
        return false;
    } else if (approvalComent == "" && isCommentsRequire) {
        bootbox.alert($('#rejectionCommentsRequired').val());
        $('#approvalComent').focus();
        return false;
    }
    return true;
}

function validateOnRevert() {
    makePermitConditionsNotMandatory();
    var approvalComent = $('#approvalComent').val();
    if (approvalComent == "") {
        $('#approvalComent').focus();
        bootbox.alert($('#applnSendbackCommentsRequired').val());
        return false;
    }
    return true;
}

function validateOnRevokePermit() {
    var approvalComent = $('#approvalComent').val();
   
   if (approvalComent == "") {
        $('#approvalComent').focus();
        bootbox.alert($('#revokeInsCommentsRequired').val());
        return false;
    }
    return true;
}

function validateForm(validator) {
    if ($('#updateInspectionForm').valid()) {
        return true;
    } else {
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
}


function validateOnApproveAndForward(validator, action) {
    validateWorkFlowApprover(action);
    if ($('#wfstateDesc').val() == 'NEW') {
        $('#approvalDepartment').removeAttr('required');
        $('#approvalDesignation').removeAttr('required');
        $('#approvalPosition').removeAttr('required');
        return true;
    } else {
        var serviceTypeName = $("#serviceType").val();
        if ($('#showPermitConditions').val() && serviceTypeName != 'Tower Construction'
            && serviceTypeName != 'Pole Structures') {
            var chkbxLength = $('.modifiablePermitConditions:checked').length;
            var chkbxLength1 = $('.staticPermitConditions:checked').length;
            if (chkbxLength <= 0 && chkbxLength1 <= 0) {
                bootbox.alert($('#permitRequired').val());
                return false;
            }
        }
        return validateForm(validator);
    }
}

function makePermitConditionsNotMandatory() {
    if($('#showPermitConditions').val()) {
        $(".modifiablePermitConditions").prop('checked', false);
        $(".staticPermitConditions").prop('checked', false);
        $(".modifiablePermitConditions").trigger('change');
    }
}
