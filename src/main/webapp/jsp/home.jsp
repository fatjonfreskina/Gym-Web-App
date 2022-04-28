<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>GWA</title>
    <meta charset="UTF-8">
    <jsp:include page="/jsp/include/stylesheets.jsp"/>
</head>
<body>
<header>
    <jsp:include page="/jsp/include/header.jsp"/>
</header>
<main class="global-container">
    <img src="<c:url value="/images/photo_gym_1.jpg"/>" width="50%" height="50%" alt="Photo Gym 1">
    <img src="<c:url value="/images/photo_gym_2.jpg"/>" width="50%" height="50%" alt="Photo Gym 2">
    <img src="<c:url value="/images/photo_gym_3.jpg"/>" width="50%" height="50%" alt="Photo Gym 3">
    <img src="<c:url value="/images/photo_gym_4.jpg"/>" width="50%" height="50%" alt="Photo Gym 4">
    <img src="<c:url value="/images/the_gym-unsplash.jpg"/>" width="50%" height="50%" alt="The Gym Unsplash">
    <br/>
</main>
<jsp:include page="/jsp/include/footer.jsp"/>
<jsp:include page="/jsp/include/scripts.jsp"/>
</body>
</html>
