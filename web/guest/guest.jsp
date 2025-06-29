<%@page import="java.util.ArrayList" %>
<%@page import="entity.Book" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="main-content">

    <%--Show New Book--%>
    <%
      Boolean isSearch = (Boolean) session.getAttribute("isSearch");
      ArrayList<Book> newBooks = (ArrayList<Book>) session.getAttribute("newBooks");
      if (isSearch == null || !isSearch) {
        if (newBooks == null || newBooks.isEmpty()) {
    %>
    <%--                    <h2>New Books</h2>--%>
    <%--                    <p class="no-books">No new books published this year.</p>--%>
    <%
    } else {
    %>
    <h2>New Books</h2>
    <div class="cards-container">
        <% for (Book b : newBooks) { %>
        <div class="book-card">
            <a href="${pageContext.request.contextPath}/ViewBookController?id=<%= b.getId() %>" class="book-link">
                <img src="<%= b.getCoverUrl() %>" alt="Book Cover" class="book-image">
            </a>
            <div class="book-meta">
                <p class="book-title"><%= b.getTitle() %>
                </p>
                <p class="book-author"><%= b.getAuthor() %>
                </p>
            </div>
            <form action="${pageContext.request.contextPath}/BorrowController" method="post" class="borrow-form">
                <input type="hidden" name="bookId" value="<%= b.getId() %>">
                <input type="hidden" name="currentPage" value="GuestHomeController">
                <button type="submit" class="borrow-button">Borrow</button>
            </form>
        </div>
        <% } %>
    </div>
    <%
        }
      }
    %>

    <%--Show Book--%>
    <h2>Books</h2>
        <c:set var="availableBooks" value="${sessionScope.availableBooks}"/>
        <c:if test="${availableBooks == null || empty availableBooks}">
            <p class="no-books">No books available at the moment.</p>
        </c:if>

        <div class="cards-container">
            <c:if test="${availableBooks != null && not empty availableBooks}">
                <c:forEach var="b" items="${availableBooks}">
                    <div class="book-card">
                        <a href="${pageContext.request.contextPath}/ViewBookController?id=${b.id}" class="book-link">
                            <img src="${b.coverUrl}" alt="Book Cover" class="book-image">
                        </a>

                        <div class="book-meta">
                            <p class="book-title">${b.title}</p>
                            <p class="book-author">${b.author}</p>
                        </div>
                        <c:if test="${b.availableCopies > 0}">
                            <form action="${pageContext.request.contextPath}/BorrowController" method="post" class="borrow-form">
                                <input type="hidden" name="bookId" value="${b.id}">
                                <input type="hidden" name="currentPage" value="GuestHomeController">
                                <button type="submit" class="borrow-button">Borrow</button>
                            </form>
                        </c:if>
                    </div>
                </c:forEach>
            </c:if>
        </div>

    </div>


