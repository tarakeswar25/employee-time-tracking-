<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="worktrack.Allprojects" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Admin Page</title>
    <link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f0f2f5;
        }
        .form-container {
            max-width: 600px;
            margin: auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 10px;
            background-color: #ffffff;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .form-container h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }
        .form-container label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #333;
        }
        .form-container input, .form-container select, .form-container textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }
        .form-container button {
            width: 100%;
            padding: 10px;
            border: none;
            border-radius: 5px;
            background-color: #4CAF50;
            color: white;
            font-size: 16px;
            cursor: pointer;
        }
        .form-container button:hover {
            background-color: #45a049;
        }
        @media (max-width: 600px) {
            .form-container {
                padding: 15px;
            }
            .form-container h2 {
                font-size: 18px;
            }
            .form-container button {
                font-size: 14px;
            }
        }
        .form-container .input-group {
            position: relative;
        }
        .form-container .input-group i {
            position: absolute;
            right: 15px;
            top: 50%;
            transform: translateY(-50%);
            color: #aaa;
        }
    </style>
</head>
<body>
<div class="form-container">
    <h2><i class='bx bx-log-in-circle'></i> Employee Work Log</h2>
    <form action="InsertWorkLog" method="post">
        <div class="input-group">
            <label for="name1">Employee ID: <i class='bx bx-id-card'></i></label>
            <input type="text" id="name1" name="name" placeholder="12345" required>
        </div>
        <div class="input-group">
            <label for="employeeName">Employee Name: <i class='bx bx-user'></i></label>
            <input type="text" id="employeeName1" name="employeeNam" required disabled>
            <input type="text" id="employeeName" name="employeeName" required hidden>
        </div>
        <div class="input-group">
            <label for="role">Role: <i class='bx bx-briefcase'></i></label>
            <input type="text" id="role1" name="rol" required disabled>
            <input type="text" id="role" name="role" required hidden>
        </div>
        <div class="input-group">
            <label for="projectName">Project Name: <i class='bx bx-task'></i></label>
            <input type="text" id="projectName1" name="projectNam" required disabled>
            <input type="text" id="projectName" name="projectName" required hidden>
        </div>
        <div class="input-group">
            <label for="date">Date: <i class='bx bx-calendar'></i></label>
            <input type="date" id="date" name="date" required>
        </div>
        <div class="input-group">
            <label for="startTime">Start Time: <i class='bx bx-time'></i></label>
            <input type="text" id="startTime" name="startTime" placeholder="09:22 AM" required>
        </div>
        <div class="input-group">
            <label for="endTime">End Time: <i class='bx bx-time-five'></i></label>
            <input type="text" id="endTime" name="endTime" placeholder="10:25 AM" required>
        </div>
        <div class="input-group">
            <label for="taskCategory">Task Category: <i class='bx bx-list-ul'></i></label>
            <select id="taskCategory" name="taskCategory" required>
                <option value="">Select a category</option>
                <option value="Development">Development</option>
                <option value="Testing">Testing</option>
                <option value="Documentation">Documentation</option>
                <option value="Meeting">Meeting</option>
                <option value="Other">Other</option>
            </select>
        </div>
        <div class="input-group">
            <label for="description">Description: <i class='bx bx-pencil'></i></label>
            <textarea id="description" name="description" rows="4" required></textarea>
        </div>
        <button type="submit"><i class='bx bx-send'></i> Submit</button>
    </form>
</div>

<script>
function fetchEmployee() {
    document.getElementById('name1').addEventListener('input', function() {
        var employeeId = this.value;
        console.log(employeeId); // Corrected typo

        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function() {
            if (this.readyState === 4) { // Check if request is complete
                if (this.status === 200) { // Check if status is OK
                    var data = JSON.parse(this.responseText);
                    document.getElementById('employeeName').value = data.employeeName;
                    document.getElementById('employeeName1').value = data.employeeName;
                    document.getElementById('role').value = data.role;
                    document.getElementById('role1').value = data.role;
                    document.getElementById('projectName').value = data.projectName;
                    document.getElementById('projectName1').value = data.projectName;
                } else {
                    console.error('Error fetching employee data: ' + this.statusText);
                }
            }
        };

        xmlhttp.open('POST', 'fetchemployee.jsp', true);
        xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xmlhttp.send('employeeId=' + encodeURIComponent(employeeId));
    });
}
document.addEventListener('DOMContentLoaded', fetchEmployee);
</script>
</body>
</html>
