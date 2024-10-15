<%@ page import="java.sql.*" %>
<%@ page import="javax.servlet.*, javax.servlet.http.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 80%;
            margin: auto;
            overflow: hidden;
        }
        form {
            background: #fff;
            padding: 20px;
            margin-bottom: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        input[type="text"], input[type="submit"] {
            padding: 10px;
            margin: 5px 0;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        input[type="submit"] {
            background-color: #007BFF;
            color: #fff;
            border: none;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Admin Page</h1>

        <!-- Form to Insert Project Name -->
        <form action="insertProjectServlet" method="post">
            <h2>Insert Project</h2>
            <label for="projectName">Project Name:</label>
            <input type="text" id="projectName" name="projectName" required>
            <input type="submit" value="Insert Project">
        </form>

        <!-- Form to Insert Role -->
        <form action="insertRoleServlet" method="post">
            <h2>Insert Role</h2>
            <label for="roleName">Role Name:</label>
            <input type="text" id="roleName" name="roleName" required>
            <input type="submit" value="Insert Role">
        </form>

        <!-- Form to Insert Category -->
        <form action="insertCategoryServlet" method="post">
            <h2>Insert Category</h2>
            <label for="categoryName">Category Name:</label>
            <input type="text" id="categoryName" name="categoryName" required>
            <input type="submit" value="Insert Category">
        </form>
    </div>
</body>
</html>
