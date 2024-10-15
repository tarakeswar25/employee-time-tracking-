package worktrack;
import java.sql.*;
import org.json.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/mtb";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "Tarak@21737";
    private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "MySQL JDBC Driver not found", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    }

    public User validate(String name, String pass) {
    	User val=null;
        String userType = "b"; // Default value for invalid credentials
        String query = "SELECT * FROM javv WHERE userid = ? AND pass = ?";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, pass);
            

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    String type = resultSet.getString("type");
                    String userid=resultSet.getString("userid");
                    String employeeName=resultSet.getString("employeeName");
                    
                    //userType = type.equals("admin") ? "c" : "a";
                    val= new User(userid,employeeName,type);
                    
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error validating user with userid: " + name, e);
        }
        return val;
    }
    public int Delete(String userid, int pos) {
        String query = "DELETE FROM data WHERE userid = ? AND pos = ?";
        int rowsAffected = 0; // Default value for invalid credentials

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, userid);
            ps.setInt(2, pos);
            rowsAffected = ps.executeUpdate();
                
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error validating user with userid: " + userid, e);
        }
        return rowsAffected;
    }

    
    
    public Userinfo getinfo(String userid) {
    	Userinfo res=null;
        //String projectName =  // Default value for invalid credentials
        String query = "SELECT * FROM javv WHERE userid = ?";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, userid);
           
            

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    String projectName = resultSet.getString("projectName");
                    String role=resultSet.getString("role");
                    String employeeName=resultSet.getString("employeeName");
                    String userpos=resultSet.getString("pos1");
                    
                    
                    
                    
                    
                    //userType = type.equals("admin") ? "c" : "a";
                    res= new Userinfo(userid,employeeName,projectName,role,userpos);
                    
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error validating user with userid: " + userid, e);
        }
        return res;
    }
    
    
    
    
    
    

    public List<Allprojects> getprojects() {
        List<Allprojects> projectsList = new ArrayList<>();
        String query = "SELECT projectName FROM projects WHERE projectName IS NOT NULL";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet resultSet = ps.executeQuery()) {

            while (resultSet.next()) {
                String projectName = resultSet.getString("projectName");
                Allprojects project = new Allprojects(projectName);
                projectsList.add(project);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "No projects", e);
        }
        System.out.print(projectsList);
        return projectsList;
    }
    
    
    
    public List<AllRoles> getRoles() {
        List<AllRoles> RolesList = new ArrayList<>();
        String query = "SELECT role FROM projects WHERE role IS NOT NULL";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet resultSet = ps.executeQuery()) {

            while (resultSet.next()) {
                String RoleName = resultSet.getString("role");
                AllRoles Role = new AllRoles(RoleName);
                RolesList.add(Role);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "No projects", e);
        }
        System.out.print(RolesList);
        return RolesList;
    }
    
    

    public List<AllCategory> getCategory() {
        List<AllCategory> CategoryList = new ArrayList<>();
        String query = "SELECT category FROM projects WHERE category IS NOT NULL";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet resultSet = ps.executeQuery()) {

            while (resultSet.next()) {
                String categoryName = resultSet.getString("category");
                AllCategory category = new AllCategory(categoryName);
                CategoryList.add(category);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "No projects", e);
        }
        System.out.print(CategoryList);
        return CategoryList;
    }

    
    
    
    
    
    
    
    
    
    
    // top code for all project names
    
    
    
    
    

    public User getUserById(String userid) {
        String query = "SELECT userid, employeeName, role FROM javv WHERE userid = ?";
        User user = null;

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, userid);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User(rs.getString("userid"), rs.getString("employeeName"), rs.getString("role"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving user with userid: " + userid, e);
        }
        return user;
    }

    public List<String> getDistinctMonths(String userId) {
        List<String> months = new ArrayList<>();
        String query = "SELECT DISTINCT DATE_FORMAT(date, '%Y-%m') as month FROM data WHERE userid = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    months.add(rs.getString("month"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving distinct months", e);
        }
        return months;
    }

    public JSONArray getDataForDate(String date, String userId) {
        JSONArray jsonArray = new JSONArray();
        String query = "SELECT * FROM data WHERE date = ? AND userid = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, date);
            stmt.setString(2, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("label", rs.getString("label"));
                    jsonObject.put("duration", rs.getDouble("duration"));
                    jsonArray.put(jsonObject);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving data for date: " + date, e);
        }
        return jsonArray;
    }

    public JSONArray getMonthlyData(String month, String userId) {
        JSONArray jsonArray = new JSONArray();
        String query = "SELECT * FROM data WHERE DATE_FORMAT(date, '%Y-%m') = ? AND userid = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, month);
            stmt.setString(2, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("label", rs.getString("label"));
                    jsonObject.put("duration", rs.getDouble("duration"));
                    jsonArray.put(jsonObject);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving monthly data for month: " + month, e);
        }
        return jsonArray;
    }

    public JSONArray getWeeklyData(String week, String userId) {
        JSONArray jsonArray = new JSONArray();
        String query = "SELECT * FROM data WHERE WEEK(date) = WEEK(?) AND YEAR(date) = YEAR(?) AND userid = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, week);
            stmt.setString(2, week);
            stmt.setString(3, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("label", rs.getString("label"));
                    jsonObject.put("duration", rs.getDouble("duration"));
                    jsonArray.put(jsonObject);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving weekly data for week: " + week, e);
        }
        return jsonArray;
    }

    public JSONArray getDatesByMonth(String month, String userId) {
        JSONArray jsonArray = new JSONArray();
        String query = "SELECT DISTINCT DATE_FORMAT(date, '%Y-%m-%d') as date FROM data WHERE DATE_FORMAT(date, '%Y-%m') = ? AND userid = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, month);
            stmt.setString(2, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    jsonArray.put(rs.getString("date"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving dates by month for month: " + month, e);
        }
        return jsonArray;
    }
    
    
    
    
    

    public boolean isUserExists(String userid) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            String checkSql = "SELECT COUNT(*) FROM javv WHERE userid = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(checkSql)) {
                pstmt.setString(1, userid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    return rs.next() && rs.getInt(1) > 0;
                }
            }
        }
    }

    public boolean addUser(String userid, String userName, String project, String role, String type) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            String insertSql = "INSERT INTO javv(userid, pass, employeeName, type, role, projectName) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setString(1, userid);
                pstmt.setString(2, userid);
                pstmt.setString(3, userName);
                pstmt.setString(4, type);
                pstmt.setString(5, role);
                pstmt.setString(6, project);
                return pstmt.executeUpdate() > 0;
            }
        }
    }
    
    
    public List<String> getDistinctUserIds() {
        List<String> userIds = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement ps = con.prepareStatement("SELECT DISTINCT userid FROM data");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                userIds.add(rs.getString("userid"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userIds;
    }

    public List<DataRecord> getDataRecords(String userId) {
        List<DataRecord> dataRecords = new ArrayList<>();
        String query = "SELECT * FROM data";
        if (userId != null && !userId.isEmpty()) {
            query += " WHERE userid = ?";
        }

        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            if (userId != null && !userId.isEmpty()) {
                ps.setString(1, userId);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DataRecord record = new DataRecord();
                    record.setUserId(rs.getString("userid"));
                    record.setEmployeeName(rs.getString("employeeName"));
                    record.setRole(rs.getString("role"));
                    record.setProjectName(rs.getString("projectName"));
                    record.setDate(rs.getDate("date"));
                    record.setStartTime(rs.getFloat("startTime"));
                    record.setEndTime(rs.getFloat("endTime"));
                    record.setTaskCategory(rs.getString("taskCategory"));
                    record.setDescription(rs.getString("description"));
                    record.setDuration(rs.getFloat("duration"));
                    record.setPos(rs.getInt("pos"));
                    dataRecords.add(record);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataRecords;
    }
    
    
    
    public boolean isProjectNameExists(String projectName) throws SQLException {
        String checkQuery = "SELECT COUNT(*) FROM projects WHERE projectName = ?";
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement checkPs = con.prepareStatement(checkQuery)) {
            checkPs.setString(1, projectName);
            try (ResultSet rs = checkPs.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public void insertProject(String projectName) throws SQLException {
        String insertQuery = "INSERT INTO projects (projectName) VALUES (?)";
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement ps = con.prepareStatement(insertQuery)) {
            ps.setString(1, projectName);
            ps.executeUpdate();
        }
    }
    public boolean roleExists(String roleName) throws SQLException {
        String query = "SELECT COUNT(*) FROM projects WHERE role = ?";
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, roleName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public void insertRole(String roleName) throws SQLException {
        String query = "INSERT INTO projects (role) VALUES (?)";
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, roleName);
            ps.executeUpdate();
        }
    }

    public boolean categoryExists(String categoryName) throws SQLException {
        String query = "SELECT COUNT(*) FROM projects WHERE category = ?";
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, categoryName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public void insertCategory(String categoryName) throws SQLException {
        String query = "INSERT INTO projects (category) VALUES (?)";
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, categoryName);
            ps.executeUpdate();
        }
    }
    
    
    public List<UserDetail>  getUsers() throws Exception {
        List<UserDetail> users = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement("SELECT userid, employeeName FROM javv WHERE type='user'");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String userId = rs.getString("userid");
                String userName = rs.getString("employeeName");
                users.add(new UserDetail(userId, userName));
            }
        }
        return users;
    }
    
    
    public boolean assignProject(String userId, String projectName, String role) {
        String sql = "UPDATE javv SET projectName = ?, role = ? WHERE userid = ?";
        boolean rowUpdated = false;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setString(1, projectName);
            pst.setString(2, role);
            pst.setString(3, userId);

            rowUpdated = pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowUpdated;
    }
    
    
    public List<Staff> getStaff(String selectedUserId) throws ClassNotFoundException, SQLException {
        List<Staff> staffList = new ArrayList<>();
        String query = "SELECT * FROM javv";
        if (selectedUserId != null && !selectedUserId.isEmpty()) {
            query += " WHERE userid = ?";
        }

        Class.forName("com.mysql.jdbc.Driver");
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {

            if (selectedUserId != null && !selectedUserId.isEmpty()) {
                ps.setString(1, selectedUserId);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Staff staff = new Staff();
                    staff.setUserId(rs.getString("userid"));
                    staff.setEmployeeName(rs.getString("employeeName"));
                    staff.setRole(rs.getString("role"));
                    staff.setProjectName(rs.getString("projectName"));
                    staff.setPos1(rs.getInt("pos1"));
                    staffList.add(staff);
                }
            }
        }
        return staffList;
    }

    public boolean deleteStaff(String userId, int pos) throws ClassNotFoundException, SQLException {
        String query = "DELETE FROM javv WHERE userid = ? AND pos1 = ?";
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, userId);
            ps.setInt(2, pos);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }  
    
    public Personnel getPersonnelById(String employeeId) {
        Personnel personnel = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "SELECT * FROM employees WHERE employee_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, employeeId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                personnel = new Personnel();
                personnel.setEmployeeId(resultSet.getString("employee_id"));
                personnel.setEmployeeName(resultSet.getString("employee_name"));
                personnel.setRole(resultSet.getString("role"));
                // Add more fields if needed
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(resultSet, preparedStatement, connection);
        }

        return personnel;
    }

}
