package worktrack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

public class ChartDAO {
    private DataSource dataSource;

    public ChartDAO() {
        this.dataSource = DataSourceConfig.getDataSource();
    }

    public ChartDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, Double> getChartData(String date, String userId) throws SQLException {
        String query = "SELECT label, duration FROM chart_data WHERE user_id = ? AND date = ?";
        Map<String, Double> chartData = new HashMap<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId);
            stmt.setString(2, date);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    chartData.put(rs.getString("label"), rs.getDouble("duration"));
                }
            }
        }

        return chartData;
    }
}
