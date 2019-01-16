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
 
jQuery(document).ready(function($) {
	
	// to refresh inbox item after forwarding
	$(window).unload(function(){
        if (typeof inboxloadmethod === 'function')
			parent.window.opener.inboxloadmethod();
	});
	
	$("#buttonCreateSubmit").click(function(e){ 
		validateDate();
		return true;
	});

    var validator=$("#lettertoPartyReplyform").validate({
        highlight: function(element, errorClass) {
            $(element).fadeOut(function() {
                $(element).fadeIn();
            });
        }
    });

    $("#btnLPReply").click(function(e){
        if ($('#lettertoPartyReplyform').valid() && validateUploadFilesMandatory()) {
            return true;
        } else {
            $errorInput=undefined;

            $.each(validator.invalidElements(), function(index, elem){

                if(!$(elem).is(":visible") && !$(elem).val() && index==0
                    && $(elem).closest('div').find('.bootstrap-tagsinput').length > 0){
                    $errorInput=$(elem);
                    console.log("Error Elements", $errorInput);
                }

                if(!$(elem).is(":visible") && !$(elem).closest('div.panel-body').is(":visible")){
                    $(elem).closest('div.panel-body').show();
                    console.log("elem", $(elem));
                }
            });

            if($errorInput)
                $errorInput.tagsinput('focus');
            validator.focusInvalid();

            return false;
		}
    });

    $('#sentDate').on('changeDate', function(e) {
		validateDate();
	});
	$('#replyDate').on('changeDate', function(e) {
		validateReplyDate();
	});
	
	// multi-select without holding ctrl key
	$("select.tick-indicator").mousedown(function(e){
	    e.preventDefault();
	    
		var select = this;
	    var scroll = select.scrollTop;
	    
	    e.target.selected = !e.target.selected;
	    
	    $(this).trigger('change');
	    
	    setTimeout(function(){select.scrollTop = scroll;}, 0);
	    
	    $(select).focus();

	}).mousemove(function(e){e.preventDefault()});
	
});

	function getUrlToPring() {
		var url = '/bpa/lettertoparty/lettertopartyprint/lp?pathVar='+$('#lettertoParty').val();
		$('#lettertoPartyform').attr('method', 'get');
		$('#lettertoPartyform').attr('action', url);
		window.location = url;
	}
	
	function validateReplyDate() {   
		if ($('#sentDate').val() && $('#replyDate').val()) {
			var sentdateStr = $('#sentDate').val();
			var sentdateDateTime = moment(sentdateStr,["DD/MM/YYYY"]);
			var replyDateStr = $('#replyDate').val();
			var replyDateTime = moment(replyDateStr,["DD/MM/YYYY"]);
			if (sentdateDateTime > replyDateTime) {
				bootbox.alert('LP Reply Date should be greater than the Letter to party sent Date');
				$('#replyDate').val('');
			}
		} else {
			bootbox.alert('Please update LP sent Date before updating LP reply details.');
			$('#replyDate').val('');
		}
	}	
	
	function validateDate() {   
		if ($('#sentDate').val() && $('#letterDate').val()) {
			var sentdateStr = $('#sentDate').val();
			var sentdateDateTime = moment(sentdateStr,["DD/MM/YYYY"]);
			var letterDateStr = $('#letterDate').val();
			var letterDateTime = moment(letterDateStr,["DD/MM/YYYY"]);
			if (letterDateTime > sentdateDateTime) {
				bootbox.alert('Letter to party sent Date  should be greater than the LP Date');
				$('#sentDate').val('');
			}
		}
	}

