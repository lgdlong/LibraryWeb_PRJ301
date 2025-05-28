<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
    Document   : admin.jsp
    Created on : May 28, 2025, 9:02:54 AM
    Author     : Long
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.Map" %>

<h2>Admin Dashboard</h2>
<p>Welcome, admin! Here's a quick overview of the system.</p>

<div class="row">
  <!-- Tổng số user -->
  <div class="col-md-3">
    <div class="card text-white bg-primary mb-3">
      <div class="card-header">Users</div>
      <div class="card-body">
        <h5 class="card-title">${userCount}</h5>
        <p class="card-text">Registered users in the system.</p>
      </div>
    </div>
  </div>

  <!-- Tổng số sách -->
  <div class="col-md-3">
    <div class="card text-white bg-warning mb-3">
      <div class="card-header">Books</div>
      <div class="card-body">
        <h5 class="card-title">${totalBooks}</h5>
        <p class="card-text">Books in library collection.</p>
      </div>
    </div>
  </div>

  <!-- Tổng yêu cầu đang chờ xử lý -->
  <div class="col-md-3">
    <div class="card text-white bg-info mb-3">
      <div class="card-header">Pending Book Requests</div>
      <div class="card-body">
        <h5 class="card-title">${pendingRequests}</h5>
        <p class="card-text">Book requests waiting for approval.</p>
      </div>
    </div>
  </div>

  <!-- Tổng tiền phạt chưa thanh toán -->
  <div class="col-md-3">
    <div class="card text-white bg-danger mb-3">
      <div class="card-header">Unpaid Fines</div>
      <div class="card-body">
        <h5 class="card-title">${unpaidFines}</h5>
        <p class="card-text">Fines not yet paid by users.</p>
      </div>
    </div>
  </div>
</div>

<!-- Danh sách các biến môi trường -->
<div class="card mt-4">
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
        <tr>
          <td>${config.configKey}</td>
          <td>${config.configValue}</td>
          <td>${config.description}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
</div>
