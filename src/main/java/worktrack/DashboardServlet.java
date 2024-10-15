package worktrack;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
	private String userid;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String duration = request.getParameter("duration");
        String employeeId = request.getParameter("employee");
        String projectId = request.getParameter("project");
        List<DataPoint> dataPoints = new ArrayList<>();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtb", "root", "Tarak@21737");

            String query = buildQuery(duration, employeeId, projectId);
            ps = con.prepareStatement(query);
            
            int paramIndex = 1;

            if (!"all".equals(employeeId)) {
                ps.setString(paramIndex++, employeeId);
            }

            if (!"all".equals(projectId)) {
                ps.setString(paramIndex, projectId);
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                DataPoint dataPoint = new DataPoint();
                if ("all".equals(employeeId) && "all".equals(projectId)) {
                    dataPoint.setLabel(rs.getString("date"));
                } else if ("all".equals(employeeId)) {
                    dataPoint.setLabel(rs.getString("userid"));
                } else if ("all".equals(projectId)) {
                    dataPoint.setLabel(rs.getString("projectName"));
                } else {
                    dataPoint.setLabel(rs.getString("date"));
                }
                dataPoint.setHours(rs.getFloat("hours"));
                dataPoints.add(dataPoint);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, con);
        }

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(serializeDataPoints(dataPoints));
        out.flush();
    }

    private String buildQuery(String duration, String employeeId, String projectId) {
        String query = "SELECT ";

        switch (duration) {
            case "daily":
                //query += "date, ";
                break;
            case "weekly":
                //query += "date, ";
                break;
            case "monthly":
                //query += "DATE_FORMAT(date, '%Y-%m') as date, ";
                break;
            default:
                throw new IllegalArgumentException("Invalid duration: " + duration);
        }
        switch (duration) {
        case "daily":
        if ("all".equals(employeeId) && "all".equals(projectId)) {
            query += "projectName as date,SUM(duration) as hours FROM data WHERE 1=1 ";
        } else if ("all".equals(employeeId)) {
            query += "userid , SUM(duration) as hours FROM data WHERE projectName = ? ";
        } else if ("all".equals(projectId)) {
            query += "projectName, SUM(duration) as hours FROM data WHERE userid = ? ";
        } else {
            query += " DATE_FORMAT(date, '%Y-%m') AS date,SUM(duration) as hours FROM data WHERE userid = ? AND projectName = ? ";
        }
        break;
        case "weekly":
        	if ("all".equals(employeeId) && "all".equals(projectId)) {
                query += "CONCAT(projectName, '  ', DATE_FORMAT(date, '%Y-%m-%d')) as date,SUM(duration) as hours FROM data WHERE 1=1 ";
            } else if ("all".equals(employeeId)) {
            	 
                query += "CONCAT(userid, ' ', DATE_FORMAT(date, '%Y-%m-%d')) AS userid ,SUM(duration) AS hours FROM data WHERE projectName = ? ";
            } else if ("all".equals(projectId)) {
                query += "CONCAT(projectName, ' ', DATE_FORMAT(date, '%Y-%m-%d')) AS projectName, SUM(duration) as hours FROM data WHERE userid = ? ";
            } else {
                query += " CONCAT(projectName, ' ', DATE_FORMAT(date, '%Y-%m-%d')) AS date,projectName,SUM(duration) as hours FROM data WHERE userid = ? AND projectName = ? ";
            }
        	break;
        case "monthly":
        	if ("all".equals(employeeId) && "all".equals(projectId)) {
                query += "CONCAT(projectName, '  ', DATE_FORMAT(date, '%Y-%m')) as date,SUM(duration) as hours FROM data WHERE 1=1 ";
            } else if ("all".equals(employeeId)) {
            	 
                query += "CONCAT(userid, ' ', DATE_FORMAT(date, '%Y-%m')) AS userid ,SUM(duration) AS hours FROM data WHERE projectName = ? ";
            } else if ("all".equals(projectId)) {
                query += "CONCAT(projectName, ' ', DATE_FORMAT(date, '%Y-%m')) AS projectName, SUM(duration) as hours FROM data WHERE userid = ? ";
            } else {
                query += " CONCAT(projectName, ' ', DATE_FORMAT(date, '%Y-%m')) AS date,projectName,SUM(duration) as hours FROM data WHERE userid = ? AND projectName = ? ";
            }
        	break;
        	
        	
        	
        	
        }
        	
        	
        	
        	

        switch (duration) {
            case "daily":
            	if ("all".equals(employeeId) && "all".equals(projectId)) {
                    query += "GROUP BY projectName";
                } else if ("all".equals(employeeId)) {
                    query += "GROUP BY userid";
                } else if ("all".equals(projectId)) {
                    query += "GROUP BY projectName";
                } else {
                    query += "GROUP BY date";
                }
                break; 
            case "weekly":
                if ("all".equals(employeeId) && "all".equals(projectId)) {
                    query += "and date >= CURDATE() - INTERVAL 7 DAY GROUP BY projectName ,DATE_FORMAT(date, '%Y-%m-%d') ";
                } else if ("all".equals(employeeId)) {
                    query += "and date >= CURDATE() - INTERVAL 7 DAY GROUP BY userid,DATE_FORMAT(date, '%Y-%m-%d')";
                } else if ("all".equals(projectId)) {
                    query += "and date >= CURDATE() - INTERVAL 7 DAY GROUP BY projectName,DATE_FORMAT(date, '%Y-%m-%d')";
                } else {
                    query += "and date >= CURDATE() - INTERVAL 7 DAY GROUP BY DATE_FORMAT(date, '%Y-%m-%d')";
                }
                break;
            case "monthly":
                if ("all".equals(employeeId) && "all".equals(projectId)) {
                    query += "GROUP BY  projectName, DATE_FORMAT(date, '%Y-%m');";
                	//query += "GROUP BY projectName";
                } else if ("all".equals(employeeId)) {
                    query += "GROUP BY userid,DATE_FORMAT(date, '%Y-%m');";
                } else if ("all".equals(projectId)) {
                    query += "GROUP BY projectName,DATE_FORMAT(date, '%Y-%m');";
                } else {
                    query += "GROUP BY projectName, DATE_FORMAT(date, '%Y-%m');";
                }
                break;
        }
System.out.print(query);
        return query;
    }

    private void closeResources(ResultSet rs, PreparedStatement ps, Connection con) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String serializeDataPoints(List<DataPoint> dataPoints) {
        StringBuilder json = new StringBuilder();
        json.append("[");

        boolean first = true;
        for (DataPoint dataPoint : dataPoints) {
            if (!first) {
                json.append(",");
            }
            json.append("{\"date\":\"").append(dataPoint.getLabel()).append("\",")
                .append("\"hours\":").append(dataPoint.getHours()).append("}");
            first = false;
        }

        json.append("]");
        return json.toString();
    }

    class DataPoint {
        private String label;
        private Float hours;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public Float getHours() {
            return hours;
        }

        public void setHours(Float hours) {
            this.hours = hours;
        }
    }
}
