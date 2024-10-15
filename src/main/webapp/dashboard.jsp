<%@ page import="java.sql.*" %>
<%@ page import="org.json.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>


<!DOCTYPE html>
<html>
<head>
    <title>Data Visualization Dashboard</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            background: linear-gradient(to right, #ff7e5f, #feb47b);
            color: #333;
        }

        .container {
            width: 100%;
            max-width: 1200px;
            padding: 20px;
            display: grid;
            grid-template-areas: 
                "monthly-chart weekly-chart"
                "day-chart day-chart";
            grid-gap: 20px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
            background: rgba(255, 255, 255, 0.8);
            border-radius: 10px;
        }

        .chart-container {
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 12px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            padding: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
            transition: transform 0.3s, box-shadow 0.3s;
        }

        .chart-container:hover {
            transform: translateY(-10px);
            box-shadow: 0 0 25px rgba(0, 0, 0, 0.2);
        }

        .chart-container h3 {
            background-color: #007bff;
            color: #fff;
            font-size: 20px;
            text-align: center;
            width: 100%;
            margin: 0;
            padding: 10px 0;
            border-radius: 8px 8px 0 0;
        }

        .select-container {
            width: 100%;
            margin: 20px 0;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .select-container label, .select-container select, .select-container button {
            margin: 5px 0;
        }

        .select-container select, .select-container button {
            width: 80%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background: #f8f9fa;
            transition: background 0.3s;
        }

        .select-container select:hover, .select-container button:hover {
            background: #e9ecef;
        }

        .select-container button {
            cursor: pointer;
            color: #fff;
            background: #007bff;
            border: none;
        }

        .select-container button:hover {
            background: #0056b3;
        }

        canvas {
            max-width: 100%;
            height: 100%;
        }

        .monthly-chart {
            grid-area: monthly-chart;
            width: 100%;
            height: 500px;
        }

        .weekly-chart {
            grid-area: weekly-chart;
            width: 100%;
            height: 500px;
        }

        .day-chart {
            grid-area: day-chart;
            width: 100%;
            height: 800px;
        }

        @media (max-width: 768px) {
            .container {
                grid-template-areas: 
                    "monthly-chart"
                    "weekly-chart"
                    "day-chart";
            }
        }
    </style>
</head>
<body>
    <h2>Data Report Dashboard</h2>
    <div class="container">
        <div class="chart-container monthly-chart">
            <h3>Monthly Report</h3>
            <div class="select-container">
                <label for="monthSelect">Select Month:</label>
                <select id="monthSelect" onchange="updateMonthlyChart()">
                    <% 
                        String userId =(String)session.getAttribute("userid");
                    System.out.print(userId);
                        if (userId != null && !userId.isEmpty()) {
                            String url = "jdbc:mysql://localhost:3306/mtb";
                            String username = "root";
                            String password = "Tarak@21737";
                            Connection conn = null;
                            PreparedStatement stmt = null;
                            ResultSet rs = null;

                            try {
                                Class.forName("com.mysql.cj.jdbc.Driver");
                                conn = DriverManager.getConnection(url, username, password);
                                String query = "SELECT DISTINCT DATE_FORMAT(date, '%Y-%m') as month FROM data WHERE userid = ?";
                                stmt = conn.prepareStatement(query);
                                stmt.setString(1, userId);
                                rs = stmt.executeQuery();
                                
                                Date currentDate = new Date();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
                                String formatted = dateFormat.format(currentDate);
                                
                                
                               // java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM");
                              //  String formattedDate = sdf.format(new java.util.Date());

                                while (rs.next()) {
                                    String month = rs.getString("month");
                                    //String selectedMonth = rs.getString("month");
                                    if (formatted.equals(month)){%>
                                    <option value="<%= month %>" selected  ><%= month %> </option>
                                    	
                                    	
                                    	
                             <%        }else{
                    %>
                                    <option value="<%= month %>"><%= month %></option>
                    <%
                                }}
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
                                if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
                                if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
                            }
                        } else {
                    %>
                            <option value="">No user ID provided</option>
                    <%
                        }
                    %>
                </select>
            </div>
            <div style=" height:150%">
                <canvas id="monthlyChart"></canvas>
            </div>
        </div>

        <div class="chart-container day-chart">
            <h3>Day Report</h3>
            <div class="select-container">
                <label for="monthSelect1">Select Month:</label>
                <select id="monthSelect1">
                    <% 
                        if (userId != null && !userId.isEmpty()) {
                            String url = "jdbc:mysql://localhost:3306/mtb";
                            String username = "root";
                            String password = "Tarak@21737";
                            Connection conn = null;
                            PreparedStatement stmt = null;
                            ResultSet rs = null;

                            try {
                                Class.forName("com.mysql.cj.jdbc.Driver");
                                conn = DriverManager.getConnection(url, username, password);
                                String query = "SELECT DISTINCT DATE_FORMAT(date, '%Y-%m') as month FROM data WHERE userid = ?";
                                stmt = conn.prepareStatement(query);
                                stmt.setString(1, userId);
                                rs = stmt.executeQuery();
                                Date currentDate = new Date();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
                                String selectedMonth = dateFormat.format(currentDate);
                                
                                while (rs.next()) {
                                    String month = rs.getString("month");
                                    //String selectedMonth = rs.getString("month");
                                    if (selectedMonth.equals(month)){%>
                                    <option value="<%= month %>" selected  ><%= month %> </option>
                                    	
                                    	
                                    	
                             <%        }else {
                    %>
                                    <option value="<%= month %>"><%= month %></option>
                    <%
                             }  }

                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
                                if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
                                if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
                            }
                        } else {
                    %>
                            <option value="">No user ID provided</option>
                    <%
                        }
                    %>
                </select>
                <button onclick="filterDates()">Filter Dates</button>
            </div>
            <div class="select-container">
                <label for="dateSelect">Select Date:</label>
                <select id="dateSelect" onchange="updateChart()">
                <%Date currentDat = new Date();
                SimpleDateFormat dateForma = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDat = dateForma.format(currentDat);
                
                %>
                    
                   <option value="<%= formattedDat %>"><%= formattedDat %></option>
                    
                    <!-- Options will be populated dynamically -->
                </select>
            </div>
            <div style=" height:100%">
                <canvas id="pieChart" style=" height:250px;	"></canvas>
            </div>
        </div>

        <div class="chart-container week-chart">
            <h3>Weekly Report</h3>
            <div class="select-container">
                <label for="weekSelect">Week Report:</label>
                <select id="weekSelect" onchange="updateWeeklyChart()" >
                    <% 
                        Date currentDate = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String formattedDate = dateFormat.format(currentDate);
                    %>
                    
                    <option value="<%= formattedDate %>" ><%= formattedDate %></option>
                    <!-- Additional options can be dynamically added here -->
                </select>
            </div>
            <div style=" height:150%">
                <canvas id="weeklyChart"></canvas>
            </div>
        </div>
    </div>

    <script>
        var userId = '<%= userId %>';

        var pieChartCtx = document.getElementById('pieChart').getContext('2d');
        var pieChart = new Chart(pieChartCtx, {
            type: 'pie',
            data: {
                labels: [],
                datasets: [{
                    label: 'Duration',
                    data: [],
                    backgroundColor: [],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'top',
                    },
                    tooltip: {
                        callbacks: {
                            label: function(tooltipItem) {
                                return tooltipItem.label + ': ' + tooltipItem.raw.toFixed(2) + ' hours';
                            }
                        }
                    }
                }
            }
        });

        var monthlyChartCtx = document.getElementById('monthlyChart').getContext('2d');
        var monthlyChart = new Chart(monthlyChartCtx, {
            type: 'bar',
            data: {
                labels: [],
                datasets: [{
                	label: [],
                    data: [],
                    backgroundColor: [],
                    borderColor: [],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    x: {
                        grid: {
                            display: false // Remove the grid lines for the x-axis
                        }
                    },
                    y: {
                        grid: {
                            display: false // Remove the grid lines for the y-axis
                        }
                    }
                },
                plugins: {
                    legend: {
                        position: 'top',
                    },
                    tooltip: {
                        callbacks: {
                            label: function(tooltipItem) {
                                return tooltipItem.label + ': ' + tooltipItem.raw.toFixed(2) + ' hours';
                            }
                        }
                    }
                }
            }
        });

        var weeklyChartCtx = document.getElementById('weeklyChart').getContext('2d');
        var weeklyChart = new Chart(weeklyChartCtx, {
            type: 'bar',
            data: {
                labels: [],
                datasets: [{
                    label: 'Weekly Duration',
                    data: [],
                    backgroundColor: [],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    x: {
                        grid: {
                            display: false // Remove the grid lines for the x-axis
                        }
                    },
                    y: {
                        grid: {
                            display: false // Remove the grid lines for the y-axis
                        }
                    }
                },
                plugins: {
                    legend: {
                        position: 'top',
                    },
                    tooltip: {
                        callbacks: {
                            label: function(tooltipItem) {
                                return tooltipItem.label + ': ' + tooltipItem.raw.toFixed(2) + ' hours';
                            }
                        }
                    }
                }
            }
        });

        function updateChart() {
            var selectedDate = document.getElementById('dateSelect').value;
            fetchChartData(selectedDate);
        }

        function fetchChartData(selectedDate) {
            fetch('getData.jsp?date=' + selectedDate + '&userid=' + userId)
                .then(response => response.json())
                .then(data => {
                    var dynamicColors = [];
                    for (var i = 0; i < data.labels.length; i++) {
                        var color = 'rgba(' + Math.floor(Math.random() * 256) + ',' + 
                                    Math.floor(Math.random() * 256) + ',' + 
                                    Math.floor(Math.random() * 256) + ', 0.7)';
                        dynamicColors.push(color);
                    }

                    pieChart.data.labels = data.labels;
                    pieChart.data.datasets[0].data = data.durations;
                    pieChart.data.datasets[0].backgroundColor = dynamicColors;
                    pieChart.update();
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
        }

        function updateMonthlyChart() {
            var selectedMonth = document.getElementById('monthSelect').value;
            fetchMonthlyChartData(selectedMonth);
        }

        function fetchMonthlyChartData(selectedMonth) {
            fetch('getMonthlyData.jsp?month=' + selectedMonth + '&userid=' + userId)
                .then(response => response.json())
                .then(data => {
                    var dynamicColors = [];
                    for (var i = 0; i < data.labels.length; i++) {
                        var color = 'rgba(' + Math.floor(Math.random() * 256) + ',' + 
                                    Math.floor(Math.random() * 256) + ',' + 
                                    Math.floor(Math.random() * 256) + ', 0.7)';
                        dynamicColors.push(color);
                    }

                    monthlyChart.data.labels = data.labels;
                    monthlyChart.data.datasets[0].label.push(data.labels);// = data.labels;

                    monthlyChart.data.datasets[0].data = data.durations;
                    monthlyChart.data.datasets[0].backgroundColor = dynamicColors;
                    monthlyChart.update();

                    var weekSelect = document.getElementById('weekSelect');
                    weekSelect.innerHTML = '';
                    var currentDate = new Date();
                    for (var i = 0; i < 7; i++) {
                        var pastDate = new Date(currentDate);
                        pastDate.setDate(currentDate.getDate() - i);
                        var option = document.createElement('option');
                        option.value = pastDate.toISOString().split('T')[0];
                        option.text = pastDate.toISOString().split('T')[0];
                        weekSelect.appendChild(option);
                    }
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
        }

        function updateWeeklyChart() {
            var selectedWeek = document.getElementById('weekSelect').value;
            fetchWeeklyChartData(selectedWeek);
        }

        function fetchWeeklyChartData(selectedWeek) {
            fetch('getWeeklyData.jsp?week=' + selectedWeek + '&userid=' + userId)
                .then(response => response.json())
                .then(data => {
                    var dynamicColors = [];
                    for (var i = 0; i < data.labels.length; i++) {
                        var color = 'rgba(' + Math.floor(Math.random() * 256) + ',' + 
                                    Math.floor(Math.random() * 256) + ',' + 
                                    Math.floor(Math.random() * 256) + ', 0.7)';
                        dynamicColors.push(color);
                    }

                    weeklyChart.data.labels = data.labels;
                    weeklyChart.data.datasets[0].data = data.durations;
                    weeklyChart.data.datasets[0].backgroundColor = dynamicColors;
                    weeklyChart.update();
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
        }
        //session.setAttribute("a", 1); 
        //Integer a = (Integer) session.getAttribute("a");
        //if (a == 1) {
        //	filterDates();
        	//}
        
        
        function filterDates() {
        	
        	
            var selectedMonth = document.getElementById('monthSelect1').value;
            fetch('getDatesByMonth.jsp?month=' + selectedMonth + '&userid=' + userId)
                .then(response => response.json())
                .then(data => {
                    var dateSelect = document.getElementById('dateSelect');
                    dateSelect.innerHTML = '';
                    var option = document.createElement('option');
                    option.value = "";
                    option.text = "select";
                    dateSelect.appendChild(option);
                    data.dates.forEach(date => {
                        var option = document.createElement('option');
                        option.value = date;
                        option.text = date;
                        dateSelect.appendChild(option);
                    });
                })
                .catch(error => {
                    console.error('Error fetching dates:', error);
                });
        }
        document.getElementById('monthSelect1').addEventListener('change', filterDates);

let a=1;
if (a==1){
	filterDates();
	a=2;
	
}
        
       // a++;
        
        
        //session.setAttribute("a", a);
        
        // Initial calls to populate charts with default data
        updateChart();
        updateMonthlyChart();
        updateWeeklyChart();
    </script>
</body>
</html>
