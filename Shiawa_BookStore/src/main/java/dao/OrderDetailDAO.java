/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.CartItem;
import model.OrderItem;

/**
 *
 * @author MY
 */
public class OrderDetailDAO extends DBContext {

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

    public List<OrderItem> getItemsByOrderId(int orderId) {
        List<OrderItem> list = new ArrayList<>();

        String sql = """
        SELECT 
            od.quantity,
            od.price,
            b.title,
            b.url_img
        FROM OrderDetail od
        JOIN Book b ON od.book_id = b.book_id
        WHERE od.order_id = ?
    """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setTitle(rs.getString("title"));
                item.setUrl_img(rs.getString("url_img"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getDouble("price"));

                list.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
