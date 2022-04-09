<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manage Subscription</title>
</head>
<body>
<jsp:include page="include/headersecreatry.jsp"/>



<form method="get" action="<c:url value="/secretary/rest/timeschedules"/>">
    <label>Course Name : </label>
    <select name="course_name">
        <c:forEach var="course" items="${courses}">
            <option  value="${course.name}"><c:out value="${course.name}"/></option><br>
        </c:forEach>
    </select><br>


    <label>Search : </label><input type="text" name="partial_email"><br>
    <label>Subscription Duration : </label>
    <select name="subscription_duration">
        <option  value="7">Free</option><br>
        <option  value="30">Mothly</option><br>
        <option  value="90">Quaterly</option><br>
        <option  value="180">Half-Yearly</option><br>
        <option  value="365">Year</option><br>
    </select><br>


    <input type="submit" name="Submit"/>
</form>





<jsp:include page="../include/footer.jsp"/>
</body>
</html>