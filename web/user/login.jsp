<%-- 
    Document   : login
    Created on : Jun 5, 2025, 2:27:20 PM
    Author     : Dien Sanh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="UserLoginController" method="post">
            <p>Gmail<input type="text" name="gmail" required=""/></p>
            <p>Password<<input type="text" name="password1" required="/"></p>
            <p><input type="submit" value="login"/></p>
        </form>
    </body>
</html>
