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
	var boundaryData = loadBoundary();
	if(boundaryData!=null && boundaryData!=''){
			paintBoundaryForNew(boundaryData);
	}
});

function loadBoundary() {
	var responseData;
	$.ajax({
        url: "/bpa/boundary/ajax-boundary-configuration",
        type: "GET",
        cache : false,
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function (response) {
        	responseData = JSON.parse(response);
        },
        error: function (response) {
            console.log("Error occurred while retrieving boundaries!!!"+response);
        }
    });
	return responseData;
}

function paintBoundaryForNew(genericBoundaryConfigData) {
	var orderArray = [];
	var tempArray = [];
	var orderMap = new Map(); 
	var fromHierarchy='';
	var toHierarchy='';
	var displayName;
	var hierarchy;
	var hierarchyNumber;
	var boundaryType;
	var tempId = [];
	var domOptionId;
	
	var crosslinkConfig=genericBoundaryConfigData['crossBoundary'];
	var boundaryData=genericBoundaryConfigData['boundaryData'];
	var uniformBoundary = genericBoundaryConfigData['uniformBoundary'];
	if(crosslinkConfig!=null && crosslinkConfig!=''){
    	fromHierarchy=crosslinkConfig['fromHierarchy'];
    	toHierarchy=crosslinkConfig['toHierarchy'];
	}
	
	for(var k in boundaryData) {
		tempId = k.split(':');
		orderArray.push(k);
		hierarchyNumber=tempId[0].split('-')[0];
		boundaryType=tempId[0].split('-')[1];
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
		displayName = orderArray[i].split(':')[2];
		hierarchy = orderArray[i].split(':')[1];
		domOptionId = hierarchy+displayName.replace(/ +/g, "");

		if(Math.max.apply(null, orderMap.get(orderArray[i].split('-')[0]))==orderArray[i].split(':')[0].split('-')[1] && 
				boundaryData[orderArray[i]]['data']!=null && boundaryData[orderArray[i]]['data']!=''){
			if(isEven(i))
				$('#boundarysection').append('<label class="col-sm-2 control-label text-right"> '+displayName+'</label>');
			 else 
			    $('#boundarysection').append('<label class="col-sm-3 control-label text-right"> '+displayName+'</label>');
			
				$('#boundarysection').append('<div class="col-sm-3 add-margin"><select name="" class="form-control" data-first-option="false" id="'+domOptionId+'"> <option value="">select</option></select></div>');

				$.each(boundaryData[orderArray[i]]['data'], function(index, value) {
				 $("#"+domOptionId).append($('<option>').text(value.name).attr('value', value.id));
		    	});
			
				if(hierarchy=='ADMINISTRATION')
					document.getElementById(domOptionId).name="adminBoundary";
				if(hierarchy=='REVENUE')
					document.getElementById(domOptionId).name="revenueBoundary";
				if(hierarchy=='LOCATION')
					document.getElementById(domOptionId).name="locationBoundary";
		}
	}
}

function isEven(n) {
	return n % 2 == 0;
}
