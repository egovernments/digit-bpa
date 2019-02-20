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
    console.log( "ready!" );
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
        	var boundaryData = JSON.parse(response);
        	var heirarchy = [];
        	var adminBoundaryType = [];
        	var revenueBoundaryType = [];
        	var locationBoundaryType = [];
        		for(var k in boundaryData) 
        		heirarchy.push(k);
        		for (var i = 0; i < heirarchy.length; i++) {
        			if (heirarchy[i].toUpperCase() == 'ADMINISTRATION') {
        				for(var k in boundaryData[heirarchy[i]]) {
    						if(boundaryData[heirarchy[i]][k]!=null && boundaryData[heirarchy[i]][k]!=''){
	        					$('#boundarydivision').append('<label class="col-sm-3 control-label text-right"> '+k+' : </label>');
	        					$('#boundarydivision').append('<div class="col-sm-3 add-margin"><select name="" data-first-option="false" id="'+k.replace(/ +/g, "")+heirarchy[i]+'"> <option value="">select</option></select></div>');
	    						$.each(boundaryData[heirarchy[i]][k], function(index, value) {
	    							$("#"+k.replace(/ +/g, "")+heirarchy[i]).append($('<option>').text(value.name).attr('value', value.id));
	    						});
	        		        	adminBoundaryType.push(k);
    						}
        				}
        			}
        			$('#boundarydivision').append('<br/>')
        			if (heirarchy[i].toUpperCase() == 'REVENUE') {
        				for(var k in boundaryData[heirarchy[i]]) {
    						if(boundaryData[heirarchy[i]][k]!=null && boundaryData[heirarchy[i]][k]!=''){
	        					$('#boundarydivision').append('<label class="col-sm-3 control-label text-right"> '+k+' : </label>');
	        					$('#boundarydivision').append('<div class="col-sm-3 add-margin"><select name="" data-first-option="false" id="'+k.replace(/ +/g, "")+heirarchy[i]+'"> <option value="">select</option></select></div>');
	    						$.each(boundaryData[heirarchy[i]][k], function(index, value) {
	    							$("#"+k.replace(/ +/g, "")+heirarchy[i]).append($('<option>').text(value.name).attr('value', value.id));
	    						});
	    						revenueBoundaryType.push(k);
    						}
        				}
        			}
        			$('#boundarydivision').append('<br/>')
        			if (heirarchy[i].toUpperCase() == 'LOCATION') {
        				for(var k in boundaryData[heirarchy[i]]) 
    						if(boundaryData[heirarchy[i]][k]!=null && boundaryData[heirarchy[i]][k]!=''){
	        					$('#boundarydivision').append('<label class="col-sm-3 control-label text-right"> '+k+' : </label>');
	        					$('#boundarydivision').append('<div class="col-sm-3 add-margin"><select name="" data-first-option="false" id="'+k.replace(/ +/g, "")+heirarchy[i]+'"> <option value="">select</option></select></div>');
	    						$.each(boundaryData[heirarchy[i]][k], function(index, value) {
	    							$("#"+k.replace(/ +/g, "")+heirarchy[i]).append($('<option>').text(value.name).attr('value', value.id));
	    						});
	    						locationBoundaryType.push(k);
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