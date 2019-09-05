jQuery('#btnsearch').click(function(e) {

	callAjaxSearch();
});

function getFormData($form) {
	var unindexed_array = $form.serializeArray();
	var indexed_array = {};

	$.map(unindexed_array, function(n, i) {
		indexed_array[n['name']] = n['value'];
	});

	return indexed_array;
}

$( "#add-row" ).click(function( event ) {
	var rowCount = $('#result tr').length;
	var content= $('#resultrow0').html();
	resultContent=   content.replace(/0/g,rowCount-1);   
	$(resultContent).find("input").val("");
	$('#result > tbody:last').append("<tr>"+resultContent+"</tr>"); 
	$('#result tr:last').find("input").val("");   
	patternvalidation(); 
});    


function callAjaxSearch() {
	drillDowntableContainer = jQuery("#resultTable");
	jQuery('.report-section').removeClass('display-hide');
	reportdatatable = drillDowntableContainer
			.dataTable({
				ajax : {
					url : "/pass/nocconfig/ajaxsearch/" + $('#mode').val(),
					type : "POST",
					"data" : getFormData(jQuery('form'))
				},
				"fnRowCallback" : function(row, data, index) {
					$(row).on(
							'click',
							function() {
								console.log(data.id);
								window.open('/pass/nocconfig/'
										+ $('#mode').val() + '/' + data.id, '',
										'width=800, height=600');
							});
				},
				"sPaginationType" : "bootstrap",
				"bDestroy" : true,
				"sDom" : "<'row'<'col-xs-12 hidden col-right'f>r>t<'row'<'col-xs-3'i><'col-xs-3 col-right'l><'col-xs-3 col-right'<'export-data'T>><'col-xs-3 text-right'p>>",
				"aLengthMenu" : [ [ 10, 25, 50, -1 ], [ 10, 25, 50, "All" ] ],
				"oTableTools" : {
					"sSwfPath" : "../../../../../../egi/resources/global/swf/copy_csv_xls_pdf.swf",
					"aButtons" : [ "xls", "pdf", "print" ]
				},
				aaSorting : [],
				columns : [ {
					"data" : "nocDepartment",
					"sClass" : "text-left"
				}, {
					"data" : "applicationType",
					"sClass" : "text-left"
				}, {
					"data" : "integrationType",
					"sClass" : "text-left"
				}, {
					"data" : "isDeemedApproved",
					"sClass" : "text-left"
				}, {
					"data" : "daysForDeemedApproval",
					"sClass" : "text-right"
				} ]
			});
}