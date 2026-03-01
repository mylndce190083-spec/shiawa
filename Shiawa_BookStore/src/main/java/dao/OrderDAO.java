package dao;

import db.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.CartItem;
import model.Orders;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author MY
 */
public class OrderDAO extends DBContext {

    public int insertOrder(Connection con,
            int customerId,
            String shippingAddress,
            double shippingFee) throws Exception {

        String sql = """
        INSERT INTO Orders
        (customer_id, staff_id, order_date, status,
         discount, shipping_address, shipping_fee)
        VALUES (?, ?, GETDATE(), ?, ?, ?, ?)
    """;

        try (PreparedStatement ps
                = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, customerId);
            ps.setNull(2, java.sql.Types.INTEGER); // staff_id
            ps.setString(3, "Pending");
            ps.setInt(4, 0);
            ps.setString(5, shippingAddress);
            ps.setDouble(6, shippingFee);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        throw new Exception("Cannot create order");
    }

    public int createOrder(int customerId,
            List<CartItem> items,
            String shippingAddress,
            double shippingFee) throws Exception {

        Connection con = getConnection();

        if (con == null) {
            throw new Exception("Cannot connect database");
        }

        try {
            con.setAutoCommit(false);

            // 1️⃣ Insert order trước
            int orderId = insertOrder(con, customerId,
                    shippingAddress, shippingFee);

            BookDAO bookDAO = new BookDAO();
            OrderDetailDAO detailDAO = new OrderDetailDAO();

            // 2️⃣ Loop từng sản phẩm
            for (CartItem item : items) {

                // Lấy stock hiện tại
                int stock = bookDAO.getStock(con, item.getBookId());

                // Kiểm tra quantity hợp lệ
                if (item.getQuantity() <= 0) {
                    throw new Exception("Invalid quantity for product ID "
                            + item.getBookId());
                }

                // Kiểm tra đủ hàng không
                if (stock < item.getQuantity()) {
                    throw new Exception("Product ID "
                            + item.getBookId()
                            + " only has "
                            + stock
                            + " items left");
                }

                // Insert OrderDetail
                detailDAO.insertOrderDetail(
                        con,
                        orderId,
                        item.getBookId(),
                        item.getQuantity(),
                        item.getPrice()
                );

                // Update stock
                bookDAO.updateStock(
                        con,
                        item.getBookId(),
                        item.getQuantity()
                );
            }

            // 3️⃣ Commit nếu mọi thứ OK
            con.commit();
            return orderId;

        } catch (Exception e) {

            // 4️⃣ Rollback nếu lỗi
            con.rollback();
            throw e;

        } finally {
            con.close();
        }
    }

    public List<Orders> getOrdersByCustomer(int customerId) {
        List<Orders> list = new ArrayList<>();

        String sql = """
        SELECT 
            o.order_id,
            o.customer_id,
            o.staff_id,
            o.order_date,
            o.status,
            o.shipping_address,
            o.shipping_fee,
            o.discount,
            SUM(od.quantity * od.price) 
                + ISNULL(o.shipping_fee,0)
                - ISNULL(o.discount,0) AS total_amount
        FROM Orders o
        JOIN OrderDetail od ON o.order_id = od.order_id
        WHERE o.customer_id = ?
        GROUP BY 
            o.order_id,
            o.customer_id,
            o.staff_id,
            o.order_date,
            o.status,
            o.shipping_address,
            o.shipping_fee,
            o.discount
        ORDER BY o.order_date DESC
    """;

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Orders o = new Orders();

                o.setOrderId(rs.getInt("order_id"));
                o.setCustomerId(rs.getInt("customer_id"));
                o.setStaffId(rs.getInt("staff_id"));

                Timestamp ts = rs.getTimestamp("order_date");
                if (ts != null) {
                    o.setOrderDate(ts.toLocalDateTime());
                }

                o.setStatus(rs.getString("status"));
                o.setShippingAddress(rs.getString("shipping_address"));
                o.setShippingFee(rs.getDouble("shipping_fee"));

                // 🔥 QUAN TRỌNG
                o.setTotalAmount(rs.getDouble("total_amount"));

                list.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Orders> getOrdersByStatus(int customerId, String status) {
        List<Orders> list = new ArrayList<>();

        String sql = """
        SELECT 
            o.order_id,
            o.customer_id,
            o.staff_id,
            o.order_date,
            o.status,
            o.shipping_address,
            o.shipping_fee,
            o.discount,
            SUM(od.quantity * od.price) 
                + ISNULL(o.shipping_fee,0)
                - ISNULL(o.discount,0) AS total_amount
        FROM Orders o
        JOIN OrderDetail od ON o.order_id = od.order_id
        WHERE o.customer_id = ? AND o.status = ?
        GROUP BY 
            o.order_id,
            o.customer_id,
            o.staff_id,
            o.order_date,
            o.status,
            o.shipping_address,
            o.shipping_fee,
            o.discount
        ORDER BY o.order_date DESC
    """;

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.setString(2, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Orders o = new Orders();

                o.setOrderId(rs.getInt("order_id"));
                o.setCustomerId(rs.getInt("customer_id"));
                o.setStaffId(rs.getInt("staff_id"));

                Timestamp ts = rs.getTimestamp("order_date");
                if (ts != null) {
                    o.setOrderDate(ts.toLocalDateTime());
                }

                o.setStatus(rs.getString("status"));
                o.setShippingAddress(rs.getString("shipping_address"));
                o.setShippingFee(rs.getDouble("shipping_fee"));

                // 🔥 QUAN TRỌNG
                o.setTotalAmount(rs.getDouble("total_amount"));

                list.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void cancelOrderIfPending(int orderId, int customerId) {

        String sql = """
        UPDATE Orders
        SET status = 'Cancelled'
        WHERE order_id = ?
        AND customer_id = ?
        AND status = 'Pending'
    """;

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.setInt(2, customerId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
