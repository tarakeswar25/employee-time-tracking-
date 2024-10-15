package worktrack;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ProjServlet")
public class ProjectsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeId = request.getParameter("employeeId");
        StringBuilder json = new StringBuilder();
        json.append("[");

        if (employeeId == null || employeeId.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid employee ID");
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtb", "root", "Tarak@21737");
             PreparedStatement ps = prepareStatement(con, employeeId);
             ResultSet rs = ps.executeQuery()) {

            boolean first = true;
            while (rs.next()) {
                if (!first) {
                    json.append(",");
                }
                json.append("{\"name\":\"").append(rs.getString("projectName")).append("\"}");
                first = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            return;
        }

        json.append("]");
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            out.print(json.toString());
        }
    }

    private PreparedStatement prepareStatement(Connection con, String employeeId) throws SQLException {
        String query;
        if ("all".equalsIgnoreCase(employeeId)) {
            query = "SELECT DISTINCT projectName FROM projects WHERE projectName IS NOT NULL";
            return con.prepareStatement(query);
        } else {
            query = "SELECT projectName FROM javv WHERE userid = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, employeeId);
            return ps;
        }
    }
}
