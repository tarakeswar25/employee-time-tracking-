package worktrack;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import worktrack.UserDAO;

@WebServlet("/deleteSubmission")
public class DeleteSubmissionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeId = request.getParameter("userid");
        String posString = request.getParameter("pos");

        if (employeeId != null && posString != null) {
            try {
                int pos = Integer.parseInt(posString);
                UserDAO userDAO = new UserDAO();
                int rowsAffected = userDAO.Delete(employeeId, pos); // Ensure UserDAO method name is correct

                response.setContentType("text/html");
                if (rowsAffected > 0) {
                    response.getWriter().println("<html><head><title>Success</title></head><body>");
                    response.getWriter().println("<script type='text/javascript'>");
                    response.getWriter().println("alert('Record deleted successfully.');");
                    response.getWriter().println("window.location.href = 'viewEmployees.jsp';");
                    response.getWriter().println("</script>");
                    response.getWriter().println("</body></html>");
                } else {
                    response.getWriter().println("<html><head><title>Failure</title></head><body>");
                    response.getWriter().println("<script type='text/javascript'>");
                    response.getWriter().println("alert('Failed to delete the record.');");
                    response.getWriter().println("window.location.href = 'viewEmployees.jsp';");
                    response.getWriter().println("</script>");
                    response.getWriter().println("</body></html>");
                }
            } catch (NumberFormatException e) {
                response.setContentType("text/html");
                response.getWriter().println("<html><head><title>Error</title></head><body>");
                response.getWriter().println("<script type='text/javascript'>");
                response.getWriter().println("alert('Invalid position format.');");
                response.getWriter().println("window.location.href = 'viewEmployees.jsp';");
                response.getWriter().println("</script>");
                response.getWriter().println("</body></html>");
            }
        } else {
            response.setContentType("text/html");
            response.getWriter().println("<html><head><title>Error</title></head><body>");
            response.getWriter().println("<script type='text/javascript'>");
            response.getWriter().println("alert('Required parameters are missing.');");
            response.getWriter().println("window.location.href = 'viewEmployees.jsp';");
            response.getWriter().println("</script>");
            response.getWriter().println("</body></html>");
        }
    }
}
