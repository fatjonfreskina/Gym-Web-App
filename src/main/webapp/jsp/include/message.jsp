<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${message != null}">
        <div class="col-sm-12">
            <div id="error-backend" class="alert alert-warning alert-dismissible fade show" role="alert">
                <c:out value="${message.message}"/>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </div>
    </c:when>
</c:choose>

<!-- Alerts handled with JS -->
<div class="col-sm-12">
    <div id="alert-success" class="alert alert-success alert-dismissible fade show" role="alert" style="display: none;">
        <p id="alert-success-message-body" class="alert-box-message">
        </p>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close" >
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <div id="alert-warning" class="alert alert-warning alert-dismissible fade show" role="alert" style="display: none;">
        <p id="alert-warning-message-body" class="alert-box-message">
        </p>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close" >
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
</div>

