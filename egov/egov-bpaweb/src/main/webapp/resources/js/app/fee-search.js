jQuery(document).ready(function($) {

$('#code').change(function () {
        if ($('#code').val() !== '') {
            $.ajax({
                url: "/bpa/bpafee/fee-by-code",
                type: "GET",
                async: false,
                dataType: "json",
                data: {code: $('#code').val()},
                success: function (response) {
                    $('#name').val(response.name);
                }
            });
        }
    });
});