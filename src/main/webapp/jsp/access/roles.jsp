<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Roles</title>
        <meta charset="UTF-8"/>
        <jsp:include page="/jsp/include/style.jsp"/>
        <link rel="stylesheet" href="<c:url value="/css/main.css"/>"/>
        <jsp:include page="/jsp/include/favicon.jsp"/>
    </head>
    <body>
        <header>
            <jsp:include page="../include/header.jsp"/>
        </header>
        <main class="global-container">

            <div class="d-flex justify-content-center align-items-center w-100 h-100">

                <form method="post" action="<c:url value="/access/roles"/>">
                    <c:forEach var="role" items="${sessionScope.roles}">
                        <c:if test="${role=='trainee'}">
                            <div class="form-group row">
                                <input type="submit" name="trainee" value="Trainee" class="btn btn-primary btn-lg btn-block"/>
                            </div>
                        </c:if>
                        <c:if test="${role=='trainer'}">
                            <div class="form-group row ">
                                <input type="submit" name="trainer" value="Trainer" class="btn btn-primary btn-lg btn-block"/>
                            </div>
                        </c:if>
                        <c:if test="${role=='secretary'}">
                            <div class="form-group row">
                                <input type="submit" name="secretary" value="Secretary" class="btn btn-primary btn-lg btn-block"/>
                            </div>
                        </c:if>
                    </c:forEach>
                </form>
            </div>

        </main>
        <jsp:include page="../include/footer.jsp"/>
        <jsp:include page="/jsp/include/scripts.jsp"/>
    </body>
</html>
