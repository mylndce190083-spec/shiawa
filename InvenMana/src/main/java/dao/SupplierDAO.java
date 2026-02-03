package dao;

import db.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Supplier;

public class SupplierDAO extends DBContext {

    public List<Supplier> getAllActive() {
        List<Supplier> list = new ArrayList<>();
        String sql = """
            SELECT supplier_id, name, phone, email, address, is_active
            FROM Supplier
            WHERE is_active = 1
            ORDER BY name
        """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Supplier s = new Supplier();
                s.setSupplierId(rs.getInt("supplier_id"));
                s.setName(rs.getString("name"));
                s.setPhone(rs.getString("phone"));
                s.setEmail(rs.getString("email"));
                s.setAddress(rs.getString("address"));
                s.setIsActive(rs.getBoolean("is_active"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(Supplier s) {
        String sql = """
            INSERT INTO Supplier(name, phone, email, address, is_active)
            VALUES(?, ?, ?, ?, 1)
        """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getPhone());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getAddress());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




