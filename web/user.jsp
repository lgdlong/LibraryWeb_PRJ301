<%-- 
    Document   : user.jsp
    Created on : Jun 2, 2025, 1:05:31 AM
    Author     : nguye
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.User" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <%
      User us = (User) session.getAttribute("LOGIN_USER");
      if(us==null){
          response.sendRedirect("index.html");
          return;
        }
        else{
        
    %>
    <body>
        <h1>Hello World!</h1>
     <a href="LogoutController">Logout</a>
     </body>
     
     <%
         }
     %>
</html>
