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

jQuery(document).ready(function() {
	
	// on page load to calculate sum of floor, plinth and carpet area
	 /*$( ".plinthArea" ).trigger( "change" );
	 $( ".carpetArea" ).trigger( "change" );
	 $( ".floorArea" ).trigger( "change" );*/
	 //setFloorCount();
	
	var proposedBldgLen = $('.buildDetails').data('bldg-len');
	if(proposedBldgLen > 0)
        for(var i = 0; i < proposedBldgLen; i++)
            validateAndSumProposedBldgFloorDetails('buildingAreaDetails'+i, i);
	var table;
	var tbody;
	var thead = '<thead><tr>'+
    '<th class="text-center">Srl.no</th>'+
    '<th class="text-center bldg-floor-toggle-mandatory">Floor Description</th>'+
    '<th class="text-center bldg-floor-toggle-mandatory">Level</th>'+
    '<th class="text-center bldg-floor-toggle-mandatory">Sub Occupancy</th>'+
    '<th class="text-center bldg-floor-toggle-mandatory">Usage</th>'+
    '<th class="text-center bldg-floor-toggle-mandatory">Builtup Area (m²)</th>'+
    '<th class="text-center bldg-floor-toggle-mandatory">Floor Area (m²)</th>'+
    '<th class="text-center bldg-floor-toggle-mandatory">Carpet Area (m²)</th>'+
    '<th class="text-center bldg-floor-toggle-mandatory">Action</th>'+
    '</tr></thead>';
	
	var tbody1 = '<tbody></tbody>';
	
	var tfoot = '<tfoot><tr>'+
    '<td></td>'+
    '<td></td>'+
    '<td></td>'+
    '<td></td>'+
    '<td></td>'+
    '<td class="text-right">Total</td>'+
    '<td class="text-right bldg-reset-values"></td>'+
    '<td class="text-right bldg-reset-values"></td>'+
    '<td class="text-right bldg-reset-values"></td>'+
'</tr></tfoot>';

var otherBldgDetails = '<div class="block-{{bldgIdx}}">'+
	'<div class="text-right add-padding" data-bldg-idx="{{bldgIdx}}">'+
	'	<button type="button"'+
	'		class="btn btn-sm btn-primary addBuildAreaRow"'+
	'		id="addBuildAreaRow{{bldgIdx}}">'+
	' 	Add Row'+
	'</button>'+
	'</div><div class="form-group">' +
    '        <label' +
    '                class="col-sm-3 control-label text-right show-hide totalPlintArea">' +
    '                Total Builtup Area (m²)<span class="mandatory"></span> </label> <label' +
    '            class="col-sm-3 control-label text-right show-hide demolition">' +
    '            Demolition Area (m²)<span class="mandatory"></span> </label><label' +
    '            class="col-sm-3 control-label text-right show-hide noofhutorshed">' +
    '            Area of the Hut/Shed (m²) <span class="mandatory"></span> </label> <label' +
    '            class="col-sm-3 control-label text-right show-hide alterationInArea">' +
    '            Alteration/Change in Area (m²) <span class="mandatory"></span> </label> <label' +
    '            class="col-sm-3 control-label text-right show-hide additionInArea">' +
    '            Addition or Extension in Area (m²) <span class="mandatory"></span> </label> <label' +
    '            class="col-sm-3 control-label text-right show-hide changeInOccupancyArea">' +
    '            Change in Occupancy Area (m²) <span class="mandatory"></span>' +
    '    </label>' +
    '        <div class="col-sm-3 add-margin">' +
    '            <input type="hidden" id="name" name="buildingDetail[{{bldgIdx}}].name"' +
    '                   value="{{name}}">' +
    '            <input type="hidden" id="number" name="buildingDetail[{{bldgIdx}}].number"' +
    '                   value="{{number}}">' +
    '            <input type="hidden" id="name" name="buildingDetail[{{bldgIdx}}].name"' +
    '                   value="{{name}}">' +
    '            <input type="hidden" id="number" name="buildingDetail[{{bldgIdx}}].number"' +
    '                   value="{{number}}">' +
    '            <input type="hidden" id="setTotalPlintArea{{bldgIdx}}" name="buildingDetail[{{bldgIdx}}].totalPlintArea"' +
    '                   value="{{totalPlinthArea}}">' +
    '            <input' +
    '                    class="form-control patternvalidation dcr-reset-values decimalfixed totalPlintArea"' +
    '                    maxlength="10" data-pattern="decimalvalue" id="totalPlintArea{{bldgIdx}}"' +
    '                    name="buildingDetail[{{bldgIdx}}].totalPlintArea"' +
    '                    value="{{totalPlinthArea}}" disabled="true"/>' +
    '        </div>' +
    '        <label' +
    '                class="col-sm-2 control-label text-right floorCount">' +
    '                Number of Floors<span class="mandatory"></span></label>' +
    '        <div class="col-sm-3 add-margin">' +
    '            <input type="hidden" id="setFloorCount" name="buildingDetail[{{bldgIdx}}].floorCount"' +
    '                   value="{{floorCount}}">' +
    '            <input' +
    '                    class="form-control patternvalidation dcr-reset-values floorCount{{bldgIdx}}"' +
    '                    data-pattern="number" maxlength="3" id="floorCountFromEdcr{{bldgIdx}}" readonly="true"' +
    '                    name="buildingDetail[{{bldgIdx}}].floorCount" value="{{floorCount}}"/>' +
    '        </div>' +
    '    </div>' +
    '' +
    '    <div class="form-group">' +
    '        <label' +
    '                class="col-sm-3 control-label text-right heightFromGroundWithOutStairRoom{{bldgIdx}}">' +
    '                Height From Ground Level without stair Room (In Mtrs)<span class="mandatory"></span></label>' +
    '        <div class="col-sm-3 add-margin">' +
    '            <input type="hidden" id="setHeightFromGroundWithOutStairRoom{{bldgIdx}}"' +
    '                   name="buildingDetail[{{bldgIdx}}].heightFromGroundWithOutStairRoom"' +
    '                   value="{{height}}">' +
    '            <input' +
    '                    class="form-control patternvalidation dcr-reset-values nonzero decimalfixed heightFromGroundWithOutStairRoom{{bldgIdx}}"' +
    '                    maxlength="6" data-pattern="decimalvalue"' +
    '                    id="heightFromGroundWithOutStairRoomFromEdcr{{bldgIdx}}" required="required"' +
    '                    name="buildingDetail[{{bldgIdx}}].heightFromGroundWithOutStairRoom"' +
    '                    value="{{height}}"/>' +
    '        </div>' +
    '        <label' +
    '                class="col-sm-2 control-label text-right heightFromGroundWithStairRoom{{bldgIdx}}">' +
    '                Height From Ground Level with stair Room (In Mtrs)</label>' +
    '        <div class="col-sm-3 add-margin">' +
    '            <input type="hidden" id="setHeightFromGroundWithStairRoom{{bldgIdx}}"' +
    '                   name="buildingDetail[{{bldgIdx}}].heightFromGroundWithStairRoom"' +
    '                   value="{{height}}">' +
    '            <input' +
    '                    class="form-control patternvalidation dcr-reset-values decimalfixed heightFromGroundWithStairRoom{{bldgIdx}}"' +
    '                    maxlength="6" data-pattern="decimalvalue"' +
    '                    id="heightFromGroundWithStairRoomFromEdcr{{bldgIdx}}"' +
    '                    name="buildingDetail[{{bldgIdx}}].heightFromGroundWithStairRoom"' +
    '                    value="{{height}}"/>' +
    '        </div>' +
    '    </div>' +
    '    <div class="form-group">' +
    '        <label' +
    '                class="col-sm-3 control-label text-right fromStreetLevelWithOutStairRoom{{bldgIdx}}">' +
    '                Height From Street Level without stair Room (In Mtrs)</label>' +
    '        <div class="col-sm-3 add-margin">' +
    '            <input type="hidden" id="setFromStreetLevelWithOutStairRoom{{bldgIdx}}"' +
    '                   name="buildingDetail[{{bldgIdx}}].fromStreetLevelWithOutStairRoom"' +
    '                   value="{{height}}">' +
    '            <input' +
    '                    class="form-control patternvalidation dcr-reset-values decimalfixed fromStreetLevelWithOutStairRoom{{bldgIdx}}"' +
    '                    maxlength="6" data-pattern="decimalvalue"' +
    '                    id="fromStreetLevelWithOutStairRoomFromEdcr{{bldgIdx}}"' +
    '                    name="buildingDetail[{{bldgIdx}}].fromStreetLevelWithOutStairRoom"' +
    '                    value="{{height}}"/>' +
    '        </div>' +
    '        <label' +
    '                class="col-sm-2 control-label text-right fromStreetLevelWithStairRoom">' +
    '                Height From Street Level with stair Room (In Mtrs)</label>' +
    '        <div class="col-sm-3 add-margin">' +
    '            <input type="hidden" id="setFromStreetLevelWithStairRoom{{bldgIdx}}"' +
    '                   name="buildingDetail[{{bldgIdx}}].fromStreetLevelWithStairRoom"' +
    '                   value="{{height}}">' +
    '            <input' +
    '                    class="form-control patternvalidation dcr-reset-values decimalfixed fromStreetLevelWithStairRoom{{bldgIdx}}"' +
    '                    maxlength="6" data-pattern="decimalvalue"' +
    '                    id="fromStreetLevelWithStairRoomFromEdcr{{bldgIdx}}"' +
    '                    name="buildingDetail[{{bldgIdx}}].fromStreetLevelWithStairRoom"' +
    '                    value="{{height}}"/>' +
    '        </div>' +
    '    </div>' +
    '    </div>';
	
	var row = '<tr>'+
	'<td class="text-center"><span class="serialNo text-center" id="slNoInsp">{{sno}}</span><input type="hidden" class="orderNo" data-sno name="buildingDetail[{{bldgIdx}}].applicationFloorDetailsForUpdate[{{idx}}].orderOfFloor"/></td>'+
	'<td ><select name="buildingDetail[{{bldgIdx}}].applicationFloorDetailsForUpdate[{{idx}}].floorDescription" data-first-option="false" class="form-control floor-details-mandatory floorDescription duplicate-clear" required="required" maxlength="128" > <option value="">Select</option></select></td>'+
	'<td class="text-right"><input type="text" class="form-control table-input text-center patternvalidation floorNumber floor-details-mandatory duplicate-clear" name="buildingDetail[{{bldgIdx}}].applicationFloorDetailsForUpdate[{{idx}}].floorNumber" data-pattern="number" required="required" maxlength="3" /></td>'+
	'<td ><select name="buildingDetail[{{bldgIdx}}].applicationFloorDetailsForUpdate[{{idx}}].subOccupancy" data-first-option="false" class="form-control floor-details-mandatory subOccupancy" required="required" maxlength="128" > <option value="">Select</option></select></td>'+
	'<td ><select name="buildingDetail[{{bldgIdx}}].applicationFloorDetailsForUpdate[{{idx}}].usage" data-first-option="false" class="form-control floor-details-mandatory usage" required="required" maxlength="128" > <option value="">Select</option></select></td>'+
	'<td class="text-right"><input type="text" class="form-control table-input text-right patternvalidation plinthArea nonzero floor-details-mandatory decimalfixed" data-pattern="decimalvalue" name="buildingDetail[{{bldgIdx}}].applicationFloorDetailsForUpdate[{{idx}}].plinthArea" required="required" maxlength="10" onblur="validateFloorDetails(this)" /></td>'+
	'<td class="text-right"><input type="text" class="form-control table-input text-right patternvalidation floorArea nonzero floor-details-mandatory decimalfixed" data-pattern="decimalvalue" name="buildingDetail[{{bldgIdx}}].applicationFloorDetailsForUpdate[{{idx}}].floorArea" maxlength="10" required="required" /></td>'+
	'<td class="text-right"><input type="text" class="form-control table-input text-right patternvalidation carpetArea floor-details-mandatory decimalfixed" data-pattern="decimalvalue" name="buildingDetail[{{bldgIdx}}].applicationFloorDetailsForUpdate[{{idx}}].carpetArea" maxlength="10" required="required" value=""  /></td>'+
	'<td class="text-center"><a href="javascript:void(0);" class="btn-sm btn-danger" id="deleteBuildAreaRow" ><i class="fa fa-trash"></i></a></td>'+
	'</tr>';
	
	$(document).on('click','.addBuildAreaRow', function() {
		var selectedBldgIdx = $(this).parent().data('bldg-idx');
		tbody = $('#buildingAreaDetails'+selectedBldgIdx).children('tbody');
		table = tbody.length ? tbody : $('#buildingAreaDetails' + selectedBldgIdx);
		if(validateBuildAreaOnAdd('buildingAreaDetails'+selectedBldgIdx)) {
			var idx=$(tbody).find('tr').length;
			var sno=idx+1;
			//Add row
			var row = {
			       'sno': sno,
			       'idx': idx,
			       'bldgIdx' : selectedBldgIdx
			   };
			addRowFromObject(row);
			patternvalidation();
			generateSno('buildingAreaDetails' + selectedBldgIdx);
			loadFloorlist("buildingDetail["+selectedBldgIdx+"].applicationFloorDetailsForUpdate["+idx+"].floorDescription");
			loadSubOccupancies("buildingDetail[0].applicationFloorDetailsForUpdate["+idx+"].subOccupancy");
	    	/*$.each($("#subOccupancies option:selected"), function(idx1) {
	    		loadOccupancyDetails1("buildingDetail["+selectedBldgIdx+"].applicationFloorDetailsForUpdate["+idx+"].subOccupancy", $(this).val(), $(this).text());
	        });*/
			
		}
	});
	
	var $table1;
	
	function addBlock(bldgIdx, name) {
        var blockTitle;
        var toggleIcon;
        blockTitle = $('#blockMsg').val()+name+$('#builtupAndCarpetDetails').val();
        /*toggleIcon = '<div class="history-icon toggle-icon'+bldgIdx+'">'+
            '      <i class="fa fa-angle-up fa-2x"></i></div>';*/
        var header = '<div class="panel-heading custom_form_panel_heading toggle-bldg-header blocks toggle-head'+bldgIdx+'"' +
            '                 data-bldg-idx="'+bldgIdx+'">'+'<div class="panel-title"> '+blockTitle+' </div> '+toggleIcon+' </div>'
        $table1 = $('<table class="table table-striped table-bordered block-'+bldgIdx+' buildingAreaDetails'+bldgIdx+'"></table>');
        $table1.append(thead);
        $table1.append(tbody1);
        $table1.append(tfoot);
        $('#blocksContainer').append(header);
        $('#blocksContainer').append($table1);
        var otherBldgDetails = {
                'bldgIdx': bldgIdx,
                'name': name,
                'number': name
            };
        addOtherProposedBldgDtls(otherBldgDetails, bldgIdx);
        $('.serviceType').trigger('change');
    }
	
	function addOtherProposedBldgDtls(other, bldgIdx) {
        $('#blocksContainer').append(otherBldgDetails.compose(other));
        $('#block-'+bldgIdx).wrap($("<div/>").attr('class','buildingDetailsToggle'+bldgIdx+' display-hide'));
    }
	
	function appendRowForNewBlock(sno, idx, selectedBldgIdx, row) {
		//Add row
		var row = {
		       'sno': sno,
		       'idx': idx,
		       'bldgIdx' : selectedBldgIdx
		   };
		addRowFromObject1(row);
		patternvalidation();
		loadFloorlist("buildingDetail["+selectedBldgIdx+"].applicationFloorDetailsForUpdate["+idx+"].floorDescription");
		//loadSubOccupancies("buildingDetail[0].applicationFloorDetailsForUpdate["+idx+"].subOccupancy");
    	$.each($("#subOccupancies option:selected"), function(idx1) {
    		loadOccupancyDetails1("buildingDetail["+selectedBldgIdx+"].applicationFloorDetailsForUpdate["+idx+"].subOccupancy", $(this).val(), $(this).text());
        });
	}
	
	/*$(document).on('click','#addBlock', function() {
		var BldgLen = $('.buildDetails').data('bldg-len');
		var curretBldgIdx = BldgLen;
		if(curretBldgIdx > 0) {
			$('.buildDetails').data('bldg-len', BldgLen+1); 
			addBlock(curretBldgIdx, curretBldgIdx);
			appendRowForNewBlock(1,0, curretBldgIdx, row);
		}
			
	});*/
	
	function addRowFromObject(rowJsonObj)
	{
		table.append(row.compose(rowJsonObj));
	}
	
	function addRowFromObject1(rowJsonObj)
	{
		$table1.append(row.compose(rowJsonObj));
	}
	
	String.prototype.compose = (function (){
		   var re = /\{{(.+?)\}}/g;
		   return function (o){
		       return this.replace(re, function (_, k){
		           return typeof o[k] != 'undefined' ? o[k] : '';
		       });
		   }
	}());
	
	function loadFloorlist(selectBoxName){
		var floorList = $('#buildingFloorList').val();
		var floorListStringArry = floorList.substring(1, floorList.length-1);
		var floorDesc = floorListStringArry.split(',');
					$.each(floorDesc, function(index, floorDesc) {
						$('select[name="'+selectBoxName+'"]').append($('<option>').val(floorDesc).text(floorDesc));
					});
		}
	
	function loadSubOccupancies(selectBoxName) {
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
						$('select[name="'+selectBoxName+'"]').append($('<option>').val(subOcc.id).text(subOcc.name));
					});
				}, 
				error: function (response) {
				}
			});
		}
	
	function loadOccupancyDetails1(selectBoxName,id,value){
					//$('select[name="'+selectBoxName+'"]').empty();
					$('select[name="'+selectBoxName+'"]').append($('<option>').val(id).text(value));
		}
	
	$(document).on('blur','.floorNumber', function() {
		var selectedBldgIdx = $(this).parents().find('.blocks').data('bldg-idx');
		var tableId = $(this).closest('table').attr('id');
		var rowObj = $(this).closest('tr');
		setFloorCount(tableId, selectedBldgIdx);
		validateUniqueDetails(tableId, rowObj.index(),$(rowObj).find('.floorDescription').val(), $(rowObj).find('.floorNumber').val(), $(rowObj).find('.subOccupancy').val());
	}); 
	
	$(document).on('blur','.floorDescription', function() {
		var selectedBldgIdx = $(this).parents().find('.blocks').data('bldg-idx');
		var tableId = $(this).closest('table').attr('id');
		var rowObj = $(this).closest('tr');
		if($(rowObj).find('.floorDescription').val() && $(rowObj).find('.floorDescription').val() == 'Cellar Floor') {
			$(rowObj).find('.floorNumber').data('pattern','numerichyphen');
		} else {
			$(rowObj).find('.floorNumber').data('pattern','number');
		}
		patternvalidation();
		$(rowObj).find('.floorNumber').val('');
		if(!$("#occupancyapplnlevel").val()) {
			$('#'+ tableId).find('select').val('');
			bootbox.alert('Please select main occupancy type.');
			return false;
		}
		setFloorCount(tableId, selectedBldgIdx);
		if(($(rowObj).find('.floorDescription').val() == '' || $(rowObj).find('.floorDescription').val() == 'undefined') && $('#buildingAreaDetails'+selectedBldgIdx +' tbody tr').length <= 1) {
			$("#floorCount"+selectedBldgIdx).val('');
		}
		validateUniqueDetails(tableId, rowObj.index(),$(rowObj).find('.floorDescription').val(), $(rowObj).find('.floorNumber').val(), $(rowObj).find('.subOccupancy').val(), $(rowObj).find('.usage').val());
	});
	
	$(document).on('blur', '.subOccupancy', function() {
		var rowObj = $(this).closest('tr');
		$(rowObj).find('.usage').empty();
		var tableId = $(this).closest('table').attr('id');
		var selectedBldgIdx = $('#'+tableId).data('bldg-idx');
		var usages = usagesReponseBySuboccupancy[$(this).val()];
		var selectBoxName = "buildingDetail["+selectedBldgIdx+"].applicationFloorDetailsForUpdate["+rowObj.index()+"].usage";
		var selectBoxName1 = "buildingDetail["+selectedBldgIdx+"].applicationFloorDetails["+rowObj.index()+"].usage";
		$('select[name="'+selectBoxName+'"]').append($("<option value=''>Select </option>"));
        $('select[name="'+selectBoxName1+'"]').append($("<option value=''>Select </option>"));
		$.each(usages, function(index, usage) {
			$('select[name="'+selectBoxName+'"]').append($('<option>').val(usage.id).text(usage.name));
			$('select[name="'+selectBoxName1+'"]').append($('<option>').val(usage.id).text(usage.name));
		});
		validateUniqueDetails(tableId, rowObj.index(),$(rowObj).find('.floorDescription').val(), $(rowObj).find('.floorNumber').val(), $(rowObj).find('.subOccupancy').val(), $(rowObj).find('.usage').val());
	});

	$(document).on('blur','.usage', function() {
		var rowObj = $(this).closest('tr');
		var tableId = $(this).closest('table').attr('id');
		validateUniqueDetails(tableId, rowObj.index(),$(rowObj).find('.floorDescription').val(), $(rowObj).find('.floorNumber').val(), $(rowObj).find('.subOccupancy').val(), $(rowObj).find('.usage').val());
	});

	$(document).on('change', '.plinthArea', function() {
    	var tableId = $(this).closest('table').attr('id');
    	var selectedBldgIdx = $('#'+tableId).data('bldg-idx');
    	if($('#isEDCRIntegrationRequire').val() === 'false') {
    		var totalPlinth = 0;
           /* var rowPlinthArea = 0;
            var rowObj = $(this).closest('tr');*/
            $("#"+ tableId +" tbody tr").each(function () {
                /*rowPlinthArea = parseFloat($(this).find('td:eq(5) input.plinthArea').val());
                 if(!rowPlinthArea) {
                     $(rowObj).find('.plinthArea').val('');
                     $(rowObj).find('.floorArea').val('');
                     $( ".floorArea" ).trigger( "change" );
                     bootbox.alert("Please Enter Builtup Area.");
                     return false;
                 }*/
                 if($(this).find('td:eq(5) input.plinthArea').val())
                     totalPlinth +=  parseFloat($(this).find('td:eq(5) input.plinthArea').val());
            });
            var seviceTypeName = $( "#serviceType option:selected" ).text();
            if(totalPlinth && 'Huts and Sheds' != seviceTypeName) {
                $("#totalPlintArea"+selectedBldgIdx).val(totalPlinth.toFixed(2));
            }
            $("#"+ tableId +" tfoot tr td:eq(5)").html(totalPlinth.toFixed(2));
    	}

        validateAndSumProposedBldgFloorDetails(tableId, selectedBldgIdx);
    });

    $(document).on('change', '.floorArea', function() {
    	var tableId = $(this).closest('table').attr('id');
    	var selectedBldgIdx = $('#'+tableId).data('bldg-idx');
    	if($('#isEDCRIntegrationRequire').val() === 'false') {
    		var totalFloorArea = 0 ;
            var rowObj = $(this).closest('tr');
            $("#"+ tableId +" tbody tr").each(function () {
                var rowPlinthArea = parseFloat($(this).find('td:eq(5) input.plinthArea').val());
                var rowFloorArea = parseFloat($(this).find('td:eq(6) input.floorArea').val());
                    if(rowFloorArea > rowPlinthArea) {
                    $(rowObj).find('.floorArea').val('');
                    $( ".plinthArea" ).trigger( "change" );
                    $( ".floorArea" ).trigger( "change" );
                    $( ".carpetArea" ).trigger( "change" );
                    bootbox.alert("Floor Area should be less than or equal to the Builtup Area.");
                    return false;
                   }
                if(rowFloorArea)
                 totalFloorArea +=  parseFloat($(this).find('td:eq(6) input.floorArea').val());
            });
                $("#sumOfFloorArea"+selectedBldgIdx).val(totalFloorArea.toFixed(2));
                $("#"+ tableId +" tfoot tr td:eq(6)").html(totalFloorArea.toFixed(2));
    	}

        validateAndSumProposedBldgFloorDetails(tableId, selectedBldgIdx);

    });

    $(document).on('change', '.carpetArea', function() {
    	var tableId = $(this).closest('table').attr('id');
    	var selectedBldgIdx = $('#'+tableId).data('bldg-idx');
    	if($('#isEDCRIntegrationRequire').val() === 'false') {
    		 var rowObj = $(this).closest('tr');
             var totalCarpet = 0;
             $("#" + tableId + " tbody tr").each(function () {
                 var rowFloorArea = parseFloat($(this).find('td:eq(6) input.floorArea').val());
                 var rowCarpetArea = parseFloat($(this).find('td:eq(7) input.carpetArea').val());
                 if($(this).find('td:eq(6) input.carpetArea').val() == '.')
                     $(this).find('td:eq(6) input.carpetArea').val(0.0);
                 if(rowCarpetArea > rowFloorArea) {
                     $(rowObj).find('.carpetArea').val('');
                     $( ".plinthArea" ).trigger( "change" );
                     $( ".floorArea" ).trigger( "change" );
                     $( ".carpetArea" ).trigger( "change" );
                     bootbox.alert("Carpet Area should be less than or equal to the Floor Area.");
                     return false;
                 }

                 validateAndSumProposedBldgFloorDetails(tableId, selectedBldgIdx);
                 if($(this).find('td:eq(7) input.carpetArea').val())
                	 totalCarpet += parseFloat($(this).find('td:eq(7) input.carpetArea').val());
             });
            $("#" + tableId + " tfoot tr td:eq(7)").html(totalCarpet.toFixed(2));
    	}
        validateAndSumProposedBldgFloorDetails(tableId, selectedBldgIdx);
    });
    
    var deletedId = [];
    $(document).on('click',"#deleteBuildAreaRow",function () {
    	var tableId = $(this).closest('table').attr('id');
    	var selectedBldgIdx = $('#'+tableId).data('bldg-idx');
        var rowIndex = $(this).closest('td').parent()[0].sectionRowIndex;
        if($(this).data('record-id'))
            deletedId.push($(this).data('record-id'));

        $('#deletedFloorIds').val(deletedId);
        $(this).closest('tr').remove();

        generateSno(tableId);
        $("#" + tableId +" tbody tr").each(function() {
            $(this).find("input, select, hidden,textarea").each(function() {
                var index = $(this).closest('td').parent()[0].sectionRowIndex;
                if(index>=rowIndex){
                    var increment = index++;
                    $(this).attr({
                        'name': function(_, name){
                            var idxWithNameStr = name.match(/\d+(\D*)$/g)[0].replace(/\d+\]/g, increment+"]");
                            return name.replace(/\d+(\D*)$/g, idxWithNameStr);
                        },
                        'id': function(_, id){
                            if(id==undefined){
                                return "";
                            }
                            return id.replace(/\d+(\D*)$/g, +increment);
                        }
                    });
                }

            });
        });
    	
        setFloorCount(tableId, selectedBldgIdx);
        validateAndSumProposedBldgFloorDetails(tableId, selectedBldgIdx);
        // on delete to re-calculate sum of plinth and carpet area
        /*$( ".floorArea" ).trigger( "change" );
        $( ".plinthArea" ).trigger( "change" );
        $( ".carpetArea" ).trigger( "change" );*/

    });
    
    //$( '.subOccupancy' ).trigger('change');
});


