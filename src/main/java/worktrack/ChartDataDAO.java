package worktrack;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.sql.DataSource;

public class ChartDataDAO {
    private DataSource dataSource;

    public ChartDataDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, Object> getPieChartData(String userId, String date) throws SQLException {
        String query = "SELECT label, duration FROM pie_chart_data WHERE user_id = ? AND date = ?";
        Map<String, Object> chartData = new HashMap<>();
        List<String> labels = new ArrayList<>();
        List<Double> durations = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId);
            stmt.setString(2, date);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    labels.add(rs.getString("label"));
                    durations.add(rs.getDouble("duration"));
                }
            }
        }

        chartData.put("labels", labels);
        chartData.put("durations", durations);
        return chartData;
    }

    public Map<String, Object> getMonthlyChartData(String userId, String month) throws SQLException {
        String query = "SELECT label, duration FROM monthly_chart_data WHERE user_id = ? AND month = ?";
        Map<String, Object> chartData = new HashMap<>();
        List<String> labels = new ArrayList<>();
        List<Double> durations = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId);
            stmt.setString(2, month);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    labels.add(rs.getString("label"));
                    durations.add(rs.getDouble("duration"));
                }
            }
        }

        chartData.put("labels", labels);
        chartData.put("durations", durations);
        return chartData;
    }

    public Map<String, Object> getWeeklyChartData(String userId, String week) throws SQLException {
        String query = "SELECT label, duration FROM weekly_chart_data WHERE user_id = ? AND week = ?";
        Map<String, Object> chartData = new HashMap<>();
        List<String> labels = new ArrayList<>();
        List<Double> durations = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId);
            stmt.setString(2, week);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    labels.add(rs.getString("label"));
                    durations.add(rs.getDouble("duration"));
                }
            }
        }

        chartData.put("labels", labels);
        chartData.put("durations", durations);
        return chartData;
    }

    public List<String> getDatesByMonth(String userId, String month) throws SQLException {
        String query = "SELECT DISTINCT date FROM pie_chart_data WHERE user_id = ? AND month = ?";
        List<String> dates = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId);
            stmt.setString(2, month);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dates.add(rs.getString("date"));
                }
            }
        }

        return dates;
    }
}

