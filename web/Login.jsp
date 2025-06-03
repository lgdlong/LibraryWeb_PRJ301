<%-- 
    Document   : Login
    Created on : Jun 1, 2025, 9:43:21 PM
    Author     : nguye
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Login In</h1>
       
        <form action="LoginController" method="POST">
            <p>Email:<input type="text" name="email" required=""/></p>
            </br><p>Password:<input type="password" name="password" required=""/></p>
            </br><p><input type="submit"  value="Login"></p>  
        </form>
        <%
         String s = (String) request.getAttribute("ERROR");
         if(s==null){
              s="";
            }
        %>
        <p style="color: red"><%= s %></p> 
    </body>
</html>
