<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Trainee page</title>
    <jsp:include page="../include/bootstrap.jsp"/>
    <jsp:include page="../include/fullcalendar.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link id="contextPathHolder" data="${pageContext.request.contextPath}"/>
</head>
<body>
<jsp:include page="/jsp/trainee/include/headertrainee.jsp"/>
<c:choose>
    <c:when test="${medicalCertificate != null}">
        <c:out value="Your Medical Certificate Expires : ${medicalCertificate.expirationDate}"></c:out>
    </c:when>
    <c:otherwise>
        <c:out value="You do not have a Medical Certificate please go to the secretary!!!"></c:out>
    </c:otherwise>
</c:choose>
<jsp:include page="/jsp/trainee/subscriptiontrainee.jsp"/>
<div id="trainee__calendar"></div>
<jsp:include page="/jsp/include/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/trainee/trainee.js"></script>
</body>
</html>
