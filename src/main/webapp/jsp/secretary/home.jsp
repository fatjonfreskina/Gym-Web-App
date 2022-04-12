<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.2/main.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@6.1.1/css/all.min.css">

</head>
<body>
<jsp:include page="../include/header.jsp"/>


<div id="calendar" style="max-width: 1100px;margin: 40px auto;">


<jsp:include page="../include/footer.jsp"/>
</body>

<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/combine/npm/fullcalendar@5.10.2,npm/fullcalendar@5.10.2/locales-all.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.2/main.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.2/locales-all.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/moment@2.29.2/moment.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/moment@2.29.2/min/locales.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/moment@2.29.2/min/moment-with-locales.min.js"></script>


<script>

    //Construct the calendar

    let calendarEl = document.getElementById('calendar');

    let calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'timeGridWeek',
        initialDate: new Date(),        //Date of today
        themeSystem: "bootstrap",
        headerToolbar: {
            left: '',
            center: 'title',
            right: 'prev,next today'
        },
        eventClick: function(info) {
            alert('Event: ' + info.event.title + ' - Take a look at the console');
            console.log(info.event.extendedProps);
            console.log(info.event);
            //alert('Coordinates: ' + info.jsEvent.pageX + ',' + info.jsEvent.pageY);
            //alert('View: ' + info.view.type);

            // change the border color just for fun
            info.el.style.borderColor = 'red';
        }
    });

    //Attach render calendar to button for week change
    $('body').on('click', 'button.fc-next-button', renderCalendar);
    $('body').on('click', 'button.fc-prev-button', renderCalendar);

    //Renders the calendar of this week
    function renderCalendar(){

        let start = moment(calendar.view.activeStart).format('YYYY-MM-DD');
        let end = moment(calendar.view.activeEnd).format('YYYY-MM-DD');

        $.ajax({
            url: "<c:url value="/secretary/rest/getalllecturetimeslot"/>",
            data: {
                "start": start,
                "end": end,
            },
            cache: false,
            type: "GET",
            dataType: 'json',
            success: function(response) {

                //Remove all the events
                calendar.removeAllEvents();

                for(const lts of response){

                    console.log(lts);

                    let event = new Object();
                    event.title = lts.courseName;

                    //Calculate dates
                    let startDate = moment(lts.date, "MMM DD, YYYY", "it");
                    startDate = moment(startDate).add(moment.duration(moment(lts.startTime, "hh:mm A")).asMinutes(), 'minutes');
                    startDate = moment(startDate).format();
                    console.log(startDate);

                    event.start = startDate;
                    event.end = moment(startDate).add(2, 'hours');

                    event.courseEditionId = lts.courseEditionId;
                    event.roomName = lts.roomName;
                    calendar.addEvent(event);

                }

                calendar.render();
            },
            error: function(xhr) {
                console.log(xhr);
            }
        });

    }

    renderCalendar();


</script>

</html>
