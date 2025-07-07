<%--user-management.jsp--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h2>User Management</h2>
<p>Manage system users: add, update, block/unblock.</p>

<!-- Combined Search + Status Filter Form -->
<form method="get" action="${pageContext.request.contextPath}/admin/users" class="d-flex mb-3 gap-2">
  <select name="status" class="form-select" style="max-width: 150px;" onchange="this.form.submit()">
    <option value="all" ${param.status == 'all' ? 'selected' : ''}>All</option>
    <option value="active" ${param.status == null || param.status == 'active' ? 'selected' : ''}>Active</option>
    <option value="blocked" ${param.status == 'blocked' ? 'selected' : ''}>Blocked</option>
  </select>

  <input type="text" name="search" class="form-control" placeholder="Search by email" value="${param.search}"/>

  <button type="submit" class="btn btn-outline-primary">Search</button>
</form>

<!-- Add User Button -->
<div class="d-flex justify-content-end mb-2">
  <button class="btn btn-success" onclick="openUserForm(null)">Add User</button>
</div>

<!-- User Table -->
<div class="card">
  <div class="card-header">User List</div>
  <div class="card-body">
    <table class="table table-bordered table-hover table-sm">
      <thead class="table-dark">
      <tr>
        <th class="text-white text-uppercase">#</th>
        <th class="text-white text-uppercase">Name</th>
        <th class="text-white text-uppercase">Email</th>
        <th class="text-white text-uppercase">Role</th>
        <th class="text-white text-uppercase">Status</th>
        <th class="text-white text-uppercase">Action</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="user" items="${userList}" varStatus="loop">
        <tr>
          <td>${loop.index + 1}</td>
          <td>${user.name}</td>
          <td>${user.email}</td>
          <td>${user.role}</td>
          <td>
            <span class="status-badge status-badge-${fn:escapeXml(user.status)}">
                ${fn:escapeXml(user.status)}
            </span>
          </td>
          <td>
            <button class="btn btn-warning btn-sm me-1"
                    onclick="openUserForm('${fn:escapeXml(user.id)}', '${fn:escapeXml(user.email)}', '${fn:escapeXml(user.name)}', '${fn:escapeXml(user.role)}', '${fn:escapeXml(user.status)}')">
              <i class="bi bi-pencil"></i> Edit
            </button>
            <button class="btn btn-danger btn-sm"
                    onclick="toggleUserStatus('${fn:escapeXml(user.id)}', '${fn:escapeXml(user.status)}')">
              <i class="bi bi-${user.status == 'active' ? 'lock' : 'unlock'}"></i>
                ${user.status == 'active' ? 'Block' : 'Unblock'}
            </button>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
</div>

<!-- User Modal Form -->
<div class="modal fade" id="userModal" tabindex="-1" aria-labelledby="userModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <form method="post" action="${pageContext.request.contextPath}/admin/users">
        <div class="modal-header">
          <h5 class="modal-title" id="userModalLabel">Edit User</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <input type="hidden" name="id" id="userId">
          <div class="mb-3">
            <label for="name" class="form-label">Name</label>
            <input type="text" class="form-control" id="name" name="name" required>
          </div>
          <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" id="email" name="email" required>
          </div>
          <div class="mb-3" id="passwordGroup">
            <label for="password" class="form-label">Password</label>
            <input type="password" class="form-control" id="password" name="password" required>
          </div>
          <div class="mb-3">
            <label for="role" class="form-label">Role</label>
            <select class="form-select" id="role" name="role" required>
              <option value="admin">Admin</option>
              <option value="user">User</option>
            </select>
          </div>
          <div class="mb-3">
            <label for="status" class="form-label">Status</label>
            <select class="form-select" id="status" name="status" required>
              <option value="active">Active</option>
              <option value="blocked">Blocked</option>
            </select>
          </div>
        </div>
        <div class="modal-footer justify-content-between">
          <button type="button" class="btn btn-danger" id="userDeleteBtn" onclick="showDeleteConfirmModal()">Delete
          </button>
          <div>
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
            <button type="submit" class="btn btn-primary">Save</button>
          </div>
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
  function openUserForm(id, email, name, role, status) {
    const modal = new bootstrap.Modal(document.getElementById('userModal'));

    // Set values
    document.getElementById('userId').value = id || '';
    document.getElementById('email').value = email || '';
    document.getElementById('name').value = name || '';
    document.getElementById('role').value = role || 'user';
    document.getElementById('status').value = status || 'active';

    const isUpdate = id != null && id !== '';
    const passwordGroup = document.getElementById('passwordGroup');
    const passwordField = document.getElementById('password');

    // Show/hide password field based on add/update
    if (isUpdate) {
      passwordGroup.style.display = 'none';
      passwordField.required = false;
    } else {
      passwordGroup.style.display = 'block';
      passwordField.required = true;
      passwordField.value = '';
    }

    // Delete button only on update
    document.getElementById('userDeleteBtn').style.display = isUpdate ? 'inline-block' : 'none';

    modal.show();
  }

  function showDeleteConfirmModal() {
    const userId = document.getElementById('userId').value;
    const userName = document.getElementById('name').value;

    // Set confirmation message
    document.getElementById('confirmMessage').innerText = `Are you sure you want to delete the user "${userName}"?`;

    const confirmModal = new bootstrap.Modal(document.getElementById('confirmModal'));

    // Show confirmation modal
    confirmModal.show();

    // Handle confirm button click
    document.getElementById('confirmActionBtn').onclick = function () {
      confirmModal.hide();
      submitUserDelete(userId);
    };
  }

  function submitUserDelete(userId) {
    if (userId) {
      const form = document.createElement('form');
      form.method = 'post';
      form.action = "${pageContext.request.contextPath}/admin/users?delete=" + encodeURIComponent(userId);
      document.body.appendChild(form);
      form.submit();
    }
  }

  function toggleUserStatus(userId, currentStatus) {
    const newStatus = currentStatus === 'active' ? 'blocked' : 'active';
    const action = newStatus === 'blocked' ? 'block' : 'unblock';

    // Set confirmation message
    document.getElementById('confirmMessage').innerText = `Are you sure you want to ${action} this user?`;

    const confirmModal = new bootstrap.Modal(document.getElementById('confirmModal'));
    confirmModal.show();

    // Handle confirm button click
    document.getElementById('confirmActionBtn').onclick = function () {
      confirmModal.hide();

      const form = document.createElement('form');
      form.method = 'post';
      form.action = "${pageContext.request.contextPath}/admin/users";

      const idField = document.createElement('input');
      idField.type = 'hidden';
      idField.name = 'id';
      idField.value = userId;

      const statusField = document.createElement('input');
      statusField.type = 'hidden';
      statusField.name = 'status';
      statusField.value = newStatus;

      form.appendChild(idField);
      form.appendChild(statusField);
      document.body.appendChild(form);
      form.submit();
    };
  }
</script>

<style>
  .status-badge {
    padding: 0.5em 1em;
    border-radius: 1em;
    font-size: 0.875em;
    color: #fff;
  }

  .status-badge-active {
    background-color: #28a745;
  }

  .status-badge-blocked {
    background-color: #dc3545;
  }
</style>
