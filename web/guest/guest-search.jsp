<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="main-content">
    <h2>Search Books</h2>

    <c:set var="books" value="${results}" />

    <c:choose>
        <c:when test="${books == null || empty books}">
            <p class="no-books">No books found for your search.</p>
        </c:when>
        <c:otherwise>
            <div class="cards-container">
                <c:forEach var="b" items="${books}">
                    <div class="book-card">
                        <a href="${pageContext.request.contextPath}/view?id=${b.id}" class="book-link">
                            <img class="book-image" src="${b.coverUrl}" alt="No Image">
                        </a>
                        <div class="book-meta">
                            <p class="book-title">${b.title}</p>
                            <p class="book-author">${b.author}</p>
                        </div>
                        <div class="book-action">
                            <c:choose>
                                <c:when test="${b.availableCopies > 0}">
                                    <form action="${pageContext.request.contextPath}/BorrowController" method="post" class="borrow-form">
                                        <input type="hidden" name="bookId" value="${b.id}">
                                        <button type="submit" class="borrow-button">Borrow</button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <p class="out-of-stock">Out of stock</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>

