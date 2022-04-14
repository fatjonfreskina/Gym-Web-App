<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<jsp:include page="/jsp/include/icon.jsp"/>
<nav>
    <a href="<c:url value="/"/>">Home</a>
    <a href="<c:url value="/the_gym"/>">The Gym</a>
    <a href="<c:url value="/courses"/>">Courses</a>
    <a href="<c:url value="/calendar"/>">Calendar</a>
    <a href="<c:url value="/prices"/>">Prices</a>
    <a href="<c:url value="/staff"/>">Staff</a>
    <a href="<c:url value="/contactus"/>">Contact Us</a>
    <c:choose>
        <c:when test="${empty sessionScope.roles}">
            <a href="<c:url value="/register"/>">Register</a>
            <a href="<c:url value="/login"/>">Login</a>
        </c:when>
        <c:otherwise>
            <a href="<c:url value="/${sessionScope.defaultRole}"/>">My Profile</a>
            <a href="<c:url value="/logout"/>">Logout</a>
        </c:otherwise>
    </c:choose>
</nav>
