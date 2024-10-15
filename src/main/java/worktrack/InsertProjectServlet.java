package worktrack;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;

@WebServlet("/insertProjectServlet")
public class InsertProjectServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String projectName = request.getParameter("projectName");

        if (projectName != null && !projectName.trim().isEmpty()) {
            try {
                // Check if the project name already exists
                if (userDAO.isProjectNameExists(projectName)) {
                    // Project name already exists
                    request.setAttribute("message", "Project name already exists!");
                    request.getRequestDispatcher("response.jsp").forward(request, response);
                    return;
                }

                // Insert the new project name
                userDAO.insertProject(projectName);

                // Set success message
                request.setAttribute("message", "Project inserted successfully!");
                request.getRequestDispatcher("projectResponse.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("message", "An error occurred: " + e.getMessage());
                request.getRequestDispatcher("response.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("message", "Project name cannot be empty!");
            request.getRequestDispatcher("response.jsp").forward(request, response);
        }
    }
}
