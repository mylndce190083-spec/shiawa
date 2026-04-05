
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Feedback;

/**
 *
 * @author admin
 */
public class FeedbackDAO extends db.DBContext {

    public void insertFeedback(Feedback fb, int order_detail_id) {
        try {
            String sql = "INSERT INTO Feedback (customer_id, book_id, rating, comment, created_at, order_detail_id, status) VALUES (?, ?, ?, ?, ?, ?, 1)";
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setInt(1, fb.getUserId());
            st.setInt(2, fb.getBookId());
            st.setInt(3, fb.getRating());
            st.setString(4, fb.getContent());
            st.setObject(5, LocalDateTime.now());
            st.setInt(6, order_detail_id);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean hasFeedback(int userId, int bookId) {
        String sql = "SELECT feedback_id FROM Feedback WHERE customer_id = ? AND book_id = ?";
        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setInt(1, userId);
            st.setInt(2, bookId);
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<Feedback> getFeedbacksByBookId(int bookId) {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT f.*, c.username FROM Feedback f "
                + "JOIN Customer c ON f.customer_id = c.customer_id "
                + "WHERE f.book_id = ? AND f.status = 1 "
                + "ORDER BY f.created_at DESC";
        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setInt(1, bookId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Feedback fb = new Feedback();
                fb.setId(rs.getInt("feedback_id"));
                fb.setUserId(rs.getInt("customer_id"));
                fb.setBookId(rs.getInt("book_id"));
                fb.setRating(rs.getInt("rating"));
                fb.setContent(rs.getString("comment"));
                fb.setUsername(rs.getString("username"));
                fb.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                fb.setOrderdetailId(rs.getInt("order_detail_id"));
                list.add(fb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void updateFeedback(Feedback fb, int order_detail_id) {
        try {
            String sql = "UPDATE Feedback SET rating = ?, comment = ? \n"
                    + "\n"
                    + "    WHERE order_detail_id = ?";
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setInt(1, fb.getRating());
            st.setString(2, fb.getContent());
            st.setInt(3, order_detail_id);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<Feedback> getAllFeedback() {
        List<Feedback> list = new ArrayList<>();

        String sql = """
        SELECT 
                         f.feedback_id, 
                         f.customer_id,
                         u.username,   -- thêm username ở đây
                         b.title, 
                         f.comment, 
                         f.rating, 
                         f.order_detail_id,
                         f.status
                     FROM Feedback f 
                     JOIN Book b ON f.book_id = b.book_id
                     JOIN Customer u ON f.customer_id=u.customer_id
    """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Feedback f = new Feedback();

                f.setId(rs.getInt("feedback_id"));
                f.setOrderdetailId(rs.getInt("order_detail_id"));
                f.setUserId(rs.getInt("customer_id"));
                f.setBookTitle(rs.getString("title"));
                f.setContent(rs.getString("comment"));
                f.setRating(rs.getInt("rating"));
                f.setUsername(rs.getString("username"));
                f.setStatus(rs.getInt("status"));
                list.add(f);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean hideFeedback(int id, int status) {
        String sql = "UPDATE Feedback SET status = ? WHERE feedback_id = ?";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, status); 
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

   
}
