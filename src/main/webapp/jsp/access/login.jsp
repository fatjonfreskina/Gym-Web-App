<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login</title>
    <meta charset="UTF-8">
    <jsp:include page="/jsp/include/style.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
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
                <input type="email" name="email" id="email" maxlength="40" class="form-control"
                       placeholder="Enter Email" value="dev@dev.dev">
            </div>
        </div>

        <div class="form-group row">
            <label for="password" class="col-sm-2 col-form-label">Password :</label>
            <div class="col-sm-10">
                <input type="password" name="password" id="password" class="form-control" placeholder="Enter Password"
                       value="CIAO">
            </div>
        </div>

        <a href="<c:url value="/password_forgot"/>" class="link-primary">Password forgot</a> <br/>

        <jsp:include page="/jsp/include/message.jsp"/>

        <button type="submit" class="btn btn-outline-primary btn-lg">Login</button>
    </form>


</main>
<jsp:include page="../include/footer.jsp"/>
<jsp:include page="/jsp/include/scripts.jsp"/>
<script src="<c:url value="/js/message_delay.js"/>"></script>
</body>
</html>
