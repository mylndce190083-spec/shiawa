/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author BA LIEM
 */
public class Voucher {

    private int voucher_id;
    private String name;
    private double discount;
    private int quantity;
    private Date createdAt;
    private Date endedAt;
    private String status;

    public Voucher() {
    }

    public Voucher(int voucher_id, String name, double discount, int quantity, Date createdAt, Date endedAt, String status) {
        this.voucher_id = voucher_id;
        this.name = name;
        this.discount = discount;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.endedAt = endedAt;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

   

    public int getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(int voucher_id) {
        this.voucher_id = voucher_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(Date endedAt) {
        this.endedAt = endedAt;
    }

    @Override
    public String toString() {
        return "Voucher{" + "voucher_id=" + voucher_id + ", name=" + name + ", discount=" + discount + ", quantity=" + quantity + ", createdAt=" + createdAt + ", endedAt=" + endedAt + ", status=" + status + '}';
    }
    
    
}
