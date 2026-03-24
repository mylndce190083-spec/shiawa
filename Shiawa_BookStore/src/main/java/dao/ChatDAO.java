package dao;

import db.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.ChatMessage;
import model.ChatSession;

public class ChatDAO extends DBContext {


    public List<ChatSession> getAllSessions() {
        List<ChatSession> list = new ArrayList<>();
        String sql = """
            SELECT s.session_id, s.customer_id, s.staff_id,
                   m.content AS last_message, m.sent_at AS last_sent_at
            FROM dbo.Chat_Session s
            OUTER APPLY (
                SELECT TOP 1 content, sent_at
                FROM dbo.Chat_Message
                WHERE session_id = s.session_id
                ORDER BY sent_at DESC, message_id DESC
            ) m
            ORDER BY m.sent_at DESC, s.session_id DESC
        """;
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChatSession s = new ChatSession();
                s.setSessionId(rs.getInt("session_id"));
                int customerId = rs.getInt("customer_id");
                s.setCustomerId(rs.wasNull() ? null : customerId);
                int staffId = rs.getInt("staff_id");
                s.setStaffId(rs.wasNull() ? null : staffId);
                s.setLastMessage(rs.getString("last_message"));
                s.setLastSentAt(rs.getTimestamp("last_sent_at"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getOrCreateSession(Integer customerId) {
        if (customerId != null) {
            String findSql = "SELECT TOP 1 session_id FROM dbo.Chat_Session WHERE customer_id = ? ORDER BY session_id DESC";
            try {
                PreparedStatement ps = getConnection().prepareStatement(findSql);
                ps.setInt(1, customerId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String insertSql = "INSERT INTO dbo.Chat_Session(customer_id, staff_id) VALUES (?, NULL)";
        try {
            PreparedStatement ps = getConnection().prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
            if (customerId == null) {
                ps.setNull(1, java.sql.Types.INTEGER);
            } else {
                ps.setInt(1, customerId);
            }
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean isSessionOwnedByCustomer(int sessionId, Integer customerId) {
        String sql = "SELECT 1 FROM dbo.Chat_Session WHERE session_id = ? AND ((customer_id IS NULL AND ? IS NULL) OR customer_id = ?)";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, sessionId);
            if (customerId == null) {
                ps.setNull(2, java.sql.Types.INTEGER);
                ps.setNull(3, java.sql.Types.INTEGER);
            } else {
                ps.setInt(2, customerId);
                ps.setInt(3, customerId);
            }
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<ChatMessage> getMessagesBySession(int sessionId) {
        List<ChatMessage> list = new ArrayList<>();
        String sql = "SELECT message_id, session_id, sender, content, sent_at FROM dbo.Chat_Message WHERE session_id = ? ORDER BY sent_at ASC, message_id ASC";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, sessionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChatMessage m = new ChatMessage();
                m.setMessageId(rs.getInt("message_id"));
                m.setSessionId(rs.getInt("session_id"));
                m.setSender(rs.getString("sender"));
                m.setContent(rs.getString("content"));
                m.setSentAt(rs.getTimestamp("sent_at"));
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addMessage(int sessionId, String sender, String content) {
        String sql = "INSERT INTO dbo.Chat_Message(session_id, sender, content, sent_at) VALUES (?, ?, ?, GETDATE())";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, sessionId);
            ps.setString(2, sender);
            ps.setString(3, content);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void assignStaffIfEmpty(int sessionId, int staffId) {
        String sql = "UPDATE dbo.Chat_Session SET staff_id = ? WHERE session_id = ? AND staff_id IS NULL";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, staffId);
            ps.setInt(2, sessionId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
