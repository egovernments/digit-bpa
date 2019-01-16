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

$(document).ready(function() {
	

   $('#appointmentDate').datepicker({
        	 format: 'dd/mm/yyyy',
    	   	 autoclose:true
   });
   
   $('#appointmentDate+span.input-group-addon').click(function(){
	   $('#appointmentDate').datepicker('show');
   });
        
  
	// To clear schedule time after enter schedule time if try to change schedule date
	$('#appointmentDate').on('changeDate', function() {
		$('#appointmentTime').datetimepicker('clear');
	});
	
	// For time picker initialization
	$('#appointmentTime').datetimepicker({
		format : 'LT',
		useCurrent : true,
		minDate : moment({ h : 10 }),
		maxDate : moment({ h : 17 })
	});

	// validate schedule appointment Date and Time
	$('#appointmentTime').on('change dp.change', function(e) {
		if (!$('#appointmentDate').val() && e.date) {
			$('#appointmentTime').datetimepicker('clear');
			bootbox.alert('Please select appointment date');
			return;
		}
		if (!e.date)
			return;
		validateDateAndTime();
	});

	// To validate on submit,if date and time entered manually
	$('#rescheduleSubmit').click(function(e) {
		validateDateAndTime();
	});
});

function validateDateAndTime() {
	if ($('#previoueappointmentDate') && $('#previoueappointmentDate').val() && $('#appointmentTime').datetimepicker('date')) {
		var prevAppointmentDateStr = $('#previoueappointmentDate').val() + " "
				+ $('#prevAppointmentTime').val();
		var previousAppointmentDateTime = moment(prevAppointmentDateStr,
				[ "YYYYY-MM-DD h:mm A" ]);
		var selectedAppointmentDateStr = $('#appointmentDate').val() + " "
				+ $('#appointmentTime').datetimepicker('date').format('h:mm A');
		var selectedAppointmentDateTime = moment(selectedAppointmentDateStr,
				[ "DD/MM/YYYY h:mm A" ]);
		if (previousAppointmentDateTime >= selectedAppointmentDateTime) {
			bootbox
					.alert('Re-Schedule Date and Time should be greater than the previous scheduled Date and Time');
			$('#appointmentTime').datetimepicker('clear');
		}
	}
}
