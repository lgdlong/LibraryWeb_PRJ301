<%--
    Document   : view-cart
    Created on : Jun 28, 2025, 2:08:41 PM
    Author     : Dien Sanh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Your Borrowed Books</h2>


<c:if test="${not empty message}">
    <p style="color: green;">${message}</p>
</c:if>


<c:set var="borrowBook" value="${sessionScope.borrowBook}" />

<c:if test="${empty borrowBook}">
    <p>You have not selected any books to borrow.</p>
</c:if>

<c:if test="${not empty borrowBook}">
    <table border="1" cellpadding="10" cellspacing="0">
        <tr>
            <th>Title</th>
            <th>Author</th>
            <th>Category</th>
            <th>Action</th>
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

    <form action="${pageContext.request.contextPath}/SendRequestBorrowController" method="post">
        <button type="submit">Send Request</button>
    </form>
</c:if>
