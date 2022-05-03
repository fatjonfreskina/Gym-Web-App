<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Register</title>
    <meta charset="UTF-8">
    <jsp:include page="/jsp/include/style.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
</head>
<body>
<header>
    <jsp:include page="../include/header.jsp"/>
</header>
<main class="global-container">
    <form method="post" action="<c:url value="/register"/>" enctype="multipart/form-data">

        <div class="form-group row">
            <label for="tax_code" class="col-sm-2 col-form-label">Tax Code :</label>
            <div class="col-sm-10">
                <input type="text" name="tax_code" minlength="16" maxlength="16" id="tax_code"
                       class="form-control" placeholder="Insert Tax Code" required>
            </div>
        </div>

        <!-- pattern="^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$" required-->

        <div class="form-group row">
            <label for="first_name" class="col-sm-2 col-form-label">First Name :</label>
            <div class="col-sm-10">
                <input type="text" name="first_name" maxlength="30" id="first_name" class="form-control"
                       placeholder="Insert First Name" required>
            </div>
        </div>

        <div class="form-group row">
            <label for="last_name" class="col-sm-2 col-form-label">Last Name :</label>
            <div class="col-sm-10">
                <input type="text" name="last_name" maxlength="30" id="last_name" class="form-control"
                       placeholder="Insert Last Name" required>
            </div>
        </div>

        <div class="form-group row">
            <label for="birth_date" class="col-sm-2 col-form-label">Birth Date :</label>
            <div class="col-sm-10">
                <input type="date" name="birth_date" id="birth_date" class="form-control" required>
            </div>
        </div>

        <div class="form-group row">
            <label for="address" class="col-sm-2 col-form-label" >Address :</label>
            <div class="col-sm-10">
                <input type="text" name="address" id="address" class="form-control"
                       placeholder="Insert Address" required>
            </div>
        </div>

        <div class="form-group row">
            <label for="telephone_number" class="col-sm-3 col-form-label">Telephone Number :</label>
            <div class="col-sm-9">
                <input type="tel" name="telephone_number" id="telephone_number"
                       class="form-control" placeholder="Insert Telephone Number" required>
            </div>
        </div>

        <div class="form-group row">
            <label for="file" class="col-sm-3 col-form-label" >Avatar (Optional) :</label>
            <div class="col-sm-9">
                <div class="custom-file">
                    <input type="file" name="avatar" id="file" class="custom-file-input">
                    <label class="custom-file-label" for="file">Choose File</label>
                </div>
            </div>
        </div>

        <div class="form-group row">
            <label for="email" class="col-sm-2 col-form-label">Email :</label>
            <div class="col-sm-10">
                <input type="email" name="email" id="email" maxlength="40"
                       class="form-control" placeholder="Enter Email" required>
            </div>
        </div>

        <div class="form-group row">
            <label for="password" class="col-sm-2 col-form-label">Password :</label>
            <div class="col-sm-10">
                <input type="password" name="password" id="password" class="form-control"
                       placeholder="Password" required>
            </div>
        </div>

        <div class="form-group row">
            <label for="confirm_password" class="col-sm-3 col-form-label">Confirm Password :</label>
            <div class="col-sm-9">
                <input type="password" name="confirm_password" id="confirm_password"
                       class="form-control" placeholder="Confirm Password" required>
            </div>
        </div>

        <jsp:include page="/jsp/include/message.jsp"/>

        <button type="submit" class="btn btn-outline-primary btn-lg">Register</button>
    </form>

</main>
<jsp:include page="../include/footer.jsp"/>
<jsp:include page="/jsp/include/scripts.jsp"/>
<script src="<c:url value="/js/message-delay.js"/>"></script>
</body>
</html>
