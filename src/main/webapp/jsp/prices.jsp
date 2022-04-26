<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Hashtable" %>
<%@ page import="org.postgresql.core.Tuple" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Prices</title>
    <jsp:include page="include/bootstrap.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/global_style.css"/>">
</head>
<body>
<header>
    <jsp:include page="/jsp/include/header.jsp"/>
</header>
<main class="global-container">
    <table class="table table-sm table-bordered text-center align-middle">
        <thead class="thead-dark">
        <tr>
            <th class="header">Course</th>
            <th class="header">Type of Subscription</th>
            <th class="header">Price</th>
            <th class="header">First Event</th>
            <th class="header">Last Event</th>
            <th class="header">Trainers</th>
            <th class="header">Days Per Week</th>
        </tr>
        </thead>
        <tbody>
            <c:set var="last_courseName" value=""/>
            <c:set var="last_courseId" value=""/>
            <%
                int startIndex = -1;
                List<Integer> rowsSpans = new ArrayList<>();
            %>
            <c:forEach var="prices" items="${pricesView}">
                <c:choose>
                    <c:when test="${!prices.courseName.equals(last_courseName) || !prices.courseEditionId.equals(last_courseId)}">
                        <%
                            rowsSpans.add(1);
                            startIndex++;
                        %>
                    </c:when>
                    <c:otherwise>
                        <%
                            rowsSpans.set(startIndex,rowsSpans.get(startIndex)+1);
                        %>
                    </c:otherwise>
                </c:choose>
                <c:set var="last_courseName" value="${prices.courseName}"/>
                <c:set var="last_courseId" value="${prices.courseEditionId}"/>
            </c:forEach>
            <% request.setAttribute("rowsSpans",rowsSpans); %>

            <c:set var="last_courseName" value=""/>
            <c:set var="last_courseId" value=""/>
            <% startIndex = -1; %>
            <c:forEach var="prices" items="${pricesView}">
                <c:choose>
                    <c:when test="${!prices.courseName.equals(last_courseName) || !prices.courseEditionId.equals(last_courseId)}">
                        <tr>
                            <% request.setAttribute("startIndex",++startIndex); %>
                            <td rowspan="<c:out value="${rowsSpans[startIndex]}"/>"><c:out value="${prices.courseName}"/></td>
                            <td><c:out value="${prices.duration}"/></td>
                            <td><c:out value="${prices.cost}"/></td>
                            <td rowspan="<c:out value="${rowsSpans[startIndex]}"/>" ><c:out value="${prices.min}"/></td>
                            <td rowspan="<c:out value="${rowsSpans[startIndex]}"/>" ><c:out value="${prices.max}"/></td>
                            <c:set var="trainers" value=""/>
                            <c:forEach var="trainer" items="${prices.trainers}">
                                <c:set var="trainers" value="${trainers} ${trainer}"/>
                            </c:forEach>

                            <td rowspan="<c:out value="${rowsSpans[startIndex]}"/>" ><c:out value="${trainers}"/></td>
                            <td rowspan="<c:out value="${rowsSpans[startIndex]}"/>" ><c:out value="${prices.lecturesPerWeek}"/></td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td><c:out value="${prices.duration}"/></td>
                            <td><c:out value="${prices.cost}"/></td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                <c:set var="last_courseName" value="${prices.courseName}"/>
                <c:set var="last_courseId" value="${prices.courseEditionId}"/>

            </c:forEach>
        </tbody>

    </table>
</main>
<jsp:include page="/jsp/include/footer.jsp"/>
</body>
</html>
