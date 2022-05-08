<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Hashtable" %>
<%@ page import="org.postgresql.core.Tuple" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Prices</title>
    <meta charset="UTF-8">
    <jsp:include page="/jsp/include/style.jsp"/>
    <jsp:include page="/jsp/include/favicon.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/tableprices.css"/>">
</head>
<body>
<header>
    <jsp:include page="/jsp/include/header.jsp"/>
</header>
<main class="global-container">
    <div class="container-table100">
        <div class="wrap-table100">
            <div class="table100 ver1 m-b-110">
                <div class="table100-head">
                    <table>
                        <thead>
                        <tr class="row100 head">
                            <th class="cell100 column1">Course</th>
                            <th class="cell100 column2">Type</th>
                            <th class="cell100 column3">Price</th>
                            <th class="cell100 column4">From-To</th>
                            <th class="cell100 column5">Trainers</th>
                            <th class="cell100 column6">Per week</th>
                        </tr>
                        </thead>
                    </table>
                </div>
                <div class="table100-body js-pscroll ps ps--active-y">
                    <table>
                        <tbody>


                        <c:set var="last_courseName" value=""/>
                        <c:set var="last_courseId" value=""/>
                        <c:forEach var="prices" items="${pricesView}">
                            <c:choose>
                                <c:when test="${!prices.courseName.equals(last_courseName) || !prices.courseEditionId.equals(last_courseId)}">
                                    <tr class="row100 body">
                                        <td class="cell100 column1"><c:out value="${prices.courseName}"/></td>
                                        <td class="cell100 column2"><c:out value="${prices.duration}"/> days</td>
                                        <td class="cell100 column3"><c:out value="${prices.cost}"/> €</td>
                                        <td class="cell100 column4"><c:out value="${prices.min}"/> to <c:out value="${prices.max}"/></td>
                                        <c:set var="trainers" value=""/>
                                        <c:forEach var="trainer" items="${prices.trainers}">
                                            <c:set var="trainers" value="${trainers} ${trainer}"/>
                                        </c:forEach>
                                        <td class="cell100 column5"><c:out value="${trainers}"></c:out></td>
                                        <td class="cell100 column6"><c:out value="${prices.lecturesPerWeek}"></c:out></td>

                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                    <tr class="row100 body">
                                        <td class="cell100 column1"></td>
                                        <td class="cell100 column2"><c:out value="${prices.duration}"/> days</td>
                                        <td class="cell100 column3"><c:out value="${prices.cost}"/> €</td>
                                    <td class="cell100 column4"></td>
                                    <td class="cell100 column5"></td>
                                    <td class="cell100 column6"></td>
                                    </tr>

                                </c:otherwise>
                            </c:choose>
                            <c:set var="last_courseName" value="${prices.courseName}"/>
                            <c:set var="last_courseId" value="${prices.courseEditionId}"/>
                        </c:forEach>
                        </tbody>
                    </table>
                 </div>
            </div>
        </div>
    </div>
</main>
<jsp:include page="/jsp/include/footer.jsp"/>
<jsp:include page="/jsp/include/scripts.jsp"/>
</body>
</html>
