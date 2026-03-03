/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
import java.util.List;

/*
 * @author BA LIEM
*/
public class Orders {

    private int orderId;
    private int customerId;
    private int staffId;
    private double totalAmount;
    private LocalDateTime orderDate;
    private String status;
    private int discount;
    private String shippingAddress;
    private double shippingFee;
    private List<OrderItem> items;
    private String customerName;
    private String phone;
    private String voucherName;
    private String receiverName;
    private int quantity;

    public Orders() {
    }

    public Orders(int orderId, int customerId, int staffId, double totalAmount, LocalDateTime orderDate, String status, int discount, String shippingAddress, double shippingFee, List<OrderItem> items, String customerName, String phone, String voucherName, String receiverName, int quantity) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.staffId = staffId;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.status = status;
        this.discount = discount;
        this.shippingAddress = shippingAddress;
        this.shippingFee = shippingFee;
        this.items = items;
        this.customerName = customerName;
        this.phone = phone;
        this.voucherName = voucherName;
        this.receiverName = receiverName;
        this.quantity = quantity;
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
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

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
