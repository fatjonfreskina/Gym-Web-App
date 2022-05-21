<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>GWA - Trainer Home page </title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width">
    <jsp:include page="/jsp/include/favicon.jsp"/>
    <jsp:include page="/jsp/include/style.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
    <jsp:include page="../include/fullcalendar/style.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/trainer/trainer.css"/>">
</head>
<body>
<header>
    <jsp:include page="/jsp/trainer/include/headertrainer.jsp"/>
</header>
<main class="global-container trainer__container">

    <div id="alert-warning" class="alert alert-warning alert-dismissible fade show" role="alert" style="display: none;">
        <p id="alert-warning-message-body" class="alert-box-message">
        </p>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>

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

    <div id="modal-info-course" class="modal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="lecture-title">Lecture</h5>
                </div>
                <div class="modal-body">
                    <div class="container text-center" id="lecture-info">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal">
                        Close
                    </button>
                </div>
            </div>
        </div>
    </div>

</main>
<jsp:include page="../include/footer.jsp"/>
<jsp:include page="/jsp/include/scripts.jsp"/>
<jsp:include page="../include/fullcalendar/scripts.jsp"/>
<jsp:include page="../include/moment/scripts.jsp"/>
<script src="<c:url value="/js/trainer/trainer-home.js"/>"></script>
</body>
</html>
