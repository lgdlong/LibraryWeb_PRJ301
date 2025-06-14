<%-- 
    Document   : login
    Created on : Jun 6, 2025, 10:11:57 AM
    Author     : Dien Sanh
--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*" %>
<%
    String error = null;

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Simulate account check (you can replace this with a database check later)
        if ("admin".equals(username) && "1234".equals(password)) {
            session.setAttribute("username", username);
            response.sendRedirect("user.jsp");
            return;
        } else {
            error = "Incorrect username or password!";
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #ecf0f1;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .login-box {
            background-color: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            width: 300px;
        }

        .login-box h2 {
            margin-bottom: 20px;
            text-align: center;
        }

        .login-box input[type="text"],
        .login-box input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #bdc3c7;
            border-radius: 6px;
        }

        .login-box input[type="submit"] {
            width: 100%;
            background-color: #2ecc71;
            border: none;
            padding: 10px;
            color: white;
            font-weight: bold;
            border-radius: 6px;
            cursor: pointer;
        }

        .error {
            color: red;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="login-box">
        <h2>Login</h2>
        <form method="post" action="login.jsp">
            <input type="text" name="username" placeholder="Username" required>
            <input type="password" name="password" placeholder="Password" required>
            <input type="submit" value="Login">
        </form>
        <% if (error != null) { %>
            <p class="error"><%= error %></p>
        <% } %>
    </div>
</body>
</html>


