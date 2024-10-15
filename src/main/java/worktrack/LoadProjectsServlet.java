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

@WebServlet("/LoadProjectsServlet")
public class LoadProjectsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeId = request.getParameter("employeeId");
        StringBuilder json = new StringBuilder();
        json.append("[");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtb", "root", "Tarak@21737");

            String query;
            if ("all".equalsIgnoreCase(employeeId)) {
                query = "SELECT DISTINCT projectName FROM projects WHERE projectName IS NOT NULL";
                ps = con.prepareStatement(query);
            } else {
                query = "SELECT projectName FROM javv WHERE userid = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, employeeId);
            }

            rs = ps.executeQuery();
            boolean first = true;
            while (rs.next()) {
                if (!first) {
                    json.append(",");
                }
                json.append("{\"name\":\"").append(rs.getString("projectName")).append("\"}");
                first = false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Database error: " + e.getMessage() + "\"}");
            return;
        } finally {
            closeResources(rs, ps, con);
        }

        json.append("]");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json.toString());
        out.flush();
    }

    private void closeResources(ResultSet rs, PreparedStatement ps, Connection con) {
        if (rs != null) {
            try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        if (ps != null) {
            try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        if (con != null) {
            try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
