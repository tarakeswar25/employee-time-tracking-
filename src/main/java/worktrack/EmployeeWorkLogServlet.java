package worktrack;
import java.io.IOException;
import java.text.DecimalFormat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;


@WebServlet("/EmployWorkLog")
public class EmployeeWorkLogServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get form parameters
        String employeeId = request.getParameter("name"); // assuming 'name' holds the employee ID
        String projectName = request.getParameter("projectName");
        String date = request.getParameter("date");
        String timeInput = request.getParameter("startTime");
        String timeOutput = request.getParameter("endTime");
        String taskCategory = request.getParameter("taskCategory");
        String description = request.getParameter("description");
        String pos=request.getParameter("pos");

        Connection con = null;
        PreparedStatement ps = null;
        
        String[] parts = timeInput.split(" ");
        String timePart = parts[0];
        String periodPart = parts[1];
        
        
        
        String[] timeParts = timePart.split(":");
        float hours = Integer.parseInt(timeParts[0]);
        float minutes = Integer.parseInt(timeParts[1]);
        
        
        
        if (periodPart.equalsIgnoreCase("PM") && hours < 12) {
            hours =hours+ 12 ;
        } else if (periodPart.equalsIgnoreCase("AM") && hours == 12) {
            hours = 0;
        }
        
        String[] parts1 = timeOutput.split(" ");
        String timePart1 = parts1[0];
        String periodPart1 = parts1[1];
        
        
        
        
        String[] timeParts1 = timePart1.split(":");
        float hours1 = Integer.parseInt(timeParts1[0]);
        float minutes1 = Integer.parseInt(timeParts1[1]);

        // Adjust hours based on the AM/PM period
        if (periodPart1.equalsIgnoreCase("PM") && hours1 < 12) {
            hours1 += 12;
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtb", "root", "Tarak@21737");
            String sql1 = "SELECT * FROM data where userid=? AND date = ?";
            
            //String selectQuery = "SELECT startTime, endTime FROM data WHERE empid = ?";
            ps = con.prepareStatement(sql1);
            ps.setString(1, employeeId);
            ps.setString(2, date);
            
            float mx=0;
            
            ResultSet rs =ps.executeQuery();
            boolean overlapFound = false;
            boolean timeexeds = false;
            float exeds=0;
            
            while (rs.next()) {
            	mx=mx+rs.getFloat("duration");
            	int ps1=rs.getInt("pos");
            	int ps2=Integer.parseInt(pos);
            	
            	
            	
            	
            	
            	if (ps1!=ps2) {
            		float tm=rs.getFloat("duration");
                    exeds=exeds+tm;

            	
            	float existingStartTime = rs.getFloat("startTime");
                float existingEndTime = rs.getFloat("endTime");
                
                
                
                
                
                if ((tst > existingStartTime && tst < existingEndTime) ||
                        (tend > existingStartTime && tend < existingEndTime) ||
                        (tst < existingStartTime && tend > existingEndTime)|| df<0) {
                        overlapFound = true;
                        break;
                    }
                
            	}
            	
            	
            }
            
            //float vla=mx;
            String mxs = Float.toString(mx);
            //String[] vla = mxs.split(".");
            
         /*   float hours1 = Integer.parseInt(timeParts1[0]);
            float minutes1 = Integer.parseInt(timeParts1[1]);

            
            
            float hours2 = Integer.parseInt(vla[0]);
            /*float minutes2 = Integer.parseInt(vla[1]);
            float tl= hours2 + minutes2+df;
            if (tl>480) {
            	overlapFound = true;
            	
            }
            
            
            		*/
            
            
            
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
            
            
            
            
            System.out.print(mxs.split("."));
            
            
            
        	
        	
            if (overlapFound) {
                response.getWriter().println("Error: The time overlaps with an existing entry or INVALID TIME STAMP");
            } else if(timeexeds){
                response.getWriter().println("Time exeds more than 8 hours.");

            }
            else {
        
        
        
        
        
        
        
        
        

        
        
        
        
        
        
        
        
            	DecimalFormat dff = new DecimalFormat("0.00");

            	// Format the float value and parse it back to float
            	float formattedTst = Float.parseFloat(dff.format(tst));

            	// Set the formatted float value in the PreparedStatement
            	
        
        
  

            // Update the employee work log in the database
            String query = "UPDATE data SET projectName = ?,duration=?, date = ?, startTime = ?, endTime = ?, taskCategory = ?, description = ? WHERE userid = ? and pos = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, projectName);
            ps.setFloat(2, df);
            ps.setString(3, date);
            ps.setFloat(4, formattedTst);           
            ps.setFloat(5,tend);
            ps.setString(6, taskCategory);
            ps.setString(7, description);
            ps.setString(8, employeeId);
            ps.setString(9, pos);
            //System.out.print(projectName+df+date+tst+description+pos+employeeId);
            //System.out.print(vla[0]);

            int result = ps.executeUpdate();

            if (result > 0) {
                // Redirect to success page or next record
                response.sendRedirect("Updateuser.jsp");
            } else {
                // Handle update failure
                response.getWriter().println("No Updates found");
            }
        
        }  } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
}