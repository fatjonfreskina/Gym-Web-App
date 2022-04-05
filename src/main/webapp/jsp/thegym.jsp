<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>The Gym</title>

    <!--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>-->
</head>
<body>
    <jsp:include page="include/header.jsp"/><br>
    <!-- <img src="${pageContext.request.contextPath}/images/the_gym-unsplash.jpg"> -->
    <h1>Rooms</h1>
    <div id="theGymCarousel" class="carousel slide" data-ride="carousel"><!--style="max-width: 600px;" -->
        <!-- Indicators -->
        <ol class="carousel-indicators">
            <li data-target="#theGymCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#theGymCarousel" data-slide-to="1"></li>
            <li data-target="#theGymCarousel" data-slide-to="2"></li>
        </ol>

        <!-- Wrapper for slides -->
        <div class="carousel-inner">
            <div class="item active">
                <img width="600" height="333"
                     src="https://images.unsplash.com/photo-1540497077202-7c8a3999166f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80"

                     alt="Gym Room">
                     <!--https://images.unsplash.com/photo-1570829460005-c840387bb1ca?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1032&q=80"-->

            </div>

            <div class="item">
                <img width="600" height="333"
                     src="https://images.unsplash.com/photo-1588286840104-8957b019727f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80"
                     alt="Yoga Room">
            </div>

            <div class="item">
                <img width="600" height="333"
                     src="https://images.unsplash.com/photo-1556817411-9571d6fba73b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80"
                     alt="Calisthenics Room">
            </div>
        </div>

        <!-- Left and right controls -->
        <a class="left carousel-control" href="#theGymCarousel" data-slide="prev">
            <span class="glyphicon glyphicon-chevron-left"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="right carousel-control" href="#theGymCarousel" data-slide="next">
            <span class="glyphicon glyphicon-chevron-right"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>

    <br><jsp:include page="include/footer.jsp"/>
</body>
</html>
