package worktrack;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import com.google.gson.Gson;

@WebServlet("/getDatesByMonth")
public class DatesByMonthServlet extends HttpServlet {
    private ChartDataDAO chartDataDAO;

    @Override
    public void init() throws ServletException {
        DataSource dataSource = (DataSource) getServletContext().getAttribute("dataSource");
        chartDataDAO = new ChartDataDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = request.getParameter("userid");
        String month = request.getParameter("month");

        try {
            List<String> dates = chartDataDAO.getDatesByMonth(userId, month);
            String json = new Gson().toJson(dates);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        } catch (SQLException e) {
            throw new ServletException("Database access error.", e);
        }
    }
}
