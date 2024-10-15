<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update User Details</title>
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
        .message {
            padding: 10px;
            margin-top: 20px;
            border-radius: 5px;
        }
        .success {
            background-color: #d4edda;
            color: #155724;
        }
        .error {
            background-color: #f8d7da;
            color: #721c24;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Update User Details</h1>
        <div class="message <%= request.getAttribute("messageType") %>">
            <%= request.getAttribute("message") %>
        </div>
    </div>
    <script>
        function showAlertAndRedirect(message, url) {
            alert(message);
            setTimeout(function() {
                window.location.href = url;
            }, 2000);
        }

        <% if ("success".equals(request.getAttribute("messageType"))) { %>
            showAlertAndRedirect("<%= request.getAttribute("message") %>", "Register.jsp");
        <% } %>
    </script>
</body>
</html>
