
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="book" value="${sessionScope.book}" />

<div class="book-container">
    <div class="book-image-details">
        <img src="${book.coverUrl}" alt="Book Cover">
    </div>

    <div class="book-details">
        <h2>Detail</h2>
        <p><span>Author:</span> ${book.author}</p>
        <p><span>ISBN:</span> ${book.isbn}</p>
        <p><span>Category:</span> ${book.category}</p>
        <p><span>Public Year:</span> ${book.publishedYear}</p>
        <p><span>Available Copies:</span> ${book.availableCopies}</p>

        <div class="book-action">
            <c:choose>
                <c:when test="${book.availableCopies > 0}">
                    <form action="${pageContext.request.contextPath}/borrow/book" method="post" class="borrow-form">
                        <input type="hidden" name="bookId" value="${book.id}">
                        <button type="submit" class="borrow-button">Borrow</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <div class="out-of-stock">Out of stock</div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>


