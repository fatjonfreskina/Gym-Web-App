<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Staff</title>
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
    <c:if test="${not empty trainerlist}">
        <ul>
            <c:forEach var="trainer" items="${trainerlist}">
                <li>
                    <img src="<c:url value="/images/staff/${trainer.person.name}-${trainer.person.surname}.jpg"/>"
                         width="15%" height="15%" alt="Image ${trainer.person.name}">
                    <b><c:out value="${trainer.person.surname}"/> <c:out value="${trainer.person.name}"/>:</b>
                    <c:forEach var="teach" items="${trainer.teaches}" varStatus="loop">
                        <c:out value="${teach.courseName}"/>
                        <c:if test="${!loop.last}">,</c:if>
                    </c:forEach>
                </li>
            </c:forEach>
        </ul>
    </c:if>
</main>
<jsp:include page="/jsp/include/footer.jsp"/>
</body>
</html>
