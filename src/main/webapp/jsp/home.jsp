<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>GWA</title>
    <meta charset="UTF-8">
    <jsp:include page="/jsp/include/style.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
</head>
<body>
<header>
    <jsp:include page="/jsp/include/header.jsp"/>
</header>
<main class="global-container">
    <div class="carousel-main-box">
        <div id="carouselExampleCaptions" class="carousel slide" data-ride="carousel">
            <ol class="carousel-indicators">
                <li data-target="#carouselExampleCaptions" data-slide-to="0" class="active bg-info"></li>
                <li data-target="#carouselExampleCaptions" data-slide-to="1" class="bg-info"></li>
                <li data-target="#carouselExampleCaptions" data-slide-to="2" class="bg-info"></li>
                <li data-target="#carouselExampleCaptions" data-slide-to="3" class="bg-info"></li>
                <li data-target="#carouselExampleCaptions" data-slide-to="4" class="bg-info"></li>
            </ol>
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="<c:url value="/images/photo_gym_1.jpg"/>" class="d-block" alt="Photo Gym 1">
                    <div class="carousel-caption d-none d-md-block text-info">
                        <h5>Photo Gym 1</h5>
                    </div>
                </div>
                <div class="carousel-item text-info">
                    <img src="<c:url value="/images/photo_gym_2.jpg"/>" class="d-block" alt="Photo Gym 2">
                    <div class="carousel-caption d-none d-md-block text-info">
                        <h5>Photo Gym 2</h5>
                    </div>
                </div>
                <div class="carousel-item text-info">
                    <img src="<c:url value="/images/photo_gym_3.jpg"/>" class="d-block" alt="Photo Gym 3">
                    <div class="carousel-caption d-none d-md-block text-info">
                        <h5>Photo Gym 3</h5>
                    </div>
                </div>
                <div class="carousel-item text-info">
                    <img src="<c:url value="/images/photo_gym_4.jpg"/>" class="d-block" alt="Photo Gym 4">
                    <div class="carousel-caption d-none d-md-block text-info">
                        <h5>Photo Gym 4</h5>
                    </div>
                </div>
                <div class="carousel-item text-info">
                    <img src="<c:url value="/images/the_gym-unsplash.jpg"/>" class="d-block" alt="The Gym Unsplash">
                    <div class="carousel-caption d-none d-md-block text-info">
                        <h5>The Gym Unsplash</h5>
                    </div>
                </div>
            </div>
            <a class="carousel-control-prev" href="#carouselExampleCaptions" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="carousel-control-next" href="#carouselExampleCaptions" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>
    </div>
</main>
<jsp:include page="/jsp/include/footer.jsp"/>
<jsp:include page="/jsp/include/scripts.jsp"/>
</body>
</html>
