package dao;

import db.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import model.StockTxn;
import model.StockTxnItem;

public class StockTxnDAO extends DBContext {

    public int createTxnAndApplyStock(StockTxn txn) throws Exception {
        Connection conn = getConnection();
        boolean oldAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);

        try {
            int txnId = insertTxn(conn, txn);
            for (StockTxnItem it : txn.getItems()) {
                insertItem(conn, txnId, it);
                applyStockDelta(conn, txn.getTxnType(), it.getBookId(), it.getQty());
            }
            conn.commit();
            return txnId;
        } catch (Exception ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(oldAutoCommit);
        }
    }

    private int insertTxn(Connection conn, StockTxn txn) throws Exception {
        String sql = """
            INSERT INTO StockTxn(txn_type, txn_code, txn_date, supplier_id, note, created_by_staff_id)
            VALUES(?, ?, SYSDATETIME(), ?, ?, ?)
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, txn.getTxnType());
            ps.setString(2, txn.getTxnCode());

            if (txn.getSupplierId() == null) {
                ps.setNull(3, java.sql.Types.INTEGER);
            } else {
                ps.setInt(3, txn.getSupplierId());
            }

            ps.setString(4, txn.getNote());

            if (txn.getCreatedByStaffId() == null) {
                ps.setNull(5, java.sql.Types.INTEGER);
            } else {
                ps.setInt(5, txn.getCreatedByStaffId());
            }

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new Exception("Cannot create StockTxn (no generated key).");
    }

    private void insertItem(Connection conn, int txnId, StockTxnItem it) throws Exception {
        String sql = """
            INSERT INTO StockTxnItem(txn_id, book_id, qty, unit_cost)
            VALUES(?, ?, ?, ?)
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, txnId);
            ps.setInt(2, it.getBookId());
            ps.setInt(3, it.getQty());
            if (it.getUnitCost() == null) {
                ps.setNull(4, java.sql.Types.DECIMAL);
            } else {
                ps.setBigDecimal(4, java.math.BigDecimal.valueOf(it.getUnitCost()));
            }
            ps.executeUpdate();
        }
    }

    private void applyStockDelta(Connection conn, String txnType, int bookId, int qty) throws Exception {
        if ("IN".equalsIgnoreCase(txnType)) {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE Book SET stock = stock + ? WHERE book_id = ?")) {
                ps.setInt(1, qty);
                ps.setInt(2, bookId);
                ps.executeUpdate();
            }
            return;
        }

        if ("OUT".equalsIgnoreCase(txnType)) {
            // validate
            int current = 0;
            try (PreparedStatement ps = conn.prepareStatement("SELECT stock FROM Book WHERE book_id = ?")) {
                ps.setInt(1, bookId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        current = rs.getInt(1);
                    } else {
                        throw new Exception("Book not found: " + bookId);
                    }
                }
            }
            if (current < qty) {
                throw new Exception("Not enough stock for book_id=" + bookId + " (current=" + current + ", need=" + qty + ")");
            }
            try (PreparedStatement ps = conn.prepareStatement("UPDATE Book SET stock = stock - ? WHERE book_id = ?")) {
                ps.setInt(1, qty);
                ps.setInt(2, bookId);
                ps.executeUpdate();
            }
            return;
        }

        if ("ADJUST".equalsIgnoreCase(txnType)) {
            throw new Exception("ADJUST is not supported by createTxnAndApplyStock() yet. Use IN/OUT.");
        }

        throw new Exception("Unknown txn_type: " + txnType);
    }
}
