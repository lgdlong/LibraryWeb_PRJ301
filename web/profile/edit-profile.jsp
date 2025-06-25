<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="entity.User" %>
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
<%
  User us = (User) session.getAttribute("LOGIN_USER");
  if (us == null) {
    response.sendRedirect(request.getContextPath() + "/GuestHomeController");
    return;
  }
%>
<div class="container min-vh-100 d-flex align-items-center justify-content-center">
  <div class="card shadow profile-card mt-4">
    <div class="card-header bg-success text-white text-center">
      <h4 class="mb-0">Edit Profile</h4>
    </div>
    <div class="card-body">
      <form action="<%= request.getContextPath() %>/profile/update" method="post">
        <div class="mb-3">
          <label for="fullname" class="form-label fw-bold">Full Name</label>
          <input type="text" id="fullname" name="fullname" class="form-control"
                 value="<%= us.getName() %>" required minlength="5" maxlength="30">
          <div class="form-text">Must be 5-30 characters</div>
        </div>
        <div class="mb-3">
          <label for="newEmail" class="form-label fw-bold">Email</label>
          <input type="email" id="newEmail" name="email" class="form-control"
                 value="<%= us.getEmail() %>" required>
        </div>

        <%-- Display error messages if any --%>
        <%
          dto.UserError userError = (dto.UserError) request.getAttribute("USER_ERROR");
          if (userError != null) {
        %>
        <div class="alert alert-danger">
          <% if (userError.getFullnameError() != null && !userError.getFullnameError().isEmpty()) { %>
          <div><%= userError.getFullnameError() %>
          </div>
          <% } %>
          <% if (userError.getEmailError() != null && !userError.getEmailError().isEmpty()) { %>
          <div><%= userError.getEmailError() %>
          </div>
          <% } %>
          <% if (userError.getPasswordError() != null && !userError.getPasswordError().isEmpty()) { %>
          <div><%= userError.getPasswordError() %>
          </div>
          <% } %>
          <% if (userError.getConfirmError() != null && !userError.getConfirmError().isEmpty()) { %>
          <div><%= userError.getConfirmError() %>
          </div>
          <% } %>
          <% if (userError.getError() != null && !userError.getError().isEmpty()) { %>
          <div><%= userError.getError() %>
          </div>
          <% } %>
        </div>
        <% } %>
        <%-- Display success message if any --%>
        <%--        <%--%>
        <%--          String successMessage = (String) request.getAttribute("SUCCESS_MESSAGE");--%>
        <%--          if (successMessage != null) {--%>
        <%--        %>--%>
        <%--        <div class="alert alert-success">--%>
        <%--          <%= successMessage %>--%>
        <%--        </div>--%>
        <%--        <% } %>--%>
        <c:if test="${not empty sessionScope.SUCCESS_MESSAGE}">
          <div class="alert alert-success">${sessionScope.SUCCESS_MESSAGE}</div>
          <c:remove var="SUCCESS_MESSAGE" scope="session"/>
        </c:if>

        <div class="d-flex justify-content-between">
          <a href="<%= request.getContextPath() %>/profile" class="btn btn-secondary">Cancel</a>
          <button type="submit" class="btn btn-primary">Save Changes</button>
        </div>
      </form>
    </div>
  </div>
</div>

<script>
  // Validate password confirmation
  document.addEventListener('DOMContentLoaded', function () {
    const confirmPasswordField = document.getElementById('confirmPassword');
    if (confirmPasswordField) {
      confirmPasswordField.addEventListener('input', function () {
        const password = document.getElementById('password').value;
        const confirmPassword = this.value;
        if (password !== confirmPassword && confirmPassword !== '') {
          this.setCustomValidity('Passwords do not match');
        } else {
          this.setCustomValidity('');
        }
      });
    }
  });
</script>
</body>
</html>
