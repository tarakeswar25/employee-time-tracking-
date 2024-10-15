package worktrack;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/insertCategoryServlet")
public class InsertCategoryServlet extends HttpServlet {
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categoryName = request.getParameter("categoryName");

        if (categoryName != null && !categoryName.trim().isEmpty()) {
            try {
                // Check if the category already exists
                if (userDAO.categoryExists(categoryName)) {
                    // Category already exists
                    response.setContentType("text/html");
                    response.getWriter().println("<html><head><title>Category Exists</title></head><body>");
                    response.getWriter().println("<script type='text/javascript'>");
                    response.getWriter().println("alert('Category already exists!');");
                    response.getWriter().println("window.location.href = 'insertprojects.jsp';");
                    response.getWriter().println("</script>");
                    response.getWriter().println("</body></html>");
                    return;
                }

                // Insert the new category
                userDAO.insertCategory(categoryName);

                // Success message and redirection
                response.setContentType("text/html");
                response.getWriter().println("<html><head><title>Success</title></head><body>");
                response.getWriter().println("<script type='text/javascript'>");
                response.getWriter().println("alert('Category inserted successfully!');");
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
            response.getWriter().println("<html><head><title>Empty Category</title></head><body>");
            response.getWriter().println("<script type='text/javascript'>");
            response.getWriter().println("alert('Category name cannot be empty!');");
            response.getWriter().println("window.location.href = 'insertprojects.jsp';");
            response.getWriter().println("</script>");
            response.getWriter().println("</body></html>");
        }
    }
}
