/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Book;
import model.BookAdmin;
import model.CartItem;
import model.Category;

/**
 *
 * @author BA LIEM
 */
public class BookDAO extends DBContext {

    public List<BookAdmin> getAllBooksInfo() {
        List<BookAdmin> list = new ArrayList<>();

        String sql = """
        SELECT
            b.book_id,
            b.title,
            b.author,
            b.price,
            b.stock,
            b.publisher,
            b.discount,
            b.url_img,
            b.is_active,
            b.created_at,
            c.name AS category_name
        FROM Book b
        LEFT JOIN Category c
            ON b.category_id = c.category_id
        ORDER BY b.book_id
    """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                BookAdmin b = new BookAdmin();
                b.setBookId(rs.getInt("book_id"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setPrice(rs.getDouble("price"));
                b.setStock(rs.getInt("stock"));
                b.setPublisher(rs.getString("publisher"));
                b.setDiscount(rs.getInt("discount"));
                b.setUrlImg(rs.getString("url_img"));
                b.setIsActive(rs.getBoolean("is_active"));
                b.setCreatedAt(rs.getString("created_at"));
                b.setCategoryName(rs.getString("category_name"));

                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insertBook(BookAdmin b) {
        String sql = """
        INSERT INTO Book
        (title, author, price, stock, category_id, is_active)
        VALUES (?, ?, ?, ?, ?, 1)
    """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setDouble(3, b.getPrice());
            ps.setInt(4, b.getStock());
            ps.setInt(5, b.getCategoryId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Book> getAllBook() {
        List<Book> list = new ArrayList<>();
        CategoryDAO dao = new CategoryDAO();
        String sql = "select * from Book where is_published = 1";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                //tao cate
                int cateId = rs.getInt("category_id");
                Category cate = dao.getCategoryById(cateId);

                int stock = rs.getInt("stock");
                String publisher = rs.getString("publisher");
                int discount = rs.getInt("discount");
                String imgUrl = this.getImgURLbyBookId(id);
                boolean isActive = rs.getBoolean("is_active");
                LocalDateTime createAte = rs.getTimestamp("created_at").toLocalDateTime();
                //tao doi tuong product
                Book b = new Book();
                b.setBookId(id);
                b.setTitle(title);
                b.setAuthor(author);
                b.setPrice(price);
                b.setDescription(description);
                b.setCategory(cate);
                b.setStock(stock);
                b.setPublisher(publisher);
                b.setDiscount(discount);
                b.setUrlImg(imgUrl);
                b.setIsActive(isActive);
                b.setCreatedAt(createAte);
                b.setSold(getSoldQuantity(id));   // 🔥 thêm dòng này
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getImgURLbyBookId(int bookId) {
        String url = "";
        String sql = """
        SELECT * FROM BookImages
        WHERE book_id = ? AND is_active = 1 and is_primary =1
        ORDER BY is_primary DESC, display_order ASC
    """;
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                url = rs.getString("image_url");
            }
        } catch (Exception e) {
        }
        return url;
    }

    public Book getBookById(int bookId) {
        String sql = """
        SELECT *
        FROM Book
        WHERE book_id = ?
    """;

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, bookId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                CategoryDAO cdao = new CategoryDAO();
                Category cate = cdao.getCategoryById(rs.getInt("category_id"));

                return new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        cate,
                        rs.getInt("stock"),
                        rs.getString("publisher"),
                        rs.getInt("discount"),
                        getImgURLbyBookId(rs.getInt("book_id")),
                        rs.getBoolean("is_active"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public BookAdmin getBookAdminById(int id) {
        String sql = """
        SELECT b.book_id,
               b.title,
               b.author,
               b.description,
               b.price,
               b.stock,
               b.publisher,
               b.discount,
               b.url_img,
               b.is_active,
               b.created_at,
               c.name AS category_name,
               c.category_id
        FROM Book b
        LEFT JOIN Category c ON b.category_id = c.category_id
        WHERE b.book_id = ?
    """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                BookAdmin b = new BookAdmin();
                b.setBookId(rs.getInt("book_id"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setDescription(rs.getString("description"));
                b.setPrice(rs.getDouble("price"));
                b.setStock(rs.getInt("stock"));
                b.setPublisher(rs.getString("publisher"));
                b.setDiscount(rs.getInt("discount"));
                b.setUrlImg(rs.getString("url_img"));
                b.setIsActive(rs.getBoolean("is_active"));
                b.setCreatedAt(rs.getString("created_at"));
                b.setCategoryName(rs.getString("category_name"));
                b.setCategoryId(rs.getInt("category_id"));
                return b;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getStock(Connection con, int bookId) throws Exception {

        String sql = "SELECT stock FROM Book WHERE book_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("stock");
                }
            }
        }

        throw new Exception("Book not found");
    }

    public void updateStock(Connection con,
            int bookId,
            int quantity) throws Exception {

        String sql = """
            UPDATE Book
            SET stock = stock - ?
            WHERE book_id = ?
        """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, bookId);

            ps.executeUpdate();
        }
    }

    public List<Book> getSimilarBook(int categoryId) {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT TOP 6 b.*, c.name AS category_name "
                + "FROM Book b "
                + "LEFT JOIN Category c ON b.category_id = c.category_id"
                + " Where c.category_id = ? ";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("book_id");
                Book b = new Book();
                b.setBookId(rs.getInt("book_id"));
                b.setTitle(rs.getString("title"));
                b.setPrice(rs.getDouble("price"));
                b.setUrlImg(this.getImgURLbyBookId(rs.getInt("book_id")));
                //b.setUrlImg(rs.getString("url_img"));

                Category c = new Category();
                c.setCategoryName(rs.getString("category_name"));
                b.setCategory(c);
                b.setSold(getSoldQuantity(id));   // 🔥 thêm dòng này
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Book> searchBookByName(String keyword) {
        List<Book> list = new ArrayList<>();
        CategoryDAO dao = new CategoryDAO();

        String sql = "SELECT * FROM Book WHERE title LIKE ?";

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                double price = rs.getDouble("price");
                String description = rs.getString("description");

                int cateId = rs.getInt("category_id");
                Category cate = dao.getCategoryById(cateId);

                int stock = rs.getInt("stock");
                String publisher = rs.getString("publisher");
                int discount = rs.getInt("discount");

                String imgUrl = this.getImgURLbyBookId(id);

                boolean isActive = rs.getBoolean("is_active");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();

                Book b = new Book();
                b.setBookId(id);
                b.setTitle(title);
                b.setAuthor(author);
                b.setPrice(price);
                b.setDescription(description);
                b.setCategory(cate);
                b.setStock(stock);
                b.setPublisher(publisher);
                b.setDiscount(discount);
                b.setUrlImg(imgUrl);
                b.setIsActive(isActive);
                b.setCreatedAt(createdAt);
                b.setSold(getSoldQuantity(id));

                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Book> getBooksByCategoryId(int cateId) {
        List<Book> list = new ArrayList<>();
        CategoryDAO dao = new CategoryDAO();

        String sql = "SELECT * FROM Book WHERE category_id = ?";

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, cateId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                double price = rs.getDouble("price");
                String description = rs.getString("description");

                int categoryId = rs.getInt("category_id");
                Category cate = dao.getCategoryById(categoryId);

                int stock = rs.getInt("stock");
                String publisher = rs.getString("publisher");
                int discount = rs.getInt("discount");

                String imgUrl = this.getImgURLbyBookId(id);

                boolean isActive = rs.getBoolean("is_active");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();

                Book b = new Book();
                b.setBookId(id);
                b.setTitle(title);
                b.setAuthor(author);
                b.setPrice(price);
                b.setDescription(description);
                b.setCategory(cate);
                b.setStock(stock);
                b.setPublisher(publisher);
                b.setDiscount(discount);
                b.setUrlImg(imgUrl);
                b.setIsActive(isActive);
                b.setCreatedAt(createdAt);
                b.setSold(getSoldQuantity(id));   // 🔥 thêm dòng này
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void increaseStock(Connection con, int bookId, int quantity) {
        String sql = "UPDATE Book SET stock = stock + ? WHERE book_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, bookId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBook(BookAdmin b) {
        String sql = """
        UPDATE Book
        SET title = ?, author = ?, category_id = ?, price = ?,
            stock = ?, is_active = ?, description = ?, url_img = ?
        WHERE book_id = ?
    """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setInt(3, b.getCategoryId());
            ps.setDouble(4, b.getPrice());
            ps.setInt(5, b.getStock());
            ps.setBoolean(6, b.isIsActive());
            ps.setString(7, b.getDescription());
            ps.setString(8, b.getUrlImg());
            ps.setInt(9, b.getBookId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public boolean isBookUsedInOrder(int bookId) {
//        String sql = """
//        SELECT COUNT(*)
//                FROM OrderDetail od
//                JOIN Orders o ON od.order_id = o.order_id
//                WHERE od.book_id = ?
//                AND o.status != 'DELIVERED'
//    """;
//
//        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
//            ps.setInt(1, bookId);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return rs.getInt(1) > 0;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
    public boolean isBookInActiveOrder(int bookId) {
        String sql = """
        SELECT COUNT(*)
        FROM OrderDetail od
        JOIN [Order] o ON od.order_id = o.order_id
        WHERE od.book_id = ?
        AND o.status NOT IN ('DELIVERED', 'FAILED')
    """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean canHardDelete(int bookId) {

        // 1. check stock
        String sql = "SELECT stock FROM Book WHERE book_id = ?";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int stock = rs.getInt("stock");
                if (stock > 0) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. check order đang active
        if (isBookInActiveOrder(bookId)) {
            return false;
        }

        return true;
    }

    public void hardDeleteBook(int bookId) {

        String deleteImages = "DELETE FROM BookImages WHERE book_id = ?";
        String deleteBook = "DELETE FROM Book WHERE book_id = ?";

        try (Connection conn = getConnection()) {

            conn.setAutoCommit(false); // transaction

            try (
                    PreparedStatement psImg = conn.prepareStatement(deleteImages); PreparedStatement psBook = conn.prepareStatement(deleteBook)) {

                // 1. Xóa ảnh trước
                psImg.setInt(1, bookId);
                psImg.executeUpdate();

                // 2. Xóa book
                psBook.setInt(1, bookId);
                psBook.executeUpdate();

                conn.commit();

            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void softDeleteBook(int bookId) {
        String sql = "UPDATE Book SET is_active = 0 WHERE book_id = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<BookAdmin> searchByTitle(String keyword) {
        List<BookAdmin> list = new ArrayList<>();
        String sql = """
        SELECT b.book_id, b.title, b.author, b.price, b.stock,
               b.publisher, b.discount, b.url_img, b.is_active,
               b.created_at, c.name AS category_name
        FROM Book b
        LEFT JOIN Category c ON b.category_id = c.category_id
        WHERE b.is_active = 1
          AND b.title LIKE ?
        ORDER BY b.book_id
    """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BookAdmin b = new BookAdmin();
                b.setBookId(rs.getInt("book_id"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setPrice(rs.getDouble("price"));
                b.setStock(rs.getInt("stock"));
                b.setCategoryName(rs.getString("category_name"));
                b.setIsActive(rs.getBoolean("is_active"));
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<BookAdmin> getBooksByCategory(int categoryId) { // filter
        List<BookAdmin> list = new ArrayList<>();

        String sql = """
        SELECT b.book_id, b.title, b.author, b.price, b.stock,
               b.publisher, b.discount, b.url_img, b.is_active,
               b.created_at, c.name AS category_name
        FROM Book b
        LEFT JOIN Category c ON b.category_id = c.category_id
        WHERE b.category_id = ?
        ORDER BY b.book_id
    """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BookAdmin b = new BookAdmin();
                b.setBookId(rs.getInt("book_id"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setPrice(rs.getDouble("price"));
                b.setStock(rs.getInt("stock"));
                b.setPublisher(rs.getString("publisher"));
                b.setDiscount(rs.getInt("discount"));
                b.setUrlImg(rs.getString("url_img"));
                b.setIsActive(rs.getBoolean("is_active"));
                b.setCreatedAt(rs.getString("created_at"));
                b.setCategoryName(rs.getString("category_name"));
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<BookAdmin> searchByTitleAndCategory(String keyword, int categoryId) { // filter
        List<BookAdmin> list = new ArrayList<>();

        String sql = """
        SELECT b.book_id, b.title, b.author, b.price, b.stock,
               b.publisher, b.discount, b.url_img, b.is_active,
               b.created_at, c.name AS category_name
        FROM Book b
        LEFT JOIN Category c ON b.category_id = c.category_id
        WHERE b.title LIKE ?
          AND b.category_id = ?
        ORDER BY b.book_id
    """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setInt(2, categoryId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BookAdmin b = new BookAdmin();
                b.setBookId(rs.getInt("book_id"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setPrice(rs.getDouble("price"));
                b.setStock(rs.getInt("stock"));
                b.setPublisher(rs.getString("publisher"));
                b.setDiscount(rs.getInt("discount"));
                b.setUrlImg(rs.getString("url_img"));
                b.setIsActive(rs.getBoolean("is_active"));
                b.setCreatedAt(rs.getString("created_at"));
                b.setCategoryName(rs.getString("category_name"));
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Book> getLowStockBooks(int threshold) {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM Book WHERE is_active = 1 AND stock <= ? ORDER BY stock ASC";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, threshold);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Book b = new Book();
                    b.setBookId(rs.getInt("book_id"));
                    b.setTitle(rs.getString("title"));
                    b.setAuthor(rs.getString("author"));
                    b.setPrice(rs.getDouble("price"));
                    b.setStock(rs.getInt("stock"));
                    b.setUrlImg(getImgURLbyBookId(rs.getInt("book_id")));
                    list.add(b);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int insertBookReturnId(Book b) throws Exception {
        String sql = """
            INSERT INTO Book(title, author, price, stock, category_id, is_active, publisher)
            VALUES(?, ?, ?, ?, ?, 1, ?)
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setDouble(3, b.getPrice());
            ps.setInt(4, b.getStock());
            if (b.getCategory() == null) {
                ps.setNull(5, java.sql.Types.INTEGER);
            } else {
                ps.setInt(5, b.getCategory().getCategoryId());
            }
            ps.setString(6, b.getPublisher());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new Exception("Cannot insert book (no generated key).");
    }

    public int countBooks(String keyword, Integer categoryId) {
        int total = 0;

        String sql = """
        SELECT COUNT(*)
        FROM Book
        WHERE (? IS NULL OR title LIKE ?)
        AND (? IS NULL OR category_id = ?)
    """;

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);

            if (keyword == null || keyword.isEmpty()) {
                ps.setNull(1, java.sql.Types.VARCHAR);
                ps.setNull(2, java.sql.Types.VARCHAR);
            } else {
                ps.setString(1, keyword);
                ps.setString(2, "%" + keyword + "%");
            }

            if (categoryId == null) {
                ps.setNull(3, java.sql.Types.INTEGER);
                ps.setNull(4, java.sql.Types.INTEGER);
            } else {
                ps.setInt(3, categoryId);
                ps.setInt(4, categoryId);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    public List<BookAdmin> getBooksByPage(int page, int pageSize,
            String keyword, Integer categoryId) {

        List<BookAdmin> list = new ArrayList<>();
        int offset = (page - 1) * pageSize;

        String sql = """
        SELECT b.book_id, b.title, b.author, b.price, b.stock,
               b.publisher, b.discount, b.url_img, b.is_active,
               b.created_at, c.name AS category_name
        FROM Book b
        LEFT JOIN Category c ON b.category_id = c.category_id
        WHERE (? IS NULL OR b.title LIKE ?)
        AND (? IS NULL OR b.category_id = ?)
        ORDER BY b.book_id
        OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
    """;

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);

            if (keyword == null || keyword.isEmpty()) {
                ps.setNull(1, java.sql.Types.VARCHAR);
                ps.setNull(2, java.sql.Types.VARCHAR);
            } else {
                ps.setString(1, keyword);
                ps.setString(2, "%" + keyword + "%");
            }

            if (categoryId == null) {
                ps.setNull(3, java.sql.Types.INTEGER);
                ps.setNull(4, java.sql.Types.INTEGER);
            } else {
                ps.setInt(3, categoryId);
                ps.setInt(4, categoryId);
            }

            ps.setInt(5, offset);
            ps.setInt(6, pageSize);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BookAdmin b = new BookAdmin();
                b.setBookId(rs.getInt("book_id"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setPrice(rs.getDouble("price"));
                b.setStock(rs.getInt("stock"));
                b.setCategoryName(rs.getString("category_name"));
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void publishBook(int bookId, double price) {

        String sql = """
        UPDATE Book
        SET price = ?, is_published = 1
        WHERE book_id = ?
    """;

        try (
                PreparedStatement ps = getConnection().prepareStatement(sql);) {

            ps.setDouble(1, price);
            ps.setInt(2, bookId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Book> getBooksInStockNotPublished() {

        List<Book> list = new ArrayList<>();

        String sql = """
        SELECT *
        FROM Book
        WHERE stock > 0
        AND is_published = 0
        AND is_active = 1
    """;

        try (
                PreparedStatement ps = getConnection().prepareStatement(sql); ResultSet rs = ps.executeQuery();) {

            while (rs.next()) {

                Book b = new Book();

                b.setBookId(rs.getInt("book_id"));
                b.setTitle(rs.getString("title"));
                b.setStock(rs.getInt("stock"));

                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int getSoldQuantity(int bookId) {
        String sql = "SELECT ISNULL(SUM(od.quantity),0) AS sold "
                + "FROM OrderDetail od "
                + "JOIN Orders o ON od.order_id = o.order_id "
                + "WHERE o.status = 'DELIVERED' AND od.book_id = ?";

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("sold");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }

    public static void main(String[] args) {
        BookDAO dao = new BookDAO();
        List<Book> list = dao.getAllBook();
        for (Book b : list) {
            System.out.println(b);
        }
    }
}
