<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Secretary</title>
        <meta charset="UTF-8"/>
        <jsp:include page="/jsp/include/style.jsp"/>
        <jsp:include page="../include/fullcalendar/style.jsp"/>
        <link rel="stylesheet" href="<c:url value="/css/secretary/calendar.css"/>"/>
        <jsp:include page="/jsp/include/favicon.jsp"/>
        <link rel="stylesheet" href="<c:url value="/css/main.css"/>"/>
    </head>
    <body>
        <header>
            <jsp:include page="/jsp/secretary/include/headersecretary.jsp"/>
        </header>

        <main class="global-container">
            <!-- Error messages -->
            <jsp:include page="../include/message.jsp"/>

            <div id="calendar" class="secretary-home-calendar">
            </div>

            <!-- Modal triggered to select an action -->
            <div id="modal-actions-course" class="modal" tabindex="-1">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Actions</h5>
                        </div>
                        <div class="modal-body">
                            <div class="container text-center">
                                <button type="button" class="btn btn-primary col-sm-3" data-dismiss="modal"
                                        data-toggle="modal" data-target="#modal-notify-substitution">
                                    Notify substitution
                                </button>
                                <button type="button" class="btn btn-warning col-sm-3" data-dismiss="modal"
                                        data-toggle="modal" data-target="#modal-change-schedule">
                                    Change schedule
                                </button>
                                <button id="button-delete-lecturetimeslot" type="button" class="btn btn-danger col-sm-3"
                                        data-dismiss="modal">
                                    Delete this lecture
                                </button>
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

            <!-- Modal used to notify a substitution -->
            <div id="modal-notify-substitution" class="modal" tabindex="-1">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Notify substitution</h5>
                        </div>
                        <div class="modal-body">
                            <div class="container">
                                <div class="form-group row">
                                    <label class="col-sm-6" for="substitute">Select a substitute</label>
                                    <div class="col-sm-6">
                                        <select id="substitute" name="substitute" class="form-control">
                                            <c:forEach var="teacher" items="${teachers}">
                                                <option value="${teacher.email}">
                                                    <c:out value="${teacher.name} ${teacher.surname}"/>
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-sm-6" for="info-substitution">Extra info</label>
                                    <div class="col-sm-6">
                                        <textarea id="info-substitution" class="form-control"
                                                  name="info-substitution"></textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" id="button-notify-substitution"
                                        class="btn btn-primary" data-dismiss="modal">
                                    Notify substitution
                                </button>
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal used to change the schedule of a class -->
            <div id="modal-change-schedule" class="modal" tabindex="-1">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Change schedule</h5>
                        </div>
                        <div class="modal-body">
                            <div class="container">
                                <div class="form-group row">
                                    <label for="newStartTime" class="col-sm-6">Specify a new start time</label>
                                    <select id="newStartTime" name="newStartTime" class="form-control col-sm-6">
                                        <option value="8:00:00">8:00</option>
                                        <option value="10:00:00">10:00</option>
                                        <option value="12:00:00">12:00</option>
                                        <option value="14:00:00">14:00</option>
                                        <option value="16:00:00">16:00</option>
                                        <option value="18:00:00">18:00</option>
                                        <option value="20:00:00">20:00</option>
                                    </select>
                                </div>
                                <div class="form-group row">
                                    <label for="newDate" class="col-sm-6">Specify a new date</label>
                                    <input type="date" id="newDate" class="form-control col-sm-6" name="newDate"/>
                                </div>
                                <div class="form-group row">
                                    <label for="newRoom" class="col-sm-6">Specify a new room</label>
                                    <select id="newRoom" class="form-control col-sm-6" name="newRoom">
                                        <c:forEach var="room" items="${rooms}">
                                            <option value="${room.name}"><c:out value="${room.name}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" id="button-change-schedule"
                                    class="btn btn-primary" data-dismiss="modal">
                                Change schedule
                            </button>
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>

        </main>
        <jsp:include page="../include/footer.jsp"/>
        <jsp:include page="../include/scripts.jsp"/>
        <jsp:include page="../include/fullcalendar/scripts.jsp"/>
        <jsp:include page="../include/moment/scripts.jsp"/>

        <!-- Common JS to handle messages -->
        <script src="<c:url value="/js/message.js"/>"></script>
        <script src="<c:url value="/js/secretary/secretary-home.js"/>"></script>
    </body>
</html>
