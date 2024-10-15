
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Employee Management System</title>
<style>
    body {
        font-family: 'Arial', sans-serif;
        background: url("background.jpg") no-repeat center center fixed;
        background-size: cover;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
    }
    form {
        background: rgba(60, 70, 180, 0.5); /* Slightly transparent to see background */
        padding: 40px;
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        max-width: 400px;
        width: 100%;
        text-align: center;
        margin-left: 20%; /* This will move the form slightly to the right of the center */
    }
    label {
        display: block;
        margin-bottom: 10px;
        color: #333;
        font-size: 14px;
        text-align: left;
    }
    input[type="text"], input[type="password"] {
        width: 100%;
        padding: 10px;
        margin-bottom: 20px;
        border: 1px solid #ccc;
        border-radius: 5px;
        box-sizing: border-box;
        font-size: 14px;
    }
    .password-container {
        position: relative;
        width: 100%;
    }
    .password-container input {
        width: 100%;
        padding-right: 40px; /* Space for the eye icon */
    }
    .password-container .eye-icon {
        position: absolute;
        top: 50%;
        right: 10px;
        transform: translateY(-50%);
        cursor: pointer;
    }
    input[type="submit"] {
        background: #007BFF;
        color: #fff;
        padding: 10px 20px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 14px;
        transition: background 0.3s ease;
    }
    .eye-icon {
        position: absolute;
        top: 50%;
        right: 10px; /* Adjust this value to position the icon as needed */
        transform: translateY(-50%);
        width: 24px; /* Adjust size of the icon */
        height: 24px; /* Adjust size of the icon */
        cursor: pointer;
    }
    input[type="submit"]:hover {
        background: #0056b3;
    }
</style>
<script>
    function togglePassword() {
        var passwordField = document.getElementById("pass");
        var eyeIcon = document.getElementById("eye-icon");
        if (passwordField.type === "password") {
            passwordField.type = "text";
            eyeIcon.src = "eyes.png"; // Path to eye-closed icon
        } else {
            passwordField.type = "password";
            eyeIcon.src = "eye (1).png"; // Path to eye-open icon
        }
    }
</script>
</head>
<body>

<form action="login" method="post">
    <h4 style="font-size:20px;">Login form</h4>
    <label for="user">User ID :-</label>
    <input type="text" name="user" id="user" placeholder="1234">
    <label for="pass">Password</label>
    <div class="password-container">
        <input type="password" name="pass" id="pass">
        <img src="eye (1).png" id="eye-icon" class="eye-icon" onclick="togglePassword()" style="height:fit;">
    </div>
    <input type="submit" value="Click Me">
</form>

</body>
</html>
