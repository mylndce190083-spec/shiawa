/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Book;
import model.Category;
import utils.DBContext;

/**
 *
 * @author Lenovo
 */
public class BookDAO extends DBContext{
    public List<Book> getAllBook() {
        List<Book> list = new ArrayList<>();
        CategoryDAO dao = new CategoryDAO();
        String sql = "select * from Book";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                //tao cate
                int cateId = rs.getInt("category_id");
                Category cate = dao.getCategoryById(cateId);
                
                int stock = rs.getInt("stock");
                String publisher = rs.getString("publisher");
                int discount = rs.getInt("discount");
                String imgUrl = rs.getString("url_img");
                boolean isActive = rs.getBoolean("is_active");
                LocalDateTime createAte = rs.getTimestamp("created_at").toLocalDateTime();
                //tao doi tuong product
                Book b = new Book(id, title, author, price, description, cate, stock, publisher, discount, imgUrl, isActive, createAte);
                list.add(b);
            }
            
        } catch (Exception e) {
            
        }
        return list;
    }
    
    public static void main(String[] args) {
        BookDAO dao = new BookDAO();
        List<Book> list= dao.getAllBook();
        for (Book e : list) {
            System.out.println(e);
        }
    }
}
