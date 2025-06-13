<%@page import="java.util.ArrayList"%>
<%@page import="entity.Book"%>

<head>
    <meta charset="UTF-8">
    <title>Library - Home</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .main-content {
            padding: 30px;
            max-width: 1200px;
            margin: auto;
            background-color: white;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 30px;
        }

.cards-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: 30px;
    padding: 10px;
}

.book-card {
    background-color: #fff;
    width: 260px; /* To hơn */
    border-radius: 10px;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
    overflow: hidden;
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 20px;
    transition: transform 0.2s ease;
}

.book-card:hover {
    transform: translateY(-8px);
}

.book-image {
    height: 200px;
    width: auto;
    object-fit: contain;
    border-radius: 6px;
    margin-bottom: 15px;
}

.book-info {
    text-align: center;
    font-size: 15px;
}

.book-info strong {
    display: block;
    margin-bottom: 8px;
    font-size: 16px;
    color: #2c3e50;
}

        .no-books {
            text-align: center;
            color: red;
        }
    </style>
</head>


<div class="main-content">
    <h2>New Books</h2>
    <%
        ArrayList<Book> books = (ArrayList<Book>) request.getAttribute("newBooks");
        if (books == null || books.isEmpty()) {
    %>
    <p class="no-books">No new books published this year.</p>
    <%
        } else {
    %>
    <div class="cards-container">
        <%
            for (Book b : books) {
        %>
        <div class="book-card">
            <img src="<%= b.getCoverUrl() %>" alt="Book Cover" class="book-image">
            <div class="book-info">
                <strong><%= b.getTitle() %></strong>
            </div>
        </div>
        <%
            }
        %>
    </div>
    <%
        }
    %>
</div>


