/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2018>  eGovernments Foundation
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

    var validator = $("#permitRenewalUpdateForm").validate({
        highlight: function (element, errorClass) {
            $(element).fadeOut(function () {
                $(element).fadeIn();
            });
        }
    });
    

    var tbody = $('#renewalAdditionalRejectionReasons').children('tbody');
    var table = tbody.length ? tbody : $('#renewalAdditionalRejectionReasons');
    var row = '<tr>' +
        '<td class="text-center"><span class="serialNo text-center" id="slNoInsp">{{sno}}</span>'
        +'<input type="hidden" name="additionalRejectReasonsTemp[{{idx}}].application" value="{{applicationId}}" />'
        +'<input type="hidden" name="additionalPermitConditionsTemp[{{idx}}].noticeCondition.checklistServicetype" value="{{permitConditionId}}" />'
        +'<input type="hidden" class="additionalPermitCondition" name="additionalRejectReasonsTemp[{{idx}}].noticeCondition.type" value="ADDITIONALREJECTIONREASONS"/>'
        +'<input type="hidden" class="serialNo" data-sno name="additionalRejectReasonsTemp[{{idx}}].noticeCondition.orderNumber"/></td>' 
        +'<td><textarea class="form-control patternvalidation additionalPermitCondition" data-pattern="alphanumericspecialcharacters" rows="2" maxlength="500" name="additionalRejectReasonsTemp[{{idx}}].noticeCondition.additionalCondition"/></td>';
    $('#addAddnlRejectRow').click(function () {
        var idx = $(tbody).find('tr').length;
        if(validateAdditionalConditionsOrReasonsOnAdd('renewalAdditionalRejectionReasons')) {
        	//Add row
            var row = {
                'sno': idx + 1,
                'idx': idx,
                'permitConditionId': $('#additionalPermitCondition').val(),
                'applicationId': $('#scrutinyapplicationid').val()
                
            };
            addRowFromObject(row);
            patternvalidation();
        }
    });


    function validateAdditionalConditionsOrReasonsOnAdd(tableId){
    	var isValid=true;
        $('#'+tableId+' tbody tr').each(function(index){
        	var additionalPermitCondition  = $(this).find('*[name$="additionalCondition"]').val();
    	    if(!additionalPermitCondition) { 
    	    	bootbox.alert("Values cannot be empty"+$('#valuesCannotEmpty').val());
    	    	isValid=false;
    	    	return false;
    	    } 
        });
        return isValid;
    }

    function addRowFromObject(rowJsonObj) {
        table.append(row.compose(rowJsonObj));
    }
    
    $(".rejectionReasons").change(function () {
        setCheckBoxValue($(this));
    });
    
    String.prototype.compose = (function () {
        var re = /\{{(.+?)\}}/g;
        return function (o) {
            return this.replace(re, function (_, k) {
                return typeof o[k] != 'undefined' ? o[k] : '';
            });
        }
    }());
    
    function setCheckBoxValue(currentVal) {
        var $hiddenName = currentVal.data('change-to');
        if (currentVal.is(':checked')) {
            $('input[name="' + $hiddenName + '"]').val(true);
        } else {
            $('input[name="' + $hiddenName + '"]').val(false);
        }
    }

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
                                        $('#permitRenewalUpdateForm').trigger('submit');
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
                                        $('#permitRenewalUpdateForm').trigger('submit');
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
                                        $('#permitRenewalUpdateForm').trigger('submit');
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
                if (validateOnApproveAndForward(validator, action)) {
                    bootbox
                        .dialog({
                            message: $('#forwardAppln').val(),
                            buttons: {
                                'confirm': {
                                    label: 'Yes',
                                    className: 'btn-primary',
                                    callback: function (result) {
                                        $('#permitRenewalUpdateForm').trigger('submit');
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
            } else if (action == 'Generate Permit Renewal Order') {
                $('#Generate Permit Renewal Order').attr('formnovalidate', 'true');
                if (validateOnApproveAndForward(validator, action)) {
                    bootbox
                        .dialog({
                            message: $('#generatePermitOrder').val(),
                            buttons: {
                                'confirm': {
                                    label: 'Yes',
                                    className: 'btn-primary',
                                    callback: function (result) {
                                        $('#permitRenewalUpdateForm').trigger('submit');
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
                                        $('#permitRenewalUpdateForm').trigger('submit');
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
});



function validateOnApproveAndForward(validator, action) {
    validateWorkFlowApprover(action);
    if ($('#wfstateDesc').val() == 'NEW') {
        $('#approvalDepartment').removeAttr('required');
        $('#approvalDesignation').removeAttr('required');
        $('#approvalPosition').removeAttr('required');
        return true;
    } else {
        return validateForm(validator);
    }
}

function validateOnReject(isCommentsRequire) {
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

function validateForm(validator) {
    if ($('#permitRenewalUpdateForm').valid()) {
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
	