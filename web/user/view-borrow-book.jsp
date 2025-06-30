<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

<div class="container my-4">
    <h2 class="text-center mb-4">Your Book Requests</h2>

    <c:if test="${empty booksRequest}">
        <p class="text-danger text-center">You have no book requests yet.</p>
    </c:if>

    <c:if test="${booksRequest != null && not empty booksRequest}">
        <div class="table-responsive">
            <table class="table table-striped table-bordered table-hover text-center">
               <thead class="table-dark">
                   <tr>
                       <th>Book Title</th>
                       <th>Request Date</th>
                       <th>Status</th>
                   </tr>
               </thead>
               <tbody>
                   <c:forEach var="req" items="${booksRequest}">
                       <tr>
                           <td>${bookTitles[req.bookId]}</td> <!-- Lấy tên từ map -->
                           <td>${req.requestDate}</td>
                           <td>
                               <c:choose>
                                   <c:when test="${req.status == 'PENDING'}">
                                       <span class="fw-bold text-dark">PENDING</span>
                                   </c:when>
                                   <c:when test="${req.status == 'APPROVED'}">
                                       <span class="fw-bold text-success">APPROVED</span>
                                   </c:when>
                                   <c:when test="${req.status == 'REJECTED'}">
                                       <span class="fw-bold text-danger">REJECTED</span>
                                   </c:when>
                                   <c:otherwise>
                                       <span class="text-secondary">${req.status}</span>
                                   </c:otherwise>
                               </c:choose>
                           </td>
                       </tr>
                   </c:forEach>
               </tbody>
            </table>
        </div>
    </c:if>
</div>
