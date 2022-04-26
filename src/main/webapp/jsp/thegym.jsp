<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>The Gym</title>
    <jsp:include page="include/bootstrap.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/global_style.css"/>">
</head>
<body>
<header>
    <jsp:include page="include/header.jsp"/>
</header>
<main class="global-container">
    <div class="container d-flex justify-content-center flex-column align-items-center">
        <div style="width:600px !important;">
            <div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-indicators">
                    <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="0" class="active"
                            aria-current="true" aria-label="Slide 1"></button>
                    <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="1"
                            aria-label="Slide 2"></button>
                    <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="2"
                            aria-label="Slide 3"></button>
                    <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="3"
                            aria-label="Slide 4"></button>
                </div>
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <img width="600" height="333" src="<c:url value="/images/thegym/the_gym_unsplash_1.jpg"/>"
                             class="d-block w-100" alt="Weight Room">
                        <div class="carousel-caption d-none d-md-block"
                             style="background: rgba(0, 0, 0, 0.35); right: 0; left: 0;">
                            <h5>Weight Room</h5>
                            <%--<p>Weight room is for bodybuilder and powerlifters.</p>--%>
                        </div>
                    </div>
                    <div class="carousel-item">
                        <img width="600" height="333" src="<c:url value="/images/thegym/the_gym_unsplash_2.jpg"/>"
                             class="d-block w-100" alt="Yoga Room">
                        <div class="carousel-caption d-none d-md-block"
                             style="background: rgba(0, 0, 0, 0.35); right: 0; left: 0;">
                            <h5>Yoga Room</h5>
                            <%--<p>Yoga room is for yoga classes.</p>--%>
                        </div>
                    </div>
                    <div class="carousel-item">
                        <img width="600" height="333" src="<c:url value="/images/thegym/the_gym_unsplash_3.jpg"/>"
                             class="d-block w-100" alt="Calisthenics Room">
                        <div class="carousel-caption d-none d-md-block"
                             style="background: rgba(0, 0, 0, 0.35); right: 0; left: 0;">
                            <h5>Calisthenics Room</h5>
                            <%--<p>Calisthenics Room is for calisthenics classes.</p>--%>
                        </div>
                    </div>
                    <div class="carousel-item">
                        <img width="600" height="333" src="<c:url value="/images/thegym/the_gym_unsplash_4.jpg"/>"
                             class="d-block w-100" alt="Swimming Pool">
                        <div class="carousel-caption d-none d-md-block"
                             style="background: rgba(0, 0, 0, 0.35); right: 0; left: 0;">
                            <h5>Swimming Pool</h5>
                            <%--<p>Swimming Pool is for swimming classes.</p>--%>
                        </div>
                    </div>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions"
                        data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions"
                        data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        </div>
        <h1>Rooms</h1>
        <div class="item">
            <img width="300" height="200"
                 src="<c:url value="/images/thegym/the_gym_rooms.jpg"/>"
                 alt="Rooms planimetry">
        </div>
    </div>
</main>
<jsp:include page="include/footer.jsp"/>
</body>
</html>
