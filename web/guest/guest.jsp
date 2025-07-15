<%@page import="java.util.ArrayList" %>
<%@page import="entity.Book" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="main-content">

    <%--Show New Book--%>
    <c:if test="${sessionScope.isSearch == null || not sessionScope.isSearch}">
            <h2>New Books</h2>

            <c:choose>
                <c:when test="${sessionScope.newBooks == null || empty sessionScope.newBooks}">
                    <p class="no-books">No new books published this year.</p>
                </c:when>
                <c:otherwise>
                    <div class="cards-container">
                        <c:forEach var="b" items="${sessionScope.newBooks}">
                            <div class="book-card">
                                <a href="${pageContext.request.contextPath}/view?id=${b.id}" class="book-link">
                                    <img src="${b.coverUrl}" alt="Book Cover" class="book-image">
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
                                                <input type="hidden" name="currentPage" value="${pageContext.request.contextPath}/home">
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
        </c:if>

        <%-- Show All Available Books --%>
        <h2>Books</h2>
        <c:set var="availableBooks" value="${sessionScope.availableBooks}" />

        <c:if test="${availableBooks == null || empty availableBooks}">
            <p class="no-books">No books available at the moment.</p>
        </c:if>

        <c:if test="${availableBooks != null && not empty availableBooks}">
            <div class="cards-container">
                <c:forEach var="b" items="${availableBooks}">
                    <div class="book-card">
                        <a href="${pageContext.request.contextPath}/view?id=${b.id}" class="book-link">
                            <img src="${b.coverUrl}" alt="Book Cover" class="book-image">
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
                                        <input type="hidden" name="currentPage" value="${pageContext.request.contextPath}/home">
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
        </c:if>

    </div>

