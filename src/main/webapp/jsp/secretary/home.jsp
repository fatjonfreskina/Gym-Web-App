<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Secretary</title>
    <meta charset="UTF-8">
    <jsp:include page="/jsp/include/style.jsp"/>
    <jsp:include page="../include/fullcalendar/style.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/secretary/calendar.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
</head>
<body>
<header>
    <jsp:include page="/jsp/secretary/include/headersecretary.jsp"/>
</header>
<main class="global-container">
    <div id="calendar" class="secretary-home-calendar">
    </div>
    <div id="modal-details" class="modal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Details about this course</h5>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">
                            <button type="button" class="btn btn-primary btn-sm" data-dismiss="modal"
                                    data-toggle="modal" data-target="#modal-notify-substitution">
                                Notify substitution
                            </button>
                        </div>
                        <hr>
                        <div class="row">
                            <button type="button" class="btn btn-warning btn-sm" data-dismiss="modal"
                                    data-toggle="modal" data-target="#modal-change-schedule">
                                Change schedule
                            </button>
                        </div>
                        <hr>
                        <div class="row">
                            <button id="button-delete-lecturetimeslot" type="button" class="btn btn-danger btn-sm"
                                    data-dismiss="modal">
                                Delete this lecture
                            </button>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal">
                        Close
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div id="modal-notify-substitution" class="modal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Notify substitution</h5>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <label for="substitute">Select a substitute</label>
                        <select id="substitute" name="substitute">
                            <c:forEach var="teacher" items="${teachers}">
                                <option value="${teacher.email}">
                                    <c:out value="${teacher.name} ${teacher.surname}"/>
                                </option>
                            </c:forEach>
                        </select>
                        <label for="info-substitution">Extra info</label>
                        <p>Add some extra information that will be embedded in the mail to the subscribed users</p>
                        <textarea id="info-substitution" name="info-substitution"></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" id="button-notify-substitution"
                            class="btn btn-primary btn-sm" data-dismiss="modal">
                        Notify substitution
                    </button>
                    <button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
    <div id="modal-change-schedule" class="modal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Change schedule</h5>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <label for="newStartTime">Specify a new start time</label>
                        <input type="time" id="newStartTime" name="newStartTime" min="09:00" max="20:00">
                        <label for="newDate">Specify a new date</label>
                        <input type="date" id="newDate" name="newDate">
                        <label for="newRoom">Specify a new room</label>
                        <select id="newRoom" name="newRoom">
                            <c:forEach var="room" items="${rooms}">
                                <option value="${room.name}"><c:out value="${room.name}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" id="button-change-schedule"
                            class="btn btn-primary btn-sm" data-dismiss="modal">Change schedule
                    </button>
                    <button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
</main>
<jsp:include page="../include/footer.jsp"/>
<jsp:include page="../include/scripts.jsp"/>
<jsp:include page="../include/fullcalendar/scripts.jsp"/>
<jsp:include page="../include/moment/scripts.jsp"/>
<script src="<c:url value="/js/secretary/secretary-home.js"/>"></script>
<jsp:include page="/jsp/include/scripts.jsp"/>
</body>
</html>
