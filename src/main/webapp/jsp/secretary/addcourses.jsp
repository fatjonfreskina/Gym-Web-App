<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Courses</title>
</head>
<body>
<jsp:include page="/jsp/secretary/include/headersecreatry.jsp"/>


<form method="post">
    <label>Course Name :</label>
    <select name="course_name">
        <c:forEach var="course" items="${courses}">
            <option  value="${course.name}"><c:out value="${course.name}"/></option><br>
        </c:forEach>
    </select>
    <br>
    <label>Teacher :</label>
    <select name="teacher">
        <c:forEach var="teacher" items="${teachers}">
            <option  value="${teacher.email}"><c:out value="${teacher.name}"/> <c:out value="${teacher.surname}"/></option><br>
        </c:forEach>
    </select>
    <br>
    <label>Rooms :</label>
    <select name="room">
        <c:forEach var="room" items="${rooms}">
            <option  value="${room.name}"><c:out value="${room.name}"/></option><br>
        </c:forEach>
    </select>
    <br>



    <label>Subscription Type : </label><br>
    <input type="checkbox" name="subscription_type_30" value="30"><label>30 Price : </label><input type="number" name="cost_30" min="1"><br>
    <input type="checkbox" name="subscription_type_90" value="90"><label>90 Price : </label><input type="number" name="cost_90" min="1"><br>
    <input type="checkbox" name="subscription_type_180" value="180"><label>180 Price : </label><input type="number" name="cost_180" min="1"><br>
    <input type="checkbox" name="subscription_type_365" value="365"><label>365 Price : </label><input type="number" name="cost_365" min="1"><br>

    <label>Date First Event : </label> <input type="date" name="date_first_event">


    <table>
        <tr>
            <td>
                <label>Monday</label>:<br>
                <label>Start Time :</label><br>
                <select name="monday" multiple>
                    <option value="8:00:00">8:00</option>
                    <option value="10:00:00">10:00</option>
                    <option value="12:00:00">12:00</option>
                    <option value="14:00:00">14:00</option>
                    <option value="16:00:00">16:00</option>
                    <option value="18:00:00">18:00</option>
                    <option value="20:00:00">20:00</option>
                </select>
            </td>
            <td>
                <label>Tuesday</label>:<br>
                <label>Start Time :</label><br>
                <select name="tuesday" multiple>
                    <option value="8:00:00">8:00</option>
                    <option value="10:00:00">10:00</option>
                    <option value="12:00:00">12:00</option>
                    <option value="14:00:00">14:00</option>
                    <option value="16:00:00">16:00</option>
                    <option value="18:00:00">18:00</option>
                    <option value="20:00:00">20:00</option>
                </select>
            </td>
            <td>
                <label>Wednesday</label>:<br>
                <label>Start Time :</label><br>
                <select name="wednesday" multiple>
                    <option value="8:00:00">8:00</option>
                    <option value="10:00:00">10:00</option>
                    <option value="12:00:00">12:00</option>
                    <option value="14:00:00">14:00</option>
                    <option value="16:00:00">16:00</option>
                    <option value="18:00:00">18:00</option>
                    <option value="20:00:00">20:00</option>
                </select>
            </td>
            <td>
                <label>Thursday</label>:<br>
                <label>Start Time :</label><br>
                <select name="thursday" multiple><!-- Max ??-->
                    <option value="8:00:00">8:00</option>
                    <option value="10:00:00">10:00</option>
                    <option value="12:00:00">12:00</option>
                    <option value="14:00:00">14:00</option>
                    <option value="16:00:00">16:00</option>
                    <option value="18:00:00">18:00</option>
                    <option value="20:00:00">20:00</option>
                </select>
            </td>
            <td>
                <label>Friday</label>:<br>
                <label>Start Time :</label><br>
                <select name="friday" multiple><!-- Max ??-->
                    <option value="8:00:00">8:00</option>
                    <option value="10:00:00">10:00</option>
                    <option value="12:00:00">12:00</option>
                    <option value="14:00:00">14:00</option>
                    <option value="16:00:00">16:00</option>
                    <option value="18:00:00">18:00</option>
                    <option value="20:00:00">20:00</option>
                </select>
            </td>
            <td>
                <label>Saturday</label>:<br>
                <label>Start Time :</label><br>
                <select name="saturday" multiple><!-- Max ??-->
                    <option value="8:00:00">8:00</option>
                    <option value="10:00:00">10:00</option>
                    <option value="12:00:00">12:00</option>
                    <option value="14:00:00">14:00</option>
                    <option value="16:00:00">16:00</option>
                    <option value="18:00:00">18:00</option>
                    <option value="20:00:00">20:00</option>
                </select>
            </td>
            <td>
                <label>Sunday</label>:<br>
                <label>Start Time :</label><br>
                <select name="sunday" multiple><!-- Max ??-->
                    <option value="8:00:00">8:00</option>
                    <option value="10:00:00">10:00</option>
                    <option value="12:00:00">12:00</option>
                    <option value="14:00:00">14:00</option>
                    <option value="16:00:00">16:00</option>
                    <option value="18:00:00">18:00</option>
                    <option value="20:00:00">20:00</option>
                </select>
            </td>
        </tr>

    </table>
    <label>Duration weeks : </label><input type="number" name="weeks" min="1" max="52">
    <input type="submit" value="Submit">
</form>

<jsp:include page="../include/footer.jsp"/>
</body>
</html>
