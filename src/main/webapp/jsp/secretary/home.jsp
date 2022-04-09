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
<jsp:include page="include/headersecreatry.jsp"/>


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
        initialView: 'dayGridMonth',
        initialDate: new Date(),        //Date of today
        themeSystem: "bootstrap",
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
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

    //AJAX request to fill the calendar
    $.get( "<c:url value="/secretary/rest/getalllecturetimeslot"/>").done(function( data ) {
        let jsondata = data;

        //Add events to the calendar
        let arrayOfLTS = JSON.parse(jsondata);

        for(const lts of arrayOfLTS){

            let event = new Object();
            event.title = lts.courseName;

            //Calculate dates
            let startDate = moment(lts.date, "MMM DD, YYYY", "it").format('YYYY-MM-DD');
            event.start = startDate;
            event.end = moment(startDate).add(2, 'hours');

            event.courseEditionId = lts.courseEditionId;
            event.roomName = lts.roomName;
            calendar.addEvent(event);
        }

        calendar.render();
    });

</script>

</html>
