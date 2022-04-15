<%@ page import="java.util.Calendar" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>GWA - Trainer Home page </title>

    <!-- for fullcalendar -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.2/main.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@6.1.1/css/all.min.css">
</head>
<body>
<div class="container">
    <c:import url="/jsp/include/icon.jsp"/>
    <jsp:include page="/jsp/trainer/include/headertrainer.jsp"/>
    <h1>Courses you teach</h1>
    <table>
        <thead>
        <tr>
            <th>Course Name</th>
            <th>Number of Trainees</th>
            <th>Lesson n°</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="course" items="${coursesStatus}">
            <tr>
                <td><c:out value="${course.getCourseName()}"/>_<c:out
                        value="${course.getCourseEdition()}"/></td>
                <td><c:out value="${course.getTraineesNumber()}"/></td>
                <td><c:out value="${course.getCurrentLessonNumber()}"/>/<c:out
                        value="${course.getTotalLessonsNumber()}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br>
    <div id="calendar" style="max-height:100%;max-width: calc(100% - 20px);margin: 40px auto;"/>
</div>
<%-- OLD CALENDAR
<c:if test="${empty addWeeks}">
    <c:set var="addWeeks" value="${0}"/>
</c:if>
<a href="<c:url value="/trainer?addWeeks=${addWeeks-1}"/>">PreviousWeek</a>
From: <c:out value="${week[0]}"/> To: <c:out value="${week[6]}"/>
<a href="<c:url value="/trainer?addWeeks=${addWeeks+1}"/>">NextWeek</a>
<table>
    <thead>
    <tr>
        <th>TIME</th>
        <th>MONDAY</th>
        <th>TUESDAY</th>
        <th>WEDNESDAY</th>
        <th>THURSDAY</th>
        <th>FRIDAY</th>
        <th>SATURDAY</th>
        <th>SUNDAY</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="slotRow" items="${slots}">
        <tr>
            <c:forEach var="slot" items="${slotRow}">
                <td><c:out value="${slot}"/>&nbsp;&nbsp;&nbsp;</td>
            </c:forEach>
        </tr>
    </c:forEach>
    </tbody>
</table>--%>
<%--<c:forEach var="lts" items="${weeklyLTSs}">
    <c:out value="${lts}"/>
    <br>
</c:forEach>--%>
<br>
<jsp:include page="../include/footer.jsp"/>
</body>

<%-- scripts for fullcalendar --%>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.2/main.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.2/locales-all.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/moment@2.29.2/moment.min.js"></script>

<script>

    //Construct the calendar
    let calendarEl = document.getElementById('calendar');
    let addWeeks = 0;
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
        eventClick: function (info) {
            //console.log(info.event);
            //console.log(info.event.extendedProps);
            //$("#modal-details").modal("show");
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
        //Perform the AJAX request to fill the calendar
        $.ajax({
            url: "<c:url value="/rest/trainer/weekly_calendar"/>",
            data: {
                "addWeeks": addWeeks,
            },
            cache: false,
            type: "GET",
            dataType: 'json',
            success: function (response) {
                //Remove all the events
                //console.log("Response", response);
                calendar.removeAllEvents();
                //Iterate over all the elements in the response
                for (const lts of response) {
                    //console.log("lts", lts);
                    //Create an event object
                    let event = {};
                    //Calculate dates
                    //let start = moment(calendar.view.activeStart).format('YYYY-MM-DD');

                    startDate = new Date(Date.parse(lts.date + ' ' + lts.startTime + ' GMT+2'))
                    event.start = startDate;
                    event.end = moment(startDate).add(2, 'hours').toDate();
                    //Set title and background color based on the title
                    event.title = lts.courseName;
                    event.backgroundColor = GetColorOfCourse(lts.courseName);
                    //Set some additional parameters
                    event.courseEditionId = lts.courseEditionId;
                    event.roomName = lts.roomName;
                    //Add the element to the calendar
                    calendar.addEvent(event);

                    //console.log("event", event);
                }
                //Render the new calendar
                calendar.render();
            },
            error: function (xhr) {
                console.log(xhr);
            }
        });
    }

    function nextWeek() {
        addWeeks = addWeeks + 1;
        renderCalendar();
    }

    function prevWeek() {
        addWeeks = addWeeks - 1;
        renderCalendar();
    }

    function goToToday() {
        addWeeks = 0;
        renderCalendar();
    }

    //Initial render when page loaded
    renderCalendar();

    //Attach render calendar to button for week change
    $('body').on('click', 'button.fc-next-button', nextWeek);
    $('body').on('click', 'button.fc-prev-button', prevWeek);
    //$('body').on('click', 'button.fc-today-button', goToToday());


</script>
</html>
