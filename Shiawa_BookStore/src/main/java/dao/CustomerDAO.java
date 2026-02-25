/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Customer;

/**
 *
 * @author BA LIEM
 */
public class CustomerDAO extends DBContext {

//    public List<Customer> getCustomerList(int page) {
//        List<Customer> list = new ArrayList<>();
//        try {
//            String query = "SELECT customer_id, username, email, created_at FROM Customer";                    
//
//            PreparedStatement ps = this.getConnection().prepareStatement(query);
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                int id = rs.getInt("customer_id");
//                String username = rs.getString("username");
//               // String role = rs.getString("role_name");
//               String email = rs.getString("email");
//                String createdAt = rs.getString("created_at");
//
//                Customer c = new Customer(id, username,email , createdAt);
//                list.add(c);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return list;
//    }
//
//    public int getTotalRows() {
//        try {
//            String query = "select count(customer_id) from Customer";
//            PreparedStatement statement = this.getConnection().prepareStatement(query);
//
//            ResultSet rs = statement.executeQuery();
//
//            if (rs.next()) {
//                return rs.getInt(1);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return 0;
//    }
    
    public Customer getCustomerByAccountId(int accountId) {
        String sql = "SELECT customer_id FROM Customer WHERE customer_id = ?";

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("customer_id"));
                return c;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean checkCustomerExist(String email) {
        String sql = "SELECT customer_id FROM Customer WHERE email = ?";

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void insert(Customer customer) {
        String sql = "INSERT INTO Customer(username, password, email, verify_token) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, customer.getUsername());
            ps.setString(2, customer.getPassword());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getVerifyToken());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean verifyUser(String token) {
    String sql = "UPDATE Customer SET status='active', verify_token=NULL WHERE verify_token=?";
    try {
        PreparedStatement ps = getConnection().prepareStatement(sql);
        ps.setString(1, token);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
    
    public static void main(String[] args) {
        CustomerDAO dao = new CustomerDAO();
//        Customer c = new Customer(4, "thehien", "123", "thehien@gmail.com", "559");
//        dao.insert(c);
        if (dao.checkCustomerExist("abc@gmail.com"))
        System.out.println("ddddddddddddddddddđ");
        else System.out.println("ssssssssssssss");
    }

}
