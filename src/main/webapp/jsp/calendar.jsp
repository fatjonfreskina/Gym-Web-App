<%@ page import="resource.view.WeeklyCalendar" %>
<%@ page import="resource.LectureTimeSlot" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Calendar</title>
</head>
<body>
    <jsp:include page="include/header.jsp"/><br>
    <form method="post" action="<c:url value="/register"/>" enctype="multipart/form-data">
        <label>Search Calendar for : </label><input type="date" name="calendar_date"><br/>
    </form>
    <c:out value="${weeklyCalendar}"/>
    <jsp:include page="include/footer.jsp"/>
</body>
</html>