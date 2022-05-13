<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>The Gym</title>
    <meta charset="UTF-8">
    <jsp:include page="/jsp/include/style.jsp"/>
    <jsp:include page="/jsp/include/favicon.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
    <style>
        .carousel-main-box{
            width:600px;
            height:500px;
            margin-left: auto;
            margin-right: auto;
            margin-top: 50px;
        }
        .carouselimg {
            object-fit: cover;
            height: 500px;
        }


    </style>
</head>
<body>
<header>
    <jsp:include page="include/header.jsp"/>
</header>
<main class="global-container">
    <h1>Our gym</h1>
    <div>
        Our gym is composed of many rooms, each one having all the best equipment to allow you to perform the best
        training sessions. <br>
        Each room is dedicated to specific courses and has a limited number of slots in order to have the most
        personalized experience possible.

    </div>
    <div class="carousel-main-box">
        <div id="carouselExampleCaptions" class="carousel slide" data-ride="carousel">
            <ol class="carousel-indicators">
                <li data-target="#carouselExampleCaptions" data-slide-to="0" class="active bg-info"></li>
                <li data-target="#carouselExampleCaptions" data-slide-to="1" class="bg-info"></li>
                <li data-target="#carouselExampleCaptions" data-slide-to="2" class="bg-info"></li>
                <li data-target="#carouselExampleCaptions" data-slide-to="3" class="bg-info"></li>
                <li data-target="#carouselExampleCaptions" data-slide-to="4" class="bg-info"></li>
            </ol>
            <div class="carousel-inner " >
                <div class="carousel-item active">
                    <img src="<c:url value="/images/thegym/the_gym_unsplash_1.jpg"/>" class="d-block w-100 carouselimg"  alt="Photo Gym 1" >
                    <div class="carousel-caption d-none d-md-block text-info">
                        <h5>Weight Room</h5>
                    </div>
                </div>
                <div class="carousel-item text-info">
                    <img src="<c:url value="/images/thegym/the_gym_unsplash_2.jpg"/>" class="d-block w-100 carouselimg"  alt="Photo Gym 2" >
                    <div class="carousel-caption d-none d-md-block text-info">
                        <h5>Yoga Room</h5>
                    </div>
                </div>
                <div class="carousel-item text-info">
                    <img src="<c:url value="/images/thegym/the_gym_unsplash_3.jpg"/>" class="d-block w-100 carouselimg"  alt="Photo Gym 3" >
                    <div class="carousel-caption d-none d-md-block text-info">
                        <h5>Calisthenics Room</h5>
                    </div>
                </div>
                <div class="carousel-item text-info">
                    <img src="<c:url value="/images/thegym/the_gym_unsplash_4.jpg"/>" class="d-block w-100 carouselimg"  alt="Photo Gym 4" >
                    <div class="carousel-caption d-none d-md-block text-info">
                        <h5>Swimming Pool</h5>
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
<jsp:include page="include/footer.jsp"/>
<jsp:include page="/jsp/include/scripts.jsp"/>
</body>
</html>
