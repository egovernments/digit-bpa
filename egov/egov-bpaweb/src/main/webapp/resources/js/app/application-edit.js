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
            var tbody = $('#bpaAdditionalPermitConditions').children('tbody');
            var table = tbody.length ? tbody : $('#bpaAdditionalPermitConditions');
            var row = '<tr>' +
                '<td class="text-center"><span class="serialNo text-center" id="slNoInsp">{{sno}}</span>'
                +'<input type="hidden" name="additionalPermitConditionsTemp[{{idx}}].application" value="{{applicationId}}" />'
                +'<input type="hidden" name="additionalPermitConditionsTemp[{{idx}}].noticeCondition.checklistServicetype" value="{{permitConditionId}}" />'
                +'<input type="hidden" class="additionalPermitCondition" name="additionalPermitConditionsTemp[{{idx}}].noticeCondition.type" value="ADDITIONALCONDITIONS"/>'
                +'<input type="hidden" class="serialNo" data-sno name="additionalPermitConditionsTemp[{{idx}}].noticeCondition.orderNumber"/></td>' 
                +'<td><textarea class="form-control patternvalidation additionalPermitCondition" data-pattern="alphanumericspecialcharacters" rows="2" maxlength="500" name="additionalPermitConditionsTemp[{{idx}}].noticeCondition.additionalCondition"/></td>';


            $('#addAddnlPermitRow').click(function () {
                var idx = $(tbody).find('tr').length;
                if(validateAdditionalConditionsOrReasonsOnAdd('bpaAdditionalPermitConditions')) {
	                //Add row
	                var row = {
	                    'sno': idx + 1,
	                    'idx': idx,
	                    'permitConditionId': $('#additionalPermitCondition').val(),
	                    'applicationId': $('#applicationId').val()
	                };
	                addRowFromObject(row);
	                patternvalidation();
                }
            });

            var tbody1 = $('#bpaAdditionalRejectionReasons').children('tbody');
            var table1 = tbody1.length ? tbody1 : $('#bpaAdditionalRejectionReasons');
            var row1 = '<tr>' +
                '<td class="text-center"><span class="serialNo text-center" id="slNoInsp">{{sno}}</span>'
                +'<input type="hidden" name="additionalRejectReasonsTemp[{{idx}}].application" value="{{applicationId}}" />'
                +'<input type="hidden" name="additionalPermitConditionsTemp[{{idx}}].noticeCondition.checklistServicetype" value="{{permitConditionId}}" />'
                +'<input type="hidden" class="additionalPermitCondition" name="additionalRejectReasonsTemp[{{idx}}].noticeCondition.type" value="ADDITIONALREJECTIONREASONS"/>'
                +'<input type="hidden" class="serialNo" data-sno name="additionalRejectReasonsTemp[{{idx}}].noticeCondition.orderNumber"/></td>' 
                +'<td><textarea class="form-control patternvalidation additionalPermitCondition" data-pattern="alphanumericspecialcharacters" rows="2" maxlength="500" name="additionalRejectReasonsTemp[{{idx}}].noticeCondition.additionalCondition"/></td>';
            $('#addAddnlRejectRow').click(function () {
                var idx = $(tbody1).find('tr').length;
                if(validateAdditionalConditionsOrReasonsOnAdd('bpaAdditionalRejectionReasons')) {
                	//Add row
                    var row = {
                        'sno': idx + 1,
                        'idx': idx,
                        'permitConditionId': $('#additionalPermitCondition').val(),
                        'applicationId': $('#scrutinyapplicationid').val()
                        
                    };
                    addRowFromObject1(row);
                    patternvalidation();
                }
            });
            
            function validateAdditionalConditionsOrReasonsOnAdd(tableId){
            	var isValid=true;
                $('#'+tableId+' tbody tr').each(function(index){
                	var additionalPermitCondition  = $(this).find('*[name$="additionalCondition"]').val();
            	    if(!additionalPermitCondition) { 
            	    	bootbox.alert($('#valuesCannotEmpty').val());
            	    	isValid=false;
            	    	return false;
            	    } 
                });
                return isValid;
            }

            function addRowFromObject(rowJsonObj) {
                table.append(row.compose(rowJsonObj));
            }

            function addRowFromObject1(rowJsonObj) {
                table1.append(row1.compose(rowJsonObj));
            }

            String.prototype.compose = (function () {
                var re = /\{{(.+?)\}}/g;
                return function (o) {
                    return this.replace(re, function (_, k) {
                        return typeof o[k] != 'undefined' ? o[k] : '';
                    });
                }
            }());

            // If fee collection is pending disable updating permit conditions
            if ($('#showPermitConditions').val() === 'true' && $('#isFeeCollectionPending').val() === 'true' && $('#status').val() === 'Approved')
                $('#permitConditions').find(':input', ':checkbox', ':textarea', ':button').each(function () {
                    $(this).attr("disabled", "disabled");
                });
            else
                $('#permitConditions').find(':input', ':checkbox', ':textarea', ':button').each(function () {
                    $(this).removeAttr("disabled");
                });

            $('.modifiablePermitConditions').each(function () {
                var $hiddenName = $(this).data('change-to');
                var rowObj = $(this).closest('tr');
                if ($(this).is(':checked')) {
                    $('input[name="' + $hiddenName + '"]').val(true);
                    $(rowObj).find('.addremovemandatory').attr('required', true);
                    //$(rowObj).find("span").removeClass('display-hide');
                } else {
                    $('input[name="' + $hiddenName + '"]').val(false);
                    $(rowObj).find('.addremovemandatory').removeAttr('required');
                    $(rowObj).find('.addremovemandatory').val('');
                }
            });

            $(".modifiablePermitConditions").change(function () {
                var $hiddenName = $(this).data('change-to');
                var rowObj = $(this).closest('tr');
                if ($(this).is(':checked')) {
                    $('input[name="' + $hiddenName + '"]').val(true);
                    $(rowObj).find('.addremovemandatory').attr('required', true);
                    //$(rowObj).find("span").removeClass('display-hide');
                } else {
                    $('input[name="' + $hiddenName + '"]').val(false);
                    $(rowObj).find('.addremovemandatory').removeAttr('required');
                    $(rowObj).find('.addremovemandatory').val('');
                    //$(rowObj).find("span").addClass('display-hide');
                }
            });
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

            $(".staticPermitConditions").change(function () {
                setCheckBoxValue($(this));
            });

            $(".rejectionReasons").change(function () {
                setCheckBoxValue($(this));
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

            $('.permitConditiondDate').change(function () {
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

            var validator = $("#viewBpaApplicationForm").validate({
                highlight: function (element, errorClass) {
                    $(element).fadeOut(function () {
                        $(element).fadeIn();
                    });
                }
            });

            $('.upload-file').removeAttr('required');
            $("#mobileNumber").prop("readOnly", true);
            $("#name").prop("readOnly", true);
            $("#emailId").prop("readOnly", true);
            $('#gender').attr("style", "pointer-events: none;");
            $("#address").prop("readOnly", true);
            $("#admissionfeeAmount").prop("disabled", true);
            $("#applicationDate").prop("disabled", true);
            $("#stakeHolderTypeHead").prop("disabled", true);
            $("#stakeHolderType").prop("disabled", true);
            $("#applicantdet").prop("disabled", true);
            $("#appDet").prop("disabled", true);
            $("#serviceType").prop("disabled", true);

            $(".workAction")
                .click(
                    function (e) {
                        var action = document
                            .getElementById("workFlowAction").value;
                        if (action == 'Reject') {
                            $('#Reject').attr('formnovalidate', 'true');
                            if (validateOnReject(true) && validateForm(validator)) {
                                bootbox
                                    .dialog({
                                        message: $('#rejectAppln').val(),
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
                                        }
                                    });
                            } else {
                                e.preventDefault();
                            }
                            return false;
                        } if (action == 'Revoke Permit') {
                            $('#Revoke Permit').attr('formnovalidate', 'true');
                            if (validateOnRevokePermit() && validateForm(validator)) {
                                bootbox
                                    .dialog({
                                        message: $('#revokePermitAppln').val(),
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
                                        }
                                    });
                            } else {
                                e.preventDefault();
                            }
                            return false;
                        } else if (action == 'Initiate Rejection') {
                            $('#Initiate Rejection').attr('formnovalidate', 'true');
                            if (validateOnReject(true) && validateForm(validator)) {
                                bootbox
                                    .dialog({
                                        message: $('#intiateRejectionAppln').val(),
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
                                        }
                                    });
                            } else {
                                e.preventDefault();
                            }
                            return false;
                        } else if (action == 'Revert') {
                            if (validateOnRevert() && validateForm(validator)) {
                                bootbox
                                    .dialog({
                                        message: $('#sendBackApplnPreOfficial').val(),
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
                                        }
                                    });
                            } else {
                                e.preventDefault();
                            }
                            return false;
                        } else if (action == 'Approve') {
                            if (validateOnApproveAndForward(validator, action)) {
                                bootbox
                                    .dialog({
                                        message: $('#approveAppln').val(),
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
                                        message: $('#forwardAppln').val(),
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
                        } else if (action == 'Generate Permit Order') {
                            $('#Generate Permit Order').attr('formnovalidate', 'true');
                            if (validateOnApproveAndForward(validator, action)) {
                                bootbox
                                    .dialog({
                                        message: $('#generatePermitOrder').val(),
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
                                        }
                                    });
                            } else {
                                e.preventDefault();
                            }
                            return false;
                        } else if (action == 'Generate Rejection Notice') {
                            if (validateOnReject(false) && validateOnApproveAndForward(validator, action)) {
                                bootbox
                                    .dialog({
                                        message: $('#generateRejectNotice').val(),
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
                                        }
                                    });
                            } else {
                                e.preventDefault();
                            }
                            return false;
                        } else {
                            validateOnApproveAndForward(validator, action);
                        }
                    });

            $("#btnSave")
                .click(
                    function (e) {
                        document
                            .getElementById("workFlowAction").value = 'Save';
                        if(validateForm(validator))
                            $('#viewBpaApplicationForm').trigger('submit');
                    });

            // mobile number validation
            $('#mobileNumber')
                .blur(
                    function () {
                        var mobileno = $(this).val();
                        if (mobileno.length < 10) {
                            bootbox
                                .alert("Please enter 10 digit mobile number");
                            $(this).val('');
                        }
                    });

            $('#zone').trigger('change');
            //$('#ward').trigger('change');
            $('#schemes').trigger('change');

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
    makePermitConditionsNotMandatory();
    var approvalComent = $('#approvalComent').val();
   
    var revocationReasonsLength = $('.rejectionReasons:checked').length;
    if (revocationReasonsLength <= 0) {
        $('.rejectionReason').show();
        bootbox.alert($('#revocationReasonMandatory').val());
        return false;
    } else if (approvalComent == "") {
        $('#approvalComent').focus();
        bootbox.alert($('#revokePermitCommentsRequired').val());
        return false;
    }
    return true;
}

function validateForm(validator) {
    if ($('#viewBpaApplicationForm').valid()) {
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
