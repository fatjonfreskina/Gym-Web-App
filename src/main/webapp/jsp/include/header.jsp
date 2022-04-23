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
                        <li class="nav-item dropdown flex-grow-1 d-flex justify-content-md-end">
                            <a class="nav-link dropdown-toggle text-white" href="#" id="navbarDropdown" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false">
                                <c:choose>
                                    <c:when test="${null != sessionScope.avatarPath}">
                                        <img class="rounded-circle" src="./avatar" width="36px" height="36px">
                                    </c:when>
                                    <c:otherwise>
                                        <i class="fa-solid fa-user p-2"></i>
                                    </c:otherwise>
                                </c:choose>
                            </a>
                            <ul class="navbar__dropdown dropdown-menu dropdown-menu-md-end" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item disabled"><c:out value="${sessionScope.email}"/></a></li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>
                                <c:forEach var="role" items="${sessionScope.roles}">
                                    <li><a class="dropdown-item" href="<c:url value="/${role}"/>"><c:out value="${role}"/></a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </li>

                        <li class="nav-item p-2"><a href="<c:url value="/logout"/>">Logout</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>
