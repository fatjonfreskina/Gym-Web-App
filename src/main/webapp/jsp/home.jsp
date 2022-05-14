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
    <style>
        img.left{
            float: left;
            margin-right: 20px;
            object-fit: cover;
        }
        img.right{
            float: right;
            margin-left: 20px;
            object-fit: cover;
        }

        div.clearleft {
            position: relative;
            box-shadow: 5px 10px 18px #888888;
            border-radius: 25px;
            width: auto;
            height: auto;
            background-color:#C0C0C0;
            margin-top: 20px;
            overflow:hidden;
            clear: right;
            transition: transform 250ms;
        }
        div.clearright {
            position: relative;
            box-shadow: 5px 10px 18px #888888;
            border-radius: 25px;
            width: auto;
            height: auto;
            background-color: #C0C0C0;
            margin-top: 20px;
            overflow:hidden;
            clear: left;
            transition: transform 250ms;
        }
        div.description{
            padding: 0px 20px 0 20px;
        }
        h2.sub{
            padding: 20px 20px 0 20px;
        }
        div.clearright:hover {

            transform: scale(1.05);
        }
        div.clearleft:hover {

            transform: scale(1.05);
        }

        div.translatetopleft{
            position: absolute;
            text-align: center;
            bottom: -50px;
            right:0;
            width: 75%;
            height: 50px;
            background-color:#ff8484;
            transition: transform 250ms;
        }
        div.clearleft:hover>div.translatetopleft{
            transform: translateY(-50px);;
        }
        div.translatetopright{
            position: absolute;
            text-align: center;
            bottom: -50px;
            left:0;
            width: 75%;
            height: 50px;
            background-color:#ff8484;
            transition: transform 250ms;
        }
        div.clearright:hover>div.translatetopright{
            transform: translateY(-50px);;
        }
        a.secondarylink:link {
            color: rgb(33, 37, 41);
        }
        a.secondarylink:hover {
            color: rgb(33, 37, 41);
        }
        a.secondarylink:visited {
            color: rgb(31, 37, 41);
        }
        .discover{
            font-family: 'myFirstFont', sans-serif;
            font-size: 30px;
            font-weight: bold;
        }


    </style>
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
        <img class="right" src="<c:url value="/images/photo_gym_2.jpg"/>" alt=""width="25%" height="250px"/>
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
        <img class="left" src="<c:url value="/images/photo_gym_3.jpg"/>" alt="" width="25%" height="250px"/>
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
    <!--<div class="carousel-main-box">
        <div id="carouselExampleCaptions" class="carousel slide" data-ride="carousel" >
            <ol class="carousel-indicators">
                <li data-target="#carouselExampleCaptions" data-slide-to="0" class="active bg-info"></li>
                <li data-target="#carouselExampleCaptions" data-slide-to="1" class="bg-info"></li>
                <li data-target="#carouselExampleCaptions" data-slide-to="2" class="bg-info"></li>
                <%--<li data-target="#carouselExampleCaptions" data-slide-to="3" class="bg-info"></li>
                <li data-target="#carouselExampleCaptions" data-slide-to="4" class="bg-info"></li>--%>
            </ol>
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="<c:url value="/images/photo_gym_1.jpg"/>" class="d-block w-100" alt="Photo Gym 1">
                    <div class="carousel-caption d-none d-md-block text-info">
                        <h5>Photo Gym 1</h5>
                    </div>
                </div>
                <div class="carousel-item text-info">
                    <img src="<c:url value="/images/photo_gym_2.jpg"/>" class="d-block w-100" alt="Photo Gym 2">
                    <div class="carousel-caption d-none d-md-block text-info">
                        <h5>Photo Gym 2</h5>
                    </div>
                </div>
                <%--<div class="carousel-item text-info">
                    <img src="<c:url value="/images/photo_gym_3.jpg"/>" class="d-block w-100" alt="Photo Gym 3">
                    <div class="carousel-caption d-none d-md-block text-info">
                        <h5>Photo Gym 3</h5>
                    </div>
                </div>
                <div class="carousel-item text-info">
                    <img src="<c:url value="/images/photo_gym_4.png"/>" class="d-block w-100" alt="Photo Gym 4">
                    <div class="carousel-caption d-none d-md-block text-info">
                        <h5>Photo Gym 4</h5>
                    </div>
                </div>--%>
                <div class="carousel-item text-info">
                    <img src="<c:url value="/images/the_gym-unsplash.jpg"/>" class="d-block w-100" alt="The Gym Unsplash">
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
    </div>-->
</main>
<jsp:include page="/jsp/include/footer.jsp"/>
<jsp:include page="/jsp/include/scripts.jsp"/>
</body>
</html>
