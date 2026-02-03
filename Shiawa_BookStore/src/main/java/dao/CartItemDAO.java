/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Book;
import model.CartItem;
import utils.DBContext;

/**
 *
 * @author MY
 */
public class CartItemDAO extends DBContext {

    // tìm cart item theo customer + book
    public CartItem findItem(int customer_id, int book_id) {
        String sql = "SELECT * FROM CartItem WHERE customer_id=? AND book_id=?";
        try (
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customer_id);
            ps.setInt(2, book_id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                CartItem item = new CartItem();
                item.setQuantity(rs.getInt("quantity"));
                return item;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(CartItem item) {

        String sql = "INSERT INTO CartItem(customer_id, book_id, quantity, price) VALUES (?,?,?,?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("customer_id");
                int book_id = rs.getInt("book_id");
                int quantity = rs.getInt("quantity");
                Double price = rs.getDouble("price");

                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(CartItem item) {
        String sql = "UPDATE CartItem SET quantity=? WHERE customer_id=? AND book_id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("customer_id");
                int book_id = rs.getInt("book_id");
                int quantity = rs.getInt("quantity");
            }
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<CartItem> getCartByCustomerId(int customerId) {
        List<CartItem> list = new ArrayList<>();

        String sql = """
        SELECT c.cart_item_id, c.customer_id, c.book_id,
               c.quantity, c.price, c.create_at,
               b.title, b.image_url
        FROM CartItem c
        JOIN Book b ON c.book_id = b.book_id
        WHERE c.customer_id = ?
    """;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem();
                item.setCart_item_id(rs.getInt("cart_item_id"));
                item.setCustomer_id(rs.getInt("customer_id"));
                item.setBook_id(rs.getInt("book_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getDouble("price"));
                item.setCreate_at(rs.getTimestamp("create_at").toLocalDateTime());

                // 🔥 SET BOOK
                Book book = new Book();
                book.setBook_id(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setImgUrl(rs.getString("image_url"));

                item.setBook(book);

                list.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
