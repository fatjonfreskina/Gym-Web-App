<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Manage Subscription</title>
    <meta charset="UTF-8">
    <jsp:include page="../include/bootstrap.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/global_style.css"/>">
</head>
<body>
<header>
    <jsp:include page="/jsp/secretary/include/headersecretary.jsp"/>
</header>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>

<main class="global-container">
    <form method="post" action="<c:url value="/secretary/rest/addsubscription"/>">

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
            <label for="partial_email" class="col-sm-2 col-form-label" >Search :</label>
            <div class="col-sm-10">
                <input type="search" name="partial_email" id="partial_email" class="form-control" placeholder="Insert Email">
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
            <label for="discount" class="col-sm-2 col-form-label">Discount :</label>
            <div class="col-sm-10">
                <input type="number" id="discount" name = "discount" min = "0" max ="100" value="0" class="form-control"/>
            </div>
        </div>

        <div class="form-group row">
            <label for="list_emails" class="col-sm-2 col-form-label">Users :</label>
            <div class="col-sm-10">
                <ul id="list_emails" class="list-group"></ul>
            </div>
        </div>

        <div class="form-group row">
            <label for="list_last_date" class="col-sm-2 col-form-label">Last Event :</label>
            <div class="col-sm-10">
                <ul id="list_last_date" class="list-group"></ul>
            </div>
        </div>

        <input type="submit" name="Submit" class="btn btn-outline-primary btn-lg"/>
    </form>
</main>


<script>
    $("#partial_email").on('input',function (){
        $.ajax({
            url: "<c:url value="/secretary/rest/listlikepersons"/>",
            data: {
                "partial_email" : $("#partial_email").val()
            },
            cache: false,
            type: "GET",
            dataType: 'json',
            success: function(response) {
                $("#list_emails").empty()

                console.log(response)

                for(const person of response)
                {
                    $("#list_emails").append("<li class='list-group-item'>"+"<input type='radio' name = 'trainee' value = '"+person.email+"'"+"/>"+" "+person.email+"</li>")
                }
            },
            error: function(xhr)
            {
                console.log(xhr);
            }
        });
    })

    $("#course_name").change(function (){
        $.ajax({
            url: "<c:url value="/secretary/rest/timeschedules"/>",
            data: {
                "course_name" : $("#course_name").val()
            },
            cache: false,
            type: "GET",
            dataType: 'json',
            success: function(response) {
                $("#list_last_date").empty()
                for(const lastDate of response)
                {
                    $("#list_last_date").append("<li class='list-group-item'>"+"<input type='radio' name='course_edition_id' value='"+lastDate.courseEditionId+"'/>"+" "+lastDate.date+"</li>")
                }
            },
            error: function(xhr)
            {
                console.log(xhr);
            }
        });
    })
    $("#course_name").trigger('change')

</script>
<jsp:include page="../include/footer.jsp"/>
</body>
</html>