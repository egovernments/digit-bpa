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
                        .dialog({
                            message: $('#confirmApproveAppln').val(),
                            buttons: {
                                'confirm': {
                                    label: 'Yes',
                                    className: 'btn-primary',
                                    callback: function (result) {
                                        formSubmit($('#btnApprove').val());
                                    }
                                },
                                'cancel': {
                                    label: 'No',
                                    className: 'btn-danger',
                                    callback: function (result) {
                                        e.stopPropagation();
                                        e.preventDefault();
                                    }
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
                        .dialog({
                            message: $('#rejectAppln').val(),
                            buttons: {
                                'confirm': {
                                    label: 'Yes',
                                    className: 'btn-primary',
                                    callback: function (result) {
                                        formSubmit($('#btnReject').val());
                                    }
                                },
                                'cancel': {
                                    label: 'No',
                                    className: 'btn-danger',
                                    callback: function (result) {
                                        e.stopPropagation();
                                        e.preventDefault();
                                    }
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
                        .dialog({
                            message: $('#confirmBlockLicense').val(),
                            buttons: {
                                'confirm': {
                                    label: 'Yes',
                                    className: 'btn-primary',
                                    callback: function (result) {
                                        formSubmit($('#btnBlock').val());
                                    }
                                },
                                'cancel': {
                                    label: 'No',
                                    className: 'btn-danger',
                                    callback: function (result) {
                                        e.stopPropagation();
                                        e.preventDefault();
                                    }
                                },
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
