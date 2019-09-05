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

function callAjaxSearch() {
	drillDowntableContainer = jQuery("#resultTable");
	jQuery('.report-section').removeClass('display-hide');
	reportdatatable = drillDowntableContainer.dataTable({
				ajax : {
					url : "/bpa/nocdepartment/ajaxsearch/" + $('#mode').val(),
					type : "POST",
					"data" : getFormData(jQuery('form'))
				},
				"fnRowCallback" : function(row, data, index) {
					$(row).on(
							'click',
							function() {
								console.log(data.id);
								window.open('/pass/nocdepartment/'
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
					"data" : "code",
					"sClass" : "text-left"
				}, {
					"data" : "name",
					"sClass" : "text-left"
				}, {
					"data" : "isActive",
					"sClass" : "text-left"
				} ]
			});
}
$("#addrow").click(function(event) {
	addRow();
});

function addRow() {
	var rowCount = $('#result tr').length;
	var content = $('#resultrow0').html();
	resultContent = content.replace(/0/g, rowCount - 1);
	// $(resultContent).find("input").val("");
	$(resultContent).find("select").val("true");
	$(resultContent).find("input[type='text']").val("");
	$('#result > tbody:last').append("<tr>" + resultContent + "</tr>");
	$('#result tr:last').find("input").val("");
	patternvalidation();
}
//enable this to implement delete function . use the appropriate id and url
/*
 * function deleteThisRow(obj) {
 * 
 * //This is to show loading effect till the row is deleted.
 * $('.loader-class').modal('show', {backdrop: 'static'});
 * 
 * setTimeout(function(){ $('.loader-class').modal('hide'); }, 3000);
 * 
 * var idx=$(obj).data('idx'); //fix below line and URL var categoryPropertyId =
 * $('input[name="categoryProperties['+ $(obj).data('idx') +'].id"]').val(); var
 * tbl = document.getElementById('result');
 * 
 * if(categoryPropertyId) { $.ajax({ url:
 * '/egassets/assetcategory/deleteCategoryProperty?categoryPropertyId='
 * +categoryPropertyId, type: "GET", success: function(response) {
 * tbl.deleteRow(idx); regenerateTable(); }, error: function(response){
 * console.log("Failed"); } }); } else { tbl.deleteRow((idx+1));
 * regenerateTable(); }
 *  }
 * 
 * function regenerateTable() { //starting index for table fields var idx=0;
 * 
 * jQuery("#result tbody tr").each(function() { jQuery(this).find("input,
 * select, button").each(function() { var customAttrs={}; if($(this).attr('id')) {
 * console.log('coming inside!'); customAttrs['id']=function(_,id){ return
 * id.replace(/\[.\]/g, '['+ idx +']'); }; } if($(this).attr('name')) {
 * customAttrs['name']=function(_,id){ return id.replace(/\[.\]/g, '['+ idx
 * +']'); }; } if($(this).attr('data-idx')) {
 * customAttrs['data-idx']=function(_,dataIdx){ return idx; }; }
 * jQuery(this).attr(customAttrs);
 * 
 * }); idx++; }); }
 */