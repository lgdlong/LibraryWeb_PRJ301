<%-- 
    Document   : user
    Created on : Jun 6, 2025, 10:11:21 AM
    Author     : Dien Sanh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String username = (String) session.getAttribute("username");
    if (username == null || username.isEmpty()) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home - Library</title>
        <style>
            /* Horizontal navigation bar */
            .navbar {
                width: 100%;
                background-color: #2c3e50;
                overflow: hidden;
                display: flex;
                justify-content: center;
                font-family: Arial, sans-serif;
            }

            .navbar a {
                color: white;
                padding: 14px 20px;
                text-decoration: none;
                text-align: center;
                font-weight: bold;
            }

            .navbar a:hover {
                background-color: #34495e;
                color: #1abc9c;
            }

            .main-content {
                padding: 20px;
                font-family: Arial, sans-serif;
            }
        </style>
    </head>
    <body>

        <!-- Horizontal navigation bar -->
        <div class="navbar">
            <a href="profile.jsp">Edit Personal Information</a>
            <a href="search.jsp">Search Books</a>
            <a href="bookDetails.jsp">View Book Status</a>
            <a href="borrowReturn.jsp">Borrow / Return Request</a>
            <a href="history.jsp">View Borrowing History</a>
            <a href="logout.jsp">Log Out</a>
        </div>

        <!-- Main content -->
        <div class="main-content">
            <h1>Login Successful</h1>
        </div>
    </body>
</html>

