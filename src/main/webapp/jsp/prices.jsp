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
                <th>Course</th><th>Type of Subscription</th><th>Price</th><th>Min</th><th>Max</th>
            </tr>
        </thead>
        <c:set var="last_courseName" value=""/>
        <c:set var="last_courseId" value=""/>
        <c:forEach var="prices" items="${pricesView}">
            <c:choose>
                <c:when test="${!prices.courseName.equals(last_courseName) || !prices.courseEditionId.equals(last_courseId)}">
                    <tr>
                        <td><c:out value="${prices.courseName}"/></td>
                        <td><c:out value="${prices.duration}"/></td>
                        <td><c:out value="${prices.cost}"/></td>
                        <td><c:out value="${prices.min}"/></td>
                        <td><c:out value="${prices.max}"/></td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td></td>
                        <td><c:out value="${prices.duration}"/></td>
                        <td><c:out value="${prices.cost}"/></td>
                    </tr>
                </c:otherwise>
            </c:choose>
            <c:set var="last_courseName" value="${prices.courseName}"/>
            <c:set var="last_courseId" value="${prices.courseEditionId}"/>
        </c:forEach>
    </table>
    <jsp:include page="/jsp/include/footer.jsp"/>
</body>
</html>
