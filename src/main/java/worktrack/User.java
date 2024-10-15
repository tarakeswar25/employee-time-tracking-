package worktrack;

public class User {
    private String userid;
    private String employeeName;
    private String role;

    // Constructor
    public User(String userid, String employeeName, String role) {
        this.userid = userid;
        this.employeeName = employeeName;
        this.role = role;
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
}
