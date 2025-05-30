<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 5/31/2025
  Time: 01:38
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h2>Borrow Request Management</h2>
<p>Manage and update borrow requests (approve/reject).</p>

<!-- Tabs -->
<ul class="nav nav-tabs mb-3" id="requestTabs" role="tablist">
  <li class="nav-item" role="presentation">
    <button class="nav-link active" id="pending-tab" data-bs-toggle="tab" data-bs-target="#pending" type="button"
            role="tab" aria-controls="pending" aria-selected="true">Pending
    </button>
  </li>
  <li class="nav-item" role="presentation">
    <button class="nav-link" id="approved-tab" data-bs-toggle="tab" data-bs-target="#approved" type="button"
            role="tab" aria-controls="approved" aria-selected="false">Approved
    </button>
  </li>
  <li class="nav-item" role="presentation">
    <button class="nav-link" id="rejected-tab" data-bs-toggle="tab" data-bs-target="#rejected" type="button"
            role="tab" aria-controls="rejected" aria-selected="false">Rejected
    </button>
  </li>
</ul>

<div class="tab-content" id="requestTabsContent">
  <!-- Pending Tab -->
  <div class="tab-pane fade show active" id="pending" role="tabpanel" aria-labelledby="pending-tab">
    <div class="card">
      <div class="card-header">Pending Requests</div>
      <div class="card-body">
        <table class="table table-bordered table-hover table-sm">
          <thead class="table-light">
          <tr>
            <th>#</th>
            <th>User Name</th>
            <th>Book Title</th>
            <th>Request Date</th>
            <th>Status</th>
            <th>Action</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="request" items="${requestList}" varStatus="loop">
            <c:if test="${request.status == 'pending'}">
              <tr>
                <td>${loop.index + 1}</td>
                <td>${fn:escapeXml(request.userName)}</td>
                <td>${fn:escapeXml(request.bookTitle)}</td>
                <td>${fn:escapeXml(request.requestDate)}</td>
                <td>${fn:escapeXml(request.status)}</td>
                <td>
                  <button class="btn btn-sm btn-primary"
                          onclick="openRequestForm(${request.id}, '${fn:escapeXml(request.userName)}', '${fn:escapeXml(request.bookTitle)}', '${fn:escapeXml(request.status)}')">
                    Update
                  </button>
                </td>
              </tr>
            </c:if>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>

  <!-- Approved Tab -->
  <div class="tab-pane fade" id="approved" role="tabpanel" aria-labelledby="approved-tab">
    <div class="card">
      <div class="card-header">Approved Requests</div>
      <div class="card-body">
        <table class="table table-bordered table-hover table-sm">
          <thead class="table-light">
          <tr>
            <th>#</th>
            <th>User Name</th>
            <th>Book Title</th>
            <th>Request Date</th>
            <th>Status</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="request" items="${requestList}" varStatus="loop">
            <c:if test="${request.status == 'approved'}">
              <tr>
                <td>${loop.index + 1}</td>
                <td>${fn:escapeXml(request.userName)}</td>
                <td>${fn:escapeXml(request.bookTitle)}</td>
                <td>${fn:escapeXml(request.requestDate)}</td>
                <td>${fn:escapeXml(request.status)}</td>
              </tr>
            </c:if>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>

  <!-- Rejected Tab -->
  <div class="tab-pane fade" id="rejected" role="tabpanel" aria-labelledby="rejected-tab">
    <div class="card">
      <div class="card-header">Rejected Requests</div>
      <div class="card-body">
        <table class="table table-bordered table-hover table-sm">
          <thead class="table-light">
          <tr>
            <th>#</th>
            <th>User Name</th>
            <th>Book Title</th>
            <th>Request Date</th>
            <th>Status</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="request" items="${requestList}" varStatus="loop">
            <c:if test="${request.status == 'rejected'}">
              <tr>
                <td>${loop.index + 1}</td>
                <td>${fn:escapeXml(request.userName)}</td>
                <td>${fn:escapeXml(request.bookTitle)}</td>
                <td>${fn:escapeXml(request.requestDate)}</td>
                <td>${fn:escapeXml(request.status)}</td>
              </tr>
            </c:if>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>

<!-- Modal for Updating Request -->
<div class="modal fade" id="requestModal" tabindex="-1" aria-labelledby="requestModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <form method="post" action="${pageContext.request.contextPath}/admin/requests">
        <input type="hidden" name="csrf_token" value="${sessionScope.csrf_token}">
        <div class="modal-header">
          <h5 class="modal-title" id="requestModalLabel">Update Request</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <input type="hidden" name="id" id="requestId">
          <div class="mb-3">
            <label for="requestUserName" class="form-label">User Name</label>
            <input type="text" class="form-control" id="requestUserName" readonly>
          </div>
          <div class="mb-3">
            <label for="requestBookTitle" class="form-label">Book Title</label>
            <input type="text" class="form-control" id="requestBookTitle" readonly>
          </div>
          <div class="mb-3">
            <label for="requestStatus" class="form-label">Status</label>
            <select class="form-select" name="status" id="requestStatus" required>
              <option value="APPROVED">Approve</option>
              <option value="REJECTED">Reject</option>
            </select>
          </div>
        </div>
        <div class="modal-footer justify-content-between">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
          <button type="submit" class="btn btn-primary">Update Status</button>
        </div>
      </form>
    </div>
  </div>
</div>

<script>
  function openRequestForm(id, userName, bookTitle, status) {
    const modal = new bootstrap.Modal(document.getElementById('requestModal'));

    document.getElementById('requestId').value = id;
    document.getElementById('requestUserName').value = userName;
    document.getElementById('requestBookTitle').value = bookTitle;
    document.getElementById('requestStatus').value = 'APPROVED'; // Mặc định chọn Approve

    modal.show();
  }
</script>
