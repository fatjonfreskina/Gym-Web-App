<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>About Us</title>
    <meta charset="UTF-8">
    <jsp:include page="include/bootstrap.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/global_style.css"/>">
</head>
<body>
<header>
    <jsp:include page="/jsp/include/header.jsp"/>
</header>
<main class="global-container">
    <div class="row">
        <div class="column">
            <p>GWA</p>
            <ul>
                <li>Location: Via Gradenigo 6/A 35131 Padova PD</li>
                <li>Tel: +39 0123 456789</li>
                <li>E-mail: secretarygwa@mail.com</li>
            </ul>

        </div>
        <div class="column">
            <p>
                <a href="https://www.google.it/maps/place/Dipartimento+di+Ingegneria+dell'Informazione+(DEI)/@45.4090076,11.8946277,20.25z/data=!4m13!1m7!3m6!1s0x477edaf1336badf3:0x3a084d7b194488a5!2sVia+Giovanni+Gradenigo,+6a,+35131+Padova+PD!3b1!8m2!3d45.4090837!4d11.8947551!3m4!1s0x477edb664644067d:0xb57b9fafc118efea!8m2!3d45.4091698!4d11.8943753">You
                    can find us here!</a></p>
        </div>
    </div>
</main>
<jsp:include page="/jsp/include/footer.jsp"/>
</body>
</html>
