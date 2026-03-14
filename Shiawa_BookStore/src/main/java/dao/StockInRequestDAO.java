package dao;

import db.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.Book;
import model.Category;
import model.StockInRequest;
import model.StockInRequestItem;
import model.StockTxn;
import model.StockTxnItem;

public class StockInRequestDAO extends DBContext {

    public void ensureTables() throws Exception {
        String createRequest = """
            IF OBJECT_ID(N'dbo.StockInRequest', N'U') IS NULL
            BEGIN
                CREATE TABLE dbo.StockInRequest (
                    request_id INT IDENTITY(1,1) PRIMARY KEY,
                    request_code VARCHAR(40) NOT NULL UNIQUE,
                    note NVARCHAR(500) NULL,
                    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
                    requested_by_staff_id INT NULL,
                    approved_by_staff_id INT NULL,
                    reject_reason NVARCHAR(500) NULL,
                    created_at DATETIME2 NOT NULL DEFAULT (SYSDATETIME()),
                    approved_at DATETIME2 NULL
                )
            END
        """;

        String createItem = """
            IF OBJECT_ID(N'dbo.StockInRequestItem', N'U') IS NULL
            BEGIN
                CREATE TABLE dbo.StockInRequestItem (
                    item_id INT IDENTITY(1,1) PRIMARY KEY,
                    request_id INT NOT NULL,
                    book_id INT NULL,
                    new_book_title NVARCHAR(255) NULL,
                    new_book_author NVARCHAR(255) NULL,
                    new_book_publisher NVARCHAR(255) NULL,
                    new_book_category_id INT NULL,
                    qty INT NOT NULL,
                    unit_cost DECIMAL(18,2) NULL,
                    CONSTRAINT FK_StockInRequestItem_Request FOREIGN KEY (request_id)
                        REFERENCES dbo.StockInRequest(request_id) ON DELETE CASCADE,
                    CONSTRAINT FK_StockInRequestItem_Book FOREIGN KEY (book_id)
                        REFERENCES dbo.Book(book_id)
                )
            END
        """;

        String alterItemColumns = """
            IF COL_LENGTH('dbo.StockInRequestItem', 'new_book_author') IS NULL
                ALTER TABLE dbo.StockInRequestItem ADD new_book_author NVARCHAR(255) NULL;
            IF COL_LENGTH('dbo.StockInRequestItem', 'new_book_publisher') IS NULL
                ALTER TABLE dbo.StockInRequestItem ADD new_book_publisher NVARCHAR(255) NULL;
            IF COL_LENGTH('dbo.StockInRequestItem', 'new_book_category_id') IS NULL
                ALTER TABLE dbo.StockInRequestItem ADD new_book_category_id INT NULL;
        """;

        try (PreparedStatement ps1 = getConnection().prepareStatement(createRequest);
             PreparedStatement ps2 = getConnection().prepareStatement(createItem);
             PreparedStatement ps3 = getConnection().prepareStatement(alterItemColumns)) {
            ps1.execute();
            ps2.execute();
            ps3.execute();
        }
    }

    public int createPendingRequest(StockInRequest req) throws Exception {
        ensureTables();
        Connection conn = getConnection();
        boolean oldAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);

