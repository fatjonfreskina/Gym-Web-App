$(document).ready(function () {
    //GLOBAL VARIABLES
    /**
     *
     * THE context path for the url
     * @type {string} contextPath value
     */
    let contextPath = "/wa2122-gwa"

    /**
     * global list of reservations == present trainees
     * @type {*[]} empty list
     */
    let reservations;
    /**
     * global list of subscriptions == trainees enrolled to the course but absent
     * @type {*[]} empty list
     */
    let subscriptions = [];

    /**
     * GET main details of a given Trainee from the backend
     * @param email of the trainee
     * @returns async call: if success return the trainee object as json otherwise returns an error
     */
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
                if (!response.responseJSON.isError) showWarningMessage("Server error while retrieving data");
            }
        });
    }

    /**
     * GET the reservations and the subscription to the currentLectureTimeSlot from the server and update the tables
     * @returns async call: if success return the trainerAttendance object as json otherwise returns an error
     */
    GET_TrainerAttendance = () => {
        //console.log("GET_TrainerAttendance");
        return $.ajax({
            url: contextPath + "/rest/trainer/attendance",
            cache: false,
            type: "GET",
            dataType: 'json',
            success: function (response) {
                /**
                 * Update the reservations table
                 */
                updateReservations(response);
                /**
                 * Update the subscriptions table
                 */
                updateSubscriptions(response);
                return response;
            },
            error: function (response) {
                if (!response.responseJSON.isError) showWarningMessage("Server error while retrieving data");
            }
        });
    }

    /**
     * POST a subscription to the server, to mark a trainee as present
     * @param email of the trainee
     */
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
                if (!response.responseJSON.isError) showWarningMessage("Server error sending data");
            }
        });
    }

    /**
     * DELETE a reservation from the server, to mark a trainee as absent
     * @param email of the trainee
     */
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
                if (!err.responseJSON.isError) showWarningMessage("Server error sending data");
            }
        });
    }

    /**
     * Clears the Reservations table and then updates the table with the given data
     * @param rows to be added to the table
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
     * Extracts reservation from the attendances
     * and for each trainee, who has booked, gets his personal information from the server
     * @param attendances trainers attendances of the current lecturetimeslot
     */
    updateReservations = (attendances) => {
        //console.log("updateReservations");
        reservations = attendances.reservations;
        let reservationsAJAX = [];
        for (let i = 0; i < reservations.length; i++) {
            reservationsAJAX.push(GET_Trainee(reservations[i].trainee))
        }

        /**
         * Retrieve all the trainees, who made a reservation, and add them to the reservation table
         */
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
        }).fail(function (response) {
            //console.log(response);
            if (!response.responseJSON.isError) showWarningMessage("Server error while updating reservation");
        });
    }

    /**
     * Clears the Subscriptions table and then updates the table with the given data
     * @param rows to be added to the table
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
     * Extracts subscriptions from the attendances
     * and for each trainee, who is enrolled to the course, gets his personal information from the server
     * @param attendance trainers attendances of the current lecturetimeslot
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

        /**
         * Retrieve all the trainees, who are enrolled to the course, and add them to the subscriptions table
         */
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
        }).fail(function (response) {
            //console.log(response);
            if (!response.responseJSON.isError) showWarningMessage("Server error while updating reservation");
        });
    }

    /**
     * First call to retrieve all the data and populate the tables
     */
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
});