/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.CartItem;
import utils.DBContext;

/**
 *
 * @author MY
 */
public class CartItemDAO extends DBContext{
    
  
    // tìm cart item theo customer + book
    public CartItem findItem(int customer_id, int book_id) {
        String sql = "SELECT * FROM CartItem WHERE customer_id=? AND book_id=?";
        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customer_id);
            ps.setInt(2, book_id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                CartItem item = new CartItem();
                item.setQuantity(rs.getInt("quantity"));
                return item;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(CartItem item) {
       
       
        String sql = "INSERT INTO CartItem(customer_id, book_id, quantity, price) VALUES (?,?,?,?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, item.getCustomer_id());
            ps.setInt(2, item.getBook_id());
            ps.setInt(3, item.getQuantity());
            ps.setBigDecimal(4, item.getPrice());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

   
