package worktrack;
import java.util.List;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("user");
        String password = request.getParameter("pass");
        
        User val = userDAO.validate(username, password);
        HttpSession session = request.getSession();
        session.setAttribute("userid", val.getUserid());
        session.setAttribute("employeeName",val.getEmployeeName());
        System.out.println(val.getRole());
        switch (val.getRole()) {
	        
            case "null":
                response.sendRedirect("Failure.jsp" );
                break;
            case "user":
            	Userinfo info=userDAO.getinfo(username);
            	//HttpSession session1 = request.getSession();
            	session.setAttribute("userid", info.getUserid());
                session.setAttribute("employeeName",info.getEmployeeName());
                session.setAttribute("projectName", info.getprojectName());
                session.setAttribute("role",info.getRole());
                session.setAttribute("userpos",info.getUserpos1());
                System.out.print(info.getRole());
                List<Allprojects> projectsList = userDAO.getprojects();
                session.setAttribute("projectsList", projectsList);
                System.out.print(projectsList);
                //request.getRequestDispatcher("employeeForm.jsp").forward(request, response);
                
                
                List<AllRoles> RolesList = userDAO.getRoles();
                session.setAttribute("RolesList", RolesList);
               // System.out.print(Roles);
                
                
                
                
                List<AllCategory> CategoryList = userDAO.getCategory();
                session.setAttribute("CategoryList", CategoryList);
               // System.out.print(Roles);
                System.out.print(projectsList);



                
            	
            	
            	
            	
            	
                if (username.equals(password)) {
                    response.sendRedirect("reset.jsp" );
                } else {
                	
                    response.sendRedirect("adminActions.jsp" );
                }
                break;
            case "admin":
            	List<Allprojects> projectsLis = userDAO.getprojects();
                session.setAttribute("projectsList", projectsLis);
                
                
                List<AllRoles> RolesLis = userDAO.getRoles();
                session.setAttribute("RolesList", RolesLis);
                response.sendRedirect("Adminpage.jsp" );
                break;
            default:
                response.sendRedirect("Failure.jsp" );
                break;
        }
    }
}
