package worktrack;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/adminActions")
public class AdminActionsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userid = request.getParameter("userid");
        System.out.print(userid+"userid");
        try {
            User user = userDAO.getUserById(userid);
            if (user != null) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("/adminActions.jsp").forward(request, response);
            } else {
                // Handle case where user is not found
                response.sendRedirect("UserNotFound.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception properly
            response.sendRedirect("Error.jsp"); // Redirect to an error page
        }
    }
}
