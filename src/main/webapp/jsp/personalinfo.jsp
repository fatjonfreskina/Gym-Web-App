<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Personal Info</title>
    <jsp:include page="include/bootstrap.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/global_style.css"/>">
</head>
<body>
<header>
    <jsp:include page="/jsp/include/header.jsp"/>
</header>
<main class="global-container">
    <table>
        <tr>
            <th>Tax Code:</th>
            <td><c:out value="${personalInfo.taxCode}"/></td>
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
            <td><c:out value="${personalInfo.birthDate}"/></td>
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
            <th>Avatar (optional):</th>
            <td>
                <c:if test="${null != sessionScope.avatarPath}">
                    <img src="./avatar" width="50px" height="50px">
                </c:if>
                <form method="post" action="<c:url value="/personal_info"/>" enctype="multipart/form-data">
                    <input type="file" name="avatar"/>
                    <input type="submit" value="Change"/>
                </form>
            </td>
        </tr>
        <tr>
            <th>Email:</th>
            <td><c:out value="${personalInfo.email}"/></td>
        </tr>
        <tr>
            <th>Change password:</th>
            <td>
                <a href="<c:url value="/password_forgot"/>">Reset</a>
            </td>
        </tr>
    </table>


    <c:if test="${message != null}">
        <c:out value="${message.message}"/>
    </c:if>

</main>
<jsp:include page="/jsp/include/footer.jsp"/>
</body>
</html>