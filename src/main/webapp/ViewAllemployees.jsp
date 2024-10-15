<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="worktrack.UserDAO" %>
<%@ page import="worktrack.Staff" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Transactions</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 20px;
        }
        .container {
            background-color: #fff;
            padding: 50px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #007bff;
            color: white;
        }
    </style>
    <script>
        function filterByUserId() {
            document.getElementById("userIdForm").submit();
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>Total Users</h1>
        <table>
            <thead>
                <tr>
                    <th>User ID</th>
                    <th>Employee Name</th>
                    <th>Role</th>
                    <th>Project Name</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody>
                <%
                    String selectedUserId = request.getParameter("userId");
                    UserDAO userDAO = new UserDAO();
                    List<Staff> staffList = null;
                    try {
                        staffList = userDAO.getStaff(selectedUserId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (staffList != null) {
                        for (Staff staff : staffList) {
                %>
                <tr>
                    <form action="deletestaff" method="post">
                        <td><%= staff.getUserId() %></td>
                        <td><%= staff.getEmployeeName() %></td>
                        <td><%= staff.getRole() %></td>
                        <td><%= staff.getProjectName() %></td>
                        <input type='hidden' name='userid' value="<%= staff.getUserId() %>">
                        <input type='hidden' name='pos' value="<%= staff.getPos1() %>">
                        <td><input type="submit" value="Delete"></td>
                    </form>
                </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
