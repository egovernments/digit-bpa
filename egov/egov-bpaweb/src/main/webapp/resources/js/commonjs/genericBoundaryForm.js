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
	var uniformMap = new Map(); 
	
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

	if (uniformBoundary != null && uniformBoundary != '') {
		for ( var i in uniformBoundary) {
			uniformMap.set(i, uniformBoundary[i]);
		}
	}

		orderArray.sort();
	for (var i = 0; i < orderArray.length; i++) {
		displayName = orderArray[i].split(':')[2];
		hierarchy = orderArray[i].split(':')[1];
		domOptionId = hierarchy+displayName.replace(/ +/g, "");
		var fromBoundary='';
		var toBoundary='';
		var result = uniformMap.get(hierarchy);
		if (result != null && result != '') {
			for (var k = 0; k < result.length; k++) {
				if (result[k]['fromBoundary'].split(':')[1] == displayName) {
					toBoundary = result[k]['toBoundary'];
					fromBoundary = result[k]['fromBoundary'];
					break;
				}
			}
		}

		if(boundaryData[orderArray[i]]['data']!=null && boundaryData[orderArray[i]]['data']!=''){
			$('#boundarydivision').append('<label class="col-sm-3 control-label text-right"> '+displayName+'<span class="mandatory"></span></label>');
			if(fromHierarchy.indexOf(displayName) != -1 && fromBoundary.split(':')[1] != displayName){
				$('#boundarydivision').append('<div class="col-sm-3 add-margin"><select name="" class="form-control" required="required" data-first-option="false" id="'+domOptionId+'" onChange="crossBoundaryNew(\''+domOptionId+'\', \''+fromHierarchy+'\', \''+toHierarchy+'\');"> <option value="">select</option></select></div>');
			} else if(fromHierarchy.indexOf(displayName) != -1 && fromBoundary.split(':')[1] == displayName){
				$('#boundarydivision').append('<div class="col-sm-3 add-margin"><select name="" class="form-control" required="required" data-first-option="false" id="'+domOptionId+'" onChange="crossAndUniformBoundaryNew(\''+domOptionId+'\', \''+hierarchy+'\', \''+toBoundary+'\',\''+fromHierarchy+'\', \''+toHierarchy+'\');"> <option value="">select</option></select></div>');
			} else if(fromBoundary !=null && fromBoundary!='' && fromBoundary.split(':')[1] == displayName){
				$('#boundarydivision').append('<div class="col-sm-3 add-margin"><select name="" class="form-control" required="required" data-first-option="false" id="'+domOptionId+'" onChange="uniformBoundaryNew(\''+domOptionId+'\', \''+hierarchy+'\', \''+toBoundary+'\');"> <option value="">select</option></select></div>');
			} else {
				$('#boundarydivision').append('<div class="col-sm-3 add-margin"><select name="" class="form-control" required="required" data-first-option="false" id="'+domOptionId+'"> <option value="">select</option></select></div>');
			}
			if(fromBoundary.split(':')[1] == displayName  && toHierarchy.indexOf(displayName) == -1){
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
			
			if(uniformMap!=null && uniformMap!=''){
				var result = uniformMap.get(hierarchy);
				if (result != null && result != '') 
					if(result.length>1){
						for (var k = 0; k < result.length; k++) {
							if (result[k]['toBoundary'].split(':')[1] == displayName) {
								$("#"+hierarchy+result[k]['toBoundary'].split(':')[1].replace(/ +/g, "")).empty()
								.append('<option value="">select</option></select>');
							}
						}
					}
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
	var uniformBoundary = genericBoundaryConfigData['uniformBoundary'];
	var domOptionId;
	var parentId = "";
	var uniformMap = new Map();
	
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
	
	if (uniformBoundary != null && uniformBoundary != '') {
		for ( var i in uniformBoundary) {
			uniformMap.set(i, uniformBoundary[i]);
			}
	}
	
	orderArray.sort();
	for (var i = 0; i < orderArray.length; i++) {
		displayName = orderArray[i].split(':')[2];
		hierarchy = orderArray[i].split(':')[1];
		domOptionId = hierarchy+displayName.replace(/ +/g, "");
		var result = uniformMap.get(hierarchy);
		if (result != null && result != '') {
			for (var k = 0; k < result.length; k++) {
				if (result[k]['fromBoundary'].includes(displayName)) {
					toBoundary = result[k]['toBoundary'];
					fromBoundary = result[k]['fromBoundary'];
					break;
				}
			}
		}
		if(boundaryData[orderArray[i]]['data']!=null && boundaryData[orderArray[i]]['data']!=''){
			$('#boundarydivision').append('<label class="col-sm-3 control-label text-right"> '+displayName+'<span class="mandatory"></span></label>');
			if(fromHierarchy.indexOf(displayName) != -1 && fromBoundary.split(':')[1] != displayName){
				$('#boundarydivision').append('<div class="col-sm-3 add-margin"><select name="" class="form-control" required="required" data-first-option="false" id="'+domOptionId+'" onChange="crossBoundaryNew(\''+domOptionId+'\', \''+fromHierarchy+'\', \''+toHierarchy+'\');"> <option value="">select</option></select></div>');
			}else if(fromHierarchy.indexOf(displayName) != -1 && fromBoundary.split(':')[1] == displayName){
				$('#boundarydivision').append('<div class="col-sm-3 add-margin"><select name="" class="form-control" required="required" data-first-option="false" id="'+domOptionId+'" onChange="crossAndUniformBoundaryNew(\''+domOptionId+'\', \''+hierarchy+'\', \''+toBoundary+'\',\''+fromHierarchy+'\', \''+toHierarchy+'\');"> <option value="">select</option></select></div>');
			}else if(fromBoundary !=null && fromBoundary!='' && fromBoundary.split(':')[1] == displayName){
				$('#boundarydivision').append('<div class="col-sm-3 add-margin"><select name="" class="form-control" required="required" data-first-option="false" id="'+domOptionId+'" onChange="uniformBoundaryModify(\''+domOptionId+'\', \''+hierarchy+'\', \''+toBoundary+'\');"> <option value="">select</option></select></div>');
			}else {
				$('#boundarydivision').append('<div class="col-sm-3 add-margin"><select name="" class="form-control" required="required" data-first-option="false" id="'+domOptionId+'"> <option value="">select</option></select></div>');
			}

			if(fromBoundary.split(':')[1] == displayName && toHierarchy.indexOf(displayName) == -1 && fromHierarchy.indexOf(displayName) == -1){
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
		if(revenueBoundaryId!=null && revenueBoundaryId!='')
			document.getElementById(revenueBoundaryId).value=selectedRevenueBoundary;
		if(adminBoundaryId!=null && adminBoundaryId!='')
			document.getElementById(adminBoundaryId).value=selectedAdminBoundary;
		if(locationBoundaryId!=null && locationBoundaryId!='')
			document.getElementById(locationBoundaryId).value=selectedLocationBoundary;
		
          setParentDetails(genericBoundaryConfigData['boundaryData'], selectedRevenueBoundary,uniformMap,'REVENUE',crosslinkConfig);
          setParentDetails(genericBoundaryConfigData['boundaryData'], selectedAdminBoundary,uniformMap,'ADMINISTRATION',crosslinkConfig);
          setParentDetails(genericBoundaryConfigData['boundaryData'], selectedLocationBoundary,uniformMap,'LOCATION',crosslinkConfig);
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

function uniformBoundaryNew(selectedBndryId, hierarchy, toHeirarchy){
	var selectedBoundary = $('#'+selectedBndryId).children(":selected").attr("value");
	$.ajax({
		url : "/bpa/boundary/ajax-child-boundary",
		type : "GET",
		data : {
			parent : hierarchy,
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
				$("#"+hierarchy+toHeirarchyArray[i].split(':')[1].replace(/ +/g, "")).empty()
				.append('<option value="">select</option></select>');
				$.each(crossBoundaryData[toHeirarchyArray[i].split(':')[0]], function(index, value) {
					$($("#"+hierarchy+toHeirarchyArray[i].split(':')[1].replace(/ +/g, ""))).append($('<option>').text(value.name).attr('value', value.id));
				});
		    }
		},
		error : function(response) {
			console.log("Error occurred while retrieving cross boundary!!!"
					+ response);
		}
	});
}

function crossAndUniformBoundaryNew(selectedBndryId, hierarchy, toHeirarchy,fromHierarchy,toHierarchy){
	uniformBoundaryNew(selectedBndryId,hierarchy,toHeirarchy);
	crossBoundaryNew(selectedBndryId,fromHierarchy,toHierarchy);
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

function uniformBoundaryModify(selectedBndryId, heirarchy, toHeirarchy) {
	var selectedBoundary = $('#'+selectedBndryId).children(":selected").attr("value");
	if(selectedBoundary!=null && selectedBoundary!=''){
	$.ajax({
		url : "/bpa/boundary/ajax-child-boundary",
		type : "GET",
		data : {
			parent : heirarchy,
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
				$("#"+heirarchy+toHeirarchyArray[i].split(':')[1].replace(/ +/g, "")).empty()
				.append('<option value="">select</option></select>');
				$.each(crossBoundaryData[toHeirarchyArray[i].split(':')[0]], function(index, value) {
					$($("#"+heirarchy+toHeirarchyArray[i].split(':')[1].replace(/ +/g, ""))).append($('<option>').text(value.name).attr('value', value.id));
				});
		    }
		},
		error : function(response) {
			console.log("Error occurred while retrieving cross boundary!!!"
					+ response);
		}
	});
	}
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

function findDomIdByBoundary(boundaryData, bndryId){
	var dataJson;
	var boundary;
	for(var k in boundaryData) {
		dataJson = boundaryData[k].data;
		for (var i = 0;  i < dataJson.length; i++) {
			boundary = dataJson[i];
			if(boundary.id==bndryId){
				return k.split(":")[1]+k.split(":")[2].replace(/ +/g, "");
			}
		}
	}
}

function parentDetails(selectedBndryId, heirarchy, toHeirarchy) {
	if(selectedBndryId!=null && selectedBndryId!=''){
	$.ajax({
		url : "/bpa/boundary/ajax-child-boundary",
		type : "GET",
		data : {
			parent : heirarchy,
			child : toHeirarchy,
			selectedParent : selectedBndryId
		},
		cache : false,
		async : false,
		contentType : 'application/json; charset=utf-8',
		success : function(response) {
			var crossBoundaryData = JSON.parse(response);
			var toHeirarchyArray = toHeirarchy.split(',');
			for (var i = 0; i < toHeirarchyArray.length; i++) {
				$("#"+heirarchy+toHeirarchyArray[i].split(':')[1].replace(/ +/g, "")).empty()
				.append('<option value="">select</option></select>');
				$.each(crossBoundaryData[toHeirarchyArray[i].split(':')[0]], function(index, value) {
					$($("#"+heirarchy+toHeirarchyArray[i].split(':')[1].replace(/ +/g, ""))).append($('<option>').text(value.name).attr('value', value.id));
				});
		    }
		},
		error : function(response) {
			console.log("Error occurred while retrieving cross boundary!!!"
					+ response);
		}
	});
	}
}

function setParentDetails(boundaryData,revenueBoundaryId,uniformMap,hierarchy,crosslinkConfig){
	var result = uniformMap.get(hierarchy);
	var fromHierarchy=crosslinkConfig['fromHierarchy'];
	var toHierarchy=crosslinkConfig['toHierarchy'];
	var selectedRevenue = revenueBoundaryId;
	
	if(result !=undefined && result!=null && result!=''){
	if(result.length == 1){
	    var parent = findParentById(boundaryData,revenueBoundaryId);
	    parentDetails(parent,hierarchy,result[0]['toBoundary']);
	    if(crosslinkConfig!=null && crosslinkConfig!='')
	        crossBoundaryModify(revenueBoundaryId, fromHierarchy, toHierarchy);
		document.getElementById(hierarchy+(result[0]['toBoundary'].split(':')[1].replace(/ +/g, ""))).value=revenueBoundaryId;
		var domId = findDomIdByParent(boundaryData, parent);
		document.getElementById(domId).value=parent;
	}else {
      for(var k=result.length-1;k>=0;k--){
    	 var parent = findParentById(boundaryData,revenueBoundaryId);
    	 parentDetails(parent,hierarchy,result[k]['toBoundary']);
    	 if(fromHierarchy.indexOf(result[k]['toBoundary'].split(':')[1]) == -1){
    		if(crosslinkConfig!=null && crosslinkConfig!='')
    			crossBoundaryModify(parent, fromHierarchy, toHierarchy);
    	 }
		var domId = findDomIdByParent(boundaryData, parent);
		document.getElementById(domId).value=parent;
    	 revenueBoundaryId = parent;
      }
      if(result.length == 2){
      var dom = findDomIdByBoundary(boundaryData, selectedRevenue);
      document.getElementById(dom).value=selectedRevenue;
      var parent = findParentById(boundaryData,selectedRevenue);
      if(parent!=null && parent!=''){
    	var domId = findDomIdByParent(boundaryData, parent);
		document.getElementById(domId).value=parent;
      }
      }
	}
	} else if(result == undefined){
		var domId = findDomIdByParent(boundaryData, revenueBoundaryId);
		document.getElementById(domId).value=revenueBoundaryId;
		return;
	}
}
