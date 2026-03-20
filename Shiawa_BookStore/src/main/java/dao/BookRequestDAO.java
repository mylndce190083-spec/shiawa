/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.BookRequest;

/**
 *
 * @author BA LIEM
 */
public class BookRequestDAO extends DBContext {

    public List<BookRequest> getAllRequests() {
        List<BookRequest> list = new ArrayList<>();

        String sql = "SELECT * FROM BookRequest ORDER BY created_at DESC";

        try (PreparedStatement ps = getConnection().prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                BookRequest r = new BookRequest();

                r.setRequestId(rs.getInt("request_id"));
                r.setTitle(rs.getString("title"));
                r.setAuthor(rs.getString("author"));
                r.setCategoryId(rs.getInt("category_id"));
                r.setRequestedQuantity(rs.getInt("requested_quantity"));
                r.setStatus(rs.getString("status"));

                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void approveRequest(int requestId, int adminId) {

        String sql = """
        UPDATE BookRequest
        SET status='APPROVED',
            approved_by=?,
            approved_at=GETDATE()
        WHERE request_id=?
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {

            ps.setInt(1, adminId);
            ps.setInt(2, requestId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rejectRequest(int requestId) {

        String sql = """
        UPDATE BookRequest
        SET status='REJECTED'
        WHERE request_id=?
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {

            ps.setInt(1, requestId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void acceptRequest(int receiptId) {
        String sql = "UPDATE ImportReceipt SET status='ACCEPTED' WHERE receipt_id=?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, receiptId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
