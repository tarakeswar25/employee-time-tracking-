<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.json.*" %>

<%
    String month = request.getParameter("month");
    String userId = request.getParameter("userid");

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    JSONObject jsonResponse = new JSONObject();

    // Database connection details
    String url = "jdbc:mysql://localhost:3306/mtb"; // replace 'yourDatabaseName' with the actual database name
    String username = "root"; // replace 'yourUsername' with your database username
    String password = "Tarak@21737"; // replace 'yourPassword' with your database password

    try {
    	Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, username, password);
        stmt = conn.createStatement();
        String query = "SELECT DISTINCT DATE_FORMAT(date, '%Y-%m-%d') as formatted_date " +
                       "FROM data WHERE DATE_FORMAT(date, '%Y-%m') = '" + month + "' AND userId = '" + userId + "'";
        rs = stmt.executeQuery(query);

        JSONArray datesArray = new JSONArray();
        while (rs.next()) {
            datesArray.put(rs.getString("formatted_date"));
        }

        jsonResponse.put("dates", datesArray);
    } catch (Exception e) {
        e.printStackTrace();
        jsonResponse.put("error", e.getMessage());
    } finally {
        if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }

    response.setContentType("application/json");
    response.getWriter().write(jsonResponse.toString());
%>
