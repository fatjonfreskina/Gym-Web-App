<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Add Courses</title>
    <jsp:include page="/jsp/include/style.jsp"/>
    <jsp:include page="/jsp/include/select_multiple/style.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
    <jsp:include page="/jsp/include/favicon.jsp"/>
    <meta charset="UTF-8">
</head>
<body>
<header>
    <jsp:include page="/jsp/secretary/include/headersecretary.jsp"/>
</header>

<main class="global-container">
    <form method="post" action="<c:url value="/secretary/addcourses"/>" id="form">

        <div class="form-group row">
            <label for="course_name" class="col-sm-3 col-form-label">Course Name :</label>
            <div class="col-sm-9">
                <select name="course_name" id="course_name" class="form-control">
                    <c:forEach var="course" items="${courses}">
                        <option  value="${course.name}"><c:out value="${course.name}"/></option><br>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="form-group row">
            <label for="teacher" class="col-sm-3 col-form-label">Teacher :</label>
            <div class="col-sm-9">
                <select name="teacher" id="teacher" class="form-control">
                    <c:forEach var="teacher" items="${teachers}">
                        <option  value="${teacher.email}"><c:out value="${teacher.name}"/> <c:out value="${teacher.surname}"/></option><br>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="form-group row">
            <label for="room" class="col-sm-3 col-form-label">Rooms :</label>
            <div class="col-sm-9">
                <select name="room" id="room" class="form-control">
                    <c:forEach var="room" items="${rooms}">
                        <option  value="${room.name}"><c:out value="${room.name}"/></option><br>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="form-group row">
            <label for="room" class="col-sm-3 col-form-label">Subscription Type :</label>
        </div>

        <div class="form-group row">
            <div class="col-sm-1 col-form-label">
                <input type="checkbox" name="subscription_type_30" value="30" id="subscription_type_30">
                <label for="subscription_type_30">30</label>
            </div>
            <label class="col-sm-2 col-form-label" for="cost_30" >Price:</label>
            <div class="col-sm-9">
                <input type="number" name="cost_30" min="1" class="form-control" id="cost_30" placeholder="Enter Price Subscription Monthly">
            </div>
        </div>



        <div class="form-group row">
            <div class="col-sm-1 col-form-label">
                <input type="checkbox" name="subscription_type_90" value="90" id="subscription_type_90">
                <label for="subscription_type_90">90</label>
            </div>
            <label class="col-sm-2 col-form-label" for="cost_90">Price:</label>
            <div class="col-sm-9">
                <input type="number" name="cost_90" min="1" class="form-control" id="cost_90" placeholder="Enter Price Subscription Quaterly">
            </div>
        </div>



        <div class="form-group row">
            <div class="col-sm-1 col-form-label">
                <input type="checkbox" name="subscription_type_180" value="180" id="subscription_type_180">
                <label for="subscription_type_180">180</label>
            </div>
            <label class="col-sm-2 col-form-label" for="cost_180">Price:</label>
            <div class="col-sm-9">
                <input type="number" name="cost_180" min="1" class="form-control" id="cost_180" placeholder="Enter Price Subscription Half-Yearly">
            </div>
        </div>


        <div class="form-group row">
            <div class="col-sm-1 col-form-label">
                <input type="checkbox" name="subscription_type_365" value="365" id="subscription_type_365">
                <label for="subscription_type_365">365</label>
            </div>
            <label class="col-sm-2 col-form-label" for="cost_365">Price:</label>
            <div class="col-sm-9">
                <input type="number" name="cost_365" min="1" class="form-control" id="cost_365" placeholder="Enter Price Subscription Yearly">
            </div>
        </div>

        <div class="form-group row">
            <label for="date" class="col-sm-3 col-form-label">Date First Event : </label>
            <div class="col-sm-9">
                <input id="date" type="date" name="date_first_event" class="form-control">
            </div>
        </div>

        <div class="form-group row">
            <label for="monday" class="col-sm-3 col-form-label">Monday, Start Time :</label>
            <div class="col-sm-9">
                <select name="monday" multiple class="selectpicker" id="monday">
                    <option value="8:00:00">8:00</option>
                    <option value="10:00:00">10:00</option>
                    <option value="12:00:00">12:00</option>
                    <option value="14:00:00">14:00</option>
                    <option value="16:00:00">16:00</option>
                    <option value="18:00:00">18:00</option>
                    <option value="20:00:00">20:00</option>
                </select>
            </div>
        </div>

        <div class="form-group row">
            <label for="tuesday" class="col-sm-3 col-form-label">Tuesday, Start Time:</label>
            <div class="col-sm-9">
                <select name="tuesday" multiple class="selectpicker" id="tuesday">
                    <option value="8:00:00">8:00</option>
                    <option value="10:00:00">10:00</option>
                    <option value="12:00:00">12:00</option>
                    <option value="14:00:00">14:00</option>
                    <option value="16:00:00">16:00</option>
                    <option value="18:00:00">18:00</option>
                    <option value="20:00:00">20:00</option>
                </select>
            </div>
        </div>

        <div class="form-group row">
            <label for="wednesday" class="col-sm-3 col-form-label">Wednesday, Start Time:</label>
            <div class="col-sm-9">
                <select name="wednesday" multiple class="selectpicker" id="wednesday">
                    <option value="8:00:00">8:00</option>
                    <option value="10:00:00">10:00</option>
                    <option value="12:00:00">12:00</option>
                    <option value="14:00:00">14:00</option>
                    <option value="16:00:00">16:00</option>
                    <option value="18:00:00">18:00</option>
                    <option value="20:00:00">20:00</option>
                </select>
            </div>
        </div>

        <div class="form-group row">
            <label for="thursday" class="col-sm-3 col-form-label">Thursday, Start Time:</label>
            <div class="col-sm-9">
                <select name="thursday" multiple class="selectpicker" id="thursday"><!-- Max ??-->
                    <option value="8:00:00">8:00</option>
                    <option value="10:00:00">10:00</option>
                    <option value="12:00:00">12:00</option>
                    <option value="14:00:00">14:00</option>
                    <option value="16:00:00">16:00</option>
                    <option value="18:00:00">18:00</option>
                    <option value="20:00:00">20:00</option>
                </select>
            </div>
        </div>

        <div class="form-group row">
            <label for="friday" class="col-sm-3 col-form-label">Friday, Start Time:</label>
            <div class="col-sm-9">
                <select name="friday" multiple class="selectpicker" id="friday"><!-- Max ??-->
                    <option value="8:00:00">8:00</option>
                    <option value="10:00:00">10:00</option>
                    <option value="12:00:00">12:00</option>
                    <option value="14:00:00">14:00</option>
                    <option value="16:00:00">16:00</option>
                    <option value="18:00:00">18:00</option>
                    <option value="20:00:00">20:00</option>
                </select>
            </div>
        </div>

        <div class="form-group row">
            <label for="saturday" class="col-sm-3 col-form-label">Saturday, Start Time:</label>
            <div class="col-sm-9">
                <select name="saturday" multiple class="selectpicker" id="saturday"><!-- Max ??-->
                    <option value="8:00:00">8:00</option>
                    <option value="10:00:00">10:00</option>
                    <option value="12:00:00">12:00</option>
                    <option value="14:00:00">14:00</option>
                    <option value="16:00:00">16:00</option>
                    <option value="18:00:00">18:00</option>
                    <option value="20:00:00">20:00</option>
                </select>
            </div>
        </div>

        <div class="form-group row">
            <label for="sunday" class="col-sm-3 col-form-label">Sunday, Start Time:</label>
            <div class="col-sm-9">
                <select name="sunday" multiple class="selectpicker" id="sunday"><!-- Max ??-->
                    <option value="8:00:00">8:00</option>
                    <option value="10:00:00">10:00</option>
                    <option value="12:00:00">12:00</option>
                    <option value="14:00:00">14:00</option>
                    <option value="16:00:00">16:00</option>
                    <option value="18:00:00">18:00</option>
                    <option value="20:00:00">20:00</option>
                </select>
            </div>
        </div>


        <div class="form-group row">
            <label for="weeks" class="col-sm-3 col-form-label">Duration weeks : </label>
            <div class="col-sm-9">
                <input type="number" name="weeks" min="1" max="52" id="weeks" class="form-control"
                       placeholder="Enter Number Of Weeks" />
            </div>
        </div>
        <!-- VISUALIZZA MESSAGGIO -->
        <div id="alert-box" class="alert alert-warning alert-dismissible fade show" role="alert" style="display: none;">
            <p id="alert-message-body" class="alert-box-message">
            </p>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close" >
                <span aria-hidden="true">&times;</span>
            </button>
        </div>

        <input type="submit" value="Submit" class="btn btn-outline-primary btn-lg" />

    </form>
</main>

<jsp:include page="../include/footer.jsp"/>
<jsp:include page="/jsp/include/scripts.jsp"/>
<jsp:include page="/jsp/include/moment/scripts.jsp"/>
<jsp:include page="/jsp/include/select_multiple/script.jsp"/>
<script src="<c:url value="/js/secretary/add-courses.js"/>"></script>

</body>
</html>