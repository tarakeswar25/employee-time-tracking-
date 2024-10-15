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

import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/FetchEmployeeDatas")
public class FetchEmployeeData extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeId = request.getParameter("employeeId");
        JSONObject jsonResponse = new JSONObject();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtb", "root", "Tarak@21737");
            ps = con.prepareStatement("SELECT employeeName, role, projectName FROM javv WHERE userid = ?");
            ps.setString(1, employeeId);
            rs = ps.executeQuery();

            if (rs.next()) {
                jsonResponse.put("employeeName", rs.getString("employeeName"));
                jsonResponse.put("role", rs.getString("role"));
                jsonResponse.put("projectName", rs.getString("projectName"));
                System.out.println(rs.getString("employeeName"));
            }
        } catch (SQLException | ClassNotFoundException | JSONException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.flush();
    }
}
