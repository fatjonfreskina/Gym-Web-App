$(document).ready(function() {
    let alertBox = $("#alert-box")
    alertBox.hide()

    let messageBody = $("#alert-message-body")

    $("#form").submit(function (e) {
        e.preventDefault()
        const formValues = $(this).serialize();
        $.ajax({
            url: "addcourses",
            cache: false,
            type: "POST",
            data: formValues,
            success: function (response) {

                messageBody.empty()
                messageBody.text(response.message)

                alertBox.show()
                alertBox.fadeTo(2000, 500).slideUp(500, function () {
                    $(this).slideUp(500);
                });
            },
            error: function (data) {
                alertBox.show()
                messageBody.empty()
                messageBody.text("Some unknown server side error occurred")
            }
        });
    });

});