<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Staff</title>
</head>
<body>
    <jsp:include page="/jsp/include/header.jsp"/><br>
    <c:out value ="1"/>
    <c:if test='$(not empty trainerlist'>
        <c:out value ="2"/>
        <ul>
        <c:forEach var="trainer" items="${trainerlist}">
            <li>
                <c:out value="${trainer.p.surname}"/> <c:out value="${trainer.p.name}"/>
            </li>
        </c:forEach>
        </ul>
    </c:if>
    <jsp:include page="/jsp/include/footer.jsp"/>
</body>
</html>
