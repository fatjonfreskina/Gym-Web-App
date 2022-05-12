<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Change the password</title>
    <meta charset="UTF-8">
    <jsp:include page="/jsp/include/style.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
    <jsp:include page="/jsp/include/favicon.jsp"/>
</head>
<body>
<header>
    <jsp:include page="../include/header.jsp"/>
</header>
<main class="global-container">
    <form method="POST" action="<c:url value="/password_change"/>" enctype="application/x-www-form-urlencoded" id="form">
        <input type="hidden" name="token" value="${token}">

        <div class="form-group row">
            <label for="password" class="col-sm-2 col-form-label">Password :</label>
            <div class="col-sm-10">
                <input type="password" name="password" id="password" class="form-control" placeholder="Password" required/>
            </div>
        </div>

        <div class="form-group row">
            <label for="confirm_password" class="col-sm-3 col-form-label">Confirm Password :</label>
            <div class="col-sm-9">
                <input type="password" name="confirm_password" id="confirm_password" class="form-control" placeholder="Confirm Password" required/>
            </div>
        </div>

        <jsp:include page="/jsp/include/message.jsp"/>
        <!-- alert box for messages -->
        <div id="alert-box" class="alert alert-warning alert-dismissible fade show" role="alert">
            <p id="alert-message-body" class="alert-box-message">
            </p>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close" >
                <span aria-hidden="true">&times;</span>
            </button>
        </div>


        <input type="submit" value="Submit" class="btn btn-outline-primary btn-lg" />
    </form>

</main>
<jsp:include page="../include/footer.jsp"/>
<jsp:include page="/jsp/include/scripts.jsp"/>
<script src="<c:url value="/js/message-delay.js"/>"></script>
<script src="<c:url value="/js/password-change.js"/>"></script>
</body>
</html>
