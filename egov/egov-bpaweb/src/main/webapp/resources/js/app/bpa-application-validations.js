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

var occupancyResponse;
var subOccupancyResponse;
var extentOfLand;
var totalFloorArea;
var extentInSqmts;
var mixedOccupancyResponse;
var occupancyResponseByName;
var subOccupancyResponseByName;
var occupancySuboccupancyMap;
var usagesReponseBySuboccupancy;
$(document).ready(function() {

    getOccupancyObjects();
	getSubOccupancyObjects();
	getOccupancyAndSubOccupanyMap();
	getGroupedUsagesBySuboccupancy();
    //$('#oneDayPermitSec').hide();
    $('.buildingdetails').hide();
    $('.existingbuildingdetails').hide();
    $('.demolitionDetails').hide();
    removeMandatoryForExistingBuildingDetails();
    $('.show-hide').hide();
    $('.totalPlintArea').show();
    $('.dcrDocuments').hide();
    
    

    roundOfDecimalPlaces();
    function roundOfDecimalPlaces() {
        // Set decimal places of 2
        $('.decimalfixed').each(function(){
            if($(this).val()){
                $(this).val(parseFloat($(this).val()).toFixed(2));
            }
        });
    }

    // showing server side validation message in building details section as well as showing same in alert also.
    if($('#violationMessage').val()) {
        bootbox.alert($('#violationMessage').val()+$('#violationMessage1').val());
        $('.showViolationMessage').focus();
        $('.showViolationMessage').html($('#violationMessage').val());
        $('.showViolationMessage').closest('div.panel-body').show();
    }

    // Validate input value must be greater than zero using class name
    $.validator.addMethod("nonzero", function(value, element) {
        return this.optional(element) || (parseFloat(value) > 0);
    }, '* Value must be greater than zero');

    // pincode validation
    $.validator.addMethod("searchpincode", function(value, element) {
        return this.optional(element) || value !== '';
    }, 'required');

    // To prevent multiple decimal places in single input
    $(document).on('keypress', '.decimalfixed', function(evt) {

        evt = (evt) ? evt : window.event;
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if (charCode == 8 || charCode == 37) {
            return true;
        } else if (charCode == 46 && $(this).val().indexOf('.') != -1) {
            return false;
        } else if (charCode > 31 && charCode != 46 && (charCode < 48 || charCode > 57)) {
            return false;
        }
        return true;
    });
    
    $('textarea').keypress(function(event) {
    	if (event.keyCode == 13) {
    		event.preventDefault();
    	}
    });
    
    
    var myStr = $(".original").text();
    var trimStr = $.trim(myStr);
    $(".trimmed").html(trimStr);
    

    $(document).on('blur', '.decimalfixed', function(evt) {
        if($(this).val() == '.')
            $(this).val(0.0);
    });


    // government or quasi validation
    $('#isEconomicallyWeakerSec').hide();
    $('#governmentType').prop('checked', false);
    $('.governmentType').on('change', function() {
        var govType = $("input[name='governmentType']:checked").val();
        if(govType == 'NOT_APPLICABLE'){
            $('#governmentType').prop('checked', false);
            $('#isEconomicallyWeakerSec').hide();
        } else {
            $('#isEconomicallyWeakerSec').show();
        }
    });

    $('.governmentType').trigger("change");
    // TOWN PLANNING SCHEME VALIDATION
    $('#schemes').change(function(){
        var scheme = $( "#schemes option:selected" ).val();
        if(scheme) {
            $('#landUsage').attr('required',true);
            $('.landUsage').find("span").addClass( "mandatory" );
        } else {
            $('#landUsage').removeAttr('required');
            $('.landUsage').find("span").removeClass( "mandatory" );
        }
    });

    // For each main service type validations
    $('.serviceType').change(function(){

        loadAmenities();

        var seviceTypeName = $( "#serviceType option:selected" ).text();
        $('.doorNo').show();
        $('.show-hide').hide();
        $('.floor-toggle-mandatory').find("span").removeClass( "mandatory" );
        $('.floor-details-mandatory').removeAttr('required');
        $('.areaOfBase').hide();
        $('.extentOfLand').show();

        if('Sub-Division of plot/Land Development'.localeCompare(seviceTypeName) == 0 ){
            hideNewAndExistingBuildingDetails();
        } else if ('Demolition' == seviceTypeName){
            $('.amenityHideShow').hide();
            hideNewAndExistingBuildingDetails();
            showDemolitionDetails();
        } else if('Tower Construction'.localeCompare(seviceTypeName) == 0 || 'Pole Structures'.localeCompare(seviceTypeName) == 0){
            $('.extentOfLand').hide();
            $('.areaOfBase').show();
            hideNewAndExistingBuildingDetails();
        } else if('Amenities' == seviceTypeName){
            hideNewAndExistingBuildingDetails();
        } else if('Huts and Sheds' == seviceTypeName){
            hideNewAndExistingBuildingDetails();
            $('.noofhutorshed').show();
            $('.Hut').find("span").addClass( "mandatory" );
            $('.noofhutorshed').find("span").addClass( "mandatory" );
        } else if('Alteration' == seviceTypeName){
            addMandatoryForExistingBuildingDetails();
            showNewAndExistingBuildingDetails();
            addMandatoryForNewBuildingDetails();
            $('.alterationInArea').find("span").addClass( "mandatory" );
            $('.totalPlintArea').attr('required',true);
            $('.totalPlintArea').attr('readOnly',true);
            $('.alterationInArea').show();
            showDemolitionDetails();
        } else if('Addition or Extension' == seviceTypeName){
            addMandatoryForExistingBuildingDetails();
            showNewAndExistingBuildingDetails();
            addMandatoryForNewBuildingDetails();
            $('.totalPlintArea').attr('required',true);
            $('.totalPlintArea').attr('readOnly',true);
            $('.additionInArea').find("span").addClass( "mandatory" );
            $('.additionInArea').show();
            showDemolitionDetails();
        } else {
            removeMandatoryForExistingBuildingDetails();
            //$('.existingbuildingdetails').hide();
            if('New Construction'.localeCompare(seviceTypeName) == 0 ){
                //$('.buildingdetails').show();
                $('.totalPlintArea').show();
                $('.doorNo').hide();
                $('.demolitionArea').find("span").removeClass( "mandatory" );
                $('#demolitionArea').removeAttr('required');
                $('.demolitionDetails').show();
            } else if('Reconstruction'.localeCompare(seviceTypeName) == 0 ){
                $('.buildingdetails').show();
                $('.totalPlintArea').show();
                showDemolitionDetails();
            }  else if('Change in occupancy' == seviceTypeName){
                addMandatoryForExistingBuildingDetails();
                showNewAndExistingBuildingDetails();
                $('.changeInOccupancyArea').show();
            } else if ('Demolition' == seviceTypeName){
                $('.amenityHideShow').hide();
                $('.buildingdetails').show();
                $('.demolition').show();
                $('.demolitionDetails').show();
            } else {
                $('.totalPlintArea').show();
            }
            addMandatoryForNewBuildingDetails();
            jQuery('.totalPlintArea').attr('readOnly',true);
        }
    });
    
    function showDemolitionDetails() {
        $('.demolitionArea').find("span").addClass( "mandatory" );
        $('#demolitionArea').attr('required',true);
        $('.demolitionDetails').show();
    }

    function showNewAndExistingBuildingDetails() {
        $('.existingbuildingdetails').show();
        $('.buildingdetails').show();
    }
    function hideNewAndExistingBuildingDetails() {
        $('#buildingAreaDetails tbody').find("tr").remove();
        $('#existingBuildingAreaDetails tbody').find("tr").remove();
        $('.handle-mandatory').removeAttr('required');
        $('.handle-mandatory').find("span").removeClass( "mandatory" );
        $('.buildingdetails').hide();
        removeMandatoryForExistingBuildingDetails();
        $('.existingbuildingdetails').hide();
    }

    function addMandatoryForNewBuildingDetails() {
        $('.handle-mandatory').attr('required',true);
        $('.handle-mandatory').find("span").addClass( "mandatory" );
        $('.floor-toggle-mandatory').find("span").addClass( "mandatory" );
        $('.floor-details-mandatory').attr('required',true);
    }

    function removeMandatoryForNewBuildingDetails() {
        $('.handle-mandatory').removeAttr('required',true);
        $('.handle-mandatory').find("span").removeClass( "mandatory" );
        $('.floor-toggle-mandatory').find("span").removeClass( "mandatory" );
        $('.floor-details-mandatory').removeAttr('required',true);
    }

    function addMandatoryForExistingBuildingDetails() {
        $('.exist-handle-mandatory').attr('required',true);
        $('.exist-handle-mandatory').find("span").addClass( "mandatory" );
        $('.exist-floor-toggle-mandatory').find("span").addClass( "mandatory" );
        $('.exist-floor-details-mandatory').attr('required',true);
    }

    function removeMandatoryForExistingBuildingDetails() {
        $('.exist-handle-mandatory').removeAttr('required');
        $('.exist-handle-mandatory').find("span").removeClass( "mandatory" );
        $('.exist-floor-details-mandatory').find("span").removeClass( "mandatory" );
        $('.exist-floor-details-mandatory').removeAttr('required');
    }

    // Each Amenity type validations

    $(document).on('change',"#applicationAmenity",function () {
        loadAmenities();
    });

    function loadAmenities(){

        var amenities = [];

        if($( "#serviceType option:selected" ).text() == 'Huts and Sheds'){
            amenities.push('Huts and Sheds');
        } else if($( "#serviceType option:selected" ).text() == 'Tower Construction'){
            amenities.push('Tower Construction');
        } else if($( "#serviceType option:selected" ).text() == 'Pole Structures'){
            amenities.push('Pole Structures');
        }

        $.each($("#applicationAmenity option:selected"), function(idx){
            amenities.push($(this).text());
        });


        var result="";
        $.each(amenities, function(idx, value){
            //console.log('is even?', $(this).text(), idx, );
            var isEven=(parseInt(idx)%2 === 0);
            if(isEven){
                result= result+ (result?"</div><div class='form-group'>":"<div class='form-group'>");
            }
            result=result+getTemplateComplie(value, isEven);
        });
        result=result+"</div>";
        $('#amenitiesInputs').html(result);
        roundOfDecimalPlaces();
        patternvalidation();
    }

    function getTemplateComplie(value, isFirstPosition){
        var templateStr="";

        switch(value) {
            case "Well":
                templateStr=$('#well-template').html();
                break;
            case "Compound Wall":
                templateStr=$('#compound-template').html();
                break;
            case "Shutter or Door Conversion/Erection under rule 100 or 101":
                templateStr=$('#shutter-template').html();
                break;
            case "Roof Conversion under rule 100 or 101":
                templateStr=$('#roof-template').html();
                break;
            case "Tower Construction":
                templateStr=$('#tower-template').html();
                break;
            case "Pole Structures":
                templateStr=$('#poles-template').html();
                break;
            case "Huts and Sheds":
                templateStr=$('#sheds-template').html();
                break;
        }

        return templateStr.replace(/{className}/g, isFirstPosition?'col-sm-3':'col-sm-2');
    }

    $('#existingAppPlan').hide();
    $('#constDiv').hide();
    $('#isexistingApprovedPlan').on('change', function(){
        if(this.checked) // if changed state is "CHECKED"
        {
            $('.removemandatory').find("span").addClass( "mandatory" );
            $('#existingAppPlan').show();
            $('#feeAmountRecieptNo').attr('required', true);
            $('#approvedReceiptDate').attr('required', true);
            $('#approvedFeeAmount').attr('required', true);
            $('#revisedPermitNumber').attr('required', true);
        } else if(!this.checked) { // if changed state is "CHECKED"
            $('.removemandatory').find("span").removeClass( "mandatory" );
            $('#feeAmountRecieptNo').attr('required', false);
            $('#approvedReceiptDate').attr('required', false);
            $('#approvedFeeAmount').attr('required', false);
            $('#revisedPermitNumber').attr('required', false);
            $('#existingAppPlan').hide();
        }
    });

    $('#isappForRegularization').on('change', function(){
        if(this.checked) // if changed state is "CHECKED"
        {
            $('.constStages').find("span").addClass( "mandatory" );
            $('#constStages').attr('required', true);
            $('#constDiv').show();
            $('#inprogress').hide();
            $('#constStages').attr('required', true);
            $('.workCommencementDate1').hide();
            $('.workCompletionDate1').hide();
            $('#constStages').change(function(){
                if($('#constStages option:selected').html()=="In Progress" ||  $(this).val()=="-1"){
                    $('#inprogress').show();
                    $('.workCommencementDate1').show();
                    $('.workCompletionDate1').hide();
                    $('.workCompletionDate').find("span").removeClass( "mandatory" );
                    $('#workCompletionDate').removeAttr('required');
                    $('.workCommencementDate').find("span").addClass( "mandatory" );
                    $('#workCommencementDate').attr('required', true);
                    $('.stateOfConstruction').find("span").addClass( "mandatory" );
                    $('#stateOfConstruction').attr('required', true);
                } else if($('#constStages option:selected').html()=="Completed" ||  $(this).val()=="-1") {
                    $('.workCommencementDate1').show();
                    $('.workCompletionDate1').show();
                    $('.workCommencementDate').find("span").addClass( "mandatory" );
                    $('#workCommencementDate').attr('required', true);
                    $('.workCompletionDate').find("span").addClass( "mandatory" );
                    $('#workCompletionDate').attr('required', true);
                    $('#inprogress').hide();
                } else {
                    $('#inprogress').hide();
                    $('.workCommencementDate1').hide();
                    $('.workCompletionDate1').hide();
                }
            });
        } else if(!this.checked) // if changed state is "CHECKED"
        {
            $('#constStages').attr('required', false);
            $('#stateOfConstruction').attr('required', false);
            $('#workCommencementDate').attr('required', false);
            $('#workCompletionDate').attr('required', false);
            $('#constDiv').hide();
        }
    });


    $('#constStages').change(function(){
        if($('#constStages option:selected').html()=="NotStarted" ||  $(this).val()=="-1"){
            $('#stateOfConstruction').attr('required', true);
            $('#stateOfConstruction').append('<span class="mandatory">*</span>');

        }else if($('#constStages option:selected').html()=="Started"){
            $('#stateOfConstruction').attr('required', false);
        }
    });

    $('#workCommencementDate').on('changeDate', function() {
    	$('#workCompletionDate').val('').datepicker("refresh");
    });
    
    $('#workCompletionDate').on('changeDate', function() {

        if (!$('#workCommencementDate').val()) {
            bootbox.alert($('#startingDateReq').val());
            $('#workCompletionDate').val('').datepicker("refresh");
        } else if ($('#workCommencementDate').val() && moment($('#workCompletionDate').val(),'DD/MM/YYYY').isSameOrBefore(moment($('#workCommencementDate').val(),'DD/MM/YYYY'))) {
            bootbox.alert($('#completionDateReq').val());
            $('#workCompletionDate').val('').datepicker("refresh");
        }
    });

    // on form load get occupancy details List
    function getOccupancyObjects() {
        $.ajax({
            url: "/bpa/application/getoccupancydetails",
            async: false,
            type: "GET",
            dataType: "json",
            success: function (response) {
                occupancyResponse = arrayGroupByKey(response, 'id');
                occupancyResponseByName = arrayGroupByKey(response, 'name');
                mixedOccupancyResponse = response;
            },
            error: function (response) {
            }
        });
    }

    function getSubOccupancyObjects() {
        $.ajax({
            url: "/bpa/application/getsuboccupancydetails",
            async: false,
            type: "GET",
            dataType: "json",
            success: function (response) {
                subOccupancyResponse = arrayGroupByKey(response, 'id');
                subOccupancyResponseByName = arrayGroupByKey(response, 'name');
               // mixedOccupancyResponse = response;
            },
            error: function (response) {
            }
        });
    }
    
    function getOccupancyAndSubOccupanyMap(){
    	$.ajax({
            url: "/bpa/application/getOccupancyAndSuboccupancyMap",
            async: false,
            type: "GET",
            dataType: "json",
            success: function (response) {
            occupancySuboccupancyMap = response;
            },
            error: function (response) {
            }
        });
    }
    
    // Api call will group usages by sub occupancy and return {key:subOccupancy Id, value:<List<Usages>}  
    function getGroupedUsagesBySuboccupancy(){
    	$.ajax({
            url: "/bpa/application/group-usages/by-suboccupancy",
            async: false,
            type: "GET",
            dataType: "json",
            success: function (response) {
            	usagesReponseBySuboccupancy = response;
            },
            error: function (response) {
            }
        });
    }
    
    $('#extentOfLand,#unitOfMeasurement').change(function(){
        var extentOfLand = $('#extentOfLand').val();
        var uom = $('#unitOfMeasurement').val();
        if(extentOfLand && uom && convertExtendOfLandToSqmts(extentOfLand, uom) > 1000000) {
            $('#extentOfLand').val('');
            $('#extentinsqmts').val('');
            bootbox.alert($('#extendAreaLimit').val());
        }
    });

    $.validator.addMethod("maximumArea", function(value, element) {
        return this.optional(element) || parseFloat(value)  <= parseFloat(1000000);
    }, '* Maximum allowed area to enter is 1000000 Sq.Mtrs only.');


    var previousIndex;
    var oneDayPermitPreviousVal;
    var landTypeDescIndex;
    $('#occupancyapplnlevel').focus(function () {
        // Store the current value on focus, before it changes
        previousIndex = this.selectedIndex;
        oneDayPermitPreviousVal = $('#isOneDayPermitApplication').is(':checked');
        landTypeDescIndex = $('#typeOfLand').val()
    }).change(function(e){
        if($('#buildingAreaDetails0 tbody tr').length <= 1 && ($('.subOccupancy').val() == '' || $('.floorDescription').val() == '')){
            resetSubOccupancyDetails();
        } else if($('#buildingAreaDetails0 tbody tr').length >= 1 && $('.subOccupancy').val() != '' && $('.floorDescription').val() != ''){

            var dropdown=e;

            bootbox
                .confirm({
                    message : $('#resetFloorDetails').val(),
                    buttons : {
                        'cancel' : {
                            label : 'No',
                            className : 'btn-danger'
                        },
                        'confirm' : {
                            label : 'Yes',
                            className : 'btn-primary'
                        }
                    },
                    callback : function(result) {
                        if (result) {
                            $('#buildingAreaDetails0').find('input').not('input[type=hidden]').val('');
                            $('#buildingAreaDetails0').find('select').val('');
                            resetSubOccupancyDetails();
                            setFloorCount();
                            getOccupancyObject();
                            $( ".plinthArea" ).trigger( "change" );
                            $( ".carpetArea" ).trigger( "change" );
                            $( ".floorArea" ).trigger( "change" );
                            return true;
                        } else {
                            dropdown.target.selectedIndex = previousIndex;
                            if(oneDayPermitPreviousVal) {
                                $('#isOneDayPermitApplication').prop('checked', true);
                                $('#typeOfLand').val(landTypeDescIndex);
                            }
                            showOnePermitOnPageLoad();
                            //hideAndShowEdcrDetails();
                            return true;
                        }
                    }
                });
        } else if($('#buildingAreaDetails0 tbody tr').length >1 && ($('.subOccupancy').val() == '' || $('.floorDescription').val() == '')){
            resetSubOccupancyDetails();
        }
    });
    
    $(document).on('change',"#subOccupancies",function () {
    	resetSubOccupancyDetails();
    });
    
    function loadSubOccupancies(selectBoxName){
    	var occupancies = [];
    	$.each($("#occupancyapplnlevel option:selected"), function(idx){
    		occupancies.push($(this).text());
        });
		 $.ajax({
				url: "/bpa/getsuboccupancies/by-occupancy?occupancies="+occupancies,     
				type: "GET",
				async: false,
				dataType: "json",
				success: function (response) {
					$('select[name="'+selectBoxName+'"]').empty();
					$('select[name="'+selectBoxName+'"]').append($("<option value=''>Select </option>"));
					var occFirst;
					$.each(response, function(index, subOcc) {
						if(subOcc && subOcc.occupancy.name)
							occFirst = subOcc.occupancy.name;
						$('select[name="'+selectBoxName+'"]').append($('<option>').val(subOcc.id).text(occFirst +' - '+ subOcc.name));
					});
				}, 
				error: function (response) {
				}
			});
		}

    // onchange of main occupancy reset floorwise occupancy details column value
    function resetSubOccupancyDetails() {
    	var proposedBldgLen = $('.buildDetails').data('bldg-len');
    	for(var i = 0; i < proposedBldgLen; i++) {
    		$('.buildingAreaDetails'+i+' tbody tr *[name$="subOccupancy"]').each(function(idx) {
                var  selectBoxName = "buildingDetail["+i+"].applicationFloorDetails["+idx+"].subOccupancy";
                var  selectBoxName1 = "buildingDetail["+i+"].applicationFloorDetailsForUpdate["+idx+"].subOccupancy";
                var  usageSelectBoxName = "buildingDetail["+i+"].applicationFloorDetails["+idx+"].usage";
                var  usageSelectBoxName1 = "buildingDetail["+i+"].applicationFloorDetailsForUpdate["+idx+"].usage";
                clearExistingDropDownValues(selectBoxName);
                clearExistingDropDownValues(selectBoxName1);
                /*if($("#occupancyapplnlevel option:selected" ).text() == 'Mixed'){*/
                $('select[name="'+selectBoxName+'"]').append($("<option value=''>Select </option>"));
                $('select[name="'+selectBoxName1+'"]').append($("<option value=''>Select </option>"));
                $.each($("#occupancyapplnlevel option:selected"), function(idx1) {
                	
                    /*$.each(mixedOccupancyResponse, function(index, occupancyObj) {*/
                		var subOccupancies = occupancySuboccupancyMap[$(this).val()];
                		var usages = usagesReponseBySuboccupancy[$(this).val()];
                		$.each(subOccupancies, function(index, subOcc) {
    						//$('select[name="'+selectBoxName+'"]').append($('<option>').val(subOcc.id).text(subOcc.name));
    						loadDropdownValues(selectBoxName, subOcc.id, subOcc.name);
                            loadDropdownValues(selectBoxName1, subOcc.id, subOcc.name);
    					});
                		$.each(usages, function(index, usage) {
    						loadDropdownValues(usageSelectBoxName, usage.id, usage.name);
                            loadDropdownValues(usageSelectBoxName1, usages.id, usages.name);
    					});
                    	
                   // });
                });
                /*} else {
                    loadDropdownValues(selectBoxName, $("#occupancyapplnlevel option:selected" ).val(), $("#occupancyapplnlevel option:selected" ).text());
                    loadDropdownValues(selectBoxName1, $("#occupancyapplnlevel option:selected" ).val(), $("#occupancyapplnlevel option:selected" ).text());
                }*/
            });
    	}
    }

    function loadDropdownValues(selectBoxName,id,value){
        $('select[name="'+selectBoxName+'"]').append($('<option>').val(id).text(value));
    }

    function clearExistingDropDownValues(selectBoxName) {
        $('select[name="'+selectBoxName+'"]').empty();
    }

    $(document).on('blur', '.floorArea', function(e) {
        var isCitizenAcceptedForAdditionalFee = $('#isCitizenAcceptedForAdditionalFee').is(':checked');
        var seviceTypeName = $( "#serviceType option:selected" ).text();
        if(seviceTypeName && 'Alteration' != seviceTypeName && 'Addition or Extension' != seviceTypeName
            && 'Huts and Sheds' != seviceTypeName) {
            var rowObj = $(this).closest('tr');
            var occpancyObj = getOccupancyObject();
            if(occpancyObj && $("#occupancyapplnlevel option:selected" ).text() != 'Mixed') {
                var numOfTimesAreaPermissible = occpancyObj[0].numOfTimesAreaPermissible;
                var numOfTimesAreaPermWitAddnlFee = occpancyObj[0].numOfTimesAreaPermWitAddnlFee;
                var areaPermissibleWOAddnlFee = parseFloat(extentInSqmts) * parseFloat(numOfTimesAreaPermissible);
                var areaPermissibleWithAddnlFee = parseFloat(extentInSqmts) * parseFloat(numOfTimesAreaPermWitAddnlFee);
                totalFloorArea = parseFloat($("#sumOfFloorArea").val()).toFixed(2);
                if(areaPermissibleWithAddnlFee == 0) {
                    if(parseFloat(totalFloorArea) > areaPermissibleWOAddnlFee.toFixed(2)){
                        bootbox.alert($("#occupancyTypeMsg").val() +occpancyObj[0].description+$("#areaPermissibleWOAddnlFee1").val()+areaPermissibleWOAddnlFee.toFixed(2)+$("#areaPermissibleWOAddnlFee2").val());
                        $(rowObj).find('.floorArea').val('');
                        $( ".floorArea" ).trigger( "change" );
                        $( ".plinthArea" ).trigger( "change" );
                        $( ".carpetArea" ).trigger( "change" );
                        return false;
                    } else {
                        return true;
                    }
                } else if(parseFloat(totalFloorArea) > areaPermissibleWithAddnlFee.toFixed(2) && isCitizenAcceptedForAdditionalFee) {
                    bootbox.alert($("#occupancyTypeMsg").val() +occpancyObj[0].description+$("#areaPermissibleWithAddnlFee1").val()+areaPermissibleWOAddnlFee.toFixed(2)+$("#areaPermissibleWithAddnlFee2").val()+areaPermissibleWithAddnlFee.toFixed(2)+$("#areaPermissibleWithAddnlFee3").val()+areaPermissibleWithAddnlFee.toFixed(2)+$("#areaPermissibleWithAddnlFee4").val());
                    $(rowObj).find('.floorArea').val('');
                    $( ".floorArea" ).trigger( "change" );
                    $( ".plinthArea" ).trigger( "change" );
                    $( ".carpetArea" ).trigger( "change" );
                    return false;
                } else if(parseFloat(totalFloorArea) > areaPermissibleWOAddnlFee.toFixed(2) && !isCitizenAcceptedForAdditionalFee) {
                    bootbox
                        .confirm({
                            message : $("#occupancyTypeMsg").val() +occpancyObj[0].description+$("#confirmAreaPermiWOAddnlFee").val()+areaPermissibleWOAddnlFee.toFixed(2)+$("#confirmAreaPermiWOAddnlFee1").val(),
                            buttons : {
                                'cancel' : {
                                    label : 'No',
                                    className : 'btn-danger'
                                },
                                'confirm' : {
                                    label : 'Yes',
                                    className : 'btn-primary'
                                }
                            },
                            callback : function(result) {
                                if (result) {
                                    $('#isCitizenAcceptedForAdditionalFee').prop('checked',true);
                                } else {
                                    $(rowObj).find('.floorArea').val('');
                                    $('#isCitizenAcceptedForAdditionalFee').prop('checked',false);
                                    $( ".floorArea" ).trigger( "change" );
                                    $( ".plinthArea" ).trigger( "change" );
                                    $( ".carpetArea" ).trigger( "change" );
                                    e.stopPropagation();
                                    e.preventDefault();
                                }
                            }
                        });
                }
            }
        }
    });

    $('.for-calculation').change(function(){
        getOccupancyObject();
        $("#extentinsqmts").val(convertExtendOfLandToSqmts(extentOfLand, $("#unitOfMeasurement").val()));
        $("#extentinsqmtshdn").val(convertExtendOfLandToSqmts(extentOfLand, $("#unitOfMeasurement").val()));
    });

    // multi-select without pressing ctrl key
    $(document).on('mousedown', 'select.tick-indicator', function(e) {
    	var isReadonly = $(this).attr('readonly');
    	e.preventDefault();
    	if(!isReadonly) {
    		e.preventDefault();

            var select = this;
            var scroll = select.scrollTop;

            e.target.selected = !e.target.selected;

            $(this).trigger('change');

            setTimeout(function(){select.scrollTop = scroll;}, 0);

            $(select).focus();
    	}

    }).mousemove(function(e){e.preventDefault()});

    // trigger events on pageloadr
    $( "#isexistingApprovedPlan" ).trigger( "change" );
    $( "#isappForRegularization" ).trigger( "change" );
    $( "#constStages" ).trigger( "change" );
    $( ".serviceType" ).trigger( "change" );
    $( ".applicationAmenity" ).trigger( "change" );
    checkIsEdcrRequire();
    var serviceTypeName = $( "#serviceType option:selected" ).text();
    //hideAndShowEdcrDetails();
    if('Addition or Extension' == serviceTypeName || 'Alteration' == serviceTypeName || 'New Construction' == serviceTypeName
        || 'Amenities' == serviceTypeName) {

        showOnePermitOnPageLoad();
        $(document).on('change',"#occupancyapplnlevel",function () {
            //$('.amenityHideShow').show();
            //resetValuesForAmenitiesOfOneDayPermit();
            if($("#occupancyapplnlevel option:selected" ).text() == 'Residential'){
                $('#oneDayPermitSec').show();
                $('#isOneDayPermitApplication').prop('checked', false);
                $('#isOneDayPermitApplication').val(false);
                $('#oneDayPermitTypeOfLandSec').hide();
                
            } else {
                $('#typeOfLand').val('');
                $('#typeOfLand').removeAttr('required');
                $('#isOneDayPermitApplication').prop('checked', false);
                $('#oneDayPermitSec').hide();
                $('#isOneDayPermitApplication').val(false);
            }
            //hideAndShowEdcrDetails();
        });
    }
    
    
    function hideAndShowEdcrDetails() {
        if( serviceTypeName && ('New Construction' === serviceTypeName || ('Addition or Extension' === serviceTypeName && $('#isOneDayPermitApplication').is(':checked')))
            && $("#occupancyapplnlevel option:selected" ) && $("#occupancyapplnlevel option:selected" ).text() == 'Residential') {
            showDCRDetails();
        } else {
            hideDcrDetails();
        }
    }

    function showDCRDetails() {
        $('#eDcrNumber').attr('required', true);
        $('#eDcrNumber').show();
        $('.edcrApplnDetails').show();
        $('.dcrDocuments').show();
        $('div.mandatory-dcr-doc').attr('required','required');
        $('#extentOfLand').attr('disabled', true);
        $('#unitOfMeasurement').attr('disabled', true);
        if($('#loadingFloorDetailsFromEdcrRequire').val() === 'true') {
            $("#buildingAreaDetails tbody").find("tr:gt(0)").remove();
            $('.dcr-floor-toggle-mandatory').find("span").addClass( "mandatory" );
            $('.dcr-floor-details-mandatory').attr('required',true);
            $('#heightFromGroundWithOutStairRoomFromEdcr').attr('required',true);
            $('.buildingdetails').hide();
            $('.edcrbuildingdetails').show();
            removeMandatoryForNewBuildingDetails();
        } else {
            $('.dcr-floor-details-mandatory').removeAttr('required');
            $('#heightFromGroundWithOutStairRoomFromEdcr').removeAttr('required');
            $('.buildingdetails').show();
            $('.edcrbuildingdetails').hide();
            addMandatoryForNewBuildingDetails();
        }
    }

    function hideDcrDetails() {
        $('#eDcrNumber').removeAttr('required');
        $('#eDcrNumber').hide();
        $('#eDcrNumber').val('');
        $('.edcrApplnDetails').hide();
        $('.dcrDocuments').hide();
        $('div.mandatory-dcr-doc').removeAttr('required');
        $('#extentOfLand').removeAttr('disabled');
        $('#unitOfMeasurement').removeAttr('disabled');
        $('.buildingdetails').show();
        $('.edcrbuildingdetails').hide();
        $('.dcr-floor-toggle-mandatory').find("span").removeClass( "mandatory" );
        $('.dcr-floor-details-mandatory').removeAttr('required');
        $('#heightFromGroundWithOutStairRoomFromEdcr').removeAttr('required');
        resetDCRAutoPopulatedValues();
        addMandatoryForNewBuildingDetails();
        $( ".serviceType" ).trigger( "change" );
    }
    function resetDCRAutoPopulatedValues() {
        $('#edcrApplicationNumber').html('');
        $('#edcrUploadedDate').html('');
        $('#edcrDxfFile').html('');
        $('#edcrPlanReportOutput').html('');
        $("#edcrBuildingAreaDetails tbody tr").each(function() {
            $(this).closest('tr').remove();
            $("#edcrBuildingAreaDetails tfoot tr td:eq(4)").html('');
            $("#edcrBuildingAreaDetails tfoot tr td:eq(5)").html('');
            $("#edcrBuildingAreaDetails tfoot tr td:eq(6)").html('');
        });
        $('.dcr-reset-values').val('');
    }

    function checkIsEdcrRequire() {
        $.ajax({
            url : "/bpa/application/edcr-require",
            type : "GET",
            contentType: 'application/json; charset=UTF-8',
            data : {
                "serviceType" : $('#serviceTypeCode').val(),
                "occupancy" : $("#occupancyapplnlevel option:selected" ).text()
            },
            cache : false,
            dataType: "json",
            success : function(response) {
                $('#isEDCRIntegrationRequire').val(response);
                if(response) {
                    showDCRDetails();
                    $('#applicationType').attr('disabled', true);
                } else {
                    hideDcrDetails();
                }
            },
            error : function(response) {
            }
        });
    }
    function showOnePermitOnPageLoad() {

        /*if('Amenities' == $( "#serviceType option:selected" ).text())
            $('.amenityHideShow').show();
        else
        if($('#isOneDayPermitApplication').is(':checked'))
            $('.amenityHideShow').show();
        else
            $('.amenityHideShow').hide();*/

        if($("#occupancyapplnlevel option:selected" ).text() == 'Residential') {
            if($('#isOneDayPermitApplication').is(':checked')) {
                $('#applicationTypeSec').hide();
                $('#applicationType').attr('required',false);
                $('.documentRequire').attr('required', 'required');
                $('.document-desc').find('span').addClass('mandatory');
                $('#oneDayPermitSec').show();
                $('#oneDayPermitTypeOfLandSec').show();
            } else {
                $('#applicationTypeSec').show();
                $('#applicationType').attr('required',true);
                $('.document-desc').find('span').removeClass('mandatory');
                $('.documentRequire').removeAttr('required');
                $('#oneDayPermitSec').show();
                $('#isOneDayPermitApplication').val(false);
                $('#oneDayPermitTypeOfLandSec').hide();
            }
        }
        
    }

    if ($('#isOneDayPermitApplication').is(':checked')){
        $('#oneDayPermitSec').show();
        $('#applicationType').attr('required',false);
    }
    else {
        $('#oneDayPermitSec').hide();
        $('#applicationType').attr('required',true);
    }


    $('#isOneDayPermitApplication').click(function() {
        //resetValuesForAmenitiesOfOneDayPermit();
        checkIsEdcrRequire();
        if ($(this).is(':checked')) {
            /*if($('#isOneDayPermitApplication').val()) {
                $('.amenityHideShow').hide();
                if('Amenities'.localeCompare($( "#serviceType option:selected" ).text()) == 0)
                    $('.amenityHideShow').show();
            }*/
            $('#applicationTypeSec').hide();
            $('#applicationType').val('');
            $('#applicationType').attr('required',false);
            $('#typeOfLand').prop('required', 'required');
            hideRegularization();
            $('#oneDayPermitTypeOfLandSec').show();
            $('#isOneDayPermitApplication').val(true);
            $('.documentRequire').attr('required', true);
            $('.document-desc').find('span').addClass('mandatory');
            if($('#zone').val()!='' && $('#electionBoundary').val()!='')
                validateSlotMappingForOneDayPermit($('#zone').val(), $('#electionBoundary').val());
            // It required if dcr integration required for particular occupancy
            /*if('Addition or Extension' == serviceTypeName){
                hideAndShowEdcrDetails();
            }*/
        } else {
            /*$('.amenityHideShow').show();*/
            $('#typeOfLand').val('');
            $('#applicationType').attr('required',true);
            $('.documentRequire').removeAttr('required');
            $('.document-desc').find('span').removeClass('mandatory');
            $('#oneDayPermitTypeOfLandSec').hide();
            $('#isOneDayPermitApplication').prop('checked', false);
            $('#typeOfLand').removeAttr('required');
            $('#isOneDayPermitApplication').val(false);
            $('#applicationTypeSec').show();
            showRegularization();
            /*if('Addition or Extension' == serviceTypeName){
                hideAndShowEdcrDetails();
            }*/
        }
    });

    $('#electionBoundary').on('change', function() {
        if($('#zone').val() &&  $('#electionBoundary').val() && $('#isOneDayPermitApplication').is(':checked'))
            validateSlotMappingForOneDayPermit($('#zone').val(), $('#electionBoundary').val());
    });

});

