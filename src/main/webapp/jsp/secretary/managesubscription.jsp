<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Manage Subscription</title>
    <meta charset="UTF-8">
    <jsp:include page="/jsp/include/style.jsp"/>
    <jsp:include page="/jsp/include/favicon.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
</head>
<body>
<header>
    <jsp:include page="/jsp/secretary/include/headersecretary.jsp"/>
</header>

<main class="global-container">
    <form method="post" action="<c:url value="/secretary/rest/addsubscription"/>" id="form">

        <div class="form-group row">
            <label for="course_name" class="col-sm-3 col-form-label">Course Name :</label>
            <div class="col-sm-9">
                <select name="course_name" id="course_name" class="form-control">
                    <c:forEach var="course" items="${courses}">
                        <option  value="${course.name}"><c:out value="${course.name}"/> </option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="form-group row">
            <label for="partial_email" class="col-sm-3 col-form-label" >Search :</label>
            <div class="col-sm-9">
                <div class="input-group rounded">
                    <input type="search" name="partial_email" id="partial_email" class="form-control rounded" placeholder="Insert Email" aria-label="Search" aria-describedby="search-addon">
                    <span class="input-group-text border-0" id="search-addon">
                        <i class="fas fa-search"></i>
                    </span>
                </div>


            </div>
        </div>

        <div class="form-group row">
            <label for="duration" class="col-sm-3 col-form-label">Subscription Duration :</label>
            <div class="col-sm-9">
                <select id="duration" class="form-control" name="duration">
                    <option  value="7">Free</option>
                    <option  value="30">Mothly</option>
                    <option  value="90">Quaterly</option>
                    <option  value="180">Half-Yearly</option>
                    <option  value="365">Year</option>
                </select>
            </div>
        </div>

        <div class="form-group row">
            <label for="discount" class="col-sm-3 col-form-label">Discount :</label>
            <div class="col-sm-9">
                <input type="number" id="discount" name = "discount" min = "0" max ="100" value="0" class="form-control" />
            </div>
        </div>

        <div class="form-group row">
            <label for="list_emails" class="col-sm-3 col-form-label">Users :</label>
            <div class="col-sm-9">
                <ul id="list_emails" class="list-group"></ul>
            </div>
        </div>

        <div class="form-group row">
            <label for="list_last_date" class="col-sm-3 col-form-label">Last Event :</label>
            <div class="col-sm-9">
                <ul id="list_last_date" class="list-group"></ul>
            </div>
        </div>

        <div id="alert-box" class="alert alert-warning alert-dismissible fade show" role="alert" style="display: none;">
            <p id="alert-message-body" class="alert-box-message">
            </p>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>

        <input type="submit" name="Submit" class="btn btn-outline-primary btn-lg"/>
    </form>
</main>


<jsp:include page="../include/footer.jsp"/>
<jsp:include page="/jsp/include/scripts.jsp"/>
<script src="<c:url value="/js/secretary/manage-subscription.js"/>"></script>
</body>
</html>