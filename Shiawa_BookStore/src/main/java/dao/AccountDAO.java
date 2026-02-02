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
import model.Account;

/**
 *
 * @author BA LIEM
 */
public class AccountDAO extends DBContext {
    public List<Account> getAllUsers() {
        List<Account> list = new ArrayList<>();

        String sql = """
        SELECT customer_id AS id, username, 'Customer' AS role, email
        FROM Customer

        UNION ALL

        SELECT s.staff_id AS id, s.username, r.name AS role, s.email
        FROM Staff s
        JOIN Role r ON s.role_id = r.role_id

        ORDER BY username
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Account(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("role"),
                    rs.getString("email")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
