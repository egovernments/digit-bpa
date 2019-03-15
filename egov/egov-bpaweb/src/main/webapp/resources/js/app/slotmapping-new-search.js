$(document)
		.ready(
				function() {
					$('.allservices').hide();
					$('.daysforonedaypermit').hide();
					$('#applicationType')
							.change(
									function() {
										var applicationTypeName = $(
												"#applicationType option:selected")
												.text();
										if ('One Day permit'
												.localeCompare(applicationTypeName) == 0) {
											$('.allservices').show();
											$('.daysforonedaypermit').show();
										} else if ('Regular'
												.localeCompare(applicationTypeName) == 0) {
											$('.allservices').hide();
											$('.daysforonedaypermit').hide();
										}
									});
					
					$('#zone')
					.change(
							function() {
								$
										.ajax({
											url : "/bpa/boundary/ajaxBoundary-blockByWard",
											type : "GET",
											data : {
												wardId : $('#zone')
														.val()
											},
											cache : false,
											dataType : "json",
											success : function(response) {
												$('#revenueWard').html("");
												$('#revenueWard')
														.append(
																"<option value=''>Select</option>");
												$
														.each(
																response,
																function(
																		index,
																		value) {
																	$(
																			'#revenueWard')
																			.append(
																					$(
																							'<option>')
																							.text(
																									value.blockName)
																							.attr(
																									'value',
																									value.blockId));
																});
											},
											error : function(response) {
												$('#revenueWard').html("");
												$('#revenueWard')
														.append(
																"<option value=''>Select</option>");
											}
										});
							});

					$('#revenueWard').change(function(){
						$.ajax({
							url: "/bpa/boundary/ajaxBoundary-electionwardbyrevenueward",
							type: "GET",
							data: {
								wardId : $('#revenueWard').val()
							},
							cache: false,
							dataType: "json",
							success: function (response) {
								$('#electionWard').html("");
								$('#electionWard').append("<option value=''>Select</option>");
								$.each(response, function(index, value) {
									$('#electionWard').append($('<option>').text(value.electionwardName).attr('value', value.electionwardId));
								});
							}, 
							error: function (response) {
								$('#electionWard').html("");
								$('#electionWard').append("<option value=''>Select</option>");
							}
						});
						
					});
					
					$('#applicationType').trigger("change");
					$('#zone').trigger("change");

					$('#btnSearchForView').click(function() {
						if ($('#slotMappingSearchViewForm').valid()) {
						return true;
						} else {
						return false;
						}
					});

					$('#btnSearchForEdit').click(function() {
						if ($('#slotMappingSearchEditForm').valid()) {
							return true;
						} else {
							return false;
						}
					});

	});

