/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Lenovo
 */
public class Category {
    private int cateId;
    private String cateName;

    public Category(int cateId, String cateName) {
        this.cateId = cateId;
        this.cateName = cateName;
    }

    public Category() {
    }

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


