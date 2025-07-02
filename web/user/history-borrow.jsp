<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

<div class="container my-4">
    <h2 class="text-center mb-4">Borrowing History</h2>

    <c:if test="${empty borrowHistory}">
        <p class="text-danger text-center fw-bold">You have no borrowing history yet.</p>
    </c:if>

    <c:if test="${not empty borrowHistory}">
        <!-- Bootstrap Tabs -->
        <ul class="nav nav-tabs mb-3" id="historyTabs" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active" id="borrowed-tab" data-bs-toggle="tab" data-bs-target="#borrowed" type="button"
                        role="tab" aria-controls="borrowed" aria-selected="true">Borrowed
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="overdue-tab" data-bs-toggle="tab" data-bs-target="#overdue" type="button"
                        role="tab" aria-controls="overdue" aria-selected="false">Overdue
                </button>
            </li>
        </ul>

        <!-- Tab Content -->
        <div class="tab-content" id="historyTabsContent">
            <!-- Borrowed Tab -->
            <div class="tab-pane fade show active" id="borrowed" role="tabpanel" aria-labelledby="borrowed-tab">
                <div class="card">
                    <div class="card-header">Borrowed Records</div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered text-center">
                                <thead class="table-light">
                                    <tr>
                                        <th>Book Title</th>
                                        <th>Borrow Date</th>
                                        <th>Due Date</th>
                                        <th>Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:set var="hasBorrowedRecords" value="false" />
                                    <c:forEach var="item" items="${borrowHistory}">
                                        <c:if test="${item.status == 'borrowed'}">
                                            <c:set var="hasBorrowedRecords" value="true" />
                                            <tr>
                                                <td>${item.bookTitle}</td>
                                                <td>${item.borrowDate}</td>
                                                <td>${item.dueDate}</td>
                                                <td>
                                                    <span class="text-warning fw-bold">Borrowed</span>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${not hasBorrowedRecords}">
                                        <tr>
                                            <td colspan="5" class="text-muted text-center">No borrowed records found.</td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Overdue Tab -->
            <div class="tab-pane fade" id="overdue" role="tabpanel" aria-labelledby="overdue-tab">
                <div class="card">
                    <div class="card-header">Overdue Records</div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered text-center">
                                <thead class="table-light">
                                    <tr>
                                        <th>Book Title</th>
                                        <th>Borrow Date</th>
                                        <th>Due Date</th>
                                        <th>Fine Amount</th>
                                        <th>Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:set var="hasOverdueRecords" value="false" />
                                    <c:forEach var="item" items="${borrowHistory}">
                                        <c:if test="${item.status == 'overdue'}">
                                            <c:set var="hasOverdueRecords" value="true" />
                                            <tr>
                                                <td>${item.bookTitle}</td>
                                                <td>${item.borrowDate}</td>
                                                <td>${item.dueDate}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${fineMap[item.id] != null}">
                                                            <span class="fw-bold text-primary">${fineMap[item.id].fineAmount}</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="text-muted">0</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <span class="text-danger fw-bold">Overdue</span>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${not hasOverdueRecords}">
                                        <tr>
                                            <td colspan="5" class="text-muted text-center">No overdue records found.</td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</div>
