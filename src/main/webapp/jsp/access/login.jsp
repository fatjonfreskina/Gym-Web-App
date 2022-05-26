<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Login</title>
        <meta charset="UTF-8"/>
        <jsp:include page="/jsp/include/style.jsp"/>
        <link rel="stylesheet" href="<c:url value="/css/main.css"/>"/>
        <jsp:include page="/jsp/include/favicon.jsp"/>
    </head>
    <body>
        <header>
            <jsp:include page="../include/header.jsp"/>
        </header>
        <main class="global-container">
            <jsp:include page="/jsp/include/message.jsp"/>
            <form method="post" action="<c:url value="/login"/>">

                <div class="form-group row">
                    <label for="email" class="col-sm-2 col-form-label">Email:</label>
                    <div class="col-sm-10">
                        <input type="email" name="email" id="email" maxlength="40" class="form-control"
                               placeholder="Enter Email" value="dev@dev.dev" required/>
                    </div>
                </div>

                <div class="form-group row">
                    <label for="password" class="col-sm-2 col-form-label">Password:</label>
                    <div class="col-sm-10">
                        <input type="password" name="password" id="password" class="form-control" placeholder="Enter Password"
                               value="CIAO" required/>
                    </div>
                </div>

                <div class="form-group row">
                    <a href="<c:url value="/password_forgot"/>" class="link-primary ml-3">Forgot your password?</a>
                </div>

                <button type="submit" class="btn btn-outline-primary btn-lg">Login</button>
            </form>


</main>
<jsp:include page="../include/footer.jsp"/>
<jsp:include page="/jsp/include/scripts.jsp"/>
<!-- Common JS to handle messages -->
<script src="<c:url value="/js/message.js"/>"></script>
</body>
</html>
