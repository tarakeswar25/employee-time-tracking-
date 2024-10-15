package worktrack;

import java.io.IOException;


import java.sql.Connection;

import java.sql.ResultSet;



import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/submitWorkLog")
public class SubmitWorkLogServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String empid=request.getParameter("name");
        System.out.print(empid+"val");
    	String employeeName = request.getParameter("employeeName");
        String role = request.getParameter("role");
        String projectName = request.getParameter("projectName"); 
        String date = request.getParameter("date");
        String timeInput = request.getParameter("startTime");
        String timeOutput = request.getParameter("endTime");
        String taskCategory = request.getParameter("taskCategory");
        String description = request.getParameter("description");
        System.out.print(employeeName+"val"+role+"f");
        
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String[] parts = timeInput.split(" ");
        String timePart = parts[0];
        String periodPart = parts[1];

        // Further split the time part by colon to get hours and minutes
        String[] timeParts = timePart.split(":");
        float hours = Integer.parseInt(timeParts[0]);
        float minutes = Integer.parseInt(timeParts[1]);

        // Adjust hours based on the AM/PM period
        if (periodPart.equalsIgnoreCase("PM") && hours <= 12) {
            hours =hours+ 12 ;
        } else if (periodPart.equalsIgnoreCase("AM") && hours == 12) {
            hours = 0;
        }

        // Create a LocalTime object
        //LocalTime time = LocalTime.of(hours, minutes);
        

        String[] parts1 = timeOutput.split(" ");
        String timePart1 = parts1[0];
        String periodPart1 = parts1[1];

        // Further split the time part by colon to get hours and minutes
        String[] timeParts1 = timePart1.split(":");
        float hours1 = Integer.parseInt(timeParts1[0]);
        float minutes1 = Integer.parseInt(timeParts1[1]);
        

        // Adjust hours based on the AM/PM period
        if (periodPart1.equalsIgnoreCase("PM") && hours1 <= 12 ) {
        	if (hours1==12) {
        		hours1=12;
        	}
        	else {
            hours1 =hours1+12;}
        } else if (periodPart1.equalsIgnoreCase("AM") && hours1 == 12) {
            hours1 = 0;
        }
        float tst=hours+(minutes/100);
        int mtst=((int)hours*60)+(int)minutes;
        float tend=hours1+(minutes1/100);
        int mtend=((int)hours1*60)+(int)minutes1;
        
        float da=mtend-mtst;
        float db=da/60;
        
        float df=(int)db+(da%60)/100;
        try {
        	
        	
        	
        	
        	
        	

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtb", "root", "Tarak@21737");
            String sql1 = "SELECT * FROM data where userid=? and date=?";
            
            //String selectQuery = "SELECT startTime, endTime FROM data WHERE empid = ?";
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, empid);
            pstmt.setString(2, date);
            
            
            
            rs =pstmt.executeQuery();
            boolean overlapFound = false;
            boolean timeexeds = false;
            float exeds=0;
            while (rs.next()) {
            	float existingStartTime = rs.getFloat("startTime");
                float existingEndTime = rs.getFloat("endTime");
                float tm=rs.getFloat("duration");
                exeds=exeds+tm;
                
                
                
                
                
                System.out.print(exeds);
                if ((tst >= existingStartTime && tst <= existingEndTime) ||
                        (tend >= existingStartTime && tend <= existingEndTime) ||
                        (tst <= existingStartTime && tend >= existingEndTime)) {
                	
                	
                        overlapFound = true;
                        System.out.print(tst +"  "+ existingStartTime +"  "+ tst +"  "+ existingEndTime+"  "+ "  "+tend +"  "+ existingStartTime +"  "+ tend +""+ existingEndTime +""+
                                tst +"  "+ existingStartTime +"  "+ tend +"  "+ existingEndTime+""+tm);
                        break;
                    }
                
            	
            	
            	
            }
        	//if (rolee) {
        		//String sq1="select role from javv where uderid=?";
        		//pstmt = conn.prepareStatement(sq1);
        		
        		
        		
        	//}
            String fnd = Float.toString(exeds);
            /*System.out.print(fnd.split("\\.")[0]);
           */ 
            
           
            String[] fndgt = fnd.split("\\.");
            int hr = Integer.parseInt(fndgt[0]);
            int mn = Integer.parseInt(fndgt[1]);
            //System.out.print(hr*60+""+mn);
            
            int out=(hr*60)+mn +(int)da;
            if (out>480) {
            	timeexeds=true;
            	System.out.print("greater value"+out);
            }
       
           
            
        	
        	
            if (overlapFound) {
            	//System.out.print(tst+""+tend);
            	
                response.getWriter().println("Error: The time overlaps with an existing entry.");
            } else if(timeexeds){
                response.getWriter().println("Time exeds more than 8 hours.");

            }
            else {
            	
            
        	
        	
        	
        	
            // Load the JDBC driver
            //Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtb", "root", "Tarak@21737");

            // Prepare the SQL statement
            String sql = "INSERT INTO data (userid,employeeName, role, projectName, date, startTime, endTime, taskCategory, description,duration) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,empid);
            pstmt.setString(2, employeeName);
            pstmt.setString(3, role);
            pstmt.setString(4, projectName);
            pstmt.setString(5, date);
            pstmt.setFloat(6,tst );
            pstmt.setFloat(7, tend);
            pstmt.setString(8, taskCategory);
            pstmt.setString(9, description);
            pstmt.setFloat(10,df );

            // Execute the statement
            int rows = pstmt.executeUpdate();

            // Check if the insert was successful
            if (rows > 0) {
                response.getWriter().println("Response submitted successfully!");
                response.sendRedirect("employeeForm.jsp");
            } else {
                response.getWriter().println("Failed to submit your response .");
            }
        } }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("An error occurred: " + e.getMessage());
        } finally {
            // Close the resources
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }	
    
    
}
