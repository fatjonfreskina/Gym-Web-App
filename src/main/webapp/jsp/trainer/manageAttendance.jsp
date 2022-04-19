<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Manage Attendance</title>
    <jsp:include page="../include/bootstrap.jsp"/>
    <jsp:include page="../include/fullcalendar.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/jsp/trainer/include/headertrainer.jsp"/>
<div class="container trainer__shift h-100">
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item">Home</li>
            <li class="breadcrumb-item"><a href="<c:url value="/trainer"/>" aria-current="page">Trainer</a></li>
            <li class="breadcrumb-item active" aria-current="page"><a href="<c:url value="/trainer/attendance"/>"
                                                                      aria-current="page">Attendace</a></li>
        </ol>
        <hr/>
    </nav>
    <c:choose>
        <c:when test="${empty error}">
            <h1>Lecture : </h1>
            <h2><c:out value="${trainerAttendance.getLecture()}"/></h2>
            <br>
            <h1>Present: </h1>
            <c:forEach var="reservation" items="${trainerAttendance.getReservations()}">
                <form action="<c:url value="/trainer/attendance"/>" method="post">
                    <input type="hidden" name="action" value="doDelete"/>
                    <c:out value="${reservation}"/>
                    <input type="hidden" name="reservation" value="${reservation}"/>
                    <input type="submit" value="REMOVE"/>
                </form>
                <br>
            </c:forEach>

            <h1>Absent: </h1>
            <c:forEach var="subscription" items="${trainerAttendance.getSubscriptions()}">
                <form action="<c:url value="/trainer/attendance"/>" method="post">
                    <input type="hidden" name="action" value="post"/>
                    <c:out value="${subscription}"/>
                    <input type="hidden" name="subscription" value="${subscription}"/>
                    <input type="submit" value="ADD"/>
                </form>
                <br>
            </c:forEach>
        </c:when>
        <c:when test="${!empty error}">
            Error:
            <br>
            <c:out value="${error}"/>
            <br>
        </c:when>
    </c:choose>
</div>

<jsp:include page="../include/footer.jsp"/>
</body>
</html>