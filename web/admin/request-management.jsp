<%--request-management.jsp--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h2>Borrow Request Management</h2>
<p>Manage and update borrow requests (approve/reject).</p>

<%-- Error handling --%>
<c:if test="${not empty error}">
  <div class="alert alert-danger mb-3">${fn:escapeXml(error)}</div>
</c:if>

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
          <thead class="table-dark">
          <tr>
            <th class="text-white text-uppercase">#</th>
            <th class="text-white text-uppercase">User Name</th>
            <th class="text-white text-uppercase">Book Title</th>
            <th class="text-white text-uppercase">Request Date</th>
            <th class="text-white text-uppercase">Status</th>
            <th class="text-white text-uppercase">Action</th>
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
                <td><span
                  class="status-badge status-badge-${fn:escapeXml(request.status)}">${fn:escapeXml(request.status)}</span>
                </td>
                <td>
                  <button class="btn btn-success btn-sm me-1" onclick="approveRequest('${fn:escapeXml(request.id)}')">
                    <i class="bi bi-check-circle"></i> Approve
                  </button>
                  <button class="btn btn-danger btn-sm" onclick="rejectRequest('${fn:escapeXml(request.id)}')">
                    <i class="bi bi-x-circle"></i> Reject
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
          <thead class="table-dark">
          <tr>
            <th class="text-white text-uppercase">#</th>
            <th class="text-white text-uppercase">User Name</th>
            <th class="text-white text-uppercase">Book Title</th>
            <th class="text-white text-uppercase">Request Date</th>
            <th class="text-white text-uppercase">Status</th>
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
                <td><span
                  class="status-badge status-badge-${fn:escapeXml(request.status)}">${fn:escapeXml(request.status)}</span>
                </td>
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
          <thead class="table-dark">
          <tr>
            <th class="text-white text-uppercase">#</th>
            <th class="text-white text-uppercase">User Name</th>
            <th class="text-white text-uppercase">Book Title</th>
            <th class="text-white text-uppercase">Request Date</th>
            <th class="text-white text-uppercase">Status</th>
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
                <td><span
                  class="status-badge status-badge-${fn:escapeXml(request.status)}">${fn:escapeXml(request.status)}</span>
                </td>
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

<!-- Confirmation Modal -->
<div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="confirmModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="confirmModalLabel">Confirm Action</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <p id="confirmMessage">Are you sure you want to perform this action?</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-danger" id="confirmActionBtn">Confirm</button>
      </div>
    </div>
  </div>
</div>

<script>
  function openRequestForm(id, userName, bookTitle, status, currentTab) {
    const modal = new bootstrap.Modal(document.getElementById('requestModal'));

    document.getElementById('requestId').value = id;
    document.getElementById('requestUserName').value = userName;
    document.getElementById('requestBookTitle').value = bookTitle;

    // Xử lý option động
    const statusSelect = document.getElementById('requestStatus');
    statusSelect.innerHTML = ''; // Clear existing options

    if (currentTab === 'pending') {
      statusSelect.innerHTML += '<option value="APPROVED">Approve</option>';
      statusSelect.innerHTML += '<option value="REJECTED">Reject</option>';
    } else if (currentTab === 'approved') {
      statusSelect.innerHTML += '<option value="PENDING">Mark as Pending</option>';
      statusSelect.innerHTML += '<option value="REJECTED">Reject</option>';
    } else if (currentTab === 'rejected') {
      statusSelect.innerHTML += '<option value="PENDING">Mark as Pending</option>';
      statusSelect.innerHTML += '<option value="APPROVED">Approve</option>';
    }

    // Set mặc định giá trị đầu tiên
    statusSelect.selectedIndex = 0;

    modal.show();
  }

  function approveRequest(requestId) {
    document.getElementById('confirmMessage').innerText = "Are you sure you want to approve this request?";

    const confirmModal = new bootstrap.Modal(document.getElementById('confirmModal'));
    confirmModal.show();

    document.getElementById('confirmActionBtn').onclick = function () {
      confirmModal.hide();
      updateRequestStatus(requestId, 'approved');
    };
  }

  function rejectRequest(requestId) {
    document.getElementById('confirmMessage').innerText = "Are you sure you want to reject this request?";

    const confirmModal = new bootstrap.Modal(document.getElementById('confirmModal'));
    confirmModal.show();

    document.getElementById('confirmActionBtn').onclick = function () {
      confirmModal.hide();
      updateRequestStatus(requestId, 'rejected');
    };
  }

  function updateRequestStatus(requestId, status) {
    const form = document.createElement('form');
    form.method = 'post';
    form.action = "${pageContext.request.contextPath}/admin/requests";

    const idField = document.createElement('input');
    idField.type = 'hidden';
    idField.name = 'id';
    idField.value = requestId;

    const statusField = document.createElement('input');
    statusField.type = 'hidden';
    statusField.name = 'status';
    statusField.value = status.toUpperCase();

    form.appendChild(idField);
    form.appendChild(statusField);
    document.body.appendChild(form);
    form.submit();
  }

  // Confirmation modal logic
  let currentRequestId;
  let currentAction;

  function showConfirmModal(requestId, action) {
    currentRequestId = requestId;
    currentAction = action;

    const actionText = action === 'approve' ? 'approve' : 'reject';
    document.getElementById('confirmMessage').innerText = `Are you sure you want to ${actionText} this request?`;

    const confirmModal = new bootstrap.Modal(document.getElementById('confirmModal'));
    confirmModal.show();
  }

  document.getElementById('confirmActionBtn').addEventListener('click', function () {
    if (currentAction === 'approve') {
      updateRequestStatus(currentRequestId, 'approved');
    } else if (currentAction === 'reject') {
      updateRequestStatus(currentRequestId, 'rejected');
    }

    const confirmModal = bootstrap.Modal.getInstance(document.getElementById('confirmModal'));
    confirmModal.hide();
  });
</script>

<style>
  .status-badge {
    display: inline-block;
    padding: 0.5em 1em;
    border-radius: 1em;
    font-size: 0.875em;
    font-weight: 500;
    color: #fff;
  }

  .status-badge-pending {
    background-color: #ffc107;
  }

  .status-badge-approved {
    background-color: #28a745;
  }

  .status-badge-rejected {
    background-color: #dc3545;
  }
</style>

