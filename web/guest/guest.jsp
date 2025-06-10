<%@page import="java.util.ArrayList"%>
<%@page import="entity.Book"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Guest - Library</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .navbar {
            width: 100%;
            background-color: #2c3e50;
            overflow: hidden;
            display: flex;
            justify-content: center;
        }

        .navbar a {
            color: white;
            padding: 14px 20px;
            text-decoration: none;
            font-weight: bold;
            display: block;
        }

        .navbar a:hover {
            background-color: #34495e;
            color: #1abc9c;
        }

        .main-content {
            padding: 30px;
            max-width: 1000px;
            margin: auto;
            background-color: white;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            color: #2c3e50;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
            vertical-align: middle;
        }

        th {
            background-color: #2c3e50;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        img.book-cover {
            height: 100px;
            width: auto;
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
        if (books == null || books.isEmpty()) {
    %>
    <p style="text-align:center; color:red;">No new book published this year.</p>
    <%
        } else {
    %>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Cover</th>
            <th>Title</th>
            <th>Author</th>
            <th>ISBN</th>
            <th>Category</th>
            <th>Year</th>
            <th>Available</th>
        </tr>
        </thead>
        <tbody>
        <%
            int count = 1;
            for (Book b : books) {
        %>
        <tr>
            <td><%= count++ %></td>
            <td><img src="<%= b.getCoverUrl() %>" class="book-cover" alt="cover"></td>
            <td><%= b.getTitle() %></td>
            <td><%= b.getAuthor() %></td>
            <td><%= b.getIsbn() %></td>
            <td><%= b.getCategory() %></td>
            <td><%= b.getPublishedYear() %></td>
            <td><%= b.getAvailableCopies() %></td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <%
        }
    %>
</div>

</body>
</html>
