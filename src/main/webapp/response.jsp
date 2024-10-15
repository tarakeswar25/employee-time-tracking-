<!DOCTYPE html>
<html>
<head>
    <title>Response</title>
    <script type="text/javascript">
        window.onload = function() {
            alert('<%= request.getAttribute("message") %>');
            window.location.href = 'insertprojects.jsp';
        }
    </script>
</head>
<body>
</body>
</html>
