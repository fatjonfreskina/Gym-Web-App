<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <jsp:include page="../include/bootstrap.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="../include/header.jsp"/>
<main>
    <form method="post" action="<c:url value="/register"/>" enctype="multipart/form-data">
        <label>Tax Code : </label><input type="text" name="tax_code" value="0123456789012345"><br/>
        <!-- pattern="^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$" required-->
        <label>First Name : </label><input type="text" name="first_name" value="a"><br/>
        <label>Last Name : </label><input type="text" name="last_name" value="a"><br/>
        <label>Birth Date : </label><input type="date" name="birth_date" value="1999-09-06"><br/>
        <label>Address : </label><input type="text" name="address" value="0"><br/>
        <label>Telephone Number : </label><input type="tel" name="telephone_number" value="0123456789"><br>
        <label>Avatar (Optional) : </label><input type="file" name="avatar"><br/>
        <label>Email : </label><input type="text" name="email" value=""><br/>
        <label>Password : </label><input type="password" name="password" value="a"><br/>
        <label>Confirm Password : </label><input type="password" name="confirm_password" value="a"><br/>
        <button type="submit">Register</button>
    </form>

    <c:choose>
        <c:when test="${message.error}">
            <p><c:out value="${message.message}"/></p>
        </c:when>
    </c:choose>

</main>
<jsp:include page="../include/footer.jsp"/>
</body>
</html>
