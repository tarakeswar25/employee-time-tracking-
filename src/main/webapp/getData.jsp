<%@ page import="java.sql.*" %>
<%@ page import="org.json.*" %>
<%
    String selectedDate = request.getParameter("date");
String userid = request.getParameter("userid");
    String url = "jdbc:mysql://localhost:3306/mtb";
    String username = "root";
    String password = "Tarak@21737";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    JSONObject jsonData = new JSONObject();
    JSONArray labels = new JSONArray();
    JSONArray durations = new JSONArray();

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, username, password);
        stmt = conn.createStatement();
        String query = "SELECT taskcategory, SUM(duration) AS total_duration " +
                       "FROM data " +
                       "WHERE date = '" + selectedDate + "' " +
                    		   "and userid = '" + userid + "' " +
                       "GROUP BY taskcategory";
        rs = stmt.executeQuery(query);

        while (rs.next()) {
            String category = rs.getString("taskcategory");
            double duration = rs.getDouble("total_duration");
            labels.put(category);
            durations.put(duration);
        }

        jsonData.put("labels", labels);
        jsonData.put("durations", durations);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(jsonData.toString());
%>
