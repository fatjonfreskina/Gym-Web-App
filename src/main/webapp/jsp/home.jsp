<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>GWA</title>
</head>
<body>
    <jsp:include page="/jsp/include/header.jsp"/><br>
    <div class="row">

        <div class="column">
            <img src="<c:url value="/images/img.png"></c:url>" alt="Pesi" style="width:100%" width="1920" height="1280">
            <img src="/images/photo_gym_1.jpg" alt="Pesi" style="width:100%" width="1920" height="1280">
        </div>
        <div class="column">

            <img src="<c:url value="../images/photo_gym_2.jpg"/>">
        </div>
        <div class="column">
            <img src="http://localhost:8080/wa2122-gwa/images/photo_gym_3.jpg" alt="Pilates" style="width:100%" width="1920" height="1280">
        </div>
        <div class="column">
            <img src="/images/photo_gym_4.jpg" alt="Pilates" style="width:100%" width="1920" height="1080">
        </div>
        
        <img src="../images/photo_gym_3.jpg">
        
    </div>
    <jsp:include page="/jsp/include/footer.jsp"/>
</body>
</html>
