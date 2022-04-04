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
            <th>Date</th><th>Time</th><th>Room</th><th>Course</th>
        </tr>
        </thead>
<%--        <c:set var="last_printed" value=""/>--%>
        <c:forEach var="lectureTimeSlot" items="${lectureTimeSlotList}">
                <tr>
                    <td><c:out value="${lectureTimeSlot.date}"/></td>
                    <td><c:out value="${lectureTimeSlot.startTime}"/></td>
                    <td><c:out value="${lectureTimeSlot.roomName}"/></td>
                    <td><c:out value="${lectureTimeSlot.courseName}"/></td>
                </tr>
<%--            <c:set var="last_printed" value="${subscriptionType.courseName}"/>--%>
        </c:forEach>
    </table>
    <jsp:include page="include/footer.jsp"/>
</body>
</html>
