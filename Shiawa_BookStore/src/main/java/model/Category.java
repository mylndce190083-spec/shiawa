/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
<<<<<<< HEAD
 * @author BA LIEM
 */
public class Category {
    private int categoryId;
    private String categoryName;
=======
 * @author Lenovo
 */
public class Category {
    private int cateId;
    private String cateName;

    public Category(int cateId, String cateName) {
        this.cateId = cateId;
        this.cateName = cateName;
    }
>>>>>>> 6eec6e2c6e3608949a045ca087e1b084a6b72c92

    public Category() {
    }

<<<<<<< HEAD
    public Category(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    
}
=======
    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    @Override
    public String toString() {
        return "Category{" + "cateId=" + cateId + ", cateName=" + cateName + '}';
    }
    
    
}
>>>>>>> 6eec6e2c6e3608949a045ca087e1b084a6b72c92
