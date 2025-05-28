<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <c:choose>
      <c:when test="${not empty contentPage}">
        <c:import url="${contentPage}"/>
      </c:when>
      <c:otherwise>
        <c:import url="/admin/error.jsp"/>
      </c:otherwise>
    </c:choose>
  </div>
</div>
</body>
</html>
