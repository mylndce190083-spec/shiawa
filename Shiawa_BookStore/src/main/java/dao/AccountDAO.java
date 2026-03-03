/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBContext;

import java.security.MessageDigest;
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
        SELECT customer_id AS id, username, 'Customer' AS role, email, status
        FROM Customer
        UNION ALL
        SELECT s.staff_id AS id, s.username, r.name AS role, s.email, s.status
        FROM Staff s
        JOIN Role r ON s.role_id = r.role_id
        ORDER BY username
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Account(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("role"),
                        rs.getString("email"),
                        rs.getString("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public Account login(String username, String pass) {
        Account u = new Account();
        //u.id = -1
        String sqlCus = "select * from Customer where (email = ? OR phone = ?) and password = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sqlCus)) {
            ps.setString(1, username);
            ps.setString(2, username);
            ps.setString(3, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u.setId(rs.getInt("customer_id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setStatus(rs.getString("status"));
            }
        } catch (Exception e) {
        }

        if (u.getUsername() == null) {
            String sqlStaff = "select * from Staff s\n"
                    + "  join Role r on s.role_id = r.role_id\n"
                    + "  where (email = ? OR phone = ?) and password = ?";
            try (PreparedStatement ps = getConnection().prepareStatement(sqlStaff)) {
                ps.setString(1, username);
                ps.setString(2, username);
                ps.setString(3, pass);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    u.setId(rs.getInt("Staff_id"));
                    u.setUsername(rs.getString("username"));
                    u.setRole(rs.getString("name"));//name là role
                    u.setEmail(rs.getString("email"));
                    u.setStatus(rs.getString("status"));
                }
            } catch (Exception e) {

            }
        }

        return u;
    }
    
    public String hashMD5(String pass){
        String hashPass = "";
        try {
            MessageDigest ms = MessageDigest.getInstance("MD5");
            byte[] bytePass = ms.digest(pass.getBytes());
            //[0x1a, 0x09, 0x1b, 0xa, 0x77,...]
            for (byte bytePas : bytePass) {
                //0x1a, 0x09, 0x1b, 0xa
                String ch = String.format("%02x", bytePas);
                //1a, 09, 1b, 0a
                hashPass += ch;
            }
        } catch (Exception e) {
        }
        return hashPass;
    }
    
public static void main(String[] args) {
        AccountDAO dao = new AccountDAO();
        //System.out.println(dao.hashMD5("123456"));
        System.out.println(dao.login("admin@gmail.com", "admin123"));
    }

}
