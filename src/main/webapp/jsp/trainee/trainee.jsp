<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Trainee page</title>
    <meta charset="UTF-8">
    <jsp:include page="/jsp/include/style.jsp"/>
    <jsp:include page="../include/fullcalendar/style.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
    <link id="contextPathHolder" data="${pageContext.request.contextPath}"/>
</head>
<body>
<header>
    <jsp:include page="/jsp/trainee/include/headertrainee.jsp"/>
</header>
<main class="global-container">
    <c:choose>
        <c:when test="${medicalCertificate != null}">
            <div id = "v_certificate" class = "text-success">
                Medical Certificate status: present and valid
            </div>
            <jsp:include page="/jsp/trainee/subscriptiontrainee.jsp"/>
            <div id="trainee__calendar"></div>
        </c:when>
        <c:otherwise>
            <div id = "e_certificate" class = "text-danger">
                Medical Certificate status: absent or expired
            </div>
            <jsp:include page="/jsp/trainee/subscriptiontrainee.jsp"/>
        </c:otherwise>
    </c:choose>

    <div id="c-reservation" class="modal" tabindex="-1" role="dialog">
      <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="text-primary modal-title">Confirm reservation</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div class="container">
				<div class="row">
					<div class="col-sm">
						<span class="text-dark">Course name:</span>
					</div>
					<div class="col-sm">
						<span id="c-course-name"></span>
					</div>
				</div>
				<div class="row">
					<div class="col-sm">
						<span class="text-dark">Date:</span>
					</div>
					<div class="col-sm">
						<span id="c-course-date"></span>
					</div>
				</div>
				<div class="row">
					<div class="col-sm">
						<span class="text-dark">Start time:</span>
					</div>
					<div class="col-sm">
						<span id="c-course-startTime"></span>
					</div>
				</div>
				<div class="row">
					<div class="col-sm">
						<span class="text-dark">Room name:</span>
					</div>
					<div class="col-sm">
						<span id="c-course-roomName"></span>
					</div>
				</div>
			</div>
          </div>
          <div class="modal-footer">
            <button type="button" id="button-save-reservation" class="btn btn-primary" data-dismiss="modal">Save reservation</button>
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>

    <div id="d-reservation" class="modal" tabindex="-1" role="dialog">
      <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="text-danger modal-title">Delete reservation</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div class="container">
				<div class="row">
					<div class="col-sm">
						<span class="text-dark">Course name:</span>
					</div>
					<div class="col-sm">
						<span id="d-course-name"></span>
					</div>
				</div>
				<div class="row">
					<div class="col-sm">
						<span class="text-dark">Date:</span>
					</div>
					<div class="col-sm">
						<span id="d-course-date"></span>
					</div>
				</div>
				<div class="row">
					<div class="col-sm">
						<span class="text-dark">Start time:</span>
					</div>
					<div class="col-sm">
						<span id="d-course-startTime"></span>
					</div>
				</div>
				<div class="row">
					<div class="col-sm">
						<span class="text-dark">Room name:</span>
					</div>
					<div class="col-sm">
						<span id="d-course-roomName"></span>
					</div>
				</div>
			</div>
          </div>
          <div class="modal-footer">
            <button type="button" id="button-delete-reservation" class="btn btn btn-danger" data-dismiss="modal">Delete reservation</button>
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>
	
	<div id="e-reservation" class="modal" tabindex="-1" role="dialog">
      <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="text-warning modal-title">Reservation Error</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div class="container">
				<div class="row">
					<div class="col-sm">
						<span class="text-dark">Error:</span>
					</div>
					<div class="col-sm">
						<span id="e-message"></span>
					</div>
				</div>
			</div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>


    <jsp:include page="../include/scripts.jsp"/>
    <jsp:include page="../include/fullcalendar/scripts.jsp"/>
    <jsp:include page="../include/moment/scripts.jsp"/>
    <script src="<c:url value="/js/trainee/trainee.js"/>"></script>
</main>
<jsp:include page="/jsp/include/footer.jsp"/>
</body>
</html>
