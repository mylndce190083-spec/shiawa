/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Book;
import model.CartItem;

/**
 *
 * @author MY
 */
public class CartItemDAO extends DBContext {

  
    public CartItem findItem(int customerId, int bookId) {
        String sql = """
            SELECT c.cart_item_id, c.quantity, c.price, c.created_at,
                   b.book_id, b.title,
                   bi.image_url
            FROM CartItem c
            JOIN Book b ON c.book_id = b.book_id
            LEFT JOIN BookImages bi 
                ON b.book_id = bi.book_id AND bi.is_primary = 1
            WHERE c.customer_id = ? AND c.book_id = ?
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.setInt(2, bookId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                CartItem item = new CartItem(
                        rs.getInt("cart_item_id"),
                        customerId,
                        bookId,
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );

                Book book = new Book();
                book.setBookId(bookId);
                book.setTitle(rs.getString("title"));
                book.setUrlImg(rs.getString("image_url"));

                item.setBook(book);
                return item;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    // insert mới
    public void insert(CartItem item) {
        String sql = """
            INSERT INTO CartItem(customer_id, book_id, quantity, price, created_at)
            VALUES (?,?,?,?,?)
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, item.getCustomerId());
            ps.setInt(2, item.getBookId());
            ps.setInt(3, item.getQuantity());
            ps.setDouble(4, item.getPrice());
            ps.setTimestamp(5, Timestamp.valueOf(item.getCreateAt()));

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // update số lượng
    public void updateQuantity(int customerId, int bookId, int quantity) {
        String sql = """
            UPDATE CartItem
            SET quantity = ?
            WHERE customer_id = ? AND book_id = ?
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, customerId);
            ps.setInt(3, bookId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<CartItem> getCartByCustomerId(int customerId) {
        List<CartItem> list = new ArrayList<>();

        String sql = """
    SELECT c.cart_item_id,
           c.customer_id,
           c.book_id,
           c.quantity,
           b.price,
           c.created_at,
           b.title,
           bi.image_url,
           b.stock
    FROM CartItem c
    JOIN Book b ON c.book_id = b.book_id
    LEFT JOIN BookImages bi
           ON b.book_id = bi.book_id AND bi.is_primary = 1
    WHERE c.customer_id = ?
""";

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int cartItemId = rs.getInt("cart_item_id");
                int bookId = rs.getInt("book_id");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
                Timestamp t = rs.getTimestamp("created_at");
                LocalDateTime createAt = (t != null) ? t.toLocalDateTime() : null;

                // tạo Book (theo style quen tay)
                Book book = new Book();
                book.setBookId(bookId);
                book.setTitle(rs.getString("title"));
                book.setUrlImg(rs.getString("image_url"));
                book.setStock(stock);
                // tạo CartItem bằng constructor
                CartItem item = new CartItem(
                        cartItemId,
                        customerId,
                        bookId,
                        quantity,
                        price,
                        createAt
                );

                item.setBook(book);
                list.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    // xóa
    public void delete(int customerId, int bookId) {
        String sql = """
            DELETE FROM CartItem
            WHERE customer_id = ? AND book_id = ?
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.setInt(2, bookId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CartItemDAO dao = new CartItemDAO();

        int customerId = 1;

        List<CartItem> cart = dao.getCartByCustomerId(customerId);

        if (cart.isEmpty()) {
            System.out.println("❌ Giỏ hàng trống");
        } else {
            System.out.println("✅ Số sản phẩm trong giỏ: " + cart.size());

            for (CartItem item : cart) {
                System.out.println("---------------");
                System.out.println("CartItem ID : " + item.getCartItemId());
                System.out.println("Book ID     : " + item.getBookId());
                System.out.println("Book title  : " + item.getBook().getTitle());
                System.out.println("Price       : " + item.getPrice());
                System.out.println("Quantity    : " + item.getQuantity());
                System.out.println("Image       : " + item.getBook().getUrlImg());
            }
        }
    }

    public void clearCart(int customerId) {

        String sql = "DELETE FROM CartItem WHERE customer_id = ?";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
