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
import model.OrderItem;

/**
 *
 * @author BA LIEM
 */

public class OrderDetailDAO extends DBContext {


    public void insertOrderDetail(Connection con,
            int orderId,
            int bookId,
            int quantity,
            double price) throws Exception {

        String sql = """
        INSERT INTO OrderDetail
        (order_id, book_id, quantity, price)
        VALUES (?, ?, ?, ?)
    """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ps.setInt(2, bookId);
            ps.setInt(3, quantity);
            ps.setDouble(4, price);

            ps.executeUpdate();
        }
    }

    public List<OrderItem> getItemsByOrderId(int orderId) {
        List<OrderItem> list = new ArrayList<>();
        BookDAO dao = new BookDAO();

        String sql = """
        SELECT 
            od.quantity,
            od.price,
            b.title,
            b.url_img,
            b.book_id,
            od.isRated,
            od.order_detail_id
        FROM OrderDetail od
        JOIN Book b ON od.book_id = b.book_id
        WHERE od.order_id = ?
    """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setTitle(rs.getString("title"));
                item.setUrl_img(rs.getString("url_img"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getDouble("price"));
                item.setBook(dao.getBookById(rs.getInt("book_id")));
                item.setIsRated(rs.getString("isRated"));
                item.setOrderDetailId(rs.getInt("order_detail_id"));

                list.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
public OrderItem getOneItemByOrderDetailId(int orderDetailId) {
        BookDAO dao = new BookDAO();
        OrderItem item = new OrderItem();
        String sql = """
        select * from OrderDetail where order_detail_id = ?
    """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, orderDetailId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getDouble("price"));
                item.setBook(dao.getBookById(rs.getInt("book_id")));
                item.setOrderDetailId(rs.getInt("order_detail_id"));
                item.setOrderId(rs.getInt("order_id"));
                item.setIsRated(rs.getString("isRated"));
                System.out.println(rs.getInt("book_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    public void changeIsRatedById(int orderDetailId) {
        String sql = """
        update OrderDetail set isRated = 'rated' where order_detail_id = ?
    """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, orderDetailId);
            ps.setInt(1, orderDetailId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        OrderDetailDAO dao = new OrderDetailDAO();
        dao.getItemsByOrderId(52);
        dao.getOneItemByOrderDetailId(85);
        dao.changeIsRatedById(84);
    }
}
