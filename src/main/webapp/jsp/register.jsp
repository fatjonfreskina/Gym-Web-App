<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title> Register </title>
</head>
<body>
<form method="post" action="">
    <label>Tax Code : </label><input type="text" name="tax_code" pattern="^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$" required><br/>
    <label>First Name : </label><input type="text" name="first_name" required><br/>
    <label>Last Name : </label><input type="text" name="last_name" required><br/>
    <label>Birth Date : </label><input type="date" name="birth_date" required><br/>
    <label>Address : </label><input type="text" name="address" required><br/>
    <label>Telephone Number : </label><input type="tel" name="phone" pattern="[0-9]{10}"><br>
    <label>Photo (Optional) : </label><input type="file" name="avatar"><br/>
    <label>Email : </label><input type="text" name="email" required pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2, 4}$"><br/>
    <label>Password : </label><input type="password" name="password" required><br/>
    <label>Confirm Password : </label><input type="password" name="confirm_password" required><br/>
    <label>Medical Certificate (optional) : </label><input type="file" name="medical_certificate"><br/>
    <label>Note : is mandatory to uplad a Medical Certificate before entering the gym.</label><br/>
    <button type="submit" >Register</button>
</form>
</body>
</html>
