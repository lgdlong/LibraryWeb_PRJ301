<%--admin-sidebar.jsp--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<div class="bg-white border-end shadow-sm" style="width: 250px; min-height: 100vh;">
  <div class="sidebar-heading d-flex align-items-center gap-2 fw-bold py-3 px-3 border-bottom">
    <i class="bi bi-book-half text-primary fs-4"></i>
    <span>Library Admin</span>
  </div>

  <div class="px-3 py-4">
    <div class="d-flex align-items-center mb-3">
      <div class="avatar-circle bg-primary text-white me-2 d-flex align-items-center justify-content-center"
           style="width: 32px; height: 32px; border-radius: 50%;">
        <i class="bi bi-person-fill"></i>
      </div>
      <div>
        <div class="fw-medium">${LOGIN_USER.name}</div>
        <small class="text-muted">${LOGIN_USER.role}</small>
      </div>
    </div>
  </div>

  <div class="list-group list-group-flush">
    <a href="${pageContext.request.contextPath}/admin"
       class="list-group-item list-group-item-action d-flex align-items-center gap-2
              ${pageContext.request.servletPath.contains('/admin.jsp') ? 'active' : ''}">
      <i class="bi bi-speedometer2"></i>
      Dashboard
    </a>

    <a href="${pageContext.request.contextPath}/admin/config"
       class="list-group-item list-group-item-action d-flex align-items-center gap-2
              ${pageContext.request.servletPath.contains('/system-config.jsp') ? 'active' : ''}">
      <i class="bi bi-gear"></i>
      System Config
    </a>

    <a href="${pageContext.request.contextPath}/admin/users"
       class="list-group-item list-group-item-action d-flex align-items-center gap-2
              ${pageContext.request.servletPath.contains('/user-management.jsp') ? 'active' : ''}">
      <i class="bi bi-people"></i>
      Manage Users
    </a>

    <a href="${pageContext.request.contextPath}/admin/books"
       class="list-group-item list-group-item-action d-flex align-items-center gap-2
              ${pageContext.request.servletPath.contains('/book-management.jsp') ? 'active' : ''}">
      <i class="bi bi-book"></i>
      Manage Books
    </a>

    <a href="${pageContext.request.contextPath}/admin/requests"
       class="list-group-item list-group-item-action d-flex align-items-center gap-2
              ${pageContext.request.servletPath.contains('/request-management.jsp') ? 'active' : ''}">
      <i class="bi bi-inbox"></i>
      Manage Requests
    </a>

    <a href="${pageContext.request.contextPath}/admin/borrow-records"
       class="list-group-item list-group-item-action d-flex align-items-center gap-2
              ${pageContext.request.servletPath.contains('/borrow-record-management.jsp') ? 'active' : ''}">
      <i class="bi bi-calendar-check"></i>
      Manage Borrows
    </a>

    <a href="${pageContext.request.contextPath}/admin/overdue-management"
       class="list-group-item list-group-item-action d-flex align-items-center gap-2
              ${pageContext.request.servletPath.contains('/overdue-management.jsp') ? 'active' : ''}">
      <i class="bi bi-exclamation-triangle"></i>
      Overdue Management
    </a>

    <a href="${pageContext.request.contextPath}/admin/fines"
       class="list-group-item list-group-item-action d-flex align-items-center gap-2
              ${pageContext.request.servletPath.contains('/fine-management.jsp') ? 'active' : ''}">
      <i class="bi bi-cash"></i>
      Manage Fines
    </a>
  </div>

  <div class="mt-auto p-3 border-top">
    <a href="${pageContext.request.contextPath}/LogoutController"
       class="btn btn-outline-danger btn-sm d-flex align-items-center gap-2 w-100 justify-content-center">
      <i class="bi bi-box-arrow-right"></i>
      Logout
    </a>
  </div>
</div>
