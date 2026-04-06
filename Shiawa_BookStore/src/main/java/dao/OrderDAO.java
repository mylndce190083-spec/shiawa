
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import model.Book;
import model.CartItem;

import model.Orders;
import model.OrderDetail;
import model.OrderItem;

import java.util.ArrayList;

/**
 *
 * @author BA LIEM
 */
public class OrderDAO extends DBContext {

    public List<Orders> getAllOrders() {

        List<Orders> list = new ArrayList<>();

        try {
            System.out.println("==== DEBUG DB INFO ====");
            System.out.println("URL: " + getConnection().getMetaData().getURL());
            System.out.println("DB Name: " + getConnection().getCatalog());
            System.out.println("=======================");
            System.out.println("URL: " + getConnection().getMetaData().getURL());
            System.out.println("DB: " + getConnection().getCatalog());

            String test = "SELECT COUNT(*) FROM Orders";
            PreparedStatement ps2 = getConnection().prepareStatement(test);
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                System.out.println("Order count in Java = " + rs2.getInt(1));
            }
            String sql = """
            SELECT 
                o.order_id,
                c.full_name,
                o.order_date,
                o.status,
                COALESCE(SUM(od.quantity * od.price), 0) AS total_amount,
                o.discount,
                o.shipping_fee,
                v.name AS voucher_name,
              o.payment_method
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
                Orders o = new Orders();
                o.setOrderId(rs.getInt("order_id"));
                o.setCustomerName(rs.getString("full_name"));
                o.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
                o.setStatus(rs.getString("status"));
                o.setTotalAmount(rs.getDouble("total_amount"));
                o.setDiscount((int) rs.getDouble("discount"));
                o.setShippingFee(rs.getDouble("shipping_fee"));
                o.setVoucherName(rs.getString("voucher_name"));
                o.setPaymentMethod(rs.getString("payment_method"));
                list.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Orders getOrderByIdAdmin(int id) {

        String sql = """
        SELECT o.order_id,
                   o.order_date,
                   o.status,
                   o.discount,
                   o.shipping_address,
                   o.shipping_fee,
                   c.full_name,
                   c.phone,
                   v.name AS voucher_name,
                     o.payment_method
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
                Orders o = new Orders();
                o.setOrderId(rs.getInt("order_id"));
                o.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
                o.setStatus(rs.getString("status"));
                o.setDiscount((int) rs.getDouble("discount"));
                o.setShippingAddress(rs.getString("shipping_address"));
                o.setShippingFee(rs.getDouble("shipping_fee"));
                o.setCustomerName(rs.getString("full_name"));
                o.setPaymentMethod(rs.getString("payment_method"));
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

    public boolean updateStatus(int orderId, String status, int adminId) {
        String sql = "UPDATE Orders SET status = ?, staff_id =? WHERE order_id = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, adminId);
            ps.setInt(3, orderId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void restoreProductQuantity(int orderId) {
    String sqlDetail = "SELECT book_id, quantity FROM OrderDetail WHERE order_id = ?";
    String sqlUpdate = "UPDATE Book SET stock = stock + ? WHERE book_id = ?";

    try (Connection conn = getConnection();
         PreparedStatement psDetail = conn.prepareStatement(sqlDetail);
         PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {

        conn.setAutoCommit(false); 

        psDetail.setInt(1, orderId);
        ResultSet rs = psDetail.executeQuery();

        while (rs.next()) {
            int bookId = rs.getInt("book_id");
            int stock = rs.getInt("quantity");

            psUpdate.setInt(1, stock);
            psUpdate.setInt(2, bookId);
            psUpdate.executeUpdate();
        }

        conn.commit();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    public boolean updateStatusCustomer(int orderId, String status) {
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

    public int insertOrder(Connection con,
            int customerId,
            String shippingAddress,
            double shippingFee,
            String receiverName,
            String phone,
            String paymentMethod,
            int discount,
            Integer voucherId
    ) throws Exception {

        String sql = """
        INSERT INTO Orders
        (customer_id, staff_id, order_date, status,
         discount, shipping_address, shipping_fee,receiver_name,phone,payment_method, voucher_id)
        VALUES (?, ?, GETDATE(), ?, ?, ?, ?,?,?,?,?)                   
    """;

        try (PreparedStatement ps
                = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, customerId);
            ps.setNull(2, java.sql.Types.INTEGER);
            ps.setString(3, "PENDING");
            ps.setInt(4, discount);
            ps.setString(5, shippingAddress);
            ps.setDouble(6, shippingFee);
            ps.setString(7, receiverName);
            ps.setString(8, phone);
            ps.setString(9, paymentMethod);

            if (voucherId == null) {
                ps.setNull(10, java.sql.Types.INTEGER);
            } else {
                ps.setInt(10, voucherId);
            }
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
            double shippingFee,
            String receiverName,
            String phone,
            String paymentMethod,
            boolean isBuyNow,
            int discount,
            Integer voucherId
    ) throws Exception {
        Connection con = getConnection();

        if (con == null) {
            throw new Exception("Cannot connect database");
        }

        try {
            con.setAutoCommit(false);

            int orderId = insertOrder(con, customerId,
                    shippingAddress, shippingFee, receiverName, phone, paymentMethod, discount, voucherId);

            BookDAO bookDAO = new BookDAO();
            OrderDetailDAO detailDAO = new OrderDetailDAO();

            for (CartItem item : items) {
                int stock = bookDAO.getStock(con, item.getBookId());
                if (item.getQuantity() <= 0) {
                    throw new Exception("Invalid quantity for product ID "
                            + item.getBookId());
                }
                if (stock == 0) {
                    throw new Exception("Sản phẩm đã hết hàng");
                }
                if (item.getQuantity() > stock) {
                    throw new Exception("Chỉ còn " + stock + " sản phẩm trong kho");
                }
                detailDAO.insertOrderDetail(
                        con,
                        orderId,
                        item.getBookId(),
                        item.getQuantity(),
                        item.getPrice()
                );
                bookDAO.updateStock(
                        con,
                        item.getBookId(),
                        item.getQuantity()
                );
            }
            if (!isBuyNow) {
                CartItemDAO cartDAO = new CartItemDAO();

                for (CartItem item : items) {
                    cartDAO.delete(customerId, item.getBookId());
                }
            }
            con.commit();
            return orderId;
        } catch (Exception e) {
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
           o.payment_method,
            SUM(od.quantity * od.price) * (1 - ISNULL(o.discount,0)/100.0)
                + ISNULL(o.shipping_fee,0) AS total_amount
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
            o.discount,
          o.payment_method 
        ORDER BY o.order_date DESC
    """;
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
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
                o.setPaymentMethod(rs.getString("payment_method"));
                o.setShippingAddress(rs.getString("shipping_address"));
                o.setShippingFee(rs.getDouble("shipping_fee"));
                o.setTotalAmount(rs.getDouble("total_amount"));

                String itemSql = """
            SELECT 
                    oi.quantity,
                    b.book_id,
                    b.title,
                    bi.image_url,
                    oi.price
                FROM OrderDetail oi
                JOIN Book b ON oi.book_id = b.book_id
                LEFT JOIN BookImages bi ON b.book_id = bi.book_id AND bi.is_primary=1
                WHERE oi.order_id = ?
        """;

                PreparedStatement ps2 = con.prepareStatement(itemSql);
                ps2.setInt(1, o.getOrderId());
                ResultSet rs2 = ps2.executeQuery();

                List<OrderItem> items = new ArrayList<>();
                BookDAO bookDAO = new BookDAO();
                while (rs2.next()) {
                    Book b = new Book();
                    b.setBookId(rs2.getInt("book_id"));
                    b.setTitle(rs2.getString("title"));

                    String img = bookDAO.getImgURLbyBookId(rs2.getInt("book_id"));
                    b.setUrlImg(img);
                    OrderItem item = new OrderItem();
                    item.setQuantity(rs2.getInt("quantity"));
                    item.setBook(b);
                    item.setTitle(rs2.getString("title"));
                    item.setPrice(rs2.getDouble("price"));

                    items.add(item);
                }

                o.setItems(items);
                int totalQuantity = 0;
                for (OrderItem item : items) {
                    totalQuantity += item.getQuantity();
                }
                o.setQuantity(totalQuantity);
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
        o.payment_method,   
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
            o.discount,
          o.payment_method   
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
                o.setPaymentMethod(rs.getString("payment_method"));
                o.setShippingAddress(rs.getString("shipping_address"));
                o.setShippingFee(rs.getDouble("shipping_fee"));

                o.setTotalAmount(rs.getDouble("total_amount"));

                list.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void cancelOrderIfPending(int orderId, int customerId) {

        Connection con = null;

        try {
            con = getConnection();
            con.setAutoCommit(false);
            String checkSql = """
            SELECT status, payment_method
            FROM Orders 
            WHERE order_id = ? AND customer_id = ?
        """;

            PreparedStatement psCheck = con.prepareStatement(checkSql);
            psCheck.setInt(1, orderId);
            psCheck.setInt(2, customerId);

            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                String status = rs.getString("status");
                String paymentMethod = rs.getString("payment_method");

                if ("Pending".equalsIgnoreCase(status)) {

                    String itemSql = """
                    SELECT book_id, quantity
                    FROM OrderDetail
                    WHERE order_id = ?
                """;

                    PreparedStatement psItem = con.prepareStatement(itemSql);
                    psItem.setInt(1, orderId);
                    ResultSet rsItem = psItem.executeQuery();

                    while (rsItem.next()) {
                        int bookId = rsItem.getInt("book_id");
                        int quantity = rsItem.getInt("quantity");

                        String updateStock = """
                        UPDATE Book
                        SET stock = stock + ?
                        WHERE book_id = ?
                    """;

                        PreparedStatement psStock = con.prepareStatement(updateStock);
                        psStock.setInt(1, quantity);
                        psStock.setInt(2, bookId);
                        psStock.executeUpdate();
                        psStock.close();
                    }
                    rsItem.close();
                    psItem.close();

                    String newStatus = "FAILED";
                    if ("ONLINE".equalsIgnoreCase(paymentMethod)) {
                        newStatus = "CANCEL_REQUESTED";
                        System.out.println("Refund VNPAY for order " + orderId);
                    }

                    String updateOrder = """
                    UPDATE Orders
                    SET status = ?
                    WHERE order_id = ?
                """;

                    PreparedStatement psUpdate = con.prepareStatement(updateOrder);
                    psUpdate.setString(1, newStatus);
                    psUpdate.setInt(2, orderId);
                    psUpdate.executeUpdate();
                    psUpdate.close();
                    con.commit();
                    System.out.println("Order " + orderId + " đã hủy, stock đã cộng lại.");
                }
            }

        } catch (Exception e) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Orders getOrderById(int id) {

        String sql = """
        SELECT o.order_id,
                   o.order_date,
                   o.status,
                   o.discount,
                   o.shipping_address,
                   o.shipping_fee,
                   o.receiver_name,
                   c.full_name,
                   o.phone,
                  o.payment_method
            FROM Orders o
            JOIN Customer c ON o.customer_id = c.customer_id
            WHERE o.order_id = ?
    """;

        try {
            Connection con = getConnection();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Orders o = new Orders();
                o.setOrderId(rs.getInt("order_id"));
                Timestamp ts = rs.getTimestamp("order_date");
                if (ts != null) {
                    o.setOrderDate(ts.toLocalDateTime());
                }
                o.setStatus(rs.getString("status"));
                o.setDiscount(rs.getInt("discount"));
                o.setShippingAddress(rs.getString("shipping_address"));
                o.setShippingFee(rs.getDouble("shipping_fee"));
                o.setCustomerName(rs.getString("full_name"));
                o.setPhone(rs.getString("phone")); 
                o.setReceiverName(rs.getString("receiver_name"));
                o.setPaymentMethod(rs.getString("payment_method"));
                String itemSql = """
                 SELECT oi.quantity,
                           b.book_id,
                           b.title,
                           bi.image_url,
                           oi.price
                    FROM OrderDetail oi
                    JOIN Book b ON oi.book_id = b.book_id
                    LEFT JOIN BookImages bi ON b.book_id = bi.book_id AND bi.is_primary = 1
                    WHERE oi.order_id = ?
            """;

                PreparedStatement ps2 = con.prepareStatement(itemSql);
                ps2.setInt(1, id);
                ResultSet rs2 = ps2.executeQuery();

                List<OrderItem> items = new ArrayList<>();

                while (rs2.next()) {

                    Book b = new Book();
                    b.setBookId(rs2.getInt("book_id"));
                    b.setTitle(rs2.getString("title"));
                    b.setUrlImg(rs2.getString("image_url"));

                    OrderItem item = new OrderItem();
                    item.setQuantity(rs2.getInt("quantity"));
                    item.setBook(b);
                    item.setTitle(rs2.getString("title"));
                    item.setUrl_img(rs2.getString("image_url"));
                    item.setPrice(rs2.getDouble("price"));
                    items.add(item);
                }

                o.setItems(items);

                return o;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public String getLastShippingAddressByUserId(int userId) {
        String sql = "SELECT TOP 1 shipping_address FROM Orders WHERE customer_id = ? ORDER BY order_id DESC";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("shipping_address");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Orders getLastOrderByUserId(int userId) {
        String sql = "SELECT TOP 1 * FROM Orders WHERE customer_id = ? ORDER BY order_id DESC";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Orders o = new Orders();
                o.setOrderId(rs.getInt("order_id"));
                o.setReceiverName(rs.getString("receiver_name"));
                o.setPhone(rs.getString("phone"));
                o.setShippingAddress(rs.getString("shipping_address"));
                return o;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Orders> getOrdersByPage(int page, int pageSize) {
        List<Orders> list = new ArrayList<>();

        int offset = (page - 1) * pageSize;

        String sql = """
        SELECT 
            o.order_id,
            c.full_name,
            o.order_date,
            o.status,
            COALESCE(SUM(od.quantity * od.price),0) AS total_amount,            
            o.shipping_fee,
            s.full_name AS staff_name
        FROM Orders o
        LEFT JOIN Customer c ON o.customer_id = c.customer_id
        LEFT JOIN OrderDetail od ON o.order_id = od.order_id
        LEFT JOIN Staff s ON o.staff_id = s.staff_id
        GROUP BY 
            o.order_id,
            c.full_name,
            o.order_date,
            o.status,
            o.shipping_fee,
            s.full_name
        ORDER BY o.order_id DESC
        OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
    """;

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, offset);
            ps.setInt(2, pageSize);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Orders o = new Orders();
                o.setOrderId(rs.getInt("order_id"));
                o.setCustomerName(rs.getString("full_name"));
                o.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
                o.setStatus(rs.getString("status"));
                o.setTotalAmount(rs.getDouble("total_amount"));
                o.setShippingFee(rs.getDouble("shipping_fee"));
                o.setStaffName(rs.getString("staff_name"));

                list.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int countOrdersByCustomer(int userId) {
        String sql = "SELECT COUNT(*) FROM Orders WHERE customer_id = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalOrders() {
        String sql = "SELECT COUNT(*) FROM Orders";

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public double getTotalIncome() {
        String sql = """
        SELECT SUM(od.quantity * od.price) AS total
        FROM OrderDetail od
        JOIN Orders o ON od.order_id = o.order_id
        WHERE o.status = 'DELIVERED'
    """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalSoldQuantity() {
        String sql = """
        SELECT SUM(od.quantity) AS total
        FROM OrderDetail od
        JOIN Orders o ON od.order_id = o.order_id
        WHERE o.status = 'DELIVERED'
    """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Book> getBestSellerBooks() {
        List<Book> list = new ArrayList<>();

        String sql = """
        SELECT TOP 5 
            b.book_id,
            b.title,
            SUM(od.quantity) AS sold
        FROM OrderDetail od
        JOIN Orders o ON od.order_id = o.order_id
        JOIN Book b ON od.book_id = b.book_id
        WHERE o.status = 'DELIVERED'
        GROUP BY b.book_id, b.title
        ORDER BY sold DESC
    """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Book b = new Book();
                b.setBookId(rs.getInt("book_id"));
                b.setTitle(rs.getString("title"));
                b.setSold(rs.getInt("sold"));
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int insertOrderMyMy(Connection con, int customerId, String shippingAddress, double shippingFee, String receiverName, String phone, List<CartItem> items) throws Exception {
        String sqlOrder = "INSERT INTO Orders (customer_id, staff_id, order_date, status, discount, shipping_address, shipping_fee, receiver_name, phone) VALUES (?, ?, GETDATE(), ?, ?, ?, ?, ?, ?)";
        int orderId = 0;

        try (PreparedStatement ps = con.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, customerId);
            ps.setNull(2, java.sql.Types.INTEGER);
            ps.setString(3, "Pending");
            ps.setInt(4, 0);
            ps.setString(5, shippingAddress);
            ps.setDouble(6, shippingFee);
            ps.setString(7, receiverName);
            ps.setString(8, phone);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return orderId;
    }

    public List<Orders> getOrdersByCustomerPagingFull(int customerId, int page, int pageSize) {
        List<Orders> list = new ArrayList<>();
        int offset = (page - 1) * pageSize;

        String sql = """
    SELECT *
    FROM Orders
    WHERE customer_id = ?
    ORDER BY order_id DESC
    OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
    """;

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.setInt(2, offset);
            ps.setInt(3, pageSize);

            ResultSet rs = ps.executeQuery();
            OrderDetailDAO detailDAO = new OrderDetailDAO();

            while (rs.next()) {
                Orders o = new Orders();
                o.setOrderId(rs.getInt("order_id"));
                o.setStatus(rs.getString("status"));
                o.setPaymentMethod(rs.getString("payment_method"));

                List<OrderItem> items = detailDAO.getItemsByOrderId(o.getOrderId());
                o.setItems(items);

                int totalQty = 0;
                double totalAmount = 0;
                for (OrderItem item : items) {
                    totalQty += item.getQuantity();
                    totalAmount += item.getQuantity() * item.getPrice();
                }
                o.setQuantity(totalQty);
                o.setTotalAmount(totalAmount);

                list.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int countOrdersByStatus(int userId, String status) {
        String sql = "SELECT COUNT(*) FROM Orders WHERE customer_id = ? AND status = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, status);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Orders> getOrdersByStatusPaging(int customerId, String status, int page, int pageSize) {
        List<Orders> list = new ArrayList<>();
        int offset = (page - 1) * pageSize;

        String sql = """
        SELECT *
        FROM Orders
        WHERE customer_id = ? AND status = ?
        ORDER BY order_id DESC
        OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
    """;

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.setString(2, status);
            ps.setInt(3, offset);
            ps.setInt(4, pageSize);

            ResultSet rs = ps.executeQuery();
            OrderDetailDAO detailDAO = new OrderDetailDAO();

            while (rs.next()) {
                Orders o = new Orders();
                o.setOrderId(rs.getInt("order_id"));
                o.setStatus(rs.getString("status"));
                o.setPaymentMethod(rs.getString("payment_method"));

                List<OrderItem> items = detailDAO.getItemsByOrderId(o.getOrderId());
                o.setItems(items);

                int totalQty = 0;
                double totalAmount = 0;
                for (OrderItem item : items) {
                    totalQty += item.getQuantity();
                    totalAmount += item.getQuantity() * item.getPrice();
                }

                o.setQuantity(totalQty);
                o.setTotalAmount(totalAmount);

                list.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
