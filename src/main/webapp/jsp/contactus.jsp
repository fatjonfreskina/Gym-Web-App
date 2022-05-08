<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>About Us</title>
    <meta charset="UTF-8">
    <jsp:include page="/jsp/include/style.jsp"/>
    <jsp:include page="/jsp/include/favicon.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">

</head>
<body>
<header>
    <jsp:include page="/jsp/include/header.jsp"/>
</header>
<main class="global-container">
    <!--<div class="row" style="margin: auto;">-->
        <!--
        <div class="column">
            <p>GWA</p>
            <ul>
                <li>Location: Via Gradenigo 6/A 35131 Padova PD</li>
                <li>Tel: +39 0123 456789</li>
                <li>E-mail: secretarygwa@mail.com</li>
            </ul>

        </div>-->
    <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2801.014020625052!2d11.892157315818293!3d45.409056845141826!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x477edb664644067d%3A0xb57b9fafc118efea!2sDipartimento%20di%20Ingegneria%20dell&#39;Informazione%20(DEI)!5e0!3m2!1sit!2sit!4v1651593340404!5m2!1sit!2sit" width="600" height="450" style="border:0;display: block; margin:auto;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade" ></iframe>

    <div style=" margin:0 auto;">
        <div>
            Location: Via Gradenigo 6/A 35131 Padova PD<br>
            Tel: +39 0123 456789<br>
            E-mail: secretarygwa@mail.com<br>
        </div>
        <!--<p style=" margin-top: 2em; alignment: center; margin-right: auto;margin-left: auto">
            Location: Via Gradenigo 6/A 35131 Padova PD<br>
            Tel: +39 0123 456789<br>
            E-mail: secretarygwa@mail.com<br>
        </p>-->
    </div>



    <!--</div>-->
</main>
<jsp:include page="/jsp/include/footer.jsp"/>
<jsp:include page="/jsp/include/scripts.jsp"/>
</body>
</html>
