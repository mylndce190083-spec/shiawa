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

    public void insertFeedback(Feedback fb) {
        try {
            String sql = "INSERT INTO Feedback (user_id, book_id, rating, content, created_at) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setInt(1, fb.getUserId());
            st.setInt(2, fb.getBookId());
            st.setInt(3, fb.getRating());
            st.setString(4, fb.getContent());
            st.setObject(5, LocalDateTime.now());
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean hasFeedback(int userId, int bookId) {
        try {
            String sql = "SELECT id FROM Feedback WHERE user_id = ? AND book_id = ?";
            PreparedStatement st = getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            st.setInt(1, userId);
            st.setInt(2, bookId);
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<Feedback> getFeedbacksByBookId(int bookId) {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT f.*, a.username FROM Feedback f "
                + "JOIN Account a ON f.user_id = a.id "
                + "WHERE f.book_id = ? ORDER BY f.created_at DESC";
        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setInt(1, bookId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Feedback fb = new Feedback();
                fb.setId(rs.getInt("id"));
                fb.setUserId(rs.getInt("user_id"));
                fb.setBookId(rs.getInt("book_id"));
                fb.setRating(rs.getInt("rating"));
                fb.setContent(rs.getString("content"));
                // Nếu model Feedback có trường username, hãy set vào để hiển thị tên người dùng
                // fb.setUserName(rs.getString("username")); 
                list.add(fb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
