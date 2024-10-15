package worktrack;

import worktrack.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userid = request.getParameter("userid");
        String userName = request.getParameter("userName");
        String project = request.getParameter("project");
        String role = request.getParameter("role");
        String type = request.getParameter("type");

        String message;
        String messageType;

        try {
            if (userDAO.isUserExists(userid)) {
                message = "User ID already exists!";
                messageType = "error";
            } else {
                if (userDAO.addUser(userid, userName, project, role, type)) {
                    message = "User details added successfully!";
                    messageType = "success";
                } else {
                    message = "Failed to add user details!";
                    messageType = "error";
                }
            }
        } catch (Exception e) {
            message = "Error updating user details: " + e.getMessage();
            messageType = "error";
        }

        request.setAttribute("message", message);
        request.setAttribute("messageType", messageType);
        request.getRequestDispatcher("reg.jsp").forward(request, response);
    }
}
