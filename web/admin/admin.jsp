<%--admin.jsp--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!-- Improved Dashboard Header with Welcome Message and Quick Actions -->
<div class="d-flex justify-content-between align-items-center mb-4">
  <div>
    <h2 class="mb-0">Admin Dashboard</h2>
    <p class="text-muted">Welcome, ${LOGIN_USER.name}! Here's your system overview.</p>
  </div>
</div>

<!-- Main Statistics Cards -->
<div class="row g-4">
  <!-- Users Card -->
  <div class="col-md-6 col-lg-3">
    <div class="card border-0 shadow-sm h-100">
      <div class="card-body p-4">
        <div class="d-flex align-items-center mb-3">
          <div class="icon-square text-bg-primary rounded-3 me-3">
            <i class="bi bi-people-fill fs-4"></i>
          </div>
          <h6 class="card-subtitle text-muted mb-0">Total Users</h6>
        </div>
        <h2 class="display-6 fw-bold mb-1">${userCount}</h2>
        <p class="card-text text-muted">Registered users in the system</p>
        <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-sm btn-outline-primary">Manage Users</a>
      </div>
    </div>
  </div>

  <!-- Books Card -->
  <div class="col-md-6 col-lg-3">
    <div class="card border-0 shadow-sm h-100">
      <div class="card-body p-4">
        <div class="d-flex align-items-center mb-3">
          <div class="icon-square text-bg-warning rounded-3 me-3">
            <i class="bi bi-book-fill fs-4"></i>
          </div>
          <h6 class="card-subtitle text-muted mb-0">Total Books</h6>
        </div>
        <h2 class="display-6 fw-bold mb-1">${totalBooks}</h2>
        <p class="card-text text-muted">Books in library collection</p>
        <a href="${pageContext.request.contextPath}/admin/books" class="btn btn-sm btn-outline-warning">Manage Books</a>
      </div>
    </div>
  </div>

  <!-- Pending Requests Card -->
  <div class="col-md-6 col-lg-3">
    <div class="card border-0 shadow-sm h-100">
      <div class="card-body p-4">
        <div class="d-flex align-items-center mb-3">
          <div class="icon-square text-bg-info rounded-3 me-3">
            <i class="bi bi-inboxes-fill fs-4"></i>
          </div>
          <h6 class="card-subtitle text-muted mb-0">Pending Requests</h6>
        </div>
        <h2 class="display-6 fw-bold mb-1">${pendingRequests}</h2>
        <p class="card-text text-muted">Book requests awaiting approval</p>
        <a href="${pageContext.request.contextPath}/admin/requests" class="btn btn-sm btn-outline-info">Review
          Requests</a>
      </div>
    </div>
  </div>

  <!-- Unpaid Fines Card -->
  <div class="col-md-6 col-lg-3">
    <div class="card border-0 shadow-sm h-100">
      <div class="card-body p-4">
        <div class="d-flex align-items-center mb-3">
          <div class="icon-square text-bg-danger rounded-3 me-3">
            <i class="bi bi-cash-coin fs-4"></i>
          </div>
          <h6 class="card-subtitle text-muted mb-0">Unpaid Fines</h6>
        </div>
        <h2 class="display-6 fw-bold mb-1">${unpaidFines}</h2>
        <p class="card-text text-muted">Fines not yet paid by users</p>
        <a href="${pageContext.request.contextPath}/admin/fines" class="btn btn-sm btn-outline-danger">Manage Fines</a>
      </div>
    </div>
  </div>
</div>

<!-- Secondary Stats and Charts -->
<div class="row g-4 mt-2">
  <!-- Currently Borrowed Card -->
  <div class="col-md-4">
    <div class="card border-0 shadow-sm h-100">
      <div class="card-header bg-white border-0 pb-0">
        <h5>Currently Borrowed</h5>
      </div>
      <div class="card-body pt-0">
        <div class="d-flex align-items-center">
          <div class="icon-square text-bg-secondary rounded-3 me-3">
            <i class="bi bi-box-arrow-right fs-4"></i>
          </div>
          <h2 class="display-6 fw-bold mb-0">${borrowedCount}</h2>
        </div>
        <p class="card-text text-muted mt-3">Books currently checked out by users</p>
        <a href="${pageContext.request.contextPath}/admin/borrow-records" class="btn btn-sm btn-outline-secondary">View
          Records</a>
      </div>
    </div>
  </div>

  <!-- Most Popular Books -->
  <div class="col-md-8">
    <div class="card border-0 shadow-sm h-100">
      <div class="card-header bg-white border-0 d-flex justify-content-between align-items-center">
        <h5 class="mb-0">Most Popular Books</h5>
        <span class="badge bg-primary">Top 5</span>
      </div>
      <div class="card-body p-0">
        <div class="table-responsive">
          <table class="table table-hover">
            <thead class="table-light">
            <tr>
              <th>Book Title</th>
              <th class="text-center">Borrow Count</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="b" items="${mostBorrowedBooks}">
              <tr>
                <td>
                  <div class="d-flex align-items-center">
                    <i class="bi bi-book me-2 text-primary" aria-hidden="true"></i>
                    <c:out value="${b.bookTitle}"/>
                  </div>
                </td>
                <td class="text-center">
                  <span class="badge bg-primary rounded-pill">${b.borrowCount}</span>
                </td>
              </tr>
            </c:forEach>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</div>

