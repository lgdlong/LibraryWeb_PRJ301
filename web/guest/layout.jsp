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
<jsp:include page="/guest/guest-sidebar.jsp" />

<!-- Main Contain -->
<div class="main-contain">
    <%
        String contentPage = (String) session.getAttribute("contentPage");
        if (contentPage == null || contentPage.trim().equals("")) {
            contentPage = "/guest/guest.jsp";
        }
    %>
    <c:import url="${contentPage}" />
</div>

</body>
</html>
