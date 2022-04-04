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
            <th>Date</th><th>Time</th><th>Room</th><th>Course</th>
        </tr>
        </thead>
        <c:set var="weeklyCalendar" value="${weeklyCalendar}"/>

        <%
            WeeklyCalendar calendar = (WeeklyCalendar) request.getAttribute("weeklyCalendar");

            for(int i = 0; i < calendar.getSizeX(); i += 1)
            {
                response.getWriter().println("<tr>");
                for(int j = 0; j < calendar.getSizeY(); j += 1)
                {
                    response.getWriter().println("<td>");
                    for (LectureTimeSlot item:
                         calendar.getTimeSlot(i, j)) {
                        response.getWriter().println(item.toString());
                    }
                    response.getWriter().println("</td>");
                }
                response.getWriter().println("</tr>");
            }
//            response.weeklyCalendar[i][j]
        %>

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
    </table>
    <jsp:include page="include/footer.jsp"/>
</body>
</html>
