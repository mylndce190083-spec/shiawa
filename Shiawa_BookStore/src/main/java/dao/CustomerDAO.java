/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import model.Customer;

/**
 *
 * @author BA LIEM
 */
public class CustomerDAO extends DBContext {

    public void updateTokenByEmail(String email, String token) {
        String sql = "UPDATE Customer SET verify_token=? WHERE email =?";
        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setString(1, token);
            st.setString(2, email);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Customer getCustomerByAccountId(int accountId) {
        String sql = "SELECT customer_id FROM Customer WHERE customer_id = ?";

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("customer_id"));
                return c;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Customer getCustomerByAccountIdUpgraded(int accountId) {
        String sql = "SELECT * FROM Customer WHERE customer_id = ?";

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("customer_id"));
                c.setUsername(rs.getString("username"));
                c.setEmail(rs.getString("email"));
                c.setAvatar(rs.getString("avatar"));
                c.setPhone(rs.getString("phone"));
                c.setAddress(rs.getString("address"));
                c.setFullname(rs.getString("full_name"));
                c.setPassword(rs.getString("password"));
                return c;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkCustomerExist(String email) {
        String sql = "SELECT customer_id FROM Customer WHERE email = ?";

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void insert(Customer customer) {
        String sql = """
    INSERT INTO Customer
    (username, password, email, full_name, status, verify_token, must_change_password)
    VALUES (?, ?, ?, ?, ?, ?, ?)
""";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, customer.getUsername());
            ps.setString(2, customer.getPassword());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getFullname());
            ps.setString(5, customer.getStatus());
            ps.setString(6, customer.getVerifyToken());
            ps.setBoolean(7, customer.isMustChangePassword());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean verifyUser(String token) {
        String sql = "UPDATE Customer SET status='active', verify_token=NULL WHERE verify_token=?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, token);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkEmailExist(String email) {
        String sql = "SELECT 1 FROM Customer WHERE email = ?";

        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void saveOTP(String email, String otp, Timestamp expiry) {
        String sql = "UPDATE Customer SET reset_otp=?, otp_expiry=? WHERE email=?";
        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setString(1, otp);
            st.setTimestamp(2, expiry);
            st.setString(3, email);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isValidOTP(String otp) {
        String sql = "SELECT * FROM Customer WHERE reset_otp=? AND otp_expiry > CURRENT_TIMESTAMP";
        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setString(1, otp);
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updatePassword(String password) {
        String sql = "UPDATE Customer SET password=?, reset_otp=NULL, otp_expiry=NULL WHERE reset_otp IS NOT NULL";
        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setString(1, password);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean updateProfile(int id, String username, String phone, String address, String fullname) {
        String sql = "UPDATE Customer SET username = ?, phone = ?, address = ?, full_name = ? WHERE customer_id = ?";

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, phone);
            ps.setString(3, address);
            ps.setString(4, fullname);
            ps.setInt(5, id);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void updateAvatar(int id, String avatarPath) {
        String sql = "UPDATE Customer SET avatar = ? WHERE customer_id = ?";

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, avatarPath);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePasswordCustomer(String password, int id) {
        String sql = "UPDATE Customer SET password=? WHERE customer_id =?";
        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setString(1, password);
            st.setInt(2, id);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
