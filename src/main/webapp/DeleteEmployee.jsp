<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import="java.sql.*" %>
<%@ page import="worktrack.UserDAO" %>

<%
    String employeeId = request.getParameter("userid");
    String pos = request.getParameter("pos");
%>

            <input type="text" name="userid" value="employeeId" hidden>

<%

    if (employeeId != null && pos != null) {
        Connection con = null;
        PreparedStatement ps = null;
        
        
        	int posi = Integer.parseInt(pos);
        	UserDAO userDAO = new UserDAO();
            int rowsAffected = userDAO.Delete(employeeId, posi);

        	

            if (rowsAffected > 0) {
                // Record deleted successfully
                out.println("<html><head><title>Success</title></head><body>");
                out.println("<script type='text/javascript'>");
                out.println("alert('Record deleted successfully.');");
                out.println("window.location.href = 'Updateuser.jsp ';"); // Pass the userid as a parameter
                out.println("</script>");
                out.println("</body></html>");
            } else {
                // Failed to delete the record
                out.println("<html><head><title>Failure</title></head><body>");
                out.println("<script type='text/javascript'>");
                out.println("alert('Failed to delete the record.');");
                out.println("window.location.href = 'upd.jsp?userid=" + employeeId + "';"); // Pass the userid as a parameter
                out.println("</script>");
                out.println("</body></html>");
            
            }}
    else {
        // Parameters were not provided
        out.println("<html><head><title>Error</title></head><body>");
        out.println("<script type='text/javascript'>");
        out.println("alert('Required parameters are missing.');");
        out.println("window.location.href = 'upd.jsp?userid=" + employeeId + "';"); // Pass the userid as a parameter
        out.println("</script>");
        out.println("</body></html>");
    }
%>
