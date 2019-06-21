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
            $('#planPermissionNumber').blur(function () {

			 if ($('#planPermissionNumber').val()) {
				 getApplicationByPermitNo();
	         }
	    });
            

        	function openPopup(url) {
        		window.open(url, 'window',
        				'scrollbars=1,resizable=yes,height=800,width=1100,status=yes');

        	}
        	
        	 if ($('#eDcrNumber').val()) {
                 getEdcrApprovedPlanDetails();
             }
        	 
        	 if ($('#planPermissionNumber').val()) {
        		 getApplicationByPermitNo($('#planPermissionNumber').val());
             }
        	 
        	 
        	 function getEdcrApprovedPlanDetails() {
                 $.ajax({
                     async: false,
                     crossDomain: true,
                     url: '/edcr/rest/approved-plan-details/by-edcr-number/' + $('#eDcrNumber').val(),
                     type: "GET",
                     contentType: 'application/json; charset=utf-8',
                     success: function (response) {
                     	
                     	if (response.errorDetail && response.errorDetail.errorCode != null && response.errorDetail.errorCode != '') {
                             bootbox.alert(response.errorDetail.errorMessage);
                             $('#eDcrNumber').val('');
                             resetDCRPopulatedValues();
                         } else if(response.applicationType !== 'Permit') {
                             bootbox.alert($('#dcrforoc').val());
                     	} else if ($("#serviceType option:selected").text()&& response.serviceType && response.serviceType !== $("#serviceType option:selected").text()) {
                             bootbox.alert($('#buildScrutinyNumber').val() + response.serviceType + $('#buildingPlanApplnForServiceType').val() + $("#serviceType option:selected").text() + $('#buildServiceType').val());
                             $('#eDcrNumber').val('');
                             resetDCRPopulatedValues();
                         } else {
                             if ($('#loadingFloorDetailsFromEdcrRequire').val() === 'true' && $('#mode').val() && $('#mode').val() === 'new')
                                 resetDCRPopulatedValues();
	                         $('#edcrApplicationNumber').html('<a onclick="openPopup(\'/bpa/application/details-view/by-permit-number/' + $('#planPermissionNumber').val() + '\')" href="javascript:void(0);">' + response.applicationNumber  + '</a>');
                             $('#edcrUploadedDate').html(response.applicationDate);
                             $('#edcrDxfFile').html('<a href="/egi/downloadfile?fileStoreId=' + response.dxfFile.fileStoreId + '&moduleName=Digit DCR&toSave=true">' + response.dxfFile.fileName + '</a>');
                             $('#edcrPlanReportOutput').html('<a href="/egi/downloadfile?fileStoreId=' + response.reportOutput.fileStoreId + '&moduleName=Digit DCR&toSave=true">' + response.reportOutput.fileName + '</a>');
                             if ($('#loadingFloorDetailsFromEdcrRequire').val() === 'true' && $('#mode').val() && $('#mode').val() === 'new' && response.plan) {
                                 var existingBldgPresent = [];
                                 if (response.plan.blocks.length > 0)
                                     for (var i = 0; i < response.plan.blocks.length; i++) {
                                         var block = response.plan.blocks[i];
                                         existingBldgPresent.push(isExistingBuildingDetailsPresent(block));
                                     }
                                 if (response.plan.blocks.length <= 0) {
                                     // Validate proposed building details if not present
                                     bootbox.alert($('#forBuildScrutinyNumber').val() + $('#eDcrNumber').val() + " " + $('#floorDetailsNotExtracted').val());
                                     $('#eDcrNumber').val('');
                                     resetDCRPopulatedValues();
                                     return false;
                                 } else if (response.plan.blocks.length > 0 && existingBldgPresent.length > 0
                                     && ($("#serviceType option:selected").text() === 'Alteration'
                                         || $("#serviceType option:selected").text() === 'Addition or Extension')
                                     && jQuery.inArray(true, existingBldgPresent) === -1) {
                                     // Validate existing building details if not present for required service
                                     bootbox.alert($('#forBuildScrutinyNumber').val() + $('#eDcrNumber').val() + " "  + $('#existingBuildDetailsNotPresent').val());
                                     $('#eDcrNumber').val('');
                                     resetDCRPopulatedValues();
                                     return false;
                                 } else {
                                 	//Reset selected occupancies 
                                 	$('#occupancyapplnlevel').val('');
                                 	if (response.plan.occupancies.length > 0) {
                                 		var occupancies = [];
 	                                    $.each(response.plan.occupancies, function(index, occupancy) {
 	                                        if(response.plan.planInformation.roadWidth < 12 && occupancy.type == 'Industrial')
                                                bootbox.alert('Road width cannot be less than 12m for industrial occupancy');
 	                                    	occupancies.push(occupancy.typeHelper.type.name);
 	                                    });
 	                                    
 	                                    $('[name=permitOccupanciesTemp] option').filter(function () {
 	                                        return occupancies.indexOf($(this).text()) > -1; //Options text exists in array
 	                                      }).prop('selected', true);
                                 	}
                                     $('#occupancyapplnlevel').trigger('change');
                                     $('#occupancyapplnlevel').attr("readonly", "true");

                                     if(existingBldgPresent.length > 0 && jQuery.inArray(true, existingBldgPresent) !== -1) {
                                      	$('.existingbuildingdetails').show();
                                     	setExistingBuildingDetailFromEdcrs(response.plan);                                        
                                      } else {
                                      	$('.existingbuildingdetails').hide();
                                      }
                                     
                                     setProposedBuildingDetailFromEdcrs(response.plan);
                                     
                                     //Get application type and set to application
                                     var occupancy = "";
                                 	if (response.plan.occupancies.length == 1) {
                                 		occupancy = response.plan.occupancies[0].typeHelper.type.name;    
                                 	} else {
                                 		if(response.plan.virtualBuilding)
                                 			occupancy=response.plan.virtualBuilding.mostRestrictiveFarHelper.type.name;
                                 	}
                                     var area =response.plotArea.toFixed(2);
                                     getApplicationType(area, response.plan.blocks[0].building.buildingHeight,occupancy);

                                     if($('#dcrDocsAutoPopulate').val() === 'true' || $('#dcrDocsAutoPopulateAndManuallyUpload').val() === 'true')
                                         autoPopulatePlanScrutinyGeneratedPdfFiles(response.planScrutinyPdfs);

                                     if (response.planFileStore === null)
                                         $('.editable').removeAttr("disabled");
                                     else
                                         $('.editable').attr("disabled", "disabled");
                                 }

                                 if (response.plan.planInformation.demolitionArea >= 0) {
                                     $('.demolitionDetails').show();
                                     $('#demolitionArea').val(response.plan.planInformation.demolitionArea);
                                     $('#demolitionArea').attr('readOnly', true);
                                 }
                                 if (response.projectType) {
                                     $('#projectName').val(response.projectType);
                                     $('#projectName').attr('readOnly', true);
                                 } else {
                                     $('#projectName').val('');
                                     $('#projectName').removeAttr('readOnly');
                                 }
                             } else if(!response.plan) {
                                 console.log("Error occurred when de-serialize, please check!!!!!!!");
                             }
                             if(response.plotArea) {
                                 $('#extentOfLand').val(response.plotArea.toFixed(2));
                             }
                             
                         }
                     },
                     error: function (response) {
                         console.log("Error occurred, when getting approved building plan scrutiny details!!!!!!!");
                     }
                 });
             }

	function getApplicationByPermitNo(permitNo) {
		$.ajax({
	        url: "/bpa/application/findby-permit-number",
	        type: "GET",
	        data: {
	            permitNumber : $('#planPermissionNumber').val()
	        },
	        cache : false,
	        async: false,
	        dataType: "json",
	        success: function (response) {
	            if(response) {
                    $('#permissionNumber').html('<a onclick="openPopup(\'/bpa/application/details-view/by-permit-number/' + $('#planPermissionNumber').val() + '\')" href="javascript:void(0);">' + $('#planPermissionNumber').val()  + '</a>');
                    $('#applicationDate').html(response.applicationDate);
                    $('#resurveyNo').html(response.reSurveyNumber);
                    $('#ownerName').html(response.applicantName);
                    $('#ownerAddress').html(response.applicantAddress);
                    $('#occupancyName').html(response.occupancy);
                    $('#serviceType').html(response.serviceTypeDesc);
                    $('#dcrNumber').html(response.dcrNumber);

	            	$.ajax({
	                    async: false,
	                    crossDomain: true,
	                    url: '/edcr/rest/approved-plan-details/by-edcr-number/' + response.dcrNumber,
	                    type: "GET",
	                    contentType: 'application/json; charset=utf-8',
	                    success: function (response) {
	                    	
	                    	if (response.errorDetail && response.errorDetail.errorCode != null && response.errorDetail.errorCode != '') {
	                            bootbox.alert(response.errorDetail.errorMessage);
	                            $('#eDcrNumber').val('');
	                            resetDCRPopulatedValues();
	                        } else if(response.applicationType !== 'Permit') {
	                            bootbox.alert($('#dcrforoc').val());
	                    	} else if ($("#serviceType option:selected").text()&& response.serviceType && response.serviceType !== $("#serviceType option:selected").text()) {
	                            bootbox.alert($('#buildScrutinyNumber').val() + response.serviceType + $('#buildingPlanApplnForServiceType').val() + $("#serviceType option:selected").text() + $('#buildServiceType').val());
	                            $('#eDcrNumber').val('');
	                            resetDCRPopulatedValues();
	                        } else {
	                            if ($('#loadingFloorDetailsFromEdcrRequire').val() === 'true' && $('#mode').val() && $('#mode').val() === 'new')
	                                resetDCRPopulatedValues();
	                            
	                          
	                            $('#edcrApplicationNumber').html(response.applicationNumber);

                                
                                
	                           // $('#edcrApplicationNumber').html('<a href="/bpa/application/details-view/by-permit-number/' + $('#planPermissionNumber').val() + '">' + response.applicationNumber  + '</a>');

	                            //$('#edcrApplicationNumber').html(response.applicationNumber);
	                            $('#edcrUploadedDate').html(response.applicationDate);
	                            $('#edcrDxfFile').html('<a href="/egi/downloadfile?fileStoreId=' + $('#planPermissionNumber').val() + '&moduleName=Digit DCR&toSave=true">' + response.dxfFile.fileName + '</a>');
	                            $('#edcrPlanReportOutput').html('<a href="/egi/downloadfile?fileStoreId=' + $('#planPermissionNumber').val() + '&moduleName=Digit DCR&toSave=true">' + response.reportOutput.fileName + '</a>');
	                            if ($('#loadingFloorDetailsFromEdcrRequire').val() === 'true' && $('#mode').val() && $('#mode').val() === 'new' && response.plan) {
	                                var existingBldgPresent = [];
	                                if (response.plan.blocks.length > 0)
	                                    for (var i = 0; i < response.plan.blocks.length; i++) {
	                                        var block = response.plan.blocks[i];
	                                        existingBldgPresent.push(isExistingBuildingDetailsPresent(block));
	                                    }
	                                if (response.plan.blocks.length <= 0) {
	                                    // Validate proposed building details if not present
	                                    bootbox.alert($('#forBuildScrutinyNumber').val() + $('#eDcrNumber').val() + $('#floorDetailsNotExtracted').val());
	                                    $('#eDcrNumber').val('');
	                                    resetDCRPopulatedValues();
	                                    return false;
	                                } else if (response.plan.blocks.length > 0 && existingBldgPresent.length > 0
	                                    && ($("#serviceType option:selected").text() === 'Alteration'
	                                        || $("#serviceType option:selected").text() === 'Addition or Extension'
	                                        || $("#serviceType option:selected").text() === 'Change in occupancy')
	                                    && jQuery.inArray(true, existingBldgPresent) === -1) {
	                                    // Validate existing building details if not present for required service
	                                    bootbox.alert($('#forBuildScrutinyNumber').val() + $('#eDcrNumber').val() + $('#existingBuildDetailsNotPresent').val());
	                                    $('#eDcrNumber').val('');
	                                    resetDCRPopulatedValues();
	                                    return false;
	                                } else {
	                                	if (response.plan.occupancies.length > 0) {
	                                		var occupancies = [];
		                                    $.each(response.plan.occupancies, function(index, occupancy) {
		                                        if(response.plan.planInformation.roadWidth < 12 && occupancy.type == 'Industrial')
	                                               bootbox.alert('Road width cannot be less than 12m for industrial occupancy');
		                                    	occupancies.push(occupancy.typeHelper.type.name);
		                                    });
		                                    
		                                    $('[name=permitOccupanciesTemp] option').filter(function () {
		                                        return occupancies.indexOf($(this).text()) > -1; //Options text exists in array
		                                      }).prop('selected', true);
	                                	}
	                                    $('#occupancyapplnlevel').trigger('change');
	                                    $('#occupancyapplnlevel').attr("readonly", "true");

	                                    if(existingBldgPresent.length > 0 && jQuery.inArray(true, existingBldgPresent) !== -1) {
	                                     	$('.existingbuildingdetails').show();
	                                    	setExistingBuildingDetailFromEdcrs(response.plan);                                        
	                                     } else {
	                                     	$('.existingbuildingdetails').hide();
	                                     }
	                                    
	                                    setProposedBuildingDetailFromEdcrs(response.plan);
	                                    
	                                    //Get application type and set to application
	                                    var occupancy = "";
	                                	if (response.plan.occupancies.length == 1) {
	                                		occupancy = response.plan.occupancies[0].typeHelper.type.name;    
	                                	} else {
	                                		if(response.plan.virtualBuilding)
	                                			occupancy=response.plan.virtualBuilding.mostRestrictiveFarHelper.type.name;
	                                	}
	                                    var area =response.plotArea.toFixed(2);
	                                    getApplicationType(area, response.plan.blocks[0].building.buildingHeight,occupancy);

	                                    if($('#dcrDocsAutoPopulate').val() === 'true' || $('#dcrDocsAutoPopulateAndManuallyUpload').val() === 'true')
	                                        autoPopulatePlanScrutinyGeneratedPdfFiles(response.planScrutinyPdfs);

	                                    if (response.planFileStore === null)
	                                        $('.editable').removeAttr("disabled");
	                                    else
	                                        $('.editable').attr("disabled", "disabled");
	                                }

	                                if (response.plan.planInformation.demolitionArea >= 0) {
	                                    $('.demolitionDetails').show();
	                                    $('#demolitionArea').val(response.plan.planInformation.demolitionArea);
	                                    $('#demolitionArea').attr('readOnly', true);
	                                }
	                                if (response.projectType) {
	                                    $('#projectName').val(response.projectType);
	                                    $('#projectName').attr('readOnly', true);
	                                } else {
	                                    $('#projectName').val('');
	                                    $('#projectName').removeAttr('readOnly');
	                                }
	                            } else if(!response.plan) {
	                                console.log("Error occurred when de-serialize, please check!!!!!!!");
	                            }
	                            if(response.plotArea) {
	                                $('#extentOfLand').val(response.plotArea.toFixed(2));
	                            }
	                            
	                        }
	                    },
	                    error: function (response) {
	                        console.log("Error occurred, when getting approved building plan scrutiny details!!!!!!!");
	                    }
	                });
	            	
	            	
	            } else {
	            	$('.resetValues').val('');
	               console.log("No application details available");
	            }
	        },
	        error: function (response) {
	            console.log("Error occurred while retrieving application details!!!!!!!");
	        }
	    });
		return true;
	}
});

function openPopup(url) {
	window.open(url, 'window',
			'scrollbars=1,resizable=yes,height=800,width=1100,status=yes');

}

