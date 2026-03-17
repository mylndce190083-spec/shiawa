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
import model.Category;

/**
 *
 * @author BA LIEM
 */
public class CategoryDAO extends DBContext {

    public List<Category> getIdNameCategory() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT category_id, name FROM Category";

        try (PreparedStatement ps = getConnection().prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

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
                Category cate = new Category();
                cate.setCategoryId(rs.getInt("category_id"));
                cate.setCategoryName(rs.getString("name"));
                cate.setParentId(rs.getInt("parent_id"));
                list.add(cate);
            }

        } catch (Exception e) {

        }
        return list;
    }

    public Category getCategoryById(int id) {//sua lai method
        String sql = "select * from Category where category_id=?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Category c = new Category();
                c.setCategoryId(rs.getInt("category_id"));
                c.setCategoryName(rs.getString("name"));
                c.setParentId(rs.getInt("parent_id"));
                return c;
            }
        } catch (Exception e) {
            System.out.println("Not found!");
        }
        return null;
    }

    public List<Category> getAllParentCategory() {
        List<Category> list = new ArrayList<>();
        String sql = "select * from Category where parent_Id is NULL";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Category cate = new Category();
                cate.setCategoryId(rs.getInt("category_id"));
                cate.setCategoryName(rs.getString("name"));
                cate.setParentId(rs.getInt("parent_id"));
                list.add(cate);
            }

        } catch (Exception e) {

        }
        return list;
    }

    public List<Category> searchCateByTitle(String keyword) {
        List<Category> list = new ArrayList<>();
        String sql = "select * from Category where name LIKE ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Category cate = new Category();
                cate.setCategoryId(rs.getInt("category_id"));
                cate.setCategoryName(rs.getString("name"));
                cate.setParentId(rs.getInt("parent_id"));
                list.add(cate);
            }

        } catch (Exception e) {

        }
        return list;
    }

    public List<Category> getCateByParentId(Integer categoryParentId) {
        List<Category> list = new ArrayList<>();
        String sql = "  select * from Category where parent_id = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, categoryParentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Category cate = new Category();
                cate.setCategoryId(rs.getInt("category_id"));
                cate.setCategoryName(rs.getString("name"));
                cate.setParentId(rs.getInt("parent_id"));
                list.add(cate);
            }

        } catch (Exception e) {

        }
        return list;
    }

    public void insertChildCategory(Category cate) {
        String sql = """
        INSERT INTO Category
        (name, parent_id)
        VALUES (?, ?)
    """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, cate.getCategoryName());
            ps.setInt(2, cate.getParentId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertParentCategory(Category cate) {
        String sql = """
        INSERT INTO Category
        (name)
        VALUES (?)
    """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, cate.getCategoryName());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateChildCategory(Category cate) {
        String sql = """
        update Category set name =?, parent_id =? where category_id =?
    """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, cate.getCategoryName());
            ps.setInt(2, cate.getParentId());
            ps.setInt(3, cate.getCategoryId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateParentCategory(Category cate) {
        String sql = """
        update Category set name =? where category_id =?
    """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, cate.getCategoryName());
            ps.setInt(2, cate.getCategoryId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isCategoryHasBook(int categoryId) {
        String sql = """
        SELECT COUNT(*)
        FROM Book
        WHERE category_id = ?
    """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isParentCategoryHasChild(int categoryParentId) {
        String sql = """
        SELECT COUNT(*) 
        FROM Category
        WHERE parent_id = ?
    """;
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, categoryParentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deleteCategory(int categoryId) {
        String sql = """
        DELETE FROM Category 
        WHERE category_id = ?
    """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, categoryId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CategoryDAO dao = new CategoryDAO();
        List<Category> list = new ArrayList<>();
        list = dao.getAllParentCategory();
        System.out.println(dao.getCategoryById(5));
        System.out.println(dao.searchCateByTitle("học"));
//        for (Category c : list) {
//            System.out.println("kkkkkkkkkkkk");
//            System.out.println(c);
//        }
        System.out.println(dao.getCateByParentId(13));
        Category cate = new Category();
        cate.setCategoryName("Sách vovinam");
        cate.setParentId(19);
        cate.setCategoryId(17);
        dao.updateChildCategory(cate);
    }

}
