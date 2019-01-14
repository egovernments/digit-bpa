/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2018  eGovernments Foundation
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

$(document)
		.ready(
				function() {


					$(document).on(
							'blur',
							'.textarea-content',
							function(evt) {
								$(this).tooltip('hide').attr(
										'data-original-title', $(this).val());
								evt.stopImmediatePropagation();
							});
                    if ($('#currentState').val() == 'NOC updation in progress') {
                        $('#bpaupdatenocdetails ').each(function () {
                            $(this).find("th").removeClass("hide");
                            $(this).find("td").removeClass("hide");
                        });
                    }
                    $('.nocStatus').on('change', function () {
                        var rowObj = $(this).closest('tr');
                        if ($(rowObj).find('.nocStatus').val() == 'REJECTED') {
                            $(rowObj).find('.nocRemarks').attr('required', true);
                        } else {
                            $(rowObj).find('.nocRemarks').removeAttr('required');
                            var elemt =$(rowObj).data('input-element');
                            rowObj.find('label').hide();
                            rowObj.find('.nocRemarks').removeClass('error');
                        }
                    });

					$(document).on(
							'click',
							'.showModal',
							function(evt) {
								var tableheaderid = $(this).data('header');
								$('#textarea-header').html(tableheaderid);
								$('#textarea-updatedcontent').attr(
										'data-assign-to',
										$(this).data('assign-to'));
								$('#textarea-updatedcontent').val(
										$('#' + $(this).data('assign-to'))
												.val());
								$("#textarea-modal").modal('show');
								evt.stopImmediatePropagation();
							});

					// update textarea content in table wrt index
					$(document)
							.on(
									'click',
									'#textarea-btnupdate',
									function(evt) {
										$('#'+ $('#textarea-updatedcontent').attr(
												'data-assign-to')).val($('#textarea-updatedcontent').val());
										evt.stopImmediatePropagation();
									});

                    $('.replyReceivedOn').on('changeDate', function() {
                        var rowObj = $(this).closest('tr');
                        $("#bpaupdatenocdetails tbody tr").each(function () {
                            var letterSent = $(this).find('*[name$="letterSentOn"]').val();
                            var replyReceived = $(this).find('*[name$="replyReceivedOn"]').val();
                            var letterSentOn = moment(letterSent,'DD/MM/YYYY');
                            var replyReceivedOn = moment(replyReceived,'DD/MM/YYYY');
                            if(!letterSentOn) {
                                bootbox
                                    .alert("Please enter Letter sent on date");
                            } else if (replyReceivedOn.isBefore(letterSentOn)) {
                                bootbox
                                    .alert("Reply Received date should be greater than letter sent date");
                                $(rowObj).find('.replyReceivedOn').val('').datepicker("refresh");
                            }
                        });
                    });

				});
