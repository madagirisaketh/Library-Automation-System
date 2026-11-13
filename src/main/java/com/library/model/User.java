package com.library.model;

public class User {
    private int userId, studentId;
    private String username, password, role, status, studentName;

    public int getUserId() { return userId; }
    public void setUserId(int v) { userId = v; }
    public int getStudentId() { return studentId; }
    public void setStudentId(int v) { studentId = v; }
    public String getUsername() { return username; }
    public void setUsername(String v) { username = v; }
    public String getPassword() { return password; }
    public void setPassword(String v) { password = v; }
    public String getRole() { return role; }
    public void setRole(String v) { role = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { status = v; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String v) { studentName = v; }
}