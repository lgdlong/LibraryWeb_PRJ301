<%--
    Document   : view-borrow-book
    Created on : Jun 29, 2025, 4:34:10 PM
    Author     : Dien Sanh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Your Book Requests</h2>

<c:if test="${empty booksRequest}">
    <p>You have no book requests yet.</p>
</c:if>

<c:if test="${booksRequest!= null && not empty booksRequest}">
    <table border="1">
        <tr>
            <th>Book ID</th>
            <th>Request Date</th>
            <th>Status</th>
        </tr>
        <c:forEach var="req" items="${booksRequest}">
            <tr>
                <td>${req.bookId}</td>
                <td>${req.requestDate}</td>
                <td>${req.status}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

