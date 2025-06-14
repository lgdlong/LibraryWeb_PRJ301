<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar">
    <!-- Menu -->
    <div class="nav-group">
        <a href="NewBookController">Home</a>
        <a href="#">Log In</a>
        <a href="#">Register</a>
    </div>

    <!-- Only Search Input -->
    <form class="search-bar" action="SearchBookController" method="get">
        <input type="text" name="keyword" placeholder="Search..."/>
        <button type="submit">Search</button>
    </form>
</div>

<style>
    .navbar {
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 12px 30px;
        background-color: #2c3e50;
        font-family: Arial, sans-serif;
    }

    .nav-group {
        display: flex;
        align-items: center;
        gap: 20px;
    }

    .nav-group a {
        color: white;
        text-decoration: none;
        font-weight: bold;
    }

    .nav-group a:hover {
        text-decoration: underline;
    }

    .search-bar {
        display: flex;
        align-items: center;
    }

    .search-bar input[type="text"] {
        width: 220px;
        padding: 6px 10px;
        font-size: 14px;
        border: 1px solid #ccc;
        border-radius: 4px 0 0 4px;
        outline: none;
    }

    .search-bar button {
        padding: 6px 12px;
        font-size: 14px;
        border: 1px solid #ccc;
        border-left: none;
        background-color: white;
        color: black;
        font-weight: bold;
        border-radius: 0 4px 4px 0;
        cursor: pointer;
    }
</style>


