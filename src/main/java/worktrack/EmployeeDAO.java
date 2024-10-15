package worktrack;import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/mtb", "root", "Tarak@21737");
    }

    public Employee getEmployeeById(String userid) throws SQLException {
        String query = "SELECT userid, employeeName, role, projectName FROM javv WHERE userid = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, userid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Employee(
                    rs.getString("userid"),
                    rs.getString("employeeName"),
                    rs.getString("role"),
                    rs.getString("projectName")
                );
            }
            return null;
        }
    }

    public List<String> getProjectNames() throws SQLException {
        String query = "SELECT role FROM projects WHERE role IS NOT NULL";
        List<String> projectNames = new ArrayList<>();
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                projectNames.add(rs.getString("role"));
            }
        }
        return projectNames;
    }
}
