/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

<<<<<<< HEAD
import db.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Book;
=======
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Book;
import model.Category;
import utils.DBContext;
>>>>>>> 6eec6e2c6e3608949a045ca087e1b084a6b72c92

/**
 *
 * @author BA LIEM
 */
public class BookDAO extends DBContext {

<<<<<<< HEAD
    public List<Book> getAllBooks() {
        List<Book> list = new ArrayList<>();

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
=======
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
>>>>>>> 6eec6e2c6e3608949a045ca087e1b084a6b72c92

        try (PreparedStatement ps = getConnection().prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
<<<<<<< HEAD
                Book b = new Book();
=======
                BookAdmin b = new BookAdmin();
>>>>>>> 6eec6e2c6e3608949a045ca087e1b084a6b72c92
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

<<<<<<< HEAD
    public void insertBook(Book b) {
        String sql = """
        INSERT INTO Book
        (title, author, price, stock, category_id, is_active)
        VALUES (?, ?, ?, ?, ?, 1)
    """;
=======
    public void insertBook(BookAdmin b) {
        String sql = """
                    INSERT INTO Book
                    (title, author, price, stock, category_id, is_active)
                    VALUES (?, ?, ?, ?, ?, 1)
                """;
>>>>>>> 6eec6e2c6e3608949a045ca087e1b084a6b72c92

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

<<<<<<< HEAD
=======
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
                // tao cate
                int cateId = rs.getInt("category_id");
                Category cate = dao.getCategoryById(cateId);
                if (cate == null) {
                    cate = new Category(cateId, "Unknown");
                }

                int stock = rs.getInt("stock");
                String publisher = rs.getString("publisher");
                int discount = rs.getInt("discount");
                String imgUrl = rs.getString("url_img");
                boolean isActive = rs.getBoolean("is_active");

                // xu ly NULL value cho created_at
                LocalDateTime createAte = null;
                if (rs.getTimestamp("created_at") != null) {
                    createAte = rs.getTimestamp("created_at").toLocalDateTime();
                }
                // tao doi tuong product
                Book b = new Book(id, title, author, price, description, cate, stock, publisher, discount, imgUrl,
                        isActive, createAte);
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Book getBookById(int id) {
        Book b = new Book();
        try {
            String sql = "SELECT b.*, c.name AS category_name "
                    + "        FROM Book b "
                    + "        LEFT JOIN Category c ON b.category_id = c.category_id "
                    + "        WHERE b.book_id = ?";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                b.setBookId(rs.getInt("book_id"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setPrice(rs.getDouble("price"));
                b.setStock(rs.getInt("stock"));
                b.setPublisher(rs.getString("publisher"));
                b.setDiscount(rs.getInt("discount"));
                b.setUrlImg(rs.getString("url_img"));
                b.setIsActive(rs.getBoolean("is_active"));
                b.setDescription(rs.getString("description"));
                b.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                Category cate = new Category();
                cate.setCateName(rs.getString("category_name"));
                b.setCategory(cate);
                return b;

            }
        } catch (SQLException ex) {
            return null;
        }
        return b;
    }

    public List<Book> getSimilarBook(int categoryId) {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT TOP 6 b.*, c.name AS category_name\n"
                + "FROM Book b\n"
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
                c.setCateName(rs.getString("category_name"));
                b.setCategory(c);

                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
>>>>>>> 6eec6e2c6e3608949a045ca087e1b084a6b72c92
}
