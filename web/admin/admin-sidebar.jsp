<%--
    Document   : admin-sidebar.jsp
    Created on : May 28, 2025, 9:15:38 AM
    Author     : ADMIN
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<div class="bg-light border-end" style="width: 250px; min-height: 100vh;">
  <div class="sidebar-heading fw-bold text-center py-3 border-bottom bg-white">
    Admin Panel
  </div>
  <div class="list-group list-group-flush">
    <a href="${pageContext.request.contextPath}/admin" class="list-group-item list-group-item-action">
      Dashboard
    </a>
    <a href="${pageContext.request.contextPath}/admin?action=config" class="list-group-item list-group-item-action">
      Config System Variable
    </a>
    <a href="${pageContext.request.contextPath}/admin?action=users" class="list-group-item list-group-item-action">
      Manage User
    </a>
    <a href="${pageContext.request.contextPath}/admin?action=books" class="list-group-item list-group-item-action">
      Manage Books
    </a>
    <a href="${pageContext.request.contextPath}/admin?action=borrows" class="list-group-item list-group-item-action">
      Manage Borrows
    </a>
    <a href="${pageContext.request.contextPath}/admin?action=fines" class="list-group-item list-group-item-action">
      Manage Fines
    </a>
  </div>
</div>

