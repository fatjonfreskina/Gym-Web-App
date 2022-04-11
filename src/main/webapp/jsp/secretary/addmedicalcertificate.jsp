<%--
  User: Simone D'Antimo
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add medical certificate</title>
</head>
<body>
<jsp:include page="include/headersecreatry.jsp"/><br>

<form method="post" action="<c:url value="/secretary/addMedicalCertificate"/>" enctype="multipart/form-data">

    <label>Email : </label><input type="text" name="person" id="person" value=""><br/>
    <label>Expiration Date : </label><input type="date" name="expirationdate" id="expirationdate" value="2023-09-06"><br/>
    <label>Doctor name : </label><input type="text" name="doctorname" id="doctorname" value="Mario Rossi"><br/>
    <label>Medical Certificate: </label><input type="file" name="medical_Certificate"  ><br/>

    <button type="submit" >Add certificate</button>
</form>

<c:choose>
    <c:when test="${message.error}">
        <p><c:out value="${message.message}"/></p>
    </c:when>
</c:choose>

<jsp:include page="../include/footer.jsp"/>
</body>
</html>
