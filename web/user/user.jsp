<%-- 
    Document   : user
    Created on : Jun 5, 2025, 9:59:31 PM
    Author     : Dien Sanh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trang chủ - Thư viện</title>
        <style>
            /* Thanh menu ngang */
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

        <!-- Thanh menu ngang -->
        <div class="navbar">
            <a href="profile.jsp">Thay đổi thông tin cá nhân</a>
            <a href="search.jsp">Tìm kiếm sách</a>
            <a href="bookDetails.jsp">Xem tình trạng sách</a>
            <a href="borrowReturn.jsp">Yêu cầu mượn / trả sách</a>
            <a href="history.jsp">Xem lịch sử mượn trả</a>
            <a href="logout.jsp">Đăng xuất</a>
        </div>

        <!-- Nội dung chính -->
        <div class="main-content">
            <h1>Hello World!</h1>
            <p>Chào mừng bạn đến hệ thống quản lý thư viện.</p>
        </div>
    </body>
</html>

