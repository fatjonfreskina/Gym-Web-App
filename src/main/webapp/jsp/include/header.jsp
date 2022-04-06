<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<table>
    <tr>
        <th><a href="<c:url value="/"/>">Home</a></th>
        <th><a href="<c:url value="/the_gym"/>">The Gym</a></th>
        <th><a href="<c:url value="/courses"/>">Courses</a></th>
        <th><a href="<c:url value="/calendar"/>">Calendar</a></th>
        <th><a href="<c:url value="/prices"/>">Prices</a></th>
        <th><a href="<c:url value="/staff"/>">Staff</a></th>
        <th><a href="<c:url value="/aboutus"/>">Contact Us</a></th>

        <!--<c:when test="${empty sessionScope.roles}">
            <th><a href="<c:url value="/register"/>">Register</a></th>
            <th><a href="<c:url value="/login"/>">Login</a></th>
        </c:when>
        <c:otherwise>
            <th><a href="<c:url value=""/>">My Home</a></th>
            <th><a href="<c:url value=""/>">Logout</a></th>
        </c:otherwise>-->


    </tr>
</table>