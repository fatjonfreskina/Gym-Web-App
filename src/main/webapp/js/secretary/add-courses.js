$(document).ready(function () {

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
        if ((cost30.val() !== "") && (subscriptionType30.is(":checked"))) {

        } else if ((cost90.val() !== "") && (subscriptionType90.is(":checked"))) {

        } else if ((cost180.val() !== "") && (subscriptionType180.is(":checked"))) {

        } else if ((cost365.val() !== "") && (subscriptionType365.is(":checked"))) {

        } else {
            showWarningMessage("Checkboxes and Prices does not match")
            return
        }

        //date correctness
        if (moment(dateFirstEvent.val(), 'YYYY-MM-DD').isBefore(moment())) {
            showWarningMessage("Date must be after today")
            return
        }

        //Time correctness
        if ((monday.val().length === 0) && (tuesday.val().length === 0) && (wednesday.val().length === 0) &&
            (thursday.val().length === 0) && (friday.val().length === 0) && (saturday.val().length === 0) && (sunday.val().length === 0)) {
            showWarningMessage("Select at least one hour!")
            return
        }


        const formValues = $(this).serialize();
        $.ajax({
            url: "addcourses",
            cache: false,
            type: "POST",
            data: formValues,
            success: function (response) {
                showSuccessMessage(response.message)
            },
            error: function (data) {
                showWarningMessage("Some unknown server side error occurred")
            }
        });
    });


});