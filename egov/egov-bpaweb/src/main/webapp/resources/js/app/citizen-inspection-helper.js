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

$(document).ready(
		function($) {
			
			 if ($('#noJAORSAMessage') && $('#noJAORSAMessage').val())
			        bootbox.alert($('#noJAORSAMessage').val());
			 
			$('#buttonSubmit').click(function (e) {
				var  button =$('#buttonSubmit').val();
				document.getElementById("workFlowAction").value = 'Submit';
				if(validateFormOnSubmit(button,validator)){
			        bootbox
			            .dialog({
			                message: $('#submitApplication').val(),
			                buttons: {
			                    'confirm': {
			                        label: 'Yes',
			                        className: 'btn-primary',
			                        callback: function (result) {
			                            $("#createInspectionForm").attr('action', 'create/' + $("#bpaApplicationNumber").val());

			                        	$("#createInspectionForm").submit();
			                            return true;
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
				}
			    return false;
			});
		});

var validator = $("#createInspectionForm").validate({
	highlight : function(element, errorClass) {
		$(element).fadeOut(function() {
			$(element).fadeIn();
		});
	}
});

function validateFormOnSubmit(button, validator) {

    if ($('#createInspectionForm').valid()) {
        document.getElementById("workFlowAction").value = 'Submit';
        return true;
    } else {
        return validateMandatoryAndFocus(validator);
    }
}

function validateMandatoryAndFocus(validator) {
	$errorInput = undefined;

	$
			.each(validator.invalidElements(),
					function(index, elem) {

						if (!$(elem).is(":visible")
								&& !$(elem).val()
								&& index == 0
								&& $(elem).closest('div').find(
										'.bootstrap-tagsinput').length > 0) {
							$errorInput = $(elem);
						}

						if (!$(elem).is(":visible")
								&& !$(elem).closest('div.panel-body').is(
										":visible")) {
							$(elem).closest('div.panel-body').show();
							console.log("elem", $(elem));
						}
					});

	if ($errorInput)
		$errorInput.tagsinput('focus');

	validator.focusInvalid();
	return false;
}


