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
import model.Voucher;

/**
 *
 * @author BA LIEM
 */
public class VoucherDAO extends DBContext {

    // Lấy tất cả voucher
    public List<Voucher> getAllVoucher() {

        List<Voucher> list = new ArrayList<>();

        String sql = "SELECT voucher_id, name, discount, quantity, createdAt, endedAt FROM Voucher";

        try {

            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Voucher v = new Voucher();

                v.setVoucher_id(rs.getInt("voucher_id"));
                v.setName(rs.getString("name"));
                v.setDiscount(rs.getDouble("discount"));
                v.setQuantity(rs.getInt("quantity"));
                v.setCreatedAt(rs.getDate("createdAt"));
                v.setEndedAt(rs.getDate("endedAt"));

                list.add(v);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Lấy voucher theo ID
    public Voucher getVoucherById(int id) {

        String sql = "SELECT * FROM Voucher WHERE voucher_id = ?";

        try {

            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Voucher v = new Voucher();

                v.setVoucher_id(rs.getInt("voucher_id"));
                v.setName(rs.getString("name"));
                v.setDiscount(rs.getDouble("discount"));
                v.setQuantity(rs.getInt("quantity"));
                v.setCreatedAt(rs.getDate("createdAt"));
                v.setEndedAt(rs.getDate("endedAt"));

                return v;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Thêm voucher
    public void insertVoucher(Voucher v) {

        String sql = "INSERT INTO Voucher(name, discount, quantity, createdAt, endedAt) VALUES(?,?,?,?,?)";

        try {

            PreparedStatement ps = getConnection().prepareStatement(sql);

            ps.setString(1, v.getName());
            ps.setDouble(2, v.getDiscount());
            ps.setInt(3, v.getQuantity());
            ps.setDate(4, new java.sql.Date(v.getCreatedAt().getTime()));
            ps.setDate(5, new java.sql.Date(v.getEndedAt().getTime()));

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update voucher
    public void updateVoucher(Voucher v) {

        String sql = "UPDATE Voucher SET name=?, discount=?, quantity=?, createdAt=?, endedAt=? WHERE voucher_id=?";

        try {

            PreparedStatement ps = getConnection().prepareStatement(sql);

            ps.setString(1, v.getName());
            ps.setDouble(2, v.getDiscount());
            ps.setInt(3, v.getQuantity());
            ps.setDate(4, new java.sql.Date(v.getCreatedAt().getTime()));
            ps.setDate(5, new java.sql.Date(v.getEndedAt().getTime()));
            ps.setInt(6, v.getVoucher_id());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xóa voucher
    public void deleteVoucher(int id) {

        String sql = "DELETE FROM Voucher WHERE voucher_id=?";

        try {

            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Voucher> getAllAvailableVoucher() {

        List<Voucher> list = new ArrayList<>();

        String sql = "SELECT *\n"
                + "FROM Voucher\n"
                + "WHERE endedAt >= GETDATE()\n"
                + "AND quantity > 0";

        try {

            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Voucher v = new Voucher();

                v.setVoucher_id(rs.getInt("voucher_id"));
                v.setName(rs.getString("name"));
                v.setDiscount(rs.getDouble("discount"));
                v.setQuantity(rs.getInt("quantity"));
                v.setCreatedAt(rs.getDate("createdAt"));
                v.setEndedAt(rs.getDate("endedAt"));

                list.add(v);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean hasVoucher(Connection conn, int customerId, int voucherId) {
        String sql = """
        SELECT COUNT(*) 
        FROM Customer_Voucher
        WHERE customer_id = ? AND voucher_id = ?
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.setInt(2, voucherId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void insertCustomerVoucher(Connection conn, int customerId, int voucherId) {
        String sql = """
        INSERT INTO Customer_Voucher(customer_id, voucher_id, status)
        VALUES (?, ?, 'unused')
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.setInt(2, voucherId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean decreaseVoucherQuantity(Connection conn, int voucherId) {
        String sql = """
        UPDATE Voucher
        SET quantity = quantity - 1
        WHERE voucher_id = ? AND quantity > 0
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, voucherId);

            int rows = ps.executeUpdate();
            return rows > 0; // false nếu hết voucher
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public enum ClaimVoucherResult {
        SUCCESS,
        ALREADY_HAVE,
        OUT_OF_STOCK,
        ERROR
    }

    public ClaimVoucherResult claimVoucher(int customerId, int voucherId) {
        Connection conn = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            // 1. check đã có chưa
            if (hasVoucher(conn, customerId, voucherId)) {
                conn.rollback();
                return ClaimVoucherResult.ALREADY_HAVE;
            }

            // 2. giảm quantity trước
            if (!decreaseVoucherQuantity(conn, voucherId)) {
                conn.rollback();
                return ClaimVoucherResult.OUT_OF_STOCK;
            }

            // 3. insert vào túi
            insertCustomerVoucher(conn, customerId, voucherId);

            conn.commit();
            return ClaimVoucherResult.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close(); // ❗ nhớ đóng connection
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ClaimVoucherResult.ERROR;
    }

    public List<Voucher> getMyVoucherList(int customerId) {
        List<Voucher> list = new ArrayList<>();

        String sql = """
        SELECT v.*, cv.status, cv.customer_voucher_id
        FROM Customer_Voucher cv
        JOIN Voucher v ON cv.voucher_id = v.voucher_id
        WHERE cv.customer_id = ? and cv.status = 'unused'
        AND v.endedAt >= DATEADD(DAY, -3, GETDATE())
        ORDER BY v.endedAt DESC
    """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Voucher v = new Voucher();

                v.setVoucher_id(rs.getInt("voucher_id"));
                v.setName(rs.getString("name"));
                v.setDiscount(rs.getDouble("discount"));
                v.setQuantity(rs.getInt("quantity"));
                v.setCreatedAt(rs.getDate("createdAt"));
                v.setEndedAt(rs.getDate("endedAt"));

                // status từ Customer_Voucher
                v.setStatus(rs.getString("status"));

                list.add(v);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void markVoucherAsUsed(int voucherId, int orderId) {
        String sql = "UPDATE Customer_Voucher SET status = 'used', order_id = ? WHERE voucher_id = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ps.setInt(2, voucherId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        VoucherDAO dao = new VoucherDAO();
        System.out.println(dao.getAllAvailableVoucher());
        List<Voucher> list = dao.getMyVoucherList(1);
        for (Voucher v : list) {
            System.out.println(v);
        }
    }

}
