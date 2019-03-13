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
			
			var citizenOrBusiness = $('#citizenOrBusinessUser').val();
			if (citizenOrBusiness == 'true') {
				$('#serviceType').prop("disabled", true);
				$("#applicationDate").prop("disabled",true);
			}
			if ($('#bpaApplication').val()=="" &&  $('#citytown') && $('#citytown').val()=="" && $('#cityName') && $('#cityName').val()) 
				 $('#citytown').val($('#cityName').val());
			
			// email validation
			$('input[id$="emailId"]')
					.blur(
							function() {
								var pattern = new RegExp(
										"^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
								var email = $(this).val();
								if (!pattern.test(email) && $(this).val().length > 0) {
									var span = $(this).siblings('span');
									$(span).addClass('error-msg');
									$(span).text('Please enter valid email..!');
									$(this).show();
									$(this).val("");
								} else {
									var span1 = $(this).siblings('span');
									$(span1).removeClass('error-msg');
									$(span1).text('');
								}
							});
			// mobile number validation
			$('#mobileNumber').blur(function() {
				var mobileno = $(this).val();
				if (mobileno && mobileno.length < 10) {
                    var span = $(this).siblings('span');
                    $(span).addClass('error-msg');
                    $(span).text('Please enter 10 digit mobile number');
                    $(this).show();
                    $(this).val("");
				} else {
                    var span1 = $(this).siblings('span');
                    $(span1).removeClass('error-msg');
                    $(span1).text('');
                }
			});
			$('#zone').change(
					function() {
						$.ajax({
							url : "/bpa/boundary/ajaxBoundary-blockByWard",
							type : "GET",
							data : {
								wardId : $('#zone').val()
							},
							cache : false,
							dataType : "json",
							success : function(response) {
								$('#ward').html("");
								$('#ward').append("<option value=''>Select</option>");
								$.each(response, function(index, value) {
									$('#ward').append(
											$('<option>').text(value.blockName).attr(
													'value', value.blockId));
								});
							},
							error : function(response) {
								$('#ward').html("");
								$('#ward').append("<option value=''>Select</option>");
							}
						});
					});
			$('#ward').change(function(){
				$.ajax({
					url: "/bpa/boundary/ajaxBoundary-localityByWard",
					type: "GET",
					data: {
						wardId : $('#ward').val()
					},
					cache: false,
					dataType: "json",
					success: function (response) {
						$('#localitys').html("");
						$('#localitys').append("<option value=''>Select</option>");
						$.each(response, function(index, value) {
							$('#localitys').append($('<option>').text(value.localityName).attr('value', value.localityId));
						});
					}, 
					error: function (response) {
						$('#localitys').html("");
						$('#localitys').append("<option value=''>Select</option>");
					}
				});

				
				populateElectionWardByRevenueWard();
				
			});
			
			function populateElectionWardByRevenueWard(){
			
				$.ajax({
					url: "/bpa/boundary/ajaxBoundary-electionwardbyrevenueward",
					type: "GET",
					data: {
						wardId : $('#ward').val()
					},
					cache: false,
					dataType: "json",
					success: function (response) {
						$('#electionBoundary').html("");
						$('#electionBoundary').append("<option value=''>Select</option>");
						$.each(response, function(index, value) {
							$('#electionBoundary').append($('<option>').text(value.electionwardName).attr('value', value.electionwardId));
						});
					}, 
					error: function (response) {
						$('#electionBoundary').html("");
						$('#electionBoundary').append("<option value=''>Select</option>");
					}
				});
			}
			

			$('#schemes').change(function(){
				$.ajax({
					url: "/bpa/ajax/getlandusagebyscheme",
					type: "GET",
					data: {
						schemeId : $('#schemes').val()
					},
					cache: false,
					dataType: "json",
					success: function (response) {
						$('#landUsage').html("");
						$('#landUsage').append("<option value=''>Select</option>");
						$.each(response, function(index, value) {
							$('#landUsage').append($('<option>').text(value.usageDesc).attr('value', value.usageId));
						});
						$('#landUsage').val($('#landUsageObjectId').val());
					}, 
					error: function (response) {
						$('#landUsage').html("");
						$('#landUsage').append("<option value=''>Select</option>");
					}
					
				});
			});
			$('#landUsage').change(function() {
				$('#landUsageId').val($('#landUsage').val());
			});
			$('#schemes').trigger("change");
			$('#serviceType,#applicationAmenity,#applicationType').change(function() {
                /*if($('#isOneDayPermitApplication').is(':checked') && $("#applicationAmenity option:selected").val() && $("#applicationAmenity option:selected").text() != 'Roof Conversion under rule 100 or 101') {
                    $(".applicationAmenity").val('');
                    $('#admissionfee').val(0);
                    $('[name=applicationAmenityTemp] option').filter(function() {
                        return ($(this).text() == 'Roof Conversion under rule 100 or 101');
                    }).prop('selected', true);
                    bootbox.alert("One day permit is applicable only for Roof Conversion under rule 100 or 101 among amenities for others not applicable so you can't select other amenity type for one day permit application.");
                }*/
                var servicesAndAmenities =[];
                var applicationTypeId = null;
				servicesAndAmenities.push($('#serviceType').val());
				$.each($("#applicationAmenity option:selected"), function(idx){     
					servicesAndAmenities.push($(this).val());
				});
				applicationTypeId = $('#applicationType').val();
				$.ajax({
					url : "/bpa/ajax/getAdmissionFees",
					type : "GET",
					contentType: 'application/json; charset=UTF-8',
					data : {
						"serviceTypeIds" : servicesAndAmenities.join(","),
						"applicationTypeId" : applicationTypeId
					},
					cache : false,
					dataType: "json",
					success : function(response) {
						$('#admissionfee').val(response);
					},
					error : function(response) {
					}
				});
				
			});				
			
			
			$('#localitys').change(function(){
				$.ajax({
					url: "/bpa/ajax/registraroffice",
					type: "GET",
					data: {
						villageId : $("#localitys option:selected").val()
					},
					cache: false,
					dataType: "json",
					success: function (response) {
						$('#registrarOffice').html("");
						$('#registrarOffice').append("<option value=''>Select</option>");
						$.each(response, function(index, value) {
							$('#registrarOffice').append($('<option>').text(value.registrarOfficeName).attr('value', value.registrarVillageId));
						});
						$('#registrarOffice').val($('#registrarOfficeObjId').val());
					}, 
					error: function (response) {
						$('#registrarOffice').html("");
						$('#registrarOffice').append("<option value=''>Select</option>");
					}
				});
			});
			$('#localitys').trigger("change");
			$('#registrarOffice').change(function() {
				$('#registrarVillageIdHdn').val($('#registrarOffice').val());
                $('#registrarOfficeObjId').val($('#registrarOffice').val());
			});


            $('#serviceType')
					.change(
							function() {
								$
										.ajax({
											url : "/bpa/application/getdocumentlistbyservicetype",
											type : "GET",
											data : {
												serviceType : $('#serviceType').val(),
                                                checklistType : 'DOCUMENTATION'
											},
											dataType : "json",
											success : function(response) {
												$('#bpaDocumentsBody').empty();
												$
														.each(
																response,
																function(index, checklist) {
																	$('#bpaDocumentsBody')
																			.append(
																					'<div class="form-group">'
																							+ '<div class="col-sm-3 add-margin check-text"> <input type="hidden" id="applicationDocument'
																							+ index
																							+ 'checklistDetail" name="applicationDocument['
																							+ index
																							+ '].checklistDetail" value="'
																							+ checklist.id
																							+ '">'
																							+ '<input type="hidden" id="applicationDocument'
																							+ index
																							+ 'checklistDetail" name="applicationDocument['
																							+ index
																							+ '].checklistDetail.isMandatory" value="'
																							+ checklist.isMandatory
																							+ '">'
																							+ '<input type="hidden" id="applicationDocument'
																							+ index
																							+ 'checklistDetail.description" name="applicationDocument['
																							+ index
																							+ '].checklistDetail.description" value="'
																							+ checklist.description
																							+ '">'
																							+ checklist.description
																							+ (checklist.isMandatory ? '<span class="mandatory"></span>'
																									: '')
																							+ '</div>'
																							+ '<div class="col-sm-2 add-margin "><input type="checkbox" id="applicationDocument'
																							+ index
																							+ 'issubmitted" name="applicationDocument['
																							+ index
																							+ '].issubmitted" /></div>'
																							+ '<div class="col-sm-3 add-margin "><div class="input-group"><textarea class="form-control patternvalidation" data-pattern="string" maxlength="256" name="applicationDocument['
																							+ index
																							+ '].remarks" /></div></div>'
																							+ '<div class="col-sm-4 add-margin "><div class="files-upload-container" data-allowed-extenstion="doc,docx,xls,xlsx,rtf,pdf,txt,zip,jpeg,jpg,png,gif,tiff" '
																							+ (checklist.isMandatory ? "required"
																									: '')
																							+ '> <div class="files-viewer"> <a href="javascript:void(0);" class="file-add" data-unlimited-files="true" data-toggle="tooltip" data-placement="top" tittle="Test Tooltip" data-file-input-name="applicationDocument['
																							+ index
																							+ '].files"> <i class="fa fa-plus" aria-hidden="true"></i></a></div></div>'
																							+ '</div>'
																							+ '</div>');
																})
											},
											error : function(response) {

											}
										});
							});


            

			// toggle between multiple tab
			$('form').validate({
				ignore : ".ignore",
				invalidHandler : function(e, validator) {
					if (validator.errorList.length)
						focusToTabElement(validator.errorList[0].element);
				}
			});

			function focusToTabElement(element) {
				$('#settingstab a[href="#'
								+ $(element).closest(".tab-pane").attr('id') + '"]')
						.tab('show');
			}

            /*$('#mobileNumber').change(function(){
                $.ajax({
                    url: "/bpa/getApplicantDetails",
                    type: "GET",
                    data: {
                        mobileNumber : $('#mobileNumber').val()
                    },
                    cache : false,
                    dataType: "json",
                    success: function (response) {
                            if(response.id!=undefined){
                                $('#name').val(response.name);
                                $('#emailId').val(response.emailId);
                                $('#gender').val(response.gender);
                                $('#address').val(response.address);
                                $('#userId').val(response.id);
                                $('.applicantname').show();
                                $( "span#applicantName" ).html(response.name);
                                $("#name").prop("readOnly", true);
                                $("#emailId").prop("readOnly", true);
                                $('#gender').attr("style", "pointer-events: none;");
                                $("#address").prop("readOnly", true);
                            }else{
                                $("#name").prop("readOnly", false);
                                $("#emailId").prop("readOnly", false);
                                $('#gender').attr("style", "pointer-events:");
                                $("#address").prop("readOnly", false);
                                $('#userId').val("");
                            }
                    },
                    error: function (response) {
                    }
                });
            });*/

			// Instantiate the stakeholder name Bloodhound suggestion engine
			var stakeholderengine = new Bloodhound({
				datumTokenizer : function(datum) {
					return Bloodhound.tokenizers.whitespace(datum.value);
				},
				queryTokenizer : Bloodhound.tokenizers.whitespace,
				remote : {
					url : '/bpa/ajax/stakeholdersbytype',
					replace : function(url, query) {
						return url + '?name=' + query + '&stakeHolderType='
								+ $('#stakeHolderType').val();
					},
					filter : function(data) {
						// Map the remote source JSON array to a JavaScript object array
						return $.map(data, function(stakeHolder) {
							return {
								name : stakeHolder.name,
								value : stakeHolder.id
							}
						});
					}
				}
			});
			// Initialize the Bloodhound suggestion engine
			stakeholderengine.initialize();
			var sh_typeahead = $('#stakeHolderTypeHead').typeahead({
				hint : false,
				highlight : false,
				minLength : 1
			}, {
				displayKey : 'name',
				source : stakeholderengine.ttAdapter(),
				templates: {
				    empty: [
				      '<div class="empty-message">',
				        'No Name Available',
				      '</div>'
				    ].join('\n')
				  }
			});
			typeaheadWithEventsHandling(sh_typeahead, '#stakeHolderName');
			
			//Instantiate the postaladdress name Bloodhound suggestion engine
			var postaladdressengine = new Bloodhound({
				datumTokenizer : function(datum) {
					return Bloodhound.tokenizers.whitespace(datum.value);
				},
				queryTokenizer : Bloodhound.tokenizers.whitespace,
				remote : {
					url : '/bpa/ajax/postaladdress',
					replace : function(url, query) {
						return url + '?pincode=' + query ;
					},
					filter : function(data) {
						return $.map(data, function(postal) {
							return {
								name : postal.pincode,
								taluk : postal.taluk,
								pincode : postal.pincode,
								value : postal.id,
								postOffice : postal.postOffice,
								district : postal.district,
								state : postal.state
							}
						});
					}
				}
			});
			//Initialize the Bloodhound suggestion engine
			postaladdressengine.initialize();
			var postal_typeahead = $('#postalAddressTypeHead').typeahead({
				hint: false,
				highlight: false,
				minLength: 2
			}, {
				displayKey : 'name',
				source : postaladdressengine.ttAdapter(),
				 templates: {
				    empty: [
				      '<div class="empty-message">',
				        'No Pincode Available',
				      '</div>'
				    ].join('\n'),
				    suggestion: Handlebars.compile('<div class="custom-list-item"><h4 class="padding-10">{{name}} <small>{{postOffice}}</small></h4></div>')
				  }
			});
			function postalCodeSelectedEvent(event, data) {
					$('#taluk').val(data.taluk);
					$('#postalAddress').val(data.value);
					$('#postOffices').val(data.postOffice);
					$('#district').val(data.district);
					$('#state').val(data.state);
			}
			typeaheadWithEventsHandling(postal_typeahead, '#postalAddress', undefined, postalCodeSelectedEvent);
			
			//while doing server side validations on form fill postal address details
			if($('#postalAddress').val()) {
				$.ajax({
					url : '/bpa/ajax/getpostaladdressbyid?id='+$('#postalAddress').val(),
					type: "GET",
					cache: false,
					dataType: "json",
					success: function (response) {
						$('#taluk').val(response.taluk);
						$('#postalAddressTypeHead').val(response.pincode);
						$('#postOffices').val(response.postOffice);
						$('#district').val(response.district);
						$('#state').val(response.state);
					}, 
					error: function (response) {
						//
					}
				});
			}

            $('select.registrarOffice option').each(function () {
                if ($(this).val() == $('#registrarVillageIdHdn').val()) {
                    this.selected = true;
                    return;
            	}
            });

	});


	function loadDocumentsByServiceTypeAndChecklistType(serviceType, checklistType) {
	    $
	        .ajax({
	            url : "/bpa/application/getdocumentlistbyservicetype",
	            type : "GET",
	            async: false,
	            data : {
	                serviceType : serviceType,
	                checklistType : checklistType
	            },
	            dataType : "json",
	            success : function(response) {
	            	if('OCGENERALDOCUMENTS' === checklistType)
	                	appendOCGeneralDocuments(response);
	            	else if('OCDCRDOCUMENTS' === checklistType)
	                	appendOCDCRDocuments(response);
	            	else if('OCNOC' === checklistType)
	                    appendOCNOCDocuments(response);
	            },
	            error : function(response) {
	
	            }
	        });
	}
	
	function appendOCGeneralDocuments(response) {
	    $('#ocDocumentsBody').empty();
	    $
	        .each(
	            response,
	            function(index, serviceChecklist) {
	                $('#ocDocumentsBody')
	                    .append(
	                        '<div class="form-group">'
	                        + '<div class="col-sm-3 add-margin check-text"> <input type="hidden" class="checklistDetail'
	                        + index
	                        + '" name="documents['
	                        + index
	                        + '].document.serviceChecklist" value="'
	                        + serviceChecklist.id
	                        + '">'
	                        + '<input type="hidden" name="documents['
	                        + index
	                        + '].document.serviceChecklist.mandatory" value="'
	                        + serviceChecklist.mandatory
	                        + '">'
	                        + '<input type="hidden" name="documents['
	                        + index
	                        + '].document.serviceChecklist.checklist.description" value="'
	                        + serviceChecklist.checklistDesc
	                        + '">'
	                        + serviceChecklist.checklistDesc
	                        + (serviceChecklist.mandatory ? '<span class="mandatory"></span>'
	                        : '')
	                        + '</div>'
	                        + '<div class="col-sm-3 add-margin "><textarea class="form-control patternvalidation" data-pattern="alphanumericspecialcharacters" maxlength="256" name="documents['
	                        + index
	                        + '].document.remarks" /></div>'
	                        + '<div class="col-sm-6 add-margin "><div class="files-upload-container" data-allowed-extenstion="doc,docx,xls,xlsx,rtf,pdf,txt,zip,jpeg,jpg,png,gif,tiff" '
	                        + (serviceChecklist.mandatory ? "required"
	                        : '')
	                        + '> <div class="files-viewer"> <a href="javascript:void(0);" class="file-add" data-unlimited-files="true" data-toggle="tooltip" data-placement="top" tittle="Test Tooltip" data-file-input-name="documents['
	                        + index
	                        + '].document.files"> <i class="fa fa-plus" aria-hidden="true"></i></a></div></div>'
	                        + '</div>'
	                        + '</div>');
	            });
	}
	
	function appendOCDCRDocuments(response) {
	    $('#ocDCRDocumentsBody').empty();
	    $
	        .each(
	            response,
	            function(index, serviceChecklist) {
	            	var auto = $('#dcrDocsAutoPopulate').val();
	            	var manual = $('#dcrDocsManuallyUpload').val();
	            	var override = $('#dcrDocsAutoPopulateAndManuallyUpload').val();
	            	var splittedChklistDesc = serviceChecklist.checklistDesc.split(" ");
	            	var checklistName = splittedChklistDesc.join("_");
	            	
	            	var whenAuto = '<div class="col-sm-6 add-margin autoPopulateDcrDocs"><div class="files-viewer '
	            		+checklistName
	            		+'"><input type="hidden" name="dcrDocuments['
	            		+ index
	            		+'].dcrDocument.fileStoreIds" id="fileStoreIds" class="'
	            		+checklistName
	            		+'_fileStoreIds" > </div> </div>';
	            	var whenManualOrOverride = '<div class="col-sm-6 add-margin "><div class="files-upload-container" data-allowed-extenstion="pdf" '
                        + (serviceChecklist.mandatory ? "required"
    	                        : '')
    	                        + '> <div class="files-viewer '
    	                        +checklistName
    	                        +'"><input type="hidden" name="dcrDocuments['
    	                        +index
    	                        +'].dcrDocument.fileStoreIds" id="fileStoreIds" class="'
    	                        +checklistName
    	                        +'_fileStoreIds dcrFileStoreIds" > <a href="javascript:void(0);" class="file-add" data-unlimited-files="true" data-toggle="tooltip" data-placement="top" tittle="Test Tooltip" data-file-input-name="dcrDocuments['
    	                        + index
    	                        + '].dcrDocument.files"> <i class="fa fa-plus" aria-hidden="true"></i></a></div></div>';
	            	var appendFileAdd;
	            	if(auto === 'true' && manual === 'false' && override === 'false') {
	            		appendFileAdd = whenAuto;
	            	} else {
	            		appendFileAdd = whenManualOrOverride;
	            	}
	            		
	                $('#ocDCRDocumentsBody')
	                    .append(
	                        '<div class="form-group">'
	                        + '<div class="col-sm-3 add-margin check-text"> <input type="hidden" name="dcrDocuments['
	                        + index
	                        + '].dcrDocument.serviceChecklist" value="'
	                        + serviceChecklist.id
	                        + '">'
	                        + '<input type="hidden" name="dcrDocuments['
	                        + index
	                        + '].dcrDocument.serviceChecklist.mandatory" value="'
	                        + serviceChecklist.mandatory
	                        + '">'
	                        + '<input type="hidden" name="dcrDocuments['
	                        + index
	                        + '].dcrDocument.serviceChecklist.checklist.description" value="'
	                        + serviceChecklist.checklistDesc
	                        + '">'
	                        + serviceChecklist.checklistDesc
	                        + (serviceChecklist.mandatory ? '<span class="mandatory"></span>'
	                        : '')
	                        + '</div>'
	                        + '<div class="col-sm-3 add-margin "><textarea class="form-control patternvalidation" data-pattern="alphanumericspecialcharacters" maxlength="256" name="dcrDocuments['
	                        + index
	                        + '].dcrDocument.remarks" /></div>'
	                        + appendFileAdd
	                        + '</div>'
	                        + '</div>');
	            });
	}
	
	function appendOCNOCDocuments(response) {
	    $('#ocNOCDocumentsBody').empty();
	    $
	        .each(
	            response,
	            function(index, serviceChecklist) {
	                $('#ocNOCDocumentsBody')
	                    .append(
	                        '<tr>'
	                        + '<td>'
							+ parseInt(index + 1)
	                        + '</td>'
	                        + '<td style="font-size: 100%;"> <input type="hidden"'
	                        + 'checklistDetail" name="nocDocuments['
	                        + index
	                        + '].nocDocument.serviceChecklist" value="'
	                        + serviceChecklist.id
	                        + '">'
	                        + '<input type="hidden"'
	                        + index
	                        + 'checklistDetail" name="nocDocuments['
	                        + index
	                        + '].nocDocument.serviceChecklist.mandatory" value="'
	                        +  serviceChecklist.mandatory
	                        + '">'
	                        + serviceChecklist.checklistDesc
	                        + (serviceChecklist.mandatory ? '<span class="mandatory"></span>'
	                        : '')
	                        + '</td>'
	                        + '<td><div class="input-group"><textarea class="form-control patternvalidation" data-pattern="alphanumericspecialcharacters" maxlength="1000"'
							+ 'id="nocDocuments'
	                        + index
							+ 'natureOfRequest" name="nocDocuments['
	                        + index
	                        + '].nocDocument.natureOfRequest" /><span class="input-group-addon showModal"'
	                        + ' data-assign-to="nocDocuments'
							+ index
							+ 'natureOfRequest" data-header="Nature Of NOC Request"><span class="glyphicon glyphicon-pencil" style="cursor: pointer"></span></span></div></td>'
	                        + '<td class="hide"><input class="form-control datepicker letterSentOn"'
					        + 'data-date-end-date="0d"'
	                		+ 'name="nocDocuments['
							+ index
							+ '].nocDocument.letterSentOn"/>'
	                        + '</td>'
	                        + '<td class="hide"><input class="form-control datepicker replyReceivedOn"'
	                        + 'data-date-end-date="0d"'
	                        + 'name="nocDocuments['
							+ index
							+ '].nocDocument.replyReceivedOn"/>'
	                        + '</td>'
	                        + '<td class="hide"><select name="nocDocuments['
							+ index
							+ '].nocDocument.nocStatus"'
	               		 	+ 'class="form-control nocStatus">'
	                    	+ '<option value="">Select</option>'
	                    	+ '</select>'
	                    	+ '</td>'
	                        + '<td><div class="input-group"><textarea class="form-control patternvalidation" data-pattern="alphanumericspecialcharacters" maxlength="1000"'
							+ ' id="nocDocuments'
	                        + index
							+ 'remarks" name="nocDocuments['
	                        + index
	                        + '].nocDocument.remarks" /><span class="input-group-addon showModal"'
	                        + ' data-assign-to="nocDocuments'
	                        + index
	                        + 'remarks" data-header="Remarks"><span'
	                        + ' class="glyphicon glyphicon-pencil" style="cursor: pointer"></span></span></div></td>'
	                        + '<td><div class="files-upload-container" data-allowed-extenstion="doc,docx,xls,xlsx,rtf,pdf,txt,zip,jpeg,jpg,png,gif,tiff" '
	                        + (serviceChecklist.mandatory ? "required"
	                        : '')
	                        + '> <div class="files-viewer"> <a href="javascript:void(0);" class="file-add" data-unlimited-files="true" data-toggle="tooltip" data-placement="top" tittle="Test Tooltip" data-file-input-name="nocDocuments['
	                        + index
	                        + '].nocDocument.files"> <i class="fa fa-plus" aria-hidden="true"></i></a></div></div>'
	                        + '</td>'
	                        + '</tr>');
	            });
	}

	function getApplicationByPermitNo(permitNo) {
		var isExist = false;
		$.ajax({
	        url: "/bpa/application/findby-permit-number",
	        type: "GET",
	        data: {
	            permitNumber : permitNo
	        },
	        cache : false,
	        async: false,
	        dataType: "json",
	        success: function (response) {
	            if(response) {
	            	isExist = true;
	            	loadDocumentsByServiceTypeAndChecklistType(response.serviceTypeId, 'OCDCRDOCUMENTS');
	            	loadDocumentsByServiceTypeAndChecklistType(response.serviceTypeId, 'OCGENERALDOCUMENTS');
	                loadDocumentsByServiceTypeAndChecklistType(response.serviceTypeId, 'OCNOC');
	                $('#serviceTypeDesc').val(response.serviceTypeDesc);
	                $('#serviceType').val(response.serviceTypeId);
	                $('#serviceTypeCode').val(response.serviceTypeCode);
	                $('#occupancy').val(response.occupancy);
	                $('#applicationType').val(response.applicationType);
	                $('.applicantName').val(response.applicantName);
	                $('#bpaApplicationId').val(response.id);
	                $('#applicationNumber').val(response.applicationNumber);
	                $('#planPermissionNumber').val(response.planPermissionNumber);
	                $('#zone').val(response.zone);
	                $('#electionWard').val(response.electionWard);
	                $('#revenueWard').val(response.revenueWard);
	                $('#resurveyNumber').val(response.reSurveyNumber);
	                $('#village').val(response.village);
	                $('#plotArea').val(response.plotArea);
	            } else {
	            	$('.resetValues').val('');
	               console.log("No application details available");
	            }
	        },
	        error: function (response) {
	            console.log("Error occurred while retrieving application details!!!!!!!");
	        }
	    });
		return isExist;
	}
