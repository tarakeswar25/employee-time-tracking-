package worktrack;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/assignProjectServlet")
public class AssignProjectServlet extends HttpServlet {

    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userid");
        String projectName = request.getParameter("projectName");
        String role = request.getParameter("role");

        boolean isSuccess = userDAO.assignProject(userId, projectName, role);

        if (isSuccess) {
            //response.sendRedirect("success.jsp"); // Redirect to a success page
        } else {
            //response.sendRedirect("error.jsp"); // Redirect to an error page
        }
    }
}
