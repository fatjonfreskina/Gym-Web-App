$(document).ready(function () {

    /**
     * Manages a response object returned by the server.
     * If the object given does not have the two expected fields returns an alert with a default message and prints
     * to the console the object that is not compliant with the standardA
     * @param response object returned by the server
     */
    function manageServerResponse(response) {
        if (response.isError === undefined || response.message === undefined) {
            showWarningMessage("Error while parsing the response");
            console.log(response);
            return;
        }
        if (response.isError === true) {
            showWarningMessage(response.message);
        } else {
            showSuccessMessage(response.message);
        }
    }

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
        eventClick: ShowActionsModal
    });


    /**
     * Shows the modal to select which action you want to perform
     * @param info event that has been clicked
     */
    function ShowActionsModal(info) {
        selectedEvent = info.event.extendedProps.customLTS;

        let div_substitution = $('#modal-div-substitution')
        if(selectedEvent.substitution !== undefined) {
            div_substitution.show();
            $('#modal-course-substitution').text("There is a substitute for this lecture!");
        }else{
            div_substitution.hide();
        }

        //Load data of this lecture
        $('#modal-course-name').text(selectedEvent.courseName);
        $('#modal-course-date').text(selectedEvent.customdate);
        $('#modal-course-startTime').text(selectedEvent.customstartTime);
        $('#modal-course-roomName').text(selectedEvent.roomName);

        //Fill the modal fields with the data
        $('#newStartTime').val(selectedEvent.customstartTime)
        $('#newDate').val(selectedEvent.customdate)
        $('#newRoom').val(selectedEvent.roomName)

        $("#modal-actions-course").modal("show");
    }

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

                    //console.log(moment(lts.dateTime).toDate());
                    //startDate = new Date(lts.dateTime);
                    event.start = startDate;
                    event.end = moment(startDate).add(2, 'hours').toDate();
                    //Set title and background color based on the title
                    event.title = lts.courseName;
                    event.backgroundColor = GetColorOfCourse(lts.courseName);

                    let lectureTimeSlot = {};
                    lectureTimeSlot.courseEditionId = lts.courseEditionId;
                    lectureTimeSlot.courseName = lts.courseName;
                    lectureTimeSlot.substitution = lts.substitution;
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
                //console.log(xhr);
                showWarningMessage("Error while rendering the calendar");
            }
        });
    }

    //Override default buttons behaviour
    $('body').on('click', 'button.fc-next-button', renderCalendar);
    $('body').on('click', 'button.fc-prev-button', renderCalendar);

    //Initial render when page loaded
    renderCalendar();

    $("#button-delete-lecturetimeslot").click(() => {
        if (selectedEvent !== undefined) {

            const roomName = selectedEvent.roomName;
            const date = selectedEvent.customdate;
            const startTime = selectedEvent.customstartTime;

            if (confirm("Do you really want to delete?")) {
                $.ajax({
                    url: "secretary/rest/deletelecturetimeslot" + '?' + $.param({
                        "roomname": roomName,
                        "date": date,
                        "starttime": startTime
                    }),
                    type: "DELETE",
                    success: function (response) {
                        manageServerResponse(response);
                        renderCalendar();
                    },
                    error: function (xhr) {
                        showWarningMessage("Internal error");
                        //console.log(xhr);
                    }
                });
            }
        } else {
            showWarningMessage("Event not found");
        }
    });

    $("#button-notify-substitution").click(() => {

        if (selectedEvent !== undefined) {

            const roomName = selectedEvent.roomName;
            const date = selectedEvent.customdate;
            const startTime = selectedEvent.customstartTime;
            const substituteEmail = $("#substitute").val();
            const note = $("#info-substitution").val();

            $.ajax({
                url: "secretary/rest/substitutionlecturetimeslot",
                type: "POST",
                data: {
                    "roomname": roomName,
                    "date": date,
                    "starttime": startTime,
                    "substitute": substituteEmail,
                    "notes": note
                },
                cache: false,
                dataType: 'json',
                success: function (response) {
                    manageServerResponse(response);
                    renderCalendar();
                },
                error: function (xhr) {
                    showWarningMessage("Internal error");
                    //console.log(xhr);
                }
            });

        } else {
            showWarningMessage("Event not found");
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
            const newDateFormatted = newDate.format("YYYY-MM-DD");
            const newStartTime = moment($('#newStartTime').val(), ["hh:mm"]);
            const newStartTimeFormatted = newStartTime.format("HH:mm:ss");

            const now = moment();

            //Check newDate >= actual date
            if (!newDate.isSameOrAfter(now, 'day')) {
                showWarningMessage("Provided date is invalid");
                return;
            }

            //If the date is the same of today check the time given is after now
            if (newDate.isSame(now, 'day') && newStartTime.isBefore(now.format("HH:mm:ss"))) {
                showWarningMessage("Provided time is invalid");
                return;
            }

            $.ajax({
                url: "secretary/rest/updatelecturetimeslot",
                type: "POST",
                data: {
                    "oldRoomName": oldRoomName,
                    "oldDate": oldDate,
                    "oldStartTime": oldStartTime,
                    "newRoomName": newRoomName,
                    "newDate": newDateFormatted,
                    "newStartTime": newStartTimeFormatted
                },
                cache: false,
                dataType: 'json',
                success: function (response) {
                    manageServerResponse(response);
                    renderCalendar();
                },
                error: function (xhr) {
                    showWarningMessage("Internal error");
                }
            });

        } else {
            showWarningMessage("Event not found");
        }

    });

});
