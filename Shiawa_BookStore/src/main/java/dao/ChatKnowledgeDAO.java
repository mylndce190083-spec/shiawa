package dao;

import db.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.ChatKnowledge;

public class ChatKnowledgeDAO extends DBContext {

    private ChatKnowledge mapKnowledge(ResultSet rs) throws Exception {
        ChatKnowledge k = new ChatKnowledge();
        k.setId(rs.getInt("id"));
        k.setKeyword(rs.getString("keyword"));
        k.setAnswer(rs.getString("answer"));
        k.setActive(rs.getBoolean("is_active"));
        return k;
    }

    public List<ChatKnowledge> getAll() {
        List<ChatKnowledge> list = new ArrayList<>();
        String sql = "SELECT id, keyword, answer, is_active FROM dbo.Chat_Knowledge ORDER BY id DESC";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapKnowledge(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ChatKnowledge getById(int id) {
        String sql = "SELECT id, keyword, answer, is_active FROM dbo.Chat_Knowledge WHERE id = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapKnowledge(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(ChatKnowledge k) {
        String sql = "INSERT INTO dbo.Chat_Knowledge(keyword, answer, is_active) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, k.getKeyword());
            ps.setString(2, k.getAnswer());
            ps.setBoolean(3, k.isActive());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(ChatKnowledge k) {
        String sql = "UPDATE dbo.Chat_Knowledge SET keyword = ?, answer = ?, is_active = ? WHERE id = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, k.getKeyword());
            ps.setString(2, k.getAnswer());
            ps.setBoolean(3, k.isActive());
            ps.setInt(4, k.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toggleActive(int id) {
        String sql = "UPDATE dbo.Chat_Knowledge SET is_active = CASE WHEN is_active = 1 THEN 0 ELSE 1 END WHERE id = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
