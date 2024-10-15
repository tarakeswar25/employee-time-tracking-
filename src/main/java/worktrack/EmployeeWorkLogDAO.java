// EmployeeWorkLogDAO.java
package worktrack;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeWorkLogDAO {

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/mtb", "root", "Tarak@21737");
    }

    public ResultSet getEmployeeData(String employeeId, String date) throws SQLException, ClassNotFoundException {
        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM data WHERE userid = ? and date= ?");
        ps.setString(1, employeeId);
        ps.setString(2, date);
        return ps.executeQuery();
    }

    public ResultSet getProjectData(String employeeId) throws SQLException, ClassNotFoundException {
        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT projectName FROM javv WHERE userid = ?");
        ps.setString(1, employeeId);
        return ps.executeQuery();
    }
}
