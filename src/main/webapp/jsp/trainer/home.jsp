<%@ page import="java.util.Calendar" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>GWA - Trainer Home page </title>
</head>
<body>
<jsp:include page="../include/header.jsp"/>
<br>
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
    <c:forEach var="course" items="${courseStatuses}">
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
<br><br>
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
</table>
<%--<c:forEach var="lts" items="${LTSsInThisWeek}">
    <c:out value="${lts}"/>
    <br>
</c:forEach>--%>
<br>
<jsp:include page="../include/footer.jsp"/>
</body>
</html>
