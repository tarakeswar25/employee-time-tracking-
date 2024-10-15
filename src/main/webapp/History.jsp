<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, worktrack.Transaction, worktrack.TransactionDAO,java.text.SimpleDateFormat, java.text.ParseException" %>
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
</head>
<body>
    <div class="container">
        <h1>View Transactions</h1>
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
                    <th>Duration (HR)</th>
                </tr>
            </thead>
            <tbody>
                <%
                    String userId = request.getParameter("userid");
                    if (userId != null && !userId.isEmpty()) {
                        TransactionDAO dao = new TransactionDAO();
                        List<Transaction> transactions = dao.getTransactionsByUserId(userId);

                        for (Transaction transaction : transactions) {
                            // Convert start and end times
                            float st = transaction.getStartTime();
                            float en = transaction.getEndTime();
                            java.text.DecimalFormat df = new java.text.DecimalFormat("00.00");
                            String time1 = df.format(st).replace(".", ":");
                            String time2 = df.format(en).replace(".", ":");

                            // Define the 24-hour time format
                            SimpleDateFormat sdf24 = new SimpleDateFormat("HH:mm");
                            // Parse the input times
                            Date date1 = sdf24.parse(time1);
                            Date date2 = sdf24.parse(time2);

                            // Define the 12-hour time format
                            SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mm a");

                            // Convert and print the times in 12-hour format
                            String time12_1 = sdf12.format(date1);
                            String time12_2 = sdf12.format(date2);
                %>
                <tr>
                    <td><%= transaction.getUserId() %></td>
                    <td><%= transaction.getEmployeeName() %></td>
                    <td><%= transaction.getRole() %></td>
                    <td><%= transaction.getProjectName() %></td>
                    <td><%= transaction.getDate() %></td>
                    <td><%= time12_1 %></td>
                    <td><%= time12_2 %></td>
                    <td><%= transaction.getTaskCategory() %></td>
                    <td><%= transaction.getDescription() %></td>
                    <td><%= transaction.getDuration() %></td>
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
