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
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
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
<jsp:include page="/jsp/include/scripts.jsp"/>
</body>
</html>