function validateUniqueDetails(tableId, idx,floorDesc,level,occupancy, usage){
	if(floorDesc && occupancy) {
		$("#"+ tableId +' tbody tr').each(function(index){
			
			if(idx===index)
				return;
			
			var floorName  = $(this).find('*[name$="floorDescription"]').val().trim();
		    var floorNumber = $(this).find('*[name$="floorNumber"]').val().trim();
		    var occupancyType = $(this).find('*[name$="subOccupancy"]').val().trim();
		    var usageType = $(this).find('*[name$="usage"]').val().trim();
							    if (floorDesc
									&& floorDesc.trim() === floorName
									&& level
									&& Number(level.trim()) === Number(floorNumber)
									&& occupancy
									&& occupancy.trim() === occupancyType
									&& usage
									&& usage.trim() === usageType) {
								$(tableId +' tbody tr:eq('
												+ idx + ')').find(
										'.duplicate-clear').val('');
								bootbox
										.alert($("#floorCombination").val()
												+ floorDesc
												+ $("#levelValidate").val()
												+ Number(level)
												+ $("#occuptypemsg").val()
												+ $(this)
														.find(
																'*[name$="subOccupancy"] option:selected')
														.text()
												+ $("#floorAlreadyExist").val());
								return false;
							}
		});
	}
}

