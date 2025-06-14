<%@page import="java.util.ArrayList"%>
<%@page import="entity.Book"%>




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


