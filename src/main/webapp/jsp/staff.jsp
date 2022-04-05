<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Staff</title>
</head>
<body>
    <jsp:include page="/jsp/include/header.jsp"/><br>
    <c:if test="${not empty trainerlist}">
        <ul>
            <c:forEach var="trainer" items="${trainerlist}">
                <li>
                    <b><c:out value="${trainer.person.surname}"/> <c:out value="${trainer.person.name}"/>:</b> <c:out value="${trainer.teaches.courseName}"/>
                </li>
            </c:forEach>
        </ul>
    </c:if>
    <jsp:include page="/jsp/include/footer.jsp"/>
</body>
</html>
