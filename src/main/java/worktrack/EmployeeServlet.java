package worktrack;import java.io.IOException;import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/employee")
public class EmployeeServlet extends HttpServlet {

    private EmployeeDAO employeeDAO = new EmployeeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userid = request.getParameter("userid");
        try {
            Employee employee = employeeDAO.getEmployeeById(userid);
            List<String> projectNames = employeeDAO.getProjectNames();

            // Store employee and project names in the session
            HttpSession session = request.getSession();
            session.setAttribute("employee", employee);
            session.setAttribute("projectNames", projectNames);
            
            request.getRequestDispatcher("/WEB-INF/views/employeeForm.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
