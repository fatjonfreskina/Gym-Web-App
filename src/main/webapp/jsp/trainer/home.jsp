<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>GWA - Trainer Home page </title>
    <jsp:include page="../include/bootstrap.jsp"/>
    <jsp:include page="../include/fullcalendar.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link id="contextPathHolder" data="${pageContext.request.contextPath}"/>
</head>
<body>
<header>
    <jsp:include page="/jsp/trainer/include/headertrainer.jsp"/>
</header>
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
        <br>
        <div id="trainer__calendar"></div>
    </div>
</div>
<jsp:include page="../include/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/trainer-home.js"></script>
</body>
</html>
