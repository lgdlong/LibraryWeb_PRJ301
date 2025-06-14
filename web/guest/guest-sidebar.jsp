<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar">
    <!-- Menu -->
    <div class="navbar">
        <a href="NewBookController"class="nav-link">Home</a>
    </div>

    <!-- Only Search Input -->
     <div class="nav-center">
        <form class="search-bar" action="SearchBookController" method="get">
            <input type="text" name="keyword" placeholder="Search..."/>
            <button type="submit">Search</button>
        </form>
     </div>

    <div class="nav-right">
        <a href="Login.jsp" class="nav-link">Login</a>
        <a href="Register.jsp"class="nav-link register">Register</a>
    </div>
    
</div>



