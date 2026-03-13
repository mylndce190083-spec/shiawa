/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author BA LIEM
 */
public class Book {

    private int bookId;
    private String title;
    private String author;
    private double price;
    private String description;
    private Category category;
    private int stock;
    private String publisher;
    private int discount;
    private String urlImg;
    private boolean isActive;
    private LocalDateTime createdAt;
    private BookImage primaryImage;
    private List<BookImage> images;

    public Book() {
    }

    public Book(int bookId, String title, String author, double price, String description, Category category, int stock, String publisher, int discount, String urlImg, boolean isActive, LocalDateTime createdAt) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.price = price;
        this.description = description;
        this.category = category;
        this.stock = stock;
        this.publisher = publisher;
        this.discount = discount;
        this.urlImg = urlImg;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
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

    public BookImage getPrimaryImage() {
        return primaryImage;
    }

    public void setPrimaryImage(BookImage primaryImage) {
        this.primaryImage = primaryImage;
    }

    public List getImages() {
        return images;
    }

    public void setImages(List images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Book{" + "bookId=" + bookId + ", title=" + title + ", author=" + author + ", price=" + price + ", description=" + description + ", category=" + category + ", stock=" + stock + ", publisher=" + publisher + ", discount=" + discount + ", urlImg=" + urlImg + ", isActive=" + isActive + ", createdAt=" + createdAt + ", primaryImage=" + primaryImage + ", images=" + images + '}';
    }
    
    
}
