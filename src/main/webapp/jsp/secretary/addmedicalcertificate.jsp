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
        <label class="col-sm-2 col-form-label">Email : </label>
        <div class="col-sm-10">
            <input type="email" name="person" value="" class="form-control">
        </div>
    </div>

    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Expiration Date : </label>
        <div class="col-sm-10">
             <input type="date" name="expirationdate" value="2023-01-01" class="form-control">
        </div>
    </div>

    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Doctor name : </label>
        <div class="col-sm-10">
             <input type="text" name="doctorname" value="" class="form-control">
        </div>
    </div>

    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Medical Certificate: </label>
        <div class="col-sm-10">
            <input type="file" name="medical_certificate" class="form-control">
        </div>
    </div>

    <button type="submit" class="btn btn-outline-primary btn-lg" >Add certificate</button>

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
