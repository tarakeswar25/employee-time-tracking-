<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.jsp.jstl.core.*" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Actions</title>
    <!-- Boxicons CDN -->
    <link href='https://unpkg.com/boxicons/css/boxicons.min.css' rel='stylesheet'>
    <style>
        /* CSS styles here (refer to the updated CSS above) */
        /* General Styles */
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(to right, #FF6FF2, #D83F60);
            color: #333;
            display: flex;
            flex-direction: column;
            height: 100vh;
        }

        /* Sidebar Styles */
        .sidebar {
            width: 200px;
            background-color: #333;
            padding: 20px;
            box-shadow: 2px 0 5px rgba(0,0,0,0.1);
            display: flex;
            flex-direction: column;
            align-items: flex-start;
            position: fixed;
            left: 0;
            top: 0;
            bottom: 0;
            overflow-y: auto;
        }

        .sidebar label {
            color: #fff;
            font-size: 18px;
            margin-bottom: 20px;
        }

        .sidebar form {
            width: 100%;
        }

        .sidebar button {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            font-size: 16px;
            color: #fff;
            background-color: #444;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .sidebar button:hover {
            background-color: #555;
        }

        /* Content Styles */
        .content {
            margin-left: 220px; /* Adjust based on sidebar width */
            padding: 20px;
            background: #f4f4f4;
            flex-grow: 1;
            display: flex;
            justify-content: center;
            align-items: center;
            position: relative;
        }

        /* Iframe Styles */
        iframe {
            width: 100%;
            height: 1000px;
            border: none;
        }

        /* Logout Button Styles */
        .logout {
            position: absolute;
            top: 10px;
            right: 10px;
        }

        .logout button {
            background-color: #FF6F61;
            color: white;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .logout button:hover {
            background-color: #D83F60;
        }

        /* Error Message Styles */
        .error-message {
            position: absolute;
            bottom: 10px;
            left: 20px;
            color: #fff;
            background: #e74c3c;
            padding: 10px;
            border-radius: 5px;
        }

    </style>
</head>
<body>

    <c:choose>
        <c:when test="${not empty user}">
            <div class="sidebar" id="sidebar">
                <!-- Display user attributes using EL -->
                <label>Welcome ${sessionScope.employeeName} <br>ID: ${sessionScope.userid}</label>
                
                <form method="post" action="dashboard.jsp" target="contentFrame">
                    <input type="hidden" name="userid" value="${sessionScope.userid}">
                    <button type="submit"><i class='bx bx-home'></i> Dashboard</button>
                </form>
                
                <form method="post" action="employeeForm.jsp" target="contentFrame">
                    <input type="hidden" name="userid" value="${sessionScope.userid}">
                    <input type="hidden" name="user" value="${sessionScope.employeeName}">
                    <button type="submit"><i class='bx bx-user-plus'></i> Add details</button>
                </form>
                
                <form method="post" action="Updateuser.jsp" target="contentFrame">
                    <input type="hidden" name="userid" value="${sessionScope.userid}">
                    <button type="submit"><i class='bx bx-edit'></i> Update/Delete details</button>
                </form>
                
                <form method="post" action="History.jsp" target="contentFrame">
                    <input type="hidden" name="userid" value="${sessionScope.userid}">
                    <button type="submit"><i class='bx bx-history'></i> History</button>
                </form>
            </div>
            
            <div class="content">
                <iframe name="contentFrame" src="dashboard.jsp"></iframe>
            </div>
            
            <form method="post" action="homepage.jsp" class="logout">
                <input type="hidden" name="userid" value="${sessionScope.userid}">
                <button type="submit"><i class='bx bx-log-out'></i> Logout</button>
            </form>
        </c:when>
        <c:otherwise>
            <p>User not found.</p>
        </c:otherwise>
    </c:choose>
   
</body>
</html>
