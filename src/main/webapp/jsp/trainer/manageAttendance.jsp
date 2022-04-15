<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manage Attendance</title>
</head>
<body>
<c:import url="/jsp/include/icon.jsp"/>
<jsp:include page="/jsp/trainer/include/headertrainer.jsp"/>

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
            <form value="${course.name}"><c:out value="${course.name}"/></form>
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
            <form value="${course.name}"><c:out value="${course.name}"/></form>
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


<jsp:include page="../include/footer.jsp"/>
</body>
</html>