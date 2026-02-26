/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;

/**
 *
 * @author BA LIEM
 */
public class CategoryDAO extends DBContext {
    public List<Category> getIdNameCategory() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT category_id, name FROM Category";

        try (PreparedStatement ps = getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Category c = new Category();
                c.setCategoryId(rs.getInt("category_id"));
                c.setCategoryName(rs.getString("name"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Category> getAllCategory() {
        List<Category> list = new ArrayList<>();
        String sql = "select * from Category";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("category_id");
                String name = rs.getString("name");
                int pId = rs.getInt("parent_id");
                //tao doi tuong category
               // Category cate = new Category(id, name, pId);
                Category cate = new Category(id, name, pId);
                list.add(cate);
            }

        } catch (Exception e) {

        }
        return list;
    }
    
    public Category getCategoryById(int id) {
        String sql ="select * from Category where category_id=?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int cat_id = rs.getInt("category_id");
                String cat_name = rs.getString("name");
                int pId = rs.getInt("parent_id");
                Category c = new Category(cat_id, cat_name, pId);
                 
                return c;
            }
        } catch(Exception e) {
            System.out.println("Not found!");
        }
        return null;
    }
    // ĐÂY LÀ HÀM QUAN TRỌNG NHẤT: Gom nhóm Cha-Con
    public List<Category> getGroupCategories() {
        List<Category> allCategories = new ArrayList<>();
        String sql = "SELECT * FROM Category";
        
        try (PreparedStatement ps = getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            // Bước 1: Lấy tất cả vào một danh sách tạm
            while (rs.next()) {
                Category ca = new Category(
                    rs.getInt("category_id"),
                    rs.getString("name"),
                    rs.getInt("parent_id")
                );
                allCategories.add(ca);
            }

            // Bước 2: Tạo danh sách chứa các ông Cha (parent_id = 0)
            List<Category> rootParents = new ArrayList<>();
            for (Category c : allCategories) {
                if (c.getParentId() == 0) {
                    rootParents.add(c);
                }
            }

            // Bước 3: Với mỗi ông Cha, đi tìm các đứa Con của ông ấy
            for (Category parent : rootParents) {
                for (Category child : allCategories) {
                    if (child.getParentId() == parent.getCategoryId()) {
                        parent.getChildCategories().add(child);
                    }
                }
            }
            
            return rootParents; // Trả về danh sách cha đã có con bên trong

        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }
}