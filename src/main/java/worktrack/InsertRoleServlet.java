package worktrack;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/insertRoleServlet")
public class InsertRoleServlet extends HttpServlet {
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roleName = request.getParameter("roleName");

        if (roleName != null && !roleName.trim().isEmpty()) {
            try {
                // Check if the role already exists
                if (userDAO.roleExists(roleName)) {
                    // Role already exists
                    response.setContentType("text/html");
                    response.getWriter().println("<html><head><title>Role Exists</title></head><body>");
                    response.getWriter().println("<script type='text/javascript'>");
                    response.getWriter().println("alert('Role already exists!');");
                    response.getWriter().println("window.location.href = 'insertprojects.jsp';");
                    response.getWriter().println("</script>");
                    response.getWriter().println("</body></html>");
                    return;
                }

                // Insert the new role
                userDAO.insertRole(roleName);

                // Success message and redirection
                response.setContentType("text/html");
                response.getWriter().println("<html><head><title>Success</title></head><body>");
                response.getWriter().println("<script type='text/javascript'>");
                response.getWriter().println("alert('Role inserted successfully!');");
                response.getWriter().println("window.location.href = 'insertprojects.jsp';");
                response.getWriter().println("</script>");
                response.getWriter().println("</body></html>");

            } catch (SQLException e) {
                e.printStackTrace();
                response.setContentType("text/html");
                response.getWriter().println("<html><head><title>Error</title></head><body>");
                response.getWriter().println("<script type='text/javascript'>");
                response.getWriter().println("alert('An error occurred: " + e.getMessage() + "');");
                response.getWriter().println("window.location.href = 'insertprojects.jsp';");
                response.getWriter().println("</script>");
                response.getWriter().println("</body></html>");
            }
        } else {
            response.setContentType("text/html");
            response.getWriter().println("<html><head><title>Empty Role</title></head><body>");
            response.getWriter().println("<script type='text/javascript'>");
            response.getWriter().println("alert('Role name cannot be empty!');");
            response.getWriter().println("window.location.href = 'insertprojects.jsp';");
            response.getWriter().println("</script>");
            response.getWriter().println("</body></html>");
        }
    }
}
