<%-- 
    Document   : Register
    Created on : Jun 2, 2025, 1:42:26 AM
    Author     : nguye
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import= "dto.UserError" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
       <h1>Register</h1>
         <% 
               UserError userError = (UserError) request.getAttribute("USER_ERROR");
                 if (userError == null) {
                userError = new UserError(); // Tránh null khi lần đầu vào hoặc không có lỗi
            }
           %>
         <form action="RegisterController" method="post">
             
              FullName <input type="text" name="fullname" required="" />
            </br><p style="color:red" ><%= userError.getFullnameError() %> </p>
            </br> Email <input type="text" name="email" required="" />
            </br><p style="color:red" ><%= userError.getEmailError() %> </p>
            </br> Password <input type="password" name="password" required="" />
            </br><p style="color:red" ><%= userError.getPasswordError() %> </p>
            </br> Confirm <input type="password" name="confirm" required="" />
            </br><p style="color:red" ><%= userError.getConfirmError() %> </p>
            <br><input type="submit" name="action" value="Register" /> 
           <input type="reset" value="reset" />
        </form>
       
    </body>
</html>
