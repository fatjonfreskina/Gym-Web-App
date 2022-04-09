<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<c:if test="${null != sessionScope.avatarPath}">
    <img src="${sessionScope.avatarPath}" sizes="5%"> <br>
</c:if>
<c:out value="${sessionScope.email}"/> <br>
<c:choose>
    <c:when test="${fn:length(sessionScope.roles) > 1}">
        <c:forEach var="role" items="${sessionScope.roles}">
            <a href="<c:url value="/${role}"/>"><c:out value="${role}"/></a><br>
        </c:forEach>
    </c:when>
    <c:otherwise>
    </c:otherwise>
</c:choose>
<a href="<c:url value="/logout"/>">Log Out</a>





