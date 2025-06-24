<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="entity.User" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>User Profile</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <%
            User us = (User) session.getAttribute("LOGIN_USER");
            if (us == null) {
                response.sendRedirect(request.getContextPath() + "/GuestHomeController");
            } else {
        %>

        <div class="container mt-5">
            <div class="card shadow-lg">
                <div class="card-header bg-primary text-white">
                    <h4 class="mb-0">User Profile</h4>
                </div>
                <div class="card-body">
                    <form>
                        <div class="mb-3">
                            <label class="form-label">Full Name</label>
                            <input type="text" class="form-control" name="fullname" value="<%= us.getName() %>" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" class="form-control" name="email" value="<%= us.getEmail() %>" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Password</label>
                            <input type="password" class="form-control" value="******" readonly>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Role</label>
                            <input type="text" class="form-control" name="roleId" value="<%= us.getRole() %>" readonly>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <% } %>
    </body>
</html>
