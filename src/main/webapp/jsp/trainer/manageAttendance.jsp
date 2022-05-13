<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Manage Attendance </title>
    <meta charset="UTF-8">
    <jsp:include page="../include/style.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/trainer/trainer.css"/>">
    <jsp:include page="../include/datatables/style.jsp"/>
</head>
<body>
<header>
    <jsp:include page="/jsp/trainer/include/headertrainer.jsp"/>
</header>
<main class="global-container">
    <c:choose>
        <c:when test="${empty message}">
            <jsp:useBean id="trainerAttendance" scope="request" type="resource.rest.TrainerAttendance"/>
            <div class="row">
                <h1 class="col-sm-3">Lecture:</h1>
                <h1 class="col-sm-9">
                    <c:out value="${trainerAttendance.lecture.courseName}"/>
                    <c:out value="${trainerAttendance.lecture.courseEditionId}"/>
                </h1>
            </div>
            <div class="row">
                <h1 class="col-sm-3">Details: </h1>
                <h1 class="col-sm-9">
                    <c:out value="${trainerAttendance.lecture.roomName}"/>
                    <c:out value="${trainerAttendance.lecture.date}"/>
                    <c:out value="${trainerAttendance.lecture.startTime}"/>
                </h1>
            </div>
            <br/>
            <div class="row">
                <h3>Present</h3>
                <div class="table-responsive">
                    <table id="reservationsTABLE" class="table table-striped w-100">
                        <thead>
                        <tr>
                            <th scope="col">Name</th>
                            <th scope="col">Surname</th>
                            <th scope="col">EMAIL</th>
                            <th scope="col" class="text-end">Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
            <br/>
            <div class="row">
                <h3>Absent</h3>
                <div class="table-responsive">
                    <table id="subscriptionsTABLE" class="table table-striped w-100">
                        <thead>
                        <tr>
                            <th scope="col">Name</th>
                            <th scope="col">Surname</th>
                            <th scope="col">EMAIL</th>
                            <th scope="col" class="text-end">Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:when>
    </c:choose>
    <jsp:include page="/jsp/include/message.jsp"/>
</main>
<jsp:include page="../include/footer.jsp"/>
<jsp:include page="../include/scripts.jsp"/>
<jsp:include page="../include/datatables/script.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"></script>
<script src="<c:url value="/js/trainer/trainer-attendance.js"/>"></script>
<script src="<c:url value="/js/message-delay.js"/>"></script>
</body>
</html>