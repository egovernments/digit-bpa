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
$(document).ready(function() {

    getOccupancyObjects();
    getSubOccupancyObjects();
    getOccupancyAndSubOccupanyMap();
    if($( "#serviceTypeDesc" ).val()) {
    	$('.buildingdetails').hide();
        $('.existingbuildingdetails').hide();
        $('.demolitionDetails').hide();
        removeMandatoryForExistingBuildingDetails();
        $('.show-hide').hide();
        $('.totalPlintArea').show();
        $('.dcrDocuments').hide();
        $( ".serviceType" ).trigger( "change" );
    }
    

    roundOfDecimalPlaces();
    function roundOfDecimalPlaces() {
        // Set decimal places of 2
        $('.decimalfixed').each(function(){
            if($(this).val()){
                $(this).val(parseFloat($(this).val()).toFixed(2));
            }
        });
    }
    
    $('#commencedDate').on('changeDate', function() {
    	$('#completionDate').val('').datepicker("refresh");
    });
    
    $('#completionDate').on('changeDate', function() {
        if (!$('#commencedDate').val()) {
            bootbox.alert("Please enter work starting date");
            $('#completionDate').val('').datepicker("refresh");
        } else if ($('#commencedDate').val() && moment($('#completionDate').val(),'DD/MM/YYYY').isSameOrBefore(moment($('#commencedDate').val(),'DD/MM/YYYY'))) {
            bootbox.alert("Date of construction completion Should be greater than Date of construction started.");
            $('#completionDate').val('').datepicker("refresh");
        }
    });

    // showing server side validation message in building details section as well as showing same in alert also.
    if($('#violationMessage').val()) {
        bootbox.alert($('#violationMessage').val()+' Please check and update building floor details to proceed further.');
        $('.showViolationMessage').focus();
        $('.showViolationMessage').html($('#violationMessage').val());
        $('.showViolationMessage').closest('div.panel-body').show();
    }

    // Validate input value must be greater than zero using class name
    $.validator.addMethod("nonzero", function(value, element) {
        return this.optional(element) || (parseFloat(value) > 0);
    }, '* Value must be greater than zero');


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

    $(document).on('blur', '.decimalfixed', function(evt) {
        if($(this).val() == '.')
            $(this).val(0.0);
    });

    // For each main service type validations
    $('.serviceType').change(function(){

        var seviceTypeName = $( "#serviceTypeDesc" ).val();
        $('.doorNo').show();
        $('.show-hide').hide();
        $('.floor-toggle-mandatory').find("span").removeClass( "mandatory" );
        $('.floor-details-mandatory').removeAttr('required');
        $('.areaOfBase').hide();
        $('.extentOfLand').show();

        if('Alteration' == seviceTypeName){
            addMandatoryForExistingBuildingDetails();
            showNewAndExistingBuildingDetails();
            addMandatoryForNewBuildingDetails();
            $('.alterationInArea').find("span").addClass( "mandatory" );
            $('#totalPlintArea').attr('required',true);
            $('#totalPlintArea').attr('readOnly',true);
            $('.alterationInArea').show();
            showDemolitionDetails();
        } else if('Addition or Extension' == seviceTypeName){
            addMandatoryForExistingBuildingDetails();
            showNewAndExistingBuildingDetails();
            addMandatoryForNewBuildingDetails();
            $('#totalPlintArea').attr('required',true);
            $('#totalPlintArea').attr('readOnly',true);
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
            jQuery('#totalPlintArea').attr('readOnly',true);
        }
    });
    
    if($( "#serviceTypeDesc" ).val()) {
    	$( ".serviceType" ).trigger( "change" );
    }
    
    function showDemolitionDetails() {
        $('.demolitionArea').find("span").addClass( "mandatory" );
        $('#demolitionArea').attr('required',true);
        $('.demolitionDetails').show();
    }

    function showNewAndExistingBuildingDetails() {
        $('.existingbuildingdetails').show();
        $('.buildingdetails').show();
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
                 mixedOccupancyResponse = response;
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

    var previousIndex;
    var oneDayPermitPreviousVal;
    var landTypeDescIndex;
    $('#occupancyapplnlevel').focus(function () {
        // Store the current value on focus, before it changes
        previousIndex= this.selectedIndex;
        oneDayPermitPreviousVal = $('#isOneDayPermitApplication').is(':checked');
        landTypeDescIndex = $('#typeOfLand').val()
    }).change(function(e){
        if($('#buildingAreaDetails tbody tr').length == 1 && ($('.occupancy').val() == '' || $('.floorDescription').val() == '')){
            resetOccupancyDetails();
        } else if($('#buildingAreaDetails tbody tr').length >= 1 && $('.occupancy').val() != '' && $('.floorDescription').val() != ''){

            var dropdown=e;

            bootbox
                .confirm({
                    message : 'If you change occupancy type, filled floor wise details will be reset. Do you want continue ? ',
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
                            $('#buildingAreaDetails').find('input').not('input[type=hidden]').val('');
                            $('#buildingAreaDetails').find('select').val('');
                            resetOccupancyDetails();
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
                            return true;
                        }
                    }
                });
        } else if($('#buildingAreaDetails tbody tr').length >1 && ($('.occupancy').val() == '' || $('.floorDescription').val() == '')){
            resetOccupancyDetails();
        }
    });

    // onchange of main occupancy reset floorwise occupancy details column value
    function resetOccupancyDetails() {
        $('#buildingAreaDetails tbody tr *[name$="occupancy"]').each(function(idx){
            var  selectBoxName = "buildingDetail[0].applicationFloorDetails["+idx+"].subOccupancy";
            var  selectBoxName1 = "buildingDetail[0].applicationFloorDetailsForUpdate["+idx+"].subOccupancy";
            clearExistingDropDownValues(selectBoxName);
            clearExistingDropDownValues(selectBoxName1);
            if($("#occupancyapplnlevel option:selected" ).text() == 'Mixed'){
                $('select[name="'+selectBoxName+'"]').append($("<option value=''>Select </option>"));
                $('select[name="'+selectBoxName1+'"]').append($("<option value=''>Select </option>"));
                $.each(mixedOccupancyResponse, function(index, occupancyObj) {
                    if(occupancyObj.description != 'Mixed')
                        loadOccupancyDetails(selectBoxName, occupancyObj.id, occupancyObj.description);
                    loadOccupancyDetails(selectBoxName1, occupancyObj.id, occupancyObj.description);
                });
            } else {
                loadOccupancyDetails(selectBoxName, $("#occupancyapplnlevel option:selected" ).val(), $("#occupancyapplnlevel option:selected" ).text());
                loadOccupancyDetails(selectBoxName1, $("#occupancyapplnlevel option:selected" ).val(), $("#occupancyapplnlevel option:selected" ).text());
            }
        });
    }

    function loadOccupancyDetails(selectBoxName,id,value){
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
                        bootbox.alert("For the occupancy type of " +occpancyObj[0].description+", maximum permissible area is "+areaPermissibleWOAddnlFee.toFixed(2)+" Sq.Mtrs, beyond of permissible area you are not allowed construct construction.");
                        $(rowObj).find('.floorArea').val('');
                        $( ".floorArea" ).trigger( "change" );
                        $( ".plinthArea" ).trigger( "change" );
                        $( ".carpetArea" ).trigger( "change" );
                        return false;
                    } else {
                        return true;
                    }
                } else if(parseFloat(totalFloorArea) > areaPermissibleWithAddnlFee.toFixed(2) && isCitizenAcceptedForAdditionalFee) {
                    bootbox.alert("For the occupancy type of " +occpancyObj[0].description+", maximum permissible area allowed with out addtional fee is "+areaPermissibleWOAddnlFee.toFixed(2)+" Sq.Mtrs and with addtional fee is "+areaPermissibleWithAddnlFee.toFixed(2)+" Sq.Mtrs, beyond of maximum permissible area of "+areaPermissibleWithAddnlFee.toFixed(2)+" Sq.Mtrs, you are not allowed construct construction.");
                    $(rowObj).find('.floorArea').val('');
                    $( ".floorArea" ).trigger( "change" );
                    $( ".plinthArea" ).trigger( "change" );
                    $( ".carpetArea" ).trigger( "change" );
                    return false;
                } else if(parseFloat(totalFloorArea) > areaPermissibleWOAddnlFee.toFixed(2) && !isCitizenAcceptedForAdditionalFee) {
                    bootbox
                        .confirm({
                            message : 'For the occupancy type of ' +occpancyObj[0].description+', maximum permissible area allowed with out addtional fee is '+areaPermissibleWOAddnlFee.toFixed(2)+' Sq.Mtrs, Do you want continue construction in additional area with addtional cost of Rs.5000 per Sq.Mtr.Are you ready pay additional amount ? , please select Yes / No ',
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


    // trigger events on pageload
    $( "#isexistingApprovedPlan" ).trigger( "change" );
    //checkIsEdcrRequire();
    var serviceTypeName = $( "#serviceType option:selected" ).text();

    function showDCRDetails() {
        $('#ocEDcrNumber').attr('required', true);
        $('#ocEDcrNumber').show();
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
        $('#ocEDcrNumber').removeAttr('required');
        $('#ocEDcrNumber').hide();
        $('#ocEDcrNumber').val('');
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
                "occupancy" : $("#occupancy" ).val()
            },
            cache : false,
            dataType: "json",
            success : function(response) {
                $('#isEDCRIntegrationRequire').val(response);
                if(response) {
                    showDCRDetails();
                } else {
                    hideDcrDetails();
                }
            },
            error : function(response) {
            }
        });
    }
});

function getOccupancyObject() {
    extentOfLand = $('#extentOfLand').val();
    extentInSqmts = $('#extentinsqmts').val();
    var occpancyId = $('#occupancyapplnlevel').val();
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
                bootbox.alert("Please enter extend of land area value");
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
                bootbox.alert("For type of " +occpancyObj[0].description+", each floor wise maximum permissable coverage area is " +permissibleAreaForFloor+" Sq.Mtrs, so beyond of maximum coverage area permission are not allowed.");
                validateAndCalculateTotalOfFloorDetails();
                $(rowObj).find('.plinthArea').focus();
                return false;
            }
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
            bootbox.alert("Floor Area should be less than or equal to the Builtup Area.");
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
            bootbox.alert("Carpet Area should be less than or equal to the Floor Area.");
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

