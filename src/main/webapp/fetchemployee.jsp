<%@ page import="java.io.*, org.json.JSONObject" %>

<%@ page import="java.sql.*" %>

<%
    String employeeId = request.getParameter("employeeId");
    JSONObject jsonResponse = new JSONObject();

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        // Load the database driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Database connection details
        String url = "jdbc:mysql://localhost:3306/mtb";
        String user = "root";
        String password = "Tarak@21737";

        con = DriverManager.getConnection(url, user, password);

        // Prepare and execute the SQL query
        String sql = "SELECT employeeName, role, projectName FROM javv WHERE userid = ?";
        ps = con.prepareStatement(sql);
        ps.setString(1, employeeId);
        rs = ps.executeQuery();

        if (rs.next()) {
            jsonResponse.put("employeeName", rs.getString("employeeName"));
            jsonResponse.put("role", rs.getString("role"));
            jsonResponse.put("projectName", rs.getString("projectName"));
        }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out1 = response.getWriter();
    out1.print(jsonResponse.toString());
    out1.flush();
%>
