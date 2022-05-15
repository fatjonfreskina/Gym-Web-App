<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Ask for password reset</title>
        <meta charset="UTF-8"/>
        <jsp:include page="/jsp/include/style.jsp"/>
        <link rel="stylesheet" href="<c:url value="/css/main.css"/>"/>
    </head>
    <body>
        <header>
            <jsp:include page="../include/header.jsp"/>
            <jsp:include page="/jsp/include/favicon.jsp"/>
        </header>
        <main class="global-container">
            <jsp:include page="/jsp/include/message.jsp"/>
            <form method="POST" action="<c:url value="/password_forgot"/>" enctype="application/x-www-form-urlencoded">

                <div class="form-group row">
                    <label for="email" class="col-sm-2 col-form-label">Email :</label>
                    <div class="col-sm-10">
                        <input type="email" name="email" id="email" maxlength="40" class="form-control"
                               placeholder="Enter Email" required/>
                    </div>
                </div>

                <input type="submit" value="Submit" class="btn btn-outline-primary btn-lg"/>
            </form>


        </main>
        <jsp:include page="../include/footer.jsp"/>
        <jsp:include page="/jsp/include/scripts.jsp"/>
        <!-- Common JS to handle messages -->
        <script src="<c:url value="/js/message.js"/>"></script>
    </body>
</html>
