<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <title>Library Guest Page</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/GuestLayout.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/my-library-sidebar.css">
  <!-- Bootstrap 5 CSS -->
  <%--  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet"--%>
  <%--        integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" crossorigin="anonymous">--%>
</head>
<body>

<!-- Sidebar -->
<c:choose>
  <c:when test="${empty sidebarPage}">
    <c:set var="sidebarPage" value="/guest/guest-sidebar.jsp"/>
  </c:when>
</c:choose>
<c:import url="${sidebarPage}"/>

<!-- Main Contain -->
<div class="main-contain">
  <c:choose>
    <c:when test="${empty contentPage}">
      <c:set var="contentPage" value="/guest/guest.jsp"/>
    </c:when>
  </c:choose>
  <c:import url="${contentPage}"/>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
