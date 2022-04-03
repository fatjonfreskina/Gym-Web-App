<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Personal Info</title>
</head>
<body>
    <jsp:include page="/jsp/include/header.jsp"/>

    <table>
        <tr>
            <th>Tax Code:</th>
            <td><c:out value="${personalInfo.TaxCode}"/></td>
        </tr>
        <tr>
            <th>First Name:</th>
            <td><c:out value="${personalInfo.name}"/></td>
        </tr>
        <tr>
            <th>Last Name:</th>
            <td><c:out value="${personalInfo.surname}"/></td>
        </tr>
        <tr>
            <th>Birth Date:</th>
            <td><c:out value="${personalInfo.Birth_date}"/></td>
        </tr>
        <tr>
            <th>Address:</th>
            <td><c:out value="${personalInfo.address}"/></td>
        </tr>
        <tr>
            <th>Telephone Number:</th>
            <td><c:out value="${personalInfo.telephone}"/></td>
        </tr>
        <tr>
            <th>Photo (optional):</th>
            <td><c:out value="${personalInfo.Path_img}"/></td>
        </tr>
        <tr>
            <th>Email:</th>
            <td><c:out value="${personalInfo.email}"/></td>
        </tr>
        <tr>
            <th>Password:</th>
            <td><c:out value="${personalInfo.password}"/></td>
        </tr>
        <tr>
            <th>Medical Certificate (optional):</th>
            <td>""</td>
        </tr>
    </table>

    <jsp:include page="/jsp/include/footer.jsp"/>
</body>
</html>