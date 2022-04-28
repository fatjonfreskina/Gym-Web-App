<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Courses</title>
    <meta charset="UTF-8">
    <jsp:include page="include/bootstrap.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/global_style.css"/>">
</head>
<body>
<header>
    <jsp:include page="/jsp/include/header.jsp"/>
</header>
<main class="global-container">
    <table>
        <c:forEach var="course" items="${courseList}">
            <tr>
                <td><img src="<c:url value="/images/courses/${course.name}.jpg"/>" alt="Image ${course.name}" width="20%"
                         height="20%"/></td>
                <td><c:out value="${course.name}"/></td>
                <td><c:out value="${course.description}"/></td>
            </tr>
        </c:forEach>
    </table>
</main>
<jsp:include page="/jsp/include/footer.jsp"/>
</body>
</html>