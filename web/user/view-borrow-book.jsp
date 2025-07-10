<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

<div class="container my-4">
    <h2 class="text-center mb-4">Borrow History</h2>

    <c:if test="${empty borrowHistory}">
        <p class="text-danger text-center">You have no borrow records yet.</p>
    </c:if>

    <c:if test="${not empty borrowHistory}">
        <div class="table-responsive">
            <table class="table table-bordered table-hover text-center align-middle">
                <thead class="table-dark">
                    <tr>
                        <th>Book Title</th>
                        <th>Borrow Date</th>
                        <th>Due Date</th>
                        <th>Return Date</th>
                        <th>Status</th>
                        <th>Fine (if any)</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="record" items="${borrowHistory}">
                        <tr>
                            <td class="text-start">${record.bookTitle}</td>
                            <td>${record.borrowDate}</td>
                            <td>${record.dueDate}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${record.returnDate != null}">
                                        ${record.returnDate}
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-danger">Not returned</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${record.status}</td>
                            <td>
                                <c:if test="${fineMap[record.id] != null}">
                                    <span class="text-danger fw-bold">
                                        $${fineMap[record.id].fineAmount}
                                    </span>
                                </c:if>
                                <c:if test="${fineMap[record.id] == null}">
                                    -
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
</div>

<!-- ✅ THÊM MỚI: Hiển thị danh sách sách quá hạn (sort theo tiền phạt) -->
<c:if test="${not empty overdueSortedByFine}">
    <div class="container my-5">
        <h3 class="text-center text-danger mb-3">Overdue Books (Sorted by Fine Amount)</h3>
        <div class="table-responsive">
            <table class="table table-striped table-bordered text-center align-middle">
                <thead class="table-danger">
                    <tr>
                        <th>Book Title</th>
                        <th>Borrow Date</th>
                        <th>Due Date</th>
                        <th>Fine Amount</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="record" items="${overdueSortedByFine}">
                        <tr>
                            <td class="text-start">${record.bookTitle}</td>
                            <td>${record.borrowDate}</td>
                            <td>${record.dueDate}</td>
                            <td class="fw-bold text-danger">
                                $${fineMap[record.id].fineAmount}
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</c:if>
