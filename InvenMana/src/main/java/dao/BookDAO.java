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
import model.Book;

/**
 *
 * @author Tiến Thành
 */
public class BookDAO extends DBContext {

    public Book getBookById(int id) {
        String sql = """
            SELECT b.book_id, b.title, b.author, b.price, b.stock,
                   b.publisher, b.discount, b.url_img, b.is_active, b.created_at,
                   b.category_id, c.name AS category_name
            FROM Book b
            LEFT JOIN Category c ON b.category_id = c.category_id
            WHERE b.book_id = ?
        """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Book b = new Book();
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
                    b.setCategoryId(rs.getInt("category_id"));
                    b.setCategoryName(rs.getString("category_name"));
                    return b;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateBook(Book b) {
        String sql = """
            UPDATE Book
            SET title = ?, author = ?, price = ?, stock = ?, category_id = ?
            WHERE book_id = ?
        """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setDouble(3, b.getPrice());
            ps.setInt(4, b.getStock());
            ps.setInt(5, b.getCategoryId());
            ps.setInt(6, b.getBookId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(int id) {
        // soft delete if column exists; fallback to hard delete
        try (PreparedStatement ps = getConnection().prepareStatement("UPDATE Book SET is_active = 0 WHERE book_id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return;
        } catch (Exception ignored) {
        }
        hardDeleteBook(id);
    }

    public void hardDeleteBook(int id) {
        try (PreparedStatement ps = getConnection().prepareStatement("DELETE FROM Book WHERE book_id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBookStatus(int id, boolean isActive) {
        String sql = "UPDATE Book SET is_active = ? WHERE book_id = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setBoolean(1, isActive);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Book> getBooksForInventory(Integer minStock, Integer maxStock, String sort) {
        List<Book> list = new ArrayList<>();
        String orderBy = "b.stock ASC";
        if ("stock_desc".equalsIgnoreCase(sort)) orderBy = "b.stock DESC";
        if ("id".equalsIgnoreCase(sort)) orderBy = "b.book_id";

        String sql = """
            SELECT b.book_id, b.title, b.author, b.price, b.stock,
                   b.publisher, b.discount, b.url_img,
                   c.name AS category_name
            FROM Book b
            LEFT JOIN Category c ON b.category_id = c.category_id
            WHERE (? IS NULL OR b.stock >= ?)
              AND (? IS NULL OR b.stock <= ?)
            ORDER BY """ + orderBy;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            if (minStock == null) {
                ps.setNull(1, java.sql.Types.INTEGER);
                ps.setNull(2, java.sql.Types.INTEGER);
            } else {
                ps.setInt(1, minStock);
                ps.setInt(2, minStock);
            }
            if (maxStock == null) {
                ps.setNull(3, java.sql.Types.INTEGER);
                ps.setNull(4, java.sql.Types.INTEGER);
            } else {
                ps.setInt(3, maxStock);
                ps.setInt(4, maxStock);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Book b = new Book();
                    b.setBookId(rs.getInt("book_id"));
                    b.setTitle(rs.getString("title"));
                    b.setAuthor(rs.getString("author"));
                    b.setPrice(rs.getDouble("price"));
                    b.setStock(rs.getInt("stock"));
                     b.setPublisher(rs.getString("publisher"));
                     b.setDiscount(rs.getInt("discount"));
                     b.setUrlImg(rs.getString("url_img"));
                    b.setCategoryName(rs.getString("category_name"));
                    list.add(b);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

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

        try (PreparedStatement ps = getConnection().prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Book b = new Book();
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
        String sql = """
            SELECT b.book_id, b.title, b.author, b.price, b.stock, c.name AS category_name
            FROM Book b
            LEFT JOIN Category c ON b.category_id = c.category_id
            WHERE b.stock <= ?
            ORDER BY b.stock ASC, b.title
        """;
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
                    b.setCategoryName(rs.getString("category_name"));
                    list.add(b);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insertBook(Book b) {
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

    public int insertBookReturnId(Book b) throws Exception {
        String sql = """
        INSERT INTO Book
        (title, author, price, stock, category_id, is_active)
        VALUES (?, ?, ?, ?, ?, 1)
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setDouble(3, b.getPrice());
            ps.setInt(4, b.getStock());
            ps.setInt(5, b.getCategoryId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        throw new Exception("Cannot insert Book (no generated key).");
    }

}
