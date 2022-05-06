<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Manage Attendance</title>
    <meta charset="UTF-8">
    <jsp:include page="/jsp/include/style.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">

</head>
<body>
<header>
    <jsp:include page="/jsp/trainer/include/headertrainer.jsp"/>
</header>
<main class="global-container">
    <c:choose>
        <c:when test="${empty message}">
            <jsp:useBean id="trainerAttendance" scope="request" type="resource.rest.TrainerAttendance"/>
            <div class="">
                <h1>Manage Attendance</h1>
                <div class="container">
                    <div class="d-flex justify-content-start align-items-start">
                        <h3 class="mr-5">Lecture:</h3>
                        <h3>
                            <c:out value="${trainerAttendance.lecture.courseName}"/>
                            <c:out value="${trainerAttendance.lecture.courseEditionId}"/>
                        </h3>
                    </div>
                    <div class="d-flex justify-content-start align-items-start">
                        <h3 class="mr-5">Details: </h3>
                        <h3>
                            <c:out value="${trainerAttendance.lecture.roomName}"/>
                            <c:out value="${trainerAttendance.lecture.date}"/>
                            <c:out value="${trainerAttendance.lecture.startTime}"/>
                        </h3>
                    </div>
                </div>
            </div>
            <div class="trainer__manage d-flex flex-wrap flex-lg-nowrap">
                <div class="trainer__manage__present w-100 py-2 px-1 p-xl-4">
                    <h1>Present </h1>
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
                <div class="trainer__manage__absent w-100 py-2 px-1 p-xl-4">
                    <h1>Absent </h1>
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
            </div>
        </c:when>
    </c:choose>
    <jsp:include page="/jsp/include/message.jsp"/>
</main>
<jsp:include page="../include/footer.jsp"/>
<jsp:include page="/jsp/include/scripts.jsp"/>
<script src="<c:url value="/js/message-delay.js"/>"></script>
</body>
</html>