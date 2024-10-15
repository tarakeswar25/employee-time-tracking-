package worktrack;
import java.sql.*;
import java.util.*;
import org.json.*;

public class DataDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/mtb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Tarak@21737";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static List<String> getDistinctMonths(String userId) {
        List<String> months = new ArrayList<>();
        String query = "SELECT DISTINCT DATE_FORMAT(date, '%Y-%m') as month FROM data WHERE userid = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                months.add(rs.getString("month"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return months;
    }

    public static JSONArray getDataForDate(String date, String userId) {
        JSONArray jsonArray = new JSONArray();
        String query = "SELECT * FROM data WHERE date = ? AND userid = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, date);
            stmt.setString(2, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("label", rs.getString("projectName"));
                jsonObject.put("duration", rs.getDouble("duration"));
                jsonArray.put(jsonObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public static JSONArray getMonthlyData(String month, String userId) {
        JSONArray jsonArray = new JSONArray();
        String query = "SELECT * FROM data WHERE DATE_FORMAT(date, '%Y-%m') = ? AND userid = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, month);
            stmt.setString(2, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("label", rs.getString("projectName"));
                jsonObject.put("duration", rs.getDouble("duration"));
                jsonArray.put(jsonObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public static JSONArray getWeeklyData(String week, String userId) {
        JSONArray jsonArray = new JSONArray();
        String query = "SELECT * FROM data WHERE WEEK(date) = WEEK(?) AND YEAR(date) = YEAR(?) AND userid = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, week);
            stmt.setString(2, week);
            stmt.setString(3, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("label", rs.getString("projectName"));
                jsonObject.put("duration", rs.getDouble("duration"));
                jsonArray.put(jsonObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public static JSONArray getDatesByMonth(String month, String userId) {
        JSONArray jsonArray = new JSONArray();
        String query = "SELECT DISTINCT DATE_FORMAT(date, '%Y-%m-%d') as date FROM data WHERE DATE_FORMAT(date, '%Y-%m') = ? AND userid = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, month);
            stmt.setString(2, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                jsonArray.put(rs.getString("date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
}
