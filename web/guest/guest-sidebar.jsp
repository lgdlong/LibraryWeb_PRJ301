<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="navbar">
  <div class="navbar">
    <a href="${pageContext.request.contextPath}/home" class="nav-link">Home</a>
  </div>

  <div class="nav-center">
    <form class="search-bar" action="${pageContext.request.contextPath}/search" method="get">
      <input type="text" name="keyword" placeholder="Search..."
             value="${param.keyword != null ? param.keyword : ''}" />
      <button type="submit">Search</button>
    </form>
  </div>

  <div class="nav-right">
    <c:choose>
      <c:when test="${empty sessionScope.LOGIN_USER}">
        <a href="${pageContext.request.contextPath}/Login.jsp" class="nav-link">Login</a>
        <a href="${pageContext.request.contextPath}/Register.jsp" class="nav-link register">Register</a>
      </c:when>
      <c:otherwise>
        <c:set var="user" value="${sessionScope.LOGIN_USER}" />
        <div class="user-nav">
          <span class="welcome-text">Welcome, ${user.name}</span>
          <a href="${pageContext.request.contextPath}/profile" class="nav-link">Profile</a>
          <a href="${pageContext.request.contextPath}/borrow/current" class="nav-link">My Library</a>
          <a href="${pageContext.request.contextPath}/LogoutController" class="nav-link logout-btn">Logout</a>
        </div>
      </c:otherwise>
    </c:choose>
  </div>
</div>
