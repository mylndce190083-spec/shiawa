/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Lenovo
 */
import java.time.LocalDateTime;

public class Book {
    private int bookId;
    private String title;
    private String author;
    private double price;
    private String description;
    private Category cate;
    private int stock;
    private String publisher;
    private int discount;
    private String imgUrl;
    private boolean isActive;
    private LocalDateTime createdAt;

    public Book() {
    }

    public Book(int bookId, String title, String author, double price, String description, Category cate, int stock, String publisher, int discount, String imgUrl, boolean isActive, LocalDateTime createdAt) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.price = price;
        this.description = description;
        this.cate = cate;
        this.stock = stock;
        this.publisher = publisher;
        this.discount = discount;
        this.imgUrl = imgUrl;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCate() {
        return cate;
    }

    public void setCate(Category cate) {
        this.cate = cate;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Book{" + "bookId=" + bookId + ", title=" + title + ", author=" + author + ", price=" + price + ", description=" + description + ", cate=" + cate + ", stock=" + stock + ", publisher=" + publisher + ", discount=" + discount + ", imgUrl=" + imgUrl + ", isActive=" + isActive + ", createdAt=" + createdAt + '}';
    }
    
    
}
