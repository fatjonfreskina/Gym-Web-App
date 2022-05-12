$(document).ready(function() {
    let alertBox = $("#alert-box")
    alertBox.hide()
    let messageBody = $("#alert-message-body")

    const cost30 = $('#cost_30')
    const cost90 = $('#cost_90')
    const cost180 = $('#cost_180')
    const cost365 = $('#cost_365')

    const subscriptionType30 = $('#subscription_type_30')
    const subscriptionType90 = $('#subscription_type_90')
    const subscriptionType180 = $('#subscription_type_180')
    const subscriptionType365 = $('#subscription_type_365')

    const dateFirstEvent = $('#date')

    const monday = $('#monday')
    const tuesday = $('#tuesday')
    const wednesday = $('#wednesday')
    const thursday = $('#thursday')
    const friday = $('#friday')
    const saturday = $('#saturday')
    const sunday = $('#sunday')

    const form = $('#form')

    form.submit(function (e) {
        e.preventDefault()

        //correctness of checkboxes
        if((cost30.val() !== "") && (subscriptionType30.is(":checked")))
        {

        }else if((cost90.val() !== "") && (subscriptionType90.is(":checked")))
        {

        }else if((cost180.val() !== "") && (subscriptionType180.is(":checked")))
        {

        }else if((cost365.val() !== "") && (subscriptionType365.is(":checked")))
        {

        }else
        {
            showMessage("Checkboxes and Prices does not match")
            return
        }

        //date correctness
        if(moment(dateFirstEvent.val(),'YYYY-MM-DD').isBefore(moment()))
        {
            showMessage("Date must be after today")
            return
        }

        //Time correctness
        if((monday.val().length === 0 ) && (tuesday.val().length === 0 ) && (wednesday.val().length === 0 ) &&
            (thursday.val().length === 0 ) && (friday.val().length === 0 ) && (saturday.val().length === 0 ) && (sunday.val().length === 0 ))
        {
            showMessage("Select at least one hour!")
            return
        }


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
                showMessage("Some unknown server side error occurred")
            }
        });
    });

    function showMessage(message) {
        messageBody.empty()
        messageBody.text(message)
        alertBox.show()
        alertBox.fadeTo(2000, 500).slideUp(500, function () {
            $(this).slideUp(500);
        });
    }

});