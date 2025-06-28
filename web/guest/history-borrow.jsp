<%--
    Document   : history-borrow
    Created on : Jun 28, 2025, 2:11:42 AM
    Author     : Dien Sanh
--%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Borrowing History</h2>

<c:if test="${empty borrowHistory}">
    <p>You have no borrowing history yet.</p>
</c:if>

<c:if test="${not empty borrowHistory}">
    <table border="1" cellpadding="10" cellspacing="0" style="width: 100%; border-collapse: collapse; text-align: center;">
        <tr style="background-color: #f2f2f2;">
            <th>Book Title</th>
            <th>Borrow Date</th>
            <th>Due Date</th>
            <th>Return Date</th>
            <th>Status</th>
        </tr>

        <c:forEach var="record" items="${borrowHistory}">
            <tr>
                <td>${record.bookTitle}</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty record.borrowDate}">
                            ${record.borrowDate}
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${not empty record.dueDate}">
                            ${record.dueDate}
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${not empty record.returnDate}">
                            ${record.returnDate}
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>
                <td>${record.status}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

