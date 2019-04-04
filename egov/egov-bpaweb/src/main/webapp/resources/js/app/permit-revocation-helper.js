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

$(document).ready(function($) {
	
	$(document).on(
			'blur',
			'.textarea-content',
			function(evt) {
				$(this).tooltip('hide').attr(
						'data-original-title', $(this).val());
				evt.stopImmediatePropagation();
			});
	$(document).on(
			'click',
			'.showModal',
			function(evt) {
				var tableheaderid = $(this).data('header');
				$('#textarea-header').html(tableheaderid);
				$('#textarea-updatedcontent').attr(
						'data-assign-to',
						$(this).data('assign-to'));
				$('#textarea-updatedcontent').val(
						$('#' + $(this).data('assign-to'))
								.val());
				$("#textarea-modal").modal('show');
				evt.stopImmediatePropagation();
			});

	// update textarea content in table wrt index
	$(document).on(
			'click', '#textarea-btnupdate',
			function(evt) {
				$('#'+ $('#textarea-updatedcontent').attr(
						'data-assign-to')).val($('#textarea-updatedcontent').val());
				evt.stopImmediatePropagation();
			});

	$('.requestDate').datepicker({
        format: "dd/mm/yyyy",
        autoclose: true
    });
	$('.replyDate').datepicker({
        format: "dd/mm/yyyy",
        autoclose: true
    });
    $('.requestDate').on('changeDate', function() {
        var rowObj = $(this).closest('tr');
        $("#permitRevocationDetails tbody tr").each(function () {
            var letterSent = $(this).find('*[name$="requestDate"]').val();
            var replyReceived = $(this).find('*[name$="replyDate"]').val();
            var letterSentOn = moment(letterSent,'DD/MM/YYYY');
            var replyReceivedOn = moment(replyReceived,'DD/MM/YYYY');
            if(!letterSentOn) {
                bootbox
                    .alert("Please enter request date");
            } else if (replyReceivedOn.isBefore(letterSentOn)) {
                bootbox
                    .alert("Reply date should be greater than requested date");
                $(rowObj).find('.replyDate').val('').datepicker("refresh");
            }
        });
    });

	var tbody = $('#permitRevocationDetails').children('tbody');
	var table = tbody.length ? tbody : $('#permitRevocationDetails');
	var row = '<tr>'+
	'<td><textarea class="form-control patternvalidation textarea-content" data-pattern="alphanumericspecialcharacters" maxlength="512" id="revocationDetails{{idx}}natureOfRequest" name="revocationDetails[{{idx}}].natureOfRequest" />'+
	'<span class="input-group-addon showModal" data-assign-to="revocationDetails{{idx}}natureOfRequest" data-header="Nature Of Request"><span class="glyphicon glyphicon-pencil" style="cursor: pointer"></span></span></td>'+
	'<td><input type="text" class="form-control datepicker requestDate" maxlength="50" data-date-end-date="0d" data-inputmask="\'mask\': \'d/m/y\'" name="revocationDetails[{{idx}}].requestDate" /> </td>'+
	'<td><input type="text" class="form-control datepicker replyDate" maxlength="50" data-date-end-date="0d" data-inputmask="\'mask\': \'d/m/y\'" name="revocationDetails[{{idx}}].replyDate" /> </td>'+
	'<td><input type="text" class="form-control issuedBy" name="revocationDetails[{{idx}}].issuedBy" maxlength="128" /></td>'+
	'<td><textarea class="form-control patternvalidation textarea-content" data-pattern="alphanumericspecialcharacters" maxlength="512" id="revocationDetails{{idx}}remarks" name="revocationDetails[{{idx}}].remarks" />'+
	'<span class="input-group-addon showModal" data-assign-to="revocationDetails{{idx}}remarks" data-header="Remarks"><span class="glyphicon glyphicon-pencil" style="cursor: pointer"></span></span></td>'+
	'<td><div class="files-upload-container" data-file-max-size="5" data-allowed-extenstion="doc,docx,xls,xlsx,rtf,pdf,txt,zip,jpeg,jpg,png,gif,tiff">'+
	'<div class="files-viewer"> <a href="javascript:void(0);" class="file-add" data-unlimited-files="true" data-file-input-name="revocationDetails[{{idx}}].files"><i class="fa fa-plus"></i></a></div></div></td>'+
	'</tr>';   
	
	$('#addRevokeDtlRow').click(function(){
		if(validateOnAdd()){
			var idx=$(tbody).find('tr').length;
			var sno=idx+1;
			//Add row
			var row={
			       'sno': sno,
			       'idx': idx
			   };
			addRowFromObject(row);
			$('.replyDate').datepicker({
		        format: "dd/mm/yyyy",
		        autoclose: true
		    });
			$('.requestDate').datepicker({
		        format: "dd/mm/yyyy",
		        autoclose: true
		    });
			patternvalidation();
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
	function validateOnAdd(){
		
		var isValid=true;
	    $('#permitRevocationDetails tbody tr').each(function(index){
	    	/*var natureOfRequest  = $(this).find('*[name$="natureOfRequest"]').val();
	    	var requestDate  = $(this).find('*[name$="requestDate"]').val();
		    var replyDate = $(this).find('*[name$="replyDate"]').val();
		    var issuedBy = $(this).find('*[name$="issuedBy"]').val();*/
		    var remarks = $(this).find('*[name$="remarks"]').val();
		    if(!remarks) { 
		    	bootbox.alert("Please enter remarks is mandatory before adding new row");
		    	isValid=false;
		    	return false;
		    } 
	    });
	   
	    return isValid;
	}
	
	$("#initiateRevokeSubmit").click(function(e){ 
		if ($('#permitRevocationInitiateForm').valid()) {
            bootbox
                .confirm({
                    message: 'Do you want to initiate for revocation for this application, are you sure ?',
                    buttons: {
                        'cancel': {
                            label: 'No',
                            className: 'btn-danger'
                        },
                        'confirm': {
                            label: 'Yes',
                            className: 'btn-primary'
                        }
                    },
                    callback: function (result) {
                        if (result) {
                            $('#permitRevocationInitiateForm').trigger('submit');
                        } else {
                            e.stopPropagation();
                            e.preventDefault();
                        }
                    }
                });
        } else {
            e.preventDefault();
        }
		return false;
	});
	
	$("#saveRevocation").click(function(e){ 
		var action = $(this).val();
		if ($('#permitRevocationApproveForm').valid()) {
            bootbox
                .confirm({
                    message: 'Do you want to the save application details, are you sure ?',
                    buttons: {
                        'cancel': {
                            label: 'No',
                            className: 'btn-danger'
                        },
                        'confirm': {
                            label: 'Yes',
                            className: 'btn-primary'
                        }
                    },
                    callback: function (result) {
                        if (result) {
                        	$('#workflowAction').val(action);
                            $('#permitRevocationApproveForm').trigger('submit');
                        } else {
                            e.stopPropagation();
                            e.preventDefault();
                        }
                    }
                });
        } else {
            e.preventDefault();
        }
		return false;
	});
	
	$("#approveRevocation").click(function(e){ 
		var action = $(this).val();
		if ($('#permitRevocationApproveForm').valid()) {
            bootbox
                .confirm({
                    message: 'Do you want to revoke the permit, are you sure ?',
                    buttons: {
                        'cancel': {
                            label: 'No',
                            className: 'btn-danger'
                        },
                        'confirm': {
                            label: 'Yes',
                            className: 'btn-primary'
                        }
                    },
                    callback: function (result) {
                        if (result) {
                        	$('#workflowAction').val(action);
                            $('#permitRevocationApproveForm').trigger('submit');
                        } else {
                            e.stopPropagation();
                            e.preventDefault();
                        }
                    }
                });
        } else {
            e.preventDefault();
        }
		return false;
	});
	
	$("#cancelRevocation").click(function(e){ 
		var action = $(this).val();
		if ($('#permitRevocationApproveForm').valid()) {
            bootbox
                .confirm({
                    message: 'Do you want to cancel the revocation, are you sure ?',
                    buttons: {
                        'cancel': {
                            label: 'No',
                            className: 'btn-danger'
                        },
                        'confirm': {
                            label: 'Yes',
                            className: 'btn-primary'
                        }
                    },
                    callback: function (result) {
                        if (result) {
                        	$('#workflowAction').val(action);
                            $('#permitRevocationApproveForm').trigger('submit');
                        } else {
                            e.stopPropagation();
                            e.preventDefault();
                        }
                    }
                });
        } else {
            e.preventDefault();
        }
		return false;
	});
});
