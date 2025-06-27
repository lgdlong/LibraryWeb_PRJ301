<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entity.User" %>

<%
  User us = (User) session.getAttribute("LOGIN_USER");
%>


<div class="navbar">

  <div class="navbar">
    <a href="GuestHomeController" class="nav-link">Home</a>
  </div>

  <div class="nav-center">
    <form class="search-bar" action="SearchBookController" method="get">
      <input type="text" name="keyword" placeholder="Search..."
             value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : "" %>"/>
      <button type="submit">Search</button>
    </form>
  </div>

  <div class="nav-right">
    <%
      if (us == null) {
    %>

    <a href="${pageContext.request.contextPath}/Login.jsp" class="nav-link">Login</a>
    <a href="${pageContext.request.contextPath}/Register.jsp" class="nav-link register">Register</a>
    <%
    } else {
    %>
    <div class="user-nav">
      <span class="welcome-text"> Welcome, <%= us.getName() %></span>
      <a href="<%= request.getContextPath() %>/profile" class="nav-link">Profile</a>
      <a href="${pageContext.request.contextPath}/ViewBorrowBookController" class="nav-link">Borrow Book</a>
      <a href="LogoutController" class="nav-link logout-btn">Logout</a>
    </div>
    <%
      }
    %>
  </div>
</div>



