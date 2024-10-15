<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.ArrayList, java.util.List" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin Dashboard</title>
<link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f9f9f9;
        margin: 0;
        padding: 20px;
    }
    h1 {
        color: #333;
    }
    .container {
        max-width: 1200px;
        margin: 0 auto;
        background-color: #fff;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }
    .form-group {
        margin-bottom: 15px;
    }
    label {
        display: block;
        margin-bottom: 5px;
        color: #555;
    }
    select, input[type="submit"] {
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 4px;
        font-size: 16px;
    }
    .chart-container {
        margin-top: 30px;
    }
    canvas {
        max-width: 100%;
    }
</style>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function() {
        // Update projects dropdown based on selected employee
        $('#employee').change(function() {
            var employeeId = $(this).val();
            var projectSelect = $('#project');
            projectSelect.prop('disabled', true);
            projectSelect.empty();
            projectSelect.append('<option value="">Select a project</option>');

            if (employeeId) {
                $.ajax({
                    url: 'ProjectLoaderServlet',
                    type: 'GET',
                    data: { employeeId: employeeId },
                    dataType: 'json',
                    success: function(data) {
                        projectSelect.prop('disabled', false);
                        projectSelect.append('<option value="all">All Projects</option>');
                        $.each(data, function(index, project) {
                            projectSelect.append('<option value="' + project.name + '">' + project.name + '</option>');
                        });
                    },
                    error: function(xhr, status, error) {
                        console.error("Error loading projects: ", status, error);
                    }
                });
            }
        });

        // Handle form submission for chart data
        var myChart; // Global variable to hold the chart instance

        $('#dashboardForm').on('submit', function(event) {
            event.preventDefault();
            var duration = $('#duration').val();
            var chartType = duration === 'daily' ? 'pie' : 'bar';

            $.ajax({
                url: 'DashboardServlet',
                type: 'POST',
                data: $(this).serialize(),
                dataType: 'json',
                success: function(data) {
                    var labels = [];
                    var dataPoints = [];
                    
                    data.forEach(function(item) {
                        labels.push(item.date + " (" + parseFloat(item.hours) + ")");
                        dataPoints.push(parseFloat(item.hours));
                    });

                    var ctx = document.getElementById('myChart').getContext('2d');

                    if (myChart) {
                        myChart.destroy(); // Destroy the old chart if it exists
                    }

                    var backgroundColors = [
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(255, 159, 64, 0.2)'
                    ];

                    var borderColors = [
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 99, 132, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(153, 102, 255, 1)',
                        'rgba(255, 159, 64, 1)'
                    ];

                    myChart = new Chart(ctx, {
                        type: chartType, // Change to 'line' or other type if needed
                        data: {
                            labels: labels,
                            datasets: [{
                                label: 'Hours Worked',
                                data: dataPoints,
                                backgroundColor: backgroundColors,
                                borderColor: borderColors,
                                borderWidth: 1
                            }]
                        },
                        options: {
                            scales: {
                                y: {
                                    beginAtZero: true
                                }
                            }
                        }
                    });
                },
                error: function(xhr, status, error) {
                    console.error("AJAX Error: ", status, error);
                }
            });
        });
    });
</script>
</head>
<body>
    <div class="container">
        <h1>Admin Dashboard</h1>
        <form id="dashboardForm">
            <div class="form-group">
                <label for="employee">Select Employee:</label>
                <select id="employee" name="employee">
                    <option value="">Select an Employee</option>
                    <option value='all'>ALL Employees</option>
                    <% 
                    Connection con = null;
                    PreparedStatement ps = null;
                    ResultSet rs = null;
                    
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtb", "root", "Tarak@21737");
                        ps = con.prepareStatement("SELECT userid, employeeName FROM javv WHERE type='user'");
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            String empId = rs.getString("userid");
                            String empName = rs.getString("employeeName");
                    %>
                        <option value="<%= empId %>"><%= empName %></option>
                    <% 
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignored */ }
                        if (ps != null) try { ps.close(); } catch (SQLException e) { /* ignored */ }
                        if (con != null) try { con.close(); } catch (SQLException e) { /* ignored */ }
                    }
                    %>
                </select>
            </div>
            <div class="form-group">
                <label for="project">Select Project:</label>
                <select id="project" name="project" disabled>
                    <option value="">Select a project</option>
                </select>
            </div>
            <div class="form-group">
                <label for="duration">Select Duration:</label>
                <select id="duration" name="duration">
                    <option value="daily">Overall</option>
                    <option value="weekly">Weekly</option>
                    <option value="monthly">Monthly</option>
                </select>
            </div>
            <input type="submit" value="View Chart">
        </form>

        <div class="chart-container" style="height:400px;width:800px;">
            <canvas id="myChart"></canvas>
        </div>
    </div>
</body>
</html>
