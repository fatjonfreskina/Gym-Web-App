<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Roles</title>
    <jsp:include page="../include/bootstrap.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/global_style.css"/>">
</head>
<body>
<header>
    <jsp:include page="../include/header.jsp"/>
</header>
<main class="global-container">
    <c:forEach var="role" items="${sessionScope.roles}">
        <c:if test="${role=='trainee'}">
            <form method="post" action="<c:url value="/access/roles"/>">
                <input type="submit" name="trainee" value="Trainee">
            </form>

        </c:if>
        <c:if test="${role=='trainer'}">
            <form method="post" action="<c:url value="/access/roles"/>">
                <input type="submit" name="trainer" value="Trainer">
            </form>
        </c:if>
        <c:if test="${role=='secretary'}">
            <form method="post" action="<c:url value="/access/roles"/>">
                <input type="submit" name="secretary" value="Secretary">
            </form>
        </c:if>
    </c:forEach>
</main>
<jsp:include page="../include/footer.jsp"/>
</body>
</html>
