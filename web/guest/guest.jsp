<%--
    Document   : guest
    Created on : Jun 6, 2025, 10:40:38 AM
    Author     : Dien Sanh
--%>
<%@page  import="java.util.ArrayList" %>
<%@page import="entity.Book" %>
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


    <div class="navbar">
        <a href="#">Search Books</a>
        <a href="#">View Book Status</a>
        <a href="#">Register Account</a>
        <a href="#">Login</a>
    </div>


    <div class="main-content">
        <h2>New Book</h2>
        <%
            ArrayList<Book> books = (ArrayList<Book>) request.getAttribute("newBooks");
            if(books == null || books.isEmpty()){

        %>
        <p>No new book publish in this year</p>
        <%
            }else{

        %>
        <table>
            <thead>
                <tr>
                    <th>#</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>ISBN</th>
                    <th>URL</th>
                    <th>Category</th>
                    <th>Public Year</th>
                    <th>Available Copy</th>
                </tr>
            </thead>

        <%
            int count = 1;
            for(Book b : books ){
        %>
        <tr>
            <td><%= count %></td>
            <td><%= b.getTitle() %></td>
            <td><%= b.getAuthor() %></td>
            <td><%= b.getIsbn() %></td>
            <td><%= b.getCoverUrl() %></td>
            <td><%= b.getCategory() %></td>
            <td><%= b.getPublishedYear() %></td>
            <td><%= b.getAvailableCopies()%></td>
        </tr>
        <%
            }
        %>
        </table>
        <%
            }
        %>
    </div>
</body>
</html>

