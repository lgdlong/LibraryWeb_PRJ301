<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>

<div class="container mt-5">
    <h2 class="text-center mb-4">Your Borrowed Books</h2>

    <c:if test="${not empty message}">
        <p class="text-center" style="color: red;">${message}</p>
    </c:if>

    <c:set var="borrowBook" value="${sessionScope.borrowBook}" />

    <c:if test="${empty borrowBook and empty message}">
        <p class="text-center text-danger">You have not selected any books to borrow.</p>
    </c:if>

    <c:if test="${not empty borrowBook}">
        <div class="table-responsive">
            <table class="table table-bordered table-striped align-middle">
                <thead class="table-dark">
                    <tr>
                        <th>Title</th>
                        <th>Author</th>
                        <th>Category</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="book" items="${borrowBook}">
                        <tr>
                            <td>${book.title}</td>
                            <td>${book.author}</td>
                            <td>${book.category}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/ViewBorrowBookController" method="post" class="d-inline">
                                    <input type="hidden" name="bookId" value="${book.id}" />
                                    <input type="hidden" name="action" value="remove" />
                                    <button type="submit" class="btn btn-sm btn-danger">Remove</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <form action="${pageContext.request.contextPath}/SendRequestBorrowController" method="post" class="text-center mt-3">
            <button type="submit" class="btn btn-primary">Send Request</button>
        </form>
    </c:if>
</div>
