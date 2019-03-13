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
    var boundaryIds = script_tag.getAttribute("data-search");
	var applicationType = boundaryIds.split(':')[0];
	var selectedAdminBoundary = boundaryIds.split(':')[1];
	var selectedRevenueBoundary = boundaryIds.split(':')[2];
	var selectedLocationBoundary = boundaryIds.split(':')[3];
	var boundaryData = loadBoundary();
	if(boundaryData!=null && boundaryData!=''){
		if(applicationType=='new'){
			paintBoundaryForNew(boundaryData);
		}
		if(applicationType=='modify'){
			paintBoundaryForModify(boundaryData, selectedAdminBoundary, selectedRevenueBoundary, selectedLocationBoundary);
		}
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
		if(boundaryData[orderArray[i]]['data']!=null && boundaryData[orderArray[i]]['data']!=''){
			$('#boundarydivision').append('<label class="col-sm-3 control-label text-right"> '+displayName+'<span class="mandatory"></span></label>');
			if(fromHierarchy.indexOf(displayName) != -1){
				$('#boundarydivision').append('<div class="col-sm-3 add-margin"><select name="" class="form-control" required="required" data-first-option="false" id="'+domOptionId+'" onChange="crossBoundaryNew(\''+domOptionId+'\', \''+fromHierarchy+'\', \''+toHierarchy+'\');"> <option value="">select</option></select></div>');
			}else {
				$('#boundarydivision').append('<div class="col-sm-3 add-margin"><select name="" class="form-control" required="required" data-first-option="false" id="'+domOptionId+'"> <option value="">select</option></select></div>');
			}
			if(toHierarchy.indexOf(displayName) == -1){
				$.each(boundaryData[orderArray[i]]['data'], function(index, value) {
					$("#"+domOptionId).append($('<option>').text(value.name).attr('value', value.id));
				});
			}
			if(Math.max.apply(null, orderMap.get(orderArray[i].split('-')[0]))==orderArray[i].split(':')[0].split('-')[1]){
				if(hierarchy=='ADMINISTRATION')
					document.getElementById(domOptionId).name="adminBoundary";
				if(hierarchy=='REVENUE')
					document.getElementById(domOptionId).name="revenueBoundary";
				if(hierarchy=='LOCATION')
					document.getElementById(domOptionId).name="locationBoundary";
			}
		}
	}
}

function paintBoundaryForModify(genericBoundaryConfigData, selectedAdminBoundary, selectedRevenueBoundary, selectedLocationBoundary) {
	var orderArray = [];
	var tempArray = [];
	var orderMap = new Map(); 
	var fromHierarchy='';
	var toHierarchy='';
	var displayName;
	var hierarchy;
	var hierarchyNumber;
	var boundaryType;
	var revenueBoundaryId;
	var adminBoundaryId;
	var locationBoundaryId;
	var tempId = [];
	var crosslinkConfig=genericBoundaryConfigData['crossBoundary'];
	var boundaryData=genericBoundaryConfigData['boundaryData'];
	var domOptionId;
	var parentId = "";
	
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
		if(boundaryData[orderArray[i]]['data']!=null && boundaryData[orderArray[i]]['data']!=''){
			$('#boundarydivision').append('<label class="col-sm-3 control-label text-right"> '+displayName+'<span class="mandatory"></span></label>');
			if(fromHierarchy.indexOf(displayName) != -1){
				$('#boundarydivision').append('<div class="col-sm-3 add-margin"><select name="" class="form-control" required="required" data-first-option="false" id="'+domOptionId+'" onChange="crossBoundaryNew(\''+domOptionId+'\', \''+fromHierarchy+'\', \''+toHierarchy+'\');"> <option value="">select</option></select></div>');
			}else {
				$('#boundarydivision').append('<div class="col-sm-3 add-margin"><select name="" class="form-control" required="required" data-first-option="false" id="'+domOptionId+'"> <option value="">select</option></select></div>');
			}
			if(toHierarchy.indexOf(displayName) == -1){
				$.each(boundaryData[orderArray[i]]['data'], function(index, value) {
					$("#"+domOptionId).append($('<option>').text(value.name).attr('value', value.id));
				});
			}
			if(Math.max.apply(null, orderMap.get(orderArray[i].split('-')[0]))==orderArray[i].split(':')[0].split('-')[1]){
				if(hierarchy=='ADMINISTRATION'){
					document.getElementById(domOptionId).name="adminBoundary";
					adminBoundaryId=domOptionId;
				}
				if(hierarchy=='REVENUE'){
					document.getElementById(domOptionId).name="revenueBoundary";
					revenueBoundaryId=domOptionId;
				}
				if(hierarchy=='LOCATION'){
					document.getElementById(domOptionId).name="locationBoundary";
					locationBoundaryId=domOptionId;
				}
				}
			}
    	}
		if(crosslinkConfig!=null && crosslinkConfig!=''){
			crossBoundaryModify(selectedRevenueBoundary, fromHierarchy, toHierarchy);
		}
		
		if(revenueBoundaryId!=null && revenueBoundaryId!='')
			document.getElementById(revenueBoundaryId).value=selectedRevenueBoundary;
		if(adminBoundaryId!=null && adminBoundaryId!='')
			document.getElementById(adminBoundaryId).value=selectedAdminBoundary;
		if(locationBoundaryId!=null && locationBoundaryId!='')
			document.getElementById(locationBoundaryId).value=selectedLocationBoundary;
		
		while(parentId==''){
			parentId = findParentById(genericBoundaryConfigData['boundaryData'], selectedRevenueBoundary);
			var domId = findDomIdByParent(genericBoundaryConfigData['boundaryData'], parentId);
			document.getElementById(domId).value=parentId;
		}
}

function crossBoundaryNew(selectedBndryId, fromHeirarchy, toHeirarchy) {
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
				.append('<option value="">select</option></select>');
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

function crossBoundaryModify(selectedBoundary, fromHeirarchy, toHeirarchy) {
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
				.append('<option value="">select</option></select>');
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

function findParentById(boundaryData, bndryId){
	var dataJson;
	var boundary;
	for(var k in boundaryData) {
		dataJson = boundaryData[k].data;
		for (var i = 0;  i < dataJson.length; i++) {
			boundary = dataJson[i];
			if(boundary.id==bndryId){
				return boundary.parent;
			}
		}
	}
}

function findDomIdByParent(boundaryData, bndryParentId){
	var dataJson;
	var boundary;
	for(var k in boundaryData) {
		dataJson = boundaryData[k].data;
		for (var i = 0;  i < dataJson.length; i++) {
			boundary = dataJson[i];
			if(boundary.id==bndryParentId){
				return k.split(":")[1]+k.split(":")[2].replace(/ +/g, "");
			}
		}
	}
}
