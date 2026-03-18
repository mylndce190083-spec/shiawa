
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
            String sql = "INSERT INTO Feedback (customer_id, book_id, rating, comment, created_at, order_detail_id) VALUES (?, ?, ?, ?, ?, ?)";
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
            // PHẢI SET TRƯỚC
            st.setInt(1, userId);
            st.setInt(2, bookId);
            // RỒI MỚI EXECUTE
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
                + "WHERE f.book_id = ? ORDER BY f.created_at DESC";
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
                // Nếu model Feedback có trường username, hãy set vào để hiển thị tên người dùng
                fb.setUsername(rs.getString("username"));
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

    public static void main(String[] args) {
        FeedbackDAO dao = new FeedbackDAO();
        List<Feedback> list = dao.getFeedbacksByBookId(6);
        for (Feedback f : list) {
            System.out.println(f);
        }
        Feedback fb = new Feedback();
        fb.setContent("dowrr");
        fb.setRating(2);
        
        
        dao.updateFeedback(fb, 115);

    }
}
