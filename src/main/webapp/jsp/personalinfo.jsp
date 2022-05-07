<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

    <%-- ********** TAX CODE ********** --%>
    <div class="form-group row">
        <label for="taxCode" class="col-sm-3 col-form-label">Tax Code :</label>
        <div class="col-sm-9">
            <input type="text" name="taxCode" id="taxCode" class="form-control"
                   value="<c:out value="${personalInfo.taxCode}"/>" readonly>
        </div>
    </div>

    <%-- ********** FIRST NAME ********** --%>
    <div class="form-group row">
        <label for="firstName" class="col-sm-3 col-form-label">First Name :</label>
        <div class="col-sm-9">
            <input type="text" name="firstName" id="firstName" class="form-control"
                   value="<c:out value="${personalInfo.name}"/>" readonly>
        </div>
    </div>

    <%-- ********** LAST NAME ********** --%>
    <div class="form-group row">
        <label for="lastName" class="col-sm-3 col-form-label">Last Name :</label>
        <div class="col-sm-9">
            <input type="text" name="lastName" id="lastName" class="form-control"
                   value="<c:out value="${personalInfo.surname}"/>" readonly>
        </div>
    </div>

    <%-- ********** BIRTH DATE ********** --%>
    <div class="form-group row">
        <label for="birthDate" class="col-sm-3 col-form-label">Birth Date :</label>
        <div class="col-sm-9">
            <input type="date" name="birthDate" id="birthDate" class="form-control"
                   value="<c:out value="${personalInfo.birthDate}"/>" readonly>
        </div>
    </div>

    <%-- ********** ADDRESS ********** --%>
    <div class="form-group row">
        <label for="address" class="col-sm-3 col-form-label">Address :</label>
        <div class="col-sm-9">
            <input type="text" name="address" id="address" class="form-control"
                   value="<c:out value="${personalInfo.address}"/>" readonly>
        </div>
    </div>

    <%-- ********** TELEPHONE NUMBER ********** --%>
    <div class="form-group row">
        <label for="telephone" class="col-sm-3 col-form-label">Telephone Number :</label>
        <div class="col-sm-9">
            <input type="text" name="telephone" id="telephone" class="form-control"
                   value="<c:out value="${personalInfo.telephone}"/>" readonly>
        </div>
    </div>

    <%-- ********** AVATAR ********** --%>
    <div class="form-group row">
        <label for="telephone" class="col-sm-3 col-form-label">Avatar :</label>
        <div class="col-sm-9">
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
        </div>
    </div>

    <%-- ********** EMAIL ********** --%>
    <div class="form-group row">
        <label for="email" class="col-sm-3 col-form-label">Email :</label>
        <div class="col-sm-9">
            <input type="email" name="email" id="email" class="form-control"
                   value="<c:out value="${personalInfo.email}"/>" readonly>
        </div>
    </div>

    <%-- ********** PASSWORD ********** --%>
    <div class="form-group row">
        <label class="col-sm-3 col-form-label">Password :</label>
        <div class="col-sm-9">
            <a class="form-control" href="<c:url value="/password_forgot"/>">Reset</a>
        </div>
    </div>

    <jsp:include page="/jsp/include/message.jsp"/>

</main>

<jsp:include page="/jsp/include/footer.jsp"/>
<jsp:include page="/jsp/include/scripts.jsp"/>
<script src="<c:url value="/js/message-delay.js"/>"></script>

</body>
</html>