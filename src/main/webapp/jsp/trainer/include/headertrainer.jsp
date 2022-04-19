<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<nav class="trainer__navbar navbar fixed-top w-100 navbar-expand-md navbar-light bg-light ">
    <div class="container-fluid container">
        <a class="navbar-brand fs-3 fw-bold" href="">GWA</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse w-100" id="navbarSupportedContent">
            <ul class="trainer__navbar__menu navbar-nav w-100 d-flex justify-content-md-center align-items-md-center">
                <li class="nav-item p-2">
                    <a href="<c:url value="/trainer"/>" aria-current="page">Home</a>
                </li>
                <li class="nav-item p-2">
                    <a href="<c:url value="/trainer/attendance"/>">Attendance</a>
                </li>
                <li class="nav-item p-2">
                    <a href="<c:url value="/personal_info"/>">Personal Info</a>
                </li>
                <li class="nav-item dropdown flex-grow-1 d-flex justify-content-md-end">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="fa-solid fa-user p-2"></i>
                    </a>
                    <ul class="trainer__navbar__dropdown dropdown-menu dropdown-menu-md-end" aria-labelledby="navbarDropdown">
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
                <li class="nav-item p-md-2 pb-4">
                    <a href="<c:url value="/logout"/>">Logout</a>
                </li>
            </ul>
        </div>
    </div>
</nav>