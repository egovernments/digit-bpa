/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2019>  eGovernments Foundation
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
	
	var table;
	var tbody;
	var row = '<tr>'+
	'<td class="text-center"><span class="applcntSerialNo text-center">{{sno}}</span></td>'+
	'<td><input type="etxt" name="coApplicants[{{idx}}].coApplicant.name" data-first-option="false" class="form-control name toggle-mandatory duplicate-clear" maxlength="99" /></td>'+
	'<td><input type="text" class="form-control patternvalidation mobileNumber duplicate-clear" name="coApplicants[{{idx}}].coApplicant.mobileNumber" data-pattern="number" minlength="10" maxlength="10" /></td>'+
	'<td><input type="text" class="form-control patternvalidation emailId duplicate-clear" name="coApplicants[{{idx}}].coApplicant.emailId" data-pattern="string" maxlength="120" /><span></span><form:errors path="coApplicants[{{idx}}].coApplicant.emailId" cssClass="error-msg" /></td>'+
	'<td><select name="coApplicants[{{idx}}].coApplicant.gender" data-first-option="false" class="form-control gender"> <option value="">Select</option></select></td>'+
	'<td class="text-center"><a href="javascript:void(0);" class="btn-sm btn-danger" id="deleteApplicantRow" ><i class="fa fa-trash"></i></a></td>'+
	'</tr>';
	
	$(document).on('click','.addApplicantRow', function() {
		tbody = $('#coApplicantDetails').children('tbody');
		table = tbody.length ? tbody : $('#coApplicantDetails');
		if(validateCoApplicantOnAdd()) {
			var idx=$(tbody).find('tr').length;
			var sno=idx+1;
			//Add row
			var row = {
			       'sno': sno,
			       'idx': idx
			   };
			addRowFromObject(row);
			patternvalidation();
			generateSno();
			loadGenders("coApplicants["+idx+"].coApplicant.gender");
		}
	});
	
	function addRowFromObject(rowJsonObj) {
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
	
	function loadGenders(selectBoxName){
		var genderArr = [];
		$('#gender option').each(function() { 
			if($(this).attr('value'))
				genderArr.push( $(this).attr('value') );
		});
		$.each(genderArr, function(index, gender) {
			$('select[name="'+selectBoxName+'"]').append($('<option>').val(gender).text(gender));
		});
		}
	
	$(document).on('blur','.name', function() {
		var rowObj = $(this).closest('tr');
		validateUniqueDetails(rowObj.index(),$(rowObj).find('.name').val());
	});

    var deletedId = [];
    $(document).on('click',"#deleteApplicantRow",function () {
        var rowIndex = $(this).closest('td').parent()[0].sectionRowIndex;
        if($(this).data('record-id'))
            deletedId.push($(this).data('record-id'));

        $('#deletedCoApplicantIds').val(deletedId);
        $(this).closest('tr').remove();

        generateSno();
        $("#coApplicantDetails tbody tr").each(function() {
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
    });
});

function generateSno() {
	var idx=1;
	$('#coApplicantDetails tbody tr').find('.applcntSerialNo').each(function(){
		$(this).text(idx);
		idx++;
	});
}

function validateCoApplicantOnAdd() {
	var isValid=true;
    $('#coApplicantDetails tbody tr').each(function(index){
    	var name  = $(this).find('*[name$="name"]').val();
	    if(!name) { 
	    	bootbox.alert('Please enter the name of the co-applicant before enter adding new, name cannot be empty');
	    	isValid=false;
	    	return false;
	    } 
    });
   
    return isValid;
}



