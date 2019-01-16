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

$(document).ready( function () {

    $('.currentOwner').hide();

    var inputs = $('#emailId1');
    inputs.each(function(){
        this.style.textTransform = 'lowercase';
    }).keyup(function(){
            this.value = this.value.toLowerCase();
    });

	// On edit form load checking is both addresses same then adding/removing check box value
	if( $('input[id="correspondenceAddress.houseNoBldgApt"]').val() != '' && $('input[id="correspondenceAddress.streetRoadLine"]').val() != ''
		&& $('input[id="permanentAddress.houseNoBldgApt"]').val() != '' && $('input[id="permanentAddress.streetRoadLine"]').val() != '') {
		if ($('input[id="permanentAddress.houseNoBldgApt"]').val() === $('input[id="correspondenceAddress.houseNoBldgApt"]').val()
			&& $('input[id="permanentAddress.streetRoadLine"]').val() === $('input[id="correspondenceAddress.streetRoadLine"]').val()
			&& $('input[id="permanentAddress.areaLocalitySector"]').val() === $('input[id="correspondenceAddress.areaLocalitySector"]').val()) {
			$('#isAddressSame').attr( "checked", "checked" );
		} else {
			$('#isAddressSame').removeAttr( "checked" );
		}
	}

    $('#buildingLicenceExpiryDate').on('changeDate', function() {
        validateBuildingLicenseeLicense();
    });
    $('#buildingLicenceIssueDate').on('changeDate', function() {
        validateBuildingLicenseeLicense();
    });
    function validateBuildingLicenseeLicense() {
        if($('#buildingLicenceExpiryDate') && $('#buildingLicenceIssueDate')) {
            var licenseExpiryDateTime = moment($('#buildingLicenceExpiryDate').val(),["DD/MM/YYYY"]);
            var licenseIssueDateTime = moment($('#buildingLicenceIssueDate').val(),["DD/MM/YYYY"]);
            if(licenseExpiryDateTime <= moment(new Date(),[ "DD/MM/YYYY" ])) {
                bootbox.alert("Please enter valid date, license expiry date should be greater than the today date.");
                $('#buildingLicenceExpiryDate').val('');
            } else if(licenseExpiryDateTime <= licenseIssueDateTime) {
                bootbox.alert("Please enter valid date, license expiry date should be greater than the license issued date.");
                $('#buildingLicenceExpiryDate').val('');
            }
        }
    }

	// mobile number validation
	$('#mobileNumber1').blur( function () {
		 var mobileno = $(this).val();
			if (mobileno && mobileno.length < 10) {
                var span = $(this).siblings('span');
                $(span).addClass('error-msg');
                $(span).text('Please enter 10 digit mobile number');
                $(this).focus();
                $(this).show();
                $(this).val("");
			} else {
                var span1 = $(this).siblings('span');
                $(span1).removeClass('error-msg');
                $(span1).text('');
                checkIsMobileNoIsExists();
			}
	});

	// To coping present address to permanent address if both same
	$('#isAddressSame').click(function(e) {
		if($('#isAddressSame').is(':checked')){
			$('input[id="permanentAddress.houseNoBldgApt"]').val($('input[id="correspondenceAddress.houseNoBldgApt"]').val());
			$('input[id="permanentAddress.streetRoadLine"]').val($('input[id="correspondenceAddress.streetRoadLine"]').val());
			$('input[id="permanentAddress.areaLocalitySector"]').val($('input[id="correspondenceAddress.areaLocalitySector"]').val());
			$('input[id="permanentAddress.cityTownVillage"]').val($('input[id="correspondenceAddress.cityTownVillage"]').val());
			$('input[id="permanentAddress.district"]').val($('input[id="correspondenceAddress.district"]').val());
			$('input[id="permanentAddress.state"]').val($('input[id="correspondenceAddress.state"]').val());
			$('input[id="permanentAddress.pinCode"]').val($('input[id="correspondenceAddress.pinCode"]').val());
			$('input[id="permanentAddress.postOffice"]').val($('input[id="correspondenceAddress.postOffice"]').val());
		}
	});
	
	$('#buttonSubmit').click(function(e){
		if($('#isAddressSame').is(':checked')){
			if($('input[id="permanentAddress.houseNoBldgApt"]').val() != $('input[id="correspondenceAddress.houseNoBldgApt"]').val()
				|| $('input[id="permanentAddress.streetRoadLine"]').val() != $('input[id="correspondenceAddress.streetRoadLine"]').val()
				|| $('input[id="permanentAddress.areaLocalitySector"]').val() != $('input[id="correspondenceAddress.areaLocalitySector"]').val()
				|| $('input[id="permanentAddress.cityTownVillage"]').val() != $('input[id="correspondenceAddress.cityTownVillage"]').val()
				|| $('input[id="permanentAddress.district"]').val() != $('input[id="correspondenceAddress.district"]').val()
				|| $('input[id="permanentAddress.state"]').val() != $('input[id="correspondenceAddress.state"]').val()
				|| $('input[id="permanentAddress.pinCode"]').val() != $('input[id="correspondenceAddress.pinCode"]').val()
				|| $('input[id="permanentAddress.postOffice"]').val() != $('input[id="correspondenceAddress.postOffice"]').val())
				{
		$('#isAddressSame').attr('checked', false);
				}
		}
	});

	// To show/hide Organization details based on user selection
	$("input[name='isOnbehalfOfOrganization']").change(function(){
		var isOnbehalfOfOrganization = $(this).val();
		if(isOnbehalfOfOrganization === 'true') {
			$('#showhide').removeClass('hide');
			$('.addremoverequired').attr( "required", "true" );
			$('.toggle-madatory').find("span").addClass( "mandatory" );
		} else {
			$('#showhide').addClass('hide');
			$('.addremoverequired').removeAttr( "required" );
			$('.toggle-madatory').find("span").removeClass( "mandatory" );
		}
	});
	if ($('#invalidBuildingLicensee').val())
		bootbox.alert($('#invalidBuildingLicensee').val());

	// email validation
	$('#emailId1').blur(function() {
		var pattern = new RegExp("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
		var email = $.trim($(this).val());
		if (email && !pattern.test(email) && $(this).val().length > 0) {
			var span = $(this).siblings('span');
	    	$(span).addClass('error-msg');
	    	$(span).text('Please enter valid email..!');
            $(this).focus();
			$(this).show();
			$(this).val("");
		} else {
			var span1 = $(this).siblings('span');
			$(span1).removeClass('error-msg');
	    	$(span1).text('');
            checkIsEmailExists();
		}
	});

    // aadhaarNumber validation
    $('#aadhaarNumber').blur( function () {
        var aadhaar = $(this).val();
        if (aadhaar && aadhaar.length < 12) {
            var span = $(this).siblings('span');
            $(span).addClass('error-msg');
            $(span).text('Aadhaar should be 12 digit number');
            $(this).focus();
            $(this).show();
            $(this).val("");
        } else {
            var span1 = $(this).siblings('span');
            $(span1).removeClass('error-msg');
            $(span1).text('');
            checkIsAadhaarExists();
        }
    });

    // panNumber validation
    $('#panNumber').blur( function () {
        var aadhaar = $(this).val();
        if (aadhaar && aadhaar.length < 10) {
            var span = $(this).siblings('span');
            $(span).addClass('error-msg');
            $(span).text('PAN should be 10 digit number');
            $(this).focus();
            $(this).show();
            $(this).val("");
        } else {
            var span1 = $(this).siblings('span');
            $(span1).removeClass('error-msg');
            $(span1).text('');
            checkIsPANExists();
        }
    });

    // licenceNumber validation
    $('#licenceNumber').blur( function () {
        var licenceNumber = $(this).val();
        if (licenceNumber) {
            var span1 = $(this).siblings('span');
            $(span1).removeClass('error-msg');
            $(span1).text('');
            checkIsLicenseNoExists();
        }  else {
            var span1 = $(this).siblings('span');
            $(span1).removeClass('error-msg');
            $(span1).text('');
        }
    });

    var stakeHolderId = $('#stakeHolderId').val();
    if(stakeHolderId){
        $('#emailId1').prop("disabled", true);
        $('#mobileNumber1').prop("disabled", true);
    }

    function checkIsMobileNoIsExists() {
        if($('#mobileNumber1').val()) {
            $.ajax({
                url: "/bpa/validate/emailandmobile",
                type: "GET",
                data: {
                    inputType : 'mobile',
                    inputValue : $('#mobileNumber1').val(),
                },
                cache: false,
                dataType: "json",
                success: function (response) {
                    if(true == response) {
                        var span = $('#mobileNumber1').siblings('span');
                        $(span).addClass('error-msg');
                        $(span).text('Mobile number is already exists');
                        $('#mobileNumber1').show();
                        $('#mobileNumber1').val('');
                        $('#mobileNumber1').focus();
                    } else {
                        var span1 = $('#mobileNumber1').siblings('span');
                        $(span1).removeClass('error-msg');
                        $(span1).text('');
                    }
                },
                error: function (response) {
                    console.log('Error occured, while validating mobile number!!!!')
                }
            });
        }
	}

	function checkIsEmailExists() {
        if($('#emailId1').val()) {
            $.ajax({
                url: "/bpa/validate/emailandmobile",
                type: "GET",
                data: {
                    inputType : 'email',
                    inputValue : $('#emailId1').val(),
                },
                cache: false,
                dataType: "json",
                success: function (response) {
                    if(true == response) {
                        var span = $('#emailId1').siblings('span');
                        $(span).addClass('error-msg');
                        $(span).text('Email is already exists');
                        $('#emailId1').show();
                        $('#emailId1').val('');
                        $('#emailId1').focus();
                    } else {
                        var span1 = $('#emailId1').siblings('span');
                        $(span1).removeClass('error-msg');
                        $(span1).text('');
                    }
                },
                error: function (response) {
                    console.log('Error occured, while validating email!!!!')
                }
            });
        }
    }

    function checkIsAadhaarExists() {
        if($('#aadhaarNumber').val()) {
            $.ajax({
                url: "/bpa/validate/emailandmobile",
                type: "GET",
                data: {
                    inputType : 'aadhaar',
                    inputValue : $('#aadhaarNumber').val(),
                },
                cache: false,
                dataType: "json",
                success: function (response) {
                    if(true == response) {
                        var span = $('#aadhaarNumber').siblings('span');
                        $(span).addClass('error-msg');
                        $(span).text('Aadhaar number is already exists');
                        $('#aadhaarNumber').show();
                        $('#aadhaarNumber').val('');
                        $('#aadhaarNumber').focus();
                    } else {
                        var span1 = $('#aadhaarNumber').siblings('span');
                        $(span1).removeClass('error-msg');
                        $(span1).text('');
                    }
                },
                error: function (response) {
                    console.log('Error occured, while validating aadhaar number!!!!')
                }
            });
        }
    }

    function checkIsPANExists() {
        if($('#panNumber').val()) {
            $.ajax({
                url: "/bpa/validate/emailandmobile",
                type: "GET",
                data: {
                    inputType : 'pan',
                    inputValue : $('#panNumber').val(),
                },
                cache: false,
                dataType: "json",
                success: function (response) {
                    if(true == response) {
                        var span = $('#panNumber').siblings('span');
                        $(span).addClass('error-msg');
                        $(span).text('PAN number is already exists');
                        $('#panNumber').show();
                        $('#panNumber').val('');
                        $('#panNumber').focus();
                    } else {
                        var span1 = $('#panNumber').siblings('span');
                        $(span1).removeClass('error-msg');
                        $(span1).text('');
                    }
                },
                error: function (response) {
                    console.log('Error occured, while validating PAN number!!!!')
                }
            });
        }
    }

    function checkIsLicenseNoExists() {
        if($('#licenceNumber').val()) {
            $.ajax({
                url: "/bpa/validate/emailandmobile",
                type: "GET",
                data: {
                    inputType : 'licenseNo',
                    inputValue : $('#licenceNumber').val(),
                },
                cache: false,
                dataType: "json",
                success: function (response) {
                    if(true == response) {
                        var span = $('#licenceNumber').siblings('span');
                        $(span).addClass('error-msg');
                        $(span).text('License number is already exists');
                        $('#licenceNumber').show();
                        $('#licenceNumber').val('');
                        $('#licenceNumber').focus();
                    } else {
                        var span1 = $('#licenceNumber').siblings('span');
                        $(span1).removeClass('error-msg');
                        $(span1).text('');
                    }
                },
                error: function (response) {
                    console.log('Error occured, while validating license number!!!!')
                }
            });
        }
    }

});
	


