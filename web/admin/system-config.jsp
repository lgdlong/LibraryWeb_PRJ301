<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 5/28/2025
  Time: 13:15
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>System Config</h2>
<p>This page use to set up system variable.</p>

<!-- Add Config Button -->
<div class="d-flex justify-content-end mt-3">
  <button class="btn btn-success" onclick="openForm(null)">Add Config</button>
</div>

<!-- Config Table -->
<div class="card mt-2">
  <div class="card-header">System Configuration Variables</div>
  <div class="card-body">
    <table class="table table-bordered table-hover table-sm">
      <thead class="table-light">
      <tr>
        <th>Key</th>
        <th>Value</th>
        <th>Description</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="config" items="${systemConfigs}">
        <tr onclick="openForm(${config.id}, '${config.configKey}', '${config.configValue}', '${config.description}')">
          <td>${config.configKey}</td>
          <td>${config.configValue}</td>
          <td>${config.description}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
</div>

<!-- Config Modal Form -->
<div class="modal fade" id="configModal" tabindex="-1" aria-labelledby="configModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <form method="post" action="${pageContext.request.contextPath}/admin/config">
        <div class="modal-header">
          <h5 class="modal-title" id="configModalLabel">Edit Config</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <input type="hidden" name="id" id="configId">
          <div class="mb-3">
            <label for="configKey" class="form-label">Key</label>
            <input type="text" class="form-control" id="configKey" name="configKey" required>
          </div>
          <div class="mb-3">
            <label for="configValue" class="form-label">Value</label>
            <input type="number" class="form-control" id="configValue" name="configValue" required>
          </div>
          <div class="mb-3">
            <label for="description" class="form-label">Description</label>
            <input type="text" class="form-control" id="description" name="description">
          </div>
        </div>
        <div class="modal-footer justify-content-between">
          <button type="button" class="btn btn-danger" id="deleteBtn" onclick="submitDelete()">Delete</button>
          <div>
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
            <button type="submit" class="btn btn-primary">Save</button>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>

<!-- Bootstrap JS + Form Script -->
<script>
  function openForm(id, key, value, desc) {
    const modal = new bootstrap.Modal(document.getElementById('configModal'));
    document.getElementById('configId').value = id || '';
    document.getElementById('configKey').value = key || '';
    document.getElementById('configValue').value = value || '';
    document.getElementById('description').value = desc || '';

    console.log(id, key, value, desc);

    // Show delete button only when editing
    document.getElementById('deleteBtn').style.display = id ? 'inline-block' : 'none';

    modal.show();
  }

  function submitDelete() {
    if (confirm("Are you sure to delete this config?")) {
      const id = document.getElementById('configId').value;
      if (id) {
        const form = document.createElement('form');
        form.method = 'post';
        form.action = "${pageContext.request.contextPath}/admin/config?delete=" + encodeURIComponent(id);

        const idField = document.createElement('input');
        idField.type = 'hidden';
        idField.name = 'id';
        idField.value = id;

        form.appendChild(idField);
        document.body.appendChild(form);
        form.submit();
      }
    }
  }
</script>

