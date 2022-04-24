<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Add Account</title>
</head>
<body>
<jsp:include page="/jsp/secretary/include/headersecretary.jsp"/>
    <c:choose>
        <c:when test="${message != null}">
            <p><c:out value="${message.message}"/></p>
        </c:when>
    </c:choose>
    <form method="post" enctype="multipart/form-data">

        <label>Tax Code : </label><input type="text" name="tax_code" value="0123450123456789"><br/><!-- pattern="^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$" required-->
        <label>First Name : </label><input type="text" name="first_name" value="z"><br/>
        <label>Last Name : </label><input type="text" name="last_name" value="z"><br/>
        <label>Birth Date : </label><input type="date" name="birth_date" value="1999-09-06"><br/>
        <label>Address : </label><input type="text" name="address" value="0"><br/>
        <label>Telephone Number : </label><input type="tel" name="telephone_number" value="0123456789"><br>
        <label>Avatar (Optional) : </label><input type="file" name="avatar" ><br/>
        <label>Email : </label><input type="text" name="email" value=""><br/>
        <div>
            <label>Role : </label>
            <input type="checkbox" id="trainee" name="trainee"/>
            <label for="trainee">Trainee</label>
            <input type="checkbox" name="trainer" id="trainer"/>
            <label for="trainer">Trainer</label>
            <input type="checkbox" name="secretary" id="secretary"/>
            <label for="secretary">Secretary</label>
        </div>
        <button type="submit" >Register</button>
    </form>


    <jsp:include page="../include/footer.jsp"/>
</body>
</html>
