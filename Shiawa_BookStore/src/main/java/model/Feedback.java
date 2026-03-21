
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author admin
 */
public class Feedback {
    private int id;
    private int userId;
    private int bookId;
    private int rating;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private int orderdetailId;
    private String bookTitle;
    private int status;

    public Feedback() {
    }

    public Feedback(int id, int userId, int bookId, int rating, String content, String username, LocalDateTime createdAt, int orderdetailId, String bookTitle) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.rating = rating;
        this.content = content;
        this.username = username;
        this.createdAt = createdAt;
        this.orderdetailId = orderdetailId;
        this.bookTitle = bookTitle;
    }

    

    public int getOrderdetailId() {
        return orderdetailId;
    }

    public void setOrderdetailId(int orderdetailId) {
        this.orderdetailId = orderdetailId;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Feedback{" + "id=" + id + ", userId=" + userId + ", bookId=" + bookId + ", rating=" + rating + ", content=" + content + ", username=" + username + ", createdAt=" + createdAt + ", orderdetailId=" + orderdetailId + ", bookTitle=" + bookTitle + ", status=" + status + '}';
    }
    

    
    
    
    
}
