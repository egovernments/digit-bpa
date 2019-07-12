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
	var script_tag = document.getElementById('genericBoundry')
    var boundaryIds = script_tag.getAttribute("data-view");
	var selectedAdminBoundary = boundaryIds.split(':')[0];
	var selectedRevenueBoundary = boundaryIds.split(':')[1];
	var selectedLocationBoundary = boundaryIds.split(':')[2];
	var boundaryData = loadBoundary();
	paintBoundaryForView(boundaryData, selectedAdminBoundary, selectedRevenueBoundary, selectedLocationBoundary);
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

function paintBoundaryForView(genericBoundaryConfigData, selectedAdminBoundary, selectedRevenueBoundary, selectedLocationBoundary) {
	var orderArray = [];
	var tempArray = [];
	var orderMap = new Map(); 
	var displayName;
	var hierarchy;
	var hierarchyNumber;
	var boundaryType;
	var tempId = [];
	var domOptionId;
	var parentId = "";
	
	var boundaryData=genericBoundaryConfigData['boundaryData'];
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
		if(boundaryData[orderArray[i]]['data']!=null && boundaryData[orderArray[i]]['data']!=''){
			$('#boundarydivision').append('<div class="col-sm-3 add-margin"> '+displayName+'</div>');
			$('#boundarydivision').append('<div class="col-sm-3 add-margin view-content" id="'+domOptionId+'"></div>');
		}
	//	if(Math.max.apply(null, orderMap.get(orderArray[i].split('-')[0]))==orderArray[i].split(':')[0].split('-')[1]){
			if(hierarchy=='ADMIN')
				document.getElementById(domOptionId).innerHTML=findNameById(boundaryData, selectedAdminBoundary);
			if(hierarchy=='REVENUE')
				document.getElementById(domOptionId).innerHTML=findNameById(boundaryData, selectedRevenueBoundary);
			if(hierarchy=='LOCATION')
				document.getElementById(domOptionId).innerHTML=findNameById(boundaryData, selectedLocationBoundary);
		//}
	}
	/*while(parentId=''){
		parentId = findParentById(genericBoundaryConfigData['boundaryData'], selectedRevenueBoundary);
		var domId = findDomIdByParent(genericBoundaryConfigData['boundaryData'], parentId);
		if(parentId!=null && domId!=null)
		document.getElementById(domId).innerHTML=findNameById(boundaryData, parentId);
	}*/
}

function findNameById(boundaryData, bndryId){
	var dataJson;
	var boundary;
	console.log("findNameById boundaryData"+boundaryData+"----"+bndryId);
	for(var k in boundaryData) {
		dataJson = boundaryData[k].data;
		for (var i = 0;  i < dataJson.length; i++) {
			boundary = dataJson[i];
			if(boundary.code==bndryId){
				return boundary.name;
			}
		}
	}
}

/*function findDomIdByParent(boundaryData, bndryParentId){
	var dataJson;
	var boundary;
	console.log("findDomIdByParent boundaryData"+boundaryData+"----"+bndryParentId);
	for(var k in boundaryData) {
		dataJson = boundaryData[k].data;
		for (var i = 0;  i < dataJson.length; i++) {
			boundary = dataJson[i];
			if(boundary.code==bndryParentId){
				return k.split(":")[1]+k.split(":")[2].replace(/ +/g, "");
			}
		}
	}
}*/

function findParentById(boundaryData, bndryId){
	var dataJson;
	var boundary;
	console.log("findParentById boundaryData"+boundaryData+"----"+bndryId);
	for(var k in boundaryData) {
		dataJson = boundaryData[k].data;
		for (var i = 0;  i < dataJson.length; i++) {
			boundary = dataJson[i];
			if(boundary.code==bndryId){
				return boundary.parent;
			}
		}
	}
}

function findDomIdByParent(boundaryData, bndryParentId){
	var dataJson;
	var boundary;
	console.log("findDomIdByParent boundaryData"+boundaryData.len+"----"+bndryParentId);
	for(var k in boundaryData) {
		dataJson = boundaryData[k].data;
		for (var i = 0;  i < dataJson.length; i++) {
			boundary = dataJson[i];
			if(boundary.code==bndryParentId){
				return k.split(":")[1]+k.split(":")[2].replace(/ +/g, "");
			}
		}
	}
}