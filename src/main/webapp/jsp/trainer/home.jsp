<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>GWA - Trainer Home page </title>
    <meta charset="UTF-8">
    <jsp:include page="/jsp/include/style.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
    <jsp:include page="../include/fullcalendar/style.jsp"/>
</head>
<body>
<header>
    <jsp:include page="/jsp/trainer/include/headertrainer.jsp"/>
</header>
<main class="global-container">
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
        <jsp:useBean id="coursesStatus" scope="request" type="java.util.List"/>
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
    <div id="trainer__calendar"></div>
</main>
<jsp:include page="../include/footer.jsp"/>
<jsp:include page="/jsp/include/scripts.jsp"/>
<jsp:include page="../include/fullcalendar/scripts.jsp"/>
<jsp:include page="../include/moment/scripts.jsp"/>
<script src="<c:url value="/js/trainer/trainer-home.js"/>"></script>
</body>
</html>
