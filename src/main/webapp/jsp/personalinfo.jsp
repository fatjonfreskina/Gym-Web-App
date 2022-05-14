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

    <%-- ********** Choose the correct Header to use, given the currently selected role. ********** --%>
    <c:choose>
        <c:when test="${empty defaultRole}">
            <jsp:include page="/jsp/include/header.jsp"/>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${defaultRole eq 'trainee'}">
                    <jsp:include page="/jsp/trainee/include/headertrainee.jsp"/>
                </c:when>

                <c:when test="${defaultRole eq 'trainer'}">
                    <jsp:include page="/jsp/trainer/include/headertrainer.jsp"/>
                </c:when>

                <c:when test="${defaultRole eq 'secretary'}">
                    <jsp:include page="/jsp/secretary/include/headersecretary.jsp"/>
                </c:when>

                <c:otherwise>
                    <jsp:include page="/jsp/include/header.jsp"/>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>
</header>
<main class="global-container">

    <div class="container">

        <form method="post" action="<c:url value="/personal_info"/>" enctype="multipart/form-data" id="form">


            <%-- ********** AVATAR ********** --%>
            <div class="form-group row">
                <label class="col-sm-3 col-form-label">Avatar :</label>

                <div class="col-sm-3 avatar-image-box">

                    <%-- Inline styling info is needed since some browsers
                         like Firefox does not support classes for images. --%>
                    <c:choose>
                        <c:when test="${null != personalInfo.avatarPath}">
                            <img src="<c:url value="/avatar"/>"
                                 onerror="this.src='<c:url value="/images/staff/default-user-image-avatar.jpg"/>';"
                                 style="max-width: 100%; max-height: 100%"
                                 width="100%" height="100%"
                                 alt="Avatar">
                        </c:when>
                        <c:otherwise>
                            <img src="<c:url value="/images/staff/default-user-image-avatar.jpg"/>"
                                 style="max-width: 100%; max-height: 100%"
                                 width="100%" height="100%"
                                 alt="Avatar">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <%-- ********** CHANGE AVATAR ********** --%>
            <div class="form-group row">
                <label class="col-sm-3 col-form-label"></label>

                <div class="col-sm-6">
                    <div class="custom-file">
                        <input type="file" name="avatar" id="avatar" class="custom-file-input" required>
                        <label class="custom-file-label" for="avatar">Choose File: </label>
                    </div>
                </div>

                <div class="col-sm-3">
                    <input type="submit" value="Change" class="btn btn-outline-primary btn-lg" id="button"/>
                </div>
            </div>

        </form>

        <div class="form-group row">
            <div id="alert-box" class="alert alert-warning alert-dismissible fade show col-sm-12" role="alert" style="display: none;">
                <p id="alert-message-body" class="alert-box-message">
                </p>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close" >
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </div>

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
                <a class="btn btn-outline-primary btn-lg" href="<c:url value="/password_forgot"/>">Change</a>
            </div>
        </div>

    </div>

    <div class="form-group row">
        <jsp:include page="/jsp/include/message.jsp"/>
    </div>

</main>

<jsp:include page="/jsp/include/footer.jsp"/>
<jsp:include page="/jsp/include/scripts.jsp"/>
<jsp:include page="/jsp/include/moment/scripts.jsp"/>

<!-- Common JS to handle messages -->
<script src="<c:url value="/js/message.js"/>"></script>

<script src="<c:url value="/js/personal-info.js"/>"></script>

</body>
</html>