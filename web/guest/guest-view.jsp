<%--
    Document   : guest-view
    Created on : Jun 11, 2025, 10:28:10 AM
    Author     : Dien Sanh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.Book"%>
<%
    Book book = (Book) session.getAttribute("book");
%>


<div class="book-container">
    <div class="book-image-details">
        <img src="<%= book.getCoverUrl() %>">
    </div>

    <div class="book-details">
        <h2>Detail</h2>
        <p><span>Author:</span> <%= book.getAuthor() %></p>
        <p><span>ISBN:</span> <%= book.getIsbn() %></p>
        <p><span>Category:</span> <%= book.getCategory() %></p>
        <p><span>Public Year:</span> <%= book.getPublishedYear() %></p>
        <p><span>Available Copies:</span> <%= book.getAvailableCopies() %></p>
        <form action="${pageContext.request.contextPath}/BorrowController" method="post" class="borrow-form">
            <input type="hidden" name="bookId" value="<%= book.getId() %>">
            <input type="hidden" name="currentPage" value="ViewBookController?id=<%= book.getId() %>">
            <button type="submit" class="borrow-button">Borrow</button>
        </form>
        <%
            session.removeAttribute("book");
        %>
    </div>
</div>


