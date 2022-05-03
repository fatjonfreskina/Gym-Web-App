$(document).ready(function() {

    let alertBox = $("#alert-box")
    alertBox.hide()
    let messageBody = $("#alert-message-body")

    $("#form").submit(function (e) {
        e.preventDefault()
        const formValues = $(this).serialize();
        $.ajax({
            url: "rest/addsubscription",
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

    $("#partial_email").on('input', function () {
        $.ajax({
            url: "rest/listlikepersons",
            data: {
                "partial_email": $("#partial_email").val()
            },
            cache: false,
            type: "GET",
            dataType: 'json',
            success: function (response) {
                let emailList = $("#list_emails")
                emailList.empty()
                for (const person of response) {
                    emailList.append(`<li class='list-group-item'><input type='radio' name='trainee' value='` + person.email + `'/>{person.email}</li>`)
                }
            },
            error: function (data) {
                alertBox.show()
                messageBody.empty()
                messageBody.text("Some unknown server side error occurred")
            }

        });
    })

    let courseName = $("#course_name")
    let listLastDate = $("#list_last_date")

    courseName.change(function () {
        $.ajax({
            url: "rest/timeschedules",
            data: {
                "course_name": courseName.val()
            },
            cache: false,
            type: "GET",
            dataType: 'json',
            success: function (response) {
                listLastDate.empty()
                for (const lastDate of response) {
                    listLastDate.append(`<li class='list-group-item'><input type='radio' name='course_edition_id' value='`+ lastDate.courseEditionId + `'/>{lastDate.date}</li>`)
                }
            },
            error: function (xhr) {
                alertBox.show()
                messageBody.empty()
                messageBody.text("Some unknown server side error occurred")
            }
        });
    })

    courseName.trigger('change')

});