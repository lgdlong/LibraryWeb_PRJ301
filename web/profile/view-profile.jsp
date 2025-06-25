<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="entity.User" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Profile</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    .profile-card {
      max-width: 500px;
      width: 100%;
    }

    .user-name {
      font-size: 30px;
      font-weight: 600;
      color: black;
    }

    .email-input {
      max-width: 400px;
      margin: 0 auto;
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
  <div class="card shadow profile-card">
    <div class="card-header bg-primary text-white text-center">
      <h4 class="mb-0">Profile</h4>
    </div>
    <div class="card-body">
      <div class="text-center mb-4">
        <span class="user-name"><%= us.getName() %></span>
      </div>
      <div class="email-input">
        <div class="mb-3">
          <label for="email" class="form-label fw-bold">Email</label>
          <input type="email" id="email" name="email" class="form-control" value="<%= us.getEmail() %>" readonly>
        </div>
      </div>
      <div class="text-center">
        <a href="<%= request.getContextPath() %>/profile/update" class="btn btn-primary">Edit Profile</a>
        <a href="<%= request.getContextPath() %>/GuestHomeController" class="btn btn-secondary ms-2">Go Back to Home</a>
        <%-- If you use a servlet/controller for editing, update the href accordingly --%>
      </div>
    </div>
  </div>
</div>
</body>
</html>
