<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Unauthorized</title>
    <jsp:include page="../include/bootstrap.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="../include/header.jsp"/>
<main>
    <h1>UNAUTHORIZED</h1>
    <p>You do not have privilege to access this resource.</p>
</main>
<jsp:include page="../include/footer.jsp"/>
</body>
</html>
