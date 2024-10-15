<%@ page import="java.sql.*" %>
<%@ page import="org.json.*" %>
<%
    String week = request.getParameter("week");
    String userId = request.getParameter("userid");
    String url = "jdbc:mysql://localhost:3306/mtb";
    String username = "root";
    String password = "Tarak@21737";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    JSONObject jsonResponse = new JSONObject();
    JSONArray labels = new JSONArray();
    JSONArray durations = new JSONArray();

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, username, password);
        String query = "SELECT DATE_FORMAT(date, '%Y-%m-%d') as date, SUM(duration) as total_duration " +
                       "FROM data " +
                       "WHERE userid = ? AND date BETWEEN DATE_SUB(?, INTERVAL 6 DAY) AND ? " +
                       "GROUP BY DATE_FORMAT(date, '%Y-%m-%d')";
        pstmt = conn.prepareStatement(query);
        pstmt.setString(1, userId);
        pstmt.setString(2, week);
        pstmt.setString(3, week);
        rs = pstmt.executeQuery();

        while (rs.next()) {
            labels.put(rs.getString("date"));
            durations.put(rs.getDouble("total_duration"));
        }

        jsonResponse.put("labels", labels);
        jsonResponse.put("durations", durations);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }

    response.setContentType("application/json");
    out.print(jsonResponse.toString());
    out.flush();
%>
