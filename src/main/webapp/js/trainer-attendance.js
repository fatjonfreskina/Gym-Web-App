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
        error: (err) => {
            console.log(err);
            return err;
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
            console.log(err);
            return null;
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
        if (cur.trainee == email) {
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
        error: function (err) {
            alert(JSON.stringify(err.responseJSON));
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
            console.log(err.responseJSON)
        }
    });
}

let reservations;
let subscriptions = [];

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
                if (results.length == res.length) {
                    updateReservationsTable(results);
                }
            });
        }); else updateReservationsTable(results)
    })
}

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
                if (results.length == res.length) {
                    updateSubscriptionsTable(results);
                }
            });
        }); else updateSubscriptionsTable(results)
    })
}

// CALL
GET_TrainerAttendance();