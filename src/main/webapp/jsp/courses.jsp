<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Courses</title>
    <meta charset="UTF-8">
    <jsp:include page="/jsp/include/style.jsp"/>
    <jsp:include page="/jsp/include/favicon.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
</head>
<body>
<header>
    <jsp:include page="/jsp/include/header.jsp"/>
</header>
<main class="global-container">
    <ul>
        <c:forEach var="course" items="${courseList}">
            <li class="d-flex pt-5">
                <div style="min-width: 150px;width: 150px; height: 150px;">
                    <img class="rounded-circle" src="<c:url value="/images/courses/${course.name}.jpg"/>"
                         alt="Image ${course.name}"
                         width="100%"
                         height="100%"/>
                </div>
                <div class="d-flex align-items-start justify-content-start flex-column pl-3 h-100">
                    <h1><c:out value="${course.name}"/></h1>
                    <p class="m-0"><c:out value="${course.description}"/></p>
                </div>
            </li>
        </c:forEach>
    </ul>
</main>
<jsp:include page="/jsp/include/footer.jsp"/>
<jsp:include page="/jsp/include/scripts.jsp"/>
</body>
</html>