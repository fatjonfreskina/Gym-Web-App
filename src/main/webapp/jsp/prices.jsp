<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Prices</title>
</head>
<body>
    <jsp:include page="/jsp/include/header.jsp"/><br>
    <table>
        <thead>
            <tr>
                <th>Course</th><th>Type of Subscription</th><th>Price</th>
            </tr>
        </thead>
        <c:set var="last_printed" value=""/>
        <c:forEach var="subscriptionType" items="${subscriptionTypeList}">
            <c:if test="${!subscriptionType.courseName.equals(last_printed)}">
                <tr>
                    <td><c:out value="${subscriptionType.courseName}"/></td>
                    <td><c:out value="${subscriptionType.duration}"/></td>
                    <td><c:out value="${subscriptionType.cost}"/></td>
                </tr>
            </c:if>
            <c:if test="${subscriptionType.courseName.equals(last_printed)}">
                <tr>
                    <td></td>
                    <td><c:out value="${subscriptionType.duration}"/></td>
                    <td><c:out value="${subscriptionType.cost}"/></td>
                </tr>
            </c:if>
            <c:set var="last_printed" value="${subscriptionType.courseName}"/>
        </c:forEach>
    </table>
    <jsp:include page="/jsp/include/footer.jsp"/>
</body>
</html>
