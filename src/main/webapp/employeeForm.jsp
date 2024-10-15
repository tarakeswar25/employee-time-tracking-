<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
    
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="worktrack.Allprojects" %>
<%@ page import="worktrack.AllRoles" %>
<%@ page import="worktrack.AllCategory" %>



<!DOCTYPE html>
<html>
<head>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .form-container {
            max-width: 600px;
            margin: auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 10px;
            background-color: #f9f9f9;
        }
        .form-container h2 {
            text-align: center;
            margin-bottom: 20px;
        }
        .form-container label {
            display: block;
            margin-bottom: 5px;
        }
        .form-container input, .form-container select, .form-container textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .form-container button {
            width: 100%;
            padding: 10px;
            border: none;
            border-radius: 5px;
            background-color: #4CAF50;
            color: white;
            font-size: 16px;
        }
        .form-container button:hover {
            background-color: #45a049;
        }
    </style>
    <meta charset="ISO-8859-1">
    <title>Employee Work Log</title>
</head>
<body>
    <h1>Welcome to the Admin Page</h1>
    <div class="form-container">
        <h2>Employee Work Log</h2>
        <form action="submitWorkLog" method="post">
            <input type="text" name="name" value="${sessionScope.userid}" hidden>
            <label for="name1">Employee id:</label>
            <input type="text" id="nam" name="userid" value="${sessionScope.userid}" required disabled>
            <label for="employeeName">Employee Name:</label>
            <input type="text" id="name" name="employeeName" value="${sessionScope.employeeName}" required disabled>
            <input type="text" id="employeeName" name="employeeName" value="${sessionScope.userid}" required hidden>
            <label for="role">Role:</label>
            <input type="text" id="role" value="${sessionScope.role}" required disabled>
            <input type="text" id="role" name="role" value="${sessionScope.role}" required hidden>
            <label for="projectName">Project Name:</label>
            <input type="text" value="${sessionScope.projectName}" disabled>
            <input type="text" name="projectName" value="${sessionScope.projectName}" hidden>
            <label for="date">Date:</label>
            <input type="date" id="date" name="dat" value="<%= new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>" min="<%= new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>" required disabled>
            <input type="date" id="date" name="date" value="<%= new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>" min="<%= new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>" required hidden>
            <label for="startTime">Start Time:</label>
            <input type="text" id="startTime" name="startTime" placeholder="10:10 AM" required>
            <label for="endTime">End Time:</label>
            <input type="text" id="endTime" name="endTime" placeholder="06:00 PM" required>
            <label for="taskCategory">Task Category:</label>
            <select name="taskCategory">
        <option value="" disabled selected>Select category</option>
        <c:forEach var="project" items="${session.getAttribute("CategoryList")}">
            
             <% 
             List<AllCategory> CategoryList = (List<AllCategory>) session.getAttribute("CategoryList");

        if (CategoryList != null) {
            for (AllCategory project : CategoryList) { 
        %>
                <option value="<%= project.getcategory() %>"><%= project.getcategory() %></option>
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
           


    
    


<!--  the below code is for all roles 
<select name="project">
        <option value="" disabled selected>Select Roles</option>
        <c:forEach var="project" items="${session.getAttribute("RolesList")}">
            
             <.% 
             List<AllRoles> RolesList = (List<AllRoles>) session.getAttribute("RolesList");

        if (RolesList != null) {
            for (AllRoles project : RolesList) { 
        %>
                <option value="<.%= project.getRole() %>"><.%= project.getRole() %></option>
        <.% 
            }
        } else {
        %>
            <option value="">No projects available</option>
        <.% 
        } 
        %>
        </c:forEach>
    </select> -->
    
    
    
    
    
    
    <!-- 
    <select name="project">
        <option value="" disabled selected>Select Role</option>
        <c:forEach var="project" items="${session.getAttribute("RolesList")}">
            
             <'% 
             List<AllRoles> projectsList = (List<AllRoles>) session.getAttribute("RolesList");

        if (projectsList != null) {
            for (Allprojects project : projectsList) { 
        %>
                <option value="<.%= project.getProjectName() %>"><.%= project.getProjectName() %></option>
        <'% 
            }
        } else {
        %>
            <option value="">No projects available</option>
        <'% 
        } 
        %>
        </c:forEach>
    </select> -->

<!--  the below code is for showing the all projects in the database

<select name="project">
        <option value="" disabled selected>Select Project</option>
        <c:forEach var="project" items="${session.getAttribute("projectsList")}">
            
             <.% 
             List<Allprojects> projectsList = (List<Allprojects>) session.getAttribute("projectsList");

        if (projectsList != null) {
            for (Allprojects project : projectsList) { 
        %>
                <option value="<.%= project.getProjectName() %>"><.%= project.getProjectName() %></option>
        <.% 
            }
        } else {
        %>
            <option value="">No projects available</option>
        <.% 
        } 
        %>
        </c:forEach>
    </select> -->

    <!-- Debugging session data 
    <.%
    List<Allprojects> projectsList = (List<Allprojects>) session.getAttribute("projectsList");
    if (projectsList != null) {
        for (Allprojects project : projectsList) {
            out.println(project.getProjectName() + "<br>");
        }
    } else {
        out.println("projectsList is null");
    }
    %>
   
-->

            <label for="description">Description:</label>
            <textarea id="description" name="description" rows="4" required></textarea>
            <button type="submit">Submit</button>
        </form>
    </div>
</body>
</html>
