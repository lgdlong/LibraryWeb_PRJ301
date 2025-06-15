<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar">
    <!-- Menu -->
    <div class="navbar">
        <a href="GuestHomeController"class="nav-link">Home</a>
    </div>

    <!-- Only Search Input -->
     <div class="nav-center">
        <form class="search-bar" action="SearchBookController" method="get">
            <input type="text" name="keyword" placeholder="Search..."
                value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : "" %>" />
            <button type="submit">Search</button>
        </form>
     </div>

    <div class="nav-right">
        <a href="${pageContext.request.contextPath}/Login.jsp" class="nav-link">Login</a>
        <a href="${pageContext.request.contextPath}/Register.jsp" class="nav-link register">Register</a>
    </div>

</div>



