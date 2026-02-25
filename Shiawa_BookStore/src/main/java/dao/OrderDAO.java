/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Order;
import model.OrderDetail;

/**
 *
 * @author BA LIEM
 */
public class OrderDAO extends DBContext {

    public List<Order> getAllOrders() {

        List<Order> list = new ArrayList<>();

        try {
            System.out.println("==== DEBUG DB INFO ====");
            System.out.println("URL: " + getConnection().getMetaData().getURL());
            System.out.println("DB Name: " + getConnection().getCatalog());
            System.out.println("=======================");
            // DEBUG CONNECTION
            System.out.println("URL: " + getConnection().getMetaData().getURL());
            System.out.println("DB: " + getConnection().getCatalog());

            // DEBUG COUNT
            String test = "SELECT COUNT(*) FROM Orders";
            PreparedStatement ps2 = getConnection().prepareStatement(test);
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                System.out.println("Order count in Java = " + rs2.getInt(1));
            }

            // ---- QUERY CHÍNH ----
            String sql = """
            SELECT 
                o.order_id,
                c.full_name,
                o.order_date,
                o.status,
                COALESCE(SUM(od.quantity * od.price), 0) AS total_amount,
                o.discount,
                o.shipping_fee,
                v.name AS voucher_name
            FROM Orders o
            LEFT JOIN Customer c ON o.customer_id = c.customer_id
            LEFT JOIN OrderDetail od ON o.order_id = od.order_id
            LEFT JOIN Voucher v ON o.voucher_id = v.voucher_id
            GROUP BY 
                o.order_id,
                c.full_name,
                o.order_date,
                o.status,
                o.discount,
                o.shipping_fee,
                v.name
            ORDER BY o.order_id DESC
        """;

            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("order_id"));
                o.setCustomerName(rs.getString("full_name"));
                o.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
                o.setStatus(rs.getString("status"));
                o.setTotalAmount(rs.getDouble("total_amount"));
                o.setDiscount(rs.getDouble("discount"));
                o.setShippingFee(rs.getDouble("shipping_fee"));
                o.setVoucherName(rs.getString("voucher_name"));
                list.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Order getOrderById(int id) {

        String sql = """
        SELECT o.order_id,
                   o.order_date,
                   o.status,
                   o.discount,
                   o.shipping_address,
                   o.shipping_fee,
                   c.full_name,
                   c.phone,
                   v.name AS voucher_name
            FROM Orders o
            JOIN Customer c ON o.customer_id = c.customer_id
            LEFT JOIN Voucher v ON o.voucher_id = v.voucher_id
            WHERE o.order_id = ?
    """;

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("order_id"));
                o.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
                o.setStatus(rs.getString("status"));
                o.setDiscount(rs.getDouble("discount"));
                o.setShippingAddress(rs.getString("shipping_address"));
                o.setShippingFee(rs.getDouble("shipping_fee"));
                o.setCustomerName(rs.getString("full_name"));
                o.setPhone(rs.getString("phone")); // ✅ thêm dòng này
                o.setVoucherName(rs.getString("voucher_name"));
                return o;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<OrderDetail> getOrderDetails(int orderId) {

        List<OrderDetail> list = new ArrayList<>();

        String sql = """
        SELECT od.order_detail_id,
               b.title,
               od.quantity,
               od.price
        FROM OrderDetail od
        JOIN Book b ON od.book_id = b.book_id
        WHERE od.order_id = ?
    """;

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderDetail d = new OrderDetail();
                d.setOrderDetailId(rs.getInt("order_detail_id"));
                d.setBookTitle(rs.getString("title"));
                d.setQuantity(rs.getInt("quantity"));
                d.setPrice(rs.getDouble("price"));
                list.add(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean updateStatus(int orderId, String status) {
        String sql = "UPDATE Orders SET status = ? WHERE order_id = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
