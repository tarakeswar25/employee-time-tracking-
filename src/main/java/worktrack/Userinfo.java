package worktrack;

public class Userinfo {
    private String userid;
    private String employeeName;
    private String role;
    private String projectName;
    private String pos1;;

    // Constructor
    public Userinfo(String userid, String employeeName, String role,String projectName, String pos1) {
        this.userid = userid;
        this.employeeName = employeeName;
        this.role = role;
        this.projectName=projectName;
        this.pos1=pos1;
    }

    // Getters
    public String getUserid() {
        return userid;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getRole() {
        return role;
    }
    public String getprojectName() {
        return projectName;
    }
    public String getUserpos1() {
        return pos1;
    }

    // Setters
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public void setprojectName(String projectName) {
        this.projectName = projectName;
    }
    public void setpos1(String pos1) {
        this.pos1 = pos1;
    }
}
