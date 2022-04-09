<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>GWA</title>
</head>
<body>
    <jsp:include page="/jsp/include/header.jsp"/><br>


    <img src="<c:url value="/images/photo_gym_1.jpg"></c:url>" width="50%" height="50%">
    <img src="<c:url value="/images/photo_gym_2.jpg"></c:url>" width="50%" height="50%">
    <img src="<c:url value="/images/photo_gym_3.jpg"></c:url>" width="50%" height="50%">
    <img src="<c:url value="/images/photo_gym_4.jpg"></c:url>" width="50%" height="50%">
    <img src="<c:url value="/images/the_gym-unsplash.jpg"></c:url>" width="50%" height="50%">
    <br/>

    <jsp:include page="/jsp/include/footer.jsp"/>
</body>
</html>
