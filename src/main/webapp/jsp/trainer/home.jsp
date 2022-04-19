<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>GWA - Trainer Home page </title>
    <jsp:include page="../include/bootstrap.jsp"/>
    <jsp:include page="../include/fullcalendar.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/jsp/trainer/include/headertrainer.jsp"/>

<div class="overflow-auto trainer__shift">
    <div class="container trainer__container">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item">Home</li>
                <li class="breadcrumb-item active"><a href="<c:url value="/trainer"/>" aria-current="page">Trainer</a>
                </li>
            </ol>
            <hr/>
        </nav>
        <h1>Courses you teach</h1>
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Course Name</th>
                <th scope="col">Number of Trainees</th>
                <th scope="col">Lesson n°</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="course" items="${coursesStatus}" varStatus="loop">
                <tr>
                    <th scope="row">${loop.index+1}</th>
                    <td><c:out value="${course.getCourseName()}"/>_<c:out
                            value="${course.getCourseEdition()}"/></td>
                    <td><c:out value="${course.getTraineesNumber()}"/></td>
                    <td><c:out value="${course.getCurrentLessonNumber()}"/>/<c:out
                            value="${course.getTotalLessonsNumber()}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <br>
        <div id="trainer__calendar"/>
    </div>
</div>
<%-- OLD CALENDAR
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
</table>--%>
<%--<c:forEach var="lts" items="${weeklyLTSs}">
    <c:out value="${lts}"/>
    <br>
</c:forEach>--%>
<jsp:include page="../include/footer.jsp"/>

<script src="${pageContext.request.contextPath}/js/trainer-home.js"></script>
</body>
</html>
