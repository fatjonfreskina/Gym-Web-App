<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <title>Confirmed Registration</title>
    <meta charset="UTF-8">
    <jsp:include page="/jsp/include/style.jsp"/>
</head>
<body>

<c:choose>
    <c:when test="${message.error}">
        <p><c:out value="${message.message}"/></p>
    </c:when>
    <c:otherwise>
        <a href="<c:url value="/login"/>">Login</a><br>
        YOUR REGISTRATION HAS BEEN COMPLETED SUCCESSFULLY!!!
    </c:otherwise>
</c:choose>



<jsp:include page="/jsp/include/scripts.jsp"/>
</body>
</html>