$('#applicationType').on('change', function() {
	if(($("#applicationType option:selected" ).text() == 'Select' || $("#applicationType option:selected" ).text() == '') && $("#occupancyapplnlevel option:selected" ).text() == 'Residential') {
        $('#oneDayPermitSec').show();
        $('#applicationType').attr('required',false);
	}
	else{
        $('#oneDayPermitSec').hide();
        $('#applicationType').attr('required',true);
	}
});

function hideRegularization() {
	$('#constDiv').hide();
	$( ".appForRegularization" ).hide();
    $( "#isappForRegularization" ).prop('checked', false);
    $('#constStages').prop('selectedIndex',0);
    $(".resetRegularization").val('');
}

function showRegularization() {
	$('#constDiv').hide();
	$( ".appForRegularization" ).show();
    $( "#isappForRegularization" ).prop('checked', false);
    $('#constStages').prop('selectedIndex',0);
    $(".resetRegularization").val('');
}

function validateSlotMappingForOneDayPermit(zoneId, electionWardId){
    $.ajax({
        url: "/bpa/ajax/getOneDayPermitSlotByBoundary",
        type: "GET",
        data: {
            zoneId : zoneId,
            electionWardId : electionWardId
        },
        dataType: "json",
        success: function (response) {
            if(response==false){
                $('#electionBoundary').val('');
                bootbox.alert($('#slotmappingValidate').val());
                return false;
            } else
                return true;
        },
        error: function (response) {
        }
    });
}


