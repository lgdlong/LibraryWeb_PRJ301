<%--overdue-management.jsp--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h2>Overdue Management</h2>
<p>Monitor and manage overdue records and automatic checking system.</p>

<!-- Manual Actions Card -->
<div class="row mb-4">
  <div class="col-md-12">
    <div class="card">
      <div class="card-header d-flex justify-content-between align-items-center">
        <h5 class="mb-0">Manual Actions</h5>
        <span class="badge bg-info">Scheduled checks run every 6 hours</span>
      </div>
      <div class="card-body">
        <div class="row">
          <div class="col-md-6">
            <h6>Run Manual Overdue Check</h6>
            <p class="text-muted">Manually trigger the overdue checking process to immediately update any records that have become overdue.</p>
            <form method="post" action="${pageContext.request.contextPath}/admin/borrow-records" style="display: inline;">
              <input type="hidden" name="action" value="check-overdue">
              <button type="submit" class="btn btn-warning" onclick="return confirm('Run overdue check now? This will update all overdue records and create fines.')">
                <i class="bi bi-arrow-clockwise"></i> Run Check Now
              </button>
            </form>
          </div>
          <div class="col-md-6">
            <h6>Process Overdue Fines</h6>
            <p class="text-muted">Update fine amounts for all existing overdue records based on current system settings.</p>
            <form method="post" action="${pageContext.request.contextPath}/admin/fines" style="display: inline;">
              <input type="hidden" name="action" value="process-overdue">
              <button type="submit" class="btn btn-primary" onclick="return confirm('Process all overdue fines? This may update fine amounts.')">
                <i class="bi bi-cash-coin"></i> Process Fines
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- Statistics Overview -->
<c:if test="${not empty overdueStats}">
<div class="row mb-4">
  <div class="col-md-12">
    <div class="card">
      <div class="card-header">
        <h5 class="mb-0">Overdue Statistics</h5>
        <small class="text-muted">Generated at: ${overdueStats.generatedAt}</small>
      </div>
      <div class="card-body">
        <div class="row text-center">
          <div class="col-md-3">
            <div class="border rounded p-3">
              <h4 class="text-primary">${overdueStats.totalOverdue}</h4>
              <small class="text-muted">Total Overdue</small>
            </div>
          </div>
          <div class="col-md-3">
            <div class="border rounded p-3">
              <h4 class="text-warning">${overdueStats.recentOverdue}</h4>
              <small class="text-muted">1-7 Days Overdue</small>
            </div>
          </div>
          <div class="col-md-3">
            <div class="border rounded p-3">
              <h4 class="text-danger">${overdueStats.moderateOverdue}</h4>
              <small class="text-muted">8-30 Days Overdue</small>
            </div>
          </div>
          <div class="col-md-3">
            <div class="border rounded p-3">
              <h4 class="text-dark">${overdueStats.severeOverdue}</h4>
              <small class="text-muted">30+ Days Overdue</small>
            </div>
          </div>
        </div>
        <div class="row mt-3">
          <div class="col-md-6">
            <div class="border rounded p-3 text-center">
              <h5 class="text-info">${overdueStats.totalBorrowed}</h5>
              <small class="text-muted">Currently Borrowed (Active)</small>
            </div>
          </div>
          <div class="col-md-6">
            <div class="border rounded p-3 text-center">
              <c:choose>
                <c:when test="${overdueStats.totalBorrowed > 0}">
                  <h5 class="text-secondary">
                    <fmt:formatNumber value="${(overdueStats.totalOverdue / overdueStats.totalBorrowed) * 100}" maxFractionDigits="1"/>%
                  </h5>
                </c:when>
                <c:otherwise>
                  <h5 class="text-secondary">0%</h5>
                </c:otherwise>
              </c:choose>
              <small class="text-muted">Overdue Rate</small>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</c:if>

