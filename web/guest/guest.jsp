<%@page import="java.util.ArrayList"%>
<%@page import="entity.Book"%>
<div class="main-content">

    <%--Show New Book--%>
    <%
        Boolean isSearch = (Boolean) request.getAttribute("isSearch");
        ArrayList<Book> newBooks = (ArrayList<Book>) request.getAttribute("newBooks");
        if (isSearch == null || !isSearch) {
                if (newBooks == null || newBooks.isEmpty()) {
        %>
                    <h2>New Books</h2>
                    <p class="no-books">No new books published this year.</p>
        <%
                } else {
        %>
                    <h2>New Books</h2>
                    <div class="cards-container">
                        <% for (Book b : newBooks) { %>
                            <div class="book-card">
                                <a href="ViewBookController?id=<%= b.getId() %>" class="book-link">
                                    <img src="<%= b.getCoverUrl() %>" alt="Book Cover" class="book-image">
                                </a>
                                <div class="book-meta">
                                    <p class="book-title"><%= b.getTitle() %></p>
                                    <p class="book-author"><%= b.getAuthor() %></p>
                                </div>
                                <form action="borrow" method="post" class="borrow-form">
                                    <input type="hidden" name="bookId" value="<%= b.getId() %>">
                                    <button type="submit" class="borrow-button">Borrow</button>
                                </form>
                            </div>
                        <% } %>
                    </div>
        <%
                }
            }
        %>

<%--Show Available Book--%>
    <h2>Books</h2>
        <%
            ArrayList<Book> availableBooks = (ArrayList<Book>) request.getAttribute("availableBooks");
            if (availableBooks == null || availableBooks.isEmpty()) {
        %>
        <p class="no-books">No books available at the moment.</p>
        <%
            } else {
        %>
        <div class="cards-container">
            <%
                for (Book b : availableBooks) {
            %>
    <div class="book-card">
        <a href="ViewBookController?id=<%= b.getId() %>" class="book-link">
            <img src="<%= b.getCoverUrl() %>" alt="Book Cover" class="book-image">
        </a>

        <div class="book-meta">
            <p class="book-title"><%= b.getTitle() %></p>
            <p class="book-author"><%= b.getAuthor() %></p>
        </div>

        <form action="borrow" method="post" class="borrow-form">
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


