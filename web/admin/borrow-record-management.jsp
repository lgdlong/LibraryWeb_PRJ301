<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 5/31/2025
  Time: 00:50
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h2>Borrow Record Management</h2>
<p>View current and past borrow records.</p>

<!-- Tabs for Borrow Status -->
<ul class="nav nav-tabs mb-3" id="borrowStatusTabs" role="tablist">
  <li class="nav-item" role="presentation">
    <button class="nav-link active" id="borrowed-tab" data-bs-toggle="tab" data-bs-target="#borrowed" type="button"
            role="tab" aria-controls="borrowed" aria-selected="true">Borrowed
    </button>
  </li>
  <li class="nav-item" role="presentation">
    <button class="nav-link" id="returned-tab" data-bs-toggle="tab" data-bs-target="#returned" type="button"
            role="tab" aria-controls="returned" aria-selected="false">Returned
    </button>
  </li>
  <li class="nav-item" role="presentation">
    <button class="nav-link" id="overdue-tab" data-bs-toggle="tab" data-bs-target="#overdue" type="button"
            role="tab" aria-controls="overdue" aria-selected="false">Overdue
    </button>
  </li>
</ul>

<!-- Tab Content -->
<div class="tab-content" id="borrowStatusTabContent">
  <!-- Borrowed Tab -->
  <div class="tab-pane fade show active" id="borrowed" role="tabpanel" aria-labelledby="borrowed-tab">
    <div class="card">
      <div class="card-header">Borrowed Records</div>
      <div class="card-body">
        <table class="table table-bordered table-hover table-sm">
          <thead class="table-dark">
          <tr>
            <th class="text-white text-uppercase">#</th>
            <th class="text-white text-uppercase">User Name</th>
            <th class="text-white text-uppercase">Book Title</th>
            <th class="text-white text-uppercase">Borrow Date</th>
            <th class="text-white text-uppercase">Due Date</th>
            <th class="text-white text-uppercase">Status</th>
            <th class="text-white text-uppercase">Action</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="request" items="${borrowRecordList}" varStatus="loop">
            <c:if test="${request.status == 'borrowed'}">
              <tr>
                <td>${loop.index + 1}</td>
                <td>${fn:escapeXml(request.userName)}</td>
                <td>${fn:escapeXml(request.bookTitle)}</td>
                <td>${fn:escapeXml(request.borrowDate)}</td>
                <td>${fn:escapeXml(request.dueDate)}</td>
                <td><span
                  class="status-badge status-badge-${fn:escapeXml(request.status)}">${fn:escapeXml(request.status)}</span>
                </td>
                <td>
                  <button class="btn btn-success btn-sm" onclick="openReturnModal('${request.id}', false)">
                    <i class="bi bi-arrow-return-left"></i> Return
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

  <!-- Returned Tab -->
  <div class="tab-pane fade" id="returned" role="tabpanel" aria-labelledby="returned-tab">
    <div class="card">
      <div class="card-header">Returned Records</div>
      <div class="card-body">
        <table class="table table-bordered table-hover table-sm">
          <thead class="table-dark">
          <tr>
            <th class="text-white text-uppercase">#</th>
            <th class="text-white text-uppercase">User Name</th>
            <th class="text-white text-uppercase">Book Title</th>
            <th class="text-white text-uppercase">Borrow Date</th>
            <th class="text-white text-uppercase">Due Date</th>
            <th class="text-white text-uppercase">Return Date</th>
            <th class="text-white text-uppercase">Status</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="request" items="${borrowRecordList}" varStatus="loop">
            <c:if test="${request.status == 'returned'}">
              <tr>
                <td>${loop.index + 1}</td>
                <td>${fn:escapeXml(request.userName)}</td>
                <td>${fn:escapeXml(request.bookTitle)}</td>
                <td>${fn:escapeXml(request.borrowDate)}</td>
                <td>${fn:escapeXml(request.dueDate)}</td>
                <td>${fn:escapeXml(request.returnDate)}</td>
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

  <!-- Overdue Tab -->
  <div class="tab-pane fade" id="overdue" role="tabpanel" aria-labelledby="overdue-tab">
    <div class="card">
      <div class="card-header">Overdue Records</div>
      <div class="card-body">
        <table class="table table-bordered table-hover table-sm">
          <thead class="table-dark">
          <tr>
            <th class="text-white text-uppercase">#</th>
            <th class="text-white text-uppercase">User Name</th>
            <th class="text-white text-uppercase">Book Title</th>
            <th class="text-white text-uppercase">Borrow Date</th>
            <th class="text-white text-uppercase">Due Date</th>
            <th class="text-white text-uppercase">Return Date</th>
            <th class="text-white text-uppercase">Status</th>
            <th class="text-white text-uppercase">Action</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="request" items="${borrowRecordList}" varStatus="loop">
            <c:if test="${request.status == 'overdue'}">
              <tr>
                <td>${loop.index + 1}</td>
                <td>${fn:escapeXml(request.userName)}</td>
                <td>${fn:escapeXml(request.bookTitle)}</td>
                <td>${fn:escapeXml(request.borrowDate)}</td>
                <td>${fn:escapeXml(request.dueDate)}</td>
                <td>${fn:escapeXml(request.returnDate)}</td>
                <td><span
                  class="status-badge status-badge-${fn:escapeXml(request.status)}">${fn:escapeXml(request.status)}</span>
                </td>
                <td>
                  <button class="btn btn-success btn-sm" onclick="openReturnModal('${request.id}', true)">
                    <i class="bi bi-arrow-return-left"></i> Return
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

