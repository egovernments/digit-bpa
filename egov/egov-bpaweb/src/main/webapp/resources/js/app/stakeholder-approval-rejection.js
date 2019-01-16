$(document)
    .ready(
        function () {

            if($('#approvalNotRequire').val() === 'true') {
                bootbox.alert('The license is expired during the approval process. Please reject the application and ask user to resubmit with valid building license.');
            }
            $('.currentOwner').hide();

            $('#btnApprove').click(function (e) {
                if ($('#stakeHolder').valid()) {
                    bootbox
                        .confirm({
                            message: 'Please confirm, do you really want to approve the application?. Please make sure submitted details are correct before approval.',
                            buttons: {
                                'cancel': {
                                    label: 'No',
                                    className: 'btn-danger'
                                },
                                'confirm': {
                                    label: 'Yes',
                                    className: 'btn-primary'
                                }
                            },
                            callback: function (result) {
                                if (result) {
                                    formSubmit($('#btnApprove').val());
                                } else {
                                    e.stopPropagation();
                                    e.preventDefault();
                                }
                            }
                        });
                } else {
                    e.preventDefault();
                }
                return false;
            });


            $('#btnReject').click(function (e) {
                if ($('#stakeHolder').valid() && validateComments('rejection')) {
                    bootbox
                        .confirm({
                            message: 'Please confirm, do you really want to reject this application ?',
                            buttons: {
                                'cancel': {
                                    label: 'No',
                                    className: 'btn-danger'
                                },
                                'confirm': {
                                    label: 'Yes',
                                    className: 'btn-primary'
                                }
                            },
                            callback: function (result) {
                                if (result) {
                                    formSubmit($('#btnReject').val());
                                } else {
                                    e.stopPropagation();
                                    e.preventDefault();
                                }
                            }
                        });
                } else {
                    e.preventDefault();
                }
                return false;
            });

            function formSubmit(workFlowAction) {
                $('#workFlowAction').val(workFlowAction);
                $('.loader-class').modal('show', {
                    backdrop: 'static'
                });
                $('#stakeHolder').trigger('submit');
                resetTable();
            }

            $('#btnBlock').click(function (e) {
                if ($('#stakeHolder').valid() && validateComments('block')) {
                    bootbox
                        .confirm({
                            message: 'Please confirm, are you want to block the building licensee ?',
                            buttons: {
                                'cancel': {
                                    label: 'No',
                                    className: 'btn-danger'
                                },
                                'confirm': {
                                    label: 'Yes',
                                    className: 'btn-primary'
                                }
                            },
                            callback: function (result) {
                                if (result) {
                                    formSubmit($('#btnBlock').val());
                                } else {
                                    e.stopPropagation();
                                    e.preventDefault();
                                }
                            }
                        });
                } else {
                    e.preventDefault();
                }
                return false;
            });

            function validateComments(type) {
                if (!$('#comments').val()) {
                    bootbox.alert('Please enter '+type+ ' comments.');
                    return false;
                } else {
                    return true;
                }
            }

        });

function resetTable() {
    localStorage.setItem("isRefresh",true);
}
