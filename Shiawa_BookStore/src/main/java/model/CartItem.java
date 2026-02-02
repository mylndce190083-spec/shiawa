/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author MY
 */
public class CartItem {
    private int cart_item_id;
    private int customer_id;
    private int book_id;
    private int quantity;
    private BigDecimal price;
   private LocalDateTime create_at;

    public CartItem() {
    }

    public CartItem(int cart_item_id, int customer_id, int book_id, int quantity, BigDecimal price, LocalDateTime create_at) {
        this.cart_item_id = cart_item_id;
        this.customer_id = customer_id;
        this.book_id = book_id;
        this.quantity = quantity;
        this.price = price;
        this.create_at = create_at;
    }

    public int getCart_item_id() {
        return cart_item_id;
    }

    public void setCart_item_id(int cart_item_id) {
        this.cart_item_id = cart_item_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

}