<!-- Records Approaching Due Date -->
<c:if test="${not empty approachingDueRecords}">
<div class="row mb-4">
  <div class="col-md-12">
    <div class="card">
      <div class="card-header">
        <h5 class="mb-0">Records Approaching Due Date (Next 3 Days)</h5>
        <small class="text-muted">These records will become overdue soon</small>
      </div>
      <div class="card-body">
        <div class="table-responsive">
          <table class="table table-bordered table-hover table-sm">
            <thead class="table-dark">
            <tr>
              <th class="text-white text-uppercase">#</th>
              <th class="text-white text-uppercase">User Name</th>
              <th class="text-white text-uppercase">Book Title</th>
              <th class="text-white text-uppercase">Due Date</th>
              <th class="text-white text-uppercase">Days Until Due</th>
              <th class="text-white text-uppercase">Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="record" items="${approachingDueRecords}" varStatus="loop">
              <tr>
                <td>${loop.index + 1}</td>
                <td>${fn:escapeXml(record.userName)}</td>
                <td>${fn:escapeXml(record.bookTitle)}</td>
                <td>${fn:escapeXml(record.dueDate)}</td>
                <td>
                  <jsp:useBean id="now" class="java.util.Date"/>
                  <c:set var="daysUntilDue" value="${record.dueDate.toEpochDay() - (now.time / 86400000)}"/>
                  <span class="badge ${daysUntilDue <= 1 ? 'bg-danger' : 'bg-warning'}">
                    ${daysUntilDue} day(s)
                  </span>
                </td>
                <td>
                  <button class="btn btn-sm btn-outline-info" onclick="sendReminder('${record.userId}', '${fn:escapeXml(record.userName)}')">
                    <i class="bi bi-envelope"></i> Send Reminder
                  </button>
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
</c:if>

<!-- Quick Navigation -->
<div class="row">
  <div class="col-md-12">
    <div class="card">
      <div class="card-header">
        <h5 class="mb-0">Quick Navigation</h5>
      </div>
      <div class="card-body">
        <div class="d-flex flex-wrap gap-2">
          <a href="${pageContext.request.contextPath}/admin/borrow-records" class="btn btn-outline-primary">
            <i class="bi bi-calendar-check"></i> All Borrow Records
          </a>
          <a href="${pageContext.request.contextPath}/admin/fines" class="btn btn-outline-danger">
            <i class="bi bi-cash-coin"></i> Manage Fines
          </a>
          <a href="${pageContext.request.contextPath}/admin/books" class="btn btn-outline-info">
            <i class="bi bi-book"></i> Manage Books
          </a>
          <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-outline-secondary">
            <i class="bi bi-people"></i> Manage Users
          </a>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- Success/Error Messages -->
<c:if test="${not empty sessionScope.successMessage}">
  <div class="alert alert-success alert-dismissible fade show mt-3" role="alert">
    ${sessionScope.successMessage}
    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
  </div>
  <c:remove var="successMessage" scope="session"/>
</c:if>

<c:if test="${not empty sessionScope.errorMessage}">
  <div class="alert alert-danger alert-dismissible fade show mt-3" role="alert">
    ${sessionScope.errorMessage}
    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
  </div>
  <c:remove var="errorMessage" scope="session"/>
</c:if>

<script>
  function sendReminder(userId, userName) {
    // This would typically send an email or notification to the user
    // For now, just show a confirmation
    if (confirm('Send reminder to ' + userName + '?')) {
      // In a real implementation, this would make an AJAX call to send the reminder
      alert('Reminder functionality would be implemented here.\n\nWould send reminder to user ID: ' + userId);
    }
  }
  
  // Auto-refresh the page every 10 minutes to keep statistics current
  setTimeout(function() {
    if (confirm('Refresh overdue statistics? (Page will reload)')) {
      window.location.reload();
    }
  }, 600000); // 10 minutes
</script>

<style>
  .border {
    border: 1px solid #dee2e6 !important;
  }
  
  .rounded {
    border-radius: 0.375rem !important;
  }
  
  .badge {
    font-size: 0.75em;
  }
  
  .card-header h5 {
    margin-bottom: 0;
  }
  
  .card-header small {
    display: block;
    margin-top: 0.25rem;
  }
</style>
