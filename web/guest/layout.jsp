<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Library Guest Page</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: white;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }

        .content {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            margin-top: 20px;
        }
    </style>
</head>
<body>

<!-- Thanh điều hướng -->
<jsp:include page="/guest/guest-sidebar.jsp" />

<!-- Nội dung chính -->
<div class="container">
    <%
        String contentPage = (String) request.getAttribute("contentPage");
        if (contentPage == null || contentPage.trim().equals("")) {
            contentPage = "/guest/guest.jsp";
        }
        request.setAttribute("contentPage", contentPage); // Gán lại để JSTL dùng được
    %>
    <c:import url="${contentPage}" />
</div>

</body>
</html>