function getOccupancyObject() {
    extentOfLand = $('#extentOfLand').val();
    extentInSqmts = $('#extentinsqmts').val();
    /*totalPlintArea = $('#totalPlintArea').val();*/
    var occpancyId = $('#occupancyapplnlevel').val();
    /*if(!occpancyId){
        bootbox.alert("Please select occpancy type");
        return false;
    }*/
    return occupancyResponse[occpancyId];
}

function arrayGroupByKey(arry, groupByKey){
    var resultData={};
    var result=arry.reduce(function(result, current) {
        result[current[groupByKey]] = result[current[groupByKey]] || [];
        result[current[groupByKey]].push(current);
        return result;
    }, {});

    Object.keys(result).sort().forEach(function(key) {
        resultData[key] = result[key];
    });

    return resultData;
}


function convertExtendOfLandToSqmts(extentOfLand,uom){
    var extentinsqmts;
    if(uom == 'ARE') {
        extentinsqmts = extentOfLand * 100;
    } else if(uom == 'HECTARE') {
        extentinsqmts = extentOfLand * 10000;
    } else {
        extentinsqmts = extentOfLand;
    }
    return extentinsqmts;
}

//Floor wise floor area validations
function validateFloorDetails(plinthArea) {
    var seviceTypeName = $( "#serviceType option:selected" ).text();
    if('Huts and Sheds' != seviceTypeName) {
        var occpancyObj = getOccupancyObject();
        var rowObj = $('#buildingAreaDetails tbody tr');
        if(occpancyObj && $("#occupancyapplnlevel option:selected" ).text() != 'Mixed'){
            if(!extentOfLand){
                bootbox.alert($("#landAreaReq").val());
                $(rowObj).find('.floorArea').val('');
                $(rowObj).find('.plinthArea').val('');
                $( ".floorArea" ).trigger( "change" );
                $( ".plinthArea" ).trigger( "change" );
                return false;
            }
            var inputPlinthArea = parseFloat($(plinthArea).val()).toFixed(2);
            var permissibleAreaInPercentage = occpancyObj[0].permissibleAreaInPercentage;
            var permissibleAreaForFloor = parseFloat(extentInSqmts * permissibleAreaInPercentage / 100).toFixed(2);
            if(parseFloat(inputPlinthArea) > parseFloat(permissibleAreaForFloor)){
                $(plinthArea).val('');
                bootbox.alert($("#typeOfMsg").val() +occpancyObj[0].description+$("#permissibleAreaForFloor1").val()+permissibleAreaForFloor+$("#permissibleAreaForFloor2").val());
                validateAndCalculateTotalOfFloorDetails();
                $(rowObj).find('.plinthArea').focus();
                return false;
            }
            /*if(parseFloat($("#sumOfFloorArea").val()) > parseFloat(totalPlintArea)){
                $(plinthArea).val('');
                bootbox.alert("Sum of floor wise floor area "+parseFloat($("#sumOfFloorArea").val())+" Sq.Mtrs is exceeding the total builtup area "+parseFloat(totalPlintArea)+" Sq.Mtrs of you entered, please check and enter valid data.");
                return false;
            }*/
            validateAndCalculateTotalOfFloorDetails();
        }
    }
}

