<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Trainee page</title>
    <meta charset="UTF-8">
    <jsp:include page="/jsp/include/style.jsp"/>
    <jsp:include page="../include/fullcalendar/style.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
    <link id="contextPathHolder" data="${pageContext.request.contextPath}"/>
</head>
<body>
<header>
    <jsp:include page="/jsp/trainee/include/headertrainee.jsp"/>
</header>
<main class="global-container">
    <c:choose>
        <c:when test="${medicalCertificate != null}">
            <div id = "v_certificate">
                <c:out value="Your Medical Certificate Expires : ${medicalCertificate.expirationDate}"/>
            </div>
        </c:when>
        <c:otherwise>
            <div id = "v_certificate">
                <c:out value="You do not have a Medical Certificate please go to the secretary!!!"/>
            </div>
        </c:otherwise>
    </c:choose>
    <jsp:include page="/jsp/trainee/subscriptiontrainee.jsp"/>
    <div id="trainee__calendar"></div>
    <jsp:include page="../include/scripts.jsp"/>
    <jsp:include page="../include/fullcalendar/scripts.jsp"/>
    <jsp:include page="../include/moment/scripts.jsp"/>
    <script src="<c:url value="/js/trainee/trainee.js"/>"></script>
</main>
<jsp:include page="/jsp/include/footer.jsp"/>
</body>
</html>
