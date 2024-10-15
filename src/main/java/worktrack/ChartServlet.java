package worktrack;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/getData")
public class ChartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String date = request.getParameter("date");
        String userId = request.getParameter("userid");
        System.out.print(date+"date");

        // Fetch data from DAO
        ChartDAO dao = new ChartDAO(); // Use default constructor with DataSourceConfig
        Map<String, Double> data = new HashMap<>();
        try {
            data = dao.getChartData(date, userId);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Set HTTP status code 500
            response.getWriter().write("{\"error\":\"Internal Server Error\"}");
            return;
        }

        // Convert data to JSON
        Gson gson = new Gson();
        String json = gson.toJson(data);

        // Send JSON response
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    // Implement other servlets as needed (e.g., getMonthlyChartData, getWeeklyChartData)
}
