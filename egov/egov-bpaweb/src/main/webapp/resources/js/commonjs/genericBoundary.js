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
$(function() {
    loadBoundary();
});

function loadBoundary() {
	var isExist = false;
	$.ajax({
        url: "/bpa/boundary/ajax-boundary-configuration",
        type: "GET",
        cache : false,
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function (response) {
        	isExist = true;
        	var responseData = JSON.parse(response);
        	var orderArray = [];
        	var tempArray = [];
        	var orderMap = new Map(); 
        	var fromHierarchy;
        	var toHierarchy;
        	var displayName;
        	var hierarchy;
        	var hierarchyNumber;
        	var boundaryType;
        	var crosslinkConfig=responseData['crossBoundary'];
        	var boundaryData=responseData['boundaryData'];
        	if(crosslinkConfig!=null && crosslinkConfig!=''){
	        	fromHierarchy=crosslinkConfig['fromHierarchy'];
	        	toHierarchy=crosslinkConfig['toHierarchy'];
        	}
        	for(var k in boundaryData) {
        		orderArray.push(k);
        		hierarchyNumber=k.split('-')[0];
        		boundaryType=k.split('-')[1];
        		
        		if(orderMap.get(hierarchyNumber) == null){
        			orderMap.set(hierarchyNumber, [boundaryType]);
        		} else {
        			tempArray = orderMap.get(hierarchyNumber);
        			tempArray.push(boundaryType);
        			orderMap.set(hierarchyNumber, tempArray);
        		}
        	}
        	orderArray.sort();
        	for (var i = 0; i < orderArray.length; i++) {
        		displayName = boundaryData[orderArray[i]]['displayName'];
        		hierarchy = boundaryData[orderArray[i]]['hierarchy'];
				if(boundaryData[orderArray[i]]['data']!=null && boundaryData[orderArray[i]]['data']!=''){
					$('#boundarydivision').append('<label class="col-sm-3 control-label text-right"> '+displayName+'<span class="mandatory"></span></label>');
					if(fromHierarchy.indexOf(displayName) != -1){
						$('#boundarydivision').append('<div class="col-sm-3 add-margin"><select name="" class="form-control" data-first-option="false" id="'+hierarchy+displayName.replace(/ +/g, "")+'" onChange="crossBoundary(\''+hierarchy+displayName.replace(/ +/g, "")+'\', \''+fromHierarchy+'\', \''+toHierarchy+'\');"> <option value="0">select</option></select></div>');
					}else {
						$('#boundarydivision').append('<div class="col-sm-3 add-margin"><select name="" class="form-control" data-first-option="false" id="'+hierarchy+displayName.replace(/ +/g, "")+'"> <option value="0">select</option></select></div>');
					}
					if(toHierarchy.indexOf(displayName) == -1){
						$.each(boundaryData[orderArray[i]]['data'], function(index, value) {
							$("#"+hierarchy+displayName.replace(/ +/g, "")).append($('<option>').text(value.name).attr('value', value.id));
						});
					}
					if(Math.max.apply(null, orderMap.get(orderArray[i].split('-')[0]))==orderArray[i].split('-')[1]){
						if(hierarchy=='ADMINISTRATION')
							document.getElementById(hierarchy+displayName.replace(/ +/g, "")).name="adminBoundary";
						if(hierarchy=='REVENUE')
							document.getElementById(hierarchy+displayName.replace(/ +/g, "")).name="revenueBoundary";
						if(hierarchy=='LOCATION')
							document.getElementById(hierarchy+displayName.replace(/ +/g, "")).name="locationBoundary";
					}
				}
        	}
        },
        error: function (response) {
            console.log("Error occurred while retrieving boundaries!!!"+response);
        }
    });
	return isExist;
}

function crossBoundary(selectedBndryId, fromHeirarchy, toHeirarchy) {
	var selectedBoundary = $('#'+selectedBndryId).children(":selected").attr("value");
	$.ajax({
		url : "/bpa/boundary/ajax-cross-boundary",
		type : "GET",
		data : {
			parent : fromHeirarchy,
			child : toHeirarchy,
			selectedParent : selectedBoundary
		},
		cache : false,
		async : false,
		contentType : 'application/json; charset=utf-8',
		success : function(response) {
			var crossBoundaryData = JSON.parse(response);
			var toHeirarchyArray = toHeirarchy.split(',');
			for (var i = 0; i < toHeirarchyArray.length; i++) {
				$("#"+toHeirarchyArray[i].split(':')[0]+toHeirarchyArray[i].split(':')[2].replace(/ +/g, "")).empty()
				.append('<option value="0">select</option></select>');
				$.each(crossBoundaryData[toHeirarchyArray[i].split(':')[1]], function(index, value) {
					$($("#"+toHeirarchyArray[i].split(':')[0]+toHeirarchyArray[i].split(':')[2].replace(/ +/g, ""))).append($('<option>').text(value.name).attr('value', value.id));
				});
		    }
		},
		error : function(response) {
			console.log("Error occurred while retrieving cross boundary!!!"
					+ response);
		}
	});
}