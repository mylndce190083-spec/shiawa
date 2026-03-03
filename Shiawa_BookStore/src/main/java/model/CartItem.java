/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 *
 * @author BA LIEM
 */
public class CartItem {
    private int cartItemId;
    private int customerId;
    private int bookId;
    private int quantity;
    private Double price;
    private LocalDateTime createAt;
    private Book book;
    private int stock;

    public CartItem() {
    }

    public CartItem(int cartItemId, int customerId, int bookId, int quantity, double price, LocalDateTime createAt) {
        this.cartItemId = cartItemId;
        this.customerId = customerId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
        this.createAt = createAt;
        this.book = book;
        this.stock = stock;
    }

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