function setFloorCount(tableId, bldgIdx) {
	
	var floorsObj={};
	
	$('#'+ tableId +' tbody tr').each(function(e){
		var floorDesc=$(this).find('.floorDescription').val().trim();
		var floorNo = $(this).find('.floorNumber').val();
		
		if(!floorDesc || !floorNo)
			return;
		else if(floorDesc == 'Cellar Floor')
			return;
		
		var floorNos= floorsObj[floorDesc] || [];
		var index = floorNos.indexOf(floorNo);
		
		if(floorNos.length > 0 && index ==-1){
			floorNos.push(floorNo);
		}
		else if(index == -1){
			floorNos.push(floorNo);
			floorsObj[floorDesc]=floorNos;
		}
	});
	
	var len=0;
	
	for(var key in floorsObj){
		len += floorsObj[key].length;
	}
	
	$("#floorCount"+bldgIdx).val(len);
}

function validateAndSumProposedBldgFloorDetails(tableId, bldgIdx) {
    var totalPlinth = 0;
    var totalFloorArea = 0;
    var totalCarpet = 0;
    $("#"+tableId+" tbody tr").each(function () {
        var rowPlinthArea = parseFloat($(this).find('td:eq(5) input.plinthArea').val());
        if(rowPlinthArea)
            totalPlinth +=  parseFloat(rowPlinthArea);
        $("#"+tableId+" tfoot tr td:eq(5)").html(totalPlinth.toFixed(2));
        $("#totalPlintArea"+bldgIdx).val(totalPlinth.toFixed(2));
        if(!rowPlinthArea) {
            $(this).closest('tr').find('.floorArea').val('');
            $(this).closest('tr').find('.carpetArea').val('');
        }
        var rowFloorArea = parseFloat($(this).find('td:eq(6) input.floorArea').val());
        if(rowFloorArea > rowPlinthArea) {
            $(this).closest('tr').find('.floorArea').val('');
            $(this).closest('tr').find('.carpetArea').val('');
            bootbox.alert($("#floorareaValidate").val());
            return false;
        }
        if(rowFloorArea)
            totalFloorArea +=  parseFloat(rowFloorArea);
        $("#sumOfFloorArea"+bldgIdx).val(totalFloorArea.toFixed(2));
        $("#"+tableId +" tfoot tr td:eq(6)").html(totalFloorArea.toFixed(2));

        var rowCarpetArea = parseFloat($(this).find('td:eq(7) input.carpetArea').val());
        if($(this).find('td:eq(7) input.carpetArea').val() == '.')
            $(this).find('td:eq(7) input.carpetArea').val(0.0);
        if(rowCarpetArea > rowFloorArea) {
            $(this).closest('tr').find('.carpetArea').val('');
            bootbox.alert($("#carpetareaValidate").val());
            return false;
        }
        if(rowCarpetArea)
            totalCarpet += parseFloat(rowCarpetArea);
        $("#"+tableId +" tfoot tr td:eq(7)").html(totalCarpet.toFixed(2));
    });

    var seviceTypeName = $( "#serviceType option:selected" ).text();
    if(totalPlinth && 'Huts and Sheds' != seviceTypeName) {
        $("#totalPlintArea"+bldgIdx).val(totalPlinth.toFixed(2));
    }
}

	
function generateSno(tableId)
{
	var idx=1;
	$('#'+tableId+ ' tbody tr').find('.serialNo').each(function(){
		$(this).text(idx);
		idx++;
	});
	
	$('#'+tableId+ ' tbody tr').find('.orderNo').each(function(i){
		$(this).val(++i);
		
	});
}

function validateBuildAreaOnAdd(tableId){
	
	var isValid=true;
    $('#'+tableId +' tbody tr').each(function(index){
    	var floorName  = $(this).find('*[name$="floorDescription"]').val();
    	var floorNumber  = $(this).find('*[name$="floorNumber"]').val();
	    var plinthArea = $(this).find('*[name$="plinthArea"]').val();
	    var floorArea = $(this).find('*[name$="floorArea"]').val();
	    var carpetArea = $(this).find('*[name$="carpetArea"]').val();
	    var occupancy  = $(this).find('*[name$="subOccupancy"]').val();
	    var usage  = $(this).find('*[name$="usage"]').val();
	    if(!floorName || !floorNumber || !plinthArea || !carpetArea || !floorArea || !occupancy || !usage) { 
	    	bootbox.alert($('#valuesCannotEmpty').val());
	    	isValid=false;
	    	return false;
	    } 
    });
   
    return isValid;
}

