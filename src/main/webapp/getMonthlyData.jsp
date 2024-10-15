<%@ page import="java.sql.*" %>
<%@ page import="org.json.*" %>
<%
    String month = request.getParameter("month");
    String userId = request.getParameter("userid");
    String url = "jdbc:mysql://localhost:3306/mtb";
    String username = "root";
    String password = "Tarak@21737";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    JSONObject json = new JSONObject();

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, username, password);
        stmt = conn.createStatement();
        String query = "SELECT date, SUM(duration) as total_duration " +
                       "FROM data WHERE DATE_FORMAT(date, '%Y-%m') = '" + month + "' " +
                       "AND userid = '" + userId + "' GROUP BY date";
        rs = stmt.executeQuery(query);

        JSONArray labels = new JSONArray();
        JSONArray durations = new JSONArray();

        while (rs.next()) {
            labels.put(rs.getString("date"));
            durations.put(rs.getDouble("total_duration"));
        }

        json.put("labels", labels);
        json.put("durations", durations);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }

    response.setContentType("application/json");
    response.getWriter().write(json.toString());
%>
