<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
    Document   : layout
    Created on : May 28, 2025, 9:12:13 AM
    Author     : ADMIN
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="/common/header.jsp"/>
<body>
<div class="d-flex">
  <!-- Sidebar -->
  <jsp:include page="/admin/admin-sidebar.jsp"/>

  <!-- Main content area -->
  <div class="flex-grow-1 p-4" style="min-height: 100vh;">
    <%
        String contentPage = (String) request.getAttribute("contentPage");
        if (contentPage == null || contentPage.trim().isEmpty()) {
            contentPage = "/admin/dashboard.jsp";
        }

        // Validate the content page path to prevent directory traversal
        if (!contentPage.startsWith("/admin/") || contentPage.contains("..")) {
            contentPage = "/admin/error.jsp";
        }
        request.setAttribute("validatedContentPage", contentPage);
    %>
    <c:import url="${contentPage}"/>
  </div>
</div>
</body>
</html>

