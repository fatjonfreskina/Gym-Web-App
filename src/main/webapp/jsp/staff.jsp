<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Staff</title>
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
    <c:if test="${not empty trainerlist}">
        <ul>
            <c:forEach var="trainer" items="${trainerlist}">
                <li>


                    <object data="<c:url value="/images/staff/${trainer.person.name}-${trainer.person.surname}.jpg"/>" type="image/jpg" width="15%" height="15%">
                        <img src="<c:url value="/images/staff/default-user-image.jpg"/>" width="15%" height="15%"/>
                    </object>


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
<jsp:include page="/jsp/include/scripts.jsp"/>
</body>
</html>
