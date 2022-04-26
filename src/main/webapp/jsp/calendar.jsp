<%@ page import="resource.view.WeeklyCalendar" %>
<%@ page import="resource.LectureTimeSlot" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Calendar</title>
    <jsp:include page="include/bootstrap.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/global_style.css"/>">
</head>
<body>
<header>
    <jsp:include page="include/header.jsp"/>
</header>
<main class="global-container">
    <form method="post" action="<c:url value="/register"/>" enctype="multipart/form-data">
        <label>Search Calendar for : </label><input type="date" name="calendar_date"><br/>
    </form>
    <c:out value="${weeklyCalendar}"/>
</main>
<jsp:include page="include/footer.jsp"/>
</body>
</html>