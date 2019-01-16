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

$(document).ready(
		function($) {
			
			//toggle between multiple tab
			jQuery('form').validate({
				ignore: ".ignore",
				invalidHandler: function(e, validator){
				if(validator.errorList.length)
				$('#settingstab a[href="#' + jQuery(validator.errorList[0].element).closest(".tab-pane").attr('id') + '"]').tab('show');
				}
				});
			
			
			var validator = $("#newApplicationform").validate({
				highlight : function(element, errorClass) {
					$(element).fadeOut(function() {
						$(element).fadeIn();
					});
				}
			});

			if ($('#noJAORSAMessage') && $('#noJAORSAMessage').val())
				bootbox.alert($('#noJAORSAMessage').val());

			if ($('#invalidStakeholder').val())
				bootbox.alert($('#invalidStakeholder').val());

			$('#buttonSubmit').click(function(e) {
				return validateForm(validator);
			});

			function validateForm(validator) {
				if ($("#postalAddressTypeHead").val() == "") {
					bootbox.alert('Please Enter Pincode.');
					return false;
				}
				if ($('#newApplicationform').valid()
						&& validateUploadFilesMandatory()) {
					document.getElementById("workFlowAction").value = $(
							'#buttonSubmit').val();
					return true;
				} else {
					$errorInput=undefined;
					
					$.each(validator.invalidElements(), function(index, elem){
						
						if(!$(elem).is(":visible") && !$(elem).val() && index==0 
								&& $(elem).closest('div').find('.bootstrap-tagsinput').length > 0){
							$errorInput=$(elem);
						}
						
						if(!$(elem).is(":visible") && !$(elem).closest('div.panel-body').is(":visible")){
							$(elem).closest('div.panel-body').show();
						}
					});
					
					if($errorInput)
						$errorInput.tagsinput('focus');
					
					validator.focusInvalid();
					return false;
				}
			}

			$('.applicantname').hide();
			$('#name').change(function() {
				$('.applicantname').show();
				$("span#applicantName").html($(this).val());
			});
		});