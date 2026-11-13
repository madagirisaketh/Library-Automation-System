package com.library.model;

public class Book {
	private int bookId, categoryId, totalCopies, availableCopies;
	private String title, author, publisher, datePublished, status, isbn, categoryName;

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int v) {
		bookId = v;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int v) {
		categoryId = v;
	}

	public int getTotalCopies() {
		return totalCopies;
	}

	public void setTotalCopies(int v) {
		totalCopies = v;
	}

	public int getAvailableCopies() {
		return availableCopies;
	}

	public void setAvailableCopies(int v) {
		availableCopies = v;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String v) {
		title = v;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String v) {
		author = v;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String v) {
		publisher = v;
	}

	public String getDatePublished() {
		return datePublished;
	}

	public void setDatePublished(String v) {
		datePublished = v;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String v) {
		status = v;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String v) {
		isbn = v;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String v) {
		categoryName = v;
	}

	public String getCoverUrl() {
		if (isbn == null || isbn.trim().isEmpty())
			return "";
		return "https://covers.openlibrary.org/b/isbn/" + isbn.replaceAll("[^0-9Xx]", "") + "-M.jpg?default=false";
	}

	public boolean isAvailable() {
		return "ACTIVE".equalsIgnoreCase(status) && availableCopies > 0;
	}

	public String getYear() {
		return datePublished != null && datePublished.length() >= 4 ? datePublished.substring(0, 4) : "";
	}
}