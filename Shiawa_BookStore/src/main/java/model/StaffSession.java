package model;

public class StaffSession {
    private int staffId;
    private String username;
    private String role; // e.g. Admin, Staff

    public StaffSession() {}

    public StaffSession(int staffId, String username, String role) {
        this.staffId = staffId;
        this.username = username;
        this.role = role;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return role != null && role.equalsIgnoreCase("admin");
    }
}




