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
<c:forEach var="role" items="${roles}">
    <c:if test="${role=='trainee'}">
        <a href="<c:url value="/trainee"/>">Trainee</a>
    </c:if>
    <c:if test="${role=='trainer'}">
        <a href="<c:url value="/trainer"/>">Trainer</a>
    </c:if>
    <c:if test="${role=='secretary'}">
        <a href="<c:url value="/secretary"/>">Secretary</a>
    </c:if>
</c:forEach>
</body>
</html>
