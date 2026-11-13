package com.library.model;

public class Borrow {
    private int borrowId, studentId, bookId, daysOverdue;
    private String borrowDate, dueDate, returnDate, status;
    private String studentCode, studentName, bookTitle, isbn;

    public int getBorrowId() { return borrowId; }
    public void setBorrowId(int v) { borrowId = v; }
    public int getStudentId() { return studentId; }
    public void setStudentId(int v) { studentId = v; }
    public int getBookId() { return bookId; }
    public void setBookId(int v) { bookId = v; }
    public int getDaysOverdue() { return daysOverdue; }
    public void setDaysOverdue(int v) { daysOverdue = v; }
    public String getBorrowDate() { return borrowDate; }
    public void setBorrowDate(String v) { borrowDate = v; }
    public String getDueDate() { return dueDate; }
    public void setDueDate(String v) { dueDate = v; }
    public String getReturnDate() { return returnDate; }
    public void setReturnDate(String v) { returnDate = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { status = v; }
    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String v) { studentCode = v; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String v) { studentName = v; }
    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String v) { bookTitle = v; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String v) { isbn = v; }

    public boolean isOverdue() {
        return "BORROWED".equals(status) && daysOverdue > 0;
    }

    public String getCoverUrl() {
        if (isbn == null || isbn.trim().isEmpty()) return "";
        return "https://covers.openlibrary.org/b/isbn/" + isbn.replaceAll("[^0-9Xx]", "") + "-M.jpg?default=false";
    }
}