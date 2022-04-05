<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Courses</title>
</head>
<body>
<jsp:include page="/jsp/include/header.jsp"/><br>

<table>
    <c:forEach var="course" items="${courseList}">
        <tr>

            <td><img src="${pageContext.request.contextPath}/images/${course.name}" alt="" /></td>
            <td><c:out value="${course.name}"/></td>
            <td><c:out value="${course.description}"/></td>
        </tr>
    </c:forEach>
</table>
<jsp:include page="/jsp/include/footer.jsp"/>
</body>
</html>