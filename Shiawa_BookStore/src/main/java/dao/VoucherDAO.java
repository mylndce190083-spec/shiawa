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
import model.Voucher;

/**
 *
 * @author BA LIEM
 */
public class VoucherDAO extends DBContext{
    // Lấy tất cả voucher
    public List<Voucher> getAllVoucher() {

        List<Voucher> list = new ArrayList<>();

        String sql = "SELECT voucher_id, name, discount, quantity, createdAt, endedAt FROM Voucher";

        try {

            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Voucher v = new Voucher();

                v.setVoucher_id(rs.getInt("voucher_id"));
                v.setName(rs.getString("name"));
                v.setDiscount(rs.getDouble("discount"));
                v.setQuantity(rs.getInt("quantity"));
                v.setCreatedAt(rs.getDate("createdAt"));
                v.setEndedAt(rs.getDate("endedAt"));

                list.add(v);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Lấy voucher theo ID
    public Voucher getVoucherById(int id) {

        String sql = "SELECT * FROM Voucher WHERE voucher_id = ?";

        try {

            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Voucher v = new Voucher();

                v.setVoucher_id(rs.getInt("voucher_id"));
                v.setName(rs.getString("name"));
                v.setDiscount(rs.getDouble("discount"));
                v.setQuantity(rs.getInt("quantity"));
                v.setCreatedAt(rs.getDate("createdAt"));
                v.setEndedAt(rs.getDate("endedAt"));

                return v;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Thêm voucher
    public void insertVoucher(Voucher v) {

        String sql = "INSERT INTO Voucher(name, discount, quantity, createdAt, endedAt) VALUES(?,?,?,?,?)";

        try {

            PreparedStatement ps = getConnection().prepareStatement(sql);

            ps.setString(1, v.getName());
            ps.setDouble(2, v.getDiscount());
            ps.setInt(3, v.getQuantity());
            ps.setDate(4, new java.sql.Date(v.getCreatedAt().getTime()));
            ps.setDate(5, new java.sql.Date(v.getEndedAt().getTime()));

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update voucher
    public void updateVoucher(Voucher v) {

        String sql = "UPDATE Voucher SET name=?, discount=?, quantity=?, createdAt=?, endedAt=? WHERE voucher_id=?";

        try {

            PreparedStatement ps = getConnection().prepareStatement(sql);

            ps.setString(1, v.getName());
            ps.setDouble(2, v.getDiscount());
            ps.setInt(3, v.getQuantity());
            ps.setDate(4, new java.sql.Date(v.getCreatedAt().getTime()));
            ps.setDate(5, new java.sql.Date(v.getEndedAt().getTime()));
            ps.setInt(6, v.getVoucher_id());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xóa voucher
    public void deleteVoucher(int id) {

        String sql = "DELETE FROM Voucher WHERE voucher_id=?";

        try {

            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
