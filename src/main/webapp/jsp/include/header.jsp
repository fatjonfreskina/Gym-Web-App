<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<jsp:include page="/jsp/include/icon.jsp"/>
<table>
    <tr>

        <c:choose>
            <c:when test="${empty sessionScope.roles}">
                <th><a href="<c:url value="/"/>">Home</a></th>
                <th><a href="<c:url value="/the_gym"/>">The Gym</a></th>
                <th><a href="<c:url value="/courses"/>">Courses</a></th>
                <th><a href="<c:url value="/calendar"/>">Calendar</a></th>
                <th><a href="<c:url value="/prices"/>">Prices</a></th>
                <th><a href="<c:url value="/staff"/>">Staff</a></th>
                <th><a href="<c:url value="/aboutus"/>">Contact Us</a></th>

                <th><a href="<c:url value="/register"/>">Register</a></th>
                <th><a href="<c:url value="/login"/>">Login</a></th>
            </c:when>

            <c:when test="${sessionScope.defaultRole=='trainee'}">
                <th><a href="<c:url value="/"/>">Home</a></th>
                <th><a href="<c:url value="/the_gym"/>">The Gym</a></th>
                <th><a href="<c:url value="/courses"/>">Courses</a></th>
                <th><a href="<c:url value="/calendar"/>">Calendar</a></th>
                <th><a href="<c:url value="/prices"/>">Prices</a></th>
                <th><a href="<c:url value="/staff"/>">Staff</a></th>
                <th><a href="<c:url value="/aboutus"/>">Contact Us</a></th>

                <th><a href="<c:url value="/${sessionScope.defaultRole}"/>">My Trainee Profile</a></th>
                <th><a href="<c:url value="/logout"/>">Logout</a></th>
            </c:when>

            <c:when test="${sessionScope.defaultRole=='trainer'}">
                <th><a href="<c:url value="/"/>">Home</a></th>
                <th><a href="<c:url value="/the_gym"/>">The Gym</a></th>
                <th><a href="<c:url value="/courses"/>">Courses</a></th>
                <th><a href="<c:url value="/calendar"/>">Calendar</a></th>
                <th><a href="<c:url value="/prices"/>">Prices</a></th>
                <th><a href="<c:url value="/staff"/>">Staff</a></th>
                <th><a href="<c:url value="/aboutus"/>">Contact Us</a></th>

                <th><a href="<c:url value="/${sessionScope.defaultRole}"/>">My Trainer Profile</a></th>
                <th><a href="<c:url value="/logout"/>">Logout</a></th>
            </c:when>

            <c:when test="${sessionScope.defaultRole=='secretary'}">
                <!--<th><a href="<c:url value="/"/>">Home</a></th>
                <th><a href="<c:url value="/the_gym"/>">The Gym</a></th>
                <th><a href="<c:url value="/courses"/>">Courses</a></th>
                <th><a href="<c:url value="/calendar"/>">Calendar</a></th>
                <th><a href="<c:url value="/prices"/>">Prices</a></th>
                <th><a href="<c:url value="/staff"/>">Staff</a></th>
                <th><a href="<c:url value="/aboutus"/>">Contact Us</a></th>

                <th><a href="<c:url value="/${sessionScope.defaultRole}"/>">My Secretary Profile</a></th>
                <th><a href="<c:url value="/logout"/>">Logout</a></th>-->

                <td><a href="<c:url value="/"/>">Home</a></td>
                <td><a href="<c:url value="/secretary"/>">My Profile</a></td> <!-- Riccardo -->
                <td><a href="<c:url value="/secretary/addcourses"/>">Add Courses</a></td>
                <td><a href="<c:url value="/secretary/addaccount"/>">Add Account</a></td> <!--Alberto-->
                <td><a href="<c:url value="/secretary/manageroles"/>">Manage Roles</a></td> <!--Alberto-->
                <td><a href="<c:url value="/secretary/addMedicalCertificate"/>">Add Medical Certificates</a></td><!-- Simone -->
                <td><a href="<c:url value="/secretary/managessubscription"/>">Manage Subscription</a></td> <!-- Francesco -->
                <td><a href="<c:url value="/personal_info"/>">Personal Info</a></td>
                <td><a href="<c:url value="/logout"/>">Logout</a></td>
                <!-- Riccardo -->
                <td><a href="<c:url value="/secretary"/>">Manages Lectures (Substitution)</a></td>



            </c:when>
        </c:choose>



        <!--<th><a href="<c:url value="/"/>">Home</a></th>
        <th><a href="<c:url value="/the_gym"/>">The Gym</a></th>
        <th><a href="<c:url value="/courses"/>">Courses</a></th>
        <th><a href="<c:url value="/calendar"/>">Calendar</a></th>
        <th><a href="<c:url value="/prices"/>">Prices</a></th>
        <th><a href="<c:url value="/staff"/>">Staff</a></th>
        <th><a href="<c:url value="/aboutus"/>">Contact Us</a></th>


        <c:choose>
            <c:when test="${empty sessionScope.roles}">
                <th><a href="<c:url value="/register"/>">Register</a></th>
                <th><a href="<c:url value="/login"/>">Login</a></th>
            </c:when>
            <c:otherwise>
                <th><a href="<c:url value="/${sessionScope.defaultRole}"/>">My Profile</a></th>
                <th><a href="<c:url value="/logout"/>">Logout</a></th>
            </c:otherwise>
        </c:choose>-->

    </tr>
</table>