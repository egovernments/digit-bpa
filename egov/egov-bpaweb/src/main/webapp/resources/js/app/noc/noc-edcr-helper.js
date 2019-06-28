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
	    function ($) {
	    	
	    	
		if ($('#eDcrNumber').val()) {
              getEdcrApprovedPlanDetails();
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
                        $('#edcrApplicationNumber').html(response.applicationNumber);
                        $('#edcrUploadedDate').html(response.applicationDate);
                        $('#edcrDxfFile').html('<a href="/egi/downloadfile?fileStoreId=' + response.dxfFile.fileStoreId + '&moduleName=Digit DCR&toSave=true">' + response.dxfFile.fileName + '</a>');
                        $('#edcrPlanReportOutput').html('<a href="/egi/downloadfile?fileStoreId=' + response.reportOutput.fileStoreId + '&moduleName=Digit DCR&toSave=true">' + response.reportOutput.fileName + '</a>');
                        if ( response.plan) {
                            var existingBldgPresent = [];
                            if (response.plan.blocks.length > 0)
                                for (var i = 0; i < response.plan.blocks.length; i++) {
                                    var block = response.plan.blocks[i];
                                  //  existingBldgPresent.push(isExistingBuildingDetailsPresent(block));
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
                                
                                
                                //Get application type and set to application
                                var occupancy = "";
                            	if (response.plan.occupancies.length == 1) {
                            		occupancy = response.plan.occupancies[0].typeHelper.type.name;    
                            	} else {
                            		if(response.plan.virtualBuilding)
                            			occupancy=response.plan.virtualBuilding.mostRestrictiveFarHelper.type.name;
                            	}
                                var area =response.plotArea.toFixed(2);

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
                      
                    }
                },
                error: function (response) {
                    console.log("Error occurred, when getting approved building plan scrutiny details!!!!!!!");
                }
            });
        }
		});


$('#buttonApprove').click(function (e) {
	document.getElementById("workFlowAction").value = 'Approved';

        bootbox
            .dialog({
                message: $('#submitApplication').val(),
                buttons: {
                    'confirm': {
                        label: 'Yes',
                        className: 'btn-primary',
                        callback: function (result) {
                        	$("#editNocApplicationForm").submit();
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
   
    return false;
});

$('#buttonReject').click(function (e) {
	document.getElementById("workFlowAction").value = 'Rejected';
	$('#remarks').attr('required', 'required');
	if ($('#editNocApplicationForm').valid()){
        bootbox
            .dialog({
                message: $('#rejectApplication').val(),
                buttons: {
                    'confirm': {
                        label: 'Yes',
                        className: 'btn-primary',
                        callback: function (result) {
                           // $('#editNocApplicationForm').trigger('submit');
                        	document.getElementById("editNocApplicationForm").submit()
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

$(document).on('change', '#myfile', function () {
    var fileformat = [ 'doc', 'docx', 'xls', 'xlsx','rtf', 'pdf', 'jpeg', 'jpg', 'png',
    	'txt', 'zip','rar' ];
    var ext = $(this).val().split('.').pop();
    var fileInput = $(this);
    if (this.files.length) {
        var fileSize = this.files[0].size; // in
        // bytes
        var charlen = (this.value
            .split('/').pop().split(
                '\\').pop()).length;
        if (charlen > 50) {
            bootbox
                .alert('Document name should not exceed 50 characters!');
            fileInput.replaceWith(fileInput.val('').clone(true));
            return false;
        } else if (fileSize > 31457280) {
            bootbox.alert('File size should not exceed 30 MB!');
            return false;
        } else if ($.inArray(ext.toLowerCase(), fileformat) == -1) {
            bootbox.alert("Please upload " + fileformat
                + " format file only");
            $(this).val('');
            return false;
        } else {
            console.log('Files -->', this.files[0]);
            $('#fileTrigger').hide().parent().find('p, .fileActions').removeClass('hide');
            $('#fileName').html(this.files[0].name);
        }
    }
});

$('#fileTrigger').click(function () {
    $('.upload-msg').addClass('hide');
    $("#myfile").trigger('click');
    this.blur();
    this.focus();
});

$('#fileDelete').click(function () {
    $('.fileSection').find('p').addClass('hide');
    $('#fileTrigger').show();
    $('.fileActions').addClass('hide');
    $('#myfile').val([]);
});




