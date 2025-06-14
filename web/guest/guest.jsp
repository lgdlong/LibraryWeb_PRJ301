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
            justify-content: flex-start; /* Căn các card về trái */
            gap: 20px; /* Khoảng cách giữa các card giống ảnh */
            padding: 10px;
        }

        .book-card {
            background-color: #fff;
            width: 180px; /* Nhỏ lại để khớp hình */
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            overflow: hidden;
            display: flex;
            padding: 8px;
            flex-direction: column;
            transition: transform 0.2s ease;
        }

        .book-card:hover {
            transform: translateY(-5px);
        }

        .book-image {
            height: 160px;
            width: 100%;
            object-fit: contain;
            margin-bottom: 10px;
        }

        .book-info {
            text-align: left;
            font-size: 14px;
        }

        .book-info strong {
            display: block;
            margin-bottom: 6px;
            font-size: 15px;
            color: #2c3e50;
            font-weight: normal;
        }

        .no-books {
            text-align: center;
            color: red;
            font-size: 16px;
        }


       .book-card {
           background-color: #fff;
           width: 180px;
           box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
           overflow: hidden;
           display: flex;
           flex-direction: column;
           padding: 8px;
           justify-content: space-between;
           height: 300px;
       }

       .book-info {
           text-align: left;
           font-size: 14px;
           flex-grow: 1;
       }

       .book-info strong {
           display: -webkit-box;
           -webkit-line-clamp: 2;
           -webkit-box-orient: vertical;
           overflow: hidden;
           text-overflow: ellipsis;
           font-size: 15px;
           color: #2c3e50;
           font-weight: normal;
           line-height: 1.3em;
           max-height: 2.6em;
       }


       .borrow-button {
           margin-top: 10px;
           padding: 6px 12px;
           background-color: #3498db;
           color: white;
           border: none;
           border-radius: 4px;
           cursor: pointer;
           font-size: 14px;
           transition: background-color 0.2s ease;
           align-self: flex-start;
       }

       .borrow-button:hover {
           background-color: #2980b9;
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
            <form action="borrow" method="post">
                            <input type="hidden" name="bookId" value="<%= b.getId() %>">
                            <button type="submit" class="borrow-button">Borrow</button>
            </form>
        </div>
        <%
            }
        %>
    </div>
    <%
        }
    %>
</div>


