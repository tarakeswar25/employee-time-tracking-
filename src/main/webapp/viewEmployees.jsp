<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="worktrack.UserDAO" %>
<%@ page import="worktrack.DataRecord" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Transactions</title>
    <style>
        /* CSS styles */
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
        <h1>View Total Submissions</h1>
        <form id="userIdForm" method="get" action="viewEmployees.jsp">
            <label for="userId">Filter by UserID: </label>
            <select name="userId" id="userId" onchange="filterByUserId()">
                <option value="">Select</option>
                <option value="">All Users</option>
                <%
                    UserDAO userDAO = new UserDAO();
                    List<String> userIds = userDAO.getDistinctUserIds();
                    for (String userId : userIds) {
                %>
                <option value="<%= userId %>"><%= userId %></option>
                <%
                    }
                %>
            </select>
        </form>
        <table>
            <thead>
                <tr>
                    <th>User ID</th>
                    <th>Employee Name</th>
                    <th>Role</th>
                    <th>Project Name</th>
                    <th>Date</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Task Category</th>
                    <th>Description</th>
                    <th>Duration</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody>
                <%
                    String selectedUserId = request.getParameter("userId");
                    List<DataRecord> records = userDAO.getDataRecords(selectedUserId);
                    for (DataRecord record : records) {
                %>
                <tr>
                    <form action="deleteSubmission" method="post">
                        <td><%= record.getUserId() %></td>
                        <td><%= record.getEmployeeName() %></td>
                        <td><%= record.getRole() %></td>
                        <td><%= record.getProjectName() %></td>
                        <td><%= record.getDate() %></td>
                        <td><%= record.getStartTime() %></td>
                        <td><%= record.getEndTime() %></td>
                        <td><%= record.getTaskCategory() %></td>
                        <td><%= record.getDescription() %></td>
                        <td><%= record.getDuration() %></td>
                        <td hidden><%= record.getPos() %></td>
                        <input type='text' name='userid' value="<%= record.getUserId() %>" hidden>
                        <input type='text' name='pos' value="<%= record.getPos() %>" hidden>
                        <td><input type="submit" value="delete"></td>
                    </form>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
