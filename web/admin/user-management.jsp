<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 5/29/2025
  Time: 20:56
  To change this template use File | Settings | File Templates.
--%>
<!-- user-management.jsp -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>User Management</h2>
<p>Manage system users: add, update, block/unblock.</p>

<!-- Search bar -->
<form method="get" action="${pageContext.request.contextPath}/admin/users" class="d-flex mb-3">
  <input type="text" name="search" class="form-control me-2" placeholder="Search by email" value="${param.search}"/>
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
      <thead class="table-light">
      <tr>
        <th>#</th> <!-- STT -->
        <th>Name</th>
        <th>Email</th>
        <th>Role</th>
        <th>Status</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="user" items="${userList}" varStatus="loop">
        <tr onclick="openUserForm('${user.id}', '${user.email}', '${user.name}', '${user.role}', '${user.status}')">
          <td>${loop.index + 1}</td> <!-- STT bắt đầu từ 1 -->
          <td>${user.name}</td>
          <td>${user.email}</td>
          <td>${user.role}</td>
          <td><span class="status-badge status-badge-${fn:escapeXml(user.status)}">${user.status}</span></td>
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
          <button type="button" class="btn btn-danger" id="userDeleteBtn" onclick="submitUserDelete()">Delete</button>
          <div>
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
            <button type="submit" class="btn btn-primary">Save</button>
          </div>
        </div>
      </form>
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


  function submitUserDelete() {
    if (confirm("Are you sure to delete this user?")) {
      const id = document.getElementById('userId').value;
      if (id) {
        const form = document.createElement('form');
        form.method = 'post';
        form.action = "${pageContext.request.contextPath}/admin/users?delete=" + encodeURIComponent(id);
        document.body.appendChild(form);
        form.submit();
      }
    }
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
