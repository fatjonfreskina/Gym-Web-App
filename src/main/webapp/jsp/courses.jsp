<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Courses</title>
    <jsp:include page="include/bootstrap.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/jsp/include/header.jsp"/>
<main>
    <table>
        <c:forEach var="course" items="${courseList}">
            <tr>
                <td><img src="<c:url value="/images/courses/${course.name}.jpg"></c:url>" alt="" width="20%"
                         height="20%"/></td>
                <td><c:out value="${course.name}"/></td>
                <td><c:out value="${course.description}"/></td>
            </tr>
        </c:forEach>
    </table>
</main>
<jsp:include page="/jsp/include/footer.jsp"/>
</body>
</html>