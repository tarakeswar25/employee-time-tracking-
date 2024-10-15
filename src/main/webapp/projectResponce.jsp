<!DOCTYPE html>
<html>
<head>
    <title>Response</title>
    <script type="text/javascript">
        window.onload = function() {
            alert('<%= request.getAttribute("message") %>');
            window.location.href = 'insertprojects.jsp';  // Change this to the desired redirect page
        }
    </script>
</head>
<body>
</body>
</html>
