<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Login</title>
</head>
<body>
<jsp:include page="../include/header.jsp"/><br>
<form method="post" action="<c:url value="/login"/>" enctype="application/x-www-form-urlencoded">
    <label>Email : </label><input type="text" name="email" value="" autocomplete="on"><br/>
    <label>Password : </label><input type="password" name="password" value="" autocomplete="on"><br/>
    <%-- Forwards the redirect parameter to LoginServlet --%>
    <input type="hidden" name="redirect" value="${redirect}">
    <button type="submit" >Login</button>
</form>

<a href="<c:url value="/password_forgot"/>">Password forgot</a>

<c:choose>
    <c:when test="${message != null}">
        <p><c:out value="${message.message}"/></p>
    </c:when>
</c:choose>
<jsp:include page="../include/footer.jsp"/><br>
</body>
</html>
