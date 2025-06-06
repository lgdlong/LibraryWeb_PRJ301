<%-- 
    Document   : guest
    Created on : Jun 6, 2025, 10:40:38 AM
    Author     : Dien Sanh
--%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Guest - Library</title>
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

    <!-- Navigation Bar -->
    <div class="navbar">
        <a href="search.jsp">Search Books</a>
        <a href="bookDetails.jsp">View Book Status</a>
        <a href="register.jsp">Register Account</a>
        <a href="login.jsp">Login</a>
    </div>

    <!-- Main Content -->
    <div class="main-content">
        <h1>Welcome, Guest!</h1>
    </div>
</body>
</html>

