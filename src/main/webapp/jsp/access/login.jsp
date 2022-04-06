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
    <form action="login" method="post">
        <div class="container">
            <label> <b>Username</b> </label> <br>
            <input type="text" placeholder="Enter Email" name="email" required><br>
            <label><b>Password</b></label><br>
            <input type="password" placeholder="Enter Password" name="password" required><br>
            <button type="submit">Login</button><br>
            <label>
                <input type="checkbox" checked="checked" name="remember"> Remember me
            </label>
        </div>
        <div class="container" style="background-color:#f1f1f1">
            <span class="psw">Forgot <a href="#">password?</a></span>
        </div>
    </form>
    <jsp:include page="../include/footer.jsp"/><br>
</body>
</html>
