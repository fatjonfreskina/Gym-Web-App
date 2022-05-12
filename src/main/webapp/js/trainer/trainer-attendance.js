let contextPath = "/wa2122-gwa"

GET_Trainee = (email) => {
    //Perform the AJAX request to fill the calendar
    return $.ajax({
        url: contextPath + "/rest/trainee/" + email,
        cache: false,
        type: "GET",
        dataType: 'json',
        success: (response) => {
            //console.log("t:", response)
            return response;
        },
        error: (response) => {
            //console.log(response);
            showWarningMessage("Server error while retrieving data");
        }
    });
}

GET_TrainerAttendance = () => {
    //console.log("GET_TrainerAttendance");
    return $.ajax({
        url: contextPath + "/rest/trainer/attendance",
        cache: false,
        type: "GET",
        dataType: 'json',
        success: function (response) {
            updateReservations(response);
            updateSubscriptions(response);
            return response;
        },
        error: function (err) {
            //console.log(response);
            showWarningMessage("Server error while retrieving data");
        }
    });
}

POST_Subscription = (email) => {
    //console.log(subscriptions);
    //let subscription = subscriptions.find(elem => elem.trainee = email); not working properly
    let subscription;
    let found = false
    for (let i = 0; i < subscriptions.length && !found; i++) {
        let cur = subscriptions[i];
        if (cur.trainee === email) {
            subscription = cur;
            found = true;
            //console.log("38", cur);
        }
    }
    //console.log("TO ADD", subscription);
    $.ajax({
        url: contextPath + "/rest/trainer/attendance",
        cache: false,
        type: "POST",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(subscription),
        success: function () { //data
            //console.log("data:", data)
            GET_TrainerAttendance()
        },
        error: function (response) {
            //console.log(response);
            showWarningMessage("Server error sending data");
        }
    });
}

DELETE_Reservation = (email) => {
    let reservation = reservations.find(elem => elem.trainee = email);
    $.ajax({
        url: contextPath + "/rest/trainer/attendance",
        cache: false,
        type: "DELETE",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(reservation),
        success: function () { //data, textStatus, xhr
            //console.log("data:", data)
            GET_TrainerAttendance()
        },
        error: function (err) {
            //console.log(response);
            showWarningMessage("Server error sending data");
        }
    });
}

let reservations;
let subscriptions = [];

/**
 *
 * @param rows
 */
updateReservationsTable = (rows) => {
    //console.log(rows)
    let TABLE = $('#reservationsTABLE');
    if ($.fn.dataTable.isDataTable('#reservationsTABLE')) TABLE.DataTable().destroy();
    TABLE.DataTable({
        data: rows,
        "paging": false,
        "info": false,
        columns: [{data: 'name'}, {data: 'surname'}, {data: 'email'}, {data: 'action'}]
    })

}

/**
 *
 * @param attendances
 */
updateReservations = (attendances) => {
    //console.log("updateReservations");
    reservations = attendances.reservations;
    let reservationsAJAX = [];
    for (let i = 0; i < reservations.length; i++) {
        reservationsAJAX.push(GET_Trainee(reservations[i].trainee))
    }

    $.when(reservationsAJAX).done((res) => {
        let results = [];
        //console.log("RES", res);
        if (res.length > 0) $.each(res, function (index, val) {
            val.then(function (trainee) {
                let obj = {
                    ...trainee,
                    "action": "" + "<div class=\"d-flex justify-content-end\">" + "<button " + "type=\"button\" " + "class=\"btn btn-sm btn-danger\" " + "onclick=\"DELETE_Reservation('" + trainee.email + "')\">REMOVE</button></div>"
                };
                //console.log("obj", obj);
                results.push(obj);
                if (results.length === res.length) {
                    updateReservationsTable(results);
                }
            });
        }); else updateReservationsTable(results)
    }).fail(function(response){
        //console.log(response);
        showWarningMessage("Server error while updating reservation");
    });
}

/**
 *
 * @param rows
 */
updateSubscriptionsTable = (rows) => {
    //console.log(rows)
    let TABLE = $('#subscriptionsTABLE');
    if ($.fn.dataTable.isDataTable('#subscriptionsTABLE')) {
        TABLE.DataTable().destroy();
    }
    TABLE.DataTable({
        data: rows,
        "paging": false,
        "info": false,
        columns: [{data: 'name'}, {data: 'surname'}, {data: 'email'}, {data: 'action'}]
    })
}

/**
 *
 * @param attendance
 */
updateSubscriptions = (attendance) => {
    //console.log("updateSubscriptions");
    subscriptions = [];
    for (let i = 0; i < attendance.subscriptions.length; i++) {
        let cur = attendance.subscriptions[i];
        subscriptions.push(cur);
        //console.log("cur", cur)
    }

    //console.log(subscriptions);
    let subsriptionsAJAX = [];
    for (let i = 0; i < subscriptions.length; i++) {
        subsriptionsAJAX.push(GET_Trainee(subscriptions[i].trainee))
    }

    $.when(subsriptionsAJAX).done((res) => {
        let results = [];
        //console.log("SUBSCRIPTIONS", res);
        if (res.length > 0) $.each(res, function (index, val) {
            val.then(function (trainee) {
                let obj = {
                    ...trainee,
                    "action": "" + "<div class=\"d-flex justify-content-end\">" + "<button " + "type=\"button\" " + "class=\"btn btn-sm btn-success\" " + "onclick=\"POST_Subscription('" + trainee.email + "')\">ADD</button></div>"
                };
                //console.log("obj", obj);
                results.push(obj);
                if (results.length === res.length) {
                    updateSubscriptionsTable(results);
                }
            });
        }); else updateSubscriptionsTable(results)
    }).fail(function(response){
        //console.log(response);
        showWarningMessage("Server error while updating reservation");
    });
}

// CALL
GET_TrainerAttendance();

/**
 * Shows an error / warning message in the appropriate alert
 * @param message message to show
 */
function showWarningMessage(message) {

    const messageBody = $('#alert-warning-message-body')
    messageBody.empty()
    messageBody.text(message)

    const alertBox = $('#alert-warning');
    alertBox.show();

    alertBox.fadeTo(2000, 500).slideUp(500, function () {
        $(this).slideUp(500);
    });

}