package worktrack;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DataServlet")
public class DataServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String userId = request.getParameter("userid");

        if ("getDataForDate".equals(action)) {
            String date = request.getParameter("date");
            request.getRequestDispatcher("getData.jsp?date=" + date + "&userid=" + userId).forward(request, response);
        } else if ("getMonthlyData".equals(action)) {
            String month = request.getParameter("month");
            request.getRequestDispatcher("getMonthlyData.jsp?month=" + month + "&userid=" + userId).forward(request, response);
        } else if ("getWeeklyData".equals(action)) {
            String week = request.getParameter("week");
            request.getRequestDispatcher("getWeeklyData.jsp?week=" + week + "&userid=" + userId).forward(request, response);
        } else if ("getDatesByMonth".equals(action)) {
            String month = request.getParameter("month");
            request.getRequestDispatcher("getDatesByMonth.jsp?month=" + month + "&userid=" + userId).forward(request, response);
        }
    }
}
