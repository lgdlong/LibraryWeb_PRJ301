<%-- 
    Document   : login
    Created on : Jun 6, 2025, 9:58:55 AM
    Author     : Dien Sanh
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String username = request.getParameter("username");
    if (username == null || username.isEmpty()) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Trang người dùng</title>
    <style>
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
    <div class="navbar">
        <a href="profile.jsp">Thay đổi thông tin cá nhân</a>
        <a href="search.jsp">Tìm kiếm sách</a>
        <a href="bookDetails.jsp">Xem tình trạng sách</a>
        <a href="borrowReturn.jsp">Yêu cầu mượn / trả sách</a>
        <a href="history.jsp">Xem lịch sử mượn trả</a>
        <a href="logout.jsp">Đăng xuất</a>
    </div>

    <div class="main-content">
        <h2>Xin chào, <%= username %>!</h2>
        <p>Chào mừng bạn đến hệ thống quản lý thư viện.</p>
    </div>
</body>
</html>

