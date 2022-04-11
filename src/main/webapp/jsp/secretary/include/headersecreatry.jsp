<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/jsp/include/icon.jsp"/>

<table>
    <tr>
        <td><a href="<c:url value="/secretary/rest/getalllecturetimeslot"/>">My Home</a></td> <!-- Riccardo -->
        <td><a href="<c:url value="/secretary/addcourses"/>">Add Courses</a></td>
        <td><a href="<c:url value="/secretary/addaccount"/>">Add Account</a></td> <!--Alberto-->
        <td><a href="<c:url value="/secretary/manageroles"/>">Manage Roles</a></td> <!--Alberto-->
        <td><a href="<c:url value="/secretary/addMedicalCertificate"/>">Add Medical Certificates</a></td><!-- Simone -->
        <td><a href="<c:url value="/secretary/managessubscription"/>">Manage Subscription</a></td> <!-- Francesco -->

        <!-- Riccardo -->
        <td><a href="<c:url value="/secretary"/>">Manages Lectures (Substitution)</a></td>
    </tr>
</table>











