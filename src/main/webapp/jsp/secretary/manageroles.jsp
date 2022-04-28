<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Manage Roles</title>
    <meta charset="UTF-8">
    <jsp:include page="/jsp/include/style.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
</head>
<body>
<header>
    <jsp:include page="/jsp/secretary/include/headersecretary.jsp"/>
</header>
<main class="global-container">
    <form method="post" enctype="application/x-www-form-urlencoded">

        <div class="form-group row">
            <label for="email" class="col-sm-2 col-form-label">Email :</label>
            <div class="col-sm-10">
                <input type="email" name="email" id="email" maxlength="40" class="form-control" placeholder="Enter Email">
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-3 col-form-label">Role : </label>
            <div class="col-sm-3">
                <input  type="checkbox" id="trainee" name="trainee" />
                <label for="trainee">Trainee</label>
            </div>
            <div class="col-sm-3">
                <input type="checkbox" name="trainer" id="trainer"/>
                <label for="trainer">Trainer</label>
            </div>
            <div class="col-sm-3">
                <input type="checkbox" name="secretary" id="secretary"/>
                <label for="secretary">Secretary</label>
            </div>
        </div>

        <button type="submit" class="btn btn-outline-primary btn-lg">Update</button>
    </form>
    <c:choose>
        <c:when test="${message != null}">
            <p><c:out value="${message.message}"/></p>
        </c:when>
    </c:choose>
</main>
<jsp:include page="../include/footer.jsp"/>
<jsp:include page="/jsp/include/scripts.jsp"/>
</body>
</html>
