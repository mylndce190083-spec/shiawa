/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.CartItem;
import model.OrderItem;

/**
 *
 * @author MY
 */
public class OrderDetailDAO extends db.DBContext {

    public void insertOrderDetail(Connection con,
            int orderId,
            int bookId,
            int quantity,
            double price) throws Exception {

        String sql = """
        INSERT INTO OrderDetail
        (order_id, book_id, quantity, price)
        VALUES (?, ?, ?, ?)
    """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ps.setInt(2, bookId);
            ps.setInt(3, quantity);
            ps.setDouble(4, price);

            ps.executeUpdate();
        }
    }
}
