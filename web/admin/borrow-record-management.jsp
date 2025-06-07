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
          <thead class="table-light">
          <tr>
            <th>#</th>
            <th>User Name</th>
            <th>Book Title</th>
            <th>Borrow Date</th>
            <th>Due Date</th>
            <th>Return Date</th>
            <th>Status</th>
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
                <td>${fn:escapeXml(request.returnDate)}</td>
                <td>${fn:escapeXml(request.status)}</td>
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
          <thead class="table-light">
          <tr>
            <th>#</th>
            <th>User Name</th>
            <th>Book Title</th>
            <th>Borrow Date</th>
            <th>Due Date</th>
            <th>Return Date</th>
            <th>Status</th>
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
                <td>${fn:escapeXml(request.status)}</td>
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
          <thead class="table-light">
          <tr>
            <th>#</th>
            <th>User Name</th>
            <th>Book Title</th>
            <th>Borrow Date</th>
            <th>Due Date</th>
            <th>Return Date</th>
            <th>Status</th>
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
