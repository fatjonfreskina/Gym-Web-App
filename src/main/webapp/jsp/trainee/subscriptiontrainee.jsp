<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<table class="table">
    <thead class="thead-dark">
    <tr>
        <th class = 'text-warning' scope='col'>Your subscriptions</th>
        <th scope='col'>Trainer</th>
        <th scope='col'>Expiration</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${not empty subscriptionlist}">
        <c:forEach var="elem" items="${subscriptionlist}">
                <tr>
                    <td><c:out value="${elem.subscription.courseName}"/></td>
                    <td><c:out value="${elem.trainer.surname}"/> <c:out value="${elem.trainer.name}"/></td>
                    <td><c:out value="${elem.expiration}"/></td>
                </tr>
        </c:forEach>
    </c:if>
    </tbody>
</table>