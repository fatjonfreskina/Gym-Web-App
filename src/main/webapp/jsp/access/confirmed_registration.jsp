<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <title>Confirmed Registration</title>
        <meta charset="UTF-8"/>
        <link rel="stylesheet" href="<c:url value="/css/main.css"/>"/>
        <jsp:include page="/jsp/include/style.jsp"/>
        <jsp:include page="/jsp/include/favicon.jsp"/>
    </head>
    <body>
        <header>
            <jsp:include page="../include/header.jsp"/>
        </header>

        <main class="global-container">
            <c:choose>
                <c:when test="${message.error}">
                    <h2><c:out value="${message.message}"/></h2>
                </c:when>
                <c:otherwise>
                    <h2>
                        YOUR REGISTRATION HAS BEEN COMPLETED SUCCESSFULLY!!!
                    </h2>
                </c:otherwise>
            </c:choose>
        </main>
        <jsp:include page="/jsp/include/footer.jsp"/>
        <jsp:include page="/jsp/include/scripts.jsp"/>
    </body>
</html>
