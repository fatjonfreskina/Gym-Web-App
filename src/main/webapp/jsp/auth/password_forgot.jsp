<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <title>Ask for password reset</title>
    <jsp:include page="../include/bootstrap.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/global_style.css"/>">
</head>
<body>
<header>
    <jsp:include page="../include/header.jsp"/>
</header>
<main class="global-container">
    <form method="POST" action="<c:url value="/password_forgot"/>" enctype="application/x-www-form-urlencoded">
        <label for="email">Email address:</label><br>
        <input type="email" id="email" name="email" placeholder="Insert your email here" autoComplete="true"><br>
        <input type="submit" value="Submit">
    </form>
    <c:choose>
        <c:when test="${message != null}">
            <p><c:out value="${message.message}"/></p>
        </c:when>
    </c:choose>
</main>
<jsp:include page="../include/footer.jsp"/>
</body>
</html>
