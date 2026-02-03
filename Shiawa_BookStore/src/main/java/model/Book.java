/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

<<<<<<< HEAD
=======
import java.time.LocalDateTime;

>>>>>>> 6eec6e2c6e3608949a045ca087e1b084a6b72c92
/**
 *
 * @author BA LIEM
 */
public class Book {
<<<<<<< HEAD

=======
>>>>>>> 6eec6e2c6e3608949a045ca087e1b084a6b72c92
    private int bookId;
    private String title;
    private String author;
    private double price;
<<<<<<< HEAD
=======
    private String description;
    private Category category;
>>>>>>> 6eec6e2c6e3608949a045ca087e1b084a6b72c92
    private int stock;
    private String publisher;
    private int discount;
    private String urlImg;
    private boolean isActive;
<<<<<<< HEAD
    private String createdAt;
    private String categoryName;
    private int categoryId;
=======
    private LocalDateTime createdAt;
>>>>>>> 6eec6e2c6e3608949a045ca087e1b084a6b72c92

    public Book() {
    }

<<<<<<< HEAD
    public Book(int bookId, String title, String author, double price, int stock, String publisher, int discount, String urlImg, boolean isActive, String createdAt, String categoryName) {
=======
    public Book(int bookId, String title, String author, double price, String description, Category category, int stock, String publisher, int discount, String urlImg, boolean isActive, LocalDateTime createdAt) {
>>>>>>> 6eec6e2c6e3608949a045ca087e1b084a6b72c92
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.price = price;
<<<<<<< HEAD
=======
        this.description = description;
        this.category = category;
>>>>>>> 6eec6e2c6e3608949a045ca087e1b084a6b72c92
        this.stock = stock;
        this.publisher = publisher;
        this.discount = discount;
        this.urlImg = urlImg;
        this.isActive = isActive;
        this.createdAt = createdAt;
<<<<<<< HEAD
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
=======
>>>>>>> 6eec6e2c6e3608949a045ca087e1b084a6b72c92
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

<<<<<<< HEAD
=======
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

>>>>>>> 6eec6e2c6e3608949a045ca087e1b084a6b72c92
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

<<<<<<< HEAD
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
=======
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
}
    
>>>>>>> 6eec6e2c6e3608949a045ca087e1b084a6b72c92
