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
	 $( ".plinthArea" ).trigger( "change" );
	 $( ".carpetArea" ).trigger( "change" );
	 $( ".floorArea" ).trigger( "change" );
	 //setFloorCount();
	  
	var tbody = $('#buildingAreaDetails').children('tbody');
	var table = tbody.length ? tbody : $('#buildingAreaDetails');
	var row = '<tr>'+
	'<td class="text-center"><span class="serialNo text-center" id="slNoInsp">{{sno}}</span><input type="hidden" class="orderNo" data-sno name="buildingDetail[0].applicationFloorDetailsForUpdate[{{idx}}].orderOfFloor"/></td>'+
	'<td ><select name="buildingDetail[0].applicationFloorDetailsForUpdate[{{idx}}].floorDescription" data-first-option="false" id="applicationFloorDetailsForUpdate[{{idx}}]floorDescription" class="form-control floor-details-mandatory floorDescription duplicate-clear" required="required" maxlength="128" > <option value="">Select</option><options items="${buildingFloorList}" /></select></td>'+
	'<td class="text-right"><input type="text" class="form-control table-input text-center patternvalidation floorNumber floor-details-mandatory duplicate-clear" name="buildingDetail[0].applicationFloorDetailsForUpdate[{{idx}}].floorNumber" data-pattern="number" required="required" id="applicationFloorDetailsForUpdate[{{idx}}]floorNumber" maxlength="3" /></td>'+
	'<td ><select name="buildingDetail[0].applicationFloorDetailsForUpdate[{{idx}}].occupancy" data-first-option="false" id="applicationFloorDetailsForUpdate[{{idx}}]occupancy" class="form-control floor-details-mandatory occupancy" required="required" maxlength="128" > <option value="">Select</option><options items="${occupancyList}" /></select></td>'+
	'<td class="text-right"><input type="text" class="form-control table-input text-right patternvalidation plinthArea nonzero floor-details-mandatory decimalfixed" data-pattern="decimalvalue" name="buildingDetail[0].applicationFloorDetailsForUpdate[{{idx}}].plinthArea" id="applicationFloorDetailsForUpdate[{{idx}}]plinthArea" required="required" maxlength="10" onblur="validateFloorDetails(this)" /></td>'+
	'<td class="text-right"><input type="text" class="form-control table-input text-right patternvalidation floorArea nonzero floor-details-mandatory decimalfixed" data-pattern="decimalvalue" name="buildingDetail[0].applicationFloorDetailsForUpdate[{{idx}}].floorArea" id="applicationFloorDetailsForUpdate[{{idx}}]floorArea" maxlength="10" required="required" /></td>'+
	'<td class="text-right"><input type="text" class="form-control table-input text-right patternvalidation carpetArea floor-details-mandatory decimalfixed" data-pattern="decimalvalue" name="buildingDetail[0].applicationFloorDetailsForUpdate[{{idx}}].carpetArea" id="applicationFloorDetailsForUpdate[{{idx}}]carpetArea" maxlength="10" required="required" value=""  /></td>'+
	'<td class="text-center"><a href="javascript:void(0);" class="btn-sm btn-danger" id="deleteBuildAreaRow" ><i class="fa fa-trash"></i></a></td>'+
	'</tr>';
	
	$('#addBuildAreaRow').click(function(){
		if(validateBuildAreaOnAdd()){
			var idx=$(tbody).find('tr').length;
			var sno=idx+1;
			//Add row
			var row={
			       'sno': sno,
			       'idx': idx
			   };
			addRowFromObject(row);
			patternvalidation();
			generateSno();
			loadFloorlist("buildingDetail[0].applicationFloorDetailsForUpdate["+idx+"].floorDescription");
			if($("#occupancyapplnlevel option:selected" ).text() == 'Mixed') {
				loadOccupanctyDetails("buildingDetail[0].applicationFloorDetailsForUpdate["+idx+"].occupancy");
			} else {
				loadOccupancyDetails1("buildingDetail[0].applicationFloorDetailsForUpdate["+idx+"].occupancy",$("#occupancyapplnlevel option:selected" ).val(),$("#occupancyapplnlevel option:selected" ).text());
			}
		}
	});
	
	function addRowFromObject(rowJsonObj)
	{
		table.append(row.compose(rowJsonObj));
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
	
	function loadOccupanctyDetails(selectBoxName){
		
		 $.ajax({
				url: "/bpa/application/getoccupancydetails",     
				type: "GET",
				async: false,
				dataType: "json",
				success: function (response) {
					$('select[name="'+selectBoxName+'"]').empty();
					$('select[name="'+selectBoxName+'"]').append($("<option value=''>Select </option>"));
					$.each(response, function(index, occupancy) {
						if(occupancy.description != 'Mixed')
						$('select[name="'+selectBoxName+'"]').append($('<option>').val(occupancy.id).text(occupancy.description));
					});
				}, 
				error: function (response) {
				}
			});
		}
	
	function loadOccupancyDetails1(selectBoxName,id,value){
					$('select[name="'+selectBoxName+'"]').empty();
					$('select[name="'+selectBoxName+'"]').append($('<option>').val(id).text(value));
		}
	
	$(document).on('blur','.floorNumber', function() {
		setFloorCount();
		var rowObj = $(this).closest('tr');
		validateUniqueDetails(rowObj.index(),$(rowObj).find('.floorDescription').val(), $(rowObj).find('.floorNumber').val(), $(rowObj).find('.occupancy').val());
	}); 
	
	$(document).on('blur','.floorDescription', function() {
		var rowObj = $(this).closest('tr');
		if($(rowObj).find('.floorDescription').val() && $(rowObj).find('.floorDescription').val() == 'Cellar Floor') {
			$(rowObj).find('.floorNumber').data('pattern','numerichyphen');
		} else {
			$(rowObj).find('.floorNumber').data('pattern','number');
		}
		patternvalidation();
		$(rowObj).find('.floorNumber').val('');
		if(!$("#occupancyapplnlevel").val()) {
			$('#buildingAreaDetails').find('select').val('');
			bootbox.alert('Please select main occupancy type.');
			return false;
		}
		setFloorCount();
		if(($(rowObj).find('.floorDescription').val() == '' || $(rowObj).find('.floorDescription').val() == 'undefined') && $('#buildingAreaDetails tbody tr').length <= 1) {
			$("#floorCount").val('');
		}
		validateUniqueDetails(rowObj.index(),$(rowObj).find('.floorDescription').val(), $(rowObj).find('.floorNumber').val(), $(rowObj).find('.occupancy').val());
	});
	
	$(document).on('blur','.occupancy', function() {
		var rowObj = $(this).closest('tr');
		validateUniqueDetails(rowObj.index(),$(rowObj).find('.floorDescription').val(), $(rowObj).find('.floorNumber').val(), $(rowObj).find('.occupancy').val());
	});

    var plinthAreaSum = 0;
    var carpetAreaSum = 0;


    $(document).on('change', '.plinthArea', function() {
        /*var totalPlinth = 0;
        var rowPlinthArea = 0;
        var rowFloorArea = 0;
        var rowObj = $(this).closest('tr');
        $("#buildingAreaDetails tbody tr").each(function () {
            rowFloorArea = parseFloat($(this).find('td:eq(3) input.floorArea').val());
            rowPlinthArea = parseFloat($(this).find('td:eq(4) input.plinthArea').val());
             if(rowPlinthArea > rowFloorArea) {
                 $(rowObj).find('.plinthArea').val('');
                 $( ".floorArea" ).trigger( "change" );
                 $( ".plinthArea" ).trigger( "change" );
                 bootbox.alert("Please enter valid values, Builtup Area should be less than or equal to the Floor Area.");
                 return false;
             }
             if($(this).find('td:eq(4) input.plinthArea').val())
                 totalPlinth +=  parseFloat($(this).find('td:eq(4) input.plinthArea').val());
        });
        var seviceTypeName = $( "#serviceType option:selected" ).text();
        if(totalPlinth && 'Huts and Sheds' != seviceTypeName) {
            $("#totalPlintArea").val(totalPlinth.toFixed(2));
        }
        $("#buildingAreaDetails tfoot tr td:eq(4)").html(totalPlinth.toFixed(2));*/

        validateAndCalculateTotalOfFloorDetails();
    });

    $(document).on('change', '.floorArea', function() {
        /*var totalFloorArea = 0 ;
        var rowObj = $(this).closest('tr');
        $("#buildingAreaDetails tbody tr").each(function () {
            var rowPlinthArea = parseFloat($(this).find('td:eq(4) input.plinthArea').val());
            var rowFloorArea = parseFloat($(this).find('td:eq(5) input.floorArea').val());
                if(rowFloorArea > rowPlinthArea) {
                $(rowObj).find('.floorArea').val('');
                $( ".plinthArea" ).trigger( "change" );
                $( ".floorArea" ).trigger( "change" );
                $( ".carpetArea" ).trigger( "change" );
                bootbox.alert("Floor Area should be less than or equal to the Builtup Area.");
                return false;
               }
            if(rowFloorArea)
             totalFloorArea +=  parseFloat($(this).find('td:eq(5) input.floorArea').val());
        });
            $("#sumOfFloorArea").val(totalFloorArea.toFixed(2));
            $("#buildingAreaDetails tfoot tr td:eq(5)").html(totalFloorArea.toFixed(2));*/

        validateAndCalculateTotalOfFloorDetails();

    });

    $(document).on('change', '.carpetArea', function() {
        /* var rowObj = $(this).closest('tr');
         var totalCarpet = 0;
         $("#buildingAreaDetails tbody tr").each(function () {
             var rowFloorArea = parseFloat($(this).find('td:eq(5) input.floorArea').val());
             var rowCarpetArea = parseFloat($(this).find('td:eq(6) input.carpetArea').val());
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

             validateAndCalculateTotalOfFloorDetails();
             if($(this).find('td:eq(6) input.carpetArea').val())
             totalCarpet += parseFloat($(this).find('td:eq(6) input.carpetArea').val());
         });
        $("#buildingAreaDetails tfoot tr td:eq(6)").html(totalCarpet.toFixed(2));*/
        validateAndCalculateTotalOfFloorDetails();
    });

    var deletedId = [];
    $(document).on('click',"#deleteBuildAreaRow",function (){
        var rowIndex = $(this).closest('td').parent()[0].sectionRowIndex;
        if($(this).data('record-id'))
            deletedId.push($(this).data('record-id'));

        $('#deletedFloorIds').val(deletedId);
        $(this).closest('tr').remove();

        generateSno();

        $("#buildingAreaDetails tbody tr").each(function() {
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
        setFloorCount();
        // on delete to re-calculate sum of plinth and carpet area
        $( ".floorArea" ).trigger( "change" );
        $( ".plinthArea" ).trigger( "change" );
        $( ".carpetArea" ).trigger( "change" );

    });

});


function validateUniqueDetails(idx,floorDesc,level,occupancy){
	if(floorDesc && occupancy) {
		$('#buildingAreaDetails tbody tr').each(function(index){
			
			if(idx===index)
				return;
			
			var floorName  = $(this).find('*[name$="floorDescription"]').val().trim();
		    var floorNumber = $(this).find('*[name$="floorNumber"]').val().trim();
		    var occupancyType = $(this).find('*[name$="occupancy"]').val().trim();
							    if (floorDesc
									&& floorDesc.trim() === floorName
									&& level
									&& Number(level.trim()) === Number(floorNumber)
									&& occupancy
									&& occupancy.trim() === occupancyType) {
								$(
										'#buildingAreaDetails tbody tr:eq('
												+ idx + ')').find(
										'.duplicate-clear').val('');
								bootbox
										.alert('With combination of Floor Description : '
												+ floorDesc
												+ ', Level : '
												+ Number(level)
												+ ' and Occupancy Type : '
												+ $(this)
														.find(
																'*[name$="occupancy"] option:selected')
														.text()
												+ ' floor details are already present, please check and enter valid details.');
								return false;
							}
		});
	}
}

function setFloorCount() {
	
	var floorsObj={};
	
	$('#buildingAreaDetails tbody tr').each(function(e){
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
	
	$("#floorCount").val(len);
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
        var rowFloorArea = parseFloat($(this).find('td:eq(5) input.floorArea').val());
        if(rowFloorArea > rowPlinthArea) {
            $(this).closest('tr').find('.floorArea').val('');
            $(this).closest('tr').find('.carpetArea').val('');
            bootbox.alert("Floor Area should be less than or equal to the Builtup Area.");
            return false;
        }
        if(rowFloorArea)
            totalFloorArea +=  parseFloat(rowFloorArea);
        $("#sumOfFloorArea").val(totalFloorArea.toFixed(2));
        $("#buildingAreaDetails tfoot tr td:eq(5)").html(totalFloorArea.toFixed(2));

        var rowCarpetArea = parseFloat($(this).find('td:eq(6) input.carpetArea').val());
        if($(this).find('td:eq(6) input.carpetArea').val() == '.')
            $(this).find('td:eq(6) input.carpetArea').val(0.0);
        if(rowCarpetArea > rowFloorArea) {
            $(this).closest('tr').find('.carpetArea').val('');
            bootbox.alert("Carpet Area should be less than or equal to the Floor Area.");
            return false;
        }
        if(rowCarpetArea)
            totalCarpet += parseFloat(rowCarpetArea);
        $("#buildingAreaDetails tfoot tr td:eq(6)").html(totalCarpet.toFixed(2));
    });

    var seviceTypeName = $( "#serviceType option:selected" ).text();
    if(totalPlinth && 'Huts and Sheds' != seviceTypeName) {
        $("#totalPlintArea").val(totalPlinth.toFixed(2));
    }
}

	
function generateSno()
{
	var idx=1;
	$(".serialNo").each(function(){
		$(this).text(idx);
		idx++;
	});
	
	$('.orderNo').each(function(i){
		$(this).val(++i);
		
	});
}

function validateBuildAreaOnAdd(){
	
	var isValid=true;
    $('#buildingAreaDetails tbody tr').each(function(index){
    	var floorName  = $(this).find('*[name$="floorDescription"]').val();
    	var floorNumber  = $(this).find('*[name$="floorNumber"]').val();
	    var plinthArea = $(this).find('*[name$="plinthArea"]').val();
	    var floorArea = $(this).find('*[name$="floorArea"]').val();
	    var carpetArea = $(this).find('*[name$="carpetArea"]').val();
	    var occupancy  = $(this).find('*[name$="occupancy"]').val();
	    if(!floorName || !floorNumber || !plinthArea || !carpetArea || !floorArea || !occupancy) { 
	    	bootbox.alert("Please enter or select all values of existing rows before adding. Values cannot empty.");
	    	isValid=false;
	    	return false;
	    } 
    });
   
    return isValid;
}

