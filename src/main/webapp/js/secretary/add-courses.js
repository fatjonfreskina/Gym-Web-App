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

    const weeks = $('#weeks')

    const form = $('#form')

    form.submit(function (e) {
        e.preventDefault()

        //correctness of checkboxes
        //console.log(cost30.val().charCodeAt(0))
        if (((cost30.val() === "") || (cost30.val() === null) || (cost30.val() === undefined)) && (subscriptionType30.is(":checked"))) {
            showWarningMessage("Prices do not match")
            return
        }

        if (((cost90.val() === "") || (cost90.val() === null) || (cost90.val() === undefined)) && (subscriptionType90.is(":checked"))) {
            showWarningMessage("Prices do not match")
            return
        }

        if (((cost180.val() === "") || (cost180.val() === null) || (cost180.val() === undefined)) && (subscriptionType180.is(":checked"))) {
            showWarningMessage("Prices do not match")
            return
        }

        if (((cost365.val() === "") || (cost365.val() === null) || (cost365.val() === undefined)) && (subscriptionType365.is(":checked"))) {
            showWarningMessage("Prices do not match")
            return
        }

        var nWeeks = parseInt(weeks.val())

        if((subscriptionType30.is(":checked")))
        {
            if((nWeeks * 7 )< 30)
            {
                showWarningMessage("Invalid Price For the number of weeks")
                return
            }
        }

        if((subscriptionType90.is(":checked")))
        {
            if((nWeeks * 7 )< 90)
            {
                showWarningMessage("Invalid Price For the number of weeks")
                return
            }
        }

        if((subscriptionType180.is(":checked")))
        {
            if((nWeeks * 7 )< 180)
            {
                showWarningMessage("Invalid Price For the number of weeks")
                return
            }
        }

        if((subscriptionType365.is(":checked")))
        {
            if((nWeeks * 7 )< 364)
            {
                showWarningMessage("Invalid Price For the number of weeks")
                return
            }
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

        //1 che ci sia durata in settimane
        if(weeks.val()  == null || weeks.val() === undefined)
        {
            showWarningMessage("Select at least one week!")
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