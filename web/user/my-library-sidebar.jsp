<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">


<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm">
    <div class="container-fluid px-4">
        <!-- Left - Brand (Home) -->
        <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/home">Home</a>

        <!-- Collapse toggle button for responsive view -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <!-- Right - Links -->
        <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
            <ul class="right-links-group d-flex flex-row navbar-nav align-items-center">
                <li class="nav-item me-3">
                    <a class="nav-link fw-semibold" href="${pageContext.request.contextPath}/ViewBorrowBookController">Selected Books</a>
                </li>
                <li class="nav-item me-3">
                    <a class="nav-link fw-semibold" href="${pageContext.request.contextPath}/ViewHistoryBorrowController">Borrowed History</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link fw-semibold" href="${pageContext.request.contextPath}/ViewBooksRequestController">Borrow Requests</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
