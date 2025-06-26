<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="navbar">
    <!-- Menu -->
    <c:choose>
        <c:when test="${not empty sessionScope.LOGIN_USER}">
            <a href="${pageContext.request.contextPath}/user-layout.jsp" class="nav-link">Home</a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/guest/layout.jsp" class="nav-link">Home</a>
        </c:otherwise>
    </c:choose>
>

    <!-- Only Search Input -->
     <div class="nav-center">
        <form class="search-bar" action="${pageContext.request.contextPath}/SearchBookController" method="get">
            <input type="text" name="keyword" placeholder="Search..."
                value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : "" %>" />
            <button type="submit">Search</button>
        </form>
     </div>

    <div class="nav-right">
        <a href="${pageContext.request.contextPath}/history.jsp" class="nav-link register">History</a>
        <a href="${pageContext.request.contextPath}/borrow-book.jsp" class="nav-link register">My Borrow Book</a>
        <a href="LogoutController" class="nav-link">Logout</a>
    </div>

</div>



