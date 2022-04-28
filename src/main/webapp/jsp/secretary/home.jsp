<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Secretary</title>
    <meta charset="UTF-8">
    <jsp:include page="/jsp/include/style.jsp"/>
    <jsp:include page="../include/fullcalendar/style.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/secretary/calendar.css"/>">
</head>
<body>
<header>
    <jsp:include page="/jsp/secretary/include/headersecretary.jsp"/>
</header>

<div id="calendar" class="secretary-home-calendar">
</div>

<jsp:include page="../include/footer.jsp"/>

<div id="modal-details" class="modal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Details about this course</h5>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <button type="button" class="btn btn-primary btn-sm" data-dismiss="modal"
                                data-toggle="modal" data-target="#modal-notify-substitution">
                            Notify substitution
                        </button>
                    </div>
                    <hr>
                    <div class="row">
                        <button type="button" class="btn btn-warning btn-sm" data-dismiss="modal"
                                data-toggle="modal" data-target="#modal-change-schedule">
                            Change schedule
                        </button>
                    </div>
                    <hr>
                    <div class="row">
                        <button id="button-delete-lecturetimeslot" type="button" class="btn btn-danger btn-sm"
                                data-dismiss="modal">
                            Delete this lecture
                        </button>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal">
                    Close
                </button>
            </div>
        </div>
    </div>
</div>

<div id="modal-notify-substitution" class="modal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Notify substitution</h5>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <label for="substitute">Select a substitute</label>
                    <select id="substitute" name="substitute">
                        <c:forEach var="teacher" items="${teachers}">
                            <option value="${teacher.email}">
                                <c:out value="${teacher.name} ${teacher.surname}"/>
                            </option>
                        </c:forEach>
                    </select>
                    <label for="info-substitution">Extra info</label>
                    <p>Add some extra information that will be embedded in the mail to the subscribed users</p>
                    <textarea id="info-substitution" name="info-substitution"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="button-notify-substitution"
                        class="btn btn-primary btn-sm" data-dismiss="modal">
                    Notify substitution
                </button>
                <button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<div id="modal-change-schedule" class="modal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Change schedule</h5>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <label for="newStartTime">Specify a new start time</label>
                    <input type="time" id="newStartTime" name="newStartTime" min="09:00" max="20:00">
                    <label for="newDate">Specify a new date</label>
                    <input type="date" id="newDate" name="newDate">
                    <label for="newRoom">Specify a new room</label>
                    <select id="newRoom" name="newRoom">
                        <c:forEach var="room" items="${rooms}">
                            <option value="${room.name}"><c:out value="${room.name}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="button-change-schedule"
                        class="btn btn-primary btn-sm" data-dismiss="modal">Change schedule
                </button>
                <button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../include/scripts.jsp"/>
<jsp:include page="../include/fullcalendar/scripts.jsp"/>
<jsp:include page="../include/moment/scripts.jsp"/>

<script>

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
            right: 'prev,next today'
        },
        eventClick: function (info) {
            //console.log(info.event);
            //console.log(info.event.extendedProps);
            selectedEvent = info.event.extendedProps.customLTS;
            $("#modal-details").modal("show");
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
            url: "<c:url value="/secretary/rest/getalllecturetimeslot"/>",
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
                    startDate = new Date(Date.parse(lts.date + ' ' + lts.startTime + ' GMT+2'));
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
                console.log(xhr);
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
                    url: "<c:url value="/secretary/rest/deletelecturetimeslot"/>" + '?' + $.param({
                        "roomname": roomNane,
                        "date": date,
                        "starttime": startTime
                    }),
                    type: "DELETE",
                    success: function (response) {
                        console.log(response);
                        //TODO: Show some alert
                        renderCalendar();
                    },
                    error: function (xhr) {
                        console.log(xhr);
                    }
                });
            }

        } else {
            console.log("Error, event not found");
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
                url: "<c:url value="/secretary/rest/substitutionlecturetimeslot"/>",
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
                    //TODO: Show some alert
                    renderCalendar();
                },
                error: function (xhr) {
                    console.log(xhr);
                }
            });


        } else {
            console.log("Error, event not found");
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
            const newDate = $('#newDate').val();
            const newStartTime = moment($('#newStartTime').val(), ["hh:mm"]).format("hh:mm:ss A");

            $.ajax({
                url: "<c:url value="/secretary/rest/updatelecturetimeslot"/>",
                type: "POST",
                data: {
                    "oldRoomName": oldRoomName,
                    "oldDate": oldDate,
                    "oldStartTime": oldStartTime,
                    "newRoomName": newRoomName,
                    "newDate": newDate,
                    "newStartTime": newStartTime
                },
                cache: false,
                dataType: 'json',
                success: function (response) {
                    console.log(response);
                    //TODO: Show some alert
                    renderCalendar();
                },
                error: function (xhr) {
                    console.log(xhr);
                }
            });

        } else {
            console.log("Error, event not found");
        }

    });

</script>
<jsp:include page="/jsp/include/scripts.jsp"/>
</body>
</html>