        try {
            String sql = """
                INSERT INTO StockInRequest(request_code, note, status, requested_by_staff_id)
                VALUES(?, ?, 'PENDING', ?)
            """;
            int requestId;
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, req.getRequestCode());
                ps.setString(2, req.getNote());
                if (req.getRequestedByStaffId() == null) {
                    ps.setNull(3, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(3, req.getRequestedByStaffId());
                }
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (!rs.next()) throw new Exception("Cannot create stock-in request.");
                    requestId = rs.getInt(1);
                }
            }

            String itemSql = """
                INSERT INTO StockInRequestItem(request_id, book_id, new_book_title, new_book_author, new_book_publisher, new_book_category_id, qty, unit_cost)
                VALUES(?, ?, ?, ?, ?, ?, ?, ?)
            """;
            try (PreparedStatement ps = conn.prepareStatement(itemSql)) {
                for (StockInRequestItem it : req.getItems()) {
                    ps.setInt(1, requestId);
                    if (it.getBookId() == null) {
                        ps.setNull(2, java.sql.Types.INTEGER);
                    } else {
                        ps.setInt(2, it.getBookId());
                    }
                    ps.setString(3, it.getNewBookTitle());
                    ps.setString(4, it.getNewBookAuthor());
                    ps.setString(5, it.getNewBookPublisher());
                    if (it.getNewBookCategoryId() == null) {
                        ps.setNull(6, java.sql.Types.INTEGER);
                    } else {
                        ps.setInt(6, it.getNewBookCategoryId());
                    }
                    ps.setInt(7, it.getQty());
                    if (it.getUnitCost() == null) {
                        ps.setNull(8, java.sql.Types.DECIMAL);
                    } else {
                        ps.setBigDecimal(8, java.math.BigDecimal.valueOf(it.getUnitCost()));
                    }
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            conn.commit();
            return requestId;
        } catch (Exception ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(oldAutoCommit);
        }
    }

    public List<StockInRequestItem> getItemsByRequestId(int requestId) {
        List<StockInRequestItem> items = new ArrayList<>();
        String sql = """
            SELECT i.item_id, i.request_id, i.book_id, i.new_book_title, i.new_book_author,
                   i.new_book_publisher, i.new_book_category_id, i.qty, i.unit_cost, b.title
            FROM StockInRequestItem i
            LEFT JOIN Book b ON i.book_id = b.book_id
            WHERE i.request_id = ?
            ORDER BY i.item_id
        """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, requestId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StockInRequestItem it = new StockInRequestItem();
                    it.setItemId(rs.getInt("item_id"));
                    it.setRequestId(rs.getInt("request_id"));
                    int bid = rs.getInt("book_id");
                    if (!rs.wasNull()) it.setBookId(bid);
                    it.setNewBookTitle(rs.getString("new_book_title"));
                    it.setNewBookAuthor(rs.getString("new_book_author"));
                    it.setNewBookPublisher(rs.getString("new_book_publisher"));
                    int catId = rs.getInt("new_book_category_id");
                    if (!rs.wasNull()) it.setNewBookCategoryId(catId);
                    it.setQty(rs.getInt("qty"));
                    java.math.BigDecimal uc = rs.getBigDecimal("unit_cost");
                    if (uc != null) it.setUnitCost(uc.doubleValue());
                    it.setBookTitle(rs.getString("title"));
                    items.add(it);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<StockInRequest> getRequestsByRequester(Integer requestedByStaffId) {
        List<StockInRequest> list = new ArrayList<>();
        try {
            ensureTables();
            String sql = """
                SELECT request_id, request_code, note, status, requested_by_staff_id, approved_by_staff_id,
                       reject_reason, created_at, approved_at
                FROM StockInRequest
                WHERE (? IS NULL OR requested_by_staff_id = ?)
                ORDER BY request_id DESC
            """;
            try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
                if (requestedByStaffId == null) {
                    ps.setNull(1, java.sql.Types.INTEGER);
                    ps.setNull(2, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(1, requestedByStaffId);
                    ps.setInt(2, requestedByStaffId);
                }
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        StockInRequest r = new StockInRequest();
                        r.setRequestId(rs.getInt("request_id"));
                        r.setRequestCode(rs.getString("request_code"));
                        r.setNote(rs.getString("note"));
                        r.setStatus(rs.getString("status"));
                        int requestedBy = rs.getInt("requested_by_staff_id");
                        if (!rs.wasNull()) r.setRequestedByStaffId(requestedBy);
                        int approvedBy = rs.getInt("approved_by_staff_id");
                        if (!rs.wasNull()) r.setApprovedByStaffId(approvedBy);
                        r.setRejectReason(rs.getString("reject_reason"));
                        list.add(r);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<StockInRequest> getRequestsWithItems(Integer requestedByStaffId) {
        Map<Integer, StockInRequest> requestMap = new LinkedHashMap<>();
        String sql = """
            SELECT r.request_id, r.request_code, r.note, r.status, r.requested_by_staff_id,
                   r.approved_by_staff_id, r.reject_reason, r.created_at, r.approved_at,
                   i.item_id, i.book_id, i.new_book_title, i.new_book_author, i.new_book_publisher,
                   i.new_book_category_id, i.qty, i.unit_cost, b.title AS book_title
            FROM StockInRequest r
            LEFT JOIN StockInRequestItem i ON r.request_id = i.request_id
            LEFT JOIN Book b ON i.book_id = b.book_id
            WHERE (? IS NULL OR r.requested_by_staff_id = ?)
            ORDER BY r.request_id DESC, i.item_id
        """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            if (requestedByStaffId == null) {
                ps.setNull(1, java.sql.Types.INTEGER);
                ps.setNull(2, java.sql.Types.INTEGER);
            } else {
                ps.setInt(1, requestedByStaffId);
                ps.setInt(2, requestedByStaffId);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int requestId = rs.getInt("request_id");
                    StockInRequest r = requestMap.get(requestId);
                    if (r == null) {
                        r = new StockInRequest();
                        r.setRequestId(requestId);
                        r.setRequestCode(rs.getString("request_code"));
                        r.setNote(rs.getString("note"));
                        r.setStatus(rs.getString("status"));
                        int requestedBy = rs.getInt("requested_by_staff_id");
                        if (!rs.wasNull()) r.setRequestedByStaffId(requestedBy);
                        int approvedBy = rs.getInt("approved_by_staff_id");
                        if (!rs.wasNull()) r.setApprovedByStaffId(approvedBy);
                        r.setRejectReason(rs.getString("reject_reason"));
                        r.setItems(new ArrayList<>());
                        requestMap.put(requestId, r);
                    }

                    int itemId = rs.getInt("item_id");
                    if (!rs.wasNull()) {
                        StockInRequestItem it = new StockInRequestItem();
                        it.setItemId(itemId);
                        it.setRequestId(requestId);
                        int bid = rs.getInt("book_id");
                        if (!rs.wasNull()) it.setBookId(bid);
                        it.setNewBookTitle(rs.getString("new_book_title"));
                        it.setNewBookAuthor(rs.getString("new_book_author"));
                        it.setNewBookPublisher(rs.getString("new_book_publisher"));
                        int catId = rs.getInt("new_book_category_id");
                        if (!rs.wasNull()) it.setNewBookCategoryId(catId);
                        it.setQty(rs.getInt("qty"));
                        java.math.BigDecimal uc = rs.getBigDecimal("unit_cost");
                        if (uc != null) it.setUnitCost(uc.doubleValue());
                        it.setBookTitle(rs.getString("book_title"));
                        r.getItems().add(it);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>(requestMap.values());
    }

    public void approveRequest(int requestId, Integer approvedByStaffId) throws Exception {
        ensureTables();
        Connection conn = getConnection();
        boolean oldAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);

        try {
            String checkSql = "SELECT status, request_code, note FROM StockInRequest WHERE request_id = ?";
            String status;
            String requestCode;
            String note;
            try (PreparedStatement ps = conn.prepareStatement(checkSql)) {
                ps.setInt(1, requestId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) throw new Exception("Request not found: " + requestId);
                    status = rs.getString("status");
                    requestCode = rs.getString("request_code");
                    note = rs.getString("note");
                }
            }
            if (!"PENDING".equalsIgnoreCase(status)) {
                throw new Exception("Request already processed.");
            }

            List<StockInRequestItem> requestItems = getItemsByRequestId(requestId);
            if (requestItems.isEmpty()) {
                throw new Exception("Request has no items.");
            }

            BookDAO bookDAO = new BookDAO();
            List<StockTxnItem> txnItems = new ArrayList<>();
            for (StockInRequestItem it : requestItems) {
                Integer bid = it.getBookId();
                if (bid == null) {
                    String title = it.getNewBookTitle() == null ? "" : it.getNewBookTitle().trim();
                    if (title.isEmpty()) {
                        throw new Exception("New book title is empty in request item " + it.getItemId());
                    }

                    String author = it.getNewBookAuthor() == null ? "" : it.getNewBookAuthor().trim();
                    String publisher = it.getNewBookPublisher() == null ? "" : it.getNewBookPublisher().trim();
                    Integer categoryId = it.getNewBookCategoryId();

                    if (author.isEmpty()) {
                        throw new Exception("Thiếu tác giả cho sách mới ở item " + it.getItemId());
                    }
                    if (publisher.isEmpty()) {
                        throw new Exception("Thiếu nhà xuất bản cho sách mới ở item " + it.getItemId());
                    }
                    if (categoryId == null) {
                        throw new Exception("Thiếu thể loại cho sách mới ở item " + it.getItemId());
                    }

                    Category cat = new CategoryDAO().findById(categoryId);
                    if (cat == null) {
                        throw new Exception("Thể loại không tồn tại cho sách mới ở item " + it.getItemId());
                    }

                    Book b = new Book();
                    b.setTitle(title);
                    b.setAuthor(author);
                    b.setPublisher(publisher);
                    b.setPrice(0);
                    b.setStock(0);
                    b.setCategory(cat);
                    bid = bookDAO.insertBookReturnId(b);
                }

                StockTxnItem txnItem = new StockTxnItem();
                txnItem.setBookId(bid);
                txnItem.setQty(it.getQty());
                txnItem.setUnitCost(it.getUnitCost());
                txnItems.add(txnItem);
            }

            StockTxn txn = new StockTxn();
            txn.setTxnType("IN");
            txn.setTxnCode("APR-" + requestCode);
            txn.setNote(note == null ? "Approved stock-in request #" + requestId : note);
            txn.setCreatedByStaffId(approvedByStaffId);
            txn.setItems(txnItems);
            new StockTxnDAO().createTxnAndApplyStock(txn);

            String update = """
                UPDATE StockInRequest
                SET status='APPROVED', approved_by_staff_id=?, approved_at=SYSDATETIME()
                WHERE request_id=?
            """;
            try (PreparedStatement ps = conn.prepareStatement(update)) {
                if (approvedByStaffId == null) {
                    ps.setNull(1, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(1, approvedByStaffId);
                }
                ps.setInt(2, requestId);
                ps.executeUpdate();
            }

            conn.commit();
        } catch (Exception ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(oldAutoCommit);
        }
    }

    public void rejectRequest(int requestId, Integer approvedByStaffId, String reason) throws Exception {
        ensureTables();
        String sql = """
            UPDATE StockInRequest
            SET status='REJECTED', approved_by_staff_id=?, approved_at=SYSDATETIME(), reject_reason=?
            WHERE request_id=? AND status='PENDING'
        """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            if (approvedByStaffId == null) {
                ps.setNull(1, java.sql.Types.INTEGER);
            } else {
                ps.setInt(1, approvedByStaffId);
            }
            ps.setString(2, reason);
            ps.setInt(3, requestId);
            ps.executeUpdate();
        }
    }
}
