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
                c.setCateId(rs.getInt("category_id"));
                c.setCateName(rs.getString("name"));
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
                // tao doi tuong category
                Category cate = new Category(id, name);
                list.add(cate);
            }

        } catch (Exception e) {
            System.out.println("Error in getAllCategory: " + e.getMessage());
            e.printStackTrace();
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public Category getCategoryById(int id) {
        String sql = "select * from Category where category_id=?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int cat_id = rs.getInt("category_id");
                String cat_name = rs.getString("name");
                Category c = new Category(cat_id, cat_name);
                return c;
            }
        } catch (Exception e) {
            System.out.println("Not found!");
        }
        return null;
    }
}
