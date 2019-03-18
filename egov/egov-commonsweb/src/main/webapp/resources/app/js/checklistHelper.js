/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2017  eGovernments Foundation
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
 *            Further, all user interfaces, including but not limited to citizen facing interfaces,
 *            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *            derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *            For any further queries on attribution, including queries on brand guidelines,
 *            please contact contact@egovernments.org
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
 *
 */
$(document).ready( function () {
	
var tbody = $('#checklist').children('tbody');
	var table = tbody.length ? tbody : $('#checklist');
	var row = '<tr>'+
	'<td ><input type="text" class="form-control table-input  patternvalidation description" name="checklistTemp[{{idx}}].description" id="checklistTemp[{{idx}}]description" required="required" maxlength="256" /></td>'+
	'<td class="text-center"><a href="javascript:void(0);" class="btn-sm btn-danger" id="deleteChecklistRow" ><i class="fa fa-trash"></i></a></td>'+
	'</tr>';
	//adding row dynamically
	$('#addrow').click(function(){
		var idx=$(tbody).find('tr').length;
		var sno=idx+1;
		//Add row
		var row={
		       'sno': sno,
		       'idx': idx
		   };
		 var rowCount = $('#checklist tbody tr').length;
         var valid = true;
         //validate all rows before adding new row
         $('#checklist tbody tr').each(function (idx, value) {
             $('#checklist tbody tr:eq(' + idx + ') td input[type="text"]').each(function (i, v) {
                 if (!$.trim($(v).val())) {
                     valid = false;
                     bootbox.alert("Enter Description for existing rows!", function () {
                         $(v).focus();
                     });
                     return false;
                 }
             });
         });
         if(valid)
			addRowFromObject(row);
	});
	

		function addRowFromObject(rowJsonObj) {
			table.append(row.compose(rowJsonObj));
		}
		
		var updaterow = '<tr>'+
		'<td ><input type="text" class="form-control table-input  patternvalidation description" name="checklistTemp[{{idx}}].description" id="checklistTemp[{{idx}}]description" required="required" maxlength="256" /></td>'+
		'</tr>';	
		
		$('#updaterow').click(function(){
			var idx=$(tbody).find('tr').length;
			var sno=idx+1;
			//Add row
			var row={
			       'sno': sno,
			       'idx': idx
			   };
			 var rowCount = $('#checklist tbody tr').length;
	         var valid = true;
	         //validate all rows before adding new row
	         $('#checklist tbody tr').each(function (idx, value) {
	             $('#checklist tbody tr:eq(' + idx + ') td input[type="text"]').each(function (i, v) {
	                 if (!$.trim($(v).val())) {
	                     valid = false;
	                     bootbox.alert("Enter Description for existing rows!", function () {
	                         $(v).focus();
	                     });
	                     return false;
	                 }
	             });
	         });
	         if(valid)
				addUpdateRowFromObject(row);
		});
		
		function addUpdateRowFromObject(rowJsonObj) {
			table.append(updaterow.compose(rowJsonObj));
		}
		
	
	String.prototype.compose = (function (){
		   var re = /\{{(.+?)\}}/g;
		   return function (o){
		       return this.replace(re, function (_, k){
		           return typeof o[k] != 'undefined' ? o[k] : '';
		       });
		   }
	}());
	
		//form submit
		$('#buttonSubmit').click(function(e) {
			if ($('form').valid()) {
				console.log('submitted')
			} else {
				e.preventDefault();
			}
		});
		
		//delete row
		var deletedId = [];
		$(document).on('click',"#deleteChecklistRow",function (){
			var id=$(tbody).find('tr').length;
		        var idx = $(this).closest('tr').index();
		        var obj = $(this);
		        if (id == 1) {
		            bootbox.alert('Cannot delete first row!');
		        } else{
		        	
		    var rowIndex = $(this).closest('td').parent()[0].sectionRowIndex;
		    if($(this).data('record-id'))
		    deletedId.push($(this).data('record-id'));
		    
		    $('#checklistIds').val(deletedId);
			$(this).closest('tr').remove();	
			
			  $("#checklist tbody tr").each(function() {
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
		   }
		});	
		});
			
