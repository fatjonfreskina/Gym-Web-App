<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <jsp:include page="../include/bootstrap.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/global_style.css"/>">
</head>
<body>
<header>
    <jsp:include page="../include/header.jsp"/>
</header>
<main class="global-container">
    <form method="post" action="<c:url value="/login"/>">

        <div class="form-group row">
            <label for="email" class="col-sm-2 col-form-label">Email :</label>
            <div class="col-sm-10">
                <input type="email" name="email" id="email" maxlength="40" class="form-control" placeholder="Enter Email">
            </div>
        </div>

        <div class="form-group row">
            <label for="password" class="col-sm-2 col-form-label">Password :</label>
            <div class="col-sm-10">
                <input type="password" name="password" id="password" class="form-control" placeholder="Enter Password">
            </div>
        </div>

        <a href="<c:url value="/password_forgot"/>" class="link-primary">Password forgot</a> <br/>

        <button type="submit" class="btn btn-outline-primary btn-lg">Login</button>
    </form>

    <c:choose>
        <c:when test="${message != null}">
            <p><c:out value="${message.message}"/></p>
        </c:when>
    </c:choose>
</main>
<jsp:include page="../include/footer.jsp"/>
</body>
</html>
