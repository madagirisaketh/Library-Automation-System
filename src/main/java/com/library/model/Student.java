package com.library.model;

public class Student {
    private int studentId;
    private String code, firstName, lastName, email, contact, department, course, status;

    public int getStudentId() { return studentId; }
    public void setStudentId(int v) { studentId = v; }
    public String getCode() { return code; }
    public void setCode(String v) { code = v; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String v) { firstName = v; }
    public String getLastName() { return lastName; }
    public void setLastName(String v) { lastName = v; }
    public String getEmail() { return email; }
    public void setEmail(String v) { email = v; }
    public String getContact() { return contact; }
    public void setContact(String v) { contact = v; }
    public String getDepartment() { return department; }
    public void setDepartment(String v) { department = v; }
    public String getCourse() { return course; }
    public void setCourse(String v) { course = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { status = v; }

    public String getFullName() {
        return (firstName == null ? "" : firstName) + (lastName == null ? "" : " " + lastName);
    }
}