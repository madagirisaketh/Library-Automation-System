package com.library.model;

public class Category {
    private int categoryId, bookCount;
    private String name;

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int v) { categoryId = v; }
    public String getName() { return name; }
    public void setName(String v) { name = v; }
    public int getBookCount() { return bookCount; }
    public void setBookCount(int v) { bookCount = v; }
}