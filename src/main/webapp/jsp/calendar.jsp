<%@ page import="resource.view.WeeklyCalendar" %>
<%@ page import="resource.LectureTimeSlot" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Calendar</title>
    <meta charset="UTF-8">
    <jsp:include page="/jsp/include/style.jsp"/>
    <jsp:include page="include/fullcalendar/style.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/secretary/calendar.css"/>">
</head>
<body>
<header>
    <jsp:include page="include/header.jsp"/>
</header>
<main class="global-container">
    <div id="calendar">
    </div>
</main>
<jsp:include page="include/scripts.jsp"/>
<jsp:include page="include/fullcalendar/scripts.jsp"/>
<jsp:include page="include/moment/scripts.jsp"/>
<script src="<c:url value="/js/calendar.js"/>"></script>
</body>
</html>