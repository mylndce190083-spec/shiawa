/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author MY
 */
public class OrderItem {
    private int orderDetailId;
    private int orderId;
    private int bookId;
    private String title;
    private String url_img;
    private int quantity;
    private double price;
    private Book book;

    public OrderItem() {
    }

    public OrderItem(int orderDetailId, int orderId, int bookId, String title, String url_img, int quantity, double price, Book book) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.bookId = bookId;
        this.title = title;
        this.url_img = url_img;
        this.quantity = quantity;
        this.price = price;
        this.book = book;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "OrderItem{" + "orderDetailId=" + orderDetailId + ", orderId=" + orderId + ", bookId=" + bookId + ", title=" + title + ", url_img=" + url_img + ", quantity=" + quantity + ", price=" + price + ", book=" + book + '}';
    }

}
