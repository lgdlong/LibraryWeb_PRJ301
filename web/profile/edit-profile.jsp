<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Edit Profile</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    .profile-card {
      max-width: 500px;
      width: 100%;
    }
  </style>
</head>
<body class="bg-light">

<c:choose>
  <c:when test="${empty sessionScope.LOGIN_USER}">
    <c:redirect url="${pageContext.request.contextPath}/GuestHomeController"/>
  </c:when>
</c:choose>

<div class="container min-vh-100 d-flex align-items-center justify-content-center">
  <div class="card shadow profile-card mt-4">
    <div class="card-header bg-success text-white text-center position-relative">
      <a href="${pageContext.request.contextPath}/profile" class="btn-close btn-close-white position-absolute top-50 end-0 translate-middle-y me-3" aria-label="Close"></a>
      <h4 class="mb-0">Edit Profile</h4>
    </div>
    <div class="card-body">
      <form action="${pageContext.request.contextPath}/profile/update" method="post">
        <div class="mb-3">
          <label for="name" class="form-label fw-bold">Full Name</label>
          <input type="text" id="name" name="name" class="form-control"
                 value="${USER.name}"
                 required minlength="5" maxlength="30"/>
          <div class="form-text">Must be 5–30 characters</div>
        </div>
        <div class="mb-3">
          <label for="newEmail" class="form-label fw-bold">Email</label>
          <input type="email" id="newEmail" name="email" class="form-control"
                 value="${USER.email}"
                 required/>
        </div>

        <!-- Error messages -->
        <c:if test="${not empty USER_ERROR}">
          <div class="alert alert-danger mb-2">
            <c:if test="${not empty USER_ERROR.nameError}">
              <div>${USER_ERROR.nameError}</div>
            </c:if>
            <c:if test="${not empty USER_ERROR.emailError}">
              <div>${USER_ERROR.emailError}</div>
            </c:if>
            <c:if test="${not empty USER_ERROR.error}">
              <div>${USER_ERROR.error}</div>
            </c:if>
          </div>
        </c:if>

        <!-- Success message via sessionScope (flash message) -->
        <c:if test="${not empty sessionScope.SUCCESS_MESSAGE}">
          <div class="alert alert-success">${sessionScope.SUCCESS_MESSAGE}</div>
          <c:remove var="SUCCESS_MESSAGE" scope="session"/>
        </c:if>

        <div class="d-flex justify-content-between">
          <a href="${pageContext.request.contextPath}/profile" class="btn btn-secondary">Cancel</a>
          <button type="submit" class="btn btn-primary">Save Changes</button>
        </div>
      </form>
    </div>
  </div>
</div>
</body>
</html>
