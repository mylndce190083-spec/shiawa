package dao;

import db.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.ImportReceipt;
import model.ImportReceiptDetail;

public class ImportReceiptDAO extends DBContext {

    public List<ImportReceipt> getReceipts() {
        List<ImportReceipt> list = new ArrayList<>();
        String sql = """
            SELECT r.receipt_id, r.import_date, r.total_amount, r.note,
                   s.name AS supplier_name
            FROM ImportReceipt r
            LEFT JOIN Supplier s ON r.supplier_id = s.supplier_id
            ORDER BY r.import_date DESC, r.receipt_id DESC
        """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ImportReceipt r = new ImportReceipt();
                r.setReceiptId(rs.getInt("receipt_id"));
                r.setImportDate(rs.getTimestamp("import_date").toLocalDateTime());
                r.setTotalAmount(rs.getDouble("total_amount"));
                r.setNote(rs.getString("note"));
                r.setSupplierName(rs.getString("supplier_name"));
                list.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ImportReceipt getReceiptWithDetails(int receiptId) {
        ImportReceipt r = null;
        String sql = """
            SELECT r.receipt_id, r.import_date, r.total_amount, r.note,
                   r.supplier_id, s.name AS supplier_name
            FROM ImportReceipt r
            LEFT JOIN Supplier s ON r.supplier_id = s.supplier_id
            WHERE r.receipt_id = ?
        """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, receiptId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    r = new ImportReceipt();
                    r.setReceiptId(rs.getInt("receipt_id"));
                    r.setImportDate(rs.getTimestamp("import_date").toLocalDateTime());
                    r.setTotalAmount(rs.getDouble("total_amount"));
                    r.setNote(rs.getString("note"));
                    r.setSupplierName(rs.getString("supplier_name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (r == null) return null;

        List<ImportReceiptDetail> details = new ArrayList<>();
        String dsql = """
            SELECT d.detail_id, d.book_id, d.qty, d.import_price, b.title
            FROM ImportReceiptDetail d
            JOIN Book b ON b.book_id = d.book_id
            WHERE d.receipt_id = ?
        """;
        try (PreparedStatement ps = getConnection().prepareStatement(dsql)) {
            ps.setInt(1, receiptId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ImportReceiptDetail d = new ImportReceiptDetail();
                    d.setDetailId(rs.getInt("detail_id"));
                    d.setReceiptId(receiptId);
                    d.setBookId(rs.getInt("book_id"));
                    d.setQty(rs.getInt("qty"));
                    d.setImportPrice(rs.getDouble("import_price"));
                    d.setBookTitle(rs.getString("title"));
                    details.add(d);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        r.setDetails(details);
        return r;
    }

    public int insertReceipt(ImportReceipt receipt) throws Exception {
        Connection conn = getConnection();
        boolean oldAuto = conn.getAutoCommit();
        conn.setAutoCommit(false);
        try {
            int receiptId = insertReceiptHeader(conn, receipt);
            double total = 0;
            for (ImportReceiptDetail d : receipt.getDetails()) {
                insertDetail(conn, receiptId, d);
                updateBookStock(conn, d.getBookId(), d.getQty());
                total += d.getQty() * d.getImportPrice();
            }
            updateReceiptTotal(conn, receiptId, total);
            conn.commit();
            return receiptId;
        } catch (Exception ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(oldAuto);
        }
    }

    private int insertReceiptHeader(Connection conn, ImportReceipt r) throws Exception {
        String sql = """
            INSERT INTO ImportReceipt(staff_id, supplier_id, import_date, total_amount, note)
            VALUES(?, ?, SYSDATETIME(), ?, ?)
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (r.getStaffId() == null) {
                ps.setNull(1, java.sql.Types.INTEGER);
            } else {
                ps.setInt(1, r.getStaffId());
            
            }
            if (r.getTotalAmount() == null) {
                ps.setNull(3, java.sql.Types.DECIMAL);
            } else {
                ps.setDouble(3, r.getTotalAmount());
            }
            ps.setString(4, r.getNote());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        throw new Exception("Cannot insert ImportReceipt");
    }

    private void insertDetail(Connection conn, int receiptId, ImportReceiptDetail d) throws Exception {
        String sql = """
            INSERT INTO ImportReceiptDetail(receipt_id, book_id, qty, import_price)
            VALUES(?, ?, ?, ?)
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, receiptId);
            ps.setInt(2, d.getBookId());
            ps.setInt(3, d.getQty());
            ps.setDouble(4, d.getImportPrice());
            ps.executeUpdate();
        }
    }

    private void updateBookStock(Connection conn, int bookId, int deltaQty) throws Exception {
        String sql = "UPDATE Book SET stock = stock + ? WHERE book_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, deltaQty);
            ps.setInt(2, bookId);
            ps.executeUpdate();
        }
    }

    private void updateReceiptTotal(Connection conn, int receiptId, double total) throws Exception {
        String sql = "UPDATE ImportReceipt SET total_amount = ? WHERE receipt_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, total);
            ps.setInt(2, receiptId);
            ps.executeUpdate();
        }
    }

    public void updateReceipt(ImportReceipt receipt) throws Exception {
        Connection conn = getConnection();
        boolean oldAuto = conn.getAutoCommit();
        conn.setAutoCommit(false);
        try {
            int receiptId = receipt.getReceiptId();
            List<ImportReceiptDetail> oldDetails = new ArrayList<>();
            String sqlOld = "SELECT detail_id, book_id, qty, import_price FROM ImportReceiptDetail WHERE receipt_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlOld)) {
                ps.setInt(1, receiptId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        ImportReceiptDetail d = new ImportReceiptDetail();
                        d.setDetailId(rs.getInt("detail_id"));
                        d.setReceiptId(receiptId);
                        d.setBookId(rs.getInt("book_id"));
                        d.setQty(rs.getInt("qty"));
                        d.setImportPrice(rs.getDouble("import_price"));
                        oldDetails.add(d);
                    }
                }
            }

            for (ImportReceiptDetail d : oldDetails) {
                updateBookStock(conn, d.getBookId(), -d.getQty());
            }

            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM ImportReceiptDetail WHERE receipt_id = ?")) {
                ps.setInt(1, receiptId);
                ps.executeUpdate();
            }

            double total = 0;
            for (ImportReceiptDetail d : receipt.getDetails()) {
                insertDetail(conn, receiptId, d);
                updateBookStock(conn, d.getBookId(), d.getQty());
                total += d.getQty() * d.getImportPrice();
            }


            conn.commit();
        } catch (Exception ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(oldAuto);
        }
    }

    public void deleteReceipt(int receiptId) throws Exception {
        Connection conn = getConnection();
        boolean oldAuto = conn.getAutoCommit();
        conn.setAutoCommit(false);
        try {
            List<ImportReceiptDetail> details = new ArrayList<>();
            String sql = "SELECT book_id, qty FROM ImportReceiptDetail WHERE receipt_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, receiptId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        ImportReceiptDetail d = new ImportReceiptDetail();
                        d.setBookId(rs.getInt("book_id"));
                        d.setQty(rs.getInt("qty"));
                        details.add(d);
                    }
                }
            }

            for (ImportReceiptDetail d : details) {
                updateBookStock(conn, d.getBookId(), -d.getQty());
            }

            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM ImportReceipt WHERE receipt_id = ?")) {
                ps.setInt(1, receiptId);
                ps.executeUpdate();
            }

            conn.commit();
        } catch (Exception ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(oldAuto);
        }
    }
}


