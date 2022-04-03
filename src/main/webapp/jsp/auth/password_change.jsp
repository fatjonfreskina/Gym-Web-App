<%--
  author: Riccardo Forzan
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Change the password</title>
</head>
<body>

<h1>Change the password</h1>

<form method="POST" action="<c:url value="/password_change"/>" enctype="multipart/form-data">
    <label for="password">New password:</label><br>
    <input type="password" id="password" name="password" placeholder="Insert your new password here"><br>
    <label for="password-confirm">Confirm password:</label><br>
    <input type="password" id="password-confirm" name="password-confirm" placeholder="Confirm your new password here"><br>
    <input type="submit" value="Submit">
</form>

</body>
</html>
