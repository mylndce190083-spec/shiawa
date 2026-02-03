/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Tiến Thành
 */
public class Customer {

    private int id;
    private String username;
    //private String role;
    private String email;
    private String createdAt;

    public Customer() {
    }

    public Customer(int id, String username, String email, String createdAt) {
        this.id = id;
        this.username = username;
        //this.role = role;
        this.email = email;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /*public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }*/

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
