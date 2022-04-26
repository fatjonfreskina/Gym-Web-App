<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <title>Ask for password reset</title>
</head>
<body>
<h1>Ask for password reset</h1>

<form method="POST" action="<c:url value="/password_forgot"/>" enctype="application/x-www-form-urlencoded">
    <label for="email">Email address:</label><br>
    <input type="text" id="email" name="email" placeholder="Insert your email here" autoComplete="true"><br>
    <input type="submit" value="Submit">
</form>

<c:choose>
    <c:when test="${message != null}">
        <p><c:out value="${message.message}"/></p>
    </c:when>
</c:choose>

</body>
</html>
