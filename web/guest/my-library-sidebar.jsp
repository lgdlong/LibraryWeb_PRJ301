<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="sidebar">
    <h2 class="sidebar-title">My Library</h2>
    <ul class="nav-list">
            <li><a href="${pageContext.request.contextPath}/GuestHomeController">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/ViewBorrowBookController">Cart</a></li>
            <li><a href="${pageContext.request.contextPath}/ViewHistoryBorrowController">Borrow History</a></li>
    </ul>
</div>
