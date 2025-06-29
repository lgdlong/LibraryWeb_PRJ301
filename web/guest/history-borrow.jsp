<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container my-4">
    <h2 class="text-center mb-4">Borrowing History</h2>

    <c:if test="${empty borrowHistory}">
        <p class="text-danger text-center fw-bold">You have no borrowing history yet.</p>
    </c:if>

    <c:if test="${not empty borrowHistory}">
        <div class="table-responsive">
            <table class="table table-bordered text-center">
                <thead class="table-light">
                    <tr>
                        <th>Book Title</th>
                        <th>Borrow Date</th>
                        <th>Due Date</th>
                        <th>Return Date</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${borrowHistory}">
                        <tr>
                            <td>${item.bookTitle}</td>
                            <td>${item.borrowDate}</td>
                            <td>${item.dueDate}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${empty item.returnDate}">
                                        <span class="text-danger fw-bold">Not Returned</span>
                                    </c:when>
                                    <c:otherwise>
                                        ${item.returnDate}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${item.status == 'returned'}">
                                        <span class="text-success fw-bold">Returned</span>
                                    </c:when>
                                    <c:when test="${item.status == 'borrowed'}">
                                        <span class="text-warning fw-bold">Borrowed</span>
                                    </c:when>
                                    <c:when test="${item.status == 'overdue'}">
                                        <span class="text-danger fw-bold">Overdue</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-secondary">${item.status}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
</div>
