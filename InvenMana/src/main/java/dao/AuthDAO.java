package dao;

import db.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.StaffSession;

public class AuthDAO extends DBContext {

    /**
     * Login by Staff credentials.
     *
     * IMPORTANT: this assumes Staff table has column [password].
     * If your DB uses a different column name, update the SQL.
     */
    public StaffSession loginStaff(String username, String password) {
        String sql = """
            SELECT s.staff_id, s.username, r.name AS role
            FROM Staff s
            JOIN Role r ON s.role_id = r.role_id
            WHERE s.username = ? AND s.password = ?
        """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new StaffSession(
                            rs.getInt("staff_id"),
                            rs.getString("username"),
                            rs.getString("role")
                    );
                }
            }
        } catch (Exception e) {
            // If login fails due to schema mismatch, print stack for easier debugging.
            e.printStackTrace();
        }
        return null;
    }
}




