<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Login</title>
    <jsp:include page="../include/bootstrap.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="../include/header.jsp"/>
<main>
    <form method="post" action="<c:url value="/login"/>" enctype="application/x-www-form-urlencoded">
        <label>Email : </label><input type="text" name="email" value="dev@dev.dev" autocomplete="on"><br/>
        <label>Password : </label><input type="password" name="password" value="CIAO" autocomplete="on"><br/>
        <button type="submit" autofocus>Login</button>
    </form>
    <a href="<c:url value="/password_forgot"/>">Password forgot</a>
    <c:choose>
        <c:when test="${message != null}">
            <p><c:out value="${message.message}"/></p>
        </c:when>
    </c:choose>
</main>
<jsp:include page="../include/footer.jsp"/>
<br>
</body>
</html>
