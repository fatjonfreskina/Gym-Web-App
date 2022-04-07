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
    <table>
        <thead>
        <tr>
            <th>Time</th><th>Monday</th><th>Tuesday</th><th>Wednesday</th><th>Thursday</th><th>Friday</th><th>Saturday</th><th>Sunday</th>
        </tr>
        </thead>
        <c:out value="${weeklyCalendar}"/>
    </table>






<%--        <c:forEach var = "list" items="${weeklyCalendar}">--%>
<%--            <tr>--%>
<%--                <c:forEach var="lectureTimeSlot" items="${list}">--%>
<%--                        <td><c:out value="${lectureTimeSlot.date}"/></td>--%>
<%--                        <td><c:out value="${lectureTimeSlot.startTime}"/></td>--%>
<%--                        <td><c:out value="${lectureTimeSlot.roomName}"/></td>--%>
<%--                        <td><c:out value="${lectureTimeSlot.courseName}"/></td>--%>
<%--                </c:forEach>--%>
<%--            </tr>--%>
<%--        </c:forEach>--%>

    <jsp:include page="include/footer.jsp"/>
</body>
</html>