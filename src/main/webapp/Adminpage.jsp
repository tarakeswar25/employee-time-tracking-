
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee Management System</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            height: 100vh;
            background-color: #f4f4f4;
            overflow: hidden;
        }
        .sidebar {
            width: 100%;
            max-width: 250px;
            background-color: #343a40;
            display: flex;
            flex-direction: column;
            padding-top: 20px;
            transition: width 0.3s ease;
            position: fixed;
            height: 100%;
        }
        .sidebar .button {
            color: #fff;
            background-color: #007bff;
            border: none;
            text-align: center;
            padding: 12px;
            margin: 10px;
            border-radius: 4px;
            text-decoration: none;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.3s ease;
        }
        .sidebar .button:hover {
            background-color: #0056b3;
            transform: scale(1.05);
        }
        .content {
            margin-left: 0;
            margin-left: max(250px, 0px);
            flex: 1;
            display: flex;
            flex-direction: column;
            transition: margin-left 0.3s ease;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px;
            background-color: #f8f9fa;
            border-bottom: 1px solid #dee2e6;
        }
        .header .button {
            color: #fff;
            background-color: #dc3545;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            text-decoration: none;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .header .button:hover {
            background-color: #c82333;
        }
        .iframe-container {
            flex: 1;
            padding: 10px;
            overflow: hidden;
        }
        .iframe-container iframe {
            width: 100%;
            height: calc(100vh - 60px); /* Adjust height for header */
            border: none;
            transition: height 0.3s ease;
        }
        /* Responsive Design */
        @media (max-width: 768px) {
            .sidebar {
                width: 100%;
                max-width: none;
                position: static;
                height: auto;
                flex-direction: row;
                overflow-x: auto;
            }
            .sidebar .button {
                margin: 5px;
                flex: 1;
                text-align: center;
                border-radius: 0;
                padding: 8px;
            }
            .content {
                margin-left: 0;
            }
            .header .button {
                padding: 8px 16px;
            }
        }
    </style>
</head>
<body>
    <div class="sidebar">
        <a href="AdminDashboard.jsp" target="contentFrame" class="button">Dashboard</a>
        <a href="Register.jsp" target="contentFrame" class="button">Register Employee</a>
        <a href="Dataentry.jsp" target="contentFrame" class="button">Add Data</a>
        <a href="viewEmployees.jsp" target="contentFrame" class="button">View Submissions</a>
        <a href="insertprojects.jsp" target="contentFrame" class="button">New Projects</a>
        <a href="assignproject.jsp" target="contentFrame" class="button">Assign Projects</a>
            <a href="ViewAllemployees.jsp" target="contentFrame" class="button">View Employees</a>
    </div>
    <div class="content">
        <div class="header">
            <span>Employee Management System</span>
            <a href="homepage.jsp" class="button">Logout</a>
        </div>
        <div class="iframe-container">
            <iframe name="contentFrame" src="AdminDashboard.jsp"></iframe>
        </div>
    </div>
</body>
</html>
