<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>GWA</title>
    <meta charset="UTF-8">
    <jsp:include page="/jsp/include/style.jsp"/>
    <jsp:include page="/jsp/include/favicon.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/home.css"/>">
</head>
<body>
<header>
    <jsp:include page="/jsp/include/header.jsp"/>
</header>
<main class="global-container">
    <h1>
        ARE YOU READY TO GET FIT, STRONG AND MOTIVATED?
    </h1>
    <div>
        Welcome to GWA! <br>
        In this gym you will find only the best courses and trainers that will help you to reach
        all your goals.
    </div>
    <div class="clearleft">
        <img class="left" src="<c:url value="/images/photo_gym_1.jpg"/>" alt="" width="25%" height="250px"/>
        <h2 class="sub">Many possible courses</h2>
        <div class="description">
            We offer you the possibility to attend several courses, ranging from Yoga to Powerlifting.<br>
            Each course will be held in a well-equipped room.
        </div>
        <div class="translatetopleft">
            <a class="secondarylink" href="<c:url value="/courses"/>">
                <div class="discover">DISCOVER MORE</div>
            </a>
        </div>

    </div>
    <div class="clearright">
        <img class="right" src="<c:url value="/images/best_trainers.jpg"/>" alt=""width="25%" height="250px"/>
        <h2 class="sub">Only the best trainers</h2>
        <div class="description">
            In our gym trainers are all qualified and experienced to make you reach all your goals.<br>
            Each trainer has been chosen carefully in order to provide you the best training sessions.
        </div>
        <div class="translatetopright">
            <a class="secondarylink" href="<c:url value="/staff"/>">
                <div class="discover">DISCOVER MORE</div>
            </a>
        </div>

    </div>
    <div class="clearleft">
        <img class="left" src="<c:url value="/images/low_prices.jpg"/>" alt="" width="25%" height="250px"/>
        <h2 class="sub">Affordable prices</h2>
        <div class="description">
            All our courses have affordable prices.<br>
            We offer different subscription plans based on
            the course you want to attend. <br>
            There are also discounts when buying long time subscriptions!
        </div>
        <div class="translatetopleft">
            <a class="secondarylink" href="<c:url value="/prices"/>">
                <div class="discover">DISCOVER MORE</div>
            </a>
        </div>
    </div>
    <div class="clearright">
        <img class="right" src="<c:url value="/images/browsing.jpg"/>" alt="" width="25%" height="250px"/>
        <h2 class="sub">Manage everything from here</h2>
        <div class="description">
            By creating an account it will be possible to manage everything related to your
            experience in our gym from here.<br>
            You can book attendances for courses, handle your personal information and much more.
        </div>

    </div>
</main>
<jsp:include page="/jsp/include/footer.jsp"/>
<jsp:include page="/jsp/include/scripts.jsp"/>
</body>
</html>