function validateAndCalculateTotalOfFloorDetails() {
    var totalPlinth = 0;
    var totalFloorArea = 0;
    var totalCarpet = 0;
    $("#buildingAreaDetails tbody tr").each(function () {
        var rowPlinthArea = parseFloat($(this).find('td:eq(4) input.plinthArea').val());
        if(rowPlinthArea)
            totalPlinth +=  parseFloat(rowPlinthArea);
        $("#buildingAreaDetails tfoot tr td:eq(4)").html(totalPlinth.toFixed(2));
        $("#totalPlintArea").val(totalPlinth.toFixed(2));
        if(!rowPlinthArea) {
            $(this).closest('tr').find('.floorArea').val('');
            $(this).closest('tr').find('.carpetArea').val('');
        }
        //validate floor area
        var rowFloorArea = parseFloat($(this).find('td:eq(5) input.floorArea').val());
        if(rowFloorArea > rowPlinthArea) {
            $(this).closest('tr').find('.floorArea').val('');
            $(this).closest('tr').find('.carpetArea').val('');
            //validateAndCalculateTotalOfFloorDetails();
            bootbox.alert($('#floorareaValidate').val());
            return false;
        }
        if(rowFloorArea)
            totalFloorArea +=  parseFloat(rowFloorArea);
        $("#sumOfFloorArea").val(totalFloorArea.toFixed(2));
        $("#buildingAreaDetails tfoot tr td:eq(5)").html(totalFloorArea.toFixed(2));
        // validate carpet area
        var rowCarpetArea = parseFloat($(this).find('td:eq(6) input.carpetArea').val());
        if($(this).find('td:eq(6) input.carpetArea').val() == '.')
            $(this).find('td:eq(6) input.carpetArea').val(0.0);
        if(rowCarpetArea > rowFloorArea) {
            $(this).closest('tr').find('.carpetArea').val('');
            //validateAndCalculateTotalOfFloorDetails();
            bootbox.alert($('#carpetareaValidate').val());
            return false;
        }
        if(rowCarpetArea)
            totalCarpet += parseFloat(rowCarpetArea);
        $("#buildingAreaDetails tfoot tr td:eq(6)").html(totalCarpet.toFixed(2));
    });
}
function resetValuesForAmenitiesOfOneDayPermit() {
    $(".applicationAmenity").val('');
    $('#admissionfee').val(0);
    $('#serviceType,.applicationAmenity').trigger('change');
}