<!-- Return Confirmation Modal -->
<div class="modal fade" id="returnConfirmModal" tabindex="-1" aria-labelledby="returnConfirmModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="returnConfirmModalLabel">Confirm Return</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <p id="returnMessage">Are you sure you want to mark this book as returned?</p>
        <div id="finePaymentSection" style="display: none;">
          <hr>
          <div class="form-check">
            <input class="form-check-input" type="checkbox" id="finePaidCheck">
            <label class="form-check-label" for="finePaidCheck">
              Has the fine been paid?
            </label>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-success" id="confirmReturnBtn">Confirm Return</button>
      </div>
    </div>
  </div>
</div>

<script>
  let currentBorrowId;
  let isCurrentOverdue;

  function openReturnModal(borrowId, isOverdue) {
    currentBorrowId = borrowId;
    isCurrentOverdue = isOverdue;

    const message = isOverdue ?
      "This book is overdue. Please confirm if you want to mark it as returned." :
      "Are you sure you want to mark this book as returned?";

    document.getElementById('returnMessage').innerText = message;
    document.getElementById('finePaymentSection').style.display = isOverdue ? 'block' : 'none';

    const modal = new bootstrap.Modal(document.getElementById('returnConfirmModal'));
    modal.show();
  }

  document.getElementById('confirmReturnBtn').addEventListener('click', function () {
    const isPaid = document.getElementById('finePaidCheck').checked;
    returnBook(currentBorrowId, isCurrentOverdue, isPaid);
  });

  function returnBook(borrowId, isOverdue, isPaid) {
    const form = document.createElement('form');
    form.method = 'post';
    form.action = "${pageContext.request.contextPath}/admin/borrow-records";

    const idField = document.createElement('input');
    idField.type = 'hidden';
    idField.name = 'id';
    idField.value = borrowId;

    const statusField = document.createElement('input');
    statusField.type = 'hidden';
    statusField.name = 'status';
    statusField.value = 'returned';

    const paidField = document.createElement('input');
    paidField.type = 'hidden';
    paidField.name = 'finePaid';
    paidField.value = isPaid ? 'true' : 'false';

    form.appendChild(idField);
    form.appendChild(statusField);
    form.appendChild(paidField);
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
    text-transform: capitalize;
  }

  .status-badge-borrowed {
    background-color: #0dcaf0; /* Bootstrap info blue */
  }

  .status-badge-returned {
    background-color: #198754; /* Bootstrap success green */
  }

  .status-badge-overdue {
    background-color: #dc3545; /* Bootstrap danger red */
  }
</style>
