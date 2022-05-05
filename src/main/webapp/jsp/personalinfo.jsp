<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Personal Info</title>
    <meta charset="UTF-8">
    <jsp:include page="/jsp/include/style.jsp"/>
    <jsp:include page="/jsp/include/favicon.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
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
                <form method="post" action="<c:url value="/personal_info"/>" enctype="multipart/form-data">
                    <div class="form-group row">
                        <div class="col-sm-3">
                            <c:if test="${null != sessionScope.avatarPath}">
                                <img src="<c:url value="/avatar"/>" width="50px" height="50px" alt="Avatar">
                            </c:if>
                        </div>
                        <div class="col-sm-6">
                            <div class="custom-file">
                                <input type="file" name="avatar" id="file" class="custom-file-input">
                                <label class="custom-file-label" for="file">Choose File</label>
                            </div>

                        </div>
                        <div class="col-sm-3">
                            <input type="submit" value="Change" class="btn btn-outline-primary btn-lg"/>
                        </div>
                    </div>
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

    <jsp:include page="/jsp/include/message.jsp"/>

</main>
<jsp:include page="/jsp/include/footer.jsp"/>
<jsp:include page="/jsp/include/scripts.jsp"/>
<script src="<c:url value="/js/message-delay.js"/>"></script>
</body>
</html>