package worktrack;
public class UserDetail {
    private String userId;
    private String employeeName;

    public UserDetail(String userId, String employeeName) {
        this.userId = userId;
        this.employeeName = employeeName;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return employeeName;
    }
}
