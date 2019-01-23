$(document)
    .ready(
        function () {

            if($('#approvalNotRequire').val() === 'true') {
                bootbox.alert($('#licenseExpired').val());
            }
            $('.currentOwner').hide();

            $('#btnApprove').click(function (e) {
                if ($('#stakeHolder').valid()) {
                    bootbox
                        .confirm({
                            message: $('#confirmApproveAppln').val(),
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
                            message: $('#rejectAppln').val(),
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
                            message: $('#confirmBlockLicense').val(),
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
                    bootbox.alert($('#enterMsg').val()+type+ $('#commentsMsg').val());
                    return false;
                } else {
                    return true;
                }
            }

        });

function resetTable() {
    localStorage.setItem("isRefresh",true);
}
