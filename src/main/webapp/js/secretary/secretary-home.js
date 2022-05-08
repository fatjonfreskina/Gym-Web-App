$(document).ready(function() {

    let alertBox = $("#alert-box")
    alertBox.hide()
    let messageBody = $("#alert-message-body")

    //This variable contains the event which has been clicked in the calendar
    let selectedEvent = undefined;

    //Construct the calendar
    let calendarEl = document.getElementById('calendar');
    let calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'timeGridWeek',
        initialDate: new Date(),
        themeSystem: "bootstrap",
        headerToolbar: {
            left: '',
            center: 'title',
            right: 'prev,next'
        },
        allDaySlot: false,
        timeFormat: 'HH:mm',
        slotMinTime: "05:00:00",
        slotMaxTime: "23:00:00",
        slotLabelFormat: {
            hour: "2-digit",
            minute: "2-digit",
            hour12: false
        },
        firstDay: 1,
        eventTimeFormat: {
            hour: "2-digit",
            minute: "2-digit",
            hour12: false
        },
        nowIndicator: true,
        eventClick: function (info) {
            selectedEvent = info.event.extendedProps.customLTS;
            $("#modal-actions-course").modal("show");
        }
    });

    /**
     * Returns the color associated to the course name, black for non associated courses
     * @param courseName name of the course (string)
     * @returns {string} color associated
     */
    function GetColorOfCourse(courseName) {
        switch (courseName) {
            case "Cardio":
                return "DarkCyan";
            case "Yoga":
                return "YellowGreen";
            case "Crossfit":
                return "DarkRed";
            case "Calisthenics":
                return "LightSalmon";
            case "Bodybuilding":
                return "Indigo";
            case "Powerlifting":
                return "DarkSlateBlue";
            default:
                return "Black";
        }
    }

    /**
     * Performs a refresh of the calendar object of this page
     */
    function renderCalendar() {
        //Get the current active window of the calendar
        let start = moment(calendar.view.activeStart).format('YYYY-MM-DD');
        let end = moment(calendar.view.activeEnd).format('YYYY-MM-DD');
        //Perform the AJAX request to fill the calendar
        $.ajax({
            url: "rest/getalllecturetimeslot",
            data: {
                "start": start,
                "end": end,
            },
            cache: false,
            type: "GET",
            dataType: 'json',
            success: function (response) {
                //Remove all the events
                calendar.removeAllEvents();
                //Iterate over all the elements in the response
                for (const lts of response) {
                    //Create an event object
                    let event = {};
                    //Calculate dates
                    //startDate = new Date(Date.parse(lts.date + ' ' + lts.startTime + ' GMT+2'));
                    startDate = new Date(Date.parse(lts.date + 'T' + lts.startTime.toString()));

                    //TODO fix date management
                    //console.log(moment(lts.dateTime).toDate());
                    //startDate = new Date(lts.dateTime);
                    event.start = startDate;
                    event.end = moment(startDate).add(2, 'hours').toDate();
                    //Set title and background color based on the title
                    event.title = lts.courseName;
                    event.backgroundColor = GetColorOfCourse(lts.courseName);

                    let lectureTimeSlot = {};
                    lectureTimeSlot.courseEditionId = lts.courseEditionId;
                    lectureTimeSlot.roomName = lts.roomName;
                    lectureTimeSlot.customdate = lts.date;
                    lectureTimeSlot.customstartTime = lts.startTime;

                    //Add the custom object to the calendar object
                    event.customLTS = lectureTimeSlot;

                    //Add the element to the calendar
                    calendar.addEvent(event);
                }
                //Render the new calendar
                calendar.render();
            },
            error: function (xhr) {
                showMessage("Some unknown server side error occurred")
            }
        });
    }

    //Attach render calendar to button for week change
    $('body').on('click', 'button.fc-next-button', renderCalendar);
    $('body').on('click', 'button.fc-prev-button', renderCalendar);

    //Initial render when page loaded
    renderCalendar();

    $("#button-delete-lecturetimeslot").click(() => {

        if (selectedEvent !== undefined) {

            const roomNane = selectedEvent.roomName;
            const date = selectedEvent.customdate;
            const startTime = selectedEvent.customstartTime;

            if (confirm("Do you really want to delete?")) {
                $.ajax({
                    url: "secretary/rest/deletelecturetimeslot" + '?' + $.param({
                        "roomname": roomNane,
                        "date": date,
                        "starttime": startTime
                    }),
                    type: "DELETE",
                    success: function (response) {
                        showMessage(response);
                        renderCalendar();
                    },
                    error: function (xhr) {
                        showMessage("Some unknown server side error occurred");
                    }
                });
            }

        } else {
            showMessage("Some unknown server side error occurred")
        }

    });

    $("#button-notify-substitution").click(() => {

        if (selectedEvent !== undefined) {

            const roomNane = selectedEvent.roomName;
            const date = selectedEvent.customdate;
            const startTime = selectedEvent.customstartTime;
            const substituteEmail = $("#substitute").val();
            const note = $("#info-substitution").val();

            $.ajax({
                url: "secretary/rest/substitutionlecturetimeslot",
                type: "POST",
                data: {
                    "roomname": roomNane,
                    "date": date,
                    "starttime": startTime,
                    "n": substituteEmail,
                    "notes": note
                },
                cache: false,
                dataType: 'json',
                success: function (response) {
                    console.log(response);
                    renderCalendar();
                },
                error: function (xhr) {
                    showMessage("Some unknown server side error occurred")
                }
            });


        } else {
            showMessage("Some unknown server side error occurred")
        }

    });

    $("#button-change-schedule").click(() => {

        if (selectedEvent !== undefined) {

            //Old parameters
            const oldRoomName = selectedEvent.roomName;
            const oldDate = selectedEvent.customdate;
            const oldStartTime = selectedEvent.customstartTime;
            //New updated parameters
            const newRoomName = $('#newRoom').val();
            const newDate = moment($('#newDate').val());
            const newStartTime = moment($('#newStartTime').val(), ["hh:mm"]);
            const newStartTimeFormatted = newStartTime.format("hh:mm:ss A");

            //Check newDate >= actual date
            if(!newDate.isSameOrAfter(now, 'day')){
                showMessage("Provided date is invalid");
                return;
            }

            //TODO: Check start time is after now

            $.ajax({
                url: "secretary/rest/updatelecturetimeslot",
                type: "POST",
                data: {
                    "oldRoomName": oldRoomName,
                    "oldDate": oldDate,
                    "oldStartTime": oldStartTime,
                    "newRoomName": newRoomName,
                    "newDate": newDate,
                    "newStartTime": newStartTimeFormatted
                },
                cache: false,
                dataType: 'json',
                success: function (response) {
                    console.log(response);
                    renderCalendar();
                },
                error: function (xhr) {
                    showMessage("Error");
                    console.log(xhr);
                }
            });

        } else {
            showMessage("Error, event not found");
        }

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
