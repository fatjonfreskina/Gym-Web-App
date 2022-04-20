<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Manage Attendance</title>
    <jsp:include page="../include/bootstrap.jsp"/>
    <jsp:include page="../include/fullcalendar.jsp"/>
    <%--DATATABLES--%>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.css">
    <script type="text/javascript" charset="utf8"
            src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.js"></script>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="${pageContext.request.contextPath}/js/trainer-attendance.js"></script>
</head>
<body>
<jsp:include page="/jsp/trainer/include/headertrainer.jsp"/>
<div class="d-flex flex-column h-100">
    <div class="container trainer__shift">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item">Home</li>
                <li class="breadcrumb-item"><a href="<c:url value="/trainer"/>" aria-current="page">Trainer</a></li>
                <li class="breadcrumb-item active" aria-current="page"><a href="<c:url value="/trainer/attendance"/>"
                                                                          aria-current="page">Attendance</a></li>
            </ol>
            <hr/>
        </nav>
        <c:choose>
            <c:when test="${empty error}">
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
            <c:when test="${!empty error}">
                <jsp:useBean id="error" scope="request" type="java.lang.String"/>
                Error:
                <br>
                <c:out value="${error}"/>
                <br>
            </c:when>
        </c:choose>
    </div>

    <div class="mt-auto">
        <jsp:include page="../include/footer.jsp"/>
    </div>
</div>
</body>
</html>