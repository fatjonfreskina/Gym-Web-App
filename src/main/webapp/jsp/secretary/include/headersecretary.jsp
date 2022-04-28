<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/jsp/include/icon.jsp"/>

<nav>
    <a href="<c:url value="/"/>">Home</a>
    <a href="<c:url value="/secretary"/>">My Profile</a> <!-- Riccardo -->
    <a href="<c:url value="/secretary/addcourses"/>">Add Courses</a>
    <a href="<c:url value="/secretary/addaccount"/>">Add Account</a> <!--Alberto-->
    <a href="<c:url value="/secretary/manageroles"/>">Manage Roles</a> <!--Alberto-->
    <a href="<c:url value="/secretary/addMedicalCertificate"/>">Add Medical Certificates</a><!-- Simone -->
    <a href="<c:url value="/secretary/managessubscription"/>">Manage Subscription</a> <!-- Francesco -->
    <a href="<c:url value="/personal_info"/>">Personal Info</a>
    <a href="<c:url value="/logout"/>">Logout</a>
</nav>













