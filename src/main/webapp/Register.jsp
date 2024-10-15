<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="worktrack.UserDAO" %>
<%@ page import="worktrack.Allprojects" %>
<%@ page import="worktrack.AllRoles" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Details</title>
     <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            background-color: #fff;
            padding: 50px;
            border-radius: 8px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 600px;
            animation: fadeIn 1s ease-in-out;
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(-20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        h1 {
            margin-bottom: 20px;
            text-align: center;
            color: #333;
        }

        .form-group {
            margin-bottom: 15px;
            position: relative;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #333;
            transition: all 0.3s ease;
        }

        .form-group input,
        .form-group select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            transition: all 0.3s ease;
        }

        .form-group input:focus,
        .form-group select:focus {
            border-color: #007bff;
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
        }

        .btn {
            display: inline-block;
            padding: 10px 20px;
            font-size: 16px;
            color: #fff;
            background-color: #007bff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-align: center;
            text-decoration: none;
            transition: background-color 0.3s ease, transform 0.3s ease;
        }

        .btn:hover {
            background-color: #0056b3;
        }

        .btn:active {
            background-color: #004494;
            transform: scale(0.98);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            animation: slideIn 1s ease-in-out;
        }

        @keyframes slideIn {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        table, th, td {
            border: 1px solid #ddd;
        }

        th, td {
            padding: 12px;
            text-align: left;
        }

        th {
            background-color: #007bff;
            color: white;
        }
    </style>

</head>
<body>
    <div class="container">
        <h1>User Details</h1>
        <form action="UpdateUserServlet" method="post">
            <div class="form-group">
                <label for="userid">User ID: *</label>
                <input type="text" id="userid" name="userid" required>
            </div>
            <div class="form-group">
                <label for="userName">User Name: *</label>
                <input type="text" id="userName" name="userName" required>
            </div>
            <div class="form-group">
                <label for="project">Project:</label>
                <select name="project" id="project">
                    <option value="">--Select Project--</option>
                   <c:forEach var="project" items="${session.getAttribute("projectsList")}">
            
             <% 
             List<Allprojects> projectsList = (List<Allprojects>) session.getAttribute("projectsList");

        if (projectsList != null) {
            for (Allprojects project : projectsList) { 
        %>
                <option value="<%= project.getProjectName() %>"><%= project.getProjectName() %></option>
        <% 
            }
        } else {
        %>
            <option value="">No projects available</option>
        <% 
        } 
        %>
        </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="role">Role:</label>
                <select name="role" id="role">
                    <option value="">--Select Role--</option>
                    <c:forEach var="project" items="${session.getAttribute("RolesList")}">
            
             <% 
             List<AllRoles> RolesList = (List<AllRoles>) session.getAttribute("RolesList");

        if (RolesList != null) {
            for (AllRoles project : RolesList) { 
        %>
                <option value="<%= project.getRole() %>"><%= project.getRole() %></option>
        <% 
            }
        } else {
        %>
            <option value="">No projects available</option>
        <% 
        } 
        %>
        </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="type">Type:</label>
                <select id="type" name="type" required>
                    <option value="user">USER</option>
                    <option value="associate">Associate</option>
                </select>
            </div>
            <button type="submit" class="btn">Submit</button>
        </form>
    </div>
</body>
</html>
