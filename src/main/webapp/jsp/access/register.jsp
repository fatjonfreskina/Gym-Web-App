<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register</title>
</head>
<body>
    <jsp:include page="../include/header.jsp"/><br>
    <form method="post" action="<c:url value="/register"/>" enctype="multipart/form-data">

        <label>Tax Code : </label><input type="text" name="tax_code" ><br/><!-- pattern="^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$" required-->
        <label>First Name : </label><input type="text" name="first_name" ><br/>
        <label>Last Name : </label><input type="text" name="last_name" ><br/>
        <label>Birth Date : </label><input type="date" name="birth_date" ><br/>
        <label>Address : </label><input type="text" name="address" ><br/>
        <label>Telephone Number : </label><input type="tel" name="telephone_number" ><br>
        <label>Avatar (Optional) : </label><input type="file" name="avatar"><br/>
        <label>Email : </label><input type="text" name="email" ><br/>
        <label>Password : </label><input type="password" name="password"><br/>
        <label>Confirm Password : </label><input type="password" name="confirm_password" ><br/>
        <button type="submit" >Register</button>
    </form>

    <c:choose>
        <c:when test="${message.error}">
            <p><c:out value="${message.message}"/></p>
        </c:when>
    </c:choose>

    <jsp:include page="../include/footer.jsp"/>
</body>
</html>
