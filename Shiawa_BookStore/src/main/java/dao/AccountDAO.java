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
        u.setId(-1);
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
                // FIX QUAN TRỌNG
                u.setRole("Customer");

                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                    return u;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return u;
    }

    public String hashMD5(String pass) {
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

    public void updateStatus(int id, String role, String status) {

        String sql;

        if ("Customer".equalsIgnoreCase(role)) {
            sql = "UPDATE Customer SET status=? WHERE customer_id=?";
        } else {
            sql = "UPDATE Staff SET status=? WHERE staff_id=?";
        }

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Account getAccountById(int id, String role) {

        String sql;

        if ("Customer".equalsIgnoreCase(role)) {
            sql = """
              SELECT customer_id AS id, username, full_name, gender,
                     email, phone, address, status
              FROM Customer
              WHERE customer_id = ?
              """;
        } else {
            sql = """
              SELECT s.staff_id AS id, s.username, s.full_name, s.gender,
                     s.email, s.phone, s.address, s.status, r.name AS role
              FROM Staff s
              JOIN Role r ON s.role_id = r.role_id
              WHERE s.staff_id = ?
              """;
        }

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Account a = new Account();
                a.setId(rs.getInt("id"));
                a.setUsername(rs.getString("username"));
                a.setFullName(rs.getString("full_name"));
                a.setGender(rs.getString("gender"));
                a.setEmail(rs.getString("email"));
                a.setPhone(rs.getString("phone"));
                a.setAddress(rs.getString("address"));
                a.setStatus(rs.getString("status"));
                return a;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Account> getUsersByRole(String role) {

        List<Account> list = new ArrayList<>();

        String sql = """
        SELECT customer_id AS id, username, 'Customer' AS role, email, status
        FROM Customer
        WHERE ? = 'Customer'

        UNION ALL

        SELECT s.staff_id AS id, s.username, r.name AS role, s.email, s.status
        FROM Staff s
        JOIN Role r ON s.role_id = r.role_id
        WHERE r.name = ?
        ORDER BY username
    """;

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);

            ps.setString(1, role);
            ps.setString(2, role);

            ResultSet rs = ps.executeQuery();

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

    public void createStaff(String username, String fullname,
            String email, String phone,
            String password, String role) {

        String sql = """
        INSERT INTO Staff(username, full_name, email, phone, password, role_id, status)
        VALUES (?, ?, ?, ?, ?, 
            (SELECT role_id FROM Role WHERE name = ?),
            'active')
    """;

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, fullname);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, hashMD5(password));
            ps.setString(6, role);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createCustomer(String username, String fullname,
            String email, String phone,
            String password) {

        String sql = """
        INSERT INTO Customer(username, full_name, email, phone, password, status)
        VALUES (?, ?, ?, ?, ?, 'active')
    """;

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, fullname);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, hashMD5(password));

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class PasswordUtil {

        public static String generatePassword() {

            String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

            StringBuilder pass = new StringBuilder();

            for (int i = 0; i < 8; i++) {

                int index = (int) (Math.random() * chars.length());

                pass.append(chars.charAt(index));
            }

            return pass.toString();
        }
    }

    public void addUser(String username, String email, String fullName,
            String phone, String role, String password) {

        if ("Customer".equalsIgnoreCase(role)) {

            createCustomer(username, fullName, email, phone, password);

        } else {

            createStaff(username, fullName, email, phone, password, role);

        }
    }

    public void saveResetToken(String email, String token) {

        String sql = """
        INSERT INTO PasswordResetToken(email, token)
        VALUES (?, ?)
    """;

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, token);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changePassword(int id, String role, String newPassword) {

        String sql;

        if ("Customer".equalsIgnoreCase(role)) {
            sql = "UPDATE Customer SET password=?, must_change_password=0 WHERE customer_id=?";
        } else {
            sql = "UPDATE Staff SET password=?, must_change_password=0 WHERE staff_id=?";
        }

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, hashMD5(newPassword));
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePassword(int id, String role, String password) {
        String sql;

        if ("Customer".equalsIgnoreCase(role)) {
            sql = "UPDATE Customer SET password=? WHERE customer_id=?";
        } else {
            sql = "UPDATE Staff SET password=? WHERE staff_id=?";
        }

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, password);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateMustChangePassword(int id, String role, boolean value) {
        String sql;

        if ("Customer".equalsIgnoreCase(role)) {
            sql = "UPDATE Customer SET must_change_password=? WHERE customer_id=?";
        } else {
            sql = "UPDATE Staff SET must_change_password=? WHERE staff_id=?";
        }

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setBoolean(1, value);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean usernameExists(String username) {

        String sql = """
        SELECT username FROM Customer WHERE username = ?
        UNION
        SELECT username FROM Staff WHERE username = ?
    """;

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {
        AccountDAO dao = new AccountDAO();
        //System.out.println(dao.hashMD5("123456"));
        System.out.println(dao.login("admin@gmail.com", "admin123"));
    }
}
