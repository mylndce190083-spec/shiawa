/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Feedback;

/**
 *
 * @author admin
 */
public class FeedbackDAO extends db.DBContext{
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
}
