<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
<table>
    <tr>
        <th><a href="<c:url value="/"/>">Home</a></th>
        <th><a href="<c:url value="/the_gym"/>">The Gym</a></th>
        <th><a href="<c:url value="/courses"/>">Courses</a></th>
        <th><a href="<c:url value="/calendar"/>">Calendar</a></th>
        <th><a href="<c:url value="/prices"/>">Prices</a></th>
        <th><a href="">Staff</a></th>
        <th><a href="<c:url value="/aboutus"/>">Contact Us</a></th>
        <th><a href="<c:url value="/register"/>">Register</a></th>
        <th><a href="">Login</a></th>
    </tr>
</table>
</body>
</html>