<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Book" %>

<div class="main-content">
    <h2>Search Books</h2>

    <%
        List<Book> books = (List<Book>) request.getAttribute("results");
        if (books == null || books.isEmpty()) {
    %>
        <p class="no-books">No books found for your search.</p>
    <%
        } else {
    %>
        <div class="cards-container">
            <%
                for (Book b : books) {
            %>
            <div class="book-card">
            <a href="ViewBookController?id=<%= b.getId() %>" class="book-link">
                <img class="book-image" src="<%= b.getCoverUrl() %>" alt="No Image">
                <div class="book-info">
                    <strong><%= b.getTitle() %></strong>
                </div>
            </a>
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

