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
import model.BookImage;

/**
 *
 * @author BA LIEM
 */
public class BookImageDAO extends DBContext {

    public boolean create(BookImage image) {
        String sql = """
        INSERT INTO BookImages
        (book_id, image_url, image_title,
         is_primary, display_order, is_active)
        VALUES (?, ?, ?, ?, ?, 1)
    """;

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, image.getBookId());
            ps.setString(2, image.getImageUrl());
            ps.setString(3, image.getImageTitle());
            ps.setBoolean(4, image.getPrimary());
            ps.setInt(5, image.getDisplayOrder());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<BookImage> getByBookId(int bookId) {
        List<BookImage> list = new ArrayList<>();
        String sql = """
        SELECT * FROM BookImages
        WHERE book_id = ? AND is_active = 1
        ORDER BY is_primary DESC, display_order ASC
    """;
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BookImage img = new BookImage();
                img.setImageId(rs.getInt("image_id"));
                img.setBookId(rs.getInt("book_id"));
                img.setImageUrl(rs.getString("image_url"));
                img.setPrimary(rs.getBoolean("is_primary"));
                img.setDisplayOrder(rs.getInt("display_order"));
                img.setActive(rs.getBoolean("is_active"));
                list.add(img);
            }
        } catch (Exception e) {
        }
        return list;
    }
    
    public boolean setPrimary(int imageId, int bookId) {
        String resetSql = "UPDATE BookImages SET is_primary = 0 WHERE book_id = ?";
        String setSql = "UPDATE BookImages SET is_primary = 1 WHERE image_id = ?";
        String updateBookSql = """
        UPDATE Book 
        SET url_img = (SELECT image_url FROM BookImages WHERE image_id = ?)
        WHERE book_id = ?
    """;

        try {
            var con = getConnection();

            PreparedStatement ps1 = con.prepareStatement(resetSql);
            ps1.setInt(1, bookId);
            ps1.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement(setSql);
            ps2.setInt(1, imageId);
            ps2.executeUpdate();

            PreparedStatement ps3 = con.prepareStatement(updateBookSql);
            ps3.setInt(1, imageId);
            ps3.setInt(2, bookId);

            return ps3.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int imageId) {
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "UPDATE BookImages SET is_active = 0 WHERE image_id = ?"
            );
            ps.setInt(1, imageId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public void clearPrimaryByBookId(int bookId) {
        String sql = "UPDATE BookImages SET is_primary = 0 WHERE book_id = ?";

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, bookId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPrimaryId(int imageId) {
        String sql = "UPDATE BookImages SET is_primary = 1 WHERE image_id = ?";

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, imageId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteByBookId(int bookId) {
        String sql = "UPDATE BookImages SET is_active = 0 WHERE book_id = ?";

        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, bookId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
