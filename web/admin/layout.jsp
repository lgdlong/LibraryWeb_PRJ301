<%--admin/layout.jsp--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="/common/header.jsp"/>
<body class="bg-light">
<div class="d-flex flex-column flex-lg-row">
  <!-- Sidebar (hidden on mobile, shown on larger screens) -->
  <div class="d-none d-lg-block">
    <jsp:include page="/admin/admin-sidebar.jsp"/>
  </div>

  <!-- Mobile Top Navigation -->
  <nav class="navbar navbar-expand-lg navbar-light bg-white border-bottom shadow-sm d-lg-none sticky-top">
    <div class="container-fluid px-3">
      <a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}/admin">
        <i class="bi bi-book-half text-primary fs-4 me-2"></i>
        <span class="fw-bold">Library Admin</span>
      </a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mobileNavbar">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="mobileNavbar">
        <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/admin">
              <i class="bi bi-speedometer2 me-1"></i> Dashboard
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/admin/books">
              <i class="bi bi-book me-1"></i> Books
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/admin/users">
              <i class="bi bi-people me-1"></i> Users
            </a>
          </li>
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
              <i class="bi bi-three-dots me-1"></i> More
            </a>
            <ul class="dropdown-menu dropdown-menu-end">
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/requests">Requests</a></li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/borrow-records">Borrowings</a>
              </li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/fines">Fines</a></li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/config">System Config</a></li>
              <li>
                <hr class="dropdown-divider">
              </li>
              <li><a class="dropdown-item text-danger"
                     href="${pageContext.request.contextPath}/LogoutController">Logout</a></li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>

  <!-- Main content area -->
  <div class="flex-grow-1 p-3 p-lg-4" style="min-height: 100vh;">
    <div class="container-fluid px-0">
      <c:choose>
        <c:when test="${not empty contentPage}">
          <c:import url="${contentPage}"/>
        </c:when>
        <c:otherwise>
          <c:import url="/admin/error.jsp"/>
        </c:otherwise>
      </c:choose>
    </div>

    <!-- Footer -->
    <footer class="mt-5 pt-4 pb-2 text-center text-muted small">
      <p>© ${pageContext.servletContext.servletContextName} Library Management
        System ${java.time.Year.now().getValue()}</p>
    </footer>
  </div>
</div>
</body>
</html>
