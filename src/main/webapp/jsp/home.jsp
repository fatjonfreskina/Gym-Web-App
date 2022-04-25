<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>GWA</title>
    <jsp:include page="include/bootstrap.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/global_style.css"/>">
</head>
<body>
<jsp:include page="/jsp/include/header.jsp"/>
<main class="global-container">
    <img src="<c:url value="/images/photo_gym_1.jpg"></c:url>" width="50%" height="50%">
    <img src="<c:url value="/images/photo_gym_2.jpg"></c:url>" width="50%" height="50%">
    <img src="<c:url value="/images/photo_gym_3.jpg"></c:url>" width="50%" height="50%">
    <img src="<c:url value="/images/photo_gym_4.jpg"></c:url>" width="50%" height="50%">
    <img src="<c:url value="/images/the_gym-unsplash.jpg"></c:url>" width="50%" height="50%">
    <br/>
</main>
<jsp:include page="/jsp/include/footer.jsp"/>
</body>
</html>
