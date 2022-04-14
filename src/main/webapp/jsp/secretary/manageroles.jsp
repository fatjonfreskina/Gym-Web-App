<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Roles</title>
</head>
<body>
<jsp:include page="/jsp/secretary/include/headersecretary.jsp"/>

    <c:choose>
        <c:when test="${message != null}">
            <p><c:out value="${message.message}"/></p>
        </c:when>
    </c:choose>

    <form method="post" enctype="application/x-www-form-urlencoded">

        <label>Email : </label><input type="text" name="email" value=""><br/>
        <div>
            <label>Role : </label>
            <input type="checkbox" id="trainee" name="trainee"/>
            <label for="trainee">Trainee</label>
            <input type="checkbox" name="trainer" id="trainer"/>
            <label for="trainer">Trainer</label>
            <input type="checkbox" name="secretary" id="secretary"/>
            <label for="secretary">Secretary</label>
        </div>
        <button type="submit" >Update</button>
    </form>

    <jsp:include page="../include/footer.jsp"/>
</body>
</html>
