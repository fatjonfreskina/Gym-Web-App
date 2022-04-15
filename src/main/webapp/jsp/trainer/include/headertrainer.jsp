<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Header trainer</title>
</head>
<body>
<nav>
    <a href="<c:url value="/trainer"/>">Home</a>
    <a href="<c:url value="/trainer/attendance"/>">Attendance</a>
    <a href="<c:url value="/personal_info"/>">Personal Info</a>
    <a href="<c:url value="/logout"/>">Logout</a>
</nav>
</body>
</html>
