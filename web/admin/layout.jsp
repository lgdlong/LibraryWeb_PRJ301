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
    <%--    <jsp:include page="${contentPage}"/>--%>
    <c:import url="${contentPage}"/>
  </div>
</div>
</body>
</html>

