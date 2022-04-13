<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <title>Staff</title>
</head>
<body>
    <jsp:include page="/jsp/include/header.jsp"/><br>
    <c:if test="${not empty trainerlist}">
        <ul>
            <c:forEach var="trainer" items="${trainerlist}">
                <li>
                    <img src="<c:url value="/images/staff/${trainer.person.name}-${trainer.person.surname}.jpg"></c:url>" width="15%" height="15%">
                    <b><c:out value="${trainer.person.surname}"/> <c:out value="${trainer.person.name}"/>:</b>
                    <c:forEach var="teach" items="${trainer.teaches}" varStatus="loop">
                        <c:out value="${teach.courseName}"/>
                        <c:if test="${!loop.last}">,</c:if>
                    </c:forEach>
                </li>
            </c:forEach>
        </ul>
    </c:if>
    <jsp:include page="/jsp/include/footer.jsp"/>
</body>
</html>
