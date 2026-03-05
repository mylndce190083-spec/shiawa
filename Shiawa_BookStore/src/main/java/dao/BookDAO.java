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
        String sql = "select * from Book";
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
                String imgUrl = rs.getString("url_img");
                boolean isActive = rs.getBoolean("is_active");
                LocalDateTime createAte = rs.getTimestamp("created_at").toLocalDateTime();
                //tao doi tuong product
                Book b = new Book(id, title, author, price, description, cate, stock, publisher, discount, imgUrl, isActive, createAte);
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

//    public Book getBookById(int bookId) {
//        String sql = """
//        SELECT *
//        FROM Book
//        WHERE book_id = ?
//    """;
//
//        try {
//            PreparedStatement ps = getConnection().prepareStatement(sql);
//            ps.setInt(1, bookId);
//
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                CategoryDAO cdao = new CategoryDAO();
//                Category cate = cdao.getCategoryById(rs.getInt("category_id"));
//
//                return new Book(
//                        rs.getInt("book_id"),
//                        rs.getString("title"),
//                        rs.getString("author"),
//                        rs.getDouble("price"),
//                        rs.getString("description"),
//                        cate,
//                        rs.getInt("stock"),
//                        rs.getString("publisher"),
//                        rs.getInt("discount"),
//                        rs.getString("url_img"),
//                        rs.getBoolean("is_active"),
//                        rs.getTimestamp("created_at").toLocalDateTime()
//                );
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    public Book getBookById(int bookId) {
        // Sử dụng LEFT JOIN để lấy cột 'name' từ bảng Category và đặt tên thay thế là 'category_name'
        String sql = """
    SELECT b.*, c.name AS category_name
    FROM Book b
    LEFT JOIN Category c ON b.category_id = c.category_id
    WHERE b.book_id = ?
                """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Bước quan trọng: Tạo đối tượng Category và gán tên vào
                    Category cate = new Category();
                    cate.setCategoryId(rs.getInt("category_id"));
                    cate.setCategoryName(rs.getString("category_name"));

                    // Trả về đối tượng Book có chứa đối tượng Category bên trong
                    return new Book(
                            rs.getInt("book_id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getDouble("price"),
                            rs.getString("description"),
                            cate, // <--- Đối tượng cate này sẽ giúp JSP hiển thị được tên
                            rs.getInt("stock"),
                            rs.getString("publisher"),
                            rs.getInt("discount"),
                            rs.getString("url_img"),
                            rs.getBoolean("is_active"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    );
                }
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
                Book b = new Book();
                b.setBookId(rs.getInt("book_id"));
                b.setTitle(rs.getString("title"));
                b.setPrice(rs.getDouble("price"));
                b.setUrlImg(rs.getString("url_img"));

                Category c = new Category();
                c.setCategoryName(rs.getString("category_name"));
                b.setCategory(c);

                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Book> searchBookByName(String keyword) {
        List<Book> list = new ArrayList<>();

        String sql = "SELECT * FROM Book WHERE title LIKE ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book b = new Book();
                b.setBookId(rs.getInt("book_id"));
                b.setTitle(rs.getString("title"));
                b.setPrice(rs.getDouble("price"));
                b.setUrlImg(rs.getString("url_img"));
                list.add(b);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public List<Book> getBooksByCategoryId(int cateId) {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM Book WHERE category_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cateId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book b = new Book();
                b.setBookId(rs.getInt("book_id"));
                b.setTitle(rs.getString("title"));
                b.setPrice(rs.getDouble("price"));
                b.setUrlImg(rs.getString("url_img"));
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
}
