package dao;

import db.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO extends DBContext {

    public static class DailyRow {
        private String day;
        private String txnType;
        private int totalQty;

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getTxnType() {
            return txnType;
        }

        public void setTxnType(String txnType) {
            this.txnType = txnType;
        }

        public int getTotalQty() {
            return totalQty;
        }

        public void setTotalQty(int totalQty) {
            this.totalQty = totalQty;
        }
    }

    public static class ProductRow {
        private int bookId;
        private String title;
        private String txnType;
        private int totalQty;

        public int getBookId() {
            return bookId;
        }

        public void setBookId(int bookId) {
            this.bookId = bookId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTxnType() {
            return txnType;
        }

        public void setTxnType(String txnType) {
            this.txnType = txnType;
        }

        public int getTotalQty() {
            return totalQty;
        }

        public void setTotalQty(int totalQty) {
            this.totalQty = totalQty;
        }
    }

    public List<DailyRow> getDailySummary(LocalDate from, LocalDate to) {
        List<DailyRow> out = new ArrayList<>();
        String sql = """
            SELECT CONVERT(date, t.txn_date) AS [day], t.txn_type, SUM(i.qty) AS total_qty
            FROM StockTxn t
            JOIN StockTxnItem i ON t.txn_id = i.txn_id
            WHERE t.txn_date >= ? AND t.txn_date < DATEADD(day, 1, ?)
            GROUP BY CONVERT(date, t.txn_date), t.txn_type
            ORDER BY [day] DESC, t.txn_type
        """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, from.toString());
            ps.setString(2, to.toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DailyRow r = new DailyRow();
                    r.setDay(rs.getString("day"));
                    r.setTxnType(rs.getString("txn_type"));
                    r.setTotalQty(rs.getInt("total_qty"));
                    out.add(r);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public List<ProductRow> getProductSummary(LocalDate from, LocalDate to, Integer bookId) {
        List<ProductRow> out = new ArrayList<>();
        String sql = """
            SELECT b.book_id, b.title, t.txn_type, SUM(i.qty) AS total_qty
            FROM StockTxn t
            JOIN StockTxnItem i ON t.txn_id = i.txn_id
            JOIN Book b ON b.book_id = i.book_id
            WHERE t.txn_date >= ? AND t.txn_date < DATEADD(day, 1, ?)
              AND (? IS NULL OR b.book_id = ?)
            GROUP BY b.book_id, b.title, t.txn_type
            ORDER BY b.title, t.txn_type
        """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, from.toString());
            ps.setString(2, to.toString());
            if (bookId == null) {
                ps.setNull(3, java.sql.Types.INTEGER);
                ps.setNull(4, java.sql.Types.INTEGER);
            } else {
                ps.setInt(3, bookId);
                ps.setInt(4, bookId);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductRow r = new ProductRow();
                    r.setBookId(rs.getInt("book_id"));
                    r.setTitle(rs.getString("title"));
                    r.setTxnType(rs.getString("txn_type"));
                    r.setTotalQty(rs.getInt("total_qty"));
                    out.add(r);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }
}



