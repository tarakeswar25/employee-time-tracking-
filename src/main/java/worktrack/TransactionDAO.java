package worktrack;
//TransactionDAO.java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

 private Connection getConnection() throws SQLException {
     return DriverManager.getConnection("jdbc:mysql://localhost:3306/mtb", "root", "Tarak@21737");
 }

 public List<Transaction> getTransactionsByUserId(String userId) {
     List<Transaction> transactions = new ArrayList<>();
     String query = "SELECT * FROM data WHERE userid = ?";

     try (Connection con = getConnection();
          PreparedStatement ps = con.prepareStatement(query)) {

         ps.setString(1, userId);
         ResultSet rs = ps.executeQuery();

         while (rs.next()) {
             float st = rs.getFloat("startTime");
             float en = rs.getFloat("endTime");
             Transaction transaction = new Transaction(
                 rs.getString("userid"),
                 rs.getString("employeeName"),
                 rs.getString("role"),
                 rs.getString("projectName"),
                 rs.getDate("date"),
                 st,
                 en,
                 rs.getString("taskCategory"),
                 rs.getString("description"),
                 rs.getFloat("duration")
             );
             transactions.add(transaction);
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }

     return transactions;
 }
}
