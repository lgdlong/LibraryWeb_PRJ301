<%--fine-management.jsp--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h2>Fine Management</h2>
<p>Manage and update fine payment status (Paid/Unpaid).</p>

<!-- Tabs -->
<ul class="nav nav-tabs mb-3" id="fineTabs" role="tablist">
  <li class="nav-item" role="presentation">
    <button class="nav-link active" id="unpaid-tab" data-bs-toggle="tab" data-bs-target="#unpaid" type="button"
            role="tab" aria-controls="unpaid" aria-selected="true">Unpaid
    </button>
  </li>
  <li class="nav-item" role="presentation">
    <button class="nav-link" id="paid-tab" data-bs-toggle="tab" data-bs-target="#paid" type="button"
            role="tab" aria-controls="paid" aria-selected="false">Paid
    </button>
  </li>
</ul>
<div class="tab-content" id="fineTabsContent">
  <!-- Unpaid Fines -->
  <div class="tab-pane fade show active" id="unpaid" role="tabpanel" aria-labelledby="unpaid-tab">
    <div class="card">
      <div class="card-header">Unpaid Fines</div>
      <div class="card-body">
        <table class="table table-bordered table-hover table-sm">
          <thead class="table-dark">
          <tr>
            <th class="text-white text-uppercase">#</th>
            <th class="text-white text-uppercase">Username</th>
            <th class="text-white text-uppercase">Book Title</th>
            <th class="text-white text-uppercase">Amount</th>
            <th class="text-white text-uppercase">Status</th>
            <th class="text-white text-uppercase">Action</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="fine" items="${fineList}" varStatus="loop">
            <c:if test="${fine.paidStatus == 'unpaid'}">
              <tr>
                <td>${loop.index + 1}</td>
                <td>${fine.userName}</td>
                <td>${fine.bookTitle}</td>
                <td class="fw-bold text-primary">${fine.fineAmount}</td>
                <td><span class="status-badge status-badge-${fine.paidStatus}">${fine.paidStatus}</span></td>
                <td>
                  <button class="btn btn-success btn-sm" onclick="markAsPaid('${fine.id}', '${fn:escapeXml(fine.userName)}')"
                  onCli>
                    <i class="bi bi-check-circle"></i> Mark as Paid
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

  <!-- Paid Fines -->
  <div class="tab-pane fade" id="paid" role="tabpanel" aria-labelledby="paid-tab">
    <div class="card">
      <div class="card-header">Paid Fines</div>
      <div class="card-body">
        <table class="table table-bordered table-hover table-sm">
          <thead class="table-dark">
          <tr>
            <th class="text-white text-uppercase">#</th>
            <th class="text-white text-uppercase">Username</th>
            <th class="text-white text-uppercase">Book Title</th>
            <th class="text-white text-uppercase">Amount</th>
            <th class="text-white text-uppercase">Status</th>
            <th class="text-white text-uppercase">Action</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="fine" items="${fineList}" varStatus="loop">
            <c:if test="${fine.paidStatus == 'paid'}">
              <tr>
                <td>${loop.index + 1}</td>
                <td>${fine.userName}</td>
                <td>${fine.bookTitle}</td>
                <td class="fw-bold text-primary">${fine.fineAmount}</td>
                <td><span class="status-badge status-badge-${fine.paidStatus}">${fine.paidStatus}</span></td>
                <td>
                  <button class="btn btn-warning btn-sm" onclick="markAsUnpaid('${fine.id}', '${fn:escapeXml(fine.userName)}')">
                    <i class="bi bi-x-circle"></i> Mark as Unpaid
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
</div>

<!-- Modal for Updating Fine -->
<div class="modal fade" id="fineModal" tabindex="-1" aria-labelledby="fineModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <form method="post" action="${pageContext.request.contextPath}/admin/fines">
        <input type="hidden" name="csrf_token" value="${sessionScope.csrf_token}">
        <div class="modal-header">
          <h5 class="modal-title" id="fineModalLabel">Update Fine</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <input type="hidden" name="id" id="fineId">
          <div class="mb-3">
            <label class="form-label">Username</label>
            <input type="text" class="form-control" id="fineUsername" readonly>
          </div>
          <div class="mb-3">
            <label class="form-label">Book Title</label>
            <input type="text" class="form-control" id="fineBookTitle" readonly>
          </div>
          <div class="mb-3">
            <label class="form-label">Fine Amount</label>
            <input type="text" class="form-control" id="fineAmount" readonly>
          </div>
          <div class="mb-3">
            <label for="fineStatus" class="form-label">Status</label>
            <select class="form-select" name="paidStatus" id="fineStatus" required>
              <!-- Options will be inserted dynamically -->
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
  function openFineForm(id, username, bookTitle, amount, paidStatus, currentTab) {
    const modal = new bootstrap.Modal(document.getElementById('fineModal'));

    document.getElementById('fineId').value = id;
    document.getElementById('fineUsername').value = username;
    document.getElementById('fineBookTitle').value = bookTitle;
    document.getElementById('fineAmount').value = amount;

    const statusSelect = document.getElementById('fineStatus');
    statusSelect.innerHTML = '';

    if (currentTab === 'unpaid') {
      statusSelect.innerHTML += '<option value="PAID">Mark as Paid</option>';
    } else if (currentTab === 'paid') {
      statusSelect.innerHTML += '<option value="UNPAID">Mark as Unpaid</option>';
    }

    statusSelect.selectedIndex = 0;

    modal.show();
  }

  function markAsPaid(fineId, userName) {
    if (confirm('Are you sure you want to mark this fine as PAID for ' + userName + '?')) {
    updateFineStatus(fineId, 'PAID');
  }
  }

  function markAsUnpaid(fineId, userName) {
    if (confirm('Are you sure you want to mark this fine as UNPAID for ' + userName + '?')) {
      updateFineStatus(fineId, 'UNPAID');
    }
  }

  function updateFineStatus(fineId, newStatus) {
    const form = document.createElement('form');
    form.method = 'post';
    form.action = '${pageContext.request.contextPath}/admin/fines';

    const idField = document.createElement('input');
    idField.type = 'hidden';
    idField.name = 'id';
    idField.value = fineId;

    const statusField = document.createElement('input');
    statusField.type = 'hidden';
    statusField.name = 'paidStatus';
    statusField.value = newStatus;

    const csrfField = document.createElement('input');
    csrfField.type = 'hidden';
    csrfField.name = 'csrf_token';
    csrfField.value = '${sessionScope.csrf_token}';

    form.appendChild(idField);
    form.appendChild(statusField);
    form.appendChild(csrfField);
    document.body.appendChild(form);
    form.submit();
  }
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

  .status-badge-unpaid {
    background-color: #dc3545;
  }

  .status-badge-paid {
    background-color: #28a745;
  }
</style>


