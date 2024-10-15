<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*, java.text.SimpleDateFormat, java.util.Date" %>
<%@ page import="worktrack.EmployeeWorkLogDAO" %>
    <%@ page import="java.util.List" %>
<%@ page import="worktrack.AllCategory" %>




<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="worktrack.Allprojects" %>
<%@ page import="worktrack.AllRoles" %>

<%
    String employeeId = request.getParameter("userid");
    Date currentDate = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String formattedDate = dateFormat.format(currentDate);

    EmployeeWorkLogDAO dao = new EmployeeWorkLogDAO();
    ResultSet rs = null;
    ResultSet rsProjects = null;

    try {
        rs = dao.getEmployeeData(employeeId, formattedDate);

        while (rs.next()) {
            rsProjects = dao.getProjectData(employeeId);

            String nm = rs.getString("employeeName");
            float st = rs.getFloat("startTime");
            float en = rs.getFloat("endTime");

            java.text.DecimalFormat df = new java.text.DecimalFormat("00.00");

            String time1 = df.format(st).replace(".", ":");
            String time2 = df.format(en).replace(".", ":");

            SimpleDateFormat sdf24 = new SimpleDateFormat("HH:mm");
            Date date1 = sdf24.parse(time1);
            Date date2 = sdf24.parse(time2);

            SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mm a");
            String time12_1 = sdf12.format(date1);
            String time12_2 = sdf12.format(date2);

            out.println("<html><head><style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f0f0f0; margin: 0; padding: 0; }");
            out.println(".form-container { width: 80%; max-width: 600px; margin: 20px auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
            out.println("h1, h2 { text-align: center; color: #333333; }");
            out.println("form { margin-top: 20px; }");
            out.println("label { display: block; margin-top: 10px; color: #555555; }");
            out.println("input[type=text], input[type=date], select, textarea { width: 100%; padding: 8px; margin-top: 6px; margin-bottom: 12px; border: 1px solid #dddddd; border-radius: 4px; box-sizing: border-box; font-size: 14px; }");
            out.println("select { height: 36px; }");
            out.println("textarea { resize: vertical; }");
            out.println("button[type=submit] { background-color: #4CAF50; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; margin-top: 10px; }");
            out.println("button[type=submit]:hover { background-color: #45a049; }");
            out.println("</style></head><body>");
            out.println("<h1>Welcome to the Admin Page</h1>");
            out.println("<div class='form-container'>");
            out.println("<h2>Employee Work Log</h2>");
            out.println("<form action='EmployWorkLog' method='post'>");

            out.println("<input type='text' name='pos' value='" + rs.getInt("pos")+ "'hidden > ");
            out.println("<label for='employeeName'>Employee Id:</label>");
            out.println("<input type='text' name='name1' value='" + rs.getString("userid") +"'disabled>");
            out.println("<input type='text' name='name' value='" + rs.getString("userid") +"'hidden>"); 

            out.println("<label for='employeeName'>Employee Name:</label>");
            out.println("<input type='text' id='employeeName' name='employeeName' value='" + rs.getString("employeeName") +"' required disabled>");

            out.println("<label for='role'>Role:</label>");
            out.println("<input type='text' id='role' name='role' value='" + rs.getString("role") + "' required disabled>");

            out.println("<label for='projectName'>Project Name:</label>");
            if (rsProjects.next()) {
                out.println("<input type='text' id='projectName' name='projectNam' value='" + rsProjects.getString("projectName") + "' required disabled>");
                out.println("<input type='text' id='projectName' name='projectName' value='" + rsProjects.getString("projectName") + "' required hidden >");
            }
            out.println("<label for='date'>Date:</label>");
            out.println("<input type='date' id='date' name='dat' value='" + rs.getDate("date") + "' required disabled>");
            out.println("<input type='date' id='date' name='date' value='" + rs.getDate("date") + "' required hidden >");

            out.println("<label for='startTime'>Start Time:</label>");
            out.println("<input type='text' id='startTime' name='startTime' value='" + time12_1 + "' required>");

            out.println("<label for='endTime'>End Time:</label>");
            out.println("<input type='text' id='endTime' name='endTime' value='" + time12_2 + "' required>");

            out.println("<label for='taskCategory'>Task Category:</label>");
            out.println("<select id='taskCategory' name='taskCategory' required>");
            out.println("<option value=''>Select a category</option>");
            
            out.println("<option value=\"" + rs.getString("taskCategory") + "\" selected>" + rs.getString("taskCategory") + "</option>");

            List<AllCategory> CategoryList = (List<AllCategory>) session.getAttribute("CategoryList");

           // out.println("<option value=\"\" disabled selected>Select category</option>");

            if (CategoryList != null) {
                for (AllCategory project : CategoryList) {
                    out.println("<option value=\"" + project.getcategory() + "\">" + project.getcategory() + "</option>");
                }
            } else {
                out.println("<option value=\"\">No projects available</option>");
            }
            

			out.println("</select>");

            out.println("<label for='description'>Description:</label>");
            out.println("<textarea id='description' name='description' rows='4' required>" + rs.getString("description") + "</textarea>");

            out.println("<button type='submit'>Submit</button>");
            out.println("</form>");

            out.println("<form action='DeleteEmployee.jsp' method='post' style='margin-top: 10px;'>");
            out.println("<input type='hidden' name='userid' value='" + rs.getString("userid") + "'>");
            out.println("<input type='hidden' name='pos' value='" + rs.getInt("pos") + "'>");
            out.println("<button type='submit'>Delete</button>");
            out.println("</form>");

            out.println("</div>");
            out.println("</body></html>");
        }

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (rs != null) rs.close();
        if (rsProjects != null) rsProjects.close();
    }
%>

<script>
    function nextRecord() {
        var currentEmployeeId = document.getElementsByName("employeeId")[0].value;
        window.location.href = "upd.jsp?userid=" + currentEmployeeId;
    }
</script>
