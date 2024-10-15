package worktrack;
//Transaction.java// Transaction.java
import java.util.Date;

public class Transaction {
    private String userId;
    private String employeeName;
    private String role;
    private String projectName;
    private Date date;
    private float startTime;
    private float endTime;
    private String taskCategory;
    private String description;
    private float duration;

    // Constructor
    public Transaction(String userId, String employeeName, String role, String projectName, Date date, float startTime, float endTime, String taskCategory, String description, float duration) {
        this.userId = userId;
        this.employeeName = employeeName;
        this.role = role;
        this.projectName = projectName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.taskCategory = taskCategory;
        this.description = description;
        this.duration = duration;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getStartTime() {
        return startTime;
    }

    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }

    public float getEndTime() {
        return endTime;
    }

    public void setEndTime(float endTime) {
        this.endTime = endTime;
    }

    public String getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(String taskCategory) {
        this.taskCategory = taskCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }
}
