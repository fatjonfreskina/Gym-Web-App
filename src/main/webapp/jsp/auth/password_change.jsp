<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <title>Change the password</title>
    <jsp:include page="../include/bootstrap.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/global_style.css"/>">
</head>
<body>
<header>
    <jsp:include page="../include/header.jsp"/>
</header>
<main class="global-container">
    <form method="POST" action="<c:url value="/password_change"/>" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="token" value="${token}">
        <label for="password">New password:</label><br>
        <input type="password" id="password" name="password" placeholder="Insert your new password here" autoComplete="true"><br>
        <label for="password_confirm">Confirm password:</label><br>
        <input type="password" id="password_confirm" name="confirm_password" placeholder="Confirm your new password here" autoComplete="true"><br>
        <input type="submit" value="Submit">
    </form>
</main>
<c:choose>
    <c:when test="${message != null}">
        <p><c:out value="${message.message}"/></p>
    </c:when>
</c:choose>
<jsp:include page="../include/footer.jsp"/>
</body>
</html>
