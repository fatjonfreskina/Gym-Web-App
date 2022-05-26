<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Manage Roles</title>
        <meta charset="UTF-8"/>
        <jsp:include page="/jsp/include/style.jsp"/>
        <jsp:include page="/jsp/include/favicon.jsp"/>
        <link rel="stylesheet" href="<c:url value="/css/main.css"/>"/>
    </head>
    <body>
        <header>
            <jsp:include page="/jsp/secretary/include/headersecretary.jsp"/>
        </header>

        <main class="global-container">
            <jsp:include page="/jsp/include/message.jsp"/>
            <form method="post" enctype="application/x-www-form-urlencoded" id="form">

                <div class="form-group row">
                    <label for="email" class="col-sm-2 col-form-label">Email:</label>
                    <div class="col-sm-10">
                        <input type="email" name="email" id="email" maxlength="40" class="form-control"
                               placeholder="Enter Email" required>
                    </div>
                </div>

                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">Role: </label>
                    <div class="col-sm-3">
                        <input type="checkbox" id="trainee" name="trainee"/>
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

                <button type="submit" class="btn btn-outline-primary btn-lg" id="btn-update">Update</button>
            </form>
        </main>
        <jsp:include page="../include/footer.jsp"/>
        <jsp:include page="/jsp/include/scripts.jsp"/>
        <jsp:include page="/jsp/include/moment/scripts.jsp"/>

        <!-- Common JS to handle messages -->
        <script src="<c:url value="/js/message.js"/>"></script>
        <script src="<c:url value="/js/secretary/manage_roles.js"/>"></script>
    </body>
</html>
