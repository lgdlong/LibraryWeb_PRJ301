<%--
    Document   : borrow-book
    Created on : Jun 27, 2025, 10:38:30 AM
    Author     : Dien Sanh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Your Borrowed Books</h2>

<c:if test="${empty borrowBook}">
    <p>You have not borrowed any books yet.</p>
</c:if>

<c:if test="${not empty borrowBook}">
    <table border="1" cellpadding="10" cellspacing="0">
        <tr>
            <th>Title</th>
            <th>Author</th>
            <th>Category</th>
        </tr>

        <c:forEach var="book" items="${borrowBook}">
            <tr>
                <td>${book.title}</td>
                <td>${book.author}</td>
                <td>${book.category}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/ViewBorrowBookController" method="post" style="display:inline;">
                        <input type="hidden" name="bookId" value="${book.id}" />
                        <input type="hidden" name="action" value="remove" />
                        <button type="submit">Remove</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>


