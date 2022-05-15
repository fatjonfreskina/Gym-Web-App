<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Change the password</title>
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
            <form method="POST" action="<c:url value="/password_change"/>" enctype="application/x-www-form-urlencoded" id="form">
                <input type="hidden" name="token" value="${token}"/>

                <div class="form-group row">
                    <label for="password" class="col-sm-3 col-form-label">Password :</label>
                    <div class="col-sm-9">
                        <input type="password" name="password" id="password" class="form-control" placeholder="Password" required/>
                    </div>
                </div>

                <div class="form-group row">
                    <label for="confirm_password" class="col-sm-3 col-form-label">Confirm Password :</label>
                    <div class="col-sm-9">
                        <input type="password" name="confirm_password" id="confirm_password" class="form-control" placeholder="Confirm Password" required/>
                    </div>
                </div>

                <input type="submit" value="Submit" class="btn btn-outline-primary btn-lg" id="button"/>
            </form>

        </main>

        <jsp:include page="../include/footer.jsp"/>
        <jsp:include page="/jsp/include/scripts.jsp"/>

        <!-- Common JS to handle messages -->
        <script src="<c:url value="/js/message.js"/>"></script>
        <script src="<c:url value="/js/form-validation-functions.js"/>"></script>
        <script src="<c:url value="/js/password-change.js"/>"></script>
    </body>
</html>
