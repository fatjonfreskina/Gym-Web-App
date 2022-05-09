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
                <li class="d-flex pt-5">
                    <div style="width: 150px; height: 150px;">
                        <object class="rounded-circle"
                                data="<c:url value="/images/staff/${trainer.person.name}-${trainer.person.surname}.jpg"/>"
                                type="image/jpg" width="150px" height="150px">
                            <img class="rounded-circle" src="<c:url value="/images/staff/default-user-image.jpg"/>"
                                 width="100%" height="100%"/>
                        </object>
                    </div>
                    <div class="d-flex align-items-start justify-content-start flex-column pl-3 h-100">
                        <h1><c:out value="${trainer.person.surname}"/> <c:out value="${trainer.person.name}"/></h1>
                        <c:forEach var="teach" items="${trainer.teaches}" varStatus="loop">
                            <p class="font-weight-bold m-0"><c:out value="${teach.courseName}"/></p>
                        </c:forEach>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </c:if>
</main>
<jsp:include page="/jsp/include/footer.jsp"/>
<jsp:include page="/jsp/include/scripts.jsp"/>
</body>
</html>
