<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<nav class="navbar fixed-top w-100 navbar-expand-lg navbar-dark bg-dark ">
    <div class="container-fluid container">
        <a class="navbar-brand fs-3 fw-bold" href="">GWA</a>
        <button class="navbar-toggler border-0 shadow-none" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse w-100" id="navbarSupportedContent">
            <ul class="navbar__menu navbar-nav w-100 d-flex justify-content-lg-center align-items-lg-center">
                <li class="nav-item p-2">
                    <a href="<c:url value="/"/>">Home</a>
                </li>
                <li class="nav-item p-2">
                    <a href="<c:url value="/the_gym"/>">The Gym</a>
                </li>
                <li class="nav-item p-2">
                    <a href="<c:url value="/courses"/>">Courses</a>
                </li>
                <li class="nav-item p-2">
                    <a href="<c:url value="/calendar"/>">Calendar</a>
                </li>
                <li class="nav-item p-2">
                    <a href="<c:url value="/prices"/>">Prices</a>
                </li>
                <li class="nav-item p-2">
                    <a href="<c:url value="/staff"/>">Staff</a>
                </li>
                <li class="nav-item p-2">
                    <a href="<c:url value="/contactus"/>">Contact Us</a>
                </li>

                <c:choose>
                    <c:when test="${empty sessionScope.roles}">
                        <li class="flex-grow-1 d-flex justify-content-lg-end align-items-end align-items-lg-center nav-item p-2">
                            <a href="<c:url value="/register"/>">Register</a>
                        </li>
                        <li class="nav-item p-2">
                            <a href="<c:url value="/login"/>">Login</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="flex-grow-1 d-flex justify-content-md-end nav-item p-2">
                            <a href="<c:url value="/${sessionScope.defaultRole}"/>">My Profile</a></li>
                        <li class="nav-item p-2"><a href="<c:url value="/logout"/>">Logout</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>
