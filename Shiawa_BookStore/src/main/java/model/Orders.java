/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author MY
 */
public class Orders {
    private int orderId;          
    private int customerId;     
    private int staffId;          

    private LocalDateTime orderDate;   
    private String status;             
    private int discount;              
    private String shippingAddress;   
    private double shippingFee;  

    public Orders() {
    }

    public Orders(int orderId, int customerId, int staffId, LocalDateTime orderDate, String status, int discount, String shippingAddress, double shippingFee) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.staffId = staffId;
        this.orderDate = orderDate;
        this.status = status;
        this.discount = discount;
        this.shippingAddress = shippingAddress;
        this.shippingFee = shippingFee;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }
    
}
