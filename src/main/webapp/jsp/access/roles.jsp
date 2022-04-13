<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Andrea Pasin
  Date: 06/04/2022
  Time: 20:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Roles</title>
</head>
<body>
<c:forEach var="role" items="${sessionScope.roles}">
    <c:if test="${role=='trainee'}">
        <form method="post" action="<c:url value="/access/roles"/>">
            <input type="submit" name="trainee" value="Trainee">
        </form>

    </c:if>
    <c:if test="${role=='trainer'}">
        <form method="post" action="<c:url value="/access/roles"/>">
            <input type="submit" name="trainer" value="Trainer">
        </form>
    </c:if>
    <c:if test="${role=='secretary'}">
        <form method="post" action="<c:url value="/access/roles"/>">
            <input type="submit" name="secretary" value="Secretary">
        </form>
    </c:if>

</c:forEach>
</body>
</html>
