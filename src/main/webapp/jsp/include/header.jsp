<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
<table>

    <tr>
        <th><a href="">Home</a></th><th><a href="">The Gym</a></th><th><a href="">Calendar</a></th><th><a href="">Courses</a></th><th><a href="<c:url value="/prices"/>">Prices</a></th><th><a href="">Staff</a></th><th><a href="">About Us</a></th>
    </tr>
</table>
<a href="<c:url value="/register"/>">Register</a>
</body>

</html>
