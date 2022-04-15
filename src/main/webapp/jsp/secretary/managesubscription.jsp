<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manage Subscription</title>
</head>
<body>
<jsp:include page="/jsp/secretary/include/headersecreatry.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>


<form method="post" action="<c:url value="/secretary/rest/addsubscription"/>">
    <label>Course Name : </label>
    <select name="course_name" id="course_name">
        <c:forEach var="course" items="${courses}">
            <option  value="${course.name}"><c:out value="${course.name}"/> </option><br>
        </c:forEach>
    </select><br>


    <label>Search : </label><input type="text" name="partial_email" id="partial_email"><br>
    <label>Subscription Duration : </label>
    <select name="duration">
        <option  value="7">Free</option><br>
        <option  value="30">Mothly</option><br>
        <option  value="90">Quaterly</option><br>
        <option  value="180">Half-Yearly</option><br>
        <option  value="365">Year</option><br>
    </select><br>
    <label>Discount : </label><input type="number" name = "discount" min = "0" max ="100" value="0">

    <input type="submit" name="Submit"/>

    <ul id="list_emails"></ul>
    <ul id="list_last_date"></ul>



</form>

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
                    $("#list_emails").append("<li>"+person.email + "<input type='radio' name = 'trainee' value = '"+person.email+"'"+"/></li>")
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
                    $("#list_last_date").append("<li>" +lastDate.date+"<input type='radio' name='course_edition_id' value='"+lastDate.courseEditionId+"'/></li>")
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