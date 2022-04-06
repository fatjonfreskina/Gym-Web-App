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
    <label>Email : </label><input type="text" name="email" ><br/>
    <label>Password : </label><input type="password" name="password"><br/>
    <button type="submit" >Login</button>
</form>
<jsp:include page="../include/footer.jsp"/><br>
</body>
</html>
