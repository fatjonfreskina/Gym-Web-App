<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Add medical certificate</title>
    <meta charset="UTF-8">
    <jsp:include page="../include/bootstrap.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/global_style.css"/>">
</head>
<body>
<header>
    <jsp:include page="/jsp/secretary/include/headersecretary.jsp"/>
</header>

<main class="global-container">
    <form method="post" action="<c:url value="/secretary/addMedicalCertificate"/>" enctype="multipart/form-data">

        <div class="form-group row">
            <label for="email" class="col-sm-2 col-form-label">Email :</label>
            <div class="col-sm-10">
                <input id="email" type="email" name="person" class="form-control" maxlength="40" placeholder="Enter Email">
            </div>
        </div>

        <div class="form-group row">
            <label for="date" class="col-sm-2 col-form-label">Expiration Date :</label>
            <div class="col-sm-10">
                 <input id="date" type="date" name="expirationdate" class="form-control">
            </div>
        </div>

        <div class="form-group row">
            <label for="doctor_name" class="col-sm-2 col-form-label">Doctor name :</label>
            <div class="col-sm-10">
                 <input id="doctor_name" type="text" name="doctorname" value="" class="form-control" placeholder="Enter Doctor Name" maxlength="30">
            </div>
        </div>

        <div class="form-group row">
            <label for="file" class="col-sm-3 col-form-label" >Medical Certificate:</label>
            <div class="col-sm-9">
                <div class="custom-file">
                    <input type="file" name="medical_certificate" class="form-control" id="file">
                    <label class="custom-file-label" for="file">Choose File</label>
                </div>
            </div>
        </div>

        <button type="submit" class="btn btn-outline-primary btn-lg" >Add Certificate</button>
    </form>

    <c:choose>
        <c:when test="${message.error}">
            <p><c:out value="${message.message}"/></p>
        </c:when>
    </c:choose>
</main>

<jsp:include page="../include/footer.jsp"/>
</body>
</html>
