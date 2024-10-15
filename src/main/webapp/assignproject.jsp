<%@ page import="java.util.*, worktrack.UserDAO, worktrack.UserDetail" %>
<%@ page import="worktrack.Allprojects" %>
<%@ page import="worktrack.AllRoles" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Assign Project</title>
     <style>
    body {
        font-family: Arial, sans-serif;
        background-color: #e2eafc; /* Soft blue background */
        margin: 0;
        padding: 0;
    }
    .container {
        max-width: 600px;
        margin: 20px auto;
        padding: 20px;
        background: #ffffff; /* White background for the form */
        border: 1px solid #ddd;
        border-radius: 8px;
        box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
    }
    h1 {
        color: #333;
        text-align: center;
        margin-bottom: 20px;
        font-size: 24px;
    }
    label {
        font-weight: bold;
        margin-bottom: 5px;
        display: block;
        color: #333;
    }
    select, input[type="submit"] {
        margin: 10px 0;
        padding: 10px;
        width: 100%;
        border: 1px solid #ddd;
        border-radius: 4px;
        box-sizing: border-box;
    }
    select:focus, input[type="submit"]:hover {
        border-color: #007bff;
        outline: none;
    }
    input[type="submit"] {
        background-color: #007bff; /* Bright blue */
        color: #fff;
        font-size: 16px;
        cursor: pointer;
        border: none;
        transition: background-color 0.3s ease;
    }
    input[type="submit"]:hover {
        background-color: #0056b3; /* Darker blue */
    }
    option {
        padding: 10px;
    }
    .form-group {
        margin-bottom: 15px;
    }

    /* Responsive Styles */
    @media (max-width: 768px) {
        .container {
            padding: 15px;
        }
        h1 {
            font-size: 20px;
        }
    }
    @media (max-width: 480px) {
        .container {
            padding: 10px;
            margin: 10px;
        }
        h1 {
            font-size: 18px;
        }
        select, input[type="submit"] {
            padding: 8px;
        }
    }
</style>
</head>
<body>
    <div class="container">
        <h1>Assign Project to User</h1>

        <form action="assignProjectServlet" method="post">
            <div class="form-group">
                <label for="user">Select User:</label>
                <select name="userid" id="user" required>
                    <option value="">--Select User--</option>
                    <%
                        UserDAO userDao = new UserDAO();
                        List<UserDetail> users = userDao.getUsers();
                        for (UserDetail user : users) {
                    %>
                    <option value="<%= user.getUserId() %>"><%= user.getUserName() %>- ID --> <%= user.getUserId() %></option>
                    <%
                        }
                    %>
                </select>
            </div>

            <div class="form-group">
                <label for="projectName">Select Project:</label>
                <select name="projectName" id="projectName" required>
                <option value="" disabled selected>Select Project</option>
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
                <label for="role">Select Role:</label>
                <select name="role" id="role" required>
                <option value="" disabled selected>Select Roles</option>
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

            <input type="submit" value="Assign Project">
        </form>
    </div>
</body>
</html>
