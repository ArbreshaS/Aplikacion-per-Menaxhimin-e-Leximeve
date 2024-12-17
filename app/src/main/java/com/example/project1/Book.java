package com.example.project1;

import java.util.HashMap;
import java.util.Map;

public class Book {
    private String title;
    private String author;
    private String description;
    private int year;
    private int currentPage;
    private int totalPages;
    private boolean completed;  // Added field for completed status
    private int rating;         // Added field for rating

    // Constructor with all necessary fields
    public Book(String title, String author, String description, int year, int currentPage,
                int totalPages, boolean completed, int rating) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.year = year;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.completed = completed;
        this.rating = rating;
    }

    // Getters and Setters (optional, if you need them)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    // Convert Book object to a Map for Firestore
    public Map<String, Object> toMap(String userId) {
        Map<String, Object> bookMap = new HashMap<>();
        bookMap.put("Title", title);
        bookMap.put("Author", author);
        bookMap.put("Description", description);
        bookMap.put("Year", year);
        bookMap.put("CurrentPage", currentPage);
        bookMap.put("TotalPages", totalPages);
        bookMap.put("Completed", completed);
        bookMap.put("Rating", rating);
        bookMap.put("userId", userId); // Add userId to associate the book with the user
        return bookMap;
    }
}
