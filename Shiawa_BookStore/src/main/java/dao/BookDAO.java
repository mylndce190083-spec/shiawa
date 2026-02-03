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
 * @author BA LIEM
 */
public class BookDAO extends DBContext {

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

}
