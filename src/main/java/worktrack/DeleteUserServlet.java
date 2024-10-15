package worktrack;

import worktrack.UserDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
@WebServlet("/deletestaff")

public class DeleteUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userid");
        String posStr = request.getParameter("pos");
        int pos = Integer.parseInt(posStr);

        UserDAO userDAO = new UserDAO();
        boolean success = false;
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
            success = userDAO.deleteStaff(userId, pos);
            if (success) {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Record deleted successfully.');");
                out.println("window.location.href = 'ViewAllemployees.jsp'  ;");
                out.println("</script>");
            } else {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Failed to delete the record.');");
                out.println("</script>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('An error occurred: " + e.getMessage() + "');");
            out.println("</script>");
        } finally {
            out.close();
        }
    }
}
