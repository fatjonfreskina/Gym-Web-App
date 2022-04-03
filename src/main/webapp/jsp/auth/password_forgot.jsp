<%--
  author: Riccardo Forzan
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Ask for password reset</title>
</head>
<body>
<h1>Ask for password reset</h1>

<form method="POST" action="<c:url value="/password_forgot"/>" enctype="multipart/form-data">
    <label for="email">First name:</label><br>
    <input type="text" id="email" name="email" placeholder="Insert your email here" autoComplete="true"><br>
    <input type="submit" value="Submit">
</form>

</body>
</html>
