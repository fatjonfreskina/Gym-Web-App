<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.2/main.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@6.1.1/css/all.min.css">

</head>
<body>
    <jsp:include page="/jsp/secretary/include/headersecretary.jsp"/>

    <div id="calendar" style="max-height:100%;max-width: 80%;margin: 40px auto;">
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
                                Change schedule</button>
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

                    </div>
                </div>
                <div class="modal-footer">
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

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>


</body>

<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.2/main.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.2/locales-all.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/moment@2.29.2/moment.min.js"></script>

<script>

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
                    startDate = new Date(lts.dateTime)
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
        if(confirm("Do you really want to delete?")){
            $.ajax({
                url: "<c:url value="/secretary/rest/deletelecturetimeslot"/>",
                type: "DELETE",
                data: {
                    "roomname" : "Energy",
                    "date" : "apr 11, 2022",
                    "starttime" : "10:00:00 AM"
                },
                cache: false,
                dataType: 'json',
                success: function (response) {
                    console.log(response);

                    //If the request has been successful render the new calendar
                    renderCalendar();
                },
                error: function (xhr) {
                    console.log(xhr);
                }
            });
        }
    });



</script>

</html>
