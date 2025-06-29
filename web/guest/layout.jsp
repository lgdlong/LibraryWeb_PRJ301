<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Library Guest Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/GuestLayout.css">
</head>
<body>

<!-- Sidebar -->
<c:choose>
    <c:when test="${empty sidebarPage}">
        <c:set var="sidebarPage" value="/guest/guest-sidebar.jsp" />
    </c:when>
</c:choose>
<c:import url="${sidebarPage}" />

<!-- Main Contain -->
<div class="main-contain">
    <c:choose>
        <c:when test="${empty contentPage}">
            <c:set var="contentPage" value="/guest/guest.jsp" />
        </c:when>
    </c:choose>
    <c:import url="${contentPage}" />
</div>

</body>
</html>
