<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${message != null}">
        <div class="alert alert-warning alert-dismissible fade show col-sm-12" role="alert" id="error-backend">
            <c:out value="${message.message}"/>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
</c:choose>
