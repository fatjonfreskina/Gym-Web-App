<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Trainee page</title>
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
<jsp:include page="/jsp/include/footer.jsp"/>
</body>
</html>
